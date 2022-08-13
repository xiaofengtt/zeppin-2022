package cn.zeppin.dao.imp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.ISsoUserTestItemCountDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.SsoUser;
import cn.zeppin.entity.SsoUserTestItemCount;
import cn.zeppin.entity.Subject;
import cn.zeppin.utility.Dictionary;

public class SsoUserTestItemCountDAO extends HibernateTemplateDAO<SsoUserTestItemCount, Integer>
		implements ISsoUserTestItemCountDAO {

	/**
	 * 通过唯一索引得到唯一的用户做题信息对象
	 * @param id
	 * @param itemId
	 * @param blankInx
	 * @return
	 */
	@Override
	public SsoUserTestItemCount getByKey(Integer userId, Integer itemId, Integer blankInx) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" from SsoUserTestItemCount sutic where 1=1");
		hql.append(" and sutic.ssoUser=").append(userId);
		hql.append(" and sutic.item=").append(itemId);
		hql.append(" and sutic.blankInx=").append(blankInx);
		return (SsoUserTestItemCount) this.getResultByHQL(hql.toString());
	}

	/**
	 * 获取用户错题本中的试题
	 * @param currentUser
	 * @param subject
	 * @return
	 */
	@Override
	public List<Map<String,Object>> searchAllWrongItem(SsoUser currentUser,	Subject subject) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		/**
		 * 跟出题接口类似，先取出错误的简单题，再取出错误的组合题，合并后再进行查询排序。
		 */
		sql.append(" select id, type, name, modeltype, is_group, inx, level, diffculty_level");
		sql.append(" ,default_score, source_type, source, parent, content_backup, analysis");
		sql.append(" ,test_item_count, test_item_correct_count, test_item_wrong_count");
		sql.append(" ,last_test_item_complete_type, is_wrongbook_item_tested, is_wrongbook_item_mastered");
		sql.append(" from (");
		//简单题
		sql.append(" (");
		sql.append(" select i.id, i.type, it.name, i.modeltype, i.is_group, ia.inx, i.level,i.diffculty_level");
		sql.append(" ,i.default_score, i.source_type, i.source, i.parent, i.content_backup, i.analysis");
		sql.append(" ,sutic.test_item_count, sutic.test_item_correct_count, sutic.test_item_wrong_count");
		sql.append(" ,sutic.last_test_item_complete_type, sutic.is_wrongbook_item_tested, sutic.is_wrongbook_item_mastered");
		sql.append(" ,sutic.wrongbook_item_createtime");
		sql.append(" from item i ");
		sql.append(" join item_type it on i.type = it.id and it.is_standard=").append(Dictionary.ITEM_ANSWER_TYPESTANDARD);
		sql.append(" join item_answer ia on i.id = ia.item");
		//错题肯定是做过的，所以不用左连接
		sql.append(" join sso_user_test_item_count sutic"); 
		sql.append("        on i.id = sutic.itemid");
		sql.append("        and ia.inx = sutic.blank_inx");
		sql.append("        and sutic.userid=").append(currentUser.getId());
		//标注为错题本的记录
		sql.append("        and sutic.is_wrongbook_item=").append(Dictionary.SSO_USER_WRONGBOOK_ITEM);
		//没移出错题本的记录
		sql.append("        and sutic.is_wrongbook_item_mastered=").append(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
		sql.append(" where i.subject=").append(subject.getId());
		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append(" and i.is_group = 0 and i.level=1");
		sql.append(" )");
		sql.append(" union all ");
		sql.append(" (");
		//组合题
		sql.append(" select i.id, i.type, it.name, i.modeltype, i.is_group, ia.inx, i.level,i.diffculty_level");
		sql.append(" ,i.default_score, i.source_type, i.source, i.parent, i.content_backup, i.analysis");
		sql.append(" ,level1item.test_item_count, level1item.test_item_correct_count, level1item.test_item_wrong_count");
		sql.append(" ,level1item.last_test_item_complete_type, level1item.is_wrongbook_item_tested, level1item.is_wrongbook_item_mastered");
		sql.append(" ,level1item.wrongbook_item_createtime");
		sql.append(" from item i");
		sql.append(" join item_type it on i.type = it.id");
		sql.append(" left join item_answer ia on i.id = ia.item");
		sql.append(" join ");
		sql.append("     ( select level2item.parent as id ");
		sql.append("            ,avg(test_item_count) as test_item_count");
		sql.append("            ,avg(test_item_correct_count) as test_item_correct_count");
		sql.append("            ,avg(test_item_wrong_count) as test_item_wrong_count");
		sql.append("            ,null as last_test_item_complete_type");
		sql.append("            ,null as is_wrongbook_item_tested");
		sql.append("            ,null as is_wrongbook_item_mastered");
		sql.append("            ,max(wrongbook_item_createtime) as wrongbook_item_createtime");
		sql.append("       from sso_user_test_item_count sutic, item level2item, item_type it");
		sql.append("       where sutic.itemid=level2item.id");
		sql.append("         and level2item.is_group = 1 and level2item.level = 2");
		sql.append("         and level2item.type=it.id and it.is_standard=").append(Dictionary.ITEM_ANSWER_TYPESTANDARD);
		sql.append("         and level2item.subject=").append(subject.getId());
		sql.append("         and level2item.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append("         and sutic.userid=").append(currentUser.getId());
		sql.append("         and sutic.is_wrongbook_item=").append(Dictionary.SSO_USER_WRONGBOOK_ITEM);
		sql.append("         and sutic.is_wrongbook_item_mastered=").append(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
		sql.append("       group by level2item.parent");
		sql.append("     )  as level1item");
		sql.append("     on level1item.id = i.id");
		sql.append(" where i.subject=").append(subject.getId());
		sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
		sql.append(" and i.is_group = 1 and i.level=1");
		sql.append(" )");
		sql.append(" ) as result");
		sql.append(" order by wrongbook_item_createtime desc");
		
		List<Object[]> rst = this.getBySQL(sql.toString());

		List<Map<String,Object>> result = new ArrayList<>();
		for (Object[] obj : rst) {
			Map<String, Object> itemMap = new HashMap<>();
			int i = 0;
			itemMap.put("id", obj[i++]);
			itemMap.put("type", obj[i++]);
			itemMap.put("typename", obj[i++]);
			itemMap.put("modeltype", obj[i++]);
			itemMap.put("isgroup", (Byte) obj[i++] > 0);
			itemMap.put("inx", obj[i++]);
			itemMap.put("level", obj[i++]);
			itemMap.put("diffcultyLevel", obj[i++]);
			itemMap.put("defaultScore", obj[i++]);
			itemMap.put("sourceType", obj[i++]);
			itemMap.put("source", obj[i++]);
			itemMap.put("parent", obj[i++]);
			itemMap.put("content", obj[i++]);
			itemMap.put("analysis", obj[i++]);
			itemMap.put("test_item_count", obj[i++]);
			itemMap.put("test_item_correct_count", obj[i++]);
			itemMap.put("test_item_wrong_count", obj[i++]);
			itemMap.put("last_test_item_complete_type", obj[i++]);
			itemMap.put("is_wrongbook_item_tested", obj[i++]);
			itemMap.put("is_wrongbook_item_mastered", obj[i++]);
			result.add(itemMap);

		}
		/**
		 * 将所有的组合题试题内容都加载出来, 添加到列表的最后
		 */
		List<Map<String,Object>> childrenItems = new ArrayList<>();
		for (Map<String, Object> parentItemMap : result) {
			if ((Boolean) parentItemMap.get("isgroup") && (Integer) parentItemMap.get("level") == 1) {
				sql.delete(0, sql.length());
				sql.append(" select i.id, i.type, it.name, i.modeltype, i.is_group, ia.inx, i.level,i.diffculty_level");
				sql.append(" ,i.default_score, i.source_type, i.source, i.parent, i.content_backup, i.analysis");
				sql.append(" ,sutic.test_item_count, sutic.test_item_correct_count, sutic.test_item_wrong_count");
				sql.append(" ,sutic.last_test_item_complete_type, sutic.is_wrongbook_item_tested, sutic.is_wrongbook_item_mastered");
				sql.append(" ,sutic.wrongbook_item_createtime");
				sql.append(" from item i ");
				sql.append(" join item_type it on i.type = it.id and it.is_standard=").append(Dictionary.ITEM_ANSWER_TYPESTANDARD);
				sql.append(" join item_answer ia on i.id = ia.item");
				//错题肯定是做过的，所以不用左连接
				sql.append(" join sso_user_test_item_count sutic"); 
				sql.append("        on i.id = sutic.itemid");
				sql.append("        and ia.inx = sutic.blank_inx");
				sql.append("        and sutic.userid=").append(currentUser.getId());
				//标注为错题本的记录
				sql.append("        and sutic.is_wrongbook_item=").append(Dictionary.SSO_USER_WRONGBOOK_ITEM);
				//没移出错题本的记录
				sql.append("        and sutic.is_wrongbook_item_mastered=").append(Dictionary.SSO_USER_WRONGBOOK_ITEM_NOTMASTER);
				sql.append(" where i.subject=").append(subject.getId());
				sql.append(" and i.status=").append(Dictionary.ITEM_STATUS_RELEASE);
				sql.append(" and i.is_group = 1 and i.level = 2");
				sql.append(" and i.parent=").append(parentItemMap.get("id").toString());
				sql.append(" order by i.id");
				
				List<Object[]> childObjs = this.getBySQL(sql.toString());
				for (Object[] childObj : childObjs) {
					Map<String, Object> childItemMap = new HashMap<>();
					int i = 0;
					childItemMap.put("id", childObj[i++]);
					childItemMap.put("type", childObj[i++]);
					childItemMap.put("typename", childObj[i++]);
					childItemMap.put("modeltype", childObj[i++]);
					childItemMap.put("isgroup", childObj[i++]);
					childItemMap.put("inx", childObj[i++]);
					childItemMap.put("level", childObj[i++]);
					childItemMap.put("diffcultyLevel", childObj[i++]);
					childItemMap.put("defaultScore", childObj[i++]);
					childItemMap.put("sourceType", childObj[i++]);
					childItemMap.put("source", childObj[i++]);
					childItemMap.put("parent", childObj[i++]);
					childItemMap.put("content", childObj[i++]);
					childItemMap.put("analysis", childObj[i++]);
					childItemMap.put("test_item_count", childObj[i++]);
					childItemMap.put("test_item_correct_count", childObj[i++]);
					childItemMap.put("test_item_wrong_count", childObj[i++]);
					childItemMap.put("last_test_item_complete_type", childObj[i++]);
					childItemMap.put("is_wrongbook_item_tested", childObj[i++]);
					childItemMap.put("is_wrongbook_item_mastered", childObj[i++]);
					
					childrenItems.add(childItemMap);
				}
			}
		}
		
		result.addAll(childrenItems);
		
		return result;
	}

}
