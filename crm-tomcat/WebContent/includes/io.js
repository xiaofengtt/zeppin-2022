//Title  : �й�IO���ܵ�ͨ��js����
//Author : Felix
//Date   : 2007-12-6

var idTmr = "";//�ֹ��ͷŽ��̲�������

function getExcelColsCount(objPath){

	var file_name = objPath;//�ļ�����ֵ
	var excelOBJ = new ActiveXObject("Excel.Application");//�����������
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//����Ӧ��Excel�ļ�
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//����Sheet����
	excelSheet.Activate;//�����Sheet
	
	var colsCount =  excelSheet.UsedRange.Cells.Columns.Count;
	
	excelBook.close();
	excelOBJ.Quit();
	excelOBJ = null;

	idTmr = window.setInterval("Cleanup()",1);
	
	return colsCount;
}

function getExcelRowsCount(objPath){

	var file_name = objPath;//�ļ�����ֵ
	var excelOBJ = new ActiveXObject("Excel.Application");//�����������
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//����Ӧ��Excel�ļ�
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//����Sheet����
	excelSheet.Activate;//�����Sheet
	
	var rowsCount = excelSheet.UsedRange.Cells.Rows.Count;
	
	excelOBJ.Quit();
	excelOBJ = null;
	idTmr = window.setInterval("Cleanup()",1);
	
	return rowsCount;
}

function readExcelbyRow(objPath,fieldArray,rowNum){
	var file_name = objPath;//�ļ�����ֵ
	var fields = fieldArray;//�����ֶθ�ֵ
	var totalValue = "";//���յ���ֵ
	var excelOBJ = new ActiveXObject("Excel.Application");//�����������
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//����Ӧ��Excel�ļ�
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//����Sheet����
	excelSheet.Activate;//�����Sheet
	
	var rowsNum = excelSheet.UsedRange.Cells.Rows.Count;
	var colsNum = excelSheet.UsedRange.Cells.Columns.Count;

	if(rowNum>rowsNum){
		alert("��Ҫ��ȡ���г�����EXCEL�������������飡");
		return false;
	}
	
	if(fields!=null&&fields!=""){
		for(i=0;i<fields.length;i++){
			var value = fields[i];
			if((value < 1) || (value > colsNum))
			{
				alert("Excel�ļ��ֶ�Index��1��ʼ����ȷ���ֶ�Index���ڵ���1С�����ֵ��");
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
 * ʹ��"Excel.Application"�����ȡExcel�ļ���ͨ�÷���
 * @param objPath fieldArray dropRowFlag totalFlag
 * return
 * @author Felix
 * @Date 2007-12-6	
 */ 
function readExcelByFields(objPath,fieldArray,dropRowFlag,totalFlag) //totalFlagΪ�ϼ�����
{
	var file_name = objPath;//�ļ�����ֵ
	var fields = fieldArray;//�����ֶθ�ֵ
	var totalValue;//���յ���ֵ
	var excelOBJ = new ActiveXObject("Excel.Application");//�����������
	
	var excelBook = excelOBJ.WorkBooks.Open(file_name);//����Ӧ��Excel�ļ�
	excelBook.Windows.Application.DisplayAlerts = false;
	excelBook.Windows.Application.Visible = false;
	
	var excelSheet = excelBook.Sheets(1);//����Sheet����
	excelSheet.Activate;//�����Sheet
	
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
			sl_alert("��ȷ�����������ںϼ�������");
			return false;
		}
	}
	else if(totalFlag == null)
	{
		sl_alert("���ж��ļ��Ƿ��кϼ��У�");
		return false;
	}
	for(var i=0;i<fields.length;i++)
	{
		var value = fields[i];
		if((value < 1) || (value > colsNum))
		{
			sl_alert("Excel�ļ��ֶ�Index��1��ʼ����ȷ���ֶ�Index���ڵ���1С�����ֵ��");
			return false;
		}
	}
	if(dropRowFlag == 1)//������������
	{
		if(rowsNum <= 1 || colsNum <=0)
		{
			sl_alert("�ļ�����Ϊ�գ����ڵ���ǰУ���ļ���");
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
//					sl_alert("���ڿ��ֶΣ����ڵ����ļ�ǰУ�����ݣ�");
				}
				rowValue = rowValue.toString() + cellValue.toString() + "#";
			}
			totalValue[i-2] = rowValue.toString();
		}
	}
	if(dropRowFlag == 0)//����Ϊ����������
	{
		if(rowsNum <= 0 || colsNum <=0)
		{
			sl_alert("�ļ�����Ϊ�գ����ڵ���ǰУ���ļ���");
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
//					sl_alert("���ڿ��ֶΣ����ڵ����ļ�ǰУ�����ݣ�");
				}
				rowValue = rowValue.toString() + cellValue.toString() + "#";
			}
			totalValue[i-1] = rowValue.toString();
		}
	}
	if((dropRowFlag != 1) && (dropRowFlag != 0))
	{
		sl_alert("��ѡ��Excel�ļ������Ƿ�Ϊ���������У�������Ϊ���⺽��dropRowFlag��Ϊ1����������Ϊ���ݺ���dropRowFlag��Ϊ0!");
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
 * �����ֶ�ֵ��Ϊ������Ϊnull�����ַ���null���ڱ�ʶ��ռλ
 * @param file_nameָ���ļ���
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
 * �����ļ�����ȡDBF�����ļ�
 * @param file_nameָ���ļ���
 * return
 * @author Felix
 * @Date 2007-12-21	
 */ 
function readDBF(file_name)
{
	SLDBF.OpenDbf(file_name);
	var i_count = SLDBF.GetTotalCount();
	var fieldValue = new Array(i_count);//����Ϊ��λ�������飬���и�ֵ
		
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
 * �����ļ�����ȡDBF�����ļ�
 * @param file_nameָ���ļ���
 * return
 * @author Felix
 * @Date 2007-12-21
 */ 
function readDBFDz(file_name)
{
	SLDBF.OpenDbf(file_name);
	var i_count = SLDBF.GetTotalCount();
	var fieldValue = new Array(i_count);//����Ϊ��λ�������飬���и�ֵ
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
 * ����ʹ��"Excel.Application"�������MS��BUG���ֹ�����Excel����
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
 * ��ʼ��div��ѯ��
 * @param
 * return
 * @author Felix
 * @Date 2007-12-6	
 */ 
function initImportFile()
{	
	queryCondition = document.getElementById("queryCondition");
	if(queryCondition==null)
	    sl_alert("��ҳ�治���ڲ�ѯ�����ؼ���");
	else
	{
		queryButton = document.getElementById("queryButton");
		//alert(document.getElementById("queryButton").value);
		if(queryButton==null)
		{
			sl_alert("��ҳ�治������ǿ�Ͳ�ѯ��ť��");
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
 * ���IE5.5-IE6�汾��pngͼƬ���ֱ���������
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
 * ʹ��enfouploadfile�ؼ�ʱ��ȡ�ļ����ķ���
 * @param elementָ����ʾ�ļ�ȫ���Ķ���
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
 * ��·���л���ļ���
 * @param fullName����·����
 * return
 * @author Felix
 * @Date 2007-12-20	
 */ 
function getLastFileName(fullName)
{
	if((fullName == null) || (fullName == ""))
	{
		alert("�ļ�����Ч�����飡");
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
			alert("�ļ�����Ч�����飡");
			return false;
		}
	}
}
/**
 * ��table�в�����
 * @param tb��Ԫ�ء�str�����html���
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