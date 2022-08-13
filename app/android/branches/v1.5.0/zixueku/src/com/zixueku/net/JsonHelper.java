package com.zixueku.net;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.google.gson.Gson;
import com.zixueku.R;
import com.zixueku.abst.ServerDataCallback;
import com.zixueku.entity.ActionResult;
import com.zixueku.entity.Message;
import com.zixueku.entity.Request;
import com.zixueku.util.NetThreadUtil;

public class JsonHelper {
	private static JsonHelper instance;
	private Gson gson = new Gson();
	private Message message;
	public JsonHelper() {
		// TODO Auto-generated constructor stub
	}

	public synchronized static JsonHelper instance() {
		if (instance == null)
			instance = new JsonHelper();
		return instance;
	}

	public void getMessage(Context context, String phone,int check,ServerDataCallback<?> callBack) {
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("mobile", phone);
		map.put("check", check);
		ActionResult<HashMap<String, Object>> actionResult = new ActionResult<HashMap<String, Object>>() {};
		Request req = new Request(R.string.SmssendSms, map, context, actionResult);
		NetThreadUtil.sendDataToServerNoProgressDialog(req, callBack);
	}
}
