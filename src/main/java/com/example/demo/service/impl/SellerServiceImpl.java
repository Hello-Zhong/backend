package com.example.demo.service.impl;

import com.example.demo.entity.Seller;
import com.example.demo.mapper.SellerMapper;
import com.example.demo.service.SellerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

//    @Mapper会报空指针异常
    @Resource
    private SellerMapper sellerMapper;

    @Override
    public List<Seller> getSeller() {
        return sellerMapper.selectList(null);
    }
}
