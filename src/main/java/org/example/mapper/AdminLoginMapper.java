package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Employee;

@Mapper
public interface AdminLoginMapper {
    @Select("select * from employee where username=#{username}")
    Employee getByName(String username);




}
