/*
 * �������� 2012-2-7
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.fileupload;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.apache.commons.fileupload.FileItem;

import enfo.crm.customer.CustomerChangesLocal;
import enfo.crm.dao.BusiException;
import enfo.crm.tools.EJBFactory;

/**
 * @author carlos
 * 
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class XlsParse implements UploadParse {

	/*
	 * ���� Javadoc��
	 * 
	 * @see enfo.crm.fileupload.UploadParse#parse(org.apache.commons.fileupload.FileItem)
	 */
	List header = null;

	List content = null;

	public XlsParse() {
		header = new ArrayList();
		content = new ArrayList();
	}

	public void parse(FileItem item,Integer operator) throws Exception {
		Workbook workbook = null;
		try {
			WorkbookSettings settings = new WorkbookSettings();
			settings.setGCDisabled(true);
			workbook = Workbook.getWorkbook(item.getInputStream(), settings);
			for (int i = 0; i != workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheet(i);
				//get first line
				if (sheet.getRows() != 0) {
					for (int col = 0; col != sheet.getColumns(); col++) {
						String content = sheet.getCell(col, 0).getContents()
								.trim();
						if (content != "") {
							Object key = DestinationTableVo.fieldMapping
									.get(content);
							if (key == null)
								throw new BusiException("�����޷�ƥ����ֶ�:"
										+ content
										+ ".���õ��ֶ�����Ϊ:"
										+ DestinationTableVo.fieldMapping
												.keySet().toString());

							header.add(col, key);
						}else{
							throw new BusiException("�����ļ��е�" + (col+1) + "��Ϊ�գ���ɾ�������ٵ�������");
						}
					}
					//read content
					for (int row = 1; row < sheet.getRows(); row++) {
						DestinationTableVo line = new DestinationTableVo();
						for (int col = 0; col != sheet.getColumns(); col++) {
							Helper.invoke(line, (String) header.get(col),
									new String[] { sheet.getCell(col, row)
											.getContents().trim() });
						}
						content.add(line);
						if (content.size() > 1000) {
							//send to DB
							sendToDB(content, operator);
							content.clear();
						}
					}
					sendToDB(content, operator);
					content.clear();
				}
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			workbook.close();
		}

	}

	private void sendToDB(List vos, Integer operator) throws Exception {

		CustomerChangesLocal local = EJBFactory.getCustomerChanges();
		String[] sqls = new String[vos.size()];

		for (int i = 0; i != vos.size(); i++) {
			StringBuffer sb = new StringBuffer();
			DestinationTableVo line = (DestinationTableVo) vos.get(i);
			sb.append("INSERT INTO IMPORT_DATA(��Ŀ����, ��ͬ���, �ͻ�����, ֤������, ֤������, �ֻ�, �̶��绰, ����, ��ϵ��ַ, �ʱ�, �ͻ�����, EMAIL, MODULE_ID, INSERT_MAN) VALUES(");
			sb.append("'" + line.getProject_name() + "', ");
			sb.append("'" + line.getContract_bh() + "', ");
			sb.append("'" + line.getCustomer_name() + "',");
			sb.append("'" + line.getCertificate_type() + "', ");
			sb.append("'" + line.getCertificate_no() + "', ");
			sb.append("'" + line.getMobile() + "',");
			sb.append("'" + line.getTelephone() + "', ");
			sb.append("'" + line.getFax() + "', ");
			sb.append("'" + line.getAddress() + "', ");
			sb.append("'" + line.getPost_code() + "', ");
			sb.append("'" + line.getCustomer_mananger() + "', ");
			sb.append("'" + line.getEmail() + "', ");
			sb.append("'20199', ");
			sb.append(operator);
			sb.append(");");
			sqls[i] = sb.toString();
		}

//		for (int i = 0; i != sqls.length; i++) {
//			System.out.println(sqls[i]);
//		}
		local.importCustomerInfo(sqls);
		local.remove();
	}

	private static class Helper {

		public static Object invoke(Object obj, String methodName, Object[] args)
				throws Exception {

			Class objClass = obj.getClass();
			Class[] argsClass = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argsClass[i] = args[i].getClass();
			}
			Method method = objClass.getMethod(methodName, argsClass);
			method.invoke(obj, args);
			return obj;
		}
	}

}