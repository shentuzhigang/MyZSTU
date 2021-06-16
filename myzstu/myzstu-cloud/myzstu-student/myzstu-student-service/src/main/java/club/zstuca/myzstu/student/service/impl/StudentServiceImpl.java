package club.zstuca.myzstu.student.service.impl;

import club.zstuca.myzstu.student.entity.Student;
import club.zstuca.myzstu.student.mapper.StudentMapper;
import club.zstuca.myzstu.student.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 学生信息 服务实现类
 *
 * @author ShenTuZhiGang
 * @version 1.0.0
 * @email 1600337300@qq.com
 * @date 2021-06-14 00:20
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService{

}
