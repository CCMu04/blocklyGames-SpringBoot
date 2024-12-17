package org.example.blocklygames.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.blocklygames.mapper.UserInfoMapper;
import org.example.blocklygames.mapper.UserMapper;
import org.example.blocklygames.model.Dvo;
import org.example.blocklygames.model.RegisterInfo;
import org.example.blocklygames.model.User;
import org.example.blocklygames.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @RequestMapping("/login")
    public Dvo login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Dvo dvo = new Dvo();
        System.out.println("发起登录请求：" + username + " " + password);
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        List<User> list = userMapper.selectList(queryWrapper);
        if (list.size() == 1) {
            dvo.setCode(1);
            dvo.setMsg("登录成功！");

            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
            userInfoQueryWrapper.eq("uid", list.getFirst().getUid());
            List<UserInfo> userInfo = userInfoMapper.selectList(userInfoQueryWrapper);
            dvo.setData(userInfo);
        } else dvo.setMsg("请检查输入的账号或密码是否错误！");

        return dvo;
    }

    @RequestMapping("/register")
    public Dvo register(@RequestBody RegisterInfo registerInfo) {
        Dvo dvo = new Dvo();
        System.out.println("发起注册请求：" + registerInfo.toString());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", registerInfo.getUsername());
        List<User> list = userMapper.selectList(queryWrapper);
        if (list.isEmpty()) {
            registerInfo.setPassword(DigestUtils.md5DigestAsHex(registerInfo.getPassword().getBytes()));

            User user = new User();
            user.setUsername(registerInfo.getUsername());
            user.setPassword(registerInfo.getPassword());
            userMapper.insert(user);
            System.out.println("成功插入用户表：" + user);

            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username", registerInfo.getUsername());
            userQueryWrapper.eq("password", registerInfo.getPassword());
            user = userMapper.selectOne(userQueryWrapper);

            UserInfo userInfo = new UserInfo();
            userInfo.setUid(user.getUid());
            userInfo.setName(registerInfo.getName());
            userInfo.setSex(registerInfo.getSex());
            userInfo.setPhone(registerInfo.getPhone());
            userInfo.setBirthday(registerInfo.getBirthday());
            userInfo.setAddress(registerInfo.getAddress());
            userInfoMapper.insert(userInfo);
            System.out.println("成功插入用户信息表：" + userInfo);

            dvo.setCode(1);
            dvo.setMsg("注册成功！");
        } else dvo.setMsg("该账号已存在！");

        return dvo;
    }
}
