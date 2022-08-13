/**
 * 
 */
package com.whaty.platform.resource.config;

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
public class ResourceConfig {

	protected static String FILENAME = "whatyResourceConfig.xml";

	private boolean setuped;

	private String resourceConfigAbsPath;

	private String resourceWebIncomingAbsPath;
	
	private String resourceWebIncomingUriPath;
	
	private String resourceWebFtpPort;

	private String resourceSystemName;

	private String resourceCopyRight;

	private String resourceLink;

	private String resourceCollege;

	public ResourceConfig(PlatformConfig platformConfig) throws PlatformException {
		this.setResourceConfigAbsPath(platformConfig.getPlatformConfigAbsPath());
	}

	public ResourceConfig(String path) throws PlatformException {

		this.setResourceConfigAbsPath(path);
	}

	/**
	 * @return Returns the resourceConfigAbsPath.
	 */
	public String getResourceConfigAbsPath() {
		return resourceConfigAbsPath;
	}

	/**
	 * @param resourceConfigAbsPath
	 *            The resourceConfigAbsPath to set.
	 */
	public void setResourceConfigAbsPath(String resourceConfigAbsPath) {
		this.resourceConfigAbsPath = resourceConfigAbsPath;
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
		File file = new File(this.getResourceConfigAbsPath() + ResourceConfig.FILENAME);
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
			this.setResourceConfigAbsPath(document.selectSingleNode(
					"//" + "configAbsPath").getText());
			this.setResourceCollege(document.selectSingleNode(
					"//" + "resourceCollege").getText());
			this.setResourceCopyRight(document.selectSingleNode(
					"//" + "resourceCopyRight").getText());
			this.setResourceLink(document.selectSingleNode(
					"//" + "resourceLink").getText());
			this.setResourceSystemName(document.selectSingleNode(
					"//" + "resourceSystemName").getText());
			this.setResourceWebFtpPort(document.selectSingleNode(
					"//" + "resourceWebFtpPort").getText());
			this.setResourceWebIncomingAbsPath(document.selectSingleNode(
					"//" + "resourceWebIncomingAbsPath").getText());
			this.setResourceWebIncomingUriPath(document.selectSingleNode(
					"//" + "resourceWebIncomingUriPath").getText());
			
		} catch (DocumentException e) {
			throw new PlatformException("Error in ResourceConfig.getConfig()!");
		}
	}

	public void setConfig() throws PlatformException {

		String filename = this.getResourceConfigAbsPath() + ResourceConfig.FILENAME;
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

			((Element) document.selectSingleNode("//resourceCollege"))
					.setText(this.getResourceCollege());
			((Element) document.selectSingleNode("//resourceConfigAbsPath"))
					.setText(this.getResourceConfigAbsPath());
			((Element) document.selectSingleNode("//resourceCopyRight"))
					.setText(this.getResourceCopyRight());
			((Element) document.selectSingleNode("//link")).setText(this
					.getResourceLink());
			((Element) document.selectSingleNode("//systemName")).setText(this
					.getResourceSystemName());
			((Element) document.selectSingleNode("//resourceWebFtpPort")).setText(this
					.getResourceWebFtpPort());
			((Element) document.selectSingleNode("//resourceWebIncomingAbsPath")).setText(this
					.getResourceWebIncomingAbsPath());
			((Element) document.selectSingleNode("//resourceWebIncomingUriPath")).setText(this
					.getResourceWebIncomingUriPath());

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

	public String getResourceWebIncomingUriPath() {
		return resourceWebIncomingUriPath;
	}

	public void setResourceWebIncomingUriPath(String resourceWebIncomingUriPath) {
		this.resourceWebIncomingUriPath = resourceWebIncomingUriPath;
	}

	public String getResourceWebIncomingAbsPath() {
		return resourceWebIncomingAbsPath;
	}

	public void setResourceWebIncomingAbsPath(String resourceWebIncomingAbsPath) {
		this.resourceWebIncomingAbsPath = resourceWebIncomingAbsPath;
	}

	public String getResourceCollege() {
		return resourceCollege;
	}

	public void setResourceCollege(String resourceCollege) {
		this.resourceCollege = resourceCollege;
	}

	public String getResourceCopyRight() {
		return resourceCopyRight;
	}

	public void setResourceCopyRight(String resourceCopyRight) {
		this.resourceCopyRight = resourceCopyRight;
	}

	public String getResourceLink() {
		return resourceLink;
	}

	public void setResourceLink(String resourceLink) {
		this.resourceLink = resourceLink;
	}

	public String getResourceSystemName() {
		return resourceSystemName;
	}

	public void setResourceSystemName(String resourceSystemName) {
		this.resourceSystemName = resourceSystemName;
	}

	public String getResourceWebFtpPort() {
		return resourceWebFtpPort;
	}

	public void setResourceWebFtpPort(String resourceWebFtpPort) {
		this.resourceWebFtpPort = resourceWebFtpPort;
	}
}
