package com.project.blog.controller;

import com.project.blog.service.IPostService;
import com.project.blog.util.PagingUtil;
import com.project.blog.vo.PostVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
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
        int i = paging.getStartPage();
        int j = paging.getListSize();
        hMap.put("startlist", i-1);
        hMap.put("listsize", j);
        log.info("hMap : " + hMap);

        List<PostVO> pList = new ArrayList<>();
        try {
            pList = postService.getPostList(hMap);
            log.info("pList : " + pList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int p = paging.getEndPage() - paging.getStartPage()+1;
        String [] pageNum = new String[p];
        int f, u = 0;

        for (f = paging.getStartPage(); f <= paging.getEndPage(); f++) {
            pageNum[u] = String.valueOf(f);
            log.info("pageNum : " + pageNum[u]);
            u++;
        }

        model.addAttribute("pList", pList);
        model.addAttribute("paging", paging);
        model.addAttribute("pageNum", pageNum);

        return "/post/List";
    }

    @GetMapping(value = "postAdd")
    public String postAdd() {
        return "/post/postAdd";
    }
    @PostMapping
    public String postAddProc(@RequestParam String title,
                              @RequestParam String content, Model model) throws Exception {
        log.info("title : " + title);
        log.info("content : " + content);

        PostVO postVO = new PostVO();
        postVO.setTitle(title);
        postVO.setContent(content);

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

    @DeleteMapping
    public String postDelete(@RequestParam("no") int post_seq, Model model) throws Exception {
        log.info(String.valueOf(post_seq));

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
    public String postModProc(@RequestParam("no") int post_seq,
                              @RequestParam String title,
                              @RequestParam String content, Model model) throws Exception {
        log.info("post_seq : " + post_seq);
        log.info("title : " + title);
        log.info("content : " + content);

        PostVO postVO = new PostVO();
        postVO.setPost_seq(post_seq);
        postVO.setTitle(title);
        postVO.setContent(content);

        HashMap<String, String> hMap = postService.postModProc(postVO);
        log.info("hMap : " + hMap);

        model.addAttribute("msg", hMap.get("msg"));
        model.addAttribute("url", hMap.get("url"));

        return "/redirect";
    }
}
