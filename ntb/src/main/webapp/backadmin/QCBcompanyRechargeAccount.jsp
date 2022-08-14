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
    .item-value select{
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
    .text_box .btn_group input{
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
    .text_box .type{
        display:inline-block;
        vertical-align: middle;
        margin:0 0 2px 0;
        padding:0;
    }
    .text_box label{
        display:inline-block;
        vertical-align:middle;
        font-size:15px;
        margin:0;
    }
</style>
</head>
<body>

    <div class="text_box">
        <p class="text_title">设置充值账户</p>
        <form:form id="rate-form" action="../rest/backadmin/qcbcompany/changeFee" method="post">
            <input type="hidden" name="uuid" id="uuid" value="">
            <div class="item">
                <span class="item-key">企业账户：</span>
                <span class="item-value">
                    <select class="" name="companyAccount" id="companyAccount">
                        <option value="">请选择企业账户</option>
                    </select>
                </span>
            </div>
            <div class="item">
                <span class="item-key">企业账号：</span>
                <span class="item-value">
                    <i id="accountNum">请选择企业账户...</i>
                </span>
            </div>
            <div class="item">
                <span class="item-key">账户类型：</span>
                <span class="item-value">
                    <input type="radio" name="type" value="virtual" class="type" id="virtual" checked>
                    <label for="Virtual">虚拟</label>

                    <input type="radio" name="type" value="real" class="type" id="real">
                    <label for="real">实体</label>
                </span>
            </div>
            <div class="item">
                <span class="item-key">虚拟账户：</span>
                <span class="item-value">
                    <select class="" name="qcbVirtualAccount" id="qcbVirtualAccount">
                        <option value="">请选择虚拟账户</option>
                    </select>
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
            var accountNum = "";
            $("#uuid").val(uuid);
            $("#close").click(function(){
                parent.$.colorbox.close();
            });
            getAccount();
            get();


            $(".type").change(function(event) {
                if($(".type:checked").val() == "virtual"){
                    getVirtualAccount($("#companyAccount").val());
                    $("#qcbVirtualAccount").parents(".item").show();
                }else{
                    $("#qcbVirtualAccount").parents(".item").hide();
                }
            });
            //获取企业账户列表
            function getAccount(){
                $.ajax({
                    url: '../rest/backadmin/companyAccount/list',
                    type: 'get',
                    async:false,
                    data: {
                        "pageNum":1,
                        "pageSize":1000
                    }
                })
                .done(function(r) {
                    if(r.status == "SUCCESS"){
                        $.each(r.data,function(index, el) {
                            $("#companyAccount").append("<option data-num="+ el.accountNumLess +" value="+ el.uuid +">"+ el.companyName +"</option>");
                        });
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
            }
            //获取企业账户列表 end

            //获取虚拟账户列表
            function getVirtualAccount(companyAccount,VirtualAccountNum){
                $.ajax({
                    url: '../rest/backadmin/qcbVirtualAccounts/list',
                    type: 'get',
                    data: {
                        "companyAccount":companyAccount,
                        "status":"normal",
                        "pageNum":1,
                        "pageSize":1000
                    }
                })
                .done(function(r) {
                    if(r.status == "SUCCESS"){
                        if(r.totalResultCount != 0){
                            $("#qcbVirtualAccount").html("<option value=''>请选择虚拟账户</option>");
                            $("#qcbVirtualAccount").append("<option value="+ VirtualAccountNum +">"+ VirtualAccountNum +"</option>");
                            $("#qcbVirtualAccount").val(VirtualAccountNum);
                            $.each(r.data,function(index, el) {
                                $("#qcbVirtualAccount").append("<option value="+ el.uuid +">"+ el.accountNum +"</option>");
                            });
                        }else{
                            $("#qcbVirtualAccount").html("<option value=''>没有可选的虚拟账户</option>");
                        }
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
            }
            //获取虚拟账户列表 end

            //获取本条数据
            function get(){
                $.ajax({
                    url: '../rest/backadmin/qcbcompany/get',
                    type: 'get',
                    data: {
                        "uuid": uuid
                    }
                })
                .done(function(r) {
                    if(r.status == "SUCCESS"){
                        accountNum = r.data.accountNum;
                        if(r.data.virtualAccountType == "virtual"){

                            $("#companyAccount").val(r.data.companyAccount);
                            $("#accountNum").html($("#companyAccount").find("option:selected").attr("data-num"));
                            getVirtualAccount($("#companyAccount").val(),r.data.accountNum);

                        }else if(r.data.virtualAccountType == "really"){
                            $("#companyAccount").val(r.data.qcbVirtualAccounts);
                            $("#accountNum").html($("#companyAccount").find("option:selected").attr("data-num"));
                            $("#qcbVirtualAccount").parents(".item").hide();
                            $(".type[id=real]").attr("checked","checked");
                        }
                    }else{
                        layer.msg(r.message,{
                            time:2000
                        });
                    }
                })
                .fail(function() {
                    layer.msg("error",{
                        time:2000
                    });
                });
            }

            // 获取本条数据 end

            $("#allRight").click(function(){
                layer.confirm("确定要变更信息吗？",function(index){

                    if($("#companyAccount").val() == ""){
                        layer.msg("请选择企业账户",{
                            time:2000
                        });
                        return false;
                    }

                    if($(".type:checked").val() == "virtual"){
                        if($("#qcbVirtualAccount").val() == ""){
                            layer.msg("请选择虚拟账户",{
                                time:2000
                            });
                            return false;
                        }
                        if($("#qcbVirtualAccount").val() == accountNum){
                            layer.msg("您没有做出修改",{
                                time:2000
                            });
                            return false;
                        }
                    }

                    if (flagSubmit == false) {
                        return false;
                    }
                    flagSubmit = false;
                    setTimeout(function() {
                        flagSubmit = true;
                    }, 3000);
                    $.ajax({
                        url: '../rest/backadmin/qcbcompany/virtualAccountBind',
                        type: 'post',
                        data: {
                            "qcbCompany": uuid,
                            "companyAccount":$("#companyAccount").val(),
                            "qcbVirtualAccount":$(".type:checked").val() == "virtual" ? $("#qcbVirtualAccount").val() : "",
                            "CSRFToken":$("input[name='CSRFToken']").val()
                        }
                    })
                    .done(function(r) {
                        if(r.status == "SUCCESS"){
                            layer.msg(r.message,{
                                time:2000
                            });
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
                    layer.close(index);
                });
            });
            // $("#allRight").click(function() end
            $("#companyAccount").change(function(event) {
                if($(this).val() != ""){
                    $("#accountNum").html($(this).find("option:selected").attr("data-num"));
                }else{
                    $("#accountNum").html("请选择企业账户...");
                }
                if($(".type:checked").val() == "virtual" && $(this).val() != ""){
                    //选择企业账户后，请求虚拟账户
                    getVirtualAccount($(this).val());
                }else{
                    $("#qcbVirtualAccount").html("<option value=''>请选择虚拟账户</option>")
                }
            });
        });
    </script>
</body>
</html>
