/*
 * 创建日期 2012-2-8
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.fileupload;

/**
 * @author carlos
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class ParseFactory {

	public static UploadParse getParse(String suffix) {

		if (suffix == null)
			return null;

		if(suffix.equals("xls")){
			return new XlsParse();
		}
		
		throw new UnsupportedOperationException("unsupport parse formate");
		
	}

	public static void main(String[] args) {
	}
}