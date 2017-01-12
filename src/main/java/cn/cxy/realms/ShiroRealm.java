package cn.cxy.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * Function: 自定义Realm，继承 AuthorizingRealm
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/1/12 10:58 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Proejct 1.0
 */

@Component
public class ShiroRealm extends AuthorizingRealm {

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.err.println("授权------doGetAuthorizationInfo------");
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、AuthenticationToken 强转为 UsernamePasswordToken
        System.err.println("认证----------doGetAuthenticationInfo---------");
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
        Object principal = username;
        Object credentials = "123456";
        if ("admin".equals(username)) {
            credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
        }
        String realmName = getName();
        //TODO 返回的 info 中需要来源于数据库的正确信息【用户名/用户实体 与 密码】 具体密码比对由 shiro 完成
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
        //设置加密 salt 值
        info.setCredentialsSalt(ByteSource.Util.bytes(username));
        return info;
    }


    public static void main(String[] args){
        String hashAlgorithmName = "md5";
        String credentials = "123456";
        ByteSource salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024 ;
        SimpleHash hash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(hash);
    }
}
