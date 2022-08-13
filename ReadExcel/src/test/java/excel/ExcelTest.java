package excel;

import org.junit.Test;

import util.ExcelUtil;

public class ExcelTest {

	private String filePath = "D:\\双语学员导入信息反馈表（0225）.xlsx";

	@Test
	public void test() {
		try {
			ExcelUtil.read(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		try {
			ExcelUtil.text(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test3() {

	}

}
