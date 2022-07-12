package com.xmxe.conf;

import com.xmxe.entity.Menu;
import com.xmxe.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	MenuService menuService;

	AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * 该方法的参数是受保护对象，在基于 URL 地址的权限控制中，受保护对象就是 FilterInvocation；该方法的返回值则是访问受保护对象所需要的权限。
	 * 在该方法里边，我们首先从受保护对象 FilterInvocation 中提取出当前请求的 URL 地址，例如 /admin/hello，然后通过 menuService 对象查询
	 * 出所有的菜单数据（每条数据中都包含访问该条记录所需要的权限），遍历查询出来的菜单数据，如果当前请求的 URL 地址和菜单中某一条记录的 pattern
	 * 属性匹配上了（例如 /admin/hello 匹配上 /admin/**），那么我们就可以获取当前请求所需要的权限。从 menu 对象中获取 roles 属性，
	 * 并将其转为一个数组，然后通过 SecurityConfig.createList 方法创建一个 Collection<ConfigAttribute> 对象并返回。
	 * 如果当前请求的 URL 地址和数据库中 menu 表的所有项都匹配不上，那么最终返回 null。如果返回 null，那么受保护对象到底能不能访问呢？
	 * 这就要看 AbstractSecurityInterceptor 对象中的 rejectPublicInvocations 属性了，该属性默认为 false，表示当 getAttributes方法返回 null 时，允许访问受保护对象
	 * @param object 受保护的对象
	 * @return
	 * @throws IllegalArgumentException
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
		List<Menu> allMenu = menuService.getAllMenu();
		for (Menu menu : allMenu) {
			if (antPathMatcher.match(menu.getPattern(), requestURI)) {
				String[] roles = menu.getRoles().stream().map(r -> r.getName()).toArray(String[]::new);
				return SecurityConfig.createList(roles);
			}
		}
		return null;
	}

	/**
	 * 该方法可以用来返回所有的权限属性，以便在项目启动阶段做校验，如果不需要校验，则直接返回 null 即可
	 * @return
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 该方法表示当前对象支持处理的受保护对象是 FilterInvocation
	 * @param clazz
	 * @return
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}