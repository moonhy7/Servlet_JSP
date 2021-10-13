package lesson02.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

///WEB-INF/temp 는 임시 폴더, 외부에서 접근금지하기 위해 일부러 /WEB-INF 아래 두었다
///upload 는 모든 파일정보를 수신하면 temp->upload로 이동

@SuppressWarnings("serial")
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 응답데이터를 UTF-8로 해석하자
		req.setCharacterEncoding("UTF-8");
		// 전송데이터를 UTF-8로 해석하도록 하자
		resp.setContentType("text/html; charset=UTF-8");
		
		// 클라이언트와 연결된 통신객체
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>Multipart Test</title></head><body>");
		
		try {
			String contextRootPath = this.getServletContext().getRealPath("/");
			System.out.println("contextRootPath : " + contextRootPath);
			
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			diskFactory.setRepository(new File(contextRootPath + "/WEB-INF/temp"));
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(req);
			
			FileItem item = null;
			for(int i=0;i<items.size();i++) {
				item = items.get(i);
				if(item.isFormField())	// 일반 전달변수
					processFormField(out, item);
				else					// 업로드 파일
					processUploadFile(out, item, contextRootPath);
			}
			
		}catch(Exception e) {
			out.println("<pre>");
			e.printStackTrace(out);
			out.println("</pre>");
		}
		
		out.println("</body></html>");
	}
	
	private void processUploadFile(PrintWriter out, FileItem item, String contextRootPath) throws Exception{
		String name = item.getFieldName();
		String fileName = item.getName();
		String contentType = item.getContentType();
		long fileSize = item.getSize();
		
		String uploadedFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
		
		//실제 경로에 폴더가 없으면 추가
		File folder = new File(contextRootPath + "/uplaod");
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		File uploadedFile = new File(contextRootPath + "/upload/" + uploadedFileName);
	
		item.write(uploadedFile);
		
		// 클라이언트한테 정보보여주기
		out.println("<p>");
		out.println("파라미터 이름: " + name + "<br>");
		out.println("파일 이름: " + fileName + "<br>");
		out.println("콘텐츠 타입: " + contentType + "<br>");
		out.println("파일 크기: " + fileSize + "<br>");
		out.println("<img src='./upload/" + uploadedFileName + "' width='500'><br>");
		out.println("</p>"); 
	}
	
	private void processFormField(PrintWriter out, FileItem item) throws Exception{
		String name = item.getFieldName();
		String value = item.getString("UTF-8");
		
		out.println(name + ":" + value + "<br>");
	}
}