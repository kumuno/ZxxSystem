package com.service.impl;

import com.Pojo.InternalUser;
import com.Pojo.commodity;
import com.Pojo.orderInformation;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.internalUserMapper;
import com.service.InternalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("InternalUserServiceImpl")
public class InternalUserServiceImpl implements InternalUserService {
    @Autowired
    @Lazy
    private internalUserMapper internalUserMapper;


    @Override
    public List<InternalUser> queryAllInternalUser() {
        System.out.println("======================service业务层，显示所有用户======================");
        return internalUserMapper.queryAllInternalUser();
    }

    //多条件查询
    @Override
    public List<InternalUser> conditionalQueries(String keyword1, String keyword2) {
        System.out.println("======================service业务层，显示所有用户======================");
        List<InternalUser> internalUsers = internalUserMapper.conditionalQueries(keyword1,keyword2);
        System.out.println(internalUsers);
        return internalUserMapper.conditionalQueries(keyword1,keyword2);
    }

    @Override
    public PageInfo<InternalUser> findAllInternalUser(Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        List<InternalUser> list = internalUserMapper.findAllInternalUser();
        System.out.println(list);
        return new PageInfo<InternalUser>(list);
    }

    @Override
    public PageInfo<InternalUser> SearchInternalUser(Integer pageNum, String InternalUser_id, String InternalUser_name) {
        PageHelper.startPage(pageNum,5);
        List<InternalUser> list = internalUserMapper.SearchInternalUser(InternalUser_id,InternalUser_name);
        return new PageInfo<InternalUser>(list);
    }

    //删除内部成员（修改内部成员状态）
    @Override
    public void deleteInternalUser(String InternalUser_id) {
        System.out.println("======================service业务层，修改内部成员状态======================");
        internalUserMapper.deleteInternalUser(InternalUser_id);
    }

    @Override
    public void addInternalUser(InternalUser internalUser) {
        System.out.println("===============IMPL层添加内部成员=============");
        System.out.println(internalUser);
        String InternalUser_id = String.valueOf("N"+ (int) (Math.random() * 1000000000));
        internalUser.setInternalUser_id(InternalUser_id);
        internalUser.setInternalUser_name(internalUser.getInternalUser_name());
        internalUser.setInternalUser_password(internalUser.getInternalUser_password());
        internalUser.setInternalUser_revenue(0);
        internalUser.setInternalUser_phone(internalUser.getInternalUser_phone());
        Date date = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);//时间存储为字符串
        System.out.println(str);
        internalUser.setInternalUser_createTime(Timestamp.valueOf(str));
        System.out.println(internalUser);
        internalUserMapper.addInternalUser(internalUser);
    }

    //更新客户信息
    @Override
    public void updateInternalUser(InternalUser internalUser) {
        System.out.println("====================service业务层，更新客户信息========================");
        internalUserMapper.updateInternalUser(internalUser.getInternalUser_id(),internalUser.getInternalUser_name(),internalUser.getInternalUser_password(),
                internalUser.getInternalUser_phone(),internalUser.getInternalUser_revenue());
    }

    @Override
    public InternalUser queryInternalUser(String InternalUser_id) {
        return internalUserMapper.queryInternalUser(InternalUser_id);
    }
}
