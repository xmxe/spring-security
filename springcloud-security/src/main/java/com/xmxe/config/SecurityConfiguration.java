package com.xmxe.config;

import com.xmxe.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
//@EnableOAuth2Sso
public class SecurityConfiguration {

    /**
     * 真实的用户信息
     */
    @Autowired
    MyUserDetailService myUserDetailService;

    /**
     * 登陆失败处理
     */
    @Autowired
    FailureHandler failureHandler;

    /**
     * 登录成功处理
     */
    @Autowired
    SuccessHandler successHandler;

    /**
     * session过期处理
     */
    @Autowired
    CustomSessionExpiredStrategy sessionInformationExpiredStrategy;

    /**
     * 自定义过滤器
     */
    @Autowired
    private CustomRequestFilter customRequestFilter;

    /**
     * 自定义用户访问无权限资源时的异常
     */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * 不使用@Bean的话直接继承webSecurityConfigurerAdapter类重写方法
     */
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {

            @Override
            public void configure(WebSecurity web) {
                web.ignoring().antMatchers("/js/**", "/css/**", "/images/**","/**/favicon.*");
            }

            @Override
            protected void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/doLogin") // 该方法并不需要定义servlet，只需要保证前端表单的action和该出的url值为一样即可，如果定义了一个同名的servlet，则会优先执行自定义的servlet，不会走security框架的验证
                        //.defaultSuccessUrl("/hello",true) //配置默认首页 加true相当于successForwardUrl 二者区别见下方注释
                        //.usernameParameter("name") // 不配置的话登陆表单参数必须为username
                        //.passwordParameter("passwd") // 不配置的话登陆表单参数必须为password
                        //.failureForwardUrl("/error")
                        .failureHandler(failureHandler) // 登陆失败时将自定义信息写入session
                        //.successHandler(successHandler) // 登陆成功后的handler
                        .permitAll() // 允许访问
                        .and()
                    .exceptionHandling()
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 配置认证失败时的调用
                        .and()
                    .sessionManagement() // session管理器
                        // ALWAYS:总是创建HttpSession. IF_REQUIRED:Spring Security只会在需要时创建一个HttpSession
                        // NEVER:Spring Security不会创建HttpSession，但如果它已经存在，将可以使用HttpSession
                        // STATELESS:Spring Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
                        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .maximumSessions(1) // 相同用户只允许登陆1个
                        //.maxSessionsPreventsLogin(true) // 相同用户登陆后不允许在登陆（禁止新的登录）
                        .expiredSessionStrategy(sessionInformationExpiredStrategy) // 自定义session过期策略
                        .and()
                        .invalidSessionUrl("/login") // session过期跳转地址
                        .and()
                    .rememberMe()
                        .rememberMeParameter("remember-me")
                        .userDetailsService(myUserDetailService)
                        .tokenValiditySeconds(60) // 记住我的时间 (秒)
                        //.tokenRepository(persistentTokenRepository()) // 设置数据访问层
                        .and()
                    .authorizeRequests()
                        .antMatchers("/guest/**","/error","/login").permitAll()// /guest/**的接口会被允许所有人访问，包括未登录的人。
                        .antMatchers("/admin/**").hasRole("adminRole")// /admin/**的接口只能被拥有admin角色的用户访问。
                        .antMatchers("/authenticated/**").authenticated()// /authenticated/**的接口可以被所有已经登录的用户访问。
                        .antMatchers("/permission/**").hasAuthority("permission")// /permission/的接口可以被拥有permission权限的用户访问。
                        .antMatchers("/ipaddress/**").hasIpAddress("127.0.0.1")
                        .anyRequest().authenticated()//所有的都需要认证
                        .and()
                    .logout()
                        .logoutUrl("/logout_1")//spring security设置logout_1为系统退出的url 当监测到这个请求url时使用spring security处理登出逻辑 请求方式默认使用post a标签方式为get 可以在spring mvc中处理登出逻辑 但是这样的话就和spring security没关系了
                        //.logoutRequestMatcher(new AntPathRequestMatcher("/logout","POST"))//自定义请求方式
                        .logoutSuccessUrl("/login")//注销成功后要跳转的页面。
                        .deleteCookies()//用来清除 cookie
                        .permitAll()
                        .and()
                    .addFilterBefore(customRequestFilter, UsernamePasswordAuthenticationFilter.class)// 在UsernamePasswordAuthenticationFilter过滤器执行之前执行自定义的过滤器
                    // .addFilterBefore(new LoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)// 添加一个过滤器 所有访问 /login 的请求交给 LoginFilter 来处理
                    // .addFilter(new TokenLoginFilter(super.authenticationManager())) // 功能同上 也是校验登录 extends UsernamePasswordAuthenticationFilter
                    // .addFilter(new TokenVerifyFilter(super.authenticationManager())) // 处理token的过滤器
                        .csrf().disable();
            }

            //代码创建用户密码
//            @Override
//            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//                auth.inMemoryAuthentication()
//                        .withUser("user")
//                        .password("password")
//                        .roles("USER")
//                        .and()
//                        .withUser("admin")
//                        .password("admin")
//                        .roles("USER", "ADMIN");

//                auth.userDetailsService(userDetailsServiceImpl);
//            }


            @Override
            protected void configure(AuthenticationManagerBuilder auth) {
                // 使用自定义身份验证组件,校验工作自己完成
                // auth.authenticationProvider(new CustomAuthenticationProvider());

                try {
                    auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

    }

    /**
     * 密码加密策略
     *
     */
    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();// 不加密 在demo中测试使用 生产环境不建议
//        return new BCryptPasswordEncoder();// BCryptPasswordEncoder：相同的密码明文每次生成的密文都不同，安全性更高

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 角色集成 user角色能访问的admin也角色可以访问
     *
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_adminRole > ROLE_guestRole");
        return hierarchy;
    }

    /**
     * 防止当设置成相同用户登陆后不允许在登陆时第一个用户注销登录后无法继续登陆
     */
    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    /**
     * 持久化token
     * Security中，默认是使用PersistentTokenRepository的子类InMemoryTokenRepositoryImpl，将token放在内存中
     * 如果使用JdbcTokenRepositoryImpl，会创建表persistent_logins，将token持久化到数据库
     */
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        tokenRepository.setDataSource(dataSource); // 设置数据源
////        tokenRepository.setCreateTableOnStartup(true); // 系统启动时自动创建表，只需要在第一次启动系统时创建即可，因此这行代码只在需要创建表时才启用
//        return tokenRepository;
//    }
}


/**
 * 在 Spring Security 中，和登录成功重定向 URL 相关的方法有两个
 * defaultSuccessUrl successForwardUrl
 * defaultSuccessUrl 有一个重载的方法，我们先说一个参数的 defaultSuccessUrl 方法。
 * 如果我们在 defaultSuccessUrl 中指定登录成功的跳转页面为 /index，此时分两种情况，
 * 如果你是直接在浏览器中输入的登录地址，登录成功后，就直接跳转到 /index，
 * 如果你是在浏览器中输入了其他地址，例如 http://localhost:8080/hello，结果因为没有登录，又重定向到登录页面，
 * 此时登录成功后，就不会来到 /index ，而是来到 /hello 页面。
 * defaultSuccessUrl 还有一个重载的方法，第二个参数如果不设置默认为 false，也就是我们上面的的情况，
 * 如果手动设置第二个参数为 true，则 defaultSuccessUrl 的效果和 successForwardUrl 一致。
 * successForwardUrl 表示不管你是从哪里来的，登录后一律跳转到 successForwardUrl 指定的地址。
 * 例如 successForwardUrl 指定的地址为 /index ，你在浏览器地址栏输入 http://localhost:8080/hello，
 * 结果因为没有登录，重定向到登录页面，当你登录成功之后，就会服务端跳转到 /index 页面；
 * 或者你直接就在浏览器输入了登录页面地址，登录成功后也是来到 /index。
 *
 * 与登录成功相似，登录失败也是有两个方法：
 * failureForwardUrl failureUrl
 * 「这两个方法在设置的时候也是设置一个即可」。
 * failureForwardUrl 是登录失败之后会发生服务端跳转，failureUrl 则在登录失败之后，会发生重定向。
 */

/**
 * spring boot如果想在浏览器中直接访问html (localhost:8090/login.html) 在resources下新建public目录
 * 否则的话必须通过视图渲染解析才能访问 也就是loginPage("/login.html") 404的原因
 * spring boot默认首页会去访问static/index.html 所以不配置defaultSuccessUrl首页的话也不会报错 如果配置的话必须在Controller写接口 否则会报404
 */