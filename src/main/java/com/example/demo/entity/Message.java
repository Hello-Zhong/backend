package com.example.demo.entity;

import lombok.*;

@Data
public class Message {
    private String action;
    private String socketType;
    private String chartName;
    private String value;
}
