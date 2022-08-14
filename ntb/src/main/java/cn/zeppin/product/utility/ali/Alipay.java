package cn.zeppin.product.utility.ali;
 
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
 
/**
 * @author 
 *
 * 
 */
@XStreamAlias("alipay")
public class Alipay {
 
    @XStreamAlias("is_success")
    private String isSuccess;
     
    @XStreamAlias("error")
    private String error;
     
    @XStreamAlias("request")
    private Request request;
     
    @XStreamAlias("response")
    private Response response;
     
    public static class Request{
         
        @XStreamImplicit(itemFieldName="param")
        private List<Param> param;
 
        public List<Param> getParam() {
            return param;
        }
 
        public void setParam(List<Param> param) {
            this.param = param;
        }
         
         
    }
     
    @XStreamAlias("param")
    public static class Param{
         
        @XStreamAsAttribute
        private String name;
 
        public String getName() {
            return name;
        }
 
        public void setName(String name) {
            this.name = name;
        }
         
    }
     
     
    public static class Response{
         
//        @XStreamAlias("trade")
//        private Rrade rrade;
// 
//        public Rrade getRrade() {
//            return rrade;
//        }
// 
//        public void setRrade(Rrade rrade) {
//            this.rrade = rrade;
//        }
    	@XStreamAlias("account_page_query_result")
    	private AccountPageQueryResult accountPageQueryResult;

		public AccountPageQueryResult getAccountPageQueryResult() {
			return accountPageQueryResult;
		}

		public void setAccountPageQueryResult(
				AccountPageQueryResult accountPageQueryResult) {
			this.accountPageQueryResult = accountPageQueryResult;
		}
    	
         
    }
    
    public static class AccountPageQueryResult {
    	
    	@XStreamAlias("account_log_list")
    	private AccountLogList accountLogList;
    	@XStreamAlias("has_next_page")
    	private String has_next_page;
    	@XStreamAlias("page_no")
    	private String page_no;
    	@XStreamAlias("page_size")
    	private String page_size;

		public AccountLogList getAccountLogList() {
			return accountLogList;
		}

		public void setAccountLogList(AccountLogList accountLogList) {
			this.accountLogList = accountLogList;
		}

		public String getHas_next_page() {
			return has_next_page;
		}

		public void setHas_next_page(String has_next_page) {
			this.has_next_page = has_next_page;
		}

		public String getPage_no() {
			return page_no;
		}

		public void setPage_no(String page_no) {
			this.page_no = page_no;
		}

		public String getPage_size() {
			return page_size;
		}

		public void setPage_size(String page_size) {
			this.page_size = page_size;
		}
    }
    
    public static class AccountLogList {
    	
//    	@XStreamAlias("AccountQueryAccountLogVO")
    	@XStreamImplicit(itemFieldName="AccountQueryAccountLogVO")
    	private List<AccountQueryAccountLogVO> accountQueryAccountLogVO;

		public List<AccountQueryAccountLogVO> getAccountQueryAccountLogVO() {
			return accountQueryAccountLogVO;
		}

		public void setAccountQueryAccountLogVO(
				List<AccountQueryAccountLogVO> accountQueryAccountLogVO) {
			this.accountQueryAccountLogVO = accountQueryAccountLogVO;
		}
    }
    
    public static class AccountQueryAccountLogVO {
    	
    	@XStreamAlias("balance")
    	private String balance;
    	@XStreamAlias("buyer_account")
    	private String buyer_account;
    	@XStreamAlias("currency")
    	private String currency;
    	@XStreamAlias("deposit_bank_no")
    	private String deposit_bank_no;
    	@XStreamAlias("goods_title")
    	private String goods_title;
    	@XStreamAlias("income")
    	private String income;
    	@XStreamAlias("iw_account_log_id")
    	private String iw_account_log_id;
    	@XStreamAlias("memo")
    	private String memo;
    	@XStreamAlias("merchant_out_order_no")
    	private String merchant_out_order_no;
    	@XStreamAlias("outcome")
    	private String outcome;
    	@XStreamAlias("partner_id")
    	private String partner_id;
    	@XStreamAlias("rate")
    	private String rate;
    	@XStreamAlias("seller_account")
    	private String seller_account;
    	@XStreamAlias("seller_fullname")
    	private String seller_fullname;
    	@XStreamAlias("service_fee")
    	private String service_fee;
    	@XStreamAlias("service_fee_ratio")
    	private String service_fee_ratio;
    	@XStreamAlias("sign_product_name")
    	private String sign_product_name;
    	@XStreamAlias("sub_trans_code_msg")
    	private String sub_trans_code_msg;
    	@XStreamAlias("total_fee")
    	private String total_fee;
    	@XStreamAlias("trade_no")
    	private String trade_no;
    	@XStreamAlias("trade_refund_amount")
    	private String trade_refund_amount;
    	@XStreamAlias("trans_code_msg")
    	private String trans_code_msg;
    	@XStreamAlias("trans_date")
    	private String trans_date;
		public String getBalance() {
			return balance;
		}
		public void setBalance(String balance) {
			this.balance = balance;
		}
		public String getBuyer_account() {
			return buyer_account;
		}
		public void setBuyer_account(String buyer_account) {
			this.buyer_account = buyer_account;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getDeposit_bank_no() {
			return deposit_bank_no;
		}
		public void setDeposit_bank_no(String deposit_bank_no) {
			this.deposit_bank_no = deposit_bank_no;
		}
		public String getGoods_title() {
			return goods_title;
		}
		public void setGoods_title(String goods_title) {
			this.goods_title = goods_title;
		}
		public String getIncome() {
			return income;
		}
		public void setIncome(String income) {
			this.income = income;
		}
		public String getIw_account_log_id() {
			return iw_account_log_id;
		}
		public void setIw_account_log_id(String iw_account_log_id) {
			this.iw_account_log_id = iw_account_log_id;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		public String getMerchant_out_order_no() {
			return merchant_out_order_no;
		}
		public void setMerchant_out_order_no(String merchant_out_order_no) {
			this.merchant_out_order_no = merchant_out_order_no;
		}
		public String getOutcome() {
			return outcome;
		}
		public void setOutcome(String outcome) {
			this.outcome = outcome;
		}
		public String getPartner_id() {
			return partner_id;
		}
		public void setPartner_id(String partner_id) {
			this.partner_id = partner_id;
		}
		public String getRate() {
			return rate;
		}
		public void setRate(String rate) {
			this.rate = rate;
		}
		public String getSeller_account() {
			return seller_account;
		}
		public void setSeller_account(String seller_account) {
			this.seller_account = seller_account;
		}
		public String getSeller_fullname() {
			return seller_fullname;
		}
		public void setSeller_fullname(String seller_fullname) {
			this.seller_fullname = seller_fullname;
		}
		public String getService_fee() {
			return service_fee;
		}
		public void setService_fee(String service_fee) {
			this.service_fee = service_fee;
		}
		public String getService_fee_ratio() {
			return service_fee_ratio;
		}
		public void setService_fee_ratio(String service_fee_ratio) {
			this.service_fee_ratio = service_fee_ratio;
		}
		public String getSign_product_name() {
			return sign_product_name;
		}
		public void setSign_product_name(String sign_product_name) {
			this.sign_product_name = sign_product_name;
		}
		public String getSub_trans_code_msg() {
			return sub_trans_code_msg;
		}
		public void setSub_trans_code_msg(String sub_trans_code_msg) {
			this.sub_trans_code_msg = sub_trans_code_msg;
		}
		public String getTotal_fee() {
			return total_fee;
		}
		public void setTotal_fee(String total_fee) {
			this.total_fee = total_fee;
		}
		public String getTrade_no() {
			return trade_no;
		}
		public void setTrade_no(String trade_no) {
			this.trade_no = trade_no;
		}
		public String getTrade_refund_amount() {
			return trade_refund_amount;
		}
		public void setTrade_refund_amount(String trade_refund_amount) {
			this.trade_refund_amount = trade_refund_amount;
		}
		public String getTrans_code_msg() {
			return trans_code_msg;
		}
		public void setTrans_code_msg(String trans_code_msg) {
			this.trans_code_msg = trans_code_msg;
		}
		public String getTrans_date() {
			return trans_date;
		}
		public void setTrans_date(String trans_date) {
			this.trans_date = trans_date;
		}
    	
    }
     
     
     
//    public static class Rrade{
//         
//        @XStreamAlias("body")
//        private String body;
//        @XStreamAlias("buyer_email")
//        private String buyer_email;
//        @XStreamAlias("buyer_id")
//        private String buyer_id;
//        @XStreamAlias("coupon_used_fee")
//        private String coupon_used_fee;
//        @XStreamAlias("discount")
//        private String discount;
//        @XStreamAlias("flag_trade_locked")
//        private String flag_trade_locked;
//        @XStreamAlias("gmt_create")
//        private String gmt_create;
//        @XStreamAlias("gmt_last_modified_time")
//        private String gmt_last_modified_time;
//        @XStreamAlias("gmt_payment")
//        private String gmt_payment;
//        @XStreamAlias("is_total_fee_adjust")
//        private String is_total_fee_adjust;
//        @XStreamAlias("operator_role")
//        private String operator_role;
//        @XStreamAlias("out_trade_no")
//        private String out_trade_no;
//        @XStreamAlias("payment_type")
//        private String payment_type;
//        @XStreamAlias("price")
//        private String price;
//        @XStreamAlias("quantity")
//        private String quantity;
//        @XStreamAlias("seller_email")
//        private String seller_email;
//        @XStreamAlias("seller_id")
//        private String seller_id;
//        @XStreamAlias("subject")
//        private String subject;
//        @XStreamAlias("to_buyer_fee")
//        private String to_buyer_fee;
//        @XStreamAlias("to_seller_fee")
//        private String to_seller_fee;
//        @XStreamAlias("total_fee")
//        private String total_fee;
//        @XStreamAlias("trade_no")
//        private String trade_no;
//        @XStreamAlias("trade_status")
//        private String trade_status;
//        @XStreamAlias("use_coupon")
//        private String use_coupon;
//        public String getBody() {
//            return body;
//        }
//        public void setBody(String body) {
//            this.body = body;
//        }
//        public String getBuyer_email() {
//            return buyer_email;
//        }
//        public void setBuyer_email(String buyer_email) {
//            this.buyer_email = buyer_email;
//        }
//        public String getBuyer_id() {
//            return buyer_id;
//        }
//        public void setBuyer_id(String buyer_id) {
//            this.buyer_id = buyer_id;
//        }
//        public String getCoupon_used_fee() {
//            return coupon_used_fee;
//        }
//        public void setCoupon_used_fee(String coupon_used_fee) {
//            this.coupon_used_fee = coupon_used_fee;
//        }
//        public String getDiscount() {
//            return discount;
//        }
//        public void setDiscount(String discount) {
//            this.discount = discount;
//        }
//        public String getFlag_trade_locked() {
//            return flag_trade_locked;
//        }
//        public void setFlag_trade_locked(String flag_trade_locked) {
//            this.flag_trade_locked = flag_trade_locked;
//        }
//        public String getGmt_create() {
//            return gmt_create;
//        }
//        public void setGmt_create(String gmt_create) {
//            this.gmt_create = gmt_create;
//        }
//        public String getGmt_last_modified_time() {
//            return gmt_last_modified_time;
//        }
//        public void setGmt_last_modified_time(String gmt_last_modified_time) {
//            this.gmt_last_modified_time = gmt_last_modified_time;
//        }
//        public String getGmt_payment() {
//            return gmt_payment;
//        }
//        public void setGmt_payment(String gmt_payment) {
//            this.gmt_payment = gmt_payment;
//        }
//        public String getIs_total_fee_adjust() {
//            return is_total_fee_adjust;
//        }
//        public void setIs_total_fee_adjust(String is_total_fee_adjust) {
//            this.is_total_fee_adjust = is_total_fee_adjust;
//        }
//        public String getOperator_role() {
//            return operator_role;
//        }
//        public void setOperator_role(String operator_role) {
//            this.operator_role = operator_role;
//        }
//        public String getOut_trade_no() {
//            return out_trade_no;
//        }
//        public void setOut_trade_no(String out_trade_no) {
//            this.out_trade_no = out_trade_no;
//        }
//        public String getPayment_type() {
//            return payment_type;
//        }
//        public void setPayment_type(String payment_type) {
//            this.payment_type = payment_type;
//        }
//        public String getPrice() {
//            return price;
//        }
//        public void setPrice(String price) {
//            this.price = price;
//        }
//        public String getQuantity() {
//            return quantity;
//        }
//        public void setQuantity(String quantity) {
//            this.quantity = quantity;
//        }
//        public String getSeller_email() {
//            return seller_email;
//        }
//        public void setSeller_email(String seller_email) {
//            this.seller_email = seller_email;
//        }
//        public String getSeller_id() {
//            return seller_id;
//        }
//        public void setSeller_id(String seller_id) {
//            this.seller_id = seller_id;
//        }
//        public String getSubject() {
//            return subject;
//        }
//        public void setSubject(String subject) {
//            this.subject = subject;
//        }
//        public String getTo_buyer_fee() {
//            return to_buyer_fee;
//        }
//        public void setTo_buyer_fee(String to_buyer_fee) {
//            this.to_buyer_fee = to_buyer_fee;
//        }
//        public String getTo_seller_fee() {
//            return to_seller_fee;
//        }
//        public void setTo_seller_fee(String to_seller_fee) {
//            this.to_seller_fee = to_seller_fee;
//        }
//        public String getTotal_fee() {
//            return total_fee;
//        }
//        public void setTotal_fee(String total_fee) {
//            this.total_fee = total_fee;
//        }
//        public String getTrade_no() {
//            return trade_no;
//        }
//        public void setTrade_no(String trade_no) {
//            this.trade_no = trade_no;
//        }
//        public String getTrade_status() {
//            return trade_status;
//        }
//        public void setTrade_status(String trade_status) {
//            this.trade_status = trade_status;
//        }
//        public String getUse_coupon() {
//            return use_coupon;
//        }
//        public void setUse_coupon(String use_coupon) {
//            this.use_coupon = use_coupon;
//        }
//          
//    }
     
    @XStreamAlias("sign")
    private String sign;
     
 
    @XStreamAlias("sign_type")
    private String sign_type;
 
    public String getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign_type() {
        return sign_type;
    }
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    public Request getRequest() {
        return request;
    }
    public void setRequest(Request request) {
        this.request = request;
    }
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
     
     
}