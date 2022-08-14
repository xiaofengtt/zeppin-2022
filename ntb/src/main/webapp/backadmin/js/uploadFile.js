var hehe = true;
var fileArr = [];
$("#upload").uploadFile({
    id: "1",
    url: "../rest/backadmin/resource/add",
    allowedTypes: "jpg,png,jpeg,bmp,tiff,gif",
    maxFileSize: 1024 * 1024 * 1024 * 10,
    fileName: "file",
    maxFileCount: 200,
    dragDropStr: "",
    extErrorStr: "文件格式不正确，请上传指定类型类型的图片文件",
    multiple: true,
    showDone: true,
    showDelete: true,
    deletelStr: '删除',
    doneStr: "完成",
    showAbort: true,
    showStatusAfterSuccess: true,
    maxFileCountErrorStr: "文件数量过多，请先删除。",
    onSuccess: function(files, data, xhr) {
        fileArr.push(data.data.uuid);
        $('input[name="receipt"]').val(fileArr.join(","));
        console.log($('input[name="receipt"]').val());
        $('.ajax-file-upload-statusbar').remove();
        $("#delete").show();
        $("#delete").unbind("click").click(function() {
            // $(".ajax-file-upload").find(".uploadImg").remove();
            layer.confirm("确定要删除吗？", function(index) {
                $(".ajax-file-upload").find(".uploadSrc").remove();
                $(".fileBox").find(".uploadSrc").remove();
                fileArr = [];
                $('input[name="receipt"]').val("");
                console.log(fileArr + "," + $('input[name="receipt"]').val());
                $("#delete").hide();
                layer.close(index);
            });
        });
        $(".ajax-file-upload").css({
            "min-height": "150px",
            "height": "auto",
            "overflow": "default"
        });
        $("#delete").css({
            "margin-left": "22px",
            "display": "inline-block"
        });
        if (hehe == true) {
            // $(".ajax-file-upload").find(".uploadImg").remove();
            $(".ajax-file-upload").find(".uploadSrc").remove();
        }
        $(".ajax-file-upload").prepend(
            "<a class='uploadSrc' href=../" + data.data.url + " target='_blank'><img src=../" + data.data.url + " class='uploadImg'></a>"
        );
        $(".fileBox").prepend(
            "<a class='uploadSrc' href=../" + data.data.url + " target='_blank'><img src=../" + data.data.url + " class='uploadImg'></a>"
        );
        $(".self .ajax-upload-dragdrop").css("display", "none");
        hehe = false;
    }
});