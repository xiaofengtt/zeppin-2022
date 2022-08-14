package cn.product.treasuremall.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import cn.product.treasuremall.dao.AdminOffsetOrderDao;
import cn.product.treasuremall.dao.FrontUserAccountDao;
import cn.product.treasuremall.dao.FrontUserCommentDao;
import cn.product.treasuremall.dao.FrontUserHistoryDao;
import cn.product.treasuremall.entity.AdminOffsetOrder;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderStatus;
import cn.product.treasuremall.entity.AdminOffsetOrder.AdminOffsetOrderType;
import cn.product.treasuremall.entity.FrontUserAccount;
import cn.product.treasuremall.entity.FrontUserAccount.FrontUserAccountStatus;
import cn.product.treasuremall.entity.FrontUserComment;
import cn.product.treasuremall.entity.FrontUserHistory;
import cn.product.treasuremall.entity.FrontUserHistory.FrontUserHistoryType;
import cn.product.treasuremall.entity.base.Constants;
import cn.product.treasuremall.mapper.FrontUserCommentMapper;
import cn.product.treasuremall.util.Utlity;

@Component
public class FrontUserCommentDaoImpl implements FrontUserCommentDao{
	
	@Autowired
	private FrontUserCommentMapper frontUserCommentMapper;
	@Autowired
	private FrontUserAccountDao frontUserAccountDao;
	@Autowired
	private AdminOffsetOrderDao adminOffsetOrderDao;
	@Autowired
	private FrontUserHistoryDao frontUserHistoryDao;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return frontUserCommentMapper.getCountByParams(params);
	}
	
	@Override
	public List<FrontUserComment> getListByParams(Map<String, Object> params) {
		return frontUserCommentMapper.getListByParams(params);
	}
	
    @Override
	@Cacheable(cacheNames="frontUserComment",key="'frontUserComment:' + #key")
	public FrontUserComment get(String key) {
		return frontUserCommentMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(FrontUserComment frontUserComment) {
		return frontUserCommentMapper.insert(frontUserComment);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserComment", key = "'frontUserComment:' + #key")})
	public int delete(String key) {
		return frontUserCommentMapper.deleteByPrimaryKey(key);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserComment", key = "'frontUserComment:' + #frontUserComment.uuid")})
	public int update(FrontUserComment frontUserComment) {
		return frontUserCommentMapper.updateByPrimaryKey(frontUserComment);
	}

	@Override
	@Caching(evict={@CacheEvict(value = "frontUserComment", key = "'frontUserComment:' + #frontUserComment.uuid")})
	public void updateAndGift(FrontUserComment frontUserComment) {
		this.frontUserCommentMapper.updateByPrimaryKey(frontUserComment);
		//赠送金币
		FrontUserAccount fua = this.frontUserAccountDao.get(frontUserComment.getFrontUser());
		if(fua != null && FrontUserAccountStatus.NORMAL.equals(fua.getAccountStatus())) {
			String content = "用户成功晒单赠送1金币";
			AdminOffsetOrder aoo = new AdminOffsetOrder();
			aoo.setUuid(UUID.randomUUID().toString());
			aoo.setdAmount(BigDecimal.ONE);
			aoo.setFrontUser(frontUserComment.getFrontUser());
			aoo.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
			aoo.setOrderNum(Utlity.getOrderNum());
			aoo.setReason(content);
			aoo.setRemark(content);
			aoo.setStatus(AdminOffsetOrderStatus.CHECKED);
			aoo.setType(AdminOffsetOrderType.ADMIN_ADD);
			aoo.setCreatetime(new Timestamp(System.currentTimeMillis()));
			
			FrontUserHistory fuh = new FrontUserHistory();
			fuh.setUuid(UUID.randomUUID().toString());
			fuh.setFrontUser(aoo.getFrontUser());
			fuh.setOrderNum(aoo.getOrderNum());
			fuh.setOrderId(aoo.getUuid());
			fuh.setdAmount(aoo.getdAmount());
			fuh.setBalanceBefore(fua.getBalance().add(fua.getBalanceLock()));
			fuh.setCreatetime(aoo.getCreatetime());
			fuh.setRemark(content);
			
			fuh.setOrderType(Constants.ORDER_TYPE_SYSTEM_ADD);
			fuh.setType(FrontUserHistoryType.USER_ADD);
			fuh.setReason(content);
			
			fua.setBalance(fua.getBalance().add(aoo.getdAmount()));
			fuh.setBalanceAfter(fua.getBalance().add(fua.getBalanceLock()));
			
			this.adminOffsetOrderDao.insert(aoo);
			this.frontUserHistoryDao.insert(fuh);
			this.frontUserAccountDao.update(fua);
		}
	}

}
