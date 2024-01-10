package com.mapper;

import com.Pojo.InternalUser;
import com.Pojo.commodity;
import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface internalUserMapper {
    public List<InternalUser> queryAllInternalUser();

    public List<InternalUser> conditionalQueries(String keyword1, String keyword2);

    //分页
    public List<InternalUser> findAllInternalUser();

    //搜索订单
    public List<InternalUser> SearchInternalUser(String InternalUser_id, String InternalUser_name);

    //删除内部成员（修改内部成员状态）
    public void deleteInternalUser(String InternalUser_id);

    //添加内部成员
    public void addInternalUser(InternalUser internalUser);

    //更新内部成员信息
    public void updateInternalUser(String InternalUser_id,String InternalUser_name,String InternalUser_password
            ,String InternalUser_phone,double InternalUser_revenue);

    //查找对应成员
    public InternalUser queryInternalUser(String InternalUser_id);
}
