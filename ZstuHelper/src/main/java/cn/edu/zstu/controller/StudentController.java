package cn.edu.zstu.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zstu.ApiResponse;
import cn.edu.zstu.Consts;
import cn.edu.zstu.GradeDTO;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.service.StudentService;


@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value="/courses",method= {RequestMethod.POST})
	@ResponseBody
	public Object getCourses(@RequestParam("sid") String sid, @RequestParam("edupw") String edupw){
		ApiResponse res = Consts.getRetTemp();
		Student stu = new Student();
		stu.setSid(sid);
		stu.setEduPw(edupw);
		try {
			res.setData(studentService.getCourses(stu));
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;	
	}
	@RequestMapping(value="/grades",method= {RequestMethod.POST})
	@ResponseBody
	public Object getGrades(@RequestParam("sid") String sid, @RequestParam("edupw") String edupw){
		GradeDTO res = new GradeDTO();
		Student stu = new Student();
		stu.setSid(sid);
		stu.setEduPw(edupw);
		try {
			res = studentService.getGrades(stu);
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;
	}
	@RequestMapping(value="/exams",method= {RequestMethod.POST})
	@ResponseBody
	public Object getExams(@RequestParam("sid") String sid, @RequestParam("edupw") String edupw){
		ApiResponse res = Consts.getRetTemp();
		Student stu = new Student();
		stu.setSid(sid);
		stu.setEduPw(edupw);
		try {
			res.setData(studentService.getExams(stu));
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;
		
	}
//	@RequestMapping(value="/balance",method= {RequestMethod.POST})
//	@ResponseBody
//	public Object getYktBalance(@RequestParam("sid") String sid,@RequestParam("ecardpw") String ecardpw) throws ParseException, ZstuHelperException {
//		ApiResponse ret = Consts.getRetTemp();
//		Student stu = new Student();
//		stu.setSid(sid);
//		stu.setEcardPw(ecardpw);
//		ret.setData(studentService.getBalance(stu));
//		return ret;
//		 
//	}
//	@RequestMapping(value="/consumption",method= {RequestMethod.POST})
//	@ResponseBody
//	public Object getConsumption(@RequestParam("sid") String sid,@RequestParam("ecardpw") String ecardpw,@RequestParam("start") String start,@RequestParam("end") String end) throws ParseException, ZstuHelperException {	
//		ApiResponse ret = Consts.getRetTemp();
//		Student stu = new Student();
//		stu.setSid(sid);
//		stu.setEcardPw(ecardpw);
//		ret.setData(studentService.getConsumptions(stu, start, end));
//		return ret;
//	}
//	@RequestMapping(value="/borrows",method= {RequestMethod.POST})
//	@ResponseBody
//	public Object getBorrowList(@RequestParam("sid") String sid,@RequestParam("libpw") String libpw) throws ParseException, ZstuHelperException{	
//		ApiResponse ret = Consts.getRetTemp();
//		Student stu = new Student();
//		stu.setSid(sid);
//		stu.setLibPw(libpw);
//		ret.setData(studentService.getBookRecord(stu));
//		return ret;		 
//	}
//	@RequestMapping(value="/book",method= {RequestMethod.POST})
//	@ResponseBody
//	public Object getBookInfo(@RequestParam("book") String book) {	
//		ApiResponse ret = Consts.getRetTemp();
//		ret.setData(studentService.getBooks(book));
//		return ret;	 
//	}
	@RequestMapping(value="/firstday",method= {RequestMethod.GET})
	@ResponseBody
	public Object getFirstDay() throws IOException {	
		ApiResponse ret = Consts.getRetTemp();
		ret.setData(Consts.getFirstDay());
		return ret;
	}
	@RequestMapping(value="/phones",method= {RequestMethod.GET})
	@ResponseBody
	public Object getPhones() throws IOException {	
		ApiResponse ret = Consts.getRetTemp();
		ret.setData(Consts.getPhones());
		return ret;	 
	}
}
