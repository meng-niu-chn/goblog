package cn.iguxue.goblog.controller;

import cn.iguxue.goblog.entity.LoginLog;
import cn.iguxue.goblog.entity.User;
import cn.iguxue.goblog.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;



@Controller
@RequestMapping("/user")
public class TestController {

    @Autowired
    UserServiceImpl userService;

//    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("login: " + username + "===========" + password);
        if (username != null && password != null) {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//            if (remember != null) {
//                if (remember.equals("true")) {
//                    //说明选择了记住我
//                    token.setRememberMe(true);
//                } else {
//                    token.setRememberMe(false);
//                }
//            } else {
//                token.setRememberMe(false);
//            }
            try {
                subject.login(token);

//                //记录登录日志
//                LoginLog log = new LoginLog();
//                //获取HTTP请求
//                HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
//                String ip = IPUtil.getIpAddr(request);
//                log.setIp(ip);
//                log.setUsername(super.getCurrentUser().getUsername());
//                log.setLocation(AddressUtil.getAddress(ip));
//                log.setCreateTime(new Date());
//                String header = request.getHeader("User-Agent");
//                UserAgent userAgent = UserAgent.parseUserAgentString(header);
//                Browser browser = userAgent.getBrowser();
//                OperatingSystem operatingSystem = userAgent.getOperatingSystem();
//                log.setDevice(browser.getName() + " -- " + operatingSystem.getName());
//                loginLogService.saveLog(log);
//
//                model.addAttribute("username", getSubject().getPrincipal());
                return "redirect: index";
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("dneglu shi bai");
            }
        } else {

        }
        return "redirect: /";
    }

//    @GetMapping("/register")
    public String register() {
        return "register";
    }

//    @PostMapping("/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("register: " + username + "===========" + password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        return "redirect: login";
    }
}
