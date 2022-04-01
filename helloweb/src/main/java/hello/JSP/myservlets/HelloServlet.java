package hello.JSP.myservlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(description = "처음 만드는 서블릿", urlPatterns = { "/HelloServlet" })
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// 컨텐츠 타입 선언 및 한글 설정
		response.setContentType("text/html;charset=UTF-8");
		
		// 웹브라우저 출력을 위한 PrintWriter 객체 확보
		PrintWriter out = response.getWriter();
		// HTML 형식으로 브라우저 출력 컨텐츠 작성
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Hello Servlet</TITLE></HEAD>");
		out.println("<BODY><H2>Hello Servlet 호출성공!!</H2></BODY>");
		out.println("</HTML>");
		System.out.println("HelloServlet 동작 완료");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
