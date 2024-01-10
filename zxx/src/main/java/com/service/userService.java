package com.service;

import com.Pojo.shipAddress;
import com.Pojo.user;
import org.apache.ibatis.annotations.Param;
//import util.PageQueryUtil;
//import util.PageResult;

import java.util.List;

public interface userService {
    //客户进行注册
    public int userEnroll(user user);

    //客户进行登录
    public user userLogin(String user_id,String user_password);

    //通过客户ID获取全部客户信息
    public user selectByUserId(@Param("user_id") String user_id);

    //通过客户姓名获取全部客户信息
    public user selectByUserName(String user_name);

    //通过客户ID更新客户最基本信息（姓名，密码，联系方式，注册时间）
    public int updateByUserIdSelective(user user);

    //分页查询所有客户
//    public PageResult findUserList(PageQueryUtil pageUtil);

    //批量锁住客户账号或者解除客户账号
//    public int lockUserBatch(@Param("user_id") String[] user_id, @Param("user_locked_flag") int user_locked_flag);

    //批量注销客户账号
    public int deleteUserBatch(@Param("user_id") String[] user_id);

    //客户添加收货地址
    public int addShipAddress(shipAddress shipAddress);

    //分页查询所有客户
    public List<shipAddress> findShipAddressListByUserId(@Param("user_id") String user_id);

    //通过地址ID更新客户发货地址（ 地区信息（省-市-区），详细地址信息，联系人，联系电话，默认地址）
    public int updateShipAddressSelective(shipAddress shipAddress);

    //客户删除收货地址
    public int deleteShipAddressById(@Param("address_id") String address_id);

    //查找userid
    public user findByOpenid(@Param("openid") String openid);
}
