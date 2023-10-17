package com.example.cbmoney;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbmoney.ViewModel.MyViewModel;
import com.example.cbmoney.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    MyViewModel myViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentAccountBinding.inflate(inflater, container, false);
       myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
       myViewModel.getNumber().observe(getViewLifecycleOwner(), new Observer<Integer>() {
           @Override
           public void onChanged(Integer integer) {
               binding.num2.setText(String.valueOf(myViewModel.getNumber().getValue()));
           }
       });


        return  binding.getRoot();
    }


}