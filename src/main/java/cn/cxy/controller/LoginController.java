package cn.cxy.controller;

import cn.cxy.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by lenovo on 2017/1/12.
 */
@Controller
@RequestMapping("/shiro/")
public class LoginController {

    @Autowired
    private TestService testService;

    //TODO shiro 权限注解，由于在 service 层中会使用 @Transactional 注解，即使用代理对象进行实际操作，故正式环境此类所需注解仅配置在 Controller 层
    //TODO shiro 自带 session 管理，即使是在非 web 环境中 --- 某些情况下需要在 service 层使用 session 可以使用此种方法（不建议）
    @RequiresRoles({"admin"})
    @RequestMapping(value = "testAnnotation",method = RequestMethod.GET)
    public String testAnnotation(HttpSession session){
        session.setAttribute("key","value");
        testService.testShiroAnno();
        return "add";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@RequestParam("username")String username,@RequestParam("password")String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        System.out.println("username: "+username+" ,password: "+password);
        /*try {*/
            subject.login(token);
        /*} catch (UnknownAccountException e){
            System.err.println("用户不存在");
        }catch (IncorrectCredentialsException e){
            System.err.println("密码不正确");
        }catch (AuthenticationException e) {
            e.printStackTrace();
        }*/
        return "list";
    }

    @RequestMapping(value = "init",method = RequestMethod.POST)
    public void init(){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.hasRole("user"));
    }
}
