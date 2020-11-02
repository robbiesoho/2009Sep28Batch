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
// https://jira.sonarsource.com/browse/RSPEC-2424
Object.defineProperty(exports, "__esModule", { value: true });
const globals_1 = require("../utils/globals");
exports.rule = {
    create(context) {
        const overriden = new Set();
        function isBuiltIn(variable) {
            return globals_1.globalsByLibraries.builtin.includes(variable.name);
        }
        function checkVariable(variable) {
            if (isBuiltIn(variable)) {
                variable.defs.forEach(def => overriden.add(def.name));
                variable.references
                    .filter(ref => ref.isWrite())
                    .forEach(ref => overriden.add(ref.identifier));
            }
        }
        function checkScope(scope) {
            scope.variables.forEach(checkVariable);
            scope.childScopes.forEach(checkScope);
        }
        return {
            Program: () => {
                checkScope(context.getScope());
            },
            'Program:exit': () => {
                overriden.forEach(node => context.report({
                    message: `Remove this override of "${node.name}".`,
                    node,
                }));
                overriden.clear();
            },
        };
    },
};
//# sourceMappingURL=no-built-in-override.js.map