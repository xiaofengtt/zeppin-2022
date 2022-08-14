<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="renderer" content="webkit">

        <title>教师评价</title>
		<link rel="stylesheet" href="../css/mainBox.css" />
        <link rel="stylesheet" href="../css/tableStlye.css">
		<link rel="stylesheet" href="../css/paging.css" />
        <link rel="stylesheet" href="../css/Assess.css">
        <link rel="stylesheet" href="../css/easyModal.css" />

		<script id="queboxTpl" type="text/template">
			 <tr {{if #index % 2 == 1}}class="odd"{{/if}}>
			 	<td>
			 		<input type="hidden" id="{{:id}}"/>
			 		<input type="checkbox" class="checkbox"/>

			 	</td>
             	<td title="{{:#index+1}}">{{:#index+1}}</td>
             	<td title="{{:name}}">{{:name}}</td>
                <td title="{{:ethnic}}">{{:ethnic}}</td>
                <td title="{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}">{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}</td>
               
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
                <td title="{{:applytime}}">{{:applytime}}</td>
                <td title="{{:credit}}">{{:credit}}</td>
                <td title="{{if creditStatus==0}}未评分{{else}}已评分{{/if}}">{{if creditStatus==0}}未评分{{else}}已评分{{/if}}</td>
                <td title="{{if recordStatus==2}}是{{else}}否{{/if}}">{{if recordStatus==2}}是{{else}}否{{/if}}</td>
                <td>
                		<a href="javascript:;" class="color_a assess">评分</a>
                		<input type="hidden" id="{{:id}}"/>
                </td>
                
              </tr>
		</script>
    </head>

    <body>
		<input id="pagename" type="hidden" value="main" />
        <%@ include file="header.jsp"%>
            <%@ include file="mainLeft.jsp"%>

                <div class="main">
                   <p class="title">教师评价</p>
                    <div class="main_content">
                    		<div class="blank">
                    			<div class="filter-isCredit">
								<span>审核状态：</span>
								<a class="lighting" data-value="-1" href="javascript:;">全部</a>
								<a data-value="1" href="javascript:;">已评分</a>
								<a data-value="0" href="javascript:;">未评分</a>
							</div>
                    		</div>
                    		<a href="javascript:;" id="assess_all">批量打分</a>
                        <div class="search_body">
                            <input type="text" autocomplete="off" class="search" placeholder="姓名/拼音/手机号" onkeypress="if(event.keyCode==13) {searchBtn();flag=false;return false;}">
                            <i onclick="searchBtn();" class="iconfont"><img src="../img/search.png"></i>
                        </div>
                        <div id="select_page">
                        </div>
                    </div>
                    <div class="scroll-warp">
						<div class="scroll-inner">
		                    <table class="teachers_info" cellspacing="0" cellpadding="0" style="width:200%;max-width: 150%;table-layout: auto;">
	                            <tr class="first_tr">
									<th>
										<a id="select_all" href="javascript:;">全选</a>
									<a id="unselect_all" href="javascript:;">反选</a>
									</th>
									<th>序号</th>
									<th class="sort_th">姓名<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">民族<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">性别<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">身份<i class="iconfont"><img src=""></i></th>
									<th>安排考场</th>
									<th class="sort_th">角色<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">二次确认<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">确认时间<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">积分<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">是否打分<i class="iconfont"><img src=""></i></th>
									<th class="sort_th">停用<i class="iconfont"><img src=""></i></th>
									<th>操作</th>
									</tr>
									<tbody id="queboxCnt"></tbody>
							</table>
						</div>
					</div>
                    <div class="back_top">
						<a href="#">↑返回页面顶部</a>
					</div>
                </div>
                <div class="clear"></div>
                <%@ include file="footer.jsp"%>
                    <div class="bg">

                    </div>
                    <div class="modal">
                        <div class="modal_main">
                            <p class="modal_title">监考老师评价</p>
                            <div class="modal_bottom">
                                <form action="#" method="post" class="form" autocomplete="off">
                                    <p>评分:</p>
                                    <input type="text" id="select"autocomplete="off" placeholder="请输入分数，不能超过4位数..." maxlength="4">
                                    <p>原因:</p>
                                    <textarea id="text" name="" id="" cols="13.7" rows="5.2" autocomplete="off" placeholder="请输入原因..."></textarea>
								<div class="button_g">
									<input type="button" id="close" value="取消">
                                    <input type="button" id="submit" value="提交">
								</div>
                                </form>

                            </div>
                        </div>
                    </div>

					<div class="modal_1">
                        <div class="modal_main">
                            <p class="modal_title">监考老师评价</p>
                            <div class="modal_bottom">
                                <form action="#" method="post" class="form" autocomplete="off">
                                    <p>评分:</p>
                                    <input type="text" class="select" autocomplete="off" placeholder="请输入分数，不能超过4位数..." maxlength="4">
                                    <p>原因:</p>
                                    <textarea name="" id="" cols="13.7" rows="5.2" class="text" autocomplete="off" placeholder="请输入原因..."></textarea>
                                    <div class="button_g">
	                                    	<input type="button" class="close" value="取消">
	                                    <input type="button" class="submit" value="提交">
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
					
					<!--简单的提示框-->
					<div class="easy_modal">
						<p></p>
						<div class="button_g">
							<input type="button" value="关闭" />
						</div>
					</div>
                    <!--                     <script src="../js/selectivizr_1.0.2.js"></script> -->

                    <script src="../js/app.js"></script>
                    <script type="text/javascript" src="../js/jsrender.min.js"></script>
                    <script type="text/javascript" src="../js/query.js"></script>
                    <script src="../js/paging.js"></script>
                    <script type="text/javascript" src="../js/teacherAssess.js" ></script>
    </body>

    </html>
            <script>
                var bg=document.querySelector('.bg');
                bg.style.height=document.body.offsetHeight+"px";
            </script>