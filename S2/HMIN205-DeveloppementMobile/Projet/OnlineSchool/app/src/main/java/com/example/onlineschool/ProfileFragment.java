package com.example.onlineschool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import com.example.onlineschool.Models.User;
import com.google.firebase.firestore.QueryDocumentSnapshot;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    //attributes db
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    //attributes view elements
    private EditText etName;
    private EditText etUsername;
    private EditText etAge;
    private EditText etPassword;
    private EditText etAddress;
    private EditText etEmail;

    //for picking interested grades
    private String[] listItems;
    private boolean[] checkedItems;
    private ArrayList<Integer> mUserItems = new ArrayList<Integer>();
    private Button bPickingGrades;
    private TextView tvItemsSelected;
    private String[] gradeArray;
    private List<Integer> grades;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        onPickingGrade(view);
        logOut(view);
        return view;
    }

    private void init(View view){
        //initialize db
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //initialize view elements
        etAge = (EditText) view.findViewById(R.id.etAge);
        etName = (EditText) view.findViewById(R.id.etName);
        etUsername = (EditText) view.findViewById(R.id.etUsername);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etAddress = (EditText) view.findViewById((R.id.etAddress));
        etEmail = (EditText) view.findViewById(R.id.etEmail);

        //initialize grade list
        tvItemsSelected = (TextView) view.findViewById(R.id.tvItemsSelected);
        listItems = getResources().getStringArray(R.array.grade_item);
        checkedItems = new boolean[listItems.length];

        //initialize user id
        userID = fAuth.getCurrentUser().getUid();

        final DocumentReference usersReference = fStore.collection("users").document(userID);
        usersReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                int age = documentSnapshot.getLong("age").intValue();
                etAge.setText(String.valueOf(age));
                etName.setText(documentSnapshot.getString("name"));
                etUsername.setText(documentSnapshot.getString("username"));
                etAddress.setText(documentSnapshot.getString("address"));
                etEmail.setText(documentSnapshot.getString("email"));

                String data = "";
                User user = documentSnapshot.toObject(User.class);

                for(Integer grade : user.getGrades()){
                    data += grade.toString() + ", ";
                }

                tvItemsSelected.setText(data);
            }
        });

    }

    private void logOut(View view){
        ((Button) view.findViewById(R.id.bLogout)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
    }

    private void onPickingGrade(View view){
        ((Button) view.findViewById(R.id.bPickingGrade)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle(getResources().getString(R.string.bSchoolYears));
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            if(! mUserItems.contains(position)){
                                mUserItems.add(position);
                            }
                        }else if(mUserItems.contains(position)){
                            mUserItems.remove(position);
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i=0; i<mUserItems.size(); i++){
                            item = item + listItems[mUserItems.get(i)];
                            grades.add(mUserItems.get(i));
                            if(i != mUserItems.size() -1){
                                item = item + ", ";
                            }
                        }
                        tvItemsSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(getResources().getString(R.string.bCancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton(getResources().getString(R.string.bClearAll), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i< checkedItems.length; i++){
                            checkedItems[i] = false;
                            mUserItems.clear();
                            tvItemsSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }
}
