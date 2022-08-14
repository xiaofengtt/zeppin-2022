/*
    通用表单验证
    FormValidate version 1.0
*/

(function($){
    var errorObj = null,
        errorMsg = "",
        nullMsg = "",
        tipMsg = {
            space:"必须填写",
            wrong:"输入的格式错误，请检查重输入"    
        };
        
	$.fn.FormValidate = function(settings){
	    var defaults = {};
        settings = $.extend({}, $.fn.FormValidate.sn.defaults, settings);	    
	    settings.checktype && $.extend($.CheckType,settings.checktype);
	    settings.checkFunc && $.extend($.CheckFunc,settings.checkFunc);
	    
	    var initObj = settings.init||[];
	    var json = initObj.normal||[];

	    var o,checkStr;
	    for(i=0;i<json.length;i++){	        
	        o = $('[name="'+json[i].FIELD_NAME+'"]');
	        o.css("background-color","#ffe7e7");
	        if(o.attr("type")=="hidden")
	            continue;
	        switch(json[i].DATA_TYPE){
	            case 'select':
	                //if(o.val()!=""&&o.val()!=0)
	                    //o.css("background-color","#fff");
	            case 'checkbox':
	            case 'radio':	                
	            case 'func':
	                checkStr = json[i].DATA_TYPE;
	                break;
	            default:
	                if(Number(json[i].MAX_LEN) > 0 && Number(json[i].MIN_LEN) > 0 )
	                    checkStr =  json[i].DATA_TYPE+json[i].MIN_LEN+"-"+json[i].MAX_LEN;
	                else if(Number(json[i].MIN_LEN)> 0)
	                    checkStr = json[i].DATA_TYPE+json[i].MIN_LEN;
	                else
	                    checkStr = json[i].DATA_TYPE;
	                break;	             	                
	        }
	        o.attr("checktype",checkStr).attr("nullmsg",json[i].NULL_MSG||tipMsg.space).attr("errormsg",json[i].ERROR_MSG||tipMsg.wrong);
	    };
	    if(settings.twiceFlag==1){
	        //创建两次输入的div
	        
	        var twiceobj = $("<div id='twice'>"+
	                            "<div id='top'></div>"+
	                            "<div id='main'>"+
	                                "<div id='close'>x</div>"+
                                    "<div class='inputdiv'><p>第一次输入:&nbsp;<input type='text' name='first_input' checktype='recheck'></p></div>"+
                                    "<div class='inputdiv'><p>第二次输入:&nbsp;<input type='text' name='confirm_input' checktype='recheck' confirmcheck='first_input'></p></div>"+
                                "</div>"+
                                "<div id='bottom'></div>"+
                             "</div>").appendTo(this).css("display","none");
            
            twiceobj.find("#close").click(function(){
                twiceobj.css("display","none");
                twiceobj.find("[name='confirm_input']").val("");
                $("#error").css("display","none");
                return false;
            }); 
	        
	        json = initObj.twice||[];
	        for(i=0;i<json.length;i++){
	
                var o = $('[name="'+json[i].FIELD_NAME+'"]');
    	        if(o.attr("type")=="hidden")
    	            continue;
    	        switch(json[i].DATA_TYPE){
    	            case 'select':
    	            case 'checkbox':
    	            case 'radio':
    	            case 'func':
    	                checkStr = json[i].DATA_TYPE;
    	                break;
    	            default:
    	                if(Number(json[i].MAX_LEN) > 0 && Number(json[i].MIN_LEN) > 0 )
    	                    checkStr =  json[i].DATA_TYPE+json[i].MIN_LEN+"-"+json[i].MAX_LEN;
    	                else if(Number(json[i].MIN_LEN)> 0)
    	                    checkStr = json[i].DATA_TYPE+json[i].MIN_LEN;
    	                else
    	                    checkStr = json[i].DATA_TYPE;
    	                break;	             	                
    	        }
    	        o.attr("checktype",checkStr).attr("nullmsg",json[i].NULL_MSG||tipMsg.space).attr("errormsg",json[i].ERROR_MSG||tipMsg.wrong).attr("readOnly",true);
    	        o.css("background-color","#ffe7e7");
    	        if(o.val()==""){
    	            o.val("需要两次输入");
    	        }
    	        /*o.focus(function(){
    	            if(o.val()=="需要两次输入")
    	                o.val("");	                
	            });*/
	            var obj;
	            o.focus(function(event){
	                obj = $("[name='"+event.target.name+"']");
	                if(obj.val()=="需要两次输入")
    	                obj.val("");	
	                switch(event.keyCode){
	                    case 13:
	                    case 9:
	                        return true;
	                        break;
	                    default:
	                        break;
	                }	                
	                twiceobj.css("display","").css("position","absolute").css('top',obj.position().top+obj.height()+5).css('left',obj.position().left-100);
	                twiceobj.find("[name='first_input']").attr("checktype",obj.attr("checktype")).focus().val(obj.val()).select();
	                twiceobj.find("[name='first_input']").attr("nullmsg",obj.attr("nullmsg"));
	                twiceobj.find("[name='first_input']").attr("errormsg",obj.attr("errormsg"));
	                twiceobj.find("[name='confirm_input']").attr("checktype",obj.attr("checktype")).val("");
	                twiceobj.find("[name='confirm_input']").attr("nullmsg",obj.attr("nullmsg"));
	                twiceobj.find("[name='confirm_input']").attr("errormsg",obj.attr("errormsg"));
	                return false;    
	            });
	            twiceobj.find("[name='first_input']").keyup(function(event){
	                 switch(event.keyCode){
	                    case 13:
	                        twiceobj.find("[name='first_input']").blur();
	                        if(errorObj==null){	                            
	                            twiceobj.find("[name='confirm_input']").blur().focus();	                            
	                        }
	                        break;
	                    default:
	                        return event.keyCode;
	                        break;  
	                 }  

	            });
	            twiceobj.find("[name='confirm_input']").keyup(function(event){
	                switch(event.keyCode){
	                    case 13:
	                        twiceobj.find("#close").focus();
	                        if(errorObj==null&&((twiceobj.find("[name='confirm_input']").val()==twiceobj.find("[name='first_input']").val())||(Number(twiceobj.find("[name='confirm_input']").val())!=NaN&&(Number(twiceobj.find("[name='confirm_input']").val())==Number(twiceobj.find("[name='first_input']").val()))))){
                                obj.val(twiceobj.find("[name='confirm_input']").val());
                                obj.css("background-color","#fff");
                                //obj.focus();
                                twiceobj.css("display","none");
                                if(settings["executeFunc"][obj.attr("name")+"Execute"]){  
                                    settings["executeFunc"][obj.attr("name")+"Execute"]();
                	            }
                            }
	                        return false;
	                        break;
	                    default:
	                        break;
	                }
	                return false;
	            });
	        }
	        
	    }
	    
	    var msgobj = $('<div id="error"><div id="top"></div><div id="msg"><div id="close">x</div><div id="tip"><p>输入的格式错误，请检查重输入</p></div></div><div id="bot"></div></div>').appendTo(this);//提示信息框;
	    msgobj.css("display","none");
	    
	    $("#error").find("#close").click(function(){
	        $("#error").css("display","none");
	    });
	    
	    
	    this.each(function(){
	        var $this = $(this);
	        
	        $this.find("[checktype][readOnly!=true][type!='hidden']").blur(function(){
	            var ret = true;
	            
	            ret = $.fn.FormValidate.sn.checkInput($(this),$this)

	            if(!ret){
	                $.fn.FormValidate.sn.showMsg($(this),errorMsg);
	                $(this).css("background-color","#ffe7e7");
	                return false;
	            }else if($(this).attr("checktype") in $.CheckFunc||$(this).attr("checktype")=="select"||$(this).attr("checkbox")=="select"||$(this).attr("checktype")=="radio"){
	                $("#error").css("display","none");    
	                $(this).css("background-color","#fff");
	                return false;
	            }
	            
	            ret = $.fn.FormValidate.sn.regCheck($(this),$this);
	            
	            if(!ret){
	                $.fn.FormValidate.sn.showMsg($(this),errorMsg);
	                $(this).css("background-color","#ffe7e7");
	                return false;
	            }
	            
	            $("#error").css("display","none");    
	            $(this).css("background-color","#fff");
	            if(settings["executeFunc"][$(this).attr("name")+"Execute"]){  
                    settings["executeFunc"][$(this).attr("name")+"Execute"]();
	            }
	        });
	        
	        $this.find(":checkbox[checktype],:radio[checktype]").each(function(){
				var _this=$(this);
				var name=_this.attr("name");
				$this.find("[name="+name+"]").filter(":checkbox,:radio").not("[checktype]").bind("blur",function(){
					_this.trigger("blur");
				});
			});
			
		    var subform = function(){
		        var ret = true;
                $this.find("[checktype][name!='first_input'][name!='confirm_input']:visible").each(function(){
    	            if($(this).val() == "需要两次输入"&&$(this).attr("readOnly") == true)
    	                $(this).val("");
    	            ret = $.fn.FormValidate.sn.checkInput($(this),$this)
    
    	            if(!ret){
    	                $.fn.FormValidate.sn.showMsg($(this),errorMsg);
    	                $(this).css("background-color","#ffe7e7");
    	                $(this).focus();
    	                return false;
    	            }else if($(this).attr("checktype") in $.CheckFunc||$(this).attr("checktype")=="select"||$(this).attr("checkbox")=="select"||$(this).attr("checktype")=="radio"){
    	                $(this).css("background-color","fff");
    	                return true;
    	            }
    	            
    	            ret = $.fn.FormValidate.sn.regCheck($(this),$this);
    	            
    	            if(!ret){
    	                $.fn.FormValidate.sn.showMsg($(this),errorMsg);
    	                $(this).css("background-color","#ffe7e7");
    	                $(this).focus();
    	                return false;
    	            }
    	            $("#error").css("display","none");    
    	            
    	            if(settings["executeFunc"][$(this).attr("name")+"Execute"]){  
                        settings["executeFunc"][$(this).attr("name")+"Execute"]();
    	            }   	                        
                });
                
                if(ret){
                    if(settings["dataInit"]){    	                
    	               settings["dataInit"]();    	                    
    	            }else{
    	                $(this).submit();	    
    	            }
                }
		        
		    };
		    settings.btnSubmit && $this.find(settings.btnSubmit).bind("click", subform);
            $this.submit(function () {
                subform();
                return false;
            });
	        
	    });
	};
	
	$.fn.ObjectValidate = function(parentObj){
	    $(this).css("background-color","#ffe7e7");
	    $(this).blur(function(){
    	    var $this = parentObj;
    	    var ret = true;
    	            
            ret = $.fn.FormValidate.sn.checkInput($(this),$this)

            if(!ret){
                $.fn.FormValidate.sn.showMsg($(this),errorMsg);
                $(this).css("background-color","#ffe7e7");
                return false;
            }else if($(this).attr("checktype") in $.CheckFunc||$(this).attr("checktype")=="select"||$(this).attr("checkbox")=="select"||$(this).attr("checktype")=="radio"){
                $("#error").css("display","none");    
                $(this).css("background-color","#fff");
                return false;
            }
            
            ret = $.fn.FormValidate.sn.regCheck($(this),$this);
            
            if(!ret){
                $.fn.FormValidate.sn.showMsg($(this),errorMsg);
                $(this).css("background-color","#ffe7e7");
                return false;
            }
            
            $("#error").css("display","none");    
            $(this).css("background-color","#fff");
            if(settings["executeFunc"][$(this).attr("name")+"Execute"]){  
                settings["executeFunc"][$(this).attr("name")+"Execute"]();
            }
    	});    
	};
	
	$.fn.FormValidate.sn = {
        defaults:{
            twiceFlag:0//用于判定是否需要创建二次输入框，0：不需要创建 1：需要创建
        },
        checkInput:function(obj,parentObj){
            
            errorMsg = obj.attr("errormsg")|| tipMsg.wrong;
            nullMsg =  obj.attr("nullmsg")|| tipMsg.space;
            var inputName;
            //校验select，value值为 0||""时判定为未选择
            if(obj.is("[checktype='select']")){
                 if(!obj.val()||obj.val()=='0'){                    
                    //显示提示信息
                    errorObj = obj;
                    errorMsg = nullMsg;
                    return false;   
                 }
            }
            //检验checkbox
            if(obj.is("[checktype='checkbox']")){
                inputname = obj.attr("name");
                var checkboxvalue = parentObj.find(":checkbox[name=" + inputname + "]:checked").val();
                if (!checkboxvalue) {
                    errorObj = obj;
                    errorMsg = nullMsg;
                    return false;
                }
            }
            //校验radio
            if (obj.is("[datatype='radio']")) {
                inputname = obj.attr("name");
                var radiovalue = parentObj.find(":radio[name=" + inputname + "]:checked").val();
                if (!radiovalue) {
                    errorObj = obj;
                    errorMsg = nullMsg;
                    return false;
                }
            }
            //函数校验
            if(obj.attr("checktype") in $.CheckFunc){
                if(!$.CheckFunc[obj.attr("checktype")]["main"](obj.val())){
                    errorObj = obj;
                    return false;
                };
            }else{
                //判断是否未输入
                if(obj.val() == ""){
                    errorObj = obj;
                    errorMsg = nullMsg;
                    return false;        
                }    
            }           
            //二次输入校验
            if (obj.attr("confirmcheck")) {
                var theother = parentObj.find("input[name=" + obj.attr("confirmcheck") + "]:first");
                if(Number(obj.val())==NaN){
                    if (obj.val() != theother.val()){
                        errorObj = obj;
                        errorMsg = "两次输入不一致";//obj.attr("errormsg");
                        return false;
                    }
                }else if(Number(obj.val())!=Number(theother.val())){
                    errorObj = obj;
                    errorMsg = "两次输入不一致";//obj.attr("errormsg");
                    return false;     
                }
                
            }
            //验证通过
            errorObj = null;
            errorMsg = "";
            return true;
            
        },
        regCheck:function(obj,parentObj){
            //采用正则表达式验证   
            
            var _type = obj.attr("checktype");
            if(_type=="d"){
                obj.val(obj.val().replaceAll(",",""));    
            }
            
            var _value = obj.val();
            
            if(!(_type in $.CheckFunc)){
                if(!(_type in $.Checktype)){
                    var type = _type.match($.Checktype["match"]),
                        temp;
                    for (var name in $.Checktype){
                        temp = name.match($.Checktype["match"]);
    
                        if (!temp) {
                            continue;
                        }
                        if (type[1] === temp[1]) {
                            var str = $.Checktype[name].toString();
                            var regxp;
                            switch(type[1]){
                                
                                case 'i':                            
                                    regxp = new RegExp("\\{" + (temp[2]-1) + "," + (temp[3]-1) + "\\}");
                                    str = str.replace(regxp, "{" + (type[2]-1) + "," + (type[3]-1) + "}").replace(/^\//, "").replace(/\/$/, "");
                                    break;
                                case 'd':
                                    break;
                                default:
                                    regxp = new RegExp("\\{" + temp[2] + "," + temp[3] + "\\}");
                                    str = str.replace(regxp, "{" + type[2] + "," + type[3] + "}").replace(/^\//, "").replace(/\/$/, "");
                                    break;
                                
                            }
                            
                            $.Checktype[_type] = new RegExp(str);
                            break;
                        }  
                    }
                }
                var ret;
                if(Object.prototype.toString.call($.Checktype[_type])=="[object RegExp]"){
    				ret =  $.Checktype[_type].test(obj.val());
    			}           
                if(!ret){                        
                    errorObj = obj;
                    errorMsg = obj.attr("errormsg");
                    return false;
                }
            }
            errorObj = null;
            errorMsg = "";
            return true; 
        },
        showMsg:function(errorObj,msg){
            //alert(errorObj.attr("name"));
            if(errorObj.attr("name")=="first_input"){
                $("#error").css("display","").css("position","absolute").css('top',$("#twice").position().top-errorObj.height()-5).css('left',$("#twice").position().left+50);    
            }else if(errorObj.attr("name")=="confirm_input"){
                $("#error").css("display","").css("position","absolute").css('top',$("#twice").position().top-errorObj.height()+20).css('left',$("#twice").position().left+50);    
            }else{
                $("#error").css("display","").css("position","absolute").css('top',errorObj.position().top-33).css('left',errorObj.position().left);    
            }
            
            $("#error").find("p").html(msg);
        }
    };
    
    $.Checktype = {
        "match":/^(.+)(\d+)-(\d+)$/,
		"*":/.+/,
		"*6-16":/^.{6,16}$/,
		"n":/^\d+$/,
		"n6-16":/^\d(6,16)$/,
		"i":/^[1-9]\d*$/,
		"i2-9":/^[1-9]\d{1,8}/,
		"d":/^[+-]?\d+(.\d+)?$/,
		"d16-3":/^\d{13}(.\d{0,3})?$/,
		"s":/^[\u4E00-\u9FA5\uf900-\ufa2d\w]+$/m,
		"s6-18":/^[\u4E00-\u9FA5\uf900-\ufa2d\w]{6,18}$/,
		"p":/^[0-9]{6}$/,
		"m":/^13[0-9]{9}$|15[0-9]{9}$|18[0-9]{9}$/,
		"e":/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
    };
    $.CheckFunc = {        
        dateCheck:{
            //闰年判断
            isLeapYear:function(year) 
            {
             	if((year%4==0&&year%100!=0)||(year%400==0)) 
             	{ 
             		return true; 
             	} 	 
             		return false; 
            },
            exchangeDateStyle:function(value)
            {
                var s1;
            	var s;
            	var year;
            	var month;
            	var day;
            	s=value;
            	
            	if(s.length==8)
            	{	
            		year=s.substring(0,4);
            		month=s.substring(4,6);
            		day=s.substring(6,8);
            		
            		s1=year+"-"+month+"-"+day;
            	}
            	
            	return s1;
            },
            main:function(gets){
            	if(gets.length==8){
            	    gets=this.exchangeDateStyle(gets);
            	}
            	var reDate = /(1\d{3}|2\d{3})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])/;
                var reFlag =  reDate.test(gets);  
                if(reFlag){
                    var tempArray = gets.split("-");
                    var tempy = tempArray[0]; 		//年
                	var tempm = tempArray[1]; 		//月	
                	var tempd = tempArray[2]; 		//日
                    if(tempm[2]=="02"){
                        if(isLeapYear(tempy)&&tempd>29)
             			{ 
               				return false; 
             			}       
         				if(!isLeapYear(tempy)&&tempd>28)
         				{  
              				 return false; 
             			}
                    }
                    if((tempm == 4||tempm == 6||tempm == 9||tempm == 11)&&tempd>30) 
			        {
       				    return false; 
			        }
                }else{
                    return false;    
                }
                return true;                    
            }            
        }        
    };
})(jQuery);