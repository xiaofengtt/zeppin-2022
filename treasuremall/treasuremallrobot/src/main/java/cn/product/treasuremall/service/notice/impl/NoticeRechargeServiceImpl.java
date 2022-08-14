package cn.product.treasuremall.service.notice.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.service.notice.NoticeRechargeService;
import cn.product.treasuremall.util.unionpay.UnionPayUtlity;

@Service("noticeRechargeService")
public class NoticeRechargeServiceImpl implements NoticeRechargeService{
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;

	@Override
	public void byAliwap(InputParams params, DataResult<Object> result) {
		//{amount=10000, channel=8c41c2ff-2c36-4f90-92e0-6c30618dd92d, sign=N%2Fd%2Fud4kpGMfJYLFzTM3ux%2F%2F%2FPNwsv%2BSlAGAzE2LvMezKfab5ASuP68I8M3RbQAsIyXcGMAtHXp9SMCeECAG2B4fLH7ytCL3DB6ZoTccqOhnxZ4GTePBcJ0soIIrjIdpk1l%2BkI739MG8UDSLaIs1r%2BaIlap9II2HRNjbUB13pFcfpNOcSJIV4Y2dt81OEE963hKXUUIPg5CgB9x%2FcCSoi%2F91If3CdIy8625dyNSCzD4jtorhlaydqvfPC6yXc0ElQEICe8lOInkmm2zxP6jIpM5LeeidNWOnt0O%2BU9llIAO7WKIpo6rvm26xk67ftE0kzlkq5tZIBW9IVWoOG0JmAg%3D%3D, orderNum=1231111213, company=2c41c2ff-2c36-4f90-92e0-6c30618dd91d, transData=111211345, paymentOrderNum=10126646600552186777600, timestamp=1585645990154, status=close}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void byUnion(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Map<String, String> paramsls = (Map<String, String>) paramsMap.get("paramsls"); 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Boolean resultFlag = false;
		try {
			//验签
			if(UnionPayUtlity.verify(paramsls)) {//通过
				resultFlag = true;
				//进行上账操作
				resultMap = this.frontUserHistoryDao.rechargeNoticeByUnion(paramsls);
				result.setData(resultMap);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("result", resultFlag);
		result.setData(resultMap);
		return;
	}
	
}
