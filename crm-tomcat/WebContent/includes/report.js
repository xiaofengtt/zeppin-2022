//Create By CaiYuan 2005
//---------------------------һЩ��ʾ����-----------------------------------------
function rp_checkOpen(fileName)
{
	if(fileName=="")
	{
		rp_alert("��������ű���cll�ļ�·������ȷ,δ���κα����ļ���");
		return false;
	}
}

function rp_alert(errinfo)
{
	alert("ϵͳ��ʾ����������\n\n" + errinfo + "\n\n");
}	

function login(obj)
{
	if(obj.Login( "�����±������ϵͳ","" ,"13040468", "3600-1505-7761-0005" ) == 0 )	
	{
		rp_alert( "ע��CELL���ʧ��!");
		return false;
	}
}
//---------------------------������ع��ܲ���--------------------------------------
//���ļ�
function OpenReport()
{	
    ret = showModalDialog('openfile.jsp','','dialogTop:120px;dialogLeft:20px;dialogWidth:390px;dialogHeight:200px;status:0;help:0');
    if(ret!=null&&ret!="")
    {    	
    	ret = changeSeperator(ret);     		
    		location.href = "init.jsp?filename=" + ret ;
    }	
}

//��ӡԤ��
function PrintPreview(flag)
{
	login(SingleeReport);	
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,80,120,80);	
	if(flag!=null)	//��ӡȫ��
	{
		SingleeReport.PrintPreview( 0, SingleeReport.GetCurSheet());	//��ӡԤ��
	}	
	else
		SingleeReport.PrintPreview( 0, 1);	//��ӡԤ��
}

//��ӡ
function PrintReport(flag)
{	
	login(SingleeReport);		
	SingleeReport.PrintPara( 1,0,0,0);
	SingleeReport.PrintSetMargin(80,80,120,80);
	if(flag!=null)	//��ӡȫ��
	{
		SingleeReport.PrintSetPrintRange(0,0);
		SingleeReport.PrintSheet( 0, -1);	//��ӡ
	}	
	else
		SingleeReport.PrintSheet( 0, 1);	//��ӡ
		
}

//ѡ���Ʒ
function ChooseProduct()
{	
    ret = showModalDialog('chooseproduct.jsp','','dialogTop:120px;dialogLeft:500px;dialogWidth:460px;dialogHeight:500px;status:0;help:0');
	
}
//-----------------------------------�����ļ����-----------------------------------------------------------------
//���ڱ���ؼ���JS����ͬһ��ҳ�棬������Ҫ��OBJ������
function dl_alert(fileName)
{
	alert("�����ļ��ɹ���\n\n�ļ�����"+fileName);

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

//����HTML
function ExportHTML(obj,fileName)
{
	rp_alert("�˹���Ŀǰ�ݲ�֧�֣�");
	//if(obj.ExportHtmlFile(fileName)==1)
	//	dl_alert(fileName);
	//else
	//	alert("����ʧ�ܣ�");
}

//����PDF
function ExportPDF(obj,fileName,sheet,flag)
{	
	login(obj);
	fileName = fileName + ".pdf";	
	obj.PrintPara( 1,0,0,0);
	obj.PrintSetMargin(40,40,120,40);
	var successFlag = 0;
	if(flag!=null&&flag==1)//����ȫ��	
		successFlag = obj.ExportPdfFile(fileName,0,0,obj.GetTotalSheets());	
	else
	 	successFlag = obj.ExportPdfFile(fileName,sheet,0,obj.PrintGetPages(sheet));
	if(successFlag==1)
		dl_alert(fileName);
	else
		rp_alert("����ʧ�ܣ�");		
}	
//-------------------------------------------------------------------------------
//�����Զ�������õ�ʵ������--����������(����ĸת��������,�൱��һ��26������ת����10������)
function getParamPos(flag) 
{		
	var pos;	//����λ	
	var num = 0; 	//���ص���������ֵ
	var abc;	//��ȡ������ĸ
	var charTonum; 	//��ĸת���Ķ�Ӧ����ֵ
	var i;
	for(i=0;i<flag.length;i++)
	{	
		if(flag.substring(i,i+1)>="0"&&flag.substring(i,i+1)<="9")
		{
			pos = i;
			break;
		}	
	}			
	abc = flag.substring(0,pos);	//ȡ����ĸ
	abc = abc.toUpperCase();	//ͳһת���ɴ�д��ĸ		
	for(i=abc.length;i>0;i--)
	{
		charTonum = abc.charCodeAt(i-1);
			num = Math.round(Math.pow(26,(abc.length-i))) *( charTonum - 64 ) + num;
	}	
	return num;	
}
//�����Զ�������õ�������
function getNumPos(flag) 
{		
	var pos;	//����λ	
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
//���Ƶ�Ԫ���ʽ---������
function CopyCellStyle_Col(ReportName,Col,Row,Sheet)
{
	ReportName.SetCellAlign(Col,Row,Sheet,ReportName.GetCellAlign(Col,Row-1,Sheet));
	ReportName.SetCellTextStyle(Col,Row,Sheet,ReportName.GetCellTextStyle(Col,Row-1,Sheet));
	ReportName.SetCellNumType(Col,Row,Sheet,ReportName.GetCellNumType(Col,Row-1,Sheet));
}

//���Ƶ�Ԫ���ʽ---������
function CopyCellStyle_Row(ReportName,Col,Row,Sheet)
{
	ReportName.SetCellAlign(Col,Row,Sheet,ReportName.GetCellAlign(Col-1,Row,Sheet));
	ReportName.SetCellTextStyle(Col,Row,Sheet,ReportName.GetCellTextStyle(Col-1,Row,Sheet));
	ReportName.SetCellNumType(Col,Row,Sheet,ReportName.GetCellNumType(Col-1,Row,Sheet));
}

//����Ԫ����--�����
function SetCellWidth(ReportName,Sheet)
{
	var i,width;
	var cols = ReportName.GetCols(Sheet);
	for(i=1;i<=cols;i++)
	{
		width = ReportName.GetColBestWidth(i);
		ReportName.SetColWidth(1,width-3,i,Sheet);  //�����ӡ����һҳ���� -3
	}
}

//ȫֵ�ϲ�---��ȫֵ�ϲ�ʱҪ�Ƚ���ȫֵ�ϲ�
function MergeLeftRight(mergeCol,startRow,endRow)
{
	var beginRow,CursorRow; //ȫֵ�ϲ�ʱ�õ���ѭ������,һ����ʼ�У�һ�����α�
	//ѭ���ж����һ�дӿ�ʼ�����������е�ֵ�Ƿ���ȣ���ȵ���ϲ��ұߵĵ�Ԫ��
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

//��"\"���"/"�û�ȡ�ļ�·��
function changeSeperator(fileName)
{
	return fileName.replace(/\\/g,'/');	
}

//��"\"���"/"�û�ȡ�ļ�·��
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
		sl_alert("δѡ���κμ�¼��");
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("��ѡ����ز�Ʒ��");
		return false;
	}
	return true;	
}

//�ж��ļ���չ����ֻ��.cll�ļ������ϴ�
function checkCellName(filename)
{	
	var pos = filename.lastIndexOf(".");
	var ext = filename.substring(pos+1).toUpperCase();	
	if(pos<0)
	{
		rp_alert("����ѡ�񱨱��ļ���");
		return false;
	}
	if(filename.indexOf("\\")==filename.lastIndexOf("\\"))
	{
		rp_alert("�����ϴ���Ŀ¼�ļ���");
		return false;
	}
	else if(filename.indexOf("@")<0)
	{
		rp_alert("�����ļ����Ʋ��Ϸ���");
		return false;
	}
	else if(ext!="CLL")
	{
		rp_alert("��CLL�ļ������ϴ���");
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
			//rp_alert(name + "�������������֣�");				
			return false;			
		}
	}
	return true;
}

//��ӡ��ͷ����ȡֵ����ȷ���ж�-------------------------------------
function getPrintTitle(name,value)
{	
	var pos = value.indexOf(":");
	var leftValue,rightValue;
	var ret = false;
	if(pos>0&&pos<value.length)
	{
		leftValue = value.substring(0,pos);
		rightValue = value.substring(pos+1);
		if(sl_checkNum(name+"��ʼ�е��к�",leftValue)&&sl_checkNum(name+"�����е��к�",rightValue))		
			ret = new Array(leftValue,rightValue);		
	}
	return	ret; 	
}

//��ӡҳü��ҳ�Ų���ȡֵ����ȷ���ж�
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
		if(leftValue=="��ͷ�к�")
		{
		    value = getPrintTitle("��ͷ�к�",rightValue);
		    if(value!=false)		 		    		    
		    	obj.PrintSetTopTitle(value[0],value[1]);
		}
		else if(leftValue=="�����к�")
		{
		    value = getPrintTitle("�����к�",rightValue);
		    if(value!=false)		 			    
		    	obj.PrintSetLeftTitle(value[0],value[1]);
		}
		else if(leftValue=="�����к�")
		{
		    value = getPrintTitle("�����к�",rightValue);
		    if(value!=false)		 			    
		    	obj.PrintSetRightTitle(value[0],value[1]);
		}
		else if(leftValue=="��β�к�")
		{
		    value = getPrintTitle("��β�к�",rightValue);
		    if(value!=false)		 			    		    
		    	obj.PrintSetBottomTitle(value[0],value[1]);
		}
		else if(leftValue=="ҳü")
		{
		    value = getPrintHead("ҳü",rightValue);		   
		    if(value!=false)		 	    		    
		    	obj.PrintSetHead(value[0],value[1],value[2]);
		}
		else if(leftValue=="ҳ��")
		{
		    value = getPrintHead("ҳ��",rightValue);
		    if(value!=false)		 			    
		    	obj.PrintSetFoot(value[0],value[1],value[2]);
		}
}

//
function rp_checkFileStatus(statusValue){
	if(statusValue == -1){
		rp_alert("��������ű���cll�ļ�·������ȷ,δ���κα����ļ���");
		return false;
	}else if(statusValue == -2){
		rp_alert("cll�ļ���������");
		return false;
	}else if(statusValue == -3){
		rp_alert("cll�ļ���ʽ����");
		return false;
	}else if(statusValue == -4){
		rp_alert("cll�ļ��������");
		return false;
	}else if(statusValue == -5){
		rp_alert("���ܴ򿪸߰汾cll�ļ���");
		return false;
	}else if(statusValue == -6){
		rp_alert("���ܴ��ض��汾cll�ļ���");
		return false;
	}
}