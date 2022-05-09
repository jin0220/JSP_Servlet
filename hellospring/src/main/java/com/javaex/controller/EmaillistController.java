package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.javaex.dao.EmaillistDao;
import com.javaex.vo.EmaillistVo;

@Controller
public class EmaillistController {
	@Autowired
	EmaillistDao dao;

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form() {
		System.out.println("form");
//		return "/WEB-INF/views/form.jsp";
		return "redirect:/getList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute EmaillistVo emaillistVo) {
		System.out.println("add");
		System.out.println(emaillistVo.toString());
		dao.insert(emaillistVo);
		return "redirect:/getList";
	}
	
	@RequestMapping(value="/getList")
	public ModelAndView getList(ModelAndView mav) {
		mav.addObject("emaillistList", dao.getList());
		mav.setViewName("list");
		return mav;
	}
}
