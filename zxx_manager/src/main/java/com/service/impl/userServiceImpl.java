package com.service.impl;

import com.Pojo.orderInformation;
import com.Pojo.user;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.userMapper;
import com.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("userServiceImpl")
public class userServiceImpl implements userService {
    @Lazy
    @Autowired
    private userMapper userMapper;

    //分页
    @Override
    public PageInfo<user> findAllUser(Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        List<user> list = userMapper.findAllUser();
        return new PageInfo<user>(list);
    }

    //多条件查询
    @Override
    public List<user> conditionalQueriesUser(String keyword1, String keyword2) {
        System.out.println("======================service业务层，显示所有用户======================");
        List<user> user = userMapper.conditionalQueriesUser(keyword1,keyword2);
        System.out.println(user);
        return user;
    }

    @Override
    public PageInfo<user> SearchUser(Integer pageNum, String user_id, String user_name) {
        PageHelper.startPage(pageNum,5);
        List<user> list = userMapper.SearchUser(user_id,user_name);
        return new PageInfo<user>(list);
    }

    //删除用户（修改用户状态）
    @Override
    public void deleteUser(String user_id) {
        System.out.println("======================service业务层，修改用户状态======================");
        userMapper.deleteUser(user_id);
    }

    //添加用户
    @Override
    public void addUser(user user) {
        System.out.println("===============IMPL层添加用户=============");
        String user_id = String.valueOf("U"+ (int) (Math.random() * 1000000000));
        user.setUser_id(user_id);
        Date date = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);//时间存储为字符串
        user.setUser_createTime(Timestamp.valueOf(str));
        userMapper.addUser(user);
    }

    //更新用户信息
    @Override
    public void updateUser(user user) {
        System.out.println("====================service业务层，用户更新信息========================");
        System.out.println(user);
        user user1 = new user();
        if (user.getUser_name()!=null){
            user1.setUser_name(user.getUser_name());
        }
        if (user.getUser_password()!=null){
            user1.setUser_password(user.getUser_password());
        }
        if (user.getUser_phone()!=null){
            user1.setUser_phone(user.getUser_phone());
        }
        if (user.getUser_Integral()>=0){
            user1.setUser_Integral(user.getUser_Integral());
        }
        user1.setUser_is_deleted(user.getUser_is_deleted());
        System.out.println(user1);
        userMapper.updateUser(user.getUser_id(),user1.getUser_name(),user1.getUser_password(),user1.getUser_phone(),
                user1.getUser_Integral(),user1.getUser_is_deleted());
    }

    @Override
    public user queryUserById(String user_id) {
        System.out.println("====================service业务层，通过ID查找用户========================");
        return userMapper.queryUserById(user_id);
    }
}
