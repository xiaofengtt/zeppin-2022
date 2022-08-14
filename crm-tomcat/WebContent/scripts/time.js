function loadtime() {
var today = new Date();
var year  = today.getYear();
if (year < 2000)   
year = year + 1900; 
var stmonth = today.getMonth() ;
var lstmonth = today.getMonth() +1;
var day  = today.getDate();
var hour = today.getHours();
var minute = today.getMinutes();
if (stmonth <= 9) stmonth = "0" + stmonth;
if (lstmonth <= 9) lstmonth = "0" + lstmonth;
if (day <= 9) day = "0" + day;
if (hour <= 9) hour = "0" + hour;
if (minute <= 9) minute = "0" + minute;
sttime = year + "-" + stmonth + "-" + day ;
lsttime = year + "-" + lstmonth + "-" + day ;
document.getElementById("sttime").value = sttime;
document.getElementById("lsttime").value = lsttime;

}