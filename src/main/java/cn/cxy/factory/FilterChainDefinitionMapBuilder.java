package cn.cxy.factory;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * Created by lenovo on 2017/1/19.
 */
@Component
public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String,String> filterChainDefinitionMapBuilder(){
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<String, String>();
        //TODO 此处可动态从数据库获取权限信息
        linkedHashMap.put("/admin.jsp","authc,roles[admin]");
        linkedHashMap.put("/user.jsp","authc,roles[user]");
        linkedHashMap.put("/add.jsp","authc,roles[user,guest]");
        linkedHashMap.put("/guest.jsp","authc,roles[guest]");
        linkedHashMap.put("/shiro/login","anon");
        linkedHashMap.put("/shiro/logout","logout");
        linkedHashMap.put("/**","authc");
        return linkedHashMap;
    }
}
