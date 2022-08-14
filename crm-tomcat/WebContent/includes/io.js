//Title  : 有关IO功能的通用js方法
//Author : Felix
//Date   : 2007-12-6

var idTmr = "";//手工释放进程参数变量

function getExcelColsCount(objPath){

	var file_name = objPath;//文件名赋值
	var excelOBJ = new ActiveXObject("Excel.Application");//建立插件对象
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//打开相应的Excel文件
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//建立Sheet对象
	excelSheet.Activate;//激活该Sheet
	
	var colsCount =  excelSheet.UsedRange.Cells.Columns.Count;
	
	excelBook.close();
	excelOBJ.Quit();
	excelOBJ = null;

	idTmr = window.setInterval("Cleanup()",1);
	
	return colsCount;
}

function getExcelRowsCount(objPath){

	var file_name = objPath;//文件名赋值
	var excelOBJ = new ActiveXObject("Excel.Application");//建立插件对象
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//打开相应的Excel文件
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//建立Sheet对象
	excelSheet.Activate;//激活该Sheet
	
	var rowsCount = excelSheet.UsedRange.Cells.Rows.Count;
	
	excelOBJ.Quit();
	excelOBJ = null;
	idTmr = window.setInterval("Cleanup()",1);
	
	return rowsCount;
}

function readExcelbyRow(objPath,fieldArray,rowNum){
	var file_name = objPath;//文件名赋值
	var fields = fieldArray;//导入字段赋值
	var totalValue = "";//最终导出值
	var excelOBJ = new ActiveXObject("Excel.Application");//建立插件对象
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//打开相应的Excel文件
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//建立Sheet对象
	excelSheet.Activate;//激活该Sheet
	
	var rowsNum = excelSheet.UsedRange.Cells.Rows.Count;
	var colsNum = excelSheet.UsedRange.Cells.Columns.Count;

	if(rowNum>rowsNum){
		alert("所要读取的行超过了EXCEL的总行数，请检查！");
		return false;
	}
	
	if(fields!=null&&fields!=""){
		for(i=0;i<fields.length;i++){
			var value = fields[i];
			if((value < 1) || (value > colsNum))
			{
				alert("Excel文件字段Index从1开始，请确定字段Index大于等于1小于最大值！");
				return false;
			}else{
				totalValue = totalValue + excelSheet.Cells(rowNum,fields[i]).Value +"#";
			}
		}
	}else{
		for(i=1;i<=colsNum;i++){
			totalValue = totalValue + excelSheet.Cells(rowNum,i).Value +"#";
		}
	}
	
	excelOBJ.Quit();
	excelOBJ = null;
	idTmr = window.setInterval("Cleanup()",10);

	return totalValue;

}

/**
 * 使用"Excel.Application"插件读取Excel文件的通用方法
 * @param objPath fieldArray dropRowFlag totalFlag
 * return
 * @author Felix
 * @Date 2007-12-6	
 */ 
function readExcelByFields(objPath,fieldArray,dropRowFlag,totalFlag) //totalFlag为合计行数
{
	var file_name = objPath;//文件名赋值
	var fields = fieldArray;//导入字段赋值
	var totalValue;//最终导出值
	var excelOBJ = new ActiveXObject("Excel.Application");//建立插件对象
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//打开相应的Excel文件
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//建立Sheet对象
	excelSheet.Activate;//激活该Sheet
	
	var rowsNum = excelSheet.UsedRange.Cells.Rows.Count;
	var colsNum = excelSheet.UsedRange.Cells.Columns.Count;
	if(totalFlag != null && totalFlag != "")
	{
		if(rowsNum > totalFlag)
		{
			rowsNum = rowsNum - totalFlag;
		}
		else
		{
			sl_alert("请确认总行数大于合计行数！");
			return false;
		}
	}
	else if(totalFlag == null)
	{
		sl_alert("请判断文件是否有合计行！");
		return false;
	}
	for(var i=0;i<fields.length;i++)
	{
		var value = fields[i];
		if((value < 1) || (value > colsNum))
		{
			sl_alert("Excel文件字段Index从1开始，请确定字段Index大于等于1小于最大值！");
			return false;
		}
	}
	if(dropRowFlag == 1)//丢弃首行数据
	{
		if(rowsNum <= 1 || colsNum <=0)
		{
			sl_alert("文件数据为空，请在导入前校验文件！");
			return false;
		}
		totalValue = new Array(rowsNum-1);
		for(var i=2;i<=rowsNum;i++)
		{
			var rowValue = "";
			for(var j=0;j<fields.length;j++)
			{	
				var cellValue = excelSheet.Cells(i,fields[j]).Value;
				if(cellValue == null || cellValue == "")
				{
					cellValue = "0";
//					sl_alert("存在空字段，请在导入文件前校验数据！");
				}
				rowValue = rowValue.toString() + cellValue.toString() + "#";
			}
			totalValue[i-2] = rowValue.toString();
		}
	}
	if(dropRowFlag == 0)//首行为有用数据行
	{
		if(rowsNum <= 0 || colsNum <=0)
		{
			sl_alert("文件数据为空，请在导入前校验文件！");
			return false;
		}
		totalValue = new Array(rowsNum);
		for(var i=1;i<=rowsNum;i++)
		{
			var rowValue = "";
			for(var j=0;j<fields.length;j++)
			{	
				var cellValue = excelSheet.Cells(i,fields[j]).Value;
				if(cellValue == null || cellValue == "")
				{
					cellValue = "0";
//					sl_alert("存在空字段，请在导入文件前校验数据！");
				}
				rowValue = rowValue.toString() + cellValue.toString() + "#";
			}
			totalValue[i-1] = rowValue.toString();
		}
	}
	if((dropRowFlag != 1) && (dropRowFlag != 0))
	{
		sl_alert("请选择Excel文件首行是否为可用数据行，若首行为标题航则dropRowFlag置为1，若首行作为数据航则dropRowFlag置为0!");
		return false;
	}
	excelOBJ.Quit();
	excelOBJ = null;
	idTmr = window.setInterval("Cleanup()",1);
	if(totalValue.length > 0)
	{
		return totalValue;
	}
	else
	{
		return null;
	}
}
/**
 * 解析字段值，为“”或为null返回字符串null用于标识和占位
 * @param file_name指定文件名
 * return
 * @author Felix
 * @Date 2007-12-21	
 */ 
function parseDBFfield(value)
{
	document.theform.tempvalue.value = value.toString();
	var temp_value = document.theform.tempvalue.value;
	document.theform.tempvalue.value = "";
	if(temp_value == "" || temp_value == null)
	{
		temp_value = "null";
		return temp_value;
	}
	else
	{
		return temp_value;
	}
}
/**
 * 根据文件名读取DBF交割文件
 * @param file_name指定文件名
 * return
 * @author Felix
 * @Date 2007-12-21	
 */ 
function readDBF(file_name)
{
	SLDBF.OpenDbf(file_name);
	var i_count = SLDBF.GetTotalCount();
	var fieldValue = new Array(i_count);//以行为单位建立数组，逐行赋值
		
	for(var irow = 1;irow <= i_count ; irow++)
	{
		SLDBF.GoTo(irow);
         
		if(SLDBF.IsDelete()==0)
		{
			fieldValue[irow-1] = parseDBFfield(SLDBF.FieldValue("gdzh")) + "$"  
								+ parseDBFfield(SLDBF.FieldValue("gdxm")) + "$" 
								+ parseDBFfield(SLDBF.FieldValue("zqdm")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("cjsl")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("bcye")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("pjjg")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("cjje")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("bzyj")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("ghf")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("yhs")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("sjje")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("cjrq")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("xwh")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("hsbz")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("fjbz")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("cjbh")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("zjzh")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("zjye")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("cur")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("gzlx")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("lxje")) + "$"
								+ parseDBFfield(SLDBF.FieldValue("bz")) + "$";
								
		}
		
	}
	SLDBF.CloseDbf();
	return fieldValue;
}
/**
 * 根据文件名读取DBF对账文件
 * @param file_name指定文件名
 * return
 * @author Felix
 * @Date 2007-12-21
 */ 
function readDBFDz(file_name)
{
	SLDBF.OpenDbf(file_name);
	var i_count = SLDBF.GetTotalCount();
	var fieldValue = new Array(i_count);//以行为单位建立数组，逐行赋值
	for(var irow = 1;irow <= i_count ; irow++)
	{
		SLDBF.GoTo(irow);
		if(SLDBF.IsDelete()==0)
		{   
			fieldValue[irow-1] = parseDBFfield(SLDBF.FieldValue("gdzh")) + "$"
          						+ parseDBFfield(SLDBF.FieldValue("zjzh")) + "$"
          						+ parseDBFfield(SLDBF.FieldValue("zqdm")) + "$"
          						+ parseDBFfield(SLDBF.FieldValue("zqye")) + "$"
          						+ parseDBFfield(SLDBF.FieldValue("zxjg")) + "$"
          						+ parseDBFfield(SLDBF.FieldValue("zqsz")) + "$"
          						+ parseDBFfield(SLDBF.FieldValue("cjrq")) + "$"; 
          }
	}  
       SLDBF.CloseDbf();
	return fieldValue;
}
 
/**
 * 修正使用"Excel.Application"插件导致MS的BUG，手工处理Excel进程
 * @param
 * return
 * @author Felix
 * @Date 2007-12-6	
 */ 
function Cleanup() 
{
	window.clearInterval(idTmr);
	CollectGarbage();
}
/**
 * 初始化div查询框
 * @param
 * return
 * @author Felix
 * @Date 2007-12-6	
 */ 
function initImportFile()
{	
	queryCondition = document.getElementById("queryCondition");
	if(queryCondition==null)
	    sl_alert("该页面不存在查询条件控件！");
	else
	{
		queryButton = document.getElementById("queryButton");
		//alert(document.getElementById("queryButton").value);
		if(queryButton==null)
		{
			sl_alert("该页面不存在增强型查询按钮！");
		}
		else
		{			
			queryCondition.style.top = queryButton.offsetTop+50;	
//			var left = queryButton.offsetLeft+queryButton.offsetParent.offsetLeft-parseInt(queryCondition.style.width)-10;		
			var left = (((window.screen.width)/2)-(parseInt(queryCondition.style.width)/2)-100);
			queryCondition.style.left = (left<0)?0:left;
			showQueryCondition();
			document.onmouseup=function(){moveFlag=false;}		
		}	
	}	
}
/**
 * 解决IE5.5-IE6版本中png图片出现背景的问题
 * @param
 * return
 * @author Felix
 * @Date 2007-12-6	
 */ 
function fixPng() 
{
	var arVersion = navigator.appVersion.split("MSIE")
	var version = parseFloat(arVersion[1])
 
	if ((version >= 5.5 && version < 7.0) && (document.body.filters)) 
	{
		for(var i=0; i<document.images.length; i++) 
		{
      		var img = document.images[i];
      		var imgName = img.src.toUpperCase();
			if (imgName.indexOf(".PNG") > 0) 
			{
				var width = img.width;
				var height = img.height;
				var sizingMethod = (img.className.toLowerCase().indexOf("scale") >= 0)? "scale" : "image"; 
				img.runtimeStyle.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img.src.replace('%23', '%2523').replace("'", "%27") + "', sizingMethod='" + sizingMethod + "')";
				img.src = "images/blank.gif";
				img.width = width;
				img.height = height;
			}
		}
	}
}

/**
 * 使用enfouploadfile控件时，取文件名的方法
 * @param element指定显示文件全名的对象
 * return
 * @author Felix
 * @Date 2007-12-6
 */ 
function callSelFiles(element)
{
    
    var path = element.value;
    if(path == "" || path == null)
    	path = "";	
    var path1 = enfoUploadFile.SelFiles();
    if((path1 == "") || (path1 == null))
    {
    	element.value = path;
    }
    else
    {
    	element.value = path1;
    }
}
/**
 * 从路径中获得文件名
 * @param fullName完整路径名
 * return
 * @author Felix
 * @Date 2007-12-20	
 */ 
function getLastFileName(fullName)
{
	if((fullName == null) || (fullName == ""))
	{
		alert("文件名无效，请检查！");
		return false;;
	}
	else
	{
		var pos = fullName.lastIndexOf("\\");
		if(pos>0 && pos<fullName.length-1)	
		{
			var fileName = fullName.substring(pos + 1,fullName.length);
			return fileName;
		}
		else
		{
			alert("文件名无效，请检查！");
			return false;
		}
	}
}
/**
 * 在table中插入行
 * @param tb表元素、str插入的html语句
 * return
 * @author Felix
 * @Date 2007-12-21	
 */ 
function insTable(tb,str)
{
    var ol;
    var o=document.createElement("div");
    o.innerHTML="<table>"+str+"</table>";
    ol=o.childNodes[0].tBodies[0].rows;
    while(ol.length>0)
    {
        tb.tBodies[0].appendChild(ol[0]);
    }
}

function clearDiv()
{
	var show_info_div = document.getElementById("show_info_div");
	show_info_div.innerHTML = "";
}

function EscDiv()
{
	var show_info_div = document.getElementById("show_info_div");
	show_info_div.innerHTML = "";
	cancelQuery();
}