/**
 * 
 */
package com.whaty.platform.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.whaty.platform.Exception.PlatformException;

/**
 * 本平台的论坛配置参数
 * 
 * @author chenjian
 * 
 */
public class ForumConfig {

	protected static String FILENAME = "whatyForumConfig.xml";

	private String forum_Url = "";

	private String class_Board = "";

	private String course_Board = "";
	
	private String paper_Board = "";

	private String platformConfigAbsPath;

	public String getForum_Url() {
		return forum_Url;
	}

	public void setForum_Url(String forum_Url) {
		this.forum_Url = forum_Url;
	}

	public String getPlatformConfigAbsPath() {
		return platformConfigAbsPath;
	}

	public void setPlatformConfigAbsPath(String platformConfigAbsPath) {
		this.platformConfigAbsPath = platformConfigAbsPath;
	}

	public ForumConfig(String configAbsPath) {
		this.setPlatformConfigAbsPath(configAbsPath);
	}

	public void getConfig() throws PlatformException {
		File file = new File(this.getPlatformConfigAbsPath()
				+ ForumConfig.FILENAME);
		if (!file.exists()) {
			throw new PlatformException("can't find the file"
					+ file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			this.setForum_Url(document.selectSingleNode("//" + "Forum_Url")
					.getText());
			this.setCourse_Board(document.selectSingleNode(
					"//" + "Course_Board").getText());
			this.setClass_Board(document.selectSingleNode("//" + "Class_Board")
					.getText());
			this.setPaper_Board(document.selectSingleNode("//" + "Paper_Board")
					.getText());
		} catch (DocumentException e) {
			throw new PlatformException("error in setConfig");
		}
	}

	public void setConfig() throws PlatformException {
		String filename = this.getPlatformConfigAbsPath()
				+ ForumConfig.FILENAME;

		File file = new File(filename);
		if (!file.exists()) {
			throw new PlatformException("Can't find the file"
					+ file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			this.setForum_Url(document.selectSingleNode("//" + "Forum_Url")
					.getText());
			this.setCourse_Board(document.selectSingleNode(
					"//" + "Course_Board").getText());
			this.setClass_Board(document.selectSingleNode("//" + "Class_Board")
					.getText());
			this.setPaper_Board(document.selectSingleNode("//" + "Paper_Board")
					.getText());

		} catch (DocumentException e) {
			throw new PlatformException("Error in setConfig");
		}

		try {
			/** 将document中的内容写入文件中 */
			// XMLWriter writer = new XMLWriter(new FileWriter(new
			// File(filename)));
			// writer.write(document);
			// writer.close();
			XMLWriter output = null;
			/** 格式化输出,类型IE浏览一样 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML字符集编码 */
			format.setEncoding("UTF-8");
			output = new XMLWriter(new FileWriter(new File(filename)), format);
			output.write(document);
			output.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void updateConfig() throws PlatformException {
		Document document = DocumentHelper.createDocument();
		this.setForum_Url(document.selectSingleNode("//" + "Forum_Url")
				.getText());
		this.setCourse_Board(document.selectSingleNode("//" + "Course_Board")
				.getText());
		this.setClass_Board(document.selectSingleNode("//" + "Class_Board")
				.getText());
		this.setPaper_Board(document.selectSingleNode("//" + "Paper_Board")
				.getText());
		/** **************************************************************************************** */

		try {
			String path = this.getPlatformConfigAbsPath()
					+ ForumConfig.FILENAME;
			File file = new File(path);
			XMLWriter output = new XMLWriter(new FileWriter(file));
			output.write(document);
			output.close();
		} catch (IOException e) {
			throw new PlatformException("error in creat forum config xml file!");
		}
	}

	public String getClass_Board() {
		return class_Board;
	}

	public void setClass_Board(String class_Board) {
		this.class_Board = class_Board;
	}

	public String getCourse_Board() {
		return course_Board;
	}

	public void setCourse_Board(String course_Board) {
		this.course_Board = course_Board;
	}

	public String getPaper_Board() {
		return paper_Board;
	}

	public void setPaper_Board(String paper_Board) {
		this.paper_Board = paper_Board;
	}

}
