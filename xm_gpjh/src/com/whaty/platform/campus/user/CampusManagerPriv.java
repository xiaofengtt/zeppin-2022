package com.whaty.platform.campus.user;

public abstract class CampusManagerPriv {
	
	public String managerId;
	
	public int applyClass=1;//申请班级
	
	public int deleteClass=1;//删除班级
	
	public int updateClass=1;//修改班级
	
	public int getClass=1;//查询班级
	
	public int checkClass = 1;//审核班级
	
	public int applyClassMember=1;//申请加入班级
	
	public int deleteClassMember=1;//删除班级成员
	
	public int updateClassMember=1;//修改班级成员
	
	public int getClassMember=1;//查询班级成员
	
	public int checkClassMember = 1;//审核班级成员
	
	public int applyAssociation=1;//申请社团
	
	public int deleteAssociation=1;//删除社团
	
	public int updateAssociation=1;//修改社团
	
	public int getAssociation=1;//查询社团
	
	public int checkAssociation = 1;//审核社团
	
	public int applyAssociationMember=1;//申请加入社团
	
	public int deleteAssociationMember=1;//删除社团成员
	
	public int updateAssociationMember=1;//修改社团成员
	
	public int getAssociationMember=1;//查询社团成员
	
	public int checkAssociationMember = 1;//审核社团成员

	public int assignClassMemberToManager = 1;//指定或取消班级成员为管理员 
	
	public int assignAssociationMemberToManager = 1;//指定或取消社团成员为管理员 
	
	public int createClassForum = 1;//创建班级论坛 


	public CampusManagerPriv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public CampusManagerPriv getNormalPriv()
	{
		this.applyClass = 1;
		this.deleteClass = 0;
		this.checkClass = 0;
		this.getClass = 1;
		this.updateClass = 0;
		this.applyClassMember = 0;
		this.deleteClassMember = 0;
		this.checkClassMember = 0;
		this.getClassMember = 1;
		this.updateClassMember = 0;
		this.applyAssociation = 1;
		this.deleteAssociation = 0;
		this.checkAssociation = 0;
		this.getAssociation = 1;
		this.updateAssociation = 0;
		this.applyAssociationMember = 0;
		this.deleteAssociationMember = 0;
		this.checkAssociationMember = 0;
		this.getAssociationMember = 1;
		this.updateAssociationMember = 0;
		this.assignAssociationMemberToManager=0;
		this.assignClassMemberToManager=0;
		this.createClassForum = 0;
		return this;
	}
	public CampusManagerPriv getManagerPriv()
	{
		this.applyClass = 1;
		this.deleteClass = 0;
		this.checkClass = 0;
		this.getClass = 1;
		this.updateClass = 1;
		this.applyClassMember = 1;
		this.deleteClassMember = 1;
		this.checkClassMember = 1;
		this.getClassMember = 1;
		this.updateClassMember = 1;
		this.applyAssociation = 1;
		this.deleteAssociation = 0;
		this.getAssociation = 1;
		this.updateAssociation = 1;
		this.checkAssociation=0;
		this.applyAssociationMember = 1;
		this.deleteAssociationMember = 1;
		this.checkAssociationMember = 1;
		this.getAssociationMember = 1;
		this.updateAssociationMember = 1;
		this.assignAssociationMemberToManager=1;
		this.assignClassMemberToManager=1;
		this.createClassForum = 1;
		return this;
	}
	public CampusManagerPriv getSuperManagerPriv()
	{
		this.applyClass = 1;
		this.deleteClass = 1;
		this.checkClass = 1;
		this.getClass = 1;
		this.updateClass = 1;
		this.applyClassMember = 1;
		this.deleteClassMember = 1;
		this.checkClassMember = 1;
		this.getClassMember = 1;
		this.updateClassMember = 1;
		this.applyAssociation = 1;
		this.deleteAssociation = 1;
		this.getAssociation = 1;
		this.updateAssociation = 1;
		this.checkAssociation=1;
		this.applyAssociationMember = 1;
		this.deleteAssociationMember = 1;
		this.checkAssociationMember = 1;
		this.getAssociationMember = 1;
		this.updateAssociationMember = 1;
		this.assignAssociationMemberToManager=1;
		this.assignClassMemberToManager=1;
		this.createClassForum = 1;
		return this;
	}
}
