// celledit.js 自定义报表操作的功能函数 -- 叶立飞 2008-05-09

function save()
{
	if(confirm("保存CELL文件？"))
	{
		document.theform.saveflag.value = 1;
		saveCellFile(SingleeReport,document.theform.saveflag.value);
	}
}

//保存.cll文件
function saveCellFile(obj,flag)
{
	if(flag == 1)
	{
		if(obj.SaveFile())
		{
			alert("保存成功");
		}
	}
}

function op_return()
{
	location = "openfile.jsp";
}

//取消操作置为有效
function init_Undo(obj)
{
	obj.EnableUndo(1);
}

//插入sheet
function insert_sheet(obj)
{
	var countSheet = obj.GetTotalSheets();//统计Sheet总数
	var countInsert = prompt("请输入需要插入的列数","0");
	if(countInsert != null && countInsert > 0)
	{
		obj.InsertSheet(countSheet,countInsert);
		list_page(obj);
		alert("成功插入 " + countInsert + " 页!");
	}
	else
	{
		return false;
	}	
}
//删除sheet
function delete_sheet(obj)
{
	if(confirm("确认删除当前所选的Sheet？"))
	{
		var deleteIndex = obj.GetCurSheet();
		obj.DeleteSheet(deleteIndex,1);
		list_page(obj);
		alert("删除成功！");
	}
	
}

//刷新选择sheet下拉菜单
function list_page(obj1)
{
	var num = obj1.GetTotalSheets();
	var page = document.getElementById("sheetpage");
	if(page == null)
	{
		alert("请确认页面存在 sheetpage 下拉菜单！");
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

//选择sheet
function change_page(obj,value)
{
	var page = document.getElementById("sheetpage");
	if(page == null)
	{
		alert("请确认页面存在 sheetpage 下拉菜单！");
		return false;
	}
	obj.SetCurSheet(value);
}

//当按下键盘时检测CTRL+Z CTRL+Y
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

///初始化字体格式
function InitFontname(obj)
 {
  var strFontnames = obj.GetDisplayFontnames();
  var arrFontname = strFontnames.split('|');
  arrFontname.sort();
  var i;
  var sysFont;
  if(obj.GetSysLangID () == 2052)
	sysFont = "宋体";
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

//设置文字字体
function font_familyex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
	obj.SetCellFont(col,row,obj.GetCurSheet(),obj.FindFontIndex(tp,1));
}

//设置文字尺寸  
function font_sizeex(obj,tp) 
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
	obj.SetCellFontSize(col,row,obj.GetCurSheet(),tp);
} 

//设置文字颜色
function font_colorex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
	obj.SetCellTextColor(col,row,obj.GetCurSheet(),obj.FindColorIndex(tp,1));
}

//设置背景颜色
function bg_colorex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
//	alert(obj.GetColor(obj.GetCellBackColor(col,row,obj.GetCurSheet())));
	obj.SetCellBackColor(col,row,obj.GetCurSheet(),obj.FindColorIndex(tp,1));
}

//设置文字样式
function font_style(obj,tp)
{
  	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var font_b = document.getElementById("font_b");
	var font_i = document.getElementById("font_i");
	var font_u = document.getElementById("font_u");
	
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
	
	if(font_b == null)
	{
		alert("请确认页面存在 font_b 按钮！");
		return false;
	}
	
	if(font_i == null)
	{
		alert("请确认页面存在 font_i 按钮！");
		return false;
	}
	
	if(font_u == null)
	{
		alert("请确认页面存在 font_u 按钮！");
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

//设置对齐方式
function font_align(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	
	var align_h = document.getElementById("align_h");
	var align_v = document.getElementById("align_v");
	
	if(align_h == null)
	{
		alert("请确认页面存在 align_h 下拉菜单！");
		return false;
	}
	
	if(align_v == null)
	{
		alert("请确认页面存在 align_v 下拉菜单！");
		return false;
	}
	
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
	
	var alignValue = Math.round(parseInt(align_h.options(align_h.selectedIndex).value) + parseInt(align_v.options(align_v.selectedIndex).value));
	obj.SetCellAlign(col,row,obj.GetCurSheet(),alignValue);
}

//设置单元格文字显示方式
function font_viewex(obj,tp)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
	obj.SetCellTextStyle(col,row,obj.GetCurSheet(),tp);
}

//设置小数位数
function bOKClick(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var textDig = document.getElementById("textDig");
	
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
    obj.SetCellNumType(col,row,obj.GetCurSheet(),1);
	if(0 <= parseInt(textDig.value) && parseInt(textDig.value) <= 15)
	{
		obj.SetCellDigital(col,row,obj.GetCurSheet(),textDig.value);
	}
    else
    {
		alert("请输入 0-15 间的整数");
		return false;
	}
}
//设置千分位分隔符  
function SelectSeparatorClick(obj)
{
   	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var SelectSeparator = document.getElementById("SelectSeparator");
	
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
	
    obj.SetCellSeparator(col,row,obj.GetCurSheet(),SelectSeparator.selectedIndex);
}
//设置输入限制
function SelectInputClick(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var SelectInput = document.getElementById("SelectInput");
	
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
    obj.SetCellInput(col,row,obj.GetCurSheet(),SelectInput.selectedIndex); 
	
}
//设置单元格允许输入的类型
function SelectValidCharsClick(obj)
{
	var col = obj.GetCurrentCol();
	var row = obj.GetCurrentRow();
	var SelectValidChars = document.getElementById("SelectValidChars");
	
	if(col == null || row == null)
	{
		alert("请选选定单元格");
		return false;
	}
	else if(col<0 || row<0)
	{
		alert("请选选定单元格");
		return false;
	}
    obj.SetCellValidChars(col,row,obj.GetCurSheet(),SelectValidChars.selectedIndex); 
}
//网格线
function drawLine(obj)
{
	obj.DrawLineDlg();
}