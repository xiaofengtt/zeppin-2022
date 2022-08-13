package com.zeppin.dao.impl;

import org.springframework.stereotype.Repository;

import com.zeppin.dao.PsqProjectMapDao;
import com.zeppin.model.PsqProjectMap;

@Repository("psqprojectmapDao")
public class PsqProjectMapDaoImpl extends BaseDaoImpl<PsqProjectMap, Integer> implements PsqProjectMapDao{

}
