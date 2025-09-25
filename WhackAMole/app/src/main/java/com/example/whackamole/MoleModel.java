package com.example.whackamole;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MoleModel extends ViewModel {

    private final MutableLiveData<Boolean> isActive = new MutableLiveData<>();

    public MoleModel() {
        isActive.setValue(false);
    }

    public MutableLiveData<Boolean> getIsActive() {
        return isActive;
    }



}
