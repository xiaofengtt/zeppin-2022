package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserCommentDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserComment;
import cn.product.treasuremall.entity.FrontUserComment.FrontUserCommentStatus;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.UserCommentService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserCommentVO;

@Service("userCommentService")
public class UserCommentServiceImpl implements UserCommentService{
	
	@Autowired
	private FrontUserCommentDao frontUserCommentDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserShowId", showId);
		searchMap.put("status", status);
		searchMap.put("sort", sort);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的用户晒单信息的总数
		Integer totalResultCount = frontUserCommentDao.getCountByParams(searchMap);
		//查询符合条件的用户晒单信息列表
		List<FrontUserComment> list = frontUserCommentDao.getListByParams(searchMap);
		List<FrontUserCommentVO> listvo = new ArrayList<FrontUserCommentVO>();
		for(FrontUserComment frontUserComment : list) {
			//界面返回封装对象
			FrontUserCommentVO frontUserCommentVO = new FrontUserCommentVO(frontUserComment);
			
			//资源信息
			//图片
			if(!Utlity.checkStringNull(frontUserComment.getImage())) {
				String[] images = frontUserComment.getImage().split(",");
				List<Map<String, Object>> listImage = new ArrayList<Map<String,Object>>();
				for(String image : images) {
					if(!Utlity.checkStringNull(image)) {
						Resource re = this.resourceDao.get(image);
						if(re != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("image", re.getUrl());
							listImage.add(map);
						}
					}
				}
				frontUserCommentVO.setImageList(listImage);
			}
			//视频
			if(!Utlity.checkStringNull(frontUserComment.getVideo())) {
				String[] videos = frontUserComment.getVideo().split(",");
				List<Map<String, Object>> listVideo = new ArrayList<Map<String,Object>>();
				for(String video : videos) {
					if(!Utlity.checkStringNull(video)) {
						Resource re = this.resourceDao.get(video);
						if(re != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("video", re.getUrl());
							listVideo.add(map);
						}
					}
				}
				frontUserCommentVO.setVideoList(listVideo);
			}
			//商品信息
			LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(frontUserComment.getGoodsIssue());
			if(lgi != null) {
				frontUserCommentVO.setTitle(lgi.getTitle());
			}
			//用户信息
			FrontUser fu = this.frontUserDao.get(frontUserComment.getFrontUser());
			if(fu != null) {
				frontUserCommentVO.setNickName(fu.getNickname());
			}
			listvo.add(frontUserCommentVO);
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取用户晒单信息
		FrontUserComment frontUserComment = frontUserCommentDao.get(uuid);
		if (frontUserComment == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		//界面返回封装对象
		FrontUserCommentVO frontUserCommentVO = new FrontUserCommentVO(frontUserComment);
		
		//资源信息
		//图片
		if(!Utlity.checkStringNull(frontUserComment.getImage())) {
			String[] images = frontUserComment.getImage().split(",");
			List<Map<String, Object>> listImage = new ArrayList<Map<String,Object>>();
			for(String image : images) {
				if(!Utlity.checkStringNull(image)) {
					Resource re = this.resourceDao.get(image);
					if(re != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("image", re.getUrl());
						listImage.add(map);
					}
				}
			}
			frontUserCommentVO.setImageList(listImage);
		}
		//视频
		if(!Utlity.checkStringNull(frontUserComment.getVideo())) {
			String[] videos = frontUserComment.getVideo().split(",");
			List<Map<String, Object>> listVideo = new ArrayList<Map<String,Object>>();
			for(String video : videos) {
				if(!Utlity.checkStringNull(video)) {
					Resource re = this.resourceDao.get(video);
					if(re != null) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("video", re.getUrl());
						listVideo.add(map);
					}
				}
			}
			frontUserCommentVO.setVideoList(listVideo);
		}
		//商品信息
		LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(frontUserComment.getGoodsIssue());
		if(lgi != null) {
			frontUserCommentVO.setTitle(lgi.getTitle());
		}
		//用户信息
		FrontUser fu = this.frontUserDao.get(frontUserComment.getFrontUser());
		if(fu != null) {
			frontUserCommentVO.setNickName(fu.getNickname());
		}
		
		result.setData(frontUserCommentVO);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void check(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String reason = paramsMap.get("reason") == null ? "" : paramsMap.get("reason").toString();
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		if(!FrontUserCommentStatus.CHECKED.equals(status) && !FrontUserCommentStatus.NOPASS.equals(status)){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("审核状态错误！");
			return;
		}
		//获取用户晒单信息
		FrontUserComment frontUserComment = frontUserCommentDao.get(uuid);
		if(frontUserComment != null && uuid.equals(frontUserComment.getUuid())){
			frontUserComment.setStatus(status);
			frontUserComment.setReason(reason);
			
			frontUserComment.setOperator(admin);
			frontUserComment.setOperattime(new Timestamp(System.currentTimeMillis()));
			
			//20201023新增审核通过赠送1金币操作
			if(FrontUserCommentStatus.CHECKED.equals(status)) {
				frontUserCommentDao.updateAndGift(frontUserComment);
			} else {
				frontUserCommentDao.update(frontUserComment);
			}
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功");
		} else {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}
}
