package com.example.demo.utils;

import com.example.demo.entity.Message;
import com.example.demo.entity.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * .
 * Package Name:   com.yyj.util
 *
 * @author: YYJ
 * Date Time:      2021/5/24 15:03
 */
public class MessageUtils {

    /**
     * 封装响应的消息
     */
    public static String getMessage(String data, Message message) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setData(data);
        resultMessage.setMessage(message);
        ObjectMapper objectMapper = new ObjectMapper();
        String repStr = null;
        try {
            repStr = objectMapper.writeValueAsString(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return repStr;
    }




}
