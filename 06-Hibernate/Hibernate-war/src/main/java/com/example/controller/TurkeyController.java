package com.example.controller;

import com.example.model.Turkey;
import com.example.service.TurkeyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TurkeyController {

    TurkeyService turkeyService = new TurkeyService();

    public void getAllTurkey(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getMethod().toLowerCase().equals("get")) {
            resp.setContentType("text/json");
            try {
                resp.getWriter().println(turkeyService.getAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            resp.sendError(400);
        }
    }
    public void createTurkey(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getMethod().toLowerCase().equals("post")) {
            Turkey turkey = new ObjectMapper().readValue(req.getInputStream(), Turkey.class);
            if (turkeyService.create(turkey) == null) {
                resp.sendError(406, "Unable to create account");
            } else {
                System.out.println(turkey);
            }
        } else {
            resp.sendError(400);
        }
    }
}