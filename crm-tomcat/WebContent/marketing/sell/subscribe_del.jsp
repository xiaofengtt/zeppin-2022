<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//查询条件
String product_code= Utility.trimNull(request.getParameter("productid"));
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String query_contract_bh= Utility.trimNull(request.getParameter("query_contract_bh"));
Integer pre_flag=Utility.parseInt(request.getParameter("pre_flag"),new Integer(0));

//获得对象及结果集
ContractLocal contract = EJBFactory.getContract();
ContractVO vo = new ContractVO();
CustomerLocal customer = EJBFactory.getCustomer();//客户
CustomerVO cust_vo = new CustomerVO();
SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

//获得主键
String[] checked_serial_no = request.getParameterValues("serial_no");
int t_serial_no;

//声明结果集对象
List rsList_contract = null;
Map map_contract = null;
Integer check_flag = new Integer(0);
Integer r_product_id = new Integer(0);
BigDecimal rg_money = new BigDecimal(0);
Integer link_man = new Integer(0);

//团队信息
Integer team_id = new Integer(-1);
BigDecimal quota_money = new BigDecimal(0);
BigDecimal already_sale = new BigDecimal(0);
Integer already_qualified_num = new Integer(0);
Integer serialNo = new Integer(0);

//客户信息
String message = "";
Integer cust_id = new Integer(0);
Integer cust_type = new Integer(0);
Integer sub_product_id = new Integer(0);
if(checked_serial_no!= null){
	for(int i=0;i<checked_serial_no.length;i++){
		t_serial_no = Utility.parseInt(checked_serial_no[i], 0);
		
		if(t_serial_no!=0){
			vo.setSerial_no(new Integer(t_serial_no));
			vo.setInput_man(input_operatorCode);

			rsList_contract = contract.load(vo);
			
			if(rsList_contract.size()>0){
				map_contract = (Map)rsList_contract.get(0);
			}
		
			if(map_contract!=null){
				cust_id = Utility.parseInt(Utility.trimNull(map_contract.get("CUST_ID")), new Integer(0));
				check_flag = Utility.parseInt(Utility.trimNull(map_contract.get("CHECK_FLAG")), new Integer(0));	
				rg_money = Utility.parseDecimal(Utility.trimNull(map_contract.get("RG_MONEY")),new BigDecimal(0));
				r_product_id = Utility.parseInt(Utility.trimNull(map_contract.get("PRODUCT_ID")), new Integer(0));
				link_man = Utility.parseInt(Utility.trimNull(map_contract.get("LINK_MAN")), new Integer(0));
				sub_product_id = Utility.parseInt(Utility.trimNull(map_contract.get("SUB_PRODUCT_ID")), new Integer(0));	
				
				team_id = Utility.parseInt(Utility.trimNull(Argument.getTeam(link_man)),new Integer(-1));
				//客户信息显示
				if(cust_id.intValue()>0){
					List rsList_cust = null;
					Map map_cust = null;	
					
					//客户单个值		
					cust_vo.setCust_id(cust_id);
					cust_vo.setInput_man(input_operatorCode);
					rsList_cust = customer.listByControl(cust_vo);
							
					if(rsList_cust.size()>0){
						map_cust = (Map)rsList_cust.get(0);
					}

					if(map_cust!=null){		
						cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
					}
				}
				//查看用户所在团队信息
				salevo.setTeamID(team_id);
				salevo.setProductID(r_product_id);
				salevo.setSub_product_id(sub_product_id);
				List saleList = sale_parameter.queryQuota(salevo,input_operatorCode);
				Map map_sale = null;
				if(saleList.size()>0){
					map_sale = (Map)saleList.get(0);
				}
				if(map_sale != null){
					serialNo = Utility.parseInt(Utility.trimNull(map_sale.get("SERIAL_NO")), new Integer(0));
					quota_money = Utility.parseDecimal(Utility.trimNull(map_sale.get("QUOTAMONEY")),new BigDecimal(0));
					already_sale = Utility.parseDecimal(Utility.trimNull(map_sale.get("ALREADYSALE")),new BigDecimal(0));
					already_qualified_num = Utility.parseInt(Utility.trimNull(map_sale.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
				}
				if(cust_type.intValue() == 1 && rg_money.compareTo(new BigDecimal(3000000)) == -1){
					int al_qualified_num = already_qualified_num.intValue() + 1;
					already_qualified_num = new Integer(al_qualified_num);
				};	
				if(check_flag.intValue()==1){
					already_sale = already_sale.subtract(rg_money);					
					salevo.setSerial_NO(serialNo);
					salevo.setTeamID(team_id);
					salevo.setProductID(r_product_id);
					salevo.setSerial_no_subscribe(new Integer(0));
					salevo.setAlreadysale(already_sale);
					salevo.setAlready_qualified_num(already_qualified_num);

					salevo.setSub_product_id(sub_product_id);
					sale_parameter.modiAlreadysale(salevo,input_operatorCode);
					contract.delete(vo);
					message = "删除成功";
				}else{
					message = LocalUtilis.language("message.cannotDeleted",clientLocale);
				}
			}	
		}
	}
}
%>
<%contract.remove();
sale_parameter.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
var url = "subscribe_list.jsp?productid=<%=product_code%>&product_id=<%=product_id%>&cust_name=<%=cust_name%>&card_id=<%=card_id%>&query_contract_bh=<%=query_contract_bh%>&pre_flag=<%=pre_flag%>";
sl_alert('<%=message%>!');
location.replace(url);
</SCRIPT>


