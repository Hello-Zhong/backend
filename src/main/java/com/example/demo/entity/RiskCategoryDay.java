package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "risk_cat_day")
public class RiskCategoryDay {
    @TableId
    private int id;
    @TableField(value = "risk_category")
    private String risk_category;
    @TableField(value = "risk_num")
    private int risk_num;
    @TableField(value = "day")
    private int day;
}
