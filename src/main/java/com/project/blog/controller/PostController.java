package com.project.blog.controller;

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

    @GetMapping
    public String getPostList(@RequestParam int pno, Model model) throws Exception {
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

        return "/post/list";
    }

    @GetMapping(value = "postAdd")
    public String postAdd() {
        return "/post/postAdd";
    }
    @PostMapping
    public String postAddProc(PostVO postVO, Model model) throws Exception {
        log.info("title : " + postVO.getTitle());
        log.info("content : " + postVO.getContent());

        HashMap<String, String> hMap = postService.postAddProc(postVO);
        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }

    @GetMapping(value = "postDetail")
    public String postDetail(@RequestParam("no") int post_seq, Model model) throws Exception {
        PostVO postVO = postService.postDetail(post_seq);
        model.addAttribute("postVO",postVO);

        return "/post/postDetail";
    }

    @PatchMapping
    public String postDelete(@RequestParam("no") int post_seq, Model model) throws Exception {
        log.info("post_seq : " + post_seq);

        HashMap<String, String> hMap = postService.postDelete(post_seq);

        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }

    @GetMapping(value = "postMod")
    public String postMod(@RequestParam("no") int post_seq, Model model) throws Exception {
        PostVO postVO = postService.postDetail(post_seq);
        model.addAttribute("postVO",postVO);
        log.info("post_seq : " + post_seq);

        return "/post/postMod";
    }
    @PutMapping
    public String postModProc(PostVO postVO, Model model) throws Exception {
        log.info("post_seq : " + postVO.getPost_seq());
        log.info("title : " + postVO.getTitle());
        log.info("content : " + postVO.getContent());

        HashMap<String, String> hMap = postService.postModProc(postVO);
        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }
}
