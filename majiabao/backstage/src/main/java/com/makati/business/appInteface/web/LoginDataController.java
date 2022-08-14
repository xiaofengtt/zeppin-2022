package com.makati.business.appInteface.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.makati.business.logindata.entity.Logindata;
import com.makati.business.logindata.service.ILogindataService;
import com.makati.business.redenvelopes.entity.Redenvelopes;
import com.makati.business.redenvelopes.service.IRedenvelopesService;
import com.makati.common.response.Response;
import com.makati.common.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code
 * @since 2019-10-24
 */
@Controller
@RequestMapping("/logindata/")
@Api(value = "登入数据",description = "登入数据接口")
//@ApiIgnore
@Slf4j
public class LoginDataController {


    @Autowired
    private ILogindataService ILogindataService;

    @RequestMapping(value = "add",method =RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value="添加", notes="添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "channel", value = "渠道", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "platformtype", value = "平台", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "text", value = "json", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "isauditor", value = "是否审核账户 1是 0否", required = true, dataType = "Int")
    })
    public Response add(String channel,String platformtype,String text,Integer isauditor,HttpServletRequest request) {

        Logindata logindata=new Logindata();
        logindata.setPlatformtype(platformtype);
        logindata.setText(text);
        logindata.setUserid(channel);
        logindata.setIp(this.getRemoteHost (request));
        logindata.setIsauditor (isauditor);
        logindata.setCreatetime (DateUtils.formatDate (new Date (),"yyyy-MM-dd HH:mm:ss"));
        boolean insert=false;
        if(!logindata.getIp ().equals ("116.50.230.196")){
             insert = ILogindataService.insert(logindata);
        }

        return Response.successResponce(insert);

    }

    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }






}
