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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class GameController {
    @Autowired
    GameMapper gameMapper;
    @Autowired
    GameDiffMapper gameDiffMapper;
    @Autowired
    MapInfoMapper mapInfoMapper;

    @RequestMapping("/saveGame")
    public Dvo saveGame(@RequestBody Game gameInfo) {
        Dvo dvo = new Dvo();
        System.out.println("发起储存游戏章节请求：" + gameInfo.toString());
        int count = gameMapper.insert(gameInfo);
        if (count > 0) {
            dvo.setCode(1);
            dvo.setMsg("插入游戏章节信息成功");
        } else {
            dvo.setCode(0);
            dvo.setMsg("插入游戏章节信息失败");
        }
        return dvo;
    }

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

    @RequestMapping("/getGameById")
    public Dvo getGameById(@RequestParam("id") Integer id) {
        Dvo dvo = new Dvo();
        System.out.println("发起通过Id获取游戏章节信息的请求！");
        QueryWrapper<Game> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<Game> games = gameMapper.selectList(queryWrapper);
        if (games.isEmpty()) {
            System.out.println("未找到章节id为" + id + "的游戏！");
            dvo.setMsg("未找到章节id为" + id + "的游戏！");
        } else {
            System.out.println("游戏章节信息为：" + Arrays.toString(games.toArray()));
            dvo.setCode(1);
            dvo.setMsg("获取游戏章节成功！");
            dvo.setData(games);
        }
        return dvo;
    }

    @RequestMapping("/deleteGameDiffById")
    public Dvo deleteGameDiffById(@RequestParam("ids") Integer[] ids) {
        Dvo dvo = new Dvo();
        System.out.println("发起删除游戏关卡，游戏DIFF为：" + Arrays.toString(ids));
        QueryWrapper<GameDiff> queryWrapper = new QueryWrapper<>();
        if (ids.length > 0) {
            for (int i : ids) {
                queryWrapper.clear();
                queryWrapper.eq("diff", i);
                gameDiffMapper.delete(queryWrapper);
            }
            dvo.setCode(1);
            dvo.setMsg("删除游戏关卡成功！");
        } else {
            dvo.setMsg("无删除内容！");
            dvo.setCode(0);
        }
        return dvo;
    }

    @RequestMapping("/deleteMapInfoById")
    public Dvo deleteMapInfoById(@RequestParam("ids") Integer[] ids) {
        Dvo dvo = new Dvo();
        System.out.println("发起删除地图信息请求，地图ID为：" + Arrays.toString(ids));
        QueryWrapper<MapInfo> queryWrapper = new QueryWrapper<>();
        if (ids.length > 0) {
            for (int i : ids) {
                queryWrapper.clear();
                queryWrapper.eq("diff", i);
                mapInfoMapper.delete(queryWrapper);
            }
            dvo.setCode(1);
            dvo.setMsg("删除地图信息成功！");
        } else {
            dvo.setCode(0);
            dvo.setMsg("无删除内容");
        }
        return dvo;
    }

    @RequestMapping("/deleteGameById")
    public Dvo deleteGameById(@RequestParam("ids") Integer[] ids) {
        Dvo dvo = new Dvo();
        System.out.println("发起删除游戏章节请求，章节ID为：" + Arrays.toString(ids));
        QueryWrapper<Game> queryWrapper = new QueryWrapper<>();
        if (ids.length > 0) {
            for (int i : ids) {
                queryWrapper.clear();
                queryWrapper.eq("id", i);
                gameMapper.delete(queryWrapper);
            }
            dvo.setCode(1);
            dvo.setMsg("删除游戏章节成功！");
        } else {
            dvo.setCode(0);
            dvo.setMsg("无删除内容");
        }
        return dvo;
    }

    @RequestMapping("/getAllGameInfo")
    public Dvo getAllGameInfo() {
        Dvo dvo = new Dvo();
        System.out.println("发起获取游戏信息列表请求！");
        List<GameDiff> gameDiffs = gameDiffMapper.selectList(null);
        dvo.setCode(1);
        dvo.setMsg("获取游戏信息成功！");
        dvo.setData(gameDiffs);
        return dvo;
    }

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

    @RequestMapping("/getGameInfoByDiff")
    public Dvo getGameInfoByDiff(@RequestParam("diff") Integer diff) {
        Dvo dvo = new Dvo();
        QueryWrapper<GameDiff> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("diff", diff);
        List<GameDiff> games = gameDiffMapper.selectList(queryWrapper);
        if (games.isEmpty()) {
            System.out.println("未找到难度为" + diff + "的游戏！");
            dvo.setMsg("未找到难度为" + diff + "的游戏！");
        } else {
            System.out.println("游戏难度信息为：" + Arrays.toString(games.toArray()));
            dvo.setCode(1);
            dvo.setMsg("获取游戏成功！");
            dvo.setData(games);
        }
        return dvo;
    }

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

    @RequestMapping("/updateGameInfo")
    public Dvo updateGameInfo(@RequestBody Game game) {
        Dvo dvo = new Dvo();

        QueryWrapper<Game> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", game.getId());
        gameMapper.update(game, queryWrapper);

        dvo.setCode(1);
        dvo.setMsg("修改章节成功！");

        return dvo;
    }
}
