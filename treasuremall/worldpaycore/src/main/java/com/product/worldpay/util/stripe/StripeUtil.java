package com.product.worldpay.util.stripe;

import java.util.Map;

import com.product.worldpay.entity.UserRecharge;
import com.product.worldpay.util.JSONUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;

public class StripeUtil {
	
	public static String createOrder(UserRecharge userRecharge, String key){
		Stripe.apiKey = key;
		Map<String, Object> transDataMap = JSONUtils.json2map(userRecharge.getTransData());
		
		SessionCreateParams.Builder builder = new SessionCreateParams.Builder();
		builder.addPaymentMethodType(PaymentMethodType.CARD);
		builder.setMode(SessionCreateParams.Mode.PAYMENT);
		builder.setClientReferenceId(userRecharge.getUuid());
		
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
		
		PriceData.Builder priceData = new PriceData.Builder();
		priceData.setCurrency("usd");
		priceData.setUnitAmountDecimal(userRecharge.getTotalAmount());
		ProductData.Builder productBuilder = new ProductData.Builder();
		productBuilder.setName("charge");
		priceData.setProductData(productBuilder.build());
		
		itemBuilder.setPriceData(priceData.build());
        builder.addLineItem(itemBuilder.build());
        
        SessionCreateParams createParams = builder.build();
        try {
			Session session = Session.create(createParams);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace();
			return "";
		}
	}
	
//	public static Charge excuteOrder(UserRecharge userRecharge,String key, String token){
//		Stripe.apiKey = key;
//		Map<String, Object> chargeParams = new HashMap<>();
//        chargeParams.put("amount", userRecharge.getTotalAmount());
//        chargeParams.put("currency", "usd");
//        chargeParams.put("description", userRecharge.getUuid());
//        chargeParams.put("source", token);
//        
//        try {
//			Charge charge = Charge.create(chargeParams);
//			return charge;
//		} catch (StripeException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	public static String createForm(UserRecharge userRecharge, String publicKey, String transferUrl){
//		Map<String, Object> map = JSONUtils.json2map(userRecharge.getTransData());
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append("<form action=\"").append(transferUrl).append("/support/stripe/checkout?orderid=" + userRecharge.getUuid() + "\" method=\"POST\">");
//		sb.append("<script src=\"https://checkout.stripe.com/checkout.js\" class=\"stripe-button\"");
//		sb.append("data-key=\"").append(publicKey).append("\"");
//		sb.append("data-amount=\"").append(userRecharge.getTotalAmount()).append("\"data-name=\"Stripe pay.\"");
//		sb.append("data-description=\"").append(map.get("title").toString()).append("\"");
//		sb.append("data-image=\"https://stripe.com/img/documentation/checkout/marketplace.png\"");
//		sb.append("data-locale=\"auto\"></script></form>");
//		return sb.toString();
//	}
}
