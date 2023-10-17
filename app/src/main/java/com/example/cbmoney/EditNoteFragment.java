package com.example.cbmoney;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbmoney.ViewModel.MyViewModel;

import com.example.cbmoney.databinding.FragmentEditNoteBinding;


public class EditNoteFragment extends Fragment {
    MyViewModel myViewModel;
    private FragmentEditNoteBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditNoteBinding.inflate(inflater, container, false);

        myViewModel= new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.getNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.texviewShownum.setText(String.valueOf(integer));
                Log.d("chin","onChanged:myViewModel.getNumber():"+myViewModel.getNumber().getValue());

            }
        });

        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewModel.add();
            }
        });


        return binding.getRoot();
    }



}
