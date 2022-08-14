<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>教师二次确认</title>
<link rel="stylesheet" href="../css/tableStlye.css">
<link rel="stylesheet" href="../css/button_group.css">
<link rel="stylesheet" href="../css/mainBox.css" />
<link rel="stylesheet" href="../css/paging.css" />
<link rel="stylesheet" href="../css/teachersConfirm.css">
<link rel="stylesheet" href="../css/easyModal.css" />

<script id="queboxTpl" type="text/template">
             <tr {{if #index % 2 == 1}}class="odd"{{/if}}>
             	<td>
			 		<input type="hidden" id="{{:tid}}"/>
			 		<input type="checkbox" class="checkbox"/>
			 	</td>
                <td>{{:#index+1}}</td>
                <td title="{{:name}}">
                		<a href="./teachersHistoryMessage.jsp?id={{:tid}}" style="color:#E0615F;">{{:name}}</a>
                </td>
                <td title="{{:mobile}}">{{:mobile}}</td>
                <td title="{{:ethnic}}">{{:ethnic}}</td>
                <td>{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}</td>
                <td title="{{if type==1}}考务组{{/if}}
                           {{if type==2}}研究生{{/if}}
                           {{if type==3}}教工{{/if}}
                           {{if type==4}}本科{{/if}}
                           {{if type==5}}非师大人员{{/if}}
                         ">{{if type==1}}考务组{{/if}}
                           {{if type==2}}研究生{{/if}}
                           {{if type==3}}教工{{/if}}
                           {{if type==4}}本科{{/if}}
                           {{if type==5}}非师大人员{{/if}}
                </td>
                <td title="{{:roomIndex}}-{{:roomAddress}}">{{:roomIndex}}-{{:roomAddress}}</td>
                <td title="{{if isChief=='1'}}主考{{/if}}{{if isChief=='0'}}副考{{/if}}">{{if isChief=='1'}}主考{{/if}}{{if isChief=='0'}}副考{{/if}}</td>
                <td title="{{if isconfirm=='1'}}是{{/if}}{{if isconfirm=='0'}}否{{/if}}">{{if isconfirm=='1'}}是{{/if}}{{if isconfirm=='0'}}否{{/if}}</td>
                <td title="{{:confirmTime}}">{{:confirmTime}}</td>
                <td>{{if recordStatus==2}}是{{else}}否{{/if}}</td>
                <td>
                		{{if startTime != ""}}
                			{{:startTime}}
                		{{else}}
                			无
                		{{/if}}
                </td>
                <td>
                		{{if endTime != ""}}
                			{{:endTime}}
                		{{else}}
                			无
                		{{/if}}
                </td>
                <td>
                {{if isconfirm=='0' && recordStatus != '2'}}
					<a href="javascript:;" class="color_a assess">确认</a>
                {{else}}
                		<a href="javascript:;" class="color_a unAssess">取消</a>
                {{/if}}
                <input type="hidden" id="{{:id}}"/>
               <!-- <a href="../admin/teachersConfirmDetails.jsp?id={{:id}}" class="color_a" target="_blank">详情</a>-->
                </td>
              </tr>
        </script>
</head>

<body>
	<input id="pagename" type="hidden" value="main" />
	<%@ include file="header.jsp"%>
	<%@ include file="mainLeft.jsp"%>
	<div class="main">
		<p class="title"></p>

		<div class="main_header">
			<div class="filter-isconfirm">
				<span>确认状态：</span><a class="lighting" data-value="-1" href="javascript:;">全部</a>
				<a data-value="1" href="javascript:;">二次确认</a> <a data-value="0" href="javascript:;">未二次确认</a>
			</div>
			<div class="filter-type">
				<span>身份类别：</span><a class="lighting" data-value="-1" href="javascript:;">全部</a>
				<a data-value="3" href="javascript:;">教工</a> <a data-value="1" href="javascript:;">考务组</a>
				<a data-value="2" href="javascript:;">研究生</a>
				<a data-value="4" href="javascript:;">本科</a>
				<a data-value="5" href="javascript:;">非师大人员</a>
			</div>
			<div class="filter-isChief">
				<span>监考角色：</span><a class="lighting" data-value="-1" href="javascript:;">全部</a>
				<a data-value="1" href="javascript:;">主考</a> <a data-value="0" href="javascript:;">副考</a>
			</div>
			<div class="filter-date">
				<span>监考时间：</span> 
				<input type="text" id="startTime" readonly />
				-
				<input type="text" id="endTime" readonly />
			</div>
		</div>
		<div class="search_body">
			<input type="text" class="search" placeholder="姓名/拼音/手机号"
				onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
			<i onclick="searchBtn();" class="iconfont"><img src="../img/search.png" alt="搜索"></i>
		</div>
		<div class="main_content">
			<!--<div class="blank"></div>-->
			<a href="javascript:;" id="delete_all">批量删除</a>
			<a href="javascript:;" id="disable_all">批量停用</a>
			<div id="select_page"></div>
		</div>
		<div class="scroll-warp">
			<div class="scroll-inner">
				<table class="teachers_info" cellspacing="0" cellpadding="0" style="width:150%;max-width: 150%;table-layout: auto;">
					<tr class="first_tr">
						<th width="6%">
							<a href="javascript:;" id="select_all">全选</a>
							<a href="javascript:;" id="unselect_all">反选</a>
						</th>
						<th>序号</th>
						<th class="sort_th">姓名<i class="iconfont"><img src=""></i></th>
						<th class="sort_th">手机号<i class="iconfont"><img src=""></i></th>
						<th class="sort_th">民族<i class="iconfont"><img src=""></i></th>
						<th class="sort_th">性别<i class="iconfont"><img src=""></i></th>
						<th class="sort_th">身份<i class="iconfont"><img src=""></i></th>
						<th>安排考场</th>
						<th class="sort_th">角色<i class="iconfont"><img src=""></i></th>
						<th class="sort_th">二次确认<i class="iconfont"><img src=""></i></th>
						<th class="sort_th">确认时间<i class="iconfont"><img src=""></i></th>
						<th class="sort_th">停用<i class="iconfont"><img src=""></i></th>
						<th>开始日期</th>
						<th>结束日期</th>
						<th>二次确认</th>
					</tr>
					<tbody id="queboxCnt"></tbody>
				</table>
			</div>
		</div>

		<div class="modal_assess">
        		<input type="hidden" id="assess_id"/>
        		<p>是否对该教师进行二次确认？</p>
        		<div class="button_g">
        			<input type="button" value="确认" id="sub_assess"/>
        			<input type="button" value="取消" id="close_assess"/>
        		</div>
       </div>
       
        
        <div class="modal_unAssess">
        		<input type="hidden" id="disable_id"/>
        		<p>是否取消二次确认？</p>
        		<div class="button_g">
        			<input type="button" value="确认" id="sub_unAssess"/>
        			<input type="button" value="取消" id="close_unAssess"/>
        		</div>
        </div>
        
        <div class="modal_delete_all">
        		<input type="hidden" id="disable_id"/>
        		<p>是否确认将已选教师所有信息永久删除？</p>
        		<div class="button_g">
        			<input type="button" value="确认" id="sub_delete_all"/>
        			<input type="button" value="取消" id="close_delete_all"/>
        		</div>
        </div>
        
        <div class="modal_disable_all">
        		<input type="hidden" id="disable_id"/>
        		<p>停用时间与原因</p>
        		<input type="checkbox" value="2" class="time_check" disabled="disabled" checked="checked"/>半年
        		<input type="checkbox" value="0" class="time_check"/>永久
        		<input type="hidden" id="time_value" value="2"/>
        		<textarea id="reason" placeholder="请填写停用原因..." autocomplete="off"></textarea>
        		<div class="button_g">
        			<input type="button" value="确认" id="sub_disable_all"/>
        			<input type="button" value="取消" id="close_disable_all"/>
        		</div>
        </div>
        
        <div class="back_top">
			<a href="#">↑返回页面顶部</a>
		</div>
	</div>
	
	<div class="easy_modal">
		<p></p>
		<div class="button_g">
			<input type="button" value="关闭"/>
		</div>
	</div>
	<%@ include file="footer.jsp"%>



	<script src="../js/app.js"></script>
	<script type="text/javascript" src="../js/jsrender.min.js"></script>
	<script type="text/javascript" src="../js/query.js"></script>
	<script src="../js/paging.js"></script>
	<script src="../laydate-v1.1/laydate/laydate.js"></script>
	<script type="text/javascript" src="../js/teachersConfirm.js"></script>
	<script>
		
	</script>
</body>

</html>
