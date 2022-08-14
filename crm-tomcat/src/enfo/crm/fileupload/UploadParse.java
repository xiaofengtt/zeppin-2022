/*
 * 创建日期 2012-2-7
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.fileupload;

import org.apache.commons.fileupload.FileItem;

/**
 * @author carlos
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public interface UploadParse {
	public void parse(FileItem item, Integer operator) throws Exception;
}
