'**************************************************
'		�ļ��˵�
'**************************************************
'�½�
Public Sub mnuFileNew_click()
	If SingleeReport.IsModified() Then '�ĵ��Ѿ�������
		rtn = MsgBox( "�ĵ��ѱ����ģ��Ƿ񱣴棿", vbExclamation Or vbYesNoCancel)
		If rtn = vbYes Then
			mnuFileSave_click
		ElseIf rtn = vbCancel Then
			Exit Sub
		End If
	End If
	SingleeReport.ResetContent
End Sub

'�򿪱����ĵ�
Public Sub mnuFileOpen_click()
	SingleeReport.OpenFile "", ""
End Sub

'��Զ���ĵ�
Public Sub mnuFileWebOpen_click()
	strFilename = InputBox( "������Զ�̷������ϵĻ����ļ���", "�򿪻����ļ�", "HTTP://" )
	If strFilename <> "" Then SingleeReport.OpenFile strFilename, ""
End Sub

'����
Public Sub mnuFileSave_click()
	SingleeReport.SaveFile
End Sub

'��ҳ���Ϊ
Public Sub mnuFileSheetSaveAs_click()
	SingleeReport.SaveSheet SingleeReport.GetCurSheet
End Sub

'�����ı��ļ�
Public Sub mnuFileImportText_click()
	SingleeReport.ImportTextDlg
End Sub

'����CSV�ļ�
Public Sub mnuFileImportCSV_click()
	On Error Resume Next
	Dim strOpenFileName
	CommonDialog1.Flags = &H4
	CommonDialog1.Filter = "CSV�����ŷָ����ļ�(*.csv)|*.csv|�����ļ� (*.*)|*.*"
	CommonDialog1.FilterIndex = 0
	
	CommonDialog1.Filename = ""
	CommonDialog1.ShowOpen
	If Err <> 32755 Then    ' �û�ѡ��ȡ������
		strOpenFileName = CommonDialog1.Filename
		SingleeReport.ImportCSVFile strOpenFileName, SingleeReport.GetCurSheet()
	End If
End Sub 

'����Excel�ļ�
Public Sub mnuFileImportExcel_click()
	SingleeReport.ImportExcelDlg
End Sub

'����ı��ļ�
Public Sub mnuFileExportText_click()
	SingleeReport.ExportTextDlg
End Sub

'���CSV�ļ�
Public Sub mnuFileExportCSV_click()
	' ���ѡ��ȡ�������򷵻ؿ��ַ�����
	On Error Resume Next
	Dim Filename
	Filename = "�±���.csv"
	CommonDialog1.Flags = &H4 Or &H2 Or &H10 Or &H800
	CommonDialog1.DefaultExt = ".csv"
	CommonDialog1.DialogTitle = "���CSV�ļ�"
	CommonDialog1.Filter = "CSV�����ŷָ����ļ�(*.csv)|*.csv|�����ļ� (*.*)|*.*"
	CommonDialog1.FilterIndex = 0
	CommonDialog1.Filename = Filename
	CommonDialog1.ShowSave
	If Err <> 32755 Then    ' �û�ѡ��ȡ������
		Filename = CommonDialog1.Filename
	Else
		Filename = ""
	End If
	If Filename <> "" Then
		SingleeReport.ExportCSVFile Filename, SingleeReport.GetCurSheet()
	End If
End Sub

'���Excel�ļ�
Public Sub mnuFileExportExcel_click()
	SingleeReport.ExportExcelDlg
End Sub

'���PDF�ļ�
Public Sub mnuFileExportPDF_click()
	' ���ѡ��ȡ�������򷵻ؿ��ַ�����
	On Error Resume Next
	Dim Filename
	Filename = "�±���.pdf"
	CommonDialog1.Flags = &H4 Or &H2 Or &H10 Or &H800
	CommonDialog1.DefaultExt = ".pdf"
	CommonDialog1.DialogTitle = "���PDF�ļ�"
	CommonDialog1.Filter = "Adobe PDF �ļ�(*.pdf)|*.pdf|�����ļ� (*.*)|*.*"
	CommonDialog1.FilterIndex = 0
	CommonDialog1.Filename = Filename
	CommonDialog1.ShowSave
	If Err <> 32755 Then    ' �û�ѡ��ȡ������
		Filename = CommonDialog1.Filename
	Else
		Filename = ""
	End If
	If Filename <> "" Then
		Dim CurSheet
		Dim Pages
		CurSheet = SingleeReport.GetCurSheet()
		Pages = SingleeReport.PrintGetPages(CurSheet)
		SingleeReport.ExportPdfFile Filename, CurSheet, 0, Pages
	End If
End Sub

'ҳ������
Public Sub mnuFilePageSetup_click()
	SingleeReport.PrintPageSetup
End Sub

'��ӡԤ��
Public Sub mnuFilePrintPreview_click()
	SingleeReport.PrintPreview True, SingleeReport.GetCurSheet
End Sub

'��ӡ
Public Sub mnuFilePrint_click()
	SingleeReport.PrintSheet True, SingleeReport.GetCurSheet
End Sub

'�˳�
Public Sub mnuFileExit_click()
	If SingleeReport.IsModified() Then
		rtn = MsgBox( "�ĵ��ѱ����ģ��Ƿ񱣴棿", vbExclamation or vbYesNoCancel)
		If rtn = vbYes Then
			mnuFileSave_click
		ElseIf rtn = vbCancel Then
			Exit Sub
		End If
	End If
	window.parent.close
End Sub

'**************************************************
'		�༭�˵�
'**************************************************
'��������
Public Sub mnuEditUndo_click()
	SingleeReport.Undo
End Sub

'���²���
Public Sub mnuEditRedo_click()
	SingleeReport.Redo
End Sub

'���в���
Public Sub mnuEditCut_click()
 	SingleeReport.GetSelectRange Startcol, Startrow, Endcol, Endrow
    	SingleeReport.CutRange Startcol, Startrow, Endcol, Endrow
End Sub

'���Ʋ���
Public Sub mnuEditCopy_click()
 	SingleeReport.GetSelectRange Startcol, Startrow, Endcol, Endrow
    	SingleeReport.CopyRange Startcol, Startrow, Endcol, Endrow
End Sub

'ճ������
Public Sub mnuEditPaste_click()
 	SingleeReport.Paste SingleeReport.GetCurrentCol, SingleeReport.GetCurrentRow, 0, False, False
End Sub

'ѡ����ճ��
Public Sub mnuEditPasteSpecial_Click()
	SingleeReport.PasteSpecialDlg
End Sub

'����
Public Sub mnuEditFind_click()
	SingleeReport.FindDialog 0
End Sub

'�滻
Public Sub mnuEditReplace_click()
	SingleeReport.FindDialog 1
End Sub

'��λ
Public Sub mnuEditGoto_click()
	MsgBox "���޴˹���"
End Sub

'ȫѡ
Public Sub mnuEditSelectAll_click()
	With SingleeReport
		If IsSelectAll Then
		    .ClearSelection
		    .Invalidate
		Else
		    .SelectRange 1, 1, .GetCols(.GetCurSheet) - 1, .GetRows(.GetCurSheet) - 1
		    .Invalidate
		End If
	End With	
End Sub

'�жϱ���Ƿ���ȫѡ״̬
Public Function IsSelectAll()
	With SingleeReport
	        .GetSelectRange StartCol, StartRow, EndCol, EndRow
	        If StartCol = 1 And StartRow = 1 And _
	        EndCol = .GetCols(.GetCurSheet) - 1 And EndRow = .GetRows(.GetCurSheet) - 1 Then
	            IsSelectAll = True
	        Else
	            IsSelectAll = False
	        End If
    End With
End Function

'��ֱ���
Public Sub mnuEditFillV_click()
	SingleeReport.FillBetweenSheet
End Sub

'�����������
Public Sub mnuEditInsertSpeChar_click()
	SingleeReport.InsertSpecialCharDlg
End Sub

'��������
Public Sub mnuEditHyperlink_click()
	SingleeReport.HyperlinkDlg
End Sub

'**************************************************
'		��ͼ�˵�
'**************************************************

'�жϱ�ҳ�Ƿ���ڲ���������
Public Function IsFreezed()
    With SingleeReport
        '���ò���������
        .GetFixedCol StartCol, EndCol
        .GetFixedRow StartRow, EndRow
        '�ж��Ƿ���ڲ������л���
        If (EndCol > 0 And StartCol > 0) Or (EndRow > 0 And StartRow > 0) Then
		IsFreezed = True
        Else
       		IsFreezed = False
        End If
    End With
End Function

'���ò���������
Public Sub mnuViewFreezed_click()
	If IsFreezed Then
		SingleeReport.SetFixedCol 0, -1
        	SingleeReport.SetFixedRow 0, -1
        Else
        	SingleeReport.SetFixedCol 1, SingleeReport.GetCurrentCol - 1
        	SingleeReport.SetFixedRow 1, SingleeReport.GetCurrentRow - 1
        End If
End Sub

'ҳǩ
Public Sub mnuViewSheetLabel_click()
	With SingleeReport
		If .GetSheetLabelState(.GetCurSheet) Then
			.ShowSheetLabel 0, .GetCurSheet
		Else 
			.ShowSheetLabel 1, .GetCurSheet
		End If
	End With
End Sub

'�б�
Public Sub mnuViewRowLabel_click()
	With SingleeReport
		If .GetTopLabelState(.GetCurSheet) Then
			.ShowTopLabel 0, .GetCurSheet
		Else 
			.ShowTopLabel 1, .GetCurSheet
		End If
	End With
End Sub

'�б�
Public Sub mnuViewColLabel_click()
	With SingleeReport
		If .GetSideLabelState(.GetCurSheet) Then
			.ShowSideLabel 0, .GetCurSheet
		Else 
			.ShowSideLabel 1, .GetCurSheet
		End If
	End With
End Sub

'ˮƽ������
Public Sub mnuViewHScroll_click()
	With SingleeReport
		If .GetHScrollState(.GetCurSheet) Then
			.ShowHScroll 0, .GetCurSheet
		Else 
			.ShowHScroll 1, .GetCurSheet
		End If
	End With
End Sub

'��ֱ������
Public Sub mnuViewVScroll_click()
	With SingleeReport
		If .GetVScrollState(.GetCurSheet) Then
			.ShowVScroll 0, .GetCurSheet
		Else 
			.ShowVScroll 1, .GetCurSheet
		End If
	End With
End Sub


'**************************************************
'		��ʽ�˵�
'**************************************************
'��Ԫ������
Public Sub mnuFormatCellProperty_click()
	SingleeReport.CellPropertyDlg
End Sub
'��/Ĩ�����
Public Sub mnuFormatDrawborder_click()
	SingleeReport.DrawLineDlg
End Sub

'����ͼƬ
Public Sub mnuFormatInsertPic_click()
	SingleeReport.SetCellImageDlg
	SingleeReport.Invalidate
End Sub

'ɾ��ͼƬ
Public Sub mnuFormatRemovePic_click()
	curSheet = SingleeReport.GetCurSheet
	SingleeReport.GetSelectRange Startcol, Startrow, Endcol, Endrow
	
	For col =  Startcol to Endcol
		For row = Startrow to Endrow
			SingleeReport.RemoveCellImage col, row, curSheet
		Next
	Next
End Sub

'���õ�Ԫ�����
Public Sub mnuFormatMergeCell_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	SingleeReport.MergeCells StartCol, StartRow, EndCol, EndRow
End Sub

'ȡ����Ԫ�����
Public Sub mnuFormatUnMergeCell_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	For col = StartCol To EndCol
		For row = StartRow To EndRow
		    SingleeReport.GetMergeRange col, row, StartCol1, StartRow1, EndCol1, EndRow1
		    SingleeReport.UnmergeCells StartCol1, StartRow1, EndCol1, EndRow1
		Next
	Next
End Sub

Public Sub menuAddRowCompage_click()
SingleeReport.GetSelectRange col1,row1,col2,row2
SingleeReport.AddRowCompages row1,row2 
End Sub

Public Sub menuDelRowCompage_click()
SingleeReport.GetSelectRange col1,row1,col2,row2
SingleeReport.DeleteRowCompages row1,row2 
End Sub

Public Sub menuAddColCompage_click()
SingleeReport.GetSelectRange col1,row1,col2,row2
SingleeReport.AddColCompages col1,col2 
End Sub

Public Sub menuDelColCompage_click()
SingleeReport.GetSelectRange col1,row1,col2,row2
SingleeReport.DeleteColCompages col1,col2 
End Sub

Public Sub menuDelAllCompage_click()
SingleeReport.RemoveAllCompages
End Sub       

       
'**************************************************
'		�����в˵�
'**************************************************

'�������
Public Sub mnuRowInsert_click()
	SingleeReport.InsertRowDlg
End Sub

'ɾ������
Public Sub mnuRowDelete_click()
	SingleeReport.DeleteRowDlg
End Sub

'׷�ӱ���
Public Sub mnuRowAppend_click()
	SingleeReport.AppendRowDlg
End Sub

'�и�
Public Sub mnuRowHeight_click()
	SingleeReport.RowHeightDlg
End Sub

'������
Public Sub mnuRowHide_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	SingleeReport.SetRowHidden StartRow, EndRow
End Sub

'ȡ��������
Public Sub mnuRowUnhide_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	SingleeReport.SetRowUnhidden StartRow, EndRow
End Sub

'������и�
Public Sub mnuRowBestHeight_click()
	With SingleeReport
		CurSheet = .GetCurSheet
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		For i = StartRow To EndRow
		    BestHeight = .GetRowBestHeight(i)
		    If BestHeight <> 0 Then
		    	.SetRowHeight 1, BestHeight, i, CurSheet
		    End If
		Next
		.Invalidate
	End With
End Sub

'�������
Public Sub mnuColInsert_click()
	SingleeReport.InsertColDlg
End Sub

'ɾ������
Public Sub mnuColDelete_click()
	SingleeReport.DeleteColDlg
End Sub

'׷�ӱ���
Public Sub mnuColAppend_click()
	SingleeReport.AppendColDlg
End Sub

'�п�
Public Sub mnuColWidth_click()
	SingleeReport.ColWidthDlg
End Sub

'������
Public Sub mnuColHide_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	SingleeReport.SetColHidden StartCol, EndCol
End Sub

'ȡ��������
Public Sub mnuColUnhide_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	SingleeReport.SetColUnhidden StartCol, EndCol
End Sub

'������п�
Public Sub mnuColBestWidth_click()
	With SingleeReport
		CurSheet = .GetCurSheet
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		For i = StartCol To EndCol
		    BestWidth = .GetColBestWidth(i)
		    If BestWidth <> 0 Then
		        .SetColWidth 1, BestWidth, i, .GetCurSheet
		        .Invalidate
		    End If
		Next
	End With
End Sub

'**************************************************
'		��ҳ�˵�
'**************************************************

'ҳǩ������
Public Sub mnuSheetRename_click()
	SingleeReport.SheetLabelRenameDlg
End Sub

'��ҳ�ߴ�
Public Sub mnuSheetSize_click()
	SingleeReport.SetSheetSizeDlg
End Sub

'��ҳ����
Public Sub mnuSheetProptect_click()
	With SingleeReport
		If .IsSheetProtect( .GetCurSheet ) Then '�Ѿ���������
			a = .SheetUnprotectDlg()
			MsgBox a
			If .SheetUnprotectDlg() = 0 Then
				MsgBox "���벻�ԣ���ҳ����ʧ�ܣ�", vbExclamation
				Exit Sub
			Else
				MsgBox "��ϲ�㣬��ҳ�����ɹ���", vbExclamation
				Exit Sub
			End If
		Else
			.SheetProtectDlg
		End If
	End With
End Sub

'�����ҳ
Public Sub mnuSheetInsert_click()
	SingleeReport.InsertSheetDlg
End Sub

'ɾ����ҳ
Public Sub mnuSheetDelete_click()
	SingleeReport.DeleteSheetDlg
End Sub

'׷�ӱ�ҳ
Public Sub mnuSheetAppend_click()
	SingleeReport.AppendSheetDlg
End Sub

'��ʽҳ����
Public Sub mnuSheetSortStyle_click()
	SingleeReport.SortStyleSheetDlg
End Sub

'����ҳ����
Public Sub mnuSheetSortValue_click()
	SingleeReport.SortValueSheetDlg
End Sub

'**************************************************
'		��ʽ�˵�
'**************************************************

'���빫ʽ
Public Sub mnuFormulaInput_click
	With SingleeReport
		.FormulaWizard .GetCurrentCol, .GetCurrentRow
	End With	
End Sub

'����¼�빫ʽ
Public Sub mnuFormulaBatchInput_click()
	SingleeReport.BatchInputFormulaDlg
End Sub

'��乫ʽ����
Public Sub mnuFormulaSerial_click()
	SingleeReport.FormulaFillSerial
End Sub

'���嵥Ԫ����ʾ��ʽ
Public Sub mnuFormulaCellShow_click()
	SingleeReport.SetCellFormulaShowDlg
End Sub

'���嵥Ԫ����ɫ��ʽ
Public Sub mnuFormulaCellColor_click()
	SingleeReport.SetCellFormulaColorDlg
End Sub

'��ʽ�б�
Public Sub mnuFormulaList_click()
	SingleeReport.FormulaListDlg
End Sub

'����ȫ��
Public Sub mnuFormulaReCalc_click()
	SingleeReport.CalculateAll '����ȫ��
	MsgBox "�������", vbExclamation
End Sub

'�����Զ��庯��
Public Sub mnuUserFuncDefine_click()
	Str = """�ҵĺ���"" Any XX( String str, [Double num] )"
	Str = Str & vbCrLf & "BEGIN_HELP"
	Str = Str & vbCrLf & "XX( String str, [Double num] )"
	Str = Str & vbCrLf & "��������ʾ���ʹ��ȱʡ����"
	Str = Str & vbCrLf & "END_HELP"
	MsgBox Str, vbInformation
	SingleeReport.DefineFunctions Str
End Sub

'�����Զ��庯��
Public Sub mnuUserFuncAdd_click()
    Str = """�ҵĺ���"" Any YY( String str, Double num )"
    Str = Str & vbCrLf & "BEGIN_HELP"
    Str = Str & vbCrLf & "YY( String str, Double num )"
    Str = Str & vbCrLf & "��������ʾ��ʵ�ù����и��ĺ�������"
    Str = Str & vbCrLf & "END_HELP"
    Str = Str & vbCrLf & """�ҵĺ���"" Any ZZ( String str, Double num )"
    Str = Str & vbCrLf & "BEGIN_HELP"
    Str = Str & vbCrLf & "ZZ( String str, Double num )"
    Str = Str & vbCrLf & "��������ʾ������"
    Str = Str & vbCrLf & "END_HELP"
    MsgBox Str, vbInformation
    SingleeReport.DefineFunctions Str
End Sub

'ɾ���Զ��庯��
Public Sub mnuUserFuncDelete_click()
	    SingleeReport.DelUserFunction "XX"
End Sub

'�޸��Զ��庯��
Public Sub mnuUserFuncModify_click()
	Str = """�ҵĺ���"" Any YY( String str )"
	Str = Str & vbCrLf & "BEGIN_HELP"
	Str = Str & vbCrLf & "YY( String str )"
	Str = Str & vbCrLf & "���ڱ�����ֻ��һ��������"
	Str = Str & vbCrLf & "END_HELP"
	MsgBox Str, vbInformation
	SingleeReport.DefineFunctions Str
End Sub

'**************************************************
'		���ݲ˵�
'**************************************************

'����ת��
Public Sub mnuDataRangeRotate_click()
	SingleeReport.RangeRotateDlg
End Sub

'��λƽ��
Public Sub mnuDataRangeBlance_click()
	SingleeReport.BlanceDlg
End Sub

'��������
Public Sub mnuDataRangeSort_click()
	SingleeReport.RangeSortDlg
End Sub

'����������
Public Sub mnuDataRangeClassSum_click()
	SingleeReport.RangeClassSumDlg
End Sub

'�����ѯ
Public Sub mnuDataRangeQuery_click()
	SingleeReport.RangeQueryDlg
End Sub

'���������
Public Sub mnuDataRange3DEasySum_click()
	SingleeReport.Range3DEasySumDlg
End Sub

'ҳ���������
Public Sub mnuDataRange3DSum_click()
	SingleeReport.Range3DSumDlg
End Sub

'ҳ������͸��
Public Sub mnuDataRange3DView_click()
	SingleeReport.Range3DViewDlg
End Sub

'ҳ�������ѯ
Public Sub mnuDataRange3DQuery_click()
	SingleeReport.Range3DQueryDlg
End Sub

'��������
Public Sub mnuDataWzdBarcode_click()
	SingleeReport.WzdBarCodeDlg
End Sub

'ͼ����
Public Sub mnuDataWzdChart_click()
	SingleeReport.WzdChartDlg
End Sub

Public Sub menuDefineVarDlg_click()
	SingleeReport.DefineVariableDlg
End Sub


'******************************************************************************************
'****************          �������е�����
'******************************************************************************************

'��������
Public Sub cmdSortAscending_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	If StartRow = EndRow And StartCol <> EndCol Then
		SingleeReport.SortRow 1, StartRow, StartCol, StartRow, EndCol, EndRow
	ElseIf StartCol = EndCol And StartRow <> EndRow Then
		SingleeReport.SortCol 1, StartCol, StartCol, StartRow, EndCol, EndRow
	ElseIf StartRow <> EndRow And StartCol <> EndCol Then
		mnuDataRangeSort_click
	End If
End Sub

'��������
Public Sub cmdSortDescending_click()
	SingleeReport.GetSelectRange StartCol, StartRow, EndCol, EndRow
	If StartRow = EndRow And StartCol <> EndCol Then
		SingleeReport.SortRow 0, StartRow, StartCol, StartRow, EndCol, EndRow
	ElseIf StartCol = EndCol And StartRow <> EndRow Then
		SingleeReport.SortCol 0, StartCol, StartCol, StartRow, EndCol, EndRow
	ElseIf StartRow <> EndRow And StartCol <> EndCol Then
		mnuDataRangeSort_Click
	End If
End Sub

'ˮƽ���
Public Sub cmdFormulaSumH_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		If StartCol = EndCol Then
			MsgBox "��ѡ��һ����������", vbExclamation
			Exit Sub
		Else
			For i = StartRow To EndRow
				formula = "sum(" & .CellToLabel(StartCol, i) & ":" & .CellToLabel(EndCol - 1, i) & ")"
				.SetFormula EndCol, i, .GetCurSheet, formula
			Next
		End If
		.Invalidate
	End With
End Sub

'��ֱ���
Public Sub cmdFormulaSumV_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		If StartRow = EndRow Then
			MsgBox "��ѡ��һ����������", vbExclamation
			Exit Sub
		Else
			For i = StartCol To EndCol
				formula = "sum(" & .CellToLabel(i, StartRow) & ":" & .CellToLabel(i, EndRow - 1) & ")"
				.SetFormula i, EndRow, .GetCurSheet, formula
			Next
		End If
		.Invalidate
	End With
End Sub

'˫�����
Public Sub cmdFormulaSumHV_click()
	With SingleeReport	
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		If StartRow = EndRow Or StartCol = EndCol Then
			MsgBox "��ѡ��һ����������", vbExclamation
			Exit Sub
		Else
			For i = StartRow To EndRow - 1
				formula = "sum(" & .CellToLabel(StartCol, i) & ":" & .CellToLabel(EndCol - 1, i) & ")"
				.SetFormula EndCol, i, .GetCurSheet, formula
			Next
			For i = StartCol To EndCol - 1
				formula = "sum(" & .CellToLabel(i, StartRow) & ":" & .CellToLabel(i, EndRow - 1) & ")"
				.SetFormula i, EndRow, .GetCurSheet, formula
			Next
			formula = "sum(" & .CellToLabel(StartCol, StartRow) & ":" & .CellToLabel(EndCol - 1, EndRow - 1) & ")"
			.SetFormula EndCol, EndRow, .GetCurSheet, formula
		End If
		.Invalidate
	End With
End Sub

'���ô���
Public Sub cmdBold_click()
	With SingleeReport
	        .GetSelectRange StartCol, StartRow, EndCol, EndRow
	        CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        FontStyle = .GetCellFontStyle(CurCol, CurRow, CurSheet)
	        If (FontStyle And 2) = 2 Then
                    For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            FontStyle = .GetCellFontStyle(i, j, CurSheet)
                            .SetCellFontStyle i, j, CurSheet, (FontStyle AND (8+4))
                        Next
                    Next
                Else
                    For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            FontStyle = .GetCellFontStyle(i, j, CurSheet)
                            .SetCellFontStyle i, j, CurSheet,  FontStyle OR 2
                        Next
                    Next
                End If
                .Invalidate
       	End With
End Sub

'����б��
Public Sub cmdItalic_click()
	With SingleeReport
	        .GetSelectRange StartCol, StartRow, EndCol, EndRow
	        CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        FontStyle = .GetCellFontStyle(CurCol, CurRow, CurSheet)
	        If (FontStyle And 4) = 4 Then
                    For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            FontStyle = .GetCellFontStyle(i, j, CurSheet)
                            .SetCellFontStyle i, j, CurSheet, (FontStyle AND (8+2))
                        Next
                    Next
                Else
                    For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            FontStyle = .GetCellFontStyle(i, j, CurSheet)
                            .SetCellFontStyle i, j, CurSheet,  FontStyle OR 4
                        Next
                    Next
                End If
                .Invalidate
       	End With	
End Sub

'�����»���
Public Sub cmdUnderline_click()
	With SingleeReport
	        .GetSelectRange StartCol, StartRow, EndCol, EndRow
	        CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        FontStyle = .GetCellFontStyle(CurCol, CurRow, CurSheet)
	        If (FontStyle And 8) = 8 Then
                    For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            FontStyle = .GetCellFontStyle(i, j, CurSheet)
                            .SetCellFontStyle i, j, CurSheet, (FontStyle AND (4+2))
                        Next
                    Next
                Else
                    For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            FontStyle = .GetCellFontStyle(i, j, CurSheet)
                            .SetCellFontStyle i, j, CurSheet,  FontStyle OR 8
                        Next
                    Next
                End If
                .Invalidate
       	End With	
End Sub

'���ñ���ɫ
Public Sub cmdBackColor_click()
	With SingleeReport
	        On Error Resume Next
	        CommonDialog1.Flags = &H2 or &H8
	        CommonDialog1.ShowColor
	        .GetSelectRange StartCol, StartRow, EndCol, EndRow
	        For i = StartCol To EndCol
	            For j = StartRow To EndRow
	                If .FindColorIndex(CommonDialog1.Color, 1) <> -1 Then
	                    If Err <> 32755 Then
	                        .SetCellBackColor i, j, CurSheet, .FindColorIndex(CommonDialog1.Color, 1)
	                     End If
	                End If
	            Next
	        Next
	        .Invalidate
        End With
End Sub

'����ǰ��ɫ
Public Sub cmdForeColor_click()
	With SingleeReport
	        On Error Resume Next
	        CommonDialog1.Flags = &H2 or &H8
	        CommonDialog1.ShowColor
	        .GetSelectRange StartCol, StartRow, EndCol, EndRow
	        For i = StartCol To EndCol
	            For j = StartRow To EndRow
	                If .FindColorIndex(CommonDialog1.Color, 1) <> -1 Then
	                    If Err <> 32755 Then
	                        .SetCellTextColor i, j, CurSheet, .FindColorIndex(CommonDialog1.Color, 1)
	                    End If
	                End If
	            Next
	        Next
	        .Invalidate
	End With
End Sub

'�Զ�����
Public Sub cmdWordWrap_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
		If .GetCellTextStyle(CurCol, CurRow, CurSheet) = 2 Then'��ǰ��Ԫ��Ϊ����״̬
		     For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            .SetCellTextStyle i, j, CurSheet, 0
                        Next
                     Next
                Else
                     For i = StartCol To EndCol
                        For j = StartRow To EndRow
                            .SetCellTextStyle i, j, CurSheet, 2
                        Next
                     Next
                End If
                .Invalidate
	End With
End Sub

'�������
Public Sub cmdAlignLeft_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        hAlign = .GetCellAlign(CurCol, CurRow, CurSheet)
	        If (HAlign and 1) = 1 Then
	        	hAlign = 0
	        Else
	        	hAlign = 1
		End If
		For i = StartCol To EndCol
			For j = StartRow To EndRow
			    vAlign = .GetCellAlign( i, j, CurSheet) and (8+16+32)
			    align = vAlign + hAlign
			    .SetCellAlign i, j, CurSheet, align
			Next
		Next		
		.Invalidate
	End With
End Sub

'���ж���
Public Sub cmdAlignCenter_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        hAlign = .GetCellAlign(CurCol, CurRow, CurSheet)
	        If (HAlign and 4) = 4 Then
	        	hAlign = 0
	        Else
	        	hAlign = 4
		End If
		For i = StartCol To EndCol
			For j = StartRow To EndRow
			    vAlign = .GetCellAlign( i, j, CurSheet) and (8+16+32)
			    align = vAlign + hAlign
			    .SetCellAlign i, j, CurSheet, align
			Next
		Next		
		.Invalidate
	End With
End Sub

'���Ҷ���
Public Sub cmdAlignRight_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        hAlign = .GetCellAlign(CurCol, CurRow, CurSheet)
	        If (HAlign and 2) = 2 Then
	        	hAlign = 0
	        Else
	        	hAlign = 2
		End If
		For i = StartCol To EndCol
			For j = StartRow To EndRow
			    vAlign = .GetCellAlign( i, j, CurSheet) and (8+16+32)
			    align = vAlign + hAlign
			    .SetCellAlign i, j, CurSheet, align
			Next
		Next		
		.Invalidate
	End With
End Sub

'���϶���
Public Sub cmdAlignTop_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        vAlign = .GetCellAlign(CurCol, CurRow, CurSheet)
	        If (HAlign and 8) = 8 Then
	        	vAlign = 0
	        Else
	        	vAlign = 8
		End If
		For i = StartCol To EndCol
			For j = StartRow To EndRow
			    hAlign = .GetCellAlign( i, j, CurSheet) and (1+2+4)
			    align = vAlign + hAlign
			    .SetCellAlign i, j, CurSheet, align
			Next
		Next		
		.Invalidate
	End With
End Sub

'��ֱ���ж���
Public Sub cmdAlignMiddle_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        vAlign = .GetCellAlign(CurCol, CurRow, CurSheet)
	        If (HAlign and 32) = 32 Then
	        	vAlign = 0
	        Else
	        	vAlign = 32
		End If
		For i = StartCol To EndCol
			For j = StartRow To EndRow
			    hAlign = .GetCellAlign( i, j, CurSheet) and (1+2+4)
			    align = vAlign + hAlign
			    .SetCellAlign i, j, CurSheet, align
			Next
		Next		
		.Invalidate
	End With
End Sub

'���¶���
Public Sub cmdAlignBottom_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        vAlign = .GetCellAlign(CurCol, CurRow, CurSheet)
	        If (HAlign and 16) = 16 Then
	        	vAlign = 0
	        Else
	        	vAlign = 16
		End If
		For i = StartCol To EndCol
			For j = StartRow To EndRow
			    hAlign = .GetCellAlign( i, j, CurSheet) and (1+2+4)
			    align = vAlign + hAlign
			    .SetCellAlign i, j, CurSheet, align
			Next
		Next		
		.Invalidate
	End With
End Sub

'���߿���
Public Sub cmdDrawBorder_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurSheet = .GetCurSheet
		.DrawGridLine StartCol, StartRow, EndCol, EndRow, 0, BorderTypeSelect.value, -1
        End With
End Sub

'Ĩ����
Public Sub cmdEraseBorder_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurSheet = .GetCurSheet
		.ClearGridLine StartCol, StartRow, EndCol, EndRow, 0
        End With
End Sub

'���ҷ���
Public Sub cmdCurrency_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurSheet = .GetCurSheet
		For i = StartCol To EndCol
			For j = StartRow To EndRow
			    .SetCellCurrency i, j, CurSheet, 2
			Next
		Next		
		.Invalidate
        End With
End Sub

'�ٷֺ�
Public Sub cmdPercent_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        If .GetCellNumType( CurCol, CurRow, CurSheet) = 5 Then
			For i = StartCol To EndCol
				For j = StartRow To EndRow
				    .SetCellNumType i, j, CurSheet, 0
				Next
			Next		
		Else 
			For i = StartCol To EndCol
				For j = StartRow To EndRow
				    .SetCellNumType i, j, CurSheet, 5
				Next
			Next	
		End If	
		.Invalidate
	End With
End Sub

'ǧ��λ
Public Sub cmdThousand_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        If .GetCellSeparator( CurCol, CurRow, CurSheet) = 2 Then
	        	For i = StartCol To EndCol
				For j = StartRow To EndRow
				    .SetCellNumType i, j, CurSheet, 1
				    .SetCellSeparator i, j, CurSheet, 1
                            	Next
			Next		
		Else 
			For i = StartCol To EndCol
				For j = StartRow To EndRow
				    .SetCellNumType i, j, CurSheet, 1
				    .SetCellSeparator i, j, CurSheet, 2
				Next
			Next	
		End If	
		.Invalidate
	End With
End Sub

'���ڻ�����
Public Sub cmdAbout_click()
	SingleeReport.AboutBox
End Sub

'������
Public Sub cmdInsertCol_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		.InsertCol StartCol, EndCol - StartCol + 1, .GetCurSheet
	End With
End Sub

'������
Public Sub cmdInsertRow_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		.InsertRow StartRow, EndRow - StartRow + 1, .GetCurSheet
	End With
End Sub

'׷����
Public Sub cmdAppendCol_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		.InsertCol .GetCols(.GetCurSheet), EndCol - StartCol + 1, .GetCurSheet
	End With
End Sub

'׷����
Public Sub cmdAppendRow_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		.InsertRow .GetRows(.GetCurSheet), EndRow - StartRow + 1, .GetCurSheet
	End With
End Sub

'ɾ����
Public Sub cmdDeleteCol_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		.DeleteCol StartCol, EndCol - StartCol + 1, .GetCurSheet
	End With
End Sub

'ɾ����
Public Sub cmdDeleteRow_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		.DeleteRow StartRow, EndRow - StartRow + 1, .GetCurSheet
	End With
End Sub

'��ҳ�ߴ�
Public Sub cmdSheetSize_click()
	With SingleeReport
		.SetCols .GetCurrentCol + 1, .GetCurSheet
		.SetRows .GetCurrentRow + 1, .GetCurSheet
	End With
End Sub

'�����
Public Sub cmdMergeRow_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		For i = StartRow To EndRow
			.MergeCells StartCol, i, EndCol, i
		Next
        End With
End Sub

'�����
Public Sub cmdMergeCol_click()
	With SingleeReport
		StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		For i = StartCol To EndCol
			.MergeCells i, StartRow, i, EndRow
		Next
        End With
End Sub

'���û��ܹ�ʽ
Public Sub cmdFormulaSum3D_click()
	With SingleeReport
           StartCol = 0: StartRow = 0: EndCol = 0: EndRow = 0
           .GetSelectRange StartCol, StartRow, EndCol, EndRow
           If .GetTotalSheets() > 1 Then
                For i = StartCol To EndCol
                    For j = StartRow To EndRow
                        If .GetCurSheet() = 0 Then
                            .SetFormula i, j, .GetCurSheet, "Sum3D( CurCell() , loopsheet() >=" & CStr(2) & " AND loopsheet() <=" & CStr(.GetTotalSheets()) & ")"
                        Else
                            .SetFormula i, j, .GetCurSheet, "Sum3D( CurCell() , loopsheet() >= 1 AND loopsheet() <= " & CStr(.GetCurSheet()) & ")"
                        End If
                    Next
                Next
            Else:
                MsgBox "��ǰ��Ԫ��ֻ��һҳ�����ܽ��л��ܡ�", vbExclamation
            End If
            .Invalidate
	End With
End Sub

'��Ԫ��ֻ��
Public Sub cmdReadOnly_click()
	With SingleeReport
		.GetSelectRange StartCol, StartRow, EndCol, EndRow
		CurCol = .GetCurrentCol
	        CurRow = .GetCurrentRow
	        CurSheet = .GetCurSheet
	        If .GetCellInput( CurCol, CurRow, CurSheet) = 5 Then
			For i = StartCol To EndCol
				For j = StartRow To EndRow
				    .SetCellInput i, j, .GetCurSheet, 1
				Next
			Next
		Else
			For i = StartCol To EndCol
				For j = StartRow To EndRow
				    .SetCellInput i, j, .GetCurSheet, 5
				Next
			Next
		End If
		.Invalidate
        End With
End Sub


'*****************************************************************
'**********      ����е��Ҽ��˵�
'*****************************************************************
Dim TotalMenu

'�����Ҽ��˵�
Public Sub SingleeReport_MenuStart( ByVal col, ByVal row, ByVal section)
With SingleeReport
    If section = 1 Then '����ڱ������
        .AddPopMenu 1001, "����(&T)", 0
        .AddPopMenu 1002, "����(&C)", 0
        .AddPopMenu 1003, "ճ��(&P)", 0
        .AddPopMenu 1004, "ѡ����ճ��(&S)...", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1005, "���빫ʽ(&I)", 0
        .AddPopMenu 1006, "��ʽˢ(&M)", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1007, "�������(&N)     Del", 0
        .AddPopMenu 1008, "�����ʽ(&L)", 0
        .AddPopMenu 1009, "���ȫ��(&A)", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1010, "��Ԫ���ʽ(&C)...", 0
        .AddPopMenu 1011, "��������(&H)...", 0
    ElseIf section = 2 Then '������б���
        .AddPopMenu 1001, "����(&T)", 0
        .AddPopMenu 1002, "����(&C)", 0
        .AddPopMenu 1003, "ճ��(&P)", 0
        .AddPopMenu 1004, "ѡ����ճ��(&S)...", 0
        .AddPopMenu 1000, "", 1
        .AddPopMenu 1012, "�������(&I)", 0
        .AddPopMenu 1013, "ɾ������(&D)", 0
        .AddPopMenu 1014, "׷�ӱ���(&A)", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1015, "�и�(&H)...", 0
        .AddPopMenu 1016, "���ʺ��и�(&B)", 0
        .AddPopMenu 1017, "����(&N)", 0
        .AddPopMenu 1018, "ȡ������(&U)", 0
    ElseIf section = 3 Then '������б���
        .AddPopMenu 1001, "����(&T)", 0
        .AddPopMenu 1002, "����(&C)", 0
        .AddPopMenu 1003, "ճ��(&P)", 0
        .AddPopMenu 1004, "ѡ����ճ��(&S)...", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1019, "�������(&I)", 0
        .AddPopMenu 1020, "ɾ������(&D)", 0
        .AddPopMenu 1021, "׷�ӱ���(&A)", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1022, "�п�(&W)", 0
        .AddPopMenu 1023, "���ʺ��п�(&W)", 0
        .AddPopMenu 1024, "����(&N)", 0
        .AddPopMenu 1025, "ȡ������(&U)", 0
    ElseIf section = 4 Then '��������Ͻ�
    	.AddPopMenu 1001, "����(&T)", 0
        .AddPopMenu 1002, "����(&C)", 0
        .AddPopMenu 1003, "ճ��(&P)", 0
        .AddPopMenu 1004, "ѡ����ճ��(&S)...", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1007, "�������(&N)     Del", 0
        .AddPopMenu 1008, "�����ʽ(&L)", 0
        .AddPopMenu 1009, "���ȫ��(&A)", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1010, "��Ԫ���ʽ(&C)...", 0
        .AddPopMenu 1026, "��ҳ�ߴ�(&S)", 0
        .AddPopMenu 1027, "��ҳ���Ϊ(&O)...", 0
    ElseIf section = 5 Then '�����ҳǩ��
        .AddPopMenu 1028, "�����ҳ(&I)...", 0
        .AddPopMenu 1029, "ɾ����ҳ(&D)...", 0
        .AddPopMenu 1030, "׷�ӱ�ҳ(&A)...", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1031, "������(&R)...", 0
        .AddPopMenu 1032, "��ҳ����(&S)...", 0
        .AddPopMenu 1000, "", 0
        .AddPopMenu 1026, "��ҳ�ߴ�(&F)...", 0
        .AddPopMenu 1027, "��ҳ���Ϊ(&O)...", 0
        If .IsSheetProtect(.GetCurSheet) Then
            .AddPopMenu 1033, "��ҳ����(&L)...", 0
        Else
            .AddPopMenu 1033, "��ҳ����(&L)...", 0
        End If
    ElseIf section = 6 Then '�����ҳǩ�Ĳ�����Ť��ʱ
    	CurSheet = .GetCurSheet
    	TotalMenu = .GetTotalSheets
        For i = 1 To TotalMenu
            If i - 1 = CurSheet Then
                .AddPopMenu 1040 + i, .GetSheetLabel(i - 1), 8
            Else
                .AddPopMenu 1040 + i, .GetSheetLabel(i - 1), 0
            End If
        Next
    End If
End With
End Sub

'�������
Public Sub cmdClearContent_Click()
	SingleeReport.Clear 1
End Sub

'�����ʽ
Public Sub cmdClearFormula_Click()
	SingleeReport.Clear 2
End Sub

'���ȫ��
Public Sub cmdClearAll_Click()
	SingleeReport.Clear 32
End Sub


'�Ҽ��˵�����
Public Sub SingleeReport_MenuCommand(ByVal col, ByVal row, ByVal itemid)
    '��Ԫ���е��Ҽ��˵�
    With SingleeReport
        Select Case itemid
            Case 1001: mnuEditCut_Click				'����
            Case 1002: mnuEditCopy_Click			'����
            Case 1003: mnuEditPaste_Click			'ճ��
            Case 1004: mnuEditPasteSpecial_Click		'ѡ����ճ��
            Case 1005: mnuFormulaInput_Click			'���빫ʽ
            Case 1006: SingleeReport.FormatPainter			'��ʽˢ
            Case 1007: cmdClearContent_Click			'�������
            Case 1008: cmdClearFormula_Click			'�����ʽ
            Case 1009: cmdClearAll_Click			'���ȫ��
            Case 1010: mnuFormatCellProperty_click		'��Ԫ������
            Case 1011: mnuEditHyperlink_Click			'��������
            
            '�б�˵�
            Case 1012 cmdInsertRow_click			'�������
            Case 1013 cmdDeleteRow_click 			'ɾ����
            Case 1014 cmdAppendRow_click			'׷����
            Case 1015: mnuRowHeight_click			'�и�
            Case 1016: mnuRowBestHeight_click			'���ʺ��и�
            Case 1017: mnuRowHide_click				'������
            Case 1018: mnuRowUnhide_click			'ȡ��������
            
            '�б�˵�
            Case 1019 cmdInsertCol_click			'������
            Case 1020 cmdDeleteCol_click			'ɾ����
            Case 1021 cmdAppendCol_click			'׷����
            Case 1022: mnuColWidth_click			'�п�
            Case 1023: mnuColBestWidth_click			'���ʺ��п�
            Case 1024: mnuColHide_click				'������
            Case 1025: mnuColUnhide_click			'ȡ��������
            
            
            '��ҳ�˵�
            Case 1026: mnuSheetSize_click			'��ҳ�ߴ�
            Case 1027: mnuFileSheetSaveAs_click			'��ҳ���Ϊ
            Case 1028: .InsertSheet .GetCurSheet, 1		'�����ҳ
            Case 1029: .DeleteSheet .GetCurSheet, 1		'ɾ����ҳ
            Case 1030: .InsertSheet .GetTotalSheets, 1		'׷�ӱ�ҳ
            Case 1031: mnuSheetRename_Click			'������ҳǩ
            Case 1032: mnuSheetSortStyle_click			'��ҳ����
            Case 1033: mnuSheetProptect_click			'��ҳ����
        End Select
    End With
    
    '�����ҳǩ�Ĳ�����Ť�ϵĲ˵�
    For i = 1 To TotalMenu
        If itemid = 1040 + i Then
        	SingleeReport.SetCurSheet i - 1
        	Exit For
        End If
    Next
End Sub

'*****************************************************************
'**********      �����б���е��¼�
'*****************************************************************
'������ʾ����
Public Sub changeViewScale( ByVal value )
	zoom = value/100.0
	SingleeReport.SetScreenScale SingleeReport.GetCurSheet, zoom
End Sub

'��������
Public Sub changeFontName( ByVal value )
    With SingleeReport
        .GetSelectRange StartCol, StartRow, EndCol, EndRow
        CurSheet = .GetCurSheet
        lFontName = .FindFontIndex( value, 1)
        For i = StartCol To EndCol
            For j = StartRow To EndRow
                .SetCellFont i, j, CurSheet, lFontName
            Next
        Next
        .Invalidate
    End With
End Sub

'�����ֺ�
Public Sub changeFontSize( ByVal value )
    With SingleeReport
        .GetSelectRange StartCol, StartRow, EndCol, EndRow
        CurSheet = .GetCurSheet
        lFontSize = value
        For i = StartCol To EndCol
            For j = StartRow To EndRow
                .SetCellFontSize i, j, CurSheet, lFontSize
            Next
        Next
        .Invalidate
    End With

End Sub

'�������
Public Sub changeFillType( ByVal value )
	SingleeReport.Fill value
End Sub

'��������
Public Sub changeDateType( ByVal value )
    With SingleeReport
        .GetSelectRange StartCol, StartRow, EndCol, EndRow
        CurSheet = .GetCurSheet
        For i = StartCol To EndCol
            For j = StartRow To EndRow
                .SetCellDouble i, j, CurSheet, CSng(Now())
                .SetCellNumType i, j, CurSheet, 3
                .SetCellDateStyle i, j, CurSheet, value
            Next
        Next
    End With
	
End Sub

'ʱ������
Public Sub changeTimeType( ByVal value )
    With SingleeReport
        .GetSelectRange StartCol, StartRow, EndCol, EndRow
        CurSheet = .GetCurSheet
        For i = StartCol To EndCol
            For j = StartRow To EndRow
                .SetCellDouble i, j, CurSheet, CSng(Now())
                .SetCellNumType i, j, CurSheet, 4
                .SetCellTimeStyle i, j, CurSheet, value
            Next
        Next
    End With
End Sub