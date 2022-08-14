package com.bbl.business.user.service.impl;

import com.bbl.business.user.entity.User;
import com.bbl.business.user.mapper.UserMapper;
import com.bbl.business.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author bobo
 * @since 2019-11-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
