package com.cmos.chinamobile.media.control;

import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

/**
 * 
 * 
 */
public interface IControlService {
	/**
	 * Call WebService Unified Method
	 * 
	 * @param inputObject
	 * @return OutputObject
	 */
	OutputObject execute(InputObject inputObject);
}
