 
package enfo.crm.system;
import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.MessageVO;

@Component(value="message")
public class MessageBean extends enfo.crm.dao.CrmBusiExBean implements MessageLocal  {

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#listPrivateMessage(enfo.crm.vo.MessageVO, int, int)
	 */
	@Override
	public IPageList listPrivateMessage(
		MessageVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[4];	
		params[0] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[1] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[2] = Utility.trimNull(vo.getTitle());
		params[3] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		rsList =
			super.listProcPage(
				"{call SP_QUERY_TTASKINFO(?,?,?,?)}",
				params,
				pageIndex,
				pageSize);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#listPrivateMessage(enfo.crm.vo.MessageVO)
	 */
	@Override
	public List listPrivateMessage(MessageVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[4];	
		params[0] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		params[1] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[2] = Utility.trimNull(vo.getTitle());
		params[3] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		rsList =
			super.listBySql("{call SP_QUERY_TTASKINFO(?,?,?,?)}", params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#listReaders(java.lang.Integer)
	 */
	@Override
	public List listReaders(Integer rec_no) throws BusiException {
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(rec_no, new Integer(0));
		rsList = super.listBySql("{call SP_QUERY_TTASKLIST(?)}", params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#listReply(java.lang.Integer)
	 */
	@Override
	public List listReply(Integer rec_no) throws BusiException {
		List rsList = null;
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(rec_no, new Integer(0));
		rsList = super.listBySql("{call SP_QUERY_TTASKREPLY(?)}", params);
		return rsList;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#appendReply(java.lang.Integer, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void appendReply(
		Integer rec_no,
		String reply_str,
		Integer input_man)
		throws Exception {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(rec_no, new Integer(0));
		params[1] = reply_str;
		params[2] = Utility.parseInt(input_man, new Integer(0));
		super.cudProc("{?=call SP_ADD_TTASKREPLY(?,?,?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#appendMessage(enfo.crm.vo.MessageVO)
	 */
	@Override
	public Integer appendMessage(MessageVO vo) throws Exception {
		Object[] params = new Object[4];
		params[0] = Utility.trimNull(vo.getTitle());
		params[1] = Utility.trimNull(vo.getInfo_STR());
		params[2] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		params[3] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		return (Integer) super.cudProc(
			"{?=call SP_ADD_TTASKINFO(?,?,?,?,?)}",
			params,
			6,
			Types.INTEGER);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#modiMessage(enfo.crm.vo.MessageVO)
	 */
	@Override
	public void modiMessage(MessageVO vo) throws Exception {
		Object[] params = new Object[5];
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getTitle());
		params[2] = Utility.trimNull(vo.getInfo_STR());
		params[3] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		params[4] = Utility.parseInt(vo.getInput_man(), new Integer(0));
		super.cudProc("{?=call SP_MODI_TTASKINFO_COMM(?,?,?,?,?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#delReaders(java.lang.Integer)
	 */
	@Override
	public void delReaders(Integer rec_no) throws Exception {
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(rec_no, new Integer(0));
		super.cudProc("{?=call SP_DEL_TTASKLIST_ALL(?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#appendReaders(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void appendReaders(
		Integer rec_no,
		Integer op_code,
		Integer input_man)
		throws Exception {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(rec_no, new Integer(0));
		params[1] = Utility.parseInt(op_code, new Integer(0));
		params[2] = Utility.parseInt(input_man, new Integer(0));
		super.cudProc("{?=call SP_ADD_TTASKLIST(?,?,?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#delMessage(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void delMessage(Integer serial_no, Integer input_man, Integer flag)
		throws Exception {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(serial_no, new Integer(0));
		params[1] = Utility.parseInt(input_man, new Integer(0));
		params[2] = Utility.parseInt(flag, new Integer(0));
		super.cudProc("{?=call SP_DEL_TTASKINFO(?,?,?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#listSystemMessage(enfo.crm.vo.MessageVO)
	 */
	@Override
	public List listSystemMessage(MessageVO vo) throws BusiException {
		List rsList = null;
		Object[] params = new Object[6];
		params[0] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getTast_type(), new Integer(0));
		params[2] = Utility.trimNull(vo.getBusi_id());
		params[3] = Utility.trimNull(vo.getTitle());
		params[4] = Utility.parseInt(vo.getRead_flag(), new Integer(0));
		params[5] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		rsList =
			super.listBySql(
				"{call SP_QUERY_TTASKINFO_COMM(?,?,?,?,?,?)}",
				params);
		return rsList;
		
	}

	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#modiMessageReadFlag(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void modiMessageReadFlag(
		Integer serial_no,
		Integer read_flag,
		Integer input_man)
		throws Exception {
		Object[] params = new Object[3];
		params[0] = Utility.parseInt(serial_no, new Integer(0));
		params[1] = Utility.parseInt(read_flag, new Integer(1));
		params[2] = Utility.parseInt(input_man, new Integer(0));
		super.cudProc("{?=call SP_MODI_TTASKINFO(?,?,?)}", params);

	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#listPrivateMessage1(enfo.crm.vo.MessageVO, int, int)
	 */
	@Override
	public IPageList listPrivateMessage1(
		MessageVO vo,
		int pageIndex,
		int pageSize)
		throws BusiException {
		IPageList rsList = null;
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[2] = Utility.trimNull(vo.getTitle());
		params[3] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		rsList =
			super.listProcPage(
				"{call SP_QUERY_TTASKINFO_1(?,?,?,?)}",
				params,
				pageIndex,
				pageSize);
		return rsList;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.system.MessageLocal#listPrivateMessage1(enfo.crm.vo.MessageVO)
	 */
	@Override
	public List listPrivateMessage1(
		MessageVO vo)
		throws BusiException {
		List rsList = null;
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(vo.getBook_code(), new Integer(0));
		params[1] = Utility.parseInt(vo.getOp_code(), new Integer(0));
		params[2] = Utility.trimNull(vo.getTitle());
		params[3] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		rsList =
			super.listBySql(
				"{call SP_QUERY_TTASKINFO_1(?,?,?,?)}",
				params);
		return rsList;
	}
}