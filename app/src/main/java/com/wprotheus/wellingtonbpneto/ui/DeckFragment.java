package com.wprotheus.wellingtonbpneto.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wprotheus.wellingtonbpneto.databinding.FragmentDeckBinding;
import com.wprotheus.wellingtonbpneto.model.repo.DeckCardHelper;
import com.wprotheus.wellingtonbpneto.utils.adapters.DeckAdapter;
import com.wprotheus.wellingtonbpneto.viewmodel.TratarDados;

import java.util.ArrayList;
import java.util.List;

public class DeckFragment extends Fragment {
    private FragmentDeckBinding fragmentDeckBinding;
    private DeckCardHelper deckCardHelper;
    private DeckAdapter cartasAdapter;
    private List<String> listBaralho;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deckCardHelper = new DeckCardHelper(getContext());
        listBaralho = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentDeckBinding = FragmentDeckBinding.inflate(inflater, container, false);
        RecyclerView rvDeckCards = fragmentDeckBinding.rvDeckOfCards;
        GridLayoutManager manager = new GridLayoutManager(this.requireContext(), 2);
        rvDeckCards.setLayoutManager(manager);

        listBaralho = deckCardHelper.getBaralhoBaixado();
        if (!listBaralho.isEmpty()) {
            listBaralho = new TratarDados().listaAdapter(listBaralho);
            if (listBaralho.size() % 3 == 0) {
                cartasAdapter = new DeckAdapter(listBaralho);
                rvDeckCards.setAdapter(cartasAdapter);
            }
        }
        return fragmentDeckBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDeckBinding = null;
    }
}