package cn.product.worldmall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.worldmall.dao.RobotSettingDao;
import cn.product.worldmall.entity.RobotSetting;
import cn.product.worldmall.mapper.RobotSettingMapper;
import cn.product.worldmall.rabbit.send.RabbitSenderService;
import cn.product.worldmall.util.Utlity;

@Component
public class RobotSettingDaoImpl implements RobotSettingDao{

    @Autowired
    private RobotSettingMapper robotSettingMapper;
    
    @Autowired
    private RabbitSenderService rabbitSenderService;
    
    @Override
	public Integer getCountByParams(Map<String, Object> params) {
		return robotSettingMapper.getCountByParams(params);
	}
	
	@Override
	public List<RobotSetting> getListByParams(Map<String, Object> params) {
		return robotSettingMapper.getListByParams(params);
	}

	@Override
	public RobotSetting get(String key) {
		return robotSettingMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(RobotSetting robotSetting) {
		return robotSettingMapper.insert(robotSetting);
	}

	@Override
	public int delete(String key) {
		return robotSettingMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int update(RobotSetting robotSetting) {
		return robotSettingMapper.updateByPrimaryKey(robotSetting);
	}

	@Override
	@Transactional
	public void batchSetting(List<RobotSetting> insert, List<RobotSetting> update) {
		if(insert != null) {
			for(RobotSetting rs : insert) {
				this.robotSettingMapper.insert(rs);
			}
		}
		
		if(update != null) {
			for(RobotSetting rs : update) {
				this.robotSettingMapper.updateByPrimaryKey(rs);
			}
		}
	}
	
	@Override
	@Transactional
	public void updateStatus(Map<String, Object> params) {
		this.robotSettingMapper.updateStatus(params);
	}

	@Override
	public void robotWorkReady(String[] frontUserArr) {
		try {
			if(frontUserArr != null && frontUserArr.length > 0) {
				for(String frontUser : frontUserArr) {
					if(!Utlity.checkStringNull(frontUser)) {
						this.rabbitSenderService.robotWorkMessageSend(frontUser);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void robotWorkStop(String[] frontUserArr) {
		try {
			if(frontUserArr != null && frontUserArr.length > 0) {
				for(String frontUser : frontUserArr) {
					this.rabbitSenderService.robotWorkMessageRemove(frontUser);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
