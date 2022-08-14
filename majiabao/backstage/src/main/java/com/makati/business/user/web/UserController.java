package com.makati.business.user.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.user.entity.User;
import com.makati.business.user.service.IUserService;
import com.makati.common.exception.BusinessException;
import com.makati.common.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-09-06
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService IUserService;

    @RequestMapping("add")
    @ResponseBody
    public  HashMap<String,Object> userAdd(User user){

        boolean insert;
        try{
             insert = IUserService.insert(user);
        }catch (Exception e){
            throw new BusinessException("9999","注册失败,请稍后尝试");
        }


        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("status",insert? 1:0);

        return  map;

    }

    @RequestMapping("login")
    @ResponseBody
    public int userlogin(User user,String yzm,HttpServletRequest request, HttpSession session){
        EntityWrapper ew=new EntityWrapper();

        User user1 = IUserService.selectOne(ew.where("user={0} and pwd={1}", user.getUser(), user.getPwd()));

        String inputStr = yzm;
        String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
        if (random == null) {
            return 2;
        }
        if (!random.equals(inputStr)) {
            return 2;
        }

        if(user1!=null)
        request.getSession().setAttribute("USER", user1);
        return  user1==null? 0:1;

    }

    /**
     * 校验验证码
     */
    @RequestMapping(value = "/checkVerify",headers = "Accept=application/json")
    public boolean checkVerify(@RequestParam String yzm, HttpSession session) {
        try{
            //从session中获取随机数
            String inputStr = yzm;
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            }
            if (random.equals(inputStr)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    @RequestMapping("checkUser")
    @ResponseBody
    public int checkUser(User user){
        EntityWrapper ew=new EntityWrapper();

        User user1 = IUserService.selectOne(ew.where("user={0} ", user.getUser()));
//        boolean insert = IUserService.insert(user);
        return  user1==null? 1:0;

    }


}
