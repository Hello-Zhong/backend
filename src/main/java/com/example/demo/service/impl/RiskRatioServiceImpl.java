/*
风险等级饼图实现类
 */
package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.mapper.RiskDayMapper;
import com.example.demo.mapper.RiskMonthMapper;
import com.example.demo.mapper.RiskYearMapper;
import com.example.demo.service.RiskRatioService;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskRatioServiceImpl implements RiskRatioService {

    @Resource
    RiskYearMapper riskYearMapper;

    @Resource
    RiskMonthMapper riskMonthMapper;

    @Resource
    RiskDayMapper riskDayMapper;

    @Override
    public List<Pie> getRiskRatio() {
        ArrayList<Pie> pies = new ArrayList<>();
        Pie pieDay = new Pie();
        pieDay.setName("本日");
        Pie pieMonth = new Pie();
        pieMonth.setName("本月");
        Pie pieYear = new Pie();
        pieYear.setName("本年");
        QueryWrapper<RiskDay> qwday = new QueryWrapper<>();
        QueryWrapper<RiskMonth> qwmon = new QueryWrapper<>();
        QueryWrapper<RiskYear> qwyear = new QueryWrapper<>();
        qwyear.select("id","risk_level", "risk_num","year").eq("year",2022);
        List<RiskYear> riskYears = riskYearMapper.selectList(qwyear);
        qwmon.select("id","risk_level", "risk_num","month").eq("month",5);
        List<RiskMonth> riskMonths = riskMonthMapper.selectList(qwmon);
        qwday.select("id","risk_level", "risk_num","day").eq("day",9);
        List<RiskDay> riskDays = riskDayMapper.selectList(qwday);

        pieYear.setChildren(new ArrayList<>());
        pieMonth.setChildren(new ArrayList<>());
        pieDay.setChildren(new ArrayList<>());

        for(RiskYear r: riskYears){
            PieData pieData = new PieData();
            if(r.getRisk_level()==1){
                pieData.setName("低风险");
            }
            else if (r.getRisk_level()==2){
                pieData.setName("中风险");
            }
            else if (r.getRisk_level()==3){
                pieData.setName("高风险");
            }
            pieData.setValue(r.getRisk_num());
            pieYear.getChildren().add(pieData);
        }

        for(RiskMonth r: riskMonths){
            PieData pieData = new PieData();
            if(r.getRisk_level()==1){
                pieData.setName("低风险");
            }
            else if (r.getRisk_level()==2){
                pieData.setName("中风险");
            }
            else if (r.getRisk_level()==3){
                pieData.setName("高风险");
            }
            pieData.setValue(r.getRisk_num());
            pieMonth.getChildren().add(pieData);
        }
        for(RiskDay r: riskDays){
            PieData pieData = new PieData();
            if(r.getRisk_level()==1){
                pieData.setName("低风险");
            }
            else if (r.getRisk_level()==2){
                pieData.setName("中风险");
            }
            else if (r.getRisk_level()==3){
                pieData.setName("高风险");
            }
            pieData.setValue(r.getRisk_num());
            pieDay.getChildren().add(pieData);
        }
        pies.add(pieDay);
        pies.add(pieMonth);
        pies.add(pieYear);
        return pies;
    }
}
