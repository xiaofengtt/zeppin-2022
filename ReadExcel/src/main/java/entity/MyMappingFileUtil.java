package entity;

import java.io.File;

public class MyMappingFileUtil {

	public static void main(String[] args) throws Exception {
		File directory = new File("E:\\projects\\ReadExcel\\src\\main\\java\\entity");// 设定为当前文件夹

		File[] files = directory.listFiles();
		for (File file : files) {
			//System.out.println(file.getName());
			
			System.out.printf("<mapping class=\"entity.%s\" />\n",file.getName().split("\\.")[0]);
			
		}

	}

}
