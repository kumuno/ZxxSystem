package com.service.impl;

import com.Pojo.shipAddress;
import com.Pojo.user;
import com.mapper.userMapper;
import org.apache.ibatis.annotations.Param;
import com.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
//import util.PageQueryUtil;
//import util.PageResult;

import java.util.List;

@Service("userServiceImpl")
public class userServiceImpl implements userService {
    @Lazy
    @Autowired
    private userMapper userMapper;

    //客户进行注册
    public int userEnroll(user user){
        int flag = 0;
        flag = userMapper.userEnroll(user);
        return flag;
    }

    //客户进行登录
    public user userLogin(String user_id,String user_password){

        user user = new user();
        user = userMapper.userLogin(user_id,user_password);
        return user;
    }

    //通过客户ID获取全部客户信息
    public user selectByUserId(String user_id){
        user user1 = userMapper.selectByUserId(user_id);
        return user1;
    }

    //通过客户姓名获取全部客户信息
    public user selectByUserName(String user_name){
        user user = userMapper.selectByUserName(user_name);
        return user;
    }

    //通过客户ID更新客户最基本信息（姓名，密码，联系方式，注册时间）
    public int updateByUserIdSelective(user user){
        int flag = 0;
        flag = userMapper.updateByUserIdSelective(user);
        return flag;
    }

    //分页查询所有客户
//    public PageResult findUserList(PageQueryUtil pageUtil){
//
//        List<user> allUser = userMapper.findUserList(pageUtil);
//        int total = userMapper.getTotalUsers();
//        PageResult pageResult = new PageResult(allUser, total, pageUtil.getLimit(), pageUtil.getPage());
//        return pageResult;
//    }

    //批量锁住客户账号或者解除客户账号
//    public int lockUserBatch(@Param("user_id") String[] user_id, @Param("user_locked_flag") int user_locked_flag){
//        int flag = 0;
//
//        flag = userMapper.lockUserBatch(user_id,user_locked_flag);
//        return flag;
//    }

    //批量注销客户账号
    public int deleteUserBatch(@Param("user_id") String[] user_id){
        int flag = 0;
        flag = userMapper.deleteUserBatch(user_id);
        return flag;
    }

    //客户添加收货地址
    public int addShipAddress(shipAddress shipAddress){
        int flag = 0;
        flag = userMapper.addShipAddress(shipAddress);
        return flag;
    }

    //客户查看自己的所有发货地址
    public List<shipAddress> findShipAddressListByUserId(@Param("user_id") String user_id){
        List<shipAddress> userAddress = userMapper.findShipAddressListByUserId(user_id);
        return userAddress;
    }

    //通过地址ID更新客户发货地址（ 地区信息（省-市-区），详细地址信息，联系人，联系电话，默认地址）
    public int updateShipAddressSelective(shipAddress shipAddress){
        int flag = 0;
        flag = userMapper.updateShipAddressSelective(shipAddress);
        return flag;
    }

    //客户删除收货地址
    public int deleteShipAddressById(@Param("address_id") String address_id){
        int flag = 0;
        flag = userMapper.deleteShipAddressById(address_id);
        return flag;
    }

    //客户删除收货地址
    public user findByOpenid(@Param("openid") String openid){
        user user = userMapper.selectByUserName(openid);
        return user;
    }

}
