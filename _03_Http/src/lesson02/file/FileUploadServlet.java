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

///WEB-INF/temp �� �ӽ� ����, �ܺο��� ���ٱ����ϱ� ���� �Ϻη� /WEB-INF �Ʒ� �ξ���
///upload �� ��� ���������� �����ϸ� temp->upload�� �̵�

@SuppressWarnings("serial")
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���䵥���͸� UTF-8�� �ؼ�����
		req.setCharacterEncoding("UTF-8");
		// ���۵����͸� UTF-8�� �ؼ��ϵ��� ����
		resp.setContentType("text/html; charset=UTF-8");
		
		// Ŭ���̾�Ʈ�� ����� ��Ű�ü
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
				if(item.isFormField())	// �Ϲ� ���޺���
					processFormField(out, item);
				else					// ���ε� ����
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
		
		//���� ��ο� ������ ������ �߰�
		File folder = new File(contextRootPath + "/uplaod");
		if(!folder.exists()) {
			folder.mkdir();
		}
		
		File uploadedFile = new File(contextRootPath + "/upload/" + uploadedFileName);
	
		item.write(uploadedFile);
		
		// Ŭ���̾�Ʈ���� ���������ֱ�
		out.println("<p>");
		out.println("�Ķ���� �̸�: " + name + "<br>");
		out.println("���� �̸�: " + fileName + "<br>");
		out.println("������ Ÿ��: " + contentType + "<br>");
		out.println("���� ũ��: " + fileSize + "<br>");
		out.println("<img src='./upload/" + uploadedFileName + "' width='500'><br>");
		out.println("</p>"); 
	}
	
	private void processFormField(PrintWriter out, FileItem item) throws Exception{
		String name = item.getFieldName();
		String value = item.getString("UTF-8");
		
		out.println(name + ":" + value + "<br>");
	}
}