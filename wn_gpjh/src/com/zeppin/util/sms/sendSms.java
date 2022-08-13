package com.zeppin.util.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

;

public class sendSms implements Callable<Integer> {
	private smsInfo sif = new smsInfo();

	public sendSms(smsInfo sif) {
		super();
		this.sif = sif;
	}

	public Integer call() throws Exception {
		int status = (int) smsBackResult.errOther.valueString;
		sif.setContent(new String(sif.getContent().getBytes("UTF-8"),
				"ISO-8859-1"));// 编码转换
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod(sif.getUrl());
		NameValuePair uidNameValuePair = new NameValuePair("username",
				sif.getUid());
		NameValuePair pwdNameValuePair = new NameValuePair("password",
				sif.getPwd());
		NameValuePair contentNameValuePair = new NameValuePair("content",
				sif.getContent());
		NameValuePair mobilenNameValuePair = new NameValuePair("mobile",
				sif.getMobile());
		NameValuePair extcodnNameValuePair = new NameValuePair("extcode",
				sif.getExtend());
		NameValuePair senddateNameValuePair = new NameValuePair("senddate",
				sif.getSendDate());
		pm.setRequestBody(new NameValuePair[] { uidNameValuePair,
				pwdNameValuePair, contentNameValuePair, mobilenNameValuePair,
				extcodnNameValuePair, senddateNameValuePair });
		try {
			status = hc.executeMethod(pm);
			if (pm.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(pm.getResponseBodyAsStream(),
								"utf-8"));
				String result = reader.readLine();
				status = Integer.parseInt(result);
			}
		} catch (Exception e) {
			String eString = e.getMessage();
			// TODO: handle exception
		}
		return status;
	}
}
