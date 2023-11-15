package com.project.blog.controller;

import com.project.blog.config.jwt.JwtTokenProvider;
import com.project.blog.service.IPostService;
import com.project.blog.util.PagingUtil;
import com.project.blog.vo.PostVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {

    private final IPostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    // 게시글 리스트 페이지
    @GetMapping
    public String getPostList(@CookieValue(value = "token", required = false) String token,
                              @RequestParam int pno, Model model) throws Exception {
        boolean user = false;
        if (token != null) {
            user = true;
        }

        int page = pno;
        int listCnt = postService.postCount();
        log.info("pno : " + page);
        log.info("listCnt : " + listCnt);

        PagingUtil paging = new PagingUtil();

        paging.pageInfo(page, listCnt);
        HashMap<String, Integer> hMap = new HashMap<>();
        int i = paging.getStartList();
        int j = paging.getListSize();
        hMap.put("startlist", i);
        hMap.put("listsize", j);
        log.info("hMap : " + hMap);

        List<PostVO> pList = new ArrayList<>();
        try {
            pList = postService.getPostList(hMap);
            log.info("pList : " + pList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<PagingUtil> puList = paging.list(paging.getPage(), paging.getRangeCnt(), paging.getStartPage());

        model.addAttribute("pList", pList);
        model.addAttribute("paging", paging);
        model.addAttribute("puList", puList);
        model.addAttribute("user", user);

        return "/post/list";
    }

    // 게시글 작성 페이지
    @GetMapping(value = "add")
    public String postAdd() {
        return "/post/postAdd";
    }

    // 게시글 작성
    @PostMapping
    public String postAddProc(@CookieValue(value = "token", required = false) String token,
                              PostVO postVO, Model model) throws Exception {
        String userId = jwtTokenProvider.getUserId(token);
        postVO.setWriteId(userId);
        log.info("title : " + postVO.getTitle());
        log.info("content : " + postVO.getContent());
        log.info("write_id : " + postVO.getWriteId());

        HashMap<String, String> hMap = postService.postAddProc(postVO);
        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }

    // 게시글 상세보기 페이지
    @GetMapping(value = "detail")
    public String postDetail(@CookieValue(value = "token", required = false) String token,
                             @RequestParam("no") int postSeq, Model model) throws Exception {
        PostVO postVO = postService.postDetail(postSeq);
        boolean write = false;

        if (token != null) {
            String userId = jwtTokenProvider.getUserId(token);
            if (userId.equals(postVO.getWriteId())) {
                write = true;
            }
        }

        model.addAttribute("postVO",postVO);
        model.addAttribute("write",write);

        return "/post/postDetail";
    }

    // 게시글 삭제
    @PatchMapping
    public String postDelete(@RequestParam("no") int postSeq, Model model) throws Exception {
        log.info("post_seq : " + postSeq);

        //TODO : RequestParam, PathVariable

        HashMap<String, String> hMap = postService.postDelete(postSeq);

        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }

    // 게시글 수정 페이지
    @GetMapping(value = "modify")
    public String postMod(@RequestParam("no") int postSeq, Model model) throws Exception {
        PostVO postVO = postService.postDetail(postSeq);
        model.addAttribute("postVO",postVO);
        log.info("postSeq : " + postSeq);

        return "/post/postModify";
    }

    // 게시글 수정
    @PutMapping
    public String postModProc(@CookieValue(value = "token", required = false) String token,
                              PostVO postVO, Model model) throws Exception {
        if (token != null) {
            String userId = jwtTokenProvider.getUserId(token);
            postVO.setChgId(userId);
        }

        log.info("post_seq : " + postVO.getPostSeq());
        log.info("title : " + postVO.getTitle());
        log.info("content : " + postVO.getContent());

        HashMap<String, String> hMap = postService.postModProc(postVO);
        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }
}
