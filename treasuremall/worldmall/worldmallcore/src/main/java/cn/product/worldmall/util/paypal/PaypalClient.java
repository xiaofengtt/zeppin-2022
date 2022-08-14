package cn.product.worldmall.util.paypal;

import java.util.Map;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;

public class PaypalClient {
	public static Map<String, String> createOrder(PaypalAccount paypalAccount, String title, String totalAmount, String currency, 
			String returnUrl) throws Exception{
        PaypalUtil paypalUtil = new PaypalUtil();
        PaypalOrderDetail orderDetail = new PaypalOrderDetail(title, totalAmount, currency, returnUrl);
        return paypalUtil.authorizePayment(paypalAccount, orderDetail);
    }
	
	public static HttpResponse<Order> excuteOrder(PaypalAccount paypalAccount, String paymentId, String payerId) throws Exception{
		PaypalUtil paypalUtil = new PaypalUtil();
		return paypalUtil.executePayment(paypalAccount, paymentId, payerId);
	}
	
	public static HttpResponse<Order> queryOrder(PaypalAccount paypalAccount, String paymentId) throws Exception{
		PaypalUtil paypalUtil = new PaypalUtil();
		return paypalUtil.getPaymentDetails(paypalAccount, paymentId);
	}
}
