/**
 * 
 */
package com.whaty.platform.training.config;

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


/**
 * @author chenjian
 *
 */
public class TrainingConfig {
	protected static String FILENAME="whatyTrainingConfig.xml"; 
	
	private boolean setuped;
	
	private String TrainingConfigAbsPath;
	
	private String cryptType;
	
	
	public TrainingConfig(PlatformConfig platformConfig) throws PlatformException {
		this.setTrainingConfigAbsPath(platformConfig.getPlatformConfigAbsPath());
	}
	
	
	public String getTrainingConfigAbsPath() {
		return TrainingConfigAbsPath;
	}

	public void setTrainingConfigAbsPath(String ssoConfigAbsPath) {
		TrainingConfigAbsPath = ssoConfigAbsPath;
	}
	
	public String getCryptType() {
		return cryptType;
	}

	public void setCryptType(String cryptType) {
		this.cryptType = cryptType;
	}

	
	public boolean getSetuped() {
		return setuped;
	}

	public void setSetuped(boolean setuped) {
		this.setuped = setuped;
	}
	
	
	public TrainingConfig(String path) throws PlatformException {
		
		this.setTrainingConfigAbsPath(path);
	}

	public void getConfig() throws PlatformException
	{
		File file=new File(this.getTrainingConfigAbsPath()+TrainingConfig.FILENAME);
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
			this.setCryptType(document.selectSingleNode("/config/crypt/type").getText());
			            
		} catch (DocumentException e) {
			throw new PlatformException("error in setConfig");
		}
	}
	
	public void setConfig() throws PlatformException
	{
		
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("root");
		rootElement.addComment("whaty training system configuration");
		Element setupElement =  rootElement.addElement("setup");
		Element setupedElement =  setupElement.addElement("setuped");
		if(this.getSetuped())
			setupedElement.setText("yes");
		else
			setupedElement.setText("no");
		Element configElement=rootElement.addElement("config");
		Element cryptElement=configElement.addElement("crypt");
		Element cryptTypeElement=cryptElement.addElement("type");
		cryptTypeElement.setText(this.getCryptType());
		try{
			File file=new File(this.getTrainingConfigAbsPath()+TrainingConfig.FILENAME);
			XMLWriter output = new XMLWriter(new FileWriter( file ));
	        output.write( document);
	        output.close();
        }
		catch(IOException e)
		{
			throw new PlatformException("error in creat training config xml file!");
		}
		
		
	}
}
