package cn.zeppin.project.chinamobile.media.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.security.RoleSign;
import cn.zeppin.project.chinamobile.media.web.service.api.IUserService;
import cn.zeppin.project.chinamobile.media.web.vo.RoleCountVO;

/**
 * 用户控制器
 * 
 * @author Clark.R
 * @since 2016年3月29日 下午3:54:00
 **/
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * 
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
    	try {
            Subject subject = SecurityUtils.getSubject();
            // 已登陆则 跳到首页
            if (subject.isAuthenticated()) {
                return "redirect:/views";
            }
            if (result.hasErrors()) {
                model.addAttribute("error", "参数错误！");
                return "redirect:../views/login.jsp";
            }
            // 身份验证
            subject.login(new UsernamePasswordToken(user.getName(), user.getPassword()));
            // 验证成功
//            final User authUser = userService.getByName(user.getName());
//            /**
//             * 在Session中保存用户信息
//             */
//            request.getSession().setAttribute("currentUser", authUser);
            
        } catch (AuthenticationException e) {
            // 身份验证失败
            model.addAttribute("error", "用户名或密码错误 ！");
            return "redirect:../views/login.jsp";
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return "redirect:/views";
    }

    /**
     * 用户登出
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        // 登出操作
        Subject subject = SecurityUtils.getSubject();
        Session shiroSession = subject.getSession();  
        shiroSession.removeAttribute("currentUser");
        //有空测试一下有了上一条语句，用不用写下面这条
        session.removeAttribute("currentUser");
        subject.logout();
        return "redirect:../views/login.jsp";
    }

    /**
     * 基于角色 标识的权限控制案例
     */
    @RequestMapping(value = "/admin")
    @ResponseBody
    @RequiresRoles(value = RoleSign.ADMIN)
    public String admin() {
        return "拥有admin角色,能访问";
    }
    
    
    /**
     * 测试入库，实际开发时一定要把写库行为封装在一个service里面，切记
     */
    @RequestMapping(value = "/testadd", method = RequestMethod.GET)
    @ResponseBody
    public List<User> add() {
    	List<User> list = new ArrayList<User>();
    	User user = new User();
    	user.setCreator("11");
    	user.setCreatetime(new Timestamp((new Date()).getTime()));
    	user.setName("qinlonga");
    	user.setPassword("123456");
    	user.setEmail("qinlonag@zeppin.cn");
    	user.setStatus("normal");
    	user.setRole("editor");
    	user.setPhone("18586878889");
    	user = userService.insert(user);
    	
    	User user1 = new User();
    	user1.setCreator("11");
    	user1.setCreatetime(new Timestamp((new Date()).getTime()));
    	user1.setName("rongjingfeng");
    	user1.setPassword("123456");
    	user1.setEmail("rongjingfeng@zeppin.cn");
    	user1.setStatus("normal");
    	user1.setRole("admin");
    	user1.setPhone("18887868584");
    	user1 = userService.insert(user1);
    	
    	list.add(user);
    	list.add(user1);
        return list;
    }

    /**
     * 测试VO模板，从DAO的SQLQuery/HQLQuery中直接按模板生成
     */
    @RequestMapping(value = "/test/vo/get", method = RequestMethod.GET)
    @ResponseBody
    public List<Entity> getRoleCount(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
    	int pagenum = 1;
		int pagesize = 10;
		int offset = (pagenum - 1) * pagesize;
    	String sorts = "";
    	String role = "admin";
    	user.setRole(role);
    	
    	List<Entity> list = userService.getRoleCount(user, sorts, offset, pagesize, RoleCountVO.class);
    	
		return list;
    	
    }
    
//    /**
//     * 基于权限标识的权限控制案例
//     */
//    @RequestMapping(value = "/create")
//    @ResponseBody
//    @RequiresPermissions(value = PermissionSign.USER_CREATE)
//    public String create() {
//        return "拥有user:create权限,能访问";
//    }
}
