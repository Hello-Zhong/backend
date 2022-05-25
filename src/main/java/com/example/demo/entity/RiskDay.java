package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "risk_info_day")
public class RiskDay {
    @TableId
    private int id;
    @TableField(value = "risk_level")
    private int risk_level;
    @TableField(value = "risk_num")
    private int risk_num;
    @TableField(value = "day")
    private int day;
}
