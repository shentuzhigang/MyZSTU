package club.zstuca.myzstu.mapper;

import club.zstuca.myzstu.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * API接口,用于测试
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    Student findStudentById(String openId);
}
