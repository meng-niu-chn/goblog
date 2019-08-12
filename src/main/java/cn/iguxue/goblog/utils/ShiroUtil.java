package cn.iguxue.goblog.utils;

import cn.iguxue.goblog.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroUtil {
    public static void setUser(User user) {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principals = subject.getPrincipals();
//realName认证信息的key，对应的value就是认证的user对象
        String realName= principals.getRealmNames().iterator().next();
//创建一个PrincipalCollection对象，userDO是更新后的user对象
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realName);
        //调用subject的runAs方法，把新的PrincipalCollection放到session里面
        subject.runAs(newPrincipalCollection);
    }
}
