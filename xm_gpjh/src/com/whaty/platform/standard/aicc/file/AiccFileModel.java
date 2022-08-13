package com.whaty.platform.standard.aicc.file;

/**
 * �ýӿ�������Aicc�μ���Ҫ������ļ�
 * ����Mandatory��.AU,.CRS,.CST,.DES
 *     Optional��.ORT,.PRE,.CMP
 * Rule #1 - All files in the set must have the same base filename
 * Rule #2 - All files in the set must be located in the same directory
 * Rule #3 - All of the mandatory file types must be included with all required course data elements  and in the proper format 
 * Rule #4 - The structure represented must follow the correct usage requirements for course data elements
 * 
 * @author chenjian
 *
 */
public interface AiccFileModel extends java.io.Serializable{
	public String toStrData();
}
