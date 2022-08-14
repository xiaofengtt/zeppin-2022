<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=7">	
<title>Project</title>
<link href="styles/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/plugins/jquery-1.8.2.js"></script>
<script type="text/javascript" src="js/plugins/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="js/plugins/utilities.js"></script>
	<script language=javascript>
		var j$ = jQuery.noConflict();
	</script>
<script type="text/javascript" src="js/plugins/initstyle.js"></script>
<script type="text/javascript" src="js/page.all.js"></script>
<script type="text/javascript" src="js/page.sidenav.js"></script>
<!--[if IE 6]><script type="text/javascript" src="js/iepngfix_tilebg.js"></script><![endif]-->

<style type="text/css">

</style>
</head>
<body class="pageSidenav">
<ul id="list_parent" class="sideNav oneActive">

</ul>
</body>
</html>
<script type="text/javascript">
 j$(function(){
	j$("#list_parent").empty();
	var param = window.location.search;
	var id = param.substring(param.indexOf("=")+1);
	if(param.indexOf("=") != -1){
		var url = '<%=request.getContextPath()%>/leftmenu2017.jsp?menu_id='+id+'&parent_id=';
	    j$.ajax({
	        type:'get',
	        //async:false,
	        url:url,
	        success:function(data){
	        	var json = eval('(' + data + ')');
	        	if(json != null){
		        	for(var i=0; i<json.length; i++){
			        	var appendStr = "";
		        		var menu_pa_name = json[i].MENU_NAME;
		        		var menu_pa_url = json[i].URL;
		        		var menu_pa_glag = json[i].BOTTOM_FLAG;
		        		var menu_id = json[i].MENU_ID;
		        		if(menu_pa_glag == 0){
		        			appendStr = appendStr+'<li>'+menu_pa_name+'<ul id="'+menu_id+'"></ul></li>';
		        			j$("#list_parent").append(appendStr);
		        			if(menu_id != null && menu_id != ''){
		        				var url_second = '<%=request.getContextPath()%>/leftmenu2017.jsp?menu_id=&parent_id='+menu_id;
		        				var menu_pa_name_s = "";
				        		var menu_pa_url_s = "";
				        		var menu_pa_glag_s = "";
				        		var menu_id_s = "";
   		        			    j$.ajax({
		        			        type:'get',
		        			        async:false,
		        			        url:url_second,
		        			        success:function(data_s){
		        			        	var json_s = eval('(' + data_s + ')');
		        			        	if(json_s != null){
		        				        	for(var j=0,len=json_s.length; j<len; j++){
		        				        		menu_pa_name_s = json_s[j].MENU_NAME;
		        				        		menu_pa_url_s = json_s[j].URL;
		        				        		menu_pa_glag_s = json_s[j].BOTTOM_FLAG;
		        				        		menu_id_s = json_s[j].MENU_ID;
		        				        		menu_id_pid = json_s[j].PARENT_ID;
	        				        			if(menu_pa_glag_s == 0){
			        			        			var appendStr_second = "";
		        				        			appendStr_second = appendStr_second + '<li>'+menu_pa_name_s+'<ul id="'+menu_id_s+'">';
		        				        			appendStr_second = appendStr_second+'</ul></li>';
		        				        			j$("#"+menu_id_pid).append(appendStr_second);
		        				        		}else{
		        				                    if(menu_pa_url_s.indexOf(".CLL")!= -1){
		        				                       var url_filename = "<%=basePath%>/webreport/Cells"+menu_pa_url_s;
		        				                       menu_pa_url_s="/webreport/init.jsp?filename="+url_filename;
		        				         			}
		        				                    if(menu_pa_url_s.indexOf("/client/analyse/client_query_list.jsp")!= -1){
			        				                       menu_pa_url_s += "?first_flag=1";
			        				         		}
		        				                    if(menu_pa_url_s.indexOf("/client/appraise/aml_customer_list.jsp")!= -1){
			        				                       menu_pa_url_s += "?first_flag=1";
			        				         		}
		        				                    if(menu_pa_url_s.indexOf("/project/flowApp/staffworklogquery010.jsp")!= -1){
			        				                       menu_pa_url_s += "?first_flag=1";
			        				         		}
		        				                   // /webreport/init.jsp?filename=http://192.168.2.49:9080//webreport/Cells/301.CLL&amp;menu_id=W990102
		        				        			var appendStr_three = "";
		        		 		        			if(menu_pa_url_s.indexOf("=") != -1){
		        		 		        				appendStr_three = appendStr_three+"<li onclick='saveMenuId(\""+menu_id_s+"\",\""+menu_pa_name_s+"\");j$.writeURL({content:\""+menu_pa_url_s+"&menu_id="+menu_id_s+"&UNQUERY=1\"})'>"+menu_pa_name_s+"</li>";
		        				        			}else{
		        				        				appendStr_three = appendStr_three+"<li onclick='saveMenuId(\""+menu_id_s+"\",\""+menu_pa_name_s+"\");j$.writeURL({content:\""+menu_pa_url_s+"?menu_id="+menu_id_s+"&UNQUERY=1\"})'>"+menu_pa_name_s+"</li>";
		        				        			}
		        		 		        			j$("#"+menu_id_pid).append(appendStr_three);
		        				        		}
		        			       			}
		        			        	}else{
		        			        		alert("加载菜单出错了，请刷新后再尝试！");
		        			        	}
		        			        },
		        			        error:function(data){
		        			            alert('error');
		        			        }
		        			    });   
		        			}
// 		        			j$("#list_parent").append('</ul></li>');
		        		}else{
 		        			if(menu_pa_url.indexOf("=") != -1){
			        			appendStr = appendStr+"<li onclick='saveMenuId(\""+menu_id+"\",\""+menu_pa_name+"\");j$.writeURL({content:\""+menu_pa_url+"&menu_id="+menu_id+"\"})'>"+menu_pa_name+"</li>";
		        			}else{
			        			appendStr = appendStr+"<li onclick='saveMenuId(\""+menu_id+"\",\""+menu_pa_name+"\");j$.writeURL({content:\""+menu_pa_url+"?menu_id="+menu_id+"\"})'>"+menu_pa_name+"</li>";
		        			}
 		        			j$("#list_parent").append(appendStr);
		        		}
	       			}
		        	IntSideNav(id);
		        	j$(".desc").attr("title",function(){
		        		return j$(this).text();
		        	});
	        	}else{
	        		alert("加载菜单出错了，请刷新后再尝试！");
	        	}
	        },
	        error:function(data){
	            alert('error');
	        }
	    });  
	}else{
		alert("加载菜单出错了，请刷新后再尝试！");
	}
}) 
function saveMenuId(menuId,menu_info){
	j$.ajax({
        type:'get',
        cache:false,
        url:'/login/savesession.jsp?menu_id='+menuId+'&menu_info='+menu_info,
        success:function(data){
        	//alert(data);
        },
        error:function(data){
        }
    });
}
</script>