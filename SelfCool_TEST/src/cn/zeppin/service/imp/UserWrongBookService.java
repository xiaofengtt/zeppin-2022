package cn.zeppin.service.imp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemDAO;
import cn.zeppin.dao.api.ISsoUserTestDAO;
import cn.zeppin.dao.api.ISsoUserTestItemCountDAO;
import cn.zeppin.dao.api.ISsoUserTestItemDAO;
import cn.zeppin.entity.Item;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTest;
import cn.zeppin.entity.SsoUserTestItem;
import cn.zeppin.entity.SsoUserTestItemCount;
import cn.zeppin.entity.Subject;
import cn.zeppin.service.api.IUserWrongBookService;
import cn.zeppin.utility.Dictionary;

public class UserWrongBookService implements IUserWrongBookService {
	
	private ISsoUserTestItemCountDAO ssoUserTestItemCountDAO;
	private ISsoUserTestDAO ssoUserTestDAO;
	private ISsoUserTestItemDAO ssoUserTestItemDAO;
	private IItemDAO itemDAO;
	
	
	public ISsoUserTestItemCountDAO getSsoUserTestItemCountDAO() {
		return ssoUserTestItemCountDAO;
	}

	public void setSsoUserTestItemCountDAO(ISsoUserTestItemCountDAO ssoUserTestItemCountDAO) {
		this.ssoUserTestItemCountDAO = ssoUserTestItemCountDAO;
	}
	
	public ISsoUserTestDAO getSsoUserTestDAO() {
		return ssoUserTestDAO;
	}

	public void setSsoUserTestDAO(ISsoUserTestDAO ssoUserTestDAO) {
		this.ssoUserTestDAO = ssoUserTestDAO;
	}

	public ISsoUserTestItemDAO getSsoUserTestItemDAO() {
		return ssoUserTestItemDAO;
	}

	public void setSsoUserTestItemDAO(ISsoUserTestItemDAO ssoUserTestItemDAO) {
		this.ssoUserTestItemDAO = ssoUserTestItemDAO;
	}


	public IItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(IItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	/**
	 * 获取用户错题本中的试题
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public List<Map<String,Object>> searchAllWrongItem(SsoUser currentUser, Subject subject) {
		// TODO Auto-generated method stub
		return this.getSsoUserTestItemCountDAO().searchAllWrongItem(currentUser, subject);
	}

	/**
	 * 保存错题本的测试（每题保存）
	 */
	@Override
	public SsoUserTestItem saveWrongbookTest(SsoUser currentUser, Item item, Map<String, Object> itemMap) {
		// TODO Auto-generated method stub
		
		Integer userId = currentUser.getId();
		int itemId = (int) itemMap.get("id");
		int blankInx = (int) itemMap.get("blankInx");
		Short isAnswered = (Short) itemMap.get("isAnswered");
		Short completeType = (Short) itemMap.get("completeType");
		int answertime = (int) itemMap.get("answertime");
		String reference = (String) itemMap.get("reference");
		String content = (String) itemMap.get("content");
		
		
		
		//目前能够判断是错题本的做题记录并取值唯一的，只能在paper is null上面做文章
		SsoUserTest ssoUsertest = this.getSsoUserTestDAO().getWrongbookUserTest(currentUser, item.getSubject());
		
		//如果没答过错题本的题，需要创建错题本的专门ssoUsertest，主要体现在Paper为空
		if(ssoUsertest == null){
		    ssoUsertest = new SsoUserTest();
			ssoUsertest.setCreatetime(new Timestamp(System.currentTimeMillis()));
			ssoUsertest.setPaper(null);
			ssoUsertest.setSsoUser(currentUser);
			ssoUsertest.setStatus(Dictionary.USER_TEST_STATUS_COMPLETE);
			ssoUsertest = this.getSsoUserTestDAO().save(ssoUsertest);
		}
		
		SsoUserTestItem ssoUserTestItem = new SsoUserTestItem();
		
		ssoUserTestItem.setItem(item);
		ssoUserTestItem.setBlankInx(blankInx);
		ssoUserTestItem.setSsoUserTest(ssoUsertest);
		ssoUserTestItem.setReference(reference);
		ssoUserTestItem.setCompleteType(completeType == null ? Dictionary.SSO_USER_TEST_ITEM_WRONG : completeType);
		ssoUserTestItem.setContent(content);
		ssoUserTestItem.setAnswertime(answertime);
		ssoUserTestItem.setIsAnswered(isAnswered);
		ssoUserTestItem = this.getSsoUserTestItemDAO().save(ssoUserTestItem);
		
		/**
		 * 计算用户试题统计信息
		 */

		SsoUserTestItemCount ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().getByKey(userId, itemId, blankInx);
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		//当已经有用户做此题记录的时候
		if (ssoUserTestItemCount != null) {
			ssoUserTestItemCount.setLastTestItem(ssoUserTestItem);
			ssoUserTestItemCount.setLastTestItemAnswerTime(now);
			ssoUserTestItemCount.setLastTestItemCompleteType(ssoUserTestItem.getCompleteType());
			//错题本中做题，需要设置为已做过
			ssoUserTestItemCount.setIsWrongbookItemTested(Dictionary.SSO_USER_WRONGBOOK_ITEM_TESTED);
			ssoUserTestItemCount.setWrongbookItemTesttime(now);
			
			if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG) {
				ssoUserTestItemCount.setTestItemWrongCount(ssoUserTestItemCount.getTestItemWrongCount() + 1);
				
				if (ssoUserTestItemCount.getIsWrongbookItemMastered() == Dictionary.SSO_USER_WRONGBOOK_ITEM_MASTERED) {
					ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
					ssoUserTestItemCount.setWrongbookItemMastertime(null);
					ssoUserTestItemCount.setWrongbookItemCreatetime(now);
				}
			}
			else if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
				ssoUserTestItemCount.setTestItemCorrectCount(ssoUserTestItemCount.getTestItemCorrectCount() + 1);
			}
			
			ssoUserTestItemCount.setTestItemCount(ssoUserTestItemCount.getTestItemCount() + 1);
			
			ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().update(ssoUserTestItemCount);
		}
		else {
			//用户不可能是第一次做此题,没做过此题不应该出现在错题本中
			System.out.println("没做过此题不应该出现在错题本中，用户ID" + currentUser.getId() + "题ID：" + ssoUserTestItem.getItem());
			ssoUserTestItemCount = new SsoUserTestItemCount();
			ssoUserTestItemCount.setSsoUser(currentUser);
			ssoUserTestItemCount.setItem(item);
			ssoUserTestItemCount.setBlankInx(blankInx);
			
			ssoUserTestItemCount.setLastTestItem(ssoUserTestItem);
			ssoUserTestItemCount.setLastTestItemAnswerTime(new Timestamp(System.currentTimeMillis()));
			ssoUserTestItemCount.setLastTestItemCompleteType(ssoUserTestItem.getCompleteType());
			if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_WRONG) {
				ssoUserTestItemCount.setTestItemCorrectCount(0);
				ssoUserTestItemCount.setTestItemWrongCount(1);

			}
			else if (ssoUserTestItem.getCompleteType() == Dictionary.SSO_USER_TEST_ITEM_CORRECT) {
				ssoUserTestItemCount.setTestItemCorrectCount(1);
				ssoUserTestItemCount.setTestItemWrongCount(0);
			}
			
			ssoUserTestItemCount.setTestItemCount(1);
			
			//新增加到错题本
			ssoUserTestItemCount.setIsWrongbookItem(Dictionary.SSO_USER_WRONGBOOK_ITEM);
			ssoUserTestItemCount.setWrongbookItemCreatetime(now);
			ssoUserTestItemCount.setIsWrongbookItemTested(Dictionary.SSO_USER_WRONGBOOK_ITEM_TESTED);
			ssoUserTestItemCount.setWrongbookItemTesttime(now);
			ssoUserTestItemCount.setIsWrongbookItemMastered(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
			
			ssoUserTestItemCount = this.getSsoUserTestItemCountDAO().save(ssoUserTestItemCount);
			
		}
		return ssoUserTestItem;
	}

}
