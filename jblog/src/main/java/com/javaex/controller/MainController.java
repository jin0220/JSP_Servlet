package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	/*JBlog 메인 폼 출력*/
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String main() {
		return "main/index";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String blogMain() {
		return "blog/blog-main";
	}
}
