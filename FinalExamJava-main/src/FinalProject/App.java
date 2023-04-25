package FinalProject;



import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);
    BookController bm = new BookController(sc);
    MemberController mm = new MemberController(sc);
    JDBC jdbc = new JDBC(); // JDBC 객체 생성
//    String cmd ="";

    void start() {
        while(true) {
            //jdbc.queryCustomerTable();
            System.out.println("기능을 선택해주세요. 1. 회원관리 2. 도서관리  3. 종료");
            int target = sc.nextInt();
            if(target == 1 || target == 2 ) {
                while (true) {
                    if (target == 1) {
                        System.out.println("0. 뒤로 1. 회원 조회 2. 회원 등록 3. 회원 수정 4. 회원 삭제 5. 삭제 취소");
                    } else if (target == 2) {
                        System.out.println("0. 뒤로 1. 책 조회 2. 책 등록  3. 책 삭제  4. 대출 가능한 도서 조회 5. 책 대출");
                    } else {
                        System.out.println("잘못된 명령어입니다.");
                        break;
                    }
                    int target2 = sc.nextInt();
                    if (target2 == 0){
                        break;
                    } else if (target == 1) {
                        mm.command = target2;
                        mm.doCommand();
                    } else if (target == 2) {
                        bm.command = target2;
                        bm.doCommand();
                    }
                    else if (target == 4) {
                        System.out.println("도서 대출 프로그램 종료");
                        break;
                    } else {
                        System.out.println("잘못된 명령어입니다.");
                        break;
                    }
                }
            } else if (target == 3) {
                System.out.println("종료합니다.");
                break;
            } else {
                System.out.println("잘못된 명령어입니다.");
                break;
            }

        }

    }
}
