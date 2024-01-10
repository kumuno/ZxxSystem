package com.service;


import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import com.Pojo.user;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface userService {
    //分页
    public PageInfo<user> findAllUser(Integer pageNum);

    //多条件查询
    public List<user> conditionalQueriesUser(String keyword1, String keyword2);

    //搜索订单
    public PageInfo<user> SearchUser(Integer pageNum, String user_id, String user_name);

    //删除用户（修改用户状态）
    public void deleteUser(String user_id);

    //添加客户
    public void addUser(user user);

    //员工信息更新
    public void updateUser(user user);

    //查找用户信息
    public user queryUserById(String user_id);


}
