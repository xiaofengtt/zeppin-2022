// cardValidata.js

// newFunction

function checkIdcard(idcard){

var area={11:"??",12:"??",13:"??",14:"??",15:"???",21:"??",22:"??",23:"???", 31:"??",32:"??",33:"??",34:"??",35:"??",36:"??",37:"??",41:"??",42:"??", 43:"??",44:"??",45:"??",46:"??",50:"??",51:"??",52:"??",53:"??",54:"??", 61:"??",62:"??",63:"??",64:"??",65:"??",71:"??",81:"??",82:"??",91:"??"} 

var idcard,Y,JYM;
var S,M;
var idcard_array = new Array();
idcard_array = idcard.split("");
//????
if(area[parseInt(idcard.substr(0,2))]==null) return 4;
//???????????
switch(idcard.length){
case 15:
if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0 -1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //??????????
} else {
ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0 -1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3} $/;//??????????
}
if(ereg.test(idcard)) return 0;
else return 2;
break;
case 18:
//18???????
//?????????? 
//????:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
//????:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3 [0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3} [0-9Xx]$/;//???????????????
} else {
ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3 [0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9] {3}[0-9Xx]$/;//???????????????
}
if(ereg.test(idcard)){//??????????
//?????
S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
+ parseInt(idcard_array[7]) * 1 
+ parseInt(idcard_array[8]) * 6
+ parseInt(idcard_array[9]) * 3 ;
Y = S % 11;
M = "F";
JYM = "10X98765432";
M = JYM.substr(Y,1);//?????
if(M == idcard_array[17]) return 0; //??ID????
else return 3;
}
else return 2;
break;
default:
return 1;
break;
}
}

         function CheckForm()
         {
         var Errors=new Array(
"????!",
"?????????!",
"????????????????????!",
"?????????!",
"???????!"
);
         var result=checkIdcard(document.getElementById("txt_sfzmhm").value);
         if(result!=0)
         {
         alert(Errors[result]);
                 document.getElementById("txt_sfzmhm").focus();
                 return false;        
         }        
             if(document.getElementById("txt_djzsxxdz").value=="")
             {
                 alert('??????????????');
                 document.getElementById("txt_djzsxxdz").focus();
                 return false;
             }
             return true;
         }

//================================================
//????
function RemoveAllOption(obj)
{
   var len = obj.length-1;
   for(var i=len; i>=0; i--)
   {
     obj.options.remove(i);
   }
}
function AddEmptyOption(obj)
{
   obj.options.add(new Option("",""));
}
function FirstChangeOption1(ele,first,second,field)
{
   RemoveAllOption(second);
   second.options.add(new Option(first.options[first.selectedIndex].text,"")); 
   var TempOptionStr;
     var SkillCode = field.value;
     var currentValue = first.value;
     if (currentValue.length >= 2){
         var arrValue = SkillCode.split(";");
         for (var i=0;i<arrValue.length;i++){
             var arrOneValue = arrValue[i].split(",");
             if (currentValue.substr(0,2) == arrOneValue[0].substr(0,2)) {     //????????
             if( arrOneValue[0].length >2 && arrOneValue[0] != currentValue)
             {
                     TempOptionStr= '?'+arrOneValue[1];
                     second.options.add(new Option(TempOptionStr,arrOneValue[0]));
             }
             }
         }
     }
   if (second.options.length <= 1) { 
   second.style.display='none'; document.getElementById(ele).value = first.value ;
   }
     else   second.style.display='';   }
function FirstChangeOption2(ele,first,second,third,field)
{
   RemoveAllOption(second);
   second.options.add(new Option(first.options[first.selectedIndex].text,""));   RemoveAllOption(third);
   AddEmptyOption(third);
   var TempOptionStr;
     var SkillCode = field.value;
     var currentValue = first.value;
     if (currentValue.length >= 2){
         var arrValue = SkillCode.split(";");
         for (var i=0;i<arrValue.length;i++){
             var arrOneValue = arrValue[i].split(","); 
             if (currentValue.substr(0,2) == arrOneValue[0].substr(0,2)) {     //????????
                 var IsZero = true; 
                 var ssub = arrOneValue[0].substr(4);
                 for(var j=0; j<ssub.length;j++)
                 {
                     if(ssub.substring(j,j+1) != "0")
                     {
                       IsZero = false;
                       break;
                     }
                 }
             if(IsZero && arrOneValue[0]!=currentValue)
             {
                   TempOptionStr= '?'+arrOneValue[1];
                   second.options.add(new Option(TempOptionStr,arrOneValue[0]));
             }
             }
         }
     }
document.getElementById(ele).value = first.value;

if (second.options.length <= 1) second.style.display='none'; else second.style.display='';
if (third.options.length <= 1) third.style.display='none'; else third.style.display=''; }
function SecondChangeOption(ele,second,third,field)
{
   RemoveAllOption(third);
   third.options.add(new Option(second.options[second.selectedIndex].text.replace('?',''),""));
     var TempOptionStr;
     var SkillCode = field.value;
     var currentValue = second.value;
     if (currentValue.length!=0){
         var arrValue = SkillCode.split(";");
         for (var i=0;i<arrValue.length;i++){
             var arrOneValue = arrValue[i].split(",");
             if (arrOneValue[0].length>4 && arrOneValue[0] != currentValue   && currentValue.substring(0,4) == arrOneValue[0].substr(0,4)) {     //????????
                 TempOptionStr= '?'+arrOneValue[1];
                 third.options.add(new Option(TempOptionStr,arrOneValue[0]));
             }
         }
     }
document.getElementById(ele).value = second.value; 

if (third.options.length <= 1) third.style.display='none'; else third.style.display=''; }
function ThirdChangeOption(ele,third)
{
   document.getElementById(ele).value = third.value ;
  
   
}
