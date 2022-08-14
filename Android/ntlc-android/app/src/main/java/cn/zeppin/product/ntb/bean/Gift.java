package cn.zeppin.product.ntb.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * 开发人: geng
 * 创建时间: 17/12/25
 */

public class Gift implements Serializable {
    private List<Coupon> redPacket;
    private List<Coupon> coupon;

    public List<Coupon> getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(List<Coupon> redPacket) {
        this.redPacket = redPacket;
    }

    public List<Coupon> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<Coupon> coupon) {
        this.coupon = coupon;
    }
}
