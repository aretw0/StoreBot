package br.com.r2dev2.storebot;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private EditText contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        contentTxt = (EditText) findViewById(R.id.editCod);
    }


    // product barcode mode
    public void scanBar(View v) {
        try{ // inicia a activity do scaneamento de com.google.zxing.client.andoird.SCAN intenção
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE","PRODUCT_MODE");
            startActivityForResult(intent,0);
        } catch (ActivityNotFoundException anfe){
            // irá mostrar a caixa de download
            showDialog(CadastroActivity.this,"Nenhum Scanner encontrado!", "Fazer download do Scanner?", "Sim", "Não").show();
        }
    }

    // dialogo de alerta para o dialogo de download
    private static AlertDialog showDialog(final AppCompatActivity act, CharSequence title,
                                          CharSequence message, CharSequence buttonYes,
                                          CharSequence buttonNo) {

        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);

        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });

        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        return downloadDialog.show();
    }


    // Activity do método de resultado
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0){
            if(resultCode == RESULT_OK){

                // consiga os extras retornados pela intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                contentTxt.setText(contents);
                Toast toast = Toast.makeText(this, "Leitura:" + contents + " Formato:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
