//Title  : Useful Javascript functions
//Desp   :
//Author : Ӧ��
//Date   : 2006-1-20

//��ʼ����ͷ
function loadONE(obj,name1,col,name2,name3)
{
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//��������
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("����",1));
	obj.SetCellFontSize (1,1,0,30);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,75,1,0);
	obj.S (1,1,0,name1);

	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("����",1));
	obj.SetCellAlign (1,2,0,4);
	obj.Mergecells (1,2,col,2);
	obj.SetRowHeight(1,25,2,0);
	obj.S (1,2,0,name2);

	obj.SetCellFont( 1,3,0,obj.FindFontIndex ("����",1));

	obj.Mergecells (1,3,(col-2),3);
	obj.SetRowHeight(1,25,3,0);
	obj.S (1,3,0,name3);
	obj.SetCellAlign ((col-1),3,0,2);
	obj.S ((col-1),3,0,'Ԫ');
	
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
//��ʼ����ͷ
function loadTWO(obj,name1,col)
{
	obj.ShowHScroll(0,0);
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//��������
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("����",1));
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
	obj.SetCols(col,0);//��������
	obj.WorkbookReadonly = true;
    
	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("����_GB2312",1));
	obj.SetCellFontSize (1,1,0,20);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,75,1,0);
	obj.S (1,1,0,name1);

	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("����_GB2312",1));
	obj.SetCellAlign (1,2,0,4);
	obj.Mergecells (1,2,col,2);
	obj.SetRowHeight(1,25,2,0);
	obj.S (1,2,0,name2);

	obj.SetCellFont( 1,3,0,obj.FindFontIndex ("����_GB2312",1));

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
	obj.SetCols(col,0);//��������
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("����",1));
	obj.SetCellFontSize (1,1,0,20);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,55,1,0);
	obj.S (1,1,0,name1);
	
	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("����",1));
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
	obj.SetCols(col,0);//��������
	obj.WorkbookReadonly = true;

	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("����",1));
	obj.SetCellFontSize (1,1,0,20);
	obj.SetCellAlign (1,1,0,4);
	obj.Mergecells (1,1,col,1);
	obj.SetRowHeight(1,55,1,0);
	obj.S (1,1,0,title);//����
	
	
	obj.SetCellFont( 1,2,0,obj.FindFontIndex ("����",1));
	obj.SetCellFontSize (1,2,0,10);
	obj.SetCellAlign (1,2,0,4);
	obj.Mergecells (1,2,col-3,1);
	obj.S (1,2,0,name2);//��Ʒ��
	obj.Mergecells (col-2,2,col,1);

	obj.SetCellFont( 2,2,0,obj.FindFontIndex ("����",1));
	obj.SetCellFontSize (2,2,0,10);
	obj.SetCellAlign (2,2,0,4);
	obj.S (2,2,0,name3);//�ڼ�
	
	obj.SetCellFont( 1,3,0,obj.FindFontIndex ("����",1));//��Ŀ
	obj.Mergecells (1,3,col-3,3);
	obj.SetCellFontSize (1,3,0,15);
	obj.SetRowHeight(1,35,3,0);
	obj.S (1,3,0,name4);
	obj.Mergecells (col-2,3,col,1);

	obj.SetCellFont(2,3,0,obj.FindFontIndex ("����",1));
	obj.SetCellFontSize (2,3,0,10);
	obj.SetCellAlign (2,3,0,4);
	obj.S (2,3,0,name5);//ҳ��
		
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


//���ݱ�ͷ��ͬ���������

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
	if(obj.Login( "�����±������ϵͳ","" ,"13040468", "3600-1505-7761-0005" ) == 0 )	
	{
		rp_alert( "ע��CELL���ʧ��!");
		return false;
	}
}

function setPrint(obj,flag)
{	

	//���ô�ӡ��ֽ����,ʮ��֮һ����	
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
	obj.PrintSetAlign(1,0);   //���ô�ӡ��ʽΪˮƽ����
	if(flag==1)		
		PrintPreview(obj);
	else
		PrintReport(obj);	
}

//��ӡԤ��
function PrintPreview(obj)
{
	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintPreview( 0, 0);	//��ӡԤ��
}

//��ӡ
function PrintReport(obj)
{	
	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintSheet (0, 0);	//��ӡ
}

function rp_alert(errinfo)
{
	alert("ϵͳ��ʾ����������\n\n" + errinfo + "\n\n");
}	


//����Excel
function ExportExcel(obj)
{	
	login(obj);
	if(obj.ExportExcelDlg()==1)
		rp_alert("����Excel�ļ��ɹ���");
}
//����Cell
function ExportCell(obj,sheet)
{		
	login(obj);	
	if(obj.SaveSheet(sheet)==1)
	{		
		obj.CloseFile();
		rp_alert("����Cell�ļ��ɹ���");
	}
}
//����TXT
function ExportTXT(obj)
{	
	if(obj.ExportTextDlg()==1)
		rp_alert("�����ļ��ɹ���");		
}
//����PDF
function ExportPDF(obj,fileName,sheet)
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);
	if(obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet))==1)
		dl_alert(fileName);
	else
		rp_alert("����ʧ�ܣ�");		
}
function dl_alert(fileName)
{
	alert("�����ļ��ɹ���\n\n�ļ�����"+fileName);

}

//�������,������Ҫ�ϲ����У����ݿ�ʼ�У������ʽpama��ʱû��
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
 * �ʲ���ծ��չ����ͷ
 */
function loadThreeByZcfzbInfo(obj,col)
{
	if(document.theform.DCellWeb1=='[object]')
	{if(screen.height==600)
		document.theform.DCellWeb1.height ="330";
	}
	//obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//��������
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
   
 * �ʲ���ծ�������Ϣ�����£���ͷ����
 */
function loadHeadOfBalanceByMonth(obj,tableName,col,hzDate,companyName) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,0);
	obj.SetCols(col,0);//�������� 
	obj.WorkbookReadonly = true;
	obj.SetCellFont(1,1,0,obj.FindFontIndex ("����_GB2312",1));
	obj.SetCellFontSize(1,1,0,16);
	obj.SetCellFontStyle(1,1,0,2);
	obj.SetCellAlign (1,1,0,32+4);
	obj.Mergecells(1,1,col-1,1); 
	obj.SetRowHeight(1,60,1,0);
	obj.S (1,1,0,tableName);
	
	obj.SetCellFont(1,2,0,obj.FindFontIndex ("����_GB2312",1)); 
	obj.SetCellFontSize(1,2,0,12);
    obj.SetCellFontStyle(1,2,0,2); 
	obj.SetCellAlign(1,2,0,16+1);
	obj.SetRowHeight(1,30,2,0);
	obj.Mergecells (1,2,4,2);
	obj.S (1,2,0,"��һ���֣�������Ŀ�ʲ���ծ���ܱ�");     
	
	obj.SetCellFontSize(1,3,0,10); 
	obj.SetRowHeight(1,30,3,0);
	obj.SetCellAlign(1,3,0,16+1);
	obj.Mergecells(1,3,2,3);
	obj.S(1,3,0,companyName);     //���Ƶ�λ

    obj.SetCellFontSize(3,3,0,10); 
	obj.SetCellAlign(3,3,0,16+1);
    obj.Mergecells (3,3,4,3);
	obj.S (3,3,0,hzDate);     //��������
	
	
	obj.SetCellFontSize(5,3,0,10); 
	obj.SetCellAlign (5,3,0,16+1);
	obj.S (5,3,0,"���ҵ�λ����Ԫ"); 
				
}


/**
   ADD BY TSG 2008-01-21
   
 * �ʲ�����/��ע��Ϣ�����£���ͷ���� 
 */
function loadHeadOfProfitByMonth(obj,tableName,col,hzDate,companyName) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,1);
	obj.SetCols(col,1);//�������� 
	obj.WorkbookReadonly = true;
	obj.SetCellFont(1,1,1,obj.FindFontIndex ("����_GB2312",1));
	obj.SetCellFontSize(1,1,1,16);
	obj.SetCellFontStyle(1,1,1,2);
	obj.SetCellAlign (1,1,1,32+4);
	obj.Mergecells(1,1,col-1,1); 
	obj.SetRowHeight(1,60,1,1);
	obj.S (1,1,1,tableName);
	
	obj.SetCellFont(1,2,1,obj.FindFontIndex ("����_GB2312",1)); 
	obj.SetCellFontSize(1,2,1,12);
    obj.SetCellFontStyle(1,2,1,2); 
	obj.SetCellAlign(1,2,1,16+1);
	obj.SetRowHeight(1,30,2,1);
	obj.Mergecells (1,2,4,2);
	obj.S (1,2,1,"�ڶ����֣�������Ŀ������ܱ�");     
	
	obj.SetCellFontSize(1,3,1,10); 
	obj.SetRowHeight(1,30,3,1);
	obj.SetCellAlign(1,3,1,16+1);
	obj.Mergecells(1,3,2,3);
	obj.S(1,3,1,companyName);     //���Ƶ�λ

    obj.SetCellFontSize(3,3,1,10); 
	obj.SetCellAlign(3,3,1,16+1);
    obj.Mergecells (3,3,4,3);
	obj.S (3,3,1,hzDate);     //��������
	
	
	obj.SetCellFontSize(5,3,1,10); 
	obj.SetCellAlign (5,3,1,16+1);
	obj.S (5,3,1,"���ҵ�λ����Ԫ");				
}
/**
   ADD BY TSG 2008-01-16
   
 * �ʲ���ծ�������Ϣ�����£���β����
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
	obj.S (2,rows,0,"����ˣ�" + op_name); 

	obj.SetCellAlign (4,rows,0,1);
	obj.SetCellFontStyle(4,rows,0,0);
	obj.S (4,rows,0,"�����ˣ�" + check_man);

	obj.SetCellAlign (5,rows,0,1);
	obj.SetCellFontStyle(5,rows,0,0);
	obj.S (5,rows,0,"�����ˣ�" + zrr); 
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
 * �ʲ���ծ���ͷ����
 */
function loadThreeByZcfzb(obj,name1,col,name2,name3,name4,name5)
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,0);
	obj.SetCols(col,0);//��������
	obj.WorkbookReadonly = true;
	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("����_GB2312",1));
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
	obj.S (1,2,0,name3);     //���Ƶ�λ
	obj.Mergecells (4,2,5,2);
	obj.S (4,2,0,name2);     //����
	obj.SetCellAlign (6,2,0,2);
	obj.S (6,2,0,"�����Ŀ01��");
	obj.SetCellFontSize (1,3,0,9);
	obj.SetCellFontSize (4,3,0,9);
	obj.SetCellFontSize (6,3,0,9);
	obj.SetRowHeight(1,38,3,0);
	obj.Mergecells (1,3,3,3);
	obj.SetCellAlign (1,3,0,32)
	obj.S (1,3,0,name4);     //������Ŀ����
	obj.Mergecells (4,3,5,3);
	obj.SetCellAlign (4,3,0,32)
	obj.S (4,3,0,name5);     //��������
	obj.SetCellAlign (6,3,0,32+2);
	obj.S (6,3,0,"��λ��Ԫ");
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
 * ������ͷ����
 */
function loadThreeBylrb(obj,name1,col,name2,name3,name4,name5)
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(0,0);
	obj.SetCols(col,0);//��������
	obj.WorkbookReadonly = true;
	obj.SetCellFont( 1,1,0,obj.FindFontIndex ("����_GB2312",1));
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
	obj.S (3,2,0,"�����Ŀ02��"); 
	obj.SetCellFontSize (1,3,0,9);
	obj.SetCellFontSize (2,3,0,9);
	obj.SetCellFontSize (3,3,0,9);
	obj.SetRowHeight(1,38,3,0);
	obj.S (1,3,0,name4);
	obj.SetCellAlign (1,3,0,32);
	obj.SetCellAlign (2,3,0,32+2);
	obj.S (2,3,0,name5);
	obj.SetCellAlign (3,3,0,32+2);
	obj.S (3,3,0,"��λ��Ԫ"); 
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
   ��SHEET ����
 * ������ͷ����
 */
function loadThreeBylrbNew(obj,name1,col,name2,name3,name4,name5)
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.ShowSheetLabel(1,1);
	obj.SetCols(col,1);//��������
	obj.WorkbookReadonly = true;
	obj.SetCellFont( 1,1,1,obj.FindFontIndex ("����_GB2312",1));
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
	obj.S (3,2,1,"�����Ŀ02��"); 
	obj.SetCellFontSize (1,3,1,9);
	obj.SetCellFontSize (2,3,1,9);
	obj.SetCellFontSize (3,3,1,9);
	obj.SetRowHeight(1,38,3,1);
	obj.S (1,3,1,name4);
	obj.SetCellAlign (1,3,1,32);
	obj.SetCellAlign (2,3,1,32+2);
	obj.S (2,3,1,name5);
	obj.SetCellAlign (3,3,1,32+2);
	obj.S (3,3,1,"��λ��Ԫ"); 
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
 * �����·���ʾ����:   ����6��
 * name1�ǻ������ name2�Ǹ��� name3���Ʊ�
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
	obj.S (1,rows,0,"������ܣ�" + name1);
	obj.Mergecells (3,rows,4,rows);
	obj.SetCellAlign (3,rows,0,32+4);
	obj.S (3,rows,0,"���ˣ�" + name2);
	obj.Mergecells (5,rows,6,rows)
	obj.SetCellAlign (5,rows,0,32+2);
	obj.S (5,rows,0,"�Ʊ�" + name3); 
}

/**
  add by tsg 
 * �����·���ʾ����:   ����6��
 * name1�ǻ������ name2�Ǹ��� name3���Ʊ�
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
	obj.S (1,rows,0,"������ܣ�" + name1);
	obj.Mergecells (3,rows,4,rows);
	obj.SetCellAlign (3,rows,0,32+4);
	obj.S (3,rows,0,"���ˣ�" + name2);
	obj.Mergecells (5,rows,6,rows)
	obj.SetCellAlign (5,rows,0,32+2);
	obj.S (5,rows,0,"�Ʊ�" + name3); 
}



/**
 * �����·���ʾ����:   ����3��
 * name1�ǻ������ name2�Ǹ��� name3���Ʊ�
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
	obj.S (1,rows,0,"������ܣ�" + name1);
	obj.SetCellAlign (2,rows,0,32+4);
	obj.S (2,rows,0,"���ˣ�" + name2);
	obj.SetCellAlign (3,rows,0,32+2);
	obj.S (3,rows,0,"�Ʊ�" + name3); 
}
/**

add  by tsg 2007-12-12
��SHEET ����
 * �����·���ʾ����:   ����3��
 * name1�ǻ������ name2�Ǹ��� name3���Ʊ�
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
	obj.S (1,rows,1,"������ܣ�" + name1);
	obj.SetCellAlign (2,rows,1,32+4);
	obj.S (2,rows,1,"���ˣ�" + name2);
	obj.SetCellAlign (3,rows,1,32+2);
	obj.S (3,rows,1,"�Ʊ�" + name3); 
}

/**
 *	�����
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
 *	�ʲ���ծ��
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

   
	//���ô�ӡ��ֽ����,ʮ��֮һ����	
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
	obj.PrintSetAlign(1,0);   //���ô�ӡ��ʽΪˮƽ���� 
	if(flag==1)		
		PrintPreview1(obj,sheetIndex);
	else
		PrintReport1(obj,sheetIndex);	
}

//��ӡԤ�� 
function PrintPreview1(obj,sheetIndex) 
{

	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintPreview( 0, sheetIndex);	//��ӡԤ��
}

//��ӡ
function PrintReport1(obj,sheetIndex)
{	
	login(obj);	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(80,80,120,80);
	obj.PrintSheet (0, sheetIndex);	//��ӡ
}

//����all sheet PDF
function ExportPDFOfAllSheets(obj,fileName,sheet) 
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);		
	if(obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet))==1)
		dl_alert(fileName);
	else
		rp_alert("����ʧ�ܣ�");		
}


/**
ADD BY TSG 2008-01-22
ʵ�ֱ���ռ������FRAME

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
   
 * S32���й�˾�������вƲ�״��ͳ�Ʊ�S32����ͷ����
 */
function loadHeadOfFundsManageByMonth(obj,statementsTitle,cols,hzDate,companyName,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//�������� 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S32��һ����");
	}else if(sheetIndex==1){
	 obj.SetSheetLabel(sheetIndex,"S32�ڶ�����"); 
	}else if(sheetIndex==2){
	 obj.SetSheetLabel(sheetIndex,"S32��������"); 
	}else if(sheetIndex==3){
	 obj.SetSheetLabel(sheetIndex,"S32�������һ"); 
	}else if(sheetIndex==4){
	 obj.SetSheetLabel(sheetIndex,"S32������϶�"); 
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	
	if(sheetIndex==4){
		    obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(1,1,sheetIndex,12); 
			obj.SetCellAlign(8,2,sheetIndex,32+1);
		    obj.Mergecells (1,1,2,1);
			obj.S (1,1,sheetIndex,"������϶��������ʽ�������ҵ�������"); 
	  
	}else{
		//��̧ͷ		
		obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("����",1));
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
		
		//�����	
		obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
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
		     //��������
		    obj.SetCellFont(8,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(8,2,sheetIndex,12); 
			obj.SetCellAlign(8,2,sheetIndex,16+4);
		    obj.Mergecells (8,2,22,2);
			obj.S (8,2,sheetIndex,hzDate);  
	
		    obj.SetCellFont(23,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(23,2,sheetIndex,12); 
			obj.SetCellAlign (23,2,sheetIndex,16+4);
			obj.Mergecells (23,2,44,2);
			obj.S (23,2,sheetIndex,"���ҵ�λ����Ԫ"); 
			
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("����",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,14);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,13,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"��һ���֣����Ϲ����ʽ�������Դ���������ϸ��");  
		}else if(sheetIndex==1){
		   obj.Mergecells (8,2,31,2);
		   
		  	obj.SetCellFont(45,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(45,2,sheetIndex,12); 
			obj.SetCellAlign (45,2,sheetIndex,16+1);
			obj.Mergecells (45,2,46,2);
			obj.S (45,2,sheetIndex,"���ҵ�λ:"); 
			
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("����",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,14);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,16,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"�ڶ����֣���һ�����ʽ�������Դ���������ϸ��");  
		}else if(sheetIndex==2){		
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("����",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,14);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,22,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"�������֣�����Ʋ����������ϸ��  ");  
		}else if(sheetIndex==3){
		     //��������
		    obj.SetCellFont(3,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(3,2,sheetIndex,12); 
			obj.SetCellAlign(3,2,sheetIndex,16+4);
		    obj.Mergecells (3,2,6,2);
			obj.S (3,2,sheetIndex,hzDate);  
	
		    obj.SetCellFont(8,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(8,2,sheetIndex,12); 
			obj.SetCellAlign (8,2,sheetIndex,16+4);
			obj.Mergecells (8,2,10,2);
			obj.S (8,2,sheetIndex,"���ҵ�λ����Ԫ"); 
			
			obj.SetCellFont(1,3,sheetIndex,obj.FindFontIndex ("����",1)); 
			obj.SetCellFontSize(1,3,sheetIndex,12);
		    obj.SetCellFontStyle(1,3,sheetIndex,2); 
			obj.SetCellAlign(1,3,sheetIndex,16+1);
			obj.SetRowHeight(1,35,3,sheetIndex);
			obj.Mergecells (1,3,2,3);
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.S (1,3,sheetIndex,"�������һ�������ʽ�Ͷ���м�֤ȯ���������");  
			
		}
	}				
}

/**
   ADD BY TSG 2008-01-23
   
 * S32���й�˾�������вƲ�״��ͳ�Ʊ�S32����β����
 */
 function loadEndOfFundsManagexByMonth(obj,cols,rows,op_name,sheetIndex){
    
     
    if(sheetIndex==0){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,7,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"����ˣ�" + op_name); 
	
		obj.SetCellAlign (8,rows,sheetIndex,32+1);
		obj.Mergecells (8,rows,13,rows);
		obj.S (8,rows,0,"�����ˣ�");
	
		obj.SetCellAlign (14,rows,sheetIndex,32+4);
		obj.Mergecells (14,rows,44,rows);
		obj.S (14,rows,sheetIndex,"�����ˣ�"); 
		
	    obj.SetCellAlign (45,rows,sheetIndex,32+1);
		obj.Mergecells (45,rows,48,rows);
		obj.S (45,rows,sheetIndex,"�����ˣ�"); 
    }else if(sheetIndex==1){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,5,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"����ˣ�" + op_name); 
	
		obj.SetCellAlign (7,rows,sheetIndex,32+1);
		obj.Mergecells (7,rows,33,rows);
		obj.S (7,rows,sheetIndex,"�����ˣ�");
	
		obj.SetCellAlign (34,rows,sheetIndex,32+1);
		obj.Mergecells (34,rows,47,rows);
		obj.S (34,rows,sheetIndex,"�����ˣ�"); 
		
    }else if(sheetIndex==2){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,8,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"����ˣ�" + op_name); 
		
		obj.Mergecells (9,rows,12,rows);
    }else if(sheetIndex==3){
        obj.InsertRow(rows,1,sheetIndex);
    	obj.SetCellFontSize (1,rows,sheetIndex,10);
		obj.Mergecells (1,rows,2,rows);
		obj.SetCellAlign (1,rows,sheetIndex,32+1);
		obj.S (1,rows,sheetIndex,"����ˣ�" + op_name); 
	
		obj.SetCellAlign (3,rows,sheetIndex,32+1);
		obj.Mergecells (3,rows,4,rows);
		obj.S (3,rows,sheetIndex,"�����ˣ�");
	
		obj.SetCellAlign (5,rows,sheetIndex,32+1);
		obj.Mergecells (5,rows,6,rows);
		obj.S (5,rows,sheetIndex,"�����ˣ�"); 
		
    }else if(sheetIndex==4){
        obj.InsertRow(obj.GetRows(sheetIndex),2,sheetIndex);
    	obj.SetCellFontSize (1,36,sheetIndex,10);
		obj.Mergecells (1,36,2,36);
		obj.SetCellAlign (1,36,sheetIndex,32+1);
		obj.S (1,36,sheetIndex,"����ˣ�" + op_name); 
	
		obj.SetCellAlign (3,36,sheetIndex,32+1);
		obj.S (3,36,sheetIndex,"�����ˣ�");
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
   
 * ��ʽ��S32���й�˾�������вƲ�״��ͳ�Ʊ�

 */
 
 function formatStatements(obj,sheetIndex){

	  if(sheetIndex==0){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У� 
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
		    
		    //������߿�Ϊ����
		    for(i=4;i<=35;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(48,i,sheetIndex,2,3);
		    }
		    for(i=1;i<=48;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,23,sheetIndex,3,3);
		       obj.SetCellBorder(i,35,sheetIndex,3,3);
		    }
		    
		    //���������Զ�����
		    for(row=5;row<=7;row++){
		      for(col=1;col<=48;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		   //�ϼƴ��������ʾ
	       obj.SetCellFontStyle(2,23,sheetIndex,2);
	       obj.SetCellAlign(2,23,sheetIndex,32+4);
	  }else if(sheetIndex==1){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У�
		    //�����и�
		    for(i=3;i<=36;i++){
		        if(i==7)
		          obj.SetRowHeight(1,50,i,sheetIndex);
		        else if(i==3)
		      	  obj.SetRowHeight(1,38,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //�����п�
		    for(i=3;i<=50;i++){
		      	obj.SetColWidth(1,70,i,sheetIndex);   
		    }
		    
		    //������߿�Ϊ����
		    for(i=4;i<=35;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(50,i,sheetIndex,2,3);
		    }
		    for(i=1;i<=50;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,23,sheetIndex,3,3);
		       obj.SetCellBorder(i,35,sheetIndex,3,3);
		    }
		    
		    //���������Զ�����
		    for(row=5;row<=7;row++){
		      for(col=1;col<=50;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    //�ϼƴ��������ʾ
	       obj.SetCellFontStyle(2,23,sheetIndex,2);
	       obj.SetCellAlign(2,23,sheetIndex,32+4);
	  }else if(sheetIndex==2){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У�
		    //�����и�
		    for(i=3;i<=36;i++){
	            if(i==3)
		      	  obj.SetRowHeight(1,38,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //�����п�
		    for(i=3;i<=26;i++){
		      	obj.SetColWidth(1,60,i,sheetIndex);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=4;i<=29;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(26,i,sheetIndex,2,3);
		    }
		    //������߿�Ϊ����--����
		    for(i=1;i<=26;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,29,sheetIndex,3,3);
		       obj.SetCellBorder(i,23,sheetIndex,3,3);
		    }
		    
		    //���������Զ�����
		    for(row=5;row<=7;row++){
		      for(col=1;col<=50;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //�ϼƴ��������ʾ
	       obj.SetCellFontStyle(2,23,sheetIndex,2);
	       obj.SetCellAlign(2,23,sheetIndex,32+4);
	  }else if(sheetIndex==3){
	  
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У�
		    
		    //�����и�
		    for(i=3;i<=68;i++){
	            if(i==3)
		      	  obj.SetRowHeight(1,38,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //�����п�
		    for(i=3;i<=10;i++){
		      	obj.SetColWidth(1,90,i,sheetIndex);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=4;i<=24;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(10,i,sheetIndex,2,3);
		    }
		    //������߿�Ϊ����--����
		    for(i=1;i<=30;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,30,sheetIndex,3,3);
		    }
		    
		     //��ע1������߿�Ϊ����--����
		    for(i=35;i<=49;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(4,i,sheetIndex,2,3);
		    }
		    //��ע1������߿�Ϊ����--����
		    for(i=1;i<=4;i++){
		       obj.SetCellBorder(i,35,sheetIndex,1,3);
		       obj.SetCellBorder(i,49,sheetIndex,3,3);
		    }
		    
		     //��ע2������߿�Ϊ����--����
		    for(i=52;i<=67;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(7,i,sheetIndex,2,3);
		    }
		    //��ע1������߿�Ϊ����--����
		    for(i=1;i<=7;i++){
		       obj.SetCellBorder(i,52,sheetIndex,1,3);
		       obj.SetCellBorder(i,67,sheetIndex,3,3);
		    }
		    
		    //���������Զ�����
		    for(row=5;row<=6;row++){
		      for(col=1;col<=10;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��ע1���������Զ�����
		    for(row=35;row<=36;row++){
		      for(col=1;col<=4;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��ע2���������Զ�����
		    for(row=53;row<=54;row++){
		      for(col=1;col<=7;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //���ñ���ɫ
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
		    
		     //���ò���ʾ�߿�
		    for(i=31;i<=34;i++){
		       for(j=1;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	
		    
		    //���ò���ʾ�߿�
		    for(i=50;i<=51;i++){
		       for(j=1;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }
		    
		    //���ò���ʾ�߿�
		    for(i=31;i<=49;i++){
		       for(j=5;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	 
		    
		    //���ò���ʾ�߿�
		    for(i=51;i<=67;i++){
		       for(j=8;j<=10;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	
		    //����������ɫ		    
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
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У�
		    
		    //�����и�
		    for(i=1;i<=36;i++){
	            if(i==3)
		      	  obj.SetRowHeight(1,31,i,sheetIndex);
		      	else if(i==30)
		      	  obj.SetRowHeight(1,31,i,sheetIndex);
		        else  
		      	  obj.SetRowHeight(1,26,i,sheetIndex);
		    }
		    //�����п�
		    for(i=3;i<=10;i++){
		      	obj.SetColWidth(1,90,i,sheetIndex);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=2;i<=26;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(5,i,sheetIndex,2,3);
		    }
		    //������߿�Ϊ����--����
		    for(i=1;i<=5;i++){
		       obj.SetCellBorder(i,2,sheetIndex,1,3);
		       obj.SetCellBorder(i,26,sheetIndex,3,3);
		    }
		    
		     //��ע1������߿�Ϊ����--����
		    for(i=29;i<=34;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(8,i,sheetIndex,2,3);
		    }
		    //��ע1������߿�Ϊ����--����
		    for(i=1;i<=8;i++){
		       obj.SetCellBorder(i,29,sheetIndex,1,3);
		       obj.SetCellBorder(i,34,sheetIndex,3,3);
		    }
		    
		  
		    
		    //���������Զ�����
		    for(row=2;row<=3;row++){
		      for(col=1;col<=5;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��ע1���������Զ�����
		    for(row=29;row<=30;row++){
		      for(col=1;col<=8;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    } 
		    
		     //���ò���ʾ�߿�
		    for(i=1;i<=26;i++){
		       for(j=6;j<=8;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }	
		    
		    //���ò���ʾ�߿�
		    for(i=27;i<=28;i++){
		       for(j=1;j<=8;j++){
		         obj.SetCellBorder(j,i,sheetIndex,0,1);
		         obj.SetCellBorder(j,i,sheetIndex,1,1);
		         obj.SetCellBorder(j,i,sheetIndex,2,1);
		         obj.SetCellBorder(j,i,sheetIndex,3,1);
		       }
		    }
		    
		    //���ò���ʾ�߿�
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
		 
		    //����������ɫ		    
		    obj.SetCellTextColor(3,36,sheetIndex,obj.FindColorIndex(0x000000,1));  
		    
			   
		     
	  }
 }
 
 /**
 *ADD BY TSG 2008-01-24
 S32�����һ���ֱ�ͷ����
 */
 function load_setrow(obj,sheetIndex)
{
  
	obj.S(1,4,sheetIndex,"���");
	obj.Mergecells(1,4,1,7);
	
	obj.S(2,4,sheetIndex,"A");
	obj.S(2,5,sheetIndex,"���޽ṹ");
	obj.Mergecells(2,5,2,7);
	
    obj.S(3,4,sheetIndex,"B");
	obj.S(3,5,sheetIndex,"����");
	obj.Mergecells(3,5,3,7);

    obj.S(4,4,sheetIndex,"C");
	obj.S(4,5,sheetIndex,"�ƽ�أ�������");
	obj.Mergecells(4,5,5,6); 	
	obj.S(5,4,sheetIndex,"D");
	obj.S(5,7,sheetIndex,"���");
	obj.S(4,7,sheetIndex,"����");
	
	obj.S(6,4,sheetIndex,"E");
	obj.S(7,4,sheetIndex,"F");
	obj.S(8,4,sheetIndex,"G");
	obj.S(9,4,sheetIndex,"H");
	obj.S(6,5,sheetIndex,"��������");
	obj.Mergecells(6,5,9,5); 	
	obj.S(6,6,sheetIndex,"��Ȼ��");
	obj.Mergecells(6,6,7,6);
	obj.S(8,6,sheetIndex,"����");
	obj.Mergecells(8,6,9,6);
	obj.S(6,7,sheetIndex,"����");
	obj.S(7,7,sheetIndex,"���");
	obj.S(8,7,sheetIndex,"����");
	obj.S(9,7,sheetIndex,"���");
	
   obj.S(10,4,sheetIndex,"I");
   obj.S(11,4,sheetIndex,"J");
   obj.S(12,4,sheetIndex,"K");
   obj.S(13,4,sheetIndex,"L");
   obj.S(10,5,sheetIndex,"���ܷ��ࣨ��");
   obj.Mergecells(10,5,13,5);
   obj.S(10,6,sheetIndex,"������");
   obj.Mergecells(10,6,10,7);
   obj.S(11,6,sheetIndex,"Ͷ����");
   obj.Mergecells(11,6,11,7);
   obj.S(12,6,sheetIndex,"���������");
   obj.Mergecells(12,6,12,7);
   obj.S(13,6,sheetIndex,"����");
   obj.Mergecells(13,6,13,7);
   
   obj.S(14,5,sheetIndex,"���÷�ʽ����");
   obj.Mergecells(14,5,30,5);
   obj.S(14,6,sheetIndex,"����");
   obj.Mergecells(14,6,15,6);
   obj.S(16,6,sheetIndex,"����Ͷ��");
   obj.Mergecells(16,6,17,6);
   obj.S(18,6,sheetIndex,"����ծȨͶ��");
   obj.Mergecells(18,6,19,6);
   obj.S(20,6,sheetIndex,"���ڹ�ȨͶ��");
   obj.Mergecells(20,6,21,6);
   obj.S(22,6,sheetIndex,"����");
   obj.Mergecells(22,6,23,6);
   obj.S(24,6,sheetIndex,"���뷵��");
   obj.Mergecells(24,6,26,6);
   obj.S(27,6,sheetIndex,"���");
   obj.Mergecells(27,6,28,6);
   obj.S(29,6,sheetIndex,"ͬҵ���(��");
   obj.Mergecells(29,6,29,7);
   obj.S(30,6,sheetIndex,"����   ����");
   obj.Mergecells(30,6,30,7);
   obj.S(14,7,sheetIndex,"�漰�ƻ�����");
   obj.S(15,7,sheetIndex,"���");
   obj.S(16,7,sheetIndex,"�漰�ƻ�����");
   obj.S(17,7,sheetIndex,"���");
   obj.S(18,7,sheetIndex,"�漰�ƻ�����");
   obj.S(19,7,sheetIndex,"���");
   obj.S(20,7,sheetIndex,"�漰�ƻ�����");
   obj.S(21,7,sheetIndex,"���");
   obj.S(22,7,sheetIndex,"�漰�ƻ�����");
   obj.S(23,7,sheetIndex,"���");
   obj.S(24,7,sheetIndex,"�漰�ƻ�����");
   obj.S(25,7,sheetIndex,"֤ȯ���");
   obj.S(26,7,sheetIndex,"�Ŵ��ʲ����");
   obj.S(27,7,sheetIndex,"�漰�ƻ�����");
   obj.S(28,7,sheetIndex,"���");
   
   obj.S(31,5,sheetIndex,"Ͷ�򣨽���");
   obj.Mergecells(31,5,44,5);
   obj.S(31,6,sheetIndex,"������ҵ");
   obj.Mergecells(31,6,32,6);
   obj.S(33,6,sheetIndex,"���ز�");
   obj.Mergecells(33,6,34,6);
   obj.S(35,6,sheetIndex,"֤ȯ");
   obj.Mergecells(35,6,38,6);
   obj.S(39,6,sheetIndex,"���ڻ���");
   obj.Mergecells(39,6,40,6);
   obj.S(41,6,sheetIndex,"������ҵ");
   obj.Mergecells(41,6,42,6);
   obj.S(43,6,sheetIndex,"����");
   obj.Mergecells(43,6,44,6);
   obj.S(31,7,sheetIndex,"�漰�ƻ�����");
   obj.S(32,7,sheetIndex,"���");
   obj.S(33,7,sheetIndex,"�漰�ƻ�����");
   obj.S(34,7,sheetIndex,"���");
   obj.S(35,7,sheetIndex,"�漰�ƻ�����");
   obj.S(36,7,sheetIndex,"��Ʊ");
   obj.S(37,7,sheetIndex,"����");
   obj.S(38,7,sheetIndex,"֤ȯ")
   obj.S(39,7,sheetIndex,"�漰�ƻ�����");
   obj.S(40,7,sheetIndex,"���");
   obj.S(41,7,sheetIndex,"�漰�ƻ�����");
   obj.S(42,7,sheetIndex,"���");
   obj.S(43,7,sheetIndex,"�漰�ƻ�����");
   obj.S(44,7,sheetIndex,"���");
   
   obj.S(45,5,sheetIndex,"���е�λ��ֵ����1");
   obj.Mergecells(45,5,46,5);
   obj.S(47,5,sheetIndex,"������Ŀ   ");
   obj.Mergecells(47,5,48,5);
   obj.S(45,6,sheetIndex,"�漰�ƻ�����");
   obj.Mergecells(45,6,45,7);
   obj.S(46,6,sheetIndex,"���");
   obj.Mergecells(46,6,46,7);
   obj.S(47,6,sheetIndex,"�漰�ƻ�����");
   obj.Mergecells(47,6,47,7);
   obj.S(48,6,sheetIndex,"���");
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
	    
	    //�ϲ���Ԫ������ɫ�������ܴ��Ӱ�죬�������@2����������ʵ��̧ͷ0x80ffffɫ���-----@1
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
 S32����ڶ����ֱ�ͷ����
 */
 function load_singlerow(obj,sheetIndex)
{
  
	obj.S(1,4,sheetIndex,"���");
	obj.Mergecells(1,4,1,7);
	
	obj.S(2,4,sheetIndex,"A");
	obj.S(2,5,sheetIndex,"���޽ṹ");
	obj.Mergecells(2,5,2,7);
	
    obj.S(3,4,sheetIndex,"B");
	obj.S(3,5,sheetIndex,"����");
	obj.Mergecells(3,5,3,7);

    obj.S(4,4,sheetIndex,"C");
    obj.S(5,4,sheetIndex,"D");
    obj.S(6,4,sheetIndex,"E");
    obj.S(7,4,sheetIndex,"F");
	obj.S(4,5,sheetIndex,"ί����");
	obj.Mergecells(4,5,7,5); 	
	obj.S(4,6,sheetIndex,"��Ȼ��");
	obj.Mergecells(4,6,5,6); 
	obj.S(6,6,sheetIndex,"����");
	obj.Mergecells(6,6,7,6);
	obj.S(4,7,sheetIndex,"����");
	obj.S(5,7,sheetIndex,"���");
	obj.S(6,7,sheetIndex,"����");
	obj.S(7,7,sheetIndex,"���");
	
	obj.S(8,4,sheetIndex,"G");
    obj.S(9,4,sheetIndex,"H");
    obj.S(10,4,sheetIndex,"I");
    obj.S(11,4,sheetIndex,"J");
	obj.S(8,5,sheetIndex,"��������");
	obj.Mergecells(8,5,11,5); 	
	obj.S(8,6,sheetIndex,"��Ȼ��");
	obj.Mergecells(8,6,9,6); 
	obj.S(10,6,sheetIndex,"����");
	obj.Mergecells(10,6,11,6);
	obj.S(8,7,sheetIndex,"����");
	obj.S(9,7,sheetIndex,"���");
	obj.S(10,7,sheetIndex,"����");
	obj.S(11,7,sheetIndex,"���");
	
   obj.S(12,4,sheetIndex,"K");
   obj.S(13,4,sheetIndex,"L");
   obj.S(14,4,sheetIndex,"M");
   obj.S(15,4,sheetIndex,"N");
   obj.S(12,5,sheetIndex,"���ܷ��ࣨ��");
   obj.Mergecells(12,5,15,5);
   obj.S(12,6,sheetIndex,"������");
   obj.Mergecells(12,6,12,7);
   obj.S(13,6,sheetIndex,"Ͷ����");
   obj.Mergecells(13,6,13,7);
   obj.S(14,6,sheetIndex,"���������");
   obj.Mergecells(14,6,14,7);
   obj.S(15,6,sheetIndex,"����");
   obj.Mergecells(15,6,15,7);
   
   obj.S(16,5,sheetIndex,"���÷�ʽ����");
   obj.Mergecells(16,5,32,5);
   obj.S(16,6,sheetIndex,"����");
   obj.Mergecells(16,6,17,6);
   obj.S(18,6,sheetIndex,"����Ͷ��");
   obj.Mergecells(18,6,19,6);
   obj.S(20,6,sheetIndex,"����ծȨͶ��");
   obj.Mergecells(20,6,21,6);
   obj.S(22,6,sheetIndex,"���ڹ�ȨͶ��");
   obj.Mergecells(22,6,23,6);
   obj.S(24,6,sheetIndex,"����");
   obj.Mergecells(24,6,25,6);
   obj.S(26,6,sheetIndex,"���뷵��");
   obj.Mergecells(26,6,28,6);
   obj.S(29,6,sheetIndex,"���");
   obj.Mergecells(29,6,30,6);
   obj.S(31,6,sheetIndex,"ͬҵ���(��");
   obj.Mergecells(31,6,31,7);
   obj.S(32,6,sheetIndex,"����   ����");
   obj.Mergecells(32,6,32,7);
   obj.S(16,7,sheetIndex,"�漰��Ŀ����");
   obj.S(17,7,sheetIndex,"���");
   obj.S(18,7,sheetIndex,"�漰��Ŀ����");
   obj.S(19,7,sheetIndex,"���");
   obj.S(20,7,sheetIndex,"�漰��Ŀ����");
   obj.S(21,7,sheetIndex,"���");
   obj.S(22,7,sheetIndex,"�漰��Ŀ����");
   obj.S(23,7,sheetIndex,"���");
   obj.S(24,7,sheetIndex,"�漰��Ŀ����");
   obj.S(25,7,sheetIndex,"���");
   obj.S(26,7,sheetIndex,"�漰��Ŀ����");
   obj.S(27,7,sheetIndex,"֤ȯ���");
   obj.S(28,7,sheetIndex,"�Ŵ��ʲ����");
   obj.S(29,7,sheetIndex,"�漰��Ŀ����");
   obj.S(30,7,sheetIndex,"���");
   
   obj.S(33,5,sheetIndex,"Ͷ�򣨽���");
   obj.Mergecells(33,5,46,5);
   obj.S(33,6,sheetIndex,"������ҵ");
   obj.Mergecells(33,6,34,6);
   obj.S(35,6,sheetIndex,"���ز�");
   obj.Mergecells(35,6,36,6);
   obj.S(37,6,sheetIndex,"֤ȯ");
   obj.Mergecells(37,6,40,6);
   obj.S(41,6,sheetIndex,"���ڻ���");
   obj.Mergecells(41,6,42,6);
   obj.S(43,6,sheetIndex,"������ҵ");
   obj.Mergecells(43,6,44,6);
   obj.S(45,6,sheetIndex,"����");
   obj.Mergecells(45,6,46,6);
   obj.S(33,7,sheetIndex,"�漰��Ŀ����");
   obj.S(34,7,sheetIndex,"���");
   obj.S(35,7,sheetIndex,"�漰��Ŀ����");
   obj.S(36,7,sheetIndex,"���");
   obj.S(37,7,sheetIndex,"�漰��Ŀ����");
   obj.S(38,7,sheetIndex,"��Ʊ");
   obj.S(39,7,sheetIndex,"����");
   obj.S(40,7,sheetIndex,"֤ȯ")
   obj.S(41,7,sheetIndex,"�漰��Ŀ����");
   obj.S(42,7,sheetIndex,"���");
   obj.S(43,7,sheetIndex,"�漰��Ŀ����");
   obj.S(44,7,sheetIndex,"���");
   obj.S(45,7,sheetIndex,"�漰��Ŀ����");
   obj.S(46,7,sheetIndex,"���");
   
   obj.S(47,5,sheetIndex,"���е�λ��ֵ����1");
   obj.Mergecells(47,5,48,5);
   obj.S(49,5,sheetIndex,"������Ŀ   ");
   obj.Mergecells(49,5,50,5);
   obj.S(47,6,sheetIndex,"�漰��Ŀ����");
   obj.Mergecells(47,6,47,7);
   obj.S(48,6,sheetIndex,"���");
   obj.Mergecells(48,6,48,7);
   obj.S(49,6,sheetIndex,"�漰��Ŀ����");
   obj.Mergecells(49,6,49,7);
   obj.S(50,6,sheetIndex,"���");
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
	    
	    //�ϲ���Ԫ������ɫ�������ܴ��Ӱ�죬�������@2����������ʵ��̧ͷ0x80ffffɫ���-----@1
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
 S32�����һ���ֱ�ͷ����
 */
 function load_propertyrow(obj,sheetIndex)
{
  
	obj.S(1,4,sheetIndex,"���");
	obj.Mergecells(1,4,1,7);
	
	obj.S(2,4,sheetIndex,"A");
	obj.S(2,5,sheetIndex,"���޽ṹ");
	obj.Mergecells(2,5,2,7);
	
    obj.S(3,4,sheetIndex,"B");
	obj.S(3,5,sheetIndex,"����");
	obj.Mergecells(3,5,3,7);

    obj.S(4,4,sheetIndex,"C");
	obj.S(4,5,sheetIndex,"���вƲ����м�ֵ");
	obj.Mergecells(4,5,4,7); 	

	
	obj.S(5,4,sheetIndex,"D");
	obj.S(6,4,sheetIndex,"E");
	obj.S(7,4,sheetIndex,"F");
	obj.S(8,4,sheetIndex,"G");
	obj.S(5,5,sheetIndex,"ί����");
	obj.Mergecells(5,5,8,5); 	
	obj.S(5,6,sheetIndex,"��Ȼ��");
	obj.Mergecells(5,6,6,6);
	obj.S(7,6,sheetIndex,"����");
	obj.Mergecells(7,6,8,6);
	obj.S(5,7,sheetIndex,"����");
	obj.S(6,7,sheetIndex,"���");
	obj.S(7,7,sheetIndex,"����");
	obj.S(8,7,sheetIndex,"���");
	
   obj.S(9,4,sheetIndex,"H");
   obj.S(10,4,sheetIndex,"I");
   obj.S(11,4,sheetIndex,"J");
   obj.S(12,4,sheetIndex,"K");
   obj.S(9,5,sheetIndex,"��������");
   obj.Mergecells(9,5,12,5);
   obj.S(9,6,sheetIndex,"��Ȼ��");
   obj.Mergecells(9,6,10,6);
   obj.S(11,6,sheetIndex,"����");
   obj.Mergecells(11,6,12,6);
   obj.S(9,7,sheetIndex,"����");
   obj.S(10,7,sheetIndex,"���");
   obj.S(11,7,sheetIndex,"����");
   obj.S(12,7,sheetIndex,"���");
   
   obj.S(13,4,sheetIndex,"L");
   obj.S(14,4,sheetIndex,"M");
   obj.S(15,4,sheetIndex,"N");
   obj.S(16,4,sheetIndex,"O");
   obj.S(13,5,sheetIndex,"���ܷ��ࣨ��");
   obj.Mergecells(13,5,16,5);
   obj.S(13,6,sheetIndex,"������");
   obj.Mergecells(13,6,13,7);
   obj.S(14,6,sheetIndex,"Ͷ����");
   obj.Mergecells(14,6,14,7);
   obj.S(15,6,sheetIndex,"���������");
   obj.Mergecells(15,6,15,7);
   obj.S(16,6,sheetIndex,"����");
   obj.Mergecells(16,6,16,7);
   
   obj.S(17,4,sheetIndex,"P");
   obj.S(18,4,sheetIndex,"Q");
   obj.S(19,4,sheetIndex,"R");
   obj.S(20,4,sheetIndex,"S");
   obj.S(21,4,sheetIndex,"T");
   obj.S(22,4,sheetIndex,"U");  
   obj.S(17,5,sheetIndex,"�������ô��ַ�ʽ");
   obj.Mergecells(17,5,22,5);
   obj.S(17,6,sheetIndex,"����");
   obj.Mergecells(17,6,17,7);
   obj.S(18,6,sheetIndex,"����");
   obj.Mergecells(18,6,18,7);
   obj.S(19,6,sheetIndex,"�ʲ�֤ȯ��");
   obj.Mergecells(19,6,19,7);
   obj.S(20,6,sheetIndex,"׼�ʲ�֤ȯ��");
   obj.Mergecells(20,6,20,7);
   obj.S(21,6,sheetIndex,"���в�����");
   obj.Mergecells(21,6,21,7);
   obj.S(22,6,sheetIndex,"����"); 
   obj.Mergecells(22,6,22,7);
   
   obj.S(23,4,sheetIndex," V");
   obj.S(24,4,sheetIndex,"W");
   obj.S(25,4,sheetIndex,"X");
   obj.S(26,4,sheetIndex,"Y");
   obj.S(23,5,sheetIndex,"���е�λ��ֵ����1");
   obj.Mergecells(23,5,24,5);
   obj.S(25,5,sheetIndex,"������Ŀ   ");
   obj.Mergecells(25,5,26,5);
   obj.S(23,6,sheetIndex,"�漰��Ŀ����");
   obj.Mergecells(23,6,23,7);
   obj.S(24,6,sheetIndex,"���");
   obj.Mergecells(24,6,24,7);
   obj.S(25,6,sheetIndex,"�漰��Ŀ����");
   obj.Mergecells(25,6,25,7);
   obj.S(26,6,sheetIndex,"���");
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
	    
	    //�ϲ���Ԫ������ɫ�������ܴ��Ӱ�죬�������@2����������ʵ��̧ͷ0x80ffffɫ���-----@1
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
 S32������Ĳ��֣��������һ�������ʽ�Ͷ���м�֤ȯ�����������ͷ����
 */
 function load_firstaddedrow(obj,sheetIndex)
{
    obj.SetCellFont(1,4,sheetIndex,obj.FindFontIndex ("����",1));
    obj.SetCellFontStyle(1,4,sheetIndex,2);
	obj.S(1,4,sheetIndex,"���");
	obj.Mergecells(1,4,1,6);
	
	obj.SetCellFont(2,4,sheetIndex,obj.FindFontIndex ("����",1));
	 obj.SetCellFontStyle(2,4,sheetIndex,2);
	obj.S(2,4,sheetIndex,"��Ŀ");
	obj.Mergecells(2,4,2,6);
	
    obj.S(3,4,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"�����ֵ");
	obj.Mergecells(3,5,3,6);

    obj.S(4,4,sheetIndex,"B");
	obj.S(4,5,sheetIndex,"�г���ֵ");
	obj.Mergecells(4,5,4,6); 	
	
	obj.S(5,4,sheetIndex,"C");
	obj.S(5,5,sheetIndex,"��ȷ������");
	obj.Mergecells(5,5,5,6); 
	
	obj.S(6,4,sheetIndex,"D");
	obj.S(7,4,sheetIndex,"E");
	obj.S(8,4,sheetIndex,"F");
	obj.S(9,4,sheetIndex,"G");
	obj.S(10,4,sheetIndex,"H");
	obj.S(6,5,sheetIndex,"����״��");
    obj.Mergecells(6,5,10,5);
    obj.S(6,6,sheetIndex,"����");
	obj.S(7,6,sheetIndex,"��ע");
	obj.S(8,6,sheetIndex,"�μ�");
	obj.S(9,6,sheetIndex,"����");
	obj.S(10,6,sheetIndex,"��ʧ");
	   
    for(var i=1;i<=10;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����      		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //���ñ߿���
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
 S32������Ĳ��ֲ������һ��ע1���������һ�������ʽ�Ͷ���м�֤ȯ�����������ͷ����
 */
 function load_firstaddednoted1row(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(3)),6,sheetIndex);	
   obj.MergeCells(1,34,4,34);
   obj.SetCellAlign(1,34,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,34,sheetIndex,2);
   obj.S(1,34,sheetIndex,"��ע1������������ṹ����ծȯͶ�ʣ�ֻ���������ֵ����");	


	obj.SetCellFontStyle(1,35,sheetIndex,2);
	obj.Mergecells(1,35,1,36);
	obj.S(1,35,sheetIndex,"���"); 
		
	obj.SetCellFontStyle(2,35,sheetIndex,2);
	obj.Mergecells(2,35,2,36);
	obj.S(2,35,sheetIndex,"��Ŀ");

	
    obj.S(3,35,sheetIndex,"A");
	obj.S(3,36,sheetIndex,"����");


    obj.S(4,35,sheetIndex,"B");
	obj.S(4,36,sheetIndex,"����������");
	
	//������������
	    for(var i=1;i<=4;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,37,sheetIndex,1);
			obj.SetCellSeparator(i,37,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����      		 
	    obj.SetCellAlign (i,35,sheetIndex,32+4);
	    obj.SetCellAlign (i,36,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //���ñ߿���
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
 S32������Ĳ��ֲ������һ��ע2���������һ�������ʽ�Ͷ���м�֤ȯ�����������ͷ����
 */
 function load_firstaddednoted2row(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(3)),5,sheetIndex);	
   obj.MergeCells(1,51,4,51);
   obj.SetCellAlign(1,51,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,51,sheetIndex,2);
   obj.S(1,51,sheetIndex,"��ע2����֤ȯ�����г���𻮷�");	


	obj.SetCellFontStyle(1,52,sheetIndex,2);
	obj.Mergecells(1,52,1,54);
	obj.S(1,52,sheetIndex,"���"); 
		
	obj.SetCellFontStyle(2,52,sheetIndex,2);
	obj.Mergecells(2,52,2,54);
	obj.S(2,52,sheetIndex,"��Ŀ");

	
    obj.S(3,52,sheetIndex,"A");
    obj.Mergecells(3,53,3,54);
	obj.S(3,53,sheetIndex,"����");


    obj.S(4,52,sheetIndex,"B");
    obj.S(5,52,sheetIndex,"C");
    obj.S(6,52,sheetIndex,"D");
    obj.S(7,52,sheetIndex,"E");
    obj.Mergecells(4,53,7,53);
	obj.S(4,53,sheetIndex,"ʵ������(���)");
	obj.S(4,54,sheetIndex,"���ź���");
    obj.S(5,54,sheetIndex,"֤�ź���");
    obj.S(6,54,sheetIndex,"˽ļ�������");
    obj.S(7,54,sheetIndex,"����");
	
	//������������
	    for(var i=1;i<=4;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,55,sheetIndex,1);
			obj.SetCellSeparator(i,55,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����      		 
	    obj.SetCellAlign (i,52,sheetIndex,32+4);
	    obj.SetCellAlign (i,53,sheetIndex,32+4);
	    obj.SetCellAlign (i,54,sheetIndex,32+4);	      		            	    	    
    }
	 
	 //���ñ߿���
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
 S32������岿�֣�������϶��������ʽ�������ҵ�����������ͷ����
 */
 function load_secondaddedrow(obj,sheetIndex)
{
    obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("����",1));
    obj.SetCellFontStyle(1,2,sheetIndex,2);
	obj.S(1,2,sheetIndex,"���");
	obj.Mergecells(1,2,1,3);
	
	 //���Խ���
	obj.Mergecells(2,2,2,3);
	obj.DrawGridLine(2,2,2,3,6,1,-1);
	obj.S(2,2,sheetIndex,"     ��ҵ����|��Ŀ     ");
	
    obj.S(3,2,sheetIndex,"A");
	obj.S(3,3,sheetIndex,"�������");

    obj.S(4,2,sheetIndex,"B");
	obj.S(4,3,sheetIndex,"�����ۼ������㽻��");	
	
	obj.S(5,2,sheetIndex,"C");
	obj.S(5,3,sheetIndex,"���������ʲ������ۼ�");
	

	   
    for(var i=1;i<=5;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,4,sheetIndex,1);
			obj.SetCellSeparator(i,4,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����      		 
	    obj.SetCellAlign (i,2,sheetIndex,32+4);
	    obj.SetCellAlign (i,3,sheetIndex,32+4);	      		            	    	    
    }
	 
	 //���ñ߿���
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
 S32������岿�ֲ�����϶��������ʽ�������ҵ�������>>ע��������Ŀ���÷����ʲ��弶���������ͷ����
 */
 function load_secondaddednoted1row(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(4)),4,sheetIndex);	
   
   obj.MergeCells(1,28,2,28);
   obj.SetCellAlign(1,28,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,28,sheetIndex,2);
   obj.S(1,28,sheetIndex,"��ע��������Ŀ���÷����ʲ��弶�������");	

	obj.Mergecells(1,29,1,30);
	obj.S(1,29,sheetIndex,"���"); 
		
	obj.Mergecells(2,29,2,30);
	obj.S(2,29,sheetIndex,"������Ŀ����");

	
    obj.S(3,29,sheetIndex,"A");
	obj.S(3,30,sheetIndex,"�������ʲ����");

    obj.S(4,29,sheetIndex,"B");
	obj.S(4,30,sheetIndex,"����");
	
	obj.S(5,29,sheetIndex,"C");
	obj.S(5,30,sheetIndex,"��ע");
	
	obj.S(6,29,sheetIndex,"D");
	obj.S(6,30,sheetIndex,"�μ�");
	
	obj.S(7,29,sheetIndex,"E");
	obj.S(7,30,sheetIndex,"����");
	
	obj.S(8,29,sheetIndex,"F");
	obj.S(8,30,sheetIndex,"��ʧ");   
	
	//������������
	    for(var i=1;i<=8;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,31,sheetIndex,1);
			obj.SetCellSeparator(i,31,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����      		 
	    obj.SetCellAlign (i,29,sheetIndex,32+4);
	    obj.SetCellAlign (i,30,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //���ñ߿���
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
   
 * S33������Ŀ�������ͳ�Ʊ� ��ͷ����
 */
function loadHeadOfFundsManageByMonthForS33(obj,statementsTitle,cols,hzDate,companyName,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//�������� 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S33");
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	if(sheetIndex==0){
	        //��̧ͷ		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("����",1));
			obj.SetCellFontSize(1,1,sheetIndex,15);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,38,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//�����	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,16+1);
			obj.Mergecells(1,2,2,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //��������---��λ
		    obj.SetCellFont(7,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(7,2,sheetIndex,10.5); 
			obj.SetCellAlign(7,2,sheetIndex,16+4);
		    obj.Mergecells (7,2,14,2); 
			obj.S (7,2,sheetIndex,hzDate+"   "+"��λ����Ԫ,��");  
			

			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,11); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"��һ����:������������Ŀ�����");  
			obj.Mergecells (1,3,5,3);
			
		}
}	

/**
 *ADD BY TSG 2008-01-29
 
 S33������Ŀ�������ͳ�Ʊ�  ��һ����:������������Ŀ����� ��ͷ����
 */
 function load_liquidatedrow(obj,sheetIndex)
{
	obj.S(1,4,sheetIndex,"���");
	obj.Mergecells(1,4,1,6);
	
	obj.S(2,4,sheetIndex,"����");
	obj.Mergecells(2,4,2,6);
	
	obj.S(3,4,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"������Ŀ����");
	obj.Mergecells(3,5,3,6);
	
    obj.S(4,4,sheetIndex,"B");
    obj.S(5,4,sheetIndex,"C");
    obj.S(6,4,sheetIndex,"D");
	obj.S(4,5,sheetIndex,"�ѵ���������Ŀʵ������");
	obj.Mergecells(4,5,6,5);
	obj.S(4,6,sheetIndex,"����");
    obj.S(5,6,sheetIndex,"��һ");
    obj.S(6,6,sheetIndex,"�Ʋ�Ȩ");

    obj.S(7,4,sheetIndex,"E");
	obj.S(7,5,sheetIndex,"���б����ۼƸ�����");
	obj.Mergecells(7,5,7,6); 	
	
	obj.S(8,4,sheetIndex,"F");
	obj.S(8,5,sheetIndex,"���������ۼƷ����");
	obj.Mergecells(8,5,8,6); 
	
	obj.S(9,4,sheetIndex,"G");
	obj.S(10,4,sheetIndex,"H");	
	obj.S(9,5,sheetIndex,"���б�������֧����Դ");
	obj.Mergecells(9,5,10,5);	
	obj.S(9,6,sheetIndex,"����Ŀ");
	obj.S(10,6,sheetIndex,"����");
	
	obj.S(11,4,sheetIndex,"I");
    obj.S(11,5,sheetIndex,"���з����ʣ�����");
    obj.Mergecells(11,5,11,6);
    
    
	obj.S(12,4,sheetIndex,"J");
	obj.S(12,5,sheetIndex,"ʵ�������ʣ�����");
	obj.Mergecells(12,5,12,6);
	 
	obj.S(13,4,sheetIndex,"K");
	obj.S(13,5,sheetIndex,"ʵ�����б����ʣ�����");
	obj.Mergecells(13,5,13,6);
	
    obj.S(14,4,sheetIndex,"L");
	obj.S(14,5,sheetIndex,"��ʧ���");
	obj.Mergecells(14,5,14,6);
	
	obj.S(15,4,sheetIndex,"M");
	obj.S(15,5,sheetIndex,"���߽��");
	obj.Mergecells(15,5,15,6);
	
    obj.S(16,4,sheetIndex,"N");
	obj.S(17,4,sheetIndex,"O");	
	obj.S(16,5,sheetIndex,"�⸶���");
	obj.Mergecells(16,5,17,5);	
	obj.S(16,6,sheetIndex,"�⸶����");
	obj.S(17,6,sheetIndex,"�⸶���");
	   
    for(var i=1;i<=17;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����      		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //���ñ߿���
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
 //S33������Ŀ�������ͳ�Ʊ�>>�ڶ�����:����δ����������Ŀ�����  ��ͷ����
 */
 function load_unliquidatedrow(obj,sheetIndex)
{
   obj.InsertRow((obj.GetRows(0)),5,sheetIndex);	
   obj.MergeCells(1,14,5,14);
   obj.SetCellAlign(1,14,sheetIndex,16+1)						  
   obj.SetCellFontStyle(1,14,sheetIndex,2);
   obj.SetCellFontSize(1,14,sheetIndex,11); 			
   obj.S(1,14,sheetIndex,"�ڶ�����:����δ����������Ŀ�����");	

    obj.S(1,15,sheetIndex,"���");
	obj.Mergecells(1,15,1,17);
	
	obj.S(2,15,sheetIndex,"A");
	obj.S(2,16,sheetIndex,"������Ŀ����(����)");
	obj.Mergecells(2,16,2,17);
	
    obj.S(3,15,sheetIndex,"B");
    obj.S(4,15,sheetIndex,"C");
    obj.S(5,15,sheetIndex,"D");
	obj.S(3,16,sheetIndex,"ʵ������");
	obj.Mergecells(3,16,5,16);
	obj.S(3,17,sheetIndex,"����");
    obj.S(4,17,sheetIndex,"��һ");
    obj.S(5,17,sheetIndex,"�Ʋ�Ȩ");

    obj.S(6,15,sheetIndex,"E");
	obj.S(6,16,sheetIndex,"���б����ۼƸ�����");
	obj.Mergecells(6,16,6,17); 	
	
	obj.S(7,15,sheetIndex,"F");
	obj.S(7,16,sheetIndex,"���������ۼƷ����");
	obj.Mergecells(7,16,7,17); 
	
	obj.S(8,15,sheetIndex,"G");
	obj.S(8,16,sheetIndex,"��ʧ���");
	obj.Mergecells(8,16,8,17); 
	
	obj.S(9,15,sheetIndex,"H");
	obj.S(9,16,sheetIndex,"���߽��");
	obj.Mergecells(9,16,9,17); 
	
	obj.S(10,15,sheetIndex,"I");
	obj.S(10,16,sheetIndex,"�⸶���");
	obj.Mergecells(10,16,10,17); 
	
	//������������
	    for(var i=1;i<=10;i++){
	     if(i!=1){
	     	obj.SetCellNumType(i,18,sheetIndex,1);
			obj.SetCellSeparator(i,18,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����      		 
	    obj.SetCellAlign (i,16,sheetIndex,32+4);
	    obj.SetCellAlign (i,17,sheetIndex,32+4);
	      		            	    	    
    }
	 
	 //���ñ߿���
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
   
 * S33������Ŀ�������ͳ�Ʊ�>>�ڶ�����:����δ����������Ŀ������β����
 */
 function loadEndOfFundsManagexByMonthForS33(obj,cols,rows,op_name,sheetIndex){  
    if(sheetIndex==0){  
        var allRows=obj.GetRows(0);
        obj.InsertRow(obj.GetRows(0),1,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,7,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"����ˣ�" + op_name); 
		obj.SetCellAlign (9,allRows,sheetIndex,32+1);
		obj.Mergecells (9,allRows,10,rows);
		obj.S (9,allRows,0,"�����ˣ�");
    }
  }
  
  /**
   ADD BY TSG 2008-01-29
   
 * ��ʽ�� S33������Ŀ�������ͳ�Ʊ�>>�ڶ�����:����δ����������Ŀ������β����

 */
 
 function formatStatementsForS33(obj,sheetIndex){
	  if(sheetIndex==0){
		    ///document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У� 
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
		    
		    //������߿�Ϊ����--����
		    for(i=4;i<=12;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(17,i,sheetIndex,2,3);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=17;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,12,sheetIndex,3,3);
		    }
		    
		     //������߿�Ϊ����--����
		    for(i=15;i<=lastrow-1;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(10,i,sheetIndex,2,3);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=10;i++){
		       obj.SetCellBorder(i,15,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-1,sheetIndex,3,3);
		    }

		    //���������Զ�����
		    for(row=5;row<=6;row++){
		      for(col=1;col<=17;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��������
		    for(row=13;row<=14;row++){
		      for(col=1;col<=17;col++){
		        obj.SetCellBorder(col,row,sheetIndex,0,1);
		        obj.SetCellBorder(col,row,sheetIndex,1,1);
		        obj.SetCellBorder(col,row,sheetIndex,2,1);
		        obj.SetCellBorder(col,row,sheetIndex,3,1);
		      }
		    }
		    
		    //��������
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
		    		    //���������Զ�����
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
   
 * S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>��һ���֣�����Ͷ�ʹ�˾�������׷���״��(1) ��ͷ����
 */
function loadHeadOfFundsManageByMonthForS34(obj,statementsTitle,cols,hzDate,companyName,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//�������� 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S34��һ����_I");
	}else if(sheetIndex==1){
	  obj.SetSheetLabel(sheetIndex,"S34��һ����_II");
	}else if(sheetIndex==2){
	  obj.SetSheetLabel(sheetIndex,"S34�ڶ�����");
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	if(sheetIndex==0){
	        //��̧ͷ		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("����",1));
			obj.SetCellFontSize(1,1,sheetIndex,16);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,43,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//�����	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,32+1);
			obj.Mergecells(1,2,4,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //��������
		    obj.SetCellFont(5,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(5,2,sheetIndex,10.5); 
			obj.SetCellAlign(5,2,sheetIndex,32+4);
		    obj.Mergecells (5,2,12,2); 
			obj.S (5,2,sheetIndex,hzDate);  
			
		    //��λ
		    obj.SetCellFont(13,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(13,2,sheetIndex,10.5); 
			obj.SetCellAlign(13,2,sheetIndex,32+4);
		    obj.Mergecells (13,2,17,2); 
			obj.S (13,2,sheetIndex,"���ҵ�λ����Ԫ"); 
			

			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,14); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"��һ���֣�����Ͷ�ʹ�˾�������׷���״��(1)");  
			obj.Mergecells (1,3,9,3);
			
			obj.SetCellAlign(1,4,sheetIndex,32+1);
			obj.SetCellFontSize(1,4,sheetIndex,11); 
			obj.S (1,4,sheetIndex,"һ������Ͷ�ʹ�˾����������׷���״����");  
			obj.Mergecells (1,4,8,4);									
		}else if(sheetIndex==1){
	        //��̧ͷ		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("����",1));
			obj.SetCellFontSize(1,1,sheetIndex,16);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,43,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//�����	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,32+1);
			obj.Mergecells(1,2,4,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //��������---��λ
		    obj.SetCellFont(5,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(5,2,sheetIndex,10.5); 
			obj.SetCellAlign(5,2,sheetIndex,32+4);
		    obj.Mergecells (5,2,9,2); 
			obj.S (5,2,sheetIndex,hzDate+"     "+"���ҵ�λ����Ԫ");  
			
			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,14); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"��һ���֣�����Ͷ�ʹ�˾�������׷���״��(2)");  
			obj.Mergecells (1,3,8,3);
			
			obj.SetCellAlign(1,4,sheetIndex,32+1);
			obj.SetCellFontStyle(1,4,sheetIndex,2); 
			obj.S (1,4,sheetIndex,"   �������вƲ��໥���׷���״����");  
			obj.Mergecells (1,4,5,4);									
		}else if(sheetIndex==2){
	        //��̧ͷ		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("����",1));
			obj.SetCellFontSize(1,1,sheetIndex,16);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,43,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//�����	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,32+1);
			obj.Mergecells(1,2,5,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //��������
		    obj.SetCellFont(6,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(6,2,sheetIndex,10.5); 
			obj.SetCellAlign(6,2,sheetIndex,32+4);
		    obj.Mergecells (6,2,11,2); 
			obj.S (6,2,sheetIndex,hzDate);  
			
		    //��λ
		    obj.SetCellFont(16,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(16,2,sheetIndex,10.5); 
			obj.SetCellAlign(16,2,sheetIndex,32+4);
		    obj.Mergecells (16,2,20,2); 
			obj.S (16,2,sheetIndex,"���ҵ�λ����Ԫ"); 
			

			obj.SetCellAlign(1,3,sheetIndex,32+1);
			obj.SetCellFontSize(1,3,sheetIndex,14); 
			obj.SetCellFontStyle(1,3,sheetIndex,2);
			obj.S (1,3,sheetIndex,"�ڶ����֣�����Ͷ�ʹ�˾���ʮ�ҿͻ��������ſͻ������ʼ��ж������");  
			obj.Mergecells (1,3,15,3);									
		}
}

/**
 *ADD BY TSG 2008-01-30
 
 S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>��һ���֣�����Ͷ�ʹ�˾�������׷���״��(1)	 ��ͷ����
 */
 function load_sheet1row(obj,sheetIndex)
{
	obj.S(1,5,sheetIndex,"���");
	obj.Mergecells(1,5,1,8);

	obj.S(2,5,sheetIndex,"A");
	obj.S(2,6,sheetIndex,"����������");
	obj.Mergecells(2,6,2,8);
	
	obj.S(3,5,sheetIndex,"B");
	obj.S(3,6,sheetIndex,"��ҵ��������");
	obj.Mergecells(3,6,3,8);
	
	obj.S(4,5,sheetIndex,"C");
	obj.S(4,6,sheetIndex,"��������");
	obj.Mergecells(4,6,4,8);
	
	obj.S(5,5,sheetIndex,"D");
	obj.S(5,6,sheetIndex,"�����ʽ���Դ");
	obj.Mergecells(5,6,5,8);
	
    obj.S(6,5,sheetIndex,"E");
    obj.S(7,5,sheetIndex,"F");
    obj.S(8,5,sheetIndex,"G");
    obj.S(9,5,sheetIndex,"H");
    obj.S(10,5,sheetIndex,"I");
	obj.S(6,6,sheetIndex,"���ڽ��ף��ʽ��ڳ���");
	obj.Mergecells(6,6,10,6);
	obj.S(6,7,sheetIndex,"�������");
	obj.Mergecells(6,7,6,8);
    obj.S(7,7,sheetIndex,"�������");
    obj.Mergecells(7,7,7,8);
    obj.S(8,7,sheetIndex,"Ͷ�����");
    obj.Mergecells(8,7,8,8);
    obj.S(9,7,sheetIndex,"���뷵���ʲ����");
    obj.Mergecells(9,7,9,8);
    obj.S(10,7,sheetIndex,"�����ڳ����");
    obj.Mergecells(10,7,10,8);
	
	obj.S(11,5,sheetIndex,"J");
	obj.S(11,6,sheetIndex,"���⽻��");
	obj.Mergecells(11,6,11,8);
	
	obj.S(12,5,sheetIndex,"K");
	obj.S(12,6,sheetIndex,"�������׺ϼƽ��");
	obj.Mergecells(12,6,12,8);
	
	obj.S(13,5,sheetIndex,"L");
    obj.S(14,5,sheetIndex,"M");
    obj.S(15,5,sheetIndex,"N");
    obj.S(16,5,sheetIndex,"O");
    obj.S(17,5,sheetIndex,"P");
	obj.S(13,6,sheetIndex,"����״��");
	obj.Mergecells(13,6,17,6);
	obj.S(13,7,sheetIndex,"����");
	obj.Mergecells(13,7,13,8);
    obj.S(14,7,sheetIndex,"��ע");
    obj.Mergecells(14,7,14,8);
    obj.S(15,7,sheetIndex,"�μ�");
    obj.Mergecells(15,7,15,8);
    obj.S(16,7,sheetIndex,"����");
    obj.Mergecells(16,7,16,8);
    obj.S(17,7,sheetIndex,"��ʧ");
    obj.Mergecells(17,7,17,8);
	   
    for(var i=1;i<=17;i++){
	     if(i!=1 && i!=2&& i!=5){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����    
	    obj.SetCellAlign (i,5,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	    obj.SetCellAlign (i,7,sheetIndex,32+4);
	    obj.SetCellAlign (i,8,sheetIndex,32+4);
	      		            	    	    
    }
	 var rows=obj.GetRows(sheetIndex);
	 
	 //���ñ߿���
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
   
 * S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>��һ���֣�����Ͷ�ʹ�˾�������׷���״��(1) ��β����
 */
 function loadEndOfFundsManagexByMonthForS34(obj,cols,rows,op_name,sheetIndex){  
    if(sheetIndex==0){  
        var allRows=obj.GetRows(0);
        obj.InsertRow(obj.GetRows(0),1,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"����ˣ�" + op_name); 
		obj.SetCellAlign (5,allRows,sheetIndex,32+1);
		obj.Mergecells (5,allRows,11,rows);
		obj.S (5,allRows,0,"�����ˣ�");
		obj.Mergecells (11,allRows,17,allRows);
    }else if(sheetIndex==1){  
        var allRows=obj.GetRows(sheetIndex);
        obj.InsertRow(obj.GetRows(sheetIndex),1,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);		
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"����ˣ�" + op_name); 
		
		obj.SetCellAlign (5,allRows,sheetIndex,32+1);
		obj.S (5,allRows,sheetIndex,"�����ˣ�");
		
		obj.SetCellAlign (7,allRows,sheetIndex,32+1);
		obj.Mergecells (7,allRows,9,rows);
		obj.S (7,allRows,sheetIndex,"�����ˣ�");
    }else if(sheetIndex==2){  
        var allRows=obj.GetRows(sheetIndex);
        obj.InsertRow(obj.GetRows(sheetIndex),2,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"����ˣ�" + op_name); 
		obj.SetCellAlign (5,allRows,sheetIndex,32+1);
		obj.Mergecells (5,allRows,12,rows);
		obj.S (5,allRows,sheetIndex,"�����ˣ�");
		obj.Mergecells (14,allRows,20,allRows);
		obj.S (1,allRows+1,sheetIndex,"ע�������ʽ���Դ��1��ʾ�����ʽ�2��ʾ�����ʽ�������ƣ���3��ʾ�����ʽ�ί����ƣ�");
		obj.Mergecells (1,allRows+1,20,allRows+1);
    }
  }
  
  /**
   ADD BY TSG 2008-01-30
   
 * ��ʽ��  S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>��һ���֣�����Ͷ�ʹ�˾�������׷���״��(1) 

 */
 
 function formatStatementsForS34(obj,sheetIndex){
	  if(sheetIndex==0){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У� 
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
		    
		    //������߿�Ϊ����--����
		    for(i=5;i<=lastrow;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(17,i,sheetIndex,2,3);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=17;i++){
		       obj.SetCellBorder(i,5,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-1,sheetIndex,3,3);
		    }
		  

		    //���������Զ�����
		    for(row=5;row<=8;row++){
		      for(col=1;col<=17;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��������
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
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У� 
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
		    
		    //������߿�Ϊ����--����
		    for(i=5;i<=lastrow;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(8,i,sheetIndex,2,3);
		    }
		    
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=8;i++){
		       obj.SetCellBorder(i,5,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow,sheetIndex,3,3);
		    }
		    
		    
		  

		    //���������Զ�����
		    for(row=5;row<=6;row++){
		      for(col=1;col<=8;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��������
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
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У� 
		    document.theform.DCellWeb1.SetColWidth(1,30,2,sheetIndex); //���õ�һ�п�����У�
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
		    
		
		    //������߿�Ϊ����--����
		    for(i=4;i<=lastrow-2;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(20,i,sheetIndex,2,3);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=20;i++){
		       obj.SetCellBorder(i,4,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-2,sheetIndex,3,3);
		    }
		  

		    //���������Զ�����
		    for(row=5;row<=6;row++){
		      for(col=1;col<=20;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��������
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
		    		    //��������
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
 
 S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>��һ���֣�����Ͷ�ʹ�˾�������׷���״��(2)>>�������вƲ��໥���׷���״����	 ��ͷ����
 */
 function load_sheet2_1row(obj,sheetIndex)
{
	obj.S(1,5,sheetIndex,"���");
	obj.Mergecells(1,5,1,6);

	obj.S(2,5,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"B");
	obj.S(4,5,sheetIndex,"C");
	obj.S(5,5,sheetIndex,"D");
	obj.S(6,5,sheetIndex,"E");
	obj.S(7,5,sheetIndex,"F");
	obj.S(8,5,sheetIndex,"G");
	
	obj.S(2,6,sheetIndex,"������Ŀ(�ƻ�)����");
	obj.S(3,6,sheetIndex,"���׶Է�������Ŀ(�ƻ�)����");
	obj.S(4,6,sheetIndex,"���׽��");
	obj.S(5,6,sheetIndex,"��������");
	obj.S(6,6,sheetIndex,"���׺�ͬ��ֹ����");
	obj.S(7,6,sheetIndex,"ί���ˡ�������ͬ�����");
	obj.S(8,6,sheetIndex,"��Ϣ��¶Ƶ��");
	   
    for(var i=1;i<=8;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����    
	    obj.SetCellAlign (i,5,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,6,sheetIndex,32+4);
	      		            	    	    
    }
	 var rows=obj.GetRows(sheetIndex);
	 
	 //���ñ߿���
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
 
 //S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>��һ���֣�����Ͷ�ʹ�˾�������׷���״��(2)>> �������вƲ�����вƲ����׷���״���� 	 ��ͷ����
 */
 function load_sheet2_2row(obj,sheetIndex,currows)
{
    obj.InsertRow(obj.GetRows(sheetIndex),4,sheetIndex);
    
   
    obj.Mergecells(1,currows+2,5,currows+2);
    obj.SetCellFontStyle(1,currows+2,sheetIndex,2);
    obj.SetCellAlign (1,currows+2,sheetIndex,32+1);
    obj.S(1,currows+2,sheetIndex,"   �������вƲ�����вƲ����׷���״����");
    
	obj.S(1,currows+3,sheetIndex,"���");
	obj.Mergecells(1,currows+3,1,currows+4);

	obj.S(2,currows+3,sheetIndex,"A");
	obj.S(3,currows+3,sheetIndex,"B");
	obj.S(4,currows+3,sheetIndex,"C");
	obj.S(5,currows+3,sheetIndex,"D");
	obj.S(6,currows+3,sheetIndex,"E");
	obj.S(7,currows+3,sheetIndex,"F");
	obj.S(8,currows+3,sheetIndex,"G");
	obj.S(9,currows+3,sheetIndex,"H");
	
	obj.S(2,currows+4,sheetIndex,"������Ŀ(�ƻ�)����");
	obj.S(3,currows+4,sheetIndex,"���вƲ����");
	obj.S(4,currows+4,sheetIndex,"���вƲ����");
	obj.S(5,currows+4,sheetIndex,"���׽��");
	obj.S(6,currows+4,sheetIndex,"��������");
	obj.S(7,currows+4,sheetIndex,"���׺�ͬ��ֹ����");
	obj.S(8,currows+4,sheetIndex,"ί����������ͬ�����");
	obj.S(9,currows+4,sheetIndex,"��Ϣ��¶Ƶ��");
	   
    for(var i=1;i<=9;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,currows+5,sheetIndex,1);
			obj.SetCellSeparator(i,currows+5,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����    
	    obj.SetCellAlign (i,currows+3,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,currows+4,sheetIndex,32+4);
	      		            	    	    
    }
	 var rows=obj.GetRows(sheetIndex);
	 
	 //���ñ߿���
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
   
 * ��ʽ��  S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>��һ���֣�����Ͷ�ʹ�˾�������׷���״��(1) 

 */
 
 function formatStatementsForS342(obj,sheetIndex,currows){
 if(sheetIndex==1){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У� 
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
		    
		    //������߿�Ϊ����--����
		    for(i=5;i<=currows;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(8,i,sheetIndex,2,3);
		    }
		    
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=8;i++){
		       obj.SetCellBorder(i,5,sheetIndex,1,3);
		       obj.SetCellBorder(i,currows,sheetIndex,3,3);
		    }
		    
		    		    //������߿�Ϊ����--����
		    for(i=currows+3;i<=lastrow-1;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(9,i,sheetIndex,2,3);
		    }
		    
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=9;i++){
		       obj.SetCellBorder(i,currows+3,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-1,sheetIndex,3,3);
		    }
		    
		    
		  

		    //���������Զ�����
		    for(row=5;row<=6;row++){
		      for(col=1;col<=8;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    		    //���������Զ�����
		    for(row=currows+3;row<=currows+4;row++){
		      for(col=1;col<=9;col++){
		        obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		     //��������
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
		    
		    //��������
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
 
 S34����Ͷ�ʹ�˾�������׷��ռ��ͻ����ж������>>�ڶ����֣�����Ͷ�ʹ�˾���ʮ�ҿͻ��������ſͻ������ʼ��ж������	 ��ͷ����
 */
 function load_sheet3row(obj,sheetIndex)
{
	obj.S(1,4,sheetIndex,"���");
	obj.Mergecells(1,4,1,6);

	obj.S(2,4,sheetIndex,"����");
	obj.Mergecells(2,4,2,6);
	
	obj.S(3,4,sheetIndex,"A");
	obj.S(3,5,sheetIndex,"�ͻ�����");
	obj.Mergecells(3,5,3,6);
	
	obj.S(4,4,sheetIndex,"B");
	obj.S(4,5,sheetIndex,"��ҵ��������");
	obj.Mergecells(4,5,4,6);
	
	obj.S(5,4,sheetIndex,"C");
	obj.S(5,5,sheetIndex,"�ͻ�����");
	obj.Mergecells(5,5,5,6);
	
	obj.S(6,4,sheetIndex,"D");
	obj.S(6,5,sheetIndex,"�ʽ���Դ");
	obj.Mergecells(6,5,6,6);
	
    obj.S(7,4,sheetIndex,"E");
    obj.S(8,4,sheetIndex,"F");
    obj.S(9,4,sheetIndex,"G");
    obj.S(10,4,sheetIndex,"H");
    obj.S(11,4,sheetIndex,"I");
	obj.S(7,5,sheetIndex,"�������ʣ��ʽ��ڳ���");
	obj.Mergecells(7,5,11,5);
	obj.S(7,6,sheetIndex,"�������");
    obj.S(8,6,sheetIndex,"�������");
    obj.S(9,6,sheetIndex,"Ͷ�����");
    obj.S(10,6,sheetIndex,"���뷵���ʲ����");
    obj.S(11,6,sheetIndex,"�����ڳ����");
	
	obj.S(12,4,sheetIndex,"J");
	obj.S(13,4,sheetIndex,"K");
	obj.S(12,5,sheetIndex,"��������");
	obj.Mergecells(12,5,13,5);
	obj.S(12,6,sheetIndex,"����");
	obj.S(13,6,sheetIndex,"����");
	
    obj.S(14,4,sheetIndex,"L");
	obj.S(14,5,sheetIndex,"���ʺϼƽ��");
	obj.Mergecells(14,5,14,6);
	
	obj.S(15,4,sheetIndex,"M");
	obj.S(15,5,sheetIndex,"���к����м��������ʺϼƽ��");
	obj.Mergecells(15,5,15,6);
	
	obj.S(16,4,sheetIndex,"N");
    obj.S(17,4,sheetIndex,"O");
    obj.S(18,4,sheetIndex,"P");
    obj.S(19,4,sheetIndex,"Q");
    obj.S(20,4,sheetIndex,"R");  
	obj.S(16,5,sheetIndex,"����״��");
	obj.Mergecells(16,5,20,5);
	obj.S(16,6,sheetIndex,"����");
    obj.S(17,6,sheetIndex,"��ע");
    obj.S(18,6,sheetIndex,"�μ�");
    obj.S(19,6,sheetIndex,"����");
    obj.S(20,6,sheetIndex,"��ʧ"); 
	   
    for(var i=1;i<=20;i++){
	     if(i!=1 && i!=2&& i!=5){
	     	obj.SetCellNumType(i,7,sheetIndex,1);
			obj.SetCellSeparator(i,7,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����    
	    obj.SetCellAlign (i,4,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,5,sheetIndex,32+4);
	    obj.SetCellAlign (i,6,sheetIndex,32+4);	      		            	    	    
    }
	var rows=obj.GetRows(sheetIndex);
	 
	 //���ñ߿���
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
   
 * S35���ڹ�ȨͶ�ʷ��������
 */
function loadHeadOfFundsManageByMonthForS35(obj,statementsTitle,cols,hzDate,companyName,currency,sheetIndex) 
{
	if(document.theform.DCellWeb1=='[object]'){ 
		document.theform.DCellWeb1.height =document.body.clientHeight-110;
	}
	obj.WorkbookReadonly = true;
	obj.SetCols(cols,sheetIndex);//�������� 
	
	if(sheetIndex==0){
	  obj.SetSheetLabel(sheetIndex,"S35");
	}
	 
	obj.ShowSheetLabel(1,sheetIndex);
	if(sheetIndex==0){
	        //��̧ͷ		
			obj.SetCellFont(1,1,sheetIndex,obj.FindFontIndex ("����",1));
			obj.SetCellFontSize(1,1,sheetIndex,15);
			obj.SetCellFontStyle(1,1,sheetIndex,2);
			obj.SetCellAlign (1,1,sheetIndex,32+4)
			 obj.Mergecells(1,1,cols-1,1);
		    obj.SetRowHeight(1,38,1,sheetIndex);		  
			obj.S (1,1,sheetIndex,statementsTitle);  
			
			//�����	
			obj.SetCellFont(1,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
			obj.SetCellFontSize(1,2,sheetIndex,10.5); 
			obj.SetRowHeight(1,30,2,sheetIndex);
			obj.SetCellAlign(1,2,sheetIndex,16+1);
			obj.Mergecells(1,2,5,2);
			obj.S(1,2,sheetIndex,companyName);
			
		     //��������
		    obj.SetCellFont(8,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(8,2,sheetIndex,10.5); 
			obj.SetCellAlign(8,2,sheetIndex,16+4);
		    obj.Mergecells (8,2,16,2); 
			obj.S (8,2,sheetIndex,hzDate); 
			
			 //����
		    obj.SetCellFont(17,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(17,2,sheetIndex,10.5); 
			obj.SetCellAlign(17,2,sheetIndex,16+4);
		    obj.Mergecells (17,2,19,2); 
			obj.S (17,2,sheetIndex,"���֣�"+currency);  
			
			//���ҵ�λ
			obj.SetCellFont(20,2,sheetIndex,obj.FindFontIndex ("����_GB2312",1));
		    obj.SetCellFontSize(20,2,sheetIndex,10.5); 
			obj.SetCellAlign(20,2,sheetIndex,16+4);
		    obj.Mergecells (20,2,24,2); 
			obj.S (20,2,sheetIndex," ���ҵ�λ����Ԫ");  
			
		}
}

/**
 *ADD BY TSG 2008-02-01
 
 S35���ڹ�ȨͶ�ʷ��������
 */
 function load_s35row(obj,sheetIndex)
{
	obj.S(1,3,sheetIndex,"���");
	obj.Mergecells(1,3,1,5);

	
	obj.S(2,3,sheetIndex,"A");
	obj.S(2,4,sheetIndex,"Ͷ�ʳֹ���ҵ����");
	obj.Mergecells(2,4,2,5);
	
	obj.S(3,3,sheetIndex,"B");
	obj.S(3,4,sheetIndex,"Ͷ������");
	obj.Mergecells(3,4,3,5);
	
	obj.S(4,3,sheetIndex,"C");
	obj.S(4,4,sheetIndex,"��ʼͶ�ʽ��");
	obj.Mergecells(4,4,4,5);
	
	obj.S(5,3,sheetIndex,"D");
	obj.S(6,3,sheetIndex,"E");
	obj.S(7,3,sheetIndex,"F");
	obj.S(8,3,sheetIndex,"G");
	obj.S(9,3,sheetIndex,"H");
	obj.S(10,3,sheetIndex,"I");
	obj.S(11,3,sheetIndex,"J");	
	obj.S(5,4,sheetIndex,"��Ͷ�ʽ��");
	obj.Mergecells(5,4,11,4);
	obj.S(5,5,sheetIndex,"��������ڻ���");
	obj.S(6,5,sheetIndex,"֤ȯ��˾");
	obj.S(7,5,sheetIndex,"����˾");
	obj.S(8,5,sheetIndex,"���չ�˾");
	obj.S(9,5,sheetIndex,"�ڻ���˾");
	obj.S(10,5,sheetIndex,"�����н��ڻ���");
	obj.S(11,5,sheetIndex,"����");
	
	obj.S(12,3,sheetIndex,"K");
	obj.S(12,4,sheetIndex,"�ֹɱ���");
	obj.Mergecells(12,4,12,5);
	
	
	obj.S(13,3,sheetIndex,"L");
	obj.S(14,3,sheetIndex,"M");
	obj.S(13,4,sheetIndex,"Ͷ������");
	obj.Mergecells(13,4,14,4);
	obj.S(13,5,sheetIndex,"�������");
	obj.S(14,5,sheetIndex,"�����ۼ���");
	
	obj.S(15,3,sheetIndex,"N");
	obj.S(16,3,sheetIndex,"O");
	obj.S(15,4,sheetIndex,"�ֹ���ҵ���ʲ���");
	obj.Mergecells(15,4,16,4);
	obj.S(15,5,sheetIndex,"����ĩ��");
	obj.S(16,5,sheetIndex,"������");
	
	obj.S(17,3,sheetIndex,"P");
	obj.S(18,3,sheetIndex,"Q");
	obj.S(17,4,sheetIndex,"�ֹ���ҵ������");
	obj.Mergecells(17,4,18,4);
	obj.S(17,5,sheetIndex,"����ĩ��");
	obj.S(18,5,sheetIndex,"������");
	
	
	obj.S(19,3,sheetIndex,"R");
    obj.S(20,3,sheetIndex,"S");
    obj.S(21,3,sheetIndex,"T");
    obj.S(22,3,sheetIndex,"U");
    obj.S(23,3,sheetIndex,"V");  
	obj.S(19,4,sheetIndex,"Ͷ�ʷ���״��");
	obj.Mergecells(19,4,23,4);
	obj.S(19,5,sheetIndex,"����");
    obj.S(20,5,sheetIndex,"��ע");
    obj.S(21,5,sheetIndex,"�μ�");
    obj.S(22,5,sheetIndex,"����");
    obj.S(23,5,sheetIndex,"��ʧ");  

	obj.S(24,3,sheetIndex,"W");
	obj.S(24,4,sheetIndex,"��ֵ׼��");
	obj.Mergecells(24,4,24,5);
	   
    for(var i=1;i<=24;i++){
	     if(i!=1 && i!=2){
	     	obj.SetCellNumType(i,6,sheetIndex,1);
			obj.SetCellSeparator(i,6,sheetIndex,2);
	     } 
	     
	     //��������̧ͷˮƽ��ֱ����    
	    obj.SetCellAlign (i,3,sheetIndex,32+4);  		 
	    obj.SetCellAlign (i,4,sheetIndex,32+4);
	    obj.SetCellAlign (i,5,sheetIndex,32+4);	      		            	    	    
    }
	var rows=obj.GetRows(sheetIndex);
	 
	 //���ñ߿���
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
   
 * S35���ڹ�ȨͶ�ʷ��������
 */
 function loadEndOfFundsManagexByMonthForS35(obj,cols,rows,op_name,sheetIndex){  
    if(sheetIndex==0){  
        var allRows=obj.GetRows(0);
        obj.InsertRow(obj.GetRows(0),2,sheetIndex);
    	obj.SetCellFontSize (1,allRows,sheetIndex,10);
		obj.Mergecells (1,allRows,4,allRows);
		obj.SetCellAlign (1,allRows,sheetIndex,32+1);
		obj.S (1,allRows,sheetIndex,"����ˣ�" + op_name); 
		obj.SetCellAlign (8,allRows,sheetIndex,32+1);
		obj.Mergecells (8,allRows,17,rows);
		obj.S (8,allRows,sheetIndex,"�����ˣ�");
		obj.SetCellAlign (19,allRows,sheetIndex,32+1);
		obj.Mergecells (19,allRows,24,rows);
		obj.S (19,allRows,sheetIndex,"�����ˣ�");
		obj.S (1,allRows+1,sheetIndex,"ע��[G]��[H]Ͷ��������ָ�Գɱ�����Ȩ�淨��������롣�ɱ��������Ϣ���룬Ȩ�淨��������");
		obj.Mergecells (1,allRows+1,24,allRows+1);
		
    }
  }
  
  /**
   ADD BY TSG 2008-02-01
   
 * S35���ڹ�ȨͶ�ʷ��������

 */
 
 function formatStatementsForS35(obj,sheetIndex){
	  if(sheetIndex==0){
		    document.theform.DCellWeb1.SetColWidth(1,30,1,sheetIndex); //���õ�һ�п�����У� 
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
		    
		
		    //������߿�Ϊ����--����
		    for(i=3;i<=lastrow-2;i++){
		       obj.SetCellBorder(1,i,sheetIndex,0,3);
		       obj.SetCellBorder(24,i,sheetIndex,2,3);
		    }
		    
		    //������߿�Ϊ����--����
		    for(i=1;i<=24;i++){
		       obj.SetCellBorder(i,3,sheetIndex,1,3);
		       obj.SetCellBorder(i,lastrow-2,sheetIndex,3,3);
		    }
		  

		    //���������Զ�����
		    for(row=4;row<=5;row++){
		      for(col=1;col<=24;col++){
		       obj.SetCellTextStyle(col,row,sheetIndex,2);
		      }
		    }
		    
		    //��������
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