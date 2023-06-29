package honda.bookworm.Object;

import java.util.ArrayList;
import java.util.Objects;

public class Author extends User {
    private ArrayList<Book> writtenBooks;
    private int authorID;

    public Author(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
        writtenBooks = new ArrayList<>();
    }

    public Author(String firstName, String lastName, String username, String password, int authorID) {
        super(firstName, lastName, username, password);
        this.authorID = authorID;
        writtenBooks = new ArrayList<>();
    }

    public ArrayList<Book> getWrittenBooks() {
        return writtenBooks;
    }

    public void addWrittenBook(Book book) {
        writtenBooks.add(book);
    }

    public void removeWrittenBook(Book book) {
        writtenBooks.remove(book);
    }

    public int getAuthorID() {return authorID;}

    public void setAuthorID(int authorID) {this.authorID = authorID;}

    public String toString() {
        return "Author: " +
                "firstName:'" + getFirstName() + '\'' +
                ", lastName:'" + getLastName() + '\'' +
                ", username:'" + getUsername() + '\'' +
                ", password:'" + getPassword() + '\''
                ;
    }

    public boolean equals(Object compare) {
        if (this == compare) {
            return true;
        }

        if (!(compare instanceof Author)) {
            return false;
        }

        if (!super.equals(compare)) {
            return false;
        }

        Author author = (Author) compare;

        return Objects.equals(writtenBooks, author.writtenBooks) && (authorID == author.getAuthorID());
    }
}
