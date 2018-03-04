package com.imooc.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Horizon
 * Time: 下午6:28 2018/3/3
 * Description:
 */
public class HbaseUtils {

    private HBaseAdmin admin;
    private Configuration configuration;
    private static HbaseUtils instance = null;

    private HbaseUtils() {
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "hadoop00:2181");
        configuration.set("hbase.rootdir", "hdfs://hadoop00:9000/hbase");
        try {
            admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HbaseUtils getInstance() {
        if (instance == null) {
            instance = new HbaseUtils();
        }
        return instance;
    }

    public HTable getTable(String tableName) {
        HTable table = null;
        try {
            table = new HTable(configuration, tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    public void put(String tableName, String rowKey, String cf, String column, String value) {
        HTable table = getTable(tableName);
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(column), Bytes.toBytes(value));
        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Long> query(String tableName, String condition) {
        Map<String, Long> map = new HashMap<>();
        HTable table = getTable(tableName);
        String cf = "info";
        String column = "click_count";
        Scan scan = new Scan();
        Filter filter = new PrefixFilter(Bytes.toBytes(condition));
        scan.setFilter(filter);
        try {
            ResultScanner rs = table.getScanner(scan);
            for (Result result : rs) {
                String row = Bytes.toString(result.getRow());
                Long value = Bytes.toLong(result.getValue(Bytes.toBytes(cf), Bytes.toBytes(column)));
                map.put(row, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String, Long> map = HbaseUtils.getInstance().query("imooc_cource_clickcount", "20180303");
        Set<Map.Entry<String, Long>> entries = map.entrySet();
        Iterator<Map.Entry<String, Long>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> next = iterator.next();
            String key = next.getKey();
            Long value = next.getValue();
            System.out.println(key + ":" + value);
        }
    }

}
