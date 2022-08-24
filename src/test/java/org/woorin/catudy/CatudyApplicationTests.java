package org.woorin.catudy;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.woorin.catudy.mapper.MainMapper;
import org.woorin.catudy.mapper.MemberMapper;
import org.woorin.catudy.mapper.MemberRoomAttendanceMapper;
import org.woorin.catudy.model.AttendDTO;
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
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberRoomAttendanceMapper attendance;

	@Test
	void contextLoads() {

		int room_no = 1;
		int member_no = 1;

		// 공부 시작(출석), 데이터 생성
		// attendance.studyStart(room_no, member_no);
		
		// 공부 종료(퇴실)
		// attendance.studyEnd(room_no, member_no, new Time(3600000));
		
		/// 아래 기능은 사용되지 않을 수 있습니다.
		
		// 유저 기록 삭제
		attendance.deleteMemberAttendance(member_no);
		
		// 방 기록 삭제
		// attendance.deleteRoomAttendance(room_no);
		
		// 한 방에 속한 한 유저의 출석정보를 전부 삭제
		// attendance.deleteMemberRoomAttendance(room_no, member_no);
		
		// 한 유저의 출석정보를 조회
		System.out.println(attendance.getMemberRoomAttendance(room_no, member_no));
	}
	
// 	//// 회원 추가
// 	@Disabled
// 	@Test
// 	void member_regist() {
// 		System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
// 		MemberDTO member = new MemberDTO();
// 		member.setMember_id("youngsun0401");
// 		member.setMember_pw("testPassword");
// 		member.setMember_comment("열공합니다.");
// 		member.setMember_nick("영선이");
// //		memberService.member_regist(member);
// 	}
	
// 	//// 회원 삭제
// 	@Disabled
// 	@Test
// 	void member_delete() {
// 		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb");
// 		memberService.member_delete(3);
// 	}
	
	
// 	//// 서랍글 조회
// 	@Disabled
// 	@Test
// 	void documnet_select() {
// 		System.out.println("AAAAAAAAAAAAAAAAA");
// 		DocumentDTO document = mapper.document_select(1);
// 		System.out.println(document);
// 		System.out.println(document.getDocument_writer());
// 		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQ");
// 	}
	
	
// 	//// 스터디방 조회
// 	@Disabled
// 	@Test
// 	void getRoom() {
// 		System.out.println("TEST ===================== getRoom");
// 		List<RoomDTO> list = roomService.getRooms_latest(1, 5, true);
// 		for( RoomDTO dto: list ) {
// 			System.out.println(dto);
// 		}
// 	}
	
	
// 	//// 게시글 조회
// //	@Disabled
// 	@Test
// 	void post_select() {
// 		System.out.println("TEST ===================== post_select");
// //		PostDTO post = mapper.post_select(1);
// 		PostDTO post = postService.get_post(1);
// 		System.out.println(post);
// 		System.out.println(post.getPost_writer_dto());
// 		System.out.println(post.getComments());
// 	}
	
// 	//// 특정 카테고리 게시글 목록 조회
// 	@Disabled
// 	@Test
// 	void getPosts() {
// 		System.out.println("TEST ===================== getPosts");
// 		List<PostDTO> list = postService.get_posts("all", 1);
// 		for( PostDTO dto: list ) {
// 			System.out.println(dto);
// 		}
// 	}
// 	@Disabled
// 	@Test
// 	void getPosts2() {
// 		System.out.println("TEST ===================== getPosts2");
// 		List<PostDTO> list = postService.get_posts("rec", 1);
// 		for( PostDTO dto: list ) {
// 			System.out.println(dto);
// 		}
// 	}
	
}
