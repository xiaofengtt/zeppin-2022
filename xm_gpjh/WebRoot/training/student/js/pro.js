/////////////////////////////////////
/////////////////////////////////////
document.write("<style type='text/css'>");
document.write("<!--");
document.write(".pro {");
document.write("	border-left:1px solid #333333;");
document.write("	border-right:1px solid #333333;");
document.write("}");
document.write(".last {");
document.write("	border-right:1px solid #888888;");
document.write("}");
document.write(".tab {");
document.write("	border-left:1px solid #888888;");
document.write("	border-right:1px solid #888888;");
document.write("}");
document.write(".tabMove {");
document.write("	border-left:1px solid #333333;");
document.write("	border-right:1px solid #888888;");
document.write("}");
document.write(".9pt {");
document.write("	color:#006699;");
document.write("	font-size:9pt;");
document.write("	padding-top:1px;");
document.write("	padding-left:2px;");
document.write("	padding-right:1px;");
document.write("	background-color:#efefef;");
document.write("}");
document.write("-->");
document.write("</style>");
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
function CreatPro(id,parPro,parTTime,parDelay,parStep,parStepDelay,parPath){
	var step=1;//pro步幅，传递值，默认1
	var stepT=10;//pro步幅间隔时间，传递值，默认10
	var pro;//pro最终值，传递值
	var delay;//pro执行前停顿时间，传递值
	var totalTime;//对象代表课程的总时间
	var path;//相对路径
	var tempPro;
	var curStep;
	var count;
	var curCount;
	var defWidth;
	/////////////////////////
	pro = parPro;
	totalTime = parTTime;
	delay = parDelay;
	step = parStep;
	stepT = parStepDelay;
	path = parPath;
	/////////////////////////
	this.showPro = showPro();
	function showPro(){
		document.write("<table width=100 height=16 cellpadding = 0 cellspacing = 0 margin-left=0 margin-top=0>");
		document.write("<tr>");
		document.write("<td>");
		document.write("<table cellpadding = 0 cellspacing = 0 border=0 width=100>");
		document.write("<tr height='10'><td align='center' width=100% class='9pt' id='proTxt"+ id +"'></td>");
		document.write("<td align=right width=0 class='9pt' id='proTxtTm"+ id +"'></td></tr></table>");
		document.write("</td>");
		document.write("</tr>");
		document.write("<tr>");
		document.write("<td>");
		document.write("<table id='tab"+ id +"' cellpadding = 0 cellspacing = 0 width =100 height=7 class='tab'>");
		document.write("<tr height=7><td id='pro"+ id +"' width=0% style=\"background-image:url(\'"+ path +"images/common/pro.gif\');\">");
		document.write("<img src=\'"+ path +"images/common/transparent.gif\' style='width:0'></td>");
		document.write("<td id='last"+ id +"' width=100% style=\"background-image:url(\'"+ path +"images/common/last.gif\')\">");
		document.write("<img src='"+ path +"images/common/transparent.gif'></td></tr></table>");
		document.write("</td>");
		document.write("</tr>");
		document.write("</table>");
		
		//////////////////////////
		this.setPro = setPro();
	}
	//////////////////////////
	function setPro(){
		document.getElementById("pro"+id).className="";
		document.getElementById("last"+id).className="";
		document.getElementById("tab"+id).className="tabMove";
	
		if(pro<=100 & pro>=0){
			tempPro = Number(document.getElementById("pro"+id).clientWidth);
			curStep = (pro>tempPro)?step:(-step);
			count = Math.abs(pro-tempPro)/step;
			curCount =1;
		}
	}
	window.obj=this;
	//////////////////////////
	function stepPro(){
		if(curCount<count){
			document.getElementById("pro"+id).style.width = tempPro+curStep+"%";
			document.getElementById("last"+id).style.width = 100-(tempPro+curStep)+"%";
			document.getElementById("proTxt"+id).innerText = tempPro+curStep+"%";
			tempPro+=curStep;
			curCount++;
			setTimeout("obj.stepPro()",stepT);
		}else
			this.dSetPro = dSetPro();
	}
	///////////////////////////
	this.stepPro = function(){
		document.getElementById("pro"+id).style.width = String(pro)+"%";
		document.getElementById("last"+id).style.width = String(100-pro)+"%";
		document.getElementById("proTxt"+id).innerText = String(pro)+"%";
		//document.getElementById("proTxt"+id+"2").innerText = "共"+totalTime+"学时";
		if(id == "selectC22")
		{
			//document.getElementById("proTxt"+id).innerText = "ss";
			//alert(document.getElementById("proTxt"+id).innerText);
		
		}
		this.setBorder =setBorder();
	}
	///////////////////////////
	function setBorder(){
		if(pro==0){
			document.getElementById("tab"+id).className="tab";
			document.getElementById("pro"+id).className="";
			document.getElementById("last"+id).className="";
		}else if(pro>=98){
			document.getElementById("tab"+id).className="";
			document.getElementById("pro"+id).className="pro";
			document.getElementById("last"+id).className="";
		}else{
			document.getElementById("tab"+id).className="";
			document.getElementById("pro"+id).className="pro";
			document.getElementById("last"+id).className="last";
		}
	}
}
