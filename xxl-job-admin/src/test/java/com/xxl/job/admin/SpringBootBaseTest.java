
package com.xxl.job.admin;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;


/**
* Created by ZengPengHui at 2021/9/11.
*/
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.xxl.job"})
@SpringBootTest
public class SpringBootBaseTest {
    
    @Test
    public void test() {
        System.out.pringln("success load");
    }
}

