package FinalProject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class BookController extends Controller{
    public int command;
    Scanner sc = new Scanner(System.in);
    public BookController(Scanner sc) {
        super(sc);
    }
    BookManager bm = new BookManager();
    JDBC jdbc = new JDBC();

    public void doCommand() {
        switch (command){
            case 0: //뒤로 기능
                System.out.println("뒤로 기능 구현");
                break;
            case 1: //도서 조회
                System.out.println("도서 조회 구현");
                try{
                    searchBooks();
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case 2: //도서 등록
                try {
                    doAdd(bm.books);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("도서 등록 구현");
                break;
            case 3: //도서 삭제
                try {
                    deleteBook();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4: //대출 도서 조회
                searchBorrowBooks(bm.books);
                break;
            case 5: //대출 기능 구현
                try {
                    borrowBook();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            default:
                break;
        }
    }
    public void borrowBook() throws SQLException {
        System.out.println("대출할 책의 제목을 입력하세요:");
        String title = sc.nextLine();

        ArrayList<Book> books = jdbc.searchBookByTitle(title);
        if (!books.isEmpty()) {
            System.out.println("[검색 결과]");
            for (Book book : books) {
                System.out.println("제목: " + book.getBookName());
                System.out.println("출판사: " + book.getPublishDate());
                System.out.println("책 ID: " + book.getBookId());
                System.out.println("대여 여부: " + (book.getPossibleUse() ? "대여 중" : "대여 가능"));
                System.out.println("--------------------");
            }

            System.out.print("대출할 책의 ID를 입력하세요: ");
            int bookId = sc.nextInt();
            sc.nextLine();

            Book bookToBorrow = null;
            for (Book book : books) {
                if (book.getBookId() == bookId) {
                    bookToBorrow = book;
                    break;
                }
            }

            if (bookToBorrow != null && !bookToBorrow.getPossibleUse()) {
                jdbc.borrowBookById(bookId);
                System.out.println("책이 대출되었습니다.");
            } else {
                System.out.println("해당 책은 대여 중입니다.");
            }

        } else {
            System.out.println("해당 제목을 가진 책이 존재하지 않습니다.");
        }
    }
    public void deleteBook() throws SQLException {
        System.out.print("삭제할 책의 제목을 입력하세요: ");
        String title = sc.nextLine();

        ArrayList<Book> books = jdbc.searchBookByTitle(title);

        if (!books.isEmpty()) {
            System.out.println("[검색 결과]");
            for (Book book : books) {
                System.out.println("제목: " + book.getBookName());
                System.out.println("출판사: " + book.getPublishDate());
                System.out.println("책 ID: " + book.getBookId());
                System.out.println("대여 여부: " + (book.getPossibleUse() ? "대여 가능" : "대여 중"));
                System.out.println("--------------------");
            }

            System.out.print("삭제할 책의 ID를 입력하세요: ");
            int bookId = sc.nextInt();
            sc.nextLine();
            jdbc.deleteBookById(bookId);

        } else {
            System.out.println("해당 제목을 가진 책이 존재하지 않습니다.");
        }
    }

    public void searchBorrowBooks(ArrayList<Book> books){
        System.out.println("대출 가능한 책 목록입니다/");

        for(int i = 0; i < books.size(); i++){
            if(books.get(i).possibleUse){
                System.out.println(books.get(i).bookName);
            }
        }
    }


    public void searchBooks() throws SQLException {
        System.out.println("1. 전체 책  2. 대출 가능한 책 ");
        int result = sc.nextInt();
        sc.nextLine();


        ArrayList<Book> books = new ArrayList<>();

        if(result ==  1) {
            books = jdbc.searchBooksAll();
        } else if(result == 2){
            books = jdbc.searchAvailableBooks();
        }

        if (books.isEmpty()) {
            System.out.println("해당하는 도서를 찾지 못했습니다.");
        } else {
            System.out.println("검색 결과:");
            for (Book book : books) {
                System.out.println("책 ID : " + book.getBookId());
                System.out.println("이름: " + book.getBookName());
                System.out.println("출판일: " + book.getPublishDate());
                System.out.println("대출 유무: " + (book.getPossibleUse() ? "대여 중" : "대여 가능"));
                System.out.println("==========");
            }
        }
    }

    public void doAdd(ArrayList<Book> books) throws ParseException {
        Book book = new Book();

        System.out.println("제목:");
        book.setBookName(sc.nextLine());

        System.out.println("출간 날짜 (yyyy-MM-dd) :");
        String publishDateStr = sc.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date publishDate = dateFormat.parse(publishDateStr);
        book.setPublishDate(publishDate);

        book.possibleUse = true;

        try {
            jdbc.insertBook(book);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("등록이 완료되었습니다");
    }


}
