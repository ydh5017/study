package com.project.blog.service.Impl;

import com.project.blog.mapper.PostMapper;
import com.project.blog.service.IPostService;
import com.project.blog.service.IUserService;
import com.project.blog.vo.PostVO;
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

    @Override
    public List<PostVO> getPostList(HashMap<String, Integer> Map) throws Exception {
        return postMapper.getPostList(Map);
    }

    @Override
    public int postCount() throws Exception {
        int count = postMapper.postCount();
        return count;
    }

    @Override
    public HashMap<String, String> postAddProc(PostVO postVO) throws Exception {
        HashMap<String, String> Map = new HashMap<>();
        String userId = userService.userInfo().getUserId();

        if (userId != null) {
            postVO.setWriteId(userId);
        } else {
            postVO.setWriteId("익명");
        }

        int result = postMapper.postAddProc(postVO);
        String msg, url;

        if (result==1) {
            msg = "게시글 등록 성공";
            url = "/post/detail?no%3D" + postVO.getPostSeq();
        } else {
            msg = "게시글 등록 실패";
            url = "/post/add";
        }
        Map.put("msg", msg);
        Map.put("url", url);

        return Map;
    }

    @Override
    public PostVO postDetail(int postSeq) throws Exception {
        PostVO postVO = postMapper.postDetail(postSeq);

        if (postVO.getWriteId().equals(userService.userInfo().getUserId())) {
            postVO.setWriter();
        }

        return postVO;
    }

    @Override
    public HashMap<String, String> postDelete(int postSeq) throws Exception {
        HashMap<String, String> Map = new HashMap<>();
        int result = postMapper.postDelete(postSeq);

        String msg, url;

        if (result==1) {
            msg = "글이 삭제되었습니다.";
            url = "/post?pno%3D1";
        } else {
            msg = "글 삭제 실패";
            url = "/post?pno%3D1";
        }
        Map.put("msg", msg);
        Map.put("url", url);

        return Map;
    }

    @Override
    public HashMap<String, String> postModProc(PostVO postVO) throws Exception {
        HashMap<String, String> Map = new HashMap<>();

        String userId = userService.userInfo().getUserId();
        if (userId != null) {
            postVO.setChgId(userId);
        } else {
            postVO.setChgId("익명");
        }

        int result = postMapper.postModProc(postVO);
        String msg, url;

        if (result==1) {
            msg = "수정되었습니다.";
            url = "/post/detail?no%3D" + postVO.getPostSeq();
        } else {
            msg = "수정 실패";
            url = "/post/detail?no%3D" + postVO.getPostSeq();
        }
        Map.put("msg", msg);
        Map.put("url", url);

        return Map;
    }
}
