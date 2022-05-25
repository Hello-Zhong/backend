/*
Radar中的属性
 */
package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Radar {
    private List<Dimension> indicator;
    private List<Value> data;
}




