package org.example.blocklygames.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import org.example.blocklygames.mapper.ScoreMapper;
import org.example.blocklygames.model.Dvo;
import org.example.blocklygames.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class ScoreController {

    @RequestMapping("/getRank")
    public Dvo getRank() {
        Dvo dvo = new Dvo();

        System.out.println("发起查询榜单请求！");
        List<Map<String, Object>> list = SqlRunner.db().selectList("SELECT user_info.uid, user_info.name, sum(score.score) as score from score left join user_info on user_info.uid = score.uid group by score.uid order by score desc");
        dvo.setCode(1);
        dvo.setMsg("查询成功！");
        dvo.setData(list);
        System.out.println("查询结果为：" + Arrays.toString(list.toArray()));

        return dvo;
    }

    @Autowired
    ScoreMapper scoreMapper;

    @RequestMapping("/addScore")
    public Dvo addScore(@RequestBody Score score) {
        Dvo dvo = new Dvo();

        System.out.println("发起添加分数请求！");
        QueryWrapper<Score> wrapper = new QueryWrapper<>();
        wrapper.eq("id", score.getId());
        wrapper.eq("diff", score.getDiff());
        wrapper.eq("uid", score.getUid());

        List<Score> scoreList = scoreMapper.selectList(wrapper);
        if (!scoreList.isEmpty()) {
            if (scoreList.getFirst().getScore() < score.getScore()) {
                scoreMapper.update(score, wrapper);
                dvo.setCode(1);
                dvo.setMsg("修改分数成功！");
            } else {
                dvo.setCode(1);
                dvo.setMsg("未超过历史分数！");
            }
        } else {
            scoreMapper.insert(score);
            dvo.setCode(1);
            dvo.setMsg("插入分数成功！");
        }

        return dvo;
    }
}
