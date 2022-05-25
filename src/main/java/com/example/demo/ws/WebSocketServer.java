package com.example.demo.ws;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.entity.Line;
import com.example.demo.entity.Pie;
import com.example.demo.entity.Seller;
import com.example.demo.service.SellerService;
import com.example.demo.service.impl.RiskCategoryServiceImpl;
import com.example.demo.service.impl.RiskRatioServiceImpl;
import com.example.demo.service.impl.SellerServiceImpl;
import com.example.demo.service.impl.TrendMonthServiceImpl;
import com.example.demo.utils.JWTUtils;
import com.example.demo.utils.JsonEncoder;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * websocket工具类
 */
@ServerEndpoint(value = "/websocket/{token}",encoders = JsonEncoder.class,configurator = GetHttpSessionConfigurator.class)
@Component
public class WebSocketServer {

    private static SellerServiceImpl sellerService;

    @Autowired
    public void setSellerService(SellerServiceImpl sellerService) {
        WebSocketServer.sellerService = sellerService;
    }

    private static TrendMonthServiceImpl trendMonthService;

    @Autowired
    public void trendMonthService(TrendMonthServiceImpl trendMonthService) {
        WebSocketServer.trendMonthService = trendMonthService;
    }

    private static RiskRatioServiceImpl riskRatioService;

    @Autowired
    public void setRiskRatioService(RiskRatioServiceImpl riskRatioService) {
        WebSocketServer.riskRatioService = riskRatioService;
    }

    //    Radar
    private static RiskCategoryServiceImpl riskCategoryService;

    @Autowired
    public void setRiskCategoryService(RiskCategoryServiceImpl riskCategoryService) {
        WebSocketServer.riskCategoryService = riskCategoryService;
    }

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
//    public void onOpen(Session session, @PathParam("username") String username) {
    public void onOpen(Session session, EndpointConfig config, @PathParam("token") String token) {
        //httpSession暂时没用到
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println(token);
//        token失效则报错
        JWTUtils.decode(token);
        sessionMap.put("demoUser", session);
        log.info("有新用户加入，username={}, 当前在线人数为：{}", "demoUser", sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            // {"username", "zhang", "username": "admin"}
            array.add(jsonObject);
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
//        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
//    public void onClose(Session session, @PathParam("username") String username) {
    public void onClose(Session session) {
        sessionMap.remove("demoUser");
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", "demoUser", sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     *
     * @param message 客户端发送过来的消息
     */


    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("服务端收到用户username={}的消息:{}", "demoUser", message);
        JSONObject obj = JSONUtil.parseObj(message);
        JSONArray jsonArray = new JSONArray();
        String action = obj.getStr("action");
        String chartName = obj.getStr("chartName");
        if (action.equals("getData")) {
            switch (chartName) {
                // 风险数量统计柱状图
                case ("seller"): {
//                    String 格式
//                    jsonArray.addAll(sellerService.getSeller());
//                    obj.set("data", jsonArray.toString());
//                    this.sendMessage(JSONUtil.toJsonStr(obj), session);

//                    Json格式
                    obj.set("data",sellerService.getSeller());
                    this.sendMessageJson(obj, session);
                    break;
                }
                // 风险趋势折线图
                case ("trend"): {
                    List<Line> TrendMonth = trendMonthService.getTrendMonth();
                    JSONObject value_obj = JSONUtil.createObj();
                    JSONObject map_obj = JSONUtil.createObj();
                    JSONObject common_obj = JSONUtil.createObj();
                    JSONObject type_obj = JSONUtil.createObj();
                    map_obj.set("title", "风险趋势");
                    map_obj.set("data", TrendMonth);
                    value_obj.set("map", map_obj);
                    common_obj.set("month", Lists.newArrayList("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"));
                    value_obj.set("common", common_obj);
                    type_obj.set("key", "map");
                    type_obj.set("text", "风险趋势");
                    value_obj.set("type", type_obj);
//                    System.out.println(JSONUtil.toJsonStr(value_obj));
//                    obj.set("data", value_obj.toString());
//                    this.sendMessage(JSONUtil.toJsonStr(obj), session);
                    obj.set("data",value_obj);
                    this.sendMessageJson(obj, session);
                    break;
                }
                // 风险占比饼图
                case ("hot"): {
//                    jsonArray.addAll(riskRatioService.getRiskRatio());
//                    obj.set("data", jsonArray.toString());
//                    this.sendMessage(JSONUtil.toJsonStr(obj), session);
                    obj.set("data",riskRatioService.getRiskRatio());
                    this.sendMessageJson(obj, session);
                    break;
                }
                // 风险类型分布雷达图
                case ("radar"): {
                    obj.set("data", riskCategoryService.getRiskCategoryRadar());
                    this.sendMessage(JSONUtil.toJsonStr(obj), session);
                    break;
                }
                default:
                    break;
            }
//            if(chartName.equals("seller")){
//                jsonArray.addAll(sellerService.getSeller());
//            }
        } else {
            obj.set("data", jsonArray.toString());
            this.sendMessage(JSONUtil.toJsonStr(obj), session);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessageJson(JSONObject message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendObject(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

//    /**
//     * 服务端发送消息给所有客户端
//     */
//    private void sendAllMessage(String message) {
//        try {
//            for (WebSocketSession session : sessionMap.values()) {
//                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
//                session.getBasicRemote().sendText(message);
//            }
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败", e);
//        }
//    }
}
