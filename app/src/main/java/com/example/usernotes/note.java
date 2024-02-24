package com.example.usernotes;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class note extends Fragment {

    EditText edt,title;                  //                      -NOTE-
    Button next,delete,update;           //   Here can edit note after click edit
    String txt;
    String note;
    int pos;
    int img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_note, container, false);
        edt=v.findViewById(R.id.textView4);
        update=v.findViewById(R.id.button3);

        next=v.findViewById(R.id.button2);
        title=v.findViewById(R.id.textView);

        title.setEnabled(false);
        title.setTextColor(Color.GRAY);
        edt.setEnabled(false);
        edt.setTextColor(Color.GRAY);

        Bundle bundle=this.getArguments();
        if(bundle!=null){
            txt=bundle.getString("title");
            note=bundle.getString("note");
            pos=bundle.getInt("pos");
            title.setText(txt);
            edt.setText(note);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setEnabled(true);
                title.setTextColor(Color.BLACK);
                edt.setEnabled(true);
                edt.setTextColor(Color.BLACK);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("title", String.valueOf(title.getText()));
                bundle.putString("note",String.valueOf(edt.getText()));
                bundle.putInt("pos",pos);
                home Note=new home();

                Note.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.container,Note).commit();
            }
        });


        return v;
    }
}