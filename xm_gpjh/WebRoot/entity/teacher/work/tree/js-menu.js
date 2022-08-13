// JavaScript Document
// JavaScript Document
var tempObj;
function openPage(obj,URL)
{
	if(URL.length>3)
	{
		if(tempObj)	
		{
			tempObj.on = false;
			out(tempObj);
		}
		document.getElementById("main").src = URL;
		obj.on = true;
		tempObj = obj;
	}
}
function out(obj)
{
	if(obj.on!=true)
		obj.className="menuA";
	else if(obj.on == true)
		obj.className = "menuAOn";
}
function over(obj)
{
	obj.className = "menuAOver";
}