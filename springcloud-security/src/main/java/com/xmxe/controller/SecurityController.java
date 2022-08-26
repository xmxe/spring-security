package com.xmxe.controller;

import com.xmxe.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class SecurityController {

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("admin/1")
    public String getAdminData() {
        return "adminData";
    }

    @GetMapping("authenticated/1")
    public String getAuthData() {
        return "authData";
    }

    @GetMapping("guest/1")
    public String getGuestData() {
        return "guestData";
    }

    @GetMapping("permission/1")
    public String getPermission1Data() {
        return "permissionData";
    }


    @Secured({"ROLE_管理员","ROLE_访客"})//角色校验 ,请求到来访问控制单元方法时必须包含XX角色才能访问,角色必须添加ROLE_前缀
    @GetMapping("ipaddress/1")
    public String getPermission2Data() {
        return "ipaddressData";
    }

    /**
     * 获取登录后的Principal（需要登录）
     */
    @GetMapping("/getPrincipal")
    public Object getPrincipal(@AuthenticationPrincipal Principal principal){
        return principal;
    }

    /**
     * 获取登录后的UserDetails（需要登录）
     */
    @GetMapping("/getUserDetails")
    public Object getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

    /**
     * 需要登录，且需要guestRole角色
     *
     */
    @GetMapping("/hasRole")
    @PreAuthorize("hasRole('guestRole')")
    public Object hasRoles() {
        return "test hasRole Annotation";
    }
    /*
     * @PreAuthorize注解表达式  返回true代表拥有权限访问 返回false代表拒绝访问
     * hasRole([role])	当前用户是否拥有指定角色。
     * hasAnyRole([role1,role2])	多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true。
     * hasAuthority([auth])	等同于hasRole
     * hasAnyAuthority([auth1,auth2])	等同于hasAnyRole
     * Principle	代表当前用户的principle对象
     * authentication	直接从SecurityContext获取的当前Authentication对象
     * permitAll	总是返回true，表示允许所有的
     * denyAll	总是返回false，表示拒绝所有的
     * isAnonymous()	当前用户是否是一个匿名用户
     * isRememberMe()	表示当前用户是否是通过Remember-Me自动登录的
     * isAuthenticated()	表示当前用户是否已经登录认证成功了。
     * isFullyAuthenticated()	如果当前用户既不是一个匿名用户，同时又不是通过Remember-Me自动登录的，则返回true。
     */

    /*
     * 权限验证表达式只能验证用户是否具有访问某个URL或方法的权限，但是权限验证的这个步骤可以在不同的阶段进行。
     * Spring Security中定义了以下四个支持使用权限验证表达式的注解，
     * 其中前两者可以用来在方法调用前或者调用后进行权限验证，后两者可以用来对集合类型的参数或者返回值进行过滤：
     * @PreAuthorize
     * @PostAuthorize
     * @PreFilter
     * @PostFilter
     */

    /*
     * @Secured 角色校验 ,请求到来访问控制单元方法时必须包含XX角色才能访问,角色必须添加ROLE_前缀
     * @Secured({"ROLE_管理员","ROLE_访客"})
     * @RequestMapping("/toMain")
     * public String toMain(){ return "main"; }
     * 使用注解@Secured需要在配置类中添加注解 使@Secured注解生效
     * @EnableGlobalMethodSecurity(securedEnabled = true)
     *
     * @PreAuthorize
     * 权限检验,请求到来访问控制单元之前必须包含xx权限才能访问，控制单元方法执行前进行角色校验
     * [ROLE_管理员, admin:read, admin:write, all:login, all:logout, all:error, all:toMain]
     * @PreAuthorize   角色 、权限 校验 方法执行前进行角色校验
     * hasAnyAuthority() hasAuthority() hasPermission() hasRole() hasAnyRole()
     * @PreAuthorize("hasAnyRole('ROLE_管理员','ROLE_访客')")
     * @RequestMapping("/toMain")
     * @PreAuthorize("hasAuthority('admin:write')")
     * public String toMain(){ return "main"; }
     * 使用@PreAuthorize和@PostAuthorize 需要在配置类中配置注解@EnableGlobalMethodSecurity才能生效
     * @EnableGlobalMethodSecurity(prePostEnabled = true)
     *
     * @PostAuthorize
     * 权限检验,请求到来访问控制单元之后必须包含xx权限才能访问 ，控制单元方法执行完后进行角色校验
     * [ROLE_管理员, admin:read, admin:write, all:login, all:logout, all:error, all:toMain]
     * @PostAuthorize  角色 、权限 校验 方法执行后进行角色校验
     * hasAnyAuthority() hasAuthority() hasPermission() hasRole() hasAnyRole()
     * @PostAuthorize("hasRole('ROLE_管理员')")
     * @RequestMapping("/toMain")
     *
     */
}