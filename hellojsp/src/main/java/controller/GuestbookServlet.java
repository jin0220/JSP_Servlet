package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.EmaillistVo;
import vo.GuestbookVo;
import dao.*;

@WebServlet(description = "guestbook 서블릿", urlPatterns = {"/views"})
public class GuestbookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GuestbookServlet() {
      super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("GuestBookServlet.doGet() 호출");
      request.setCharacterEncoding("utf-8");

      String actionName = request.getParameter("a");
      System.out.println("actionName => " + actionName);
      
      if("deleteform".equals(actionName)) {
        // deleteform action 작성
        
        RequestDispatcher rd = request.getRequestDispatcher("/views/deleteform.jsp");
        rd.forward(request, response);
      }
      else if("add".equals(actionName)) {
    	  System.out.println("actionName => " + actionName);
        request.setCharacterEncoding("utf-8");
		// add action 작성
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        String content = request.getParameter("content");
        
        GuestbookVo vo = new GuestbookVo(name, pwd, content);
        
        GuestbookDaoImpl dao = new GuestbookDaoImpl();
        dao.insert(vo);
        
        response.sendRedirect("/hellojsp/views");
      }else if("delete".equals(actionName)) {
        request.setCharacterEncoding("utf-8");
        // delete action 작성
        int no = Integer.parseInt(request.getParameter("no"));
        String dbpass = request.getParameter("dbpass");
        String password = request.getParameter("password");
        
        if(dbpass.equals(password)) {
        	GuestbookVo vo = new GuestbookVo(no, password);
            
            GuestbookDaoImpl dao = new GuestbookDaoImpl();
            dao.delete(vo);
        }
        
        response.sendRedirect("/hellojsp/views");
      }else {
        GuestbookDao dao = new GuestbookDaoImpl();
        List<GuestbookVo> list = dao.getList();

        request.setAttribute("list", list);

        RequestDispatcher rd = request.getRequestDispatcher("/views/index.jsp");
        rd.forward(request, response);
      }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}