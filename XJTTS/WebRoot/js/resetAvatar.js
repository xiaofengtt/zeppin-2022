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
    var height=$(window).height();
	$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");
	$(window).resize(function(){
		var height=$(window).height();
		$(".main").css("min-height",height-$("footer").outerHeight(true)-$(".navbar").outerHeight(true)-115+"px");	
	});
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
function fullAvatarEditor() {
	var id				= 'fullAvatarEditor'			//flash文件的ID
	var file			= 'fullAvatarEditor.swf';		//flash文件的路径
	var	version			= "10.1.0";						//播放该flash所需的最低版本
	var	expressInstall	= 'expressInstall.swf';			//expressInstall.swf的路径
	var	width			= 630;							//flash文件的宽度
	var	height			= 430;							//flash文件的高度
	var container		= id;							//装载flash文件的容器(如div)的id
	var flashvars		= {};
	var callback		= function(){};
	var heightChanged	= false;
	//智能获取参数，字符类型为装载flash文件的容器(如div)的id，第一个数字类型的为高度，第二个为宽度，第一个object类型的为参数对象，如此4个参数的顺序可随意。
	for(var i = 0; i < arguments.length; i++)
	{
		var a = arguments[i];
		if(typeof a == 'string')
		{
			if (a.substring(a.length - 'fullAvatarEditor.swf'.length) == 'fullAvatarEditor.swf')
			{
				file = a;
			}
			else if (a.substring(a.length - 'expressInstall.swf'.length) == 'expressInstall.swf')
			{
				expressInstall = a;
			}
			else
			{
				container = a;
			}
		}
		else if(typeof a == 'number')
		{
			if(heightChanged)
			{
				width = a;
			}
			else
			{
				height = a;
				heightChanged = true;
			}
		}
		else if(typeof a == 'function')
		{
			callback = a;
		}
		else
		{
			flashvars = a;
		}
	}
	var vars = {
		id : id
	};
	//合并参数
	for (var name in flashvars)
	{
		if(flashvars[name] != null)
		{
			if(name == 'upload_url' || name == 'src_url')
			{
				vars[name] = encodeURIComponent(flashvars[name]);
			}
			else
			{
				vars[name] = flashvars[name];
			}
		}
	}
	var params = {
		menu				: 'true',
		scale				: 'noScale',
		allowFullscreen		: 'true',
		allowScriptAccess	: 'always',
		wmode				: 'transparent'
	};
	var attributes = {
		id	: vars.id,
		name: vars.id
	};
	var swf = null;
	var	callbackFn = function (e) {
		swf = e.ref;
		swf.eventHandler = function(json){
			callback.call(swf, json);
		};
	};
	swfobject.embedSWF(
		file, 
		container,
		width,
		height,
		version,
		expressInstall,
		vars,
		params, 
		attributes,
		callbackFn
	);
	return swf;
}