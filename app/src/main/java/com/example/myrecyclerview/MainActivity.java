package com.example.myrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvTari;
    private ArrayList<Tari> list = new ArrayList<>();
    private String title = "Mode List";
    private final String STATE_TITLE = "state_string";
    private final String STATE_LIST = "state_list";
    private final String STATE_MODE = "state_mode";
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTari = findViewById(R.id.rv_tari);
        rvTari.setHasFixedSize(true);

        if (savedInstanceState == null) {
            setActionBarTitle(title);
            list.addAll(getListTari());
            showRecyclerList();
            mode = R.id.action_list;
        } else {
            title = savedInstanceState.getString(STATE_TITLE);
            ArrayList<Tari> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            int stateMode = savedInstanceState.getInt(STATE_MODE);
            setActionBarTitle(title);
            if (stateList != null) {
                list.addAll(stateList);
            }
            setMode(stateMode);
        }
    }
    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
    public ArrayList<Tari> getListTari() {
        String[] dataName = getResources().getStringArray(R.array.data_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_description);
        String[] dataPhoto = getResources().getStringArray(R.array.data_photo);
        ArrayList<Tari> listTari = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            Tari tari = new Tari();
            tari.setName(dataName[i]);
            tari.setDescription(dataDescription[i]);
            tari.setPhoto(dataPhoto[i]);
            listTari.add(tari);
        }
        return listTari;
    }
    private void showSelectedTari(Tari tari) {
        Toast.makeText(this, "Kamu memilih " + tari.getName(), Toast.LENGTH_SHORT).show();
    }
    private void showRecyclerList(){
        rvTari.setLayoutManager(new LinearLayoutManager(this));
        ListTariAdapter listTariAdapter = new ListTariAdapter(list);
        rvTari.setAdapter(listTariAdapter);

        listTariAdapter.setOnItemClickCallback(new ListTariAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tari data) {
                showSelectedTari(data);
            }
        });
    }
    private void showRecyclerGrid(){
        rvTari.setLayoutManager(new GridLayoutManager(this, 2));
        GridTariAdapter gridTariAdapter = new GridTariAdapter(list);
        rvTari.setAdapter(gridTariAdapter);
        gridTariAdapter.setOnItemClickCallback(new GridTariAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tari data) {
                showSelectedTari(data);
            }
        });
    }
    private void showRecyclerCardView(){
        rvTari.setLayoutManager(new LinearLayoutManager(this));
        CardViewTariAdapter cardViewTariAdapter = new CardViewTariAdapter(list);
        rvTari.setAdapter(cardViewTariAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, title);
        outState.putParcelableArrayList(STATE_LIST, list);
        outState.putInt(STATE_MODE, mode);
    }
    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.action_list:
                title = "Mode List";
                showRecyclerList();
                break;
            case R.id.action_grid:
                title = "Mode Grid";
                showRecyclerGrid();
                break;
            case R.id.action_cardview:
                title = "Mode CardView";
                showRecyclerCardView();
                break;
        }
        mode = selectedMode;
        setActionBarTitle(title);
    }
}