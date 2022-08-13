package cn.zeppin.utility;

import java.util.SortedMap;
import java.util.TreeMap;

public class DictionaryRank {

	public static TreeMap<Long, String> RankTitle = new TreeMap<Long, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -9154397337456424769L;
		{
			this.put(100L, "幼儿园小班");
			this.put(300L, "幼儿园大班");
			this.put(500L, "小学一年级");
			this.put(800L, "小学二年级");
			this.put(1200L, "小学三年级");
			this.put(1600L, "小学四年级");
			this.put(2100L, "小学五年级");
			this.put(3000L, "小学六年级");
			this.put(5000L, "初中一年级");
			this.put(7000L, "初中二年级");
			this.put(10000L, "初中三年级");
			this.put(15000L, "高中一年级");
			this.put(20000L, "高中二年级");
			this.put(30000L, "高中三年级");
			this.put(45000L, "大学一年级");
			this.put(60000L, "大学二年级");
			this.put(75000L, "大学三年级");
			this.put(100000L, "大学四年级");
			this.put(150000L, "学士");
			this.put(250000L, "硕士");
			this.put(500000L, "博士");
			this.put(1000000L, "博士后");
			this.put(20000000L, "院士");
		}
	};

	public static String getRankTitle(Long key) {
		SortedMap<Long, String> sort = DictionaryRank.RankTitle.tailMap(key);
		if (!sort.isEmpty()) {
			return sort.get(sort.firstKey());
		} else {
			return "院士";
		}
	}

}
