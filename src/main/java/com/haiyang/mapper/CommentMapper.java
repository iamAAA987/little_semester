package com.haiyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haiyang.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
} 