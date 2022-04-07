package hellojsp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestBookDaoImpl implements GuestBookDao{
	
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

	@Override
	public List<GuestBookVo> getList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<GuestBookVo> list = new ArrayList<GuestBookVo>();
		
		try {
			conn = getConnection();
			
			String sql = "select * "
					+ " from guestbook "
					+ " order by no desc ";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String content = rs.getString("content");
				String date = rs.getString("reg_date");
				
				GuestBookVo vo = new GuestBookVo(no, name, content, date);
				list.add(vo);
			}
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}finally {
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

	@Override
	public int insert(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0 ;
		
		try {
			conn = getConnection();
			
			String sql = "insert into guestbook values (seq_guestbook_no.nextval, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 등록");
			
		}catch(SQLException e){
			System.out.println("error:" + e);
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		return count;
	}

	@Override
	public int delete(GuestBookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt_d = null;
		ResultSet rs = null;
		
		String sql_pass = "select * from guestbook where no = ?";
		
		String sql = "delete from guestbook where no = ?";
		
		try{
			conn = getConnection();
			
			pstmt = conn.prepareStatement(sql_pass, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1,vo.getNo());

			rs = pstmt.executeQuery();
			
			rs.last();
			int rowCount = rs.getRow();
			rs.beforeFirst();
			
			if(rowCount > 0 && rs.next()){
				if(vo.getName().equals(rs.getString("name")) && vo.getPassword().equals(rs.getString("password"))){
					pstmt_d = conn.prepareStatement(sql);
					
					pstmt_d.setInt(1, vo.getNo());
					
					pstmt_d.executeUpdate();
				}	
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if (pstmt != null)
					pstmt.close();
				if (pstmt_d != null)
					pstmt_d.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}
