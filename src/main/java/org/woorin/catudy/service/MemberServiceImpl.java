package org.woorin.catudy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.mapper.MemberMapper;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.RoomDTO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MainMapper mapper;

	@Autowired
	private MemberMapper member;

	@Autowired
	private BCryptPasswordEncoder pwEncoder;


	// 회원 등록
	@Override
	public void member_insert(MemberDTO dto) {

		System.out.println("회원가입 - 비밀번호 암호화 중.");
		dto.setMember_pw(pwEncoder.encode(dto.getMember_pw()));
		mapper.member_insert(dto);
		System.out.println("회원가입 및 비밀번호 암호화 완료.");
	}
	// 회원가입 아이디 중복 체크
	@Override
	public int memberIdCheck(String member_id) {
		int cnt = mapper.memberIdCheck(member_id);
		return cnt;
	}


	// 회원가입 닉네임 중복 체크
	@Override
	public int memberNickCheck(String member_nick) {
		int nickCnt = mapper.memberNickCheck(member_nick);
		return nickCnt;
	}
	// 회원 목록
	@Override
	public List<MemberDTO> member_select() {
		return mapper.member_select();
	}

	// 회원 삭제
	@Override
	public void member_delete(Integer member_no) {
	}


	// 회원 로그인
	@Override
	public MemberDTO member_login(MemberDTO dto) {
		String resultPW = mapper.memberRealPassword( dto.getMember_id() );// 입력된 이메일로 회원정보 찾기
		boolean loginFilter = pwEncoder.matches(dto.getMember_pw(), resultPW);
		System.out.println(loginFilter);
		System.out.println("resultPW 값 : " + resultPW);

		if (loginFilter) {

			MemberDTO resultID = mapper.memberOneSelect(dto.getMember_id());

			return resultID;
		} else {
			return null;
		}

	}

	@Override
	public MemberDTO member_find(int member_no) {
		// 조회 시 멤버의 번호만 오게 되는데, 번호를 다시 멤버의 전체 정보를 가져온다.
		return member.member_find(member_no);
	}

	// 회원번호 → 닉네임
	@Override
	public String member_nick(int member_no) {
		return member_find(member_no).getMember_nick();
	}

	@Override
	public List<MemberDTO> member_list_on_a_room(int room_no) {
		// room_no 방의 참여자 번호들을 가져옵니다.
		ArrayList<Integer> member_no_list = member.attended_member(room_no);

		// 가져온 참여자 번호로 유저 정보를 가져옵니다.
		List<MemberDTO> member_list_on_a_room = new ArrayList<MemberDTO>();
		member_no_list.forEach((member_no) -> member_list_on_a_room.add(member.member_find(member_no)));
		return member_list_on_a_room;
	}


}
