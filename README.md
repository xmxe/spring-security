##### HttpSecurity常用方法

| [HttpSecurity常用方法](https://blog.csdn.net/qq_52006948/article/details/122729236)                | 说明                                                         |
| ------------------- | ------------------------------------------------------------ |
| openidLogin()       | 用于基于 OpenId 的验证                                       |
| headers()           | 将安全标头添加到响应                                         |
| cors()              | 配置跨域资源共享（ CORS ）                                   |
| sessionManagement() | 允许配置会话管理                                             |
| portMapper()        | 允许配置一个PortMapper(HttpSecurity#(getSharedObject(class)))，其他提供SecurityConfigurer的对象使用 PortMapper 从 HTTP 重定向到 HTTPS 或者从 HTTPS 重定向到 HTTP。默认情况下，Spring Security使用一个PortMapperImpl映射 HTTP 端口8080到 HTTPS 端口8443，HTTP 端口80到 HTTPS 端口443 |
| jee()               | 配置基于容器的预认证。 在这种情况下，认证由Servlet容器管理   |
| x509()              | 配置基于x509的认证                                           |
| rememberMe          | 允许配置“记住我”的验证                                       |
| authorizeRequests() | 允许基于使用HttpServletRequest限制访问                       |
| requestCache()      | 允许配置请求缓存                                             |
| exceptionHandling() | 允许配置错误处理                                             |
| securityContext()   | 在HttpServletRequests之间的SecurityContextHolder上设置SecurityContext的管理。 当使用WebSecurityConfigurerAdapter时，这将自动应用 |
| servletApi()        | 将HttpServletRequest方法与在其上找到的值集成到SecurityContext中。 当使用WebSecurityConfigurerAdapter时，这将自动应用 |
| csrf()              | 添加 CSRF 支持，使用WebSecurityConfigurerAdapter时，默认启用 |
| logout()            | 添加退出登录支持。当使用WebSecurityConfigurerAdapter时，这将自动应用。默认情况是，访问URL”/ logout”，使HTTP Session无效来清除用户，清除已配置的任何#rememberMe()身份验证，清除SecurityContextHolder，然后重定向到”/login?success” |
| anonymous()         | 允许配置匿名用户的表示方法。 当与WebSecurityConfigurerAdapter结合使用时，这将自动应用。 默认情况下，匿名用户将使用org.springframework.security.authentication.AnonymousAuthenticationToken表示，并包含角色 “ROLE_ANONYMOUS” |
| formLogin()         | 指定支持基于表单的身份验证。如果未指定FormLoginConfigurer#loginPage(String)，则将生成默认登录页面 |
| oauth2Login()       | 根据外部OAuth 2.0或OpenID Connect 1.0提供程序配置身份验证    |
| requiresChannel()   | 配置通道安全。为了使该配置有用，必须提供至少一个到所需信道的映射 |
| httpBasic()         | 配置 Http Basic 验证                                         |
| addFilterAt()       | 在指定的Filter类的位置添加过滤器                             |

---

##### Spring Security认证流程

1. Spring Security支持多种用户认证的方式，最常用的是基于用户名和密码的用户认证方式，其认证流程如下图所示：

![](https://github.com/xmxe/spring-security/blob/master/assest/rzlc.png)

2. “记住我”功能的认证流程如下图所示：

![](https://github.com/xmxe/spring-security/blob/master/assest/rememberme.png)

3. Spring Security的用户认证流程是由一系列的过滤器链来实现的，默认的关于用户认证的过滤器链大致如下图所示：

![](https://github.com/xmxe/spring-security/blob/master/assest/filterchain.png)


| SpringSecurity 采用的是责任链的设计模式， | 它有一条很长的过滤器链。                                     |
| ----------------------------------------- | ------------------------------------------------------------ |
| WebAsyncManagerIntegrationFilter          | 将 Security 上下文与 Spring Web 中用于处理异步请求映射的 WebAsyncManager 进行集成。 |
| SecurityContextPersistenceFilter          | 在每次请求处理之前将该请求相关的安全上下文信息加载到 SecurityContextHolder 中，然后在该次请求处理完成之后，将SecurityContextHolder 中关于这次请求的信息存储到一个“仓储”中，然后将SecurityContextHolder 中的信息清除，例如在 Session 中维护一个用户的安全信息就是这个过滤器处理的。 |
| HeaderWriterFilter                        | 用于将头信息加入响应中                                       |
| CsrfFilter                                | 用于处理跨站请求伪造                                         |
| LogoutFilter                              | 用于处理退出登录                                             |
| UsernamePasswordAuthenticationFilter      | 用于处理基于表单的登录请求，从表单中获取用户名和密码。默认情况下处理来自 /login 的请求。从表单中获取用户名和密码时，默认使用的表单 name 值为 username 和 password，这两个值可以通过设置这个过滤器的 usernameParameter 和 passwordParameter 两个参数的值进行修改 |
| DefaultLoginPageGeneratingFilter          | 如果没有配置登录页面，那系统初始化时就会配置这个过滤器，并且用于在需要进行登录时生成一个登录表单页面。 |
| BasicAuthenticationFilter                 | 检测和处理 http basic 认证                                   |
| RequestCacheAwareFilter                   | 用来处理请求的缓存                                           |
| SecurityContextHolderAwareRequestFilter   | 主要是包装请求对象 request                                   |
| AnonymousAuthenticationFilter             | 检测 SecurityContextHolder 中是否存在Authentication 对象，如果不存在为其提供一个匿名 Authentication |
| SessionManagementFilter                   | 管理 session 的过滤器                                        |
| ExceptionTranslationFilter                | 处理 AccessDeniedException 和AuthenticationException 异常。  |
| FilterSecurityInterceptor                 | 可以看做过滤器链的出口                                       |
| RememberMeAuthenticationFilter            | 当用户没有登录而直接访问资源时, 从 cookie里找出用户的信息, 如果 Spring Security 能够识别出用户提供的 remember me cookie,用户将不必填写用户名和密码, 而是直接登录进入系统，该过滤器默认不开启。 |

- [spring security的认证和授权流程](https://blog.csdn.net/u011066470/article/details/119086893)
- [一文带你搞懂Spring Security认证授权流程](https://zhuanlan.zhihu.com/p/502290821)

---

##### Spring Security登录流程

1. 用户在页面输入账户密码并提交。
2. UsernamePasswordAuthenticationFilter拦截认证请求并获取用户输入的账号和密码，
然后创建一个未认证的Authentication对象并交给AuthenticationManager进行认证。
3. AuthenticationManager调用相应的AuthenticationProvider对象对未认证的Authentication对象进行认证。
4. AuthenticationProvider从未认证的Authentication对象中获取用户输入的账号，并调用UserDetailsService对象查询该账号的正确信息，然后检查用户输入的账号信息与正确的账号信息是否相同--(UserDetailsService查询用户所输入的账号所对应的正确的密码和角色等信息并封装成UserDetails对象，然后返回给AuthenticationProvider进行认证)，如果不相同则认证失败并返回，如果相同认证成功并创建一个已认证的Authentication对象。
调用链
AuthenticationManager.authenticate()--> ProviderManager.authenticate()-->DaoAuthenticationProvider(AbstractUserDetailsAuthenticationProvider).authenticate()处理 。
在最后的authenticate()⽅法中，调⽤了 UserDetailsService.loadUserByUsername()并进⾏了密码校验，校验成功就构造⼀个认证过的 UsernamePasswordAuthenticationToken 对象放⼊ SecurityContext。
5. SecurityContext将已认证的Authentication对象保存在Spring Security上下文环境中表示用户已认证。


---

##### 相关文章

- [SpringBoot安全认证Security](https://zhuanlan.zhihu.com/p/67519928)
- [单点登陆注解@EnableOAuth2Sso](https://mp.weixin.qq.com/s?__biz=MzI1NDY0MTkzNQ==&mid=2247488278&idx=1&sn=b21345a1daa86dd48ea89cdb9138def8&scene=21#wechat_redirect)
- [Spring Security中的权限注解很神奇吗?(@PreAuthorize)](https://mp.weixin.qq.com/s/TaPlws-ZLTDUnffuiw-r1Q)
- [68篇干货，手把手教你通关Spring Security！](https://mp.weixin.qq.com/s/2sVZxZDXP_dq-YgS86u4DQ)
- [Spring Security5.x教程合集(江南一点雨)](http://www.javaboy.org/springsecurity/)
- [想要控制好权限，这八个注解你必须知道](https://mp.weixin.qq.com/s/1NlWRwiBs8dl3Lu40haz5Q)
- [进入SpringBoot2.7，有一个重要的类过期了](https://mp.weixin.qq.com/s/WFbcvzqIM2muK3Ha4B0a3w)
- [Spring Security中，想在权限中使用通配符，怎么做？](https://mp.weixin.qq.com/s/2Ci_Xg8wTrRcEnjgt18CDw)
- [如何在项目中自定义权限表达式](https://mp.weixin.qq.com/s/NTyYPGOSF9NobwtHas97sA)
- [TienChin项目中的RBAC是怎么玩的？](https://mp.weixin.qq.com/s/6wXK0zk_x_TP-qhHZQForw)
- [权限想要细化到按钮，怎么做？](https://mp.weixin.qq.com/s/g_8UprUi6cX70q4kTv4W9g)
- [Spring Security用户认证和权限控制（默认实现）](https://blog.csdn.net/weixin_44516305/article/details/87860966)
- [Spring Security用户认证和权限控制（自定义实现）](https://blog.csdn.net/weixin_44516305/article/details/88868791)
- [Spring Security动态权限实现方案！](https://mp.weixin.qq.com/s/Bau8poOA4fMh3DNb9GaR1A)
- [Spring Security最佳实践，看了必懂！※](https://mp.weixin.qq.com/s/rJqXpL7Zy9q_TU5B_E7nSw)
- [认证和授权：前后端分离状态下使用Spring Security实现安全访问控制](https://mp.weixin.qq.com/s/kB2SViBlcKwl2cV91FbidQ)