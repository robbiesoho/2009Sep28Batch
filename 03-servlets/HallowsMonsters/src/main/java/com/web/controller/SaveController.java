package com.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.model.Monster;
import com.web.service.MonsterService;
import com.web.service.MonsterTypeService;

public class SaveController {

	private MonsterService ms;
	private MonsterTypeService mts;

	public SaveController() {
		this(new MonsterService(), new MonsterTypeService());
	}

	public SaveController(MonsterService ms, MonsterTypeService mts) {
		super();
		this.ms = ms;
		this.mts = mts;
	}

	public void save(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			Monster m = new ObjectMapper().readValue(req.getInputStream(), Monster.class);
			System.out.println(m);
			int typeResult = mts.save(m.getType());
			int monResult = ms.save(m);
			res.getWriter().println("the monster is added");
		} catch (IOException e) {
			e.printStackTrace();
			res.getWriter().println("something went wrong");
		}
	}
}
