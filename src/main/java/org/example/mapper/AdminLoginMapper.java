package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.entity.Employee;

@Mapper
public interface AdminLoginMapper {


    @Update("update employee set password=#{password} where id=#{id}")
    void updatePassword(Employee employee);



    @Select("select * from employee where username=#{username}")
    Employee getByName(String username);




}
