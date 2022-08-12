package org.woorin.catudy;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.model.DocumentDTO;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.PostDTO;
import org.woorin.catudy.model.RoomDTO;
import org.woorin.catudy.service.MemberService;
import org.woorin.catudy.service.PostService;
import org.woorin.catudy.service.RoomService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatudyApplicationTests {
	@Autowired private MainMapper mapper;
	@Autowired private MemberService memberService;
	@Autowired private PostService   postService;
	@Autowired private RoomService   roomService;

	@Disabled
	@Test
	void contextLoads() {
	}
	
	//// 회원 추가
	@Disabled
	@Test
	void member_regist() {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
		MemberDTO member = new MemberDTO();
		member.setMember_id("youngsun0401");
		member.setMember_pw("testPassword");
		member.setMember_comment("열공합니다.");
		member.setMember_nick("영선이");
		memberService.member_regist(member);
	}
	
	//// 회원 삭제
	@Disabled
	@Test
	void member_delete() {
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb");
		memberService.member_delete(3);
	}
	
	
	//// 게시글 조회
	@Disabled
	@Test
	void post_select() {
		System.out.println("AAAAAAAAAAAAAAAAA");
//		PostDTO post = mapper.post_select(1);
		PostDTO post = postService.get_post(1);
		System.out.println(post);
		System.out.println(post.getPost_writer());
		System.out.println(post.getComments());
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQ");
	}
	
	
	//// 서랍글 조회
	@Disabled
	@Test
	void documnet_select() {
		System.out.println("AAAAAAAAAAAAAAAAA");
		DocumentDTO document = mapper.document_select(1);
		System.out.println(document);
		System.out.println(document.getDocument_writer());
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQ");
	}
	
	
	//// 스터디방 조회
	@Test
	void getRoom() {
		System.out.println("AAAAAAAAAAAAAAAAA");
		List<RoomDTO> list = roomService.getRooms_latest(1, 5, true);
		for( RoomDTO dto: list ) {
			System.out.println(dto);
		}
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQ");
	}
	
}
