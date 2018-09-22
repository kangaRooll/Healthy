package com.a59070055.healthy_h1.weight;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.a59070055.healthy_h1.MenuFragment;
import com.a59070055.healthy_h1.R;

import java.util.ArrayList;
import java.util.List;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();
    ProgressBar _loading;
    FirebaseAuth _mAuth;
    FirebaseFirestore _mStore;
    ListView _weightList;
    WeightAdapter _weightAdapter;
    String _uid;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _mAuth = FirebaseAuth.getInstance();
        _mStore = FirebaseFirestore.getInstance();
        _loading = getView().findViewById(R.id.weight_loading);

        _weightList = getView().findViewById(R.id.weight_list);
        _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_item, weights);
        _uid = _mAuth.getCurrentUser().getUid();

        loadWeight(_uid);
        initAddWeightBtn();

        initBackButton();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    void initAddWeightBtn(){
        Button _addweightBtn = getView().findViewById(R.id.weight_add_weight_btn);

        _addweightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFormFragment()).addToBackStack(null).commit();
                Log.d("WEIGHT_FORM", "Click Add Weight Button");
            }
        });
    }

    void loadWeight(String uid) {
        _mStore.collection("myfitness").document(uid).collection("weight").orderBy("date", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                _weightAdapter.clear();
                _loading.setVisibility(View.GONE);

                if(!queryDocumentSnapshots.isEmpty()) {

                    List<DocumentSnapshot> listWeightData = queryDocumentSnapshots.getDocuments();
                    ArrayList<Weight> _tempWeight_1 = new ArrayList<>();
                    ArrayList<Weight> _tempWeight_2 = new ArrayList<>();

                    // Get data from firestore
                    for(DocumentSnapshot _doc : listWeightData) {
                        Weight _weightData = _doc.toObject(Weight.class);
                        Log.d("FIRESTORE", "Form Firestore : " + _weightData.getDate() + " : " + _weightData.getWeight());
                        _tempWeight_1.add(_weightData);
                        _tempWeight_2.add(_weightData);
                        Log.d("TEMP", "Add to tempWeight");
                    }

                    // Compare Weight and set Status
                    for(int i = 0;i < _tempWeight_1.size();i++) {
                        Log.d("TEMP", "I : " + i + " tempWeight : " + _tempWeight_1.get(i).getWeight() + " : " + _tempWeight_1.get(i).getStatus());
                        for(int j = 1;j < _tempWeight_2.size();j++) {
                            Log.d("TEMP", "J : " + j + " tempWeight 2 : " + _tempWeight_2.get(i).getWeight() + " : " + _tempWeight_2.get(i).getStatus());
                            if(_tempWeight_1.get(i).getWeight() > _tempWeight_2.get(j).getWeight()) {
                                _tempWeight_1.get(i).setStatus("ขึ้น");
                                Log.d("TEMP", "tempWeight : " + _tempWeight_1.get(i).getWeight() + " : " + _tempWeight_1.get(i).getStatus());
                                weights.add(_tempWeight_1.get(i));
                                updateStatusWeight(_tempWeight_1.get(i));
                            } else if(_tempWeight_1.get(i).getWeight() < _tempWeight_2.get(j).getWeight()){
                                _tempWeight_1.get(i).setStatus("ลง");
                                Log.d("TEMP", "tempWeight : " + _tempWeight_1.get(i).getWeight() + " : " + _tempWeight_1.get(i).getStatus());
                                weights.add(_tempWeight_1.get(i));
                                updateStatusWeight(_tempWeight_1.get(i));
                            } else {
                                _tempWeight_1.get(i).setStatus("คงที่");
                                Log.d("TEMP", "tempWeight : " + _tempWeight_1.get(i).getWeight() + " : " + _tempWeight_1.get(i).getStatus());
                                weights.add(_tempWeight_1.get(i));
                                updateStatusWeight(_tempWeight_1.get(i));
                            }
                            i++;
                        }
                        weights.add(_tempWeight_1.get(i));
                    }

                    _weightAdapter.notifyDataSetChanged();
                    _weightList.setAdapter(_weightAdapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "ERROR - " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void updateStatusWeight(Weight weight) {
        _mStore.collection("myfitness").document(_uid).collection("weight").document(weight.getDate())
                .update("status", weight.getStatus());
    }
    void initBackButton() {
        Button _btn = getView().findViewById(R.id.weight_add_back_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
            }
        });
    }
}
