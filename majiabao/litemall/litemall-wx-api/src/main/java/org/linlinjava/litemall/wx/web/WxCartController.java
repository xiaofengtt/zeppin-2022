package org.linlinjava.litemall.wx.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.config.sign.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

import static org.linlinjava.litemall.wx.util.WxResponseCode.GOODS_NO_STOCK;
import static org.linlinjava.litemall.wx.util.WxResponseCode.GOODS_UNSHELVE;

/**
 * 用户购物车服务
 */
@RestController
@RequestMapping("/wx/cart")
@Validated
@Api(value = "购物车下单",description = "购物车下单接口")
public class WxCartController {
    private final Log logger = LogFactory.getLog(WxCartController.class);

    @Autowired
    private LitemallCartService cartService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallGoodsProductService productService;
    @Autowired
    private LitemallAddressService addressService;
    @Autowired
    private LitemallGrouponRulesService grouponRulesService;
    @Autowired
    private LitemallCouponService couponService;
    @Autowired
    private LitemallCouponUserService couponUserService;
    @Autowired
    private CouponVerifyService couponVerifyService;

    /**
     * 用户购物车信息
     *
     * @param userId 用户ID
     * @return 用户购物车信息
     */
    @GetMapping("index")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int")
    })
    @ApiOperation(value="查询购物车", notes="查询购物车(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object index(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        List<LitemallCart> cartList = cartService.queryByUid(userId);
        Integer goodsCount = 0;
        BigDecimal goodsAmount = new BigDecimal(0.00);
        Integer checkedGoodsCount = 0;
        BigDecimal checkedGoodsAmount = new BigDecimal(0.00);
        for (LitemallCart cart : cartList) {
            goodsCount += cart.getNumber();
            goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            if (cart.getChecked()) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("goodsCount", goodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);

        Map<String, Object> result = new HashMap<>();
        result.put("cartList", cartList);
        result.put("cartTotal", cartTotal);

        return ResponseUtil.ok(result);
    }

    /**
     * 加入商品到购物车
     * <p>
     * 如果已经存在购物车货品，则增加数量；
     * 否则添加新的购物车货品项。
     *
     * @param userId 用户ID
     * @return 加入购物车操作结果
     */
    @PostMapping("add")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="goodsId" ,value = "商品id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="number" ,value = "数量", required = true, dataType = "int")
    })
    @ApiOperation(value="加入购物车", notes="加入购物车(登入验证),成功后返回购物车总数量")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object add(@LoginUser Integer userId, Integer goodsId, Integer number) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        LitemallCart cart=new LitemallCart();

        if (goodsId == null||number==null) {
            return ResponseUtil.badArgument();
        }
//        if (cart == null) {
//            return ResponseUtil.badArgument();
//        }

//        Integer productId = cart.getProductId();
//        Integer number = cart.getNumber().intValue();
//        Integer goodsId = cart.getGoodsId();
        Integer productId = 71;

        if (!ObjectUtils.allNotNull(productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0){
            return ResponseUtil.badArgument();
        }

        //判断商品是否可以购买
        LitemallGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        LitemallGoodsProduct product = productService.findById(productId);
        //判断购物车中是否存在此规格商品
        LitemallCart existCart = cartService.queryExist(goodsId, productId, userId);
        if (existCart == null) {
            //取得规格的信息,判断规格库存
            if (product == null || number > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }

            cart.setId(null);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName((goods.getName()));
            cart.setPicUrl(goods.getPicUrl());
            cart.setPrice(goods.getRetailPrice());
            cart.setSpecifications(product.getSpecifications());
            cart.setUserId(userId);
            cart.setChecked(true);


            cart.setNumber (number.shortValue ());
            cart.setProductId(71);
            cart.setGoodsId(goods.getId());
            cartService.add(cart);
        } else {
            //取得规格的信息,判断规格库存
            int num = existCart.getNumber() + number;
            if (num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if (cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return goodscount(userId);
    }

    /**
     * 立即购买
     * <p>
     * 和add方法的区别在于：
     * 1. 如果购物车内已经存在购物车货品，前者的逻辑是数量添加，这里的逻辑是数量覆盖
     * 2. 添加成功以后，前者的逻辑是返回当前购物车商品数量，这里的逻辑是返回对应购物车项的ID
     *
     * @param userId 用户ID
     * @return 立即购买操作结果
     */
    @PostMapping("fastadd")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="goodsId" ,value = "商品id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="number" ,value = "数量", required = true, dataType = "int")
    })
    @ApiOperation(value="立即购买", notes="立即购买(登入验证),成功后返回购物车id,在可调用订单接口的submit方法")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object fastadd(@LoginUser Integer userId,Integer goodsId,Integer number) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (goodsId == null||number==null) {
            return ResponseUtil.badArgument();
        }

        LitemallCart cart=new LitemallCart();
        if (!ObjectUtils.allNotNull( number, goodsId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0){
            return ResponseUtil.badArgument();
        }


        //判断商品是否可以购买
        LitemallGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        LitemallGoodsProduct product = productService.findById(71);
        //判断购物车中是否存在此规格商品
        LitemallCart existCart = cartService.queryExist(goodsId, 71, userId);
        if (existCart == null) {
            //取得规格的信息,判断规格库存
            if (product == null || number > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }

            cart.setId(null);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName((goods.getName()));
            cart.setPicUrl(goods.getPicUrl());
//            cart.setPrice(product.getPrice());
            cart.setPrice(goods.getRetailPrice());

            cart.setSpecifications(product.getSpecifications());
            cart.setUserId(userId);
            cart.setChecked(true);
            cart.setNumber (number.shortValue ());


            cart.setProductId(71);
            cart.setGoodsId(goods.getId());
            cartService.add(cart);
        } else {
            //取得规格的信息,判断规格库存
            int num = number;
            if (num > product.getNumber()) {
                return ResponseUtil.fail(GOODS_NO_STOCK, "库存不足");
            }
            existCart.setNumber((short) num);
            if (cartService.updateById(existCart) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        return ResponseUtil.ok(existCart != null ? existCart.getId() : cart.getId());
    }

    /**
     * 修改购物车商品货品数量
     *
     * @param userId 用户ID
     * @return 修改结果
     */
    @PostMapping("update")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="goodsId" ,value = "商品id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="number" ,value = "数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="cartId" ,value = "购物车id", required = true, dataType = "int")
    })
    @ApiOperation(value="修改购物车", notes="修改购物车(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object update(@LoginUser Integer userId,Integer number ,Integer goodsId,Integer cartId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (number == null||goodsId==null||cartId==null) {
            return ResponseUtil.badArgument();
        }
//        Integer productId = cart.getProductId();
        Integer productId = 71;
        Integer id = cartId;
        if (!ObjectUtils.allNotNull(id, productId, number, goodsId)) {
            return ResponseUtil.badArgument();
        }
        if(number <= 0){
            return ResponseUtil.badArgument();
        }

        //判断是否存在该订单
        // 如果不存在，直接返回错误
        LitemallCart existCart = cartService.findById(id);
        if (existCart == null) {
            return ResponseUtil.badArgumentValue();
        }

        // 判断goodsId和productId是否与当前cart里的值一致
        if (!existCart.getGoodsId().equals(goodsId)) {
            return ResponseUtil.badArgumentValue();
        }
        if (!existCart.getProductId().equals(productId)) {
            return ResponseUtil.badArgumentValue();
        }

        //判断商品是否可以购买
        LitemallGoods goods = goodsService.findById(goodsId);
        if (goods == null || !goods.getIsOnSale()) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "商品已下架");
        }

        //取得规格的信息,判断规格库存
        LitemallGoodsProduct product = productService.findById(productId);
        if (product == null || product.getNumber() < number) {
            return ResponseUtil.fail(GOODS_UNSHELVE, "库存不足");
        }

        existCart.setNumber(number.shortValue());
        if (cartService.updateById(existCart) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    /**
     * 购物车商品货品勾选状态
     * <p>
     * 如果原来没有勾选，则设置勾选状态；如果商品已经勾选，则设置非勾选状态。
     *
     * @param userId 用户ID
     * @return 购物车信息
     */
    @PostMapping("checked")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="cartIds" ,value = "购物车id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name="isChecked" ,value = "是否勾选(1勾选，0不勾选)", required = true, dataType = "int")
    })
    @ApiOperation(value="购物车商品货品勾选状态", notes="购物车商品货品勾选状态(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object checked(@LoginUser Integer userId, String cartIds,Integer isChecked) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cartIds == null||isChecked==null) {
            return ResponseUtil.badArgument();
        }

//        List<Integer> productIds = JacksonUtil.parseIntegerList(body, "cartIds");
        ArrayList<Integer> productIds=new ArrayList<>();
        List<String> productId = Arrays.asList(cartIds.split(","));
        for(String str : productId) {
            int i = Integer.parseInt(str);
            productIds.add(i);
        }

        if (productIds == null) {
            return ResponseUtil.badArgument();
        }

        Integer checkValue = isChecked;
        if (checkValue == null) {
            return ResponseUtil.badArgument();
        }
        Boolean isCheckeds = (checkValue == 1);

        cartService.updateCheck(userId, productIds, isCheckeds);
        return index(userId);
    }

    /**
     * 购物车商品删除
     *
     * @param userId 用户ID
     * @return 购物车信息
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data: xxx
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("delete")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="cartIds" ,value = "购物车id", required = true, dataType = "String")
    })
    @ApiOperation(value="删除购物车", notes="删除购物车(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object delete(@LoginUser Integer userId,String cartIds) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        if (cartIds == null) {
            return ResponseUtil.badArgument();
        }

        ArrayList<Integer> productIds=new ArrayList<>();
        List<String> productId = Arrays.asList(cartIds.split(","));
        for(String str : productId) {
            int i = Integer.parseInt(str);
            productIds.add(i);
        }

        if (productIds == null || productIds.size() == 0) {
            return ResponseUtil.badArgument();
        }

        cartService.delete(productIds, userId);
        return index(userId);
    }

    /**
     * 购物车商品货品数量
     * <p>
     * 如果用户没有登录，则返回空数据。
     *
     * @param userId 用户ID
     * @return 购物车商品货品数量
     */
    @GetMapping("goodscount")
    @ApiOperation(value="", hidden = true)
    public Object goodscount(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.ok(0);
        }

        int goodsCount = 0;
        List<LitemallCart> cartList = cartService.queryByUid(userId);
        for (LitemallCart cart : cartList) {
            goodsCount += cart.getNumber();
        }

        return ResponseUtil.ok(goodsCount);
    }

    /**
     * 购物车下单
     *
     * @param userId    用户ID
     * @param cartId    购物车商品ID：
     *                  如果购物车商品ID是空，则下单当前用户所有购物车商品；
     *                  如果购物车商品ID非空，则只下单当前购物车商品。
     * @param addressId 收货地址ID：
     *                  如果收货地址ID是空，则查询当前用户的默认地址。
     * @param couponId  优惠券ID：
     *                  如果优惠券ID是空，则自动选择合适的优惠券。
     * @return 购物车操作结果
     */
    @GetMapping("checkout")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name="userId" ,value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="cartId" ,value = "购物车id,传0全部", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="addressId" ,value = "收货地址id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType="query", name="couponId" ,value = "优惠券id,传0自动选择", required = true, dataType = "int")
    })
    @ApiOperation(value="购物车下单", notes="购物车下单(登入验证)")
    @AuthInterceptor(needAuthTokenVerify = true)
    public Object checkout(@LoginUser Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        grouponRulesId=0;

        // 收货地址
        LitemallAddress checkedAddress = null;
        if (addressId == null || addressId.equals(0)) {
            checkedAddress = addressService.findDefault(userId);
            // 如果仍然没有地址，则是没有收货地址
            // 返回一个空的地址id=0，这样前端则会提醒添加地址
            if (checkedAddress == null) {
                checkedAddress = new LitemallAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }

        } else {
            checkedAddress = addressService.query(userId, addressId);
            // 如果null, 则报错
            if (checkedAddress == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        // 团购优惠
        BigDecimal grouponPrice = new BigDecimal(0.00);
        LitemallGrouponRules grouponRules = grouponRulesService.queryById(grouponRulesId);
        if (grouponRules != null) {
            grouponPrice = grouponRules.getDiscount();
        }

        // 商品价格
        List<LitemallCart> checkedGoodsList = null;
        if (cartId == null || cartId.equals(0)) {
            checkedGoodsList = cartService.queryByUidAndChecked(userId);
        } else {
            LitemallCart cart = cartService.findById(cartId);
            if (cart == null) {
                return ResponseUtil.badArgumentValue();
            }
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }
        BigDecimal checkedGoodsPrice = new BigDecimal(0.00);
        for (LitemallCart cart : checkedGoodsList) {
            //  只有当团购规格商品ID符合才进行团购优惠
            if (grouponRules != null && grouponRules.getGoodsId().equals(cart.getGoodsId())) {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().subtract(grouponPrice).multiply(new BigDecimal(cart.getNumber())));
            } else {
                checkedGoodsPrice = checkedGoodsPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())));
            }
        }

        // 计算优惠券可用情况
        BigDecimal tmpCouponPrice = new BigDecimal(0.00);
        Integer tmpCouponId = 0;
        int tmpCouponLength = 0;
        List<LitemallCouponUser> couponUserList = couponUserService.queryAll(userId);
        for(LitemallCouponUser couponUser : couponUserList){
            LitemallCoupon coupon = couponVerifyService.checkCoupon(userId, couponUser.getCouponId(), checkedGoodsPrice);
            if(coupon == null){
                continue;
            }

            tmpCouponLength++;
            if(tmpCouponPrice.compareTo(coupon.getDiscount()) == -1){
                tmpCouponPrice = coupon.getDiscount();
                tmpCouponId = coupon.getId();
            }
        }
        // 获取优惠券减免金额，优惠券可用数量
        int availableCouponLength = tmpCouponLength;
        BigDecimal couponPrice = new BigDecimal(0);
        // 这里存在三种情况
        // 1. 用户不想使用优惠券，则不处理
        // 2. 用户想自动使用优惠券，则选择合适优惠券
        // 3. 用户已选择优惠券，则测试优惠券是否合适
        if (couponId == null || couponId.equals(-1)){
            couponId = -1;
        }
        else if (couponId.equals(0)) {
            couponPrice = tmpCouponPrice;
            couponId = tmpCouponId;
        } else {
            LitemallCoupon coupon = couponVerifyService.checkCoupon(userId, couponId, checkedGoodsPrice);
            // 用户选择的优惠券有问题，则选择合适优惠券，否则使用用户选择的优惠券
            if(coupon == null){
                couponPrice = tmpCouponPrice;
                couponId = tmpCouponId;
            }
            else {
                couponPrice = coupon.getDiscount();
            }
        }

        // 根据订单商品总价计算运费，满88则免运费，否则8元；
        BigDecimal freightPrice = new BigDecimal(0.00);
        if (checkedGoodsPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
            freightPrice = SystemConfig.getFreight();
        }

        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = new BigDecimal(0.00);

        // 订单费用
        BigDecimal orderTotalPrice = checkedGoodsPrice.add(freightPrice).subtract(couponPrice).max(new BigDecimal(0.00));

        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        Map<String, Object> data = new HashMap<>();
        data.put("addressId", addressId);
        data.put("couponId", couponId);
        data.put("cartId", cartId);
        data.put("grouponRulesId", grouponRulesId);
//        data.put("grouponPrice", grouponPrice);
        data.put("checkedAddress", checkedAddress);
        data.put("availableCouponLength", availableCouponLength);
        data.put("goodsTotalPrice", checkedGoodsPrice);
        data.put("freightPrice", freightPrice);
        data.put("couponPrice", couponPrice);
        data.put("orderTotalPrice", orderTotalPrice);
        data.put("actualPrice", actualPrice);
        data.put("checkedGoodsList", checkedGoodsList);
        return ResponseUtil.ok(data);
    }
}