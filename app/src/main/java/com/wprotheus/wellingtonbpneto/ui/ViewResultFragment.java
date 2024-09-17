package com.wprotheus.wellingtonbpneto.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.databinding.FragmentViewResultBinding;
import com.wprotheus.wellingtonbpneto.model.Partida;
import com.wprotheus.wellingtonbpneto.utils.FirebaseUtils;
import com.wprotheus.wellingtonbpneto.utils.adapters.RankingAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewResultFragment extends Fragment {
    private FragmentViewResultBinding fragmentViewResultBinding;
    private RankingAdapter rankingAdapter;
    private DatabaseReference reference;
    private List<Partida> partidaList;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseUtils.getRankingRef();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentViewResultBinding = FragmentViewResultBinding.inflate(inflater, container, false);
        partidaList = new ArrayList<>();
        recyclerView = fragmentViewResultBinding.rvRanking;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rankingAdapter = new RankingAdapter(partidaList);
        recyclerView.setAdapter(rankingAdapter);
        preparandoDadosVisualizacao();
        fragmentViewResultBinding.btnHome.setOnClickListener(v -> retornar());
        return fragmentViewResultBinding.getRoot();
    }

    private void retornar() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.popBackStack();
        navController.navigate(R.id.nav_home);
        navController.navigate(R.id.nav_start);
        navController.navigate(R.id.nav_report);
        navController.navigate(R.id.nav_show);
        navController.navigate(R.id.nav_result);
        navController.navigate(R.id.nav_view);
        navController.popBackStack(R.id.nav_home, false);
    }

    private void preparandoDadosVisualizacao() {
        reference = FirebaseUtils.getRankingRef();
        reference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                partidaList.clear();
                int position = 1;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Partida partida = snapshot.getValue(Partida.class);
                    if (partida != null) {
                        partida.setId(position);
                        partidaList.add(partida);
                        position++;
                    }
                }
                rankingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentViewResultBinding = null;
    }
}