package com.service.impl;

import com.Pojo.InternalUser;
import com.Pojo.hireUser;
import com.Pojo.orderInformation;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.hireUserMapper;
import com.service.hireUserService;
import com.util.PagerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("hireUserServiceImpl")
public class hireUserServiceImpl implements hireUserService {
    @Lazy
    @Autowired
    private hireUserMapper hireUserMapper;

    @Override
    public List<hireUser> queryAllHireUser() {
        System.out.println("======================service业务层，显示所有用户======================");
        return hireUserMapper.queryAllHireUser();
    }

    //多条件查询
    @Override
    public List<hireUser> conditionalQueriesHireUser(String keyword1, String keyword2) {
        System.out.println("======================service业务层，显示所有用户======================");
        List<hireUser> hireUsers = hireUserMapper.conditionalQueries(keyword1,keyword2);
        return hireUsers;
    }

    @Override
    public PageInfo<hireUser> SearchHireUser(Integer pageNum, String hireUser_id, String hire_user_name) {
        PageHelper.startPage(pageNum,5);
        List<hireUser> list = hireUserMapper.SearchHireUser(hireUser_id,hire_user_name);
        return new PageInfo<hireUser>(list);
    }

    //更新员工状态
    @Override
    public void modifyHireUserStatus(String hire_user_id, String hireUser_is_deleted) {
        hireUser hireUser = hireUserMapper.queryHireUser(hire_user_id);
        hireUser.setHire_user_id(hire_user_id);
        hireUserMapper.modifyHireUserStatus(hire_user_id,hireUser_is_deleted);
    }

//    查找对应员工
    @Override
    public hireUser queryHireUser(String hire_user_id) {
        return hireUserMapper.queryHireUser(hire_user_id);
    }

    @Override
    public void addHireUserById(hireUser hireUser) {
        System.out.println("===============IMPL层添加员工=============");
        String hire_user_id = String.valueOf("H"+ (int) (Math.random() * 1000000000));
        hireUser.setHire_user_id(hire_user_id);
        hireUser.setHire_user_name(hireUser.getHire_user_name());
        hireUser.setHire_user_password(hireUser.getHire_user_password());
        hireUser.setHire_user_identity(hireUser.getHire_user_identity());
        hireUser.setHire_user_phone(hireUser.getHire_user_phone());
        hireUser.setHire_user_identitycard(hireUser.getHire_user_identitycard());
        hireUser.setHire_user_revenue(0);
        hireUser.setHire_user_proportion(0);
        hireUser.setHire_user_tradednumber(0);
        Date date = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);//时间存储为字符串
        hireUser.setHire_user_createTime(Timestamp.valueOf(str));
        hireUserMapper.addHireUserById(hireUser);
    }

    //分页
    @Override
    public PageInfo<hireUser> findAllHireUser(Integer pageNum) {
        PageHelper.startPage(pageNum,5);
        List<hireUser> list = hireUserMapper.findAllHireUser();
        return new PageInfo<hireUser>(list);
    }


    @Override
    public void updateHireUser(hireUser hireUser) {
        System.out.println("====================service业务层，用户更新信息========================");
        hireUserMapper.updateHireUser(hireUser.getHire_user_id(),hireUser.getHire_user_name(),hireUser.getHire_user_identity(),
                                      hireUser.getHire_user_password(),hireUser.getHire_user_phone(),hireUser.getHire_user_identitycard(),
                hireUser.getHire_user_revenue(),hireUser.getHire_user_proportion(),hireUser.getHire_user_tradednumber());
    }

    //查找员工
    @Override
    public hireUser queryHireUserById(String hire_user_id) {
        return hireUserMapper.queryHireUserById(hire_user_id);
    }

    //修改员工状态
    @Override
    public void deleteHireUserById(String hireUser_id) {
        System.out.println("======================service业务层，修改员工状态======================");
        hireUserMapper.deleteHireUserById(hireUser_id);
    }


}
