package com.wprotheus.wellingtonbpneto.utils.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.databinding.CardsLayoutBinding;
import com.wprotheus.wellingtonbpneto.model.Naipe;
import com.wprotheus.wellingtonbpneto.viewmodel.TratarDados;

import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {
    private List<String> listaDeCartas;

    public DeckAdapter(List<String> listaDeCartas) {
        this.listaDeCartas = listaDeCartas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardsLayoutBinding cardsBinding =
                CardsLayoutBinding.inflate(LayoutInflater
                        .from(parent.getContext()), parent, false);
        return new ViewHolder(cardsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int psnValor = position * 3;
        holder.setBinding(
                listaDeCartas.get(psnValor),
                listaDeCartas.get(psnValor + 1),
                listaDeCartas.get(psnValor + 2));
    }

    @Override
    public int getItemCount() {
        return listaDeCartas.size() / 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardsLayoutBinding cardsBinding;

        public ViewHolder(CardsLayoutBinding cardsBinding) {
            super(cardsBinding.getRoot());
            this.cardsBinding = cardsBinding;
        }

        public void setBinding(String codigo, String valor, String naipe) {
            new TratarDados();
            String codImg = TratarDados.getCodigoCarta(codigo);
            try {
                Naipe naipeEnum = Naipe.valueOf(codImg);
                cardsBinding.ivNaipe.setImageResource(naipeEnum.getResourceId());
            } catch (IllegalArgumentException e) {
                cardsBinding.ivNaipe.setImageResource(R.drawable.four_color_deck);
                Log.e("DeckAdapter", "Naipe inválido: " + codigo, e);
            }
            cardsBinding.tvCodigo.setText(codigo);
            cardsBinding.tvValor.setText(valor);
            cardsBinding.tvNaipe.setText(naipe);
        }
    }
}