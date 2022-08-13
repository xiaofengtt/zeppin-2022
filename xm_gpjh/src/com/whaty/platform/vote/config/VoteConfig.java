/**
 * 
 */
package com.whaty.platform.vote.config;

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
public class VoteConfig {
	protected static String FILENAME="whatyVoteConfig.xml"; 
	
	private boolean setuped;
	
	private String VoteConfigAbsPath;
	
	private String voteWebIncomingAbsPath;
	
	private String voteWebIncomingUriPath;
	
	private String voteWebFtpPort;

	private String voteSystemName;

	private String voteCopyRight;

	private String voteLink;

	private String voteCollege;
	
	public VoteConfig(PlatformConfig platformConfig) throws PlatformException {
		this.setVoteConfigAbsPath(platformConfig.getPlatformConfigAbsPath());
	}
	
	
	/**
	 * @return the voteCollege
	 */
	public String getVoteCollege() {
		return voteCollege;
	}


	/**
	 * @param voteCollege the voteCollege to set
	 */
	public void setVoteCollege(String voteCollege) {
		this.voteCollege = voteCollege;
	}


	/**
	 * @return the voteCopyRight
	 */
	public String getVoteCopyRight() {
		return voteCopyRight;
	}


	/**
	 * @param voteCopyRight the voteCopyRight to set
	 */
	public void setVoteCopyRight(String voteCopyRight) {
		this.voteCopyRight = voteCopyRight;
	}


	/**
	 * @return the voteLink
	 */
	public String getVoteLink() {
		return voteLink;
	}


	/**
	 * @param voteLink the voteLink to set
	 */
	public void setVoteLink(String voteLink) {
		this.voteLink = voteLink;
	}


	/**
	 * @return the voteSystemName
	 */
	public String getVoteSystemName() {
		return voteSystemName;
	}


	/**
	 * @param voteSystemName the voteSystemName to set
	 */
	public void setVoteSystemName(String voteSystemName) {
		this.voteSystemName = voteSystemName;
	}


	/**
	 * @return the voteWebFtpPort
	 */
	public String getVoteWebFtpPort() {
		return voteWebFtpPort;
	}


	/**
	 * @param voteWebFtpPort the voteWebFtpPort to set
	 */
	public void setVoteWebFtpPort(String voteWebFtpPort) {
		this.voteWebFtpPort = voteWebFtpPort;
	}


	/**
	 * @return the voteWebIncomingAbsPath
	 */
	public String getVoteWebIncomingAbsPath() {
		return voteWebIncomingAbsPath;
	}


	/**
	 * @param voteWebIncomingAbsPath the voteWebIncomingAbsPath to set
	 */
	public void setVoteWebIncomingAbsPath(String voteWebIncomingAbsPath) {
		this.voteWebIncomingAbsPath = voteWebIncomingAbsPath;
	}


	/**
	 * @return the voteWebIncomingUriPath
	 */
	public String getVoteWebIncomingUriPath() {
		return voteWebIncomingUriPath;
	}


	/**
	 * @param voteWebIncomingUriPath the voteWebIncomingUriPath to set
	 */
	public void setVoteWebIncomingUriPath(String voteWebIncomingUriPath) {
		this.voteWebIncomingUriPath = voteWebIncomingUriPath;
	}


	
	
	public String getVoteConfigAbsPath() {
		return VoteConfigAbsPath;
	}

	public void setVoteConfigAbsPath(String ssoConfigAbsPath) {
		VoteConfigAbsPath = ssoConfigAbsPath;
	}
	public boolean getSetuped() {
		return setuped;
	}

	public void setSetuped(boolean setuped) {
		this.setuped = setuped;
	}
	
	
	public VoteConfig(String path) throws PlatformException {
		
		this.setVoteConfigAbsPath(path);
	}

	public void getConfig() throws PlatformException
	{
		File file=new File(this.getVoteConfigAbsPath()+VoteConfig.FILENAME);
		if(!file.exists())
		{
			throw new PlatformException("can't find the file"+file.getAbsolutePath());
		}
		SAXReader reader = new SAXReader();
        Document document=null;
        try {
			document=reader.read(file);
			if(document.selectSingleNode("//setup/"+"setuped").getText().equalsIgnoreCase("yes"))
			{
				this.setSetuped(true);
			}
			else
			{
				this.setSetuped(false);
			}
			this.setVoteConfigAbsPath(document.selectSingleNode(
					"//" + "configAbsPath").getText());
			this.setVoteCollege(document.selectSingleNode(
					"//" + "voteCollege").getText());
			this.setVoteCopyRight(document.selectSingleNode(
					"//" + "voteCopyRight").getText());
			this.setVoteLink(document.selectSingleNode(
					"//" + "voteLink").getText());
			this.setVoteSystemName(document.selectSingleNode(
					"//" + "voteSystemName").getText());
			this.setVoteWebFtpPort(document.selectSingleNode(
					"//" + "voteWebFtpPort").getText());
			this.setVoteWebIncomingAbsPath(document.selectSingleNode(
					"//" + "voteWebIncomingAbsPath").getText());
			this.setVoteWebIncomingUriPath(document.selectSingleNode(
					"//" + "voteWebIncomingUriPath").getText());            
		} catch (DocumentException e) {
			throw new PlatformException("error in setConfig");
		}
	}
	
	public void setConfig() throws PlatformException
	{
		
		String filename = this.getVoteConfigAbsPath() + VoteConfig.FILENAME;
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

			((Element) document.selectSingleNode("//voteCollege"))
					.setText(this.getVoteCollege());
			((Element) document.selectSingleNode("//voteConfigAbsPath"))
					.setText(this.getVoteConfigAbsPath());
			((Element) document.selectSingleNode("//voteCopyRight"))
					.setText(this.getVoteCopyRight());
			((Element) document.selectSingleNode("//voteLink")).setText(this
					.getVoteLink());
			((Element) document.selectSingleNode("//voteSystemName")).setText(this
					.getVoteSystemName());
			((Element) document.selectSingleNode("//voteWebFtpPort")).setText(this
					.getVoteWebFtpPort());
			((Element) document.selectSingleNode("//voteWebIncomingAbsPath")).setText(this
					.getVoteWebIncomingAbsPath());
			((Element) document.selectSingleNode("//voteWebIncomingUriPath")).setText(this
					.getVoteWebIncomingUriPath());

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
}
