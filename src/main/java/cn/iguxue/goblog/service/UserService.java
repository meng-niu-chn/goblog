package cn.iguxue.goblog.service;

import cn.iguxue.goblog.entity.Setting;
import cn.iguxue.goblog.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface UserService {
    public User getUserById(Long id);

    void save(User user);

    User getByName(String username);

    void update(User user);

    void delete(List<Long> ids);

    Setting getSetting();

    void updateSetting(Setting setting);

    User getCurrent();

    List<User> getAll();

    IPage<User> getByPage(Integer start, Integer size, String username);
}
