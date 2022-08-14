package cn.product.worldmall.util.socket;

import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import cn.product.worldmall.util.JSONUtils;

public class ServerEncoder implements Encoder.Text<Map<String, Object>> {

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(Map<String, Object> object) throws EncodeException {
		try {
			return JSONUtils.obj2json(object);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
