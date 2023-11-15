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
            url = "/post/detail?no%3D" + postVO.getPostSeq();
        } else {
            msg = "게시글 등록 실패";
            url = "/post/add";
        }
        hMap.put("msg", msg);
        hMap.put("url", url);

        return hMap;
    }

    @Override
    public PostVO postDetail(int postSeq) throws Exception {
        PostVO postVO = postMapper.postDetail(postSeq);
        log.info("postSeq : " + postVO.getPostSeq());
        log.info("title : " + postVO.getTitle());
        log.info("content : " + postVO.getContent());
        log.info("writeId : " + postVO.getWriteId());
        log.info("writeDt : " + postVO.getWriteDt());
        log.info("chgId : " + postVO.getChgId());
        log.info("chgDt : " + postVO.getChgDt());
        log.info("isDeleted : " + postVO.getIsDeleted());

        return postVO;
    }

    @Override
    public HashMap<String, String> postDelete(int postSeq) throws Exception {
        HashMap<String, String> hMap = new HashMap<>();
        int result = postMapper.postDelete(postSeq);
        log.info("result : " + result);

        String msg, url;

        if (result==1) {
            msg = "글이 삭제되었습니다.";
            url = "/post?pno%3D1";
        } else {
            msg = "글 삭제 실패";
            url = "/post?pno%3D1";
        }
        hMap.put("msg", msg);
        hMap.put("url", url);

        return hMap;
    }

    @Override
    public HashMap<String, String> postModProc(PostVO postVO) throws Exception {
        HashMap<String, String> hMap = new HashMap<>();
        int result = postMapper.postModProc(postVO);
        log.info("result : " + result);

        String msg, url;

        if (result==1) {
            msg = "수정되었습니다.";
            url = "/post/detail?no%3D" + postVO.getPostSeq();
        } else {
            msg = "수정 실패";
            url = "/post/detail?no%3D" + postVO.getPostSeq();
        }
        hMap.put("msg", msg);
        hMap.put("url", url);

        return hMap;
    }
}
