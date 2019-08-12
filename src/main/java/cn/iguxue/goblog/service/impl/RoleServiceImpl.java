package cn.iguxue.goblog.service.impl;

import cn.iguxue.goblog.entity.Role;
import cn.iguxue.goblog.mapper.RoleMapper;
import cn.iguxue.goblog.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Role getById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Role getByName(String role) {

        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Role::getRole,role);

        return roleMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public List<Role> getAll() {
        return roleMapper.selectList(null);
    }
}
