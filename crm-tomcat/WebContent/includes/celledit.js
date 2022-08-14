// celledit.js �Զ��屨������Ĺ��ܺ��� -- Ҷ���� 2008-05-09

function save()
{
	if(confirm("����CELL�ļ���"))
	{
		document.theform.saveflag.value = 1;
		saveCellFile(SingleeReport,document.theform.saveflag.value);
	}
}

//����.cll�ļ�
function saveCellFile(obj,flag)
{
	if(flag == 1)
	{
		if(obj.SaveFile())
		{
			alert("����ɹ�");
		}
	}
}

function op_return()
{
	location = "openfile.jsp";
}

//ȡ��������Ϊ��Ч
function init_Undo(obj)
{
	obj.EnableUndo(1);
}

//����sheet
function insert_sheet(obj)
{
	var countSheet = obj.GetTotalSheets();//ͳ��Sheet����
	var countInsert = prompt("��������Ҫ���������","0");
	if(countInsert != null && countInsert > 0)
	{
		obj.InsertSheet(countSheet,countInsert);
		list_page(obj);
		alert("�ɹ����� " + countInsert + " ҳ!");
	}
	else
	{
		return false;
	}	
}
//ɾ��sheet
function delete_sheet(obj)
{
	if(confirm("ȷ��ɾ����ǰ��ѡ��Sheet��"))
	{
		var deleteIndex = obj.GetCurSheet();
		obj.DeleteSheet(deleteIndex,1);
		list_page(obj);
		alert("ɾ���ɹ���");
	}
	
}

//ˢ��ѡ��sheet�����˵�
function list_page(obj1)
{
	var num = obj1.GetTotalSheets();
	var page = document.getElementById("sheetpage");
	if(page == null)
	{
		alert("��ȷ��ҳ����� sheetpage �����˵���");
		return false;
	}
	if(num != null && num > 0)
	{
  		page.options.length = num;
  		for(i=0;i<=num-1;i++)
  		{
  			page.options(i).text = i+1;
  			page.options(i).value = i;
  		}
  		page.selectedIndex = obj1.GetCurSheet();
  	}
  	
}

//ѡ��sheet
function change_page(obj,value)
{
	var page = document.getElementById("sheetpage");
	if(page == null)
	{
		alert("��ȷ��ҳ����� sheetpage �����˵���");
		return false;
	}
	obj.SetCurSheet(value);
}

//�����¼���ʱ���CTRL+Z CTRL+Y
function keyDown()
{
	var ctrlKey = event.ctrlKey;
	var keyCode = event.keyCode;
	if((ctrlKey == true) && (keyCode == 90))
	{
		SingleeReport.Undo();
	}
	if((ctrlKey == true) && (keyCode == 89))
	{
		SingleeReport.ReDo();
	}
}
document.onkeydown = keyDown;

///��ʼ�������ʽ
function InitFontname(obj)
 {
  var strFontnames = obj.GetDisplayFontnames();
  var arrFontname = strFontnames.split('|');
  arrFontname.sort();
  var i;
  var sysFont;
  if(obj.GetSysLangID () == 2052)
	sysFont = "����";
  else sysFont = "Arial";

  for(i = 0; i < arrFontname.length;i++ )
   {
	var oOption = document.createElement("OPTION");
	font_family.options.add(oOption);
	oOption.innerText = arrFontname[i];
	oOption.value = arrFontname[i];
	if( arrFontname[i] == sysFont ) oOption.selected = true;
   }
}

//������������
function font_familyex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	obj.SetCellFont(col,row,obj.GetCurSheet(),obj.FindFontIndex(tp,1));
}

//�������ֳߴ�  
function font_sizeex(obj,tp) 
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	obj.SetCellFontSize(col,row,obj.GetCurSheet(),tp);
} 

//����������ɫ
function font_colorex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	obj.SetCellTextColor(col,row,obj.GetCurSheet(),obj.FindColorIndex(tp,1));
}

//���ñ�����ɫ
function bg_colorex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
//	alert(obj.GetColor(obj.GetCellBackColor(col,row,obj.GetCurSheet())));
	obj.SetCellBackColor(col,row,obj.GetCurSheet(),obj.FindColorIndex(tp,1));
}

//����������ʽ
function font_style(obj,tp)
{
  	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var font_b = document.getElementById("font_b");
	var font_i = document.getElementById("font_i");
	var font_u = document.getElementById("font_u");
	
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	
	if(font_b == null)
	{
		alert("��ȷ��ҳ����� font_b ��ť��");
		return false;
	}
	
	if(font_i == null)
	{
		alert("��ȷ��ҳ����� font_i ��ť��");
		return false;
	}
	
	if(font_u == null)
	{
		alert("��ȷ��ҳ����� font_u ��ť��");
		return false;
	}
	
	fontstyle = obj.GetCellFontStyle(col,row,obj.GetCurSheet());
	
	if(tp == 2)
    {
    	if(font_b.checked)
    	{
      		obj.SetCellFontStyle(col,row,obj.GetCurSheet(),fontstyle + tp);
      	}
    	else
    	{
      		obj.SetCellFontStyle(col,row,obj.GetCurSheet(),fontstyle - tp);
      	}
    }
    if(tp == 4)
    {
    	if(font_i.checked)
    	{
    		obj.SetCellFontStyle(col,row,obj.GetCurSheet(),fontstyle + tp);
    	}
    	else
    	{
			obj.SetCellFontStyle(col,row,obj.GetCurSheet(),fontstyle - tp);
		}
    }
    if(tp == 8)
    {
    	if(font_u.checked)
    	{
    		obj.SetCellfontStyle(col,row,obj.GetCurSheet(),fontstyle + tp);
    	}
		else
		{
			obj.SetCellFontStyle(col,row,obj.GetCurSheet(),fontstyle - tp);
		}
    }
}

//���ö��뷽ʽ
function font_align(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	
	var align_h = document.getElementById("align_h");
	var align_v = document.getElementById("align_v");
	
	if(align_h == null)
	{
		alert("��ȷ��ҳ����� align_h �����˵���");
		return false;
	}
	
	if(align_v == null)
	{
		alert("��ȷ��ҳ����� align_v �����˵���");
		return false;
	}
	
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	
	var alignValue = Math.round(parseInt(align_h.options(align_h.selectedIndex).value) + parseInt(align_v.options(align_v.selectedIndex).value));
	obj.SetCellAlign(col,row,obj.GetCurSheet(),alignValue);
}

//���õ�Ԫ��������ʾ��ʽ
function font_viewex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	obj.SetCellTextStyle(col,row,obj.GetCurSheet(),tp);
}

//����С��λ��
function bOKClick(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var textDig = document.getElementById("textDig");
	
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
    obj.SetCellNumType(col,row,obj.GetCurSheet(),1);
	if(0 <= parseInt(textDig.value) && parseInt(textDig.value) <= 15)
	{
		obj.SetCellDigital(col,row,obj.GetCurSheet(),textDig.value);
	}
    else
    {
		alert("������ 0-15 �������");
		return false;
	}
}
//����ǧ��λ�ָ���  
function SelectSeparatorClick(obj)
{
   	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var SelectSeparator = document.getElementById("SelectSeparator");
	
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	
    obj.SetCellSeparator(col,row,obj.GetCurSheet(),SelectSeparator.selectedIndex);
}
//������������
function SelectInputClick(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var SelectInput = document.getElementById("SelectInput");
	
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
    obj.SetCellInput(col,row,obj.GetCurSheet(),SelectInput.selectedIndex); 
	
}
//���õ�Ԫ���������������
function SelectValidCharsClick(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var SelectValidChars = document.getElementById("SelectValidChars");
	
	if(col == null || row == null)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("��ѡѡ����Ԫ��");
		return false;
	}
    obj.SetCellValidChars(col,row,obj.GetCurSheet(),SelectValidChars.selectedIndex); 
}
//������
function drawLine(obj)
{
	obj.DrawLineDlg();
}