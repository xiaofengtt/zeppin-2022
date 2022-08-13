<%@page import="org.apache.struts2.components.Include"%>
<%@page import="cn.zeppin.action.sso.UserSession"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>个人信息能力详细信息</title>

<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">
<link rel="stylesheet" href="../css/hsdstyle.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="../css/metro/blue/jtable.css" rel="stylesheet"
	type="text/css">
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script>
<script type="text/javascript" src="../js/Chart.js"></script>
<script src="../js/excanvas.js" type="text/javascript"></script>

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>

</head>
<body>
	<div class="wraper_block">
		<div class="wraper">
			<div class="wraperin">
				<div class="middcontent">
					<div class="simple_detail">

						<div class="cont" style="padding-top: 20px; "
							id="result_div">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tbody>
									<tr>
										<td colspan="13">
											<table width="100%" cellspacing="0" cellpadding="0">
												<tbody>
													<tr>
														<td
															style="text-align: center; color: #000080; font-size: large;">
															信息技术应用能力诊断测评报告</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td style="color: #000080; font-size: large;">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;信息技术应用能力是信息化社会教师必备专业能力，为全面提升中小学（幼儿园）教师的信息技术应用能力，促进信息技术与教育教学深度融合，教育部于2014年5月27日颁布了《中小学教师信息技术应用能力标准（试行）》，该标准根据我国中小学校信息技术实际条件的不同、师生信息技术应用情境的差异，对中小学教师在教育教学和专业发展中应用信息技术提出了基本要求和发展性要求。其中，<span
															style="color: Green;">I. 应用信息技术优化课堂教学</span>的能力为基本要求，主要包括教师利用信息技术进行讲解、启发、示范、指导、评价等教学活动应具备的能力；<span
															style="color: Green;">II. 应用信息技术转变学习方式</span>的能力为发展性要求，主要针对教师在学生具备网络学习环境或相应设备的条件下，利用信息技术支持学生开展自主、合作、探究等学习活动所应具有的能力。本标准根据教师的教育教学工作与学习发展主线，将信息技术应用能力区分为技术素养、计划与准备、组织与管理、评估与诊断、学习与发展五个维度。
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td>
															<h2>通过诊断测评，您的信息技术应用能力情况如下：</h2>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<!--<tr><td>&nbsp;</td></tr>-->
													<tr>
														<td>
															<table width="100%" cellspacing="0" cellpadding="0">
																<tbody>
																	<tr>
																		<td colspan="3">
																			<div style="width: 100px; margin-left: 16px;">
																				&nbsp;&nbsp;
																				<h2>能力自评</h2>
																			</div>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<table width="200" border="1" cellspacing="0"
																				cellpadding="0" style="margin-left: 25px;">
																				<tbody>
																					<tr style="background-color: #4f81bd;">
																						<td style="width: 100px;"></td>
																						<td style="width: 50px;">&nbsp;&nbsp;I</td>
																						<td style="width: 50px;">&nbsp;&nbsp;II</td>
																					</tr>
																					<tr>
																						<td style="background: #e4e4e4;">技术素养</td>
																						<td id="nl1"></td>
																						<td id="nl6"></td>
																					</tr>
																					<tr>
																						<td style="background: #e4e4e4;">计划与准备</td>
																						<td id="nl2"></td>
																						<td id="nl7"></td>
																					</tr>
																					<tr>
																						<td style="background: #e4e4e4;">组织与管理</td>
																						<td id="nl3"></td>
																						<td id="nl8"></td>
																					</tr>
																					<tr>
																						<td style="background: #e4e4e4;">评估与诊断</td>
																						<td id="nl4"></td>
																						<td id="nl9"></td>
																					</tr>
																					<tr>
																						<td style="background: #e4e4e4;">学习与发展</td>
																						<td id="nl5"></td>
																						<td id="nl10"></td>
																					</tr>
																				</tbody>
																			</table> <br>
																			<div style="margin-left: 25px;">
																				注：I代表应用信息技术优化课堂<br> 教学，II代表应用信息技术转变学<br>
																				习方式；红色区域代表不合格，<br> 黄色区域代表合格；绿色区域代<br> 表优秀。
																			</div>
																		</td>
																		<td style="padding-bottom: 70px;"><canvas
																				id="canvas_1" height="300" width="300"
																				style="margin-left: -50px;"></canvas> <br>
																			I，应用信息技术优化课堂教学</td>
																		<td style="padding-bottom: 70px;"><canvas
																				id="canvas_2" height="300" width="300"
																				style="margin-left: -30px;"></canvas> <br>
																			II，应用信息技术转变学习方式</td>
																	</tr>
																</tbody>
															</table>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td align="center">
															<!--<input style="text-align: center; border: 3px solid yellow;" type="button" onclick="showlist(); value="查看详细解释" />-->
															<div
																style="text-align: center; color: Blue; width: 125px; cursor: pointer;"
																id="bt_xx" onclick="showlist();">
																<b>查看详细解释</b>
															</div>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td><div id="tr_xx" style="display: none;">
																<table border="1" id="t1" style="text-align: center;">
																	<tbody>
																		<tr style="background-color: #f3e7c9">
																			<td style="width: 150px;"></td>
																			<td style="width: 300px;">I.应用信息技术优化课堂教学</td>
																			<td style="width: 70px;">水平</td>
																			<td style="width: 300px;">II.应用信息技术转变学习方式</td>
																			<td style="width: 70px;">水平</td>
																		</tr>
																		<tr>
																			<td>技术素养</td>
																			<td style="text-align: left;">1.
																				理解信息技术对改进课堂教学的作用，具有主动运用信息技术优化课堂教学的意识。<br>2.了解多媒体教学环境的类型与功能，熟练操作常用设备。<br>3.了解与教学相关的通用软件及学科软件的功能及特点，并能熟练应用。<br>4.通过多种途径获取数字教育资源，掌握加工、制作和管理数字教育资源的工具与方法。<br>5.具备信息道德与信息安全意识，能够以身示范。


																			</td>
																			<td id="td1"></td>
																			<td style="text-align: left;">1.
																				了解信息时代对人才培养的新要求，具有主动探索和运用信息技术变革学生学习方式的意识。<br>2.
																				掌握互联网、移动设备及其他新技术的常用操作，了解其对教育教学的支持作用。<br>3.
																				探索并使用支持学生自主、合作、探究学习的网络教学平台等技术资源。<br>4.
																				利用技术手段整合多方资源，实现学校、家庭、社会相连接，拓展学生的学习空间。<br>5.
																				帮助学生建立信息道德与信息安全意识，培养良好行为习惯。
																			</td>
																			<td id="td6"></td>
																		</tr>
																		<tr>
																			<td>计划与准备</td>
																			<td style="text-align: left;">
																				1.依据课程标准、学习目标、学生特征和技术条件，选择适当的教学方法，找准运用信息技术解决教学问题的契合点。<br>2.设计有效实现学习目标的信息化教学过程。<br>3.根据教学需要，合理选择与使用技术资源。<br>4.加工制作有效支持课堂教学的数字教育资源。<br>5.确保相关设备与技术资源在课堂教学环境中正常使用。<br>6.预见信息技术应用过程中可能出现的问题，制订应对方案。


																			</td>
																			<td id="td2"></td>
																			<td style="text-align: left;">
																				1.依据课程标准、学习目标、学生特征和技术条件，选择适当的教学方法，确定运用信息技术培养学生综合能力的契合点。<br>2.设计有助于学生进行自主、合作、探究学习的信息化教学过程与学习活动。<br>3.合理选择与使用技术资源，为学生提供丰富的学习机会和个性化的学习体验。<br>4.设计学习指导策略与方法，促进学生的合作、交流、探索、反思与创造。<br>5.确保学生便捷、安全地访问网络和利用资源。<br>6.预见学生在信息化环境中进行自主、合作、探究学习可能遇到的问题，制订应对方案。


																			</td>
																			<td id="td7"></td>
																		</tr>
																		<tr>
																			<td>组织与管理</td>
																			<td style="text-align: left;">
																				1.利用技术支持，改进教学方式，有效实施课堂教学。<br>2.让每个学生平等地接触技术资源，激发学生学习兴趣，保持学生学习注意力。<br>3.在信息化教学过程中，观察和收集学生的课堂反馈，对教学行为进行有效调整。<br>4.灵活处置课堂教学中因技术故障引发的意外状况。<br>5.鼓励学生参与教学过程，引导他们提升技术素养并发挥技术优势。


																			</td>
																			<td id="td3"></td>
																			<td style="text-align: left;">
																				1.利用技术支持，转变学习方式，有效开展学生自主、合作、探究学习。<br>2.让学生在集体、小组和个别学习中平等获得技术资源和参与学习活动的机会。<br>3.有效使用技术工具收集学生学习反馈，对学习活动进行及时指导和适当干预。<br>4.灵活处理学生在信息化环境中开展学习活动发生的意外状况。<br>5.支持学生积极探索使用新的技术资源，创造性地开展学习活动。


																			</td>
																			<td id="td8"></td>
																		</tr>
																		<tr>
																			<td>评估与诊断</td>
																			<td style="text-align: left;">
																				1.根据学习目标科学设计并实施信息化教学评价方案。<br>2.尝试利用技术工具收集学生学习过程信息，并能整理与分析，发现教学问题，提出针对性的改进措施。<br>3.尝试利用技术工具开展测验、练习等工作，提高评价工作效率。<br>4.尝试建立学生学习电子档案，为学生综合素质评价提供支持。


																			</td>
																			<td id="td4"></td>
																			<td style="text-align: left;">
																				1.根据学习目标科学设计并实施信息化教学评价方案，并合理选取或加工利用评价工具。<br>2.综合利用技术手段对学情进行科学分析，为促进学生的个性化学习提供依据。<br>3.引导学生利用评价工具开展自评与互评，做好过程性和终结性评价。<br>4.利用技术手段持续收集学生学习过程及结果的关键信息，建立学生学习电子档案，为学生综合素质评价提供支持。


																			</td>
																			<td id="td9"></td>
																		</tr>
																		<tr>
																			<td>学习与发展</td>
																			<td style="text-align: left;">
																				1.理解信息技术对教师专业发展的作用，具有主动运用信息技术促进自我反思与发展的意识。<br>2.利用教师网络研修社区，积极参与技术支持的专业发展活动，养成网络学习的习惯，不断提升教育教学能力。<br>3.利用信息技术与专家和同行建立并保持业务联系，依托学习共同体，促进自身专业成长。<br>4.掌握专业发展所需的技术手段和方法，提升信息技术环境下的自主学习能力。<br>5.有效参与信息技术支持下的校本研修，实现学用结合。


																			</td>
																			<td id="td5"></td>
																			<td style="text-align: left;">
																				1.理解信息技术对教师专业发展的作用，具有主动运用信息技术促进自我反思与发展的意识。<br>2.利用教师网络研修社区，积极参与技术支持的专业发展活动，养成网络学习的习惯，不断提升教育教学能力。<br>3.利用信息技术与专家和同行建立并保持业务联系，依托学习共同体，促进自身专业成长。<br>4.掌握专业发展所需的技术手段和方法，提升信息技术环境下的自主学习能力。<br>5.有效参与信息技术支持下的校本研修，实现学用结合。


																			</td>
																			<td id="td10"></td>
																		</tr>
																	</tbody>
																</table>
															</div></td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td>
															<div style="width: 100px; margin-left: 16px;">
																&nbsp;&nbsp;
																<h2>课程推荐</h2>
															</div>
														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;《中小学教师信息技术应用能力培训课程标准(试行)》依据能力标准对中小学教师信息技术应用能力的基本要求和发展性要求，设置“应用信息技术优化课堂教学”“应用信息技术转变学习方式”和“应用信息技术支持教师专业发展”3
															个系列的课程，共 27 个主题，帮助教师提升信息技术素养，应用信息技术提高学科教学能力、促进专业发展。 <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据您的能力自评结果，推荐课程主题如下表，您在选课时可参考此表，有针对性的选择相应课程进行学习。

														</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
													<tr>
														<td id="tres"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
													</tr>
													<tr>
														<td id="ts"></td>
													</tr>
													<tr>
														<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
													</tr>
													<tr>
														<td align="right"><a
															href="../HSDTesting/resource/nlbz.zip"
															style="text-decoration: none;"><b
																style="color: Blue; cursor: pointer;">下载能力标准</b></a>&nbsp;&nbsp;<a
															href="../HSDTesting/resource/kczt.zip"
															style="text-decoration: none;"><b
																style="color: Blue; cursor: pointer;">下载课程主题</b></a></td>
													</tr>
													<tr>
														<td>&nbsp;</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(function() {
			
			var rc = ["T1信息技术引发的教育教学变革", "T2多媒体教学环境认知与常用设备使用", "T3学科资源检索与获取", "T4素材的处理与加工", "T5多媒体课件制作", "T6学科软件的使用", "T7信息道德与信息安全", "T8简易多媒体教学环境下的学科教学", "T9交互多媒体环境下的学科教学", "T10学科教学资源支持下的课程教学", "T11技术支持的课堂导入", "T12技术支持的课堂讲授", "T13技术支持的学生技能训练与指导", "T14技术支持的总结与复习", "T15技术支持的教学评价", "T16教学空间的构建与管理", "T17网络教学平台的应用", "T18适用于移动设备的软件应用", "T19网络教学环境中的自主合作探究学习", "T20移动学习环境中的自主合作探究学习", "T21技术支持的探究学习任务设计", "T22技术支持的学习小组的组织与管理", "T23技术支持的学习过程监控", "T24技术支持的学习评价", "T25中小学教师信息技术应用能力标准解读", "T26教师工作坊与教师专业发展", "T27网络研修社区与教师专业发展"];
	        function getRandom(n) {
	            return Math.floor(Math.random() * n + 1)
	        }
			
			function hsdTests(id) {
	
				$.getJSON('../teacher/hsdTest_getHsdTestInfo.action?id='+id, function(data) {
					
					if (data.status == 'OK') {
						
						var f_data = [];
			            var s_data = [];
			           	
			            var score = data.score.split(",");
			            var jhyzb1 = parseFloat(score[1]); //1计划与准备
			            var zzygl1 = parseFloat(score[2]); //1组织与管理
			            var pgyzd1 = parseFloat(score[3]);//1评估与诊断
			            var jssy1 = parseFloat(score[0]);  //1技术素养
			            var xxyfz1 = parseFloat(score[4]);//1学习与发展
			            
			            var jhyzb2 = parseFloat(score[6]); //2计划与准备
			            var zzygl2 = parseFloat(score[7]); //2组织与管理
			            var pgyzd2 = parseFloat(score[8]); //2评估与诊断
			            var jssy2 = parseFloat(score[5]);   //2技术素养
			            var xxyfz2 = parseFloat(score[9]); //2学习与发展
			            
			            
			            var s = "<table border='1'><tr style='background-color:#5b9bd5; text-align:center;'><td style='width:200px;'>类别</td><td style='width:400px;'>I.应用信息技术优化课堂教学</td><td style='text-align:center; width:400px;'>II.应用信息技术转变学习方式</td></tr>";
			            var result = [];
			            var rec = data.recommend.split(',');
			            var f_data = [];
			            var s_data = [];
			            var n = 0;
			            for (var i = 0; i < rc.length; i++) {
			                for (var j = 0; j < rec.length; j++) {
			                    if (rc[i] == rec[j]) {
			                        result[n] = rc[i];
			                        n++;
			                    }
			                }
			            }
			            var js1 = ["T1信息技术引发的教育教学变革;T1", "T2多媒体教学环境认知与常用设备使用;T2", "T3学科资源检索与获取;T3", "T4素材的处理与加工;T4", "T5多媒体课件制作;T5", "T6学科软件的使用;T6", "T7信息道德与信息安全;T7"];
			            var js2 = ["T16教学空间的构建与管理;T16", "T17网络教学平台的应用;T17", "T18适用于移动设备的软件应用;T18"];
			            var zh1 = ["T8简易多媒体教学环境下的学科教学;T8", "T9交互多媒体环境下的学科教学;T9", "T10学科教学资源支持下的课程教学;T10"];
			            var zh2 = ["T19网络教学环境中的自主合作探究学习;T19", "T20移动学习环境中的自主合作探究学习;T20"];
			            var zt1 = ["T11技术支持的课堂导入;T11", "T12技术支持的课堂讲授;T12", "T13技术支持的学生技能训练与指导;T13", "T14技术支持的总结与复习;T14", "T15技术支持的教学评价;T15"];
			            var zt2 = ["T21技术支持的探究学习任务设计;T21", "T22技术支持的学习小组的组织与管理;T22", "T23技术支持的学习过程监控;T23", "T24技术支持的学习评价;T24"];
			            var jszt = ["T25中小学教师信息技术应用能力标准解读;T25", "T26教师工作坊与教师专业发展;T26", "T27网络研修社区与教师专业发展;T27"];
	
			            var fm1 = ["T1信息技术引发的教育教学变革", "T2多媒体教学环境认知与常用设备使用", "T3学科资源检索与获取", "T4素材的处理与加工", "T5多媒体课件制作", "T6学科软件的使用", "T7信息道德与信息安全", "T8简易多媒体教学环境下的学科教学", "T9交互多媒体环境下的学科教学", "T10学科教学资源支持下的课程教学", "T11技术支持的课堂导入", "T12技术支持的课堂讲授", "T13技术支持的学生技能训练与指导", "T14技术支持的总结与复习", "T15技术支持的教学评价"];
			            var fm2 = ["T16教学空间的构建与管理", "T17网络教学平台的应用", "T18适用于移动设备的软件应用", "T19网络教学环境中的自主合作探究学习", "T20移动学习环境中的自主合作探究学习", "T21技术支持的探究学习任务设计", "T22技术支持的学习小组的组织与管理", "T23技术支持的学习过程监控", "T24技术支持的学习评价"];
			            var fm3 = ["T25中小学教师信息技术应用能力标准解读", "T26教师工作坊与教师专业发展", "T27网络研修社区与教师专业发展"];
			            var nf1 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < fm1.length; j++) {
			                    if (result[i] == fm1[j]) {
			                        nf1++;
			                    }
			                }
			            }
			            var nf2 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < fm2.length; j++) {
			                    if (result[i] == fm2[j]) {
			                        nf2++;
			                    }
			                }
			            }
			            var nf3 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < fm3.length; j++) {
			                    if (result[i] == fm3[j]) {
			                        nf3++;
			                    }
			                }
			            }
	
			            var str1 = "";
			            var str2 = "";
			            var jn1 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < js1.length; j++) {
			                    if (result[i] == js1[j].split(';')[0]) {
			                        jn1++;
			                        str1 = str1 + js1[j] + ",";
			                    }
			                }
			            }
			            str1 = str1.substring(0, str1.length - 1);
	
	
			            var jn2 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < js2.length; j++) {
			                    if (result[i] == js2[j].split(';')[0]) {
			                        jn2++;
			                        str2 = str2 + js2[j] + ",";
			                    }
			                }
			            }
			            str2 = str2.substring(0, str2.length - 1);
			            var j1 = jn1 > jn2 ? jn1 : jn2;
			            for (var i = 0; i < j1; i++) {
			                if (i == 0) {
			                    s = s + "<tr><td rowspan=" + j1 + " style='text-align:center;'>技术素养类</td>";
			                }
			                else {
			                    s = s + "<tr>";
			                }
			                if (jn1 > jn2) {
			                    s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str1.split(',')[i].split(';')[1] + ".docx>" + str1.split(',')[i].split(';')[0] + "</a></td>";
			                    if (i < jn2) {
			                        s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str2.split(',')[i].split(';')[1] + ".docx>" + str2.split(',')[i].split(';')[0] + "</a></td></tr>";
			                    }
			                    else {
			                        s = s + "<td></td></tr>";
			                    }
			                }
			                else {
			                    if (i < jn1) {
			                        s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str1.split(',')[i].split(';')[1] + ".docx>" + str1.split(',')[i].split(';')[0] + "</a></td>";
			                    }
			                    else {
			                        s = s + "<td></td>";
			                    }
			                    s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str2.split(',')[i].split(';')[1] + ".docx>" + str2.split(',')[i].split(';')[0] + "</a></td></tr>";
			                }
			            }
	
			            var str3 = "";
			            var str4 = "";
			            var jn3 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < zh1.length; j++) {
			                    if (result[i] == zh1[j].split(';')[0]) {
			                        jn3++;
			                        str3 = str3 + zh1[j] + ",";
			                    }
			                }
			            }
			            str3 = str3.substring(0, str3.length - 1);
	
			            var jn4 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < zh2.length; j++) {
			                    if (result[i] == zh2[j].split(';')[0]) {
			                        jn4++;
			                        str4 = str4 + zh2[j] + ",";
			                    }
			                }
			            }
			            str4 = str4.substring(0, str4.length - 1);
	
			            var j2 = jn3 > jn4 ? jn3 : jn4;
			            for (var i = 0; i < j2; i++) {
			                if (i == 0) {
			                    s = s + "<tr><td rowspan=" + j2 + " style='text-align:center;'>综合类</td>";
			                }
			                else {
			                    s = s + "<tr>";
			                }
			                if (jn3 > jn4) {
			                    s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str3.split(',')[i].split(';')[1] + ".docx>" + str3.split(',')[i].split(';')[0] + "</a></td>";
			                    if (i < jn4) {
			                        s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str4.split(',')[i].split(';')[1] + ".docx>" + str4.split(',')[i].split(';')[0] + "</a></td></tr>";
			                    }
			                    else {
			                        s = s + "<td></td></tr>";
			                    }
			                }
			                else {
			                    if (i < jn3) {
			                        s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str3.split(',')[i].split(';')[1] + ".docx>" + str3.split(',')[i].split(';')[0] + "</a></td>";
			                    }
			                    else {
			                        s = s + "<td></td>";
			                    }
			                    s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str4.split(',')[i].split(';')[1] + ".docx>" + str4.split(',')[i].split(';')[0] + "</a></td></tr>";
			                }
			            }
	
	
			            var str5 = "";
			            var str6 = "";
			            var jn5 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < zt1.length; j++) {
			                    if (result[i] == zt1[j].split(';')[0]) {
			                        jn5++;
			                        str5 = str5 + zt1[j] + ",";
			                    }
			                }
			            }
			            str5 = str5.substring(0, str5.length - 1);
	
			            var jn6 = 0;
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < zt2.length; j++) {
			                    if (result[i] == zt2[j].split(';')[0]) {
			                        jn6++;
			                        str6 = str6 + zt2[j] + ",";
			                    }
			                }
			            }
			            str6 = str6.substring(0, str6.length - 1);
	
			            var j3 = jn5 > jn6 ? jn5 : jn6;
			            for (var i = 0; i < j3; i++) {
			                if (i == 0) {
			                    s = s + "<tr><td rowspan=" + j3 + " style='text-align:center;'>专题类</td>";
			                }
			                else {
			                    s = s + "<tr>";
			                }
			                if (jn5 > jn6) {
			                    s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str5.split(',')[i].split(';')[1] + ".docx>" + str5.split(',')[i].split(';')[0] + "</a></td>";
			                    if (i < jn6) {
			                        s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str6.split(',')[i].split(';')[1] + ".docx>" + str6.split(',')[i].split(';')[0] + "</a></td></tr>";
			                    }
			                    else {
			                        s = s + "<td></td></tr>";
			                    }
			                }
			                else {
			                    if (i < jn5) {
			                        s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str5.split(',')[i].split(';')[1] + ".docx>" + str5.split(',')[i].split(';')[0] + "</a></td>";
			                    }
			                    else {
			                        s = s + "<td></td>";
			                    }
			                    s = s + "<td><a style='color:blue;' href=../HSDTesting/resource/" + str6.split(',')[i].split(';')[1] + ".docx>" + str6.split(',')[i].split(';')[0] + "</a></td></tr>";
			                }
			            }
	
			            var str7 = "";
			            for (var i = 0; i < result.length; i++) {
			                for (var j = 0; j < jszt.length; j++) {
			                    if (result[i] == jszt[j].split(';')[0]) {
			                        str7 = str7 + jszt[j] + ",";
			                    }
			                }
			            }
			            str7 = str7.substring(0, str7.length - 1);
			            for (var i = 0; i < str7.split(',').length; i++) {
			                if (i == 0) {
			                    s = s + "<tr><td rowspan=" + str7.split(',').length + " style='text-align:center;'>教师专业发展</td>";
			                }
			                else {
			                    s = s + "<tr>";
			                }
			                s = s + "<td colspan=2><a style='color:blue;' href=../HSDTesting/resource/" + str7.split(',')[i].split(';')[1] + ".docx>" + str7.split(',')[i].split(';')[0] + "</a></td>";
			            }
	
			            if (nf1 > 6 && nf2 > 6) {
			                document.getElementById("ts").innerHTML = "<b>您的信息技术应用水平还有较大提升空间，建议先选学“应用信息技术优化课堂教学”下面的主题来学习。</b>";
			            }
			            else if (nf1 == 0 && nf2 == 0 && nf3 == 0) {
			                document.getElementById("ts").innerHTML = "<b>您的信息技术应用水平已经达到了相当的高度，请与本省信息技术应用能力提升工程项目办联系，会对您的情况进行电话访谈。</b>";
			            }
			            else if (nf1<3&& nf2>6) {
			                document.getElementById("ts").innerHTML = "<b>根据您当前填写的设备状态，建议您先选学“应用信息技术转变学习方式“中的综合课程，再逐步学习信息素养类课程和相关专题课程。</b>";
			            }
	
			            s = s + "</table>";
			            $("#tres").html(s);
			            
			            
			            var str = [jssy1, jhyzb1, zzygl1, pgyzd1, xxyfz1, jssy2, jhyzb2, zzygl2, pgyzd2, xxyfz2];
			            f_data[0] = jhyzb1;
			            f_data[1] = zzygl1;
			            f_data[2] = pgyzd1;
			            f_data[4] = jssy1;
			            f_data[3] = xxyfz1;
			            s_data[0] = jhyzb2;
			            s_data[1] = zzygl2;
			            s_data[2] = pgyzd2;
			            s_data[4] = jssy2;
			            s_data[3] = xxyfz2;
			            
			            for (var i = 1; i <= str.length; i++) {
			                if (str[i - 1] <= 0.4) {
			                    document.getElementById("td" + i).style.background = "#ff0000";
			                    document.getElementById("td" + i).innerHTML = "待提高";
			                    document.getElementById("nl" + i).style.background = "#ff0000";
			                }
			                else if (str[i - 1] >= 0.8) {
			                    document.getElementById("td" + i).style.background = "#00ff00";
			                    document.getElementById("td" + i).innerHTML = "优秀";
			                    document.getElementById("nl" + i).style.background = "#00ff00";
			                }
			                else {
			                    document.getElementById("td" + i).style.background = "#ffff00";
			                    document.getElementById("td" + i).innerHTML = "合格";
			                    document.getElementById("nl" + i).style.background = "#ffff00";
			                }
			            }
			            
			          //雷达图
			            var radarChartData_1 = {
			                labels: ["I计划与准备", "I组织与管理", "I评估与诊断", "I学习与发展", "I技术素养"],
			                datasets: [
						                    {
						                        label: "",
						                        fillColor: "rgba(0,102,0,0.2)",
						                        strokeColor: "#006600",
						                        pointColor: "#006600",
						                        pointStrokeColor: "#006600",
						                        pointHighlightFill: "#006600",
						                        pointHighlightStroke: "#006600",
						                        data: f_data
	
						                    }
					                    ]
			            };
	
			            var radarChartData_2 = {
			                labels: ["II计划与准备", "II组织与管理", "II评估与诊断", "II学习与发展", "II技术素养"],
			                datasets: [
						                    {
						                        label: "",
						                        fillColor: "rgba(0,51,225,0.2)",
						                        strokeColor: "#0033FF",
						                        pointColor: "#0033FF",
						                        pointStrokeColor: "#0033FF",
						                        pointHighlightFill: "#0033FF",
						                        pointHighlightStroke: "#0033FF",
						                        data: s_data
	
						                    }
					                    ]
			            };
			            
			          //雷达图
			            var myRadar = new Chart(document.getElementById("canvas_1").getContext("2d")).Radar(radarChartData_1, {
			                animation: false,
	
			                //Boolean - 是否显示出每个点线表
			                scaleShowLine: true,
	
			                //Boolean - 是否显示角线的雷达
			                angleShowLineOut: true,
	
			                //Boolean - Whether to show labels on the scale
			                scaleShowLabels: true,
	
			                // Boolean - 是否显示标尺上的标签
			                scaleBeginAtZero: true,
	
			                //String - 角线的颜色
			                angleLineColor: "rgba(0,0,0,0.3)",
	
			                scaleLineColor: "rgba(0,0,0,0.3)",
	
			                //Number - 像素宽度的线角
			                angleLineWidth: 1,
	
			                //String - 标签
			                pointLabelFontFamily: "'Arial'",
	
			                //String - 点标签的字体重量
	
			                pointLabelFontStyle: "normal",
	
			                //Number - 在像素点标签的字体大小
			                pointLabelFontSize: 10,
	
			                //String - 点颜色标签
			                pointLabelFontColor: "#000000",
	
			                //Boolean - Whether to show a dot for each point
			                pointDot: true,
	
			                //Number - 每个像素点半径
			                pointDotRadius: 3,
	
			                //Number - 点点像素宽度
			                pointDotStrokeWidth: 1,
	
			                //Number - 大量额外添加到半径为迎合命中检测外画点
			                pointHitDetectionRadius: 20,
	
			                //Boolean - 是否要显示一个中风的数据集
			                datasetStroke: false,
	
			                //Number - 数据集笔划像素宽度
			                datasetStrokeWidth: 2,
	
			                //Boolean - 是否有一种颜色填充数据集
			                datasetFill: true,
			                //onAnimationComplete: function () { alert("OK")},
			                scaleOverride: true,
			                scaleSteps: 5,
			                // Number - The value jump in the hard coded scale
			                scaleStepWidth: 0.2,
			                // Number - The scale starting value
			                scaleStartValue: 0
			            });
			            var myRadar_2 = new Chart(document.getElementById("canvas_2").getContext("2d")).Radar(radarChartData_2, {
			                animation: false,
	
			                //Boolean - 是否显示出每个点线表
			                scaleShowLine: true,
	
			                //Boolean - 是否显示角线的雷达
			                angleShowLineOut: true,
	
			                //Boolean - Whether to show labels on the scale
			                scaleShowLabels: true,
	
			                // Boolean - 是否显示标尺上的标签
			                scaleBeginAtZero: true,
	
			                //String - 角线的颜色
			                angleLineColor: "rgba(0,0,0,0.3)",
	
			                scaleLineColor: "rgba(0,0,0,0.3)",
	
			                //Number - 像素宽度的线角
			                angleLineWidth: 1,
	
			                //String - 标签
			                pointLabelFontFamily: "'Arial'",
	
			                //String - 点标签的字体重量
	
			                pointLabelFontStyle: "normal",
	
			                //Number - 在像素点标签的字体大小
			                pointLabelFontSize: 10,
	
			                //String - 点颜色标签
			                pointLabelFontColor: "#000000",
	
			                //Boolean - Whether to show a dot for each point
			                pointDot: true,
	
			                //Number - 每个像素点半径
			                pointDotRadius: 3,
	
			                //Number - 点点像素宽度
			                pointDotStrokeWidth: 1,
	
			                //Number - 大量额外添加到半径为迎合命中检测外画点
			                pointHitDetectionRadius: 20,
	
			                //Boolean - 是否要显示一个中风的数据集
			                datasetStroke: false,
	
			                //Number - 数据集笔划像素宽度
			                datasetStrokeWidth: 2,
	
			                //Boolean - 是否有一种颜色填充数据集
			                datasetFill: true,
			                scaleOverride: true,
			                scaleSteps: 5,
			                // Number - The value jump in the hard coded scale
			                scaleStepWidth: 0.2,
			                // Number - The scale starting value
			                scaleStartValue: 0
			            });
						
					}
					else{
						
					}
					
				});
	
			}
			
			var id = url('?id');
			hsdTests(id);
			
		});
		
		function showlist() {

	        if ($("#tr_xx").css("display") == "none") {
	            $("#tr_xx").show(1000);
	        }
	        else {
	            $("#tr_xx").hide(1000);
	        }
	    }
		
	</script>

</body>
</html>