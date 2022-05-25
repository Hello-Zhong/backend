package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "risk_info_month")
public class RiskMonth {
    @TableId
    private int id;
    @TableField(value = "risk_level")
    private int risk_level;
    @TableField(value = "risk_num")
    private int risk_num;
    @TableField(value = "month")
    private int month;
}
