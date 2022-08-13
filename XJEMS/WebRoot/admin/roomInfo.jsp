<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>本场考试信息</title>
		<link rel="stylesheet" href="../css/paging.css" />
		<link rel="stylesheet" href="../css/mainBox.css" />
        <link rel="stylesheet" href="../css/roomInfo.css">
        <link rel="stylesheet" href="../css/easyModal.css" />
        
        		<script id="title" type="text/template">
        			<p class="title">{{:exam}}</p>
        		</script>
            <script id="queboxTpl" type="text/template">
              <div class="info_main_bd">
                    <div class="title_color">
                        <table class="header_table">
                        		<tr>
                        			<td class="b_r" title="{{:roomIndex}}">考场号：{{:roomIndex}}</td>
                        			<td class="b_r" title="{{:roomAddress}}">考场地点：{{:roomAddress}}</td>
                        			<td>
                           			<a href="javascript:;" class="first_a">修改</a>
                           			<a href="javascript:;" class="color">删除</a>
									<input type="hidden" id="{{:id}}" />
								</td>
                        		</tr>
                        </table>
                    </div>
                    <div class="info_content">
                    		<table class="room_table">
                    			<tr>
                    				<th>考试科目</th>
                    				<th>考试时间</th>
                    				<th>到岗时间</th>
                    			</tr>
                    			{{for examnation}}
	                    			<tr>
	                    				<td title="{{:examnationInformation}}">{{:examnationInformation}}</td>
	                    				<td title="{{:examnationTime}}">{{:examnationTime}}</td>
	                    				<td title="{{:arrivaltime}}">{{:arrivaltime}}</td>
	                    			</tr>
	                    		{{/for}}
                    		</table>
                       
                    </div> 
                </div>
                
		</script>
    			
    			<script id="modalTpl" type="text/template">
    				<input type="hidden" name="id" value="{{:id}}" id="hidden_msg"/>
				<div class="box">
	        			<p>考场号：</p>
	        			<input type="text" name="roomIndex" value="{{:roomIndex}}" id="roomIndex" placeholder="请填写考场号..."/>
				</div>
				
				<div class="box">
	        			<p>考场地点：</p>
	        			<input type="text" name="roomAddress" value="{{:roomAddress}}" id="roomAddress" placeholder="请填写考场地点..."/>
				</div>
				
				{{for examnation}}

					<hr style="margin-bottom:10px"/>
					<div class="info_box">
						<div class="box">
			        			<p>考试科目：</p>
			        			<input type="text" name="examnationInformation" value="{{:examnationInformation}}" class="examnationInformation" placeholder="请填写考试科目..."/>
						</div>
						
						<div class="box">
			        			<p>考试时间：</p>
			        			<input type="text" name="examnationTime" value="{{:examnationTime}}" class="examnationTime" placeholder="请填写考试时间..."/>
						</div>
						
						<div class="box">
			        			<p>到岗时间：</p>
			        			<input type="text" name="arrivaltime" value="{{:arrivaltime}}" class="arrivaltime" placeholder="请填写到岗时间..." />
						</div>
					</div>
				{{/for}}
    			</script>
    </head>
        
    <body>
    <input id="pagename" type="hidden" value="main" />
       <%@ include file="header.jsp"%>
            <%@ include file="mainLeft.jsp"%>
        <div class="main">
            <div id="title_msg"></div>
            <div class="up_load">
                <a href="javascript:;" class="first_a">
                		<form action="../admin/resourceAdd?type=2" enctype="multipart/form-data" method="post" id="excel">
                			<input type="file" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="excel" id="file"/>
                			<input type="hidden" name="type" value="2"/>
                		</form>
                		导入考场信息excel
                </a>
                <a href="../model/roomModel.xlsx">考场信息模版下载</a>
            </div>
            <p class="warning">文件格式不正确，格式为：.xls .xlsx</p>
            
            <div class="info_page">
                <p>考场相关信息</p>
                
                <div id="select_page">
					
                </div>
            </div>
            
            <div class="info_main">
                <div id="queboxCnt"></div>
            </div>
            <div class="back_top">
				<a href="#">↑返回页面顶部</a>
			</div>
        </div>
                
        <%@ include file="footer.jsp"%>
                
        <div class="bg"></div>
        
        <div class="modal">
            <div class="modal_content">
                <p class="modal_title">
                    
                </p>
                <div class="sub">
                    <p class="sub_warn">
                        <i class="iconfont"><img src="../img/warn.png"></i>
                        注意：导入数据过程中请勿关闭此页面
                    </p>
                    
                    <div class="progress">
                        <div class="progress_bar">
                            <span class="progress_num">0%</span>
                        </div>
                    </div>
                    <p class="loading">
                        正在导入考场信息....
                    </p>
                </div>
                
                
                <div class="finish">
                    <p>
                        <i class="iconfont"><img src="../img/success.png"></i>
                        导入数据成功！
                    </p>
                    <a href="javascript:;" class="modal_close">关闭并刷新</a>
                </div>
            </div>
        </div>
        
        <div class="modal_msg">
        		<!--<form id="change_msg" action="..admin/roomUpdate" method="post">-->
        			<div id="modal_msg_main"></div>
        			<div class="button_g">
		        		<input type="button" value="修改" class="subm"/>
	        			<input type="button" value="取消" class="_back"/>
				</div>
        		<!--</form>-->
        </div>
        
        <div class="modal_del">
        		<input type="hidden" id="del_id"/>
        		<p>确认删除？</p>
        		<div class="button_g">
        			<input type="button" value="删除"/>
        			<input type="button" value="取消"/>
        		</div>
        </div>
        <div class="easy_modal">
        		<p></p>
        		<div class="button_g">
        			<input type="button" value="关闭"/>
        		</div>
        </div>
		<script src="../js/app.js"></script>
		<script type="text/javascript" src="../js/jsrender.min.js"></script>
		<script type="text/javascript" src="../js/query.js"></script>
		<script src="../js/paging.js"></script>
		<script src="../js/input_test.js"></script>
		<script type="text/javascript" src="../js/roomInfo.js" ></script>
		<script src="../js/jquery-form.js"></script>
          
        
    </body>

    </html>
    <script>
        var bg=document.querySelector('.bg');
        bg.style.height=document.body.offsetHeight+"px";
        bg.style.width=document.body.offsetWidth+"px";
        
        
        
        		
    </script>