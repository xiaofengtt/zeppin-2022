package enfo.crm.contractManage;

import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.VideoRecordingVO;
import java.util.List;

public class VideoRecordingBean
{
  public List getVideoRecordingListByContractId(Integer contractId, Integer opCode)
  {
    String listSqlCrm = "{call SP2_QUERY_TVideoRecording_CONTRACTID(?,?)}";
    Object[] params = new Object[2];
    params[0] = contractId;
    params[1] = opCode;
    try {
      return CrmDBManager.listProcAll(listSqlCrm, params);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public IPageList getVideoRecordingList(VideoRecordingVO vo, int pageIndex, int pageSize) {
    String listSqlCrm = "{call SP2_QUERY_TVideoRecording(?,?,?,?,?,?)}";
    Object[] params = new Object[6];
    params[0] = vo.getContractID();
    params[1] = vo.getContract_BH();
    params[2] = vo.getProductName();
    params[3] = vo.getCustName();
    params[4] = vo.getCheckFlag();
    params[5] = vo.getInputMan();
    try {
      return CrmDBManager.listProcPage(listSqlCrm, params, pageIndex, pageSize);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public String checkVideoRecording(Integer vId, Integer checkFlag, String comment, Integer opCode) {
    String sqlCrm = "{?=call SP2_CHECK_TVideoRecording(?,?,?,?)}";
    Object[] params = new Object[4];
    params[0] = vId;
    params[1] = checkFlag;
    params[2] = comment;
    params[3] = opCode;
    try {
      CrmDBManager.execProc(sqlCrm, params);
      return "";
    } catch (Exception e) {
      e.printStackTrace();
      return e.getMessage();
    }
  }
}