package com.project.blog.service.Impl;

import com.project.blog.mapper.LikeMapper;
import com.project.blog.mapper.PostMapper;
import com.project.blog.service.IPostService;
import com.project.blog.service.IUserService;
import com.project.blog.util.ResponseMapUtil;
import com.project.blog.vo.PostVO;
import com.project.blog.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService implements IPostService {

    @Resource
    private final PostMapper postMapper;
    private final IUserService userService;
    private final LikeMapper likeMapper;
    private final ResponseMapUtil responseMapUtil;

    @Override
    public List<PostVO> getPostList(HashMap<String, Integer> Map) throws Exception {
        return postMapper.getPostList(Map);
    }

    @Override
    public List<PostVO> getSearchList(HashMap<String, Object> hMap) throws Exception {
        return postMapper.getSearchList(hMap);
    }

    @Override
    public int postCount() throws Exception {
        int count = postMapper.postCount();
        return count;
    }

    @Override
    public int searchCount(String type, String keyword) throws Exception {
        int count = postMapper.searchCount(type, keyword);
        return count;
    }

    @Override
    public HashMap<String, String> postAddProc(PostVO postVO) throws Exception {
        HashMap<String, String> map;
        String userId = userService.userInfo().getUserId();

        if (userId != null) {
            postVO.setWriteId(userId);
        } else {
            postVO.setWriteId("익명");
        }

        int result = postMapper.postAddProc(postVO);

        if (result==1) {
            map = responseMapUtil.getResponseMap("post.add", "post.detail", postVO.getPostSeq());
        } else {
            map = responseMapUtil.getResponseMap("post.add.error", "post.add");
        }

        return map;
    }

    @Override
    public PostVO postDetail(int postSeq) throws Exception {
        PostVO postVO = postMapper.postDetail(postSeq);
        UserVO userVO = userService.userInfo();

        postMapper.viewIncrease(postSeq);

        if (userVO.getUserSeq() != null) {
            postVO.setLikeUser(likeMapper.postLikeCheck(Integer.parseInt(userVO.getUserSeq()), postSeq));
        }

        if (postVO.getWriteId().equals(userVO.getUserId())) {
            postVO.setWriter();
        }

        return postVO;
    }

    @Override
    public HashMap<String, String> postDelete(int postSeq) throws Exception {
        HashMap<String, String> map;
        int result = postMapper.postDelete(postSeq);

        if (result==1) {
            map = responseMapUtil.getResponseMap("post.delete", "post.list");
        } else {
            map = responseMapUtil.getResponseMap("post.delete.error", "post.list");
        }

        return map;
    }

    @Override
    public HashMap<String, String> postModProc(PostVO postVO) throws Exception {
        HashMap<String, String> map;

        String userId = userService.userInfo().getUserId();
        if (userId != null) {
            postVO.setChgId(userId);
        } else {
            postVO.setChgId("익명");
        }

        int result = postMapper.postModProc(postVO);

        if (result==1) {
            map = responseMapUtil.getResponseMap("post.mod", "post.detail", postVO.getPostSeq());
        } else {
            map = responseMapUtil.getResponseMap("post.mod.error", "post.detail", postVO.getPostSeq());
        }

        return map;
    }

    @Override
    public void postLikeInc(int postSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        postMapper.likeInc(postSeq);
        likeMapper.postLikeInc(userSeq, postSeq);
    }

    @Override
    public void postLikeDec(int postSeq) throws Exception {
        int userSeq = Integer.parseInt(userService.userInfo().getUserSeq());
        postMapper.likeDec(postSeq);
        likeMapper.postLikeDec(userSeq, postSeq);
    }
}
