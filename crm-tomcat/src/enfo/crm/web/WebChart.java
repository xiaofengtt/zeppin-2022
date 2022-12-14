package enfo.crm.web;

import java.io.*;
import java.math.BigDecimal;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.*;
import java.util.*;
import java.awt.color.*;
import jxl.write.*;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.jfree.util.TableOrder;
import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.data.time.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.axis.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.labels.CategoryLabelGenerator;
import org.jfree.chart.labels.StandardCategoryLabelGenerator;
import org.jfree.chart.labels.StandardXYLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.urls.*;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.servlet.*;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.ui.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.util.Rotation;

import java.awt.Insets;
import java.awt.BasicStroke;import java.io.PrintWriter;
import org.jfree.data.*;

import java.awt.Insets;import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import org.jfree.data.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.urls.*;
import org.jfree.chart.servlet.*;//import org.jfree.chart.labels.StandardPieToolTipGenerator;

public class WebChart {
	private static DefaultPieDataset pieDataset = null;
	private static DefaultCategoryDataset barDataSet = null;
	private static TimeSeries dateSeries = null;
	private static TimeSeries dateSeries1 = null;
	private static TimeSeries dateSeries2 = null;
	private static TimeSeries dateS[] = null;
	private String Message = null;

	public void initBarChart() {
		barDataSet = new DefaultCategoryDataset();
	}

	public void initXYChart(String text) {
		dateSeries = new TimeSeries(text);
	}

	public void initXYChart2(String text, String text2) {
		dateSeries = new TimeSeries(text);
		dateSeries2 = new TimeSeries(text2);
	}

	public void initXYChart3(String text, String text1, String text2) {
		dateSeries = new TimeSeries(text);
		dateSeries1 = new TimeSeries(text1);
		dateSeries2 = new TimeSeries(text2);
	} //060801pj

	public void initXYChart0(String[] text) {
		dateS = new TimeSeries[text.length];
		for (int i = 0; i < text.length; i++) {
			dateS[i] = new TimeSeries(text[i]);
			System.out.println(dateS[i]);
		}
	}

	public void initPieChart() {
		pieDataset = new DefaultPieDataset();
	}

	public void addXYValue(Integer val1, double val2) {
		int day, month, year;
		String date = val1.toString();
		year = Integer.parseInt(date.substring(0, 4));
		month = Integer.parseInt(date.substring(4, 6));
		day = Integer.parseInt(date.substring(6, 8));
		dateSeries.add(new Day(day, month, year), val2);
	}

	public void addXYValue1(Integer val1, double val2) {
		int day, month, year;
		String date = val1.toString();
		year = Integer.parseInt(date.substring(0, 4));
		month = Integer.parseInt(date.substring(4, 6));
		day = Integer.parseInt(date.substring(6, 8));
		dateSeries1.add(new Day(day, month, year), val2);
	}
	public void addXYValue2(Integer val1, double val2) {
		int day, month, year;
		String date = val1.toString();
		year = Integer.parseInt(date.substring(0, 4));
		month = Integer.parseInt(date.substring(4, 6));
		day = Integer.parseInt(date.substring(6, 8));
		dateSeries2.add(new Day(day, month, year), val2);
	}

	public void addXYValue0(Integer val1, double val2, int i) {
		int day, month, year;
		String date = val1.toString();
		year = Integer.parseInt(date.substring(0, 4));
		month = Integer.parseInt(date.substring(4, 6));
		day = Integer.parseInt(date.substring(6, 8));
		dateS[i].add(new Day(day, month, year), val2);
	}

	public void addBarValue(double val, String name1, String name2) {
		barDataSet.addValue(val, name1, name2);
	}

	public void addPieValue(double val, String name1) {
		pieDataset.setValue(name1, val);
	}

	//??????????????	
	public String generateBar3DChart(
		HttpSession session,
		String[] params,
		int[] size,
		String Title2) {
		String filename = null;
		/**
		 * columnName[0]??
		 * columnName[1]??---??????????????????????????
		 * columnName[2]????????????
		 */
		try {
			JFreeChart chart =
				ChartFactory.createBarChart3D(
					"",
					params[1],
					params[2],
					barDataSet,
					PlotOrientation.VERTICAL,
					true,
					false,
					false);
			//chart.getRenderingHints().put(
			//	RenderingHints.KEY_TEXT_ANTIALIASING,
			//	RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(params[0], font);
			chart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			chart.addSubtitle(title2);
			chart.setBackgroundPaint(java.awt.Color.white);
			CategoryPlot plot = chart.getCategoryPlot();
			BarRenderer3D vBarRenderer3D = new BarRenderer3D();
			vBarRenderer3D.setBaseOutlinePaint(java.awt.Color.gray);
			vBarRenderer3D.setItemMargin(0.1);

			vBarRenderer3D.setItemLabelsVisible(true);
			vBarRenderer3D.setMaxBarWidth(0.05D); //??????????????????????
			vBarRenderer3D.setMinimumBarLength(0.1D);

			CategoryLabelGenerator generator =
				new StandardCategoryLabelGenerator("{2}", new DecimalFormat());
			//????????????????????????
			vBarRenderer3D.setLabelGenerator(generator);
			vBarRenderer3D.setItemLabelPaint(java.awt.Color.black);
			vBarRenderer3D.setItemLabelFont(new Font("????", Font.PLAIN, 12));

			ItemLabelPosition itemlabelposition =
				new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE12,
					TextAnchor.CENTER,
					TextAnchor.CENTER,
					0);
			ItemLabelPosition itemlabelposition2 =
				new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE6,
					TextAnchor.CENTER,
					TextAnchor.CENTER,
					0);//??????
			vBarRenderer3D.setPositiveItemLabelPositionFallback(
				itemlabelposition);
			vBarRenderer3D.setNegativeItemLabelPositionFallback(
				itemlabelposition2);
			vBarRenderer3D.setItemLabelsVisible(true);
			plot.setRenderer(vBarRenderer3D);

			ValueAxis rangeAxis = plot.getRangeAxis();
			rangeAxis.setLowerMargin(0.3D);
			rangeAxis.setLabelAngle(-1.45D);
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			//??????????????????????????
			plot.setRangeAxis(rangeAxis);
			plot.setRenderer(vBarRenderer3D);
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setForegroundAlpha(0.5f);
			plot.setNoDataMessageFont(font);
			plot.setNoDataMessage(Message);
			plot.setBackgroundPaint(java.awt.Color.white); //????????????????
			plot.setDomainGridlinePaint(java.awt.Color.white); //????????????????
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} finally {
			barDataSet = null;
		}
		return filename;

	}

	//??????????????
	public String generateXYChart5(
		HttpSession session,
		String[] params,
		int[] size,
		String Url,
		String Title,
		String Title2) {
		String filename = null;
		try {
			if (params.length < 2)
				throw new Exception("????????????????????????????????");
			TimeSeriesCollection tsc = new TimeSeriesCollection(dateSeries);
			tsc.setDomainIsPointsInTime(true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
					sdf,NumberFormat.getInstance());
			TimeSeriesURLGenerator urlg = new TimeSeriesURLGenerator(sdf, Url, "value", "date");
			//????????
			DateAxis dateAxis = new DateAxis(params[0]);
			dateAxis.setDateFormatOverride(sdf);
			NumberAxis valueAxis = new NumberAxis(params[1]);
			valueAxis.setAutoRangeIncludesZero(false);
			// override default		
			StandardXYItemRenderer renderer =
				new StandardXYItemRenderer(
					StandardXYItemRenderer.LINES
						+ StandardXYItemRenderer.SHAPES,
					ttg,
					urlg);
			renderer.setShapesFilled(true);
	//		renderer.setLabelGenerator(new StandardXYLabelGenerator());
			renderer.setItemLabelsVisible(true);
			XYPlot plot = new XYPlot(tsc, dateAxis, valueAxis, renderer);
			plot.setBackgroundPaint(java.awt.Color.lightGray);
			plot.setDomainGridlinePaint(java.awt.Color.white);
			plot.setRangeGridlinePaint(java.awt.Color.white);
			plot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
			plot.setDomainCrosshairVisible(true);
			plot.setRangeCrosshairVisible(true);
			JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(Title, font);
			chart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			chart.addSubtitle(title2);
			chart.setBackgroundPaint(java.awt.Color.lightGray);
			filename = ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		}
		return filename;
	}
	
//	??????????????(????)
	  public String generateXYChart6(
		  HttpSession session,
		  String[] params,
		  int[] size,
		  String Url,
		  String Title,
		  String Title2) {
		  String filename = null;
		  try {
			  if (params.length < 2)
				  throw new Exception("????????????????????????????????");
			  TimeSeriesCollection tsc = new TimeSeriesCollection();
			  tsc.addSeries(dateSeries);
			  tsc.addSeries(dateSeries1);
			  tsc.addSeries(dateSeries2);
			  tsc.setDomainIsPointsInTime(true);
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			  StandardXYToolTipGenerator ttg =
				  new StandardXYToolTipGenerator(
					  StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
					  sdf,
					  NumberFormat.getInstance());
			  TimeSeriesURLGenerator urlg =
				  new TimeSeriesURLGenerator(sdf, Url, "value", "date");
			  //????????
			  DateAxis dateAxis = new DateAxis(params[0]);
			  dateAxis.setDateFormatOverride(sdf);
			  NumberAxis valueAxis = new NumberAxis(params[1]);
			  valueAxis.setAutoRangeIncludesZero(false);
			  // override default
			  StandardXYItemRenderer renderer =
				  new StandardXYItemRenderer(
					  StandardXYItemRenderer.LINES
						  + StandardXYItemRenderer.SHAPES,
					  ttg,
					  urlg);
			  if(size[2]==1){	
			  renderer.setShapesFilled(true);
			  renderer.setLabelGenerator(new StandardXYLabelGenerator());
			  renderer.setItemLabelsVisible(true);
			  }
			  XYPlot plot = new XYPlot(tsc, dateAxis, valueAxis, renderer);
			  plot.setBackgroundPaint(java.awt.Color.lightGray);
			  plot.setDomainGridlinePaint(java.awt.Color.white);
			  plot.setRangeGridlinePaint(java.awt.Color.white);
			  plot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
			  plot.setDomainCrosshairVisible(true);
			  plot.setRangeCrosshairVisible(true);
			  JFreeChart chart =
				  new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
			  Font font = new Font("????", Font.CENTER_BASELINE, 20);
			  TextTitle title = new TextTitle(Title, font);
			  chart.setTitle(title);
			  Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			  TextTitle title2 = new TextTitle(Title2, font2);
			  chart.addSubtitle(title2);
			  chart.setBackgroundPaint(java.awt.Color.lightGray);
			  filename =
				  ServletUtilities.saveChartAsPNG(
					  chart,
					  size[0],
					  size[1],
					  null,
					  session);
		  } catch (Exception e) {
			  System.out.println("Exception : " + e.toString());
			  e.printStackTrace(System.out);
		  }
		  return filename;
	  }

	//	??????060809pj
	public String generateXYChart0(
		HttpSession session,
		String[] params,
		int[] size,
		String Title,
		String Title2,
		int length) {
		String filename = null;
		try {
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			for (int i = 0; i < length; i++) {
				dataset.addSeries(dateS[i]);
			}
			dataset.setDomainIsPointsInTime(true);
			JFreeChart jfreechart =
				ChartFactory.createTimeSeriesChart(
					params[0],
					params[1],
					params[2],
					dataset,
					true,
					true,
					false);
			jfreechart.getRenderingHints().put(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(Title, font);
			jfreechart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			jfreechart.addSubtitle(title2);
			XYPlot xyplot = (XYPlot) jfreechart.getPlot(); //???? plot 
			xyplot.setBackgroundPaint(java.awt.Color.lightGray); //
			xyplot.setDomainGridlinePaint(java.awt.Color.white);
			xyplot.setRangeGridlinePaint(java.awt.Color.white);
			xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
			xyplot.setDomainCrosshairVisible(true);
			xyplot.setRangeCrosshairVisible(true);
			xyplot.setNoDataMessageFont(font);
			xyplot.setNoDataMessage(Message);
			org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer =
				xyplot.getRenderer();
			if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
				XYLineAndShapeRenderer xylineandshaperenderer =
					(XYLineAndShapeRenderer) xyitemrenderer;
				xylineandshaperenderer.setDefaultShapesFilled(true); //?????????????? 
			}
			NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
			numberaxis.setLabelAngle(1.55D);
			DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
			//??domain ????????????????????
			dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
			filename =
				ServletUtilities.saveChartAsPNG(
					jfreechart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		}
		return filename;
	}

	//????????????
	public String generateXYChart2(
		HttpSession session,
		String[] params,
		int[] size,
		String Title,
		String Title2) {
		String filename = null;
		try {
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(dateSeries);
			dataset.setDomainIsPointsInTime(true);
			JFreeChart jfreechart =
				ChartFactory.createTimeSeriesChart(
					"",
					params[0],
					params[1],
					dataset,
					true,
					true,
					false);
			jfreechart.getRenderingHints().put(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			TimePeriodValuesCollection timeseriescollection =
				new TimePeriodValuesCollection();
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(Title, font);
			jfreechart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			jfreechart.addSubtitle(title2);
			XYPlot xyplot = (XYPlot) jfreechart.getPlot();
			xyplot.setBackgroundPaint(java.awt.Color.lightGray); //
			xyplot.setDomainGridlinePaint(java.awt.Color.white);
			xyplot.setRangeGridlinePaint(java.awt.Color.white);
			xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
			xyplot.setDomainCrosshairVisible(true);
			xyplot.setRangeCrosshairVisible(true);
			xyplot.setNoDataMessageFont(font);
			xyplot.setNoDataMessage(Message);
			xyplot.setDataset(dataset);
			org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer =
				xyplot.getRenderer();
			if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
				System.out.println("ok");
				XYLineAndShapeRenderer xylineandshaperenderer =
					(XYLineAndShapeRenderer) xyitemrenderer;
				xylineandshaperenderer.setDefaultShapesVisible(true); //?????????? 
				xylineandshaperenderer.setDefaultShapesFilled(true); //?????????????? 
			}
			NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
			numberaxis.setLabelAngle(1.55D);
			DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
			dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
			timeseriescollection.setDomainIsPointsInTime(true);
			//x??????????????????????????????????????
			filename =
				ServletUtilities.saveChartAsPNG(
					jfreechart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		}
		return filename;
	}

	//????????????
	public String generateXYChart3(
		HttpSession session,
		String[] params,
		int[] size,
		String Title,
		String Title2) {
		String filename = null;
		try { //????????
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(dateSeries);
			dataset.addSeries(dateSeries2);
			dataset.setDomainIsPointsInTime(true);
			//domain?????????????????????????????????????? 				 
			JFreeChart jfreechart =
				ChartFactory.createTimeSeriesChart(
					"",
					params[0],
					params[1],
					dataset,
					true,
					true,
					false);
			jfreechart.getRenderingHints().put(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			//060731 pj
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(Title, font);
			jfreechart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			jfreechart.addSubtitle(title2);
			XYPlot xyplot = (XYPlot) jfreechart.getPlot(); //???? plot
			xyplot.setBackgroundPaint(java.awt.Color.lightGray); //????????????????
			xyplot.setDomainGridlinePaint(java.awt.Color.white); //????????????????
			xyplot.setRangeGridlinePaint(java.awt.Color.white);
			xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
			xyplot.setDomainCrosshairVisible(true);
			xyplot.setRangeCrosshairVisible(true);
			xyplot.setNoDataMessageFont(font);
			xyplot.setNoDataMessage(Message); //??????????????????
			org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer =
				xyplot.getRenderer();
			NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
			numberaxis.setAutoTickUnitSelection(false); //????????????????????????????
			double rangetick = 0.001D;
			numberaxis.setTickUnit(new NumberTickUnit(rangetick));
			//y????????????0.001
			numberaxis.setNumberFormatOverride(new DecimalFormat("0.00%"));
			numberaxis.setLabelAngle(1.55D);
			DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
			//??domain ????????????????????
			dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
			filename =
				ServletUtilities.saveChartAsPNG(
					jfreechart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		}
		return filename;
	}

	//????????????????
	public String generateXYChart4(
		HttpSession session,
		String[] params,
		int[] size,
		String Title,
		String Title2) {
		String filename = null;
		try {
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(dateSeries);
			dataset.addSeries(dateSeries1);
			dataset.addSeries(dateSeries2);
			dataset.setDomainIsPointsInTime(true);
			JFreeChart jfreechart =
				ChartFactory.createTimeSeriesChart(
					"",
					params[0],
					params[1],
					dataset,
					true,
					true,
					false);
			jfreechart.getRenderingHints().put(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(Title, font);
			jfreechart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			jfreechart.addSubtitle(title2);
			XYPlot xyplot = (XYPlot) jfreechart.getPlot();
			xyplot.setBackgroundPaint(java.awt.Color.lightGray);
			xyplot.setDomainGridlinePaint(java.awt.Color.white);
			xyplot.setRangeGridlinePaint(java.awt.Color.white);
			xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
			xyplot.setDomainCrosshairVisible(true);
			xyplot.setRangeCrosshairVisible(true);
			xyplot.setNoDataMessageFont(font);
			xyplot.setNoDataMessage(Message);
			xyplot.setWeight(4);
			org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer =
				xyplot.getRenderer();
			xyitemrenderer.setSeriesPaint(2, java.awt.Color.green);
			//??series3  ??????????????????
			xyplot.setRenderer(xyitemrenderer);
			if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
				XYLineAndShapeRenderer xylineandshaperenderer =
					(XYLineAndShapeRenderer) xyitemrenderer;
			}
			NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
			numberaxis.setLabelAngle(1.55D);
			DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
			//??domain ????????????????????
			dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));
			filename =
				ServletUtilities.saveChartAsPNG(
					jfreechart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		}
		return filename;
	}

	/**??EXCEL????????????PNG????
	 * 
	 * @param filename			EXCEL????
	 * @param imagename		?????? ??????????PNG????????
	 * @param start_row		??????????????????????
	 * @throws Exception
	 */
	public static void writeImage(
		String filename,
		String imagename,
		String imageUrl,
		int start_row)
		throws Exception {
		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setGCDisabled(true);
		WritableWorkbook wwb = Workbook.createWorkbook(new File(filename),wbs);
		WritableSheet ws = wwb.createSheet(imagename, 0);
		File file = new File(imageUrl);
		WritableImage image = new WritableImage(1, start_row, 6, 18, file);
		ws.addImage(image);
		wwb.write();
		wwb.close();
	}

	public String generateBarChart(
		HttpSession session,
		String[] params,
		int[] size,
		String Title2) {
		String filename = null;
		/**
		 * columnName[0]??
		 * columnName[1]??---??????????????????????????
		 * columnName[2]????????????
		 */
		try {
			if (params.length < 3)
				throw new Exception("????????????????????????????????");

				JFreeChart chart = ChartFactory.createBarChart("", // ????????
		params[1], // ????????????????
		params[2], // ????????????????
		barDataSet, // ??????
		PlotOrientation.VERTICAL, // ????????????????????
		true, // ????????????(??????????????????????false)
		true, // ????????????
		false // ????????URL????
	);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(params[0], font);
			chart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			chart.addSubtitle(title2);
			chart.setBackgroundPaint(java.awt.Color.white);
			CategoryPlot plot = chart.getCategoryPlot();
			BarRenderer render = new BarRenderer();
			render.setBaseOutlinePaint(java.awt.Color.gray);
			render.setItemMargin(0.1);
			plot.setRenderer(render);
			plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setForegroundAlpha(0.5f);
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} finally {
			barDataSet = null;
		}
		return filename;

	}

	public String generateLine3DChart(
		HttpSession session,
		String[] params,
		int[] size,
		String Title,
		String Title2) {
		String filename = null;
		try {
			JFreeChart chart =
				ChartFactory.createLineChart3D(
					"",
					"",
					"",
					barDataSet,
					PlotOrientation.VERTICAL,
					true,
					false,
					false);
			CategoryPlot vCategoryPlot = chart.getCategoryPlot();
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(Title, font);
			chart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			chart.addSubtitle(title2);
			chart.setBackgroundPaint(java.awt.Color.white);
			LineRenderer3D vLineRenderer3D = new LineRenderer3D();
			vLineRenderer3D.setBaseOutlinePaint(java.awt.Color.gray);
			vCategoryPlot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
			vCategoryPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			vCategoryPlot.setDomainGridlinesVisible(true);
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		}
		return filename;
	}

	//??????	
	public String generateAreaChart(
		HttpSession session,
		String[] params,
		int[] size,
		String Title2) {
		String filename = null;
		/**
		 * columnName[0]??
		 * columnName[1]??---??????????????????????????
		 * columnName[2]????????????
		 */
		try {

			JFreeChart chart =
				ChartFactory.createStackedAreaChart(
					"",
					"",
					"",
					barDataSet,
					PlotOrientation.VERTICAL,
					true,
					false,
					false);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(params[0], font);
			chart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			chart.addSubtitle(title2);
			chart.setBackgroundPaint(java.awt.Color.white);
			CategoryPlot plot = chart.getCategoryPlot();
			StackedAreaRenderer vStackedAreaRenderer =
				new StackedAreaRenderer();
			vStackedAreaRenderer.setBaseOutlinePaint(java.awt.Color.gray);
			plot.setRenderer(vStackedAreaRenderer);
			plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			plot.setForegroundAlpha(0.8f);
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} finally {
			barDataSet = null;
		}
		return filename;

	}

	//	????????????????????????????????????
	public String generateBarChart(
		HttpSession session,
		String[] params,
		int[] size,
		boolean saveFlag) {
		String filename = null;
		/**
		 * columnName[0]??
		 * columnName[1]??---????????????????
		 * columnName[2]????????????
		 */
		try {
			if (params.length < 3)
				throw new Exception("????????????????????????????????");
				JFreeChart chart =
					ChartFactory.createBarChart(params[0], // ????????
		params[1], // ????????????????
		params[2], // ????????????????
		barDataSet, // ??????
		PlotOrientation.VERTICAL, // ????????????????????
		true, // ????????????(??????????????????????false)
		true, // ????????????
		false // ????????URL????
	);
			chart.setBackgroundPaint(java.awt.Color.white);

			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
			if (saveFlag) {
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream("D:\\??????????.jpg");
					ChartUtilities.writeChartAsJPEG(
						fos,
						100,
						chart,
						800,
						600,
						null);
				} finally {
					fos.close();
				}
			}
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} finally {
			barDataSet = null;
		}
		return filename;
	}

	public String generateXYChart(
		HttpSession session,
		String[] params,
		int[] size,
		String Url,
		String Title,
		String Title2) {
		String filename = null;
		try {
			if (params.length < 2)
				throw new Exception("????????????????????????????????");
			TimeSeriesCollection tsc = new TimeSeriesCollection(dateSeries);
			tsc.setDomainIsPointsInTime(true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StandardXYToolTipGenerator ttg =
				new StandardXYToolTipGenerator(
					StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
					sdf,
					NumberFormat.getInstance());
			TimeSeriesURLGenerator urlg =
				new TimeSeriesURLGenerator(sdf, Url, "value", "date");
			//????????
			DateAxis dateAxis = new DateAxis(params[0]);
			dateAxis.setDateFormatOverride(sdf);
			NumberAxis valueAxis = new NumberAxis(params[1]);
			valueAxis.setAutoRangeIncludesZero(false);
			// override default		
			StandardXYItemRenderer renderer =
				new StandardXYItemRenderer(
					StandardXYItemRenderer.LINES
						+ StandardXYItemRenderer.SHAPES,
					ttg,
					urlg);
			renderer.setShapesFilled(true);
			renderer.setLabelGenerator(new StandardXYLabelGenerator());
			renderer.setItemLabelsVisible(true);
			XYPlot plot = new XYPlot(tsc, dateAxis, valueAxis, renderer);
			plot.setBackgroundPaint(java.awt.Color.lightGray);
			plot.setDomainGridlinePaint(java.awt.Color.white);
			plot.setRangeGridlinePaint(java.awt.Color.white);
			plot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
			plot.setDomainCrosshairVisible(true);
			plot.setRangeCrosshairVisible(true);
			JFreeChart chart =
				new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(Title, font);
			chart.setTitle(title);
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(Title2, font2);
			chart.addSubtitle(title2);
			chart.setBackgroundPaint(java.awt.Color.white);
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		}
		return filename;
	}
	//????????????
	public String generatePie3D2Chart(
		HttpSession session,
		String[] params,
		int[] size,
		String Url) {
		String filename = null;
		try {
			PiePlot3D plot = new PiePlot3D(pieDataset);
			plot.setURLGenerator(new StandardPieURLGenerator(Url, "section"));
			//????????????
			JFreeChart chart =
				new JFreeChart(
					params[0],
					PiePlot.DEFAULT_LABEL_FONT,
					plot,
					true);
			//chart.getRenderingHints().put(
			//RenderingHints.KEY_TEXT_ANTIALIASING,
			//RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(params[0], font);
			chart.setTitle(title);//????1
			Font font2 = new Font("????", Font.CENTER_BASELINE, 12);
			TextTitle title2 = new TextTitle(params[1], font2);
			chart.addSubtitle(title2);//????2
			chart.setBackgroundPaint(java.awt.Color.white);//????????
			plot.setToolTipGenerator(new StandardPieItemLabelGenerator());
			//MAP????????????????????
			plot.setLabelGenerator(
				new StandardPieItemLabelGenerator(
					"{0}={1}({2})",
					NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));//??????????????????????null????????????????????????????
			plot.setDepthFactor(0.16);
			plot.setCircular(false);//??????????????????
			//plot.setLabelOutlinePaint(java.awt.Color.white);//????????????????
			plot.setForegroundAlpha(0.6f);//????????????????????0.0??1.0??
			plot.setLegendLabelGenerator( new StandardPieItemLabelGenerator(
					"{0}",
			NumberFormat.getNumberInstance(),
			NumberFormat.getNumberInstance()));
			plot.setNoDataMessageFont(font);
			plot.setNoDataMessage(Message);
			StandardEntityCollection sec = new StandardEntityCollection();
			ChartRenderingInfo info = new ChartRenderingInfo(sec);
			PrintWriter w = new PrintWriter(System.out); //????MAP????
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
			ChartUtilities.writeImageMap(w, "map0", info, false);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} finally {
			pieDataset = null;
		}
		return filename;

	}


	//??????????????
	public String generateMultiplePie3DChart(
		HttpSession session,
		String[] params,
		int[] size) {
		String filename = null;
		try {
			JFreeChart vFreeChartExtend = null;
			JFreeChart chart =
				ChartFactory.createMultiplePieChart3D(
					"",
					barDataSet,
					TableOrder.BY_ROW,
					true,
					false,
					false);
			Font font = new Font("????", Font.CENTER_BASELINE, 20);
			TextTitle title = new TextTitle(params[0], font);
			chart.setTitle(title);
			chart.setBackgroundPaint(java.awt.Color.white);
			MultiplePiePlot plot = (MultiplePiePlot) chart.getPlot();
			vFreeChartExtend = plot.getPieChart();
			PiePlot vPiePlot = (PiePlot) vFreeChartExtend.getPlot();
			vPiePlot.setLabelGenerator(
				new StandardPieItemLabelGenerator(
					"{0}={1},{2})",
					NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));
			vPiePlot.setForegroundAlpha(0.5f);
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
			System.out.println(filename);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} finally {
			pieDataset = null;
		}
		return filename;
	}

	//??????????????????????????
	public String generatePie3D2ChartTogether(
		HttpSession session,
		String[] params,
		int[] size,
		String Url) {
		String filename = null;
		try {
			PiePlot3D plot = new PiePlot3D(pieDataset);
			plot.setURLGenerator(new StandardPieURLGenerator(Url, "section"));
			//????????????
			JFreeChart chart =
				new JFreeChart(
					params[0],
					PiePlot.DEFAULT_LABEL_FONT,
					plot,
					true);
			//chart.getRenderingHints().put(
			//RenderingHints.KEY_TEXT_ANTIALIASING,
			//RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			Font font = new Font("????", Font.CENTER_BASELINE, 15);
			TextTitle title = new TextTitle(params[0], font);
			chart.setTitle(title);//????1
			Font font2 = new Font("????", Font.CENTER_BASELINE, 9);
			TextTitle title2 = new TextTitle(params[1], font2);
			chart.addSubtitle(title2);//????2
			chart.setBackgroundPaint(java.awt.Color.white);//????????
			plot.setToolTipGenerator(new StandardPieItemLabelGenerator());
			//MAP????????????????????
			plot.setLabelGenerator(
				null);//??????????????????????null????????????????????????????
			plot.setDepthFactor(0.16);
			plot.setCircular(false);//??????????????????
			//plot.setLabelOutlinePaint(java.awt.Color.white);//????????????????
			plot.setForegroundAlpha(0.6f);//????????????????????0.0??1.0??
			plot.setLegendLabelGenerator( new StandardPieItemLabelGenerator(
					"{0}",
			NumberFormat.getNumberInstance(),
			NumberFormat.getNumberInstance()));
			plot.setNoDataMessageFont(font);
			plot.setNoDataMessage(Message);
			StandardEntityCollection sec = new StandardEntityCollection();
			ChartRenderingInfo info = new ChartRenderingInfo(sec);
			PrintWriter w = new PrintWriter(System.out); //????MAP????
			filename =
				ServletUtilities.saveChartAsPNG(
					chart,
					size[0],
					size[1],
					null,
					session);
			ChartUtilities.writeImageMap(w, "map0", info, false);
		} catch (Exception e) {
			System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} finally {
			pieDataset = null;
		}
		return filename;

	}
	
	public static String createLineChart(String chartTitle, String x,
	        String y,CategoryDataset linedataset,HttpServletRequest request,BigDecimal range){
		String filename = "";
		JFreeChart chart = ChartFactory.createLineChart(chartTitle,// ????????????????????
			x, // ????
			y, // ????
			linedataset, // ??????????
			PlotOrientation.VERTICAL, // ????????????
			true, // ????????
			true, // ????????
			false // ????????URL????
			);
		//????????;
		CategoryPlot plot = chart.getCategoryPlot();
		//????????????????????????????????????
		TextTitle textTitle = new TextTitle(chartTitle);
		textTitle.setFont(new Font("????", Font.BOLD, 30));
		chart.setTitle(textTitle);
		chart.setBackgroundPaint(Color.white);
		plot.setBackgroundPaint(Color.white);//????????????
		plot.setNoDataMessage("????????");//??????????????????????????
		//????X??
		CategoryAxis domainAxis = plot.getDomainAxis();
		//domainAxis.setLabelFont(new Font("????", Font.PLAIN, 15)); // ????????????
		//domainAxis.setTickLabelFont(new Font("????", Font.PLAIN, 15));// ????????????????????
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// ???? lable ?????? ???????? Lable 45?????? DOWN_45
		//????Y??
		NumberAxis numAxis = (NumberAxis) plot.getRangeAxis();
		numAxis.setAutoRange(false);
		//numAxis.setFixedAutoRange(range.doubleValue());//Y??????
		//numAxis.setTickUnit(new NumberTickUnit(0.1));//????
		//?????????????????? 
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0,Color.blue);//??????????????
        plot.setRenderer(renderer);
        try {
	    	filename = ServletUtilities.saveChartAsPNG(chart, 400, 300, null, request.getSession());
	    } catch (Exception e) {
	    	System.out.println("Exception : " + e.toString());
			e.printStackTrace(System.out);
		} 

	    return filename;
	}
	
	
	
	public static void main(String[] args) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * @return
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		Message = string;
	}

}