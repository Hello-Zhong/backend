package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Pie {
    private String name;
    private List<PieData> children;
}
