package com.wprotheus.wellingtonbpneto.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.databinding.FragmentShowBinding;
import com.wprotheus.wellingtonbpneto.model.Naipe;
import com.wprotheus.wellingtonbpneto.utils.MusicaFundo;
import com.wprotheus.wellingtonbpneto.utils.MySharedDataPref;
import com.wprotheus.wellingtonbpneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbpneto.viewmodel.TratarDados;

import java.util.ArrayList;
import java.util.List;

public class ShowFragment extends Fragment {
    private FragmentShowBinding fragmentShowBinding;
    private MySharedDataPref mySharedDataPref;
    private DadosViewModel viewModel;
    private TratarDados tratarDados;
    private List<String> listCartas;
    private Handler handler;

    private static void pausa() {
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DadosViewModel.class);
        mySharedDataPref = new MySharedDataPref(this.requireActivity());
        tratarDados = new TratarDados();
        listCartas = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentShowBinding = FragmentShowBinding.inflate(inflater, container, false);

        if (mySharedDataPref.recuperarCartasEcolhidas() == null) {
            fragmentShowBinding.btnAvancar.setEnabled(false);
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.popBackStack();
            navController.navigate(R.id.nav_start);
        } else {
            pegarCartasParaMostrar();
        }
        return fragmentShowBinding.getRoot();
    }

    private void pegarCartasParaMostrar() {
        viewModel.getDeckmLiveData().observe(getViewLifecycleOwner(), listCartas -> {
            TratarDados.separarValoresBaralho separarValoresBaralho =
                    TratarDados.getSepararValoresBaralho(mySharedDataPref
                            .recuperarCartasEcolhidas());
            fragmentShowBinding.btnAvancar.setVisibility(View.GONE);
            mostrarCartas(separarValoresBaralho.listDeckCode);
        });
    }

    private void mostrarCartas(List<String> listCartas) {
        handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            Intent intent = new Intent(requireActivity(), MusicaFundo.class);
            requireActivity().startService(intent);
            for (int i = 0; i < listCartas.size(); i++) {
                String codigo = listCartas.get(i);
                new TratarDados();
                String codImg = TratarDados.getCodigoCarta(codigo);
                Naipe srcImg = Naipe.valueOf(Naipe.class, codImg);
                pausa();
                if (srcImg != null) {
                    handler.post(() -> fragmentShowBinding.ivCartasEscolhidas
                            .setImageResource(srcImg.getResourceId()));
                } else {
                    handler.post(() -> fragmentShowBinding.ivCartasEscolhidas
                            .setImageResource(R.drawable.four_color_deck));
                }
                if (i == listCartas.size() - 1) {
                    pausa();
                    handler.post(() -> {
                        fragmentShowBinding.ivCartasEscolhidas
                                .setImageResource(R.drawable.four_color_deck);
                        fragmentShowBinding.btnAvancar.setVisibility(View.VISIBLE);
                    });
                    proximaTela();
                }
            }
            pausa();
            requireActivity().stopService(intent);
        }).start();
    }

    private void proximaTela() {
        clearData();
        fragmentShowBinding.btnAvancar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_result);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentShowBinding = null;
    }

    public void clearData() {
        if (mySharedDataPref != null) {
            mySharedDataPref.removerCartasEscolhidas();
        } else {
            Log.e("ReportFragmento", "mySharedDataPref está vazio.");
        }
    }
}