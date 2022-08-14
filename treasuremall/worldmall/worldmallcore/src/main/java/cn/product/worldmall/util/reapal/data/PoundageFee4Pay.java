package cn.product.worldmall.util.reapal.data;

import java.math.BigDecimal;
import java.util.HashMap;

public class PoundageFee4Pay {
	public final static HashMap<String,BigDecimal> bigdecimal2p = new HashMap<String, BigDecimal>() {

		/**
		 * 各银行费率
		 */
		private static final long serialVersionUID = 3636785395222820991L;
		{
			put("工商银行", BigDecimal.valueOf(Double.parseDouble("0.003")));
			put("农业银行", BigDecimal.valueOf(Double.parseDouble("0.003")));
			put("中国银行", BigDecimal.valueOf(Double.parseDouble("0.003")));
			put("建设银行", BigDecimal.valueOf(Double.parseDouble("0.003")));
			put("交通银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
			put("招商银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
			put("中信银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
			put("民生银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
			put("兴业银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
			put("浦发银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
			put("邮储银行", BigDecimal.valueOf(Double.parseDouble("0.004")));
			put("光大银行", BigDecimal.valueOf(Double.parseDouble("0.004")));
			put("平安银行", BigDecimal.valueOf(Double.parseDouble("0.0025")));
			put("华夏银行", BigDecimal.valueOf(Double.parseDouble("0.0025")));
			put("北京银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
			put("广发银行", BigDecimal.valueOf(Double.parseDouble("0.0025")));
			put("上海银行", BigDecimal.valueOf(Double.parseDouble("0.0035")));
		}
	};
}
