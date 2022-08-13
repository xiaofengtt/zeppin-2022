package com.whaty.platform.entity.util;

import java.util.Comparator;
import java.util.Map;
/**
 * 用于合并尾考场，对HashMap<PeExamRoom, Integer>按value降序排列
 */

import com.whaty.platform.entity.bean.PeExamRoom;
public class EntryComparator implements Comparator<Map.Entry<PeExamRoom, Integer>> {
	  public EntryComparator() {
	  }

	  public int compare(Map.Entry<PeExamRoom, Integer> o1, Map.Entry<PeExamRoom, Integer> o2) {
	    return o2.getValue().intValue() - o1.getValue().intValue();
	  }
	}
