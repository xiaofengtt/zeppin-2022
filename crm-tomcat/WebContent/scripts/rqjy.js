function checkdate(checkstr) {
	if(checkstr==""){return true}
	try
	{
		ymd1=checkstr.split("-");
		month1=ymd1[1]-1
		var Date1 = new Date(ymd1[0],month1,ymd1[2]); 
		if (Date1.getMonth()+1!=ymd1[1]||Date1.getDate()!=ymd1[2]||Date1.getFullYear()!=ymd1[0]||ymd1[0].length!=4)
		{
			alert("非法日期,请依【YYYY-MM-DD】格式输入"); 
			return false;          
		}checkstr
		return true;
	}
	catch(err){
		alert("对不起，时间错误！");
		return false;
	}
}
