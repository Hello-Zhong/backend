/*
风险等级饼图实现类
 */
package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.mapper.RiskCategoryDayMapper;
import com.example.demo.service.RiskCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RiskCategoryServiceImpl implements RiskCategoryService {

    @Resource
    RiskCategoryDayMapper riskCategoryDayMapper;

    @Override
    public Radar getRiskCategoryRadar() {
        QueryWrapper<RiskCategoryDay> qwDay = new QueryWrapper<>();
        qwDay.select("id","risk_category", "risk_num","day").eq("day",1);
        List<RiskCategoryDay> riskCategoryDays = riskCategoryDayMapper.selectList(qwDay);
        qwDay.clear();
        qwDay.select("risk_num").eq("day",1).orderByDesc("risk_num").last("limit 1");
        int max_risk_num = riskCategoryDayMapper.selectOne(qwDay).getRisk_num();

        Radar radar = new Radar();
        radar.setIndicator(new ArrayList<>());
        radar.setData(new ArrayList<>());
        Value value = new Value();
        List<Integer> valueArr = new ArrayList<>();

        for(RiskCategoryDay r: riskCategoryDays){
            Dimension dimension = new Dimension();
            dimension.setName(r.getRisk_category());
            dimension.setMax(max_risk_num);
            radar.getIndicator().add(dimension);
            valueArr.add(r.getRisk_num());
        }

        value.setValue(valueArr);
        radar.getData().add(value);
        return radar;
    }
}
