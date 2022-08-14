var selnodehref=null;

function SetCookie(sName, sValue)
{
  date = new Date();
  document.cookie = sName + "=" + escape(sValue) + "; expires=" + date.toGMTString();
}

function GetCookie(sName)
{
  // cookies are separated by semicolons
  var aCookie = document.cookie.split("; ");
  var strvar=null;
  for (var i=0; i < aCookie.length; i++)
  {
    // a name/value pair (a crumb) is separated by an equal sign
    var aCrumb = aCookie[i].split("=");
    if (sName == aCrumb[0]) 
    {
      strvar=unescape(aCrumb[1]);
      break;
     }
  }
  // a cookie with the requested name does not exist
  return strvar;
}


function getcurdate(format)
{
   var strdate=new Date();
   var daystr=strdate.getDate();
   var yearstr=strdate.getYear();
   var monthstr=strdate.getMonth()+1;
   var restr="";
   switch(type)
   {
      case 1:restr=yearstr+monthstr+daystr;
      case 2:restr=yearstr+"-"+monthstr+"-"+daystr;	
      case 3:restr=monthstr+"/"+daystr+"/"+yearstr;
   }
   return restr;
}
