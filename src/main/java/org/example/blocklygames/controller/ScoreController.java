package org.example.blocklygames.controller;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import org.example.blocklygames.model.Dvo;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}
