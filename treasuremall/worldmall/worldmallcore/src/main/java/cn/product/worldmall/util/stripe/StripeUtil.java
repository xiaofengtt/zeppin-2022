package cn.product.worldmall.util.stripe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;
import com.stripe.param.checkout.SessionCreateParams.PaymentIntentData;

import cn.product.worldmall.entity.FrontUserRechargeOrder;
import cn.product.worldmall.util.JSONUtils;

public class StripeUtil {
	
	public static final String CHANNEL_RECHARGE_STRIPE_PATH = "/page/stripe.html";//stripe web
	public static final String UNSUPPORT_DECIMAL_CURRENCIES = "bif|clp|djf|gnf|jpy|kmf|krw|mga|pyg|rwf|ugx|vnd|vuv|xaf|xof|xpf";
	
	public static String createOrder(FrontUserRechargeOrder userRecharge, boolean flagCent, String key, PaymentMethodType paymentMethodType){
		Stripe.apiKey = key;
		Map<String, Object> transDataMap = JSONUtils.json2map(userRecharge.getTransData());
		
		SessionCreateParams.Builder builder = new SessionCreateParams.Builder();
//		builder.addPaymentMethodType(PaymentMethodType.CARD);
		builder.addPaymentMethodType(paymentMethodType);
		builder.setMode(SessionCreateParams.Mode.PAYMENT);
		builder.setClientReferenceId(userRecharge.getUuid());
//		builder.putMetadata("orderid", userRecharge.getUuid());//自定义参数，填入平台订单ID
		
		String returnUrl = transDataMap.get("returnUrl") + "";
		if(returnUrl.indexOf("?") < 0){
			builder.setSuccessUrl(returnUrl + "?result=1");
			builder.setCancelUrl(returnUrl + "?result=0");
		}else{
			builder.setSuccessUrl(returnUrl + "&result=1");
			builder.setCancelUrl(returnUrl + "&result=0");
		}
//		 .setQuantity(1L).setCurrency("usd").setPrice(userRecharge.getTotalAmount().toString()).build();
		LineItem.Builder itemBuilder = new LineItem.Builder();
		itemBuilder.setQuantity(1L);
//		itemBuilder.putExtraParam("orderid", userRecharge.getUuid());
		PriceData.Builder priceData = new PriceData.Builder();
		priceData.setCurrency(userRecharge.getCurrency().toLowerCase());
		BigDecimal realAmount = BigDecimal.ZERO;
		if(flagCent) {
			realAmount = BigDecimal.valueOf(Double.valueOf(userRecharge.getCurrencyAmount())).multiply(BigDecimal.valueOf(100));
		} else {
			realAmount = BigDecimal.valueOf(Double.valueOf(userRecharge.getCurrencyAmount()));
		}
		priceData.setUnitAmountDecimal(realAmount);
		ProductData.Builder productBuilder = new ProductData.Builder();
		productBuilder.setName("Top Up");
//		productBuilder.putMetadata("orderid", userRecharge.getUuid());
		priceData.setProductData(productBuilder.build());
//		priceData.putExtraParam("orderid", userRecharge.getUuid());
		itemBuilder.setPriceData(priceData.build());
        builder.addLineItem(itemBuilder.build());
        
        Map<String, String> metaData = new HashMap<String, String>();
        metaData.put("orderid", userRecharge.getUuid());
        metaData.put("ordernum", String.valueOf(userRecharge.getOrderNum()));
        
        PaymentIntentData.Builder intentData = new PaymentIntentData.Builder().putAllMetadata(metaData);
        builder.setPaymentIntentData(intentData.build());
        
        
        SessionCreateParams createParams = builder.build();
        try {
			Session session = Session.create(createParams);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static Charge excuteOrder(FrontUserRechargeOrder userRecharge, String currency, String key, String token){
		Stripe.apiKey = key;
		Map<String, Object> chargeParams = new HashMap<>();
		BigDecimal realAmount = BigDecimal.valueOf(Double.valueOf(userRecharge.getCurrencyAmount())).multiply(BigDecimal.valueOf(100));
        chargeParams.put("amount", realAmount.intValue() + "");
        chargeParams.put("currency", currency);
        chargeParams.put("description", userRecharge.getUuid());
        chargeParams.put("source", token);
        
        try {
			Charge charge = Charge.create(chargeParams);
			return charge;
		} catch (StripeException e) {
			e.printStackTrace();
			return null;
		}
	}
}
