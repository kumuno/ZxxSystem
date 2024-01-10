package com.service.impl;

import com.Pojo.boss;
import com.mapper.BossMapper;
import com.service.bossService;
import com.util.MD5Utils;
import com.util.ResponseJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class bossServiceImpl implements bossService {
    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private RedisTemplate redisTemplate;

    @Lazy
    @Autowired
    private BossMapper bossMapper;

    //发邮箱者
    @Value("${spring.mail.username}")
    private String from;


    //随机生成验证码
    @Override
    public String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i=0;i<6;i++){
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    @Override
    public ResponseJSONResult sendMimeMail(String email) {

            //创建Redis简单string的操作类
            ValueOperations operations = redisTemplate.opsForValue();
            //邮箱信息
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            //你自己的邮箱
             mailMessage.setFrom(from);

            //发给谁
            mailMessage.setTo(email);

            //主题
            mailMessage.setSubject("Boss_email注册验证码");

            //生成随机数
            String code = randomCode();
            System.out.println("code = " + code );

            //内容
            mailMessage.setText("Boss_email注册的验证码是：" + code);

            //发送
            javaMailSender.send(mailMessage);

            //设置键值对  k-v = 邮箱:验证码  的有效时间
            operations.set(email,code,1, TimeUnit.MINUTES);

            return new ResponseJSONResult(200,"邮箱验证已发送");

    }



    @Override
    public ResponseJSONResult registered(String email,String code,String phone,String password) {
        //从redis获取刚刚缓存的验证码 60秒内的
        String Redis_code = (String)redisTemplate.opsForValue().get(email);
        System.out.println(Redis_code);
        if (Redis_code.equals(code)){

            //保存数据
            boss boss=new boss();
            Date date = new Date();//获取当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = sdf.format(date);//时间存储为字符串
            boss.setBoss_createTime(Timestamp.valueOf(str));
            String boss_id = String.valueOf("BS"+ (int) (Math.random() * 1000000000));
            boss.setBoss_id(boss_id);
            boss.setBoss_name("内部管理成员");
            boss.setBoss_email(email);
            boss.setBoss_phone(phone);
            boss.setBoss_password(MD5Utils.code(password));
            String img = "images/unAdd.jpg";
            boss.setBoss_image(img);

            boss boss1 = bossMapper.queryByEmail(email);
            if (boss1 == null){
                //将数据写入数据库
                bossMapper.insertBoss(boss);
                return new ResponseJSONResult(200,"注册成功！你的账号id为:"+boss_id);
            }else {
                return new ResponseJSONResult(201,"该邮箱已经被注册，请重新输入邮箱！");
            }
        }else{
            return new ResponseJSONResult(211,"验证码错误！");
        }
    }

    @Override
    public ResponseJSONResult loginIn(String email, String password,HttpSession session) {
        System.out.println("email:"+email);
        boss boss = bossMapper.queryByEmail(email);
        if (boss==null){
            return new ResponseJSONResult(212,"账号不存在！");
        }else{
            boss boss1=bossMapper.selectBoss(email,MD5Utils.code(password));
            if (boss1!=null){
                session.setAttribute("boss_id",boss.getBoss_id());
                session.setAttribute("boss_password", boss.getBoss_password());
                //session过期时间设置为7200秒 即两小时
                //session.setMaxInactiveInterval(60 * 60 * 2);
                return new ResponseJSONResult(200,"登录成功！");
            }else{
                return new ResponseJSONResult(211,"密码错误！");
            }
        }
    }

    @Override
    public boss queryById(String boss_id) {
        return bossMapper.queryById(boss_id);
    }


}
