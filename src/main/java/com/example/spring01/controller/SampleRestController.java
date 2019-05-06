package com.example.spring01.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring01.model.vo.ProductVO;

@RestController	// 스프링 4.0부터 사용 가능
public class SampleRestController {

	@ResponseBody	// json 형식으로 리턴
	@RequestMapping("test/doF")	// url mapping
	public ProductVO doF() {
		// 호출한 곳으로 데이터가 넘어감(json 형식)
		return new ProductVO("냉장고", 500000);
	}
	
}
