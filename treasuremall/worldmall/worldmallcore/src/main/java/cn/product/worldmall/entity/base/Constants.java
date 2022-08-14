package cn.product.worldmall.entity.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量
 * @author Administrator
 *
 */
public class Constants {

	public final static String ORDER_TYPE_DEMO = "demo";
	
	public final static String GAME_TYPE_TRADITION = "tradition";//传统玩法
	public final static String GAME_TYPE_ONEVONE = "onevone";//1v1PK
	public final static String GAME_TYPE_GROUP2 = "group2";//两群PK
	public final static String GAME_TYPE_GROUP3 = "group3";//三群PK
	
	public final static String ORDER_TYPE_SYSTEM_ADD = "system_add";
	public final static String ORDER_TYPE_SYSTEM_SUB = "system_sub";
	public final static String ORDER_TYPE_SYSTEM_GIVE = "system_give";
	public final static String ORDER_TYPE_USER_RECHARGE = "user_recharge";
	public final static String ORDER_TYPE_USER_WITHDRAW = "user_withdraw";
	public final static String ORDER_TYPE_USER_PAYMENT = "user_payment";
	public final static String ORDER_TYPE_USER_EXCHANGE = "user_exchange";
	public final static String ORDER_TYPE_USER_REGISTER = "user_register";//用户注册
	public final static String ORDER_TYPE_USER_SCORELOTTERY = "user_scorelottery";//用户参加积分抽奖活动活动
	public final static String ORDER_TYPE_USER_BUYFREE = "user_buyfree";//用户参加0元购活动活动
	public final static String ORDER_TYPE_USER_CHECKIN = "user_checkin";//用户参加签到活动活动
	public final static String ORDER_TYPE_USER_DELIVERY = "user_delivery";
	public final static String ORDER_TYPE_USER_RECOMMEND = "user_recommend";
	
	
	public final static String ORDER_REASON_SYSTEM_ADD = "Earned";
	public final static String ORDER_REASON_SYSTEM_SUB = "Deducted";
	public final static String ORDER_REASON_SYSTEM_GIVE = "Earned";
	public final static String ORDER_REASON_USER_RECHARGE = "Top Up";
	public final static String ORDER_REASON_USER_WITHDRAW = "Withdraw";
	public final static String ORDER_REASON_USER_PAYMENT = "Payment";
	public final static String ORDER_REASON_USER_EXCHANGE = "Exchange";
	public final static String ORDER_REASON_USER_REGISTER = "Register";
	public final static String ORDER_REASON_USER_SCORELOTTERY = "LuckyDrawActivity";
	public final static String ORDER_REASON_USER_BUYFREE = "FreeLuckyDrawActivity";
	public final static String ORDER_REASON_USER_CHECKIN = "DailySign-in";
	public final static String ORDER_REASON_USER_DELIVERY = "UserDelivery";
	public final static String ORDER_REASON_USER_RECOMMEND = "UserRecommend";
	

	public final static String NOTICE_MESSAGE_TYPE_SYSTEM_PUBLISH = "system_publish";
	
	public static final Map<String, String> orderTypeTemplateInfoMap = new HashMap<String, String> () {
		private static final long serialVersionUID = 1L;

		{
			put(ORDER_TYPE_SYSTEM_ADD,ORDER_REASON_SYSTEM_ADD);
			put(ORDER_TYPE_SYSTEM_SUB,ORDER_REASON_SYSTEM_SUB);
			put(ORDER_TYPE_SYSTEM_GIVE,ORDER_REASON_SYSTEM_GIVE);
			put(ORDER_TYPE_USER_RECHARGE,ORDER_REASON_USER_RECHARGE);
			put(ORDER_TYPE_USER_WITHDRAW,ORDER_REASON_USER_WITHDRAW);
			put(ORDER_TYPE_USER_PAYMENT,ORDER_REASON_USER_PAYMENT);
			put(ORDER_TYPE_USER_EXCHANGE,ORDER_REASON_USER_EXCHANGE);
			put(ORDER_TYPE_USER_REGISTER,ORDER_REASON_USER_REGISTER);
			put(ORDER_TYPE_USER_SCORELOTTERY,ORDER_REASON_USER_SCORELOTTERY);
			put(ORDER_TYPE_USER_BUYFREE,ORDER_REASON_USER_BUYFREE);
			put(ORDER_TYPE_USER_CHECKIN,ORDER_REASON_USER_CHECKIN);
			put(ORDER_TYPE_USER_DELIVERY,ORDER_REASON_USER_DELIVERY);
			put(ORDER_TYPE_USER_RECOMMEND,ORDER_REASON_USER_RECOMMEND);
		}
	};

	public final static long ZK_LOCK_TIMEOUT = 10L;//zookeeper同步锁超时时间,单位：秒
    
    /*
     * 声明根节点
     */
    public final static String ZK_LOCK_PATHPREFIX="/worldmall/zkLock/";
    
    /**
     * 推送数据类型
     */
    public final static String SOCKET_DATA_TYPE_LOTTERYLIST = "lotteryList";
    public final static String SOCKET_DATA_TYPE_LOTTERYINFO = "lotteryInfo";
    public final static String SOCKET_DATA_TYPE_WININFO = "winInfo";
    public final static String SOCKET_DATA_TYPE_WINILIST = "winList";
    
    public static final Map<String, String> companyInfoMap = new HashMap<String, String> () {
		private static final long serialVersionUID = 1L;

		{
//			put("shunfeng","顺丰速运");
//			put("yuantong","圆通快递");
//			put("zhongtong","中通快递");
//			put("jingdong","京东快递");
//			put("yihaodian","一号店");
//			put("youzhengems","邮政EMS");
//			put("yunda","韵达快递");
//			put("baishi","百世快递");
//			put("shentong","申通快递");
//			put("zhaijisong","宅急送");
//			put("tiantian","天天快递");
//			put("debang","德邦快递");
//			put("yousu","优速快递");
//			put("kuaijie","快捷快递");
			put("DHL", "DHL");
			put("UPS", "UPS");
			put("EMS", "EMS");
			put("FedEx", "FedEx");
		}
	};
    
	public final static String ACTIVITY_INFO_PRIZE_TYPE_FIRSTCHARGE_VOUCHER = "firstcharge_voucher";
	public final static String ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_VOUCHER = "recharge_voucher";
	public final static String ACTIVITY_INFO_PRIZE_TYPE_RECHARGE_GOLD = "recharge_gold";
	
	

	public final static String BASE_CURRENCY = "USD";
	public static final Map<String, String> currences = new HashMap<String, String> () {
		private static final long serialVersionUID = 1L;

		{
			put("AED", "United Arab Emirates Dirham");
			put("AFN", "Afghan Afghani");
			put("ALL", "Albanian Lek");
			put("AMD", "Armenian Dram");
			put("ANG", "Netherlands Antillean Guilder");
			put("AOA", "Angolan Kwanza");
			put("ARS", "Argentine Peso");
			put("AUD", "Australian Dollar");
			put("AWG", "Aruban Florin");
			put("AZN", "Azerbaijani Manat");
			put("BAM", "Bosnia-Herzegovina Convertible Mark");
			put("BBD", "Barbadian Dollar");
			put("BDT", "Bangladeshi Taka");
			put("BGN", "Bulgarian Lev");
			put("BHD", "Bahraini Dinar");
			put("BIF", "Burundian Franc");
			put("BMD", "Bermudan Dollar");
			put("BND", "Brunei Dollar");
			put("BOB", "Bolivian Boliviano");
			put("BRL", "Brazilian Real");
			put("BSD", "Bahamian Dollar");
			put("BTC", "Bitcoin");
			put("BTN", "Bhutanese Ngultrum");
			put("BWP", "Botswanan Pula");
			put("BYN", "Belarusian Ruble");
			put("BZD", "Belize Dollar");
			put("CAD", "Canadian Dollar");
			put("CDF", "Congolese Franc");
			put("CHF", "Swiss Franc");
			put("CLF", "Chilean Unit of Account (UF)");
			put("CLP", "Chilean Peso");
			put("CNH", "Chinese Yuan (Offshore)");
			put("CNY", "Chinese Yuan");
			put("COP", "Colombian Peso");
			put("CRC", "Costa Rican Colón");
			put("CUC", "Cuban Convertible Peso");
			put("CUP", "Cuban Peso");
			put("CVE", "Cape Verdean Escudo");
			put("CZK", "Czech Republic Koruna");
			put("DJF", "Djiboutian Franc");
			put("DKK", "Danish Krone");
			put("DOP", "Dominican Peso");
			put("DZD", "Algerian Dinar");
			put("EGP", "Egyptian Pound");
			put("ERN", "Eritrean Nakfa");
			put("ETB", "Ethiopian Birr");
			put("EUR", "Euro");
			put("FJD", "Fijian Dollar");
			put("FKP", "Falkland Islands Pound");
			put("GBP", "British Pound Sterling");
			put("GEL", "Georgian Lari");
			put("GGP", "Guernsey Pound");
			put("GHS", "Ghanaian Cedi");
			put("GIP", "Gibraltar Pound");
			put("GMD", "Gambian Dalasi");
			put("GNF", "Guinean Franc");
			put("GTQ", "Guatemalan Quetzal");
			put("GYD", "Guyanaese Dollar");
			put("HKD", "Hong Kong Dollar");
			put("HNL", "Honduran Lempira");
			put("HRK", "Croatian Kuna");
			put("HTG", "Haitian Gourde");
			put("HUF", "Hungarian Forint");
			put("IDR", "Indonesian Rupiah");
			put("ILS", "Israeli New Sheqel");
			put("IMP", "Manx pound");
			put("INR", "Indian Rupee");
			put("IQD", "Iraqi Dinar");
			put("IRR", "Iranian Rial");
			put("ISK", "Icelandic Króna");
			put("JEP", "Jersey Pound");
			put("JMD", "Jamaican Dollar");
			put("JOD", "Jordanian Dinar");
			put("JPY", "Japanese Yen");
			put("KES", "Kenyan Shilling");
			put("KGS", "Kyrgystani Som");
			put("KHR", "Cambodian Riel");
			put("KMF", "Comorian Franc");
			put("KPW", "North Korean Won");
			put("KRW", "South Korean Won");
			put("KWD", "Kuwaiti Dinar");
			put("KYD", "Cayman Islands Dollar");
			put("KZT", "Kazakhstani Tenge");
			put("LAK", "Laotian Kip");
			put("LBP", "Lebanese Pound");
			put("LKR", "Sri Lankan Rupee");
			put("LRD", "Liberian Dollar");
			put("LSL", "Lesotho Loti");
			put("LYD", "Libyan Dinar");
			put("MAD", "Moroccan Dirham");
			put("MDL", "Moldovan Leu");
			put("MGA", "Malagasy Ariary");
			put("MKD", "Macedonian Denar");
			put("MMK", "Myanma Kyat");
			put("MNT", "Mongolian Tugrik");
			put("MOP", "Macanese Pataca");
			put("MRO", "Mauritanian Ouguiya (pre-2018)");
			put("MRU", "Mauritanian Ouguiya");
			put("MUR", "Mauritian Rupee");
			put("MVR", "Maldivian Rufiyaa");
			put("MWK", "Malawian Kwacha");
			put("MXN", "Mexican Peso");
			put("MYR", "Malaysian Ringgit");
			put("MZN", "Mozambican Metical");
			put("NAD", "Namibian Dollar");
			put("NGN", "Nigerian Naira");
			put("NIO", "Nicaraguan Córdoba");
			put("NOK", "Norwegian Krone");
			put("NPR", "Nepalese Rupee");
			put("NZD", "New Zealand Dollar");
			put("OMR", "Omani Rial");
			put("PAB", "Panamanian Balboa");
			put("PEN", "Peruvian Nuevo Sol");
			put("PGK", "Papua New Guinean Kina");
			put("PHP", "Philippine Peso");
			put("PKR", "Pakistani Rupee");
			put("PLN", "Polish Zloty");
			put("PYG", "Paraguayan Guarani");
			put("QAR", "Qatari Rial");
			put("RON", "Romanian Leu");
			put("RSD", "Serbian Dinar");
			put("RUB", "Russian Ruble");
			put("RWF", "Rwandan Franc");
			put("SAR", "Saudi Riyal");
			put("SBD", "Solomon Islands Dollar");
			put("SCR", "Seychellois Rupee");
			put("SDG", "Sudanese Pound");
			put("SEK", "Swedish Krona");
			put("SGD", "Singapore Dollar");
			put("SHP", "Saint Helena Pound");
			put("SLL", "Sierra Leonean Leone");
			put("SOS", "Somali Shilling");
			put("SRD", "Surinamese Dollar");
			put("SSP", "South Sudanese Pound");
			put("STD", "São Tomé and Príncipe Dobra (pre-2018)");
			put("STN", "São Tomé and Príncipe Dobra");
			put("SVC", "Salvadoran Colón");
			put("SYP", "Syrian Pound");
			put("SZL", "Swazi Lilangeni");
			put("THB", "Thai Baht");
			put("TJS", "Tajikistani Somoni");
			put("TMT", "Turkmenistani Manat");
			put("TND", "Tunisian Dinar");
			put("TOP", "Tongan Pa'anga");
			put("TRY", "Turkish Lira");
			put("TTD", "Trinidad and Tobago Dollar");
			put("TWD", "New Taiwan Dollar");
			put("TZS", "Tanzanian Shilling");
			put("UAH", "Ukrainian Hryvnia");
			put("UGX", "Ugandan Shilling");
			put("USD", "United States Dollar");
			put("UYU", "Uruguayan Peso");
			put("UZS", "Uzbekistan Som");
			put("VEF", "Venezuelan Bolívar Fuerte (Old)");
			put("VES", "Venezuelan Bolívar Soberano");
			put("VND", "Vietnamese Dong");
			put("VUV", "Vanuatu Vatu");
			put("WST", "Samoan Tala");
			put("XAF", "CFA Franc BEAC");
			put("XAG", "Silver Ounce");
			put("XAU", "Gold Ounce");
			put("XCD", "East Caribbean Dollar");
			put("XDR", "Special Drawing Rights");
			put("XOF", "CFA Franc BCEAO");
			put("XPD", "Palladium Ounce");
			put("XPF", "CFP Franc");
			put("XPT", "Platinum Ounce");
			put("YER", "Yemeni Rial");
			put("ZAR", "South African Rand");
			put("ZMW", "Zambian Kwacha");
			put("ZWL", "Zimbabwean Dollar");
		}
	};
}
