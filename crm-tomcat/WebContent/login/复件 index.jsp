<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK" import="enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%
//���ػ���Ϣ
Integer languageFlag = Utility.parseInt(Utility.trimNull(session.getAttribute("languageFlag")),Integer.valueOf("0"));
String languageType = "auto";
Locale clientLocale = null;

//�Ƿ�webcall����
Integer isWebcall = Utility.parseInt(Utility.trimNull(session.getAttribute("isWebcall")),Integer.valueOf("0"));
//no_callcenter=1��ʹ��callcenter
Integer no_callcenter = Utility.parseInt(Utility.trimNull(session.getAttribute("no_callcenter")),Integer.valueOf("0"));

//�������Ի���
if(languageFlag.intValue()==0){
	clientLocale = request.getLocale();//�õ����ػ���Ϣ
	languageType = clientLocale.getLanguage()+"_"+clientLocale.getCountry();
}
else if(languageFlag.intValue()==1){
	clientLocale = new Locale("zh","CN");
	languageType = "zh_CN";
}
else if(languageFlag.intValue()==2){
	clientLocale = new Locale("en","US");
	languageType = "en_US";
}
Integer user_id= (Integer)application.getAttribute("USER_ID");
//���ú������Ļ���
String callcenter_mode = "";
if(user_id.intValue() == 15){
	callcenter_mode = "_jianxin";
}

OperatorLocal oplocal = EJBFactory.getOperator();  
InputMan input_operator = (InputMan)session.getAttribute("OPERATOR");
String DB00001="1";
Integer dbdriverflag = (Integer)session.getAttribute("DBDRIVER"); 
if(dbdriverflag!= null)
	DB00001 = dbdriverflag.toString();

int status=11;

//����Ա��δ��¼
if (input_operator == null) 
    throw new BusiException(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"��");
String input_operatorName = input_operator.getOp_name();
if (input_operatorName == null)
    throw new BusiException(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"��");
Integer input_operatorCode = input_operator.getOp_code();
if (input_operatorCode == null)
    throw new BusiException(enfo.crm.tools.LocalUtilis.language("index.msg.operatorUnknown", clientLocale)+"��");
   
String parent_id = Utility.trimNull(request.getParameter("parent_id"),"11");

String user_logo_top="";
 
if(user_id!=null){
	 user_logo_top=request.getContextPath()+Argument.getUserLogoInfo(user_id.intValue())[1];
}

//���ò�Ʒid
Integer session_product_id=(Integer)session.getAttribute("overall_product_id");
Integer overall_product_id=enfo.crm.tools.Utility.parseInt(session_product_id,new Integer(0));
if(overall_product_id==null || overall_product_id.intValue()==0){
	Integer get_product_id=input_operator.getProductidByOpcode(input_operatorCode);
	overall_product_id=get_product_id;
	session.setAttribute("overall_product_id",get_product_id);
}

//��ȡ����Ա���CC��Ϣ
String extension = "";
String recordExtension = "";
Integer depart = new Integer(0);
Integer role_id = new Integer(0);
String role_name = "";
TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
vo.setManagerid(input_operatorCode);
String[] totalColumn = new String[0];
IPageList pageList =tcustmanagers_Bean.pagelist_query(vo,totalColumn,1,-1);

List depList = tcustmanagers_Bean.operator_query(vo);
Map depMap = null;
List rsList = pageList.getRsList();
Map operatorMap = null;

if(rsList!=null&&rsList.size()==1){
    operatorMap = (Map)rsList.get(0);
    extension = Utility.trimNull(operatorMap.get("Extension"));
    recordExtension = Utility.trimNull(operatorMap.get("RecordExtension"));
}

if(depList!=null&&depList.size()==1){
	depMap = (Map)depList.get(0);
	depart = Utility.parseInt(Utility.trimNull(depMap.get("DEPART_ID")), new Integer(0));
	role_id = Utility.parseInt(Utility.trimNull(depMap.get("ROLE_ID")), new Integer(0));
}

DepartmentLocal departmentLocal = EJBFactory.getDepartment();
DepartmentVO departVO = new DepartmentVO();

departVO.setDepart_id(depart);
List departList = departmentLocal.listProcAll(departVO);
Map departMap = null;
String depart_name = "";

if(departList!=null&&departList.size()==1){
	departMap = (Map)departList.get(0);
	depart_name = Utility.trimNull(departMap.get("DEPART_NAME"));
}

RoleLocal roleLocal = EJBFactory.getRole();
RoleVO role_vo = new RoleVO();
role_vo.setRole_id(role_id);
List role_list = roleLocal.listBySql(role_vo);
Map role_map = null;

if(role_list!=null&&role_list.size()==1){
	role_map = (Map)role_list.get(0);
	role_name = Utility.trimNull(role_map.get("ROLE_NAME"));
}

//cc_status ��ϯ�����Ϣ״̬ 0��ȫ��OK��1���ֻ���δ���ã�2��¼����δ���ã�3���ֻ��ź�¼���ֻ��Ŷ�Ϊ���� 4������
if(!"".equals(extension)&&!"".equals(recordExtension)){
    session.setAttribute("cc_status","0");
    session.setAttribute("extension",extension);
    session.setAttribute("recordExtension",recordExtension);
}else if("".equals(extension)&&!"".equals(recordExtension)){
    session.setAttribute("cc_status","1");
}else if(!"".equals(extension)&&"".equals(recordExtension)){
    session.setAttribute("cc_status","2");
}else if("".equals(extension)&&"".equals(recordExtension)){
    session.setAttribute("cc_status","3");
}else{
    session.setAttribute("cc_status","4");
}
String cc_status = (String)session.getAttribute("cc_status");
extension = (String)session.getAttribute("extension");
recordExtension = (String)session.getAttribute("recordExtension");
String homepage_style="style='background: url("+request.getContextPath()+"/styles/images/sitearea-nav.jpg) repeat-x 0 -100px; COLOR:white;;'";
String selected_model_color="<font color='white'>";
Integer LOG0001=(Integer)session.getAttribute("LOG0001");
String mainPageName = "main.jsp";
if(Argument.getSyscontrolValue("USERNEWPORTAL")==1){
	mainPageName = "main_3.jsp";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<style>
	.home-banner1 {    
	  	background-image:url('/images/bg-top-white.jpg');   
		background-repeat: no-repeat; 
	
	caption { 
padding: 0 0 5px 0; 
width: 700px; 
font: italic 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 
text-align: right; 
} 

th { 
font: bold 11px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 
color: #4f6b72; 
border-right: 1px solid #C1DAD7; 
border-bottom: 1px solid #C1DAD7; 
border-top: 1px solid #C1DAD7; 
letter-spacing: 2px; 
text-transform: uppercase; 
text-align: left; 
padding: 6px 6px 6px 12px; 
background: #CAE8EA  no-repeat; 
} 
/*power by www.winshell.cn*/ 
th.nobg { 
border-top: 0; 
border-left: 0; 
border-right: 1px solid #C1DAD7; 
background: none; 
} 

td { 
border-right: 1px solid #C1DAD7; 
border-bottom: 1px solid #C1DAD7; 
background: #fff; 
font-size:11px; 
padding: 6px 6px 6px 12px; 
color: #4f6b72; 
} 
/*power by www.winshell.cn*/ 

td.alt { 
background: #F5FAFA; 
color: #797268; 
} 

th.spec { 
border-left: 1px solid #C1DAD7; 
border-top: 0; 
background: #fff no-repeat; 
font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 
} 

th.specalt { 
border-left: 1px solid #C1DAD7; 
border-top: 0; 
background: #f5fafa no-repeat; 
font: bold 10px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 
color: #797268; 
} 
	}
	</style>
	<title><%=application.getAttribute("APPLICATION_NAME")%></title> 
	<meta http-equiv="Content-Type" content="text/html;charset=GBK"/> 
	<meta http-equiv="Cache-Control" content="no-store"/> 
	<meta http-equiv="Pragma" content="no-cache"/>   
	<meta http-equiv="Expires" content="0"/>
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css"></link>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/includes/default.css"></link>	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/index.css"></link>
	<style type="text/css">
		a#close{ padding:0 5px;background:#fff;filter:Alpha(opacity=40); opacity:0.4; right:6px; top:6px; font-family:Verdana, Arial, Helvetica, sans-serif; font-weight:bold; color:#ccc; font-size:18px; text-align:center; position:absolute; text-decoration:none;}
		a#close:hover{ color:#000;}
	</style>
	<script type="text/javascript" src='<%=request.getContextPath()%>/widgets/ext/ext-base.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/widgets/ext/ext-all.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js'></script>
	<script language=javascript>
		var j$ = jQuery.noConflict();
	</script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/prototype.lite.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/moo.fx.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/accordion.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/index_<%=languageType%>.js'></script>
<!--	<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/ext_index.js'></script>-->
	<script type="text/javascript" src='<%=request.getContextPath()%>/scripts/register.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/includes/callcenter/callcenter<%=callcenter_mode%>.js'></script>
	<script language="vbscript" src='<%=request.getContextPath()%>/includes/callcenter/callcenter.vbs'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/loginService.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/menuService.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<%if(user_id.intValue() == 15){ %>
    <%@ include file="/includes/callcenter/callcenter.inc" %>
<%}%>

	<script language="javascript"> 
	    var menuStr = "";//�����������ɵ��ַ���
	    var tree,root;
	    var pre_menu_id = 11;
	    var busyorfree;
		var leavingorback;
		//var myPhone;

		var op_code = '<%=input_operatorCode%>';
		var extension = '<%=extension%>';
		var recordExtension = '<%=recordExtension%>';
    	function logOut(){
    		//�Ƿ��˳�ϵͳ
    		if(confirm("<%=LocalUtilis.language("index.msg.exitSystem",clientLocale)%> ��")){
    			quitCallCenter();
    			loginService.doLogOut(<%=input_operatorCode%>,{callback: function(data){
    				if(data == "0"){
    					//ע��ʧ��,δ�ܳɹ��˳�ϵͳ��������!
    					alert("<%=LocalUtilis.language("index.msg.cancelFail",clientLocale)%> ");
    					return;
    				}
    				else if(data == "1"){
    					location = "<%=request.getContextPath()%>/login/logout.jsp";
    				}
    			}});			
    		}
    		else{
    			return;
    		}
    	}
    	function quitCallCenter(){
			 <%if(Argument.getSyscontrolValue("DT_CALL")!=0 && no_callcenter.intValue() != 1 && cc_status.equals("0")){%>
				if(extension != 0){
					disconnect2server(op_code,extension);
				}
			<%}%>
		}
    	function showPasswordDialog()//�޸��������
    	{
    		//�޸ĳɹ�
    		if(showModalDialog('<%=request.getContextPath()%>/login/password.jsp', '', 'dialogWidth:320px;dialogHeight:270px;status:0;help:0')!= null)
    			alert('<%=LocalUtilis.language("message.updateOk",clientLocale)%> !');
    	}
    	
    	function telMatch(){//�绰ƥ��
    		showModalDialog('<%=request.getContextPath()%>/callcenter/callingMatch.jsp', '', 'dialogWidth:950px;dialogHeight:350px;status:0;help:0')
    	}
    	
    	function newInfo()
    	{
    		if(showModalDialog('<%=request.getContextPath()%>/system/systemparam/message_update.jsp', '', 'dialogWidth:680px;dialogHeight:548px;status:0;help:0') != null)
    		{
    			//����ɹ�
    			alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ��");
    			location.reload(); 
    		}
    	}

		//���ټ����ͻ���Ϣ
		function quickSearchCustomer()
		{
			showModalDialog('<%=request.getContextPath()%>/client/clientinfo/quickSearchCustomer.jsp', '', 'dialogWidth:950px;dialogHeight:500px;status:0;help:0')
		}
    
    	function loadMenu(url,menuid,menuinfo,menuurl){
    		loadMain(url);	
    	}
    	
    	function loadMenuInfo(op_code,menu_id,parent_id){    	
    	    var texta,languageType,languageFlag;
    	    document.getElementById("parent_id").value = menu_id;
    	    document.getElementById("td_"+pre_menu_id).className = "nav";
    	    texta = document.getElementById("a_"+pre_menu_id).innerHTML;
    	    document.getElementById("a_"+pre_menu_id).innerHTML = texta.substring(texta.indexOf(">")+1,texta.lastIndexOf("<"));
    	    document.getElementById("td_"+menu_id).className = "selectnav";
    	    texta = document.getElementById("a_"+menu_id).innerHTML;
    	    document.getElementById("a_"+menu_id).innerHTML = "<font color='#ffffff'>"+texta.substring(texta.lastIndexOf(">"),texta.length);
    	    var dt_call = document.getElementById("dt_call").value;//��ú������ı�־

			//������Ի���
			languageType = document.getElementById("languageType").value;
			if(languageType=="zh_CN"){
				languageFlag =1;
			}
			else if(languageType=="en_US"){
				languageFlag =2;
			};
			
    	    pre_menu_id = menu_id;
    	    Ext.BLANK_IMAGE_URL = "<%=request.getContextPath()%>/images/s.gif";
    	    if(tree==null){
        	    tree = new Ext.tree.TreePanel('home-column-left', { 
        	        animate: true,
            	    enableDD:true,
            	    border: false,
            	   	rootVisible: true, //�Ƿ���ʾ���ڵ�
            	    containerScroll: true
        	    });
    	    }
    	    
    	    if(root==null){
    	    	//�ͻ�����
        	    root = new Ext.tree.TreeNode({
            	    text: "<span style='font-size:10px'><%=LocalUtilis.language("index.menu.customerManagement",clientLocale)%></span> ",
            	    draggable:true,
            	    id:'root'
       		    });
            }
                	    
    	    while(root.childNodes.length>0){
    	        root.removeChild(root.firstChild);   
    	    }
    	    
    	    layout.getRegion('west').show();  
    	    if(dt_call!=0){
    	    	layout.getRegion('south').show(); 
    	    }    	    
    	    layout.getRegion('west').expand(); 
    	    if(menu_id==1)
            	root.setText("<%=LocalUtilis.language("index.menu.systemConfig",clientLocale)%> ");//ϵͳ���ù���
            else if(menu_id==2)
            	root.setText("<%=LocalUtilis.language("index.menu.customerManagement",clientLocale)%> ");//�ͻ�����
            else if(menu_id==3)
            	root.setText("<%=LocalUtilis.language("index.menu.marketingManagement",clientLocale)%> ");//Ӫ������
            else if(menu_id==4)
            	root.setText("<%=LocalUtilis.language("index.menu.managerManagement",clientLocale)%> ");//�������
            else if(menu_id==5)
            	root.setText("<%=LocalUtilis.language("index.menu.callcenter",clientLocale)%> ");//��������
            else if(menu_id==6)
            	root.setText("<%=LocalUtilis.language("message.custAnalysis",clientLocale)%> ");//�ͻ�����
		    else if(menu_id==7)
            	root.setText("��ͬ���� ");//��ͬ����
            if(menu_id==11){
                root.setText("<%=LocalUtilis.language("index.menu.myDesk",clientLocale)%> ");  //�ҵĹ���̨
                //layout.getRegion('west').hide(); 
                //layout.getRegion('south').hide(); 
                 <%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>
                 	if(init_success != 1)
                		  setTimeout("loadMain('<%=request.getContextPath()%>/login/<%=mainPageName%>?display_flag=1&timer_id='+timer_id)",1000);
                	else
                			loadMain('<%=request.getContextPath()%>/login/<%=mainPageName%>?display_flag=1&timer_id='+timer_id);
                <%}else{%>
                	loadMain('<%=request.getContextPath()%>/login/<%=mainPageName%>?display_flag=1');
              	<%}%> 		 
            } else {
	   	    	loadMain('<%=request.getContextPath()%>/login/main.jsp?display_flag=0');
			}

			//�������
			if(menu_id == 99){
				root.setText("�������");
				layout.getRegion('west').hide();
				loadMain('<%=Utility.trimNull(application.getAttribute("INTRUST_ADDRESS"))%>/k2/logintrust.jsp?intrust_flag=1&uid=<%=input_operator.getLogin_user()%>');
			}
			
			if(menu_id=='w'){ 
				DWREngine.setAsync(false);
	    	    menuService.getMenuList(op_code,menu_id,parent_id,languageFlag,menuCallBack);
	    	    menuinfo1 = eval(menuStr);
	    	    for(i=0;i<menuinfo1.length;i++){ 
	    	        	if(menuinfo1[i].BOTTOM_FLAG==0){
		    	            tempnode = new Ext.tree.TreeNode({ 
				            text: menuinfo1[i].MENU_NAME, 
				            draggable:true
			            });
			            root.appendChild(tempnode);
	    	            menuService.getMenuList(menuinfo1[i].OP_CODE,'',menuinfo1[i].MENU_ID,languageFlag,menuCallBack);  
	    	            menuinfo2 = eval(menuStr);
	    	            for(k=0;k<menuinfo2.length;k++){
	    	                if(menuinfo2[k].BOTTOM_FLAG==0){
	    	                    tempnode1 = new Ext.tree.TreeNode({ 
				                    text: menuinfo2[k].MENU_NAME, 
				                    draggable:false
			                    });
			                    tempnode.appendChild(tempnode1);
	    	                    menuService.getMenuList(menuinfo2[k].OP_CODE,'',menuinfo2[k].MENU_ID,languageFlag,menuCallBack);  
	    	                    menuinfo3 = eval(menuStr);
	    	                    for(j=0;j<menuinfo3.length;j++){
	    	                        tempnode2 = new Ext.tree.TreeNode({ 
				                    text: menuinfo3[j].MENU_NAME, 
				                    draggable:false,
				                    id: menuinfo3[j].MENU_ID+'$'+menuinfo3[j].URL,
				                    listeners:{'click': function(){checkMenu(this)}}
			                    });  
			                    tempnode1.appendChild(tempnode2);
	    	                    }
	    	                }else if(menuinfo2[k].PARENT_ID==menuinfo1[i].MENU_ID){
	    	                    tempnode1 = new Ext.tree.TreeNode({ 
				                    text: menuinfo2[k].MENU_NAME, 
				                    draggable:false,
				                    id: menuinfo2[k].MENU_ID+'$'+menuinfo2[k].URL,
				                    listeners:{'click': function(){checkMenu(this)}}
			                    });
			                    tempnode.appendChild(tempnode1);
	    	                }
	    	            }           
	    	        }else{
		    	        tempnode = new Ext.tree.TreeNode({ 
							text: menuinfo1[i].MENU_NAME, 
							draggable:true,
							id: menuinfo1[i].MENU_ID+'$'+menuinfo1[i].URL,
							listeners:{'click': function(){checkMenu(this)}}
						});
			            root.appendChild(tempnode);
	    	        }
	    	    }
	    	    tree.setRootNode(root);
				//tree.rootVisible = false;
	    	    tree.render();
	    	   	root.expand(true,true);
				loadMain('<%=request.getContextPath()%>/wiki/search_all.jsp');
			}else{
            	if(menu_id == 11){
					menu_id = "w";//�ƻ���ľ
				}
	    	    DWREngine.setAsync(false);
	    	    menuService.getMenuList(op_code,menu_id,parent_id,languageFlag,menuCallBack);
	    	    menuinfo1 = eval(menuStr);
	    	    for(i=0;i<menuinfo1.length;i++){   
	    	        if(menuinfo1[i].BOTTOM_FLAG==0){
	    	            
	    	            tempnode = new Ext.tree.TreeNode({ 
				            text: menuinfo1[i].MENU_NAME, 
				            draggable:true
			            });
			            root.appendChild(tempnode);
	    	            menuService.getMenuList(menuinfo1[i].OP_CODE,'',menuinfo1[i].MENU_ID,languageFlag,menuCallBack);  
	    	            menuinfo2 = eval(menuStr);
	    	            for(k=0;k<menuinfo2.length;k++){
	    	                if(menuinfo2[k].BOTTOM_FLAG==0){
	    	                    tempnode1 = new Ext.tree.TreeNode({ 
				                    text: menuinfo2[k].MENU_NAME, 
				                    draggable:false
			                    });
			                    tempnode.appendChild(tempnode1);
	    	                    menuService.getMenuList(menuinfo2[k].OP_CODE,'',menuinfo2[k].MENU_ID,languageFlag,menuCallBack);  
	    	                    menuinfo3 = eval(menuStr);
	    	                    for(j=0;j<menuinfo3.length;j++){
	    	                        tempnode2 = new Ext.tree.TreeNode({ 
				                    text: menuinfo3[j].MENU_NAME, 
				                    draggable:false,
				                    id: menuinfo3[j].MENU_ID+'$'+menuinfo3[j].URL,
				                    listeners:{'click': function(){checkMenu(this)}}
			                    });  
			                    tempnode1.appendChild(tempnode2);
	    	                    }
	    	                }else if(menuinfo2[k].PARENT_ID==menuinfo1[i].MENU_ID){
	    	                    tempnode1 = new Ext.tree.TreeNode({ 
				                    text: menuinfo2[k].MENU_NAME, 
				                    draggable:false,
				                    id: menuinfo2[k].MENU_ID+'$'+menuinfo2[k].URL,
				                    listeners:{'click': function(){checkMenu(this)}}
			                    });
			                    tempnode.appendChild(tempnode1);
	    	                }
	    	            }           
	    	        }else{
	    	            //alert(menuinfo1[i].MENU_INFO);  
		    	        tempnode = new Ext.tree.TreeNode({ 
							text: menuinfo1[i].MENU_NAME, 
							draggable:true,
							id: menuinfo1[i].MENU_ID+'$'+menuinfo1[i].URL,
							listeners:{'click': function(){checkMenu(this)}}
						});
			            root.appendChild(tempnode);  
	    	        }
	    	    }
	    	    tree.setRootNode(root);
				//tree.rootVisible = true;
				
	    	    tree.render();	    	    
				if(menu_id == "w"){
					root.expand(true,true);
				}
				else{
					root.expand();
				}
			}
			
    	}
    	
    	function menuCallBack(data){
    	    menuStr = data;  
    	}
    	
    	function checkMenu(obj){
    	    menu_id = obj.id.split('$')[0];
    	    menu_url = obj.id.split('$')[1];
    	    SuffixIndex = menu_url.lastIndexOf(".");
			URLSuffix = "";
			if(menu_url != "" && SuffixIndex != -1){
				URLSuffix = menu_url.substring(SuffixIndex,menu_url.length);
			}else{
			    loadMain('<%=request.getContextPath()%>/login/main.jsp?display_flag=0'); 
			    //���� �ò˵���δ�������ӣ�����ϵ����Ա����
			    Ext.MessageBox.alert('<%=LocalUtilis.language("message.warn",clientLocale)%>','<%=LocalUtilis.language("index.msg.menuFail",clientLocale)%> ');
			    return false;
			}
            if(".CLL"==(URLSuffix.toLocaleUpperCase())){
			   menu_url="<%=request.getContextPath()%>/webreport/init.jsp?filename=http://<%=request.getServerName()%>:<%=request.getServerPort()%>/webreport/Cells"+menu_url;
			}
			else if("http:"==menu_url.substring(0,5)){
				menu_url=menu_url;
	    	}
			else{
			   if(menu_url.indexOf('?')<=0){
				   menu_url="<%=request.getContextPath()%>"+menu_url+"?menu_id="+menu_id+"&first_flag=1&parent_id=&log=<%=LOG0001.toString()%>";
			   }else{
			   	   menu_url="<%=request.getContextPath()%>"+menu_url+"&menu_id="+menu_id+"&first_flag=1&parent_id=&log=<%=LOG0001.toString()%>";
			   }
		  	}					
            loginService.alertString(menu_id,'<%=LOG0001.toString()%>',<%=input_operatorCode%>,{callback: function(data){loadMain(menu_url);}});
    	}
    	
    	
        Ext.onReady(function() {
            loadMenuInfo('<%=input_operatorCode%>','<%=parent_id%>','');
            <%if(Argument.getSyscontrolValue("DT_CALL")!=0 && no_callcenter.intValue() != 1){%>	
            	 initMyPhone();
	            <%if(cc_status.equals("0")){%>
					//alert('<%=Argument.getCTIServerIP()%>');
	                connect2Server('<%=Argument.getCTIServerIP()%>','<%=Argument.getCTIServerPort()%>','<%=Argument.getCallCenterDbIP()%>','<%=Argument.getCallCenterDbName()%>','<%=Argument.getCallCenterDbUser()%>','<%=Argument.getCallCenterDbPassword()%>');
	            <%}else if(cc_status.equals("1")){%>
	            	 //���� ��δ���÷ֻ��ţ�����ϵ����Ա����ǰ����������ʹ��CallCenter
	                Ext.MessageBox.alert('<%=LocalUtilis.language("message.warn",clientLocale)%>','<%=LocalUtilis.language("index.callcenter.noExtension",clientLocale)%>��<%=LocalUtilis.language("index.callcenter.connectAdmin",clientLocale)%> ');
	            <%}else if(cc_status.equals("2")){%>
	            	//���� ��δ����¼���ֻ��ţ�����ϵ����Ա����ǰ����������ʹ��CallCenter
	                Ext.MessageBox.alert('<%=LocalUtilis.language("message.warn",clientLocale)%>','<%=LocalUtilis.language("index.callcenter.noRecordExtension",clientLocale)%>��<%=LocalUtilis.language("index.callcenter.connectAdmin",clientLocale)%> ');
	            <%}else if(cc_status.equals("3")){%>
	            	//���� ��δ���÷ֻ�����¼���ֻ��ţ�����ϵ����Ա����ǰ����������ʹ��CallCenter
	                Ext.MessageBox.alert('<%=LocalUtilis.language("message.warn",clientLocale)%>','<%=LocalUtilis.language("index.callcenter.noExtension&noRecordExtension",clientLocale)%>��<%=LocalUtilis.language("index.callcenter.connectAdmin",clientLocale)%> ');
	            <%}else{%>
	            	//���� δ֪����CallCenter��ʱ������
	                Ext.MessageBox.alert('<%=LocalUtilis.language("message.warn",clientLocale)%>','<%=LocalUtilis.language("index.callcenter.unknownException",clientLocale)%> ');  
	            <%}%>
	         <%}%>
           // myPhone = Ext.getDom("phone");
            /*��ӷ���*/
		    Ext.get("callLink").on('click',function(){
				//Ext.MessageBox.prompt('�绰����','��������Ҫ����ĵ绰����:',calloutConfirm);
				document.getElementById("callcenterLink").onclick();
			});		
			//Ext.get("freeLink").on('click',busyFree);
			Ext.get("leavingLink").on('click',leavingBack);
			leavingorback = 1;
        });
 
       // window.onbeforeunload = function(){ 
        	//<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>
				//disconnect2server(op_code,extension);
			//<%}%>
		//} 
       /*���滰���¼*/      
       function saveCallInInfo(){
       	var content = document.getElementById("callin-record").value;
		var service_man = document.getElementById("cc_manager_man_id").value;
       	var input_operatorCode = document.getElementById("input_operatorCode").value;
		var cc_extension = document.getElementById("cc_extension").value;
		var target_custid = document.getElementById("cc_cust_name").value;
		var phoneNumber = document.getElementById("cc_tel_num").value;
		var input_operatorCode = "<%=input_operatorCode%>";
 		if(content == ""){
			Ext.MessageBox.alert("��ܰ��ʾ","��������������ݣ�");
			return;
		}
		//���汸��¼
       	//loginService.addReminder(input_operatorCode,content,{callback:function(data){
       		//var arr = data.split("|");
       		//alert(arr[1]);
       	//}});
		//����ͨ����¼
		var ccVO = {direction:1,callTime:0,callLength:0,managerID:service_man,extension:cc_extension,cust_id:target_custid,contactID:0,phoneNumStr:phoneNumber,businessID:0,content:content,status:1,callCenterID:0,input_man:input_operatorCode};
		Ext.MessageBox.confirm(
            '��ܰ��ʾ','��ȷ��Ҫ��ӻ��������',
            function(btn){
                if(btn=='yes'){
                    insertCallCenter(ccVO,target_custid,phoneNumber);
                }else{
                    document.getElementById("callin-record").value = "";
                }
            }
        );
       }
       
      function insertCallCenter(ccVO,target_custid,phoneNumber){
        ccService.appendCallRecord(ccVO,{callback:function(data){
			//��ӳɹ������²�ѯ��ʷͨ����¼
			if(data != ""){
				Ext.MessageBox.alert("��ܰ��ʾ","������±���ɹ���");
				document.getElementById("callin-record").value = "";
				ccService.getCallRecords(target_custid,phoneNumber,callInfoCallBack);
			}
		}});
      }


      function calloutConfirm(btn,text){
			if(btn=='ok'){
				var telnum = /^[0-9]*$/;
				if(!telnum.test(text)){
					//���� ������ĵ绰���� �����Ϲ����������������
					Ext.MessageBox.alert('<%=LocalUtilis.language("message.warn",clientLocale)%>','<%=LocalUtilis.language("index.callcenter.yourTel",clientLocale)%>'+text+'<%=LocalUtilis.language("index.callcenter.confirmRuls",clientLocale)%> ');
				}else{
					Ext.MessageBox.confirm(
						//ȷ�� ��Ҫ����ĵ绰������ ����ժ���󵥻�ȷ�ϰ�ť
						'<%=LocalUtilis.language("message.infoConfirm",clientLocale)%>','<%=LocalUtilis.language("index.callcenter.yourCallTel",clientLocale)%>:'+text+'��<%=LocalUtilis.language("index.callcenter.confirmPickTel",clientLocale)%> ',
						function(btn){
							if(btn=='yes'){
								callout('<%=extension%>',text);
							}	
						}
					);
				}
			}
		}
		function busyFree(e){		
			if(busyorfree==1){
					j$('#freeLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/free.jpg' );
					j$('#freeLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.clickToBusy",clientLocale)%> ');//����ʾæ
					setFree('<%=extension%>','<%=input_operatorCode%>');
					busyorfree = 0;
					showStatus('ʾæ');
				}
				else{
					j$('#freeLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/busy.jpg' );
					j$('#freeLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.clickToFree",clientLocale)%> ');//����ʾ��
					setBusy('<%=input_operatorCode%>');
					busyorfree = 1;	
					showStatus('<%=LocalUtilis.language("index.callcenter.FreeStatus",clientLocale)%> ');//ʾ��
				}
		}
		function leavingBack(e){
			if(leavingorback==1){
				j$('#leavingLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/leaving.jpg' );
				j$('#leavingLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.toLeaveStatus",clientLocale)%> ');//��Ϊ�뿪״̬
				setBusy('<%=extension%>','<%=input_operatorCode%>');
				leavingorback = 0;	
				showStatus('<%=LocalUtilis.language("index.callcenter.LeaveStatus",clientLocale)%> ');//�뿪
			}
			else{
				j$('#leavingLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/back.jpg' );
				j$('#leavingLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.toBackStatus",clientLocale)%> ');//��Ϊ����״̬
				setFree('<%=extension%>','<%=input_operatorCode%>');
				leavingorback = 1;
				showStatus('<%=LocalUtilis.language("index.callcenter.BackStatus",clientLocale)%> ');//����
			}
		}
		function showStatus(text){
			document.getElementById("cc_show_status").innerText = text;
		}
	function dialogHidden(){
		dialog.hide();
	}

	function web(){
		showModalDialog('<%=request.getContextPath()%>/system/systemparam/web_link.jsp', '1', 'dialogWidth:340px;dialogHeight:200px;edge: Raised; center: Yes; help: Yes; resizable: Yes; status: Yes; ')
	}
    
    var q_dialog_tel = "";
    //����/�����ϵ��ʽ
    function showConditionTel(){
        document.getElementById("tel_selected1").checked = true;
        document.getElementById("tel_selected2").checked = false;
        document.getElementById("tel_selected3").checked = false;
        document.getElementById("tel_selected4").checked = false;
        if(!q_dialog_tel){
            q_dialog_tel = new Ext.BasicDialog("tel-dlg", { 
                            width:430,
                            height:150,
                            shadow:true,
                            modal: true,
                            minWidth:430,
                            minHeight:350,
                            proxyDrag: true,
                            resizable:false,
                            buttonAlign:'center'
                    });
            q_dialog_tel.addKeyListener(27, q_dialog_tel.hide, q_dialog_tel);
            q_dialog_tel.addButton('ȷ ��', matchCustTel, q_dialog_tel);
            q_dialog_tel.addButton('�� ��', closeCustTel, q_dialog_tel);
        }
        q_dialog_tel.show();
    }
    
    function closeCustTel(){
        q_dialog_tel.hide();
        document.getElementById("cust_update_flag1").checked = false;
        document.getElementById("cust_update_flag2").checked = false;
    }
    
    function matchCustTel(){
        var str = "����";
        var val = 1;
        if(document.getElementById("cust_update_flag2").checked){
            str = "�޸�";
            val = 2;
        }
        Ext.MessageBox.confirm(
            '��ܰ��ʾ','��ȷ��Ҫ'+str+'��ϵ��ʽ��',
            function(btn){
                if(btn=='yes'){
                    updateCustTel(val);
                }
            }
        );
    }
    
    function updateCustTel(val){
        var target_custid = document.getElementById("cc_cust_name").value;
		var phoneNumber = document.getElementById("cc_tel_num").value;
        var tel_selected_flag = document.getElementById("tel_selected_flag").value
        ccService.operatorIngCustTel(target_custid,phoneNumber,val,<%=input_operatorCode%>,tel_selected_flag,{callback:function(data){
			if(data != ""){
				Ext.MessageBox.alert("��ܰ��ʾ",data.split("|")[1]+"��");
				document.getElementById("update_cust_tel").style.display = "none";
                if(val == 1){
                    if(tel_selected_flag == 1)
                        document.getElementById("cc_buy_date").value = phoneNumber;
                    else if(tel_selected_flag == 2)
                        document.getElementById("cc_sy_money").value = phoneNumber;
                    else if(tel_selected_flag == 3)
                        document.getElementById("cc_zr_money").value = phoneNumber;
                    else
                        document.getElementById("cc_df_money").value = phoneNumber;
                }
				document.getElementById("cust_update_flag1").checked = false;
                document.getElementById("cust_update_flag2").checked = false;
                q_dialog_tel.hide();
			}
		}});
    }
    
    //�绰ת��
    function telePhoneSwitch(){
        var text = document.getElementById("cc_extension").value;
        Ext.MessageBox.confirm(
            '��ܰ��ʾ','��ȷ��Ҫֱ��ת���ͻ�������',
            function(btn){
                if(btn=='yes'){
                    var telnum = /^[0-9]*$/;
                    if(text != ""){
                        if(!telnum.test(text)){
                            Ext.MessageBox.alert('����','������ĵ绰����'+text+'�����Ϲ����������������');
                        }else{

                            Ext.MessageBox.confirm(
                                'ȷ��','��Ҫת�ӵĵ绰������:'+text+'������ȷ�ϰ�ť���ת��',
                                function(btn){
                                    if(btn=='yes'){
										                            transferInit(text);
                                        transferComplete();
                                        dialog.hide();
                                    }	
                                }
                            );
                        }
                    }else{
                        Ext.MessageBox.alert('����','��������ֻ�����');
                    }
                }
            }
        );
    }
	//�ٶ��������
	function test(){
		setTimeout("telRing('15210170163')",5000);
	}
	//�ı䵱ǰ��Ʒ
	function changeSelectProduct(){
		var ret_productname = showModalDialog('<%=request.getContextPath()%>/login/product_type_select.jsp', window, 'dialogWidth:650px;dialogHeight:560px;status:0;help:0');
		
    	if(ret_productname!=null){	  	
			document.getElementById("now_product").innerText = ret_productname;
		}    	
   	 }
	</script>
</head>
<body>
<input type="hidden" id="tel_selected_flag" name="tel_selected_flag" value="1">
<input type="hidden" id="overall_product_id" name="overall_product_id" value="<%=overall_product_id%>">
<div id="callin-dlg" style="visibility:hidden;">
	<div class="x-dlg-hd">Layout Dialog</div>
	<div class="x-dlg-bd">
		<div id="north">
			<div id="menubar"></div>
			<div id="toolbar"></div>
		</div>
		<div id="callininfo" class="x-layout-inactive-content" style="padding:10px;">
			<div id="callin-cust-info">
				<form id="callin-cust-info-form" class="x-form" method=post action="#">
					<div>
						<div style="float: left; text-align: left; width: 260px;">&nbsp;&nbsp;&nbsp;&nbsp;
						<font color='red' style='font-weight: bold;'>������룺</font><span id="cc_tel_num_str"></span><input type="hidden" id="cc_tel_num" name="cc_tel_num" value=""/>
						</div>
						<div style="float: left; text-align: right; width: 460px; display: none;" id="update_cust_tel">
							<table>
                                <tr>
                                    <td align="right"><font color='red' style='font-weight: bold;'>ע�⣺����δƥ�䵽�ͻ�[</font>
                                    <input type="radio" name="cust_update_flag" id="cust_update_flag1" value="1" class="flatcheckbox" onclick="javascript:showConditionTel(this);">
                                    </td>
                                    <td>������ϵ��ʽ&nbsp;&nbsp;<font color='red' style='font-weight: bold;'>]</font></td>
                                    <td align="right">
									<!--  
									<input type="radio" name="cust_update_flag" id="cust_update_flag2" value="2" class="flatcheckbox" onclick="javascript:showConditionTel(this);"></td>
                                    <td>
                                        �����ϵ��ʽ
                                    </td>
									-->
                                </tr>
                            </table>
						</div>
					</div>
					<fieldset id="callin-cust-info-fs" class="x-form-label-right" style="height:195px; float: left;">
						<legend>ƥ�䵽�Ŀͻ���Ϣ</legend>
							<div id="cc_cust_info"></div>
							<table style="width:100%;border-collapse:0px;border-spacing:0px;" >
								<tr>
									<td align="right" height="25"><%=LocalUtilis.language("class.customerName",clientLocale)%>��</td><!-- �ͻ����� -->
									<td colspan="4" valign="top">
										<select id="cc_cust_name" name="cc_cust_name" style="width:280px;" onchange="javascript:refreshCustInfo();">
										</select>&nbsp;&nbsp;
										<img src="/images/search.gif" style="cursor:hand"  onclick="javascript:showCondition()"/>
										<input readonly class="edline" id="select_cc_cust_name" name="select_cc_cust_name" value="" style="visibility:hidden"/>
									</td>
									<!-- 
									<td height="25">��ϵ�绰��<input readonly class="edline" size="15" id="cc_cust_tel_num" name="cc_cust_tel_num" value=""/>
									</td>
									-->	
								</tr>
								<tr>
									<td align="right" height="25"><%=LocalUtilis.language("class.customerID",clientLocale)%>��</td><!-- �ͻ���� -->
									<td><input readonly class="edline" size="27" id="cc_cust_no" name="cc_cust_no" value=""/>			
										<input type="hidden" id="cc_cust_id" name="cc_cust_id" value=""/>									
									</td>
									<td align="right">�ͻ�������</td><!-- �ͻ���� -->
									<td><input readonly class="edline" size="27" id="cc_cust_type" name="cc_cust_type" value=""/></td>
									<td align="right" id="customer_sex_title">�Ա�</td><!-- �ͻ����� -->
									<td  id="customer_sex_content"><input readonly class="edline" size="27" id="cc_cust_grade" name="cc_cust_grade" value=""/></td>
								</tr>
								<tr>
									<td align="right" height="25">֤�����ͣ�</td>
									<td><input readonly class="edline" size="27" id="cc_card_type_name" name="cc_card_type_name" value=""/></td>
									<td align="right">֤�����룺</td>
									<td><input readonly class="edline" size="27" id="cc_card_id" name="cc_card_id" value=""/></td>
									<td align="right">�ͻ�����</td>
									<td>
										<input readonly class="edline" size="27" id="cc_manager_man" name="cc_manager_man" value=""/>
										<input type="hidden" id="cc_manager_man_id" name="cc_manager_man_id" value=""/>
										<input type="hidden" id="cc_extension" name="cc_extension" value=""/>
										<input type="hidden" id="user_id" name="user_id" value="<%=user_id%>"/>
										<input type="hidden" id="cc_is_team" name="cc_is_team" value=""/>
									</td>
								</tr>
								<tr>
									<td align="right" height="25">��ϵ��ʽ��</td><!-- �Ϲ���� -->
									<td><input readonly class="edline" size="27" id="cc_rg_money" name="cc_rg_money" value=""/></td>
									<td align="right">�ֻ�1��</td><!-- ת�ý�� -->
									<td><input readonly class="edline" size="27" id="cc_zr_money" name="cc_zr_money" value=""/></td>
									<td align="right">�ֻ�2��</td><!-- �Ҹ���� -->
									<td><input readonly class="edline" size="27" id="cc_df_money" name="cc_df_money" value=""/></td>
								</tr>
								<tr>
									<td align="right" height="25">��˾�绰��</td><!-- ������ -->
									<td><input readonly class="edline" size="27" id="cc_sy_money" name="cc_sy_money" value=""/></td>
									<td align="right">��ͥ�绰��</td><!-- ������� -->
									<td><input readonly class="edline" size="27" id="cc_buy_date" name="cc_buy_date" value=""/></td>
									<td align="right">EMAIL��</td>
									<td><input readonly class="edline" size="27" id="cc_cust_emial" name="cc_cust_emial" value=""/></td>
								</tr>
                                <tr>
									<td align="right" height="25">�ʼĵ�ַ��</td>
									<td><input readonly class="edline" size="27" id="cc_post_address" name="cc_sy_money" value=""/></td>
									<td align="right">�ʱࣺ</td>
									<td><input readonly class="edline" size="27" id="cc_post_code" name="cc_buy_date" value=""/></td>
									<td align="right"></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" height="25">�ʼĵ�ַ2��</td>
									<td><input readonly class="edline" size="27" id="cc_post_address2" name="cc_sy_money" value=""/></td>
									<td align="right">�ʱ�2��</td>
									<td><input readonly class="edline" size="27" id="cc_post_code2" name="cc_buy_date" value=""/></td>
									<td align="center" colspan="2">
                                        <div style="padding-left: 30px;">
										<div id="zj_server_man" style="display: none; float: left; padding-top: 3px;"><a href="#" onclick="javascript:telePhoneSwitch();"><font color="red"><b>�绰ת��</b></font></a>&nbsp;|&nbsp;</div>
										<div id="customer_details" style="float: left; padding-top: 5px;"><a href="#" onclick="javascript:showCustDetail();"><font color="red"><b>�ͻ���ϸ</b></font></a></div>
                                        </div>
                                    </td>
								</tr>
							</table>	
					</fieldset>

					<fieldset id="callin-cust-info-fs" class="x-form-label-right" style="height:100px;">
						<legend>��ʷ�����¼</legend>
						<div style="overflow: scroll; width: 100%; height: 22px;overflow-x:hidden;overflow-y:hidden;">
						<TABLE style="BORDER-COLLAPSE: collapse; BORDER-RIGHT-BOTTOM: 0px;" borderColor=#CCFFFF bgColor=#DFFFDF cellSpacing=0 cellPadding=0 width="97%" border=1>							 
							<TR align=middle>
								<th style="border-bottom: medium none; text-align: center;" height=20 align="center" width="8%">��ʽ</td>
								<th style="border-bottom: medium none; text-align: center;" height=20 align="center" width="65%">ͨ������</td>
								<th style="border-bottom: medium none; text-align: center;" height=20 align="center" width="17%">ʱ��</td>
							</TR>
						</TABLE>
						</div>
						<div style="overflow: scroll; width: 100%; height: 50px;overflow-x:hidden;">
						<TABLE  id="mybody" style="BORDER-COLLAPSE: collapse; BORDER-TOP-WIDTH: 0px;" borderColor=#CCFFFF bgColor=#DFFFDF cellSpacing=0 cellPadding=0 width="97%" border=1>							 
							
						</TABLE>
						</div>
					</fieldset>
				</form>
			</div>

			<div id="record">
				<form id="callin-record-form" class=" x-form" method=post>
					<fieldset  id="callin-record-fs" class="x-form-label-right" style="height: 61px">
						<legend>�������</legend>
						<textarea id="callin-record" name="callin-record" class=" x-form-textarea x-form-field " style="width:99%;height:37px;overflow:auto"></textarea>
					</fieldset>
					<div align="right">
						<button class="xpbutton2" id="btnSave" name="btnSave" onclick="javascript:saveCallInInfo();">����</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div id="hello-dlg" style="visibility:hidden;position:absolute;top:0px;">
	<div class="x-dlg-hd">�ͻ�ƥ������</div>
   	<div class="x-dlg-bd">
        <div class="x-dlg-tab" title="�ͻ�ƥ������">
            <div class="inner-tab">
            	<table style="width:100%;border-collapse:5px;border-spacing:5px;" >
            		<tr>
            			<td align="right" height="25">�ͻ����ƣ�
            			</td>
            			<td><input name="q_cust_name" id="q_cust_name" size="30"/>
            			</td>
            		</tr>
            		<tr>
            			<td align="right" height="25">֤�����룺
            			</td>
            			<td><input name="q_card_num" id="q_card_num" size="30"/>
            			</td>
            		</tr>
            	</table>
            </div>
        </div>
	</div>
</div>
<div id="tel-dlg" style="visibility:hidden;position:absolute;top:0px;">
	<div class="x-dlg-hd">��ϵ��ʽ����</div>
   	<div class="x-dlg-bd">
    <table style="width:100%;border-collapse:5px;border-spacing:5px;" >
        <tr>
            <td align="right" height="32"><input class="flatcheckbox" type="radio" name="tel_selected1" id="tel_selected1" value="1" checked onclick="javascript:document.getElementById('tel_selected_flag').value=1;">��ͥ�绰:</td>
            <td><input class="edline"  id="dd_h_tel" name="dd_h_tel" size="20" value="" readonly/></td>
            <td align="right"><input class="flatcheckbox" type="radio" name="tel_selected1" id="tel_selected2" value="2" onclick="javascript:document.getElementById('tel_selected_flag').value=2;"><%=LocalUtilis.language("class.companyPhone",clientLocale)%>:</td><!--��˾�绰-->
            <td><INPUT class="edline" id="dd_o_tel" name="dd_o_tel" size="20" value="" readonly/></td>
        </tr>
        
        <tr>
            <td align="right" height="32"><input class="flatcheckbox" type="radio" name="tel_selected1" id="tel_selected3" value="3" onclick="javascript:document.getElementById('tel_selected_flag').value=3;">��&nbsp;&nbsp;&nbsp;&nbsp;��1:</td><!--�ֻ�-->
            <td><input class="edline" id="dd_mobile" name="dd_mobile" size="20" value="" readonly/></td>
            <td align="right"><input class="flatcheckbox" type="radio" name="tel_selected1" id="tel_selected4" value="4" onclick="javascript:document.getElementById('tel_selected_flag').value=4;">��&nbsp;&nbsp;&nbsp;&nbsp;��2:</td><!--�ֻ�-->
            <td><INPUT class="edline" id="dd_bp" name="dd_bp" size="20" value="" readonly/></td>
        </tr>
    </table>
    </div>
</div>
	<div id="home-banner" class="home-banner1">
		<div id="home-logo" valign="center">&nbsp;<img src="<%=user_logo_top%>" width="275" height="52" /></div>
		<div id="home-header"> 
			<div class="search">
				<p>&nbsp;&nbsp;
				<p>
				<form method="post" action="#" name="sitewidesearch">
					<input tabindex="1" id="searchPhrase" name="strQueryText" type="hidden" />
					<input tabindex="2" src="<%=request.getContextPath()%>/styles/images/btn_go.gif" id="go" alt="Go" type="hidden"/>
				</form> 			
			</div>
			<div id="scroll"> 
				<div id="scrollMe" style="overflow:hidden;height:27px;margin-right:10px;">
					<!-- ���� ���� -->
					<%=LocalUtilis.language("index.msg.hello",clientLocale)%>!<%=input_operatorName%>
					&nbsp;&nbsp;<font color="#000000"><%=LocalUtilis.language("class.departID3",clientLocale)%>:<%=depart_name %>
					&nbsp;&nbsp;<%=LocalUtilis.language("class.roleName2",clientLocale)%>:<%=role_name %>
					</font>
					[<span id="cc_show_status"><%=LocalUtilis.language("index.callcenter.FreeStatus",clientLocale)%> </span>]
					<br/>
				</div>
				<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/srcMarquee.js"></script>
				<!--<script>new srcMarquee("scrollMe",0,1,808,27,30,2000,2000,27)</script> -->
			</div>
			<div class="nav-secondary">
				
				<span class="region-selector">
				    <input type="hidden" id="systemMessage" />	
					<!--��ǰ��Ʒ-->
				  	<a href="#" onclick="javascript:changeSelectProduct();">
      					��ǰ��Ʒ:<span  id="now_product">
						<%if(overall_product_id==null||overall_product_id.intValue()==0) {out.println("ȫ��");}
							 else {out.print(Utility.trimNull(Argument.getProductFlag(overall_product_id,"product_code")) + "-" + Utility.trimNull(Argument.getProductFlag(overall_product_id,"product_name")));}%>
						</span>
					</a>&nbsp;&nbsp;
					<!-- ���ټ��� -->
					<a href="#" onclick="javascript:quickSearchCustomer();" title="���ټ���">���ټ���<img src="/images/search.gif" /></a>&nbsp;&nbsp;

					<!-- �ⲿ����-->
					<a href="#" onclick="web();" id="weblinkEl"  title="�ⲿ���� ">
					�ⲿ����<img src="<%=request.getContextPath()%>/images/FUNC220000.gif" /></a>&nbsp;&nbsp;
					<!--<a href="#" onclick="javascript:newInfo();" title="ϵͳ֪ͨ">��Ϣ<img src="/images/message.gif" /></a>&nbsp;&nbsp;&nbsp;&nbsp;-->
					<!-- �������� -->
					<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>
					<a href="#" id="callcenterLink" target="_self" onclick="dialog.show(Ext.get('scrollMe'));"><%=LocalUtilis.language("index.menu.callcenter",clientLocale)%> <img src="<%=request.getContextPath()%>/images/icon-phone.gif" /></a>&nbsp;&nbsp;
					<%}%>
					<%if(Argument.getSyscontrolValue("DT_CALL")==0){%>
					<a href="#" id="callcenterLink" target="_self" onclick="javascript:telMatch();">�绰ƥ��<img src="<%=request.getContextPath()%>/images/icon-phone.gif" /></a>&nbsp;&nbsp;                                                       
					<%}%>					
					<!-- ����  �����û�����-->
					<a href="#" onclick="javascript:showPasswordDialog();" id="modiPassWord" title="<%=LocalUtilis.language("index.msg.setUserInfo",clientLocale)%> "><%=LocalUtilis.language("index.msg.set",clientLocale)%> <img src="<%=request.getContextPath()%>/images/secret.gif" /></a>&nbsp;&nbsp;
					<%if(Argument.getSyscontrolValue("SCANCEL")==0 && isWebcall.intValue()==0){%>
					<!-- ע��  �ǳ�ϵͳ-->
					<a href="#" onclick="logOut();" id="logOutEl"  title="<%=LocalUtilis.language("index.msg.quitSys",clientLocale)%> ">�˳�<img src="<%=request.getContextPath()%>/images/logout.gif" /></a>&nbsp;&nbsp;
					<%}%>
					<%if(input_operatorCode.intValue() == 888){%><a href="#"  id="callin" onclick="telRing('15210170163');"  title="����������">����������<img src="/images/logout.gif" /></a><%}%>
				</span>
				<a href="javascript:loadMain('main.jsp')" class="homepage"></a>
				<!--<span class="link-delimiter">|</span><a href="#">����</a>-->
			</div> 
		<div class="rc-container" id="nav-module"  align="left">       
			<span class="corner-top"><span class="corner-left"></span></span>
			<table class="nav-container" border="0" cellpadding="0" cellspacing="0">   
				<tr>
					<td id="td_11" class="nav" align="left">
						<a id="a_11" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','11','')"><font color="#ffffff"><%=LocalUtilis.language("index.menu.myDesk",clientLocale)%> </font></a>
					</td>
					<%if(oplocal.checkMainMenu("2",input_operatorCode.toString())){%>
					<td id="td_2" class="nav" width="106" align="left">
						<a id="a_2" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','2','')"><%=LocalUtilis.language("index.menu.customerManagement",clientLocale)%> </a><!-- �ͻ�����-->
          </td>
          <%}%>
          <%if(oplocal.checkMainMenu("3",input_operatorCode.toString())){%>
					<td id="td_3" class="nav" width="106" align="left">
						<a id="a_3" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','3','')"><%=LocalUtilis.language("index.menu.marketingManagement",clientLocale)%> </a><!-- Ӫ������-->
          </td>
          <%}%>
          <%if(oplocal.checkMainMenu("4",input_operatorCode.toString())){%>
					<td id="td_4" class="nav"  width="106" align="left">
						<a id="a_4" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','4','')"><%if (user_id.intValue()==21/*21:������У������˵��������ĳ�ά������*/){out.print("ά������");}else{out.print(LocalUtilis.language("index.menu.managerManagement",clientLocale));}%> </a><!-- �������-->
          </td>
          <%}%>
          <%if(oplocal.checkMainMenu("5",input_operatorCode.toString())){%>
					<td id="td_5" class="nav" width="106" align="left">
						<a id="a_5" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','5','')"><%=LocalUtilis.language("index.menu.callcenter",clientLocale)%> </a><!-- ��������-->
          </td>
          <%}%>
          <%if(oplocal.checkMainMenu("6",input_operatorCode.toString())){%>
					<td id="td_6" class="nav" width="106" align="left">
						<a id="a_6" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','6','')"><%=LocalUtilis.language("message.custAnalysis",clientLocale)%> </a><!-- �ͻ�����-->
          </td>
          <%}%>
		  <%if(oplocal.checkMainMenu("7",input_operatorCode.toString())){%>
					<td id="td_7" class="nav" width="106" align="left">
						<a id="a_7" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','7','')">��ͬ����</a><!-- ��ͬ����-->
          </td>
          <%}%>
		  <%if(oplocal.checkMainMenu("w",input_operatorCode.toString())){%>
					<td id="td_w" class="nav" width="106" align="left">
						<a id="a_w" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','w','')">��Ϣ��</a>
          </td>
          <%}%>
		 <%if(oplocal.checkMainMenu("1",input_operatorCode.toString())){%>
					<td id="td_1" class="nav" width="106" align="left">
						<a id="a_1" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','1','')"><%=LocalUtilis.language("index.menu.systemConfig",clientLocale)%> </a><!-- ϵͳ����-->
          </td>
          <%}%>
          <%if(oplocal.checkMainMenu("s",input_operatorCode.toString())){%>
					<td id="td_s" class="nav" width="106" align="left">
						<a id="a_s" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','s','')">��������</a>
          </td>
          <%}%>
          			<%if((Utility.parseInt(input_operator.getIs_intrust(), new Integer(0)).intValue() == 1) && (!"".equals(Utility.trimNull(application.getAttribute("INTRUST_ADDRESS"))))){%>
		  			<td id="td_99" class="nav" width="106" align="left">
						<a id="a_99" href="#" onclick="javascript:loadMenuInfo('<%=input_operatorCode%>','99','')">�������</a>
					</td>
					<%}%>
				</tr>
			</table>
		</div>
	</div> 
	<div id="home-column-left" style="margin 0 0 0 0;padding-top: 7px;padding-left: 7px;"></div>
		<iframe id="home-column-main" name="content" frameborder="0" style="width:100%;height:100%;" target="content"></iframe>
		<noiframe><span><%=LocalUtilis.language("index.msg.iframFail",clientLocale)%> </span></noiframe> <!-- �����������֧�ֿ��-->
	<div id="home-column-south" style="margin 0 0 0 0;padding-top: 7px;padding-left: 7px;">
<%if (user_id.intValue()!=22/*��Ͷ*/){ %>
		<div id="main-callcenter-accordion">
			<div>
				<a id="callLink" href="#"><img src="<%=request.getContextPath()%>/images/callcenter/call.jpg" width="78" height="78" title="<%=LocalUtilis.language("index.callcenter.callTel",clientLocale)%> "/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- ����绰-->
				<a id="meettingLink" href="#"><img src="<%=request.getContextPath()%>/images/callcenter/meetting.jpg" width="78" height="78" title="<%=LocalUtilis.language("index.callcenter.manyMeetings",clientLocale)%> "/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- �෽����-->
				<!--
					<a id="freeLink" href="#"><img src="<%=request.getContextPath()%>/images/callcenter/free.jpg" width="78" height="78" title="<%=LocalUtilis.language("index.callcenter.clickToBusy",clientLocale)%> "/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				--><!-- ����ʾæ-->
				<a id="leavingLink" href="#"><img src="<%=request.getContextPath()%>/images/callcenter/back.jpg" width="78" height="78" title="<%=LocalUtilis.language("index.callcenter.clickToLeave",clientLocale)%> "/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- �����뿪-->
			</div>
		</div>
<%} %>
	</div>	

<input type="hidden" name="input_operatorCode" id="input_operatorCode" value="<%=input_operatorCode%>">
	<input type="hidden" id="parent_id" value="<%=parent_id%>" />
	<input type="hidden" id="agent_status" value=""/>
	<input type="hidden" id="callRecordID" value=""/>
	<input type="hidden" id="callTalkingStartTime" value="" /><!-- ͨ����ʼʱ�� -->
	<input type="hidden" id="callTalkType" value="0" /> <!-- ͨ�����ͣ�0-Ĭ�ϴ�index.jsp�������Ľ��룻1-������ҳ����룬�Դ�cust_id��2.�������� -->
	<input type="hidden" id="target_custid" value="" /> <!-- Ŀ��cust_id -->
	<input type="hidden" id="communication_info" value=""/>
	<input type="hidden" id="queueInfo" value=""/>
	<input type="hidden" id="isWebcall" value="<%=isWebcall%>"/>

	<input type="hidden" id="my_status" value="st_free"/>
	<input type="hidden" id="languageFlag" value="<%=languageFlag%>"/>
	<input type="hidden" id="languageType" value="<%=languageType%>"/>
	<input type="hidden" id="dt_call" value="<%=Argument.getSyscontrolValue("DT_CALL")%>"/>
<%if(Argument.getSyscontrolValue("DT_CALL")!=0){
	if(user_id.intValue() == 15){//����
%>
	<object classid="clsid:4CFBD1C3-7492-4F9D-92BF-4001D006387E" 
		codebase="/includes/callcenter/CCLink2008.ocx" id="phone" width="0" height="0">
	</object>
	<%}else{%>
	<object classid="clsid:BB4A050B-2860-4FD5-A08C-A418804D7A18" 
		codebase="/includes/callcenter/CtiApiClient.ocx" 
		id="phone" style="width:0px; height:0px">
		<param name="_Version" value="65536"/>
		<param name="_ExtentX" value="2646"/>
		<param name="_ExtentY" value="1323"/>
		<param name="_StockProps" value="0"/>
	</object>
	<%} %>
<%}%>
</body>
</html>
<%
tcustmanagers_Bean.remove();
roleLocal.remove();
departmentLocal.remove();
%>