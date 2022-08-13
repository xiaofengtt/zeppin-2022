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
 * 本平台的总体配置参数
 * 
 * @author chenjian
 * 
 */
public class PlatformConfig {

	protected static String FILENAME = "whatyPlatformConfig.xml";

	private String platformConfigAbsPath;

	private String platformWebAppAbsPath;

	private String platformWebAppUriPath;

	private String platformConfigRefPath;

	private String platformWebINFRefPath;

	private String platformIP;

	private String platformPort;

	private String platformDomainName;

	private String managepwd;

	private String mailSmtp;

	private String mailUser;

	private String mailPassword;

	private boolean setuped;

	private List subSystemSetup;

	public String getManagepwd() {
		return managepwd;
	}

	public void setManagepwd(String managepwd) {
		this.managepwd = managepwd;
	}

	public String getPlatformConfigAbsPath() {
		return platformConfigAbsPath;
	}

	public void setPlatformConfigAbsPath(String platformConfigAbsPath) {
		this.platformConfigAbsPath = platformConfigAbsPath;
	}

	public String getPlatformConfigRefPath() {
		return platformConfigRefPath;
	}

	public void setPlatformConfigRefPath(String platformConfigRefPath) {
		this.platformConfigRefPath = platformConfigRefPath;
	}

	public String getPlatformPort() {
		return platformPort;
	}

	public void setPlatformPort(String platformPort) {
		this.platformPort = platformPort;
	}

	public String getPlatformDomainName() {
		return platformDomainName;
	}

	public void setPlatformDomainName(String platformDomainName) {
		this.platformDomainName = platformDomainName;
	}

	public String getPlatformIP() {
		return platformIP;
	}

	public void setPlatformIP(String platformIP) {
		this.platformIP = platformIP;
	}

	public String getPlatformWebAppAbsPath() {
		return platformWebAppAbsPath;
	}

	public void setPlatformWebAppAbsPath(String platformWebAppAbsPath) {
		this.platformWebAppAbsPath = platformWebAppAbsPath;
	}

	public String getPlatformWebAppUriPath() {
		return platformWebAppUriPath;
	}

	public void setPlatformWebAppUriPath(String platformWebAppUriPath) {
		this.platformWebAppUriPath = platformWebAppUriPath;
	}

	public String getPlatformWebINFRefPath() {
		return platformWebINFRefPath;
	}

	public void setPlatformWebINFRefPath(String platformWebINFRefPath) {
		this.platformWebINFRefPath = platformWebINFRefPath;
	}

	public List getSubSystemSetup() {
		return subSystemSetup;
	}

	public void setSubSystemSetup(List subSystemSetup) {
		this.subSystemSetup = subSystemSetup;
	}

	public static String getFILENAME() {
		return FILENAME;
	}

	public static void setFILENAME(String filename) {
		FILENAME = filename;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailSmtp() {
		return mailSmtp;
	}

	public void setMailSmtp(String mailSmtp) {
		this.mailSmtp = mailSmtp;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public PlatformConfig() {
		this.setSubSystemSetup(new ArrayList());
	}

	public PlatformConfig(String configAbsPath) {

		this.setSubSystemSetup(new ArrayList());
		this.setPlatformConfigAbsPath(configAbsPath);
	}

	public void getConfig() throws PlatformException {
		File file = new File(this.getPlatformConfigAbsPath()
				+ PlatformConfig.FILENAME);
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
			this
					.setPlatformIP(document.selectSingleNode("//" + "IP")
							.getText());
			this.setPlatformPort(document.selectSingleNode("//" + "port")
					.getText());
			this.setPlatformDomainName(document.selectSingleNode(
					"//" + "domainName").getText());
			this.setPlatformWebAppAbsPath(document.selectSingleNode(
					"//" + "webAppAbsPath").getText());
			this.setPlatformWebAppUriPath(document.selectSingleNode(
					"//" + "webAppUriPath").getText());
			this.setPlatformWebINFRefPath(document.selectSingleNode(
					"//" + "webInfRefPath").getText());
			this.setPlatformConfigRefPath(document.selectSingleNode(
					"//" + "configRefPath").getText());
			this.setPlatformConfigAbsPath(document.selectSingleNode(
					"//" + "configAbsPath").getText());
			this.setManagepwd(document.selectSingleNode("//managepwd")
					.getText());

			this
					.setMailSmtp(document.selectSingleNode("//" + "smtp")
							.getText());
			this
					.setMailUser(document.selectSingleNode("//" + "user")
							.getText());
			this.setMailPassword(document.selectSingleNode("//" + "password")
					.getText());

			List subSystemList = new ArrayList();
			List list = document.selectNodes("//subSystems/subSystem");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Node node = (Node) iter.next();
				SubSystemSetup subSystem = new SubSystemSetup();
				subSystem.setName(node.selectSingleNode("name").getText());
				if (node.selectSingleNode("setuped").getText()
						.equalsIgnoreCase("yes")) {
					subSystem.setSetuped(true);
				} else {
					subSystem.setSetuped(false);
				}

				subSystemList.add(subSystem);
			}

			this.setSubSystemSetup(subSystemList);

		} catch (DocumentException e) {
			throw new PlatformException("error in setConfig");
		}
	}

	public void setConfig() throws PlatformException {
		String filename = this.getPlatformConfigAbsPath()
				+ PlatformConfig.FILENAME;
		
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

			((Element) document.selectSingleNode("//IP")).setText(this
					.getPlatformIP());
			((Element) document.selectSingleNode("//port")).setText(this
					.getPlatformPort());
			((Element) document.selectSingleNode("//domainName")).setText(this
					.getPlatformDomainName());
			((Element) document.selectSingleNode("//webAppAbsPath"))
					.setText(this.getPlatformWebAppAbsPath());
			((Element) document.selectSingleNode("//webAppUriPath"))
					.setText(this.getPlatformWebAppUriPath());
			((Element) document.selectSingleNode("//configAbsPath"))
					.setText(this.getPlatformConfigAbsPath());
			((Element) document.selectSingleNode("//configRefPath"))
					.setText(this.getPlatformConfigRefPath());
			((Element) document.selectSingleNode("//webInf/webInfRefPath"))
					.setText(this.getPlatformWebINFRefPath());
			((Element) document.selectSingleNode("//managepwd")).setText(this
					.getManagepwd());
			((Element) document.selectSingleNode("//smtp")).setText(this
					.getMailSmtp());
			((Element) document.selectSingleNode("//user")).setText(this
					.getMailUser());
			((Element) document.selectSingleNode("//password")).setText(this
					.getMailPassword());

			HashMap map = new HashMap();
			for (int i = 0; i < this.getSubSystemSetup().size(); i++) {
				SubSystemSetup subSystem = (SubSystemSetup) this
						.getSubSystemSetup().get(i);
				String name = subSystem.getName();
				String setuped = subSystem.getSetuped() ? "yes" : "no";
				map.put(name, setuped);
			}

			List list = document.selectNodes("//subSystems/subSystem");
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Element subElement = (Element) iter.next();
				Element subNameElement = subElement.element("name");
				Element subSetupedElement = subElement.element("setuped");
				String setuped = (String) map.get(subNameElement.getText());
				subSetupedElement.setText(setuped);
			}

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
		Element rootElement = document.addElement("root");
		rootElement.addComment("whaty platform configuration");
		Element setupElement = rootElement.addElement("setup");
		Element setupedElement = setupElement.addElement("setuped");
		if (this.getSetuped())
			setupedElement.setText("yes");
		else
			setupedElement.setText("no");
		Element webAppElement = rootElement.addElement("webApp");
		Element webAppAbsPathElement = webAppElement
				.addElement("webAppAbsPath");
		webAppAbsPathElement.setText(this.getPlatformWebAppAbsPath());
		Element webAppUriPathElement = webAppElement
				.addElement("webAppUriPath");
		webAppUriPathElement.setText(this.getPlatformWebAppUriPath());
		Element configAbsPathElement = webAppElement
				.addElement("configAbsPath");
		configAbsPathElement.setText(this.getPlatformConfigAbsPath());
		Element configRefPathElement = webAppElement
				.addElement("configRefPath");
		configRefPathElement.setText(this.getPlatformConfigRefPath());
		Element webInfUriPathElement = webAppElement.addElement("webInf");
		Element webInfRefPathElement = webInfUriPathElement
				.addElement("webInfRefPath");
		webInfRefPathElement.setText(this.getPlatformWebINFRefPath());
		Element IPElement = webAppElement.addElement("IP");
		IPElement.setText(this.getPlatformIP());
		Element portElement = webAppElement.addElement("port");
		portElement.setText(this.getPlatformPort());
		Element domainNameElement = webAppElement.addElement("domainName");
		domainNameElement.setText(this.getPlatformDomainName());

		/** **************************************************************************************** */
		Element mailElement = rootElement.addElement("mail");
		Element smtpElement = mailElement.addElement("smtp");
		smtpElement.setText(this.getMailSmtp());
		Element userElement = mailElement.addElement("user");
		userElement.setText(this.getMailUser());
		Element passwordElement = mailElement.addElement("password");
		passwordElement.setText(this.getMailPassword());
		/** **************************************************************************************** */
		Element subSystemsElement = rootElement.addElement("subSystems");
		for (int i = 0; i < this.getSubSystemSetup().size(); i++) {
			Element subSystemElement = subSystemsElement
					.addElement("subSystem");
			Element nameElement = subSystemElement.addElement("name");
			nameElement.setText(((SubSystemSetup) this.getSubSystemSetup().get(
					i)).getName());
			Element setupsElement = subSystemElement.addElement("setuped");
			if (((SubSystemSetup) this.getSubSystemSetup().get(i)).getSetuped())
				setupsElement.setText("yes");
			else
				setupsElement.setText("no");
		}
		/** **************************************************************************************** */

		try {
			String path = this.getPlatformConfigAbsPath()
					+ PlatformConfig.FILENAME;
			File file = new File(path);
			XMLWriter output = new XMLWriter(new FileWriter(file));
			output.write(document);
			output.close();
		} catch (IOException e) {
			throw new PlatformException(
					"error in creat platform config xml file!");
		}
	}

	public boolean getSetuped() {
		return setuped;
	}

	public void setSetuped(boolean setuped) {
		this.setuped = setuped;
	}

}
