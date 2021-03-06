package cn.cxy.realms;

import cn.cxy.beans.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Function: 自定义Realm，继承 AuthorizingRealm
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/1/12 10:58 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Proejct 1.0
 */
public class SecondRealm extends AuthorizingRealm {

    /**
     * 授权
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("第二个-----授权----SecondRealm---------");
        User principal = (User)principals.getPrimaryPrincipal();
        List<String> roleList = principal.getRoleList();
        Set<String> roles = new HashSet<String>();
        if (roleList.contains("user")||roleList.contains("guest")){
            roles.add("guest");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、AuthenticationToken 强转为 UsernamePasswordToken
        System.err.println("第二个-----认证-----SecondRealm---------");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        //2、从 UsernamePasswordToken 中获取用户录入的 username   TODO 密码
        String username = token.getUsername();
        //3、从数据库中查询对应记录
        System.out.println("data from db:"+username+" : "+"123456");

        //TODO
        if ("unknown".equals(username)){
            throw new UnknownAccountException("-------unknown account-------");
        }
        if ("monster".equals(username)){
            throw new UnknownAccountException("------account has been locked-------");
        }
        String inputPassword = new String(token.getPassword());
        if (!"123456".equals(inputPassword)){
            throw new IncorrectCredentialsException("---------wrong password--------");
        }
        //3、根据记录存在与否返回 null[异常交由对应 Controller 抛出] 或 SimpleAuthenticationInfo
        Object credentials = "123456";
        List<String> roleList = new ArrayList<String>();
        if ("admin".equals(username)) {
            credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
            roleList.add("admin");
            roleList.add("user");
        }else if ("user".equals(username)){
            credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
            roleList.add("user");
        }else if ("guest".equals(username)){
            credentials = "3e17f932da1d0abac5b09c46004c1766a66211be";
            roleList.add("guest");
        }
        Object principal = new User(username,credentials.toString(),roleList);
        String realmName = getName();
        //TODO 返回的 info 中需要来源于数据库的正确信息【用户名/用户实体 与 密码】 具体密码比对由 shiro 完成
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
        //设置加密 salt 值
        info.setCredentialsSalt(ByteSource.Util.bytes(username));
        return info;
    }


    /**
     * 盐值加密
     * @param args
     */
    public static void main(String[] args){
        String hashAlgorithmName = "sha1";
        String credentials = "123456";
        ByteSource salt = ByteSource.Util.bytes("guest");
        int hashIterations = 1024 ;
        SimpleHash hash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(hash);
    }
}
