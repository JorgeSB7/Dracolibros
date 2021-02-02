package com.dracolibros.views;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.dracolibros.R;
import com.dracolibros.interfaces.FormInterface;
import com.dracolibros.model.BookEntity;
import com.dracolibros.model.BookModel;
import com.dracolibros.presenters.FormPresenter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;

    String TAG = "Dracolibros/FormActivity";

    CheckBox disp;
    ImageView img;

    //_____CALENDAR
    Context myContext;
    EditText editTextDate;
    ImageButton buttonDate;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;

    //_____SPINNER
    ImageButton buttonPlus;

    //_____ATRIBUTOS LIBRO
    private String id;
    private String name;
    private String author;
    private String code;
    private String isbn;
    private String date;
    private String image;
    private String genre;
    private boolean available;

    //_____GALLERY
    private static final int REQUEST_CAPTURE_IMAGE = 200;
    private static final int REQUEST_SELECT_IMAGE = 201;
    private Uri uri;

    //_____PERMISO
    final private int CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 123;
    private ConstraintLayout constraintLayoutFormActivity;

    BookEntity book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        super.onCreate(savedInstanceState);

        // PRIMERO
        setContentView(R.layout.activity_form);

        book = new BookEntity();
        disp = (CheckBox) findViewById(R.id.ava);

        //_______GALLERY
        constraintLayoutFormActivity = findViewById(R.id.formCTL);

        Log.d(TAG, "Starting toolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.Form);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            Log.d(TAG, "Error al cargar toolbar");
        }

        presenter = new FormPresenter(this);

        buttonPlus = (ImageButton) findViewById(R.id.plus);
        Spinner spinner = (Spinner) findViewById(R.id.genero);
        ArrayList<String> genero = presenter.getGenres();
        ArrayList<String> gen = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, gen);
                for(String pg : genero){
                    gen.add(pg);
                }

                /*
                genero.add(getString(R.string.GenreLite));
                genero.add(getString(R.string.Fantasy));
                genero.add(getString(R.string.Sciencefiction));
                genero.add(getString(R.string.Romantic));
                genero.add(getString(R.string.Horror));
                genero.add(getString(R.string.CrimeNovel));
                genero.add(getString(R.string.History));
                genero.add(getString(R.string.bibliography));
                genero.add(getString(R.string.AdultNovel));
                 */

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Starting onClick");
                // Recuperación de la vista del AlertDialog a partir del layout de la Actividad
                LayoutInflater layoutActivity = LayoutInflater.from(myContext);
                View viewAlertDialog = layoutActivity.inflate(R.layout.alert_dialog, null);

                // Definición del AlertDialog
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myContext);

                // Asignación del AlertDialog a su vista
                alertDialog.setView(viewAlertDialog);

                // Recuperación del EditText del AlertDialog
                final EditText dialogInput = (EditText) viewAlertDialog.findViewById(R.id.newG);

                // Configuración del AlertDialog
                alertDialog
                        .setCancelable(false)
                        // Botón Añadir
                        .setPositiveButton(getResources().getString(R.string.confirm),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        if(!dialogInput.getText().toString().isEmpty()){
                                            adapter.add(dialogInput.getText().toString());
                                            spinner.setSelection(adapter.getPosition(dialogInput.getText().toString()));
                                        }else{
                                            Toast.makeText(getApplicationContext(),presenter.getError("NewGenre"),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                        // Botón Cancelar
                        .setNeutralButton(getResources().getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });

        //_________________________________________________CONTROL-DE-ERRORES

        //_________________________________________________NAME-ERROR
        TextInputLayout nameTIL;
        TextInputEditText nameTE;
        nameTE = findViewById(R.id.nameTE);
        nameTIL = findViewById(R.id.nameTIL);

        nameTE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "Exit EditText");
                    if (book.setName(nameTE.getText().toString()) == false ) {
                        nameTIL.setError(presenter.getError("BookName"));
                        nameTIL.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        nameTIL.setError(null);
                        nameTIL.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d(TAG, "Input EditText");
                }
            }
        });

        //_________________________________________________AUTHOR-ERROR
        TextInputLayout authorTIL;
        TextInputEditText authorTE;
        authorTE = findViewById(R.id.authorTE);
        authorTIL = findViewById(R.id.authorTIL);

        authorTE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "Exit EditText");
                    if (book.setAuthor(authorTE.getText().toString()) == false ) {
                        authorTIL.setError(presenter.getError("AuthorName"));
                        authorTIL.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        authorTIL.setError(null);
                        authorTIL.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d("FormActivity", "Input EditText");
                }
            }
        });

        //_________________________________________________CODE-ERROR
        TextInputLayout codeTIL;
        TextInputEditText codeTE;
        codeTE = findViewById(R.id.codeTE);
        codeTIL = findViewById(R.id.codeTIL);

        codeTE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d(TAG, "Exit EditText");
                    if (book.setCode(codeTE.getText().toString()) == false ) {
                        codeTIL.setError(presenter.getError("BookCode"));
                        codeTIL.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        codeTIL.setError(null);
                        codeTIL.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d("FormActivity", "Input EditText");
                }
            }
        });

        //_________________________________________________ISBN-ERROR
        TextInputLayout isbnTIL;
        TextInputEditText isbnTE;
        isbnTE = findViewById(R.id.isbnTE);
        isbnTIL = findViewById(R.id.isbnTIL);

        isbnTE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("FormActivity", "Exit EditText");
                    if (book.setIsbn(isbnTE.getText().toString()) == false ) {
                        isbnTIL.setError(presenter.getError("BookISBN"));
                        isbnTIL.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        isbnTIL.setError(null);
                        isbnTIL.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d("FormActivity", "Input EditText");
                }
            }
        });

        //_________________________________________________DATE-ERROR
        TextInputLayout dateTIL;
        TextInputEditText dateTE;
        dateTE = findViewById(R.id.dateTE);
        dateTIL = findViewById(R.id.dateTIL);

        dateTE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("FormActivity", "Exit EditText");
                    if (book.setDate(dateTE.getText().toString()) == false ) {
                        dateTIL.setError(presenter.getError("BookDate"));
                        dateTIL.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        dateTIL.setError(null);
                        dateTIL.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d("FormActivity", "Input EditText");
                }
            }
        });

        //_________________________________________________CALENDAR BUTTON
        myContext = this;
        // Obtener la fecha actual
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        editTextDate = (EditText)findViewById(R.id.dateTE);

        // Definir la acción del botón para abrir el calendario
        buttonDate = (ImageButton)findViewById(R.id.buttonDate);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Definir el calendario con la fecha seleccionada por defecto
                datePickerDialog = new DatePickerDialog(myContext, new DatePickerDialog.OnDateSetListener() {
                    // Definir la acción al pulsar OK en el calendario
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // Asignar la fecha a un campo de texto
                        editTextDate.setText(String.valueOf(day) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
                    }
                },Year, Month, Day);
                // Mostrar el calendario
                datePickerDialog.show();
            }
        });

        //______________________________GALLERY
        ImageView buttonGallery = (ImageView) findViewById(R.id.buttonGallery);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickImage();
            }
        });

        deleteIMG();

        //_________________________________________________ALERT-DIALOG

        ImageButton delete=(ImageButton) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on Save Button");
                presenter.onClickDeleteButton();
            }
        });

        //_________________________________________________


        String id = getIntent().getStringExtra("id");
        Log.d(TAG, "id"+id);

        if (id != null){
            BookEntity bk = presenter.getbyid(id);
            book.setId(id);
            image=bk.getImage();
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            buttonGallery.setImageBitmap(decodedByte);
            available=bk.isAvailable();
            disp.setChecked(available);
            genre=bk.getGenre();
            spinner.setSelection(adapter.getPosition(genre));
            date=bk.getDate();
            dateTE.setText(date);
            name=bk.getName();
            nameTE.setText(name);
            author=bk.getAuthor();
            authorTE.setText(author);
            code=bk.getCode();
            codeTE.setText(code);
            isbn=bk.getIsbn();
            isbnTE.setText(isbn);

            if(image != null){
                buttonGallery.setBackground(this.getResources().getDrawable(R.drawable.signo_de_interrogacion));
            }

        } else {
            delete.setEnabled(false);
            delete.setBackground(this.getResources().getDrawable(R.drawable.no));
        }

        //_________________________________________________________________________SAVE
        img = (ImageView) findViewById(R.id.buttonGallery);

        ImageButton save=(ImageButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                    book.setName(nameTE.getText().toString()) &&
                    book.setAuthor(authorTE.getText().toString()) &&
                    book.setCode(codeTE.getText().toString()) &&
                    book.setIsbn(isbnTE.getText().toString()) &&
                    book.setDate(dateTE.getText().toString()) &&
                    spinner.getSelectedItemPosition() !=0
                ){
                    book.setAvailable(disp.isChecked());
                    Log.d(TAG, "Image " + img);
                    if(img!=null&&img.getDrawable()!=null){
                        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        if(bitmap!=null){
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            book.setImage(fotoEnBase64);
                        }
                    }
                    book.setGenre(spinner.getSelectedItem().toString());

                    Log.d("Prueba SAVE", book.toString());
                    presenter.onClickSaveButton(book);
                } else {
                    Toast.makeText(getApplicationContext(),presenter.getError("SaveError"),Toast.LENGTH_LONG).show();
                }
            }
        });

        //_________________________________________________________________________SAVE-END

    } //_________________FIN_DEL_ONCREATE_________________

    //______________________________GALLERY

    @Override
    public void selectPicture(){
        // Se le pide al sistema una imagen del dispositivo
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.choose_picture)),
                REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case (REQUEST_CAPTURE_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Starting onActivityResult OK");
                    // Se carga la imagen desde un objeto URI al imageView
                    ImageView imageView = findViewById(R.id.buttonGallery);
                    imageView.setImageURI(uri);

                    // Se le envía un broadcast a la Galería para que se actualice
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(uri);
                    sendBroadcast(mediaScanIntent);

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d(TAG, "Starting onActivityResult CANCELED");
                    // Se borra el archivo temporal
                    File file = new File(uri.getPath());
                    file.delete();
                }
                break;

            case (REQUEST_SELECT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    // Se carga la imagen desde un objeto Bitmap
                    Uri selectedImage = data.getData();
                    String selectedPath = selectedImage.getPath();

                    if (selectedPath != null) {
                        // Se leen los bytes de la imagen
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Se transformam los bytes de la imagen a un Bitmap
                        Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                        // Se carga el Bitmap en el ImageView
                        Bitmap imageScaled = Bitmap.createScaledBitmap(bmp, 200, 200, false);
                        ImageView imageView = findViewById(R.id.buttonGallery);
                        imageView.setImageBitmap(imageScaled);
                    }
                }
                break;
        }
    }

    //______________________________LIMPIAR IMAGEN
    @Override
    public void deleteIMG(){
        ImageButton deleteimg = (ImageButton) findViewById(R.id.deleteimg);
        deleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView buttonGallery = findViewById(R.id.buttonGallery);
                buttonGallery.setImageBitmap(null);
            }
        });
    }

    //______________________________PEDIR PERMISO
    @Override
    public void IntentChooser(){
        Log.d(TAG, "Starting IntentChooser");
        ActivityCompat.requestPermissions(FormActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
    }

    @Override
    public void showError(){
        Log.d(TAG, "Starting showError");
        Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CODE_WRITE_EXTERNAL_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Starting onRequestPermissionsResult GRANTED");
                    presenter.PermissionGranted();
                } else {
                    Log.d(TAG, "Starting onRequestPermissionsResult DENIED");
                    presenter.PermissionDenied();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //______________________________AELRT DELETE
    @Override
    public void alertDelete(){
        Log.d(TAG, "Starting alertDelete");
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setTitle(R.string.deleteQuestion);

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.clicAcceptDelete();
                // Toast.makeText(getApplicationContext(),"Yes button Clicked", Toast.LENGTH_LONG).show();
                Log.i("Code2care ", "Yes button Clicked!");
                presenter.delete(book);
            }
        });

        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //    Toast.makeText(getApplicationContext(),"Cancel button Clicked",Toast.LENGTH_LONG).show();
                Log.i("Code2care ","Cancel button Clicked!");
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onNavigateUp() {
        return true;
    }

    @Override
    public void CloseFormActivity() {
        finish();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Starting onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Starting onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Starting onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "Starting onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Starting onDestroy");
        super.onDestroy();
    }

    //_________________________________________________HELP
    /* Se trata del mismo menú con la opción Ayuda
    de SearchActivity reutilizado ahora en el formulario*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Order) {
            Log.d(TAG, "Starting Help");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}