//函数说明：合并指定表格（表格id为_w_table_id）指定列（行数大于_w_table_mincolnum 小于_w_table_maxcolnum）相同列中的相同文本的相邻单元格
//          多于一列时，后一列的单元格合并范围不能超过前一列的合并范围。避免出现交错。
//参数说明：_w_table_id 为需要进行合并单元格的表格id。如在HTMl中指定表格 id="data" ，此参数应为 #data 
//参数说明：_w_table_mincolnum 为需要进行比较合并的起始列数。为数字，则从最左边第一行为1开始算起。
//          此参数可以为空，为空则第一列为起始列。
//          需要注意，如果第一列为序号列，则需要排除此列。
//参数说明：_w_table_maxcolnum 为需要进行比较合并的最大列数，列数大于这个数值的单元格将不进行比较合并。
//          为数字，从最左边第一列为1开始算起。
//          此参数可以为空，为空则同_w_table_mincolnum。
function _w_table_lefttitle_rowspan(_w_table_id,_w_table_mincolnum,_w_table_maxcolnum){   
	if(_w_table_mincolnum == void 0){_w_table_mincolnum=1;}
	 if(_w_table_maxcolnum == void 0){_w_table_maxcolnum=_w_table_mincolnum;}
	 if(_w_table_mincolnum>_w_table_maxcolnum){
	  return "";
	 }else{
	  var _w_table_splitrow=new Array();
	  for(iLoop=_w_table_mincolnum;iLoop<=_w_table_maxcolnum;iLoop++){
	   _w_table_onerowspan(iLoop);
	  }
}
 
 function _w_table_onerowspan(_w_table_colnum){
  _w_table_firstrow = 0;//前一列合并区块第一行
  _w_table_SpanNum = 0;//合并单元格时的，单元格Span个数
  _w_table_splitNum = 0;//数组的_w_table_splitrow的当前元素下标
  _w_table_Obj = $(_w_table_id + " tr td:nth-child(" + _w_table_colnum + ")");
  _w_table_curcol_rownum = _w_table_Obj.length-1;//此列最后一行行数
  if(_w_table_splitrow.length==0){_w_table_splitrow[0] = _w_table_curcol_rownum;}
  _w_table_lastrow = _w_table_splitrow[0];//前一列合并区块最后一行
  var _w_table_firsttd;
  var _w_table_currenttd;
  var _w_table_curcol_splitrow=new Array();
  _w_table_Obj.each(function(i){
   if(i==_w_table_firstrow){
    _w_table_firsttd = $(this);
    _w_table_SpanNum = 1;
   }else{
    _w_table_currenttd = $(this);
    if(_w_table_firsttd.text()==_w_table_currenttd.text()){
     _w_table_SpanNum++;
     _w_table_currenttd.hide(); //remove();
     _w_table_firsttd.attr("rowSpan",_w_table_SpanNum); 
    }else{
     _w_table_firsttd = $(this);
     _w_table_SpanNum = 1;
     setTableRow(i-1);
    }
    if(i==_w_table_lastrow){setTableRow(i);}
   }
   function setTableRow(_splitrownum){
    if(_w_table_lastrow<=_splitrownum&&_w_table_splitNum++<_w_table_splitrow.length){
     //_w_table_firstrow=_w_table_lastrow+1;
     _w_table_lastrow=_w_table_splitrow[_w_table_splitNum];
    }
    _w_table_curcol_splitrow[_w_table_curcol_splitrow.length]=_splitrownum;
    if(i<_w_table_curcol_rownum){_w_table_firstrow=_splitrownum+1;}
   }
  });
  _w_table_splitrow=_w_table_curcol_splitrow;
 }
}