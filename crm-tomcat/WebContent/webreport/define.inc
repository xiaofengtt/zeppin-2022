  var debug = false;
  
  //报表表页
  var SHEET_INPUT  = 0;
  var SHEET_OUTPUT = 1;
  var SHEET_DEFINE = 2;
  //报表参数列标定义
  var  COL_SQL      = 1;	//SQL语句定义列
  var  COL_SQL_ZB   = 2;	//填充坐标
  var  COL_PARAM    = 3;	//参数名称
  var  COL_PARAM_ZB = 4;	//参数坐标
  var  COL_PARAM_HH = 3;	//参数定义行号  
  var  COL_PARAM_FLAG = 5;	//系统标志
  var  COL_PARAM_DEFAULT = 6;	//参数默认值
  var  COL_PARAM_NULL = 7;	//参数是否可以为空
  var  COL_SUM_START  = 8;	
  var  COL_SUM_FILL   = 9;
  var  COL_PRINT_NAME= 10;
  var  COL_PRINT_PARAM= 11;	//打印纸张类型
  var  COL_PRINT_PARAM= 12;	//扩展：统计单元

  //报表参数行标定义
  var  ROL_PAGE_TYPE = 2 ;   
