package com.example.cbmoney.expense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cbmoney.R;
import com.example.cbmoney.databinding.ActivityChooseCategoryBinding;

public class ChooseCategory extends AppCompatActivity {

        private ActivityChooseCategoryBinding binding;
        private ArrayAdapter adapter; // 統一項目使用的資料

        private String[] data ={"飲食","日常開銷","娛樂","醫療保健","其他"} ;
        private String category;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityChooseCategoryBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setTitle("Choose Category");

            setAdapter();
            setListners();


        }

        // 按下item 背景顏色改變，並且將account儲存新的名稱
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 將點擊的項目背景色設置為你想要的顏色
                view.setBackgroundColor(Color.GRAY);

                // 將其他項目背景色設置為透明
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View childView = parent.getChildAt(i);
                    if (i != position) {
                        childView.setBackgroundColor(Color.TRANSPARENT);
                    }
                }

                category = ((TextView)view).getText().toString();

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("Extra_category",category);
                intent.putExtras(bundle);

                setResult(RESULT_OK,intent);
                finish();


            }
        };

        private void setListners(){
            binding.listCategory.setOnItemClickListener(listener);
        }

        // 設定背景
        // 顏色、字體，以及分隔線的顏色
        private void setAdapter(){
            //   adapter = new ArrayAdapter(ChooseAccount.this, android.R.layout.simple_list_item_1,data);
            // 在這裡設置初始的字體顏色為白色
            adapter = new ArrayAdapter<String>(com.example.cbmoney.expense.ChooseCategory.this, android.R.layout.simple_list_item_1, data) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    // 在這裡設置初始的字體顏色為白色
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setTextColor(Color.WHITE);

                    return view;
                }
            };
            binding.listCategory.setDivider(new ColorDrawable(Color.GRAY));
            binding.listCategory.setDividerHeight(1); // 設置分隔線的高度
            binding.listCategory.setAdapter(adapter);
        }

}