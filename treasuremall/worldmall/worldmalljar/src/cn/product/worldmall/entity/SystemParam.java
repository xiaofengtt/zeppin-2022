/**
 * 
 */
package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * @description 系统参数
 */
public class SystemParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1050291838235748878L;
	
	
	private String paramKey;
	private String paramValue;
	private String description;
	private String partitional;
	private String type;
	private Timestamp createtime;
	private String creator;
	
	public SystemParam() {
		super();
	}
	
	public class SystemParamKey{
		public final static String WITHDRAW_POUNDAGE = "withdraw_poundage";
		public final static String WITHDRAW_CHECK_LINE = "withdraw_check_line";
		public final static String WITHDRAW_DEFAULT_ACCOUNT = "withdraw_default_account";
		public final static String WITHDRAW_MESSAGE_FLAG = "withdraw_message_flag";
		public final static String WITHDRAW_NOTICE_MOBILE = "withdraw_notice_mobile";
		public final static String RECHARGE_TIMEOUT = "recharge_timeout";
		public final static String BET_MONOVALENT = "bet_monovalent";
		public final static String BET_CHECK_LINE = "bet_check_line";
		public final static String BET_USER_RELOAD_DURATION = "bet_user_reload_duration";
		public final static String GOLD_EXCHANGE_RATE = "gold_exchange_rate";//金币汇率
		public final static String WITHDRAW_CAPITAL_ACCOUNT = "withdraw_capital_account";
		public final static String IMAGE_PATH_URL = "image_path_url";
		public final static String SMS_SEND_DEVICE_TYPE = "sms_send_device_type";
		public final static String RECHARGE_EXPLANATION = "recharge_explanation";//��ֵ�û���֪
		public final static String WITHDRAW_EXPLANATION = "withdraw_explanation";//�����û���֪
	}
	
	public class SystemParamType{
    	public final static String STRING = "string";
    	public final static String NUMERIC = "numberic";
    	public final static String CURRENCY = "currency";
    	public final static String BOOLEAN = "boolean";
    	public final static String PRIMARYKEY = "primarykey";
    	public final static String MAP = "map";
    	public final static String RICHTEXT = "richtext";
    }

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPartitional() {
		return partitional;
	}

	public void setPartitional(String partitional) {
		this.partitional = partitional;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
