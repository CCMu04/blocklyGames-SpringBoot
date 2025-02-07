package org.example.blocklygames.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.example.blocklygames.mapper.GameDiffMapper;
import org.example.blocklygames.mapper.MapInfoMapper;
import org.example.blocklygames.model.Dvo;
import org.example.blocklygames.model.GameDiff;
import org.example.blocklygames.model.MapGame;
import org.example.blocklygames.model.MapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class MapBuildController {
    @Autowired
    GameDiffMapper gameDiffMapper;
    @Autowired
    MapInfoMapper mapInfoMapper;

    @RequestMapping("/saveEditedMap")
    public Dvo saveEditedMap(@RequestBody MapGame mapGame) {
        Dvo dvo = new Dvo();
        System.out.println("***" + mapGame.toString());
        System.out.println("发起编辑id为" + mapGame.getDiff() + "地图的请求！");
        QueryWrapper<GameDiff> gameDiffQueryWrapper = new QueryWrapper<>();
        gameDiffQueryWrapper.eq("diff", mapGame.getDiff());

        List<GameDiff> gameDiffList = gameDiffMapper.selectList(gameDiffQueryWrapper);
        if (gameDiffList.isEmpty()) {
            gameDiffQueryWrapper.clear();
            GameDiff gameDiff = new GameDiff();
            gameDiff.setId(mapGame.getId());
            gameDiff.setInfo(mapGame.getInfo());
            gameDiff.setDisplay(0);
            gameDiffQueryWrapper.eq("id", gameDiff.getId());
            gameDiffQueryWrapper.eq("info", gameDiff.getInfo());
            gameDiffList = gameDiffMapper.selectList(gameDiffQueryWrapper);
            if (!gameDiffList.isEmpty()) {
                dvo.setCode(0);
                dvo.setMsg("添加失败，在该章节下已经有相同的INFO（关卡）");
            } else {
                gameDiffMapper.insert(gameDiff);
                System.out.println("插入信息：" + gameDiff);
                System.out.println("获取插入的游戏信息：" + gameDiff);
                gameDiff = gameDiffMapper.selectOne(gameDiffQueryWrapper);
                MapInfo mapInfo = new MapInfo();
                mapInfo.setDiff(gameDiff.getDiff());
                mapInfo.setMapInfo(mapGame.getMapInfo());
                mapInfo.setToolbox(mapGame.getToolbox());
                mapInfo.setNeedCoin(mapGame.getNeedCoin());
                mapInfo.setFirstDegree(mapGame.getFirstDegree());
                mapInfo.setMinStep(mapGame.getMinStep());
                mapInfo.setMinBlock(mapGame.getMinBlock());
                mapInfoMapper.insert(mapInfo);
                System.out.println("插入地图：" + mapInfo);
                dvo.setCode(1);
                dvo.setMsg("插入地图成功！");
            }

        } else {
            GameDiff gameDiff = new GameDiff();
            if (mapGame.getId() != null) gameDiff.setId(mapGame.getId());
            gameDiff.setInfo(mapGame.getInfo());
            gameDiff.setDiff(mapGame.getDiff());

            UpdateWrapper<GameDiff> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("diff", mapGame.getDiff());
            updateWrapper.set("id", mapGame.getId());
            updateWrapper.set("info", mapGame.getInfo());
            gameDiffMapper.update(gameDiff, updateWrapper);
            int diff = gameDiffList.getFirst().getDiff();
            MapInfo mapInfo = new MapInfo();
            mapInfo.setDiff(diff);
            mapInfo.setMapInfo(mapGame.getMapInfo());
            mapInfo.setToolbox(mapGame.getToolbox());
            mapInfo.setNeedCoin(mapGame.getNeedCoin());
            mapInfo.setFirstDegree(mapGame.getFirstDegree());
            mapInfo.setMinStep(mapGame.getMinStep());
            mapInfo.setMinBlock(mapGame.getMinBlock());
            QueryWrapper<MapInfo> mapInfoQueryWrapper = new QueryWrapper<>();
            mapInfoQueryWrapper.eq("diff", diff);
            mapInfoMapper.update(mapInfo, mapInfoQueryWrapper);
            System.out.println("修改信息为：" + gameDiff + mapInfo);
            dvo.setCode(1);
            dvo.setMsg("修改地图成功！");
        }
        return dvo;
    }
}
