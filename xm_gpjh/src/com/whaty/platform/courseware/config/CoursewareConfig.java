package com.whaty.platform.courseware.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;

public class CoursewareConfig {

	protected static String FILENAME = "whatyCoursewareConfig.xml";

	private boolean setuped;

	private String coursewareConfigAbsPath;

	private String coursewareTemplateAbsPath;

	private String coursewareTemplateRefPath;

	private String coursewareTemplateImgAbsPath;

	private String coursewareTemplateImgRefPath;

	private String coursewareAbsPath;

	private String coursewareRefPath;

	private String coursewareURI;

	private String cryptType;
	
	// 课程的相对根路径，即servlet根路径之后到课程名称之前的路径，默认为: /CourseImports
	private String coursewareBaseUrl = "/CourseImports";

	public CoursewareConfig() {

	}

	public CoursewareConfig(String configAbsPath) {
		this.setCoursewareConfigAbsPath(configAbsPath);
	}

	public CoursewareConfig(PlatformConfig platformConfig)
			throws PlatformException {
		this.setCoursewareConfigAbsPath(platformConfig
				.getPlatformConfigAbsPath());
	}

	public String getCoursewareAbsPath() {
		return coursewareAbsPath;
	}

	public void setCoursewareAbsPath(String coursewareAbsPath) {
		this.coursewareAbsPath = coursewareAbsPath;
	}

	public String getCoursewareConfigAbsPath() {
		return coursewareConfigAbsPath;
	}

	public void setCoursewareConfigAbsPath(String coursewareConfigAbsPath) {
		this.coursewareConfigAbsPath = coursewareConfigAbsPath;
	}

	
	/**
	 * @return Returns the coursewareRefPath.
	 */
	public String getCoursewareRefPath() {
		return coursewareRefPath;
	}

	/**
	 * @param coursewareRefPath The coursewareRefPath to set.
	 */
	public void setCoursewareRefPath(String coursewareRefPath) {
		this.coursewareRefPath = coursewareRefPath;
	}

	public String getCoursewareTemplateAbsPath() {
		return coursewareTemplateAbsPath;
	}

	public void setCoursewareTemplateAbsPath(String coursewareTemplateAbsPath) {
		this.coursewareTemplateAbsPath = coursewareTemplateAbsPath;
	}

	public String getCoursewareTemplateImgAbsPath() {
		return coursewareTemplateImgAbsPath;
	}

	public void setCoursewareTemplateImgAbsPath(
			String coursewareTemplateImgAbsPath) {
		this.coursewareTemplateImgAbsPath = coursewareTemplateImgAbsPath;
	}

	public String getCoursewareTemplateImgRefPath() {
		return coursewareTemplateImgRefPath;
	}

	public void setCoursewareTemplateImgRefPath(
			String coursewareTemplateImgRefPath) {
		this.coursewareTemplateImgRefPath = coursewareTemplateImgRefPath;
	}

	public String getCoursewareTemplateRefPath() {
		return coursewareTemplateRefPath;
	}

	public void setCoursewareTemplateRefPath(String coursewareTemplateRefPath) {
		this.coursewareTemplateRefPath = coursewareTemplateRefPath;
	}

	public String getCoursewareURI() {
		return coursewareURI;
	}

	public void setCoursewareURI(String coursewareURI) {
		this.coursewareURI = coursewareURI;
	}

	public String getCryptType() {
		return cryptType;
	}

	public void setCryptType(String cryptType) {
		this.cryptType = cryptType;
	}

	public boolean isSetuped() {
		return setuped;
	}

	public void setSetuped(boolean setuped) {
		this.setuped = setuped;
	}

	public void getConfig() throws PlatformException {
		File file = new File(this.getCoursewareConfigAbsPath()
				+ CoursewareConfig.FILENAME);
		if (!file.exists()) {
			throw new PlatformException("can't find the file"
					+ file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			if (document.selectSingleNode("//setup/" + "setuped").getText()
					.equalsIgnoreCase("yes")) {
				this.setSetuped(true);
			} else {
				this.setSetuped(false);
			}
			
			this.setCoursewareConfigAbsPath(document.selectSingleNode("//"+"configAbsPath").getText());
			this.setCoursewareTemplateAbsPath(document.selectSingleNode("//"+"templateAbsPath").getText());
            this.setCoursewareTemplateRefPath(document.selectSingleNode("//"+"templateRefPath").getText());
            this.setCoursewareTemplateImgAbsPath(document.selectSingleNode("//"+"templateImgAbsPath").getText());
            this.setCoursewareTemplateImgRefPath(document.selectSingleNode("//"+"templateImgRefPath").getText());
			this.setCoursewareAbsPath(document.selectSingleNode("//"+"coursewareAbsPath").getText());
			this.setCoursewareRefPath(document.selectSingleNode("//coursewareRefPath").getText());
			this.setCoursewareURI(document.selectSingleNode("//"+"coursewareURI").getText());
            
			
			this.setCryptType(document.selectSingleNode("//config/crypt/type")
					.getText());

		} catch (DocumentException e) {
			throw new PlatformException("error in setConfig");
		}
	}

	public void setConfig() throws PlatformException {

		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("root");
		rootElement.addComment("whaty courseware system configuration");
		Element setupElement = rootElement.addElement("setup");
		Element setupedElement = setupElement.addElement("setuped");
		if (this.isSetuped())
			setupedElement.setText("yes");
		else
			setupedElement.setText("no");
		
		Element webAppElement =  rootElement.addElement("webApp");
		Element configAbsPathElement=webAppElement.addElement("configAbsPath");
		configAbsPathElement.setText(this.getCoursewareConfigAbsPath());
		
		Element templateAbsPathElement=webAppElement.addElement("templateAbsPath");
		templateAbsPathElement.setText(this.getCoursewareTemplateAbsPath());
		
		Element templateRefPathElement=webAppElement.addElement("templateRefPath");
		templateRefPathElement.setText(this.getCoursewareTemplateRefPath());
		
		Element templateImgAbsPathElement=webAppElement.addElement("templateImgAbsPath");
		templateImgAbsPathElement.setText(this.getCoursewareTemplateImgAbsPath());
		
		Element templateImgRefPathElement=webAppElement.addElement("templateImgRefPath");
		templateImgRefPathElement.setText(this.getCoursewareTemplateImgRefPath());
		
		Element coursewareAbsPathElement=webAppElement.addElement("coursewareAbsPath");
		coursewareAbsPathElement.setText(this.getCoursewareAbsPath());
		
		Element coursewareRefPathElement=webAppElement.addElement("coursewareRefPath");
		coursewareRefPathElement.setText(this.getCoursewareRefPath());
		
		Element coursewareURIElement=webAppElement.addElement("coursewareURI");
		coursewareURIElement.setText(this.getCoursewareURI());
		
		Element configElement = rootElement.addElement("config");
		Element cryptElement = configElement.addElement("crypt");
		Element cryptTypeElement = cryptElement.addElement("type");
		cryptTypeElement.setText(this.getCryptType());
		try {
			File file = new File(this.getCoursewareConfigAbsPath()
					+ CoursewareConfig.FILENAME);
			XMLWriter output = new XMLWriter(new FileWriter(file));
			output.write(document);
			output.close();
		} catch (IOException e) {
			throw new PlatformException(
					"Error in creat courseware config xml file!");
		}

	}
	
	public String getCoursewareBaseUrl() {
		return coursewareBaseUrl;
	}

	public void setCoursewareBaseUrl(String coursewareBaseUrl) {
		if (coursewareBaseUrl.trim().length() <= 0)
			this.coursewareBaseUrl = "/CourseImports";
		else
			this.coursewareBaseUrl = coursewareBaseUrl;
	}

}
