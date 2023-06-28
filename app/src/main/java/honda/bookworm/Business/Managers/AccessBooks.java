package honda.bookworm.Business.Managers;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class AccessBooks implements IAccessBooks {
    private IBookPersistence bookPersistence;
    private final int MAX_BOOK_TITLE_LENGTH = 40;

    public AccessBooks() {
        bookPersistence = Services.getBookPersistence(false);
    }

    public AccessBooks(IBookPersistence bookPersistence){
        this.bookPersistence = bookPersistence;
    }

    public List<Book> getBooksGenre(Genre genre) {
        if (genre != null) {
            return bookPersistence.getBooksByGenre(genre);
        } else {
            throw new NullPointerException("Genre input can't be null");
        }
    }

    public Book addBook(Book newBook){
        //will do validation of book in another dev task
        if(newBook != null){
            return bookPersistence.addBook(newBook);
        } else {
            throw new NullPointerException("new book can't be empty");
        }
    }

    public String getTrimmedBookName(Book b) {
        String trimmedTitle = b.getName().trim();
        String [] words;

        if (trimmedTitle.length() > MAX_BOOK_TITLE_LENGTH) {
            words = trimmedTitle.split(" ");
            if(words.length>1) {
                trimmedTitle = titleTrimHelper(words);
            }else {
                trimmedTitle= titleTrimmer(trimmedTitle,"");
            }
        }

        return trimmedTitle;
    }

    private String titleTrimHelper(String [] words){
        String first = words[0];
        String middle = " ";
        String last = words [words.length-1];
        int i = 1;

        while((first+middle+last).length()< MAX_BOOK_TITLE_LENGTH && i < words.length-1){
            middle= middle+words[i]+" ";
            i++;
        }

        return titleTrimmer(first+middle,last);
    }

    private String titleTrimmer(String first, String last) {
        StringBuilder trimmedTitle = new StringBuilder();
        int firstLength = first.length();
        int lastLength = last.length();

        if (last.isEmpty()) {
            trimmedTitle.append(first, 0, (firstLength / 2));
            trimmedTitle.append("...");
            trimmedTitle.append(first, (int) (firstLength * 0.85), firstLength);
        } else if (firstLength < lastLength) {
            trimmedTitle.append(first.trim());
            trimmedTitle.append("...");
            trimmedTitle.append(last, (int) (lastLength * 0.85), lastLength);
        } else {
            trimmedTitle.append(first, 0, (int) (firstLength * 0.55));
            trimmedTitle.append("...");
            trimmedTitle.append(last);
        }

        return trimmedTitle.toString();
    }

}
