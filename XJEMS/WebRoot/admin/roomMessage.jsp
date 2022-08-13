<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>分配监考教师</title>
        <link rel="stylesheet" href="../css/paging.css" />
        <link rel="stylesheet" href="../css/mainBox.css" />
        <link rel="stylesheet" href="../css/tableStlye.css">
        <link rel="stylesheet" href="../css/roomMessage.css">
        <link rel="stylesheet" href="../css/easyModal.css" />
        <script src="../js/utf8-jsp/ueditor.config.js"></script>
		<script src="../js/utf8-jsp/ueditor.all.min.js"></script>
		<script src="../js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>

        <script id="queboxTpl" type="text/template">
			<div class="main_left_msg{{if isChecked=='1'}} border_lighting{{/if}}">
				<input type="hidden" id="{{:id}}"/>
				<div class="msg_left">
				    <p>{{:roomIndex}}-{{:roomAddress}}</p>
					<table cellspacing="0" cellpadding="0">
						{{for examnation}}
							<tr>
								<td title="{{:examnationInformation}}">{{:examnationInformation}}</td>
								<td title="{{:examnationTime}}">{{:examnationTime}}</td>
							</tr>
						{{/for}}
					</table>
				</div>
				<div class="msg_right">
					{{if teacher.length==0}}
						<a href="javascript:;" class="h_color">主考：未设置</a>
						<a href="javascript:;" class="h_color">副考：未设置</a>
					{{else}}
						{{for teacher}}
							{{if isChief==1}}
								<a href="javascript:;" class="r_color">
									<span title="{{:name}}">主考：{{:name}}</span>
									<input type="hidden" id="{{:id}}"/>
									<i class="iconfont"><img src="../img/close.png" alt="" /></i>
								</a>
							{{else}}
								<a href="javascript:;" class="g_color">
									<span title="{{:name}}">副考：{{:name}}</span>
									<input type="hidden" id="{{:id}}"/>
									<i class="iconfont"><img src="../img/close.png" alt="" /></i>
								</a>
							{{/if}}
						{{/for}}
					{{/if}}
				</div>
				<div class="clear"></div>
			</div>

		</script>

    		<script id="teacherListTpl" type="text/template">
    			<tr {{if #index % 2 == 1}}class="odd"{{/if}}>
            		<td>

            			主<input type="checkbox" class="checkbox" {{if distribute=='1'}}{{if isChief=='1'}}checked="checked" disabled="true"{{/if}}{{/if}}/>
            			副<input type="checkbox" class="checkbox" {{if distribute=='1'}}{{if isChief=='0'}}checked="checked" disabled="true"{{/if}}{{/if}}/>


            			<input type="hidden" class="teacher_id" id="{{:id}}"/>
            		</td>
                <td title="{{:name}}" class="click_name" {{if isPunish == 1}}style="color: #E0615F;"{{/if}}>
                		<input type="hidden" class="t_id" id="{{:tid}}"/>
                		{{:name}}
                </td>
                <td>{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}</td>
                <td title="{{if type==1}}教务组{{/if}}{{if type==2}}研究生{{/if}}{{if type==3}}教工{{/if}}">{{if type==1}}教务组{{/if}}{{if type==2}}研究生{{/if}}{{if type==3}}教工{{/if}}</td>
                <td title="{{:major}}">{{:major}}</td>
                <td title="{{:organization}}">{{:organization}}</td>
                <td title="{{:intgral}}">{{:intgral}}</td>
                <td title="{{:invigilateCampus}}">{{:invigilateCampus}}</td>
                <td title="{{:invigilateType}}">{{:invigilateType}}</td>
				<td title="{{:invigilateCount}}">{{:invigilateCount}}</td>
                <td title="{{:mobile}}">{{:mobile}}</td>
			</tr>
    		</script>

		<script id="historyTpl" type="text/template">
			<tr {{if #index % 2 == 1}}class="odd"{{/if}}>
				<td title="{{:index+1}}">{{:index+1}}</td>
				<td title="{{:examName}}">{{:examName}}</td>
				<td title="{{:roomIndex}}">{{:roomIndex}}</td>
				<td title="{{:roomAdd}}">{{:roomAdd}}</td>
				<td title="{{:score}}">{{:score}}</td>
				<td title="{{:applytime}}">{{:applytime}}</td>
			</tr>
		</script>
    </head>

    <body>
    <input id="pagename" type="hidden" value="main" />
        <%@ include file="header.jsp"%>
            <%@ include file="mainLeft.jsp"%>
                <div class="main">




					<div class="main_left">
						<p class="title">考场信息</p>
						<a href="javascript:;" id="load_room">导出(考场维度)</a>
						<a href="javascript:;" class="load_result">导出结果</a>
						<div class="main_content">
                        		<div class="search_body">
								<input type="text" class="search" placeholder="考场号/考场地点/考试科目" onkeypress="if(event.keyCode==13) {searchBtn();return false;}" autocomplete="off">
								<i onclick="searchBtn();" class="iconfont"><img src="../img/search.png" alt="搜索"></i>
							</div>
						</div>

						<ul class="select_bar_left">
							<li><a href="javascript:;">考场号<i class="iconfont"><img src="../img/toptop_b_14.png"></i></a></li>
							<li><a href="javascript:;">教室名称<i class="iconfont"><img src="../img/toptop_b_14.png"></i></a></li>
							<li><a href="javascript:;">考试科目<i class="iconfont"><img src="../img/toptop_b_14.png"></i></a></li>
						</ul>

						<div id="queboxCnt"></div>
						<div id="select_page"></div>

					</div>

                    <div class="main_right">
						<p class="title">
							待分配申报监考人员
						</p>
						<a href="javascript:;" class="wechat_msg">发送通知</a>
						<div class="main_content">
							<div class="search_body">
								<input type="text" id="search1" placeholder="姓名/拼音/手机号" onkeypress="if(event.keyCode==13) {searchBtn_1();return false;}" autocomplete="off">
								<i onclick="searchBtn_1();" class="iconfont"><img src="../img/search.png" alt="搜索"></i>
							</div>
							<p class="slide_p">查看筛选项</p>
						</div>

						<div class="main_header">
							<div class="filter-type">
								<span>身份类别：</span> <a class="lighting" data-value="-1" href="javascript:;">全部</a>
								<a data-value="3" href="javascript:;">教工</a> <a data-value="1" href="javascript:;">考务组</a>
								<a data-value="2" href="javascript:;">研究生</a>
							</div>

							<div class="filter-isChief">
								<span>主考经验：</span> <a class="lighting" data-value="-1" href="javascript:;">全部</a>
								<a data-value="1" href="javascript:;">是</a> <a data-value="0" href="javascript:;">否</a>
							</div>

							<div class="filter-isMixed">
								<span>副考经验：</span> <a class="lighting" data-value="-1" href="javascript:;">全部</a>
								<a data-value="1" href="javascript:;">是</a> <a data-value="0" href="javascript:;">否</a>
							</div>

							<div class="filter-invigilateCampus">
								<span>监考校区：</span> <a class="lighting" data-value="-1" href="javascript:;">全部</a>
								<a data-value="1" href="javascript:;">温泉校区</a><a data-value="2" href="javascript:;">昆仑校区</a>
								<a data-value="3" href="javascript:;">文光校区</a>
							</div>

							<div class="filter-invigilateType">
								<span>监考类型：</span> <a class="lighting" data-value="-1" href="javascript:;">全部</a>
								<a data-value="1" href="javascript:;">笔试</a><a data-value="2" href="javascript:;">无纸化</a>
								<a data-value="3" href="javascript:;">测试</a>
							</div>
						</div>



							<table class="teachers_info" cellspacing="0" cellpadding="0">
	                            <tr class="first_tr">
	                            		<th width="7%">设置</th>
									<th class="sort_th" width="23%">
										姓名<i class="iconfont"><img src=""></i>
									</th>
									<th class="sort_th" width="5%">
										性别<i class="iconfont"><img src=""></i>
									</th>
									<th class="sort_th" width="6%">
										身份<i class="iconfont"><img src=""></i>
									</th>
									<th width="6%">
										专业
									</th>
									<th width="5%">
										学校<br />部门
									</th>
									<th class="sort_th" width="5%">
										积分<i class="iconfont"><img src=""></i>
									</th>
									<th class="sort_th" width="5%">
										监考<br />校区<i class="iconfont"><img src=""></i>
									</th>
									<th class="sort_th" width="5%">
										监考<br />类型<i class="iconfont"><img src=""></i>
									</th>
									<th class="sort_th" width="5%">
										监考<br />次数<i class="iconfont"><img src=""></i>
									</th>
									<th class="sort_th" width="8%">
										手机号<i class="iconfont"><img src=""></i>
									</th>
	                            </tr>
	                            <tbody id="teacherList"></tbody>
	                            <tr class="tip_tr">
	                            		<td colspan="11">请在左侧选择考场...</td>
	                            </tr>

	                       </table>
	                       <div id="select_page_r"></div>
                    </div>




                    <div class="clear"></div>
                    
                    <div class="back_top">
						<a href="#">↑返回页面顶部</a>
					</div>
                </div><!--mian-->

                <div id="bg">
                		<img src="../img/select2-spinner.gif" alt="" />
                </div>

            		<div class="history">
            			<table cellspacing="0" cellpadding="0" class="teachers_info">
						<tr class="first_tr">
							<th>序号</th>
							<th>考试名称</th>
							<th>考场号</th>
							<th>监考地点</th>
							<th>积分</th>
							<th>时间</th>
						</tr>
						<tbody id="history_msg"></tbody>
            			</table>
            			<a href="javascript:;">关闭</a>
            		</div>

            		<div class="easy_modal">
            			<p></p>
            			<div class="button_g">
            				<input type="button" value="关闭"/>
            			</div>
            		</div>

            		<div id="edit_modal">
            			<p>查看/填写监考注意事项</p>
            			<textarea id="invigilationNotice"></textarea>
            			<div class="button_g">
            				<a href="javascript:;" id="submit_edit">确定</a>
            				<a href="javascript:;" id="close">关闭</a>
            			</div>
            		</div>

				
				
                <%@ include file="footer.jsp"%>





                <script src="../js/app.js"></script>
                <script type="text/javascript" src="../js/jsrender.min.js"></script>
                <script src="../js/query.js"></script>
                <script src="../js/paging.js"></script>
                <script src="../js/roomMessage.js"></script>


    </body>

    </html>
    <script>
		$("#bg").height($('body').height());
		var invigilationNotice = new UE.getEditor('invigilationNotice', {
			initialFrameHeight: 170
		});
	</script>
