package honda.bookworm.View;

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

import androidx.appcompat.app.AppCompatActivity;

import com.honda.bookworm.R;

import java.io.IOException;

import honda.bookworm.Business.Exceptions.Books.DuplicateISBNException;
import honda.bookworm.Business.Exceptions.Books.InvalidBookException;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.View.Extra.Adapters.GenreAdapter;
import honda.bookworm.View.Extra.ImageConverter;
import honda.bookworm.View.Extra.ImageImporter;

public class AddBook_ViewHandler extends AppCompatActivity implements ImageImporter.ImageImportCallback {

    private EditText bookTitleEditText;
    private EditText ISBNEditText;
    private EditText descriptionEditText;

    private IAccessBooks accessBooks;
    private IUserManager userManager;

    private Spinner spinner;
    private GenreAdapter adapter;
    private CheckBox isPurchaseable;
    private Bitmap bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_view_handler);

        spinner = findViewById(R.id.addbook_genre_spinner);

        bookTitleEditText = findViewById(R.id.addbook_booktitle_input);
        ISBNEditText = findViewById(R.id.addbook_ISBN_input);
        descriptionEditText = findViewById(R.id.addbook_description_input);
        isPurchaseable = findViewById(R.id.addbook_is_available_checkbox);
        bookImage = null;

        userManager = new UserManager();
        accessBooks = new AccessBooks();
        updateSpinner();
    }

    private void processBooksInput(String bookTitle, Genre genre, String ISBN, String description, boolean isPurchaseable){
        boolean success = false;
        String msg = "";

        try {
            Book addedBook = accessBooks.addBook(bookTitle, genre, ISBN, description, ImageConverter.EncodeToBase64(bookImage), isPurchaseable);
            success = true;

            msg = String.format(("New Book Successfully Added: %s"), addedBook.getName());
        } catch (InvalidBookException | IllegalStateException |
                 DuplicateISBNException | InvalidGenreException e) {
            msg = String.format(("Invalid Book: %s"), e.getMessage());
        }

        //redirect to User Profile if signup was successful
        if (success) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            onBackPressed();
            Home_ViewHandler.bookAdded = true;
            finish();
        } else {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }

    }

    public void isAddBookButtonClicked(View view) {
        String bookTitle = bookTitleEditText.getText().toString();
        String ISBN = ISBNEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        boolean isPurchasable = this.isPurchaseable.isChecked();
        Genre genre =  (spinner.getSelectedItem() instanceof Genre)
                ? (Genre) spinner.getSelectedItem(): null ;

        if (userManager.isAuthorActive()) {
            Log.d("Processing Book: ",bookTitle+" "+userManager.getActiveUser().getUsername());
            processBooksInput(bookTitle, genre, ISBN, description, isPurchasable);
        }else {
            Toast.makeText(this,"You aren't registered as an Author, You shouldnt have access to this page",Toast.LENGTH_LONG).show();
        }
    }

    public void isAvailableToPurchaseClicked(View v) {
        if (v instanceof CheckBox) {
            TextView warning = findViewById(R.id.addbook_purchase_warning);
            if (((CheckBox) v).isChecked()) {
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