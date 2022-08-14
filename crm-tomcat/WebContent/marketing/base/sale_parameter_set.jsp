<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//ҳ�洫�ݱ���
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String sub_product_name = Utility.trimNull(request.getParameter("sub_product_name"));
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
String productCode = Utility.trimNull(request.getParameter("productcode"));
String productName = Utility.trimNull(request.getParameter("productName"));
String risk_level = Utility.trimNull(request.getParameter("risk_level"));
Integer intrust_flag1 = Utility.parseInt(request.getParameter("intrust_flag1"),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));

Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
String r_channel_name = Utility.trimNull(request.getParameter("r_channel_name"));
BigDecimal fareRate = Utility.parseDecimal(Utility.trimNull(request.getParameter("fareRate")), new BigDecimal(0),4,"0.01");
Integer preStartDate = Utility.parseInt(request.getParameter("preStartDate"),new Integer(0));
Integer preEndDate = Utility.parseInt(request.getParameter("preEndDate"),new Integer(0));
BigDecimal pre_max_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_max_rate")), new BigDecimal(0),4,"0.01");
String pre_code = Utility.trimNull(request.getParameter("pre_code"));
String pre_start_time = Utility.trimNull(request.getParameter("pre_start_time"));
String pre_end_time = Utility.trimNull(request.getParameter("pre_end_time"));
Integer template_id = Utility.parseInt(request.getParameter("template_id"),new Integer(0));//��ӡģ��ID
Integer pre_min_amount = Utility.parseInt(request.getParameter("pre_min_amount"),new Integer(0));//ԤԼ��С���
Integer up_to_show = Utility.parseInt(request.getParameter("up_to_show"),new Integer(0));//�Ϲ���ʾ��ֵ


//��Ϣ��ʾ��������
Integer qualified_flag = Utility.parseInt(request.getParameter("qualified_flag"), new Integer(0));//new Integer(0);
Integer qualified_num = new Integer(0);
Integer asfund_flag = new Integer(3);
Integer gain_flag = new Integer(1) ;
Integer open_gain_flag = new Integer(2);
BigDecimal qualified_money = new BigDecimal(0);
Integer is_bank_consign = new Integer(2); //�Ƿ����д��� 1�ǡ�2�� Ĭ�Ϸ�
BigDecimal jg_min_subamount = new BigDecimal(0); 
BigDecimal gr_min_subamount = new BigDecimal(0); 
BigDecimal jg_min_bidsamount = new BigDecimal(0); 
BigDecimal gr_min_bidsamount = new BigDecimal(0); 
BigDecimal jg_min_appbidsamount = new BigDecimal(0); 
BigDecimal gr_min_appbidsamount = new BigDecimal(0); 
BigDecimal min_redeem_vol = new BigDecimal(0); 
Integer large_redeem_flag = new Integer(2);
Integer large_redeem_condition = new Integer(1);
BigDecimal large_redeem_percent = new BigDecimal(0); 
Integer large_redeem_deal = new Integer(1);
Integer coerce_redeem_flag = new Integer(2);
String styleStr = "none" ;
if(qualified_flag.intValue()==1){
	styleStr = "" ;
}
String productJC = "";
BigDecimal preMoney = new BigDecimal(0);
Integer preNum = new Integer(0);
BigDecimal minMoney = new BigDecimal(0);
Integer periodUnit = new Integer(0);
String team_name = "";
BigDecimal quota_money = new BigDecimal(0);//�������
Integer quota_qualified_num = new Integer(0);//�ϸ�Ͷ�����������
String valid_period = "";



//��ö���
ProductVO vo = new ProductVO();
ProductLocal product = EJBFactory.getProduct();

//ҳ�����
List listProduct = null;
Map map = null;
List listIssue = null;
Map map_issue = null;
List list_crm_product = null;
Map map_issue_crm = null;
boolean bSuccess = false ;
String error_mess = "";

//�����Ʒ������Ϣ
if(request.getMethod().equals("POST")){
	vo.setProduct_id(productId) ;
	vo.setProduct_code(productCode);
	vo.setProduct_name(productName);
	vo.setRisk_level(risk_level);
	vo.setInput_man(input_operatorCode);
	vo.setPre_start_date(preStartDate);
	vo.setPre_end_date(preEndDate);
	vo.setPre_code(pre_code);
	vo.setPre_max_rate(pre_max_rate);
	vo.setSub_product_id(sub_product_id);
	vo.setList_name(sub_product_name);
	
	if(intrust_flag1.intValue() == 2){//�Ƿ�Ϊ���ϲ�Ʒ
		vo.setQualified_flag(Utility.parseInt(request.getParameter("qualified_flag"),new Integer(1)));
		vo.setAsfund_flag(Utility.parseInt(request.getParameter("asfund_flag"),new Integer(1)));
		vo.setGain_flag(Utility.parseInt(request.getParameter("gain_flag"),new Integer(1)));
		vo.setOpen_gain_flag(Utility.parseInt(request.getParameter("open_gain_flag"),new Integer(2)));
		vo.setIs_bank_consign(Utility.parseInt(request.getParameter("is_bank_consign"),new Integer(2)));
	
		if(is_bank_consign.intValue() == 1)//�Ƿ����д��� 1�ǡ�2�� Ĭ�Ϸ�
		{
			vo.setJg_min_subamount(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("jg_min_subamount"))),new BigDecimal(0)));
			vo.setGr_min_subamount(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("gr_min_subamount"))),new BigDecimal(0)));
			vo.setJg_min_bidsamount(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("jg_min_bidsamount"))),new BigDecimal(0)));
			vo.setGr_min_bidsamount(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("gr_min_bidsamount"))),new BigDecimal(0)));
			vo.setGr_min_appbidsamount(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("jg_min_appbidsamount"))),new BigDecimal(0)));
			vo.setJg_min_appbidsamount(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("gr_min_appbidsamount"))),new BigDecimal(0)));
			vo.setMin_redeem_vol(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("min_redeem_vol"))),new BigDecimal(0)));
			vo.setCoerce_redeem_flag(Utility.parseInt(Utility.trimNull(request.getParameter("coerce_redeem_flag")), new Integer(2)));
			if(large_redeem_flag.intValue() == 1)//�Ƿ���޶����
			{
				vo.setLarge_redeem_flag(Utility.parseInt(Utility.trimNull(request.getParameter("large_redeem_flag")), new Integer(2)));
				vo.setLarge_redeem_condition(Utility.parseInt(Utility.trimNull(request.getParameter("large_redeem_condition")), new Integer(1)));
				vo.setLarge_redeem_percent(Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("large_redeem_percent"))),new BigDecimal(0)).divide(new BigDecimal(100), 4, BigDecimal.ROUND_UP));
				vo.setLarge_redeem_deal(Utility.parseInt(Utility.trimNull(request.getParameter("large_redeem_deal")), new Integer(1)));
			}else{
				vo.setLarge_redeem_flag(new Integer(2));
				vo.setLarge_redeem_condition(new Integer(1));
				vo.setLarge_redeem_percent(new BigDecimal(0));
				vo.setLarge_redeem_deal(new Integer(1));
			}
			
		}else{//���������д���
			vo.setJg_min_subamount(new BigDecimal(0));
			vo.setGr_min_subamount(new BigDecimal(0));
			vo.setJg_min_bidsamount(new BigDecimal(0));
			vo.setGr_min_bidsamount(new BigDecimal(0));
			vo.setGr_min_appbidsamount(new BigDecimal(0));
			vo.setJg_min_appbidsamount(new BigDecimal(0));
			vo.setMin_redeem_vol(new BigDecimal(0));
			vo.setLarge_redeem_flag(new Integer(2));
			vo.setLarge_redeem_condition(new Integer(1));
			vo.setLarge_redeem_percent(new BigDecimal(0));
			vo.setLarge_redeem_deal(new Integer(1));
			vo.setCoerce_redeem_flag(new Integer(2));
		}
	
		if(qualified_flag.intValue()==1){//��Ȼ�������ɿ��� �����������ͽ��
			vo.setQualified_num(Utility.parseInt(request.getParameter("qualified_num"),new Integer(0)));	
			vo.setQualified_money(Utility.parseDecimal(Utility.trimNull(request.getParameter("qualified_money")),new BigDecimal(0)));	
		}else{
			vo.setQualified_num(null);	
			vo.setQualified_money(null);	
		}
		//�����޸Ĳ�Ʒ��������
		product.updateProductLimit(vo);
	}

	vo.setR_channel_id(r_channel_id);
	vo.setR_chanel_rate(fareRate);
	double comm_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("commission_rate")),new BigDecimal(0)).doubleValue()/100.0;
	vo.setCommission_rate(new BigDecimal(comm_rate));
	vo.setPre_start_time(pre_start_time);
	vo.setPre_end_time(pre_end_time);
	vo.setTemplate_id(template_id);
	vo.setPre_min_amount(pre_min_amount);
	vo.setUp_to_show(up_to_show);
	//�����޸�CRM�в�Ʒ��
	product.modiCRMProduct(vo);	
	bSuccess = true ;
}
if(!request.getMethod().equals("POST")){

	//productId������0�����ѯ��ѯ������Ʒ��Ϣ
	if(productId.intValue()!= 0){
		//��Ʒ��Ϣ
		vo.setProduct_id(productId);
			
		if(sub_product_id.intValue()!=0){
			vo.setSub_product_id(sub_product_id);
			listProduct = product.listSubProduct(vo);
		}else{
			listProduct = product.load(vo);
		}	
		if(listProduct.size() >0){
			map = (Map)listProduct.get(0);
		}
		
		if(intrust_flag1.intValue() == 2){//�Ƿ�Ϊ���ϲ�Ʒ
			Utility.debug(vo.getProduct_id());
			listIssue = product.queryProductLimit(vo);
			if(listIssue.size() >0){
				map_issue = (Map)listIssue.get(0);
			}
		}
		
		//���յȼ�
		vo.setBook_code(new Integer(1));
		vo.setStart_date(new Integer(0));
		vo.setEnd_date(new Integer(0));
		vo.setProduct_status("");
		vo.setCheck_flag(new Integer(0));
		vo.setOpen_flag(new Integer(0));
		vo.setIntrust_flag1(new Integer(0));
		vo.setProduct_id(productId);
		vo.setProduct_name("");
		vo.setProduct_code("");
		vo.setSale_status(new Integer(0));
		vo.setInput_man(input_operatorCode);
		list_crm_product = product.listCrmProduct(vo);
		if(list_crm_product.size()>0){
			map_issue_crm = (Map)list_crm_product.get(0); 
		}
		
	}
	//��Ʒ��Ϣ
	if(map!= null){
		productCode = Utility.trimNull(map.get("PRODUCT_CODE"));
		productName = Utility.trimNull(map.get("PRODUCT_NAME"));
		productJC = Utility.trimNull(map.get("PRODUCT_JC"));
		preMoney = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0),2,"1");
		preNum = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
		minMoney = Utility.parseDecimal(Utility.trimNull(map.get("MIN_MONEY")), new BigDecimal(0),2,"1");
		preStartDate = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
		preEndDate = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
		periodUnit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
		intrust_flag1= Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG1")),new Integer(0));
		valid_period = Utility.trimNull(map.get("VALID_PERIOD"));
		pre_code =  Utility.trimNull(map.get("PRE_CODE"));	
		pre_max_rate = Utility.parseDecimal(Utility.trimNull(map_issue_crm.get("PRE_MAX_RATE")), new BigDecimal(0),4,"100");
		pre_max_rate = pre_max_rate.setScale(2,BigDecimal.ROUND_HALF_UP);
		
	}
	//��Ʒ������Ϣ
	if(map_issue != null){
			qualified_money = Utility.parseDecimal(Utility.trimNull(map_issue.get("QUALIFIED_MONEY")),new BigDecimal(0));
			qualified_flag = Utility.parseInt(Utility.trimNull(map_issue.get("QUALIFIED_FLAG")),new Integer(1));
			qualified_num = Utility.parseInt(Utility.trimNull(map_issue.get("QUALIFIED_NUM")),new Integer(0));
			asfund_flag = Utility.parseInt(Utility.trimNull(map_issue.get("ASFUND_FLAG")),new Integer(1)) ;
			gain_flag = Utility.parseInt(Utility.trimNull(map_issue.get("GAIN_FLAG")),new Integer(1)) ;
			open_gain_flag = Utility.parseInt(Utility.trimNull(map_issue.get("OPEN_GAIN_FLAG")),new Integer(2)) ;
			is_bank_consign = Utility.parseInt(Utility.trimNull(map_issue.get("TA_FLAG")),new Integer(2));
			jg_min_subamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("JG_MIN_SUBAMOUNT")),new BigDecimal(0));
			gr_min_subamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("GR_MIN_SUBAMOUNT")),new BigDecimal(0));
			jg_min_bidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("JG_MIN_BIDSAMOUNT")),new BigDecimal(0));
			gr_min_bidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("GR_MIN_BIDSAMOUNT")),new BigDecimal(0));
			jg_min_appbidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("JG_MIN_APPBIDSAMOUNT")),new BigDecimal(0));
			gr_min_appbidsamount = Utility.parseDecimal(Utility.trimNull(map_issue.get("GR_MIN_APPBIDSAMOUNT")),new BigDecimal(0));
			min_redeem_vol = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map_issue.get("MIN_REDEEM_VOL"))),new BigDecimal(0)); 
			large_redeem_flag = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_FLAG")), new Integer(2));
			large_redeem_condition = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_CONDITION")), new Integer(1));
			large_redeem_percent = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(map_issue.get("LARGE_REDEEM_PERENT"))),new BigDecimal(0)); 
			large_redeem_deal = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_DEAL")), new Integer(1));
			coerce_redeem_flag = Utility.parseInt(Utility.trimNull(map_issue.get("LARGE_REDEEM_DEAL")), new Integer(2));
 			template_id = Utility.parseInt(Utility.trimNull(map_issue.get("CONTRACT_PRT_TEMPLATE")), new Integer(0));
			up_to_show = Utility.parseInt(Utility.trimNull(map_issue.get("UP_TO_SHOW")), new Integer(0));
	}
	//�鿴���յȼ�
	if(map_issue_crm != null){
		risk_level = Utility.trimNull(map_issue_crm.get("RISK_LEVEL"));
		r_channel_id = Utility.parseInt(Utility.trimNull(map_issue_crm.get("CHANNEL_ID")),new Integer(0));
		r_channel_name = Utility.trimNull(map_issue_crm.get("CHANNEL_NAME"));
		fareRate = Utility.parseDecimal(Utility.trimNull(map_issue_crm.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100");
		fareRate = fareRate.setScale(2,BigDecimal.ROUND_HALF_UP);
		pre_min_amount = Utility.parseInt(Utility.trimNull(map_issue_crm.get("PRE_MIN_AMOUNT")),new Integer(0));
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.saleParameterSet",clientLocale)%></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script language="javascript">
if(<%=bSuccess%>){
	sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ");//����ɹ�
	window.location.href="sale_parameter.jsp?";
}else{
	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';
	var invest_type_name = '';
	var comboBoxParentTree;
	Ext.onReady(function(){
		comboBoxParentTree = new Ext.ux.ComboBoxTree({
			renderTo : 'comboBoxParentTree',
			width:180,
			tree : {
				xtype:'treepanel',
				width:220,
				loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/parentTree.jsp?channel_type='}),
	       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnName",clientLocale)%> '}),//������Ϣ
	       	 	listeners:{
	       	 		beforeload:function(node){
	       	 			if(node.id!='-1')
	       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/parentTree.jsp?value='+node.id+'&channel_type=';
	       	       	 }
	       	 	}
	    	},
	    	
			//all:���н�㶼��ѡ��
			//exceptRoot��������㣬������㶼��ѡ(Ĭ��)
			//folder:ֻ��Ŀ¼����Ҷ�ӺͷǸ���㣩��ѡ
			//leaf��ֻ��Ҷ�ӽ���ѡ
			selectNodeModel:'exceptRoot'
		});

		if(<%=r_channel_id%> !=0){
			comboBoxParentTree.setValue({id:'<%=r_channel_id%>',text:"<%=r_channel_name%>"});
		}

	}); 
}
	var oState = {
		
	}
	
	function refreshPage()
	{
		var url ="sale_parameter.jsp?page=1&pagesize=" + document.theform.pagesize.value
						+"&team_id="+document.theform.team_id.value;
						
		location = url
	}
	//����
	function CancelAction(){
		window.location.href="sale_parameter.jsp?";
	}

/*����*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="sale_parameter_set.jsp" ;
		document.theform.submit();
	}
}

window.onload = function(){
	changeView(<%=qualified_flag.intValue() %>);
};

function validateForm(form){
	document.theform.channel_id.value = comboBoxParentTree.getValue();

		if(!sl_checkDate(document.theform.preStartDate_picker,'�ƽ���ʼ���� '))	return false;//�ƽ���ʼ����
		if(!sl_checkDate(document.theform.preEndDate_picker,'�ƽ���ֹ����'))	return false;//�ƽ���ֹ����
		syncDatePicker(form.preStartDate_picker, form.preStartDate);
		syncDatePicker(form.preEndDate_picker, form.preEndDate);
		if(form.preStartDate.value > form.preEndDate.value){
			sl_alert('�ƽ���ʼ���ڲ�Ӧ�������ƽ���ֹ���ڣ�����������');//�ƽ���ʼ���ڲ�Ӧ�������ƽ���ֹ���ڣ�����������
			form.preStartDate_picker.focus();
			return false;
		}

		if(!sl_checkDecimal(form.pre_max_rate, 'ԤԼ������ ', 3, 4, 0))		return false;//ԤԼ������

	if(form.intrust_flag1.value ==2){
		if(!sl_checkChoice(form.qualified_flag,"��Ȼ����������"))	return false;
		if(!sl_checkChoice(form.asfund_flag,"���漶����㵥λ"))	return false;	
		if(theform.qualified_flag.value==1){
			if(!sl_checkDecimal(form.qualified_money, "�ϸ�Ͷ�����ʽ������", 16, 2, 0))		return false;
			if(!sl_checkNum(form.qualified_num, '��Ȼ������ ', 3, 0))	return false;
			if(theform.qualified_num.value=='' || theform.qualified_num.value==0){
				alert("��Ȼ��������Ч�����飡");
				return false;			
			}	
			if(theform.qualified_money.value=='' || theform.qualified_money.value==0){
				alert("�ϸ�Ͷ�����ʽ��������Ч�����飡");
				return false;			
			}	
		}
		<%if(Argument.getSyscontrolValue("TA_FLAG") == 1){%>
			if(form.is_bank_consign.value == 1)
			{
				if(!sl_checkDecimal(form.jg_min_subamount, "�����Ϲ����ٽ��", 13, 2, 1))		return false;
				if(!sl_checkDecimal(form.gr_min_subamount, "�����Ϲ����ٽ��", 13, 2, 1))		return false;
				if(!sl_checkDecimal(form.jg_min_bidsamount, "�����״��깺���ٽ��", 13, 2, 1))		return false;
				if(!sl_checkDecimal(form.gr_min_bidsamount, "�����״��깺���ٽ��", 13, 2, 1))		return false;
				if(!sl_checkDecimal(form.jg_min_appbidsamount, "����׷���깺��ͽ��", 13, 2, 1))		return false;
				if(!sl_checkDecimal(form.gr_min_appbidsamount, "����׷���깺��ͽ��", 13, 2, 1))		return false;
				if(!sl_checkDecimal(form.min_redeem_vol, "���ٱ����ݶ�", 13, 2, 1))		return false;
				if(form.large_redeem_flag.value == 1)
				{
					if(!sl_checkChoice(form.large_redeem_condition, "�޶�����ж�����"))			return false;
					if(!sl_checkDecimal(form.large_redeem_percent, "�����ܷݶ�İٷֱ�", 3, 2, 1))		return false;
					if(form.large_redeem_percent.value <= 0)
					{
						sl_alert("�����ܷݶ�İٷֱȲ���С�ڵ����㣡");
						form.large_redeem_percent.select();
						return false;
					}
					if(form.large_redeem_percent.value >100)
					{
						sl_alert("�����ܷݶ�İٷֱȲ��ܴ���100��");
						form.large_redeem_percent.select();
						return false;
					}
					if(!sl_checkChoice(form.large_redeem_deal, "�޶���ش���ʽ"))			return false;
				}
			}
		<%}%>
	}
	
	return sl_check_update();	
}

function showCnMoney(value)
{
	temp = value;
	if (trim(value) == ""){
		to_money_cn.innerText = "(Ԫ)";
	}else{
		to_money_cn.innerText = "(" + numToChinese(temp) + ")";
	}
}

function changeView(v){
	
	if(document.theform.intrust_flag1.value ==2){
		if(v==1){
			//document.getElementById('v_1').style.display = '';
			document.getElementById('v_2').style.display = '';
			//document.theform.qualified_money.value= <%=Format.formatMoney(qualified_money) %>;
			//document.theform.qualified_num.value= <%=qualified_num %>;
		}else{
			//document.getElementById('v_1').style.display = 'none';
			document.getElementById('v_2').style.display = 'none';
			//document.theform.qualified_money.value='' ;
			//document.theform.qualified_num.value='' ;		
		}
	}

}

function setTa(obj)
{
	<%if(Argument.getSyscontrolValue("TA_FLAG") == 1 ){%>
	if(obj.value == 1)
	{
		document.getElementById("is_ta1").style.display = "block";
		document.getElementById("is_ta2").style.display = "block";
		document.getElementById("is_ta3").style.display = "block";
	}
	else
	{
		document.getElementById("is_ta1").style.display = "none";
		document.getElementById("is_ta2").style.display = "none";
		document.getElementById("is_ta3").style.display = "none";
		document.theform.jg_min_subamount.value = "";
		document.theform.gr_min_subamount.value = "";
		document.theform.jg_min_bidsamount.value = "";
		document.theform.gr_min_bidsamount.value = "";
		document.theform.gr_min_appbidsamount.value = "";
		document.theform.jg_min_appbidsamount.value = "";
		document.theform.min_redeem_vol.value = "";
		document.theform.large_redeem_condition.options[1].selected="selected";
		document.theform.large_redeem_percent.value = "";
		document.theform.large_redeem_deal.options[1].selected="selected";
	}
	<%}%>
}

function setLarge(obj)
{
	if(obj.value == 1)
	{
		document.getElementById("tr1").style.display = "block";
		document.getElementById("tr2").style.display = "block";
		document.getElementById("tr3").style.display = "block";
	}else{
		document.getElementById("tr1").style.display = "none";
		document.getElementById("tr2").style.display = "none";
		document.getElementById("tr3").style.display = "none";
	}
}

function setFeeRate(productId, subproductId) {
	//popWindow("<%=request.getContextPath()%>/marketing/base/fee_rate_set.jsp?productId="+productId+"&subproductId="+subproductId);

	var url = "<%=request.getContextPath()%>/marketing/base/fee_rate_set.jsp?productId="+productId+"&subproductId="+subproductId;
	showModalDialog(url,oState, 'dialogWidth:600px;dialogHeight:500px;status:0;help:0');
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="sub_product_id" value="<%=sub_product_id%>">
<input type="hidden" name="productId" value="<%=productId%>">
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>">

<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>

	<div align="left" class="page-title page-title-noborder">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div>
		<table  border="0" width="100%" cellspacing="4" cellpadding="2" class="product-list">
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ���-->
				<td align="left" >
					<input type="text" name="productcode" readonly="readonly" class="edline" value="<%=productCode%>" onkeydown="javascript:setProduct(this.value);" size="50">
				</td>
			</tr>
			<tr>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ����-->
				<td  align="left">
					<input type="text" name="productName" readonly="readonly" class="edline" value="<%=productName%>" size="50">
				</td>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productShortName",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ���-->
				<td  align="left">
					<input type="text" name="productJC" readonly="readonly" class="edline" value="<%=productJC%>" size="30">
				</td>
			</tr>
			<tr>	
				<%if(!"1".equals(sub_product_name)){ %>
				<td align="right" width="120px">�Ӳ�Ʒ���� :&nbsp;&nbsp;</td>
				<td><input type="text" name="sub_product_name" readonly="readonly" class="edline" value="<%=sub_product_name %>"size="40"> </td>	
				<%}%>		
				<td align="right" width="120px"><%=LocalUtilis.language("class.preMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--Ԥ�ڷ��н��-->
				<td><input type="text" name="preMoney" readonly="readonly" class="edline" value="<%=Format.formatMoney(preMoney) %>"size="50"> </td>														
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.preNum",clientLocale)%> :&nbsp;&nbsp;</td><!--Ԥ�ڷ��з���-->
				<td><input type="text" name="preNum" readonly="readonly" class="edline" value="<%=preNum%>" size="50"></td>				
				<td align="right" width="120px"><%=LocalUtilis.language("class.minMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--��ͷ��н��-->
				<td><input type="text" name="minMoney" readonly="readonly" class="edline" value="<%=Format.formatMoney(minMoney) %>"size="30"></td>			
			</tr>
			<tr>
				<td align="right" width="120px">ԤԼ���� :&nbsp;&nbsp;</td>
				<td><input type="text" id="pre_max_rate" name="pre_max_rate" size="40" value="<%=pre_max_rate %>" style="text-align: right;"/>%</td>
				<td align="right" width="120px">��ͬ���ǰ׺ :&nbsp;&nbsp;</td>
				<td><input  type="text" name="pre_code" value="<%=pre_code %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td>
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.preStartDate",clientLocale)%> :&nbsp;&nbsp;</td><!--�ƽ���ʼ����-->
				<td>				
					<INPUT TYPE="text" NAME="preStartDate_picker" class=selecttext value="<%=Format.formatDateLine(preStartDate)%>" size="40" >
					<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.preStartDate_picker,theform.preStartDate_picker.value,this);" tabindex="14">
					<INPUT TYPE="hidden" NAME="preStartDate" value="<%=preStartDate%>">
				</td>			
	
				<td align="right" width="120px"><%=LocalUtilis.language("class.preEndDate",clientLocale)%> :&nbsp;&nbsp;</td><!--�ƽ���ֹ����-->
				<td>					
					<INPUT TYPE="text" NAME="preEndDate_picker" class=selecttext value="<%=Format.formatDateLine(preEndDate)%>" >
					<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.preEndDate_picker,theform.preEndDate_picker.value,this);" tabindex="14">
					<INPUT TYPE="hidden" NAME="preEndDate" value="<%=preEndDate%>">
				</td>					
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.periodUnit",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ����-->
				<td>
				<input type="text" name="periodUnit" size="50" readonly="readonly" class="edline" value="<%if(periodUnit!=null){
				if(periodUnit.intValue()!=0){
				%><%=valid_period%><%=Argument.getProductUnitName(periodUnit)
				%><%}else{
				%><%=Argument.getProductUnitName(periodUnit)%><%}}%>" size="30"></td>
				<td align="right" width="120px"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> :&nbsp;&nbsp;</td><!-- ���յȼ� -->
				<td >
					<SELECT name="risk_level" style="width:120px;" ><%=Argument.getProductRiskGrade(risk_level)%></SELECT></td> 		
			</tr>

			<tr style="display: none;">
				<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%> :&nbsp;&nbsp;</td><!--��������-->
				<td><div id="comboBoxParentTree"></div></td>

				<td align="right"><%=LocalUtilis.language("message.channelsRate",clientLocale)%> :&nbsp;&nbsp;</td><!--�������۷���-->
				<td><input type="text" id="fareRate" name="fareRate" value="<%=fareRate %>" style="text-align: right;"/>%</td>
			</tr>


		</table>
	<%if(intrust_flag1.intValue()== 2){ %>
	<br/>
	<div align="left" class="page-title page-title-noborder">
		<font color="#215dc6"><b>&nbsp;&nbsp;<%=LocalUtilis.language("intrsut.home.productrelease",clientLocale)%><%=LocalUtilis.language("message.set",clientLocale)%></b></font><!-- ��Ʒ�������� -->
	</div>
	<div  class="direct-panel">
		<fieldset style="border-width: 0px; border-color: #000000; ">   
		<p class="title-table">	<legend><font color="red"><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</font></legend></p><!-- ���漶�� -->
				<table border="0" width="100%" cellspacing="0" cellpadding="0" class="product-list">
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.incomeLevelAccountingUnit",clientLocale)%>:</td><!-- ���漶����㵥λ -->
						<td width="25%">
							<SELECT name="asfund_flag" style="width:120px;" ><!--											
								<OPTION  value="1" <%if(asfund_flag!=null&&asfund_flag.intValue()==1){ out.print("selected");}//�����¼%>  ><%=LocalUtilis.language("class.benefitRecord",clientLocale)%></OPTION>
								<OPTION  value="2"  <%if(asfund_flag!=null&&asfund_flag.intValue()==2){ out.print("selected");}//Ͷ����%> ><%=LocalUtilis.language("class.investor",clientLocale)%></OPTION>
								<OPTION  value="3" <%if(asfund_flag!=null&&asfund_flag.intValue()==3){ out.print("selected");}//����ͬ����%>  ><%=LocalUtilis.language("class.contractDefined",clientLocale)%></OPTION>
								--><%=Argument.getTableOptions2(3004,asfund_flag)%>
							</SELECT></td> 
						<td align="right" width="25%"><%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- ���漶�� -->
						<td width="25%">
							<SELECT name="gain_flag" style="width:120px;" ><!--											
								<OPTION value="1" <%if(gain_flag!=null&&gain_flag.intValue()==1){out.print("selected");}//��ʵ��������%>><%=LocalUtilis.language("class.actualReturnRate",clientLocale)%></OPTION>
								<OPTION value="2" <%if(gain_flag!=null&&gain_flag.intValue()==2){out.print("selected");}//����׼������%>><%=LocalUtilis.language("class.benchmarkReturnRate",clientLocale)%></OPTION>
								<OPTION value="3" <%if(gain_flag!=null&&gain_flag.intValue()==3){out.print("selected");}//��ʱ��ֶ�������%>><%=LocalUtilis.language("class.timeBlockReturnRate",clientLocale)%></OPTION>	
								--><%=Argument.getTableOptions2(3005,gain_flag)%>										
							</SELECT></td> 
					</tr>
				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<p class="title-table"><legend><font color="red"><%=LocalUtilis.language("class.openDate",clientLocale)%>:</font></legend></p><!-- ������ -->			
				<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.openIncome",clientLocale)%>:</td><!-- �Ƿ���Ὺ�������� -->
						<td colspan="3" width="75%">
							<SELECT name="open_gain_flag" style="width:120px;" >								
								<OPTION  value="1" <%if(open_gain_flag!=null&&open_gain_flag.intValue()==1){ out.print("selected");}%>  ><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
								<OPTION  value="2"  <%if(open_gain_flag!=null&&open_gain_flag.intValue()==2){ out.print("selected");}%> ><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
							</SELECT></td> 
					</tr>
				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<p class="title-table"><legend><font color="red"><%=LocalUtilis.language("class.customerHgtzr",clientLocale)%>:</font></legend></p><!-- �ϸ�Ͷ���� -->
				<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.naturalPersonNumControl",clientLocale)%>:</td><!-- ��Ȼ���������� -->
						<td colspan="3" width="75%">
							<%if(user_id.intValue() ==2 ){
							%>
								<SELECT name="qualified_flag" style="width:120px;" onchange="javascript:changeView(this.value);" >
									<OPTION  value="2"  selected><%=LocalUtilis.language("class.noControl",clientLocale)%></OPTION><!-- ������ -->
								</SELECT>
							<%}else{ %>
								<SELECT name="qualified_flag" style="width:120px;" onchange="javascript:changeView(this.value);" >
									<OPTION  value="2" <%if(qualified_flag.intValue()==2){ out.print("selected");}%>  ><%=LocalUtilis.language("class.noControl",clientLocale)%></OPTION><!-- ������ -->
									<OPTION  value="1" <%if(qualified_flag.intValue()==1){ out.print("selected");}%>  ><%=LocalUtilis.language("class.control",clientLocale)%></OPTION><!-- ���� -->									
								</SELECT>
							<%} %>
						</td> 
					</tr>
					<tr id="v_2" style="display:<%=styleStr %>;">
						<td align="right" width="25%"><%=LocalUtilis.language("class.naturalPersonNumControl",clientLocale)%>:</td><!-- ��Ȼ������ -->
						<td width="25%">
						   <input  type="text" name="qualified_num" value="<%=qualified_num %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
						<td align="right" width="25%"><%=LocalUtilis.language("class.amountQualfiedInvestorsLower",clientLocale)%>:</td><!-- �ϸ�Ͷ���˽������ -->
						<td width="25%">
						   <input  type="text" name="qualified_money" value="<%=Format.formatMoney(qualified_money) %>"  onkeyup="javascript:showCnMoney(this.value)" onkeydown="javascript:nextKeyPress(this)" size="20"> 
						<span id="to_money_cn" class="span" style="display: none;">&nbsp;(Ԫ)</span></td>
					</tr>
				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<p class="title-table"><legend><font color="red">������ɱ���:</font></legend></p><!-- ������ɱ��� -->			
				<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
					<tr>
						<%--td align="right" width="25%">������ɱ���(%):</td><!-- ������ɱ��� -->
						<td colspan="3" width="75%">
							<input  type="text" name="commission_rate" value="<%if (map_issue_crm!=null&&map_issue_crm.get("COMMISSION_RATE")!=null) out.print(Format.formatMoney0(((BigDecimal)map_issue_crm.get("COMMISSION_RATE")).doubleValue()*100)); %>">
							
						</td--%> 
						<td width="25%">&nbsp;</td>
						<td width="75%" colspan="3" align="left"><button type="button"  class="xpbutton6" onclick="javascript:setFeeRate(<%=productId%>,<%=sub_product_id%>)">��ɱ�������</button></td>
					</tr>
				</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;">
			<p class="title-table"><legend><font color="red">ÿ�տ�ԤԼʱ��:</font></legend>	</p>	
				<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list" >
					<tr>
						<td align="right" width="25%">ԤԼ��ʼʱ��:</td>
						<td width="25%">
							<input  type="text" name="pre_start_time" value="<%=pre_start_time %>"> �磺00:00
						</td> 
						<td align="right" width="25%">ԤԼ��ֹʱ��:</td>
						<td width="25%">
							<input  type="text" name="pre_end_time" value="<%=pre_end_time%>"> �磺23:59
						</td>
					</tr>
				</table>
		</fieldset>
	<%if(Argument.getSyscontrolValue("TA_FLAG") == 1 || user_id.intValue() == 2){%>
		<fieldset style="border-width: 0px; border-color: #000000;">
		<p class="title-table">	<legend><font color="red"><%=LocalUtilis.language("class.emissionBank",clientLocale)%>:</font></legend></p><!-- ���д��� -->
				<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
					<tr>
						<td align="right" width="25%"><%=LocalUtilis.language("class.whereEmissionsbank",clientLocale)%>:</td><!-- �Ƿ����д��� -->
						<td colspan="3" width="75%">
							<SELECT name="is_bank_consign" style="width:120px;" onclick="javascript:setTa(this);" >								
								<OPTION  value="1" <%if(is_bank_consign!=null&&is_bank_consign.intValue()==1){ out.print("selected");}%>><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
								<OPTION  value="2" <%if(is_bank_consign!=null&&is_bank_consign.intValue()==2){ out.print("selected");}%>><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
							</SELECT></td> 
					</tr>
				</table>
		</fieldset>
	<%} %>
	<fieldset style="border-width: 0px; border-color: #000000;display: <%if(Argument.getSyscontrolValue("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta1">
		<p class="title-table"><legend><font color="red"><%=LocalUtilis.language("message.subscription",clientLocale)%>:</font></legend></p><!-- �Ϲ� -->	
			<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
				<tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.orgSubscribeMoney",clientLocale)%>:</td><!-- �����Ϲ����ٽ�� -->
					<td width="25%">
					   <input  type="text" name="jg_min_subamount" value="<%=Format.formatMoney(jg_min_subamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.personalSubscribeMoney",clientLocale)%>:</td><!-- �����Ϲ����ٽ�� -->
					<td width="25%">
					   <input  type="text" name="gr_min_subamount" value="<%=Format.formatMoney(gr_min_subamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
				</tr>
			</table>
	</fieldset>
	<fieldset style="border-width: 0px; border-color: #000000;display: <%if(Argument.getSyscontrolValue("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta2">
		<p class="title-table"><legend><font color="red">�깺:</font></legend>	</p>		
			<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
				<tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.firstApplyMoney",clientLocale)%>:</td><!-- �����״��깺���ٽ�� -->
					<td width="25%">
					   <input  type="text" name="jg_min_bidsamount" value="<%=Format.formatMoney(jg_min_bidsamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.firstPersonalApplyMoney",clientLocale)%>:</td><!-- �����״��깺���ٽ�� -->
					<td width="25%">
					   <input  type="text" name="gr_min_bidsamount" value="<%=Format.formatMoney(gr_min_bidsamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
				</tr>
                            <tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.orgSubscribeMinMoney",clientLocale)%>:</td><!-- ����׷���깺��ͽ�� -->
					<td width="25%">
					   <input  type="text" name="jg_min_appbidsamount" value="<%=Format.formatMoney(jg_min_appbidsamount) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.PersonalSubscribeMinMoney",clientLocale)%>:</td><!-- ����׷���깺��ͽ�� -->
					<td width="25%">
					   <input  type="text" name="gr_min_appbidsamount" value="<%=Format.formatMoney(gr_min_appbidsamount) %>" onkeydown="javascript:nextKeyPress(this)" size="20"> 
					</td>
				</tr>
			</table>
	</fieldset>
	<fieldset style="border-width: 0px; border-color: #000000;display: <%if(Argument.getSyscontrolValue("TA_FLAG") == 1 && (is_bank_consign!=null&&is_bank_consign.intValue()==1)){ out.print("");} else { out.print("none");}%>;"  id="is_ta3">
		<p class="title-table"><legend><font color="red"><%=LocalUtilis.language("message.redemption",clientLocale)%>:</font></legend> </p>	<!-- ��� -->		
			<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
				<tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.leastAmount",clientLocale)%>:</td><!-- ���ٱ����ݶ� -->
					<td width="25%">
					   <input  type="text" name="min_redeem_vol" value="<%=Format.formatMoney(min_redeem_vol) %>" onkeydown="javascript:nextKeyPress(this);" size="20"></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.whetherForcedRedeem",clientLocale)%>:</td><!-- �Ƿ����ǿ����� -->
					<td width="25%">
					  	<select name="coerce_redeem_flag" onkeydown="javascript:nextKeyPress(this);" style="width: 120px;" >
							<option value="1" <%=coerce_redeem_flag.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
							<option value="2" <%=coerce_redeem_flag.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.hugeRedemptive",clientLocale)%>:</td><!-- �Ƿ���޶���� -->
					<td width="25%">
						<select  name="large_redeem_flag" onkeydown="javascript:nextKeyPress(this);" style="width: 120px;" onclick="javascript:setLarge(this);">
							<option value="1" <%=large_redeem_flag.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("message.yes",clientLocale)%></OPTION><!-- �� -->
							<option value="2" <%=large_redeem_flag.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("message.no3",clientLocale)%></OPTION><!-- �� -->
						</select>
					</td>
					<td align="right" width="25%" id="tr1" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;"><font color="red">*</font><%=LocalUtilis.language("class.hugeRedemptiveCondition",clientLocale)%>:</td><!-- �޶�����ж����� -->
					<td width="25%" id="tr2" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;">
					    <select  style="width: 120px;" name="large_redeem_condition" onkeydown="javascript:nextKeyPress(this)">
							<option>��ѡ��</option>
							<option value="1" <%=large_redeem_condition.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("class.redemptiveShareJudgment",clientLocale)%></option><!-- ������طݶ��ж� -->
							<option value="2" <%=large_redeem_condition.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("class.totalRedemptionShare",clientLocale)%></option><!-- ������طݶ��ж� -->
						</select>
					</td>
				</tr>
				<tr id="tr3" style="display: <%=large_redeem_flag.intValue() == 2 ? "none" : ""%>;">
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.percentageTotalShare",clientLocale)%>:</td><!-- �����ܷݶ�İٷֱ� -->
					<td width="25%">
					   <input  type="text" name="large_redeem_percent" value="<%=Utility.trimNull(Format.formatMoney(large_redeem_percent.multiply(new BigDecimal(100)).doubleValue(), 2))%>" onkeydown="javascript:nextKeyPress(this);" size="20"><font color="blue">(%)</font></td> 
					<td align="right" width="25%"><font color="red">*</font><%=LocalUtilis.language("class.hugeRedemptiveApproach",clientLocale)%>:</td><!-- �޶���ش���ʽ -->
					<td width="25%">
					  	<select  style="width: 120px;" name="large_redeem_deal" onkeydown="javascript:nextKeyPress(this)">
							<option>��ѡ��</option>
							<option value="1" <%=large_redeem_deal.intValue() == 1 ? "selected" : ""%>><%=LocalUtilis.language("class.postpone",clientLocale)%></option><!-- ˳�� -->
							<option value="2" <%=large_redeem_deal.intValue() == 2 ? "selected" : ""%>><%=LocalUtilis.language("class.applicationDateClient",clientLocale)%></option><!-- ���ݿͻ��������ݴ��� -->
						</select>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="border-width: 0px; border-color: #000000;" id="other2">
	<p class="title-table">	<legend><font color="red">����:</font></legend>	</p>		
			<table border="0" width="100%" cellspacing="0" cellpadding="0"  class="product-list">
				<tr>
					<td align="right" width="25%">��ͬ��ӡģ��:</td><!-- ��ͬ��ӡģ�� -->
					<td width="25%">
					   <SELECT name="template_id" style="width:120px;" ><option value="0">--��ѡ��--</option><%=Argument.getPrintTemplate("RGHT",""+template_id) %></SELECT></td> 
					<td align="right" width="25%">��СԤԼ���(Ԫ):</td><!-- ��СԤԼ��� -->
					<td>
					   <input  type="text" name="pre_min_amount" value="<%=pre_min_amount %>" onkeydown="javascript:nextKeyPress(this);" size="20">
					</td>
				</tr>
                <tr>
					<td align="right" width="25%">�ۼ��Ϲ����ѽ��(Ԫ):</td><!-- �Ϲ����ѽ�� -->
					<td colspan="3">
					   <input  type="text" name="up_to_show" value="<%=up_to_show %>" onkeydown="javascript:nextKeyPress(this)" size="20"> ������Ʒ���Ϲ�����ۼƳ�����ֵʱ��ʾ
					</td>
				</tr>
			</table>
		</fieldset></div><%} %>
<br/>
</form>
<div align="right">
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<p>
<%
product.remove();
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
