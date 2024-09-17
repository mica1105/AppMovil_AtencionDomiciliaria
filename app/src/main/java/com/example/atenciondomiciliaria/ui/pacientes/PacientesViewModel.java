package com.example.atenciondomiciliaria.ui.pacientes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PacientesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PacientesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is pacientes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}