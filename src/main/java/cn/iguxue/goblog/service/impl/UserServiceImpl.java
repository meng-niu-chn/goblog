package cn.iguxue.goblog.service.impl;

import ch.qos.logback.classic.boolex.GEventEvaluator;
import cn.iguxue.goblog.entity.Role;
import cn.iguxue.goblog.entity.Setting;
import cn.iguxue.goblog.entity.User;
import cn.iguxue.goblog.entity.UserRole;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.SettingMapper;
import cn.iguxue.goblog.mapper.UserMapper;
import cn.iguxue.goblog.mapper.UserRoleMapper;
import cn.iguxue.goblog.service.UserService;
import cn.iguxue.goblog.utils.PasswordHelper;
import cn.iguxue.goblog.utils.ShiroUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordHelper passwordHelper;

    @Autowired
    SettingMapper settingMapper;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    UserRoleServiceImpl userRoleService;

    private void formateUser(User user) {
        if (user != null) {
            Role role = userRoleService.getRoleByUserId(user.getId());
            if (role != null && !role.getRole().isEmpty()) {
                user.setRole(role.getRole());
            }
        }
    }

    private Boolean exist(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        return userMapper.selectCount(lambdaQueryWrapper) > 0 ? true : false;
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        formateUser(user);
        return user;
    }

    @Override
    public User getByName(String username) {
        if (!StringUtils.isEmpty(username)) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(User::getUsername, username);
            User user = userMapper.selectOne(lambdaQueryWrapper);
            formateUser(user);
            return user;
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = userMapper.selectList(null);
        if (users.size() > 0) {
            users.forEach(user -> {
                formateUser(user);
            });
        }
        return users;
    }

    @Override
    public IPage<User> getByPage(Integer start, Integer size, String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!username.isEmpty()) {
            lambdaQueryWrapper.like(User::getUsername,username);
        }
        Page<User> page = new Page<>(start,size);
        IPage<User> iPage = userMapper.selectPage(page,lambdaQueryWrapper);
        iPage.getRecords().forEach(user -> {
            if (user != null) {
                formateUser(user);
            }
        });
        return iPage;
    }

    private void updateUserRole(User user) {
        if (user.getId() > 0) {
            if (!StringUtils.isEmpty(user.getRole())) {
                Role role = roleService.getByName(user.getRole());
                userRoleService.save(new UserRole(user.getId(),role.getId()));
            }
        }
    }

    @Override
    public void save(User user) {
        if (user != null && !exist(user)) {
            PasswordHelper passwordHelper = new PasswordHelper();
            passwordHelper.encryptPassword(user);
            userMapper.insert(user);
            updateUserRole(user);
        } else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void update(User user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            passwordHelper.encryptPassword(user);
        }
        userMapper.updateById(user);

        updateUserRole(user);
        if (user.getId() == ((User) SecurityUtils.getSubject().getPrincipal()).getId()) {
            ShiroUtil.setUser(user);
        }

    }

    @Override
    public void delete(List<Long> ids) {
        userMapper.deleteBatchIds(ids);
        userRoleService.deleteByUserIds(ids);
    }

    @Override
    public Setting getSetting() {
        return settingMapper.selectOne(null);
    }

    @Override
    public void updateSetting(Setting setting) {
        settingMapper.updateById(setting);
    }

    @Override
    public User getCurrent() {
        // TODO: 2019/8/4
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println("currentUser:===" + user);
        formateUser(user);
        return user;
    }


}
