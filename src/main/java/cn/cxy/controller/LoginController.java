package cn.cxy.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lenovo on 2017/1/12.
 */
@Controller
@RequestMapping("/shiro/")
public class LoginController {

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
}
