 
package enfo.crm.customer;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;
import enfo.crm.vo.ChannelVO;
import enfo.crm.vo.ContractVO;

@Component(value="channelQuery")
public class ChannelQueryBean extends enfo.crm.dao.CrmBusiExBean implements ChannelQueryLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询渠道来源购买明细
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	public IPageList query(ChannelVO vo,int pageIndex,int pageSize) throws BusiException{
		String procSql = "{call SP_STAT_CHANNELBUYDETAIL(?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[11];
		IPageList rsList = null;
		
		params[0] = vo.getCell_id();
		params[1] = vo.getChannel_type();
		params[2] = vo.getChannelName();
		params[3] = vo.getBegin_date();
		params[4] = vo.getEnd_date();
		
		params[5] = vo.getCust_id();
		params[6] = vo.getProduct_id();
		params[7] = vo.getChannel_id();
		params[8] = vo.getSale_man();
		params[9] = vo.getService_man();
		params[10] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		
		rsList= super.listProcPage(procSql, params, pageIndex, pageSize);
		return rsList;
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询渠道贡献统计
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	public IPageList querychannel(ChannelVO vo,int pageIndex,int pageSize) throws BusiException{
		String strSql = "{call SP_STAT_CHANNELTOTAL1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[23];
		IPageList rsList = null;
		
		params[0] = Utility.parseInt(Utility.trimNull(vo.getCell_id()), new Integer(0));
		params[1] = Utility.trimNull(vo.getChannel_type());
		params[2] = Utility.trimNull(vo.getChannelName());
		params[3] = Utility.parseInt(Utility.trimNull(vo.getBegin_date()), new Integer(0));
		params[4] = Utility.parseInt(Utility.trimNull(vo.getEnd_date()), new Integer(0));
		params[5] = Utility.parseInt(Utility.trimNull(vo.getGroup_type()), new Integer(0));
		
		params[6] = Utility.parseInt(Utility.trimNull(vo.getList_product()), new Integer(0));
		params[7] = Utility.parseInt(Utility.trimNull(vo.getList_channel()), new Integer(0));
		params[8] = Utility.parseInt(Utility.trimNull(vo.getList_saleman()), new Integer(0));
		params[9] = Utility.parseInt(Utility.trimNull(vo.getList_serviceman()), new Integer(0));
		params[10] = Utility.parseInt(Utility.trimNull(vo.getIntrust_flag1()), new Integer(0));
		params[11] = Utility.parseInt(Utility.trimNull(vo.getChannel_id()), new Integer(0));
		params[12] = Utility.parseInt(Utility.trimNull(vo.getSale_man()), new Integer(0));
		params[13] = Utility.parseInt(Utility.trimNull(vo.getService_man()), new Integer(0));

		params[14] = Utility.parseInt(vo.getList_cust(), new Integer(0));
		params[15] = Utility.trimNull(vo.getCust_name());
		params[16] = Utility.trimNull(vo.getProduct_name());
		params[17] = Utility.parseInt(vo.getList_channel_coopertype(), new Integer(0));
		params[18] = Utility.trimNull(vo.getChannel_coopertype());
		
		params[19] = Utility.parseInt(vo.getRecommend_flag(), new Integer(2));
		params[20] = Utility.parseInt(vo.getNormal_flag(), new Integer(1));
		params[21] = Utility.parseInt(vo.getEnd_flag(), new Integer(2));
		
		params[22] = Utility.parseInt(vo.getSub_product_id(),new Integer(0));
		
		rsList= super.listProcPage(strSql, params, pageIndex, pageSize);
		return rsList;
	}
	
	/**
	 * @ejb.interface-method view-type = "local"
	 * SP_MODI_CHANNEL @IN_CONTRACT_SERIAL_NO INT
                     @IN_CHANNEL_TYPE       NVARCHAR(20)
                     @IN_CHANNEL_ID         INT
                     @IN_CHANNEL_MEMO       NVARCHAR(200)
                     @IN_INPUT_MAN          INT
					 @IN_CONTRACT_SUB_BH	NVARCHAR(60)
					 @IN_LINK_MAN			INTEGER
					 @IN_RECOMMEND_MAN		INTEGER
					 @IN_CHANNEL_COOPERTYPE NVARCHAR(10)
					 @IN_MARKET_MONEY		DEC(16,3)
	 * 修改已审核的认购渠道
	 * @param vo
	 * @return
	 * @throws BusiException
	 */
	public void modiContractChannel(ContractVO vo, Integer input_man) throws BusiException{
		String strSql = "{?=call SP_MODI_CHANNEL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
		Object[] params = new Object[21];		
		params[0] = Utility.parseInt(vo.getSerial_no(), new Integer(0));
		params[1] = Utility.trimNull(vo.getChannel_type());
		params[2] = Utility.parseInt(vo.getChannel_id(), new Integer(0));
		params[3] = Utility.trimNull(vo.getChannel_memo());
		params[4] = Utility.parseInt(input_man, new Integer(0));
		params[5] = Utility.parseInt(vo.getBonus_flag(),null);
		params[6] = Utility.trimNull(vo.getContract_sub_bh());
		params[7] = Utility.parseInt(vo.getLink_man(),new Integer(0));
		params[8] = Utility.parseInt(vo.getRecommend_man(),new Integer(0));
		params[9] = Utility.trimNull(vo.getChannel_cooperation());
		params[10] = Utility.parseBigDecimal(vo.getMarket_trench_money(),new BigDecimal(0.00));		
		params[11] = Utility.trimNull(vo.getBank_id());
		params[12] = Utility.trimNull(vo.getBank_sub_name());
		params[13] = Utility.trimNull(vo.getBank_acct());
		params[14] = Utility.parseInt(vo.getQs_date(), new Integer(0));
		params[15] = Utility.parseInt(vo.getJk_date(), new Integer(0));
		params[16] = Utility.trimNull(vo.getJk_type());
		params[17] = Utility.trimNull(vo.getSummary());
		params[18] = Utility.parseInt(vo.getProv_flag(), new Integer(0));
		params[19] = Utility.trimNull(vo.getProv_level());
		params[20] = Utility.trimNull(vo.getGain_acct());
		super.cudProc(strSql, params);
	}
}