package cn.zeppin.access;

import java.util.LinkedList;
import java.util.List;


public class ItemEx {
	private int id;
	private int item;
	private String html;
	private int inx;
	private List<ItemEx> lstChild = new LinkedList<ItemEx>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public int getInx() {
		return inx;
	}
	public void setInx(int inx) {
		this.inx = inx;
	}
	public List<ItemEx> getLstChild() {
		return lstChild;
	}
	public void setLstChild(List<ItemEx> lstChild) {
		this.lstChild = lstChild;
	}	
	
}
