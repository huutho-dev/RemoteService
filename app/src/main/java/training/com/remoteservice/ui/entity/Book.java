package training.com.remoteservice.ui.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hnc on 04/04/2017.
 */

public class Book  implements Parcelable{
    public String title ;

    public String author ;

    public String name ;

    public Book(String title, String author, String name) {
        this.title = title;
        this.author = author;
        this.name = name;
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
