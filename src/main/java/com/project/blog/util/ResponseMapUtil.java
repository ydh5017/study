package com.project.blog.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ResponseMapUtil {

    @Resource
    private final MessageSource messageSource;

    public HashMap<String, String> getResponseMap(String message, String URL) {
        HashMap<String, String> map = new HashMap<>();

        String msg = messageSource.getMessage(message, null, null);
        String url = messageSource.getMessage(URL+".url", null, null);

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }

    public HashMap<String, String> getResponseMap(String message, String URL, int seq) {
        HashMap<String, String> map = new HashMap<>();

        String msg = messageSource.getMessage(message, null, null);
        String url = messageSource.getMessage(URL+".url", new Object[]{seq}, null);

        map.put("msg", msg);
        map.put("url", url);

        return map;
    }
}
