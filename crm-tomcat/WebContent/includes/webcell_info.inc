					
<SCRIPT LANGUAGE='javascript'>
var ColumnTypeArray ; 	//����������ֶε����ͣ�����ֻ�ж��Ƿ�Ϊ�����ַ���
var colArray ; //���������飬�����������	
var xArray,yArray,xlength;  //���������ת��������
var rowstart ;  //�����������ʼ�У�����ȡ�ĵ�һ���������������꣬�����������Ҫ������
	<%
int colcount = 0;	//�������¼���ֶ���	
int rowcount = 0;	//������ĳ���	
int colCursor = 1;	//�����ѭ����ʼ��¼��
	
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
					resultBuffer.append("��");
				}
				resultBuffer.append("��");								
			}
			rowset.close();
			rowset = null;		
			%>			
			//�����������JS			
			var xArray = '<%=resultBuffer.toString()%>'.split("��");
			xlength = xArray.length - 1;
			yArray = new Array();													
			for(i=0;i<xlength;i++)
			{
				yArray[i] = xArray[i].split("��");								
			}
			<%
			
			}%>
			<%if(rowCursor==1)	{%>					
				rowstart = 5; //����ȡ�ĵ�һ����������������
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
			//--------------------ѭ���������------------------------------
			for(i=0;i<xlength;i++)
			{	
				//���ǰ��Ҫ�����иߣ�CELLû���Զ�������
				document.theform.DCellWeb1.SetRowHeight(1,25,rowstart,0);							
				for(j=0;j<colArray.length;j++)
				{		
							//���Ƶ�Ԫ���ʽ
							if(i>0)
								document.theform.DCellWeb1.SetCellNumType(colArray[j],rowstart,0,document.theform.DCellWeb1.GetCellNumType(colArray[j],rowstart,0));			
							//��Ҫ�жϸ��ֶε����ͣ�����ǽ���͵�Ҫ��ǧ��λ�Ӷ���
							
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
		
				