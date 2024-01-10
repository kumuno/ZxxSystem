package com.service;

import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.util.PagerModel;

import java.util.List;

public interface hireUserService {
    public List<hireUser> queryAllHireUser();

    public List<hireUser> conditionalQueriesHireUser(String keyword1, String keyword2);

    //员工搜索后分页
    public PageInfo<hireUser> SearchHireUser(Integer pageNum,String hireUser_id, String hire_user_name);

    public void modifyHireUserStatus(String hireUser_id,String hireUser_is_deleted);

    public hireUser queryHireUser(String hireUser_id);

    //    员工注册
    public void addHireUserById(hireUser hireUser);

    //管理员分页查看所有订单信息
    public PageInfo<hireUser> findAllHireUser(Integer pageNum);

    //员工信息更新
    public void updateHireUser(hireUser hireUser);

    public hireUser queryHireUserById(String hireUser_id);

    //    删除对应员工(修改员工状态)
    public void deleteHireUserById(String hireUser_id);
}
