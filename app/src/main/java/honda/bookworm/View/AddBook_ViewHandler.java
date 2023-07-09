package honda.bookworm.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.honda.bookworm.R;

import java.io.IOException;

import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.View.Extra.Adapters.GenreAdapter;
import honda.bookworm.View.Extra.ImageImporter;
import honda.bookworm.View.Extra.ImageConverter;

public class AddBook_ViewHandler extends AppCompatActivity implements ImageImporter.ImageImportCallback {

    private EditText bookTitleEditText;
    private EditText ISBNEditText;
    private EditText descriptionEditText;

    private IAccessBooks accessBooks;
    private IUserManager userManager;

    private Spinner spinner;
    private GenreAdapter adapter;
    private Bitmap bookImage;
    private boolean isPurchaseable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_view_handler);

        spinner = findViewById(R.id.addbook_genre_spinner);

        bookTitleEditText = findViewById(R.id.addbook_booktitle_input);
        ISBNEditText = findViewById(R.id.addbook_ISBN_input);
        descriptionEditText = findViewById(R.id.addbook_description_input);
        bookImage = null;
        isPurchaseable = false;

        accessBooks = new AccessBooks();
        updateSpinner();

    }

    private void processBooksInput(String bookTitle, Genre genre, String ISBN, String description){
        boolean signUpState = false;
        String msg = "";

        try {
            Book addedBook = accessBooks.addBook(bookTitle, genre, ISBN, description, ImageConverter.EncodeToBase64(bookImage), isPurchaseable);
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

            Intent intent = new Intent(getApplicationContext(), Home_ViewHandler.class);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

    }

    public void isAddBookButtonClicked(View view) {
        String bookTitle = bookTitleEditText.getText().toString();
        Genre genre = (Genre) spinner.getSelectedItem();
        String ISBN = ISBNEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        processBooksInput(bookTitle, genre, ISBN, description);

    }

    public void isAvailableToPurchaseClicked(View v) {

        if (v instanceof CheckBox) {
            isPurchaseable = ((CheckBox) v).isChecked();
            TextView warning = findViewById(R.id.addbook_purchase_warning);
            if (isPurchaseable) {
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