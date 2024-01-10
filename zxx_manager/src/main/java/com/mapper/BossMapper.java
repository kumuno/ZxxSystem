package com.mapper;

import com.Pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BossMapper {

    //注册，插入数据
    void insertBoss(boss boss);

    //根据邮箱查询
    boss queryByEmail(String email);

    //根据id查询
    boss queryById(String boss_id);

    //登录
    boss selectBoss(@Param("boss_email")String boss_email,@Param("boss_password")String boss_password);




    //根据检验username和email是否存在
    boss selectBossByUsername(boss boss);

    //修改boss信息
    void updateBoss_password(boss boss);



////////////////////////////////
//    用户注册
    public int addBoss(boss boss);

    public boss loginById(@Param("boss_id") String boss_id, @Param("boss_password")String boss_password);

    public void deleteBossById(String boss_id);

    public void updateBoss(String boss_phone,String boss_id,String boss_password,String boss_name);



    public boss queryByPhone(String boss_phone);

}
