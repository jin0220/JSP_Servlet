package com.javaex.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.javaex.dao.BoardDao;
import com.javaex.dao.BoardDaoImpl;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 파일 업로드
	private static final String CHARSET = "utf-8";
    private static final String ATTACHES_DIR = "C:\\Users\\hhh73\\StudioProjects\\JSP_Servlet\\mysite\\src\\main\\webapp\\files";
    private static final int LIMIT_SIZE_BYTES = 1024 * 1024;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String actionName = request.getParameter("a");
		System.out.println("board:" + actionName);

		if ("list".equals(actionName)) {
//			// 리스트 가져오기
//			BoardDao dao = new BoardDaoImpl();
//			List<BoardVo> list = dao.getList();
//
//			System.out.println(list.toString());
//
//			// 리스트 화면에 보내기
//			request.setAttribute("list", list);
//			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
			BoardDao dao = new BoardDaoImpl();
			int cnt = dao.getList().size();
			
			// 한 페이지에 출력될 리스트 개수
			int pageSize = 10;
			
			// 현재 페이지 정보 설정
			String pageNum = request.getParameter("pageNum");
			if(pageNum == null) {
				pageNum = "1";
			}
			System.out.println(pageNum);
			
			
			// 각 페이지의 첫행번호를 계산
			int currentPage = Integer.parseInt(pageNum);
			int startRow = (currentPage-1) * pageSize + 1;
			
			List<BoardVo> list = dao.getBoardList(startRow, pageSize * Integer.parseInt(pageNum));
			System.out.println(list.toString());
			
			request.setAttribute("list", list);
			if(cnt % pageSize == 0) {
				request.setAttribute("totalPage", cnt / pageSize);
			}
			else {
				request.setAttribute("totalPage", cnt / pageSize + 1);
			}
			request.setAttribute("currentPage", currentPage);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("read".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);

			System.out.println(boardVo.toString());
			
			if(request.getParameter("hit") != null) {
				// 게시물 조회수 올리기
				int hit = Integer.parseInt(request.getParameter("hit"));
				BoardVo vo = new BoardVo(no, hit);
				BoardDao dao2 = new BoardDaoImpl();
				
				dao2.update_hit(vo);
			}

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		} else if ("modifyform".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
		} else if ("modify".equals(actionName)) {
			// 게시물 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo vo = new BoardVo(no, title, content);
			BoardDao dao = new BoardDaoImpl();
			
			dao.update(vo);
			
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		} else if ("writeform".equals(actionName)) {
			// 로그인 여부체크
			UserVo authUser = getAuthUser(request);
			if (authUser != null) { // 로그인했으면 작성페이지로
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");
			} else { // 로그인 안했으면 리스트로
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}

		} else if ("write".equals(actionName)) {
			// 파일 업로드
			PrintWriter out = response.getWriter();
			 
	        File attachesDir = new File(ATTACHES_DIR);
	 
	 
	        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); // 업로드의 데이터를 메모리에 임시로 저장할 크기를 만듦
	        fileItemFactory.setRepository(attachesDir); // 업로드 저장파일을 임시로 저장할 폴더 생성
	        fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES); // 업로드 메모리 크기를 지정
	        ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory); // 파일 업로드 핸들러
	        
	        
	        String[] file = new String[2];
	        int i = 0;
	        
	        String title = null, content = null;

	        try {
	        	System.out.println("성공!");
	            List<FileItem> items = fileUpload.parseRequest(request);
	            for (FileItem item : items) {
	                if (item.isFormField()) { // input의 text와 같은 속성
	                    System.out.printf("파라미터 명 : %s, 파라미터 값 :  %s \n", item.getFieldName(), item.getString(CHARSET));
	                    
	                    if(item.getFieldName().equals("title")) {
	                    	title = item.getString(CHARSET);
	                    }else if(item.getFieldName().equals("content")) {
	                    	content = item.getString(CHARSET);
	                    }
	                } else {
	                    System.out.printf("파라미터 명 : %s, 파일 명 : %s,  파일 크기 : %s bytes \n", item.getFieldName(),
	                            item.getName(), item.getSize());
	                    if (item.getSize() > 0) {
	                        String separator = File.separator;
	                        int index =  item.getName().lastIndexOf(separator);
	                        file[i] = item.getName().substring(index  + 1);
	                        System.out.println("index : "+ i +" fileName " +file[i]);
	                        File uploadFile = new File(ATTACHES_DIR +  separator + file[i]);
	                        if(uploadFile.exists()) { // 중복된 파일명이 존재할 경우
	                        	boolean check = true;
	                        	
	                        	int j = 0;
	                        	while(check) {
	                        		j++;
	                        		uploadFile = new File(ATTACHES_DIR +  separator + file[i] + "(" + j + ")");
	                        		if(!uploadFile.exists()) {
	                        			break;
	                        		}
	                        	}
	                        }
	                        item.write(uploadFile);
	                        i++;
	                    }
	                }
	            }
	        } catch (Exception e) {
	            // 파일 업로드 처리 중 오류가 발생하는 경우
	        	System.out.println("에러");
	            e.printStackTrace();
	        }

	        
	        
			UserVo authUser = getAuthUser(request);
			
			int userNo = authUser.getNo();
			System.out.println("userNo : ["+userNo+"]");
			System.out.println("title : ["+title+"]");
			System.out.println("content : ["+content+"]");
			System.out.println("file1 : ["+file[0]+"]");
			System.out.println("file2 : ["+file[1]+"]");

			BoardVo vo = new BoardVo(userNo, title, content, file[0], file[1]);
			BoardDao dao = new BoardDaoImpl();
			dao.insert(vo);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		} else if ("delete".equals(actionName)) {
			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao dao = new BoardDaoImpl();
			dao.delete(no);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		} else if ("search".equals(actionName)) {
			BoardDao dao = new BoardDaoImpl();
			List<BoardVo> list = dao.search(request.getParameter("kwd"));

			System.out.println(list.toString());

			// 리스트 화면에 보내기
			request.setAttribute("list", list);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("download".equals(actionName)) { 
			String fileName = request.getParameter("fileName");
			System.out.println(ATTACHES_DIR + File.separator + fileName);
			File file = new File(ATTACHES_DIR + File.separator + fileName);
			if(file.exists()) {
				OutputStream os = null;
				FileInputStream is = null;
				String encodedName = null;
				if(request.getHeader("User-Agent").contains("Firefox")) {
					encodedName = new String(fileName.getBytes("utf-8"),"ios-8859-1");
				}
				else {
					encodedName = URLEncoder.encode(fileName,"utf-8");
					encodedName = encodedName.replaceAll("\\+"," ");
				}
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Dispostion", "attachment;filename=" + fileName + ";");
				try {
					os = response.getOutputStream();
					is = new FileInputStream(file);
					int su = 0;
					while((su = is.read()) != -1) {
						os.write(su);
					}
					System.out.println("다운");
				}catch(IOException e) {
					System.out.println("에러!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					e.printStackTrace();
				}finally {
					try {
						if(os != null) os.close();
						if(is != null) is.close();
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// 로그인 되어 있는 정보를 가져온다.
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

}
