					
<SCRIPT LANGUAGE='javascript'>
var ColumnTypeArray ; 	//结果集返回字段的类型，这里只判断是否为金额还是字符型
var colArray ; //列坐标数组，用于纵向填充	
var xArray,yArray,xlength;  //结果集按行转换成数组
var rowstart ;  //行坐标填充起始行，这里取的第一个填充坐标的行坐标，这个参数很重要！！！
	<%
int colcount = 0;	//结果集记录的字段数	
int rowcount = 0;	//结果集的长度	
int colCursor = 1;	//结果集循环起始记录号
	
int rowCursor = 1;
StringBuffer resultBuffer = new StringBuffer();

if(rowset != null && rowset.getMetaData()!=null)
{
			metdata = rowset.getMetaData();
			colcount = metdata.getColumnCount();
			rowcount = local.getRows();
%>
ColumnTypeArray = new Array(); 
<%for(colCursor=1;colCursor<=colcount;colCursor++)
{%>
	ColumnTypeArray[<%=colCursor-1%>] = '<%=metdata.getColumnType(colCursor)%>';		
<%}		
			resultBuffer = new StringBuffer();
			while(rowset.next())
			{		  
				for(colCursor=1;colCursor<=colcount;colCursor++)
				{
					resultBuffer.append(Utility.trimNull(rowset.getString(colCursor)));
					resultBuffer.append("α");
				}
				resultBuffer.append("β");								
			}
			rowset.close();
			rowset = null;		
			%>			
			//将结果集存入JS			
			var xArray = '<%=resultBuffer.toString()%>'.split("β");
			xlength = xArray.length - 1;
			yArray = new Array();													
			for(i=0;i<xlength;i++)
			{
				yArray[i] = xArray[i].split("α");								
			}
			<%
			
			}%>
			<%if(rowCursor==1)	{%>					
				rowstart = 5; //这里取的第一个填充坐标的行坐标
				loadFour(document.theform.DCellWeb1,<%=cellcolcount%>,'<%=title%>','<%=title_name1%>','<%=title_name2%>','<%=title_name3%>','<%=title_name4%>');
				load_row(document.theform.DCellWeb1);
				document.theform.DCellWeb1.SetRows(5,0);
				document.theform.DCellWeb1.InsertRow((document.theform.DCellWeb1.GetRows(0)),<%=rowcount-1%>,0);
				
			<%}%>
			colArray = new Array();
			
			for(var i=0;i<document.theform.DCellWeb1.getCols(0);i++)
			{
				colArray[i] = i+1;
			}
			
			//
			//--------------------循环填充数据------------------------------
			for(i=0;i<xlength;i++)
			{	
				//填充前还要复制行高（CELL没有自动处理）
				document.theform.DCellWeb1.SetRowHeight(1,25,rowstart,0);							
				for(j=0;j<colArray.length;j++)
				{		
							//复制单元格格式
							if(i>0)
								document.theform.DCellWeb1.SetCellNumType(colArray[j],rowstart,0,document.theform.DCellWeb1.GetCellNumType(colArray[j],rowstart,0));			
							//需要判断该字段的类型，如果是金额型的要在千分位加逗号
							
							if(ColumnTypeArray[j]==3&&yArray[i][j]!="")	
							{						
								
								document.theform.DCellWeb1.D(colArray[j],rowstart,0,parseFloat(yArray[i][j]));
							}
							else if(ColumnTypeArray[j]==4&&yArray[i][j]!="")
							{
								document.theform.DCellWeb1.D(colArray[j],rowstart,0,parseInt(yArray[i][j]));
							}
							else 
							{							
								document.theform.DCellWeb1.S(colArray[j],rowstart,0,yArray[i][j]);
							}	
							alert(yArray[i][j]);
				}								
			 	rowstart++;
			 }
			 for(j=0;j<colArray.length;j++)
			{
					document.theform.DCellWeb1.SetColWidth(1,document.theform.DCellWeb1.GetColBestWidth(colArray[j]),colArray[j],0)	;
			}
		</script>
		
				