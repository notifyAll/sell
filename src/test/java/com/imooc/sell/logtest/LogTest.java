package com.imooc.sell.logtest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogTest {

//    Logger logger= LoggerFactory.getLogger(this.toString());
    @Test
    public void logrun(){
//         logger.info("log测试");
        log.info("log  info  测试");
        log.debug("log  debug  测试");
        log.error("log  error  测试");
        log.warn("log  warn  测试");
    }

    @Test
    public void assertTest(){
//        Object o=null;
        Object o=new Object();
        Assert.assertNotNull(null);
    }
}
