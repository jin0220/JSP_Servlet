package com.javaex.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;

@Controller
public class ApiUserController {

//	@Autowired
//	private UserService userService;
	
//	/*아이디체크 : 회원가입시 사용중인 아이디인지 검사*/
//	@ResponseBody
//	@RequestMapping(value = "/api/user/idcheck", method = RequestMethod.POST)
//	public boolean cateList(String id) {
//
//		return userService.idCheck(id);
//	}
}
