package enfo.crm.cash;

import java.awt.Color;
import java.awt.Font;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;

public class WebChart {
	
	public static String createLineChart(String chartTitle, String x,
	        String y,CategoryDataset linedataset,HttpServletRequest request){
		String filename = "";
		JFreeChart chart = ChartFactory.createLineChart(chartTitle,// 报表题目，字符串类型
			x, // 横轴
			y, // 纵轴
			linedataset, // 获得数据集
			PlotOrientation.VERTICAL, // 图标方向垂直
			true, // 显示图例
			true, // 生成工具
			false // 不用生成URL地址
			);
		//生成图形;
		CategoryPlot plot = chart.getCategoryPlot();
		//设置标题并且设置其字体，防止中文乱码
		TextTitle textTitle = new TextTitle(chartTitle);
		textTitle.setFont(new Font("宋体", Font.BOLD, 30));
		chart.setTitle(textTitle);
		chart.setBackgroundPaint(Color.white);
		plot.setBackgroundPaint(Color.white);//设置背景颜色
		plot.setNoDataMessage("没有数据");//没有数据时显示的文字说明。
		//设置X轴
		CategoryAxis domainAxis = plot.getDomainAxis();
		//domainAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15)); // 设置横轴字体
		//domainAxis.setTickLabelFont(new Font("宋书", Font.PLAIN, 15));// 设置坐标轴标尺值字体
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
		//设置Y轴
		NumberAxis numAxis = (NumberAxis) plot.getRangeAxis();
		numAxis.setAutoRange(false);
		//numAxis.setFixedAutoRange(range.doubleValue());//Y轴范围
		//numAxis.setTickUnit(new NumberTickUnit(0.1));//间隔
		//获取显示线条的对象 
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0,Color.blue);//设置折线的颜色
        plot.setRenderer(renderer);
        try {
	    	filename = ServletUtilities.saveChartAsPNG(chart, 420, 280, null, request.getSession());
	    } catch (Exception e) {
	    	System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} 

	    return filename;
	}

}