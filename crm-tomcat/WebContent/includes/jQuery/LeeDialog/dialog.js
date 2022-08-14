// Lee dialog 1.0 http://www.xij.cn/blog/?p=68

var dialogFirst=true;
function dialog(title,content,width,height,cssName,path){

if(dialogFirst==true){
  var temp_float=new String;
  var closeImg = "/includes/jQuery/LeeDialog/close.gif";
  if(path){
  	closeImg  = path+"/includes/jQuery/LeeDialog/close.gif";
  }
  temp_float="<div id=\"floatBoxBg\" style=\"height:"+j$(document).height()+"px;filter:alpha(opacity=0);opacity:0;\"></div>";
  temp_float+="<div id=\"floatBox\" class=\"floatBox\">";
  temp_float+="<div class=\"title\"><h4></h4><span><img name=\"closeImg\"  id=\"closeImg\" src='"+closeImg+"'/></span></div>";
  temp_float+="<div class=\"content\"></div>";
  temp_float+="</div>";
  j$("body").append(temp_float);
  dialogFirst=false;
}

j$("#closeImg").click(function(){
  j$("#floatBoxBg").animate({opacity:"0"},"normal",function(){j$(this).hide();window.document.body.removeChild(this);});
  j$("#floatBox").animate({top:(j$(document).scrollTop()-(height=="auto"?300:parseInt(height)))+"px"},"normal",function(){j$(this).hide();window.document.body.removeChild(this);});   
  
  if(j$("#_dialogIframe")!=null){
      j$("#_dialogIframe").src = "";
  } 
 
  dialogFirst=true;  
});

j$("#floatBox .title h4").html(title);
contentType=content.substring(0,content.indexOf(":"));
content=content.substring(content.indexOf(":")+1,content.length);
switch(contentType){
  case "url":
  var content_array=content.split("?");
  j$("#floatBox .content").ajaxStart(function(){
    j$(this).html("loading...");
  });
  j$.ajax({
    type:content_array[0],
    url:content_array[1],
    data:content_array[2],
	error:function(){
	  j$("#floatBox .content").html("error...");
	},
    success:function(html){
      j$("#floatBox .content").html(html);
    }
  });
  break;
  case "text":
  j$("#floatBox .content").html(content);
  break;
  case "id":
  j$("#floatBox .content").html(j$("#"+content+"").html());
  break;
  case "iframe":
  j$("#floatBox .content").html("<iframe id=\"_dialogIframe\" src=\""+content+"\" width=\"100%\" height=\""+(parseInt(height)-10)+"px"+"\" scrolling=\"auto\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>");
}

j$("#floatBoxBg").show();
j$("#floatBoxBg").animate({opacity:"0.5"},"normal");
j$("#floatBox").attr("class","floatBox "+cssName);
j$("#floatBox").css({display:"block",left:((j$(document).width())/2-(parseInt(width)/2))+"px",top:(j$(document).scrollTop()-(height=="auto"?300:parseInt(height)))+"px",width:width,height:height});
j$("#floatBox").animate({top:(j$(document).scrollTop()+50)+"px"},"normal"); 
}