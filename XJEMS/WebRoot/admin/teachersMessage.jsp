<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title>监考教师信息</title>

		<link rel="stylesheet" href="../css/button_group.css">
		<link rel="stylesheet" href="../css/select_page_bar.css">
		<link rel="stylesheet" href="../css/teachers_message.css">
		<link rel="stylesheet" href="../css/button_group.css">
		<link rel="stylesheet" href="../css/mainBox.css" />
		<link rel="stylesheet" href="../css/paging.css" />
		<link rel="stylesheet" href="../css/teachersMessage.css">
		<link rel="stylesheet" href="../css/easyModal.css" />

		<script id="queboxTpl" type="text/template">
			<div class="teacher_msg">
				<div class="msg_header b_b">
					<img src="{{:photo.sourcePath}}">
					<p class="msg_text">
						<input type="checkbox" class="checkbox" data-id="{{:id}}" />
						<span id="hover_change"><a href="../admin/teachersHistoryMessage.jsp?id={{:id}}" target="_blank">{{:name}}</a></span>
						<i></i>
						<span>{{if sex == '1'}}男{{else}}女{{/if}}</span>
						<i></i>
						<span class="num">{{:intgral}}</span><em class="f">分</em>
					</p>

					<div class="msg_header_button">
						{{if isApply == 0}}
						<a data-id="{{:id}}" class="apply">申请监考</a>{{/if}}
						<a href="../admin/setTeachers_.jsp?id={{:id}}" target="_blank">编辑</a>
						{{if status == 0}}
						<a class="action" data-id="{{:id}}">启用</a>
						{{else}}
						<a class="delete" data-id="{{:id}}">停用</a>
						{{/if}}
						<a class="delete_teacher" data-id="{{:id}}">删除</a>
					</div>
				</div>

				<div class="msg_body">
					<div class="msg_content b_r">
						<p>身份证号：<span title="{{:idcard}}">{{:idcard}}</span></p>
						<p>民族：<span title="{{:ethnic}}">{{:ethnic}}</span></p>
						<p>专业：<span title="{{:major}}">{{:major}}</span></p>
						<p>身份类型：
                         <span title="{{if type==1}}考务组{{/if}}
                           {{if type==2}}研究生{{/if}}
                           {{if type==3}}教工{{/if}}
                           {{if type==4}}本科{{/if}}
                           {{if type==5}}非师大人员{{/if}}
                         ">{{if type==1}}考务组{{/if}}
                           {{if type==2}}研究生{{/if}}
                           {{if type==3}}教工{{/if}}
                           {{if type==4}}本科{{/if}}
                           {{if type==5}}非师大人员{{/if}}
                         </span>
						</p>
						<p>职务：<span title="{{:jobDuty}}">{{:jobDuty}}</span></p>
						<p>职业： {{if occupation==''}}<span title="未填写">未填写</span>
						{{else}}<span title="{{:occupation}}">{{:occupation}}</span>{{/if}}
						</p>
					</div>

					<div class="msg_content b_r">
						<p>手机号：<span title="{{:mobile}}">{{:mobile}}</span></p>
						<p>所在学院：<span title="{{:organization}}">{{:organization}}</span></p>
						<p>入校时间：<span title="{{:inschooltime}}">{{:inschooltime}}</span></p>
						<p>状态：<span title="{{if status == '1'}}正常{{else}}停用{{/if}}">{{if status == '1'}}正常{{else}}停用{{/if}}</span></p>
						<p>采集时间：<span title="{{:createtime}}">{{:createtime}}</span></p>
						<p>电子信箱： {{if email==''}}<span title="未填写">未填写</span>
						{{else}}<span title="{{:email}}">{{:email}}</span>{{/if}}</p>
					</div>

					<div class="msg_content b_r">
						<p>主考经验：<span title="{{if isChiefExaminer == '1'}}有{{else}}无{{/if}}">{{if isChiefExaminer == '1'}}有{{else}}无{{/if}}</span></p>
						<p>混合监考经验：<span title="{{if isMixedExaminer == '1'}}有{{else}}无{{/if}}">{{if isMixedExaminer == '1'}}有{{else}}无{{/if}}</span></p>
						<p>特长：<span title="{{:specialty}}">{{:specialty}}</span></p>
						<p>所在年级：<span title="{{if studyGrade == 0}}无{{else}}{{:studyGrade}}{{/if}}">{{if studyGrade == 0}}无{{else}}{{:studyGrade}}{{/if}}</span></p>
						{{if type == 2 || type == 4}}
						<p>学制时长：<span title="{{if studyLength == 0}}无{{else}}{{:studyLength}}年制{{/if}}">{{if studyLength == 0}}无{{else}}{{:studyLength}}年制{{/if}}</span></p>
						{{/if}}
						<p>编制属性： {{if formation==''}}<span title="未选择">未选择</span>
						{{else}}<span title="{{:formation}}">{{:formation}}</span>{{/if}}</p>
					</div>
					<div class="msg_content">
						<p>学工号：<span title="{{:sid}}">{{:sid}}</span></p>
						<p>监考校区：<span title="{{:invigilateCampusCN}}">{{:invigilateCampusCN}}</span></p>
						<p>监考类型：<span title="{{:invigilateTypeCN}}">{{:invigilateTypeCN}}</span></p>
						<p>交通银行卡号：<span title="{{:bankCard}}">{{:bankCard}}</span></p>
						<p>开户行所属地区： {{if bankOrg==''}}<span title="未填写">未填写</span>
						{{else}}<span title="{{:bankOrg}}">{{:bankOrg}}</span>{{/if}}</p>
						<p>开户行： {{if bankName==''}}<span title="未填写">未填写</span>
						{{else}}<span title="{{:bankName}}">{{:bankName}}</span>{{/if}}</p>						
					</div>
				</div>
			</div>
		</script>
	</head>

	<body>
		<input id="pagename" type="hidden" value="teachersMessage" />
		<%@ include file="header.jsp"%>
		<%@ include file="mainLeft.jsp"%>

		<div class="main">
			<div class="main_header">
				<div class="filter-type">
					<span>身份类别：</span>
					<a class="lighting" data-value="-1" href="javascript:;">全部</a>
					<a data-value="3" href="javascript:;">教工</a>
					<a data-value="1" href="javascript:;">考务组</a>
					<a data-value="2" href="javascript:;">研究生</a>
					<a data-value="4" href="javascript:;">本科</a>
					<a data-value="5" href="javascript:;">非师大人员</a>
				</div>
				<div class="filter-status">
					<span>教师状态：</span>
					<a class="lighting" data-value="-1" href="javascript:;">全部</a>
					<a data-value="1" href="javascript:;">正常</a>
					<a data-value="0" href="javascript:;">停用</a>
				</div>
				<div class="filter-isChief">
					<span>主考经验：</span>
					<a class="lighting" data-value="-1" href="javascript:;">全部</a>
					<a data-value="1" href="javascript:;">是</a>
					<a data-value="0" href="javascript:;">否</a>
				</div>

				<div class="filter-isMixed">
					<span>副考经验：</span>
					<a class="lighting" data-value="-1" href="javascript:;">全部</a>
					<a data-value="1" href="javascript:;">是</a>
					<a data-value="0" href="javascript:;">否</a>
				</div>
				<div class="filter-studyLength">
					<span>学制筛选：</span>
					<a class="lighting" data-value="-1" href="javascript:;">全部</a>
					<a data-value="1" href="javascript:;">一年制</a>
					<a data-value="2" href="javascript:;">二年制</a>
					<a data-value="3" href="javascript:;">三年制</a>
					<a data-value="4" href="javascript:;">四年制</a>
					<a data-value="0" href="javascript:;">其他</a>
				</div>
				<div class="filter-studyGrade">
					<span>所在年级：</span>
					<a class="lighting" href="javascript:;" data-value="">全部</a>
				</div>
			</div>
			<div class="search_body">
				<input type="text" class="search" placeholder="姓名/拼音/身份证/手机号" onkeypress="if(event.keyCode==13) {searchBtn();return false;}" autocomplete="off" />
				<i onclick="searchBtn();" class="iconfont"><img src="../img/search.png"></i>
			</div>
			<div class="more">
				<p>
					<span>查看更多筛选<i class="iconfont"><img src="../img/bottom_r.png"></i></span>
					<a href="javascript:;" id="load-list">导出</a>
					<a href="javascript:;" id="select_all_">全选</a>
					<!--<a href="javascript:;" id="working" class="list-dis">列表结果禁用</a>-->
					<a href="javascript:;" id="delete_all" class="list-dis">批量禁用</a>
					<a href="../admin/setTeachers.jsp" class="add_teacher" target="_blank">添加监考人员</a>
				</p>
			</div>

			<div class="select_bar">
				<ul class="select_bar_left">
					<li>
						<a href="javascript:;">姓名<i class="iconfont"><img src="../img/toptop_b_14.png"></i></a>
					</li>
					<li>
						<a href="javascript:;">性别<i class="iconfont"><img src="../img/toptop_b_14.png"></i></a>
					</li>
					<li>
						<a href="javascript:;">申请时间<i class="iconfont"><img src="../img/toptop_b_14.png"></i></a>
					</li>
				</ul>
				<div id="select_page"></div>
			</div>

			<div id="queboxCnt"></div>

			<div class="back_top">
				<a href="#">↑返回页面顶部</a>
			</div>

			<div id="modal">
				<input type="hidden" name="id" id="teacherId" />
				<p>停用时间与原因</p>
				<input type="checkbox" value="2" class="time_check" disabled="disabled" checked="checked" />半年
				<input type="checkbox" value="0" class="time_check" />永久
				<input type="hidden" id="time_value" value="2" />
				<textarea id="reason" placeholder="请填写停用原因..." autocomplete="off"></textarea>
				<div class="button_g">
					<a id="submit">提交</a>
					<a id="close">返回</a>
				</div>
			</div>
			
			<div id="modal_" style="display: none;">
				<input type="hidden" name="id" id="teacherId" />
				<p>停用时间与原因</p>
				<input type="checkbox" value="2" class="time_check_" disabled="disabled" checked="checked" />半年
				<input type="checkbox" value="0" class="time_check_" />永久
				<input type="hidden" id="time_value_" value="2" />
				<textarea id="reason_" placeholder="请填写停用原因..." autocomplete="off"></textarea>
				<div class="button_g">
					<a id="submit_">提交</a>
					<a id="close_">返回</a>
				</div>
			</div>

			<div class="modal_action">
				<input type="hidden" name="id" id="action_id" />
				<p>确认启用该教师？</p>
				<div class="button_g">
					<input type="button" value="确定" id="resume" />
					<input type="button" value="取消" />
				</div>
			</div>

			<div class="easy_modal">
				<p>提示</p>
				<div class="button_g">
					<input type="button" value="关闭" />
				</div>
			</div>

			<div id="delete_teacher_modal">
				<p>确认将该教师所有信息永久删除？</p>
				<div class="button_g">
					<input type="button" value="确定" />
					<input type="button" value="取消" />
				</div>
			</div>
			<div id="apply_modal">
				<input type="hidden" id="applyTeacher"/>

				<p>请选择监考时间</p>
				<div class="time-box">
					<span>开始日期：</span>
					<input type="text" id="startTime" readonly="" placeholder="请选择开始日期" autocomplete="off"/>
				</div>
				<div class="time-box">
					<span>结束日期：</span>
					<input type="text" id="endTime" readonly="" placeholder="请选择结束日期" autocomplete="off"/>
				</div>
				
				<div class="button_g">
					<input type="button" value="确定" id="apply" />
					<input type="button" value="取消" />
				</div>
			</div>
		</div>
		<div id="bg">
        		<img src="../img/select2-spinner.gif" alt="" />
        </div>
		<script src="../js/app.js"></script>
		<script type="text/javascript" src="../js/jsrender.min.js"></script>
		<script type="text/javascript" src="../js/query.js"></script>
		<script src="../js/paging.js"></script>
		<script src="../js/jquery-form.js"></script>
		<script src="../js/laydate/laydate.js"></script>
		<script type="text/javascript" src="../js/teachersMessage.js"></script>
		<%@ include file="footer.jsp"%>
	</body>

</html>
<script>
	$(document).ready(function() {
		/* $(".more p span").click(function() {
			if($(".main_header").height() == 85) {
				if($(".filter-type").find(".lighting").attr("data-value") == 2) {
					$(".main_header").css({
						"height": "150px"
					});
				} else {
					$(".main_header").css({
						"height": "220px"
					});
				}
				$(this).html("收起更多筛选<i class='iconfont'><img src='../img/top_r.png'></i>");
			} else {
				$(".main_header").css({
					"height": "85px"
				});
				$(this).html("收起更多筛选<i class='iconfont'><img src='../img/bottom_r.png'></i>")
			}
		}); */
				$("#bg").height($('body').height());
				$(".more p span").click(function() {
					if($(".main_header").height() == 85) {
						$(".main_header").css({
							"height": "260px"
						});
						$(this).html("收起更多筛选<i class='iconfont'><img src='../img/top_r.png'></i>");
					} else {
						$(".main_header").css({
							"height": "85px"
						});
						$(this).html("收起更多筛选<i class='iconfont'><img src='../img/bottom_r.png'></i>")
					}
				});
	});
</script>