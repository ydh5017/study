package com.project.blog.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {

    void postLikeInc(int userSeq, int postSeq) throws Exception;

    void postLikeDec(int userSeq, int postSeq) throws Exception;

    boolean postLikeCheck(int userSeq, int postSeq) throws Exception;

    void commentLikeInc(int userSeq, int commentSeq) throws Exception;

    void commentLikeDec(int userSeq, int commentSeq) throws Exception;

    boolean commentLikeCheck(int userSeq, int commentSeq) throws Exception;
}
