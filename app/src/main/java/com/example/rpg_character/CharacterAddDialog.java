package com.example.rpg_character;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

public class CharacterAddDialog extends Dialog implements android.view.View.OnClickListener {//диалог добавления нового персонажа
    //character dialog when adding new character

    public CharacterActivity c;
    public Button yes;
    public Button load;
    private String code =null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    public CharacterAddDialog(CharacterActivity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.characteradd);
        yes = findViewById(R.id.Okinputbtn);
        load = findViewById(R.id.Loadcharbtn);
        yes.setOnClickListener(this);
        load.setOnClickListener(this);
        EditText pglvinput = findViewById(R.id.chlvinput);
        String tmp = 1 + "";
        pglvinput.setText(tmp);
        nfcAdapter = NfcAdapter.getDefaultAdapter(c);
        if (nfcAdapter != null) {
            readfromIntent(c.getIntent());
            pendingIntent = PendingIntent.getActivity(c, 0, new Intent(c, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Okinputbtn) {
            EditText pgnameinput = findViewById(R.id.chnameinput);
            EditText pgclassinput = findViewById(R.id.chclassinput);
            EditText pglvinput = findViewById(R.id.chlvinput);

            if (TextUtils.isEmpty(pgnameinput.getText().toString())) {
                pgnameinput.setError(getContext().getString(R.string.chnameerror));
                this.dismiss();
                Toast.makeText(this.c.getApplicationContext(), getContext().getString(R.string.chnameerror), Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CharacterAddDialog.this.show();
                    }
                });
            } else if (TextUtils.isEmpty(pgclassinput.getText().toString())) {
                pgclassinput.setError(getContext().getString(R.string.chclasserror));
                this.dismiss();
                Toast.makeText(this.c.getApplicationContext(), getContext().getString(R.string.chclasserror), Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CharacterAddDialog.this.show();
                    }
                });
            } else if (TextUtils.isEmpty(pglvinput.getText().toString())) {
                pglvinput.setError(getContext().getString(R.string.chlverror));
                this.dismiss();
                Toast.makeText(this.c.getApplicationContext(), getContext().getString(R.string.chlverror), Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CharacterAddDialog.this.show();
                    }
                });
            } else {
                c.character = new Character();
                int lv = Integer.parseInt(pglvinput.getText().toString());
                lv = (lv <= 0) ? 1 : lv;
                c.character.name = pgnameinput.getText().toString();
                c.character.chclass = pgclassinput.getText().toString();
                c.character.LV = lv;
                try {
                    c.character.EXP = c.xptable[c.character.LV - 1];
                } catch (Exception ignored) {
                }

                TextView pgnametxt = c.findViewById(R.id.pgnametxt);
                TextView pgclasstxt = c.findViewById(R.id.pgclasstxt);
                TextView pglvtxt = c.findViewById(R.id.pglvtxt);
                TextView pgexptxt = c.findViewById(R.id.pgxptxtv);
                TextView proftxt = c.findViewById(R.id.proftxt);

                c.setTitle(c.character.name);
                pgnametxt.setText(c.character.name);
                pgclasstxt.setText(c.character.chclass);
                String tmp = c.character.LV + "";
                pglvtxt.setText(tmp);
                tmp = c.character.EXP + " xp";
                pgexptxt.setText(tmp);
                tmp = "+" + (CharacterActivity.prof(c.character.LV));
                proftxt.setText(tmp);
                CharacterActivity.state.edit().putString("lastchar", c.character.name).apply();

                c.saveSchedaPG();
                this.dismiss();
            }
        } else if (v.getId() == R.id.Loadcharbtn) {//прогрузка
            //вызываем чтение метки
            readfromIntent(c.getIntent());
            if (code==null){
                Toast.makeText(c.getApplicationContext(),  getContext().getString(R.string.NoNFCTag),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                db.collection("characters")
                        .document(code)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Character character = documentSnapshot.toObject(Character.class);
                                    Log.d("test", String.valueOf(character.meleelist));
                                    c.character = character;
                                    c.PrepareCharPage();
                                    c.saveSchedaPG();
                                    code = null;
                                    pendingIntent = null;
                                }
                                else {
                                    Toast.makeText(c.getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                                    Handler handler = new Handler();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            CharacterAddDialog.this.show();
                                        }
                                    });
                                }
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(c, "Load failed.",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
            }
        }
        dismiss();
    }

    private void readfromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }

    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;
        String text = "";
        //String tagId=new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int languageCodeLength = payload[0] & 0063;
        try {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }
        code=text;
    }
}
