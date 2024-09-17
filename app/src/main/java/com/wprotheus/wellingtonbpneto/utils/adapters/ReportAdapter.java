package com.wprotheus.wellingtonbpneto.utils.adapters;

import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wprotheus.wellingtonbpneto.databinding.ReportLayoutBinding;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<String> listaRelatorio;

    public ReportAdapter(List<String> listaRelatorio) {
        this.listaRelatorio = listaRelatorio;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReportLayoutBinding reportBinding =
                ReportLayoutBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(reportBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int psnValor = position * 4;
        holder.rBinding(
                listaRelatorio.get(psnValor),
                listaRelatorio.get(psnValor + 1),
                listaRelatorio.get(psnValor + 2),
                listaRelatorio.get(psnValor + 3));
    }

    @Override
    public int getItemCount() {
        return listaRelatorio.size() / 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ReportLayoutBinding cardsLayoutBinding;

        public ViewHolder(@NonNull ReportLayoutBinding cardsLayoutBinding) {
            super(cardsLayoutBinding.getRoot());
            this.cardsLayoutBinding = cardsLayoutBinding;
        }

        public void rBinding(String codigo, String link, String valor, String naipe) {
            cardsLayoutBinding.tvValueCodeReport.setText(codigo);
            prepararLink(link);
            cardsLayoutBinding.tvValueValueReport.setText(valor);
            cardsLayoutBinding.tvValueSuitReport.setText(naipe);
        }

        private void prepararLink(String link) {
            cardsLayoutBinding.tvValueLinkReport.setText(link);
            cardsLayoutBinding.tvValueLinkReport.setLinksClickable(true);
            cardsLayoutBinding.tvValueLinkReport.setMovementMethod(LinkMovementMethod.getInstance());
            Linkify.addLinks(cardsLayoutBinding.tvValueLinkReport, Linkify.WEB_URLS);
        }
    }
}