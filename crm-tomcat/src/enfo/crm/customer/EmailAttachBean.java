 
package enfo.crm.customer;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.EmailAttachVO;

@Component(value="emailAttach")
public class EmailAttachBean extends enfo.crm.dao.CrmBusiExBean implements EmailAttachLocal {

	/**
	 * 添加邮件附件
	 */
	private static final String appendSql =
		"{?=call SP_ADD_TEMAILATTACH(?,?,?,?)}";
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.EmailAttachLocal#appendAttach(enfo.crm.vo.EmailAttachVO)
	 */
	@Override
	public Integer appendAttach(EmailAttachVO vo) throws BusiException {
		Object[] oparams = new Object[3];
		oparams[0] = Utility.parseLong(vo.getMail_serial_no(), new Long(0));
		oparams[1] = vo.getAttach_name();
		oparams[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		Integer temp_serial_no = new Integer(0);

		temp_serial_no =
			(Integer) super.cudProc(
				appendSql,
				oparams,
				5,
				java.sql.Types.INTEGER);
		return temp_serial_no;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.customer.EmailAttachLocal#updateToDB(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateToDB(
		Long serial_no2,
		String attach_name2,
		String strFolder)
		throws Exception {
		InputStream inputStream = null;
		if (serial_no2 == null)
			return false;

		Connection conn = IntrustDBManager.getConnection();
		try {
			PreparedStatement stmt =
				conn.prepareStatement(
					"UPDATE TEMAILATTACH SET ATTACH_CONTENT=? WHERE SERIAL_NO="
						+ serial_no2);

			File file =
				new File(strFolder + java.io.File.separator + attach_name2);
			inputStream = new FileInputStream(file);
			stmt.setBinaryStream(1, inputStream, (int) (file.length()));
			stmt.executeUpdate();
			inputStream.close();
			file.delete();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusiException("附件保存数据库失败: " + e.getMessage());
		} finally {
			conn.close();
		}
	}
}