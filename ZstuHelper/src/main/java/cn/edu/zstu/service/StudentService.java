package cn.edu.zstu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.newedu.ZFsoft;
import cn.edu.zstu.GradeDTO;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Book;
import cn.edu.zstu.beans.BookRecord;
import cn.edu.zstu.beans.Course;
import cn.edu.zstu.beans.Exam;
import cn.edu.zstu.beans.Grade;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.spyder.EcardSpyder;
import cn.edu.zstu.spyder.EduSpyder;
import cn.edu.zstu.spyder.LibSpyder;
import cn.edu.zstu.spyder.LibSpyderB;

@Service
public class StudentService{
	
    @Autowired
    private EduSpyder eduSpyder;
    
    @Autowired
    private EcardSpyder ecardSpyder;
    
    @Autowired
    private LibSpyder libSpyder;
    
    @Autowired
    private LibSpyderB libSpyderB;
    
	@Autowired
	private StudentDAO studentDao;
	/**
	 * 
	 * @param openId
	 * @return
	 */
	public Student findStudentById(String openId) throws ZstuHelperException{
		Student stu = studentDao.findStudentById(openId);
		if(stu == null || "".equals(stu.getSid())) {
			throw new ZstuHelperException("201|请补全个人信息");
		}
		return stu;
	}
	/**
	 * 添加一个学生，如果不存在添加，如果已存在，则更新
	 * @param stu
	 * @return
	 */
	public Map<String,Object> saveStudent(Student stu) {
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			Student s = studentDao.findStudentById(stu.getOpenid());
			if (s == null) {
				studentDao.doCreate(stu);
			} else {
				studentDao.doUpdate(stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;

	}
	
	public List<Course> getCourses(Student stu) throws ZstuHelperException{
		List<Course> courses = eduSpyder.getCourses(stu);
		System.out.println(courses);
		ArrayList<Course> res = new ArrayList<>();
		for(int i = 0; i < courses.size(); i ++) {
			boolean isAdd = true;
			Course itema = courses.get(i);
			for(int j = 0; j < res.size(); j ++) {
				Course itemb = res.get(j);
				if(itema.getType().equals("实践课") && itema.getType().equals(itemb.getType()) && itema.getName().equals(itemb.getName())) {
					isAdd = false;
					break;
				}else if(itema.getType().equals("一般课") && itema.getType().equals(itemb.getType()) && itema.getName().equals(itemb.getName()) && itema.getDay().equals(itemb.getDay()) && itema.getWeek().equals(itemb.getWeek())) {
					res.remove(j);
					itema.setLength(Integer.parseInt(itema.getLength()) + Integer.parseInt(itemb.getLength()) + "");
					itema.setPeriod(Integer.parseInt(itema.getPeriod()) >= Integer.parseInt(itemb.getPeriod()) ? itemb.getPeriod() : itema.getPeriod());
				}
			}
			if(isAdd) res.add(itema);
		}
		return res;
	}

	public GradeDTO getGrades(Student stu) throws ZstuHelperException{
		GradeDTO dto = new GradeDTO();
		dto.setCode("200");
		dto.setMsg("success");
		//List<Grade> data = eduSpyder.getGrades(stu);
		
		ZFsoft zFsoft=new ZFsoft();
		List<Grade> data = zFsoft
                .login(stu.getSid(),stu.getEduPw())
                .checkScore("","");
        
		Iterator<Grade> it=data.iterator();
		while(it.hasNext()){
		    Grade x = it.next();
		    if(x.getGrade().equals("放弃")){
		        it.remove();
		    }
		}
		Set<String> years = new LinkedHashSet<String>();
		String time = "";
		for(int i=0;i<data.size();i++) {
			time=data.get(i).getYear()+"-"+data.get(i).getTerm();
			years.add(time);
		}
		dto.setData(data);
		dto.setYears(years);
		return dto;
	}

	public List<Exam> getExams(Student stu) throws ZstuHelperException{
		return eduSpyder.getExams(stu);
	}
	
	public Map<String, Object> getCardInfo(Student stu, String start,String end) throws ZstuHelperException{
		return ecardSpyder.getCardInfo(stu, start, end);
	}

	public List<Book> getBooks(String key){
		return libSpyder.getBookInfo(key);
	}

	public Map<String, List<BookRecord>> getBookRecord(Student stu) throws ZstuHelperException{
		Map<String, List<BookRecord>> res = new HashMap<>();
		try {
			res = libSpyderB.getBorrowList(stu.getSid());
		} catch (ZstuHelperException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
