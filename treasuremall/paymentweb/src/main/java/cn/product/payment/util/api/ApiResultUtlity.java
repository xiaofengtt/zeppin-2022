package cn.product.payment.util.api;

public class ApiResultUtlity {
	
	public enum ApiResultCode {
		TRADE_QUERY_SUCCESS("订单查询成功"),
		TRADE_QUERY_FAIL("订单查询失败"),
		TRADE_QUERY_TIMESTAMP_TIMEOUT("请求已超时"),
		TRADE_QUERY_COMPANY_ERROR("商户信息错误"),
		TRADE_QUERY_ORDERNUM_EMPTY("订单号不能为空"),
		TRADE_QUERY_SIGN_ERROR("签名错误"),
		TRADE_QUERY_OREDER_NULL("订单不存在"),
		
		TRADE_CLOSE_SUCCESS("关闭订单成功"),
		TRADE_CLOSE_FAIL("关闭订单失败"),
		TRADE_CLOSE_TIMESTAMP_TIMEOUT("请求已超时"),
		TRADE_CLOSE_COMPANY_ERROR("商户信息错误"),
		TRADE_CLOSE_ORDERNUM_EMPTY("订单号不能为空"),
		TRADE_CLOSE_SIGN_ERROR("签名错误"),
		TRADE_CLOSE_OREDER_NULL("订单不存在"),
		TRADE_CLOSE_OREDER_DISABLE("订单无法关闭"),
		TRADE_CLOSE_OREDER_ALREADY("订单已经关闭"),
		
		TRADE_CREATE_SUCCESS("订单创建成功"),
		TRADE_CREATE_FAIL("订单创建失败"),
		TRADE_CREATE_PARAMS_ERROR("参数校验有误"),
		TRADE_CREATE_TIMEOUT_ILLEGAL("订单超时时间格式错误"),
		TRADE_CREATE_TIMEOUT_LESS("订单超时时间过短"),
		TRADE_CREATE_COMPANY_ERROR("商户信息错误"),
		TRADE_CREATE_CHANNEL_ERROR("交易渠道错误"),
		TRADE_CREATE_CHANNEL_DISABLE("交易渠道已关闭"),
		TRADE_CREATE_AMOUNT_ILLEGAL("订单金额格式错误"),
		TRADE_CREATE_AMOUNT_MORE("订单金额过多"),
		TRADE_CREATE_AMOUNT_LESS("订单金额过少"),
		TRADE_CREATE_DATA_ERROR("订单数据格式错误"),
		TRADE_CREATE_ORDERNUM_REPEAT("唯一订单号重复"),
		TRADE_CREATE_CHANNEL_EMPTY("交易渠道暂不可用"),
		TRADE_CREATE_TRANSDATA_ILLEGAL("回传参数无法编码"),
		TRADE_CREATE_SIGN_ERROR("签名错误"),
		TRADE_CREATE_ACCOUNT_LACK("账户余额不足"),
		TRADE_CREATE_TIMESTAMP_TIMEOUT("请求已超时");
		private String name;
   	 
		private ApiResultCode(String name) {
            this.name = name;
        }
		
		@Override
		public String toString() {
			return this.name;
		}
	}
}
