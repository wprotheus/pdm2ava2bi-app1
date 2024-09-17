package com.wprotheus.wellingtonbpneto.ui;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.databinding.FragmentStartBinding;
import com.wprotheus.wellingtonbpneto.utils.MySharedDataPref;

public class StartFragment extends Fragment implements View.OnClickListener {
    private FragmentStartBinding startBinding;
    private MySharedDataPref mySharedDataPref;

    @SuppressLint("RestrictedApi")
    @NonNull
    private static View.OnFocusChangeListener getFocusChangeListener() {
        return (v, hasFocus) -> {
            if (!hasFocus) hideKeyboard(v);
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startBinding = FragmentStartBinding.inflate(inflater, container, false);
        mySharedDataPref = new MySharedDataPref(this.requireActivity());
        setUpInputs();
        iniciarOuvintes();
        return startBinding.getRoot();
    }

    private void setUpInputs() {
        startBinding.tietNome.setText(null);
        startBinding.tietQuantidade.setText(null);
    }

    private void iniciarOuvintes() {
        startBinding.btnJogar.setOnClickListener(this);
        startBinding.tietNome.setOnFocusChangeListener(getFocusChangeListener());
        startBinding.tietQuantidade.setOnFocusChangeListener(getFocusChangeListener());
    }

    @Override
    public void onClick(View v) {
        jogar();
    }

    private void jogar() {
        String qtdCartas = startBinding.tietQuantidade.getText().toString();
        if (startBinding.tietNome.getText().toString().isEmpty() ||
                startBinding.tietQuantidade.getText().toString().isEmpty()) {
            Toast.makeText(this.requireContext(), "Preencher todos os campos", Toast.LENGTH_SHORT).show();
            return;
        } else if (Integer.parseInt(qtdCartas) < 6 || Integer.parseInt(qtdCartas) > 52) {
            Toast.makeText(this.requireContext(), "Preencher a quantidade de cartas entre 6 e 52!", Toast.LENGTH_SHORT).show();
            return;
        }
        String nome = startBinding.tietNome.getText().toString();
        salvarNomeQtdCartas(nome, qtdCartas);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.popBackStack();
        navController.navigate(R.id.nav_report);
    }

    public void salvarNomeQtdCartas(String nome, String qtdCartas) {
        if (mySharedDataPref != null) {
            Log.d("StartFragmento", "Salvando o nome e quantidade de cartas.");
            mySharedDataPref.salvarNome(nome);
            mySharedDataPref.salvarQtdCartas(qtdCartas);
        } else {
            Log.e("StartFragmento", "mySharedDataPref está vazio.");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setUpInputs();
        startBinding = null;
    }
}