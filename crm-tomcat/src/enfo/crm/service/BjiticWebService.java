/*
 * �������� 2013-4-23
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.xml.soap.SOAPElement;

import enfo.crm.dao.CrmDBManager;
import enfo.crm.tools.Utility;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class BjiticWebService {
	/**
     * UM�ýӿڣ��ж��ͻ��Ƿ�Ϊ��������ֱ���ͻ�
     * @param custName �ͻ�����
     * @param custType �ͻ�����
     *            0    ����
     *            1    ����
     * @param cardType ֤�����ͣ������ţ�        
     *        ����֤�����ͣ�
     *            0    ���֤
     *            1    �й�����
     *            2    ����֤
     *            3    ʿ��֤
     *            4    ����֤
     *            5    ���ڱ�
     *            6    �⼮����
     *            7    ���� 
     *            8    ��ְ
     *            9    ����
     *            A    ̨��֤
     *        ����֤�����ͣ�
     *            0    �����ල�ִ���
     *            1    Ӫҵִ��
     *            2    ��������
     *            3    �������
     *            4    ����
     *            5    �侯
     *            6    ��������
     *            7    �����
     *            8    ��������
     * @param cardNo   ֤������
     * @return String��
     *              ��""|"0"|"1"(��""[��ʾ�����ڴ˿ͻ�]|���Ǳ�������ֱ���ͻ�|�Ǳ�������ֱ���ͻ�)
     */
    public String checkIsBjiticDSCust(Object custName, Object custType, Object cardType, Object cardNo) 
        throws Exception {
        String result = "";
        //String reason = "";
        
        String _custName = ((SOAPElement)custName).getFirstChild().getNodeValue(); //Utility.trimNull(custName);
        int _custType = Utility.parseInt(((SOAPElement)custType).getFirstChild().getNodeValue(), 1); // Ĭ�ϣ�����
        String _cardType = ((SOAPElement)cardType).getFirstChild().getNodeValue();
        String _cardNo = ((SOAPElement)cardNo).getFirstChild().getNodeValue(); //Utility.trimNull(cardNo);
        
        String listSql = "{call SP_ISORNOT_AGENTCUST_BJITIC(?,?,?,?)}";
        
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = CrmDBManager.getConnection();
            stmt = conn.prepareCall(listSql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, _custName);
            stmt.setInt(2, _custType);
            stmt.setString(3, _cardType);
            stmt.setString(4, _cardNo);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                result = rs.getString("RESULT");
                //reason = rs.getString("REASON");
            }
        } finally {
            if (rs!=null) rs.close();
            if (stmt!=null) stmt.close();            
            if (conn!=null) conn.close();
        }
    	return result; //new String[]{result, reason};
    }
}
