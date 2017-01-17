package cn.cxy.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * Function: 自定义Realm，继承 AuthorizingRealm
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/1/12 10:58 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Proejct 1.0
 */
public class ShiroRealm extends AuthorizingRealm {

    /**
     * 授权
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("第一个-----授权-----ShiroRealm------");
        //1、从 PrincipalCollection 中获取登录用户信息
        //TODO 此处 principalCollection.getPrimaryPrincipal() 返回值由 doGetAuthenticationInfo 方法构建的参数决定
        Object primaryPrincipal = principals.getPrimaryPrincipal();

        //2、利用登录用户的信息获取对应的角色或权限
        Set<String> role = new HashSet<String>();
        if ("admin".equals(primaryPrincipal)||"user".equals(primaryPrincipal)){
            role.add("user");
        }
        if ("admin".equals(primaryPrincipal)){
            role.add("admin");
        }

        //3、构建 SimpleAuthorizationInfo 并设置 roles、permission
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(role);

        //4、返回对应info
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
        System.err.println("第一个-----认证-----ShiroRealm---------");
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
        }else if ("user".equals(username)){
            credentials = "098d2c478e9c11555ce2823231e02ec1";
        }else if ("guest".equals(username)){
            credentials = "6af5141317258b4c866bd2bfe6b16e4e";
        }
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
        String hashAlgorithmName = "md5";
        String credentials = "123456";
        ByteSource salt = ByteSource.Util.bytes("guest");
        int hashIterations = 1024 ;
        SimpleHash hash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(hash);
    }
}
