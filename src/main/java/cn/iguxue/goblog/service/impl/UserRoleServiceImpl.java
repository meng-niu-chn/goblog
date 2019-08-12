package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.Role;
import cn.iguxue.goblog.entity.User;
import cn.iguxue.goblog.entity.UserRole;
import cn.iguxue.goblog.enums.ResultEnum;
import cn.iguxue.goblog.exception.GoblogException;
import cn.iguxue.goblog.mapper.RoleMapper;
import cn.iguxue.goblog.mapper.UserRoleMapper;
import cn.iguxue.goblog.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserRole getById(Long id) {
        return userRoleMapper.selectById(id);
    }

    private boolean exist(UserRole userRole) {
        if (userRole != null) {
            LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserRole::getUId,userRole.getUId());
            return userRoleMapper.selectCount(lambdaQueryWrapper) > 0 ? true : false;
        }else {
            throw new GoblogException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void save(UserRole userRole) {
        if (!exist(userRole)) {
            userRoleMapper.insert(userRole);
        }else {
            LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserRole::getUId,userRole.getUId());
            userRoleMapper.update(userRole,lambdaQueryWrapper);
        }
    }

    @Override
    public Role getRoleByUserId(Long id) {
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUId,id);
        UserRole userRole = userRoleMapper.selectOne(lambdaQueryWrapper);
        if (userRole != null) {
            return roleMapper.selectById(userRole.getRId());
        } else {
            return null;
        }

    }

    @Override
    public void deleteByUserIds(List<Long> ids) {
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(UserRole::getUId,ids);
        List<UserRole> userRoles = userRoleMapper.selectList(lambdaQueryWrapper);
        List<Long> userRoleIds = new ArrayList<>();
        userRoles.forEach(userRole -> {
            userRoleIds.add(userRole.getId());
        });
        if (userRoleIds.size() > 0){
            userRoleMapper.deleteBatchIds(userRoleIds);
        }

    }
}
