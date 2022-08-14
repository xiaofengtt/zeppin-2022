package enfo.crm.customer;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IBusiExLocal;
import enfo.crm.dao.IPageList;
import enfo.crm.vo.ChannelVO;
import enfo.crm.vo.ContractVO;

public interface ChannelQueryLocal extends IBusiExLocal {

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询渠道来源购买明细
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList query(ChannelVO vo, int pageIndex, int pageSize) throws BusiException;

	/**
	 * @ejb.interface-method view-type = "local"
	 * 查询渠道贡献统计
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws BusiException
	 */
	IPageList querychannel(ChannelVO vo, int pageIndex, int pageSize) throws BusiException;

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
	void modiContractChannel(ContractVO vo, Integer input_man) throws BusiException;

}