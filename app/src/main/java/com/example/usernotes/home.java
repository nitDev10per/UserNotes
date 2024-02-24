package com.example.usernotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class home extends Fragment implements ViewNote{

    RecyclerView reccycle;
    static ArrayList<dataClass> arr;
    int position;
    Button btn;
    ViewNote vn=this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        DataDAO dataDAO = new DataDAO(getContext());
        reccycle=v.findViewById(R.id.recycle);
        btn=v.findViewById(R.id.Glog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataClass newData = new dataClass(R.drawable.ic_launcher_foreground, "new", "");

                // Insert the new data into the SQLite database

                dataDAO.insertData(newData);

                // Update the RecyclerView with the latest data from the database
                arr = dataDAO.getAllData();
                recycleadapter adapt = new recycleadapter(getContext(), arr, vn);
                reccycle.setAdapter(adapt);
            }
        });


        arr = dataDAO.getAllData();
        Bundle bnl=this.getArguments();
        if(bnl!=null){
            position=bnl.getInt("pos");
            dataDAO.updateDataAtIndex(position, new dataClass(R.drawable.ic_launcher_foreground,bnl.getString("title"), bnl.getString("note")));
            arr = dataDAO.getAllData();
        }else if(arr.isEmpty()){

            dataDAO.insertData(new dataClass(R.drawable.ic_launcher_foreground,"hi,click here",
                    "1) if you want to add more Note then click add button \n\n " +
                            "2) if you want to edit text click edit button then click next \n\n" +
                            "3) if you want to delete note click Delete Icon \n\n" +
                            "4) if you want to logout from app click Logged icon then click logout \n\n" +
                            "5) your all data stored in your mobail storage"
            ));
            arr = dataDAO.getAllData();
        }



// Refresh the RecyclerView with the updated data
        reccycle.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleadapter adapt=new recycleadapter(getContext(),arr,this);
        reccycle.setAdapter(adapt);
        return v;
    }

    @Override
    public void viewNote(int pos) {
        Bundle bundle=new Bundle();
        bundle.putString("title",arr.get(pos).txt);
        bundle.putString("note",arr.get(pos).Note);
        bundle.putInt("pos",pos);
        note Note=new note();
        Note.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.container,Note).commit();
    }

    @Override
    public void removeN(int pos) {

        DataDAO dataDAO = new DataDAO(getContext());
        dataDAO.deleteDataAtPosition(pos);

        arr = dataDAO.getAllData();
        recycleadapter adapt = new recycleadapter(getContext(), arr, vn);
        reccycle.setAdapter(adapt);
    }
}