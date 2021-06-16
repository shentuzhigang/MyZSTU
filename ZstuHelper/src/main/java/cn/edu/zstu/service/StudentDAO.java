package cn.edu.zstu.service;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cn.edu.zstu.beans.Student;
/**
 * API接口,用于测试
 *
 */
@Repository
public class StudentDAO extends SqlSessionDaoSupport{

    @Autowired
	public void setSuperSessionFactory(SqlSessionFactory sessionFactory){
        this.setSqlSessionFactory(sessionFactory);
    }
	public void doCreate(Student stu) {
		this.getSqlSession().insert("doCreate",stu);
	}

	public void doUpdate(Student stu) {
		this.getSqlSession().update("doUpdate",stu);		
	}

	public Student findStudentById(String openId) {
		Student stu = null;
		stu = this.getSqlSession().selectOne("getStudentById", openId);
		return stu;		
	}	
}
