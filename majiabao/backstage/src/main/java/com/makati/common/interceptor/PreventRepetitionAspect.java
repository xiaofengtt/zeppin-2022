//package com.makati.common.interceptor;
//
//import com.makati.common.exception.BusinessException;
//import com.makati.common.exception.ErrorResultEnums;
//import com.makati.common.lock.DistributedLock;
//import com.makati.common.util.lottery.ErrorCodeUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 防止重复提交操作AOP类
// *  jason
// */
//@Aspect
//@Component
//@EnableAspectJAutoProxy(proxyTargetClass=true)//spring自动切换JDK动态代理和CGLIB
//public class PreventRepetitionAspect {
//
//    @Autowired
//    private DistributedLock distributedLock;
//
//    private static Logger logger = LoggerFactory.getLogger(PreventRepetitionAspect.class);
//
//    private static final String USER_TOKEN = "user_token";
//    //避免和小李飞刀一样
//    private static final String LOCK_KEY =  "game_lock_key:";
//
//
//    @Pointcut("@annotation(com.makati.common.annotion.PreventRepeatSubmit)")
//    public void controllerNoReapetAspect()
//    {
//    }
//
//
//    /**
//     * around
//     * @throws Throwable
//     */
//    @Around("controllerNoReapetAspect()")
//    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable{
//        Object result = null;
//        Object[] args = joinPoint.getArgs();
//        for(int i = 0;i < args.length;i++){
//            if(args[i] != null && args[i]  instanceof HttpServletRequest){
//                HttpServletRequest request =  (HttpServletRequest) args[i];//被调用的方法需要加上HttpServletRequest request这个参数
//                result =  validation(joinPoint, request);
//            }
//        }
//        return result;
//    }
//
//
//    public Object validation(ProceedingJoinPoint joinPoint, HttpServletRequest request) throws Throwable {
//        String methodName = joinPoint.getSignature().getName();
//        String userToken=request.getParameter(USER_TOKEN);
//        if(StringUtils.isBlank(userToken)){
//            userToken=request.getParameter("userId");
//            if(StringUtils.isBlank(userToken)){
//                userToken=request.getParameter("user_id");
//            }
//        }
//        if(userToken == null){
//            throw new BusinessException(ErrorResultEnums.PARAMS_ERROR.getCode()+ "","同步锁参数缺失");
//        }
//
//        String lockKey = LOCK_KEY + userToken + methodName;
//        boolean lock = false;
//        try {
//            lock = distributedLock.tryGetDistributedLock(lockKey, userToken, 30);
//            logger.info("====>用户抢占锁  : lockKey: {},抢占锁的状态 :{}", lockKey, lock);
//            if (lock) {
//                //加锁成功
//                Object funcResult = joinPoint.proceed();
//                return funcResult;
//            } else {
//                //锁已存在,不能重复提交
//                logger.error( " 用户 {} 调用 {} 获取分布式锁失败",userToken,methodName );
//                throw new BusinessException(ErrorCodeUtil.USER_REPEAT_SUBMIT,"请求过于频繁，请稍后尝试");
//            }
//        }finally {
//            if(lock) {
//                distributedLock.releaseDistributedLock(lockKey, userToken);
//                logger.info("====>用户 {} 调用{} 释放锁 :lockKey {}",userToken,methodName, lockKey);
//            }
//        }
//    }
//}
