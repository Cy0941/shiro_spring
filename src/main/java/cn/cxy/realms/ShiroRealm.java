package cn.cxy.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
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

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.err.println("授权------doGetAuthorizationInfo------");
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.err.println("认证----------doGetAuthenticationInfo---------");
        return null;
    }
}
