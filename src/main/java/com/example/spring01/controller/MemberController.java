package com.example.spring01.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spring01.model.vo.MemberVO;
import com.example.spring01.service.MemberService;

@Controller	// 현재 클래스를 Controller Bean으로 등록함
public class MemberController {
	private static final Logger logger=
			LoggerFactory.getLogger(MemberController.class);
	
	@Inject	// MemberService 객체가 주입됨
	MemberService memberService;
	
	@RequestMapping("member/list.do")	// 사용자가 요청하는 주소
	public String memberList(Model model) {
		List<MemberVO> list = memberService.memberList();
		logger.info("회원 목록 : " + list);
		model.addAttribute("list", list);	// 모델에 저장
		return "member/member_list";	// 출력 페이지로 포워딩
	}
	
	@RequestMapping("member/write.do")
	public String write() {
		return "member/write";
	}
	
	// 폼에 입력한 데이터가 MemberVO vo 변수에 저장됨
	// request.getParameter 생략
	@RequestMapping("member/insert.do")
	public String insert(MemberVO vo) {
		memberService.insertMember(vo);
		return "redirect:/member/list.do";	// 목록 갱신
	}
	
	// view.do?userid=kim 이라면
	// RequestParam String userid 변수에 kim이 저장됨
	@RequestMapping("member/view.do")
	public String view(@RequestParam String userid, 
						Model model) {
		model.addAttribute("vo", memberService.viewMember(userid));
		return "member/view";	// view.jsp로 포워딩
	}
	
	@RequestMapping("member/update.do")
	public String update(@ModelAttribute MemberVO vo, Model model) {
		boolean result = memberService.checkPw(vo.getUserid(), vo.getPasswd());
		logger.info("비밀번호 확인 : " + result);
		
		// 비밀번호가 맞으면
		if(result) {
			memberService.updateMember(vo);	// 레코드 수정
			return "redirect:/member/list.do";	// 목록으로 이동
			
		// 비밀번호가 틀리면
		} else {
			MemberVO vo2 = memberService.viewMember(vo.getUserid());
			vo.setJoin_date(vo2.getJoin_date()); // 날짜가 지워지지 않도록
			model.addAttribute("vo", vo);
			model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
			return "member/view";	// 수정 페이지로 되돌아감
		}
		
	}
	
	@RequestMapping("member/delete.do")
	public String delete(@RequestParam String userid,
						@RequestParam String passwd,
						Model model) {
		boolean result = memberService.checkPw(userid, passwd);
		if(result) {
			memberService.deleteMember(userid);
			return "redirect:/member/list.do";
			
		} else {
			model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("vo", memberService.viewMember(userid));
			return "member/view";
			
		}
	}
}
