package com.wprotheus.wellingtonbpneto.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.databinding.ActivityMainBinding;
import com.wprotheus.wellingtonbpneto.model.Baralho;
import com.wprotheus.wellingtonbpneto.utils.NotifyUtil;
import com.wprotheus.wellingtonbpneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbpneto.viewmodel.DataSetCartas;
import com.wprotheus.wellingtonbpneto.viewmodel.TratarDados;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private DadosViewModel dadosViewModel = null;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        FirebaseApp.initializeApp(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NotifyUtil.criarNotificacao(this);
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.navView.setItemIconTintList(null);
        dadosViewModel = new ViewModelProvider(this).get(DadosViewModel.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_fragment_content_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_deck, R.id.nav_start,
                R.id.nav_report, R.id.nav_show, R.id.nav_result, R.id.nav_view)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        baixarDadosBaralho();
    }

    private void baixarDadosBaralho() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        service.execute(() -> {
            try {
                Baralho tempo = new DataSetCartas().baixarJSON();
                if (tempo != null) {
                    dadosViewModel.getBaralhoMutableLiveData().postValue(tempo);
                    dadosViewModel.getDeckmLiveData().postValue(new TratarDados().prepararDados(tempo));
                    handler.post(() -> Toast.makeText(getApplicationContext(), R.string.dados_baixados, Toast.LENGTH_SHORT).show());
                } else
                    handler.post(() -> Toast.makeText(getApplicationContext(), R.string.problema_end, Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NotifyUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}