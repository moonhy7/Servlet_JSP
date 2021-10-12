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

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Multipart Test</title></head><body>");
		
		try {
			String contextRootPath = this.getServletContext().getRealPath("/");
			System.out.println("contextRootPath : " + contextRootPath);
			
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			diskFactory.setRepository(new File(contextRootPath + "/WEB-INF/temp"));
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			
			//서블릿 3.0 파일명 
//			@SuppressWarnings("unchecked")
//			List<FileItem> items = upload.parseRequest((RequestContext) request);
//			
//			FileItem item = null;
//			for(int i = 0; i < items.size(); i++) {
//				item = items.get(i);
//				
//				if(item.isFormField()) {
//					proccessFormField(out, item);
//				} else {
//					proccessUploadFilie(out, item, contextRootPath);
//				}
//			}
			//서블릿 3.1 파일명 
			List items = upload.parseRequest((RequestContext) request);
			Iterator iterator = items.iterator();
			FileItem item = null;
			while(iterator.hasNext()) {
				item = (FileItem)iterator.next();
				if(item.isFormField()) {
					proccessFormField(out, item);
				} else {
					proccessUploadFilie(out, item, contextRootPath);
				}
			}
		} catch(Exception e) {
			out.println("<pre>");
			e.printStackTrace(out);
			out.println("</pre>");
		}
		
		out.println("</body></html>");
	}
	
	private void proccessUploadFilie(PrintWriter out, FileItem item, String contextRootPath) throws Exception {
		String name = item.getFieldName();
		String fileName = item.getName();
		String contentType = item.getContentType();
		long fileSize = item.getSize();
		
		String uploadFileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
		File uploadFile = new File(contextRootPath + "/upload/" + uploadFileName);
		item.write(uploadFile);
		
		out.println("<p>");
		out.println("파라미터 이름 : " + name + "<br>");
		out.println("파일 이름 : " + fileName + "<br>");
		out.println("콘텐츠 타입 : " + contentType + "<br>");
		out.println("파일 크기 : " + fileSize + "<br>");
		out.println("<img src='./upload/" + uploadFileName + "' width='500'><br>");
		out.println("</p>");
	}
	
	private void proccessFormField(PrintWriter out, FileItem item) throws Exception {
		String name = item.getFieldName();
		String value = item.getString("UTF-8");
		
		out.println(name + " : " + value + "<br>");
	}
}
