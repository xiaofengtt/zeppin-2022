package cn.zeppin.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Date;
import java.util.regex.Pattern;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

public class OpenOfficeUtility {

//	private OfficeDocumentConverter converter;

	/**
	 * office中各种格式
	 */
	private static final String[] OFFICE_POSTFIXS = { "doc", "docx", "xls",
			"xlsx", "ppt", "pptx" };
	private ArrayList<String> Office_Formats = new ArrayList<String>();

	/**
	 * pdf格式
	 */
//	private static final String PDF_POSTFIX = "pdf";

	/**
	 * 根据操作系统的名称，获取OpenOffice.org 3的安装目录 如我的OpenOffice.org 3安装在：C:/Program
	 * Files/OpenOffice.org 3
	 */

	public String getOfficeHome() {
		String osName = System.getProperty("os.name");
		if (Pattern.matches("Linux.*", osName)) {
			return "/opt/openoffice4";
		} else if (Pattern.matches("Windows.*", osName)) {
			return "C:\\OpenOffice4";
		}
		return null;
	}

	/**
	 * 转换文件
	 * 
	 * @param inputFilePath
	 * @param outputFilePath
	 * @param converter
	 */
	public void converterFile(String inputFilePath, String outputFilePath,
			OfficeDocumentConverter converter) {
		File inputFile = new File(inputFilePath);
		File outputFile = new File(outputFilePath);
		// 假如目标路径不存在,则新建该路径
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		converter.convert(inputFile, outputFile);
//		System.out.println("文件：" + inputFilePath + "\n转换为\n目标文件：" + outputFile
//				+ "\n成功！");
		
	}

	/**
	 * 使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx) 转化为pdf文件
	 * 
	 * @param inputFilePath
	 *            源文件路径，如："e:/test.docx"
	 * @param outputFilePath
	 *            如果指定则按照指定方法，如果未指定（null）则按照源文件路径自动生成目标文件路径，如："e:/test_docx.pdf"
	 * @return
	 */
	public boolean openOffice2Pdf(String inputFilePath, String outputFilePath)
			throws Exception {
		boolean flag = true;
		/*
		 * 连接OpenOffice.org 并且启动OpenOffice.org
		 */
		DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
		// 获取OpenOffice.org 3的安装目录
		String officeHome = getOfficeHome();
		config.setOfficeHome(officeHome);
		// 启动OpenOffice的服务
		OfficeManager officeManager = config.buildOfficeManager();
		officeManager.start();
		// 连接OpenOffice
		OfficeDocumentConverter converter = new OfficeDocumentConverter(
				officeManager);

		method(inputFilePath, outputFilePath, converter, flag);
//		method(inputFilePath, outputFilePath, converter, flag);
//		method(inputFilePath, outputFilePath, converter, flag);
		// 关闭服务
		officeManager.stop();
		return flag;
	}

	public void method(String inputFilePath, String outputFilePath,
			OfficeDocumentConverter converter, boolean flag) {
//		long begin_time = new Date().getTime();
		File inputFile = new File(inputFilePath);
		Collections.addAll(Office_Formats, OFFICE_POSTFIXS);
		if ((null != inputFilePath) && (inputFile.exists())) {
			// 判断目标文件路径是否为空
			if (Office_Formats.contains(getPostfix(inputFilePath))) {
				if (null == outputFilePath) {
					// 转换后的文件路径
					String outputFilePath_new = generateDefaultOutputFilePath(inputFilePath);
					converterFile(inputFilePath, outputFilePath_new, converter);
					flag = true;

				} else {
					converterFile(inputFilePath, outputFilePath, converter);
					flag = true;
				}
			}

		} else {
//			System.out.println("con't find the resource");
		}
//		long end_time = new Date().getTime();
//		System.out.println("文件转换耗时：[" + (end_time - begin_time) + "]ms");
	}

	/**
	 * 如果未设置输出文件路径则按照源文件路径和文件名生成输出文件地址。例，输入为 D:/fee.xlsx 则输出为D:/fee_xlsx.pdf
	 */
	public String generateDefaultOutputFilePath(String inputFilePath) {
		String outputFilePath = inputFilePath.replaceAll("."
				+ getPostfix(inputFilePath), "_" + getPostfix(inputFilePath)
				+ ".pdf");
		return outputFilePath;
	}

	/**
	 * 获取inputFilePath的后缀名，如："e:/test.pptx"的后缀名为："pptx"
	 */
	public String getPostfix(String inputFilePath) {
		String[] p = inputFilePath.split("\\.");
		if (p.length > 0) {// 判断文件有无扩展名
			// 比较文件扩展名
			return p[p.length - 1];
		} else {
			return null;
		}
	}

//	public static void main(String[] args) throws Exception {
//
//		OpenOfficeUtility office2pdf = new OpenOfficeUtility();
//		 office2pdf.openOffice2Pdf("E:\\YQ\\000.doc",
//		 "E:\\YQ\\000" + new Date().getTime() + "."
//		 + PDF_POSTFIX);
////		office2pdf.openOffice2Pdf("E:\\YQ\\193.xls", "E:\\YQ\\193.pdf");
//
//	}
}
