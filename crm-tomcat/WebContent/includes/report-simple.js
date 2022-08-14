var SingleeReport ;	//报表对象

//定义报表配置对象
function ConfigVO(){

	this.CELL_FILE ;	//报表文件
	this.ROW_START = 4;	//填充的起始行号
	this.CUR_SHEET;	//当前表页的页号
	this.ROW_HEIGHT;	//行高
	this.columnMap = new Array();	//结果集字段映射
	this.columnEx = new Array();	//字段额外处理
	this.columnNumType = new Array();	//单元格格式	
	this.columnFont = new Array();	//单元格字体
}

var reportConfig;	//新建配置文件对象

/**
 * 读取模板文件
 * @param templateFile 模版文件路径
 * @param sheetName 表页名称 
 */
function initReport(templateFile,obj,sheetName)
{
	SingleeReport = obj;
	reportConfig = new ConfigVO();	//新建配置文件对象
	SingleeReport.OpenFile(templateFile,"");
	rp_checkOpen(SingleeReport.GetFileName());	//检查文件是否存在
	
	SingleeReport.SetCurSheetEx(sheetName);	//读取定义页
	reportConfig.CELL_FILE = templateFile;
	reportConfig.CUR_SHEET = SingleeReport.GetCurSheet();	//获得当前表页的页号
	SingleeReport.ShowSheetLabel(0,reportConfig.CUR_SHEET); 	//隐藏页签	
	SingleeReport.WorkbookReadonly = true;	//设置表页只读

	var i=1;
	while(SingleeReport.GetCellString(i,reportConfig.ROW_START,reportConfig.CUR_SHEET)!="")
	{
		reportConfig.columnMap.push(SingleeReport.GetCellString(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));	//读取结果集字段映射
		reportConfig.columnEx.push(SingleeReport.GetCellTip(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));//读注释参数
		reportConfig.columnNumType.push(SingleeReport.GetCellNumType(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));	//读单元格格式
		reportConfig.columnFont.push(SingleeReport.GetCellFont(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));	//读单元格字体
		i++;
	}
	reportConfig.ROW_HEIGHT = SingleeReport.GetRowHeight(0,reportConfig.ROW_START,reportConfig.CUR_SHEET);	
}

/**
 * 重新读取报表文件（不读取配置信息）
 */
function refreshReport(templateFile,sheet)
{
	SingleeReport.OpenFile(templateFile,"");
	rp_checkOpen(SingleeReport.GetFileName());	//检查文件是否存在
	
	SingleeReport.SetCurSheet(sheet);	//读取定义页
	SingleeReport.ShowSheetLabel(0,reportConfig.CUR_SHEET); 	//隐藏页签	
	SingleeReport.WorkbookReadonly = true;	//设置表页只读
}

/**
 * 动态填充的回调函数
 */
function cellCallback(data)
{
	refreshReport(reportConfig.CELL_FILE,reportConfig.CUR_SHEET);
	
	var rowCount = data.length;	
	//一次插入所有空行
	SingleeReport.InsertRow(reportConfig.ROW_START+1,rowCount,reportConfig.CUR_SHEET);	
	SingleeReport.DeleteRow(reportConfig.ROW_START,1,reportConfig.CUR_SHEET);	//删除定义行
	for(i=0;i<rowCount;i++)
	{
		for(j=0;j<reportConfig.columnMap.length;j++)
		{		
			
			//设置显示样式---多行显示，文本自动换行
			//SingleeReport.SetCellTextStyle(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,2);			
			//复制单元格格式
			SingleeReport.SetCellNumType(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,reportConfig.columnNumType[j]);	
			//复制单元格字体
			//SingleeReport.SetCellFont(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,reportConfig.columnFont[j]);
			//复制行高					
			SingleeReport.SetRowHeight(0,reportConfig.ROW_HEIGHT,reportConfig.ROW_START+i,reportConfig.CUR_SHEET);		

			if(reportConfig.columnNumType[j]==1)	
				SingleeReport.D(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,parseFloat(data[i][reportConfig.columnMap[j]]));	
			else	
				SingleeReport.S(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,data[i][reportConfig.columnMap[j]]);
		}
	}
	//合并单元格
	for(i=0;i<reportConfig.columnEx.length;i++)
	{			
		if(reportConfig.columnEx[i]==null||reportConfig.columnEx[i]=="")
		{
			continue;		
		}	
		else if(reportConfig.columnEx[i]=="合并")	//单元格需要合并
		{
			var mergeRowStart = reportConfig.ROW_START;
			var mergeRowEnd = reportConfig.ROW_START;
			var mergeValue = SingleeReport.GetCellString(i+1,reportConfig.ROW_START,reportConfig.CUR_SHEET);//需要合并的值		
			var thisRowValue;	//当前行的值
			for(j=1;j<rowCount+1;j++)
			{
				thisRowValue = SingleeReport.GetCellString(i+1,reportConfig.ROW_START+j,reportConfig.CUR_SHEET);
				if(thisRowValue!=mergeValue)	//上一行需要合并
				{			
					
					if(mergeRowEnd>mergeRowStart)	//确实是多行的话
					{
						SingleeReport.MergeCells(i+1,mergeRowStart,i+1,mergeRowEnd);
						
						mergeRowStart = reportConfig.ROW_START+j;
						mergeRowEnd = mergeRowStart;	
						mergeValue = SingleeReport.GetCellString(i+1,mergeRowStart,reportConfig.CUR_SHEET);	//新的需要合并的值					
					}	
				}
				else
				{
					mergeRowEnd++;
				}
			}
			
		}
	}
	
	//setCellBestWidth();	//设置最佳宽度（该功能已取消，由模版中定义宽度，不够换行）
	showWaiting(0); //关闭进度条
	document.getElementById("cellReport").style.display="";
}

function rp_checkOpen(fileName)
{
	if(fileName=="")
	{
		sl_alert("未打开任何报表文件！");
		return false;
	}
}

/**
 * 注册
 */
function login(obj)
{
	if(obj.Login( "新力新报表管理系统","" ,"13040468", "3600-1505-7761-0005" ) == 0 )	
	{
		sl_alert( "注册CELL插件失败!");
		return false;
	}
}

//打印预览
function printPreview()
{
	login(SingleeReport);	
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,40,120,80);
	SingleeReport.PrintPreview( 0, reportConfig.CUR_SHEET);	//打印预览
}

//打印
function printReport()
{	
	login(SingleeReport);		
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,40,120,80);
	SingleeReport.PrintSheet (0, reportConfig.CUR_SHEET );	//打印
}

//处理单元格宽度--最大宽度
function setCellBestWidth()
{
	var i,width;
	var cols = SingleeReport.GetCols(reportConfig.CUR_SHEET);
	for(i=1;i<=cols;i++)
	{
		width = SingleeReport.GetColBestWidth(i);	
		SingleeReport.SetColWidth(1,width,i,reportConfig.CUR_SHEET);
	}
}

/**
  *  导出报表
  */
function exportReport()
{
	var name = SingleeReport.GetCellString(1,1,reportConfig.CUR_SHEET);	
	showModalDialog('export.jsp?name='+ name+'&sheet='+reportConfig.CUR_SHEET,SingleeReport,'dialogTop:120px;dialogLeft:500px;dialogWidth:360px;dialogHeight:250px;status:0;help:0');					
}

//导出Excel
function ExportExcel(obj)
{	
	login(obj);
	if(obj.ExportExcelDlg()==1)
		sl_alert("导出Excel文件成功！");
}

//导出Cell
function ExportCell(obj,sheet)
{		
	login(obj);	
	if(obj.SaveSheet(sheet)==1)
	{		
		obj.CloseFile();
		sl_alert("导出Cell文件成功！");
	}
}

//导出TXT
function ExportTXT(obj)
{	
	if(obj.ExportTextDlg()==1)
		sl_alert("导出文件成功！");		
}

//导出HTML
function ExportHTML(obj,fileName)
{
	sl_alert("此功能目前暂不支持！");
	//if(obj.ExportHtmlFile(fileName)==1)
	//	dl_alert(fileName);
	//else
	//	alert("导出失败！");
}

//导出PDF
function ExportPDF(obj,fileName,sheet)
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);
	if(obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet))==1)
		alert("导出文件成功！\n\n文件名："+fileName);
	else
		sl_alert("导出失败！");		
}	