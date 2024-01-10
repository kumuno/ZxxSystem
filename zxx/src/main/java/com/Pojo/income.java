package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

//雇佣人员收益表
@Data
@AllArgsConstructor
@ToString
public class income {
    private String income_id;// 收益id
    private String hireUser_id;// 雇佣人员id
    private String order_id;// 总订单id
    private double income_amount;// 收益金额

    public income() {
    }
}
