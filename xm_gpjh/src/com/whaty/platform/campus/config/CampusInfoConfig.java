package com.whaty.platform.campus.config;


import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;

/**
 * @author wq
 * 
 */
public class CampusInfoConfig {

	protected static String FILENAME = "whatyInfoConfig.xml";

	private boolean setuped;

	private String infoConfigAbsPath;

	/*private String infoConfigRefPath;

	private String infoWebAppAbsPath;

	private String infoWebAppUriPath;

	private String infoWebINFRefPath;

	private String infoIP;

	private String infoPort;

	private String infoDomainName;*/
	
	private String infoWebIncomingAbsPath;
	
	private String infoWebIncomingUriPath;
	
	private String infoWebFtpPort;

	private String infoSystemName;

	private String infoCopyRight;

	private String infoLink;

	private String infoCollege;

	public CampusInfoConfig(PlatformConfig platformConfig) throws PlatformException {
		this.setInfoConfigAbsPath(platformConfig.getPlatformConfigAbsPath());
	}

	public CampusInfoConfig(String path) throws PlatformException {

		this.setInfoConfigAbsPath(path);
	}

	/**
	 * @return Returns the infoConfigAbsPath.
	 */
	public String getInfoConfigAbsPath() {
		return infoConfigAbsPath;
	}

	/**
	 * @param infoConfigAbsPath
	 *            The infoConfigAbsPath to set.
	 */
	public void setInfoConfigAbsPath(String infoConfigAbsPath) {
		this.infoConfigAbsPath = infoConfigAbsPath;
	}

	/**
	 * @return Returns the setuped.
	 */
	public boolean isSetuped() {
		return setuped;
	}

	/**
	 * @param setuped
	 *            The setuped to set.
	 */
	public void setSetuped(boolean setuped) {
		this.setuped = setuped;
	}

	public void getConfig() throws PlatformException {
		File file = new File(this.getInfoConfigAbsPath() + CampusInfoConfig.FILENAME);
		if (!file.exists()) {
			throw new PlatformException("Can't find the file"
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
			this.setInfoConfigAbsPath(document.selectSingleNode(
					"//" + "configAbsPath").getText());
			this.setInfoCollege(document.selectSingleNode(
					"//" + "infoCollege").getText());
			this.setInfoCopyRight(document.selectSingleNode(
					"//" + "infoCopyRight").getText());
			this.setInfoLink(document.selectSingleNode(
					"//" + "infoLink").getText());
			this.setInfoSystemName(document.selectSingleNode(
					"//" + "infoSystemName").getText());
			this.setInfoWebFtpPort(document.selectSingleNode(
					"//" + "infoWebFtpPort").getText());
			this.setInfoWebIncomingAbsPath(document.selectSingleNode(
					"//" + "infoWebIncomingAbsPath").getText());
			this.setInfoWebIncomingUriPath(document.selectSingleNode(
					"//" + "infoWebIncomingUriPath").getText());
			/*
			this.setInfoIP(document.selectSingleNode("//" + "IP").getText());
			this
					.setInfoPort(document.selectSingleNode("//" + "port")
							.getText());
			this.setInfoDomainName(document.selectSingleNode(
					"//" + "domainName").getText());
			this.setInfoWebAppAbsPath(document.selectSingleNode(
					"//" + "webAppAbsPath").getText());
			this.setInfoWebAppUriPath(document.selectSingleNode(
					"//" + "webAppUriPath").getText());
			this.setInfoWebINFRefPath(document.selectSingleNode(
					"//" + "webInfRefPath").getText());
			this.setInfoConfigRefPath(document.selectSingleNode(
					"//" + "configRefPath").getText());
					*/
		} catch (DocumentException e) {
			throw new PlatformException("Error in InfoConfig.getConfig()!");
		}
	}

	public void setConfig() throws PlatformException {

		String filename = this.getInfoConfigAbsPath() + CampusInfoConfig.FILENAME;
		File file = new File(filename);
		if (!file.exists()) {
			throw new PlatformException("Can't find the file"
					+ file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(file);
			Element setupedElement = (Element) document
					.selectSingleNode("//setup/setuped");
			if (this.isSetuped())
				setupedElement.setText("yes");
			else
				setupedElement.setText("no");

			((Element) document.selectSingleNode("//infoCollege"))
					.setText(this.getInfoCollege());
			((Element) document.selectSingleNode("//configAbsPath"))
					.setText(this.getInfoConfigAbsPath());
			((Element) document.selectSingleNode("//infoCopyRight"))
					.setText(this.getInfoCopyRight());
			((Element) document.selectSingleNode("//infoLink")).setText(this
					.getInfoLink());
			((Element) document.selectSingleNode("//infoSystemName")).setText(this
					.getInfoSystemName());
			((Element) document.selectSingleNode("//infoWebFtpPort")).setText(this
					.getInfoWebFtpPort());
			((Element) document.selectSingleNode("//infoWebIncomingAbsPath")).setText(this
					.getInfoWebIncomingAbsPath());
			((Element) document.selectSingleNode("//infoWebIncomingUriPath")).setText(this
					.getInfoWebIncomingUriPath());

		} catch (DocumentException e) {
			throw new PlatformException("Error in setConfig");
		}

		try {
			/** ��document�е�����д���ļ��� */
			// XMLWriter writer = new XMLWriter(new FileWriter(new
			// File(filename)));
			// writer.write(document);
			// writer.close();
			XMLWriter output = null;
			/** ��ʽ�����,����IE���һ�� */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** ָ��XML�ַ���� */
			format.setEncoding("UTF-8");
			output = new XMLWriter(new FileWriter(new File(filename)), format);
			output.write(document);
			output.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public String getInfoWebIncomingUriPath() {
		return infoWebIncomingUriPath;
	}

	public void setInfoWebIncomingUriPath(String infoWebIncomingUriPath) {
		this.infoWebIncomingUriPath = infoWebIncomingUriPath;
	}

	public String getInfoWebIncomingAbsPath() {
		return infoWebIncomingAbsPath;
	}

	public void setInfoWebIncomingAbsPath(String infoWebIncomingAbsPath) {
		this.infoWebIncomingAbsPath = infoWebIncomingAbsPath;
	}

	public String getInfoCollege() {
		return infoCollege;
	}

	public void setInfoCollege(String infoCollege) {
		this.infoCollege = infoCollege;
	}

	public String getInfoCopyRight() {
		return infoCopyRight;
	}

	public void setInfoCopyRight(String infoCopyRight) {
		this.infoCopyRight = infoCopyRight;
	}

	public String getInfoLink() {
		return infoLink;
	}

	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}

	public String getInfoSystemName() {
		return infoSystemName;
	}

	public void setInfoSystemName(String infoSystemName) {
		this.infoSystemName = infoSystemName;
	}

	public String getInfoWebFtpPort() {
		return infoWebFtpPort;
	}

	public void setInfoWebFtpPort(String infoWebFtpPort) {
		this.infoWebFtpPort = infoWebFtpPort;
	}
}
