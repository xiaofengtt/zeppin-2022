/*
 * 创建日期 2013-4-23
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
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
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class BjiticWebService {
	/**
     * UM用接口：判定客户是否为北京信托直销客户
     * @param custName 客户名称
     * @param custType 客户类型
     *            0    机构
     *            1    个人
     * @param cardType 证件类型（传入编号）        
     *        个人证件类型：
     *            0    身份证
     *            1    中国护照
     *            2    军官证
     *            3    士兵证
     *            4    回乡证
     *            5    户口本
     *            6    外籍护照
     *            7    其他 
     *            8    文职
     *            9    警官
     *            A    台胞证
     *        机构证件类型：
     *            0    技术监督局代码
     *            1    营业执照
     *            2    行政机关
     *            3    社会团体
     *            4    军队
     *            5    武警
     *            6    下属机构
     *            7    基金会
     *            8    其他机构
     * @param cardNo   证件号码
     * @return String：
     *              空""|"0"|"1"(空""[表示不存在此客户]|不是北京信托直销客户|是北京信托直销客户)
     */
    public String checkIsBjiticDSCust(Object custName, Object custType, Object cardType, Object cardNo) 
        throws Exception {
        String result = "";
        //String reason = "";
        
        String _custName = ((SOAPElement)custName).getFirstChild().getNodeValue(); //Utility.trimNull(custName);
        int _custType = Utility.parseInt(((SOAPElement)custType).getFirstChild().getNodeValue(), 1); // 默认：个人
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
