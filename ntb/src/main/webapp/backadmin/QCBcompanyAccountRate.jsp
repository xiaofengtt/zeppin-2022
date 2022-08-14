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
<link rel="stylesheet" href="./css/bootstrap2.css">
<style>
    *{
        box-sizing: border-box;
    }
    .text_box{
        margin:28px auto 0 auto;
        width:94%;
    }
    .text_box .text_title{
        margin-bottom:30px;
    }

    .item{
        margin-bottom:20px;
        font-size:15px;
    }

    .item-key{
        display: inline-block;
        width:80px;
        vertical-align: middle;
        text-align: right
    }
    .item-value{
        display: inline-block;
        vertical-align: middle;
    }
    .item-value input{
        width:200px;
        margin-bottom:0;
    }
    .item-value i{
        font-style:normal;
        display:inline-block;
        vertical-align: middle;
        margin-left:4px;
    }
    .text_box .btn_group{
        text-align: center;
        margin-top:30px;
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
    .text_box .btn_group input:nth-child(1){
        margin-right:14px;
    }
    .text_box .btn_group input:hover {
        color: #FFFFFF;
        background: #4D9CFF;
    }
</style>
</head>
<body>

    <div class="text_box">
        <p class="text_title">设置费率</p>
        <form:form id="rate-form" action="../rest/backadmin/qcbcompany/changeFee" method="post">
            <input type="hidden" name="uuid" id="uuid" value="">
            <div class="item">
                <span class="item-key">企业名称：</span>
                <span class="item-value" id="name"></span>
            </div>
            <div class="item">
                <span class="item-key">费率：</span>
                <span class="item-value">
                    <input type="number" step="0.0001" id="rate" value=""><i>%</i>
                    <input type="hidden" name="fee" id="fee" value="">
                </span>
            </div>
        </form:form>
        <div class="btn_group">
            <input type="button" name="" value="确定" id="allRight">
            <input type="button" name="" value="关闭" id="close">
        </div>
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
            var rate = url("?rate");
            $("#uuid").val(uuid);
            $("#close").click(function(){
                parent.$.colorbox.close();
            });
            $.ajax({
                url: '../rest/backadmin/qcbcompany/get',
                type: 'get',
                data: {
                    "uuid": uuid
                }
            })
            .done(function(r) {
                if(r.status == "SUCCESS"){
                    $('#name').html(r.data.name);
                    $("#rate").val(Number(rate) * 100);
                }else{
                    layer.msg(r.message,{
                        time:2000
                    });
                }
            })
            .fail(function() {
                layer.msg('error',{
                    time:2000
                });
            });

            $("#allRight").click(function(){
                layer.confirm("确定要变更信息吗？",function(index){

                    if($("#rate").val() == ""){
                        layer.msg("费率填写有误",{
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
                    $("#fee").val(Number($("#rate").val()) / 100);
                    $("#rate-form").ajaxSubmit(function(r){
                        if(r.status == "SUCCESS"){
                            layer.msg(r.message,{time:2000},function(){
                                parent.getList();
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
