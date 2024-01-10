package com.mapper;

import com.Pojo.InternalUser;
import com.Pojo.orderInformation;
import com.Pojo.user;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface userMapper {
    //分页
    public List<user> findAllUser();

    //搜索客户
    public List<user> SearchUser(String user_id, String user_name);

    //多条件查询
    public List<user> conditionalQueriesUser(String keyword1, String keyword2);

    //删除用户（修改用户状态）
    public void deleteUser(String user_id);

    //添加客户
    public void addUser(user user);

    //更新用户
    public void updateUser(String user_id,String user_name,String user_password,String user_phone,
                               int user_Integral,int user_is_deleted);

    //查找用户信息
    public user queryUserById(String user_id);
}
