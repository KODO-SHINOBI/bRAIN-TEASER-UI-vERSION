package com.kodoshinobi.braintrainermaths;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SocreActivity extends AppCompatActivity {


    TextView scoreTxt,totalTxt;

    int score,total;
    FirebaseUser user;
    DatabaseReference reference  ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socre);

        score = getIntent().getIntExtra("score", 0);
        total = getIntent().getIntExtra("total", 0);

        scoreTxt = findViewById(R.id.score);
        totalTxt = findViewById(R.id.total);

        scoreTxt.setText(String.valueOf(score));
        totalTxt.setText(String.valueOf(total));

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Score").child(user.getUid()).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    score += Integer.parseInt(dataSnapshot.getValue().toString());

                }

                dataSnapshot.getRef().setValue(score);
/*
                   Intent intent = new Intent(SocreActivity.this, fblogin.class);
                   intent.putExtra("datatransfer",score);
                    startActivity(intent);
                    finish();
*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}