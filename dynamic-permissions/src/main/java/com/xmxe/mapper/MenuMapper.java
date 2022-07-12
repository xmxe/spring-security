package com.xmxe.mapper;

import com.xmxe.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

	List<Menu> getAllMenu();
}