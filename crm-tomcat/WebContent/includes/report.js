//Create By CaiYuan 2005
//---------------------------一些提示功能-----------------------------------------
function rp_checkOpen(fileName)
{
	if(fileName=="")
	{
		rp_alert("服务嚣存放报表cll文件路径不正确,未打开任何报表文件！");
		return false;
	}
}

function rp_alert(errinfo)
{
	alert("系统提示：　　　　\n\n" + errinfo + "\n\n");
}	

function login(obj)
{
	if(obj.Login( "新力新报表管理系统","" ,"13040468", "3600-1505-7761-0005" ) == 0 )	
	{
		rp_alert( "注册CELL插件失败!");
		return false;
	}
}
//---------------------------报表相关功能操作--------------------------------------
//打开文件
function OpenReport()
{	
    ret = showModalDialog('openfile.jsp','','dialogTop:120px;dialogLeft:20px;dialogWidth:390px;dialogHeight:200px;status:0;help:0');
    if(ret!=null&&ret!="")
    {    	
    	ret = changeSeperator(ret);     		
    		location.href = "init.jsp?filename=" + ret ;
    }	
}

//打印预览
function PrintPreview(flag)
{
	login(SingleeReport);	
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,80,120,80);	
	if(flag!=null)	//打印全部
	{
		SingleeReport.PrintPreview( 0, SingleeReport.GetCurSheet());	//打印预览
	}	
	else
		SingleeReport.PrintPreview( 0, 1);	//打印预览
}

//打印
function PrintReport(flag)
{	
	login(SingleeReport);		
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,80,120,80);
	if(flag!=null)	//打印全部
	{
		SingleeReport.PrintSetPrintRange(0,0);
		SingleeReport.PrintSheet( 0, -1);	//打印
	}	
	else
		SingleeReport.PrintSheet( 0, 1);	//打印
		
}

//选择产品
function ChooseProduct()
{	
    ret = showModalDialog('chooseproduct.jsp','','dialogTop:120px;dialogLeft:500px;dialogWidth:460px;dialogHeight:500px;status:0;help:0');
	
}
//-----------------------------------导出文件相关-----------------------------------------------------------------
//由于报表控件和JS不在同一个页面，所以需要把OBJ传进来
function dl_alert(fileName)
{
	alert("导出文件成功！\n\n文件名："+fileName);

}

//导出Excel
function ExportExcel(obj)
{	
	login(obj);
	if(obj.ExportExcelDlg()==1)
		rp_alert("导出Excel文件成功！");
}

//导出Cell
function ExportCell(obj,sheet)
{		
	login(obj);	
	if(obj.SaveSheet(sheet)==1)
	{		
		obj.CloseFile();
		rp_alert("导出Cell文件成功！");
	}
}

//导出TXT
function ExportTXT(obj)
{	
	if(obj.ExportTextDlg()==1)
		rp_alert("导出文件成功！");		
}

//导出HTML
function ExportHTML(obj,fileName)
{
	rp_alert("此功能目前暂不支持！");
	//if(obj.ExportHtmlFile(fileName)==1)
	//	dl_alert(fileName);
	//else
	//	alert("导出失败！");
}

//导出PDF
function ExportPDF(obj,fileName,sheet,flag)
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);
	var successFlag = 0;
	if(flag!=null&&flag==1)//导出全部	
		successFlag = obj.ExportPdfFile(fileName,0,0,obj.GetTotalSheets());	
	else
	 	successFlag = obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet));
	if(successFlag==1)
		dl_alert(fileName);
	else
		rp_alert("导出失败！");		
}	
//-------------------------------------------------------------------------------
//根据自定义坐标得到实际坐标--计算列坐标(将字母转换成数字,相当于一个26进制数转换成10进制数)
function getParamPos(flag) 
{		
	var pos;	//数字位	
	var num = 0; 	//返回的列坐标数值
	var abc;	//截取出的字母
	var charTonum; 	//字母转换的对应的数值
	var i;
	for(i=0;i<flag.length;i++)
	{	
		if(flag.substring(i,i+1)>="0"&&flag.substring(i,i+1)<="9")
		{
			pos = i;
			break;
		}	
	}			
	abc = flag.substring(0,pos);	//取出字母
	abc = abc.toUpperCase();	//统一转换成大写字母		
	for(i=abc.length;i>0;i--)
	{
		charTonum = abc.charCodeAt(i-1);
			num = Math.round(Math.pow(26,(abc.length-i))) *( charTonum - 64 ) + num;
	}	
	return num;	
}
//根据自定义坐标得到行坐标
function getNumPos(flag) 
{		
	var pos;	//数字位	
	var i;
	for(i=0;i<flag.length;i++)
	{	
		if(flag.substring(i,i+1)>="0"&&flag.substring(i,i+1)<="9")
		{
			pos = i;			
			break;
		}	
	}
	return flag.substring(pos);
}
//复制单元格格式---纵向复制
function CopyCellStyle_Col(ReportName,Col,Row,Sheet)
{
	ReportName.SetCellAlign(Col,Row,Sheet,ReportName.GetCellAlign(Col,Row-1,Sheet));
	ReportName.SetCellTextStyle(Col,Row,Sheet,ReportName.GetCellTextStyle(Col,Row-1,Sheet));
	ReportName.SetCellNumType(Col,Row,Sheet,ReportName.GetCellNumType(Col,Row-1,Sheet));
}

//复制单元格格式---横向复制
function CopyCellStyle_Row(ReportName,Col,Row,Sheet)
{
	ReportName.SetCellAlign(Col,Row,Sheet,ReportName.GetCellAlign(Col-1,Row,Sheet));
	ReportName.SetCellTextStyle(Col,Row,Sheet,ReportName.GetCellTextStyle(Col-1,Row,Sheet));
	ReportName.SetCellNumType(Col,Row,Sheet,ReportName.GetCellNumType(Col-1,Row,Sheet));
}

//处理单元格宽度--最大宽度
function SetCellWidth(ReportName,Sheet)
{
	var i,width;
	var cols = ReportName.GetCols(Sheet);
	for(i=1;i<=cols;i++)
	{
		width = ReportName.GetColBestWidth(i);
		ReportName.SetColWidth(1,width-3,i,Sheet);  //解决打印不在一页问题 -3
	}
}

//全值合并---有全值合并时要先进行全值合并
function MergeLeftRight(mergeCol,startRow,endRow)
{
	var beginRow,CursorRow; //全值合并时用到的循环变量,一个起始行，一个是游标
	//循环判断左边一列从开始到结束所有行的值是否相等，相等的则合并右边的单元格
	beginRow = startRow;
	lastRow = parseInt(startRow)+1 ;	
	while(lastRow<=endRow)
	{
		previewRowValue = SingleeReport.GetCellString(mergeCol-1,beginRow,SHEET_OUTPUT);
		thisRowValue = SingleeReport.GetCellString(mergeCol-1,lastRow,SHEET_OUTPUT);		
		if((thisRowValue!=previewRowValue))
		{			
			SingleeReport.MergeCells(mergeCol,beginRow,mergeCol,(lastRow-1));				
			beginRow = lastRow;
		}
		else if(lastRow==endRow)
		{
			SingleeReport.MergeCells(mergeCol,beginRow,mergeCol,lastRow);
		}	
		lastRow++;
	}	
}

//将"\"变成"/"用户取文件路径
function changeSeperator(fileName)
{
	return fileName.replace(/\\/g,'/');	
}

//将"\"变成"/"用户取文件路径
function replaceAll(str,old_value,replace_value)
{
	var pos = str.indexOf(old_value);	
	while(pos>=0)
	{
		str = str.replace(old_value,replace_value);
		pos = str.indexOf(old_value);
	}
	return str;	
}

function selectAll(checkname)
{
	if(checkname == null)
		return;
	var iCount = checkedCount(checkname);
	var i;

	if(checkname.length == null)
	{
		checkname.checked = iCount < 1;
		return;
	}

	for(i = 0; i < checkname.length; i++)
		checkname[i].checked = iCount < checkname.length;
}

function checkedCount(element)
{
	var iCount = 0;
	var i;
	
	if(element == null)
		return 0;
		
	if(element.length == null)
	{
		if(element.checked)
			return 1;
		else
			return 0;
	}

	for(i = 0; i < element.length; i++)
		if(element[i].checked)
			iCount++;

	return iCount;
}

function confirmSelect(element)
{
	if (element == null)
	{
		sl_alert("未选定任何记录！");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("请选定相关产品！");
		return false;
	}
	return true;	
}

//判断文件扩展名，只有.cll文件才能上传
function checkCellName(filename)
{	
	var pos = filename.lastIndexOf(".");
	var ext = filename.substring(pos+1).toUpperCase();	
	if(pos<0)
	{
		rp_alert("请先选择报表文件！");
		return false;
	}
	if(filename.indexOf("\\")==filename.lastIndexOf("\\"))
	{
		rp_alert("不能上传根目录文件！");
		return false;
	}
	else if(filename.indexOf("@")<0)
	{
		rp_alert("报表文件名称不合法！");
		return false;
	}
	else if(ext!="CLL")
	{
		rp_alert("非CLL文件不能上传！");
		return false;
	}
	return true;
}

function sl_checkNum(name,str)
{
	var i;
	for (i = 0; i < str.length; i++)
	{
		if (str.charCodeAt(i) < 48 || str.charCodeAt(i) > 57)
		{
			//rp_alert(name + "必须是整数数字！");				
			return false;			
		}
	}
	return true;
}

//打印表头参数取值的正确性判断-------------------------------------
function getPrintTitle(name,value)
{	
	var pos = value.indexOf(":");
	var leftValue,rightValue;
	var ret = false;
	if(pos>0&&pos<value.length)
	{
		leftValue = value.substring(0,pos);
		rightValue = value.substring(pos+1);
		if(sl_checkNum(name+"开始行的行号",leftValue)&&sl_checkNum(name+"结束行的行号",rightValue))		
			ret = new Array(leftValue,rightValue);		
	}
	return	ret; 	
}

//打印页眉、页脚参数取值的正确性判断
function getPrintHead(name,value)
{
	var pos = value.indexOf(",");
	var pos2 = value.lastIndexOf(",");
	var leftValue,middleValue,rightValue;
	var ret = false;
	if(pos>=0&&pos2>0&&pos<pos2)
	{
		if(pos==0)
			leftValue = "";
		else
			leftValue = value.substring(0,pos);
		if((pos+1)==pos2)
			middleValue = "";
		else
			middleValue = value.substring(pos+1,pos2);
		if(pos2==value.length)
			rightValue = "";
		else
			rightValue = value.substring(pos2+1);
		ret = new Array(leftValue,middleValue,rightValue);		
	}
	return ret;		 		 	
}

function printLeftRight(obj,leftValue,rightValue)
{
		var value;
		if(leftValue=="表头行号")
		{
		    value = getPrintTitle("表头行号",rightValue);
		    if(value!=false)		 		    		    
		    	obj.PrintSetTopTitle(value[0],value[1]);
		}
		else if(leftValue=="表左行号")
		{
		    value = getPrintTitle("表左行号",rightValue);
		    if(value!=false)		 			    
		    	obj.PrintSetLeftTitle(value[0],value[1]);
		}
		else if(leftValue=="表右行号")
		{
		    value = getPrintTitle("表右行号",rightValue);
		    if(value!=false)		 			    
		    	obj.PrintSetRightTitle(value[0],value[1]);
		}
		else if(leftValue=="表尾行号")
		{
		    value = getPrintTitle("表尾行号",rightValue);
		    if(value!=false)		 			    		    
		    	obj.PrintSetBottomTitle(value[0],value[1]);
		}
		else if(leftValue=="页眉")
		{
		    value = getPrintHead("页眉",rightValue);		   
		    if(value!=false)		 	    		    
		    	obj.PrintSetHead(value[0],value[1],value[2]);
		}
		else if(leftValue=="页脚")
		{
		    value = getPrintHead("页脚",rightValue);
		    if(value!=false)		 			    
		    	obj.PrintSetFoot(value[0],value[1],value[2]);
		}
}

//
function rp_checkFileStatus(statusValue){
	if(statusValue == -1){
		rp_alert("服务嚣存放报表cll文件路径不正确,未打开任何报表文件！");
		return false;
	}else if(statusValue == -2){
		rp_alert("cll文件操作错误！");
		return false;
	}else if(statusValue == -3){
		rp_alert("cll文件格式错误！");
		return false;
	}else if(statusValue == -4){
		rp_alert("cll文件密码错误！");
		return false;
	}else if(statusValue == -5){
		rp_alert("不能打开高版本cll文件！");
		return false;
	}else if(statusValue == -6){
		rp_alert("不能打开特定版本cll文件！");
		return false;
	}
}