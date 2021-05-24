package club.zstuca.myzstu.student.mapper;

import club.zstuca.myzstu.student.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @date 2020-02-10 13:26
 */
@Mapper
public interface StudentMapper  {

    @Select("select openid,username,edupw,ecardpw,libpw,ssopw from user where openid=#{openid}")
    Student findStudentById(String openid);

    @Select("select 学号,QQ from student where xh=#{id}")
    Student findStudentByStudentID(String id);

    @Insert("insert into user(openid,username,ecardpw,edupw,libpw,ssopw) values (#{openid},#{username},#{ecardPw},#{eduPw},#{libPw},#{ssoPw})")
    void create(Student student);

    @Update("update user set username=#{username},ecardpw=#{ecardPw},edupw=#{eduPw},libpw=#{libPw},ssopw=#{ssoPw} where openid=#{openid}")
    void uppdate(Student student);



}
