package com.wprotheus.wellingtonbpneto.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.databinding.FragmentReportBinding;
import com.wprotheus.wellingtonbpneto.model.Match;
import com.wprotheus.wellingtonbpneto.model.repo.DeckCardHelper;
import com.wprotheus.wellingtonbpneto.utils.MySharedDataPref;
import com.wprotheus.wellingtonbpneto.utils.adapters.ReportAdapter;
import com.wprotheus.wellingtonbpneto.viewmodel.TratarDados;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {
    private FragmentReportBinding fragmentReportBinding;
    private MySharedDataPref mySharedDataPref;
    private DeckCardHelper deckCardHelper;
    private ReportAdapter reportAdapter;
    private List<String> listReport;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySharedDataPref = new MySharedDataPref(this.requireContext());
        deckCardHelper = new DeckCardHelper(getContext());
        listReport = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentReportBinding = FragmentReportBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = fragmentReportBinding.rvDeckOfCardsReport;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        if (mySharedDataPref.recuperarQtdCartas() == null) {
            fragmentReportBinding.btnNext.setEnabled(false);
        } else {
            listReport = deckCardHelper.getBaralhoBaixado();
            int[] qtd = new Match().cartasEscolhidas(Integer.parseInt(mySharedDataPref.recuperarQtdCartas()));
            listReport = new TratarDados().cartasSelecionadas(listReport, qtd);
            String somaCartas = listReport.remove(listReport.size() - 1);
            salvarCartasEscolhidas(listReport, somaCartas);

            if (listReport.size() % 4 == 0) {
                if (reportAdapter == null) {
                    reportAdapter = new ReportAdapter(listReport);
                    recyclerView.setAdapter(reportAdapter);
                } else {
                    reportAdapter.notifyDataSetChanged();
                }
            } else {
                Log.e("DeckFragmento", "A lista de cartas não contém múltiplos de 4 itens.");
            }
        }
        fragmentReportBinding.btnNext.setOnClickListener(v -> proximaTela());
        return fragmentReportBinding.getRoot();
    }

    private void proximaTela() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.popBackStack();
        navController.navigate(R.id.nav_show);
    }

    public void salvarCartasEscolhidas(List<String> listReport, String soma) {
        TratarDados.separarValoresBaralho separarValoresBaralho = TratarDados.getSepararValoresBaralho(listReport);
        if (mySharedDataPref != null) {
            Log.d("ReportFragmento", "Cartas escolhidas gravadas.");
            mySharedDataPref.salvarSomaCartas(soma);
            mySharedDataPref.salvarCartasEscolhidas(separarValoresBaralho.listDeckCode, separarValoresBaralho.listDeckImage,
                    separarValoresBaralho.listDeckValue, separarValoresBaralho.listDeckSuit);
        } else {
            Log.e("ReportFragmento", "mySharedDataPref está vazio.");
        }
        clearData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentReportBinding = null;
    }

    public void clearData() {
        if (mySharedDataPref != null) {
            mySharedDataPref.removerQtdCartas();
        } else {
            Log.e("StartFragmento", "mySharedDataPref está vazio.");
        }
    }
}