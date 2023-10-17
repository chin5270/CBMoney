package com.example.cbmoney.ViewModel;
import androidx.lifecycle.ViewModel;
import java.util.List;
import androidx.lifecycle.MutableLiveData;

public class MyViewModel  extends ViewModel{
    private MutableLiveData<Integer> number;

    public MutableLiveData<Integer> getNumber(){
        if(number==null){
            number=new MutableLiveData<>();
            number.setValue(0);
        }
       return  number;
    }

    public void add(){
        number.setValue(number.getValue()+1);


    }
}

