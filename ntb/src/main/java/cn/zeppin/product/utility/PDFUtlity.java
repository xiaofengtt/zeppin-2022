package cn.zeppin.product.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import cn.zeppin.product.ntb.core.entity.Resource;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PDFUtlity {

	public static Map<String, Object> ToPdf(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean flag = true;
		String message = "创建成功";
		try {
			String fileName = params.get("fileName") == null ? "" : params.get(
					"fileName").toString();
			// int id = getIntValue(request.getParameter("id"));
			// ExamTeacherRoom etr = this.examTeacherRoomService.getById(id);
			String beanPath = Resource.class.getResource("").getPath();
			// System.out.println(beanPath);
			String path = beanPath.substring(0, beanPath.indexOf("WEB-INF"));
			// String path = beanPath.substring(0,beanPath.indexOf("target"));
//			 String path = "E:\\workspace2015\\ntb\\src\\main\\webapp\\";
			String TemplatePDF = path + "resource/agreement4bankProductPDF.pdf";
			String TemplatePDF2 = path + "resource/agreement2-8.pdf";
			String TemplatePDF3 = path + "resource/agreement9.pdf";

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);

			PdfReader reader = new PdfReader(TemplatePDF);
			PdfStamper stamp = new PdfStamper(reader, baos);

			AcroFields form = stamp.getAcroFields();
			form.addSubstitutionFont(bfChinese);

			String scode = params.get("scode") == null ? "" : params.get(
					"scode").toString();
			String realname = params.get("realname") == null ? "" : params.get(
					"realname").toString();
			String phone = params.get("phone") == null ? "" : params.get(
					"phone").toString();
			String idcard = params.get("idcard") == null ? "" : params.get(
					"idcard").toString();
			String productName = params.get("productName") == null ? ""
					: params.get("productName").toString();
			String price = params.get("price") == null ? "" : params.get(
					"price").toString();

			form.setField("scode", scode);
			form.setField("realname", realname);
			form.setField("phone", phone);
			form.setField("idcard", idcard);
			form.setField("productName", productName);
			form.setField("price", price);

			stamp.setFormFlattening(true); // 千万不漏了这句啊, */
			stamp.close();

			// response.reset();
			// response.setContentType("application/pdf");
			// String filename = etr.getTeacher().getName() + "-执考通知单";
			// response.setHeader("Content-disposition",
			// "attachment;filename=" + new String(filename.getBytes(),
			// "iso-8859-1") + ".pdf");

			String uuid = params.get("uuid") == null ? "" : params.get("uuid")
					.toString();
			String[] dir = uuid.split("-");
			String basePath = "/agreement/";
			for (String sPath : dir) {
				basePath += sPath + "/";
				File tfFile = new File(path + "/" + basePath);
				if (!tfFile.exists()) {
					tfFile.mkdir();
				}
			}
			File file2 = new File(path + basePath + fileName + ".pdf");
			result.put("url", basePath + fileName + ".pdf");
			OutputStream ouputStream = new FileOutputStream(file2);

			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, ouputStream);
			doc.open();
			PdfReader reader2 = new PdfReader(TemplatePDF2);
			
			PdfImportedPage impPage = pdfCopy.getImportedPage(new PdfReader(
					baos.toByteArray()), 1);
			pdfCopy.addPage(impPage);
			int n = reader2.getNumberOfPages();
			for (int j = 1; j <= n; j++) {
				doc.newPage();
				PdfImportedPage page = pdfCopy.getImportedPage(reader2, j);
				pdfCopy.addPage(page);
			}
			reader2 = new PdfReader(TemplatePDF3);
			n = reader2.getNumberOfPages();
			for (int j = 1; j <= n; j++) {
				doc.newPage();
				PdfImportedPage page = pdfCopy.getImportedPage(reader2, j);
				pdfCopy.addPage(page);
			}
			doc.close();// 当文件拷贝 记得关闭doc
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			message = "创建失败";
		}
		result.put("result", flag);
		result.put("message", message);
		return result;
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scode", "138461368631653");
		params.put("realname", "刘运涛");
		params.put("phone", "186****3494");
		params.put("idcard", "37************5111");
		params.put("productName", "asdad定向理财协议1");
		params.put("price", "5000.00");
		params.put("uuid", "1e711df2-8f57-46e3-81bb-f96a084b8668");
		params.put("fileName", "定向理财协议112");
		Map<String, Object> result = PDFUtlity.ToPdf(params);
		System.out.println(result);
	}
}
