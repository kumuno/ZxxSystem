package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//优惠劵
@Data
@AllArgsConstructor
@ToString
public class coupons {
    private String coupons_id;// 优惠劵id
    private String user_id;// 用户id
    private String coupons_type;// 优惠劵类型
    private String coupons_state;// 优惠券状态
    private double coupons_fund;// 优惠券金额
    private int coupons_is_deleted;// 注销标识字段(0-正常 1-已锁定)

    public coupons() {
    }
    
}
