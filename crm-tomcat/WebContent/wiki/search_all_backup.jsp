<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
ProductInfoReposLocal wikiRepos = EJBFactory.getProductInfoRepos();
List colud_list = wikiRepos.getCloudKeyword();
Map map = null;
Iterator iterator = colud_list.iterator();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>index</title>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script src='../lib/3rd/jquery-1.11.1.min.js'></script>
<script src='../lib/aYin/aYin.js'></script>
<link rel='stylesheet' type='text/css' href='../lib/aYin/aYin.css'>
<script src='../lib/3rd/bootstrap/js/bootstrap.js'></script>
<link rel='stylesheet' type='text/css' href='../lib/3rd/bootstrap/css/bootstrap.css'>
<script src='../lib/3rd/bootstrap-multiselect/js/bootstrap-multiselect.js'></script>
<link rel='stylesheet' href='../lib/3rd/bootstrap-multiselect/css/bootstrap-multiselect.css' type='text/css'>
<script src='../lib/3rd/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js'></script>
<script src='../lib/3rd/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js'></script>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<link rel='stylesheet' type='text/css' href='../lib/3rd/bootstrap-datetimepicker/bootstrap-datetimepicker.css'>
<style type="text/css">
html{font-size:14px;}
body { background:#fff; padding: 0;margin: 0; color:#666; font: 14px/150%  "Lucida Grande", Lucida Sans Unicode, Hiragino Sans GB, WenQuanYi Micro Hei, Verdana, Aril, sans-serif;-webkit-text-size-adjust: none; -webkit-font-smoothing: antialiased;}
ul{margin:0; padding:0;}
li{list-style: none;}
a{blr:expression(this.onFocus=this.blur()); cursor: pointer;text-decoration: none;}
a,input,button{outline:none!important;}
a {text-decoration: none!important; }
embed{display:none;}

.search-con-wrap{padding:10px; position: absolute; top:0; right:0; left:0; background-color: #f5f5f5; border-bottom: 1px solid #ccc;}
.search-con{width: 100%;}
.search-con .title{}
.search-con td,
.search-con th{padding:0 10px; white-space: nowrap;} 
.search-con td{}
.search-con td select{min-width: 160px; }
.search-con th{text-align: right; padding: 0;}

.search-box{position: absolute; top:35%; left:50%; width: 700px; height:90px; margin-left:-350px;}
.search-box .key-word{position: absolute; top:100%; left:0px; right:0px; font-size: 0;}
.search-box .key-word li{display:inline-block; margin:5px; background-color: #f5f5f5; border-radius: 5px; float: left; line-height: 2rem; padding:0 10px; font-size: 1rem; color:#197fe6;}
.search-box .key-word li:hover{background-color: #197fe6; color:#fff;}
.search-box .check-box{position: absolute; top:-50px; left:50%; margin-left:-100px; width: 200px;}
.search-box .check-box li{width: 100px;float: left; text-align: center; font-size: 16px;}
.search-box .check-box li input{margin-right: 10px;}
.search-box .check-box li label{font-weight: normal;}
.search-box .form-control{line-height: 60px; height:60px; font-size: 2rem;}
.search-box .btn{position: absolute; right:5px;top:5px; height:50px; font-size: 1.8rem; padding:0 30px; }
</style>
<script language="javascript">
 	setTimeout(function(){
		try{
			document.getElementById('enfo_crm_wiki_q').focus();
		    //document.getElementById("productSearchDiv").style.top=document.getElementById('enfo_crm_wiki_q').offsetTop+document.getElementById('enfo_crm_wiki_q').clientHeight+20;
			document.getElementById('productSearchDiv').style.display = "block";
		} catch(e){}
	}, 200); 
	function switchpsd() {
		if (document.getElementById('chkProduct').checked==true) {
			document.getElementById('productSearchDiv').style.display = "block";
		} else {
			document.getElementById('productSearchDiv').style.display = "none";
		}
	}
	function validateForm(form){
		if ((!form.chkProduct.checked) && (!form.chkWiki.checked)) {
			alert("必须选择产品库或知识库！");
			return false;
		}
    	//if(!sl_check(form.enfo_crm_wiki_q, "搜索关键字", 200, 1)) return false;
    	syncDatePicker(form.pre_date1_picker, form.pre_date1);
    	syncDatePicker(form.pre_date2_picker, form.pre_date2);
    	syncDatePicker(form.prestart_date1_picker, form.prestart_date1);
    	syncDatePicker(form.prestart_date2_picker, form.prestart_date2);
    	return true;
	}
	function search(key) {
		document.theform.enfo_crm_wiki_q.value = key;
		document.theform.submit();
	}
</script>
</head>
<body class="body">
<form name="theform" method="post" action="search_result.jsp" onsubmit="javascript:return validateForm(this);">
	<div id="productSearchDiv" class="search-con-wrap">
		<table class="search-con">
			<tr>
				<th class="title"></th>
				<th>状态：</th>
				<td>
					<select class="form-control" name="selStatus">
						<OPTION value="0">全部</OPTION>
						<OPTION value="2">推介</OPTION>
						<OPTION value="3">存续</OPTION>
						<OPTION value="4">结束</OPTION>
					</select>
				</td>
				<th>类别：</th>
				<td>
					<select class="form-control" name="selClass1">
						<%=Argument.getClassType1Options("")%>
					</select>
				</td>
				<th>推介期：</th>
				<td><input type="text" class="form-control" /></td>
				<td><input type="text" class="form-control" /></td>
				<th>预约期限：</th>
				<td><input type="text" class="form-control" /></td>
				<td><input type="text" class="form-control" /></td>
			</tr>
		</table>
	</div>

	<div class="search-box">
		<ul class="check-box">
			<li><input type="checkbox" checked="checked" id="chkProduct" name="chkProduct" value="1" onclick="javascript:switchpsd();"/><label for="">产品库</label></li>
			<li><input type="checkbox" checked="checked" name="chkWiki" value="1" /><label for="">知识库</label></li>
		</ul>
		<input type="text" id="enfo_crm_wiki_q" name="enfo_crm_wiki_q" class="form-control form-group-lg"/>
		<button class="btn btn-primary" type="submit" name="btnG">搜索</button>
		<ul class="key-word">
		<%
			while(iterator.hasNext()){
			map = (Map)iterator.next();
		%>
			<li><a href="javascript:search('<%=Utility.trimNull(map.get("KEY_WORD"))%>');"><%=Utility.trimNull(map.get("KEY_WORD"))%></a>&nbsp;</li>
		<%
		}
		%>
		</ul>
	</div>
</form>		
	
</body>
</html>
<script src="../lib/init/loadFiles-after.js"></script>
<script>

</script>
