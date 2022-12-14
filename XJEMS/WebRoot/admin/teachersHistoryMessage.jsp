<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>监考老师详情</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<link rel="stylesheet" href="../css/tableStlye.css">
		<link rel="stylesheet" href="../css/mainBox.css" />
		<link rel="stylesheet" href="../css/paging.css" />
		<link rel="stylesheet" href="../css/teachersHistoryMessage.css">
		<link rel="stylesheet" href="../css/easyModal.css" />
		<script id="queboxTpl" type="text/template">
			<p class="title">监考教师信息</p>
			<a href="../admin/setTeachers_.jsp?id={{:id}}" class="set" target="_blank">编辑</a>
			<table cellspacing="0" cellpadding="0" class="teachers_msg">
				<tr>
					<td>身份证号码</td>
					<td title="{{:idcard}}">{{:idcard}}</td>
					<td>姓名</td>
					<td title="{{:name}}">{{:name}}</td>
					<td rowspan="5" class="head"><img style="max-width: 100%;" src="{{:photo.sourcePath}}"></td>
				</tr>
				<tr>
					<td>性别</td>
					<td title="{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}">{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}</td>
					<td>民族</td>
					<td title="{{:ethnic}}">{{:ethnic}}</td>
				</tr>
				<tr>
					<td>身份类别</td>
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
					<td>所在学院或部门</td>
					<td title="{{:organization}}">{{:organization}}</td>
				</tr>
				<tr>
					<td>所学专业</td>
					<td title="{{if type=='2' || type=='4'}}{{:major}}{{else}}{{:studyMajor}}{{/if}}">{{if type=='2' || type=='4'}}{{:major}}{{else}}{{:studyMajor}}{{/if}}</td>
					<td>入校时间</td>
					<td title="{{:inschooltime}}">{{:inschooltime}}</td>
				</tr>
				<tr>
					<td>主考经验</td>
					<td title="{{if isChiefExaminer=='1'}}是{{/if}}{{if isChiefExaminer=='0'}}否{{/if}}">{{if isChiefExaminer=='1'}}是{{/if}}{{if isChiefExaminer=='0'}}否{{/if}}</td>
					<td>混合监考经验</td>
					<td title="{{if isMixedExaminer=='1'}}是{{/if}}{{if isMixedExaminer=='0'}}否{{/if}}">{{if isMixedExaminer=='1'}}是{{/if}}{{if isMixedExaminer=='0'}}否{{/if}}</td>
				</tr>
				<tr>
					<td>积分</td>
					<td title="{{:intgral}}">{{:intgral}}</td>
					<td>添加时间</td>
					<td colspan="2" title="{{:createtime}}">{{:createtime}}</td>
				</tr>
				<tr>
					<td>职务</td>
					<td title="{{if type!='2'}}研究生{{/if}}">{{if type!='2'}}研究生{{/if}}</td>
					<td>学工号</td>
					<td colspan="2" title="{{:sid}}">{{:sid}}</td>
	
				</tr>
				<tr>
					<td>监考校区</td>
					<td title="{{:invigilateCampusCN}}">{{:invigilateCampusCN}}</td>
					<td>监考类型</td>
					<td colspan="2" title="{{:invigilateTypeCN}}">{{:invigilateTypeCN}}</td>
				</tr>
				<tr>
					<td>所在年级</td>
					<td title="{{if type=='2' || type=='4'}}{{if studyGrade == 0}}无{{else}}{{:studyGrade}}{{/if}}{{/if}}">{{if type=='2' || type=='4'}}{{if studyGrade == 0}}无{{else}}{{:studyGrade}}{{/if}}{{/if}}</td>
					<td>监考次数</td>
					<td colspan="2" title="{{:invigilateCount}}">{{:invigilateCount}}</td>
				</tr>
				<tr>
					<td>联系方式</td>
					<td title="{{:mobile}}">{{:mobile}}</td>
					<td>银行卡卡号</td>
					<td colspan="2" title="{{:bankCard}}">{{:bankCard}}</td>
				</tr>
				<tr>
					<td>编制属性</td>
					{{if formation==''}}
					<td colspan="4" title="未选择">未选择</td>{{else}}
					<td colspan="4" title="{{:formation}}">{{:formation}}</td>
					{{/if}}
				</tr>
				<tr>
					<td>职业</td>
					{{if occupation==''}}
					<td title="未填写">未填写</td>{{else}}
					<td title="{{:occupation}}">{{:occupation}}</td>{{/if}}
					<td>开户行所属地区</td>
					{{if bankOrg==''}}
					<td colspan="2" title="未填写">未填写</td>{{else}}
					<td colspan="2" title="{{:bankOrg}}">{{:bankOrg}}</td>{{/if}}
				</tr>
				<tr>
					<td>开户行</td>
					{{if bankName==''}}
					<td title="未填写">未填写</td>{{else}}
					<td title="{{:bankName}}">{{:bankName}}</td>{{/if}}
					<td>电子信箱</td>
					{{if email==''}}
					<td colspan="2" title="未填写">未填写</td>{{else}}
					<td colspan="2" title="{{:email}}">{{:email}}</td>{{/if}}
				</tr>
				<tr>
					<td>特长</td>
					<td colspan="4" title="{{:specialty}}">{{:specialty}}</td>
				</tr>
				{{if checkStatus == '0'}}
					<tr>
						<td>审核不通过原因</td>
						<td colspan="4" title="{{:checkReason}}">{{:checkReason}}</td>
					</tr>
				{{/if}}
				
				{{if status == '0'}}
					<tr>
						<td>停用原因</td>
						<td colspan="4" title="{{:reason}}">{{:reason}}</td>
					</tr>
				{{/if}}
				
				{{if type == '2' || type == '4'}}
					<tr>
						<td>学制时长</td>
						{{if studyLength == 0}}
							<td colspan="4">无</td>
						{{else}}
							<td colspan="4" title="{{:studyLength}}">{{:studyLength}}年制</td>
						{{/if}}
					</tr>
				{{/if}}

				
				
				{{if idcardPhoto.id != 1}}
					<tr class="idCard_tr">
						<td>身份证个人信息照片</td>
						<td colspan="4" style="padding:10px">
							<img src="{{:idcardPhoto.sourcePath}}" id="card_img"/>
						</td>
					</tr>
				{{else}}
					<tr>
						<td>身份证个人信息照片</td>
						<td colspan="4">
							该教师未上传身份证照片
						</td>
					</tr>
				{{/if}}
			</table>
			
		</script>
		<script id="historyInfo" type="text/template">
			<tr {{if #index % 2==1 }}class="odd" {{/if}}>
				<td>{{:#index+1}}</td>
				<td title="{{:examName}}">{{:examName}}</td>
				<td title="{{:roomIndex}} - {{:roomAdd}}">{{:roomIndex}} - {{:roomAdd}}</td>
				<td title="{{:applytime}}">{{:applytime}}</td>
				<td title="{{:score}}">{{:score}}</td>
				<td title="{{:reason}}">{{:reason}}</td>
			</tr>
		</script>
	</head>

	<body>
		<input id="pagename" type="hidden" value="teachersMessage" />
		<%@ include file="header.jsp"%>
			<%@ include file="mainLeft.jsp"%>

				<div class="main">
					<div id="queboxCnt"></div>
					
					
					




					<div class="history_title">
						<p class="title">监考教师历史信息</p>

						<div id="select_page">
						</div>
					</div>

					<div class="scroll-warp">
						<div class="scroll-inner">
							<table class="teachers_info" cellspacing="0" cellpadding="0" style="width:130%;max-width: 130%;table-layout: auto;">
								<tr class="first_tr">
									<th>序号</th>
									<th>考试名称</th>
									<th>监考考场</th>
									<th>申报时间</th>
									<th>积分</th>
									<th>评价</th>
								</tr>
		                        <tbody id="historyContent"></tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="easy_modal">
		        		<p></p>
		        		<div class="button_g">
		        			<input type="button" value="关闭"/>
		        		</div>
		        </div>				<%@ include file="footer.jsp"%>
					<script src="../js/app.js"></script>
					<script type="text/javascript" src="../js/jsrender.min.js"></script>
					<script type="text/javascript" src="../js/query.js"></script>
					<script src="../js/paging.js"></script>
					<script type="text/javascript" src="../js/teachersHistoryMessage.js"></script>
	</body>

	</html>