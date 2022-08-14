package com.makati.business.user.service.impl;

import com.makati.business.user.entity.User;
import com.makati.business.user.mapper.UserMapper;
import com.makati.business.user.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code
 * @since 2019-09-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	
}
