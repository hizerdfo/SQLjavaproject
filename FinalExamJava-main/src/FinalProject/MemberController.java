package FinalProject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberController extends Controller{
    public int command;

    public MemberController(Scanner sc) {
        super(sc);
    }
    MemberManager mm = new MemberManager();
    JDBC jdbc = new JDBC();
    public void doCommand() {
        switch(command) {
            case 0: //뒤로 구현
                System.out.println("뒤로 구현");
                break;
            case 1: //회원 조회
                System.out.println("회원 조회 구현");
                try {
                    searchMembers();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2: //회원 등록
                System.out.println("회원 등록 구현");
                createMember(mm.members);
                System.out.println("회원 등록이 완료되었습니다.");
                break;
            case 3: //회원 수정
                System.out.println("회원 수정 구현");
                try {
                    updateMemberById();
                } catch (SQLException e) {
                    System.out.println("회원 정보 수정 중 오류가 발생했습니다.");
                    e.printStackTrace();
                }
                break;
            case 4: //회원 삭제
                System.out.println("회원 삭제 구현");
                try {
                    deleteMember();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("잘못된 입력입니다.");
                break;
        }
    }

    public void deleteMember() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("삭제할 회원의 이름을 입력하세요: ");
        String name = scanner.nextLine();

        ArrayList<Member> members = jdbc.searchMemberByName(name);

        if (!members.isEmpty()) {
            System.out.println("[검색 결과]");
            for (Member member : members) {
                System.out.println("이름: " + member.getUserName());
                System.out.println("회원 ID: " + member.getMemberId());
                System.out.println("주소: " + member.getUserAddress());
                System.out.println("연락처: " + member.getPhoneNumber());
                System.out.println("책 대여일: " + member.getRegistrationDate());
                System.out.println("생년월일: " + member.getBirthDate());
                System.out.println("--------------------");
            }

            System.out.print("삭제할 회원의 ID를 입력하세요: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();
            jdbc.deleteMemberById(memberId);

        } else {
            System.out.println("해당 이름을 가진 회원이 존재하지 않습니다.");
        }
    }

    public void searchMembers() throws SQLException {
        System.out.println("1. 전체 회원  2. 회원 검색");
        int result = sc.nextInt();
        sc.nextLine();
        ArrayList<Member> members;

        if(result == 1) {
            members = jdbc.searchMemberByName("");
        } else if (result == 2) {
            System.out.println("검색할 회원의 이름을 입력하세요: ");
            String name = sc.nextLine();
            members = jdbc.searchMemberByName(name);
        } else {
            System.out.println("잘못된 입력입니다.");
            return;
        }

        if (members.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        System.out.println("검색 결과:");
        for (Member member : members) {
            System.out.println("이름: " + member.getUserName());
            System.out.println("회원 ID: " + member.getMemberId());
            System.out.println("주소: " + member.getUserAddress());
            System.out.println("연락처: " + member.getPhoneNumber());
            System.out.println("책 대여일: " + member.getRegistrationDate());
            System.out.println("생년월일: " + member.getBirthDate());
            System.out.println("--------------------");
        }
    }

    public void updateMemberById() throws SQLException {
        // 이름 입력 받기
        System.out.print("회원 이름을 입력하세요: ");
        String name = sc.nextLine();
        sc.nextLine();

        // 해당 이름을 가진 모든 회원 정보 출력
        ArrayList<Member> members = jdbc.searchMemberByName(name);
        if (members.isEmpty()) {
            System.out.println("해당 이름을 가진 회원이 존재하지 않습니다.");
            return;
        }
        System.out.println("검색 결과:");

        // 수정할 회원의 MEMBER_ID 입력 받기
        System.out.print("수정할 회원의 MEMBER_ID를 입력하세요: ");
        int memberId = sc.nextInt();
        sc.nextLine();

        // 어떤 정보를 수정할지 선택하기
        System.out.println("어떤 정보를 수정하시겠습니까?");
        System.out.println("1. 이름 수정");
        System.out.println("2. 주소 수정");
        System.out.println("3. PHONENUM 수정");
        int option = sc.nextInt();
        sc.nextLine();

        // 선택한 정보에 따라 수정할 값을 입력 받기
        String value = "";
        switch (option) {
            case 1:
                System.out.print("새로운 이름을 입력하세요: ");
                value = sc.nextLine();
                jdbc.updateMember(memberId, "MEMBER_NAME", value);
                break;
            case 2:
                System.out.print("새로운 주소를 입력하세요: ");
                value = sc.nextLine();
                jdbc.updateMember(memberId, "MEMBER_ADDRESS", value);
                break;
            case 3:
                System.out.print("새로운 전화번호를 입력하세요: ");
                value = sc.nextLine();
                jdbc.updateMember(memberId, "MEMBER_PHONENUM", value);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
                return;
        }

    }



    public void createMember(ArrayList<Member> members) {
        Member member = new Member();

        System.out.println("회원 이름을 입력하세요.");
        member.setUserName(sc.next());
        sc.nextLine();
        System.out.println("회원 주소를 입력하세요.");
        member.setUserAddress(sc.nextLine());

        System.out.println("회원 전화번호를 입력하세요.");
        member.setPhoneNumber(sc.nextLine());

        System.out.println("회원 생년월일을 입력하세요. (YYYY-MM-DD)");
        member.birthDate(sc.nextLine());

        try {
            jdbc.insertMember(member);
            System.out.println("회원 등록이 완료되었습니다.");
        } catch (SQLException e) {
            System.out.println("회원 등록 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

    }





}
