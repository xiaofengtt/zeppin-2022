//����˵�����ϲ�ָ����񣨱��idΪ_w_table_id��ָ���У���������_w_table_mincolnum С��_w_table_maxcolnum����ͬ���е���ͬ�ı������ڵ�Ԫ��
//          ����һ��ʱ����һ�еĵ�Ԫ��ϲ���Χ���ܳ���ǰһ�еĺϲ���Χ��������ֽ���
//����˵����_w_table_id Ϊ��Ҫ���кϲ���Ԫ��ı��id������HTMl��ָ����� id="data" ���˲���ӦΪ #data 
//����˵����_w_table_mincolnum Ϊ��Ҫ���бȽϺϲ�����ʼ������Ϊ���֣��������ߵ�һ��Ϊ1��ʼ����
//          �˲�������Ϊ�գ�Ϊ�����һ��Ϊ��ʼ�С�
//          ��Ҫע�⣬�����һ��Ϊ����У�����Ҫ�ų����С�
//����˵����_w_table_maxcolnum Ϊ��Ҫ���бȽϺϲ�������������������������ֵ�ĵ�Ԫ�񽫲����бȽϺϲ���
//          Ϊ���֣�������ߵ�һ��Ϊ1��ʼ����
//          �˲�������Ϊ�գ�Ϊ����ͬ_w_table_mincolnum��
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
  _w_table_firstrow = 0;//ǰһ�кϲ������һ��
  _w_table_SpanNum = 0;//�ϲ���Ԫ��ʱ�ģ���Ԫ��Span����
  _w_table_splitNum = 0;//�����_w_table_splitrow�ĵ�ǰԪ���±�
  _w_table_Obj = $(_w_table_id + " tr td:nth-child(" + _w_table_colnum + ")");
  _w_table_curcol_rownum = _w_table_Obj.length-1;//�������һ������
  if(_w_table_splitrow.length==0){_w_table_splitrow[0] = _w_table_curcol_rownum;}
  _w_table_lastrow = _w_table_splitrow[0];//ǰһ�кϲ��������һ��
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