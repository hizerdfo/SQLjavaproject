package FinalProject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class BookManager {
    BookManager(){
        //testData();
    }

    ArrayList<Book> books = new ArrayList<>();

    public ArrayList<Book> getBooks(){
        return books;
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void testData() {
        Book book1 = new Book();
        book1.bookName = "하나";
        book1.possibleUse = true;

        Book book2 = new Book();
        book2.bookName = "둘";
        book2.possibleUse = true;

        Book book3 = new Book();
        book3.bookName = "셋";
        book3.possibleUse = true;

        Book book4 = new Book();
        book4.bookName = "넷";
        book4.possibleUse = false;

        Book book5 = new Book();
        book5.bookName = "다섯";
        book5.possibleUse = true;

        Book book6 = new Book();
        book6.bookName = "여섯";
        book6.possibleUse = false;

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);



    }


}
