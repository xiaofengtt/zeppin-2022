package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AdminDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserMessageDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.dao.WinningInfoReceiveDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserMessage;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageSourceType;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageStatus;
import cn.product.treasuremall.entity.FrontUserMessage.FrontUserMessageType;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.entity.WinningInfoReceive;
import cn.product.treasuremall.entity.WinningInfoReceive.WinningInfoReceiveStatus;
import cn.product.treasuremall.entity.WinningInfoReceive.WinningInfoReceiveType;
import cn.product.treasuremall.service.back.WinningInfoService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.SendSmsNew;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.ProvideInfoVO;
import cn.product.treasuremall.vo.back.WinningInfoVO;

/**
 */
@Service("winningInfoService")
public class WinningInfoServiceImpl implements WinningInfoService{
	
	@Autowired
    private FrontUserDao frontUserDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
    private WinningInfoDao winningInfoDao;
	
	@Autowired
    private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
    private GoodsDao goodsDao;
	
	@Autowired
    private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
    private WinningInfoReceiveDao winningInfoReceiveDao;
	
	@Autowired
    private FrontUserMessageDao frontUserMessageDao;

	/**
	 * 获取中奖纪录列表
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String goodsId = paramsMap.get("goodsId") == null ? "" : paramsMap.get("goodsId").toString();
		String goodsIssue = paramsMap.get("goodsIssue") == null ? "" : paramsMap.get("goodsIssue").toString();
		String winningTime = paramsMap.get("winningTime") == null ? "" : paramsMap.get("winningTime").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("showId", showId);
		searchMap.put("goodsId", goodsId);
		searchMap.put("goodsIssue", goodsIssue);
		searchMap.put("type", type);
		if(!Utlity.checkStringNull(winningTime)) {
			String[] times = winningTime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0]);
				searchMap.put("timeend", times[1]);
			}
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<WinningInfo> list = this.winningInfoDao.getListByParams(searchMap);
		Integer totalCount = this.winningInfoDao.getCountByParams(searchMap);
		
		List<WinningInfoVO> listvo = new ArrayList<WinningInfoVO>();
		if(list != null && list.size() > 0) {
			for(WinningInfo wi : list) {
				WinningInfoReceive wir = this.winningInfoReceiveDao.get(wi.getUuid());
				WinningInfoVO wivo = null;
				if(wir != null) {
					wivo = new WinningInfoVO(wi, wir);
				} else {
					wivo = new WinningInfoVO(wi);
				}
				
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
				if(fu != null) {
					wivo.setNickname(fu.getNickname());
					wivo.setShowId(fu.getShowId());
					wivo.setIsRobot(fu.getType().equals(FrontUserType.ROBOT));
					
					Resource re = this.resourceDao.get(fu.getImage());
					if(re != null) {
						wivo.setImageUrl(re.getUrl());
					}
				}
				
				
				
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
				if(lgi != null) {
					wivo.setTitle(lgi.getTitle());
					wivo.setShortTitle(lgi.getShortTitle());
					wivo.setShares(lgi.getShares());
					wivo.setIssueNum(lgi.getIssueNum());
					wivo.setStarttime(lgi.getCreatetime());
					wivo.setFinishedtime(lgi.getFinishedtime());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setPrice(good.getPrice());
					wivo.setCover("");
					//商品封面图
					searchMap.clear();
					searchMap.put("belongs", lgi.getGoodsId());
					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							wivo.setCover(re.getUrl());
						}
					}
				}
				listvo.add(wivo);
			}
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;	
	}

	/**
	 * 获取派奖列表
	 */
	@Override
	public void receivelist(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String source = paramsMap.get("source") == null ? "" : paramsMap.get("source").toString();
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String receiveTime = paramsMap.get("receiveTime") == null ? "" : paramsMap.get("receiveTime").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("showId", showId);
		searchMap.put("source", source);
		searchMap.put("name", name);
		searchMap.put("status", status);
		searchMap.put("type", WinningInfoReceiveType.ENTITY);
		if(!Utlity.checkStringNull(receiveTime)) {
			String[] times = receiveTime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0]);
				searchMap.put("timeend", times[1]);
			}
		}
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<WinningInfoReceive> list = this.winningInfoReceiveDao.getListByParams(searchMap);
		Integer totalCount = this.winningInfoReceiveDao.getCountByParams(searchMap);
		
		List<WinningInfoVO> listvo = new ArrayList<WinningInfoVO>();
		if(list != null && list.size() > 0) {
			for(WinningInfoReceive wir : list) {
				WinningInfo wi = this.winningInfoDao.get(wir.getWinningInfo());
				WinningInfoVO wivo = new WinningInfoVO(wi, wir);
				
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
				if(fu != null) {
					wivo.setNickname(fu.getNickname());
					wivo.setShowId(fu.getShowId());
					wivo.setIsRobot(fu.getType().equals(FrontUserType.ROBOT));
					
					Resource re = this.resourceDao.get(fu.getImage());
					if(re != null) {
						wivo.setImageUrl(re.getUrl());
					}
				}
				
				//封装订单号
				FrontUserPaymentOrder fupo = this.frontUserPaymentOrderDao.get(wir.getOrderId());
				if(fupo != null) {
					wivo.setOrderNum(String.valueOf(fupo.getOrderNum()));
				}
				
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
				if(lgi != null) {
					wivo.setTitle(lgi.getTitle());
					wivo.setShortTitle(lgi.getShortTitle());
					wivo.setShares(lgi.getShares());
					wivo.setIssueNum(lgi.getIssueNum());
					wivo.setStarttime(lgi.getCreatetime());
					wivo.setFinishedtime(lgi.getFinishedtime());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setPrice(good.getPrice());
					wivo.setSource(good.getSource());
					wivo.setSourceUrl(good.getSourceUrl());
					wivo.setCover("");
					//商品封面图
					searchMap.clear();
					searchMap.put("belongs", lgi.getGoodsId());
					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							wivo.setCover(re.getUrl());
						}
					}
				}
				listvo.add(wivo);
			}
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;	
	}

	/**
	 * 派奖登记
	 */
	@Override
	public void receive(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String company = paramsMap.get("company") == null ? "" : paramsMap.get("company").toString();
		String expressNumber = paramsMap.get("expressNumber") == null ? "" : paramsMap.get("expressNumber").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		try {
			WinningInfoReceive wir = this.winningInfoReceiveDao.get(uuid);
			if(wir == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息不存在！");
				return;
			}
			if(!WinningInfoReceiveStatus.RECEIVE.equals(wir.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息状态错误！");
				return;
			}
			if(!Utlity.checkStringNull(wir.getProvideInfo())) {
				ProvideInfoVO pivo = JSONUtils.json2obj(wir.getProvideInfo(), ProvideInfoVO.class);
				pivo.setCompany(company);
				pivo.setExpressNumber(expressNumber);
				
				wir.setProvideInfo(JSONUtils.obj2json(pivo));
				wir.setStatus(WinningInfoReceiveStatus.FINISHED);
				wir.setOperator(admin);
				wir.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.winningInfoReceiveDao.update(wir);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("操作失败！");
				return;
			}
			
			/*
			 * 异步消息
			 */
			//派奖通知消息			
			Goods g = this.goodsDao.get(wir.getGoodsId());
			FrontUserMessage fum = new FrontUserMessage();
			fum.setUuid(UUID.randomUUID().toString());
			fum.setFrontUser(wir.getFrontUser());
			fum.setFrontUserShowId(wir.getShowId());
			fum.setTitle("提示，商品发货");
			StringBuilder content = new StringBuilder();
			if(g != null) {
				content.append("您在"+Utlity.timeSpanToChinaDateString(wir.getCreatetime())+"申请兑换的"+g.getShortname()+"商品已出库发货！");
			}
			fum.setContent(content.toString());
			fum.setSourceId(wir.getWinningInfo());
			fum.setStatus(FrontUserMessageStatus.NORMAL);
			fum.setType(FrontUserMessageType.USER_ORDER);
			fum.setSourceType(FrontUserMessageSourceType.ORDER_TYPE_USER_PAYMENT);
			fum.setCreatetime(new Timestamp(System.currentTimeMillis()));
			this.frontUserMessageDao.sendMessage(fum);
			this.frontUserMessageDao.sendMessage(fum, SendSmsNew.TREASUREMALL_SH_TEMP_DELIVERY);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("派奖登记成功！");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
		
	}

	@Override
	public void confirm(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		try {
			WinningInfoReceive wir = this.winningInfoReceiveDao.get(uuid);
			if(wir == null) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息不存在！");
				return;
			}
			if(!WinningInfoReceiveStatus.RETURN.equals(status) && !WinningInfoReceiveStatus.CONFIRM.equals(status)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("状态错误！");
				return;
			}
			if(!WinningInfoReceiveStatus.FINISHED.equals(wir.getStatus())) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("领奖信息状态错误！");
				return;
			}
			if(!Utlity.checkStringNull(wir.getProvideInfo())) {
				wir.setStatus(status);
				wir.setOperator(admin);
				wir.setOperattime(new Timestamp(System.currentTimeMillis()));
				
				this.winningInfoReceiveDao.update(wir);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("操作失败！");
				return;
			}
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功！");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}
	
	
}
