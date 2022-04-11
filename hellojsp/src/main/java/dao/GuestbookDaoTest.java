package dao;

public class GuestbookDaoTest {

	public static void main(String[] args) {
		
		GuestbookDao dao = new GuestbookDaoImpl();
		System.out.println(dao.getList().toString());
	}
	
}
