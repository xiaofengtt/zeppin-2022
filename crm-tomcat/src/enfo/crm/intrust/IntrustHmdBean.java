package enfo.crm.intrust;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.dao.IntrustBusiExBean;
import enfo.crm.vo.HmdVO;

@Component(value="intrustHmd")
public class IntrustHmdBean extends IntrustBusiExBean implements IntrustHmdLocal {

	 //  查询黑名单信息
    private final String queryBlackSql = "{call SP_AML_QUERY_TBLACKLIST(?,?,?,?,?,?,?,?,?,?,?,?)}";

    //增加黑名单信息
    private final String addBlackSql = "{?=call SP_ADD_TBLACKLIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    //修改黑名单信息
    private final String modiBlackSql = "{?=call SP_AML_MODI_TBLACKLIST(?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    //删除黑名单信息
    private final String removeBlackSql = "{?=call SP_AML_DEL_TBLACKLIST(?,?)}";

    //删除化名
    private final String removeTalisSql = "{?=call SP_AML_DEL_TALIAS(?,?)}";

    //增加别名信息
    private final String addTaliasSql = "{?=call SP_ADD_TALIAS(?,?,?,?,?,?,?,?,?)}";

    //查询别名信息
    private final String query_bmSql = "{call SP_AML_QUERY_TALIAS(?)}";

    //查询匹配的黑名单或别名信息
    private final String queryAllBlackSql = "{call SP_AML_QUERY_TBLACKLIST_ALL(?,?,?,?,?,?,?)}";

    //检查与黑名单匹配的客户名单
    private final String checkCustSql = "{call SP_AML_QUERY_TBLACKLIST_CHECK(?,?,?,?)}";

    //查询制裁名单文档
    private final String queryBlackFileSql = "{call SP_AML_QUERY_TBLACKLIST_FILE(?,?,?,?)}";

    //增加制裁名单文档
    private final String addBlackFileSql = "{?=call SP_AML_ADD_TBLACKLIST_FILE(?,?,?,?,?)}";

    //删除制裁名单信息
    private final String removeBlackFileSql = "{?=call SP_AML_DEL_TBLACKLIST_FILE(?,?)}";

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#queryBlack(enfo.crm.vo.HmdVO, int, int)
	 */
    @Override
	public IPageList queryBlack(HmdVO vo, int pageIndex, int pageSize)
            throws Exception {
        IPageList list = null;
        Object[] params = new Object[12];
        params[0] = vo.getSerial_no();
        params[1] = vo.getFull_name_c();
        params[2] = vo.getFor_short_c();
        params[3] = vo.getFull_name_e();
        params[4] = vo.getFor_short_e();
        params[5] = vo.getOther_lang_name();
        params[6] = vo.getClassification_no();
        params[7] = vo.getCategory_no();
        params[8] = vo.getCard_type();
        params[9] = vo.getCard_no();
        params[10] = vo.getCountry();
        params[11] = vo.getExplanation();

        try {
            list = super.listProcPage(queryBlackSql, params, pageIndex,
                    pageSize);
        } catch (Exception e) {
            throw new BusiException("查询失败:" + e.getMessage());
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#query_hmd(enfo.crm.vo.HmdVO)
	 */
    @Override
	public List query_hmd(HmdVO vo) throws BusiException {
        List list = new ArrayList();

        Object[] params = new Object[12];
        params[0] = vo.getSerial_no();
        params[1] = vo.getFull_name_c();
        params[2] = vo.getFor_short_c();
        params[3] = vo.getFull_name_e();
        params[4] = vo.getFor_short_e();
        params[5] = vo.getOther_lang_name();
        params[6] = vo.getClassification_no();
        params[7] = vo.getCategory_no();
        params[8] = vo.getCard_type();
        params[9] = vo.getCard_no();
        params[10] = vo.getCountry();
        params[11] = vo.getExplanation();
        try {
            list = super.listBySql(queryBlackSql, params);
        } catch (Exception e) {
            throw new BusiException("查询失败: " + e.getMessage());
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#query_bm(enfo.crm.vo.HmdVO)
	 */
    @Override
	public List query_bm(HmdVO vo) throws BusiException {
        List list = new ArrayList();

        Object[] params = new Object[1];
        params[0] = vo.getBirth_name_id();

        try {
            list = super.listBySql(query_bmSql, params);
        } catch (Exception e) {
            throw new BusiException("查询失败: " + e.getMessage());
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#addBlack(enfo.crm.vo.HmdVO)
	 */
    @Override
	public Integer addBlack(HmdVO vo) throws BusiException {

        Object[] params = new Object[12];

        params[0] = vo.getFull_name_c();
        params[1] = vo.getFor_short_c();
        params[2] = vo.getFull_name_e();
        params[3] = vo.getFor_short_e();
        params[4] = vo.getOther_lang_name();
        params[5] = vo.getClassification_no();
        params[6] = vo.getCategory_no();
        params[7] = vo.getCard_type();
        params[8] = vo.getCard_no();
        params[9] = vo.getCountry();
        params[10] = vo.getExplanation();
        params[11] = vo.getInput_man();
        return (Integer) super.cudProc(addBlackSql, params, 14,
                java.sql.Types.INTEGER);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#modiBlack(enfo.crm.vo.HmdVO)
	 */

    @Override
	public void modiBlack(HmdVO vo) throws BusiException {

        Object[] params = new Object[13];

        params[0] = vo.getSerial_no();
        params[1] = vo.getFull_name_c();
        params[2] = vo.getFor_short_c();
        params[3] = vo.getFull_name_e();
        params[4] = vo.getFor_short_e();
        params[5] = vo.getOther_lang_name();
        params[6] = vo.getClassification_no();
        params[7] = vo.getCategory_no();
        params[8] = vo.getCard_type();
        params[9] = vo.getCard_no();
        params[10] = vo.getCountry();
        params[11] = vo.getExplanation();
        params[12] = vo.getInput_man();

        try {
            super.cudProc(modiBlackSql, params);
        } catch (BusiException e) {
            throw new BusiException("修改失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#addTalias(enfo.crm.vo.HmdVO)
	 */

    @Override
	public void addTalias(HmdVO vo) throws BusiException {

        Object[] params = new Object[9];

        params[0] = vo.getFull_name_c();
        params[1] = vo.getFor_short_c();
        params[2] = vo.getFull_name_e();
        params[3] = vo.getFor_short_e();
        params[4] = vo.getOther_lang_name();
        params[5] = vo.getBirth_name_id();
        params[6] = vo.getClassification_no();
        params[7] = vo.getCategory_no();
        params[8] = vo.getInput_man();

        try {
            super.cudProc(addTaliasSql, params);
        } catch (BusiException e) {
            throw new BusiException("修改失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#removeBlack(enfo.crm.vo.HmdVO)
	 */

    @Override
	public void removeBlack(HmdVO vo) throws BusiException {

        Object[] params = new Object[2];

        params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();

        try {
            super.cudProc(removeBlackSql, params);
        } catch (BusiException e) {
            throw new BusiException("删除失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#removeTalis(enfo.crm.vo.HmdVO)
	 */
    @Override
	public void removeTalis(HmdVO vo) throws BusiException {
        Object[] params = new Object[2];

        params[0] = vo.getBirth_name_id();
        params[1] = vo.getInput_man();

        try {
            super.cudProc(removeTalisSql, params);
        } catch (BusiException e) {
            throw new BusiException("修改失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#queryAll(enfo.crm.vo.HmdVO)
	 */
    @Override
	public List queryAll(HmdVO vo) throws BusiException {
        List list = new ArrayList();

        Object[] params = new Object[7];
        params[0] = vo.getFull_name_c();
        params[1] = vo.getFull_name_e();
        params[2] = vo.getOther_lang_name();
        params[3] = vo.getCategory_no();
        params[4] = vo.getCard_type();
        params[5] = vo.getCard_no();
        params[6] = vo.getCountry();
        try {
            list = super.listBySql(queryAllBlackSql, params);
        } catch (Exception e) {
            throw new BusiException("查询失败: " + e.getMessage());
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#checkCustList(java.lang.Object[], int, int)
	 */
    @Override
	public IPageList checkCustList(Object params[], int pageIndex, int pageSize)
            throws Exception {
        IPageList list = null;
        try {
            list = super
                    .listProcPage(checkCustSql, params, pageIndex, pageSize);
        } catch (Exception e) {
            throw new BusiException("查询失败:" + e.getMessage());
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#queryBlackFile(enfo.crm.vo.HmdVO, int, int)
	 */
    @Override
	public IPageList queryBlackFile(HmdVO vo, int pageIndex, int pageSize)
            throws Exception {
        IPageList list = null;
        Object[] params = new Object[4];
        params[0] = vo.getSerial_no();
        params[1] = vo.getFile_name();
        params[2] = vo.getClassification_no();
        params[3] = vo.getFile_date();

        try {
            list = super.listProcPage(queryBlackFileSql, params, pageIndex,
                    pageSize);
        } catch (Exception e) {
            throw new BusiException("查询失败:" + e.getMessage());
        }
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#addBlackListFile(enfo.crm.vo.HmdVO)
	 */
    @Override
	public Integer addBlackListFile(HmdVO vo) throws BusiException {

        Object[] params = new Object[4];

        params[0] = vo.getSerial_no();
        params[1] = vo.getFile_name();
        params[2] = vo.getClassification_no();
        params[3] = vo.getInput_man();
        return (Integer) super.cudProc(addBlackFileSql, params, 6,
                java.sql.Types.INTEGER);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustHmdLocal#removeBlackFile(enfo.crm.vo.HmdVO)
	 */

    @Override
	public void removeBlackFile(HmdVO vo) throws BusiException {

        Object[] params = new Object[2];

        params[0] = vo.getSerial_no();
        params[1] = vo.getInput_man();

        try {
            super.cudProc(removeBlackFileSql, params);
        } catch (BusiException e) {
            throw new BusiException("删除失败:" + e.getMessage());
        }
    }
}