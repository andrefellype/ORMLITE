package br.com.andre.app_sqlite;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.andre.app_sqlite.dao.CidadeDAO;
import br.com.andre.app_sqlite.entidade.Cidade;
import br.com.andre.app_sqlite.fragment.CidadeFragment;
import br.com.andre.app_sqlite.fragment.PopulacaoFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final int requestcode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.setType("gagt/sdf");
                try {
                    startActivityForResult(fileintent, requestcode);
                } catch (ActivityNotFoundException e) {
                    Log.e("QTD", "ActivityNotFoundException: " + e.getMessage());
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CidadeFragment(), "Cidade");
        adapter.addFragment(new PopulacaoFragment(), "População");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode) {
            case requestcode:
                String filepath = data.getData().getPath();
                try {
                    if (resultCode == RESULT_OK) {
                        try {
                            FileReader file = new FileReader(filepath);
                            BufferedReader buffer = new BufferedReader(file);
                            ContentValues contentValues = new ContentValues();
                            String line = "";
                            int x = 0;
                            while ((line = buffer.readLine()) != null) {
                                String[] str = line.split(",", 5);
                                if(x>0) {
                                    Cidade cidade = new Cidade();
                                    cidade.setCodmun(Integer.parseInt(str[2].toString()));
                                    cidade.setNomemunic(str[3].toString());
                                    cidade.setCoduf(Integer.parseInt(str[1].toString()));
                                    cidade.setUf(str[0].toString());
                                    cidade.setPopulacao(Integer.parseInt(str[4].toString()));
                                    CidadeDAO cidadeDAO = new CidadeDAO(MainActivity.this);
                                    cidadeDAO.inserir(cidade);
                                }
                                x++;
                            }
                        } catch (IOException e) {
                            Log.e("QTD","IOException: " + e.getMessage());
                        }
                    } else {
                        Log.e("QTD","Only CSV files allowed");
                    }
                } catch (Exception e) {
                    Log.e("QTD","Exception: " + e.getMessage());
                }
        }
    }

}
