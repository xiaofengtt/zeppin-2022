<%@ page contentType="text/html; charset=GBK"  import="java.util.*,com.enfo.intrust.tools.*,java.io.*,org.xml.sax.*,org.jdom.*,org.jdom.input.*,org.apache.commons.httpclient.*,org.apache.commons.httpclient.methods.*,org.apache.commons.httpclient.util.*,enfo.crm.tools.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> 
<%@ include file="/includes/operator.inc" %>
<%
try{
boolean bSuccess = false;
boolean badded = false;

String cust_name = Utility.trimNull(request.getParameter("cust_name"));//客户名称(姓名)
String customer_post_code = Utility.trimNull(request.getParameter("customer_post_code"));//邮政编码
String customer_cust_type_name = Utility.trimNull(request.getParameter("customer_cust_type_name"));//客户类别
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
String url_black = "http://192.168.1.180:8080/transwatchwebapp/webresources/sdqueryservice/search";
//String url_black = "http://127.0.0.1:8080/xml/xml.do";
//String return_black =  HttpClientHelper.sendSimpleGet(url_black);
String return_black;

HttpClient client = null;
		PostMethod method = null;
		try {
			client = new HttpClient();
			method = new PostMethod(URIUtil.decode(url_black));
			String customer_birthday_picker = Utility.trimNull(session.getAttribute("customer_birthday_picker"));//出生日期
			String customer_sex_name = Utility.trimNull(session.getAttribute("customer_sex_name"));//性别
			String nationality = Utility.trimNull(session.getAttribute("nationality"));//客户国籍
			String customer_service_man = Utility.trimNull(session.getAttribute("customer_service_man"));//客户经理
			String customer_post_address = Utility.trimNull(session.getAttribute("customer_post_address"));//(邮寄)地址
			String nationalityText = Utility.trimNull(session.getAttribute("nationalityText"));//客户国籍文本				
			String body_xml =  Utility.trimNull(session.getAttribute("body_xml"));			
			body_xml = body_xml.replaceAll("</sdQuery>","<createCaseOnMatches>CREATECASE</createCaseOnMatches><residencyName>"+customer_service_man+"</residencyName><nationalityName>"+nationalityText+"</nationalityName></sdQuery>");
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
		session.setAttribute("shujuxml",shujuxml+"数据");
System.out.println("return_black:"+return_black);
		return_black="<?xml version='1.0' encoding='UTF-8' standalone='yes'?><sdResultSet><indexDate>2016-07-12T16:00:18.436+08:00</indexDate><sdQuery><clientType>UNSPECIFIED</clientType><createCaseOnMatches>UNSPECIFIED</createCaseOnMatches><types><type>INDIVIDUAL</type></types><name>Jimmy Carter</name></sdQuery><sdResults><sdResult><categories><category>ec_4</category></categories><entityId>e_tr_wci_11426</entityId><modifiedDate>2014-10-02T08:00:00+08:00</modifiedDate><name>Jimmy CARTER</name><originalScript>Jimmy CARTER</originalScript><score>99.00000095367432</score><secondaryAddrInfo>Plains, Georgia, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Plains</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1924-01-10</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Match，来源类别: -30.0</secondaryRuleInfo><secondaryScore>-30.0</secondaryScore><sources><source>b_trwc_1</source></sources><totalScore>69.00000095367432</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_169265</entityId><modifiedDate>2003-07-09T08:00:00+08:00</modifiedDate><name>Jimmy Jr. CARTER</name><originalScript>Jimmy Jr. CARTER</originalScript><score>97.78863787651062</score><secondaryAddrInfo>AL, UNITED STATES</secondaryAddrInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>UNKNOWN</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source></sources><totalScore>97.78863787651062</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_5</category></categories><entityId>e_tr_wci_1564630</entityId><modifiedDate>2012-02-29T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Manhattan, New York, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Manhattan</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1966-03-09</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -10.0</secondaryRuleInfo><secondaryScore>-10.0</secondaryScore><sources><source>b_trwc_4</source></sources><totalScore>82.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_2001228</entityId><modifiedDate>2013-04-16T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Drogheda, County Louth, IRELAND</secondaryAddrInfo><secondaryCityInfo>Drogheda</secondaryCityInfo><secondaryCountryInfo>IRELAND</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>IE/IRELAND</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_136</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_3</category></categories><entityId>e_tr_wci_69307</entityId><modifiedDate>2011-12-08T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Atlanta, Georgia, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Atlanta</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_415</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_5</category></categories><entityId>e_tr_wci_1333247</entityId><modifiedDate>2010-09-21T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Exeter, Devon, UNITED KINGDOM</secondaryAddrInfo><secondaryCityInfo>Exeter</secondaryCityInfo><secondaryCountryInfo>UNITED KINGDOM</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>GB/UNITED KINGDOM</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -10.0</secondaryRuleInfo><secondaryScore>-10.0</secondaryScore><sources><source>b_trwc_4</source></sources><totalScore>82.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_680535</entityId><modifiedDate>2016-02-25T08:00:00+08:00</modifiedDate><name>James CARTER</name><originalScript>James CARTER</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Lumberton, North Carolina, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Lumberton</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_2781465</entityId><modifiedDate>2016-06-03T08:00:00+08:00</modifiedDate><name>CARTER,James</name><originalScript>CARTER,James</originalScript><score>92.76845455169678</score><secondaryAddrInfo>Fort Pierce, Florida, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Fort Pierce</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source><source>b_trwc_418</source></sources><totalScore>92.76845455169678</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_5</category></categories><entityId>e_tr_wci_321002</entityId><modifiedDate>2011-01-10T08:00:00+08:00</modifiedDate><name>Jimmy CARR</name><originalScript>Jimmy CARR</originalScript><score>90.92227220535278</score><secondaryAddrInfo>Jacksonville, Florida, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Jacksonville</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -10.0</secondaryRuleInfo><secondaryScore>-10.0</secondaryScore><sources><source>b_trwc_4</source></sources><totalScore>80.92227220535278</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_3</category></categories><entityId>e_tr_wci_704583</entityId><modifiedDate>2016-02-16T08:00:00+08:00</modifiedDate><name>Dr James E CARTER</name><originalScript>Dr James E CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Tompkinsville, Kentucky, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Tompkinsville</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1932-12-12</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_368</source></sources><totalScore>90.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_4</category></categories><entityId>e_tr_wci_1862953</entityId><modifiedDate>2012-11-14T08:00:00+08:00</modifiedDate><name>James E CARTER</name><originalScript>James E CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Edmonton, Alberta, CANADA</secondaryAddrInfo><secondaryCityInfo>Edmonton</secondaryCityInfo><secondaryCountryInfo>CANADA</secondaryCountryInfo><secondaryDateOfBirthInfo>1950-00-00</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>CA/CANADA</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -30.0</secondaryRuleInfo><secondaryScore>-30.0</secondaryScore><sources><source>b_trwc_1</source></sources><totalScore>60.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_4</category></categories><entityId>e_tr_wci_182943</entityId><modifiedDate>2010-08-06T08:00:00+08:00</modifiedDate><name>James H CARTER</name><originalScript>James H CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Cedar Rapids, Iowa, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Cedar Rapids</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryDateOfBirthInfo>1935-01-18</secondaryDateOfBirthInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match，来源类别: -30.0</secondaryRuleInfo><secondaryScore>-30.0</secondaryScore><sources><source>b_trwc_1</source></sources><totalScore>60.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult><sdResult><categories><category>ec_2</category></categories><entityId>e_tr_wci_233930</entityId><modifiedDate>2010-12-03T08:00:00+08:00</modifiedDate><name>James R CARTER</name><originalScript>James R CARTER</originalScript><score>90.15541672706604</score><secondaryAddrInfo>Clinton, Maryland, UNITED STATES</secondaryAddrInfo><secondaryCityInfo>Clinton</secondaryCityInfo><secondaryCountryInfo>UNITED STATES</secondaryCountryInfo><secondaryGenderInfo>MALE</secondaryGenderInfo><secondaryNationalityInfo>US/UNITED STATES</secondaryNationalityInfo><secondaryRuleInfo>Name Partial Match</secondaryRuleInfo><secondaryScore>0.0</secondaryScore><sources><source>b_trwc_419</source></sources><totalScore>90.15541672706604</totalScore><type>INDIVIDUAL</type></sdResult></sdResults></sdResultSet>";
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
<!--<script>alert('<%=new String(shujuxml.getBytes("ISO-8859-1"),"UTF-8")%>')</script>-->
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
   window.showModalDialog( "purchase_info_black_filing.jsp", window,"dialogWidth=600px;dialogHeight=400px;status=no;help=no;scroll=no;resizable=no;location=no;toolbar=no" ) ;
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
	bl.setCommonName(sdResult.getChild("originalScript").getText());
	if(Double.parseDouble(sdResult.getChild("totalScore").getText())>92){
	bl.setState("待处理");
	}else{
	bl.setState("可能性较低");
	}
	bl.setAddress(sdResult.getChild("secondaryAddrInfo").getText());
	bl.setGender(sdResult.getChild("secondaryGenderInfo").getText());
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
	bl.setNationality(sdResult.getChild("secondaryNationalityInfo").getText());
	String totalScore_Twodecimalplaces=sdResult.getChild("totalScore").getText();
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
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getName()%>
</td>
<td>
	<%=""%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getCommonName()%>
</td>
<td>
	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getState()%>
</td>
<td>
  	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getAddress()%>
</td>
<td>
  	<%=((com.enfo.intrust.risk.vo.BlackListVO)cloneBlist.get(i)).getGender()%>
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
