package com.kosta.lmj.dao;

import java.util.List;

import com.kosta.lmj.vo.BoardVo;

public interface BoardDao {
	public List<BoardVo> getList();  // 게시물 전체 목록 조회
	public BoardVo getBoard(int no); // 게시물 상세 조회
	public int insert(BoardVo vo);   // 게시물 등록
	public int delete(int no);       // 게시물 삭제
	public int update(BoardVo vo);   // 게시물 수정
	public int update_hit(BoardVo vo); // 게시물 조회수
	public List<BoardVo> search(String kwd, int startRow, int pageSize); // 게시물 검색
	public List<BoardVo> getBoardList(int startRow, int pageSize); // 페이징 처리
	public int searchCount(String kwd); // 검색 게시물 개수
}
