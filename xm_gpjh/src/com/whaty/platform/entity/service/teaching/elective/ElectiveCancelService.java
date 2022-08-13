package com.whaty.platform.entity.service.teaching.elective;

import java.util.List;

import com.whaty.platform.entity.exception.EntityException;

public interface ElectiveCancelService {

	public String delElective(List ids) throws EntityException;
}
