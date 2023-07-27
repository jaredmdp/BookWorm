package honda.bookworm.Object;

import java.util.Objects;

public class Comment {
    public String username;
    public String ISBN;
    public String comment;
    public long timeStamp;

    public Comment(String username, String ISBN, String comment) {
        this.username = username;
        this.ISBN = ISBN;
        this. comment = comment;
        timeStamp = System.currentTimeMillis();
    }

    public String getUsername(){return username;}

    public String getISBN(){return ISBN;}

    public String getComment(){return comment;}

    public long getTime(){return timeStamp;}

    public void setTime(long time){timeStamp = time;}

    public boolean equals(Object compare) {
        if (this == compare) {
            return true;
        }

        if (compare == null || getClass() != compare.getClass()) {
            return false;
        }

        Comment comment = (Comment) compare;

        return  Objects.equals(username, comment.username) &&
                Objects.equals(ISBN, comment.ISBN) &&
                Objects.equals(this.comment, comment.comment) &&
                timeStamp == comment.timeStamp;
    }
}
