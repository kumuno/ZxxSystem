package com.service;

import com.Pojo.InternalUser;
import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface InternalUserService {
    public List<InternalUser> queryAllInternalUser();

    //多条件查询
    public List<InternalUser> conditionalQueries(String keyword1, String keyword2);

    //分页
    public PageInfo<InternalUser> findAllInternalUser(Integer pageNum);

    //搜索订单
    public PageInfo<InternalUser> SearchInternalUser(Integer pageNum, String InternalUser_id, String InternalUser_name);

    //删除内部成员（修改内部成员状态）
    public void deleteInternalUser(String InternalUser_id);

    //添加内部成员
    public void addInternalUser(InternalUser internalUser);

    //内部成员信息更新
    public void updateInternalUser(InternalUser internalUser);

    //查找对应内部成员
    public InternalUser queryInternalUser(String InternalUser_id);

}
