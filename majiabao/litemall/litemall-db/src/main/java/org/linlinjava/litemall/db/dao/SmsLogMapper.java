package org.linlinjava.litemall.db.dao;


import org.apache.ibatis.annotations.Mapper;
import org.linlinjava.litemall.db.domain.SmsLog;

@Mapper
public interface SmsLogMapper {

    void add(SmsLog smsLog);

    Integer findList(SmsLog smsLog);
}
