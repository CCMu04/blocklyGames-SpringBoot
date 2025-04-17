package org.example.blocklygames.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import org.example.blocklygames.mapper.GameStateMapper;
import org.example.blocklygames.mapper.UserInfoMapper;
import org.example.blocklygames.model.Dvo;
import org.example.blocklygames.model.GameState;
import org.example.blocklygames.model.UserDetail;
import org.example.blocklygames.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    GameStateMapper gameStateMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @RequestMapping("/getUserBlocks")
    public Dvo getUserBlocks(@RequestParam("diff") Integer diff, @RequestParam("uid") Integer uid) {
        Dvo dvo = new Dvo();

        System.out.println("get " + diff.toString() + " " + uid.toString());
        QueryWrapper<GameState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("diff", diff);
        queryWrapper.eq("uid", uid);

        List<GameState> gameStates = gameStateMapper.selectList(queryWrapper);
        dvo.setCode(1);
        dvo.setMsg("获取用户储存的blocks成功！");
        dvo.setData(gameStates);

        return dvo;
    }

    @RequestMapping("/setUserBlocks")
    public Dvo setUserBlocks(@RequestBody GameState gameState) {
        Dvo dvo = new Dvo();

        System.out.println("set " + gameState);
        QueryWrapper<GameState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("diff", gameState.getDiff());
        queryWrapper.eq("uid", gameState.getUid());

        List<GameState> gameStates = gameStateMapper.selectList(queryWrapper);
        if (!gameStates.isEmpty()) {
            UpdateWrapper<GameState> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("diff", gameState.getDiff());
            updateWrapper.eq("uid", gameState.getUid());
            updateWrapper.set("id", gameState.getId());
            updateWrapper.set("status", gameState.getStatus());
            gameStateMapper.update(gameState, updateWrapper);

            dvo.setCode(1);
            dvo.setMsg("更新数据成功！");
        } else {
            gameStateMapper.insert(gameState);
            dvo.setCode(1);
            dvo.setMsg("插入数据成功！");
        }

        return dvo;
    }

    @RequestMapping("getUserInfo")
    public Dvo getUserInfo(@RequestParam("uid") Integer uid) {
        Dvo dvo = new Dvo();

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);

        List<UserInfo> userInfoList = userInfoMapper.selectList(queryWrapper);
        if (!userInfoList.isEmpty()) {
            dvo.setCode(1);
            dvo.setMsg("获取用户信息成功！");
            dvo.setData(userInfoList);
        } else {
            dvo.setCode(0);
            dvo.setMsg("未找到该用户！");
        }

        return dvo;
    }

    @RequestMapping("getAllUserInfo")
    public Dvo getAllUserInfo() {
        Dvo dvo = new Dvo();

        dvo.setCode(1);
        dvo.setMsg("获取全部用户信息成功！");

        String sql = "SELECT u.uid, u.username, ui.name, ui.sex, ui.phone, ui.birthday, ui.address, ui.type FROM user u LEFT JOIN user_info ui ON u.uid = ui.uid";
        List<Map<String, Object>> userDetailList = SqlRunner.db().selectList(sql, UserDetail.class);
        dvo.setData(userDetailList);

        return dvo;
    }

    @RequestMapping("updateUserInfo")
    public Dvo updateUserInfo(@RequestBody UserInfo userInfo) {
        Dvo dvo = new Dvo();

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", userInfo.getUid());
        userInfoMapper.update(userInfo, queryWrapper);

        dvo.setCode(1);
        dvo.setMsg("修改信息成功！");
        return dvo;
    }
}
