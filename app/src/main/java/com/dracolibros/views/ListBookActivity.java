package com.dracolibros.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dracolibros.R;
import com.dracolibros.interfaces.ListInterface;
import com.dracolibros.presenters.ListPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListBookActivity extends AppCompatActivity implements ListInterface.View {

    private ListInterface.Presenter presenter;

    String TAG = "Dracolibros/ListBookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting onCreate");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Starting layout");
        setContentView(R.layout.activity_listbook);

        Log.d(TAG, "Starting toolbar");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new ListPresenter(this);

        Log.d(TAG, "Starting FloatingActionButton");
        FloatingActionButton newbook = findViewById(R.id.newbook);
        newbook.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.onClickFloatingButton();
        }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Starting onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting onStart");
        super.onStart();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.d(TAG, "Starting Settings");
            return true;
        }

        if (id == R.id.action_search) {
            Log.d(TAG, "Starting search");
            presenter.onClickSearchButton();
            return true;
        }

        if (id == R.id.action_Help) {
            Log.d(TAG, "Starting Help");
            return true;
        }

        if (id == R.id.action_Order) {
            Log.d(TAG, "Starting Order");
            return true;
        }

        if (id == R.id.action_AppDL) {
            Log.d(TAG, "Starting AppDL");
            presenter.onClickAboutAPPButton();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void StartFormActivity() {
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);
        startActivity(intent);
    }

    @Override
    public void StartSearchActivity() {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void StartAboutAPPActivity() {
        Intent intent = new Intent(getApplicationContext(), AboutAPPActivity.class);
        startActivity(intent);
    }
}