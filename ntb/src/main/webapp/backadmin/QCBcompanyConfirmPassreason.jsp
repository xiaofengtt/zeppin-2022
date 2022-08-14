<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/base.css">
<style>
    *{
        box-sizing: border-box;
    }
    .text_box{
        margin-top:6px;
    }
    .text_box .text_title{
        margin-bottom:10px;
    }
    .text_box textarea{
        width:94%;
        height:160px;
        border:1px solid#E6E6E6;
        resize: none;
        outline: none;
        padding:14px;
        font-size:16px;
        color:#737373
    }
    .text_box .btn_group{
        text-align: center;
        margin-top:20px;
        width:100%;
    }
    .text_box .btn_group input {
        display: inline-block;
        cursor: pointer;
        font-size: 16px;
        width: 100px;
        height: 35px;
        line-height: 35px;
        text-align: center;
        color: #4D9CFF;
        border: 1px solid #4D9CFF;
        -webkit-transition: all 0.2s linear;
        -o-transition: all 0.2s linear;
        transition: all 0.2s linear;
        background: #FFFFFF;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        -ms-border-radius: 5px;
        -o-border-radius: 5px;
        border-radius: 5px;
        outline: none;
    }

    .text_box .btn_group input:hover {
        color: #FFFFFF;
        background: #4D9CFF;
    }

</style>
</head>
<body>
    <div class="text_box">
        <form:form id="pass-form" action="../rest/backadmin/qcbcompany/operateCheck" method="post">
            <p class="text_title">审核不通过原因</p>
            <textarea name="reason" id="reason"></textarea>
            <input type="hidden" name="status" value="unpassed">
            <input type="hidden" id="uuid" name="qcbCompanyOperate" value="">
            <div class="btn_group">
                <input type="button" name="" value="确定" id="pass-this">
            </div>
        </form:form>
    </div>

    <script type="text/javascript" src="./js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            var uuid = url("?uuid");
            $("#uuid").val(uuid);
            $("#pass-this").click(function(){
                layer.confirm("确定要变更状态吗？",function(index){

                    if($("#reason").val() == ""){
                        layer.msg("请填写原因",{
                            time:2000
                        });
                        return false;
                    }

                    if (flagSubmit == false) {
                        return false;
                    }
                    flagSubmit = false;
                    setTimeout(function() {
                        flagSubmit = true;
                    }, 3000);
                    $("#pass-form").ajaxSubmit(function(r){
                        if(r.status == "SUCCESS"){
                            layer.msg(r.message,{time:2000},function(){
                                parent.get();
                                layer.close(index);
                                parent.$.colorbox.close();
                            });
                        }else{
                            layer.msg(r.message,{
                                time:2000
                            });
                        }
                    });
                });
            });
        });
    </script>
</body>
</html>
