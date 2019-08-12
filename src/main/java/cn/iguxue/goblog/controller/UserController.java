package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.Setting;
import cn.iguxue.goblog.entity.User;
import cn.iguxue.goblog.service.impl.RoleServiceImpl;
import cn.iguxue.goblog.service.impl.UserServiceImpl;
import cn.iguxue.goblog.utils.ResultVOUtil;
import cn.iguxue.goblog.vo.ResultVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.text.normalizer.UnicodeSet;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @GetMapping("/getById")
    public ResultVO getUserById(@RequestParam("id") Long id){
        return ResultVOUtil.success(userService.getUserById(id));
    }

    @GetMapping("/getAll")
    public ResultVO getAll() {
        return ResultVOUtil.success(userService.getAll());
    }

    @GetMapping("/getByPage")
    public ResultVO getByPage(@RequestParam(value = "start", defaultValue = "1") Integer start, @RequestParam(value = "size", defaultValue = "5") Integer size,@RequestParam(value = "username",defaultValue = "") String username) {
        IPage<User> iPage = userService.getByPage(start,size,username);
        return ResultVOUtil.success(iPage);
    }

    @GetMapping("/getRoles")
    public ResultVO getRoles() {
        return ResultVOUtil.success(roleService.getAll());
    }

    @PostMapping("/save")
    public ResultVO save(@RequestBody User user) {
        try {
            userService.save(user);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"新增失败");
        }
    }

    @PostMapping("/update")
    public ResultVO update(@RequestBody User user) {
        System.out.println("update:===" + user);
        try {
            userService.update(user);
            return ResultVOUtil.success();
        } catch (Exception e) {
            return ResultVOUtil.error(101,"更新失败");
        }
    }

    @PostMapping("/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        try {
            userService.delete(ids);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"删除失败");
        }
    }

    @PostMapping("/login")
    public ResultVO login(@RequestBody User user) {

        if (user != null) {
            System.out.println("login: " + user.getUsername() + "===========" + user.getPassword());
            Subject subject = SecurityUtils.getSubject();
            Map<String,Object> map = new HashMap<>();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            try {
                subject.login(token);
                map.put("token",token);
                System.out.println("login:====" + (User)subject.getPrincipal());
                return ResultVOUtil.success(map);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("dneglu shi bai");
                return ResultVOUtil.error(101,"登录失败");
            }
        } else {
            return ResultVOUtil.error(101,"登录失败");
        }
    }

    @GetMapping("/logout")
    public ResultVO logout() {
        try {
            SecurityUtils.getSubject().logout();
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"注销失败");
        }

    }

    @GetMapping("/getSetting")
    public ResultVO getSetting() {
        return ResultVOUtil.success(userService.getSetting());
    }

    @PostMapping("/updateSetting")
    public ResultVO updateSetting(@RequestBody Setting setting) {
        try {
            System.out.println(setting);
            userService.updateSetting(setting);
            return ResultVOUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"修改失败");
        }
    }

    @GetMapping("/getCurrent")
    public ResultVO getCurrent() {
        try {
            return ResultVOUtil.success( userService.getCurrent());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"获取失败");
        }
    }

    @PostMapping("uploadAvatar")
    public ResultVO uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultVOUtil.error(101,"上传失败");
        }

        try {
            //获取文件在服务器的储存位置
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File filePath = new File(path.getAbsolutePath(), "static/imgs/user/");
            if (!filePath.exists() && !filePath.isDirectory()) {
                filePath.mkdir();
            }

            String fileName = RandomUtils.nextInt(1,100) + file.getOriginalFilename();
            System.out.println("fileName: " + fileName);

            File dest = new File(filePath, fileName);
            file.transferTo(dest);

            Map map = new HashMap<>();
            map.put("name", fileName);
            map.put("url", "http://localhost:8899/imgs/user/" + fileName);

            return ResultVOUtil.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"SHA");
        }
    }

    @PostMapping("deleteAvatar")
    public ResultVO deleteAvatar(String filename) {
        if (filename.isEmpty()) {
            return ResultVOUtil.error(101,"删除失败");
        }
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File filePath = new File(path.getAbsolutePath(), "static/imgs/user/"+filename);

            if (!filePath.exists()) {
                return ResultVOUtil.error(101,"删除失败，文件不存在");
            } else {
                if (filePath.isFile()&&filePath.delete()) {
                    return ResultVOUtil.success();
                }else {
                    return ResultVOUtil.error(101,"删除失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVOUtil.error(101,"删除失败");
        }
    }

}
