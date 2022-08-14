package cn.zeppin.product.score.util.alipay;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088021828014590";//pay@niutoulicai.com
	// 商户的私钥					MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCj6KRLrowbcRAWew0Agn9pgY7nbernUIJVGC/RxIs1uo4LGJsC2p8cxpqGsopTurRvQszGKeQAd4s6nds2vPGO78AUjtxPSd+9cHiPf5No8xCCrBFivJhvIYDXeMYJ6/+KO6+9Y9CYsiNfam62GtqhEZSyTvgsaW22l8VQ5EZKKBxHVUS/j4OAKXDtDUjH5tyKNT71sjpVjgNdIRZxt0S+jQuFSS5lej+Oa2hcCJ1vnqZyvUOpss7n2cBi/j24r0BAgiPf5snY8ti7wpAc637QH6YrrHKAlS3pMn91qDVTPFSDYkRnkRB33GhRJic4v1U3AwDKNdXNHc3hXnSycIevAgMBAAECggEBAKKg1ErnL8qWftDfXIIx+Ls1OhXz4IuMPRSzP9cQ/NLde8wUqNDHG/IQOAgHo+n5qMdv7v97VucDtZf+Qh/ojoA07082g+8DrEQpEOXIPfl2md4dXc6qs1AoXM7t3QjBKLX+DJuMKs8miKRGVPzIXj5L1E6qveBK5vmxUqy1Iey2jW7AV0ANKbDXzi1myKvhtAxarR1Yc1mzhWw8rJtdPNOQGZnz12OUVMbJ2ZkDP9TaT8KPaXmaVOMjs0rurUWojYsxU/aL/4Upm6C+AkA32JQ5+ytkZsp+SnhJh79vZSxbBtUSFs5uVubLBEjR/FRU2gNPmUecIDrwS+eWvL7MSkkCgYEA8bPGllJDd+U+DjnQjUYLkSPNfZAbYd1IRqJI6W6n3pQmFKIKwNhGlqPFBE8h6ntWR9P3MJYh7QmYmPPyjN14soujsSHTlDDnI3jVDeE+lueZlK9nGXWTGVhs6bm7RaS3p+5hM3rbXNAEWMV9BlJqQZRBgV1gL1ELhjIBGy03M8MCgYEArZrMXIt110p9WAa5a1Ihp+sFv0te8jF3h//UyGUuaeIFj0to6QI2gT1Uzc8kk5+kRnWs3bkNZu5jxhsjO6TpVl+shOx95o39kkCya0qntb1PP6oTNMW2rhin0knVDWYV5EMMjfrPtDnZlYI6BvtHtZvoNx5LeNPfAoBh7VK3eaUCgYBLtTQPAdWASJ4XdqSMm9QjskM7gVgSX220MkEEXVTXsy/6ZodXwGbb6JBduSu2dsuf1BUpct1NkiPqRP9EgFq+El9DrITJdkfwJHkXz+X6/rBskkSJBPr+hWQYEcVHG0ErqM9pgKIVgFLcO3/d6xK9V+Ls0oK+T3R8pE0UZiVUYQKBgQCgNXri7NCTHesOkSYMJH9qtzlWj/fPCleE6lMznCx5ClyXIMBwR9qE6lSYmdDnayvu2intdBkqJFVvPRwGrumnDCPph1WoruCTV6FP4lVjIpE/73RJ/yvW/mnhZsF22/7X6Aht/kgvyjNCBiwGxV4n+vkR5KNBnkTvygqVOQCZAQKBgQDfn4HB8ur60y9lwXxcI6CRezV4b7c/8ULSfBAePulLHfYv4PZfEoFeVLYpA6sLgNks2nFOwAWLtTA8xf0EHC1VfJJm4t9IjWgdUICIjvwTQ0W1vSj713G09T5rpFV0Aj0n0Xreb7gqZeOGOkfYXzfkgt1YG09SwceQGcFzM5GF0w==";
	public static String key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCj6KRLrowbcRAWew0Agn9pgY7nbernUIJVGC/RxIs1uo4LGJsC2p8cxpqGsopTurRvQszGKeQAd4s6nds2vPGO78AUjtxPSd+9cHiPf5No8xCCrBFivJhvIYDXeMYJ6/+KO6+9Y9CYsiNfam62GtqhEZSyTvgsaW22l8VQ5EZKKBxHVUS/j4OAKXDtDUjH5tyKNT71sjpVjgNdIRZxt0S+jQuFSS5lej+Oa2hcCJ1vnqZyvUOpss7n2cBi/j24r0BAgiPf5snY8ti7wpAc637QH6YrrHKAlS3pMn91qDVTPFSDYkRnkRB33GhRJic4v1U3AwDKNdXNHc3hXnSycIevAgMBAAECggEBAKKg1ErnL8qWftDfXIIx+Ls1OhXz4IuMPRSzP9cQ/NLde8wUqNDHG/IQOAgHo+n5qMdv7v97VucDtZf+Qh/ojoA07082g+8DrEQpEOXIPfl2md4dXc6qs1AoXM7t3QjBKLX+DJuMKs8miKRGVPzIXj5L1E6qveBK5vmxUqy1Iey2jW7AV0ANKbDXzi1myKvhtAxarR1Yc1mzhWw8rJtdPNOQGZnz12OUVMbJ2ZkDP9TaT8KPaXmaVOMjs0rurUWojYsxU/aL/4Upm6C+AkA32JQ5+ytkZsp+SnhJh79vZSxbBtUSFs5uVubLBEjR/FRU2gNPmUecIDrwS+eWvL7MSkkCgYEA8bPGllJDd+U+DjnQjUYLkSPNfZAbYd1IRqJI6W6n3pQmFKIKwNhGlqPFBE8h6ntWR9P3MJYh7QmYmPPyjN14soujsSHTlDDnI3jVDeE+lueZlK9nGXWTGVhs6bm7RaS3p+5hM3rbXNAEWMV9BlJqQZRBgV1gL1ELhjIBGy03M8MCgYEArZrMXIt110p9WAa5a1Ihp+sFv0te8jF3h//UyGUuaeIFj0to6QI2gT1Uzc8kk5+kRnWs3bkNZu5jxhsjO6TpVl+shOx95o39kkCya0qntb1PP6oTNMW2rhin0knVDWYV5EMMjfrPtDnZlYI6BvtHtZvoNx5LeNPfAoBh7VK3eaUCgYBLtTQPAdWASJ4XdqSMm9QjskM7gVgSX220MkEEXVTXsy/6ZodXwGbb6JBduSu2dsuf1BUpct1NkiPqRP9EgFq+El9DrITJdkfwJHkXz+X6/rBskkSJBPr+hWQYEcVHG0ErqM9pgKIVgFLcO3/d6xK9V+Ls0oK+T3R8pE0UZiVUYQKBgQCgNXri7NCTHesOkSYMJH9qtzlWj/fPCleE6lMznCx5ClyXIMBwR9qE6lSYmdDnayvu2intdBkqJFVvPRwGrumnDCPph1WoruCTV6FP4lVjIpE/73RJ/yvW/mnhZsF22/7X6Aht/kgvyjNCBiwGxV4n+vkR5KNBnkTvygqVOQCZAQKBgQDfn4HB8ur60y9lwXxcI6CRezV4b7c/8ULSfBAePulLHfYv4PZfEoFeVLYpA6sLgNks2nFOwAWLtTA8xf0EHC1VfJJm4t9IjWgdUICIjvwTQ0W1vSj713G09T5rpFV0Aj0n0Xreb7gqZeOGOkfYXzfkgt1YG09SwceQGcFzM5GF0w==";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";

}
