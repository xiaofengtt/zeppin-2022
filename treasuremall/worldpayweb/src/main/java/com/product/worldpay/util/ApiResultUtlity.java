package com.product.worldpay.util;

public class ApiResultUtlity {
	
	public enum ApiResultCode {
		TRADE_SUCCESS("api success"),
		TRADE_ERROR("api fail"),
		
		TRADE_QUERY_SUCCESS("order query success"),
		TRADE_QUERY_FAIL("order query fail"),
		TRADE_QUERY_WHITEURL_ERROR("without white order"),
		TRADE_QUERY_TIMESTAMP_TIMEOUT("api timeout"),
		TRADE_QUERY_COMPANY_ERROR("company error"),
		TRADE_QUERY_ORDERNUM_EMPTY("ordernum can not be empty"),
		TRADE_QUERY_SIGN_ERROR("sign error"),
		TRADE_QUERY_OREDER_NULL("order not exist"),
		
		TRADE_CLOSE_SUCCESS("order close success"),
		TRADE_CLOSE_FAIL("order close fail"),
		TRADE_CLOSE_WHITEURL_ERROR("without white order"),
		TRADE_CLOSE_TIMESTAMP_TIMEOUT("api timeout"),
		TRADE_CLOSE_COMPANY_ERROR("company error"),
		TRADE_CLOSE_ORDERNUM_EMPTY("ordernum can not be empty"),
		TRADE_CLOSE_SIGN_ERROR("sign error"),
		TRADE_CLOSE_OREDER_NULL("order not exist"),
		TRADE_CLOSE_OREDER_DISABLE("order can not close"),
		TRADE_CLOSE_OREDER_ALREADY("order already closed"),
		
		TRADE_CREATE_SUCCESS("order create success"),
		TRADE_CREATE_FAIL("order create fail"),
		TRADE_CREATE_WHITEURL_ERROR("without white order"),
		TRADE_CREATE_PARAMS_ERROR("params error"),
		TRADE_CREATE_TIMEOUT_ILLEGAL("param timeout illegal"),
		TRADE_CREATE_TIMEOUT_LESS("param timeout too less"),
		TRADE_CREATE_COMPANY_ERROR("company error"),
		TRADE_CREATE_CHANNEL_ERROR("channel error"),
		TRADE_CREATE_CHANNEL_DISABLE("channel closed"),
		TRADE_CREATE_AMOUNT_ILLEGAL("param amount illegal"),
		TRADE_CREATE_AMOUNT_MORE("param amount too much"),
		TRADE_CREATE_AMOUNT_LESS("param amount too less"),
		TRADE_CREATE_DATA_ERROR("param data error"),
		TRADE_CREATE_ORDERNUM_REPEAT("ordernum exist"),
		TRADE_CREATE_CHANNEL_EMPTY("channel disabled"),
		TRADE_CREATE_TRANSDATA_ILLEGAL("param passbackParams illegal"),
		TRADE_CREATE_SIGN_ERROR("sign error"),
		TRADE_CREATE_ACCOUNT_LACK("insufficient balance"),
		TRADE_CREATE_TIMESTAMP_TIMEOUT("api timeout");
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
