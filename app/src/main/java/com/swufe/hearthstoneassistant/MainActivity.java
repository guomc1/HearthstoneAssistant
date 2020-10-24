package com.swufe.hearthstoneassistant;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity {

    private Fragment fragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome,rbtInfo,rbtSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new Fragment[2];
        fragmentManager= getSupportFragmentManager();
        fragments[0] = fragmentManager.findFragmentById(R.id.fragment1);
        fragments[1] = fragmentManager.findFragmentById(R.id.fragment2);
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(fragments[0]).hide(fragments[1]);
        fragmentTransaction.show(fragments[0]).commit();

        rbtHome = findViewById(R.id.function);
        rbtInfo = findViewById(R.id.warehouse);

        radioGroup = findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(fragments[0]).hide(fragments[1]);
                switch (i){
                    case R.id.function:
                        fragmentTransaction.show(fragments[0]).commit();
                        break;
                    case R.id.warehouse:
                        fragmentTransaction.show(fragments[1]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
