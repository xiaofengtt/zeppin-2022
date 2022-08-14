//Copyright (c) 2000 Microsoft Corporation.  All rights reserved.
//<script>
var g_WinDoc            =   window.document;
var g_WinDocAll         =   g_WinDoc.all;

function f_onCbMouseOut()
{
	event.cancelBubble=true;
	if( null != this.sticky )		// If it is sticky
	{
		if( true == this.buttondown )
		{
			return;
		};
	}
	this.className= (true == this.raised) ? "tbButtonRaise" : "tbButton";
	this.onmouseout=null;
}
function f_onCbMouseUp(obj)
{
	event.cancelBubble=true;
	if( null != this.sticky )
	{
		if( true == this.buttondown )
		{
			return;
		};
	}
	this.className= (true == this.raised) ? "tbButtonRaise" : "tbButton";
	this.onmouseup=null;
}
function onCbMouseDown(obj)
{
	obj.className="tbButtonDown";
	obj.onmouseout = f_onCbMouseOut;
	obj.onmouseup = f_onCbMouseUp;
}

function onCbClickEvent(obj, fNoEvent)
{
	if( null != event )
	{
		event.cancelBubble=true;
	}
	// Regular push button
	onCbClick(obj.id, true);
	return(false);
}

function onCbClick(szCommand, fState)
{
	//��ʼ����
	switch(szCommand.toUpperCase())
	{
		case "CMDFILENEW"://�½�
			mnuFileNew_click();
			break;
		case "CMDOPEN":
			mnuFileOpen_click();
			break;
		case "CMDFILEOPEN"://��Զ���ļ�
			mnuFileWebOpen_click();
			break;
		case "CMDFILESAVE"://�����ĵ�
			mnuFileSave_click();
			break;
		case "CMDFILEPRINT"://��ӡ�ĵ�
			SingleeReport.PrintSheet( false, SingleeReport.GetCurSheet() );
			break;
		case "CMDFILEPRINTPREVIEW"://��ӡԤ���ĵ�
			mnuFilePrintPreview_click();
			break;
		case "CMDEDITCUT"://����
			mnuEditCut_click();
			break;
		case "CMDEDITCOPY"://����
			mnuEditCopy_click();
			break;		
		case "CMDEDITPASTE"://ճ��
			mnuEditPaste_click();
			break;
		case "CMDEDITFIND"://����
		    	mnuEditFind_click();
			break;
		case "CMDEDITUNDO"://����
		    	mnuEditUndo_click();
			break;
		case "CMDEDITREDO"://����
			mnuEditRedo_click();
			break;
		case "CMDVIEWFREEZE"://����������
		    	mnuViewFreezed_click();
			break;
		case "CMDFORMATPAINTER"://��ʽˢ
			SingleeReport.FormatPainter();
			break;
		case "CMDSORTASCENDING"://��������
			cmdSortAscending_click();
			break;
		case "CMDSORTDESCENDING"://��������
			cmdSortDescending_click();
			break;
		case "CMDFORMULAINPUT"://���빫ʽ
			mnuFormulaInput_click();
			break;
		case "cmdFormulaSerial"://��䵥Ԫ��ʽ����
			mnuFormulaSerial_click();
			break;
		case "CMDFORMULASUMH"://ˮƽ���
			cmdFormulaSumH_click();
			break;
		case "CMDFORMULASUMV"://��ֱ���
			cmdFormulaSumV_click();
			break;
		case "CMDFORMULASUMHV"://˫�����
			cmdFormulaSumHV_click();
			break;
		case "CMDCHARTWZD"://ͼ����
			mnuDataWzdChart_click();
			break;
		case "CMDINSERTPIC"://����ͼƬ
			mnuFormatInsertPic_click();
			break;
		case "CMDHYPERLINK"://��������
			mnuEditHyperlink_click();
			break;
		case "CMDWZDBARCODE"://��������
			mnuDataWzdBarcode_click();
			break;		
		case "CMDSHOWGRIDLINE"://��ʾ/���ر���������
			with(SingleeReport){
				ShowGridLine(!GetGridLineState(GetCurSheet()), GetCurSheet() );
			}
			break;
		case "CMDVPAGEBREAK"://��ֱ�ָ���
			with(SingleeReport){
                		if(IsColPageBreak(GetCurrentCol()))
                    			SetColPageBreak( GetCurrentCol(), 0);
                		else
                    			SetColPageBreak( GetCurrentCol(), 1);
                	}
            		break;
		case "CMDHPAGEBREAK"://ˮƽ�ָ���
			with(SingleeReport){
				if (IsRowPageBreak(GetCurrentRow()))
					SetRowPageBreak( GetCurrentRow(), 0);
				else
					SetRowPageBreak( GetCurrentRow(), 1);
			}
			break;
		case "CMDSHOWPAGEBREAK"://��ʾ/���طָ���
			with(SingleeReport){
				ShowPageBreak(!GetPageBreakState());
				Invalidate();
			}
			break;
		//***********************************************************			
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		case "CMDBOLD"://���ô���
		    	cmdBold_click();
			break;
		case "CMDITALIC"://����б��
			cmdItalic_click();
			break;
		case "CMDUNDERLINE"://�����»���
			cmdUnderline_click();
			break;
		case "CMDBACKCOLOR"://���ñ���ɫ
			cmdBackColor_click();
			break;
		case "CMDFORECOLOR"://����ǰ��ɫ
			cmdForeColor_click();
			break;
		case "CMDWORDWRAP"://�����Զ�����
			cmdWordWrap_click();
			break;	
		case "CMDALIGNLEFT"://�����
			cmdAlignLeft_click();
			break;
		case "CMDALIGNCENTER"://���ж���
			cmdAlignCenter_click();
			break;
		case "CMDALIGNRIGHT"://���Ҷ���
			cmdAlignRight_click();
			break;
		case "CMDALIGNTOP"://���϶���
			cmdAlignTop_click();
			break;
		case "CMDALIGNMIDDLE"://��ֱ���ж���
			cmdAlignMiddle_click();
			break;
		case "CMDALIGNBOTTOM"://���¶���
			cmdAlignBottom_click();
			break;
		case "CMDDRAWBORDER"://������
			cmdDrawBorder_click();
			break;
		case "CMDERASEBORDER"://Ĩ����
			cmdEraseBorder_click();
			break;
		case "CMDCURRENCY"://���ҷ���
			cmdCurrency_click();
			break;
		case "CMDPERCENT"://�ٷֺ�
			cmdPercent_click();
			break;		
		case "CMDTHOUSAND"://ǧ��λ
			cmdThousand_click();
			break;
		case "CMDABOUT"://���ڻ�����
			cmdAbout_click();
			break;
		//***********************************************************			
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		case "CMDINSERTCOL"://������
		    	cmdInsertCol_click();
			break;
		case "CMDINSERTROW"://������
		    	cmdInsertRow_click();
			break;
		case "CMDAPPENDCOL"://׷����
			cmdAppendCol_click();
			break;
		case "CMDAPPENDROW"://׷����
		    	cmdAppendRow_click();
			break;
		case "CMDDELETECOL"://ɾ����
			cmdDeleteCol_click();
			break;
		case "CMDDELETEROW"://ɾ����
			cmdDeleteRow_click();
			break;
		case "CMDSHEETSIZE"://��ҳ�ߴ�
			cmdSheetSize_click();
			break;
		case "CMDMERGECELL"://�ϲ���Ԫ��
			mnuFormatMergeCell_click();
			break;
		case "CMDUNMERGECELL"://ȡ���ϲ���Ԫ��
			mnuFormatUnMergeCell_click();
			break;
		case "CMDMERGEROW"://�����
			cmdMergeRow_click();
			break;
		case "CMDMERGECOL"://�����
			cmdMergeCol_click();
			break;
		case "CMDRECALCALL"://����ȫ��
			mnuFormulaReCalc_click();
			break;
		case "CMDFORMULASUM3D"://���û��ܹ�ʽ
			cmdFormulaSum3D_click();
			break;
		case "CMDREADONLY"://��Ԫ��ֻ��
			cmdReadOnly_click();
			break;
		case "CMDFILLSERIAL"://�������
			SingleeReport.FillSerialDlg();
			break;
		
	}
}

