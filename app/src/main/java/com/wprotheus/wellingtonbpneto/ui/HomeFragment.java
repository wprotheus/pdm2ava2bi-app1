package com.wprotheus.wellingtonbpneto.ui;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wprotheus.wellingtonbpneto.databinding.FragmentHomeBinding;
import com.wprotheus.wellingtonbpneto.model.repo.DeckCardHelper;
import com.wprotheus.wellingtonbpneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbpneto.viewmodel.TratarDados;

import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private DeckCardHelper deckCardHelper;
    private DadosViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DadosViewModel.class);
        deckCardHelper = new DeckCardHelper(getContext());
        deckCardHelper.clearDatabase();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel.getDeckmLiveData().observe(getViewLifecycleOwner(), baralho -> {
            TratarDados.separarValoresBaralho separarValoresBaralho = TratarDados.getSepararValoresBaralho(baralho);
            List<String> code = separarValoresBaralho.listDeckCode;
            List<String> image = separarValoresBaralho.listDeckImage;
            List<String> value = separarValoresBaralho.listDeckValue;
            List<String> suit = separarValoresBaralho.listDeckSuit;
            salvarBaralhoNoBanco(code, image, value, suit);
        });
        return binding.getRoot();
    }

    public void salvarBaralhoNoBanco(@NonNull List<String> code, List<String> image, List<String> value, List<String> suit) {
        SQLiteDatabase db = deckCardHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < code.size(); i++) {
            values.put("CODIGO", code.get(i));
            values.put("IMAGEM", image.get(i));
            values.put("VALOR", value.get(i));
            values.put("NAIPE", suit.get(i));
            db.insert("tb_deck_cards", null, values);
        }
        db.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}