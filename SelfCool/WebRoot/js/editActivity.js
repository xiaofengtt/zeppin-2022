/**
 * Created by thanosYao on 2015/8/14.
 */
$( document ).ready(function() {
    console.log( "ready!" );
});
function replaceContent () {
    if(contentSelect.options[1].selected==true){
        $(".editorBar").removeClass("hide");
    }else{
        $(".editorBar").addClass("hide");
    }
}
function saveHtml () {
    $.ajax({
        url: "../admin/createHtmlCreate",
        type: "POST",
        data:{
            content:$(".simditor-body")[0].innerHTML
        },
        success:function(data) {
            if (data.Status == "success") {
                $("#url").val(data.Records.url);
                console.log(data);
                $("<a target='_blank' style='float:right;' href='"+data.Records.url+"'>去查看</a>").insertAfter("#saveHtmlBtn");
            } else {
                $('.alert-danger').html(data.Message).removeClass("hide");
            }
        }
    })
}
$(function() {
    function getUrlParam(name) {
        var url = window.location.href;
        var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
        var paraObj = {}
        var i = 0;
        var j;
        for (i = 0; j = paraString[i]; i++){
            paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
        }

        var returnValue = paraObj[name.toLowerCase()];
        if(typeof(returnValue)=="undefined"){
                return "";
        }else{
            return returnValue;
        }
    };
    var id = getUrlParam('id');
    $('input[name="id"]').val(id);
    $.ajax({
        url:"../activityLoad?id="+id+"&split=_",
        type: "POST",
        success: function (r) {
            if(r.Status == 'success') {
                $("#name").val(r.Records.name);
                $("#id").val(r.Records.id);
                $("#title").val(r.Records.title);
                $("#weight").val(r.Records.weight);
                $("#contentSelect").val(r.Records.contentType);
                if(r.Records.contentType==2){
                    $(".editorBar").removeClass("hide");
                    $(".simditor-body")[0].innerHTML=r.Records.content;
                    $("<a target='_blank' style='float:right;' href='"+r.Records.url+"'>去查看</a>").insertAfter("#saveHtmlBtn");
                }
                $("#url").val(r.Records.url);
                $("#status").val(r.Records.status);
                $("#uploadImage").attr("src","../"+r.Records.resource_url);
                $("#resourceId").uploadFile({
                    url:"../resourceAdd?type=1",
                    allowedTypes:"jpg,png,gif,jpeg",
                    maxFileSize:1024*1024*5,
                    fileName:"applyReportBook",
                    maxFileCount : 1,
                    dragDropStr: "",
                    extErrorStr:"文件格式不正确，请上传jpg或png类型的图片",
                    showStatusAfterSuccess:false,
                    showDelete : true,
                    deletelStr : '删除',
                    showAbort:false,
                    onSuccess:function(files,data,xhr)
                    {
                        if($('input[name="resource.id"]').length > 0) {
                            $('input[name="resource.id"]').val(data.Records.id);
                        }else {
                            $("#resourceId").append('<input type="hidden" name="resource.id" value="' + data.Records.id + '">');
                        }
                        $('#uploadImage').attr('src',data.Records.sourcePath);
                        
                    },
                    deleteCallback: function (data, pd) {
                        $.get("../resourceDelete?id="+$('input[name="resource.id"]').val(),function (resp,textStatus, jqXHR) {
                            if(resp.Status=='success')  
                                 $("#resourceId").find('input[name="resource.id"]').remove();
                            else 
                                alert(resp.Message);
                         });
                    }
                })
            }else {
            alert('服务端出错！请稍后重试！');
        }
        }

    })
    
    
    $('#formsubmit').submit(function() {
        var str = $(this).serialize();
        $.ajax({
            url:"../activityUpdate?"+ str,
            type:"POST",
            data:{
                content:$(".simditor-body")[0].innerHTML
            },
            success:function(data) {
                if (data.Status == "success") {
                    alert("创建成功");
                    window.close();
                } else {
                    $('.alert-danger').html(data.Message).show();
                }
            }
        })
        return false;
    });
    
    //项目类型 helper 函数
    $(document).on("click",function(e) {
        if(!$(e.target).parents().is('.ufc'))
            $('.uul').hide();
    });
});
