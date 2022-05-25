package com.example.demo;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.entity.Line;
import com.example.demo.entity.Pie;
import com.example.demo.entity.Radar;
import com.example.demo.entity.Seller;
import com.example.demo.service.impl.RiskCategoryServiceImpl;
import com.example.demo.service.impl.RiskRatioServiceImpl;
import com.example.demo.service.impl.SellerServiceImpl;
import com.example.demo.service.impl.TrendMonthServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class test {

//    @Autowired
//    private SellerMapper SellerMapper;
//
//    @Test
//    public void testSelect() {
//        System.out.println(("----- selectAll method test ------"));
//        QueryWrapper<Seller> wrapper = new QueryWrapper<>();
//        wrapper.eq("id",1);
//        List<Seller> sellerList = SellerMapper.selectList(wrapper);
////        Assertions.assertEquals(4, sellerList.size());
//        for(Seller seller:sellerList){
//            System.out.println(seller.toString());
//        }
//    }

    @Autowired
    private SellerServiceImpl SellerServiceImpl;
    @Autowired
    private TrendMonthServiceImpl trendMonthServiceimpl;
    @Autowired
    private RiskRatioServiceImpl riskRatioService;

    @Test
    public void testSeller() {
        System.out.println(("----- selectAll method test ------"));
        List<Seller> seller = SellerServiceImpl.getSeller();
        for(Seller s:seller){
            System.out.println(s);
        }
    }
    @Test
    public void testTrend() {
        System.out.println(("----- selectAll method test ------"));
        List<Line> TrendMonth = trendMonthServiceimpl.getTrendMonth();
        JSONObject obj = JSONUtil.createObj();
        JSONObject map_obj = JSONUtil.createObj();
        JSONObject common_obj = JSONUtil.createObj();
        JSONObject type_obj = JSONUtil.createObj();
        map_obj.set("title","风险趋势");
        map_obj.set("data",TrendMonth);
        obj.set("map",map_obj);
        common_obj.set("month", Lists.newArrayList("一月", "二月", "三月", "四月", "五月","六月","七月","八月","九月","十月","十一月","十二月"));
        obj.set("common",common_obj);
        type_obj.set("key","map");
        type_obj.set("text","风险趋势");
        obj.set("type",type_obj);
        System.out.println(JSONUtil.toJsonStr(obj));
    }

    @Test
    public void testRiskRatio() {
        System.out.println(("----- selectAll method test ------"));
        List<Pie> riskRatio = riskRatioService.getRiskRatio();
        System.out.println(riskRatio.toString());
    }

    @Autowired
    private RiskCategoryServiceImpl riskCategoryService;

    @Test
    public void testRiskCategoryService() {
        System.out.println("======================");
        Radar riskCategoryRadar = riskCategoryService.getRiskCategoryRadar();

        System.out.println(riskCategoryRadar.toString());
        JSONObject jsonObject = JSONUtil.parseObj(riskCategoryRadar);
        System.out.println(jsonObject);
        System.out.println(JSONUtil.toJsonStr(jsonObject));

    }
}
