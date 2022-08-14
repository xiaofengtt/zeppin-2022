package enfo.crm.util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.WorkbookSettings;
import enfo.crm.tools.Format;
import enfo.crm.tools.Utility;
import sun.jdbc.rowset.CachedRowSet;

/**
 * <pre>
 *  Title:Excel工具类
 *  Description:Excel相关操作工具类，包括Excel文件的读和写
 *              字段输出类型：-1:空（即不从结果集中取数据，对应字段为空） 
 *                           1：String
 *                           2：BigDecimal(保留2位小数) 
 *                           3: BigDecimal(保留5位小数) 
 *                           4: Integer
 *                           5: Integer to String(yyyy-MM-dd)
 *                           6：timestamp to String(yyyy-MM-dd HH:mm:ss)
 * 
 * </pre>
 * 
 * @author <a href="mailto:littcai@hotmail.com">空心大白菜</a>
 * @date 2007-10-11
 * @version 1.0
 */
public class ExcelUtil {
	
	private String strFolder = "C:\\Temp";		//excel文件保存的路径


	/**
	 * 使用自定义结果集作为获取结果集途径
	 * 
	 * @param excelName excel文件名称
	 * @param sheetName 表页名称
	 * @param titleName 字段标题
	 * @param fieldName 数据库字段名称
	 * @param fieldType 字段取值、解析类型
	 * @param rslist 结果集
	 * @throws Exception
	 */
	public File writeExcel(
		String excelName,
		String sheetName,
		String[] titleName,
		String[] fieldName,
		int[] fieldType,
		CachedRowSet rowset)
		throws Exception {
		java.io.File file = null;		
			file = new java.io.File(strFolder + Constants.SEPARATOR + excelName);
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setGCDisabled(true);
			WritableWorkbook wwb = Workbook.createWorkbook(file,wbs);
			WritableSheet ws = wwb.createSheet(sheetName, 0);				
			//写中文标题	
			int i = 0;
			for (; i < titleName.length; i++) {
				jxl.write.Label labelC1 =
					new jxl.write.Label(i, 0, titleName[i]);
				ws.addCell(labelC1);
			}
			//写结果集
			int j = 1;
			int k = 0;			
			while (rowset.next()) {				
				k = 0;
				for (; k < fieldType.length; k++) {					
					switch (fieldType[k]) {
						case -1:	//空
							{
								jxl.write.Label labelValue0 = new jxl.write.Label(k, j, "");
								ws.addCell(labelValue0);
								break;
							}
						case 1 :	//字符串
							{								
								String fieldValue =
									Utility.trimNull(rowset.getString(fieldName[k]));

								jxl.write.Label labelValue0 =
									new jxl.write.Label(k, j, fieldValue);
								ws.addCell(labelValue0);
								break;
							}
						case 2 : //BigDecimal数值类型，保留2位小数
							{
								BigDecimal fiedvalue = rowset.getBigDecimal(fieldName[k]);

								double tempmoney =
									(fiedvalue == null
										? 0
										: fiedvalue.doubleValue());
								jxl.write.NumberFormat nf =
									new jxl.write.NumberFormat("0.00");
								jxl.write.WritableCellFormat wcfN =
									new jxl.write.WritableCellFormat(nf);
								jxl.write.Number labe4NF =
									new jxl.write.Number(k, j, tempmoney, wcfN);
								ws.addCell(labe4NF);
								break;
							}
						case 3 : //BigDecimal数值类型，保留2位小数
							{
								BigDecimal fiedvalue = rowset.getBigDecimal(fieldName[k]);

								double tempmoney =
									(fiedvalue == null
										? 0
										: fiedvalue.doubleValue());
								jxl.write.NumberFormat nf =
									new jxl.write.NumberFormat("0.00000");
								jxl.write.WritableCellFormat wcfN =
									new jxl.write.WritableCellFormat(nf);
								jxl.write.Number labe4NF =
									new jxl.write.Number(k, j, tempmoney, wcfN);
								ws.addCell(labe4NF);
								break;
						}
						case 4 :	//Integer
							{
								Integer fieldValue = new Integer(rowset.getInt(fieldName[k]));
								String finalValue = "";
								if (fieldValue != null)
									finalValue = fieldValue.toString();

								jxl.write.Label labelValue0 = 
									new jxl.write.Label(k,j,finalValue);

								ws.addCell(labelValue0);
								break;
							}
						case 5 : //Integer to Date(yyyy-mm-dd)
						{
							Integer fieldValue = new Integer(rowset.getInt(fieldName[k]));
							String finalValue = "";
							if (fieldValue != null)
							{
								finalValue = Format.formatDateLine(fieldValue);
							} 

							jxl.write.Label labelValue0 = 
								new jxl.write.Label(k,j,finalValue);

							ws.addCell(labelValue0);
							break;
						}
						case 6 : //timesamp数值类型的
						{
							Timestamp fieldValue = rowset.getTimestamp(fieldName[k]);
							String finalValue = Format.formatDatetimeCn(fieldValue);
							jxl.write.Label labelValue0 =
								new jxl.write.Label(k,j,finalValue);

							ws.addCell(labelValue0);
							break;
						}	
						default :
							break;
					}
				}
				j++;
			}
			wwb.write();
			wwb.close();						
		 
		
		return file;
	}

	public static void main(String[] args) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
