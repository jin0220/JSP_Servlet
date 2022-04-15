package com.kosta.lmj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kosta.lmj.dao.BoardDao;
import com.kosta.lmj.dao.BoardDaoImpl;
import com.kosta.lmj.util.WebUtil;
import com.kosta.lmj.vo.BoardVo;
import com.kosta.lmj.vo.UserVo;

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
			
			// 페이징
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
			int currentPage = Integer.parseInt(pageNum); // 현재 페이지 번호
			int startRow = (currentPage-1) * pageSize + 1; // 현재 페이지의 결과 첫 행의 행번호
			
			// 페이징 처리를 위해 시작 행 번호와 마지막행 번호를 전달
			List<BoardVo> list = dao.getBoardList(startRow, pageSize * Integer.parseInt(pageNum));
			System.out.println(list.toString());
			
			request.setAttribute("list", list); // 현재 페이지의 게시물 데이터 전달
			
			// 해당 페이지의 게시물을 view로 전달
			if(cnt % pageSize == 0) {
				request.setAttribute("totalPage", cnt / pageSize);
			}
			else {
				request.setAttribute("totalPage", cnt / pageSize + 1);
			}
			
			// 현재 페이지 번호 전달
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
//	                    System.out.printf("파라미터 명 : %s, 파일 명 : %s,  파일 크기 : %s bytes \n", item.getFieldName(),
//	                            item.getName(), item.getSize());
	                    if (item.getSize() > 0) {
	                        String separator = File.separator;
	                        int index =  item.getName().lastIndexOf(separator);
	                        file[i] = item.getName().substring(index  + 1);
	                        
//	                        System.out.println("index : "+ i +" fileName " +file[i]);
	                        
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
			// 검색 결과 및 페이징 처리 구현
			BoardDao dao = new BoardDaoImpl();
			
			// 한 페이지에 출력될 리스트 개수
			int pageSize = 10;
			
			// 현재 페이지 정보 설정
			String pageNum = request.getParameter("pageNum"); // 버튼을 누르면 페이지 값 들어옴.
			if(pageNum == null) {
				pageNum = "1";
			}
			System.out.println("pageNum" + pageNum);
			
			
			// 각 페이지의 첫행번호를 계산
			int currentPage = Integer.parseInt(pageNum); // 현재 페이지 번호
			int startRow = (currentPage-1) * pageSize + 1; // 현재 페이지의 결과 첫 행의 행번호
			
			// 페이징 처리를 위해 키워드와 시작 행 번호와 마지막행 번호를 전달
			List<BoardVo> list = dao.search(request.getParameter("kwd"), startRow, pageSize * Integer.parseInt(pageNum));
			
			// 페이지 버튼을 만들기 위해 검색 결과 게시물 개수를 받아옴.
			int cnt = dao.searchCount(request.getParameter("kwd"));
			System.out.println(cnt);
			
			// 해당 페이지의 게시물을 view로 전달
			request.setAttribute("list", list);
			
			// 만들어져야할 페이지 버튼의 개수를 view로 전달
			if(cnt % pageSize == 0) {
				request.setAttribute("totalPage", cnt / pageSize);
			}
			else {
				request.setAttribute("totalPage", cnt / pageSize + 1);
			}
			
			request.setAttribute("currentPage", currentPage); // view에서 현재 페이지 번호를 전달
			request.setAttribute("a", "search"); // 서블릿에서 list와 search 부분의 페이징 처리를
												 // 따로 구현했기 때문에 list와 search를 구분하기 위해 view에 전달
			request.setAttribute("kwd", request.getParameter("kwd")); // 검색을 하게되면 input의 값이 초기화가 되기 때문에 
																	  // 페이지 버튼을 누르면 게시물 조회가 제대로 이루어지지 않음
																	  // 따라서 키워드를 전달하여 키워드 값이 초기화 되는 것을 막음.
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		} else if ("download".equals(actionName)) { 
			// 파일 다운로드 구현
			String fileName = request.getParameter("fileName");
			
//			System.out.println(ATTACHES_DIR + File.separator + fileName);
			
			File file = new File(ATTACHES_DIR + File.separator + fileName);
			
			// 다운로드할 파일이 존재할 시 실행
			if(file.exists()) {
				OutputStream os = null;
				FileInputStream is = null;
				String encodedName = null;
				
				// 브라우저마다 인코딩이 다를 수 있기 때문에 사용
				if(request.getHeader("User-Agent").contains("Firefox")) {
					encodedName = new String(fileName.getBytes("utf-8"),"ios-8859-1");
				}
				else {
					encodedName = URLEncoder.encode(fileName,"utf-8");
					encodedName = encodedName.replaceAll("\\+"," ");
				}
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
//				response.setHeader("Content-Disposition","attachment;filename=\""+file.getName()+"\"");

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
