package com.example.spring01.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring01.model.vo.ProductVO;

// 컨트롤러 어노테이션(컨트롤러 객체를 자동으로 생성)
@Controller
public class MainController {
	// 로그 객체 생성
	private static final Logger logger = 
			LoggerFactory.getLogger(MainController.class);
	
	// 시작 페이지로 이동
	@RequestMapping("/")	// url pattern mapping
	public String main(Model model) {
		// Model : 데이터를 담는 그릇 역할, map 구조로 저장됨
		// model.addAttribute("변수명", 값)
		model.addAttribute("message", "홈페이지 방문을 환영합니다.");
		// <beans:property name="prefix" value="/WEB-INF/views/" />
		// <beans:property name="suffix" value=".jsp" />
		// /WEB-INF/views/main.jsp
		return "main";	// main.jsp로 포워딩됨
	}
	
	@RequestMapping(value = "gugu.do", method = RequestMethod.GET)
	public String gugu(@RequestParam(defaultValue = "3")int dan, Model model) { 

		String result = "";
		for(int i = 1; i <= 9; i++) {
			result += dan + "x" + i + " = " + dan*i + "<br>";
		}
		model.addAttribute("result", result);
		
		// gugu.jsp로 포워딩
		// <beans:property name="prefix" value="/WEB-INF/views/" />
		// <beans:property name="suffix" value=".jsp" />
		return "test/gugu";
		// /WEB-INF/views/gugu.jsp
	}
	
	// 리턴 타입이 void인 경우 RequestMapping과 동일한 페이지로 넘어감
	@RequestMapping("test")
	public void test() {
		
	}
	
	@RequestMapping("test/doA")
	public String doA(Model model) {
		logger.info("doA called...");	// 로그 출력
		model.addAttribute("message", "홈페이지 방문을 환영합니다.");
		// 리턴 타입이 void이면 method가 종료된 후 doA.jsp로 포워드
		return "test/doB";	// doB.jsp로 포워딩됨
	}
	
	@RequestMapping("test/doB") 
	public void doB() {
		logger.info("doB called...");
	}
	
	// ModelAndView : Model - 데이터 저장소, View 화면
	// 데이터와 포워드할 페이지의 정보
	// forward : 주소 그대로, 화면 전환, 대량의 데이터 전달
	// redirect : 주소 바뀜, 화면 전환, 소량의 get 방식 데이터
	@RequestMapping("test/doC")
	public ModelAndView doC() {
		Map<String, Object> map = new HashMap<>();
		// 맵에 객체 저장
		map.put("product", new ProductVO("샤프", 1000));
		// new ModelAndView("view의 이름", "맵변수명", 맵);
		return new ModelAndView("test/doC", "map", map);
	}
	
	@RequestMapping("test/doD")
	public String doD() {
		return "redirect:/test/doE";
	}
	
	@RequestMapping("test/doE")
	public void doE() {}
	
}


















