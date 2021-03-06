package com.pj.cherrypick.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pj.cherrypick.domain.BizMemberVO;
import com.pj.cherrypick.mapper.BizMemberMapper;

@Service
public class BizMemberService {

	@Autowired
	private BizMemberMapper bizMemberMapper;
	
	public int findDupUsername(String bid) throws Exception { 
		if(bizMemberMapper.findDupBid(bid) == 1)
			return 1; // 회원가입시 입력한 username으로 DB에 중복되는 username있는지 조회하여 있다면 1 리턴 = 중복Id
		else 
			return 0; 
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void signUp(BizMemberVO bizMember) throws Exception {
		bizMemberMapper.signUp(bizMember);
	}
	
	public String findBid(String bname, String bemail) throws Exception { // 아이디 찾기
		if(bizMemberMapper.findBid(bname, bemail)==null) {
			return "";
		} else {
			return bizMemberMapper.findBid(bname, bemail);
		} 
	}
	
	/* 임시비번으로 비번 재설정 후, 메일로 임시비번 전송하기 */
	
	// 임시비번 생성
	@Transactional(rollbackFor = Exception.class)
    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String tmpPassword = "";

        /* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random()); // 랜덤 정수
            tmpPassword += charSet[idx]; // 배열에서 고르기
        }
        System.out.println("tmpPassword : " + tmpPassword);
        return tmpPassword; // 임시비번 리턴
    }

    // 임시비번으로 업데이트
	@Transactional(rollbackFor = Exception.class)
    public void updatePassword(String tmpPassword, String bid, String bemail) {
        
        try {
        	bizMemberMapper.updateBpwd(tmpPassword, bid, bemail); // 해쉬를 비번으로 업데이트
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
	
	// DB저장된 비번과 회원수정 위해 입력한 비번 비교하기 위해 후자를 같은 방식으로 암호화 하여 비교
    public boolean checkEqualsPassword(String inputPassword, String dbPassword) {
		if(inputPassword.equals(dbPassword)) {
			return true;
		}
		return false;
    }

	public List<BizMemberVO> getMList() {
		List<BizMemberVO> bmList = new ArrayList<BizMemberVO>();
		bmList =  bizMemberMapper.getBMList();
		return bmList;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateMember(BizMemberVO bizMember) {
		BizMemberVO orgMember = bizMemberMapper.findByBid(bizMember.getBid()); // 기존 회원정보 들고오기
		
		/*기존회원정보에 입력받은 수정 데이터 업데이트(따로 변경안하면 기존 회원정보로 셋팅 되도록 설계)*/
		orgMember.setBpwd(bizMember.getBpwd());
		orgMember.setBname(bizMember.getBname());
		orgMember.setBphone(bizMember.getBphone());
		orgMember.setBemail(bizMember.getBemail());
		
		bizMemberMapper.updateBMember(orgMember); // 수정 SQL문 
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void updateMemberWithoutPwd(BizMemberVO bizMember/*입력받은 수정 데이터*/) {
		BizMemberVO orgMember = bizMemberMapper.findByBid(bizMember.getBid()); // username은 불변이므로 기존 회원정보 들고오기
		
		/*기존회원정보에 입력받은 수정 데이터 업데이트(따로 변경안하면 기존 회원정보로 셋팅 되도록 설계)*/
		orgMember.setBname(bizMember.getBname());
		orgMember.setBphone(bizMember.getBphone());
		orgMember.setBemail(bizMember.getBemail());
		
		bizMemberMapper.updateBMemberWithoutPwd(orgMember); // 변경된 회원 객체로 UPDATE SQL문 
		
	}
	
	public BizMemberVO findByUsername(String bid) {
		return bizMemberMapper.findByBid(bid);
	}
	
	public BizMemberVO signIn (String bid, String bpwd) {
		return bizMemberMapper.signIn(bid, bpwd);
	}
	
}
