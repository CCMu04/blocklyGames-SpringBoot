package org.example.blocklygames.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.blocklygames.mapper.GameDiffMapper;
import org.example.blocklygames.mapper.GameMapper;
import org.example.blocklygames.mapper.MapInfoMapper;
import org.example.blocklygames.model.Dvo;
import org.example.blocklygames.model.Game;
import org.example.blocklygames.model.GameDiff;
import org.example.blocklygames.model.MapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class GameController {

    @Autowired
    GameMapper gameMapper;

    @RequestMapping("/getGame")
    public Dvo getGames() {
        Dvo dvo = new Dvo();

        System.out.println("发起获取游戏列表请求！");
        List<Game> games = gameMapper.selectList(null);
        dvo.setCode(1);
        dvo.setMsg("获取游戏成功！");
        dvo.setData(games);
        System.out.println("获取的游戏列表为：" + Arrays.toString(games.toArray()));

        return dvo;
    }

    @Autowired
    GameDiffMapper gameDiffMapper;

    @RequestMapping("/getGameInfo")
    public Dvo getGameInfo(@RequestParam("gameId") Integer gameId) {
        Dvo dvo = new Dvo();

        System.out.println("发起查询id为" + gameId + "的游戏难度请求！");
        QueryWrapper<GameDiff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", gameId);
        List<GameDiff> games = gameDiffMapper.selectList(queryWrapper);
        if (games.isEmpty()) {
            System.out.println("未找到id为" + gameId + "的游戏！");
            dvo.setMsg("未找到id为" + gameId + "的游戏！");
        } else {
            System.out.println("游戏难度信息为：" + Arrays.toString(games.toArray()));
            dvo.setCode(1);
            dvo.setMsg("获取游戏成功！");
            dvo.setData(games);
        }

        return dvo;
    }

    @Autowired
    MapInfoMapper mapInfoMapper;

    @RequestMapping("/getMapInfo")
    public Dvo getMapInfo(@RequestParam("mapDiff") Integer mapDiff) {
        Dvo dvo = new Dvo();

        System.out.println("发起查询diff为" + mapDiff + "的游戏地图请求！");
        QueryWrapper<MapInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("diff", mapDiff);
        List<MapInfo> maps = mapInfoMapper.selectList(queryWrapper);
        if (maps.isEmpty()) {
            System.out.println("未找到diff为" + mapDiff + "的地图！");
            dvo.setMsg("未找到diff为" + mapDiff + "的地图！");
        } else {
            System.out.println("地图信息为：" + Arrays.toString(maps.toArray()));
            dvo.setCode(1);
            dvo.setMsg("获取地图成功！");
            dvo.setData(maps);
        }

        return dvo;
    }
}
