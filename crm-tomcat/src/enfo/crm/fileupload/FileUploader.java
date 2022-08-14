package enfo.crm.fileupload;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*
import org.apache.commons.io.FileCleaningTracker;
*/

/**
 * @author carlos
 * 
 * TODO 要更改此生成的类型注释的模板，请转至 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class FileUploader {

	private static Calendar calendar = null;

	private String tempDir = "C:\\uploadTemp";

	private static String subDir = null;
	private Dispatcher dispatcher = null;

	private static int MaxRequestSize = 1000000;
	static {
		calendar = Calendar.getInstance(new Locale("zh_CN"));
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		subDir = format.format(calendar.getTime());
	}

	public static DiskFileItemFactory newDiskFileItemFactory(
			ServletContext context, File repository) {
		/*
		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup
				.getFileCleaningTracker(context);
		DiskFileItemFactory factory = new DiskFileItemFactory(
				DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository);
		factory.setFileCleaningTracker(fileCleaningTracker);
		
		return factory;
		*/
		throw new RuntimeException("Runnimter Exception with FileUplaod...");
	}

	private File ensureDir(File dir) {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	private String getItemSuffix(String FileUrl) {
		if(FileUrl.lastIndexOf(".") != -1)
			return FileUrl.substring(FileUrl.lastIndexOf(".") + 1).toLowerCase();
		return null;
	}
	
	public void uploadFile(PageContext ctx) throws Exception {

		DiskFileItemFactory factory = FileUploader.newDiskFileItemFactory(ctx
				.getServletContext(), ensureDir(new File(tempDir, subDir)));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MaxRequestSize);
		final JspWriter out = ctx.getOut();
		
//		ProgressListener progressListener = new ProgressListener() {
//			public void update(long pBytesRead, long pContentLength, int pItems) {
//				try {
//					//("We are currently reading item " + pItems);
//					if (pContentLength == -1) {
//						out.write("So far, " + pBytesRead
//								+ " bytes have been read.");
//						
//					} else {
//						out.write("So far, " + pBytesRead + " of "
//								+ pContentLength + " bytes have been read.");
//						
//					}
//				} catch (IOException e) {
//					// TODO 自动生成 catch 块
//					e.printStackTrace();
//				}
//
//			}
//		};
//		upload.setProgressListener(progressListener);

		HttpServletRequest request = (HttpServletRequest) ctx.getRequest();
		enfo.crm.system.InputMan input_operator = (enfo.crm.system.InputMan) ctx.getSession().getAttribute("OPERATOR");
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new UnsupportedOperationException("没有上传文件");
		}
		//up to server
		List items = upload.parseRequest(request);
		dispatcher = new Dispatcher();

		
		Iterator iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField()) {
				// 如果是文件数据，入队

				dispatcher.invoke(item, input_operator.getOp_code());
				
			} else {
				// 如果是表单数据
				;
			}
		}
		
		//finish upload;
		out.write("<script>alert('导入成功.'); window.location = 'cust_import1.jsp';</script>");
	}
	
	class Dispatcher{
		ParseFactory factory;
		
		public void invoke(FileItem item, Integer operator) throws Exception{

			UploadParse parse = ParseFactory.getParse(getItemSuffix(item.getName()));
			parse.parse(item, operator);
			
		}

	}
}