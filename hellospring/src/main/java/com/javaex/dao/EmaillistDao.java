package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.EmaillistVo;

@Repository
public class EmaillistDao {
	@Autowired
	private SqlSession sqlSession;

	public List<EmaillistVo> getList() {
		System.out.println("----> sqlSession.selectList()");
		System.out.println(sqlSession);
		return sqlSession.selectList("EmaillistXml.selectList");
	}

	public int insert(EmaillistVo vo) {
		System.out.println(vo);
		return sqlSession.insert("EmaillistXml.insert", vo);
	}
}
