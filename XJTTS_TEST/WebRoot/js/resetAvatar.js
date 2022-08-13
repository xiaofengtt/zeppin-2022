$("#header").load("header.html");
$("#footer").load("footer.html");

swfobject.addDomLoadEvent(function () {
    var swf = new fullAvatarEditor("fullAvatarEditor.swf", "expressInstall.swf", "swfContainer", {
            id : 'swf',
            upload_url : '../teacher/tinfo_inputImage.action',	//上传接口
            method : 'post',	//传递到上传接口中的查询参数的提交方式。更改该值时，请注意更改上传接口中的查询参数的接收方式
            src_upload : 0,		//是否上传原图片的选项，有以下值：0-不上传；1-上传；2-显示复选框由用户选择
            avatar_box_border_width : 0,
            avatar_sizes : '100*100|50*50|32*32',
            avatar_sizes_desc : '100*100像素|50*50像素|32*32像素'
        }, function (msg) {
            switch(msg.code)
            {
                case 2 :
                    document.getElementById("upload").style.display = "inline";
                    break;
                case 3 :
                    if(msg.type == 0)
                    {
                        console.log("摄像头已准备就绪且用户已允许使用。");
                    }
                    else if(msg.type == 1)
                    {
                        alert("摄像头已准备就绪但用户未允许使用！");
                    }
                    else
                    {
                        alert("摄像头被占用！");
                    }
                    break;
                case 5 :
                    if(msg.type == 0)
                    {
                        location.reload();
                    }
                    break;
            }
        }
    );
    document.getElementById("upload").onclick=function(){
        swf.call("upload");
    };
});
$(document).ready(function(){
    getAvatar();
});
function getAvatar(){
    $.ajax({
        type: "post",
        url: "../teacher/tinfo_getHeadImg.action",
        success: function (data) {
            if(data.Result == "OK"){
                if(data.Records.headImgPath == '0'){
                    $("#bigAvatar").attr("src","../images/userInfomation/default.png");
                }
                else{
                    $("#bigAvatar").attr("src",data.Records.headImgPath);
                }
                userName.innerHTML     = data.Records.name;
            }
            else{
                console.log(data);
            }
        }
    })
}