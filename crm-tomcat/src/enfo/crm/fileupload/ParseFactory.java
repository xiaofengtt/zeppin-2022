/*
 * �������� 2012-2-8
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.fileupload;

/**
 * @author carlos
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
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