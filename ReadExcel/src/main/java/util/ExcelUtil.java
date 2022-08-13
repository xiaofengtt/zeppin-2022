package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private static final int rowStart = 2; // based 0

	private static final DecimalFormat df = new DecimalFormat("###########");// 最多保留几位小数，就用几个#，最少位就用0来确定

	public static void readExcel(String filePath) throws Exception {
		// Use a file
		Workbook wb = WorkbookFactory.create(new File(filePath));
		Sheet sheet = wb.getSheetAt(0);

		for (Row row : sheet) {
			for (Cell cell : row) {
				// Do something here
			}
		}

		// Decide which rows to process
		int rowStart = sheet.getFirstRowNum();
		int rowEnd = sheet.getLastRowNum();

		for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			Row r = sheet.getRow(rowNum);

			int lastColumn = r.getLastCellNum();

			for (int cn = 0; cn < lastColumn; cn++) {
				Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
				if (c == null) {
					// The spreadsheet is empty in this cell
				} else {
					// Do something useful with the cell's contents
				}
			}
		}
	}

	public static void read(String filePath) throws Exception {
		Workbook wb = WorkbookFactory.create(new File(filePath));
		Sheet sheet1 = wb.getSheetAt(0);
		int i = 0;
		for (Row row : sheet1) {
			i++;
			System.out.print(i + "  ");

			for (Cell cell : row) {

				System.out.print(" - ");
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					printf(replaceBlank(cell.getRichStringCellValue().getString()));
					break;
				case Cell.CELL_TYPE_NUMERIC: // 数值型
					if (DateUtil.isCellDateFormatted(cell)) {
						printf(cell.getDateCellValue().toString());
					} else {
						printf((int) cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN: // 布尔值
					printf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA: // 公式
					printf(cell.getCellFormula() + "");
					break;
				case Cell.CELL_TYPE_BLANK: // 空格

				case Cell.CELL_TYPE_ERROR: // 错误
				default:
					printf("null");
				}
			}

			System.out.println("");
		}
	}

	public static void text(String filePath) throws Exception {
		InputStream inp = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
		ExcelExtractor extractor = new ExcelExtractor(wb);

		extractor.setFormulasNotResults(true);
		extractor.setIncludeSheetNames(false);
		String text = extractor.getText();
		System.out.println(text);
	}

	/**
	 * 获取指定行某列的字符串值
	 * 
	 * @param index
	 *            (based 0)
	 * @param row
	 * @return 返回制定单元格的字符串值
	 */
	public static String GetCellValue(int index, Row row) {
		return GetCellValue(row.getCell(index, Row.RETURN_BLANK_AS_NULL));
	}

	public static String GetCellValue(Cell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = replaceBlank(cell.getRichStringCellValue().getString());
			break;
		case Cell.CELL_TYPE_NUMERIC: // 数值型
			if (DateUtil.isCellDateFormatted(cell)) {
				value = cell.getDateCellValue().toString();
			} else {
				value = df.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN: // 布尔值
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			value = cell.getCellFormula();
			break;
		case Cell.CELL_TYPE_BLANK: // 空格

		case Cell.CELL_TYPE_ERROR: // 错误
		default:
			value = "";
		}
		return value;
	}

	public static void printf(Object object) {
		System.out.printf("%-30s", object);
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static Date stringToDate(String sDate) {
		DateFormat format1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format3 = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat format4 = new SimpleDateFormat("yyyy—MM—dd");
		Date date = null;
		try {
			date = format1.parse(sDate);
		} catch (Exception e) {
			try {
				date = format2.parse(sDate);
			} catch (Exception e2) {
				try {
					date = format3.parse(sDate);
				} catch (Exception e3) {
					try {
						date = format4.parse(sDate);
					} catch (ParseException e1) {

					}
				}
			}
		}
		return date;
	}
}
