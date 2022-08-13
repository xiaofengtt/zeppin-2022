package com.zixueku.listerner;

import java.util.LinkedList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.zixueku.adapter.KnowledgeAdapter;
import com.zixueku.entity.Knowledge;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-3-12 上午11:36:05
 */
public class KnowledgeItemClickListener implements OnItemClickListener {

	private KnowledgeAdapter knowledgeAdapter;

	public KnowledgeItemClickListener(KnowledgeAdapter knowledgeAdapter) {
		this.knowledgeAdapter = knowledgeAdapter;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 点击的item代表的元素
		Knowledge element = (Knowledge) knowledgeAdapter.getItem(position);
		// 树中的元素
		List<Knowledge> displayedKnowledgeData = knowledgeAdapter.getDisplayedKnowledgeData();
		// 点击没有子项的item直接返回
		if (!element.isHasChild()||element.getLevel()==2) {
			return;
		}
		if (element.isExpanded()) {
			// 非递归深度优先算法
			element.setExpanded(false);
			LinkedList<Knowledge> list = new LinkedList<Knowledge>();
			int sum = 0;   //统计点击的元素下有多少个子元素已经显示
			sum = element.getData().size();
			list.addAll(element.getData());
			Knowledge konwledge;
			while (!list.isEmpty()) {
				konwledge = list.removeFirst();
				if (!konwledge.isExpanded()) {
					continue;
				} else if (konwledge.isExpanded() && konwledge.getData() != null) {
					LinkedList<Knowledge> childs = konwledge.getData();
					sum += childs.size();
					list.addAll(0, childs);
				}
			}

			// 删除节点内部对应子节点数据，包括子节点的子节点...
			for (int i = 0; i < sum; i++) {
				displayedKnowledgeData.remove(position + 1);
			}
			knowledgeAdapter.notifyDataSetChanged();
		} else {
			element.setExpanded(true);
			// 从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
			int i = 1;// 注意这里的计数器放在for外面才能保证计数有效
			for (Knowledge e : element.getData()) {
				e.setExpanded(false);
				displayedKnowledgeData.add(position + i, e);
				i++;
			}
			knowledgeAdapter.notifyDataSetChanged();
		}

	}

}
