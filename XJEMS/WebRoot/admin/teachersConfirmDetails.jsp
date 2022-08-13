<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>教师二次确认-详情</title>
        <link rel="stylesheet" href="../css/mainBox.css" />
        <link rel="stylesheet" href="../css/teachersConfirmDetails.css">
        <script id="queboxTpl" type="text/template">
              <p class="title">关于{{:exam}}考试监考老师相关事宜通知</p>
              <ul class="teacher_msg">
                <li>监考老师：{{:name}}</li>
                <li>电话号码：{{:mobile}}</li>
                <li>民族：{{:ethnic}}</li>
                <li>性别：{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}</li>
                <li>身份：{{if type=='1'}}考务组{{/if}}{{if type=='2'}}研究生{{/if}}{{if type=='3'}}教工{{/if}}</li>
                <li>考场安排：{{:roomIndex}}-{{:roomAddress}}</li>
                <li>教师角色：{{if isChief=='1'}}主考{{/if}}{{if isChief=='0'}}副考{{/if}}</li>
                <li>二次确认：{{if isconfirm=='1'}}是{{/if}}{{if isconfirm=='0'}}否{{/if}}</li>
                <li>确认时间：{{:applytime}}</li>
               </ul>
		</script>
    </head>

    <body>
    	<input id="pagename" type="hidden" value="main" />
        <%@ include file="header.jsp"%>
            <%@ include file="mainLeft.jsp"%>
                <div class="main">
                    <div class="back_confirm">
                        <i class="iconfont"><img src="../img/back_r.png" alt="返回"></i>
                        <a href="javascript:;" id="back">返回监考老师二次确认</a>
                    </div>
                    <div id="queboxCnt"></div>
                </div>
                <div class="clear"></div>
                <%@ include file="footer.jsp"%>
    <script src="../js/app.js"></script>
    <script type="text/javascript" src="../js/jsrender.min.js"></script>
    <script type="text/javascript" src="../js/teachersConfirmDetails.js" ></script>
    </body>
    </html>
