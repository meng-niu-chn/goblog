package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Role;
import cn.iguxue.goblog.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    UserRole getById(Long id);

    void save(UserRole userRole);

    Role getRoleByUserId(Long id);

    void deleteByUserIds(List<Long> ids);
}
