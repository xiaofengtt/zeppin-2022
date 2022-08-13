package com.zixueku.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;

import android.content.Context;

public class LiveNet {
	private static LiveNet instance;

	private static Context con;

	private LiveNet(Context con) {

	}

	public static LiveNet instance(Context context) {
		if (instance == null) {
			instance = new LiveNet(context);
			con = context;
		}
		return instance;
	}

	@SuppressWarnings("deprecation")
	public String getCaCode(String phone,int check) throws HttpResponseException,
			ClientProtocolException, UnknownHostException,
			UnsupportedEncodingException, IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(URL.ServerPath);
		sb.append("smssendSms?mobile=");
		sb.append(phone);
		sb.append("&check=");
		sb.append(check);
		return INet.instance().httpGet(sb.toString());
	}
}
