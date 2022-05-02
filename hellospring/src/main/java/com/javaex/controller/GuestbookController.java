package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.servlet.ModelAndView;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Controller
public class GuestbookController {
  @Autowired
  GuestbookDao guestbookDao;
  
  @RequestMapping("/getGuestbookList")
  public ModelAndView getGuestbookList( ModelAndView mav) {
    System.out.println(">>> "+this.getClass()+ " 호출됨!");
    
//    mav = new ModelAndView(); 
    
    mav.addObject( "guestbookList", guestbookDao.getList() );
//    mav.setViewName( "/WEB-INF/views/getGuestbookList.jsp" );
    mav.setViewName( "getGuestbookList" );
    return mav;
  }
  
}