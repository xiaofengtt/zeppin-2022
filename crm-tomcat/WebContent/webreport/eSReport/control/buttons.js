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
	//开始命令
	switch(szCommand.toUpperCase())
	{
		case "CMDFILENEW"://新建
			mnuFileNew_click();
			break;
		case "CMDOPEN":
			mnuFileOpen_click();
			break;
		case "CMDFILEOPEN"://打开远程文件
			mnuFileWebOpen_click();
			break;
		case "CMDFILESAVE"://保存文档
			mnuFileSave_click();
			break;
		case "CMDFILEPRINT"://打印文档
			SingleeReport.PrintSheet( false, SingleeReport.GetCurSheet() );
			break;
		case "CMDFILEPRINTPREVIEW"://打印预览文档
			mnuFilePrintPreview_click();
			break;
		case "CMDEDITCUT"://剪切
			mnuEditCut_click();
			break;
		case "CMDEDITCOPY"://复制
			mnuEditCopy_click();
			break;		
		case "CMDEDITPASTE"://粘贴
			mnuEditPaste_click();
			break;
		case "CMDEDITFIND"://查找
		    	mnuEditFind_click();
			break;
		case "CMDEDITUNDO"://撤消
		    	mnuEditUndo_click();
			break;
		case "CMDEDITREDO"://重做
			mnuEditRedo_click();
			break;
		case "CMDVIEWFREEZE"://不滚动行列
		    	mnuViewFreezed_click();
			break;
		case "CMDFORMATPAINTER"://格式刷
			SingleeReport.FormatPainter();
			break;
		case "CMDSORTASCENDING"://升序排序
			cmdSortAscending_click();
			break;
		case "CMDSORTDESCENDING"://降序排序
			cmdSortDescending_click();
			break;
		case "CMDFORMULAINPUT"://输入公式
			mnuFormulaInput_click();
			break;
		case "cmdFormulaSerial"://填充单元公式序列
			mnuFormulaSerial_click();
			break;
		case "CMDFORMULASUMH"://水平求和
			cmdFormulaSumH_click();
			break;
		case "CMDFORMULASUMV"://垂直求和
			cmdFormulaSumV_click();
			break;
		case "CMDFORMULASUMHV"://双向求和
			cmdFormulaSumHV_click();
			break;
		case "CMDCHARTWZD"://图表向导
			mnuDataWzdChart_click();
			break;
		case "CMDINSERTPIC"://插入图片
			mnuFormatInsertPic_click();
			break;
		case "CMDHYPERLINK"://超级链接
			mnuEditHyperlink_click();
			break;
		case "CMDWZDBARCODE"://条形码向导
			mnuDataWzdBarcode_click();
			break;		
		case "CMDSHOWGRIDLINE"://显示/隐藏背景网格线
			with(SingleeReport){
				ShowGridLine(!GetGridLineState(GetCurSheet()), GetCurSheet() );
			}
			break;
		case "CMDVPAGEBREAK"://垂直分隔线
			with(SingleeReport){
                		if(IsColPageBreak(GetCurrentCol()))
                    			SetColPageBreak( GetCurrentCol(), 0);
                		else
                    			SetColPageBreak( GetCurrentCol(), 1);
                	}
            		break;
		case "CMDHPAGEBREAK"://水平分隔线
			with(SingleeReport){
				if (IsRowPageBreak(GetCurrentRow()))
					SetRowPageBreak( GetCurrentRow(), 0);
				else
					SetRowPageBreak( GetCurrentRow(), 1);
			}
			break;
		case "CMDSHOWPAGEBREAK"://显示/隐藏分隔线
			with(SingleeReport){
				ShowPageBreak(!GetPageBreakState());
				Invalidate();
			}
			break;
		//***********************************************************			
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		case "CMDBOLD"://设置粗体
		    	cmdBold_click();
			break;
		case "CMDITALIC"://设置斜体
			cmdItalic_click();
			break;
		case "CMDUNDERLINE"://设置下划线
			cmdUnderline_click();
			break;
		case "CMDBACKCOLOR"://设置背景色
			cmdBackColor_click();
			break;
		case "CMDFORECOLOR"://设置前景色
			cmdForeColor_click();
			break;
		case "CMDWORDWRAP"://设置自动折行
			cmdWordWrap_click();
			break;	
		case "CMDALIGNLEFT"://左对齐
			cmdAlignLeft_click();
			break;
		case "CMDALIGNCENTER"://居中对齐
			cmdAlignCenter_click();
			break;
		case "CMDALIGNRIGHT"://居右对齐
			cmdAlignRight_click();
			break;
		case "CMDALIGNTOP"://居上对齐
			cmdAlignTop_click();
			break;
		case "CMDALIGNMIDDLE"://垂直居中对齐
			cmdAlignMiddle_click();
			break;
		case "CMDALIGNBOTTOM"://居下对齐
			cmdAlignBottom_click();
			break;
		case "CMDDRAWBORDER"://画框线
			cmdDrawBorder_click();
			break;
		case "CMDERASEBORDER"://抹框线
			cmdEraseBorder_click();
			break;
		case "CMDCURRENCY"://货币符号
			cmdCurrency_click();
			break;
		case "CMDPERCENT"://百分号
			cmdPercent_click();
			break;		
		case "CMDTHOUSAND"://千分位
			cmdThousand_click();
			break;
		case "CMDABOUT"://关于华表插件
			cmdAbout_click();
			break;
		//***********************************************************			
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		case "CMDINSERTCOL"://插入列
		    	cmdInsertCol_click();
			break;
		case "CMDINSERTROW"://插入行
		    	cmdInsertRow_click();
			break;
		case "CMDAPPENDCOL"://追加列
			cmdAppendCol_click();
			break;
		case "CMDAPPENDROW"://追加行
		    	cmdAppendRow_click();
			break;
		case "CMDDELETECOL"://删除列
			cmdDeleteCol_click();
			break;
		case "CMDDELETEROW"://删除行
			cmdDeleteRow_click();
			break;
		case "CMDSHEETSIZE"://表页尺寸
			cmdSheetSize_click();
			break;
		case "CMDMERGECELL"://合并单元格
			mnuFormatMergeCell_click();
			break;
		case "CMDUNMERGECELL"://取消合并单元格
			mnuFormatUnMergeCell_click();
			break;
		case "CMDMERGEROW"://行组合
			cmdMergeRow_click();
			break;
		case "CMDMERGECOL"://列组合
			cmdMergeCol_click();
			break;
		case "CMDRECALCALL"://重算全表
			mnuFormulaReCalc_click();
			break;
		case "CMDFORMULASUM3D"://设置汇总公式
			cmdFormulaSum3D_click();
			break;
		case "CMDREADONLY"://单元格只读
			cmdReadOnly_click();
			break;
		case "CMDFILLSERIAL"://序列填充
			SingleeReport.FillSerialDlg();
			break;
		
	}
}

