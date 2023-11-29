package com.example.cbmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.cbmoney.account.AccountFragment;
import com.example.cbmoney.databinding.ActivityMainBinding;
import com.example.cbmoney.expense.EditNoteFragment;
import com.example.cbmoney.piechart.PieChartFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 一開始的畫面
        replaceFragment(new EditNoteFragment());
        // 監聽按鍵被觸發時，做得動作
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            if (id == R.id.edit_note) {
                replaceFragment(new EditNoteFragment());
            } else if (id == R.id.account) {
                replaceFragment(new AccountFragment());
            } else if (id == R.id.pie_chart) {
                replaceFragment(new PieChartFragment());
            }
            return true;
        });
    }

    // 切換畫面
    private void replaceFragment(Fragment fragment){
        FragmentManager fmrg = getSupportFragmentManager();
        FragmentTransaction transaction = fmrg.beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();

    }

}