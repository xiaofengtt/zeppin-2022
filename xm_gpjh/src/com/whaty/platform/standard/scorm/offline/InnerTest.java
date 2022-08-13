package com.whaty.platform.standard.scorm.offline;

public class InnerTest{
	
	private String name;
	private String value;
	
	public InnerTest(){}
	public InnerTest(String name,String value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof InnerTest))
			return false;
		InnerTest o = (InnerTest)obj;
		return this.name != null && this.name.equals(o.getName()) && this.value != null && this.value.equals(o.getValue()); 

	}
	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + (this.name == null ? 0 : this.name.hashCode());
		result = 37 * result
				+ (this.value == null ? 0 : this.value.hashCode());
		return result;
	}
}
