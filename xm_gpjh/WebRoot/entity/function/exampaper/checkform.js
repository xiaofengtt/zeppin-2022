	Object.prototype.getAttribute= function(name)
	{	
		return this.attributes[name].nodeValue;	
	}	
	Object.prototype.setHook=function(hook)
	{			
		this.setAttribute('hook',hook);					
	}
	Object.prototype.setRegx=function(regx)
	{			
		this.setAttribute('regx',regx);					
	}
	
	String.prototype.trim=function()
	{
        return this.replace(/(^\s*)|(\s*$)/g, "");
	}

	
	function check(id)
	{
		var flag=true;
		var fromhook=false;
		var input=document.getElementById(id);
		var min=input.getAttribute("min");
		var max=input.getAttribute("max");
		var trim=input.getAttribute("trim");
		var regx=input.getAttribute("regx");
		var hook=input.getAttribute("hook");
		var msg=input.getAttribute("msg");
		
		if(input.type=="password"&&(trim==null||trim==""))
			trim="false";
		if(trim==null||trim=="")
			trim="true";
		
		var value=input.value;
		if(trim=="true"||trim=="TRUE")		
			value=value.trim();		
		
		len=value.length;
		if(min!=null)
		{
			if(len<min)
				flag=false;
		}
		if((len!=null)&&flag)
		{
			if(len>max)
				flag=false;
		}
		if((regx!=null)&&flag)
		{
			if(!regx.test(value))
				flag=false;
		}
		if((hook!=null)&&flag)
		{
			var innerMsg=eval(hook);	
			if(innerMsg!=null&&innerMsg!="")
			{
				if(innerMsg=='true'||innerMsg=='false'||innerMsg=='TRUE'||innerMsg=='FLASE')
				{
					fromhook=true;
					flag=innerMsg;
				}
				else
				{
					msg=innerMsg;
					flag=false;
				}
			}
		}
		if(!flag)
		{	
			input.focus();
			if(!fromhook)	
				alert(msg);
		}
		
		return flag;	
	}
	
	function checkAll(ids)
	{
		var idArray=ids.split(",");
		var flag=true;
		for(i=0;i<idArray.length;i++)
		{
			if(!check(idArray[i]))
			{
				flag=false;
				break;
			}
		}
		return flag;	
	}
	
	function setRemoteHook(local,flag,remote,hook)
	{			
		var wedge=document.getElementById(local);
		var target=document.getElementById(remote);
		if(wedge.value==flag)
		{			
			target.setAttribute("hook",hook);
		}
		else
		{			
			target.removeAttribute("hook");
		}							
	}
	



function checkCardNO(idCard)
{
 	var idDate=new RegExp("^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$");
	var yyyy,mm,dd;
	var cardNO=document.getElementById(idCard).value;
	var len=cardNO.lenght;
	var brithday="";
	var thisYear=new Date().getYear();
    if (len==0){
        alert("请输入身份证号码!");
        return false;
    }
    if (len!=15 && len!=18)
    {
        alert("身份证号长度应为15位或18位！");
        return false;
    }

    if (len==15)
    {    	
        yyyy="19"+cardNO.substring(6,8);
        mm=cardNO.substring(8,10);
        dd=cardNO.substring(10,12);
		if(yyyy<1950||yyyy>1998)
		{
			alert("年份非法！");
            return false;
		}
        if (mm>12 || mm<=0){
            alert("月份非法！");
            return false;
        }

        if (dd>31 || dd<=0){
            alert("日期非法！");
            return false;
        }
        birthday=yyyy+ "-" +mm+ "-" +dd;
    }
    else if (id_length==18)
    {
        if (id.indexOf("X") > 0 && id.indexOf("X")!=17 || id.indexOf("x")>0 && id.indexOf("x")!=17)
        {
            alert("身份证中\"X\"输入位置不正确！");
            return false;
        }
        yyyy=cardNO.substring(6,10);      
        if (yyyy>(thisYear-15) || yyyy<(thisYear+150))
        {
            alert("年度非法！");
            return false;
        }
        mm=id.substring(10,12);
        if (mm>12 || mm<=0)
        {
            alert("月份非法！");
            return false;
        }

        dd=id.substring(12,14);
        if (dd>31 || dd<=0){
            alert("日期非法！");
            return false;
        }

        if (id.charAt(17)=="x" || id.charAt(17)=="X")
        {
            if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id))
            {
                alert("身份证校验错误，请检查最后一位！");
                return false;
            }
        }
        else
        {
            if (id.charAt(17)!=GetVerifyBit(id))
            {
                alert("身份证校验错误，请检查最后一位！");
                return false;
            }
        }
        birthday=id.substring(6,10) + "-" + id.substring(10,12) + "-" + id.substring(12,14);
        
    }
    if(!isDate.test(birthday))
    {
    	alert("该年不是闰年!");
    	return false;
    }
    return true;
    
}

function GetVerifyBit(id)
{
    var result;
    var nNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
    nNum=nNum%11;
    switch (nNum) {
       case 0 :
          result="1";
          break;
       case 1 :
          result="0";
          break;
       case 2 :
          result="X";
          break;
       case 3 :
          result="9";
          break;
       case 4 :
          result="8";
          break;
       case 5 :
          result="7";
          break;
       case 6 :
          result="6";
          break;
       case 7 :
          result="5";
          break;
       case 8 :
          result="4";
          break;
       case 9 :
          result="3";
          break;
       case 10 :
          result="2";
          break;
    }

    return result;
}

	
	