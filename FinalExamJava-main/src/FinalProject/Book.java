package FinalProject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Book {
    int bookId;
    String bookName;
    boolean possibleUse = true;
    Date publishDate;


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean getPossibleUse() {
        return possibleUse;
    }

    public void setPossibleUse(boolean possibleUse) {
        this.possibleUse = possibleUse;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    @Override

    public String toString() {
        return "Book [name =" + bookName + "]";
    }

    public void BookId(int book_id) {
    }
}
