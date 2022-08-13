package cn.zeppin.access;

import java.util.*;

public class KnowledgeEx {
	private int id;
	private String name;
	private List<KnowledgeEx> childs = new LinkedList<KnowledgeEx>();

	public KnowledgeEx() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<KnowledgeEx> getChilds() {
		return childs;
	}

	public void setChilds(List<KnowledgeEx> childs) {
		this.childs = childs;
	}

}
