package com.zeppin.util.sms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WriteExcel {

	public void getExcel(List<String> list, OutputStream output) {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("sheet1");
		HSSFRow row = sheet1.createRow((short) 0);
		row.createCell((short) 0).setCellValue("ids");
		row.createCell((short) 1).setCellValue("用户名");
		row.createCell((short) 2).setCellValue("手机号码");
		row.createCell((short) 3).setCellValue("所属项目");
		row.createCell((short) 4).setCellValue("培训单位");
		row.createCell((short) 5).setCellValue("科目");
		row.createCell((short) 6).setCellValue("短信状态");
		row.createCell((short) 7).setCellValue("问卷状态");
		row.createCell((short) 8).setCellValue("结业状态");

		for (int i = 1; i <= list.size(); i++) {

			row = sheet1.createRow((short) i);

			String[] values = list.get(i - 1).split(",");

			row.createCell((short) 0).setCellValue(values[0]);
			row.createCell((short) 1).setCellValue(values[1]);
			row.createCell((short) 2).setCellValue(values[2]);
			row.createCell((short) 3).setCellValue(values[3]);
			row.createCell((short) 4).setCellValue(values[4]);
			row.createCell((short) 5).setCellValue(values[5]);
			row.createCell((short) 6).setCellValue(values[6]);
			row.createCell((short) 7).setCellValue(values[7]);
			row.createCell((short) 8).setCellValue(values[8]);

		}

		try {
			output.flush();
			wb.write(output);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output   is   closed ");
		}
	}
}