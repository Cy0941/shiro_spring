package cn.cxy.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lenovo on 2017/1/20.
 */
@Service
public class TestService {

    public void testShiroAnno(){
        Session session = SecurityUtils.getSubject().getSession();
        System.err.println("key from session : "+session.getAttribute("key"));
        System.out.println("--------test shiro requires annotation-------"+new Date());
    }

}
