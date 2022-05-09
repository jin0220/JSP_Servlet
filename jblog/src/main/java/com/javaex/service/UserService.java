package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.BlogDao;
import com.javaex.dao.CateDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CateVo;
//import com.javaex.vo.BlogVo;
//import com.javaex.vo.CateVo;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CateDao cateDao;
	
	

	/*회원가입: 회원정보, 블로그기본정보, 카테고리기본정보가 저장되어야함*/
	@Transactional
	public void join(UserVo userVo) {
		//회원정보 저장
		int userNo = userDao.insertUserVo(userVo); // 키값 필요
		
		//블로그 초기값 저장(개설)
		BlogVo blogVo = new BlogVo();
		blogVo.setUserNo(userNo);
		blogVo.setBlogTitle(userVo.getUserName() +"의 블로그입니다.");
		blogVo.setLogoFile("default");
		
		blogDao.insertBlog(blogVo);
		
		//카테고리 초기값 저장
		CateVo cateVo = new CateVo();
		cateVo.setCateName("미분류");
		cateVo.setDescription("기본으로 만들어지는 카테고리 입니다.");
		cateVo.setUserNo(userNo);
		
		cateDao.insertCate(cateVo);
	}
	
	/*아이디체크 : 회원가입시 사용중인 아이디인지 검사*/
	public boolean idCheck(String id) {
		boolean isExist;
		UserVo userVo = userDao.selectUserVo(id);
		if(userVo == null) {
			isExist = false; //존재하니-->아니요: 사용할 수 있음
		} else {
			isExist = true;  //존재하니-->예: 사용할 수 없음
		}
		return isExist;
	}
	
	/*로그인*/
	public UserVo login(UserVo userVo) {
		//회원정보 가져오기
		UserVo authUser = userDao.selectUserVo(userVo);
		return authUser;
	}
}
