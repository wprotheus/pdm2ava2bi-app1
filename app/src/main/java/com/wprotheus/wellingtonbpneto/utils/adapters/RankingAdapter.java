package com.wprotheus.wellingtonbpneto.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wprotheus.wellingtonbpneto.databinding.ViewResultLayoutBinding;
import com.wprotheus.wellingtonbpneto.model.Partida;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    private List<Partida> partidaList;

    public RankingAdapter(List<Partida> partidaList) {
        this.partidaList = partidaList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewResultLayoutBinding binding =
                ViewResultLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Partida p = partidaList.get(position);
        holder.setBinding(p);
    }

    @Override
    public int getItemCount() {
        return partidaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ViewResultLayoutBinding binding;

        public MyViewHolder(ViewResultLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setBinding(Partida partida) {
            binding.tvRankingPsn.setText(String.valueOf(partida.getId()));
            binding.tvJogadorNome.setText(partida.getNome());
            binding.tvJogadorPalpite.setText(partida.getPalpite());
            binding.tvJogadaResultado.setText(partida.getResultado());
        }
    }
}