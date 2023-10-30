package com.project.blog.service.Impl;

import com.project.blog.mapper.PostMapper;
import com.project.blog.service.IPostService;
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

    @Override
    public List<PostVO> getPostList(HashMap<String, Integer> hMap) throws Exception {
        return postMapper.getPostList(hMap);
    }

    @Override
    public int postCount() throws Exception {
        int count = postMapper.postCount();
        return count;
    }

    @Override
    public HashMap<String, String> postAddProc(PostVO postVO) throws Exception {
        HashMap<String, String> hMap = new HashMap<>();
        int result = postMapper.postAddProc(postVO);
        String msg, url;

        if (result==1) {
            msg = "게시글 등록 성공";
            url = "/post/list?pno=1";
        } else {
            msg = "게시글 등록 실패";
            url = "/post/postAdd";
        }
        hMap.put("msg", msg);
        hMap.put("url", url);

        return hMap;
    }

    @Override
    public PostVO postDetail(int post_seq) throws Exception {
        PostVO postVO = postMapper.postDetail(post_seq);
        log.info("post_seq : " + postVO.getPost_seq());
        log.info("title : " + postVO.getTitle());
        log.info("content : " + postVO.getContent());
        log.info("write_dt : " + postVO.getPost_write_dt());
        log.info("chg_dt : " + postVO.getPost_chg_dt());
        log.info("deleted : " + postVO.getPost_deleted());

        return postVO;
    }

    @Override
    public HashMap<String, String> postDelete(int post_seq) throws Exception {
        HashMap<String, String> hMap = new HashMap<>();
        int result = postMapper.postDelete(post_seq);
        String msg, url;

        if (result==1) {
            msg = "글이 삭제되었습니다.";
            url = "/post/";
        } else {
            msg = "글 삭제 실패";
            url = "/post/";
        }
        hMap.put("msg", msg);
        hMap.put("url", url);

        return hMap;
    }
}
