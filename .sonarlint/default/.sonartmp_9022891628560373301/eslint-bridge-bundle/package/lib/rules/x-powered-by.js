"use strict";
/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2020 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
// https://jira.sonarsource.com/browse/RSPEC-5689
Object.defineProperty(exports, "__esModule", { value: true });
const utils_1 = require("./utils");
const HELMET = 'helmet';
const EXPRESS = 'express';
const HIDE_POWERED_BY = 'hide-powered-by';
const HEADER_X_POWERED_BY = 'X-Powered-By'.toLowerCase();
const MESSAGE = 'Disable the fingerprinting of this web technology.';
const PROTECTING_MIDDLEWARES = [HELMET, HIDE_POWERED_BY];
/** Expected number of arguments in `app.set`. */
const APP_SET_NUM_ARGS = 2;
exports.rule = {
    create(context) {
        let appInstantiation = null;
        let isSafe = false;
        return {
            Program() {
                appInstantiation = null;
                isSafe = false;
            },
            CallExpression: (node) => {
                if (!isSafe && appInstantiation) {
                    const callExpr = node;
                    isSafe =
                        isUsingMiddleware(context, callExpr, appInstantiation, isProtecting(context)) ||
                            isDisabledXPoweredBy(callExpr, appInstantiation) ||
                            isSetFalseXPoweredBy(callExpr, appInstantiation) ||
                            isAppEscaping(callExpr, appInstantiation);
                }
            },
            VariableDeclarator: (node) => {
                if (!isSafe && !appInstantiation) {
                    const varDecl = node;
                    const app = attemptFindExpressAppInstantiation(varDecl, context);
                    if (app) {
                        appInstantiation = app;
                    }
                }
            },
            'Program:exit'() {
                if (!isSafe && appInstantiation) {
                    context.report({
                        node: appInstantiation,
                        message: MESSAGE,
                    });
                }
            },
        };
    },
};
function attemptFindExpressAppInstantiation(varDecl, context) {
    var _a;
    const rhs = varDecl.init;
    if (rhs && rhs.type === 'CallExpression') {
        const { callee } = rhs;
        if (((_a = utils_1.getModuleNameOfNode(context, callee)) === null || _a === void 0 ? void 0 : _a.value) === EXPRESS) {
            const pattern = varDecl.id;
            return pattern.type === 'Identifier' ? pattern : undefined;
        }
    }
    return undefined;
}
/**
 * Checks whether the expression looks somewhat like `app.use(m1, [m2, m3], ..., mN)`,
 * where one of `mK`-nodes satisfies the given predicate.
 */
function isUsingMiddleware(context, callExpression, app, middlewareNodePredicate) {
    if (isMethodInvocation(callExpression, app.name, 'use', 1)) {
        const flattenedArgs = utils_1.flattenArgs(context, callExpression.arguments);
        return Boolean(flattenedArgs.find(middlewareNodePredicate));
    }
    return false;
}
/**
 * Checks whether a node looks somewhat like `require('m')()` for
 * some middleware `m` from the list of middlewares.
 */
function isMiddlewareInstance(context, middlewares, n) {
    var _a;
    if (n.type === 'CallExpression') {
        const usedMiddleware = (_a = utils_1.getModuleNameOfNode(context, n.callee)) === null || _a === void 0 ? void 0 : _a.value;
        if (usedMiddleware) {
            return middlewares.includes(String(usedMiddleware));
        }
    }
    return false;
}
/**
 * Checks whether node looks like `helmet.hidePoweredBy()`.
 */
function isHidePoweredByFromHelmet(context, n) {
    var _a;
    if (n.type === 'CallExpression') {
        const callee = n.callee;
        return (callee.type === 'MemberExpression' &&
            ((_a = utils_1.getModuleNameOfNode(context, callee.object)) === null || _a === void 0 ? void 0 : _a.value) === HELMET &&
            callee.property.type === 'Identifier' &&
            callee.property.name === 'hidePoweredBy');
    }
    return false;
}
function isProtecting(context) {
    return (n) => isMiddlewareInstance(context, PROTECTING_MIDDLEWARES, n) ||
        isHidePoweredByFromHelmet(context, n);
}
function isDisabledXPoweredBy(callExpression, app) {
    if (isMethodInvocation(callExpression, app.name, 'disable', 1)) {
        const arg0 = callExpression.arguments[0];
        return arg0.type === 'Literal' && String(arg0.value).toLowerCase() === HEADER_X_POWERED_BY;
    }
    return false;
}
function isSetFalseXPoweredBy(callExpression, app) {
    if (isMethodInvocation(callExpression, app.name, 'set', APP_SET_NUM_ARGS)) {
        const [headerName, onOff] = callExpression.arguments;
        return (headerName.type === 'Literal' &&
            String(headerName.value).toLowerCase() === HEADER_X_POWERED_BY &&
            onOff.type === 'Literal' &&
            onOff.value === false);
    }
    return false;
}
function isMethodInvocation(callExpression, objectIdentifierName, methodName, minArgs) {
    return (callExpression.callee.type === 'MemberExpression' &&
        utils_1.isIdentifier(callExpression.callee.object, objectIdentifierName) &&
        utils_1.isIdentifier(callExpression.callee.property, methodName) &&
        callExpression.callee.property.type === 'Identifier' &&
        callExpression.arguments.length >= minArgs);
}
function isAppEscaping(callExpr, app) {
    return Boolean(callExpr.arguments.find(arg => arg.type === 'Identifier' && arg.name === app.name));
}
//# sourceMappingURL=x-powered-by.js.map