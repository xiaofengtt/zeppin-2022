package cn.product.worldmall.util.lottery;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.dao.FrontUserPaidNumberDao;
import cn.product.worldmall.dao.FrontUserPaymentOrderDao;
import cn.product.worldmall.entity.FrontUserPaidNumber;
import cn.product.worldmall.entity.FrontUserPaymentOrder;
import cn.product.worldmall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.worldmall.entity.LuckygameGoodsIssue;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.SharenumsVO;


@Service
public class LuckygameGoodsLotteryUtil {
	public static final Logger log= LoggerFactory.getLogger(LuckygameGoodsLotteryUtil.class);

	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private FrontUserPaidNumberDao frontUserPaidNumberDao;
	
	
	public int getLuckyNum(LuckygameGoodsIssue lgi, Map<Integer, FrontUserPaymentOrder> realOrderMap, Map<String, Integer> realBuyCountMap, List<FrontUserPaymentOrder> listrealOrder) {
		Timestamp lotterytime = lgi.getLotterytime();
		//由此时间向前取100条
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("timeend", Utlity.timeSpanToString(lotterytime));
		searchMap.put("offSet", 0);
		searchMap.put("pageSize", 100);
		List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);//计算用的记录
		if(listorder != null && listorder.size() > 0) {
			Map<Integer, FrontUserPaymentOrder> orderMap = new HashMap<Integer, FrontUserPaymentOrder>();
			
			searchMap.clear();
			searchMap.put("goodsIssue", lgi.getUuid());
			searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
			listrealOrder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
			if(listrealOrder != null && listrealOrder.size() > 0) {
				for(FrontUserPaymentOrder fupo : listrealOrder) {
					addOrderMap(fupo, realOrderMap, realBuyCountMap);
				}
			}
			//计算数值A
			long calculateNum = 0;
			if(listorder.size() == 100) {
				for(FrontUserPaymentOrder fupo : listorder) {
					calculateNum += addOrderMap(fupo, orderMap);
				}
			} else {
				for(FrontUserPaymentOrder fupo : listorder) {
					calculateNum += addOrderMap(fupo, orderMap);
				}
				int remain = 100 - listorder.size();
				int k = 0;
				for(int i = 0; i < remain; i++) {
					if(k < listorder.size()) {
						FrontUserPaymentOrder fupo = listorder.get(k);
						calculateNum += addOrderMap(fupo, orderMap);
						k++;
					} else {
						k = 0;
						FrontUserPaymentOrder fupo = listorder.get(k);
						calculateNum += addOrderMap(fupo, orderMap);
						k++;
					}
				}
			}
			
			//计算幸运号码
			long anum = calculateNum%lgi.getShares().intValue();
			anum = Math.abs(anum);
			int luckyNum = BigDecimal.valueOf(anum).add(BigDecimal.valueOf(Double.valueOf(Utlity.LUCKY_NUM_START))).intValue();
			return luckyNum;
		}
		return 0;
	}
    
	/**
	 * 重复代码封装
	 * 处理luckyNum和order的对应关系
	 * @param fupo
	 * @param calculateNum
	 * @param orderMap
	 */
	public long addOrderMap(FrontUserPaymentOrder fupo, Map<Integer, FrontUserPaymentOrder> orderMap) {
		FrontUserPaidNumber fupn = this.frontUserPaidNumberDao.get(fupo.getUuid());
		String paidSharenums = fupn.getPaidSharenums();
		if(!Utlity.checkStringNull(paidSharenums)) {//封装中奖号码和对应购买记录
			SharenumsVO svo = JSONUtils.json2obj(paidSharenums, SharenumsVO.class);
			List<Integer> current = svo.getCurrentNums();
			for(Integer num : current) {
				orderMap.put(num, fupo);
			}
		}
		long timeNum = Utlity.getTimeNum(fupo.getCreatetime());
		return timeNum;
	}
	
	/**
	 * 传统玩法
	 * 重复代码封装
	 * 处理luckyNum和order的对应关系
	 * @param fupo
	 * @param calculateNum
	 * @param orderMap
	 */
	public long addOrderMap(FrontUserPaymentOrder fupo, Map<Integer, FrontUserPaymentOrder> orderMap, Map<String, Integer> realBuyCountMap) {
		FrontUserPaidNumber fupn = this.frontUserPaidNumberDao.get(fupo.getUuid());
		String paidSharenums = fupn.getPaidSharenums();
		if(!Utlity.checkStringNull(paidSharenums)) {//封装中奖号码和对应购买记录
			SharenumsVO svo = JSONUtils.json2obj(paidSharenums, SharenumsVO.class);
			List<Integer> current = svo.getCurrentNums();
			for(Integer num : current) {
				orderMap.put(num, fupo);
			}
		}
		
		if(realBuyCountMap != null) {
			//计算总投注数
			Integer totalBuyCount = realBuyCountMap.get(fupo.getFrontUser()) == null ? 0 : realBuyCountMap.get(fupo.getFrontUser());
			totalBuyCount+=fupo.getBuyCount();
			realBuyCountMap.put(fupo.getFrontUser(), totalBuyCount);
		}
		
		long timeNum = Utlity.getTimeNum(fupo.getCreatetime());
		return timeNum;
	}
	
	public static void main(String[] args) {
		
	}
}
