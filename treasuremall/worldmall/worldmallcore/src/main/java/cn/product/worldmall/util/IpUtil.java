package cn.product.worldmall.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IpUtil {

	private final static Logger log = LoggerFactory.getLogger(IpUtil.class);
	// ip范围
	public static int[][] range = { { 607649792, 608174079 }, // 36.56.0.0-36.63.255.255
			{ 1038614528, 1039007743 }, // 61.232.0.0-61.237.255.255
			{ 1783627776, 1784676351 }, // 106.80.0.0-106.95.255.255
			{ 2035023872, 2035154943 }, // 121.76.0.0-121.77.255.255
			{ 2078801920, 2079064063 }, // 123.232.0.0-123.235.255.255
			{ -1950089216, -1948778497 }, // 139.196.0.0-139.215.255.255
			{ -1425539072, -1425014785 }, // 171.8.0.0-171.15.255.255
			{ -1236271104, -1235419137 }, // 182.80.0.0-182.92.255.255
			{ -770113536, -768606209 }, // 210.25.0.0-210.47.255.255
			{ -569376768, -564133889 }, // 222.16.0.0-222.95.255.255
	};
	
	public static final String LINK_URL = "http://ip.taobao.com/service/getIpInfo.php?";
	
	@Value("${ipdata.filePath}")
	private String filePath;
	  
	
	/**
	 * 获取随机IP地址
	 * 
	 * @return
	 */
	public String getRandomIp() {
		Random rdint = new Random();
		int index = rdint.nextInt(10);
		String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
		return ip;
	}

	/**
	 * 判断IP地址准确性
	 * @param ip
	 * @return
	 */
	public static boolean isIp(String ip) {
        boolean b1 = ip.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
        return b1;
    }
	
	/*
	 * 将十进制转换成ip地址
	 */
	public static String num2ip(int ip) {
		int[] b = new int[4];
		String x = "";

		b[0] = (int) ((ip >> 24) & 0xff);
		b[1] = (int) ((ip >> 16) & 0xff);
		b[2] = (int) ((ip >> 8) & 0xff);
		b[3] = (int) (ip & 0xff);
		x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "."
				+ Integer.toString(b[3]);

		return x;
	}

	/**
	 * 根据IP获取城市信息
	 * @param ip
	 * @return
	 */
	public String getAreaByIp(String ip) {
		String address = "";
		try {
//			address = getAddresses("ip=" + ip, "utf-8");
			address = getCityInfo(ip);
		} catch (Exception e) {
			e.printStackTrace();
			address = getCityInfo(ip);
			return address;
		}
		return address;
	}

	/**
	 *
	 * @param content 请求的参数 格式为：name=xxx&pwd=xxx 服务器端请求编码。如GBK,UTF-8等
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	private static String getAddresses(String content, String encodingString) throws Exception {
		// 这里调用pconline的接口
		String returnStr = getResult(LINK_URL, content, encodingString);
		if (returnStr != null) {
			//JSON转MAP
			Map<String, Object> resultMap = JSONUtils.json2map(returnStr);
			if(resultMap == null) {
				return null;
			}
			if(!"0".equals(resultMap.get("code").toString())) {
				return null;
			}
			Map<String, Object> areaMap = (Map<String, Object>)resultMap.get("data");
			if(areaMap != null) {
				String region = areaMap.get("region") == null ? "" : areaMap.get("region").toString();
				String city = areaMap.get("city") == null ? "" : areaMap.get("city").toString();
				return decodeUnicode(region+city);// 省份
			}
//			// 处理返回的省市区信息
//			String[] temp = returnStr.split(",");
//			if (temp.length < 3) {
//				return "0";// 无效IP，局域网测试
//			}
//			String region = (temp[5].split(":"))[1].replaceAll("\"", "");
//			region = decodeUnicode(region);// 省份
//			return region;
		}
		return null;
	}

	/**
	 * @param urlStr   请求的地址
	 * @param content  请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding 服务器端请求编码。如GBK,UTF-8等
	 * @return
	 */
	private static String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(20000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			System.out.println(buffer.toString());
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
//		try {
//			return HttpClientUtil.get_ssl(urlStr+content);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
	}

	/**
	 * unicode 转换成 中文
	 *
	 * @author fanhui 2007-3-15
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}
	
	/**
	 * 本地数据库-查询IP所在地
	 * @param ip
	 * @return
	 */
	private String getCityInfo(String ip) {
		String dbPath = filePath + "/ip2region.db";
//		String dbPath = IpUtil.class.getResource("/ipdata/ip2region.db").getPath();
		File file = new File(dbPath);
		if(file.exists() == false) {
			log.error(("Error: Invalid ip2region.db file"));
		}
		int algorithm = DbSearcher.BTREE_ALGORITHM;//B-tree
		//DbSearcher.BINARY_ALGORITHM //Binary
		//DbSearcher.MEMORY_ALGORITYM //Memory
		
		try {
			DbConfig config = new DbConfig();
			DbSearcher searcher = new DbSearcher(config, dbPath);
			
			//define the method
            Method method = null;
            switch ( algorithm ) 
            {
            case DbSearcher.BTREE_ALGORITHM:
                method = searcher.getClass().getMethod("btreeSearch", String.class);
                break;
            case DbSearcher.BINARY_ALGORITHM:
                method = searcher.getClass().getMethod("binarySearch", String.class);
                break;
            case DbSearcher.MEMORY_ALGORITYM:
                method = searcher.getClass().getMethod("memorySearch", String.class);
                break;
            }
            
            DataBlock dataBlock = null;
            if(isIp(ip)) {
            	dataBlock  = (DataBlock) method.invoke(searcher, ip);
            	String[] dataRegion = dataBlock.getRegion().split("\\|");
            	return decodeUnicode(dataRegion[2]+dataRegion[3]);// 省份+城市
            } else {
            	return null;
            }
            
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		String ip = "171.9.98.54";
		System.out.println("ip:"+ip);
//		System.out.println("城市:"+getAreaByIp(ip));
//		System.out.println(IpUtil.class.getResource("/ipdata"));
//		System.out.println(getCityInfo(ip));
	}
}
