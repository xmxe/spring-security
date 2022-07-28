package com.xmxe.conf;

import com.xmxe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomSecurityMetadataSource customSecurityMetadataSource;

	@Autowired
	UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	/**
	 * 由于访问路径规则和所需要的权限之间的映射关系已经保存在数据库中，所以我们就没有必要在 Java 代码中配置映射关系了，同时这里的权限对比也不会
	 * 用到权限表达式，所以我们通过 UrlAuthorizationConfigurer 来进行配置。
	 * 在配置的过程中，通过 withObjectPostProcessor 方法调用 ObjectPostProcessor 对象后置处理器，在对象后置处理器中，
	 * 将 FilterSecurityInterceptor 中的 SecurityMetadataSource 对象替换为我们自定义的 customSecurityMetadataSource 对象即可。
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
		http.apply(new UrlAuthorizationConfigurer<>(applicationContext)).
				withObjectPostProcessor(
					new ObjectPostProcessor<FilterSecurityInterceptor>() {
						@Override
						public <O extends FilterSecurityInterceptor> O postProcess(O object) {
							object.setSecurityMetadataSource(customSecurityMetadataSource);
							// 通过设置 FilterSecurityInterceptor 中的 rejectPublicInvocations 属性为 true，就可以关闭URL的公开访问，所有 URL 必须具备对应的权限才能访问,url没有指定权限抛出500错误
							object.setRejectPublicInvocations(true);
							return object;
						}
					});
		http.formLogin().and().csrf().disable();
	}
}