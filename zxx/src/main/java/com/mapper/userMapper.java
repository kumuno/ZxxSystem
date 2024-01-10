package com.mapper;

import com.Pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
//import util.PageQueryUtil;

import java.util.List;

@Mapper
@Repository
public interface userMapper {


    //客户进行注册
    public int userEnroll(user user);

    //客户进行登录
    public user userLogin(String user_id,String user_password);

    //通过客户ID获取全部客户信息
    public user  selectByUserId(String user_id);

    //通过客户姓名获取全部客户信息
    public user selectByUserName(String user_name);

    //通过客户ID更新客户最基本信息（姓名，密码，联系方式，注册时间）
    public int updateByUserIdSelective(user user);

    //分页查询所有客户
//    List<user> findUserList(PageQueryUtil pageUtil);

    //查询所有客户总数
    public int getTotalUsers();

    //批量锁住客户账号或者解除客户账号
//    public int lockUserBatch(@Param("user_id") String[] user_id, @Param("user_locked_flag") int user_locked_flag);

    //批量注销客户账号
    public int deleteUserBatch(@Param("user_id") String[] user_id);

    //客户添加收货地址
    public int addShipAddress(shipAddress shipAddress);

    //客户查看自己的所有发货地址
    public List<shipAddress> findShipAddressListByUserId(@Param("user_id") String user_id);

    //通过地址ID更新客户发货地址（ 地区信息（省-市-区），详细地址信息，联系人，联系电话，默认地址）
    public int updateShipAddressSelective(shipAddress shipAddress);

    //客户删除收货地址
    public int deleteShipAddressById(@Param("address_id") String address_id);

    //通过总订单ID获取该总订单的订单商品数量
    public int getOrderCommodityNumber();

    //获取该用户当前的积分
    public int getUserIntegral(@Param("user_id") String user_id);

    //更新用户当前积分
    public int updateUserIntegral(user user);
//
//    //通过总订单ID获取该总订单的订单商品的具体信息
//    public List<orderItem> getOrderItemByOrderId(@Param("order_id") String order_id);

}
