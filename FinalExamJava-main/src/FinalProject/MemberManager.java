package FinalProject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemberManager {
    MemberManager() { testData();}

    ArrayList<Member> members = new ArrayList<>();
    public ArrayList<Member> getMembers(){ return members; }

    public void addMember(Member member){
        members.add(member);
    }

    public void testData(){
        Member member1 = new Member();
        member1.userName = "김하영";
        member1.userAddress = "광명";
        member1.phoneNumber = "01034807095";
        member1.registrationDate("2023-03-24");
        member1.birthDate("2000-02-15");


        Member member2 = new Member();
        member2.userName = "홍길동";
        member2.userAddress = "철산";
        member2.registrationDate("2023-03-02");
        member2.birthDate("2002-01-24");

        Member member3 = new Member();
        member3.userName = "김기루";
        member3.userAddress = "역곡";
        member3.registrationDate("2022-03-02");
        member3.birthDate("1997-02-03");

        members.add(member1);
        members.add(member2);
        members.add(member3);

    }
}
