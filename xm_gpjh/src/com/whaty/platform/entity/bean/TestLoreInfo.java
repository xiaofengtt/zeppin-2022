package com.whaty.platform.entity.bean;

/**
 * TestLoreInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TestLoreInfo extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private TestLoreInfoId id;
	private TestLoreDir testLoreDir;

	// Constructors

	/** default constructor */
	public TestLoreInfo() {
	}

	/** minimal constructor */
	public TestLoreInfo(TestLoreInfoId id) {
		this.id = id;
	}

	/** full constructor */
	public TestLoreInfo(TestLoreInfoId id, TestLoreDir testLoreDir) {
		this.id = id;
		this.testLoreDir = testLoreDir;
	}

	// Property accessors

	public TestLoreInfoId getId() {
		return this.id;
	}

	public void setId(TestLoreInfoId id) {
		this.id = id;
	}

	public TestLoreDir getTestLoreDir() {
		return this.testLoreDir;
	}

	public void setTestLoreDir(TestLoreDir testLoreDir) {
		this.testLoreDir = testLoreDir;
	}

}