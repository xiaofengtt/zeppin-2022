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
		JFreeChart chart = ChartFactory.createLineChart(chartTitle,// ������Ŀ���ַ�������
			x, // ����
			y, // ����
			linedataset, // ������ݼ�
			PlotOrientation.VERTICAL, // ͼ�귽��ֱ
			true, // ��ʾͼ��
			true, // ���ɹ���
			false // ��������URL��ַ
			);
		//����ͼ��;
		CategoryPlot plot = chart.getCategoryPlot();
		//���ñ��Ⲣ�����������壬��ֹ��������
		TextTitle textTitle = new TextTitle(chartTitle);
		textTitle.setFont(new Font("����", Font.BOLD, 30));
		chart.setTitle(textTitle);
		chart.setBackgroundPaint(Color.white);
		plot.setBackgroundPaint(Color.white);//���ñ�����ɫ
		plot.setNoDataMessage("û������");//û������ʱ��ʾ������˵����
		//����X��
		CategoryAxis domainAxis = plot.getDomainAxis();
		//domainAxis.setLabelFont(new Font("����", Font.PLAIN, 15)); // ���ú�������
		//domainAxis.setTickLabelFont(new Font("����", Font.PLAIN, 15));// ������������ֵ����
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// ���� lable ��λ�� �����ϵ� Lable 45����б DOWN_45
		//����Y��
		NumberAxis numAxis = (NumberAxis) plot.getRangeAxis();
		numAxis.setAutoRange(false);
		//numAxis.setFixedAutoRange(range.doubleValue());//Y�᷶Χ
		//numAxis.setTickUnit(new NumberTickUnit(0.1));//���
		//��ȡ��ʾ�����Ķ��� 
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0,Color.blue);//�������ߵ���ɫ
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