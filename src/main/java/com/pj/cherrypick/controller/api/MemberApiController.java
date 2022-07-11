package com.pj.cherrypick.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pj.cherrypick.service.MailService;
import com.pj.cherrypick.service.MemberService;

@Controller
public class MemberApiController {
	
	@Autowired
	private MemberService memberService; // 스프링이 컴포넌트 스캔을 통해 MemberService.java의 @Service 어노테이션을 보면 Bean에 등록을 해 줌 (=IOC 해줌)
	
	@Autowired
	private MailService mailService; 
	
	@PostMapping("/auth/findUsernameProc")
	public String findUsername(@RequestParam("name") String name, @RequestParam("email") String email, Model model) throws Exception {
//		System.out.println("name : " + name);
//		System.out.println("email : " + email);
		
		/*아이디 3자리 이후부터 잘라 *로 처리하기*/
		String rawUsername = memberService.findUsername(name, email);
		int rawUsernameLen = rawUsername.length(); // 아이디 글자수
		String rawUsernameStart = rawUsername.substring(0, 3); // 시작위치, 종료위치 // 그대로 나타낼 부분
		String rawUsernameEnd = rawUsername.substring(3, rawUsernameLen); // * 표시할 부분
		
		String encUsernameEnd = "";
		for (int i = 0; i < rawUsernameEnd.length(); i++) {
			encUsernameEnd+="*"; // * 처리
		}

		String encUsername = rawUsernameStart + encUsernameEnd; // 일부 *로 변환된 아이디
		
//		System.out.println("encUsername : " + encUsername);
		
		model.addAttribute("username", encUsername);
		return "member/findUsernameResult";
	}
	
	@PostMapping("/auth/sendEmailProc")
	public String sendEmailProc(@RequestParam String username, @RequestParam String email) {
		String tmpPassword = memberService.getTmpPassword(); // 임시비번 생성
		
		memberService.updatePassword(tmpPassword, username, email); // Service단에 임시비번 전달하면 해쉬로 암호화 거쳐서 업데이트해줌
		
		mailService.sendEmail(email, tmpPassword); // 이메일로 임시비번 전송
		
		return "member/loginForm";
	}
	
	@GetMapping("/member/memberEditForm")
	// http://localhost/auth/findPwdForm
	public String memberEditForm() {
		return "member/memberEditForm";
	}
		
	
}
