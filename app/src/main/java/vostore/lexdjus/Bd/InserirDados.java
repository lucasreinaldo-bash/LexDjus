package vostore.lexdjus.Bd;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vostore.lexdjus.R;

public class InserirDados extends AppCompatActivity {

    private EditText titulo,mensagem;
    private Button enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_dados);


        //Cast
        titulo = findViewById(R.id.titulo);
        mensagem = findViewById(R.id.mensagem);
        enviar = findViewById(R.id.enviar);



        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostDbHelper dbHelper = new PostDbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String titulomensagem = titulo.getText().toString();
                String corpoMensagem = mensagem.getText().toString();

                ContentValues values = new ContentValues();

                values.put(PostContract.PostEntry.COLUMN_NAME_TITLE,titulomensagem);
                values.put(PostContract.PostEntry.COLUMN_NAME_SUBTITLE,corpoMensagem);

                long newRowId = db.insert(PostContract.PostEntry.TABLE_NAME,null,values);
            }
        });

    }
}
