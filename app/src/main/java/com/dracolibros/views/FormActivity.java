package com.dracolibros.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import com.dracolibros.R;
import com.dracolibros.interfaces.FormInterface;
import com.dracolibros.model.BookEntity;
import com.dracolibros.presenters.FormPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity implements FormInterface.View {

    private FormInterface.Presenter presenter;

    String TAG = "Dracolibros/FormActivity";

    //_____CALENDAR
    Context myContext;
    EditText editTextDate;
    ImageButton buttonDate;
    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;

    //_____SPINNER
    ImageButton buttonPlus;

    //_____Nuevo
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);

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

        ImageButton save=(ImageButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSaveButton();
            }
        });

        buttonPlus = (ImageButton) findViewById(R.id.plus);
        Spinner spinner = (Spinner) findViewById(R.id.genero);
        ArrayList<String> genero = new ArrayList();
                genero.add(getString(R.string.GenreLite));
                genero.add(getString(R.string.Fantasy));
                genero.add(getString(R.string.Sciencefiction));
                genero.add(getString(R.string.Romantic));
                genero.add(getString(R.string.Horror));
                genero.add(getString(R.string.CrimeNovel));
                genero.add(getString(R.string.History));
                genero.add(getString(R.string.bibliography));
                genero.add(getString(R.string.AdultNovel));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, genero);
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

        BookEntity book;
        book = new BookEntity();

        //_________________________________________________NAME-ERROR
        TextInputLayout nameTIL;
        TextInputEditText nameTE;
        nameTE = findViewById(R.id.nameTE);
        nameTIL = findViewById(R.id.nameTIL);

        nameTE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("FormActivity", "Exit EditText");
                    if (book.setName(nameTE.getText().toString()) == false ) {
                        nameTIL.setError(presenter.getError("BookName"));
                        nameTIL.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        nameTIL.setError(null);
                        nameTIL.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d("FormActivity", "Input EditText");
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
                    Log.d("FormActivity", "Exit EditText");
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
                    Log.d("FormActivity", "Exit EditText");
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
                        editTextDate.setText(String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year));
                    }
                },Year, Month, Day);
                // Mostrar el calendario
                datePickerDialog.show();
            }
        });

        //_________________________________________________ALERT-DIALOG

        ImageButton delete=(ImageButton) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on Save Button");
                presenter.onClickDeleteButton();
            }
        });

        //______________________________NUEVO2

        id = getIntent().getStringExtra("id");

        if (id != null){
            nameTE.setText(id);
        } else {
            //Deshabilitar el botón eliminar
        }
    }

    @Override
    public void alertDelete(){
        Log.d(TAG, "Starting alertDelete");
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setTitle(R.string.deleteQuestion);

        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CloseFormActivity();
                // Toast.makeText(getApplicationContext(),"Yes button Clicked", Toast.LENGTH_LONG).show();
                Log.i("Code2care ", "Yes button Clicked!");
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