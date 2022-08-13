package cn.zeppin.access;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.access.Navigation;

public class Navigation {

	private int id;
	private int level;
	private String name;
	private String path;
	private String icon;
	private boolean iscurrent;
	private String scode;
	
	private List<Navigation> data = new ArrayList<Navigation>();

	public Navigation() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public List<Navigation> getData() {
		return data;
	}

	public void setData(List<Navigation> data) {
		this.data = data;
	}

	public boolean isIscurrent() {
		return iscurrent;
	}

	public void setIscurrent(boolean iscurrent) {
		this.iscurrent = iscurrent;
	}

	
	public String getScode() {
		return scode;
	}
	

	public void setScode(String scode) {
		this.scode = scode;
	}
	

}
