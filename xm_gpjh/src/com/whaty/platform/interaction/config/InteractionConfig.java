/**
 * 
 */
package com.whaty.platform.interaction.config;

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
public class InteractionConfig {

	protected static String FILENAME = "whatyInteractionConfig.xml";

	private boolean setuped;

	private String interactionConfigAbsPath;
	
	private String interactionWebIncomingAbsPath;
	
	private String interactionWebIncomingUriPath;
	
	private String interactionWebFtpPort;

	private String interactionSystemName;

	private String interactionCopyRight;

	private String interactionLink;

	private String interactionCollege;

	public InteractionConfig(PlatformConfig platformConfig) throws PlatformException {
		this.setInteractionConfigAbsPath(platformConfig.getPlatformConfigAbsPath());
	}

	public InteractionConfig(String path) throws PlatformException {

		this.setInteractionConfigAbsPath(path);
	}

	/**
	 * @return Returns the interactionConfigAbsPath.
	 */
	public String getInteractionConfigAbsPath() {
		return interactionConfigAbsPath;
	}

	/**
	 * @param interactionConfigAbsPath
	 *            The interactionConfigAbsPath to set.
	 */
	public void setInteractionConfigAbsPath(String interactionConfigAbsPath) {
		this.interactionConfigAbsPath = interactionConfigAbsPath;
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
		File file = new File(this.getInteractionConfigAbsPath() + InteractionConfig.FILENAME);
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
			this.setInteractionConfigAbsPath(document.selectSingleNode(
					"//" + "configAbsPath").getText());
			this.setInteractionCollege(document.selectSingleNode(
					"//" + "interactionCollege").getText());
			this.setInteractionCopyRight(document.selectSingleNode(
					"//" + "interactionCopyRight").getText());
			this.setInteractionLink(document.selectSingleNode(
					"//" + "interactionLink").getText());
			this.setInteractionSystemName(document.selectSingleNode(
					"//" + "interactionSystemName").getText());
			this.setInteractionWebFtpPort(document.selectSingleNode(
					"//" + "interactionWebFtpPort").getText());
			this.setInteractionWebIncomingAbsPath(document.selectSingleNode(
					"//" + "interactionWebIncomingAbsPath").getText());
			this.setInteractionWebIncomingUriPath(document.selectSingleNode(
					"//" + "interactionWebIncomingUriPath").getText());
			
		} catch (DocumentException e) {
			throw new PlatformException("Error in InteractionConfig.getConfig()!");
		}
	}

	public void setConfig() throws PlatformException {

		String filename = this.getInteractionConfigAbsPath() + InteractionConfig.FILENAME;
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

			((Element) document.selectSingleNode("//interactionCollege"))
					.setText(this.getInteractionCollege());
			((Element) document.selectSingleNode("//interactionConfigAbsPath"))
					.setText(this.getInteractionConfigAbsPath());
			((Element) document.selectSingleNode("//interactionCopyRight"))
					.setText(this.getInteractionCopyRight());
			((Element) document.selectSingleNode("//link")).setText(this
					.getInteractionLink());
			((Element) document.selectSingleNode("//systemName")).setText(this
					.getInteractionSystemName());
			((Element) document.selectSingleNode("//interactionWebFtpPort")).setText(this
					.getInteractionWebFtpPort());
			((Element) document.selectSingleNode("//interactionWebIncomingAbsPath")).setText(this
					.getInteractionWebIncomingAbsPath());
			((Element) document.selectSingleNode("//interactionWebIncomingUriPath")).setText(this
					.getInteractionWebIncomingUriPath());

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

	public String getInteractionWebIncomingUriPath() {
		return interactionWebIncomingUriPath;
	}

	public void setInteractionWebIncomingUriPath(String interactionWebIncomingUriPath) {
		this.interactionWebIncomingUriPath = interactionWebIncomingUriPath;
	}

	public String getInteractionWebIncomingAbsPath() {
		return interactionWebIncomingAbsPath;
	}

	public void setInteractionWebIncomingAbsPath(String interactionWebIncomingAbsPath) {
		this.interactionWebIncomingAbsPath = interactionWebIncomingAbsPath;
	}

	public String getInteractionCollege() {
		return interactionCollege;
	}

	public void setInteractionCollege(String interactionCollege) {
		this.interactionCollege = interactionCollege;
	}

	public String getInteractionCopyRight() {
		return interactionCopyRight;
	}

	public void setInteractionCopyRight(String interactionCopyRight) {
		this.interactionCopyRight = interactionCopyRight;
	}

	public String getInteractionLink() {
		return interactionLink;
	}

	public void setInteractionLink(String interactionLink) {
		this.interactionLink = interactionLink;
	}

	public String getInteractionSystemName() {
		return interactionSystemName;
	}

	public void setInteractionSystemName(String interactionSystemName) {
		this.interactionSystemName = interactionSystemName;
	}

	public String getInteractionWebFtpPort() {
		return interactionWebFtpPort;
	}

	public void setInteractionWebFtpPort(String interactionWebFtpPort) {
		this.interactionWebFtpPort = interactionWebFtpPort;
	}
}
