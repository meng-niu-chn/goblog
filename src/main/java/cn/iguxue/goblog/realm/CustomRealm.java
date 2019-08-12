package cn.iguxue.goblog.realm;

import cn.iguxue.goblog.entity.Role;
import cn.iguxue.goblog.entity.User;
import cn.iguxue.goblog.service.RoleService;
import cn.iguxue.goblog.service.UserRoleService;
import cn.iguxue.goblog.service.impl.UserRoleServiceImpl;
import cn.iguxue.goblog.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleServiceImpl userRoleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Set<String> roleSet = new HashSet<>();
        System.out.println("授权:===" + user);
        if (user != null) {
            Role role = userRoleService.getRoleByUserId(user.getId());
            if (role != null) {
                roleSet.add(role.getRole());
            }
        }

        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        info.setRoles(roleSet);
        System.out.println("set===========" + roleSet);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        System.out.println("认证：" + username);
        User user = userService.getByName(username);
        if (user == null) {
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }
}
