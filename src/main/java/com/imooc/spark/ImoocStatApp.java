package com.imooc.spark;

import com.imooc.dao.CourseClickCountDAO;
import com.imooc.domain.CourseClickCount;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Horizon
 * Time: 下午9:51 2018/3/3
 * Description:
 */
@RestController
public class ImoocStatApp {

    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("112", "Spark SQL慕课网日志分析");
        map.put("128", "10小时入门大数据");
        map.put("145", "深度学习之神经网络核心原理与算法");
        map.put("146", "强大的Node.js在Web开发中的应用");
        map.put("130", "Vue+Django实战");
        map.put("131", "Web前端性能优化");
    }

    @Autowired
    CourseClickCountDAO courseClickCountDAO;

    @RequestMapping(value = "/course_clickcount_dynamic", method = RequestMethod.POST)
    public List<CourseClickCount> courseClickCount() throws IOException {
        List<CourseClickCount> list = courseClickCountDAO.query("imooc_cource_clickcount", "20180303");
        for (CourseClickCount model : list) {
            model.setName(map.get(model.getName().substring(9)));
        }
        return list;
    }

    @RequestMapping(value = "/echars", method = RequestMethod.GET)
    public ModelAndView echars() {
        return new ModelAndView("echars");
    }
}
