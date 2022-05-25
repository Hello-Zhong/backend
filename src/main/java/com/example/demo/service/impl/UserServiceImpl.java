package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Seller;
import com.example.demo.entity.User;
import com.example.demo.mapper.SellerMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.SellerService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

//    @Mapper会报空指针异常
    @Resource
    private UserMapper userMapper;

    @Override
    public User getUser(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
            .eq("username",user.getUsername())
            .eq("password",user.getPassword());
        return userMapper.selectOne(wrapper);
    }
}
