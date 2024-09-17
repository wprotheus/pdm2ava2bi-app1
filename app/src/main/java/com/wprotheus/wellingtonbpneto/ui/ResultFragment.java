package com.wprotheus.wellingtonbpneto.ui;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.database.DatabaseReference;
import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.databinding.FragmentResultBinding;
import com.wprotheus.wellingtonbpneto.model.Partida;
import com.wprotheus.wellingtonbpneto.model.repo.PartidaHelper;
import com.wprotheus.wellingtonbpneto.utils.FirebaseUtils;
import com.wprotheus.wellingtonbpneto.utils.MySharedDataPref;

import java.util.Locale;

public class ResultFragment extends Fragment implements TextToSpeech.OnInitListener {
    private final String MSG = "Agora vamos testar sua matemática e memória";
    private FragmentResultBinding fragmentResultBinding;
    private MySharedDataPref mySharedDataPref;
    private PartidaHelper partidaHelper;
    private DatabaseReference reference;
    private TextToSpeech toSpeech;
    private String nomeJogador;
    private String resultado;
    private int somaUser = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseUtils.getRankingRef();
        mySharedDataPref = new MySharedDataPref(this.requireActivity());
        toSpeech = new TextToSpeech(requireActivity(), this);
        partidaHelper = new PartidaHelper(getContext());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentResultBinding = FragmentResultBinding.inflate(inflater, container, false);
        fragmentResultBinding.btnConferir.setOnClickListener(v -> conferir());
        fragmentResultBinding.btnAvancar.setOnClickListener(v -> proximaTela());
        if (mySharedDataPref.recuperarSomaCartas() == null) {
            fragmentResultBinding.btnConferir.setEnabled(false);
        }
        fragmentResultBinding.ettResultado.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) hideKeyboard(v);
        });
        return fragmentResultBinding.getRoot();
    }

    private void conferir() {
        if (!mySharedDataPref.recuperarSomaCartas().isEmpty()) {
            int soma = Integer.parseInt(mySharedDataPref.recuperarSomaCartas());
            String valor = fragmentResultBinding.ettResultado.getText().toString();
            if (valor.isEmpty())
                Toast.makeText(this.requireActivity(), "Digite um valor para a soma", Toast.LENGTH_SHORT).show();
            else {
                somaUser = Integer.parseInt(valor);
                verificarPartida(soma, somaUser);
            }
        }
    }

    private void proximaTela() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.popBackStack();
        navController.navigate(R.id.nav_view);
    }

    private void verificarPartida(Integer somaPartida, int somaUser) {
        fragmentResultBinding.btnConferir.setEnabled(false);
        fragmentResultBinding.ettResultado.setEnabled(false);
        resultado = (somaPartida == somaUser) ? "Acertou" : "Errou";
        fragmentResultBinding.ivResultado.setImageResource(
                resultado.equals("Acertou") ? R.drawable.happy_emoji : R.drawable.crying_emoji);
        nomeJogador = mySharedDataPref.recuperarNome();
        salvarResultadoNoFireBase(nomeJogador, somaUser, resultado);
        salvarPartidaNoBanco(nomeJogador, somaUser, resultado);
    }

    private void salvarResultadoNoFireBase(String nomeJogador, int somaUser, String resultado) {
        Partida p = new Partida();
        p.setNome(nomeJogador);
        p.setPalpite(String.valueOf(somaUser));
        p.setResultado(resultado);

        reference.push().setValue(p, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                Toast.makeText(requireContext(), R.string.msg_certo, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), R.string.msg_erro, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvarPartidaNoBanco(@NonNull String nomeJogador, int somaUser, String resultado) {
        SQLiteDatabase db = partidaHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME", nomeJogador);
        values.put("GUESS", somaUser);
        values.put("RESULT", resultado);
        db.insert("tb_partidas", null, values);
        db.close();

        clearData();
    }

    public void clearData() {
        fragmentResultBinding.ettResultado.setText(null);
        if (mySharedDataPref != null) {
            mySharedDataPref.removerNome();
            mySharedDataPref.removerSomaCartas();
        } else {
            Log.e("ResultFragmento", "mySharedDataPref está vazio.");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentResultBinding = null;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("pt", "BR");
            int resultLocale = toSpeech.setLanguage(locale);
            toSpeech.setSpeechRate(0.7f);
            if (resultLocale == TextToSpeech.LANG_MISSING_DATA ||
                    resultLocale == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("Problemas", "Problemas com o idioma escolhido.");
            } else {
                anunciarResultado();
                Log.d("TextToSpeech", "Falando: ");
            }
        } else {
            Log.e("Problemas", "Problemas com o TextToSpeech.");
        }
    }

    private void anunciarResultado() {
        toSpeech.speak(MSG, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}