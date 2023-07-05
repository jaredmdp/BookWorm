package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.honda.bookworm.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.View.Extra.Adapters.GenreAdapter;
import honda.bookworm.View.Extra.ImageImporter;

public class AddBook_ViewHandler extends AppCompatActivity implements ImageImporter.ImageImportCallback {

    private EditText bookTitleEditText;
    private EditText ISBNEditText;
    private EditText descriptionEditText;

    private IAccessBooks accessBooks;
    private IUserManager userManager;

    private Spinner spinner;
    private GenreAdapter adapter;
    private Bitmap bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_view_handler);

        spinner = findViewById(R.id.addbook_genre_spinner);

        bookTitleEditText = findViewById(R.id.addbook_booktitle_input);
        ISBNEditText = findViewById(R.id.addbook_ISBN_input);
        descriptionEditText = findViewById(R.id.addbook_description_input);
        bookImage = null;

        accessBooks = new AccessBooks();
        updateSpinner();

    }

    private void processBooksInput(String bookTitle, String authorName, int authorID, Genre genre, String ISBN, String description) {
        boolean signUpState = false;
        String msg = "";

        try {
            Book newBook = new Book(bookTitle, authorName, authorID, genre, ISBN, description);
            Book addedBook = accessBooks.addBook(newBook);
            signUpState = true;
            //** TODO: will delete after user profile has been made, only use for toast now **
            msg = String.format(("Add Book: %s, %s, %s, %s"), addedBook.getName(), addedBook.getISBN(), addedBook.getGenre(), addedBook.getDescription());
        } catch (NullPointerException e) {
            msg = String.format(("Invalid Book: %s"), e.getMessage());
        }

        //redirect to User Profile if signup was successful
        if (signUpState) {
            //** TODO: will intent to user profile after user profile has been made **
            //** TODO: delete toast after user profile has been made **
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

    }

    public void isAddBookButtonClicked(View view) {
        //** TODO: just for testing purposes, will remove after user profile has been made **
        Author theAuthor = new Author("John", "Doe", "johndoe", "password", 0);
        //Author theAuthor = (Author) userManager.getActiveUser();

        String bookTitle = bookTitleEditText.getText().toString();
        String authorName = theAuthor.getFirstName() + " " + theAuthor.getLastName();
        int authorID = theAuthor.getAuthorID();
        Genre genre = (Genre) spinner.getSelectedItem();
        String ISBN = ISBNEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        processBooksInput(bookTitle, authorName, authorID, genre, ISBN, description);

    }

    public void isAvailableToPurchaseClicked(View v) {

        if (v instanceof CheckBox) {
            boolean isChecked = ((CheckBox) v).isChecked();
            TextView warning = findViewById(R.id.addbook_purchase_warning);
            if (isChecked) {
                warning.setVisibility(View.VISIBLE);
            }else {
                warning.setVisibility(View.GONE);
            }
        }

    }

    public void isCancelButtonClicked(View view) {
        resetSignUpFields();
        finish();
    }

    private void resetSignUpFields() {
        emptyEditText(bookTitleEditText);
        emptyEditText(ISBNEditText);
        emptyEditText(descriptionEditText);
    }

    private void emptyEditText(EditText et) {
        if (et != null) {
            et.setText("");
        }
    }

    //Code to populate spinner
    private void updateSpinner() {
        adapter = new GenreAdapter(this);
        spinner.setAdapter(adapter);
    }

    public void isAddImageButtonClicked(View view) {
        ImageImporter.importImage(AddBook_ViewHandler.this);
    }


    //This is a built in method it will be called when an image is imported and handles image data
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageImporter.handleActivityResult(requestCode, resultCode, data, this);
    }

    /* this is called automatically.
       the code within can be modified
       to perform whatever action that is needed to be done
       with the imported image
     */
    @Override
    public void onImageImported(Uri imageUri) {
        ImageView bookImage = findViewById(R.id.addbook_image);
        try{
            Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            if(ImageImporter.isValidImageSize(this,image)){
                this.bookImage = image;
                bookImage.setImageBitmap(this.bookImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}