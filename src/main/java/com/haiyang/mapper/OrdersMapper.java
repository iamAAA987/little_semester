package com.haiyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haiyang.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
} 