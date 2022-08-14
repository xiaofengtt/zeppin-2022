 
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;
import enfo.crm.vo.GainLevelRateVO;

@Component(value="gainLevelRate")
public class GainLevelRateBean extends enfo.crm.dao.IntrustBusiExBean implements GainLevelRateLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3368415541705841042L;

	private static final String addRateSql = "{?=CALL SP_ADD_TGAINLEVELRATE(?,?,?,?,?,?)}" ;//新增收益率信息
	
	private static final String updateRateSql = "{?=CALL SP_MODI_TGAINLEVELRATE(?,?,?,?,?,?)}" ;//修改收益率信息
	
	private static final String delRateSql = "{?=CALL SP_DEL_TGAINLEVELRATE(?,?)}" ;//删除收益率信息
	
	private static final String queryRateSql = "{CALL SP_QUERY_TGAINLEVELRATE(?,?)}" ;//查询收益率信息
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelRateLocal#add(enfo.crm.vo.GainLevelRateVO)
	 */
	@Override
	public void add(GainLevelRateVO vo)throws Exception{
		Object[] param = new Object[6];
		param[0] = Utility.parseInt(vo.getDf_serial_no() , new Integer(0)) ;
		param[1] = Utility.parseBigDecimal(vo.getGain_rate(), new BigDecimal(0));
		param[2] = Utility.parseBigDecimal(vo.getFloat_rate() , new BigDecimal(0));
		param[3] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		param[4] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		param[5] = Utility.parseInt(vo.getInput_man() , new Integer(0));
		try{
			super.cud(addRateSql, param);
		}catch(Exception e){
			throw new BusiException("新增收益率失败:"+ e.getMessage()) ;
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelRateLocal#update(enfo.crm.vo.GainLevelRateVO)
	 */	
	@Override
	public void update(GainLevelRateVO vo) throws Exception{
		Object[] param = new Object[6];
		param[0] = Utility.parseInt(vo.getSerial_no() , new Integer(0)) ;
		param[1] = Utility.parseBigDecimal(vo.getGain_rate() , new BigDecimal(0));
		param[2] = Utility.parseBigDecimal(vo.getFloat_rate(), new BigDecimal(0));
		param[3] = Utility.parseInt(vo.getStart_date(), new Integer(0));
		param[4] = Utility.parseInt(vo.getEnd_date(), new Integer(0));
		param[5] = Utility.parseInt(vo.getInput_man() , new Integer(0));
		try{
			super.update(updateRateSql , param) ;
		}catch(Exception e){
			throw new BusiException("修改收益率失败:"+ e.getMessage()) ;
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelRateLocal#del(enfo.crm.vo.GainLevelRateVO)
	 */
	@Override
	public void del(GainLevelRateVO vo) throws Exception{
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(vo.getSerial_no() , new Integer(0));
		param[1] = Utility.parseInt(vo.getInput_man() , new Integer(0));
		try{
			super.cud(delRateSql , param) ;
		}catch(Exception e){
			throw new BusiException("删除收益率失败:" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelRateLocal#query(enfo.crm.vo.GainLevelRateVO)
	 */
	@Override
	public List query(GainLevelRateVO vo) throws Exception{
		Object[] param = new Object[2];
		List list = null;
		param[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		param[1] = Utility.parseInt(vo.getDf_serial_no(), new Integer(0));
		try{
			list = super.listBySql(queryRateSql , param) ;
			return list;
		}catch(Exception e){
			throw new BusiException("查询失败:" + e.getMessage());
		}
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelRateLocal#listOpenDate(enfo.crm.vo.GainLevelRateVO)
	 */
    @Override
	public List listOpenDate(GainLevelRateVO vo) throws Exception {
        Object[] params = new Object[9];
        List list = null;
        params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
        params[1] = Utility.parseInt(vo.getProduct_id(), new Integer(0));
        params[2] = Utility.parseInt(vo.getInput_man(), new Integer(0));
        params[3] = Utility.parseInt(vo.getSub_product_id(), new Integer(0));
        params[4] = Utility.parseInt(vo.getShare_flag(), new Integer(0));
        params[5] = Utility.parseInt(vo.getOpen_status(), new Integer(0));
        params[6] = Utility.parseInt(vo.getPz_flag(), new Integer(0));
        params[7] = Utility.parseInt(vo.getOpen_begin_date(),new Integer(0));
        params[8] = Utility.parseInt(vo.getOpen_end_date(),new Integer(0));
        return super.listBySql("{CALL SP_QUERY_TOPENDATE(?,?,?,?,?,?,?,?,?)}", params);
    }
}