package com.javaex.dao;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo selectUserVo(UserVo userVo) {
		return sqlSession.selectOne("userXml.selectUser", userVo);
	}
	
	public int insertUserVo(UserVo userVo) {
		System.out.println(userVo);
		return sqlSession.insert("userXml.insertUserVo", userVo);
	}


	public UserVo selectUserVo(String id) {
		System.out.println("userdao" + id);
		return sqlSession.selectOne("userXml.selectUserVo", id);
	}
}
