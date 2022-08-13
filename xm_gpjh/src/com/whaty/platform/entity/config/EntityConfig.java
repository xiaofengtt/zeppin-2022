/**
 * 
 */
package com.whaty.platform.entity.config;

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
 * @author chenjian
 * 
 */
public class EntityConfig {
	protected static String FILENAME = "whatyEntityConfig.xml";

	private boolean setuped;

	private String entityConfigAbsPath;

	private String entityWebIncomingAbsPath;

	private String entityWebIncomingUriPath;

	private String entityWebFtpPort;

	private String entitySystemName;

	private String entityCopyRight;

	private String entityLink;

	private String entityCollege;

	public EntityConfig(PlatformConfig platformConfig) throws PlatformException {
		this.setEntityConfigAbsPath(platformConfig.getPlatformConfigAbsPath());
	}

	public String getEntityConfigAbsPath() {
		return entityConfigAbsPath;
	}

	public void setEntityConfigAbsPath(String ssoConfigAbsPath) {
		entityConfigAbsPath = ssoConfigAbsPath;
	}

	public boolean getSetuped() {
		return setuped;
	}

	public void setSetuped(boolean setuped) {
		this.setuped = setuped;
	}

	public String getEntityWebFtpPort() {
		return entityWebFtpPort;
	}

	public void setEntityWebFtpPort(String entityWebFtpPort) {
		this.entityWebFtpPort = entityWebFtpPort;
	}

	public String getEntityWebIncomingAbsPath() {
		return entityWebIncomingAbsPath;
	}

	public void setEntityWebIncomingAbsPath(String entityWebIncomingAbsPath) {
		this.entityWebIncomingAbsPath = entityWebIncomingAbsPath;
	}

	public String getEntityWebIncomingUriPath() {
		return entityWebIncomingUriPath;
	}

	public void setEntityWebIncomingUriPath(String entityWebIncomingUriPath) {
		this.entityWebIncomingUriPath = entityWebIncomingUriPath;
	}

	/**
	 * @return Returns the entityCollege.
	 */
	public String getEntityCollege() {
		return entityCollege;
	}

	/**
	 * @param entityCollege
	 *            The entityCollege to set.
	 */
	public void setEntityCollege(String entityCollege) {
		this.entityCollege = entityCollege;
	}

	/**
	 * @return Returns the entityCopyRight.
	 */
	public String getEntityCopyRight() {
		return entityCopyRight;
	}

	/**
	 * @param entityCopyRight
	 *            The entityCopyRight to set.
	 */
	public void setEntityCopyRight(String entityCopyRight) {
		this.entityCopyRight = entityCopyRight;
	}

	/**
	 * @return Returns the entityLink.
	 */
	public String getEntityLink() {
		return entityLink;
	}

	/**
	 * @param entityLink
	 *            The entityLink to set.
	 */
	public void setEntityLink(String entityLink) {
		this.entityLink = entityLink;
	}

	/**
	 * @return Returns the entitySystemName.
	 */
	public String getEntitySystemName() {
		return entitySystemName;
	}

	/**
	 * @param entitySystemName
	 *            The entitySystemName to set.
	 */
	public void setEntitySystemName(String entitySystemName) {
		this.entitySystemName = entitySystemName;
	}

	public EntityConfig(String path) throws PlatformException {

		this.setEntityConfigAbsPath(path);
	}

	public void getConfig() throws PlatformException {
		File file = new File(this.getEntityConfigAbsPath()
				+ EntityConfig.FILENAME);
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
			this.setEntityWebFtpPort(document.selectSingleNode(
					"//webFtp/" + "webFtpPort").getText());
			this.setEntityWebIncomingAbsPath(document.selectSingleNode(
					"//webIncoming/" + "webIncomingAbsPath").getText());
			this.setEntityWebIncomingUriPath(document.selectSingleNode(
					"//webIncoming/" + "webIncomingUriPath").getText());
			this.setEntityConfigAbsPath(document.selectSingleNode(
					"//" + "configAbsPath").getText());

			this.setEntitySystemName(document.selectSingleNode("//systemName")
					.getText());

			this.setEntityCopyRight(document.selectSingleNode(
					"//notes"+"/copyright").getText());

			this.setEntityLink(document.selectSingleNode("//notes"+"/link")
					.getText());

			this.setEntityCollege(document.selectSingleNode("//notes"+"/college")
					.getText());
		} catch (DocumentException e) {
			System.out.println(e.toString());
			throw new PlatformException("error in getConfig");
		}
	}

	public void setConfig() throws PlatformException {
		String filename = this.getEntityConfigAbsPath() + EntityConfig.FILENAME;
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
			if (this.getSetuped())
				setupedElement.setText("yes");
			else
				setupedElement.setText("no");

			((Element) document.selectSingleNode("//configAbsPath"))
					.setText(this.getEntityConfigAbsPath());
			((Element) document.selectSingleNode("//webIncomingAbsPath"))
					.setText(this.getEntityWebIncomingAbsPath());
			((Element) document.selectSingleNode("//webIncomingUriPath"))
					.setText(this.getEntityWebIncomingUriPath());
			((Element) document.selectSingleNode("//webFtpPort")).setText(this
					.getEntityWebFtpPort());
			((Element) document.selectSingleNode("//systemName")).setText(this
					.getEntitySystemName());
			((Element) document.selectSingleNode("//notes/copyright")).setText(this
					.getEntityCopyRight());
			((Element) document.selectSingleNode("//notes/link")).setText(this
					.getEntityLink());
			((Element) document.selectSingleNode("//notes/college")).setText(this
					.getEntityCollege());

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

		// ===========================================================
		/***********************************************************************
		 * Document document = DocumentHelper.createDocument(); Element
		 * rootElement = document.addElement("root");
		 * rootElement.addComment("whaty entity configuration"); Element
		 * setupElement = rootElement.addElement("setup"); Element
		 * setupedElement = setupElement.addElement("setuped"); if
		 * (this.getSetuped()) setupedElement.setText("yes"); else
		 * setupedElement.setText("no");
		 * 
		 * Element webAppElement = rootElement.addElement("webApp"); Element
		 * webAppAbsPathElement = webAppElement .addElement("webAppAbsPath");
		 * webAppAbsPathElement.setText(this.getEntityWebAppAbsPath()); Element
		 * webAppUriPathElement = webAppElement .addElement("webAppUriPath");
		 * webAppUriPathElement.setText(this.getEntityWebAppUriPath()); Element
		 * webInfUriPathElement = webAppElement.addElement("webInf"); Element
		 * webInfRefPathElement = webInfUriPathElement
		 * .addElement("webInfRefPath");
		 * webInfRefPathElement.setText(this.getEntityWebINFRefPath()); Element
		 * webFtpElement = rootElement.addElement("webFtp"); Element
		 * webFtpPortElement = webFtpElement.addElement("webFtpPort");
		 * webFtpPortElement.setText(this.getEntityWebFtpPort());
		 * 
		 * Element webIncomingElement = rootElement.addElement("webIncoming");
		 * Element webIncomingAbsPathElement = webIncomingElement
		 * .addElement("webIncomingAbsPath");
		 * webIncomingAbsPathElement.setText(this.getEntityWebIncomingAbsPath());
		 * Element webIncomingUriPathElement = webIncomingElement
		 * .addElement("webIncomingUriPath");
		 * webIncomingUriPathElement.setText(this.getEntityWebIncomingUriPath());
		 * 
		 * Element configAbsPathElement = webAppElement
		 * .addElement("configAbsPath");
		 * configAbsPathElement.setText(this.getEntityConfigAbsPath()); Element
		 * configRefPathElement = webAppElement .addElement("configRefPath");
		 * configRefPathElement.setText(this.getEntityConfigRefPath()); Element
		 * IPElement = webAppElement.addElement("IP");
		 * IPElement.setText(this.getEntityIP()); Element portElement =
		 * webAppElement.addElement("port");
		 * portElement.setText(this.getEntityPort()); Element domainNameElement =
		 * webAppElement.addElement("domainName");
		 * domainNameElement.setText(this.getEntityDomainName()); Element
		 * configElement = rootElement.addElement("config"); Element
		 * cryptElement = configElement.addElement("crypt"); Element
		 * cryptTypeElement = cryptElement.addElement("type");
		 * cryptTypeElement.setText(this.getCryptType()); try { File file = new
		 * File(this.getEntityConfigAbsPath() + EntityConfig.FILENAME);
		 * XMLWriter output = new XMLWriter(new FileWriter(file));
		 * output.write(document); output.close(); } catch (IOException e) {
		 * throw new PlatformException( "error in creat entity config xml
		 * file!"); }
		 **********************************************************************/
	}
}
