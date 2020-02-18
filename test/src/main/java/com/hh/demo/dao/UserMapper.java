package com.hh.demo.dao;

import com.hh.demo.entity.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);

    /**
     * 按用户名查找用户信息
     */
    int selectByUsername(@Param("uname") String username);

    /**
     * 按用户名查找用户信息
     */
    int selectByEmail(@Param("email") String email);

    /**
     * 按用户名查找用户信息
     */
    User selectByUsernameAndPassword(@Param("uname") String username,
                                    @Param("pword") String password);
}
