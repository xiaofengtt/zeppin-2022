/**
 * Copyright (C), 1995-2006, 
 * File Name: InitPage.js
 * Encoding UTF-8 
 * Version: 4.0 
 * Date: 2007-01-10
 * History: // 修改历史记录列表，每条修改记录应包括修改日期、修改者及修改内容简述 
 * 1. Date: 2007-01-18
 *    Author: 
 *    Modification:创建 公共函数
 * 2.
 * ...
 */
 Common = Class.create();

 //清除文本的值
 Array.prototype.ItemClear  =  function(){
    for(var i=0;i<this.length;i++)
    {
        Common.Clear(this[i]);
    }
 }
 Common.Clear=function(id){
    var obj = $(id);
    try
    {
        switch(obj.type)
        {
            case "text":
                obj.value = "";
                break;
            case "select-one":
                obj.selectedIndex = 0;
                break;
            default:
                obj.innerHTML = "";
                break;
        }
    }
    catch(e){}
 }
 
 /*
 ** 去掉字符串的前后空格
 */
 Common.Trim = function(id){
 
    var obj = $(id);
    obj.value = obj.value.Trim();
 
 }
 String.prototype.Trim  =  function(){
	return  this.replace(/(^\s*)  |(\s*$)/g,  "");
 }
 
 /*
 ** 判断对象的值是否为空
 */
 Common.isEmpty=function(id){
    var obj = $(id);
    try
    {
        if(obj.value.Trim().length == 0){
            obj.focus();
            return true;            
        }
        else{
            return false;
        }
    }
    catch(e){ return null;}
 }
 String.prototype.isEmpty = function(){
 
    return this.Trim().length ==0 ? true : false;
    
 }
 
 /*
 ** 判断字符串的真实长度(中文和全角的字符算两个字节)
 */
 String.prototype.RealLength=function(){
    
    var str = this.Trim().split('');
    var length = 0;
    for(var i=0;i<str.length;i++){
        length += 1;
        if(str[i].isExistSpecialChar()){
            length += 1;
        }
    }
    return length;
    
 }
 Common.RealLength = function(id){
    
    var obj = $(id);
    try{
        return obj.value.RealLength();
    }
    catch(e){
        return null;
    }
    
 }
 
 /*
 ** 判断是否存在中文和全角的字符
 */
 String.prototype.isExistSpecialChar = function(){
 
    return escape(this).indexOf("%u") == "-1" ? false : true;
    
 }
 Common.isExistSpecialChar = function(id){
 
    var obj = $(id);
    try{
        return obj.value.isExistSpecialChar();
    }
    catch(e){
        return null;
    }
 }
 
 /*
 ** 判断是否是IPV4
 */
 String.prototype.isIpV4 = function(){
 
    try{
        var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	    return pattern.test(this.Trim());
	}
	catch(e){
	    return false;
	}
	
 }
 Common.isIpV4 = function(id){
    var obj = $(id);
    try{
        return obj.value.Trim().isIpV4();
    }
    catch(e){
        return null;
    }
 }
 
 /*
 ** 判断是否是IPV6
 */
 String.prototype.isIpV6 = function(){
    return true;
 }
 Common.isIpV6 = function(id){
    var obj = $(id);
    try{
        if(obj.value.Trim().isIpV6()){
            return true;
        }
        else{
            obj.focus();
            return false;
        }
    }
    catch(e){
        return null;
    }
 }
 
 /*
 ** 判断是否是电子邮箱
 */
 String.prototype.isEmail = function(){
 
    try{
        var pattern=/^(([a-z A-Z \- _ 0-9]+)@[a-z A-Z \- _ 0-9]+\.([a-z A-Z]+(\.)?)?[a-z A-Z]+)$/;
	    return pattern.test(this.Trim());
    }
    catch(e){
        return false;
    }
    
 }
 Common.isEmail = function(id){
    var obj = $(id);
    try{
        if(obj.value.isEmail()){
            return true;
        }
        else{
            obj.focus();
            return false;
        }
    }
    catch(e){
        return null;
    }
 }
 
 /*
 ** 判断字符长度的范围
 */
 Common.LengthBetween=function(id,min,max){
    var len = Common.RealLength(id);
    if(len>max||len<min){
        $(id).focus();
        return false;
    }
    return true;
 }
 String.prototype.LengthBetween = function(min,max){
 
    var len = this.RealLength();
    if(len>max||len<=min){
        return false;
    }
    return true;
    
 }
 
 /*
 ** 判断是否含特殊符号
 */
 Common.isContainSpecialChar=function(id){
    var obj = $(id);
    var pattern=/^([^:*?<>,;|]*)$/;
	flag=pattern.test(obj.value.Trim());
	if(flag)
	{
		obj.focus();
		return false;
	}
	return true;
 }
 
  /*
 ** 判断是否含特殊符号
 */
 Common.isContainSpecialCharFile=function(obj){
    var pattern=/^([^:*?<>,;|]*)$/;
	flag=pattern.test(obj.value.Trim());
	if(flag)
	{
		return false;
	}
	return true;
 }
 
 Common.DomainisContainSpecialChar=function(obj){
    var pattern=/^([a-z A-Z - _ 0-9]*)$/;
	flag=pattern.test(obj.value.Trim());
	if(flag)
	{
		return true;
	}
	return false;
 }
 
 /*
 ** 判断是否是域名
 */
  Common.isDomain=function(obj){
    var bReturn = true;
    
    if(obj.value.RealLength() >1024)        
        return false;
        
    var list = obj.value.split('.');
    if(list.length < 2)
        return false;
     
    for(var i=0;i<list.length;i++){
        if(list[i].RealLength() == 0 || list[i].RealLength()>64){
            bReturn = false;
            break;
        }
    }
    if(list[list.length-1].RealLength()>4 || list[list.length-1].RealLength()<2)
        return false;
        
    if(bReturn == true){
        var pattern=/^([^~!#$\s%@&*^?(){}_<>,:'￥+=\"\[\];|]*)$/;
	    flag=pattern.test(obj.value.Trim());
	    if(!flag)
	    {		
		    bReturn = false;
	    }
	}        
    return bReturn;
 }
 

 
 /*
 ** 给控件赋值
 */
 Common.SetValue = function(obj,str){
    obj.value = str;
 }
 
 /*
 ** 密码规则
 */
 Common.PwdChk = function(id){
    var obj = $(id);
    try{
        var pattern=/^([a-z A-Z 0-9]*)$/;
	    if(!pattern.test(obj.value.Trim())){
	        obj.focus();
	        return false;
	    }
	    else{
	        pattern=/^(([a-z A-Z 0-9]*[a-z]+[a-z A-Z 0-9]*)|([a-z A-Z 0-9]*[A-Z]+[a-z A-Z 0-9]*)|([a-z A-Z 0-9]*[0-9]+[a-z A-Z 0-9]*))$/;
	        if(!pattern.test(obj.value.Trim())){
	            obj.focus();
	            return false;
	        }
	    }
	    return true;
    }
    catch(e){
        obj.focus();
        return false;
    }
 }
 
  
 /*
 ** 设置Tab页面的高度和宽度
 */
 function SetTabPageHW(item){    
    try{
        var objHeight = document.body.offsetHeight;
        if(top.window.loadingIframeId != "Default"){
            if(document.all != "[object]"){
                objHeight = objHeight + 80;
            }
            else{
                objHeight = objHeight + 10;
            }
        }
        else{
            objHeight = objHeight + 10;
        }
        top.document.getElementById(item).style.height = (objHeight>450?objHeight:450) + "px";
    }
    catch(e){
       top.document.getElementById(item).style.height = "450px"; 
    }
 }


function LoadData(url,pars){
    var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars, onComplete: showResponse}
                    );
}
function showResponse(response){
    $("div_Main_Content").innerHTML = response.responseText;
}

function replace(source,from,to)
{   
    var dest_str = "";
    var i = 0;
    var pos =0;
    var j = from.length;
    i =source.indexOf(from,i);
    while (i >=0 && i < source.length)
    {    	
    	dest_str = dest_str + source.substring(pos,i);
    	pos = i+j;
    	dest_str = dest_str + to;
    	i++
    	i =source.indexOf(from,i);    	
    }
    dest_str = dest_str + source.substring(pos);
    return dest_str;
  
}

function Selectup(select)
{ 
	var c = document.form1.allup;
	if(select == "all")
	{			
		for(var i=0;i<document.form1.length;i++)
		{ 
			var box=document.form1.elements[i];
			var boxid=box.id;
			var trid=document.getElementById('t'+boxid);
			if(boxid)
			{
			allsetbg(trid,boxid)
			}
			//var Pobj=box.Parent;
			//Pobj.className="Cstyle";
			//box.checked =true;
			
		}
		c.checked=true;
	}
	if(select == "all1")
	{			
		for(var i=0;i<document.form1.length;i++)
		{ 
			var box=document.form1.elements[i]
			var boxid=box.id;
			if (boxid!="allupid")
			{
				var trid=document.getElementById('t'+boxid);
				
				if(c.checked)
				{
				
				if(boxid)
				{
				
				allsetbg(trid,boxid)
				}
				}
				else
				{
				if(boxid)
				{
				nonesetbg(trid,boxid)
				
				}
				}
			}

		}
	}
	if(select == "none")
	{
		for(var i=0;i<document.form1.length;i++)
		{ 
			var box=document.form1.elements[i]
			var boxid=box.id;
			var trid=document.getElementById('t'+boxid);
			if(boxid)
			{
			nonesetbg(trid,boxid)
			}
		}
		c.checked=false;
	}
	if(select == "allup")//全选上 day:0301
	{
	var c =document.form1.allup;
	var cu= document.form1.alldown;	
		for(var i=0;i<document.form1.length;i++)
		{
			var box=document.form1.elements[i]
			var boxid=box.id;
			
			var trid=document.getElementById('t'+boxid);
			
			if(c.checked)
			{
			if(boxid)
			{
			allsetbg(trid,boxid);
			cu.checked=c.checked;
			}
			}
			else
			{
			if(boxid)
			{
			nonesetbg(trid,boxid);
			cu.checked=c.checked;
			}
			}

		}
	}
	if(select == "alldown")//全选下 day:0301
	{
	var cu =document.form1.allup;
	var c = document.form1.alldown;	
		for(var i=0;i<document.form1.length;i++)
		{
			var box=document.form1.elements[i]
			var boxid=box.id;
			var trid=document.getElementById('t'+boxid);
			if(c.checked)
			{
			if(boxid)
			{
			allsetbg(trid,boxid);
			cu.checked=c.checked;
			}
			}
			else
			{
			if(boxid)
			{
			nonesetbg(trid,boxid);
			cu.checked=c.checked;
			}
			}

		}
	}
}

function changesBg(temp)
{
	for(var i=1;i<9;i++)
	{
	var spanid=document.getElementById("setting"+i);
	spanid.className="bar1"
	}
	document.getElementById(temp).className="bar";

}
function changesBg1(temp)
{
	for(var i=1;i<5;i++)
	{
	var spanid=document.getElementById("setting"+i);
	spanid.className="bar1"
	}
	document.getElementById(temp).className="bar";

}
function  allsetbg(temp,i)
{
	var obj=temp;
	var obj1=document.getElementById(i);
	obj.className ="Cstyle";
	obj1.checked=true;
}
function  nonesetbg(temp,i)
{
	var obj=temp;
	var obj1=document.getElementById(i);
	obj.className ="";
	obj1.checked=false;
}
function  setbg(temp,i)
{
	var obj=temp;
	var obj1=document.getElementById(i);
	
		if(obj.className=="Cstyle")
			{
			obj.className ="";
			obj1.checked=false;
			}
		else 
			{obj.className ="Cstyle";
			obj1.checked=true;
			}

}
function  setbg1(temp)
{
	var obj=temp;
	if(obj.className=="Cstyle")
		{obj.className ="Cstyle";}
	else 
		{obj.className ="";}
}
function  setbg2(temp)
{
	var obj=temp;
	if(obj.className=="Cstyle")
		{obj.className ="Cstyle";}
	else 
		{obj.className ="Ovstyle";}
}
function hideheaddiv()
	{
	var tag=event.srcElement;
	if ((tag.id!="opimg"))
	{
	MM_showHideLayers('menu3','','hide')
	}
	}