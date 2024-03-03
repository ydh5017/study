package com.project.blog.mapper;

import com.project.blog.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryVO> getChildCategory(HashMap<String, Object> hMap) throws Exception;
}
