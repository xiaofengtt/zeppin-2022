<%@ page contentType="text/html; charset=gbk"  import="java.util.*,com.enfo.intrust.tools.*,java.io.*,org.xml.sax.*,org.jdom.*,org.jdom.input.*,org.apache.commons.httpclient.*,org.apache.commons.httpclient.methods.*,org.apache.commons.httpclient.util.*,enfo.crm.tools.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> 
<%@ include file="/includes/operator.inc" %>
<%
try{
boolean bSuccess = false;
boolean badded = false;

String cust_name = request.getParameter("cust_name");//客户名称(姓名)
String customer_post_address = Utility.trimNull(request.getParameter("customer_post_address"));//(邮寄)地址
if(customer_post_address.equals("")){
customer_post_address = Utility.trimNull(session.getAttribute("customer_post_address"));
}
String nationality = Utility.trimNull(request.getParameter("nationality"));//客户国籍
if(nationality.equals("")){
			nationality = Utility.trimNull(session.getAttribute("nationality"));
}
			
			nationality=nationality.replaceAll("0","");
		    nationality=nationality.replaceAll("1","");
		    nationality=nationality.replaceAll("2","");
		    nationality=nationality.replaceAll("3","");
		    nationality=nationality.replaceAll("4","");
		    nationality=nationality.replaceAll("5","");
		    nationality=nationality.replaceAll("6","");
		    nationality=nationality.replaceAll("7","");
		    nationality=nationality.replaceAll("8","");
		    nationality=nationality.replaceAll("9","");			
	        nationality=com.enfo.intrust.tools.BlackListMatchingSource.getisoCode(nationality);
String nationalityText = Utility.trimNull(request.getParameter("nationalityText"));//客户国籍文本   
if(!nationalityText.equals("")){
session.setAttribute("nationalityText",nationalityText);
}	
  
String customer_sex_name = Utility.trimNull(request.getParameter("customer_sex_name"));//性别

if(customer_sex_name.equals("")){
customer_sex_name = Utility.trimNull(session.getAttribute("customer_sex_name"));
}
customer_sex_name = customer_sex_name.replaceAll("的","");
if(customer_sex_name.equals("男")){
customer_sex_name = "MALE";
}
if(customer_sex_name.equals("女")){
customer_sex_name = "FEMALE";
}
if(customer_sex_name.equals("")){
customer_sex_name = "UNKNOWN";
}
String customer_birthday_picker = Utility.trimNull(request.getParameter("customer_birthday_picker"));//出生日期
if(customer_birthday_picker.equals("")){
customer_birthday_picker = Utility.trimNull(session.getAttribute("customer_birthday_picker"));
}
//String customer_post_code = Utility.trimNull(request.getParameter("customer_post_code"));//邮政编码
String customer_cust_type_name = Utility.trimNull(request.getParameter("customerType"));//客户类别
String customer_service_man = Utility.trimNull(request.getParameter("accountManager"));//客户经理
if(!customer_service_man.equals("")){
session.setAttribute("customer_service_man",customer_service_man);
}
if(customer_cust_type_name.equals("个人")){
customer_cust_type_name = "INDIVIDUAL";
}
if(customer_cust_type_name.equals("国家")){
	customer_cust_type_name = "COUNTRY";
}
if(customer_cust_type_name.equals("船舶")){
    customer_cust_type_name = "VESSEL";
}
if(customer_cust_type_name.equals("机构")){
	customer_cust_type_name = "ORGANISATION";
}
if(customer_cust_type_name.equals("")){
	customer_cust_type_name = Utility.trimNull(session.getAttribute("customer_cust_type_name"));//如果客户类别在URL中没有，则去session当中去取
	if(customer_cust_type_name.equals("个人")){
customer_cust_type_name = "INDIVIDUAL";
}
if(customer_cust_type_name.equals("国家")){
	customer_cust_type_name = "COUNTRY";
}
if(customer_cust_type_name.equals("船舶")){
    customer_cust_type_name = "VESSEL";
}
if(customer_cust_type_name.equals("机构")){
	customer_cust_type_name = "ORGANISATION";
}
}
String url_black = "http://192.168.1.180:8080/transwatchwebapp/webresources/sdqueryservice/search";
//String url_black = "http://127.0.0.1:8080/xml/xml.do";
//String return_black =  HttpClientHelper.sendSimpleGet(url_black);
String return_black;

HttpClient client = null;
		PostMethod method = null;
		try {
			client = new HttpClient();
			method = new PostMethod(URIUtil.decode(url_black));			
			String body_xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><sdQuery><types><type>"+customer_cust_type_name+"</type></types><name>"+cust_name+"</name><indDob>"+customer_birthday_picker+"</indDob><indGender>"+customer_sex_name+"</indGender><nationalityCode>"+nationality+"</nationalityCode><address>"+customer_post_address+"</address></sdQuery>";
			session.setAttribute("body_xml",body_xml);
            method.setRequestBody(body_xml);
            //method.setRequestHeader("Accept", "Application/xml");
            //method.setRequestHeader("Authorization", "Basic Auth");
            method.setRequestHeader("Content-type", "Application/xml; charset=UTF-8");
            //method.setRequestHeader("Content-Length", "300");
            //method.setRequestHeader("Connection", "Keep-Alive");
            //method.setRequestHeader("User-Agent", "Apache-HttpClient/4.1.3(java1.5)");
			int statusCode = 0;

			statusCode = client.executeMethod(method);
			if (statusCode == 200) {
				return_black = method.getResponseBodyAsString();
			} else {
				throw new Exception("返回代码：" + statusCode);
			}
		} catch (Exception e) {			
			throw new Exception("HTTP访问异常," + e.getMessage());
		} finally {
			if (method != null)
				method.releaseConnection();//释放连接
		}

String shujuxml=return_black;
System.out.println("return_black:"+return_black);
		session.setAttribute("shujuxml",shujuxml);
		//return_black="<?xml version='1.0' encoding='UTF-8' standalone='yes'?><sdResultSet><indexDate>2016-07-12T16:00:18.436+08:00</indexDate><sdQuery><clientType>UNSPECIFIED</clientType><createCaseOnMatches>UNSPECIFIED</createCaseOnMatches><types><type>INDIVIDUAL</type></types><name>Jimmy Carter</name></sdQuery><sdResults><sdResult><categories><category>ec_4</category></categories><entityId>e_tr_wci_11426</entityId><modifiedDate>2014-10-02T08:00:00+08:00</modifiedDate><name>Jimmy CARTER</name><originalScript>Jimmy CARTER</originalScript><score>99.00000095367432</score><secondaryAddrInfo>Plains, Georgia, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Plains</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1924-01-10</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Match，来源类别: -30.0</secondaryRuleInfo><secondaryScore>-30.0</secondaryScore><sources><source>b_trwc_1</source></sources><totalScore>69.00000095367432</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_169265</entityId><modifiedDate>2003-07-09T08:00:00+08:00</modifiedDate><name>Jimmy Jr. CARTER</name><originalScript>Jimmy Jr. CARTER</originalScript><score>97.78863787651062</score><secondaryAddrInfo>AL, UNITED STATES</secondaryAddrInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>UNKNOWN</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source></sources><totalScore>97.78863787651062</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_5</category></categories><entityId>e_tr_wci_1564630</entityId><modifiedDate>2012-02-29T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Manhattan, New York, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Manhattan</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1966-03-09</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -10.0</secondaryRuleInfo><secondaryScore>-10.0</secondaryScore><sources><source>b_trwc_4</source></sources><totalScore>82.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_2001228</entityId><modifiedDate>2013-04-16T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Drogheda, County Louth, IRELAND</secondaryAddrInfo><secondaryCityInfo>Drogheda</secondaryCityInfo><secondaryCountryInfo>IRELAND</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>IE/IRELAND</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_136</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_3</category></categories><entityId>e_tr_wci_69307</entityId><modifiedDate>2011-12-08T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Atlanta, Georgia, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Atlanta</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_415</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_5</category></categories><entityId>e_tr_wci_1333247</entityId><modifiedDate>2010-09-21T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Exeter, Devon, UNITED KINGDOM</secondaryAddrInfo><secondaryCityInfo>Exeter</secondaryCityInfo><secondaryCountryInfo>UNITED KINGDOM</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>GB/UNITED KINGDOM</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -10.0</secondaryRuleInfo><secondaryScore>-10.0</secondaryScore><sources><source>b_trwc_4</source></sources><totalScore>82.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_680535</entityId><modifiedDate>2016-02-25T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Lumberton, North Carolina, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Lumberton</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_2781465</entityId><modifiedDate>2016-06-03T08:00:00+08:00</modifiedDate><name>CARTER,James</name><originalScript>CARTER,James</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Fort Pierce, Florida, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Fort Pierce</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source><source>b_trwc_418</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_5</category></categories><entityId>e_tr_wci_321002</entityId><modifiedDate>2011-01-10T08:00:00+08:00</modifiedDate><name>Jimmy CARR</name><originalScript>Jimmy CARR</originalScript><score>90.92227220535278</score><secondaryAddrInfo>Jacksonville, Florida, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Jacksonville</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -10.0</secondaryRuleInfo><secondaryScore>-10.0</secondaryScore><sources><source>b_trwc_4</source></sources><totalScore>80.92227220535278</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_3</category></categories><entityId>e_tr_wci_704583</entityId><modifiedDate>2016-02-16T08:00:00+08:00</modifiedDate><name>Dr James E CARTER</name><originalScript>Dr James E CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Tompkinsville, Kentucky, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Tompkinsville</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1932-12-12</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_368</source></sources><totalScore>90.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_4</category></categories><entityId>e_tr_wci_1862953</entityId><modifiedDate>2012-11-14T08:00:00+08:00</modifiedDate><name>James E CARTER</name><originalScript>James E CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Edmonton, Alberta, CANADA</secondaryAddrInfo><secondaryCityInfo>Edmonton</secondaryCityInfo><secondaryCountryInfo>CANADA</secondaryCountryInfo><secondaryDateOfBirthInfo>1950-00-00</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>CA/CANADA</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -30.0</secondaryRuleInfo><secondaryScore>-30.0</secondaryScore><sources><source>b_trwc_1</source></sources><totalScore>60.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_4</category></categories><entityId>e_tr_wci_182943</entityId><modifiedDate>2010-08-06T08:00:00+08:00</modifiedDate><name>James H CARTER</name><originalScript>James H CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Cedar Rapids, Iowa, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Cedar Rapids</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1935-01-18</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -30.0</secondaryRuleInfo><secondaryScore>-30.0</secondaryScore><sources><source>b_trwc_1</source></sources><totalScore>60.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_233930</entityId><modifiedDate>2010-12-03T08:00:00+08:00</modifiedDate><name>James R CARTER</name><originalScript>James R CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Clinton, Maryland, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Clinton</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source></sources><totalScore>90.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult></sdResults></sdResultSet>";
		//创建一个新的字符串
        StringReader read = new StringReader(return_black);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            System.out.println(root.getName());//输出根元素的名称
			Element sdResults = root.getChild("sdResults");
            //得到sdResults元素所有子元素的集合
            List sdResult_list = sdResults.getChildren();
            Element sdResult = null;
            for(int i=0;i<sdResult_list.size();i++){
                sdResult = (Element) sdResult_list.get(i);//循环依次得到子元素
                System.out.println(sdResult.getChild("name").getText());
                //System.out.println(sdResult.getChild("score").getText());
           }

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title></title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
</head>
<!--<script>alert('<%=new String(shujuxml.getBytes("ISO-8859-1"),"UTF-8")%>'); var shuju = '<%=new String(shujuxml.getBytes("ISO-8859-1"),"UTF-8")%>';</script>-->
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/financing/pzim/jquery-1.8.2.min.js'></script>

<%if(language.equals("en")){ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_en.js"></SCRIPT>
<%}else{ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<%} %>
<SCRIPT language="JavaScript">
<%if (bSuccess)
{%>
	var ret = new Array();
	ret[0] = '';
	window.returnValue = ret;
	window.close();
<%}%>
function save(){
	document.theform.submit();
}
function filingBlack(){
	if(confirm("确定要立案吗?")){
	alert("已经将黑名单立案!");
	ajax({
        url: "<%=request.getContextPath()%>/marketing/black_filing.jsp",              //请求地址
        type: "GET",                       //请求方式
        data: { lian:"lian"},        //请求参数
        dataType: "json",
        success: function (response, xml) {
			alert("success");
            // 此处放成功后执行的代码
        },
        fail: function (status) {
            // 此处放失败后执行的代码
			
        }
    });
	window.close();
	}
   //window.showModalDialog( "purchase_info_black_filing.jsp", window,"dialogWidth=600px;dialogHeight=400px;status=no;help=no;scroll=no;resizable=no;location=no;toolbar=no" ) ;
}
function ajax(options) {
        options = options || {};
        options.type = (options.type || "GET").toUpperCase();
        options.dataType = options.dataType || "json";
        var params = formatParams(options.data);

        //创建 - 非IE6 - 第一步
        if (window.XMLHttpRequest) {
            var xhr = new XMLHttpRequest();
        } else { //IE6及其以下版本浏览器
            var xhr = new ActiveXObject('Microsoft.XMLHTTP');
        }

        //接收 - 第三步
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                var status = xhr.status;
                if (status >= 200 && status < 300) {
                    options.success && options.success(xhr.responseText, xhr.responseXML);
                } else {
                    options.fail && options.fail(status);
                }
            }
        }

        //连接 和 发送 - 第二步
        if (options.type == "GET") {
            xhr.open("GET", options.url + "?" + params, true);
            xhr.send(null);
        } else if (options.type == "POST") {
            xhr.open("POST", options.url, true);
            //设置表单提交时的内容类型
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send(params);
        }
    }
    
//格式化参数
function formatParams(data) {
        var arr = [];
        for (var name in data) {
            arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name]));
        }
        arr.push(("v=" + Math.random()).replace(".",""));
        return arr.join("&");
 }
</SCRIPT>

<base target="_self">
<body  class="BODY" topmargin="8" leftmargin="8" rightmargin="8" onkeydown="javascript:chachEsc(window.event.keyCode)" >
<form name="theform" action="purchase_info_check_black.jsp" method="post">
<input type="hidden" id="case_type" name="case_type" value="CREATECASE">
<table border="0" width="100%" cellspacing="0" cellpadding="4">
	<tr>
		<td>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2"><b><img border="0" src="/images/member.gif" align="absmiddle" width="32" height="28">黑名单筛查结果</b></td>
				</tr>
			</table>
		</td>
	</tr>
	
	
	<tr>
		<td colspan="2">
		<table border="0" width="100%">
			<tr>
				<td align="right">
				<button class="xpbutton3" accessKey=s  name="btnSave" onclick="javascript:filingBlack();">立案 (<u>S</u>)</button>
				&nbsp;&nbsp;
				<br/><br/>
				<span style="color:red">注:如果名单中状态一栏均为可能性较低，则无需立案</span>
				</td>
			</tr>			
		</table>
		</td>
	</tr>
	<tr>
		<td>
			<table id="table3"  border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
<tr class="trh">
<td align="center">
	姓名
</td>
<td align="center">
	名称类型
</td>
<td align="center">
	常用名称
</td>
<td align="center">
	状态
</td>
<td align="center">
	地址
</td>
<td align="center">
	性别
</td>
<td align="center">
	出生日期
</td>
<td align="center">
	居住地
</td>
<td align="center">
	国籍
</td>
<td align="center">
	评级分数
</td>
<td align="center">
	来源类别
</td>
<td align="center">
	子类别
</td>
<td align="center">
	匹配来源
</td>
<%
    List blist=new ArrayList();
	List cloneBlist=new ArrayList();
 %>
</tr>
<% for(int i=0;i<sdResult_list.size();i++){
                sdResult = (Element) sdResult_list.get(i);//循环依次得到子元素
	com.enfo.intrust.risk.vo.BlackListVO bl=new com.enfo.intrust.risk.vo.BlackListVO();
	bl.setName(new String(sdResult.getChild("name").getText().getBytes("ISO-8859-1"),"UTF-8"));
	bl.setNameType("");
	bl.setCommonName(new String(sdResult.getChild("originalScript").getText().getBytes("ISO-8859-1"),"UTF-8"));
	if(Double.parseDouble(sdResult.getChild("totalScore").getText())>92){
	bl.setState("待处理");
	}else{
	bl.setState("可能性较低");
	}
	if(sdResult.getChild("secondaryAddrInfo")==null){
		bl.setAddress("");
	}else{
		bl.setAddress(sdResult.getChild("secondaryAddrInfo").getText());
	}
	if(sdResult.getChild("secondaryGenderInfo")==null){
		bl.setGender("");
	}else{
		bl.setGender(sdResult.getChild("secondaryGenderInfo").getText());
	}
	
	if(sdResult.getChild("secondaryDateOfBirthInfo")==null){
	bl.setDateOfBirth("");
	}else{
	bl.setDateOfBirth(sdResult.getChild("secondaryDateOfBirthInfo").getText());
	}
	if(sdResult.getChild("secondaryResidencyInfo")==null){
	bl.setResidence("");
	}else{
	bl.setResidence(sdResult.getChild("secondaryResidencyInfo").getText());
	}
	if(sdResult.getChild("secondaryNationalityInfo")==null){
	bl.setNationality("");	
	}else{
	bl.setNationality(sdResult.getChild("secondaryNationalityInfo").getText());
	}
	String totalScore_Twodecimalplaces=sdResult.getChild("totalScore").getText()+"000";
	totalScore_Twodecimalplaces=totalScore_Twodecimalplaces.substring(0, 5);
	bl.setRatingscore(Double.parseDouble(totalScore_Twodecimalplaces));
	
	if(sdResult.getChild("categories").getChildText("category").equals("ec_1")){	 
	bl.setSourcecategory("Sanctions");
	}
	if(sdResult.getChild("categories").getChildText("category").equals("ec_2")){     
	bl.setSourcecategory("Law Enforcement");
	}
	if(sdResult.getChild("categories").getChildText("category").equals("ec_3")){     
	bl.setSourcecategory("Regulatory Enforcement");
	}
	if(sdResult.getChild("categories").getChildText("category").equals("ec_4")){     
	bl.setSourcecategory("PEP");
	}
	if(sdResult.getChild("categories").getChildText("category").equals("ec_5")){     
	bl.setSourcecategory("Other Bodies");
	}
	if(sdResult.getChild("categories").getChildText("category").equals("ec_99")){    
	bl.setSourcecategory("Client Watchlist");
	}
	bl.setSubcategory("");
	bl.setMatchingsource(com.enfo.intrust.tools.BlackListMatchingSource.getname(sdResult.getChild("sources").getChildText("source")));
	blist.add(bl);
%>

<%} %>
<%
	double[] arrays=new double[blist.size()];
	for(int i=0;i<arrays.length;i++){
	com.enfo.intrust.risk.vo.BlackListVO blv=new com.enfo.intrust.risk.vo.BlackListVO();
	blv=(com.enfo.intrust.risk.vo.BlackListVO)blist.get(i);
	arrays[i]=blv.getRatingscore();
	}
	Arrays.sort(arrays);
	double[] cloneArrays=new double[arrays.length];
	for(int i=0;i<cloneArrays.length;i++){
    cloneArrays[i]=arrays[cloneArrays.length-i-1];
	} 	
	for(int k=0;k<cloneArrays.length;k++){
	for(int l=0;l<blist.size();l++){
	com.enfo.intrust.risk.vo.BlackListVO blv=new com.enfo.intrust.risk.vo.BlackListVO();
	blv=(com.enfo.intrust.risk.vo.BlackListVO)blist.get(l);	
	if(cloneArrays[k]==blv.getRatingscore()){	
	cloneBlist.add(blv);
	blist.remove(l);
	break;
	}
	}		
	}
	
 %>
 <%
 for(int i=0;i<cloneBlist.size();i++){ 
 %>
 <tr class="tr<%=(i % 2)%>">
<td nowrap>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getName()%>
</td>
<td>
	<%=""%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getCommonName()%>
</td>
<td>
	<span  <%String state = ((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getState();%><% if(state.equals("可能性较低")){%>style="color:#458E18"<%}%><% if(state.equals("待处理")){%>style="color:red"<%}%>><%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getState()%></span>
</td>
<td>
  	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getAddress()%>
</td>
<td nowrap>
  	<%
		String  gender = ((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getGender();	
	%>
  	<%
		if("MALE".equals(gender)){			
		
	%>
	<%="男"%>
	<%
		}
	%>
	<%
		if("FEMALE".equals(gender)){			
		
	%>
	<%="女"%>
	<%
		}
	%>
	<%
		if("UNKNOWN".equals(gender)){			
		
	%>
	<%="未知"%>
	<%
		}
	%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getDateOfBirth()%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getResidence()%>
  	
</td>
<td>
  	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getNationality()%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getRatingscore()%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getSourcecategory()%>
</td>
<td>
	<%=""%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getMatchingsource()%>
</td>
</tr>
 <%}
 %>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<% } catch (JDOMException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        } catch (Exception e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
}catch (Exception e) {
            throw e;
        }
%>
