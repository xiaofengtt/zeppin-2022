/**
 * Created by thanosYao on 2015/8/15.
 */

//Switch Content type options
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
$('#createForm').submit(function() {
    var str = $(this).serialize();
    $.ajax({
        url: "../activityAdd?" + str,
        type: "POST",
        data:{
            content:$(".simditor-body")[0].innerHTML
        },
        success:function(data) {
            if (data.Status == "success") {
                alert("创建成功");
                window.close();
            } else {
                $('.alert-danger').html(data.Message).removeClass("hide");
            }
        }
    })
    return false;
});
//Upload picture init
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
}); 

