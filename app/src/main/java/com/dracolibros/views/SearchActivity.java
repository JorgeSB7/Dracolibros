package com.dracolibros.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.dracolibros.interfaces.SearchInterface;
import com.dracolibros.model.BookEntity;
import com.dracolibros.presenters.SearchPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.dracolibros.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity implements SearchInterface.View {

    private SearchInterface.Presenter presenter;

    String TAG = "Dracolibros/SearchActivity";

    //_____CALENDAR
    Context myContext2;
    EditText editTextDate2;
    ImageButton buttonDate2;
    Calendar calendar2 ;
    DatePickerDialog datePickerDialog2 ;
    int Year2, Month2, Day2 ;

    EditText nainfoTE;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.d(TAG, "Starting toolbar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_search);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            Log.d(TAG, "Error al cargar toolbar");
        }
        presenter = new SearchPresenter(this);

        FloatingActionButton fab = findViewById(R.id.booksearch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickSearchButton();
            }
        });

        String gene = "seleccionar";
        spinner = (Spinner) findViewById(R.id.Searchspinner);
        ArrayList<String> genero = presenter.getGenres();
        ArrayList<String> gen = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, gen);
        gen.add(gene);
        for(String pg : genero){
            gen.add(pg);
        }

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        BookEntity book;
        book = new BookEntity();

        //_________________________________________________NAME-ERROR
        TextInputLayout nainfoTIL;

        nainfoTE = findViewById(R.id.nainfoTE);
        nainfoTIL = findViewById(R.id.nainfoTIL);

        nainfoTE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("SearchActivity", "Exit EditText");
                    if (book.setName(nainfoTE.getText().toString()) == false ) {
                        nainfoTIL.setError(presenter.getError("BookInformation"));
                        nainfoTIL.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        nainfoTIL.setError(null);
                        nainfoTIL.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d("SearchActivity", "Input EditText");
                }
            }
        });

        //_________________________________________________DATE-ERROR
        TextInputLayout dateTIL2;
        TextInputEditText dateTE2;
        dateTE2 = findViewById(R.id.dateTE2);
        dateTIL2 = findViewById(R.id.dateTIL2);

        dateTE2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("SearchActivity", "Exit EditText");
                    if (book.setDate(dateTE2.getText().toString()) == false ) {
                        dateTIL2.setError(presenter.getError("BookDate"));
                        dateTIL2.setErrorTextColor(ColorStateList.valueOf(Color.BLUE));
                    } else {
                        dateTIL2.setError(null);
                        dateTIL2.setBoxStrokeColor(Color.GREEN);
                    }
                }else{
                    Log.d("SearchActivity", "Input EditText");
                }
            }
        });

        //_________________________________________________CALENDAR BUTTON
        myContext2 = this;
        // Obtener la fecha actual
        calendar2 = Calendar.getInstance();
        Year2 = calendar2.get(Calendar.YEAR) ;
        Month2 = calendar2.get(Calendar.MONTH);
        Day2 = calendar2.get(Calendar.DAY_OF_MONTH);

        editTextDate2 = (EditText)findViewById(R.id.dateTE2);

        // Definir la acción del botón para abrir el calendario
        buttonDate2 = (ImageButton)findViewById(R.id.buttonDate2);
        buttonDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Definir el calendario con la fecha seleccionada por defecto
                datePickerDialog2= new DatePickerDialog(myContext2, new DatePickerDialog.OnDateSetListener() {
                    // Definir la acción al pulsar OK en el calendario
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        // Asignar la fecha a un campo de texto
                        editTextDate2.setText(String.valueOf(day) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
                    }
                },Year2, Month2, Day2);
                // Mostrar el calendario
                datePickerDialog2.show();
            }
        });
    }

    @Override
    public void CloseSearchActivity() {
        Intent i = getIntent();
        i.putExtra("name", nainfoTE.getText().toString());
        i.putExtra("spinner", spinner.getSelectedItemId());
        i.putExtra("date", editTextDate2.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }

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
}