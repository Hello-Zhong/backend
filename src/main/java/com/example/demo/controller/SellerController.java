package com.example.demo.controller;


import com.example.demo.entity.Seller;
import com.example.demo.entity.User;
import com.example.demo.service.impl.SellerServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.utils.JWTUtils;
import com.example.demo.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/api")
@RestController
public class SellerController {

    @Autowired
    private SellerServiceImpl sellerService;

    @RequestMapping(value = "/seller",method = RequestMethod.GET)
    public List<Seller> getSeller(){
        return sellerService.getSeller();
    }

    @Autowired
    private WebSocketServer websocket;

    /**
     * 接收数据
     */
//    @RequestMapping(value="/receive")
//    public void receive(HttpServletRequest request){
//        // 使用websocket刷新指定页面
//        try {
//            websocket.sendMessage("1010");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(User user){
        HashMap<String, Object> result = new HashMap<>();
        User login = userService.getUser(user);
        if (login!= null) {
            result.put("msg","success");
            result.put("status","200");
            result.put("token", JWTUtils.getToken(user));
        }else {
            result.put("msg","error");
            result.put("status","400");
        }
        return result;
    }

}
