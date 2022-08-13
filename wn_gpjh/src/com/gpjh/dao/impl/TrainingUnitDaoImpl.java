package com.gpjh.dao.impl;

import org.springframework.stereotype.Repository;

import com.gpjh.dao.TrainingUnitDao;
import com.gpjh.model.TrainingUnit;

@Repository("trainingUnitDao")
public class TrainingUnitDaoImpl extends BaseDaoImpl<TrainingUnit, String>
		implements TrainingUnitDao {

}
