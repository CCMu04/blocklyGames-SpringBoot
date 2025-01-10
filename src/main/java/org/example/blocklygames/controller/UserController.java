package org.example.blocklygames.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.example.blocklygames.mapper.GameStateMapper;
import org.example.blocklygames.model.Dvo;
import org.example.blocklygames.model.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    GameStateMapper gameStateMapper;

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
}
