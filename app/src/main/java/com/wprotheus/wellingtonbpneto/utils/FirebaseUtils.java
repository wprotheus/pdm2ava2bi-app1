package com.wprotheus.wellingtonbpneto.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {
    private static final DatabaseReference
            RANKING_REF = FirebaseDatabase.getInstance().getReference().child("Raking");

    private FirebaseUtils() {
    }

    public static DatabaseReference getRankingRef() {
        return RANKING_REF;
    }
}