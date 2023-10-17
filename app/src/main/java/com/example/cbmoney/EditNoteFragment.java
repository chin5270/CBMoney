package com.example.cbmoney;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbmoney.ViewModel.MyViewModel;
import com.example.cbmoney.databinding.ActivityMainBinding;
import com.example.cbmoney.databinding.FragmentEditNoteBinding;


public class EditNoteFragment extends Fragment {
    MyViewModel myViewModel;
    FragmentEditNoteBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}