package vostore.lexdjus;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baset.randomtv.RandomTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import vostore.lexdjus.Bd.PostContract;
import vostore.lexdjus.Bd.PostDbHelper;
import vostore.lexdjus.Firebase.Common.Common;
import vostore.lexdjus.Firebase.Model.Question;
import vostore.lexdjus.Services.BroadcastLeis;
import vostore.lexdjus.Services.LockScreenService;
import vostore.lexdjus.Services.UpdateService;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference artigos,question;
    public WindowManager winManager;
    public RelativeLayout wrapperView;
    private CheckBox aprendi,naosei;
    private BroadcastReceiver broadcastReceiver;
    private Button close;
    BroadcastLeis broadcastLeis = new BroadcastLeis();

    private RandomTextView randomTextView;
    private ArrayList<String> textList = new ArrayList<>();
    private RecyclerView rvTextList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Cast
        database = FirebaseDatabase.getInstance();
        question = database.getReference("Artigos");
        aprendi = findViewById(R.id.checkbox_aprendido);
        naosei = findViewById(R.id.checkbox_naosei);
        randomTextView = findViewById(R.id.txt_leis);
        broadcastReceiver = new BroadcastLeis();
        close = findViewById(R.id.btn_close);
        //NotificationManager.startServiceInForeground()(new Intent(this, LockScreenService.class));
        setupArrayList();
        //setupViews();
        setupRandomTextView();


        loadQuestion(Common.categoryId);
        //Não poderá fechar sem antes marcar o Checkbox
        close.setEnabled(false);




        //Verificação do Checkbox
        naosei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   close.setEnabled(true);
                   aprendi.setEnabled(false);
               }
               else {
                   aprendi.setEnabled(true);
                   close.setEnabled(false);
               }
            }
        });
        aprendi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    naosei.setEnabled(false);
                    close.setEnabled(true);
                }
                else {
                    naosei.setEnabled(true);
                    close.setEnabled(false);
                }
            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
                }
        });

        Intent myService = new Intent(this, LockScreenService.class);
        IntentFilter intent = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        if (Intent.ACTION_SCREEN_OFF.equals(intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MainActivity.this.startForegroundService(new Intent(MainActivity.this, LockScreenService.class));

            } else {
                MainActivity.this.startService(new Intent(MainActivity.this, LockScreenService.class));

            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
    registerReceiver(broadcastLeis,intentFilter);
    //registerReceiver(broadcastReceiver,intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastLeis);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void setupRandomTextView() {
        randomTextView.setTextList(textList);
    }
    private void setupArrayList() {
        textList.addAll(Arrays.asList("Não há crime sem lei anterior que o defina. Não há pena sem prévia cominação legal. (Redação dada pela Lei nº 7.209, de 11.7.1984)", "PHP", "JavaScript", "Python", "Objective-C", "Ruby",
                "Perl", "C, C++ and C#", "SQL", "Swift"));
    }
    private void loadQuestion(String categoryId) {


    }
}

