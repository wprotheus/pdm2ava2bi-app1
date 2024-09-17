package com.wprotheus.wellingtonbpneto.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wprotheus.wellingtonbpneto.model.Baralho;

import java.util.List;

import lombok.Getter;

@Getter
public class DadosViewModel extends ViewModel {
    private final MutableLiveData<Baralho> baralhoMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> deckmLiveData = new MutableLiveData<>();

    public MutableLiveData<Baralho> getBaralhoMutableLiveData() {
        return baralhoMutableLiveData;
    }

    public MutableLiveData<List<String>> getDeckmLiveData() {
        return deckmLiveData;
    }
}