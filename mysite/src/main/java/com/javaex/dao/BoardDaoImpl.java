package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDaoImpl implements BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, "webdb", "1234");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 로드 실패!");
		}
		return conn;
	}

	public List<BoardVo> getList() {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.hit, b.reg_date, b.user_no, u.name " + " from board b, users u "
					+ " where b.user_no = u.no " + " order by no desc";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");

				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, userName);
				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return list;

	}

	public BoardVo getBoard(int no) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo boardVo = null;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, u.name, b.file1, b.file2 "
					+ "from board b, users u " + "where b.user_no = u.no " + "and b.no = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");
				String file1 = rs.getString("file1");
				String file2 = rs.getString("file2");

				boardVo = new BoardVo(no, title, content, hit, regDate, userNo, userName, file1, file2);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		System.out.println(boardVo);
		return boardVo;

	}

	public int insert(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			System.out.println("vo.userNo : [" + vo.getUserNo() + "]");
			System.out.println("vo.title : [" + vo.getTitle() + "]");
			System.out.println("vo.content : [" + vo.getContent() + "]");
			System.out.println("vo.getFile1 : [" + vo.getFile1() + "]");
			System.out.println("vo.getFile2 : [" + vo.getFile2() + "]");

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUserNo());
			pstmt.setString(4, vo.getFile1());
			pstmt.setString(5, vo.getFile2());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int delete(int no) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int update(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set title = ?, content = ? where no = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int update_hit(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set hit = ? where no = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, vo.getHit() + 1);
			pstmt.setInt(2, vo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}
	
	public int searchCount(String kwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		List<BoardVo> list = new ArrayList<BoardVo>();
		int count = 0;
		System.out.println(kwd);

		try {
			conn = getConnection();

			String query = " select count(*) cnt "
							+ "from ( select b.no, b.title, b.hit, b.reg_date, b.user_no, u.name, b.content, b.file1, b.file2 "
									+ " from board b, users u " 
									+ " where b.user_no = u.no " + " order by no desc) "
							+ " where name like ? or title like ? or content like ? or reg_date like ? or file1 like ? or file2 like ?";
			
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");
			pstmt.setString(3, "%" + kwd + "%");
			pstmt.setString(4, "%" + kwd + "%");
			pstmt.setString(5, "%" + kwd + "%");
			pstmt.setString(6, "%" + kwd + "%");

			rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count; 
	}

	public List<BoardVo> search(String kwd, int startRow, int pageSize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();
		System.out.println(kwd);

		try {
			conn = getConnection();

			String query = "select *" 
					+ "from ( select ROW_NUMBER () OVER (ORDER BY NO desc) num, a.* "
							+ "from ( select no, title, hit, reg_date, user_no, name, file1, file2 "
							+ "from ( select b.no, b.title, b.hit, b.reg_date, b.user_no, u.name, b.content, b.file1, b.file2 "
									+ " from board b, users u " 
									+ " where b.user_no = u.no " + " order by no desc) "
									+ " where name like ? or title like ? or content like ? or reg_date like ? or file1 like ? or file2 like ? ) a"
							+ " ) "
					+ "where num between ? and ? ";
			

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");
			pstmt.setString(3, "%" + kwd + "%");
			pstmt.setString(4, "%" + kwd + "%");
			pstmt.setString(5, "%" + kwd + "%");
			pstmt.setString(6, "%" + kwd + "%");
			
			pstmt.setInt(7, startRow);
			pstmt.setInt(8, pageSize);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");
				String file1 = rs.getString("file1");
				String file2 = rs.getString("file2");

				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, userName, file1, file2);

				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return list;
	}

	public List<BoardVo> getBoardList(int startRow, int pageSize) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();

			String query = "select *" + "from ( " + "select ROW_NUMBER () OVER (ORDER BY NO desc) num, a.* "
					+ "from (select b.no, b.title, b.hit, b.reg_date, b.user_no, u.name, b.content "
					+ " from board b, users u " + " where b.user_no = u.no " + " order by no desc) a " + " ) "
					+ "where num between ? and ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");

				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, userName);
				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return list;
	}
}
