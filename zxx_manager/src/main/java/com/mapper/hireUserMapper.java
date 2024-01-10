package com.mapper;


import com.Pojo.boss;
import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.util.PagerModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface hireUserMapper extends BaseMapper<hireUser> {
    public List<hireUser> queryAllHireUser();

    //分页搜索后判断
    public List<hireUser> SearchHireUser(String hire_user_id, String hire_user_name);

    public List<hireUser> conditionalQueries(String keyword1, String keyword2);

    public void modifyHireUserStatus(String hire_user_id,String hire_user_is_deleted);

    public hireUser queryHireUser(String hire_user_id);
    //    用户注册
    public void addHireUserById(hireUser hireUser);

    //管理员分页查看所有员工信息
    public List<hireUser> findAllHireUser();

    public void updateHireUser(String hire_user_id,String hire_user_name,String hire_user_identity,String hire_user_password,String hire_user_phone,
                               String  hire_user_identitycard,double hire_user_revenue,double hire_user_proportion,int hire_user_tradednumber);

    public hireUser queryHireUserById(String boss_id);

    //    删除对应员工(修改员工状态)
    public void deleteHireUserById(String hireUser_id);
}
