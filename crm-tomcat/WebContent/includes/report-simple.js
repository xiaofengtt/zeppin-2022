var SingleeReport ;	//�������

//���屨�����ö���
function ConfigVO(){

	this.CELL_FILE ;	//�����ļ�
	this.ROW_START = 4;	//������ʼ�к�
	this.CUR_SHEET;	//��ǰ��ҳ��ҳ��
	this.ROW_HEIGHT;	//�и�
	this.columnMap = new Array();	//������ֶ�ӳ��
	this.columnEx = new Array();	//�ֶζ��⴦��
	this.columnNumType = new Array();	//��Ԫ���ʽ	
	this.columnFont = new Array();	//��Ԫ������
}

var reportConfig;	//�½������ļ�����

/**
 * ��ȡģ���ļ�
 * @param templateFile ģ���ļ�·��
 * @param sheetName ��ҳ���� 
 */
function initReport(templateFile,obj,sheetName)
{
	SingleeReport = obj;
	reportConfig = new ConfigVO();	//�½������ļ�����
	SingleeReport.OpenFile(templateFile,"");
	rp_checkOpen(SingleeReport.GetFileName());	//����ļ��Ƿ����
	
	SingleeReport.SetCurSheetEx(sheetName);	//��ȡ����ҳ
	reportConfig.CELL_FILE = templateFile;
	reportConfig.CUR_SHEET = SingleeReport.GetCurSheet();	//��õ�ǰ��ҳ��ҳ��
	SingleeReport.ShowSheetLabel(0,reportConfig.CUR_SHEET); 	//����ҳǩ	
	SingleeReport.WorkbookReadonly = true;	//���ñ�ҳֻ��

	var i=1;
	while(SingleeReport.GetCellString(i,reportConfig.ROW_START,reportConfig.CUR_SHEET)!="")
	{
		reportConfig.columnMap.push(SingleeReport.GetCellString(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));	//��ȡ������ֶ�ӳ��
		reportConfig.columnEx.push(SingleeReport.GetCellTip(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));//��ע�Ͳ���
		reportConfig.columnNumType.push(SingleeReport.GetCellNumType(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));	//����Ԫ���ʽ
		reportConfig.columnFont.push(SingleeReport.GetCellFont(i,reportConfig.ROW_START,reportConfig.CUR_SHEET));	//����Ԫ������
		i++;
	}
	reportConfig.ROW_HEIGHT = SingleeReport.GetRowHeight(0,reportConfig.ROW_START,reportConfig.CUR_SHEET);	
}

/**
 * ���¶�ȡ�����ļ�������ȡ������Ϣ��
 */
function refreshReport(templateFile,sheet)
{
	SingleeReport.OpenFile(templateFile,"");
	rp_checkOpen(SingleeReport.GetFileName());	//����ļ��Ƿ����
	
	SingleeReport.SetCurSheet(sheet);	//��ȡ����ҳ
	SingleeReport.ShowSheetLabel(0,reportConfig.CUR_SHEET); 	//����ҳǩ	
	SingleeReport.WorkbookReadonly = true;	//���ñ�ҳֻ��
}

/**
 * ��̬���Ļص�����
 */
function cellCallback(data)
{
	refreshReport(reportConfig.CELL_FILE,reportConfig.CUR_SHEET);
	
	var rowCount = data.length;	
	//һ�β������п���
	SingleeReport.InsertRow(reportConfig.ROW_START+1,rowCount,reportConfig.CUR_SHEET);	
	SingleeReport.DeleteRow(reportConfig.ROW_START,1,reportConfig.CUR_SHEET);	//ɾ��������
	for(i=0;i<rowCount;i++)
	{
		for(j=0;j<reportConfig.columnMap.length;j++)
		{		
			
			//������ʾ��ʽ---������ʾ���ı��Զ�����
			//SingleeReport.SetCellTextStyle(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,2);			
			//���Ƶ�Ԫ���ʽ
			SingleeReport.SetCellNumType(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,reportConfig.columnNumType[j]);	
			//���Ƶ�Ԫ������
			//SingleeReport.SetCellFont(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,reportConfig.columnFont[j]);
			//�����и�					
			SingleeReport.SetRowHeight(0,reportConfig.ROW_HEIGHT,reportConfig.ROW_START+i,reportConfig.CUR_SHEET);		

			if(reportConfig.columnNumType[j]==1)	
				SingleeReport.D(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,parseFloat(data[i][reportConfig.columnMap[j]]));	
			else	
				SingleeReport.S(j+1,reportConfig.ROW_START+i,reportConfig.CUR_SHEET,data[i][reportConfig.columnMap[j]]);
		}
	}
	//�ϲ���Ԫ��
	for(i=0;i<reportConfig.columnEx.length;i++)
	{			
		if(reportConfig.columnEx[i]==null||reportConfig.columnEx[i]=="")
		{
			continue;		
		}	
		else if(reportConfig.columnEx[i]=="�ϲ�")	//��Ԫ����Ҫ�ϲ�
		{
			var mergeRowStart = reportConfig.ROW_START;
			var mergeRowEnd = reportConfig.ROW_START;
			var mergeValue = SingleeReport.GetCellString(i+1,reportConfig.ROW_START,reportConfig.CUR_SHEET);//��Ҫ�ϲ���ֵ		
			var thisRowValue;	//��ǰ�е�ֵ
			for(j=1;j<rowCount+1;j++)
			{
				thisRowValue = SingleeReport.GetCellString(i+1,reportConfig.ROW_START+j,reportConfig.CUR_SHEET);
				if(thisRowValue!=mergeValue)	//��һ����Ҫ�ϲ�
				{			
					
					if(mergeRowEnd>mergeRowStart)	//ȷʵ�Ƕ��еĻ�
					{
						SingleeReport.MergeCells(i+1,mergeRowStart,i+1,mergeRowEnd);
						
						mergeRowStart = reportConfig.ROW_START+j;
						mergeRowEnd = mergeRowStart;	
						mergeValue = SingleeReport.GetCellString(i+1,mergeRowStart,reportConfig.CUR_SHEET);	//�µ���Ҫ�ϲ���ֵ					
					}	
				}
				else
				{
					mergeRowEnd++;
				}
			}
			
		}
	}
	
	//setCellBestWidth();	//������ѿ�ȣ��ù�����ȡ������ģ���ж����ȣ��������У�
	showWaiting(0); //�رս�����
	document.getElementById("cellReport").style.display="";
}

function rp_checkOpen(fileName)
{
	if(fileName=="")
	{
		sl_alert("δ���κα����ļ���");
		return false;
	}
}

/**
 * ע��
 */
function login(obj)
{
	if(obj.Login( "�����±������ϵͳ","" ,"13040468", "3600-1505-7761-0005" ) == 0 )	
	{
		sl_alert( "ע��CELL���ʧ��!");
		return false;
	}
}

//��ӡԤ��
function printPreview()
{
	login(SingleeReport);	
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,40,120,80);
	SingleeReport.PrintPreview( 0, reportConfig.CUR_SHEET);	//��ӡԤ��
}

//��ӡ
function printReport()
{	
	login(SingleeReport);		
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,40,120,80);
	SingleeReport.PrintSheet (0, reportConfig.CUR_SHEET );	//��ӡ
}

//����Ԫ����--�����
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
  *  ��������
  */
function exportReport()
{
	var name = SingleeReport.GetCellString(1,1,reportConfig.CUR_SHEET);	
	showModalDialog('export.jsp?name='+ name+'&sheet='+reportConfig.CUR_SHEET,SingleeReport,'dialogTop:120px;dialogLeft:500px;dialogWidth:360px;dialogHeight:250px;status:0;help:0');					
}

//����Excel
function ExportExcel(obj)
{	
	login(obj);
	if(obj.ExportExcelDlg()==1)
		sl_alert("����Excel�ļ��ɹ���");
}

//����Cell
function ExportCell(obj,sheet)
{		
	login(obj);	
	if(obj.SaveSheet(sheet)==1)
	{		
		obj.CloseFile();
		sl_alert("����Cell�ļ��ɹ���");
	}
}

//����TXT
function ExportTXT(obj)
{	
	if(obj.ExportTextDlg()==1)
		sl_alert("�����ļ��ɹ���");		
}

//����HTML
function ExportHTML(obj,fileName)
{
	sl_alert("�˹���Ŀǰ�ݲ�֧�֣�");
	//if(obj.ExportHtmlFile(fileName)==1)
	//	dl_alert(fileName);
	//else
	//	alert("����ʧ�ܣ�");
}

//����PDF
function ExportPDF(obj,fileName,sheet)
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);
	if(obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet))==1)
		alert("�����ļ��ɹ���\n\n�ļ�����"+fileName);
	else
		sl_alert("����ʧ�ܣ�");		
}	