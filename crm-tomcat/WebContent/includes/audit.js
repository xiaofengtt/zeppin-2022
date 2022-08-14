//Title  : Useful Javascript functions
//Desp   :
//Author : 应旭晶
//Date   : 2006-1-20

//初始化表头
function loadONE(obj,name1,col,name2,name3)
{
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellFontSize (1,1,0,30);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,75,1,0);
	obj.S (1,1,0,name1);

	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellAlign (1,2,0,4);
	obj.Mergecells (1,2,col,2);
	obj.SetRowHeight(1,25,2,0);
	obj.S (1,2,0,name2);

	obj.SetCellFont( 1,3,0,obj.FindFontIndex ("宋书",1));

	obj.Mergecells (1,3,(col-2),3);
	obj.SetRowHeight(1,25,3,0);
	obj.S (1,3,0,name3);
	obj.SetCellAlign ((col-1),3,0,2);
	obj.S ((col-1),3,0,'元');
	
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,4,0,4);
		obj.SetCellBorder(i, 4, 0, 0, 2);
		obj.SetCellBorder(i, 4, 0, 1, 2);
		obj.SetCellBorder(i, 4, 0, 2, 2);
		obj.SetCellBorder(i, 4, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,5,0,4);
		obj.SetCellBorder(i, 5, 0, 0, 2);
		obj.SetCellBorder(i, 5, 0, 1, 2);
		obj.SetCellBorder(i, 5, 0, 2, 2);
		obj.SetCellBorder(i, 5, 0, 3, 2);
		
	}
}
//初始化表头
function loadTWO(obj,name1,col)
{
	obj.ShowHScroll(0,0);
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellFontSize (1,1,0,30);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,75,1,0);
	obj.S (1,1,0,name1);

	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,2,0,4);
		obj.SetCellBorder(i, 2, 0, 0, 2);
		obj.SetCellBorder(i, 2, 0, 1, 2);
		obj.SetCellBorder(i, 2, 0, 2, 2);
		obj.SetCellBorder(i, 2, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{	
		if(i==1)
		obj.SetCellAlign (i,3,0,4);
		obj.SetCellBorder(i, 3, 0, 0, 2);
		obj.SetCellBorder(i, 3, 0, 1, 2);
		obj.SetCellBorder(i, 3, 0, 2, 2);
		obj.SetCellBorder(i, 3, 0, 3, 2);
		
	}
}

function loadThree2(obj,name1,col,name2,name3)
{
	//obj.ShowSideLabel(0,0);
	//obj.ShowTopLabel(0,0);
	//obj.ShowHScroll(0,1);
	if(document.theform.DCellWeb1=='[object]')
	{if(screen.height==600)
		document.theform.DCellWeb1.height ="330";
	}
	
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;
    
	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("楷体_GB2312",1));
	obj.SetCellFontSize (1,1,0,20);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,75,1,0);
	obj.S (1,1,0,name1);

	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("楷体_GB2312",1));
	obj.SetCellAlign (1,2,0,4);
	obj.Mergecells (1,2,col,2);
	obj.SetRowHeight(1,25,2,0);
	obj.S (1,2,0,name2);

	obj.SetCellFont( 1,3,0,obj.FindFontIndex ("楷体_GB2312",1));

	obj.Mergecells (1,3,(col-2),3);
	obj.SetRowHeight(1,25,3,0);
	obj.S (1,3,0,name3);
	obj.SetCellAlign ((col-1),3,0,2);
	obj.S ((col-1),3,0,'');
	
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,4,0,4);
		obj.SetCellBorder(i, 4, 0, 0, 2);
		obj.SetCellBorder(i, 4, 0, 1, 2);
		obj.SetCellBorder(i, 4, 0, 2, 2);
		obj.SetCellBorder(i, 4, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,5,0,4);
		obj.SetCellBorder(i, 5, 0, 0, 2);
		obj.SetCellBorder(i, 5, 0, 1, 2);
		obj.SetCellBorder(i, 5, 0, 2, 2);
		obj.SetCellBorder(i, 5, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,6,0,4);
		obj.SetCellBorder(i, 6, 0, 0, 2);
		obj.SetCellBorder(i, 6, 0, 1, 2);
		obj.SetCellBorder(i, 6, 0, 2, 2);
		obj.SetCellBorder(i, 6, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,7,0,4);
		obj.SetCellBorder(i, 7, 0, 0, 2);
		obj.SetCellBorder(i, 7, 0, 1, 2);
		obj.SetCellBorder(i, 7, 0, 2, 2);
		obj.SetCellBorder(i, 7, 0, 3, 2);
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,8,0,4);
		obj.SetCellBorder(i, 8, 0, 0, 2);
		obj.SetCellBorder(i, 8, 0, 1, 2);
		obj.SetCellBorder(i, 8, 0, 2, 2);
		obj.SetCellBorder(i, 8, 0, 3, 2);
	}
	obj.setRowHeight(1,obj.GetRowBestHeight(1),1,0);
	obj.setRowHeight(1,obj.GetRowBestHeight(2),2,0);
	if(obj.GetCellString(1,2,0)=="")
		obj.setRowHeight(1,0,2,0);
	obj.setRowHeight(1,obj.GetRowBestHeight(3),3,0);
}

function loadThree(obj,name1,col,name2,name3)
{
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellFontSize (1,1,0,20);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,55,1,0);
	obj.S (1,1,0,name1);
	
	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("宋书",1));
	obj.Mergecells (1,2,col-1,3);
	obj.SetCellFontSize (1,2,0,15);
	obj.SetRowHeight(1,35,3,0);
	obj.S (1,2,0,name3);
	obj.SetCellAlign ((col-1),3,0,2);
	obj.S ((col-1),3,0,'');
		
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,4,0,4);
		obj.SetCellBorder(i, 4, 0, 0, 2);
		obj.SetCellBorder(i, 4, 0, 1, 2);
		obj.SetCellBorder(i, 4, 0, 2, 2);
		obj.SetCellBorder(i, 4, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,5,0,4);
		obj.SetCellBorder(i, 5, 0, 0, 2);
		obj.SetCellBorder(i, 5, 0, 1, 2);
		obj.SetCellBorder(i, 5, 0, 2, 2);
		obj.SetCellBorder(i, 5, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,6,0,4);
		obj.SetCellBorder(i, 6, 0, 0, 2);
		obj.SetCellBorder(i, 6, 0, 1, 2);
		obj.SetCellBorder(i, 6, 0, 2, 2);
		obj.SetCellBorder(i, 6, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,7,0,4);
		obj.SetCellBorder(i, 7, 0, 0, 2);
		obj.SetCellBorder(i, 7, 0, 1, 2);
		obj.SetCellBorder(i, 7, 0, 2, 2);
		obj.SetCellBorder(i, 7, 0, 3, 2);
	}
}

function loadFour(obj,col,title,name2,name3,name4,name5)
{
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellFontSize (1,1,0,20);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,55,1,0);
	obj.S (1,1,0,title);//表名
	
	
	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellFontSize (1,2,0,10);
	obj.SetCellAlign (1,2,0,4);
	obj.Mergecells (1,2,col-3,1);
	obj.S (1,2,0,name2);//产品名
	obj.Mergecells (col-2,2,col,1);

	obj.SetCellFont( 2,2,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellFontSize (2,2,0,10);
	obj.SetCellAlign (2,2,0,4);
	obj.S (2,2,0,name3);//期间
	
	obj.SetCellFont( 1,3,0,obj.FindFontIndex ("宋书",1));//科目
	obj.Mergecells (1,3,col-3,3);
	obj.SetCellFontSize (1,3,0,15);
	obj.SetRowHeight(1,35,3,0);
	obj.S (1,3,0,name4);
	obj.Mergecells (col-2,3,col,1);

	obj.SetCellFont(2,3,0,obj.FindFontIndex ("宋书",1));
	obj.SetCellFontSize (2,3,0,10);
	obj.SetCellAlign (2,3,0,4);
	obj.S (2,3,0,name5);//页数
		
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,4,0,4);
		obj.SetCellBorder(i, 4, 0, 0, 2);
		obj.SetCellBorder(i, 4, 0, 1, 2);
		obj.SetCellBorder(i, 4, 0, 2, 2);
		obj.SetCellBorder(i, 4, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,5,0,4);
		obj.SetCellBorder(i, 5, 0, 0, 2);
		obj.SetCellBorder(i, 5, 0, 1, 2);
		obj.SetCellBorder(i, 5, 0, 2, 2);
		obj.SetCellBorder(i, 5, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,6,0,4);
		obj.SetCellBorder(i, 6, 0, 0, 2);
		obj.SetCellBorder(i, 6, 0, 1, 2);
		obj.SetCellBorder(i, 6, 0, 2, 2);
		obj.SetCellBorder(i, 6, 0, 3, 2);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellAlign (i,7,0,4);
		obj.SetCellBorder(i, 7, 0, 0, 2);
		obj.SetCellBorder(i, 7, 0, 1, 2);
		obj.SetCellBorder(i, 7, 0, 2, 2);
		obj.SetCellBorder(i, 7, 0, 3, 2);
	}
}


//根据表头不同排序分两种

function DCellWeb1_SortCol(obj,i)
{
	obj.SelectRange(obj.GetCurrentCol(), obj.getCurrentRow()+1, obj.GetCurrentCol(), obj.GetRows(obj.GetCurSheet()));
	obj.SortCol(i,obj.GetCurrentCol(),1, obj.getCurrentRow()+1, obj.GetCols(obj.GetCurSheet()), obj.GetRows(obj.GetCurSheet())-1);

}
function DCellWeb1_SortCol1(obj,i)
{
	obj.SelectRange(obj.GetCurrentCol(), obj.getCurrentRow()+2, obj.GetCurrentCol(), obj.GetRows(obj.GetCurSheet()));
	obj.SortCol(i,obj.GetCurrentCol(),1, obj.getCurrentRow()+2, obj.GetCols(obj.GetCurSheet()), obj.GetRows(obj.GetCurSheet())-1);

}
function login(obj)
{
	if(obj.Login( "新力新报表管理系统","" ,"13040468", "3600-1505-7761-0005" ) == 0 )	
	{
		rp_alert( "注册CELL插件失败!");
		return false;
	}
}

function setPrint(obj,flag)
{	

	//设置打印进纸方向,十分之一毫米	
	var cols = obj.GetCols(0);	
	var total_width = 0;
	for(var i=1;i<=cols;i++)
	{
	     total_width = obj.GetColWidth(0,i,0) + parseInt(total_width);
	}
	obj.PrintSetOrient(0);  
	if(total_width>obj.PrintGetPaperWidth(0))
		obj.PrintSetOrient(1);
	else
		obj.PrintSetOrient(0);
	obj.PrintSetAlign(1,0);   //设置打印格式为水平居中
	if(flag==1)		
		PrintPreview(obj);
	else
		PrintReport(obj);	
}

//打印预览
function PrintPreview(obj)
{
	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintPreview( 0, 0);	//打印预览
}

//打印
function PrintReport(obj)
{	
	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintSheet (0, 0);	//打印
}

function rp_alert(errinfo)
{
	alert("系统提示：　　　　\n\n" + errinfo + "\n\n");
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
//导出PDF
function ExportPDF(obj,fileName,sheet)
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);
	if(obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet))==1)
		dl_alert(fileName);
	else
		rp_alert("导出失败！");		
}
function dl_alert(fileName)
{
	alert("导出文件成功！\n\n文件名："+fileName);

}

//报表对象,行数，要合并的列，数据开始行，对齐格式pama暂时没用
function ColMerge(obj,rowcount,col,rowstart,pama){
	var end=0;
	for(var i=0;i<rowcount;i++){
		if(i==0) continue;
		rowstart++; 
		end++; 
		if(i==rowcount-1){
			if(obj.GetCellString(col,rowstart-1,0)==obj.GetCellString(col,rowstart,0)){
				obj.MergeCells(col,rowstart-end,col,rowstart); 
				obj.SetCellAlign(col,rowstart-end,0,8+4);
			}	
		}
		if(obj.GetCellString(col,rowstart-1,0)!=obj.GetCellString(col,rowstart,0)){
			obj.MergeCells(col,rowstart-end,col,rowstart-1); 
			obj.SetCellAlign(col,rowstart-end,0,8+4);
			end=0;
		}
	}
}

/**
 * 资产负债表展开表头
 */
function loadThreeByZcfzbInfo(obj,col)
{
	if(document.theform.DCellWeb1=='[object]')
	{if(screen.height==600)
		document.theform.DCellWeb1.height ="330";
	}
	//obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,1,0,9);
		obj.SetCellAlign (i,1,0,4);
		obj.SetCellBorder(i, 1, 0, 0, 3);
		obj.SetCellBorder(i, 1, 0, 1, 3);
		obj.SetCellBorder(i, 1, 0, 2, 3);
		obj.SetCellBorder(i, 1, 0, 3, 3);
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,2,0,9);
		obj.SetCellAlign (i, 2,0,4);
		obj.SetCellBorder(i, 2, 0, 0, 3);
		obj.SetCellBorder(i, 2, 0, 1, 3);
		obj.SetCellBorder(i, 2, 0, 2, 3);
		obj.SetCellBorder(i, 2, 0, 3, 3);
	}
}

/**
   ADD BY TSG 2008-01-16
   
 * 资产负债表汇总信息（按月）表头设置
 */
function loadHeadOfBalanceByMonth(obj,tableName,col,hzDate,companyName) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,0);
	obj.SetCols(col,0);//设置列数 
	obj.WorkbookReadonly = true;
	obj.SetCellFont(1,1,0,obj.FindFontIndex ("楷体_GB2312",1));
	obj.SetCellFontSize(1,1,0,16);
	obj.SetCellFontStyle(1,1,0,2);
	obj.SetCellAlign (1,1,0,32+4);
	obj.Mergecells(1,1,col-1,1); 
	obj.SetRowHeight(1,60,1,0);
	obj.S (1,1,0,tableName);
	
	obj.SetCellFont(1,2,0,obj.FindFontIndex ("楷体_GB2312",1)); 
	obj.SetCellFontSize(1,2,0,12);
    obj.SetCellFontStyle(1,2,0,2); 
	obj.SetCellAlign(1,2,0,16+1);
	obj.SetRowHeight(1,30,2,0);
	obj.Mergecells (1,2,4,2);
	obj.S (1,2,0,"第一部分：信托项目资产负债汇总表");     
	
	obj.SetCellFontSize(1,3,0,10); 
	obj.SetRowHeight(1,30,3,0);
	obj.SetCellAlign(1,3,0,16+1);
	obj.Mergecells(1,3,2,3);
	obj.S(1,3,0,companyName);     //编制单位

    obj.SetCellFontSize(3,3,0,10); 
	obj.SetCellAlign(3,3,0,16+1);
    obj.Mergecells (3,3,4,3);
	obj.S (3,3,0,hzDate);     //报表日期
	
	
	obj.SetCellFontSize(5,3,0,10); 
	obj.SetCellAlign (5,3,0,16+1);
	obj.S (5,3,0,"货币单位：万元"); 
				
}


/**
   ADD BY TSG 2008-01-21
   
 * 资产利润/附注信息（按月）表头设置 
 */
function loadHeadOfProfitByMonth(obj,tableName,col,hzDate,companyName) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,1);
	obj.SetCols(col,1);//设置列数 
	obj.WorkbookReadonly = true;
	obj.SetCellFont(1,1,1,obj.FindFontIndex ("楷体_GB2312",1));
	obj.SetCellFontSize(1,1,1,16);
	obj.SetCellFontStyle(1,1,1,2);
	obj.SetCellAlign (1,1,1,32+4);
	obj.Mergecells(1,1,col-1,1); 
	obj.SetRowHeight(1,60,1,1);
	obj.S (1,1,1,tableName);
	
	obj.SetCellFont(1,2,1,obj.FindFontIndex ("楷体_GB2312",1)); 
	obj.SetCellFontSize(1,2,1,12);
    obj.SetCellFontStyle(1,2,1,2); 
	obj.SetCellAlign(1,2,1,16+1);
	obj.SetRowHeight(1,30,2,1);
	obj.Mergecells (1,2,4,2);
	obj.S (1,2,1,"第二部分：信托项目利润汇总表");     
	
	obj.SetCellFontSize(1,3,1,10); 
	obj.SetRowHeight(1,30,3,1);
	obj.SetCellAlign(1,3,1,16+1);
	obj.Mergecells(1,3,2,3);
	obj.S(1,3,1,companyName);     //编制单位

    obj.SetCellFontSize(3,3,1,10); 
	obj.SetCellAlign(3,3,1,16+1);
    obj.Mergecells (3,3,4,3);
	obj.S (3,3,1,hzDate);     //报表日期
	
	
	obj.SetCellFontSize(5,3,1,10); 
	obj.SetCellAlign (5,3,1,16+1);
	obj.S (5,3,1,"货币单位：万元");				
}
/**
   ADD BY TSG 2008-01-16
   
 * 资产负债表汇总信息（按月）表尾设置
 */
 function loadEndOfBalanceByMonth(obj,cols,rows,op_name,check_man,zrr){
    obj.InsertRow(rows,1,0);
 	for(i=1;i<=(cols-1);i++)
	{
		obj.SetCellBorder(i, rows, 0, 0, 1);
		obj.SetCellBorder(i, rows, 0, 1, 1);
		obj.SetCellBorder(i, rows, 0, 2, 1);
		obj.SetCellBorder(i, rows, 0, 3, 1);
	}
	obj.SetCellFontSize (1,rows,0,10);
	obj.SetRowHeight(1,30,rows,0);
	obj.Mergecells (2,rows,3,rows);
	obj.SetCellAlign (2,rows,0,1);
	obj.S (2,rows,0,"填表人：" + op_name); 

	obj.SetCellAlign (4,rows,0,1);
	obj.SetCellFontStyle(4,rows,0,0);
	obj.S (4,rows,0,"复核人：" + check_man);

	obj.SetCellAlign (5,rows,0,1);
	obj.SetCellFontStyle(5,rows,0,0);
	obj.S (5,rows,0,"负责人：" + zrr); 
		for(i=6;i<=obj.GetRows(0);i++){
	  obj.SetRowHeight(1,25,i,0);
	}
	
	for(i=1;i<=5;i++)
	{		
		obj.SetCellBorder(i, 51,0, 3, 3);	
	}
	obj.SetCellTextColor(5,52,0,-1);
}
/**
 * 资产负债表表头设置
 */
function loadThreeByZcfzb(obj,name1,col,name2,name3,name4,name5)
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;
	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("楷体_GB2312",1));
	obj.SetCellFontSize (1,1,0,16);
	obj.SetCellFontStyle(1,1,0,2);
	obj.SetCellAlign (1,1,0,16+4);
	obj.Mergecells (1,1,col-1,1);
	obj.SetRowHeight(1,125,1,0);
	obj.S (1,1,0,name1);
	obj.SetCellFontSize (1,2,0,9);
	obj.SetCellFontSize (4,2,0,9);
	obj.SetCellFontSize (6,2,0,9);
	obj.SetRowHeight(1,58,2,0);
	obj.Mergecells (1,2,3,2);
	obj.S (1,2,0,name3);     //编制单位
	obj.Mergecells (4,2,5,2);
	obj.S (4,2,0,name2);     //日期
	obj.SetCellAlign (6,2,0,2);
	obj.S (6,2,0,"会计项目01表");
	obj.SetCellFontSize (1,3,0,9);
	obj.SetCellFontSize (4,3,0,9);
	obj.SetCellFontSize (6,3,0,9);
	obj.SetRowHeight(1,38,3,0);
	obj.Mergecells (1,3,3,3);
	obj.SetCellAlign (1,3,0,32)
	obj.S (1,3,0,name4);     //信托项目名称
	obj.Mergecells (4,3,5,3);
	obj.SetCellAlign (4,3,0,32)
	obj.S (4,3,0,name5);     //信托期限
	obj.SetCellAlign (6,3,0,32+2);
	obj.S (6,3,0,"单位：元");
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,4,0,9);
		obj.SetCellAlign (i,4,0,4);
		obj.SetCellBorder(i, 4, 0, 0, 3);
		obj.SetCellBorder(i, 4, 0, 1, 3);
		obj.SetCellBorder(i, 4, 0, 2, 3);
		obj.SetCellBorder(i, 4, 0, 3, 3); 
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,5,0,9);
		obj.SetCellAlign (i,5,0,4);
		obj.SetCellBorder(i, 5, 0, 0, 3);
		obj.SetCellBorder(i, 5, 0, 1, 3);
		obj.SetCellBorder(i, 5, 0, 2, 3);
		obj.SetCellBorder(i, 5, 0, 3, 3);
	}
		
}

/**
 * 利润表表头设置
 */
function loadThreeBylrb(obj,name1,col,name2,name3,name4,name5)
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//设置列数
	obj.WorkbookReadonly = true;
	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("楷体_GB2312",1));
	obj.SetCellFontSize (1,1,0,16);
	obj.SetCellFontStyle(1,1,0,2);
	obj.SetCellAlign (1,1,0,16+4);
	obj.Mergecells (1,1,col-1,1);
	obj.SetRowHeight(1,125,1,0);
	obj.S (1,1,0,name1);
	obj.SetCellFontSize (1,2,0,9);
	obj.SetCellFontSize (2,2,0,9);
	obj.SetCellFontSize (3,2,0,9);
	obj.SetRowHeight(1,58,2,0);
	obj.S (1,2,0,name3);
	obj.SetCellAlign (2,2,0,4);
	obj.S (2,2,0,name2);
	obj.SetCellAlign (3,2,0,2);
	obj.S (3,2,0,"会计项目02表"); 
	obj.SetCellFontSize (1,3,0,9);
	obj.SetCellFontSize (2,3,0,9);
	obj.SetCellFontSize (3,3,0,9);
	obj.SetRowHeight(1,38,3,0);
	obj.S (1,3,0,name4);
	obj.SetCellAlign (1,3,0,32);
	obj.SetCellAlign (2,3,0,32+2);
	obj.S (2,3,0,name5);
	obj.SetCellAlign (3,3,0,32+2);
	obj.S (3,3,0,"单位：元"); 
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,4,0,9);
		obj.SetCellAlign (i,4,0,4);
		obj.SetCellBorder(i, 4, 0, 0, 3);
		obj.SetCellBorder(i, 4, 0, 1, 3);
		obj.SetCellBorder(i, 4, 0, 2, 3);
		obj.SetCellBorder(i, 4, 0, 3, 3);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,5,0,9);
		obj.SetCellAlign (i,5,0,4);
		obj.SetCellBorder(i, 5, 0, 0, 3);
		obj.SetCellBorder(i, 5, 0, 1, 3);
		obj.SetCellBorder(i, 5, 0, 2, 3);
		obj.SetCellBorder(i, 5, 0, 3, 3);
	}
		
}

/**
   add by tsg 2007-12-12
   多SHEET 报表
 * 利润表表头设置
 */
function loadThreeBylrbNew(obj,name1,col,name2,name3,name4,name5)
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,1);
	obj.SetCols(col,1);//设置列数
	obj.WorkbookReadonly = true;
	obj.SetCellFont( 1,1,1,obj.FindFontIndex ("楷体_GB2312",1));
	obj.SetCellFontSize (1,1,1,16);
	obj.SetCellFontStyle(1,1,1,2);
	obj.SetCellAlign (1,1,1,16+4);
	obj.Mergecells (1,1,col-1,1);
	obj.SetRowHeight(1,125,1,1);
	obj.S (1,1,1,name1);
	obj.SetCellFontSize (1,2,1,9);
	obj.SetCellFontSize (2,2,1,9);
	obj.SetCellFontSize (3,2,1,9);
	obj.SetRowHeight(1,58,2,1);
	obj.S (1,2,1,name3);
	obj.SetCellAlign (2,2,1,4);
	obj.S (2,2,1,name2);
	obj.SetCellAlign (3,2,1,2);
	obj.S (3,2,1,"会计项目02表"); 
	obj.SetCellFontSize (1,3,1,9);
	obj.SetCellFontSize (2,3,1,9);
	obj.SetCellFontSize (3,3,1,9);
	obj.SetRowHeight(1,38,3,1);
	obj.S (1,3,1,name4);
	obj.SetCellAlign (1,3,1,32);
	obj.SetCellAlign (2,3,1,32+2);
	obj.S (2,3,1,name5);
	obj.SetCellAlign (3,3,1,32+2);
	obj.S (3,3,1,"单位：元"); 
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,4,1,9);
		obj.SetCellAlign (i,4,1,4);
		obj.SetCellBorder(i, 4, 1, 0, 3);
		obj.SetCellBorder(i, 4, 1, 1, 3);
		obj.SetCellBorder(i, 4, 1, 2, 3);
		obj.SetCellBorder(i, 4, 1, 3, 3);
		
	}
	for(i=1;i<=(col-1);i++)
	{
		obj.SetCellFontSize (i,5,1,9);
		obj.SetCellAlign (i,5,1,4);
		obj.SetCellBorder(i, 5, 1, 0, 3);
		obj.SetCellBorder(i, 5, 1, 1, 3);
		obj.SetCellBorder(i, 5, 1, 2, 3);
		obj.SetCellBorder(i, 5, 1, 3, 3); 
	}
		
}

/**
 * 报表下方显示内容:   用于6列
 * name1是会计主管 name2是复核 name3是制表
 */
 function loadEndBy6(obj,cols,rows,name1,name2,name3){
 	for(i=1;i<=(cols-1);i++)
	{
		obj.SetCellBorder(i, rows, 0, 0, 1);
		obj.SetCellBorder(i, rows, 0, 1, 1);
		obj.SetCellBorder(i, rows, 0, 2, 1);
		obj.SetCellBorder(i, rows, 0, 3, 1);
	}
	obj.SetCellFontSize (1,rows,0,9);
	obj.SetCellFontSize (3,rows,0,9);
	obj.SetCellFontSize (5,rows,0,9);
	obj.SetRowHeight(1,38,rows,0);
	obj.Mergecells (1,rows,2,rows);
	obj.SetCellAlign (1,rows,0,32);
	obj.S (1,rows,0,"会计主管：" + name1);
	obj.Mergecells (3,rows,4,rows);
	obj.SetCellAlign (3,rows,0,32+4);
	obj.S (3,rows,0,"复核：" + name2);
	obj.Mergecells (5,rows,6,rows)
	obj.SetCellAlign (5,rows,0,32+2);
	obj.S (5,rows,0,"制表：" + name3); 
}

/**
  add by tsg 
 * 报表下方显示内容:   用于6列
 * name1是会计主管 name2是复核 name3是制表
 */
 function loadEndBy6New(obj,cols,rows,name1,name2,name3){

 	for(i=1;i<=(cols-1);i++)
	{
		obj.SetCellBorder(i, rows, 0, 0, 1);
		obj.SetCellBorder(i, rows, 0, 1, 1);
		obj.SetCellBorder(i, rows, 0, 2, 1);
		obj.SetCellBorder(i, rows, 0, 3, 1);
	}
	obj.SetCellFontSize (1,rows,0,9);
	obj.SetCellFontSize (3,rows,0,9);
	obj.SetCellFontSize (5,rows,0,9);
	obj.SetRowHeight(1,38,rows,0);
	obj.Mergecells (1,rows,2,rows);
	obj.SetCellAlign (1,rows,0,32);
	obj.S (1,rows,0,"会计主管：" + name1);
	obj.Mergecells (3,rows,4,rows);
	obj.SetCellAlign (3,rows,0,32+4);
	obj.S (3,rows,0,"复核：" + name2);
	obj.Mergecells (5,rows,6,rows)
	obj.SetCellAlign (5,rows,0,32+2);
	obj.S (5,rows,0,"制表：" + name3); 
}



/**
 * 报表下方显示内容:   用于3列
 * name1是会计主管 name2是复核 name3是制表
 */
 function loadEndBy3(obj,cols,rows,name1,name2,name3){
 	for(i=1;i<=(cols-1);i++)
	{
		obj.SetCellBorder(i, rows, 0, 0, 1);
		obj.SetCellBorder(i, rows, 0, 1, 1);
		obj.SetCellBorder(i, rows, 0, 2, 1);
		obj.SetCellBorder(i, rows, 0, 3, 1);
	}
	obj.SetCellFontSize (1,rows,0,9);
	obj.SetCellFontSize (2,rows,0,9);
	obj.SetCellFontSize (3,rows,0,9);
	obj.SetRowHeight(1,38,rows,0);
	obj.SetCellAlign (1,rows,0,32);
	obj.S (1,rows,0,"会计主管：" + name1);
	obj.SetCellAlign (2,rows,0,32+4);
	obj.S (2,rows,0,"复核：" + name2);
	obj.SetCellAlign (3,rows,0,32+2);
	obj.S (3,rows,0,"制表：" + name3); 
}
/**

add  by tsg 2007-12-12
多SHEET 报表
 * 报表下方显示内容:   用于3列
 * name1是会计主管 name2是复核 name3是制表
 */
 function loadEndBy3New(obj,cols,rows,name1,name2,name3){
 	for(i=1;i<=(cols-1);i++)
	{
		obj.SetCellBorder(i, rows, 1, 0, 1);
		obj.SetCellBorder(i, rows, 1, 1, 1);
		obj.SetCellBorder(i, rows, 1, 2, 1);
		obj.SetCellBorder(i, rows, 1, 3, 1);
	}
	obj.SetCellFontSize (1,rows,1,9);
	obj.SetCellFontSize (2,rows,1,9);
	obj.SetCellFontSize (3,rows,1,9);
	obj.SetRowHeight(1,38,rows,1);
	obj.SetCellAlign (1,rows,1,32);
	obj.S (1,rows,1,"会计主管：" + name1);
	obj.SetCellAlign (2,rows,1,32+4);
	obj.S (2,rows,1,"复核：" + name2);
	obj.SetCellAlign (3,rows,1,32+2);
	obj.S (3,rows,1,"制表：" + name3); 
}

/**
 *	利润表
 */
function MouseDClick_Cell02(form,obj,item_id,cell_type){
	if(obj.getCurrentRow() <= 4 || obj.getCurrentRow() == (obj.getRows(0)-1))
        return false;
    if(obj.getCurrentCol() == 1)
        return false;
    if(obj.GetCellString(obj.getCurrentCol(),obj.getCurrentRow(),0) == "")
        return false;
    if(cell_type == 1){
		return false;
	}
	syncDatePicker(form.date_picker, form.date);
	var col = "";
	var item_name = "";
	if(obj.getCurrentCol()==2){ 
		col = "B";
		item_name = obj.GetCellString(obj.getCurrentCol()-1,obj.getCurrentRow(),0);
	}
	if(obj.getCurrentCol()==3){ 
		col = "C";
		item_name = obj.GetCellString(obj.getCurrentCol()-2,obj.getCurrentRow(),0);
	}
	var local = "report_lrb_info.jsp?date="+form.date.value+"&item_id="+item_id+"&cell_id="+form.cell_id.value+"&col="+col+"&cell_type="+cell_type+"&item_name="+item_name;
	openWin(local, "", 655, 500);
}

/**
 *	资产负债表
 */
function MouseDClick_Cell01(form,obj,item_id,cell_type){
	if(obj.getCurrentRow() <= 4 || obj.getCurrentRow() == (obj.getRows(0)-1))
        return false;
    if(obj.getCurrentCol() == 1 || obj.getCurrentCol() == 4)
        return false;
    if(obj.GetCellString(obj.getCurrentCol(),obj.getCurrentRow(),0) == "")
        return false;
    if(cell_type == 1){
		return false;
	}
	syncDatePicker(form.date_picker, form.date);
	var col = "";
	var item_name = "";
	if(obj.getCurrentCol()==2){ 
		col = "B";
		item_name = obj.GetCellString(obj.getCurrentCol()-1,obj.getCurrentRow(),0);
	}
	if(obj.getCurrentCol()==3){ 
		col = "C";
		item_name = obj.GetCellString(obj.getCurrentCol()-2,obj.getCurrentRow(),0);
	}
	if(obj.getCurrentCol()==5){ 
		col = "E";
		item_name = obj.GetCellString(obj.getCurrentCol()-1,obj.getCurrentRow(),0);
	}
	if(obj.getCurrentCol()==6){ 
		col = "F";
		item_name = obj.GetCellString(obj.getCurrentCol()-2,obj.getCurrentRow(),0);
	}
	
	var local = "report_zcfzb_info.jsp?date="+form.date.value+"&item_id="+item_id+"&cell_id="+form.cell_id.value+"&col="+col+"&cell_type="+cell_type+"&item_name="+item_name;
	openWin(local, "", 655, 500);
}

function MouseDClick_CellInfo01(form,obj){
	if(obj.getCurrentRow() == 1 || obj.getCurrentRow() == (obj.getRows(0)-1))
        return false;
    if(obj.GetCellString(4,obj.getCurrentRow(),0) == "")
    	return false;
    if(obj.GetCellString(8,obj.getCurrentRow(),0) == 1){
		return false;
	}
	location = "report_zcfzb_info.jsp?date="+form.date.value+"&item_id="+form.item_id.value+"&cell_id="+obj.GetCellString(6,obj.getCurrentRow(),0)+"&col="+form.col.value+"&cell_type="+obj.GetCellString(8,obj.getCurrentRow(),0)+"&item_name="+form.item_name.value;
}

function MouseDClick_CellInfo02(form,obj){
	if(obj.getCurrentRow() == 1 || obj.getCurrentRow() == (obj.getRows(0)-1))
        return false;
    if(obj.GetCellString(4,obj.getCurrentRow(),0) == "")
    	return false;
    if(obj.GetCellString(8,obj.getCurrentRow(),0) == 1){
		return false;
	}
	location = "report_lrb_info.jsp?date="+form.date.value+"&item_id="+form.item_id.value+"&cell_id="+obj.GetCellString(6,obj.getCurrentRow(),0)+"&col="+form.col.value+"&cell_type="+obj.GetCellString(8,obj.getCurrentRow(),0)+"&item_name="+form.item_name.value;
}


//add by tsg function 
function setNewPrint(obj,flag,sheetIndex)  
{	

   
	//设置打印进纸方向,十分之一毫米	
	var cols = obj.GetCols(sheetIndex);	 
	var total_width = 0;
	for(var i=1;i<=cols;i++)
	{
	     total_width = obj.GetColWidth(0,i,sheetIndex) + parseInt(total_width);
	}
	obj.PrintSetOrient(0);  
	if(total_width>obj.PrintGetPaperWidth(sheetIndex))
		obj.PrintSetOrient(1);
	else
		obj.PrintSetOrient(0);
	obj.PrintSetAlign(1,0);   //设置打印格式为水平居中 
	if(flag==1)		
		PrintPreview1(obj,sheetIndex);
	else
		PrintReport1(obj,sheetIndex);	
}

//打印预览 
function PrintPreview1(obj,sheetIndex) 
{

	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintPreview( 0, sheetIndex);	//打印预览
}

//打印
function PrintReport1(obj,sheetIndex)
{	
	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintSheet (0, sheetIndex);	//打印
}

//导出all sheet PDF
function ExportPDFOfAllSheets(obj,fileName,sheet) 
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);		
	if(obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet))==1)
		dl_alert(fileName);
	else
		rp_alert("导出失败！");		
}


/**
ADD BY TSG 2008-01-22
实现报表占据整个FRAME

*/
function Select()
{	
	if(parent.leftshow)
	{
		parent.centerFrame.cols="0,*";	
		parent.leftshow = false;			
	}
	else
	{
		parent.centerFrame.cols="200,*";
		parent.leftshow = true;		
	}	
}

/**
   ADD BY TSG 2008-01-23
   
 * S32信托公司管理信托财产状况统计表（S32）表头设置
 */
function loadHeadOfFundsManageByMonth(obj,statementsTitle,cols,hzDate,companyName,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//设置列数 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S32第一部分");
	}else if(sheetIndex==1){
	 obj.SetSheetLabel(sheetIndex,"S32第二部分"); 
	}else if(sheetIndex==2){
	 obj.SetSheetLabel(sheetIndex,"S32第三部分"); 
	}else if(sheetIndex==3){
	 obj.SetSheetLabel(sheetIndex,"S32补充材料一"); 
	}else if(sheetIndex==4){
	 obj.SetSheetLabel(sheetIndex,"S32补充材料二"); 
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	
	if(sheetIndex==4){
		    obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(1,1,sheetIndex,12); 
			obj.SetCellAlign(8,2,sheetIndex,32+1);
		    obj.Mergecells (1,1,2,1);
			obj.S (1,1,sheetIndex,"补充材料二：信托资金运用行业分类情况"); 
	  
	}else{
		//大抬头		
		obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("黑体",1));
		obj.SetCellFontSize(1,1,sheetIndex,20);
		obj.SetCellFontStyle(1,1,sheetIndex,2);
		obj.SetCellAlign (1,1,sheetIndex,32+4);
		if(sheetIndex==0 || sheetIndex==1){
		 obj.Mergecells(1,1,cols-5,1); 
		 obj.Mergecells(cols-4,1,cols,1); 
		}else if(sheetIndex==2 || sheetIndex==3 ){
		  obj.Mergecells(1,1,cols-1,1);
		}
	    obj.SetRowHeight(1,60,1,sheetIndex);		  
		obj.S (1,1,sheetIndex,statementsTitle);  
		
		//填报机构	
		obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		obj.SetCellFontSize(1,2,sheetIndex,12); 
		obj.SetRowHeight(1,30,2,sheetIndex);
		obj.SetCellAlign(1,2,sheetIndex,16+1);
		if(sheetIndex==0 || sheetIndex==1){
		  obj.Mergecells(1,2,7,2);
		 }else if(sheetIndex==2){
		   obj.Mergecells(1,2,7,2);
		   obj.Mergecells(9,2,12,2);
		 }else if(sheetIndex==3){
		   obj.Mergecells(1,2,2,2);
		 }  
		obj.S(1,2,sheetIndex,companyName);     
		
		if(sheetIndex==0){
		     //报表日期
		    obj.SetCellFont(8,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(8,2,sheetIndex,12); 
			obj.SetCellAlign(8,2,sheetIndex,16+4);
		    obj.Mergecells (8,2,22,2);
			obj.S (8,2,sheetIndex,hzDate);  
	
		    obj.SetCellFont(23,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(23,2,sheetIndex,12); 
			obj.SetCellAlign (23,2,sheetIndex,16+4);
			obj.Mergecells (23,2,44,2);
			obj.S (23,2,sheetIndex,"货币单位：万元"); 
			
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("宋体",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,14);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,13,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"第一部分：集合管理资金信托来源运用情况明细表");  
		}else if(sheetIndex==1){
		   obj.Mergecells (8,2,31,2);
		   
		  	obj.SetCellFont(45,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(45,2,sheetIndex,12); 
			obj.SetCellAlign (45,2,sheetIndex,16+1);
			obj.Mergecells (45,2,46,2);
			obj.S (45,2,sheetIndex,"货币单位:"); 
			
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("宋体",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,14);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,16,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"第二部分：单一管理资金信托来源运用情况明细表");  
		}else if(sheetIndex==2){		
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("宋体",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,14);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,22,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"第三部分：管理财产信托情况明细表  ");  
		}else if(sheetIndex==3){
		     //报表日期
		    obj.SetCellFont(3,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(3,2,sheetIndex,12); 
			obj.SetCellAlign(3,2,sheetIndex,16+4);
		    obj.Mergecells (3,2,6,2);
			obj.S (3,2,sheetIndex,hzDate);  
	
		    obj.SetCellFont(8,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(8,2,sheetIndex,12); 
			obj.SetCellAlign (8,2,sheetIndex,16+4);
			obj.Mergecells (8,2,10,2);
			obj.S (8,2,sheetIndex,"货币单位：万元"); 
			
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("宋体",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,12);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,2,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"补充材料一：信托资金投资有价证券风险情况表");  
			
		}
	}				
}

/**
   ADD BY TSG 2008-01-23
   
 * S32信托公司管理信托财产状况统计表（S32）表尾设置
 */
 function loadEndOfFundsManagexByMonth(obj,cols,rows,op_name,sheetIndex){
    
     
    if(sheetIndex==0){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,7,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"填表人：" + op_name); 
	
		obj.SetCellAlign (8,rows,sheetIndex,32+1);
		obj.Mergecells (8,rows,13,rows);
		obj.S (8,rows,0,"复核人：");
	
		obj.SetCellAlign (14,rows,sheetIndex,32+4);
		obj.Mergecells (14,rows,44,rows);
		obj.S (14,rows,sheetIndex,"负责人："); 
		
	    obj.SetCellAlign (45,rows,sheetIndex,32+1);
		obj.Mergecells (45,rows,48,rows);
		obj.S (45,rows,sheetIndex,"负责人："); 
    }else if(sheetIndex==1){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,5,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"填表人：" + op_name); 
	
		obj.SetCellAlign (7,rows,sheetIndex,32+1);
		obj.Mergecells (7,rows,33,rows);
		obj.S (7,rows,sheetIndex,"复核人：");
	
		obj.SetCellAlign (34,rows,sheetIndex,32+1);
		obj.Mergecells (34,rows,47,rows);
		obj.S (34,rows,sheetIndex,"负责人："); 
		
    }else if(sheetIndex==2){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,8,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"填表人：" + op_name); 
		
		obj.Mergecells (9,rows,12,rows);
    }else if(sheetIndex==3){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,2,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"填表人：" + op_name); 
	
		obj.SetCellAlign (3,rows,sheetIndex,32+1);
		obj.Mergecells (3,rows,4,rows);
		obj.S (3,rows,sheetIndex,"复核人：");
	
		obj.SetCellAlign (5,rows,sheetIndex,32+1);
		obj.Mergecells (5,rows,6,rows);
		obj.S (5,rows,sheetIndex,"负责人："); 
		
    }else if(sheetIndex==4){
        obj.InsertRow(obj.GetRows(sheetIndex),2,sheetIndex);
    	obj.SetCellFontSize (1,36,sheetIndex,10);
		obj.Mergecells (1,36,2,36);
		obj.SetCellAlign (1,36,sheetIndex,32+1);
		obj.S (1,36,sheetIndex,"填表人：" + op_name); 
	
		obj.SetCellAlign (3,36,sheetIndex,32+1);
		obj.S (3,36,sheetIndex,"复核人：");
    }

	
	for(i=1;i<=(cols-1);i++){
		obj.SetCellBorder(i, rows, sheetIndex, 0, 1);
		obj.SetCellBorder(i, rows, sheetIndex, 1, 1);
		obj.SetCellBorder(i, rows, sheetIndex, 2, 1);
		obj.SetCellBorder(i, rows, sheetIndex, 3, 1);
	}
}

/**
   ADD BY TSG 2008-01-23
   
 * 格式化S32信托公司管理信托财产状况统计表

 */
 
 function formatStatements(obj,sheetIndex){

	  if(sheetIndex==0){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列） 
		    for(i=3;i<=36;i++){
		        if(i==7)
		          obj.SetRowHeight(1,50,i,sheetIndex);
		        else if(i==3)
		      	  obj.SetRowHeight(1,38,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    for(i=3;i<=48;i++){
		      	obj.SetColWidth(1,60,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线
		    for(i=4;i<=35;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(48,i,sheetIndex,2,3);
		    }
		    for(i=1;i<=48;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,23,sheetIndex,3,3);
		       obj.SetCellBorder(i,35,sheetIndex,3,3);
		    }
		    
		    //设置字体自动换行
		    for(row=5;row<=7;row++){
		      for(col=1;col<=48;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		   //合计粗体居中显示
	       obj.SetCellFontStyle(2,23,sheetIndex,2);
	       obj.SetCellAlign(2,23,sheetIndex,32+4);
	  }else if(sheetIndex==1){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列）
		    //设置行高
		    for(i=3;i<=36;i++){
		        if(i==7)
		          obj.SetRowHeight(1,50,i,sheetIndex);
		        else if(i==3)
		      	  obj.SetRowHeight(1,38,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //设置列宽
		    for(i=3;i<=50;i++){
		      	obj.SetColWidth(1,70,i,sheetIndex);   
		    }
		    
		    //设置外边框为粗线
		    for(i=4;i<=35;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(50,i,sheetIndex,2,3);
		    }
		    for(i=1;i<=50;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,23,sheetIndex,3,3);
		       obj.SetCellBorder(i,35,sheetIndex,3,3);
		    }
		    
		    //设置字体自动换行
		    for(row=5;row<=7;row++){
		      for(col=1;col<=50;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    //合计粗体居中显示
	       obj.SetCellFontStyle(2,23,sheetIndex,2);
	       obj.SetCellAlign(2,23,sheetIndex,32+4);
	  }else if(sheetIndex==2){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列）
		    //设置行高
		    for(i=3;i<=36;i++){
	            if(i==3)
		      	  obj.SetRowHeight(1,38,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //设置列宽
		    for(i=3;i<=26;i++){
		      	obj.SetColWidth(1,60,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线--左右
		    for(i=4;i<=29;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(26,i,sheetIndex,2,3);
		    }
		    //设置外边框为粗线--上下
		    for(i=1;i<=26;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,29,sheetIndex,3,3);
		       obj.SetCellBorder(i,23,sheetIndex,3,3);
		    }
		    
		    //设置字体自动换行
		    for(row=5;row<=7;row++){
		      for(col=1;col<=50;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //合计粗体居中显示
	       obj.SetCellFontStyle(2,23,sheetIndex,2);
	       obj.SetCellAlign(2,23,sheetIndex,32+4);
	  }else if(sheetIndex==3){
	  
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列）
		    
		    //设置行高
		    for(i=3;i<=68;i++){
	            if(i==3)
		      	  obj.SetRowHeight(1,38,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //设置列宽
		    for(i=3;i<=10;i++){
		      	obj.SetColWidth(1,90,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线--左右
		    for(i=4;i<=24;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(10,i,sheetIndex,2,3);
		    }
		    //设置外边框为粗线--上下
		    for(i=1;i<=30;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,30,sheetIndex,3,3);
		    }
		    
		     //附注1设置外边框为粗线--左右
		    for(i=35;i<=49;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(4,i,sheetIndex,2,3);
		    }
		    //附注1设置外边框为粗线--上下
		    for(i=1;i<=4;i++){
		       obj.SetCellBorder(i,35,sheetIndex,1,3);
		       obj.SetCellBorder(i,49,sheetIndex,3,3);
		    }
		    
		     //附注2设置外边框为粗线--左右
		    for(i=52;i<=67;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(7,i,sheetIndex,2,3);
		    }
		    //附注1设置外边框为粗线--上下
		    for(i=1;i<=7;i++){
		       obj.SetCellBorder(i,52,sheetIndex,1,3);
		       obj.SetCellBorder(i,67,sheetIndex,3,3);
		    }
		    
		    //设置字体自动换行
		    for(row=5;row<=6;row++){
		      for(col=1;col<=10;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //附注1设置字体自动换行
		    for(row=35;row<=36;row++){
		      for(col=1;col<=4;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //附注2设置字体自动换行
		    for(row=53;row<=54;row++){
		      for(col=1;col<=7;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //设置背景色
		    for(row=8;row<=28;row++){
			     if(row!=14&&row!=15&&row!=19&&row!=20&&row!=24&&row!=25&&row!=27){
				      for(col=6;col<=10;col++){
				        document.theform.DCellWeb1.SetCellBackColor(col,row,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
				      }
			     } 
		    }
		    document.theform.DCellWeb1.SetCellBackColor(4,7,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,14,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,15,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,19,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,20,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,24,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,25,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,27,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,29,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(4,30,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 		    
		    document.theform.DCellWeb1.SetCellBackColor(5,14,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(5,19,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(5,24,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(5,27,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    document.theform.DCellWeb1.SetCellBackColor(5,29,sheetIndex,document.theform.DCellWeb1.FindColorIndex(0xc0c0c0,1)); 
		    
		     //设置不显示边框
		    for(i=31;i<=34;i++){
		       for(j=1;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	
		    
		    //设置不显示边框
		    for(i=50;i<=51;i++){
		       for(j=1;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }
		    
		    //设置不显示边框
		    for(i=31;i<=49;i++){
		       for(j=5;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	 
		    
		    //设置不显示边框
		    for(i=51;i<=67;i++){
		       for(j=8;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	
		    //设置字体颜色		    
		    obj.SetCellTextColor(3,35,3,obj.FindColorIndex(0x000000,1));   
		    obj.SetCellTextColor(3,35,3,obj.FindColorIndex(0x000000,1)); 	
		    obj.SetCellTextColor(3,52,3,obj.FindColorIndex(0x000000,1));   
		    obj.SetCellTextColor(3,53,3,obj.FindColorIndex(0x000000,1));
		    obj.SetCellTextColor(5,52,3,obj.FindColorIndex(0x000000,1));   
		    obj.SetCellTextColor(6,52,3,obj.FindColorIndex(0x000000,1)); 	
		    obj.SetCellTextColor(5,52,3,obj.FindColorIndex(0x000000,1));   
		    obj.SetCellTextColor(6,54,3,obj.FindColorIndex(0x000000,1));	
		    obj.SetCellTextColor(5,68,3,obj.FindColorIndex(0x000000,1));   
		    obj.SetCellTextColor(3,68,3,obj.FindColorIndex(0x000000,1));    
		    obj.SetCellTextColor(3,36,3,obj.FindColorIndex(0x000000,1));
		    obj.SetCellTextColor(5,54,3,obj.FindColorIndex(0x000000,1));
	  }else if(sheetIndex==4){
	  
	        obj.ShowHScroll(0,sheetIndex);
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列）
		    
		    //设置行高
		    for(i=1;i<=36;i++){
	            if(i==3)
		      	  obj.SetRowHeight(1,31,i,sheetIndex);
		      	else if(i==30)
		      	  obj.SetRowHeight(1,31,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //设置列宽
		    for(i=3;i<=10;i++){
		      	obj.SetColWidth(1,90,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线--左右
		    for(i=2;i<=26;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(5,i,sheetIndex,2,3);
		    }
		    //设置外边框为粗线--上下
		    for(i=1;i<=5;i++){
		       obj.SetCellBorder(i,2,sheetIndex,1,3);
		       obj.SetCellBorder(i,26,sheetIndex,3,3);
		    }
		    
		     //附注1设置外边框为粗线--左右
		    for(i=29;i<=34;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(8,i,sheetIndex,2,3);
		    }
		    //附注1设置外边框为粗线--上下
		    for(i=1;i<=8;i++){
		       obj.SetCellBorder(i,29,sheetIndex,1,3);
		       obj.SetCellBorder(i,34,sheetIndex,3,3);
		    }
		    
		  
		    
		    //设置字体自动换行
		    for(row=2;row<=3;row++){
		      for(col=1;col<=5;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //附注1设置字体自动换行
		    for(row=29;row<=30;row++){
		      for(col=1;col<=8;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    } 
		    
		     //设置不显示边框
		    for(i=1;i<=26;i++){
		       for(j=6;j<=8;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	
		    
		    //设置不显示边框
		    for(i=27;i<=28;i++){
		       for(j=1;j<=8;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }
		    
		    //设置不显示边框
		    for(i=35;i<=36;i++){
		       for(j=1;j<=8;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	 
		    
		    
		    for(row=2;row<=34;row++){
		     obj.SetCellFontStyle(2,row,sheetIndex,0);
		    }
		    obj.SetCellFontStyle(1,2,sheetIndex,0);
		    obj.SetCellFontStyle(1,1,sheetIndex,2);
		 
		    //设置字体颜色		    
		    obj.SetCellTextColor(3,36,sheetIndex,obj.FindColorIndex(0x000000,1));  
		    
			   
		     
	  }
 }
 
 /**
 *ADD BY TSG 2008-01-24
 S32报表第一部分表头部分
 */
 function load_setrow(obj,sheetIndex)
{
  
	obj.S(1,4,sheetIndex,"序号");
	obj.Mergecells(1,4,1,7);
	
	obj.S(2,4,sheetIndex,"A");
	obj.S(2,5,sheetIndex,"期限结构");
	obj.Mergecells(2,5,2,7);
	
    obj.S(3,4,sheetIndex,"B");
	obj.S(3,5,sheetIndex,"个数");
	obj.Mergecells(3,5,3,7);

    obj.S(4,4,sheetIndex,"C");
	obj.S(4,5,sheetIndex,"推介地（个数）");
	obj.Mergecells(4,5,5,6); 	
	obj.S(5,4,sheetIndex,"D");
	obj.S(5,7,sheetIndex,"异地");
	obj.S(4,7,sheetIndex,"本地");
	
	obj.S(6,4,sheetIndex,"E");
	obj.S(7,4,sheetIndex,"F");
	obj.S(8,4,sheetIndex,"G");
	obj.S(9,4,sheetIndex,"H");
	obj.S(6,5,sheetIndex,"现受益人");
	obj.Mergecells(6,5,9,5); 	
	obj.S(6,6,sheetIndex,"自然人");
	obj.Mergecells(6,6,7,6);
	obj.S(8,6,sheetIndex,"机构");
	obj.Mergecells(8,6,9,6);
	obj.S(6,7,sheetIndex,"人数");
	obj.S(7,7,sheetIndex,"金额");
	obj.S(8,7,sheetIndex,"个数");
	obj.S(9,7,sheetIndex,"金额");
	
   obj.S(10,4,sheetIndex,"I");
   obj.S(11,4,sheetIndex,"J");
   obj.S(12,4,sheetIndex,"K");
   obj.S(13,4,sheetIndex,"L");
   obj.S(10,5,sheetIndex,"功能分类（金额）");
   obj.Mergecells(10,5,13,5);
   obj.S(10,6,sheetIndex,"融资类");
   obj.Mergecells(10,6,10,7);
   obj.S(11,6,sheetIndex,"投资类");
   obj.Mergecells(11,6,11,7);
   obj.S(12,6,sheetIndex,"事务管理类");
   obj.Mergecells(12,6,12,7);
   obj.S(13,6,sheetIndex,"其他");
   obj.Mergecells(13,6,13,7);
   
   obj.S(14,5,sheetIndex,"运用方式（金额）");
   obj.Mergecells(14,5,30,5);
   obj.S(14,6,sheetIndex,"贷款");
   obj.Mergecells(14,6,15,6);
   obj.S(16,6,sheetIndex,"短期投资");
   obj.Mergecells(16,6,17,6);
   obj.S(18,6,sheetIndex,"长期债权投资");
   obj.Mergecells(18,6,19,6);
   obj.S(20,6,sheetIndex,"长期股权投资");
   obj.Mergecells(20,6,21,6);
   obj.S(22,6,sheetIndex,"租赁");
   obj.Mergecells(22,6,23,6);
   obj.S(24,6,sheetIndex,"买入返售");
   obj.Mergecells(24,6,26,6);
   obj.S(27,6,sheetIndex,"拆出");
   obj.Mergecells(27,6,28,6);
   obj.S(29,6,sheetIndex,"同业存放(金额）");
   obj.Mergecells(29,6,29,7);
   obj.S(30,6,sheetIndex,"其他   （金额）");
   obj.Mergecells(30,6,30,7);
   obj.S(14,7,sheetIndex,"涉及计划个数");
   obj.S(15,7,sheetIndex,"金额");
   obj.S(16,7,sheetIndex,"涉及计划个数");
   obj.S(17,7,sheetIndex,"金额");
   obj.S(18,7,sheetIndex,"涉及计划个数");
   obj.S(19,7,sheetIndex,"金额");
   obj.S(20,7,sheetIndex,"涉及计划个数");
   obj.S(21,7,sheetIndex,"金额");
   obj.S(22,7,sheetIndex,"涉及计划个数");
   obj.S(23,7,sheetIndex,"金额");
   obj.S(24,7,sheetIndex,"涉及计划个数");
   obj.S(25,7,sheetIndex,"证券金额");
   obj.S(26,7,sheetIndex,"信贷资产金额");
   obj.S(27,7,sheetIndex,"涉及计划个数");
   obj.S(28,7,sheetIndex,"金额");
   
   obj.S(31,5,sheetIndex,"投向（金额））");
   obj.Mergecells(31,5,44,5);
   obj.S(31,6,sheetIndex,"基础产业");
   obj.Mergecells(31,6,32,6);
   obj.S(33,6,sheetIndex,"房地产");
   obj.Mergecells(33,6,34,6);
   obj.S(35,6,sheetIndex,"证券");
   obj.Mergecells(35,6,38,6);
   obj.S(39,6,sheetIndex,"金融机构");
   obj.Mergecells(39,6,40,6);
   obj.S(41,6,sheetIndex,"工商企业");
   obj.Mergecells(41,6,42,6);
   obj.S(43,6,sheetIndex,"其他");
   obj.Mergecells(43,6,44,6);
   obj.S(31,7,sheetIndex,"涉及计划个数");
   obj.S(32,7,sheetIndex,"金额");
   obj.S(33,7,sheetIndex,"涉及计划个数");
   obj.S(34,7,sheetIndex,"金额");
   obj.S(35,7,sheetIndex,"涉及计划个数");
   obj.S(36,7,sheetIndex,"股票");
   obj.S(37,7,sheetIndex,"基金");
   obj.S(38,7,sheetIndex,"证券")
   obj.S(39,7,sheetIndex,"涉及计划个数");
   obj.S(40,7,sheetIndex,"金额");
   obj.S(41,7,sheetIndex,"涉及计划个数");
   obj.S(42,7,sheetIndex,"金额");
   obj.S(43,7,sheetIndex,"涉及计划个数");
   obj.S(44,7,sheetIndex,"金额");
   
   obj.S(45,5,sheetIndex,"信托单位净值低于1");
   obj.Mergecells(45,5,46,5);
   obj.S(47,5,sheetIndex,"涉诉项目   ");
   obj.Mergecells(47,5,48,5);
   obj.S(45,6,sheetIndex,"涉及计划个数");
   obj.Mergecells(45,6,45,7);
   obj.S(46,6,sheetIndex,"金额");
   obj.Mergecells(46,6,46,7);
   obj.S(47,6,sheetIndex,"涉及计划个数");
   obj.Mergecells(47,6,47,7);
   obj.S(48,6,sheetIndex,"金额");
   obj.Mergecells(48,6,48,7);
   
   obj.S(14,4,sheetIndex,"M");
   obj.S(15,4,sheetIndex,"N");
   obj.S(16,4,sheetIndex,"O");
   obj.S(17,4,sheetIndex,"P");
   obj.S(18,4,sheetIndex,"Q");
   obj.S(19,4,sheetIndex,"R");
   obj.S(20,4,sheetIndex,"S");
   obj.S(21,4,sheetIndex,"T");
   obj.S(22,4,sheetIndex,"U");
   obj.S(23,4,sheetIndex,"V");
   obj.S(24,4,sheetIndex,"W");
   obj.S(25,4,sheetIndex,"X");
   obj.S(26,4,sheetIndex,"Y");
   obj.S(27,4,sheetIndex,"Z");
   obj.S(28,4,sheetIndex,"AA");
   obj.S(29,4,sheetIndex,"AB");
   obj.S(30,4,sheetIndex,"AC");
   obj.S(31,4,sheetIndex,"AD");
   obj.S(32,4,sheetIndex,"AE");
   obj.S(33,4,sheetIndex,"AF");
   obj.S(34,4,sheetIndex,"AG");
   obj.S(35,4,sheetIndex,"AH");
   obj.S(36,4,sheetIndex,"HI");
   obj.S(37,4,sheetIndex,"AJ");
   obj.S(38,4,sheetIndex,"AK");
   obj.S(39,4,sheetIndex,"AL");
   obj.S(40,4,sheetIndex,"AM");
   obj.S(41,4,sheetIndex,"AN");
   obj.S(42,4,sheetIndex,"AO");
   obj.S(43,4,sheetIndex,"AP");
   obj.S(44,4,sheetIndex,"AQ");
   obj.S(45,4,sheetIndex,"AR");
   obj.S(46,4,sheetIndex,"AS");
   obj.S(47,4,sheetIndex,"AT");
   obj.S(48,4,sheetIndex,"AU");
    
    for(var i=1;i<=48;i++){
        if(i!=1&&i!=2){
	        obj.SetCellNumType(i,8,sheetIndex,1);
		    obj.SetCellSeparator(i,8,sheetIndex,2);
        }    		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	    obj.SetCellAlign (i,7,sheetIndex,32+4);
	    
	    //合并单元格会对颜色填充产生很大的影响，必须加上@2处的语句才能实现抬头0x80ffff色填充-----@1
	    if(i>=14&&i<=45 &&i!=30){
	       obj.SetCellBackColor(i,6,sheetIndex,obj.FindColorIndex(0x80ffff,1));   
	       obj.SetCellBackColor(i,7,sheetIndex,obj.FindColorIndex(0x80ffff,1)); 	        
	    }   		            	    	    
    }


	 
    for(var j=4;j<=35;j++){
    	 for(i=1;i<=48;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}


/**
 *ADD BY TSG 2008-01-24
 S32报表第二部分表头部分
 */
 function load_singlerow(obj,sheetIndex)
{
  
	obj.S(1,4,sheetIndex,"序号");
	obj.Mergecells(1,4,1,7);
	
	obj.S(2,4,sheetIndex,"A");
	obj.S(2,5,sheetIndex,"期限结构");
	obj.Mergecells(2,5,2,7);
	
    obj.S(3,4,sheetIndex,"B");
	obj.S(3,5,sheetIndex,"个数");
	obj.Mergecells(3,5,3,7);

    obj.S(4,4,sheetIndex,"C");
    obj.S(5,4,sheetIndex,"D");
    obj.S(6,4,sheetIndex,"E");
    obj.S(7,4,sheetIndex,"F");
	obj.S(4,5,sheetIndex,"委托人");
	obj.Mergecells(4,5,7,5); 	
	obj.S(4,6,sheetIndex,"自然人");
	obj.Mergecells(4,6,5,6); 
	obj.S(6,6,sheetIndex,"机构");
	obj.Mergecells(6,6,7,6);
	obj.S(4,7,sheetIndex,"人数");
	obj.S(5,7,sheetIndex,"金额");
	obj.S(6,7,sheetIndex,"个数");
	obj.S(7,7,sheetIndex,"金额");
	
	obj.S(8,4,sheetIndex,"G");
    obj.S(9,4,sheetIndex,"H");
    obj.S(10,4,sheetIndex,"I");
    obj.S(11,4,sheetIndex,"J");
	obj.S(8,5,sheetIndex,"现受益人");
	obj.Mergecells(8,5,11,5); 	
	obj.S(8,6,sheetIndex,"自然人");
	obj.Mergecells(8,6,9,6); 
	obj.S(10,6,sheetIndex,"机构");
	obj.Mergecells(10,6,11,6);
	obj.S(8,7,sheetIndex,"人数");
	obj.S(9,7,sheetIndex,"金额");
	obj.S(10,7,sheetIndex,"个数");
	obj.S(11,7,sheetIndex,"金额");
	
   obj.S(12,4,sheetIndex,"K");
   obj.S(13,4,sheetIndex,"L");
   obj.S(14,4,sheetIndex,"M");
   obj.S(15,4,sheetIndex,"N");
   obj.S(12,5,sheetIndex,"功能分类（金额）");
   obj.Mergecells(12,5,15,5);
   obj.S(12,6,sheetIndex,"融资类");
   obj.Mergecells(12,6,12,7);
   obj.S(13,6,sheetIndex,"投资类");
   obj.Mergecells(13,6,13,7);
   obj.S(14,6,sheetIndex,"事务管理类");
   obj.Mergecells(14,6,14,7);
   obj.S(15,6,sheetIndex,"其他");
   obj.Mergecells(15,6,15,7);
   
   obj.S(16,5,sheetIndex,"运用方式（金额）");
   obj.Mergecells(16,5,32,5);
   obj.S(16,6,sheetIndex,"贷款");
   obj.Mergecells(16,6,17,6);
   obj.S(18,6,sheetIndex,"短期投资");
   obj.Mergecells(18,6,19,6);
   obj.S(20,6,sheetIndex,"长期债权投资");
   obj.Mergecells(20,6,21,6);
   obj.S(22,6,sheetIndex,"长期股权投资");
   obj.Mergecells(22,6,23,6);
   obj.S(24,6,sheetIndex,"租赁");
   obj.Mergecells(24,6,25,6);
   obj.S(26,6,sheetIndex,"买入返售");
   obj.Mergecells(26,6,28,6);
   obj.S(29,6,sheetIndex,"拆出");
   obj.Mergecells(29,6,30,6);
   obj.S(31,6,sheetIndex,"同业存放(金额）");
   obj.Mergecells(31,6,31,7);
   obj.S(32,6,sheetIndex,"其他   （金额）");
   obj.Mergecells(32,6,32,7);
   obj.S(16,7,sheetIndex,"涉及项目个数");
   obj.S(17,7,sheetIndex,"金额");
   obj.S(18,7,sheetIndex,"涉及项目个数");
   obj.S(19,7,sheetIndex,"金额");
   obj.S(20,7,sheetIndex,"涉及项目个数");
   obj.S(21,7,sheetIndex,"金额");
   obj.S(22,7,sheetIndex,"涉及项目个数");
   obj.S(23,7,sheetIndex,"金额");
   obj.S(24,7,sheetIndex,"涉及项目个数");
   obj.S(25,7,sheetIndex,"金额");
   obj.S(26,7,sheetIndex,"涉及项目个数");
   obj.S(27,7,sheetIndex,"证券金额");
   obj.S(28,7,sheetIndex,"信贷资产金额");
   obj.S(29,7,sheetIndex,"涉及项目个数");
   obj.S(30,7,sheetIndex,"金额");
   
   obj.S(33,5,sheetIndex,"投向（金额））");
   obj.Mergecells(33,5,46,5);
   obj.S(33,6,sheetIndex,"基础产业");
   obj.Mergecells(33,6,34,6);
   obj.S(35,6,sheetIndex,"房地产");
   obj.Mergecells(35,6,36,6);
   obj.S(37,6,sheetIndex,"证券");
   obj.Mergecells(37,6,40,6);
   obj.S(41,6,sheetIndex,"金融机构");
   obj.Mergecells(41,6,42,6);
   obj.S(43,6,sheetIndex,"工商企业");
   obj.Mergecells(43,6,44,6);
   obj.S(45,6,sheetIndex,"其他");
   obj.Mergecells(45,6,46,6);
   obj.S(33,7,sheetIndex,"涉及项目个数");
   obj.S(34,7,sheetIndex,"金额");
   obj.S(35,7,sheetIndex,"涉及项目个数");
   obj.S(36,7,sheetIndex,"金额");
   obj.S(37,7,sheetIndex,"涉及项目个数");
   obj.S(38,7,sheetIndex,"股票");
   obj.S(39,7,sheetIndex,"基金");
   obj.S(40,7,sheetIndex,"证券")
   obj.S(41,7,sheetIndex,"涉及项目个数");
   obj.S(42,7,sheetIndex,"金额");
   obj.S(43,7,sheetIndex,"涉及项目个数");
   obj.S(44,7,sheetIndex,"金额");
   obj.S(45,7,sheetIndex,"涉及项目个数");
   obj.S(46,7,sheetIndex,"金额");
   
   obj.S(47,5,sheetIndex,"信托单位净值低于1");
   obj.Mergecells(47,5,48,5);
   obj.S(49,5,sheetIndex,"涉诉项目   ");
   obj.Mergecells(49,5,50,5);
   obj.S(47,6,sheetIndex,"涉及项目个数");
   obj.Mergecells(47,6,47,7);
   obj.S(48,6,sheetIndex,"金额");
   obj.Mergecells(48,6,48,7);
   obj.S(49,6,sheetIndex,"涉及项目个数");
   obj.Mergecells(49,6,49,7);
   obj.S(50,6,sheetIndex,"金额");
   obj.Mergecells(50,6,50,7);
   
   obj.S(16,4,sheetIndex,"O");
   obj.S(17,4,sheetIndex,"P");
   obj.S(18,4,sheetIndex,"Q");
   obj.S(19,4,sheetIndex,"R");
   obj.S(20,4,sheetIndex,"S");
   obj.S(21,4,sheetIndex,"T");
   obj.S(22,4,sheetIndex,"U");
   obj.S(23,4,sheetIndex,"V");
   obj.S(24,4,sheetIndex,"W");
   obj.S(25,4,sheetIndex,"X");
   obj.S(26,4,sheetIndex,"Y");
   obj.S(27,4,sheetIndex,"Z");
   obj.S(28,4,sheetIndex,"AA");
   obj.S(29,4,sheetIndex,"AB");
   obj.S(30,4,sheetIndex,"AC");
   obj.S(31,4,sheetIndex,"AD");
   obj.S(32,4,sheetIndex,"AE");
   obj.S(33,4,sheetIndex,"AF");
   obj.S(34,4,sheetIndex,"AG");
   obj.S(35,4,sheetIndex,"AH");
   obj.S(36,4,sheetIndex,"HI");
   obj.S(37,4,sheetIndex,"AJ");
   obj.S(38,4,sheetIndex,"AK");
   obj.S(39,4,sheetIndex,"AL");
   obj.S(40,4,sheetIndex,"AM");
   obj.S(41,4,sheetIndex,"AN");
   obj.S(42,4,sheetIndex,"AO");
   obj.S(43,4,sheetIndex,"AP");
   obj.S(44,4,sheetIndex,"AQ");
   obj.S(45,4,sheetIndex,"AR");
   obj.S(46,4,sheetIndex,"AS");
   obj.S(47,4,sheetIndex,"AT");
   obj.S(48,4,sheetIndex,"AU");
   obj.S(49,4,sheetIndex,"AV");
   obj.S(50,4,sheetIndex,"AW"); 
   
    for(var i=1;i<=50;i++){
        if(i!=1&&i!=2){
	        obj.SetCellNumType(i,8,sheetIndex,1);
		    obj.SetCellSeparator(i,8,sheetIndex,2);
        }    		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	    obj.SetCellAlign (i,7,sheetIndex,32+4);
	    
	    //合并单元格会对颜色填充产生很大的影响，必须加上@2处的语句才能实现抬头0x80ffff色填充-----@1
	    if(i>=16&&i<=47 &&i!=32){
	       obj.SetCellBackColor(i,6,sheetIndex,obj.FindColorIndex(0x80ffff,1));   
	       obj.SetCellBackColor(i,7,sheetIndex,obj.FindColorIndex(0x80ffff,1)); 	        
	    }   		            	    	    
    }
	 
    for(var j=4;j<=35;j++){
    	 for(i=1;i<=50;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

} 

/**
 *ADD BY TSG 2008-01-24
 S32报表第一部分表头部分
 */
 function load_propertyrow(obj,sheetIndex)
{
  
	obj.S(1,4,sheetIndex,"序号");
	obj.Mergecells(1,4,1,7);
	
	obj.S(2,4,sheetIndex,"A");
	obj.S(2,5,sheetIndex,"期限结构");
	obj.Mergecells(2,5,2,7);
	
    obj.S(3,4,sheetIndex,"B");
	obj.S(3,5,sheetIndex,"个数");
	obj.Mergecells(3,5,3,7);

    obj.S(4,4,sheetIndex,"C");
	obj.S(4,5,sheetIndex,"信托财产受托价值");
	obj.Mergecells(4,5,4,7); 	

	
	obj.S(5,4,sheetIndex,"D");
	obj.S(6,4,sheetIndex,"E");
	obj.S(7,4,sheetIndex,"F");
	obj.S(8,4,sheetIndex,"G");
	obj.S(5,5,sheetIndex,"委托人");
	obj.Mergecells(5,5,8,5); 	
	obj.S(5,6,sheetIndex,"自然人");
	obj.Mergecells(5,6,6,6);
	obj.S(7,6,sheetIndex,"机构");
	obj.Mergecells(7,6,8,6);
	obj.S(5,7,sheetIndex,"人数");
	obj.S(6,7,sheetIndex,"金额");
	obj.S(7,7,sheetIndex,"数量");
	obj.S(8,7,sheetIndex,"金额");
	
   obj.S(9,4,sheetIndex,"H");
   obj.S(10,4,sheetIndex,"I");
   obj.S(11,4,sheetIndex,"J");
   obj.S(12,4,sheetIndex,"K");
   obj.S(9,5,sheetIndex,"现受益人");
   obj.Mergecells(9,5,12,5);
   obj.S(9,6,sheetIndex,"自然人");
   obj.Mergecells(9,6,10,6);
   obj.S(11,6,sheetIndex,"机构");
   obj.Mergecells(11,6,12,6);
   obj.S(9,7,sheetIndex,"人数");
   obj.S(10,7,sheetIndex,"金额");
   obj.S(11,7,sheetIndex,"数量");
   obj.S(12,7,sheetIndex,"金额");
   
   obj.S(13,4,sheetIndex,"L");
   obj.S(14,4,sheetIndex,"M");
   obj.S(15,4,sheetIndex,"N");
   obj.S(16,4,sheetIndex,"O");
   obj.S(13,5,sheetIndex,"功能分类（金额）");
   obj.Mergecells(13,5,16,5);
   obj.S(13,6,sheetIndex,"融资类");
   obj.Mergecells(13,6,13,7);
   obj.S(14,6,sheetIndex,"投资类");
   obj.Mergecells(14,6,14,7);
   obj.S(15,6,sheetIndex,"事务管理类");
   obj.Mergecells(15,6,15,7);
   obj.S(16,6,sheetIndex,"其他");
   obj.Mergecells(16,6,16,7);
   
   obj.S(17,4,sheetIndex,"P");
   obj.S(18,4,sheetIndex,"Q");
   obj.S(19,4,sheetIndex,"R");
   obj.S(20,4,sheetIndex,"S");
   obj.S(21,4,sheetIndex,"T");
   obj.S(22,4,sheetIndex,"U");  
   obj.S(17,5,sheetIndex,"管理运用处分方式");
   obj.Mergecells(17,5,22,5);
   obj.S(17,6,sheetIndex,"出售");
   obj.Mergecells(17,6,17,7);
   obj.S(18,6,sheetIndex,"出租");
   obj.Mergecells(18,6,18,7);
   obj.S(19,6,sheetIndex,"资产证券化");
   obj.Mergecells(19,6,19,7);
   obj.S(20,6,sheetIndex,"准资产证券化");
   obj.Mergecells(20,6,20,7);
   obj.S(21,6,sheetIndex,"持有并管理");
   obj.Mergecells(21,6,21,7);
   obj.S(22,6,sheetIndex,"其他"); 
   obj.Mergecells(22,6,22,7);
   
   obj.S(23,4,sheetIndex," V");
   obj.S(24,4,sheetIndex,"W");
   obj.S(25,4,sheetIndex,"X");
   obj.S(26,4,sheetIndex,"Y");
   obj.S(23,5,sheetIndex,"信托单位净值低于1");
   obj.Mergecells(23,5,24,5);
   obj.S(25,5,sheetIndex,"涉诉项目   ");
   obj.Mergecells(25,5,26,5);
   obj.S(23,6,sheetIndex,"涉及项目个数");
   obj.Mergecells(23,6,23,7);
   obj.S(24,6,sheetIndex,"金额");
   obj.Mergecells(24,6,24,7);
   obj.S(25,6,sheetIndex,"涉及项目个数");
   obj.Mergecells(25,6,25,7);
   obj.S(26,6,sheetIndex,"金额");
   obj.Mergecells(26,6,26,7);
   
    
    for(var i=1;i<=26;i++){
        if(i!=1&&i!=2){
	        obj.SetCellNumType(i,8,sheetIndex,1);
		    obj.SetCellSeparator(i,8,sheetIndex,2);
        }    		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	    obj.SetCellAlign (i,7,sheetIndex,32+4);
	    
	    //合并单元格会对颜色填充产生很大的影响，必须加上@2处的语句才能实现抬头0x80ffff色填充-----@1
	    if(i>=17&&i<=23){
	       obj.SetCellBackColor(i,6,sheetIndex,obj.FindColorIndex(0x80ffff,1));   
	       obj.SetCellBackColor(i,7,sheetIndex,obj.FindColorIndex(0x80ffff,1)); 	        
	    }   		            	    	    
    }
	 
    for(var j=4;j<=29;j++){
    	 for(i=1;i<=26;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}

/**
 *ADD BY TSG 2008-01-25
 S32报表第四部分（补充材料一：信托资金投资有价证券风险情况表）表头部分
 */
 function load_firstaddedrow(obj,sheetIndex)
{
    obj.SetCellFont(1,4,sheetIndex,obj.FindFontIndex ("宋体",1));
    obj.SetCellFontStyle(1,4,sheetIndex,2);
	obj.S(1,4,sheetIndex,"序号");
	obj.Mergecells(1,4,1,6);
	
	obj.SetCellFont(2,4,sheetIndex,obj.FindFontIndex ("宋体",1));
	 obj.SetCellFontStyle(2,4,sheetIndex,2);
	obj.S(2,4,sheetIndex,"项目");
	obj.Mergecells(2,4,2,6);
	
    obj.S(3,4,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"账面价值");
	obj.Mergecells(3,5,3,6);

    obj.S(4,4,sheetIndex,"B");
	obj.S(4,5,sheetIndex,"市场价值");
	obj.Mergecells(4,5,4,6); 	
	
	obj.S(5,4,sheetIndex,"C");
	obj.S(5,5,sheetIndex,"已确认损益");
	obj.Mergecells(5,5,5,6); 
	
	obj.S(6,4,sheetIndex,"D");
	obj.S(7,4,sheetIndex,"E");
	obj.S(8,4,sheetIndex,"F");
	obj.S(9,4,sheetIndex,"G");
	obj.S(10,4,sheetIndex,"H");
	obj.S(6,5,sheetIndex,"风险状况");
    obj.Mergecells(6,5,10,5);
    obj.S(6,6,sheetIndex,"正常");
	obj.S(7,6,sheetIndex,"关注");
	obj.S(8,6,sheetIndex,"次级");
	obj.S(9,6,sheetIndex,"可疑");
	obj.S(10,6,sheetIndex,"损失");
	   
    for(var i=1;i<=10;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中      		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //设置边框线
    for(var j=4;j<=30;j++){
    	 for(i=1;i<=10;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}


/**
 *ADD BY TSG 2008-01-25
 S32报表第四部分补充材料一附注1（补充材料一：信托资金投资有价证券风险情况表）表头部分
 */
 function load_firstaddednoted1row(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(3)),6,sheetIndex);	
   obj.MergeCells(1,34,4,34);
   obj.SetCellAlign(1,34,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,34,sheetIndex,2);
   obj.S(1,34,sheetIndex,"附注1：从信用主体结构分析债券投资（只需填账面价值）：");	


	obj.SetCellFontStyle(1,35,sheetIndex,2);
	obj.Mergecells(1,35,1,36);
	obj.S(1,35,sheetIndex,"序号"); 
		
	obj.SetCellFontStyle(2,35,sheetIndex,2);
	obj.Mergecells(2,35,2,36);
	obj.S(2,35,sheetIndex,"项目");

	
    obj.S(3,35,sheetIndex,"A");
	obj.S(3,36,sheetIndex,"本币");


    obj.S(4,35,sheetIndex,"B");
	obj.S(4,36,sheetIndex,"外币折人民币");
	
	//设置数据类型
	    for(var i=1;i<=4;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,37,sheetIndex,1);
			obj.SetCellSeparator(i,37,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中      		 
	    obj.SetCellAlign (i,35,sheetIndex,32+4);
	    obj.SetCellAlign (i,36,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //设置边框线
    for(var j=35;j<=49;j++){
    	 for(i=1;i<=4;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	
}

/**
 *ADD BY TSG 2008-01-25
 S32报表第四部分补充材料一附注2（补充材料一：信托资金投资有价证券风险情况表）表头部分
 */
 function load_firstaddednoted2row(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(3)),5,sheetIndex);	
   obj.MergeCells(1,51,4,51);
   obj.SetCellAlign(1,51,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,51,sheetIndex,2);
   obj.S(1,51,sheetIndex,"附注2：按证券交易市场类别划分");	


	obj.SetCellFontStyle(1,52,sheetIndex,2);
	obj.Mergecells(1,52,1,54);
	obj.S(1,52,sheetIndex,"序号"); 
		
	obj.SetCellFontStyle(2,52,sheetIndex,2);
	obj.Mergecells(2,52,2,54);
	obj.S(2,52,sheetIndex,"项目");

	
    obj.S(3,52,sheetIndex,"A");
    obj.Mergecells(3,53,3,54);
	obj.S(3,53,sheetIndex,"个数");


    obj.S(4,52,sheetIndex,"B");
    obj.S(5,52,sheetIndex,"C");
    obj.S(6,52,sheetIndex,"D");
    obj.S(7,52,sheetIndex,"E");
    obj.Mergecells(4,53,7,53);
	obj.S(4,53,sheetIndex,"实收信托(金额)");
	obj.S(4,54,sheetIndex,"银信合作");
    obj.S(5,54,sheetIndex,"证信合作");
    obj.S(6,54,sheetIndex,"私募基金合作");
    obj.S(7,54,sheetIndex,"其他");
	
	//设置数据类型
	    for(var i=1;i<=4;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,55,sheetIndex,1);
			obj.SetCellSeparator(i,55,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中      		 
	    obj.SetCellAlign (i,52,sheetIndex,32+4);
	    obj.SetCellAlign (i,53,sheetIndex,32+4);
	    obj.SetCellAlign (i,54,sheetIndex,32+4);	      		            	    	    
    }
	 
	 //设置边框线
    for(var j=52;j<=67;j++){
    	 for(i=1;i<=4;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	
}

/**
 *ADD BY TSG 2008-01-28
 S32报表第五部分（补充材料二：信托资金运用行业分类情况）表头部分
 */
 function load_secondaddedrow(obj,sheetIndex)
{
    obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("宋体",1));
    obj.SetCellFontStyle(1,2,sheetIndex,2);
	obj.S(1,2,sheetIndex,"序号");
	obj.Mergecells(1,2,1,3);
	
	 //画对角线
	obj.Mergecells(2,2,2,3);
	obj.DrawGridLine(2,2,2,3,6,1,-1);
	obj.S(2,2,sheetIndex,"     行业名称|项目     ");
	
    obj.S(3,2,sheetIndex,"A");
	obj.S(3,3,sheetIndex,"本期余额");

    obj.S(4,2,sheetIndex,"B");
	obj.S(4,3,sheetIndex,"本年累计已清算交付");	
	
	obj.S(5,2,sheetIndex,"C");
	obj.S(5,3,sheetIndex,"管理信托资产本年累计");
	

	   
    for(var i=1;i<=5;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,4,sheetIndex,1);
			obj.SetCellSeparator(i,4,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中      		 
	    obj.SetCellAlign (i,2,sheetIndex,32+4);
	    obj.SetCellAlign (i,3,sheetIndex,32+4);	      		            	    	    
    }
	 
	 //设置边框线
    for(var j=2;j<=26;j++){
    	 for(i=1;i<=5;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}

/**
 *ADD BY TSG 2008-01-25
 S32报表第五部分补充材料二：信托资金运用行业分类情况>>注：信托项目信用风险资产五级分类情况表头部分
 */
 function load_secondaddednoted1row(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(4)),4,sheetIndex);	
   
   obj.MergeCells(1,28,2,28);
   obj.SetCellAlign(1,28,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,28,sheetIndex,2);
   obj.S(1,28,sheetIndex,"附注：信托项目信用风险资产五级分类情况");	

	obj.Mergecells(1,29,1,30);
	obj.S(1,29,sheetIndex,"序号"); 
		
	obj.Mergecells(2,29,2,30);
	obj.S(2,29,sheetIndex,"信托项目类型");

	
    obj.S(3,29,sheetIndex,"A");
	obj.S(3,30,sheetIndex,"信用类资产余额");

    obj.S(4,29,sheetIndex,"B");
	obj.S(4,30,sheetIndex,"正常");
	
	obj.S(5,29,sheetIndex,"C");
	obj.S(5,30,sheetIndex,"关注");
	
	obj.S(6,29,sheetIndex,"D");
	obj.S(6,30,sheetIndex,"次级");
	
	obj.S(7,29,sheetIndex,"E");
	obj.S(7,30,sheetIndex,"可疑");
	
	obj.S(8,29,sheetIndex,"F");
	obj.S(8,30,sheetIndex,"损失");   
	
	//设置数据类型
	    for(var i=1;i<=8;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,31,sheetIndex,1);
			obj.SetCellSeparator(i,31,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中      		 
	    obj.SetCellAlign (i,29,sheetIndex,32+4);
	    obj.SetCellAlign (i,30,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //设置边框线
    for(var j=29;j<=34;j++){
    	 for(i=1;i<=8;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	
}

/**
   ADD BY TSG 2008-01-29
   
 * S33信托项目到期情况统计表 表头设置
 */
function loadHeadOfFundsManageByMonthForS33(obj,statementsTitle,cols,hzDate,companyName,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//设置列数 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S33");
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	if(sheetIndex==0){
	        //大抬头		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("黑体",1));
			obj.SetCellFontSize(1,1,sheetIndex,15);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,38,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//填报机构	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,16+1);
			obj.Mergecells(1,2,2,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //报表日期---单位
		    obj.SetCellFont(7,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(7,2,sheetIndex,10.5); 
			obj.SetCellAlign(7,2,sheetIndex,16+4);
		    obj.Mergecells (7,2,14,2); 
			obj.S (7,2,sheetIndex,hzDate+"   "+"单位：万元,个");  
			

			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,11); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"第一部分:已清算信托项目情况表");  
			obj.Mergecells (1,3,5,3);
			
		}
}	

/**
 *ADD BY TSG 2008-01-29
 
 S33信托项目到期情况统计表  第一部分:已清算信托项目情况表 表头部分
 */
 function load_liquidatedrow(obj,sheetIndex)
{
	obj.S(1,4,sheetIndex,"序号");
	obj.Mergecells(1,4,1,6);
	
	obj.S(2,4,sheetIndex,"类型");
	obj.Mergecells(2,4,2,6);
	
	obj.S(3,4,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"信托项目个数");
	obj.Mergecells(3,5,3,6);
	
    obj.S(4,4,sheetIndex,"B");
    obj.S(5,4,sheetIndex,"C");
    obj.S(6,4,sheetIndex,"D");
	obj.S(4,5,sheetIndex,"已到期信托项目实收信托");
	obj.Mergecells(4,5,6,5);
	obj.S(4,6,sheetIndex,"集合");
    obj.S(5,6,sheetIndex,"单一");
    obj.S(6,6,sheetIndex,"财产权");

    obj.S(7,4,sheetIndex,"E");
	obj.S(7,5,sheetIndex,"信托本金累计给付额");
	obj.Mergecells(7,5,7,6); 	
	
	obj.S(8,4,sheetIndex,"F");
	obj.S(8,5,sheetIndex,"信托收益累计分配额");
	obj.Mergecells(8,5,8,6); 
	
	obj.S(9,4,sheetIndex,"G");
	obj.S(10,4,sheetIndex,"H");	
	obj.S(9,5,sheetIndex,"信托本金及收益支付来源");
	obj.Mergecells(9,5,10,5);	
	obj.S(9,6,sheetIndex,"本项目");
	obj.S(10,6,sheetIndex,"其他");
	
	obj.S(11,4,sheetIndex,"I");
    obj.S(11,5,sheetIndex,"信托费用率（％）");
    obj.Mergecells(11,5,11,6);
    
    
	obj.S(12,4,sheetIndex,"J");
	obj.S(12,5,sheetIndex,"实际收益率（％）");
	obj.Mergecells(12,5,12,6);
	 
	obj.S(13,4,sheetIndex,"K");
	obj.S(13,5,sheetIndex,"实际信托报酬率（％）");
	obj.Mergecells(13,5,13,6);
	
    obj.S(14,4,sheetIndex,"L");
	obj.S(14,5,sheetIndex,"损失金额");
	obj.Mergecells(14,5,14,6);
	
	obj.S(15,4,sheetIndex,"M");
	obj.S(15,5,sheetIndex,"涉诉金额");
	obj.Mergecells(15,5,15,6);
	
    obj.S(16,4,sheetIndex,"N");
	obj.S(17,4,sheetIndex,"O");	
	obj.S(16,5,sheetIndex,"赔付情况");
	obj.Mergecells(16,5,17,5);	
	obj.S(16,6,sheetIndex,"赔付个数");
	obj.S(17,6,sheetIndex,"赔付金额");
	   
    for(var i=1;i<=17;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中      		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //设置边框线
    for(var j=4;j<=12;j++){
    	 for(i=1;i<=17;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}

/**
 *ADD BY TSG 2008-01-29
 //S33信托项目到期情况统计表>>第二部分:到期未清算信托项目情况表  表头部分
 */
 function load_unliquidatedrow(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(0)),5,sheetIndex);	
   obj.MergeCells(1,14,5,14);
   obj.SetCellAlign(1,14,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,14,sheetIndex,2);
   obj.SetCellFontSize(1,14,sheetIndex,11); 			
   obj.S(1,14,sheetIndex,"第二部分:到期未清算信托项目情况表");	

    obj.S(1,15,sheetIndex,"序号");
	obj.Mergecells(1,15,1,17);
	
	obj.S(2,15,sheetIndex,"A");
	obj.S(2,16,sheetIndex,"信托项目名称(个数)");
	obj.Mergecells(2,16,2,17);
	
    obj.S(3,15,sheetIndex,"B");
    obj.S(4,15,sheetIndex,"C");
    obj.S(5,15,sheetIndex,"D");
	obj.S(3,16,sheetIndex,"实收信托");
	obj.Mergecells(3,16,5,16);
	obj.S(3,17,sheetIndex,"集合");
    obj.S(4,17,sheetIndex,"单一");
    obj.S(5,17,sheetIndex,"财产权");

    obj.S(6,15,sheetIndex,"E");
	obj.S(6,16,sheetIndex,"信托本金累计给付额");
	obj.Mergecells(6,16,6,17); 	
	
	obj.S(7,15,sheetIndex,"F");
	obj.S(7,16,sheetIndex,"信托收益累计分配额");
	obj.Mergecells(7,16,7,17); 
	
	obj.S(8,15,sheetIndex,"G");
	obj.S(8,16,sheetIndex,"损失金额");
	obj.Mergecells(8,16,8,17); 
	
	obj.S(9,15,sheetIndex,"H");
	obj.S(9,16,sheetIndex,"涉诉金额");
	obj.Mergecells(9,16,9,17); 
	
	obj.S(10,15,sheetIndex,"I");
	obj.S(10,16,sheetIndex,"赔付金额");
	obj.Mergecells(10,16,10,17); 
	
	//设置数据类型
	    for(var i=1;i<=10;i++){
	     if(i!=1){
	     	obj.SetCellNumType(i,18,sheetIndex,1);
			obj.SetCellSeparator(i,18,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中      		 
	    obj.SetCellAlign (i,16,sheetIndex,32+4);
	    obj.SetCellAlign (i,17,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //设置边框线
    for(var j=15;j<=26;j++){
    	 for(i=1;i<=10;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	
}

/**
   ADD BY TSG 2008-01-29
   
 * S33信托项目到期情况统计表>>第二部分:到期未清算信托项目情况表表尾设置
 */
 function loadEndOfFundsManagexByMonthForS33(obj,cols,rows,op_name,sheetIndex){  
    if(sheetIndex==0){  
        var allRows=obj.GetRows(0);
        obj.InsertRow(obj.GetRows(0),1,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,7,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"填表人：" + op_name); 
		obj.SetCellAlign (9,allRows,sheetIndex,32+1);
		obj.Mergecells (9,allRows,10,rows);
		obj.S (9,allRows,0,"复核人：");
    }
  }
  
  /**
   ADD BY TSG 2008-01-29
   
 * 格式化 S33信托项目到期情况统计表>>第二部分:到期未清算信托项目情况表表尾设置

 */
 
 function formatStatementsForS33(obj,sheetIndex){
	  if(sheetIndex==0){
		    ///document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列） 
		    var lastrow=obj.GetRows(sheetIndex)-1;
		    for(i=2;i<=26;i++){
		        if(i==13)
		          obj.SetRowHeight(1,10,i,sheetIndex);
		        else if(i==5)
		          obj.SetRowHeight(1,30,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    for(i=3;i<=18;i++){
		      	obj.SetColWidth(1,60,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线--左右
		    for(i=4;i<=12;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(17,i,sheetIndex,2,3);
		    }
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=17;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,12,sheetIndex,3,3);
		    }
		    
		     //设置外边框为粗线--左右
		    for(i=15;i<=lastrow-1;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(10,i,sheetIndex,2,3);
		    }
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=10;i++){
		       obj.SetCellBorder(i,15,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-1,sheetIndex,3,3);
		    }

		    //设置字体自动换行
		    for(row=5;row<=6;row++){
		      for(col=1;col<=17;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //设置无线
		    for(row=13;row<=14;row++){
		      for(col=1;col<=17;col++){
		        obj.SetCellBorder(col,row,sheetIndex,0,1);
		        obj.SetCellBorder(col,row,sheetIndex,1,1);
		        obj.SetCellBorder(col,row,sheetIndex,2,1);
		        obj.SetCellBorder(col,row,sheetIndex,3,1);
		      }
		    }
		    
		    //设置无线
		    for(row=15;row<=lastrow-1;row++){
		      for(col=11;col<=17;col++){
		        obj.SetCellBorder(col,row,sheetIndex,0,1);
		        obj.SetCellBorder(col,row,sheetIndex,1,1);
		        obj.SetCellBorder(col,row,sheetIndex,2,1);
		        obj.SetCellBorder(col,row,sheetIndex,3,1);
		      }
		    }
		    
		      for(col=1;col<=17;col++){
		        obj.SetCellBorder(col,lastrow,sheetIndex,0,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,1,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,2,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,3,1);		    
		    }
		    		    //设置字体自动换行
		    for(row=16;row<=17;row++){
		      for(col=1;col<=10;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    obj.SetCellAlign(2,15,sheetIndex,32+4);
		    obj.SetCellBackColor(9,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1)); 
		    obj.SetCellBackColor(10,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1)); 
		    obj.SetCellBackColor(11,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1)); 
		    obj.SetCellBackColor(12,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1)); 
		    obj.SetCellBackColor(13,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1));
	  }
}	

/**
   ADD BY TSG 2008-01-30
   
 * S34信托投资公司关联交易风险及客户集中度情况表>>第一部分：信托投资公司关联交易风险状况(1) 表头设置
 */
function loadHeadOfFundsManageByMonthForS34(obj,statementsTitle,cols,hzDate,companyName,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//设置列数 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S34第一部分_I");
	}else if(sheetIndex==1){
	  obj.SetSheetLabel(sheetIndex,"S34第一部分_II");
	}else if(sheetIndex==2){
	  obj.SetSheetLabel(sheetIndex,"S34第二部分");
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	if(sheetIndex==0){
	        //大抬头		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("黑体",1));
			obj.SetCellFontSize(1,1,sheetIndex,16);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,43,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//填报机构	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,32+1);
			obj.Mergecells(1,2,4,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //报表日期
		    obj.SetCellFont(5,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(5,2,sheetIndex,10.5); 
			obj.SetCellAlign(5,2,sheetIndex,32+4);
		    obj.Mergecells (5,2,12,2); 
			obj.S (5,2,sheetIndex,hzDate);  
			
		    //单位
		    obj.SetCellFont(13,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(13,2,sheetIndex,10.5); 
			obj.SetCellAlign(13,2,sheetIndex,32+4);
		    obj.Mergecells (13,2,17,2); 
			obj.S (13,2,sheetIndex,"货币单位：万元"); 
			

			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,14); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"第一部分：信托投资公司关联交易风险状况(1)");  
			obj.Mergecells (1,3,9,3);
			
			obj.SetCellAlign(1,4,sheetIndex,32+1);
			obj.SetCellFontSize(1,4,sheetIndex,11); 
			obj.S (1,4,sheetIndex,"一、信托投资公司与关联方交易风险状况表");  
			obj.Mergecells (1,4,8,4);									
		}else if(sheetIndex==1){
	        //大抬头		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("黑体",1));
			obj.SetCellFontSize(1,1,sheetIndex,16);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,43,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//填报机构	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,32+1);
			obj.Mergecells(1,2,4,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //报表日期---单位
		    obj.SetCellFont(5,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(5,2,sheetIndex,10.5); 
			obj.SetCellAlign(5,2,sheetIndex,32+4);
		    obj.Mergecells (5,2,9,2); 
			obj.S (5,2,sheetIndex,hzDate+"     "+"货币单位：万元");  
			
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,14); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"第一部分：信托投资公司关联交易风险状况(2)");  
			obj.Mergecells (1,3,8,3);
			
			obj.SetCellAlign(1,4,sheetIndex,32+1);
			obj.SetCellFontStyle(1,4,sheetIndex,2); 
			obj.S (1,4,sheetIndex,"   二、信托财产相互交易风险状况表");  
			obj.Mergecells (1,4,5,4);									
		}else if(sheetIndex==2){
	        //大抬头		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("黑体",1));
			obj.SetCellFontSize(1,1,sheetIndex,16);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,43,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//填报机构	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,32+1);
			obj.Mergecells(1,2,5,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //报表日期
		    obj.SetCellFont(6,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(6,2,sheetIndex,10.5); 
			obj.SetCellAlign(6,2,sheetIndex,32+4);
		    obj.Mergecells (6,2,11,2); 
			obj.S (6,2,sheetIndex,hzDate);  
			
		    //单位
		    obj.SetCellFont(16,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(16,2,sheetIndex,10.5); 
			obj.SetCellAlign(16,2,sheetIndex,32+4);
		    obj.Mergecells (16,2,20,2); 
			obj.S (16,2,sheetIndex,"货币单位：万元"); 
			

			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,14); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"第二部分：信托投资公司最大十家客户（含集团客户）融资集中度情况表");  
			obj.Mergecells (1,3,15,3);									
		}
}

/**
 *ADD BY TSG 2008-01-30
 
 S34信托投资公司关联交易风险及客户集中度情况表>>第一部分：信托投资公司关联交易风险状况(1)	 表头部分
 */
 function load_sheet1row(obj,sheetIndex)
{
	obj.S(1,5,sheetIndex,"序号");
	obj.Mergecells(1,5,1,8);

	obj.S(2,5,sheetIndex,"A");
	obj.S(2,6,sheetIndex,"关联方名称");
	obj.Mergecells(2,6,2,8);
	
	obj.S(3,5,sheetIndex,"B");
	obj.S(3,6,sheetIndex,"企业机构代码");
	obj.Mergecells(3,6,3,8);
	
	obj.S(4,5,sheetIndex,"C");
	obj.S(4,6,sheetIndex,"关联类型");
	obj.Mergecells(4,6,4,8);
	
	obj.S(5,5,sheetIndex,"D");
	obj.S(5,6,sheetIndex,"交易资金来源");
	obj.Mergecells(5,6,5,8);
	
    obj.S(6,5,sheetIndex,"E");
    obj.S(7,5,sheetIndex,"F");
    obj.S(8,5,sheetIndex,"G");
    obj.S(9,5,sheetIndex,"H");
    obj.S(10,5,sheetIndex,"I");
	obj.S(6,6,sheetIndex,"表内交易（资金融出）");
	obj.Mergecells(6,6,10,6);
	obj.S(6,7,sheetIndex,"贷款余额");
	obj.Mergecells(6,7,6,8);
    obj.S(7,7,sheetIndex,"租赁余额");
    obj.Mergecells(7,7,7,8);
    obj.S(8,7,sheetIndex,"投资余额");
    obj.Mergecells(8,7,8,8);
    obj.S(9,7,sheetIndex,"买入返售资产余额");
    obj.Mergecells(9,7,9,8);
    obj.S(10,7,sheetIndex,"其他融出余额");
    obj.Mergecells(10,7,10,8);
	
	obj.S(11,5,sheetIndex,"J");
	obj.S(11,6,sheetIndex,"表外交易");
	obj.Mergecells(11,6,11,8);
	
	obj.S(12,5,sheetIndex,"K");
	obj.S(12,6,sheetIndex,"关联交易合计金额");
	obj.Mergecells(12,6,12,8);
	
	obj.S(13,5,sheetIndex,"L");
    obj.S(14,5,sheetIndex,"M");
    obj.S(15,5,sheetIndex,"N");
    obj.S(16,5,sheetIndex,"O");
    obj.S(17,5,sheetIndex,"P");
	obj.S(13,6,sheetIndex,"风险状况");
	obj.Mergecells(13,6,17,6);
	obj.S(13,7,sheetIndex,"正常");
	obj.Mergecells(13,7,13,8);
    obj.S(14,7,sheetIndex,"关注");
    obj.Mergecells(14,7,14,8);
    obj.S(15,7,sheetIndex,"次级");
    obj.Mergecells(15,7,15,8);
    obj.S(16,7,sheetIndex,"可疑");
    obj.Mergecells(16,7,16,8);
    obj.S(17,7,sheetIndex,"损失");
    obj.Mergecells(17,7,17,8);
	   
    for(var i=1;i<=17;i++){
	     if(i!=1 && i!=2&& i!=5){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中    
	    obj.SetCellAlign (i,5,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	    obj.SetCellAlign (i,7,sheetIndex,32+4);
	    obj.SetCellAlign (i,8,sheetIndex,32+4);
	      		            	    	    
    }
	 var rows=obj.GetRows(sheetIndex);
	 
	 //设置边框线
    for(var j=5;j<=rows;j++){
    	 for(i=1;i<=17;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}	  

/**
   ADD BY TSG 2008-01-30
   
 * S34信托投资公司关联交易风险及客户集中度情况表>>第一部分：信托投资公司关联交易风险状况(1) 表尾设置
 */
 function loadEndOfFundsManagexByMonthForS34(obj,cols,rows,op_name,sheetIndex){  
    if(sheetIndex==0){  
        var allRows=obj.GetRows(0);
        obj.InsertRow(obj.GetRows(0),1,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"填表人：" + op_name); 
		obj.SetCellAlign (5,allRows,sheetIndex,32+1);
		obj.Mergecells (5,allRows,11,rows);
		obj.S (5,allRows,0,"复核人：");
		obj.Mergecells (11,allRows,17,allRows);
    }else if(sheetIndex==1){  
        var allRows=obj.GetRows(sheetIndex);
        obj.InsertRow(obj.GetRows(sheetIndex),1,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);		
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"填表人：" + op_name); 
		
		obj.SetCellAlign (5,allRows,sheetIndex,32+1);
		obj.S (5,allRows,sheetIndex,"复核人：");
		
		obj.SetCellAlign (7,allRows,sheetIndex,32+1);
		obj.Mergecells (7,allRows,9,rows);
		obj.S (7,allRows,sheetIndex,"负责人：");
    }else if(sheetIndex==2){  
        var allRows=obj.GetRows(sheetIndex);
        obj.InsertRow(obj.GetRows(sheetIndex),2,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"填表人：" + op_name); 
		obj.SetCellAlign (5,allRows,sheetIndex,32+1);
		obj.Mergecells (5,allRows,12,rows);
		obj.S (5,allRows,sheetIndex,"复核人：");
		obj.Mergecells (14,allRows,20,allRows);
		obj.S (1,allRows+1,sheetIndex,"注：交易资金来源：1表示固有资金；2表示信托资金（信托理财）；3表示代理资金（委托理财）");
		obj.Mergecells (1,allRows+1,20,allRows+1);
    }
  }
  
  /**
   ADD BY TSG 2008-01-30
   
 * 格式化  S34信托投资公司关联交易风险及客户集中度情况表>>第一部分：信托投资公司关联交易风险状况(1) 

 */
 
 function formatStatementsForS34(obj,sheetIndex){
	  if(sheetIndex==0){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列） 
		    var lastrow=obj.GetRows(sheetIndex)-1;
		    for(i=2;i<=lastrow;i++){
		        if(i==3)
		          obj.SetRowHeight(1,30,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    for(i=3;i<=18;i++){
		      	obj.SetColWidth(1,60,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线--左右
		    for(i=5;i<=lastrow;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(17,i,sheetIndex,2,3);
		    }
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=17;i++){
		       obj.SetCellBorder(i,5,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-1,sheetIndex,3,3);
		    }
		  

		    //设置字体自动换行
		    for(row=5;row<=8;row++){
		      for(col=1;col<=17;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //设置无线
		    for(row=lastrow;row<=lastrow;row++){
		      for(col=1;col<=17;col++){
		        obj.SetCellBorder(col,lastrow,sheetIndex,0,1);
		       obj.SetCellBorder(col,lastrow,sheetIndex,1,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,2,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,3,1);
		      }
		    }
		    
		    for(col=2;col<=17;col++){
		      obj.SetCellFontStyle(col,5,sheetIndex,2);
		    }
		    //obj.SetCellBackColor(9,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1)); 		  
	  }else if(sheetIndex==1){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列） 
		    var lastrow=obj.GetRows(sheetIndex)-1;
		    for(i=2;i<=lastrow;i++){
		        if(i==3)
		          obj.SetRowHeight(1,30,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,30,i,sheetIndex);
		    }
		    for(i=2;i<=8;i++){
		      	obj.SetColWidth(1,130,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线--左右
		    for(i=5;i<=lastrow;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(8,i,sheetIndex,2,3);
		    }
		    
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=8;i++){
		       obj.SetCellBorder(i,5,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow,sheetIndex,3,3);
		    }
		    
		    
		  

		    //设置字体自动换行
		    for(row=5;row<=6;row++){
		      for(col=1;col<=8;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //设置无线
		    for(row=lastrow;row<=lastrow;row++){
		      for(col=1;col<=8;col++){
		        obj.SetCellBorder(col,lastrow,sheetIndex,0,1);
		       obj.SetCellBorder(col,lastrow,sheetIndex,1,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,2,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,3,1);
		      }
		    }
		    
		    for(col=2;col<=8;col++){
		      obj.SetCellFontStyle(col,5,sheetIndex,2);
		    }
		    //obj.SetCellBackColor(9,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1)); 		  
	  }else  if(sheetIndex==2){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列） 
		    document.theform.DCellWeb1.SetColWidth(1,30,2,sheetIndex); //设置第一列宽（序号列）
		    var lastrow=obj.GetRows(sheetIndex)-1;
		    for(i=2;i<=lastrow;i++){
		        if(i==3 || i==4 || i==5 || i==6)
		          obj.SetRowHeight(1,30,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    for(i=5;i<=20;i++){
		      	obj.SetColWidth(1,60,i,sheetIndex);
		    }
		    
		
		    //设置外边框为粗线--左右
		    for(i=4;i<=lastrow-2;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(20,i,sheetIndex,2,3);
		    }
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=20;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-2,sheetIndex,3,3);
		    }
		  

		    //设置字体自动换行
		    for(row=5;row<=6;row++){
		      for(col=1;col<=20;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //设置无线
		    for(row=lastrow-1;row<=lastrow;row++){
		      for(col=1;col<=20;col++){
		        obj.SetCellBorder(col,row,sheetIndex,0,1);
		        obj.SetCellBorder(col,row,sheetIndex,1,1);
		        obj.SetCellBorder(col,row,sheetIndex,2,1);
		        obj.SetCellBorder(col,row,sheetIndex,3,1);
		      }
		    }
		    
		    for(col=3;col<=20;col++){
		      obj.SetCellFontStyle(col,4,sheetIndex,2);
		    }
		    		    //设置无线
		    for(row=7;row<=lastrow-3;row++){
		        obj.SetCellAlign(3,row,sheetIndex,32+1);
		        obj.SetCellAlign(3,row,sheetIndex,32+1);
		        obj.SetCellAlign(3,row,sheetIndex,32+1);
		        obj.SetCellAlign(3,row,sheetIndex,32+1);
		    
		    }		  
	  }
}	

/**
 *ADD BY TSG 2008-01-30
 
 S34信托投资公司关联交易风险及客户集中度情况表>>第一部分：信托投资公司关联交易风险状况(2)>>二、信托财产相互交易风险状况表	 表头部分
 */
 function load_sheet2_1row(obj,sheetIndex)
{
	obj.S(1,5,sheetIndex,"序号");
	obj.Mergecells(1,5,1,6);

	obj.S(2,5,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"B");
	obj.S(4,5,sheetIndex,"C");
	obj.S(5,5,sheetIndex,"D");
	obj.S(6,5,sheetIndex,"E");
	obj.S(7,5,sheetIndex,"F");
	obj.S(8,5,sheetIndex,"G");
	
	obj.S(2,6,sheetIndex,"信托项目(计划)名称");
	obj.S(3,6,sheetIndex,"交易对方信托项目(计划)名称");
	obj.S(4,6,sheetIndex,"交易金额");
	obj.S(5,6,sheetIndex,"交易种类");
	obj.S(6,6,sheetIndex,"交易合同起止日期");
	obj.S(7,6,sheetIndex,"委托人、受益人同意情况");
	obj.S(8,6,sheetIndex,"信息披露频率");
	   
    for(var i=1;i<=8;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中    
	    obj.SetCellAlign (i,5,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	      		            	    	    
    }
	 var rows=obj.GetRows(sheetIndex);
	 
	 //设置边框线
    for(var j=5;j<=rows;j++){
    	 for(i=1;i<=8;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}	

/**
 *ADD BY TSG 2008-01-30
 
 //S34信托投资公司关联交易风险及客户集中度情况表>>第一部分：信托投资公司关联交易风险状况(2)>> 三、信托财产与固有财产交易风险状况表 	 表头部分
 */
 function load_sheet2_2row(obj,sheetIndex,currows)
{
    obj.InsertRow(obj.GetRows(sheetIndex),4,sheetIndex);
    
   
    obj.Mergecells(1,currows+2,5,currows+2);
    obj.SetCellFontStyle(1,currows+2,sheetIndex,2);
    obj.SetCellAlign (1,currows+2,sheetIndex,32+1);
    obj.S(1,currows+2,sheetIndex,"   三、信托财产与固有财产交易风险状况表");
    
	obj.S(1,currows+3,sheetIndex,"序号");
	obj.Mergecells(1,currows+3,1,currows+4);

	obj.S(2,currows+3,sheetIndex,"A");
	obj.S(3,currows+3,sheetIndex,"B");
	obj.S(4,currows+3,sheetIndex,"C");
	obj.S(5,currows+3,sheetIndex,"D");
	obj.S(6,currows+3,sheetIndex,"E");
	obj.S(7,currows+3,sheetIndex,"F");
	obj.S(8,currows+3,sheetIndex,"G");
	obj.S(9,currows+3,sheetIndex,"H");
	
	obj.S(2,currows+4,sheetIndex,"信托项目(计划)名称");
	obj.S(3,currows+4,sheetIndex,"信托财产类别");
	obj.S(4,currows+4,sheetIndex,"固有财产类别");
	obj.S(5,currows+4,sheetIndex,"交易金额");
	obj.S(6,currows+4,sheetIndex,"交易种类");
	obj.S(7,currows+4,sheetIndex,"交易合同起止日期");
	obj.S(8,currows+4,sheetIndex,"委托人受益人同意情况");
	obj.S(9,currows+4,sheetIndex,"信息披露频率");
	   
    for(var i=1;i<=9;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,currows+5,sheetIndex,1);
			obj.SetCellSeparator(i,currows+5,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中    
	    obj.SetCellAlign (i,currows+3,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,currows+4,sheetIndex,32+4);
	      		            	    	    
    }
	 var rows=obj.GetRows(sheetIndex);
	 
	 //设置边框线
    for(var j=currows+3;j<=rows-2;j++){
    	 for(i=1;i<=9;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}	

  /**
   ADD BY TSG 2008-01-30
   
 * 格式化  S34信托投资公司关联交易风险及客户集中度情况表>>第一部分：信托投资公司关联交易风险状况(1) 

 */
 
 function formatStatementsForS342(obj,sheetIndex,currows){
 if(sheetIndex==1){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列） 
		    var lastrow=obj.GetRows(sheetIndex)-1;
		    for(i=2;i<=lastrow;i++){
		        if(i==currows+1)
		          obj.SetRowHeight(1,10,i,sheetIndex);
		        else  if(i==3 || i==6 || i==currows+4)
		          obj.SetRowHeight(1,35,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,30,i,sheetIndex);
		    }
		    
		    for(i=2;i<=lastrow;i++){
		      	obj.SetColWidth(1,108,i,sheetIndex);
		    }
		    
		    //设置外边框为粗线--左右
		    for(i=5;i<=currows;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(8,i,sheetIndex,2,3);
		    }
		    
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=8;i++){
		       obj.SetCellBorder(i,5,sheetIndex,1,3);
		       obj.SetCellBorder(i,currows,sheetIndex,3,3);
		    }
		    
		    		    //设置外边框为粗线--左右
		    for(i=currows+3;i<=lastrow-1;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(9,i,sheetIndex,2,3);
		    }
		    
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=9;i++){
		       obj.SetCellBorder(i,currows+3,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-1,sheetIndex,3,3);
		    }
		    
		    
		  

		    //设置字体自动换行
		    for(row=5;row<=6;row++){
		      for(col=1;col<=8;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    		    //设置字体自动换行
		    for(row=currows+3;row<=currows+4;row++){
		      for(col=1;col<=9;col++){
		        obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		     //设置无线
		    for(row=currows+1;row<=currows+2;row++){
		      for(col=1;col<=9;col++){
		        obj.SetCellBorder(col,row,sheetIndex,0,1);
		        obj.SetCellBorder(col,row,sheetIndex,1,1);
		        obj.SetCellBorder(col,row,sheetIndex,2,1);
		        obj.SetCellBorder(col,row,sheetIndex,3,1);
		      }
		    }
		    
		     for(col=1;col<=9;col++){
		        obj.SetCellBorder(col,lastrow,sheetIndex,0,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,1,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,2,1);
		        obj.SetCellBorder(col,lastrow,sheetIndex,3,1);
		      }
		    
		    //设置无线
		    for(row=5;row<=currows+2;row++){
		        obj.SetCellBorder(9,row,sheetIndex,0,1);
		       obj.SetCellBorder(9,row,sheetIndex,1,1);
		        obj.SetCellBorder(9,row,sheetIndex,2,1);
		        obj.SetCellBorder(9,row,sheetIndex,3,1);
		    }
		    
		    for(col=2;col<=8;col++){
		      obj.SetCellFontStyle(col,5,sheetIndex,2);
		    }
		    
		    for(col=2;col<=8;col++){
		      obj.SetCellFontStyle(col,currows+3,sheetIndex,2);
		    }
		    //obj.SetCellBackColor(9,7,sheetIndex,obj.FindColorIndex(0xc0c0c0,1)); 		  
	  }
} 

/**
 *ADD BY TSG 2008-01-31
 
 S34信托投资公司关联交易风险及客户集中度情况表>>第二部分：信托投资公司最大十家客户（含集团客户）融资集中度情况表	 表头部分
 */
 function load_sheet3row(obj,sheetIndex)
{
	obj.S(1,4,sheetIndex,"序号");
	obj.Mergecells(1,4,1,6);

	obj.S(2,4,sheetIndex,"排序");
	obj.Mergecells(2,4,2,6);
	
	obj.S(3,4,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"客户名称");
	obj.Mergecells(3,5,3,6);
	
	obj.S(4,4,sheetIndex,"B");
	obj.S(4,5,sheetIndex,"企业机构代码");
	obj.Mergecells(4,5,4,6);
	
	obj.S(5,4,sheetIndex,"C");
	obj.S(5,5,sheetIndex,"客户类型");
	obj.Mergecells(5,5,5,6);
	
	obj.S(6,4,sheetIndex,"D");
	obj.S(6,5,sheetIndex,"资金来源");
	obj.Mergecells(6,5,6,6);
	
    obj.S(7,4,sheetIndex,"E");
    obj.S(8,4,sheetIndex,"F");
    obj.S(9,4,sheetIndex,"G");
    obj.S(10,4,sheetIndex,"H");
    obj.S(11,4,sheetIndex,"I");
	obj.S(7,5,sheetIndex,"表内融资（资金融出）");
	obj.Mergecells(7,5,11,5);
	obj.S(7,6,sheetIndex,"贷款余额");
    obj.S(8,6,sheetIndex,"租赁余额");
    obj.S(9,6,sheetIndex,"投资余额");
    obj.S(10,6,sheetIndex,"买入返售资产余额");
    obj.S(11,6,sheetIndex,"其他融出余额");
	
	obj.S(12,4,sheetIndex,"J");
	obj.S(13,4,sheetIndex,"K");
	obj.S(12,5,sheetIndex,"表外融资");
	obj.Mergecells(12,5,13,5);
	obj.S(12,6,sheetIndex,"担保");
	obj.S(13,6,sheetIndex,"其他");
	
    obj.S(14,4,sheetIndex,"L");
	obj.S(14,5,sheetIndex,"融资合计金额");
	obj.Mergecells(14,5,14,6);
	
	obj.S(15,4,sheetIndex,"M");
	obj.S(15,5,sheetIndex,"固有和信托及代理融资合计金额");
	obj.Mergecells(15,5,15,6);
	
	obj.S(16,4,sheetIndex,"N");
    obj.S(17,4,sheetIndex,"O");
    obj.S(18,4,sheetIndex,"P");
    obj.S(19,4,sheetIndex,"Q");
    obj.S(20,4,sheetIndex,"R");  
	obj.S(16,5,sheetIndex,"风险状况");
	obj.Mergecells(16,5,20,5);
	obj.S(16,6,sheetIndex,"正常");
    obj.S(17,6,sheetIndex,"关注");
    obj.S(18,6,sheetIndex,"次级");
    obj.S(19,6,sheetIndex,"可疑");
    obj.S(20,6,sheetIndex,"损失"); 
	   
    for(var i=1;i<=20;i++){
	     if(i!=1 && i!=2&& i!=5){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中    
	    obj.SetCellAlign (i,4,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);	      		            	    	    
    }
	var rows=obj.GetRows(sheetIndex);
	 
	 //设置边框线
    for(var j=4;j<=rows;j++){
    	 for(i=1;i<=20;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}  

/**
   ADD BY TSG 2008-02-01
   
 * S35长期股权投资风险情况表
 */
function loadHeadOfFundsManageByMonthForS35(obj,statementsTitle,cols,hzDate,companyName,currency,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//设置列数 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S35");
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	if(sheetIndex==0){
	        //大抬头		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("黑体",1));
			obj.SetCellFontSize(1,1,sheetIndex,15);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,38,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//填报机构	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,16+1);
			obj.Mergecells(1,2,5,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //报表日期
		    obj.SetCellFont(8,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(8,2,sheetIndex,10.5); 
			obj.SetCellAlign(8,2,sheetIndex,16+4);
		    obj.Mergecells (8,2,16,2); 
			obj.S (8,2,sheetIndex,hzDate); 
			
			 //币种
		    obj.SetCellFont(17,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(17,2,sheetIndex,10.5); 
			obj.SetCellAlign(17,2,sheetIndex,16+4);
		    obj.Mergecells (17,2,19,2); 
			obj.S (17,2,sheetIndex,"币种："+currency);  
			
			//货币单位
			obj.SetCellFont(20,2,sheetIndex,obj.FindFontIndex ("楷体_GB2312",1));
		    obj.SetCellFontSize(20,2,sheetIndex,10.5); 
			obj.SetCellAlign(20,2,sheetIndex,16+4);
		    obj.Mergecells (20,2,24,2); 
			obj.S (20,2,sheetIndex," 货币单位：万元");  
			
		}
}

/**
 *ADD BY TSG 2008-02-01
 
 S35长期股权投资风险情况表
 */
 function load_s35row(obj,sheetIndex)
{
	obj.S(1,3,sheetIndex,"序号");
	obj.Mergecells(1,3,1,5);

	
	obj.S(2,3,sheetIndex,"A");
	obj.S(2,4,sheetIndex,"投资持股企业名称");
	obj.Mergecells(2,4,2,5);
	
	obj.S(3,3,sheetIndex,"B");
	obj.S(3,4,sheetIndex,"投资日期");
	obj.Mergecells(3,4,3,5);
	
	obj.S(4,3,sheetIndex,"C");
	obj.S(4,4,sheetIndex,"初始投资金额");
	obj.Mergecells(4,4,4,5);
	
	obj.S(5,3,sheetIndex,"D");
	obj.S(6,3,sheetIndex,"E");
	obj.S(7,3,sheetIndex,"F");
	obj.S(8,3,sheetIndex,"G");
	obj.S(9,3,sheetIndex,"H");
	obj.S(10,3,sheetIndex,"I");
	obj.S(11,3,sheetIndex,"J");	
	obj.S(5,4,sheetIndex,"现投资金额");
	obj.Mergecells(5,4,11,4);
	obj.S(5,5,sheetIndex,"银行类金融机构");
	obj.S(6,5,sheetIndex,"证券公司");
	obj.S(7,5,sheetIndex,"基金公司");
	obj.S(8,5,sheetIndex,"保险公司");
	obj.S(9,5,sheetIndex,"期货公司");
	obj.S(10,5,sheetIndex,"非银行金融机构");
	obj.S(11,5,sheetIndex,"其他");
	
	obj.S(12,3,sheetIndex,"K");
	obj.S(12,4,sheetIndex,"持股比例");
	obj.Mergecells(12,4,12,5);
	
	
	obj.S(13,3,sheetIndex,"L");
	obj.S(14,3,sheetIndex,"M");
	obj.S(13,4,sheetIndex,"投资收益");
	obj.Mergecells(13,4,14,4);
	obj.S(13,5,sheetIndex,"上年度数");
	obj.S(14,5,sheetIndex,"本年累计数");
	
	obj.S(15,3,sheetIndex,"N");
	obj.S(16,3,sheetIndex,"O");
	obj.S(15,4,sheetIndex,"持股企业净资产额");
	obj.Mergecells(15,4,16,4);
	obj.S(15,5,sheetIndex,"上年末数");
	obj.S(16,5,sheetIndex,"上期数");
	
	obj.S(17,3,sheetIndex,"P");
	obj.S(18,3,sheetIndex,"Q");
	obj.S(17,4,sheetIndex,"持股企业净利润");
	obj.Mergecells(17,4,18,4);
	obj.S(17,5,sheetIndex,"上年末数");
	obj.S(18,5,sheetIndex,"上期数");
	
	
	obj.S(19,3,sheetIndex,"R");
    obj.S(20,3,sheetIndex,"S");
    obj.S(21,3,sheetIndex,"T");
    obj.S(22,3,sheetIndex,"U");
    obj.S(23,3,sheetIndex,"V");  
	obj.S(19,4,sheetIndex,"投资风险状况");
	obj.Mergecells(19,4,23,4);
	obj.S(19,5,sheetIndex,"正常");
    obj.S(20,5,sheetIndex,"关注");
    obj.S(21,5,sheetIndex,"次级");
    obj.S(22,5,sheetIndex,"可疑");
    obj.S(23,5,sheetIndex,"损失");  

	obj.S(24,3,sheetIndex,"W");
	obj.S(24,4,sheetIndex,"减值准备");
	obj.Mergecells(24,4,24,5);
	   
    for(var i=1;i<=24;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,6,sheetIndex,1);
			obj.SetCellSeparator(i,6,sheetIndex,2);
	     } 
	     
	     //设置数据抬头水平垂直居中    
	    obj.SetCellAlign (i,3,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);	      		            	    	    
    }
	var rows=obj.GetRows(sheetIndex);
	 
	 //设置边框线
    for(var j=3;j<=rows;j++){
    	 for(i=1;i<=24;i++){	
			obj.SetCellBorder(i, j, sheetIndex, 0, 2);
			obj.SetCellBorder(i, j, sheetIndex, 1, 2);
			obj.SetCellBorder(i, j, sheetIndex, 2, 2);
			obj.SetCellBorder(i, j, sheetIndex, 3, 2); 	
	     }
    }	

}  	

/**
   ADD BY TSG 2008-02-01
   
 * S35长期股权投资风险情况表
 */
 function loadEndOfFundsManagexByMonthForS35(obj,cols,rows,op_name,sheetIndex){  
    if(sheetIndex==0){  
        var allRows=obj.GetRows(0);
        obj.InsertRow(obj.GetRows(0),2,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"填表人：" + op_name); 
		obj.SetCellAlign (8,allRows,sheetIndex,32+1);
		obj.Mergecells (8,allRows,17,rows);
		obj.S (8,allRows,sheetIndex,"复核人：");
		obj.SetCellAlign (19,allRows,sheetIndex,32+1);
		obj.Mergecells (19,allRows,24,rows);
		obj.S (19,allRows,sheetIndex,"负责人：");
		obj.S (1,allRows+1,sheetIndex,"注：[G]、[H]投资收益是指以成本法或权益法核算的收入。成本法计算股息收入，权益法计算利润。");
		obj.Mergecells (1,allRows+1,24,allRows+1);
		
    }
  }
  
  /**
   ADD BY TSG 2008-02-01
   
 * S35长期股权投资风险情况表

 */
 
 function formatStatementsForS35(obj,sheetIndex){
	  if(sheetIndex==0){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //设置第一列宽（序号列） 
		    var lastrow=obj.GetRows(sheetIndex)-1;
		    for(i=2;i<=lastrow;i++){
		        if(i==3 || i==4 || i==5)
		          obj.SetRowHeight(1,30,i,sheetIndex);
		        else  if(i==5)
		          obj.SetRowHeight(1,35,i,sheetIndex);
		        else   
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    for(i=5;i<=24;i++){
		      	obj.SetColWidth(1,60,i,sheetIndex);
		    }
		    
		
		    //设置外边框为粗线--左右
		    for(i=3;i<=lastrow-2;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(24,i,sheetIndex,2,3);
		    }
		    
		    //设置外边框为粗线--上下
		    for(i=1;i<=24;i++){
		       obj.SetCellBorder(i,3,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-2,sheetIndex,3,3);
		    }
		  

		    //设置字体自动换行
		    for(row=4;row<=5;row++){
		      for(col=1;col<=24;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //设置无线
		    for(row=lastrow-1;row<=lastrow;row++){
		      for(col=1;col<=24;col++){
		        obj.SetCellBorder(col,row,sheetIndex,0,1);
		        obj.SetCellBorder(col,row,sheetIndex,1,1);
		        obj.SetCellBorder(col,row,sheetIndex,2,1);
		        obj.SetCellBorder(col,row,sheetIndex,3,1);
		      }
		    }
		    
		    for(col=2;col<=24;col++){
		      obj.SetCellFontStyle(col,3,sheetIndex,2);
		    }
		    
		    for(row=6;row<=lastrow-2;row++){
		      obj.SetCellAlign(2,row,sheetIndex,32+1);
		    }  
		   		  
	  }
}	