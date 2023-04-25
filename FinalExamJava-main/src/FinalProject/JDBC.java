package FinalProject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JDBC {
    private static Connection conn;
    Scanner sc = new Scanner(System.in);


    public JDBC() {
        try {
            conn = getConnection();


        } catch (SQLException e) {
            System.out.println("JDBC 연결 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:oracle:thin:@192.168.119.119:1521/dink";
            String user = "scott";
            String password = "tiger";
            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }

    public void insertMember(Member member) throws SQLException {
        String sql = "INSERT INTO MEMBER_LIST (MEMBER_ID,MEMBER_NAME,MEMBER_ADDRESS,MEMBER_PHONENUM,MEMBER_BIRTH,BRROW,BRROW_DATE) " +
                "VALUES (MEMBER_LIST_SEQ.NEXTVAL, ?, ?, ?, ?, 0, null)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, member.getUserName());
        pstmt.setString(2, member.getUserAddress());
        pstmt.setString(3, member.getPhoneNumber());
        java.sql.Date birthDate = new java.sql.Date(member.getBirthDate().getTime());
        pstmt.setDate(4, birthDate);
        pstmt.executeUpdate();
    }

    public ArrayList<Member> searchMemberByName(String name) throws SQLException {
        String sql;
        if (name.length() == 0) {
            sql = "SELECT * FROM MEMBER_LIST";
        } else {
            sql = "SELECT * FROM MEMBER_LIST WHERE MEMBER_NAME LIKE ?";
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (name.length() > 0) {
            pstmt.setString(1, "%" + name + "%");
        }
        ResultSet rs = pstmt.executeQuery();

        ArrayList<Member> members = new ArrayList<>();

        while (rs.next()) {
            Member member = new Member();
            member.setMemberId(rs.getInt("MEMBER_ID"));
            member.setUserName(rs.getString("MEMBER_NAME"));
            member.setUserAddress(rs.getString("MEMBER_ADDRESS"));
            member.setPhoneNumber(rs.getString("MEMBER_PHONENUM"));
            member.setRegistrationDate(rs.getDate("BRROW_DATE"));
            member.setBirthDate(rs.getDate("MEMBER_BIRTH"));
            members.add(member);
        }

        if (members.isEmpty()) {
            System.out.println("해당 이름을 가진 회원이 존재하지 않습니다.");
        }

        return members;
    }
    public void updateMember(int memberId, String columnName, String value) {
        try {
            String sql = "UPDATE MEMBER_LIST SET " + columnName + "=? WHERE MEMBER_ID=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);
            pstmt.setInt(2, memberId);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("회원 정보가 수정되었습니다.");
            } else {
                System.out.println("회원 정보 수정에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("회원 정보 수정에 실패하였습니다..");
            e.printStackTrace();
        }
    }

    public void deleteMemberById(int memberId) throws SQLException {
        String sql = "DELETE FROM MEMBER_LIST WHERE MEMBER_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, memberId);
        int count = pstmt.executeUpdate();
        if (count > 0) {
            System.out.println("회원 ID " + memberId + "번이 삭제되었습니다.");
        } else {
            System.out.println("회원 ID " + memberId + "번을 삭제하는 데 실패했습니다.");
        }
        System.out.println("삭제 취소: 1      삭제 확인: 0");
        int result = sc.nextInt();
        if(result == 1){
            conn.rollback();
            System.out.println("삭제가 취소되었습니다.");
        } else {
            conn.commit();
            System.out.println("삭제가 완료되었습니다.");
        }
    }


    // Book JDBC
    public void insertBook(Book book) throws SQLException {
        String sql = "INSERT INTO BOOK_LIST(BOOK_ID, BOOK_NAME, BRROW, BOOK_DATE) "+
                "VALUES (BOOK_LIST_SEQ.NEXTVAL, ?, 0, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, book.getBookName());
        java.sql.Date publishDate = new java.sql.Date(book.getPublishDate().getTime());
        pstmt.setDate(2, publishDate);
        pstmt.executeUpdate();
    }

    public ArrayList<Book> searchAvailableBooks() throws SQLException {
        String sql = "SELECT * FROM BOOK_LIST WHERE BRROW = 0";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        ArrayList<Book> books = new ArrayList<>();

        while (rs.next()) {
            Book book = new Book();
            book.BookId(rs.getInt("BOOK_ID"));
            book.setBookName(rs.getString("BOOK_NAME"));
            book.setPublishDate(rs.getDate("BOOK_DATE"));
            book.setPossibleUse(rs.getBoolean("BRROW"));
            book.setPublishDate(rs.getDate("BRROW_DATE"));
            books.add(book);
        }

        return books;
    }

    public ArrayList<Book> searchBooksAll() throws SQLException {
        ArrayList<Book> books = new ArrayList<>();

        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM BOOK_LIST");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Book book = new Book();
            book.setBookId(rs.getInt("BOOK_ID"));
            book.setBookName(rs.getString("BOOK_NAME"));
            book.setPublishDate(rs.getDate("BOOK_DATE"));
            book.setPossibleUse(rs.getBoolean("BRROW"));
            books.add(book);
        }

        return books;
    }

    public ArrayList<Book> searchBookByTitle(String title) throws SQLException {
        String sql;
        if (title.length() == 0) {
            sql = "SELECT * FROM BOOK_LIST";
        } else {
            sql = "SELECT * FROM BOOK_LIST WHERE BOOK_NAME LIKE ?";
        }
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (title.length() > 0) {
            pstmt.setString(1, "%" + title + "%");
        }
        ResultSet rs = pstmt.executeQuery();

        ArrayList<Book> books = new ArrayList<>();

        while (rs.next()) {
            Book book = new Book();
            book.setBookId(rs.getInt("BOOK_ID"));
            book.setBookName(rs.getString("BOOK_NAME"));  // "BOOK_PUBLISHER" 컬럼을 가져와서 출판사 설정
            book.setPublishDate(rs.getDate("BOOK_DATE"));
            book.setPossibleUse(rs.getBoolean("BRROW"));
            books.add(book);
        }

        if (books.isEmpty()) {
            System.out.println("해당 제목을 가진 책이 존재하지 않습니다.");
        }

        return books;
    }
    public void deleteBookById(int bookId) throws SQLException {
        String sql = "DELETE FROM BOOK_LIST WHERE BOOK_ID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bookId);
        int count = pstmt.executeUpdate();
        if (count > 0) {
            System.out.println("책 ID " + bookId + "번이 삭제되었습니다.");
        } else {
            System.out.println("책 ID " + bookId + "번을 삭제하는 데 실패했습니다.");
        }
    }

    public void borrowBookById(int bookId) throws SQLException {
        System.out.println("대출할 회원의 이름을 입력하세요.");
        String name = sc.nextLine();
        ArrayList<Member> members = searchMemberByName(name);
        if (members.isEmpty()) {
            System.out.println("해당 이름을 가진 회원이 존재하지 않습니다.");
        }
        for (Member member : members) {
            System.out.printf("[%d] %s%n", member.getMemberId(), member.getUserName());
        }
        System.out.println("대출할 회원의 ID를 입력하세요.");
        int memberId = sc.nextInt();
        sc.nextLine();  // 버퍼 비우기
        boolean found = false;
        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("해당 ID를 가진 회원이 존재하지 않습니다.");
        }
        PreparedStatement pstmt = conn.prepareStatement("UPDATE BOOK_LIST SET BRROW = ?, BRROW_DATE = ?, BRROW_ID = ? WHERE BOOK_ID = ?");
        pstmt.setBoolean(1, true);  // 대출 불가능으로 변경
        pstmt.setDate(2, new Date(System.currentTimeMillis()));  // 대출 일자 설정
        pstmt.setInt(3, memberId);  // 대출자 ID 설정
        pstmt.setInt(4, bookId);
        int result = pstmt.executeUpdate();
        if (result > 0) {
            System.out.println("책 대출이 완료되었습니다.");

            // BRROWED_BOOKS 테이블에 대출 정보 추가
            int brrowedId = getNextBorrowedId(); // BRROWED_BOOKS 테이블의 ID 값 설정
            pstmt = conn.prepareStatement("INSERT INTO BRROWED_BOOKS (ID, BOOK_ID, MEMBER_ID, BRROW_DATE, DUE_DATE) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, brrowedId);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, memberId);
            pstmt.setDate(4, new Date(System.currentTimeMillis())); // 대출 일자
            pstmt.setDate(5, getDueDate()); // 반납 기한
            pstmt.executeUpdate();
            //대출자의 BRROW 값 변경
            pstmt = conn.prepareStatement("UPDATE MEMBER_LIST SET BRROW = ? WHERE MEMBER_ID = ?");
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, memberId);
            pstmt.executeUpdate();
        } else {
            System.out.println("책 대출에 실패했습니다.");
        }
    }
    // BRROWED_BOOKS 테이블의 ID 값을 반환하는 메소드
    private int getNextBorrowedId() throws SQLException {
        int brrowedId = 1;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM BRROWED_BOOKS");
        if (rs.next()) {
            brrowedId = rs.getInt(1) + 1;
        }
        rs.close();
        stmt.close();
        return brrowedId;
    }

    // 반납 기한을 계산하여 Date 객체로 반환하는 메소드
    private Date getDueDate() {
        long currentTime = System.currentTimeMillis();
        long dueTime = currentTime + (1000L * 60 * 60 * 24 * 7 * 2); // 2주일 후
        return new Date(dueTime);
    }
}