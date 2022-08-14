package com.bbl.business.smslog.mapper;

import com.bbl.business.smslog.entity.SmsLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2019-10-25
 */
public interface SmsLogMapper extends BaseMapper<SmsLog> {
    void add(SmsLog smsLog);

    Integer findList(SmsLog smsLog);
}
