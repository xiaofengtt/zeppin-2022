'CELL�ļ��Ҽ��˵��Ѿ�������Ӧ�¼�Դ����--Ҷ���� 2008-05-09
Sub SingleeReport_MenuStart( ByVal col, ByVal row, ByVal section )
    SingleeReport.GetSelectRange col1, row1, col2, row2
	select case section
	  case "1"
		SingleeReport.AddPopMenu 1000, "����", 0
		SingleeReport.AddPopMenu 1001, "����", 0
		SingleeReport.AddPopMenu 1002, "ճ��", 0
		
		SingleeReport.AddPopMenu 1003, "", 0
		
		SingleeReport.AddPopMenu 1101, "�������",0
		SingleeReport.AddPopMenu 1102, "�༭��ʽ",0
		SingleeReport.AddPopMenu 1103, "�������",0
		SingleeReport.AddPopMenu 1009, "�༭�����", 0
		
        if col2-col1>0 or row2-row1>0 then
          tp=0
        else
          tp=1
        end if
		SingleeReport.AddPopMenu 1004, "�ϲ���Ԫ��", tp
		SingleeReport.AddPopMenu 1005, "��ֵ�Ԫ��", tp
		
		SingleeReport.AddPopMenu 1100, "", 0
		SingleeReport.AddPopMenu 1109, "����ΪCell", 0
		SingleeReport.AddPopMenu 1108, "����ΪExcel", 0
	  case "2"
        SingleeReport.AddPopMenu 1010, "������", 0
		SingleeReport.AddPopMenu 1011, "ɾ����", 0
	  case "3"
	    SingleeReport.AddPopMenu 1020, "������", 0
        SingleeReport.AddPopMenu 1021, "ɾ����", 0
	  case "4"
	    SingleeReport.AddPopMenu 1030, "ȡ��ѡ��", 0
      case "5"
        SingleeReport.AddPopMenu 1040, "����ҳ", 0
        SingleeReport.AddPopMenu 1041, "ɾ��ҳ", 0
        SingleeReport.AddPopMenu 1043, "ҳ����", 0
        SingleeReport.AddPopMenu 1042, "ҳ��ǩ", 0
      case "6"
        SingleeReport.AddPopMenu 1050, "��һҳ", 0
        SingleeReport.AddPopMenu 1051, "��һҳ", 0
        SingleeReport.AddPopMenu 1052, "��һҳ", 0
        SingleeReport.AddPopMenu 1053, "ĩһҳ", 0
	end select
End Sub


Sub SingleeReport_MenuCommand( ByVal col, ByVal row, ByVal itemid )
    SingleeReport.GetSelectRange col1, row1, col2, row2
	select case itemid
	  case "1000"
	  SingleeReport.CutRange col1, row1, col2, row2
	  case "1001"
	  SingleeReport.CopyRange col1, row1, col2, row2
	  case "1002"
	  SingleeReport.Paste col, row, 0, 1, 0
      case "1004"
      SingleeReport.MergeCells col1, row1, col2, row2
      case "1005"
      SingleeReport.UnMergeCells col1, row1, col2, row2
	  case "1009"
      SingleeReport.DrawLineDlg
      
      case "1020"
      SingleeReport.InsertCol col+1, 1, SingleeReport.GetCurSheet
	  case "1021"
	  SingleeReport.DeleteCol col, 1, SingleeReport.GetCurSheet

      case "1010"
      SingleeReport.InsertRow row+1, 1, SingleeReport.GetCurSheet
      case "1011"
      SingleeReport.DeleteRow row, 1, SingleeReport.GetCurSheet

      case "1030"
      SingleeReport.ClearSelection
      SingleeReport.ReDraw

      case "1040"
      SingleeReport.InsertSheet SingleeReport.GetCurSheet+1, 1
      case "1041"
      SingleeReport.DeleteSheet SingleeReport.GetCurSheet, 1
	  case "1042"
      SingleeReport.SheetLabelRenameDlg
      case "1043"
      SingleeReport.SortStyleSheetDlg

      case "1050"
      SingleeReport.SetCurSheet 0
      case "1051"
      SingleeReport.SetCurSheet SingleeReport.GetCurSheet - 1
      case "1052"
      SingleeReport.SetCurSheet SingleeReport.GetCurSheet + 1
      case "1053"
      SingleeReport.SetCurSheet SingleeReport.GetTotalSheets - 1
      
      case "1108"
      SingleeReport.ExportExcelDlg
      case "1109"
      SingleeReport.SaveFile
      case "1101"
      SingleeReport.FillSerialDlg
      case "1102"
      SingleeReport.FormulaListDlg
      case "1103"
      SingleeReport.DefineVariableDlg
      
    end select

End Sub

function DecimalToBinary(DecimalValue,bit)
DecimalValue = Abs(DecimalValue)

Do
  result = CStr(DecimalValue Mod 2) & result
  DecimalValue = DecimalValue \ 2
Loop While DecimalValue > 0

While len(result) < bit
 result = "0" + result
wend
DecimalToBinary = result
end function

sub SingleeReport_AllowMove(oldcol, oldrow, col, row, approve) '�۽���Ԫ��
   'ȡ�ر�ѡ��Ԫ����
   fontindex = SingleeReport.GetCellFont(col,row,SingleeReport.GetCurSheet)
   fontname = SingleeReport.GetFontName(fontindex)
   for j = 0 to font_family.options.length-1
     if font_family.options(j).value=fontname then
       font_family.selectedIndex=j
       exit for
     end if
   next
   'ȡ�ر�ѡ��Ԫ����


   'ȡ�ر�ѡ��Ԫ����ߴ�
   fontsize = SingleeReport.GetCellFontSize(col,row,SingleeReport.GetCurSheet)
   for j = 0 to font_size.options.length-1
     if cint(font_size.options(j).value)=fontsize then
       font_size.selectedIndex=j
       exit for
     else
       font_size.selectedIndex=0
     end if
   next
   'ȡ�ر�ѡ��Ԫ����ߴ�


   'ȡ�ر�ѡ��Ԫ������ɫ
   fontcolor = SingleeReport.GetColor(SingleeReport.GetCellTextColor(col,row,SingleeReport.GetCurSheet))
   for j = 0 to font_color.options.length-1
     if font_color.options(j).value=cstr(fontcolor) then
       font_color.selectedIndex=j
       exit for
     else
       font_color.selectedIndex=0
     end if
   next
   'ȡ�ر�ѡ��Ԫ������ɫ


   'ȡ�ر�ѡ��Ԫ������ɫ
   fontcolor = SingleeReport.GetColor(SingleeReport.GetCellBackColor(col,row,SingleeReport.GetCurSheet))
   for j = 0 to bg_color.options.length-1
     if bg_color.options(j).value=cstr(fontcolor) then
       bg_color.selectedIndex=j
       exit for
     else
       bg_color.selectedIndex=0
     end if
   next
   'ȡ�ر�ѡ��Ԫ������ɫ


   'ȡ�ر�ѡ��Ԫ������ʽ
   fontstyle = DecimalToBinary(SingleeReport.GetCellFontStyle(col,row,SingleeReport.GetCurSheet),4)
   if mid(fontstyle,1,1)=1 then
     font_u.checked=true
   else
     font_u.checked=false
   end if
   if mid(fontstyle,2,1)=1 then
     font_i.checked=true
   else
     font_i.checked=false
   end if
   if mid(fontstyle,3,1)=1 then
     font_b.checked=true
   else
     font_b.checked=false
   end if
   'ȡ�ر�ѡ��Ԫ������ʽ


   'ȡ�ض��뷽ʽ
   fontalign = DecimalToBinary(SingleeReport.GetCellAlign(col,row,SingleeReport.GetCurSheet),6)
   if mid(fontalign,1,1)=1 then
     align_v.selectedIndex=1
   else
     if mid(fontalign,3,1)=1 then
       align_v.selectedIndex=0
     else
       align_v.selectedIndex=2
     end if
   end if

   if mid(fontalign,4,1)=1 then
     align_h.selectedIndex=1
   else
     if mid(fontalign,5,1)=1 then
       align_h.selectedIndex=2
     else
       align_h.selectedIndex=0
     end if
   end if

   'ȡ�ض��뷽ʽ

   'ȡ�ص�Ԫ����ı����
   fontview = SingleeReport.GetCellTextStyle(col,row,SingleeReport.GetCurSheet)
   if fontview=0 then fontview=1
   execute("n" + cstr(fontview) + ".checked = true")
   'ȡ�ص�Ԫ����ı����
   
	textDig.value=SingleeReport.GetCellDigital(col,row,SingleeReport.GetCurSheet)

	SelectSeparator.selectedIndex = SingleeReport.GetCellSeparator(col,row,SingleeReport.GetCurSheet)
	SelectInput.selectedIndex     = SingleeReport.GetCellInput(col,row,SingleeReport.GetCurSheet)
	SelectValidChars.selectedIndex= SingleeReport.GetCellValidChars(col,row,SingleeReport.GetCurSheet)
   
end sub

Sub window_onload
 
 SingleeReport.MoveToCell 2,2
 SingleeReport_AllowMove 0,0,2,2,1
 
End sub