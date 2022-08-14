package enfo.crm.tools;


/**
 * 创建日期 2008-11-7
 * @author yelf
 * 
 * 用于创建FusionCharts所需的xml格式文件通用类
 * 
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class FusionChartsGanerate {
	
	/**
	 * 新建3D饼图的方法
	 *  
	 * @param ItemName
	 * @param ItemValue
	 * @param PercentageValue
	 * @param FileName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String ganeratePie3D(String[] ItemName,String[] ItemValue,String[] PercentageValue,String FileName,String Caption,String ValueUnit,String linkURL,String pieYScale,String startingAngle) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
				
		int ParameterCount = ItemName.length;
		//传入参数长度无误，开始生成xml格式文件
		if(ItemName.length == ItemValue.length && ItemName.length == PercentageValue.length){
				
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "' palette='2' decimals='2' enableSmartLabels='1' enableRotation='0' bgColor='99CCFF,FFFFFF' showValues='0' bgAlpha='0,100' bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='"+startingAngle+"' pieYScale='"+pieYScale+"' showPercentageValues='0' showPercentInToolTip='1' baseFontSize='10' baseFontColor='nav' baseFont='宋体' captionPadding='0' showLabels='0' clickURL='"+linkURL+"'>";
			sb.append(ChartRootStr);
			
			for(int i=0;i<ParameterCount;i++){	
				
				String setFormatStr = "<set label='" + Utility.trimNull(ItemName[i]) + "' value='" + Utility.trimNull(PercentageValue[i]) + "'/>";
				sb.append(setFormatStr);
			}
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='15' bold='0'/>" 
										  + "<style name='myTooltip' type='font' font='Arial' size='12' bold='0' />"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>" 
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		}
		return sb.toString();
	}
	
	
	public String ganeratePie3D(String[] ItemName,String[] ItemValue,String[] PercentageValue,String FileName,String Caption,String ValueUnit) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
				
		int ParameterCount = ItemName.length;
		//传入参数长度无误，开始生成xml格式文件
		if(ItemName.length == ItemValue.length && ItemName.length == PercentageValue.length){
				
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "' subcaption='("+FileName+")' palette='2' decimals='2' enableSmartLabels='1' enableRotation='0' bgColor='99CCFF,FFFFFF' bgAlpha='0,100' bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='70' showPercentageValues='1' showPercentInToolTip='1' baseFontSize='10' baseFontColor='nav' baseFont='宋体'>";
			sb.append(ChartRootStr);
			
			for(int i=0;i<ParameterCount;i++){	
				
				String setFormatStr = "<set label='" + Utility.trimNull(ItemName[i]) + ":" + Utility.trimNull(ItemValue[i]) + Utility.trimNull(ValueUnit) + "' value='" + Utility.trimNull(PercentageValue[i]) + "'/>";
				sb.append(setFormatStr);
			}
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='15' bold='0'/>" 
										  + "<style name='myTooltip' type='font' font='Arial' size='12' bold='0' />"
										  + "<style name='myDataLabels' type='font' font='Arial' size='12' bold='0'/>"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>"
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "<apply toObject='DataLabels' styles='myDataLabels'/>" 
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		}
		return sb.toString();
	}
	
	/**
	 * 生成3维柱状图
	 * @param xAxisValue
	 * @param yAxisValue
	 * @param xAxisName
	 * @param yAxisName
	 * @param Caption
	 * @param ValueUnit
	 * @return
	 * @throws Exception
	 */
	public String ganerateColumn3D(String[] xAxisValue,String[] yAxisValue,String xAxisName,String yAxisName,String Caption,String ValueUnit) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
				
		int ParameterCount = xAxisValue.length;
		//传入参数长度无误，开始生成xml格式文件
		System.out.println(xAxisValue.length+"-----"+yAxisValue.length);
		if(xAxisValue.length == yAxisValue.length){
				
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "' xAxisName='" + Utility.trimNull(xAxisName) + "' yAxisName='" + Utility.trimNull(yAxisName) + "'  rotateYAxisName='0' showValues='1' decimals='2' formatNumberScale='0' baseFontSize='10' baseFontColor='nav' baseFont='宋体' labelDisplay='WRAP'>";
			sb.append(ChartRootStr);
			
			for(int i=0;i<ParameterCount;i++){	
				
				String setFormatStr = "<set label='" + Utility.trimNull(xAxisValue[i]) + "' value='" + Utility.trimNull(yAxisValue[i]) + "'/>";
				sb.append(setFormatStr);
			}
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='15' bold='0'/>"
										  + "<style name='myTooltip' type='font' font='Arial' size='12' bold='1' />" 
										  + "<style name='myDataLabels' type='font' font='Arial' size='12' bold='1'/>"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>" 
										  + "<apply toObject='DataLabels' styles='myDataLabels'/>"
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
			//Utility.debugln("________sb________"+sb.toString());
		}
		return sb.toString();
	}
	
	/**
	 * 生成3D圈图
	 * @param ItemName
	 * @param ItemValue
	 * @param PercentageValue
	 * @param FileName
	 * @param Caption
	 * @param ValueUnit
	 * @return
	 * @throws Exception
	 */
	public String ganerateDoughnut3D(String[] ItemName,String[] ItemValue,String[] PercentageValue,String FileName,String Caption,String ValueUnit) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
				
		int ParameterCount = ItemName.length;
		//传入参数长度无误，开始生成xml格式文件
		if(ItemName.length == ItemValue.length && ItemName.length == PercentageValue.length){
				
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "'palette='2' decimals='2' enableSmartLabels='1' enableRotation='0' bgColor='99CCFF,FFFFFF' bgAlpha='0,100' bgRatio='0,100' bgAngle='360' showBorder='1' startingAngle='70' showPercentageValues='1' showPercentInToolTip='1' baseFontSize='10' baseFontColor='nav' baseFont='宋体'>";
			sb.append(ChartRootStr);
			
			for(int i=0;i<ParameterCount;i++){	
				
				String setFormatStr = "<set label='" + Utility.trimNull(ItemName[i]) + ":" + Utility.trimNull(ItemValue[i]) + Utility.trimNull(ValueUnit) + "' value='" + Utility.trimNull(PercentageValue[i]) + "'/>";
				sb.append(setFormatStr);
			}
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='25' bold='1'/>" 
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>" 
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		}
		return sb.toString();
	}
	
	
	/**
	 * 生成多列柱状图的方法 add by guiFeng
	 *   
	 */
	public String generateMutiColumn(String[] yAxisValue,String[][] xAxisValue,String[] ItemName,String queryName,String FileName,String Caption,String linkURL) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容		
		int yLength = yAxisValue.length;
		int xLength = xAxisValue.length;
		//传入参数长度无误，开始生成xml格式文件
		if(yAxisValue.length == xAxisValue[0].length){
			
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "' subcaption='("+queryName+")'   baseFontSizeshownames='1' showvalues='0' decimals='2' formatNumberScale='2' clickURL='"+ linkURL +"' >";
			sb.append(ChartRootStr);
			
			sb.append("<categories >");
			for(int i=0;i<yLength;i++){	//填充y坐标的值				
				String setFormatStr = "<category  label='" + Utility.trimNull(yAxisValue[i])+"'/>"; 
				sb.append(setFormatStr);
			}
			sb.append("</categories>");
		
			for(int i=0;i < xLength;i++){	//填充x坐标的值
				String setFormatStr = "<dataset seriesName='"+Utility.trimNull(ItemName[i])+"' showValues='0' labelDisplay='ROTATE'>";
				sb.append(setFormatStr);
				for(int j=0;j<xAxisValue[i].length;j++){					
					String setFormatStr2 = "<set value='" + Utility.trimNull(xAxisValue[i][j]) +"'/>";
					sb.append(setFormatStr2);
				}
				sb.append("</dataset>");
			}
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='15' bold='0'/>" 
										  + "<style name='myTooltip' type='font' font='Arial' size='12' bold='0' />"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>" 
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		}
		return sb.toString();
	} 
	
	public String ganerateLine2D(String[] xAxisValue,String[] yAxisValue,String xAxisName,String yAxisName,String Caption) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
			
		int ParameterCount = xAxisValue.length;
		//传入参数长度无误，开始生成xml格式文件
		if(xAxisValue.length == yAxisValue.length){
			
//<chart caption='Daily Visits' subcaption='(from 8/6/2006 to 8/12/2006)' lineThickness='1' showValues='0' formatNumberScale='0' anchorRadius='2' divLineAlpha='20' divLineColor='CC3300' divLineIsDashed='1' showAlternateHGridColor='1' alternateHGridAlpha='5' alternateHGridColor='CC3300' shadowAlpha='40' labelStep='2' numvdivlines='5' chartRightMargin='35' bgColor='FFFFFF,CC3300' bgAngle='270' bgAlpha='10,10'>"			
			
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "' xAxisName='" + Utility.trimNull(xAxisName) + "' yAxisName='" + Utility.trimNull(yAxisName) + "' rotateYAxisName='0' showValues='1' decimals='4' formatNumberScale='0' baseFontSize='10' baseFontColor='nav' baseFont='宋体'  labelDisplay='WRAP' >";
			sb.append(ChartRootStr);
		
			for(int i=0;i<ParameterCount;i++){	
			
				String setFormatStr = "<set label='" + Utility.trimNull(xAxisValue[i]) + "' value='" + Utility.trimNull(yAxisValue[i]) + "'/>";
				sb.append(setFormatStr);
			}
			String line = " <trendlines><line startValue='1' color='91C728' displayValue='1' showOnTop='1'/></trendlines>";
			sb.append(line);
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='15' bold='0'/>"
										  + "<style name='myTooltip' type='font' font='Arial' size='12' bold='1' />" 
										  + "<style name='myDataLabels' type='font' font='Arial' size='8' bold='1'/>"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>" 
										  + "<apply toObject='DataLabels' styles='myDataLabels'/>"
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
			//Utility.debugln("________sb________"+sb.toString());
		}
		return sb.toString();
	}
	
	/**
	 * 新建多条曲线图的方法 add by guiFeng
	 *  
	 */
	public String generateMutiLine(String[] Name,String[] Year, String[][] Value,String FileName,String Caption,String linkURL) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
			
		int ParameterCount = Name.length;
		int ParameterCount2 = Year.length;
		int ParameterCount3 = Value.length;
		
		//传入参数长度无误，开始生成xml格式文件
		if(Year.length == Value.length){
			
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "' subcaption='("+Caption+")' baseFontSizeshownames='1' showvalues='0' decimals='4' formatNumberScale='2' labelDisplay='Rotate' slantLabels='1' clickURL='"+ linkURL +"' >";
			sb.append(ChartRootStr);
			
			sb.append("<categories>");
			for(int i=0;i<ParameterCount2;i++){	
				
				String setFormatStr = "<category  label='" + Utility.trimPercent(Year[i])+"'/>";
				sb.append(setFormatStr);
			}
			sb.append("</categories>");
					
			for(int i=0;i < ParameterCount;i++){	
				String setFormatStr = "<dataset seriesName='" + Utility.trimNull(Name[i]) +"' showValues='0'>";
				sb.append(setFormatStr);
				for(int j=0;j<ParameterCount3;j++){					
					//Utility.debug(i+"value--"+j+"lenth:"+ParameterCount3+"--"+Utility.trimNull(Value[j][i]));
					String setFormatStr2 = "<set value='" + Utility.trimNull(Value[j][i]) +"'/>";
					sb.append(setFormatStr2);
				}
				sb.append("</dataset>");
			}		
			String line = " <trendlines><line startValue='1' color='91C728' displayValue='1' showOnTop='1'/></trendlines>";
			sb.append(line);
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='15' bold='0'/>" 
										  + "<style name='myTooltip' type='font' font='Arial' size='12' bold='0' />"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>" 
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		}
		
		
		
		return sb.toString();
	}

	
	public String gridComponents()throws Exception{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
		String ChartRootStr = "<graph caption='Monthly Unit Sales' xAxisName='Month' yAxisName='Units' showValues='0' decimals='0' formatNumberScale='0' labelDisplay='Rotate'>";
		sb.append(ChartRootStr);
		
		String setStr = "<set label='Jan' value='462' /><set label='Feb' value='857' /><set label='Mar' value='671' /><set label='Apr' value='494' />"
			+"<set label='May' value='761' /><set label='Jun' value='960' /><set label='Jul' value='629' /><set label='Aug' value='622' />"
			+"<set label='Sep' value='376' /><set label='Oct' value='494' /><set label='Nov' value='761' /><set label='Dec' value='960' />";
		sb.append(setStr);		
		sb.append("<categories>");
		String endStr = "</graph>";
		sb.append(endStr);
		return sb.toString();
	}

	/**
	 * 生成柱状+曲线图的方法
	 *  (test) 
	 */
	public String generateMutiColumnAndLine(String queryName,String FileName,String Caption,String linkURL) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容		
		//传入参数长度无误，开始生成xml格式文件

			
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "' subcaption='("+queryName+")'   baseFontSizeshownames='1' showvalues='0' decimals='2' formatNumberScale='2' clickURL='"+ linkURL +"' >";
			sb.append(ChartRootStr);
			
			sb.append("<categories >");

			sb.append("<category label='Aug 05' /> "
				  +"<category label='Sep 05' /> "
				  +"<category label='Oct 05' /> "
				  +"<category label='Nov 05' /> "
				  +"<category label='Dec 05' /> ");	
			sb.append("</categories>");
		

			
			sb.append(" <dataset seriesName='Product A'>"+"<set value='36000' />" 
					+"<set value='34300' /> "
					+"<set value='30000' /> "
					+"<set value='27800' /> "
					+"<set value='25000' /> "
					+"</dataset>"
					+"<dataset seriesname='Product B'>"
					+"<set value='31000' /> "
					+"<set value='29300' /> "
					+"<set value='26000' /> "
					+"<set value='21000' /> "
					+"<set value='20500' /> "
					+"</dataset>"
					+"<dataset seriesname='Predicted' renderAs='Line'>"
					+"<set value='25000' /> "
					+"<set value='23000' /> "
					+"<set value='20000' /> "
					+"<set value='18000' /> "
					+"<set value='14500' /> "
					+"</dataset>"
					+"<dataset seriesname='2004 Avg.' renderAs='Line'>"
					+"<set value='17000' /> "
					+"<set value='15000' /> "
					+"<set value='16000' /> "
					+"<set value='11500' /> "
					+"<set value='10000' /> "
					+"</dataset>");
			
			
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='15' bold='0'/>" 
										  + "<style name='myTooltip' type='font' font='Arial' size='12' bold='0' />"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>" 
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		return sb.toString();
	} 
	/**
	 * 新建2D横图的方法 add by guiFeng
	 *  
	 * @param ItemName
	 * @param ItemValue
	 * @param FileName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String generateBar2D(String[] ItemName,String[] ItemValue,String FileName,String Caption,String ValueUnit) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
				
		int ParameterCount = ItemName.length;
		//传入参数长度无误，开始生成xml格式文件
		if(ItemName.length == ItemValue.length){
				
			String ChartRootStr = "<chart caption='" + Utility.trimNull(Caption) + "'bgColor='99CCFF,FFFFFF' baseFontSize='7' baseFontColor='nav' baseFont='宋体' decimalPrecision='1' formatNumberScale='0'>";
			sb.append(ChartRootStr);
			
			for(int i=0;i<ParameterCount;i++){	
				
				String setFormatStr = "<set label='" + Utility.trimNull(ItemName[i]) +"' value='" + Utility.trimNull(ItemValue[i]) + "'/>";
				sb.append(setFormatStr);
			}
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='11' bold='0'/>" 
										  + "<style name='myTooltip' type='font' font='Arial' size='15' bold='0' />"
										  + "<style name='myDataLabels' type='font' font='Arial' size='12' bold='0'/>"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>"
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "<apply toObject='DataLabels' styles='myDataLabels'/>" 
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		}
		return sb.toString();
	}
	
	/**
	 * Stacked Bar 2D Chart
	 * @param yAxisValue
	 * @param xAxisValue
	 * @param Value
	 * @param color
	 * @param FileName
	 * @param Caption
	 * @param ValueUnit
	 * @return
	 * @throws Exception
	 */
	public String generateStackedBar2D(String[] yAxisValue,String[] xAxisValue,String[][] Value,String[] color,String FileName,String Caption,String ValueUnit) throws Exception
	{
		StringBuffer sb = new StringBuffer("");//返回的文件内容
				
		int ParameterCount = xAxisValue.length;
		//传入参数长度无误，开始生成xml格式文件
		if(yAxisValue.length == yAxisValue.length){
				
			String ChartRootStr = "<chart palette='2' caption='" + Utility.trimNull(Caption) + "' shownames='1' showvalues='0'  numberSuffix='%25' showSum='1' decimals='0' useRoundEdges='1' formatNumberScale='0'>";
			sb.append(ChartRootStr);
			
			sb.append("<categories>");
			for(int i=0;i<yAxisValue.length;i++){	
				String setFormatStr = "<category  label='" + Utility.trimPercent(yAxisValue[i])+"'/>";
				sb.append(setFormatStr);
			}
			sb.append("</categories>");
			
			for(int i=0;i<ParameterCount;i++){
				String setFormatStr = "<dataset seriesName='" + Utility.trimNull(xAxisValue[i]) +"'  color='" + Utility.trimNull(color[i]) +"'showValues='0'>";
				sb.append(setFormatStr);
				for(int j=0;j<yAxisValue.length;j++){
					String setFormatStr2 = "<set value='" + Utility.trimNull(Value[i][j]) +"'/>";
					sb.append(setFormatStr2);
				}
				sb.append("</dataset>");
			}
			String CaptionDefinitionStr =	"<styles>" //定义标题格式
										  + "<definition>"  
										  + "<style name='myCaptionFont' type='font' font='Arial' size='11' bold='0'/>" 
										  + "<style name='myTooltip' type='font' font='Arial' size='15' bold='0' />"
										  + "<style name='myDataLabels' type='font' font='Arial' size='12' bold='0'/>"
										  + "</definition>" 
										  + "<application>" 
										  + "<apply toObject='Caption' styles='myCaptionFont'/>"
										  + "<apply toObject='Tooltip' styles='myTooltip'/>"
										  + "<apply toObject='DataLabels' styles='myDataLabels'/>" 
										  + "</application>"
										  + "</styles>";
			sb.append(CaptionDefinitionStr);
			String EndStr = "</chart>";
			sb.append(EndStr);
		}
		return sb.toString();
	}
}
