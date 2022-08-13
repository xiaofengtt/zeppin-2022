package com.zeppin.util.sms;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class sendSmsServ

{
	String path = null;
	String uid = null;
	String pwd = null;
	String url = null;
	smsInfo sif = null;
	{

		path = "/home/www/webapps/mw_gpjh/WEB-INF/config/sms.xml"; 
		// path =
		// "E:\\apache-tomcat-6.0.32\\webapps\\mw_gpjh\\WEB-INF\\config\\sms.xml";
		// System.out.println(path);
		// path = path.substring(0, path.indexOf("/WEB-INF/"))
		// + "/WEB-INF/config/sms.xml";
		path = path.replaceAll("%20", " ");

		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
		Element root = null;

		try {
			dBuilderFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();

			Document document = dBuilder.parse(new File(path));
			root = document.getDocumentElement();

			uid = document.getElementsByTagName("uid").item(0).getFirstChild().getNodeValue();
			pwd = root.getElementsByTagName("pwd").item(0).getFirstChild().getNodeValue();
			url = document.getElementsByTagName("url").item(0).getFirstChild().getNodeValue();

			System.out.println(uid + "  " + pwd + "  " + url);

		} catch (Exception e) {
			//String eString = e.getMessage();
			System.out.print(e);

		}

	}

	public sendSmsServ(String mobile, String content) {
		sif = new smsInfo();
		sif.setContent(content);
		sif.setMobile(mobile);
		sif.setExtend("");
		sif.setPwd(pwd);
		sif.setUid(uid);
		sif.setSendDate("");
		sif.setUrl(url);

	}

	public int sendSms() throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(1);
		sendSms sssSendSms = new sendSms(sif);
		Future<Integer> future = pool.submit(sssSendSms);
		pool.shutdown();
		return future.get();

	}
}
