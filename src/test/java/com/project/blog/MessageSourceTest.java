package com.project.blog;

import com.project.blog.util.ResponseMapUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;
import java.util.HashMap;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    private MessageSource messageSource;

    @Resource
    private ResponseMapUtil responseMapUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void hiMessage() {
        String result = messageSource.getMessage("hi", null, null);
        System.out.println("result : " + result);
    }

    @Test
    void getResponseMap() {
        HashMap<String, String> map = responseMapUtil.getResponseMap("test", "test");
        System.out.println(map.get("msg"));
        System.out.println(map.get("url"));
    }

    @Test
    void logTest() {
        logger.trace("Trace Level 테스트");
        logger.debug("DEBUG Level 테스트");
        logger.info("INFO Level 테스트");
        logger.warn("Warn Level 테스트");
        logger.error("ERROR Level 테스트");
    }
}
