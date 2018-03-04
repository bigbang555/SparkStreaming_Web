package com.imooc.dao;

import com.imooc.domain.CourseClickCount;
import com.imooc.util.HbaseUtils;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by Horizon
 * Time: 下午9:29 2018/3/3
 * Description:
 */
@Component
public class CourseClickCountDAO {

    public List<CourseClickCount> query(String tableName, String day) throws IOException {
        List<CourseClickCount> list = new ArrayList<>();
        Map<String, Long> map = HbaseUtils.getInstance().query(tableName, day);
        Set<Map.Entry<String, Long>> entries = map.entrySet();
        Iterator<Map.Entry<String, Long>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> next = iterator.next();
            String name = next.getKey();
            Long value = next.getValue();
            CourseClickCount model = new CourseClickCount();
            model.setName(name);
            model.setValue(value);
            list.add(model);
        }
        return list;
    }

    public static void main(String[] args) {
        CourseClickCountDAO dao = new CourseClickCountDAO();
        try {
            List<CourseClickCount> imooc_cource_clickcount = dao.query("imooc_cource_clickcount", "20180303");
            for (CourseClickCount x : imooc_cource_clickcount) {
                System.out.println(x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
