package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Role;
import cn.iguxue.goblog.vo.ResultVO;

import java.util.List;

public interface RoleService {
    Role getById(Integer id);

    Role getByName(String role);

    List<Role> getAll();
}
