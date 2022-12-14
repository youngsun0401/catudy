package org.woorin.catudy.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.woorin.catudy.model.MemberDTO;
import org.woorin.catudy.model.RoomDTO;
import org.woorin.catudy.service.MemberRoomAttendServiceImpl;
import org.woorin.catudy.service.ChattingService;
import org.woorin.catudy.service.MemberService;
import org.woorin.catudy.service.RoomService;

@Controller
public class MainController {

	@Autowired
	private RoomService roomService;

    @Autowired
    MemberService memberService;
    @Autowired
    ChattingService chattest;

    @Autowired
    MemberRoomAttendServiceImpl memberRoomAttendService;

    @GetMapping("/")
    public String indexPage(Model model, HttpServletRequest request) {
		System.out.println("HELLO");
		List<RoomDTO> roomList = roomService.room_list();
        List<MemberDTO> memberList = memberService.member_select();
        model.addAttribute("roomList", roomList);
        model.addAttribute("memberList", memberList);

        // 멤버 번호로 참여한 방을 불러옵니다.
        MemberDTO member = new MemberDTO();
        HttpSession session = request.getSession();
        Integer member_no = (Integer)session.getAttribute("member_no");
        System.out.println(member_no);
        if (member_no != null){
            member.setMember_no(member_no);
            model.addAttribute("myRoomList", memberRoomAttendService.getMyRooms(member));
        }


        return "index";
    }
    
    @PostMapping("/memberRoomJoin")
    public String memberRoomJoin(int room_no, HttpSession session){
    	memberRoomAttendService.memberRoomJoin(room_no, loginId(session));
        return "redirect:/";
    }




    // 기능 확인을 위한 임시 페이지입니다.
    @GetMapping("/roomInfo")
    public String roomInfo(@RequestParam("id") int room_no, Model model) {
        RoomDTO aRoom = roomService.getRoom(room_no);
        model.addAttribute("room", aRoom);

        // 방장이름 가져오기
        String host = memberService.member_find(aRoom.getRoom_ruler()).getMember_nick();
        model.addAttribute("host", host);

        // 팀원들 정보 가져오기: 서비스에서 방 번호를 기반으로 attendTBL 테이블에서 정보를 가져옵니다.
        Iterator<MemberDTO> members = memberService.member_list_on_a_room(aRoom.getRoom_no()).iterator();
        ArrayList<String> member_nicks = new ArrayList<String>();
        while(members.hasNext()){
            member_nicks.add(members.next().getMember_nick());
        }
        model.addAttribute("nicks", member_nicks);

        return "room/roomInfo";
    }

    // 기능 확인을 위한 임시 페이지입니다.
    @GetMapping(value="/roomInfo_test")
    public String getMethodName(Model model) {
        RoomDTO room = roomService.getRoom(1);
        model.addAttribute("room", room);
        return  "room/roomInfo";
    }



    // 현재 로그인한 회원 번호(정수) 가져오기
    private static int loginId(HttpSession session) {
        if (session.getAttribute("member_no") == null)
            return 0;
        return Integer.parseInt(session.getAttribute("member_no") + "");
    }
}
