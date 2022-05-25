package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Line;
import com.example.demo.entity.RiskMonth;
import com.example.demo.mapper.RiskMonthMapper;
import com.example.demo.service.TrendMonthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrendMonthServiceImpl implements TrendMonthService {

    @Resource
    RiskMonthMapper riskMonthMapper;

    @Override
    public List<Line> getTrendMonth() {
        QueryWrapper<RiskMonth> qw = new QueryWrapper<>();
        qw.select("id", "risk_level", "risk_num", "month").eq("area_name", "北京").orderByAsc("area_id","risk_level","month");
        List<RiskMonth> riskMonths = riskMonthMapper.selectList(qw);
        ArrayList<Line> lines = new ArrayList<>();
        Line low = new Line();
        Line mid = new Line();
        Line high = new Line();
        List<Integer> low_data = new ArrayList<>();
        List<Integer> mid_data = new ArrayList<>();
        List<Integer> high_data = new ArrayList<>();
        low.setName("低风险");
        mid.setName("中风险");
        high.setName("高风险");
        low.setData(low_data);
        mid.setData(mid_data);
        high.setData(high_data);
        for(RiskMonth tm: riskMonths){
            if(tm.getRisk_level()==1){
                low_data.add(tm.getRisk_num());
            }
            else if(tm.getRisk_level()==2){
                mid_data.add(tm.getRisk_num());
            }
            else if(tm.getRisk_level()==3){
                high_data.add(tm.getRisk_num());
            }
        }
        lines.add(low);
        lines.add(mid);
        lines.add(high);
        return lines;
    }
}
