package org.linlinjava.litemall.wx.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.config.sign.AuthInterceptor;
import org.linlinjava.litemall.wx.service.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/wx/order")
@Validated
@Api(value = "订单",description = "订单接口")
@Slf4j
public class WxOrderController {
    private final Log logger = LogFactory.getLog(WxOrderController.class);

    @Autowired
    private WxOrderService wxOrderService;

    /**
     * 订单列表
     *
     * @param userId   用户ID
     * @param showType 订单信息
     * @param page     分页页数
     * @param limit     分页大小
     * @return 订单列表
     */
    @GetMapping("list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="showType" ,value = "查询类型(0，全部订单；1，待付款；2，待发货；3，待收货；4，待评价。)", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="page" ,value = "当前页", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="limit" ,value = "分页数", required = true, dataType = "Int")
    })
    @ApiOperation(value="订单列表", notes="订单列表(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object list(@LoginUser Integer userId,
                       @RequestParam(defaultValue = "0") Integer showType,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return wxOrderService.list(userId, showType, page, limit, sort, order);
    }

    /**
     * 订单详情
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int")
    })
    @ApiOperation(value="订单详情", notes="订单详情(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer orderId) {
        return wxOrderService.detail(userId, orderId);
    }

    /**
     * 提交订单
     *
     * @param userId 用户ID
     * @return 提交订单操作结果
     */
    @PostMapping("submit")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="cartId" ,value = "购物车id(0:清空购物车)", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="addressId" ,value = "收货地址id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="couponId" ,value = "优惠券id,传0自动选择", required = true, dataType = "Int")
    })
    @ApiOperation(value="提交订单", notes="提交订单(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object submit(@LoginUser Integer userId, Integer cartId, Integer addressId , Integer couponId, String message, Integer grouponRulesId, Integer grouponLinkId) {
        grouponRulesId=0;
        return wxOrderService.submit(userId,   cartId, addressId , couponId, message, grouponRulesId, grouponLinkId);
    }

    /**
     * 取消订单
     *
     * @param userId 用户ID
     * @return 取消订单操作结果
     */
    @PostMapping("cancel")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int")
    })
    @ApiOperation(value="取消订单", notes="取消订单(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object cancel(@LoginUser Integer userId,  Integer orderId) {
        return wxOrderService.cancel(userId, orderId);
    }

    /**
     * 微信付款订单的预支付会话标识
     *
     * @param userId 用户ID
     * @param orderId   订单信息，{ orderId：xxx }
     * @return 支付订单ID
     */
    @PostMapping("prepay")
    @ApiOperation(value="",hidden = true)
    public Object prepay(@LoginUser Integer userId,  Integer orderId, HttpServletRequest request) {
        return wxOrderService.prepay(userId, orderId, request);
    }


    /**
     * 支付宝付款订单的预支付会话标识
     *
     * @param userId 用户ID
     * @param orderId   订单信息，{ orderId：xxx }
     * @return 支付订单ID
     */
    @PostMapping("alipay")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="returnUrl" ,value = "返回地址", required = true, dataType = "String")
    })
    @ApiOperation(value="支付宝付款订单的预支付", notes="支付宝付款订单的预支付(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object alipay(@LoginUser Integer userId,  Integer orderId,String returnUrl, HttpServletRequest request) {
        return wxOrderService.alipay(userId, orderId, request,returnUrl);
    }

    @PostMapping("pay201")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int"),
    })
    @ApiOperation(value="更改订单状态", notes="更改订单状态(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object pay201(@LoginUser Integer userId,  Integer orderId) {
        return wxOrderService.pay201 (userId, orderId);
    }

    /**
     * 微信付款成功或失败回调接口
     * <p>
     *  TODO
     *  注意，这里pay-notify是示例地址，建议开发者应该设立一个隐蔽的回调地址
     *
     * @param request 请求内容
     * @param response 响应内容
     * @return 操作结果
     */
    @PostMapping("wxpay-notify")
    @ApiOperation(value="",hidden = true)
    public Object payNotify(HttpServletRequest request, HttpServletResponse response) {
        return wxOrderService.payNotify(request, response);
    }

    /**
     * 支付宝付款成功或失败回调接口
     * <p>
     *  TODO
     *  注意，这里pay-notify是示例地址，建议开发者应该设立一个隐蔽的回调地址
     *
     * @param request 请求内容
     * @param response 响应内容
     * @return 操作结果
     */
    @PostMapping("pay-notify")
    @ApiOperation(value="",hidden = true)
    public Object alipayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            return wxOrderService.alipayNotify(request, response);
        }catch (Exception e){
            log.error("支付宝付款成功或失败回调接口,失败");
            return "";
        }

    }

    /**
     * 订单申请退款
     *
     * @param userId 用户ID
     * @param orderId   订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    @PostMapping("refund")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="amount" ,value = "退款金额", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="reason" ,value = "退款原因", required = true, dataType = "String")
    })
    @ApiOperation(value="订单申请退款", notes="订单申请退款(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object refund(@LoginUser Integer userId,  Integer orderId,String amount,String reason) {
        return wxOrderService.refund(userId, orderId,amount,reason);
    }

    /**
     * 确认收货
     *
     * @param userId 用户ID
     * @param orderId   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("confirm")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int")
    })
    @ApiOperation(value="确认收货", notes="确认收货(登入验证)")
    public Object confirm(@LoginUser Integer userId,  Integer orderId) {
        return wxOrderService.confirm(userId, orderId);
    }

    /**
     * 删除订单
     *
     * @param userId 用户ID
     * @param orderId   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("delete")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int")
    })
    @ApiOperation(value="删除订单", notes="删除订单(登入验证)")
    public Object delete(@LoginUser Integer userId,  Integer orderId) {
        return wxOrderService.delete(userId, orderId);
    }

    /**
     * 待评价订单商品信息
     *
     * @param userId  用户ID
     * @param orderId 订单ID
     * @param goodsId 商品ID
     * @return 待评价订单商品信息
     */
    @GetMapping("goods")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderId" ,value = "订单id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="goodsId" ,value = "商品id", required = true, dataType = "Int")
    })
    @ApiOperation(value="待评价订单商品信息", notes="待评价订单商品信息(登入验证)")
    public Object goods(@LoginUser Integer userId,
                        @NotNull Integer orderId,
                        @NotNull Integer goodsId) {
        return wxOrderService.goods(userId, orderId, goodsId);
    }

    /**
     * 评价订单商品
     *
     * @param userId 用户ID
     * @param orderGoodsId   订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @PostMapping("comment")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="orderGoodsId" ,value = "商品订单id", required = true, dataType = "Int"),
            @ApiImplicitParam(paramType="query", name="content" ,value = "内容", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="hasPicture" ,value = "是否有图片", required = true, dataType = "Boolean"),
            @ApiImplicitParam(paramType="query", name="picUrls" ,value = "图片地址,号隔开", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="star" ,value = "星级（1-5）", required = true, dataType = "Int")
    })
    @ApiOperation(value="评价订单商品", notes="评价订单商品(登入验证)")
    public Object comment(@LoginUser Integer userId,  Integer orderGoodsId,String content,Boolean hasPicture,String picUrls,Integer star) {

        return wxOrderService.comment(userId, orderGoodsId,content,hasPicture,picUrls,star);
    }

}