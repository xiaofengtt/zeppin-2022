/**
 * PaymentServices class - encapsulates PayPal payment integration functions.
 * @author Nam Ha Minh
 * @copyright https://codeJava.net
 */
package com.product.worldpay.util.paypal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.PurchaseUnitRequest;

public class PaypalUtil {
	private static final String MODE = "sandbox";
//	private static final String MODE = "live";
	private static String executeApi = "/support/paypal/execute";

	public Map<String, String> authorizePayment(PaypalAccount paypalAccount, PaypalOrderDetail orderDetail) {
		
		PayPalEnvironment environment = MODE == "live" ? new PayPalEnvironment.Live(paypalAccount.getClientId(), paypalAccount.getSecret()) : 
			new PayPalEnvironment.Sandbox(paypalAccount.getClientId(), paypalAccount.getSecret());
		PayPalHttpClient client = new PayPalHttpClient(environment);
		
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer","return=representation");
		
		OrderRequest orderRequest = getOrderRequest(paypalAccount, orderDetail);
		request.requestBody(orderRequest);
		
		HttpResponse<Order> response = null;
		try {
			response = client.execute(request);
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, String>();
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		if (response.statusCode() == 201) {
			for (LinkDescription link : response.result().links()) {
				if(link.rel().equals("approve")) {
					resultMap.put("approvalLink", link.href());
				}
			}
			resultMap.put("payperId", response.result().id());
		}
		return resultMap;
		
//		Payer payer = new Payer();
//		payer.setPaymentMethod("paypal");
//		
//		PayerInfo payerInfo = new PayerInfo();
//		payer.setPayerInfo(payerInfo);
//		
//		RedirectUrls redirectUrls = new RedirectUrls();
//		redirectUrls.setCancelUrl(orderDetail.getReturnUrl());
//		redirectUrls.setReturnUrl(paypalAccount.getTransferUrl() + executeApi);
//		
//		List<Transaction> listTransaction = getTransactionInformation(orderDetail);
//		
//		Payment requestPayment = new Payment();
//		requestPayment.setTransactions(listTransaction);
//		requestPayment.setRedirectUrls(redirectUrls);
//		requestPayment.setPayer(payer);
//		requestPayment.setIntent("authorize");
//
//		APIContext apiContext = new APIContext(paypalAccount.getClientId(), paypalAccount.getSecret(), MODE);
//
//		Payment approvedPayment = requestPayment.create(apiContext);
//		
//		return getOrderInfo(approvedPayment);

	}
	
	private OrderRequest getOrderRequest(PaypalAccount paypalAccount, PaypalOrderDetail orderDetail) {
		
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.checkoutPaymentIntent("CAPTURE");
		
		ApplicationContext applicationContext = new ApplicationContext()
		.brandName("JUMEI")
		.landingPage("NO_PREFERENCE")
		.cancelUrl(orderDetail.getReturnUrl()).returnUrl(paypalAccount.getTransferUrl() + executeApi)
		.userAction("CONTINUE")
		.shippingPreference("NO_SHIPPING");
		orderRequest.applicationContext(applicationContext);
		
		List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
		PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest();
		
		
		AmountBreakdown details = new AmountBreakdown();
		details.itemTotal(new Money().currencyCode(orderDetail.getCurrency()).value(orderDetail.getAmount()));
		
		AmountWithBreakdown amount = new AmountWithBreakdown();
		amount.currencyCode(orderDetail.getCurrency());
		amount.value(orderDetail.getAmount());
		amount.amountBreakdown(details);
		purchaseUnitRequest.amountWithBreakdown(amount);
		
		List<Item> items = new ArrayList<>();
		Item item = new Item();
		item.name(orderDetail.getProductName());
		item.unitAmount(new Money().currencyCode(orderDetail.getCurrency()).value(orderDetail.getAmount()));
		item.quantity("1");
		items.add(item);
		purchaseUnitRequest.items(items);
		
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
		
//		Details details = new Details();
//		details.setSubtotal(orderDetail.getAmount());
//
//		Amount amount = new Amount();
//		amount.setCurrency(orderDetail.getCurrency());
//		amount.setTotal(orderDetail.getAmount());
//		amount.setDetails(details);
//
//		Transaction transaction = new Transaction();
//		transaction.setAmount(amount);
//		transaction.setDescription(orderDetail.getProductName());
//		
//		ItemList itemList = new ItemList();
//		List<Item> items = new ArrayList<>();
//		
//		Item item = new Item();
//		item.setCurrency(orderDetail.getCurrency());
//		item.setName(orderDetail.getProductName());
//		item.setPrice(orderDetail.getAmount());
//		item.setQuantity("1");
//		
//		items.add(item);
//		itemList.setItems(items);
//		transaction.setItemList(itemList);
//
//		List<Transaction> listTransaction = new ArrayList<>();
//		listTransaction.add(transaction);	
//		
//		return listTransaction;
	}
	
//	private Map<String, String> getOrderInfo(Payment approvedPayment) {
//		String payperId = approvedPayment.getId();
//		List<Links> links = approvedPayment.getLinks();
//		String approvalLink = null;
//		
//		for (Links link : links) {
//			if (link.getRel().equalsIgnoreCase("approval_url")) {
//				approvalLink = link.getHref();
//				break;
//			}
//		}		
//		
//		Map<String, String> info = new HashMap<String, String>();
//		info.put("payperId", payperId);
//		info.put("approvalLink", approvalLink);
//		
//		return info;
//	}

	public HttpResponse<Order> executePayment(PaypalAccount paypalAccount, String paymentId, String payerId) throws Exception {
		PayPalEnvironment environment = MODE == "live" ? new PayPalEnvironment.Live(paypalAccount.getClientId(), paypalAccount.getSecret()) : 
			new PayPalEnvironment.Sandbox(paypalAccount.getClientId(), paypalAccount.getSecret());
		PayPalHttpClient client = new PayPalHttpClient(environment);
		
		OrdersCaptureRequest request = new OrdersCaptureRequest(paymentId);
		request.requestBody(new OrderRequest());
		
		HttpResponse<Order> response = null;
		try {
			response = client.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
//		PaymentExecution paymentExecution = new PaymentExecution();
//		paymentExecution.setPayerId(payerId);
//
//		Payment payment = new Payment().setId(paymentId);
//		APIContext apiContext = new APIContext(paypalAccount.getClientId(), paypalAccount.getSecret(), MODE);
//
//		return payment.execute(apiContext, paymentExecution);
	}
//	
	public HttpResponse<Order> getPaymentDetails(PaypalAccount paypalAccount, String paymentId) throws Exception {
		PayPalEnvironment environment = MODE == "live" ? new PayPalEnvironment.Live(paypalAccount.getClientId(), paypalAccount.getSecret()) : 
			new PayPalEnvironment.Sandbox(paypalAccount.getClientId(), paypalAccount.getSecret());
		PayPalHttpClient client = new PayPalHttpClient(environment);
		
		OrdersGetRequest request = new OrdersGetRequest(paymentId);
		request.requestBody(new OrderRequest());
		
		HttpResponse<Order> response = null;
		try {
			response = client.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
