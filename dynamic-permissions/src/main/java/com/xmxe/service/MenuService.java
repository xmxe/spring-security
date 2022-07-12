package com.xmxe.service;

import com.xmxe.entity.Menu;
import com.xmxe.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
	@Autowired
	MenuMapper menuMapper;

	public List<Menu> getAllMenu() {

		return menuMapper.getAllMenu();
	}
}