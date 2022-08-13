package com.whaty.platform.entity.apply;

public class ApplyType {
	private String typeCode;

	private String typeName;

	private String className;

	private ApplyTypeFlow applyTypeFlow;

	public ApplyTypeFlow getApplyTypeFlow() {
		return applyTypeFlow;
	}

	public void setApplyTypeFlow(ApplyTypeFlow applyTypeFlow) {
		this.applyTypeFlow = applyTypeFlow;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
