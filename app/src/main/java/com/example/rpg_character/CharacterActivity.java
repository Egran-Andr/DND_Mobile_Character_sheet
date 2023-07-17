package com.example.rpg_character;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.service.autofill.OnClickAction;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, CheckBox.OnCheckedChangeListener {//главная активность

    NfcAdapter mNfcAdapter;
    PendingIntent mPendingIntent;
    boolean writemode;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Tag mTag;
    Context mContext;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    int count;

    static final int PICK_IMAGE = 100;
    static SharedPreferences state;
    int modstr, moddex, modcon, modint, modwis, modcha;
    public Character character;
    TextView STR, STRmod,
            DEX, DEXmod,
            CON, CONmod,
            INT, INTmod,
            WIS, WISmod,
            CHA, CHAmod;
    TextView HP;
    TextView HPmax;
    TextView XP;
    TextView Armorclass;
    TextView abilitiesandtal;
    ImageView abilititalarrow;
    TextView inventory;
    ImageView inventoryarrow;
    TextView background;
    ImageView backgroundarrow;
    TextView attacks;
    ImageView attacksarrow;
    Button addxpbtn;
    ProgressBar xpbar;
    TextView lvtxt;
    TextView nametxt;
    TextView classtxt;
    TextView proftxt;
    CheckBox inspirationtbn;
    ImageView portrait;
    ImageButton HPplus;
    ImageButton HPminus;
    TextView mptxtv;
    TextView mgtxtv;
    TextView mstxtv;
    TextView mctxtv;
    TextView totalmtxtv;
    RecyclerView inventoryView;
    //STR abilities
    TextView tsstrtxt;
    CheckBox comptsstr;
    TextView athleticstxt;
    TextView athletics;
    CheckBox compathletics;
    CheckBox expatletics;
    //DEX abilities
    TextView tsdextxt;
    CheckBox comptsdex;
    TextView acrobaticstxt;
    TextView acrobatics;
    CheckBox compacrobatics;
    CheckBox expacrobatics;
    TextView stealthtxt;
    TextView stealth;
    CheckBox compstealth;
    CheckBox expstealth;
    TextView handtxt;
    TextView hand;
    CheckBox comphand;
    CheckBox exphand;
    //CON abilities
    TextView tscostxt;
    CheckBox comptscos;
    //INT abilities
    TextView tsinttxt;
    CheckBox comptsint;
    TextView investigationtxt;
    TextView investigation;
    CheckBox compinvestigation;
    CheckBox expinvestigation;
    TextView arcanetxt;
    TextView arcana;
    CheckBox comparcana;
    CheckBox exparcana;
    TextView historytxt;
    TextView history;
    CheckBox comphistory;
    CheckBox exphistory;
    TextView religionfolkloretxt;
    TextView religionfolklore;
    CheckBox compreligionfolklore;
    CheckBox expreligionfolklore;
    TextView naturetxt;
    TextView nature;
    CheckBox compnature;
    CheckBox expnature;
    //WIS abilities
    TextView tssagtxt;
    CheckBox comptssag;
    TextView survivaltxt;
    TextView survival;
    CheckBox compsurvival;
    CheckBox expsurvival;
    TextView medicinetxt;
    TextView medicine;
    CheckBox compmedicine;
    CheckBox expmedicine;
    TextView perceptiontxt;
    TextView perception;
    CheckBox compperception;
    CheckBox expperception;
    TextView insighttxt;
    TextView insight;
    CheckBox compinsight;
    CheckBox expinsight;
    TextView animaltxt;
    TextView animal;
    CheckBox companimal;
    CheckBox expanimal;
    //CHA abilities
    TextView tscartxt;
    CheckBox comptscar;
    TextView intimidationtxt;
    TextView intimidation;
    CheckBox compintimidation;
    CheckBox expintimidation;
    TextView deceptiontxt;
    TextView deception;
    CheckBox compdeception;
    CheckBox expdeception;
    TextView perfomancetxt;
    TextView perfomance;
    CheckBox compperfomance;
    CheckBox expperfomance;
    TextView persuasiontxt;
    TextView persuasion;
    CheckBox comppersuasion;
    CheckBox exppersuasion;
    Button addranged;
    Button addmelee;
    TableLayout rangedatks;
    TableLayout meleeatks;
    Button atkmelee;
    Button atkranged;
    Button addmanabtn;
    Button removemanabtn;
    TextView spellatk;
    TextView spellcd;
    TextView spellstat;
    TextView spellmana;
    Button spellapp;
    EditText cantrip;
    EditText firstlv;
    EditText secondlv;
    EditText thirdlv;
    EditText fourthlv;
    EditText fifthlv;
    EditText sixthlv;
    EditText seventhlv;
    EditText eighthlv;
    EditText ninthlv;
    EditText pluslv;
    TextView firstlvslots;
    TextView secondlvslots;
    TextView thirdlvslots;
    TextView fourthlvslots;
    TextView fifthlvslots;
    TextView sixthlvslots;
    TextView seventhlvslots;
    TextView eighthlvslots;
    TextView ninthlvslots;
    TextView pluslvslots;
    Button castfirstlv;
    Button castsecondlv;
    Button castthirdlv;
    Button castfourthlv;
    Button castfifthlv;
    Button castsixthlv;
    Button castseventhlv;
    Button casteightlv;
    Button castninthlv;
    Button castpluslv;

    int xptable[] = {0, 300, 900, 2700, 6500, 14000, 23000, 34000, 46000, 64000, 85000, 100000,
            120000, 140000, 165000, 195000, 225000, 265000, 305000, 355000};

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            Uri fileUri = intent.getData();
            if (fileUri != null) {
                try {
                    InputStream in = getContentResolver().openInputStream(fileUri);
                    InputStreamReader inr = new InputStreamReader(in);
                    BufferedReader br = new BufferedReader(inr);
                    String rstr;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((rstr = br.readLine()) != null) {
                        stringBuilder.append("\n").append(rstr);
                    }
                    in.close();
                    String json = stringBuilder.toString();
                    Character pg = (Character) (new Gson()).fromJson(json, Character.class);
                    Log.d("FILE", pg.name);
                    Log.d("FILE", "n: " + pg.inventorylist.size());
                    character = pg;
                    PrepareCharPage();
                } catch (IOException e) {
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.mainscroll), R.string.fileopenerror, Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_sheet);
        context = this;
        state = getApplicationContext().getSharedPreferences(getString(R.string.state), Context.MODE_PRIVATE);
        setTitle(getString(android.R.string.unknownName));
        int n = state.getInt("launchn", 0);
        n++;
        state.edit().putInt("launchn", n).apply();
        migrateFromPreferences();
        loadCharecter(state.getBoolean("loadlastchar", true));//продумать настройку того что мы загружаем последнего персонажа(Сувать куда нибудь пункт настройки где чекается true или false) сейчас грузит последнего по умолчанию
        //state.edit().putBoolean("loadlastchar", b).apply(); //настройка при switch compat
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if (nfcAdapter != null) {
                pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
                tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
                writingTagFilters = new IntentFilter[]{tagDetected};
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.dice) {
            DiceDialog inputdialog = new DiceDialog(this);
            inputdialog.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
            inputdialog.show();
        } else if (item.getItemId() == R.id.charselect) {
            loadCharecter(false);
        } else if (item.getItemId() == R.id.sleep) {
            String tmps;

            if (character.HP < character.HPMAX) {
                tmps = "" + character.HPMAX;
                character.HP = character.HPMAX;
                HP.setText(tmps);
            }

            character.currslot1 = character.slot1;
            tmps = character.currslot1 + "/" + character.slot1;
            firstlvslots.setText(tmps);

            character.currslot2 = character.slot2;
            tmps = character.currslot2 + "/" + character.slot2;
            secondlvslots.setText(tmps);

            character.currslot3 = character.slot3;
            tmps = character.currslot3 + "/" + character.slot3;
            thirdlvslots.setText(tmps);

            character.currslot4 = character.slot4;
            tmps = character.currslot4 + "/" + character.slot4;
            fourthlvslots.setText(tmps);

            character.currslot5 = character.slot5;
            tmps = character.currslot5 + "/" + character.slot5;
            fifthlvslots.setText(tmps);

            character.currslot6 = character.slot6;
            tmps = character.currslot6 + "/" + character.slot6;
            sixthlvslots.setText(tmps);

            character.currslot7 = character.slot7;
            tmps = character.currslot7 + "/" + character.slot7;
            seventhlvslots.setText(tmps);

            character.currslot8 = character.slot8;
            tmps = character.currslot8 + "/" + character.slot8;
            eighthlvslots.setText(tmps);

            character.currslot9 = character.slot9;
            tmps = character.currslot9 + "/" + character.slot9;
            ninthlvslots.setText(tmps);

            character.currslotplus = character.slotplus;
            tmps = character.currslotplus + "/" + character.slotplus;
            pluslvslots.setText(tmps);

            character.spellmana = character.spellmanamax;
            tmps = character.spellmanamax + "/" + character.spellmanamax;
            spellmana.setText(tmps);
            Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.resttxt), Snackbar.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.share)//cant press if nfc is not supported
        {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if (nfcAdapter == null) {
                Toast.makeText(this, R.string.NoNFCsupport, Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                try {
                    if (myTag == null) {
                        Toast.makeText(context, R.string.NoNFCTag, Toast.LENGTH_SHORT).show();
                    } else {
                        DocumentReference newCharRef = db.collection("characters").document();
                        newCharRef.set(character);
                        Log.d("Meme",newCharRef.getId());
                        write(newCharRef.getId(), myTag);
                        Toast.makeText(context, R.string.WriteSucksessNFC, Toast.LENGTH_SHORT).show();
                        myTag=null;
                    }
                } catch (IOException e) {
                    Toast.makeText(context, R.string.WriteErrorNFC, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(context, R.string.WriteErrorNFC, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    @Override
    public boolean onLongClick(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
        final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
        final AlertDialog alertd;
        String tempstr;
        int id = view.getId();
        if (id == R.id.pglvtxt) {//смена уровня персонажа
            String tmpstr = character.LV + "";
            input.setText(tmpstr);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            alert.setTitle(getString(R.string.insertlevelof) + " " + character.name);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int lv = Integer.parseInt(input.getText().toString());
                        if (lv <= 0) lv = 1;
                        String tempstr;
                        tempstr = lv + "";
                        lvtxt.setText(tempstr);
                        tempstr = "+" + prof(lv);
                        proftxt.setText(tempstr);
                        character.LV = lv;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.pgnametxt) {//смена имени персонажа
            input.setText(character.name);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);

            alert.setTitle(getString(R.string.insertnameof) + " " + character.name);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String name = input.getText().toString();
                    nametxt.setText(name);
                    character.name = name;
                    dialog.cancel();
                    PrepareCharPage();
                    saveSchedaPG();
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.pgclasstxt) {
            input.setText(character.chclass);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertclassof) + " " + character.name);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String classs = input.getText().toString();
                    classtxt.setText(classs);
                    character.chclass = classs;
                    dialog.cancel();
                    PrepareCharPage();
                    saveSchedaPG();
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.STR) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insert) + " " + getString(R.string.str));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        STR.setText(tempstr);
                        tempstr = suffix + mod;
                        STRmod.setText(tempstr);
                        character.STR = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.DEX) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insert) + " " + getString(R.string.dex));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        DEX.setText(tempstr);
                        tempstr = suffix + mod;
                        DEXmod.setText(tempstr);
                        character.DEX = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.CON) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insert) + " " + getString(R.string.constitution));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        CON.setText(tempstr);
                        tempstr = suffix + mod;
                        CONmod.setText(tempstr);
                        character.CON = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.INT) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insert) + " " + getString(R.string.inte));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        INT.setText(tempstr);
                        tempstr = suffix + mod;
                        INTmod.setText(tempstr);
                        character.INT = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.WIS) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insert) + " " + getString(R.string.wisdom));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        WIS.setText(tempstr);
                        tempstr = suffix + mod;
                        WISmod.setText(tempstr);
                        character.WIS = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.CHA) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insert) + " " + getString(R.string.charisma));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        CHA.setText(tempstr);
                        tempstr = suffix + mod;
                        CHAmod.setText(tempstr);
                        character.CHA = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.CA) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            tempstr = character.ARMOR + "";
            input.setText(tempstr);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            alert.setTitle(getString(R.string.insertarmor));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());

                        String tempstr = pnt + "";
                        Armorclass.setText(tempstr);
                        character.ARMOR = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.PF) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            tempstr = character.HP + "";
            input.setText(tempstr);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.inserthp));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tempstr = pnt + "";
                        HP.setText(tempstr);
                        character.HP = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.pfplus) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            tempstr = 0 + "";
            input.setText(tempstr);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.entercure));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        character.HP += pnt;

                        String tempstr = character.HP + "";
                        HP.setText(tempstr);
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.pfminus) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            tempstr = 0 + "";
            input.setText(tempstr);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.enterdamage));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        character.HP -= pnt;

                        String tempstr = character.HP + "";
                        HP.setText(tempstr);
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.PFmax) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            tempstr = character.HPMAX + "";
            input.setText(tempstr);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxhp));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());

                        String tempstr = pnt + "";
                        HPmax.setText(tempstr);
                        character.HPMAX = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.platcount) {
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.pgsplatpieces, character.name));
            tempstr = character.mplat + "";
            input.setText(tempstr);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int money = Integer.parseInt(input.getText().toString());
                        String tempstr = money + "";
                        mptxtv.setText(tempstr);
                        character.mplat = money;
                        double moneteTot = ceil(money * 10 + character.mgold + character.msilver * 0.1 + character.mcopper * 0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.goldcount) {
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.pgsgoldpieces, character.name));
            tempstr = character.mgold + "";
            input.setText(tempstr);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int money = Integer.parseInt(input.getText().toString());
                        String tempstr = money + "";
                        mgtxtv.setText(tempstr);
                        character.mgold = money;
                        double moneteTot = ceil(character.mplat * 10 + money + character.msilver * 0.1 + character.mcopper * 0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.silvercount) {
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.pgssilvpieces, character.name));
            tempstr = character.msilver + "";
            input.setText(tempstr);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int money = Integer.parseInt(input.getText().toString());
                        String tempstr = money + "";
                        mstxtv.setText(tempstr);
                        character.msilver = money;
                        double moneteTot = ceil(character.mplat * 10 + character.mgold + money * 0.1 + character.mcopper * 0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.coppercount) {
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.pgscopppieces, character.name));
            tempstr = character.mcopper + "";
            input.setText(tempstr);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int money = Integer.parseInt(input.getText().toString());
                        String tempstr = money + "";
                        mctxtv.setText(tempstr);
                        character.mcopper = money;
                        double moneteTot = ceil(character.mplat * 10 + character.mgold + character.msilver * 0.1 + money * 0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.manatxt) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxpoints));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        String maxp = input.getText().toString();
                        int mana = Integer.parseInt(maxp);
                        String tempstr = character.spellmana + "/" + mana;
                        spellmana.setText(tempstr);
                        character.spellmanamax = mana;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotfirsttxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_1) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        firstlvslots.setText(tmp);
                        character.slot1 = pnt;
                        character.currslot1 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotsecondtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_2) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        secondlvslots.setText(tmp);
                        character.slot2 = pnt;
                        character.currslot2 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotthirdtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_3) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        thirdlvslots.setText(tmp);
                        character.slot3 = pnt;
                        character.currslot3 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotfourthtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_4) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        fourthlvslots.setText(tmp);
                        character.slot4 = pnt;
                        character.currslot4 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotfifthtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_5) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        fifthlvslots.setText(tmp);
                        character.slot5 = pnt;
                        character.currslot5 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotsixthtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_6) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        sixthlvslots.setText(tmp);
                        character.slot6 = pnt;
                        character.currslot6 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotseventhtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_7) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        seventhlvslots.setText(tmp);
                        character.slot7 = pnt;
                        character.currslot7 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.sloteigthtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_8) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        eighthlvslots.setText(tmp);
                        character.slot8 = pnt;
                        character.currslot8 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotninthtxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level_9) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        ninthlvslots.setText(tmp);
                        character.slot9 = pnt;
                        character.currslot9 = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        } else if (id == R.id.slotplustxtv) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), null);
            alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.level) + ")");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        pluslvslots.setText(tmp);
                        character.slotplus = pnt;
                        character.currslotplus = pnt;
                        dialog.cancel();
                        PrepareCharPage();
                        saveSchedaPG();
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        String tempstr, suffix;
        final AlertDialog dialog;
        int id = view.getId();
        if (id == R.id.spelstatselection) {
            AlertDialog.Builder b = new AlertDialog.Builder(CharacterActivity.this);

            b.setTitle(getString(R.string.selectspellstat));
            String[] types = {getString(R.string.inte), getString(R.string.wisdom), getString(R.string.charisma)};
            b.setItems(types, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String stat = "";
                    switch (which) {
                        case 0:
                            stat = getString(R.string.inte);
                            break;
                        case 1:
                            stat = getString(R.string.wisdom);
                            break;
                        case 2:
                            stat = getString(R.string.charisma);
                            break;
                    }
                    character.spellstat = stat;
                    spellstat.setText(stat);
                    int bonus;
                    if (character.spellstat.equals("WIS"))
                        bonus = prof(character.LV) + mod(character.WIS);
                    else if (character.spellstat.equals("CHA"))
                        bonus = prof(character.LV) + mod(character.CHA);
                    else bonus = prof(character.LV) + mod(character.INT);
                    String suffix = (bonus < 0) ? "" : "+";
                    String tempstr;
                    tempstr = suffix + bonus;
                    spellatk.setText(tempstr);
                    tempstr = "" + (8 + bonus);
                    spellcd.setText(tempstr);
                    saveSchedaPG();
                }
            });
            dialog = b.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.show();
        } else if (id == R.id.dwnaab || id == R.id.skillstitle) {
            LinearLayout skilllyt = findViewById(R.id.skills);
            if (skilllyt.getVisibility() == View.VISIBLE) {
                skilllyt.setVisibility(View.GONE);
                abilititalarrow.setImageResource(R.drawable.downarrow);
            } else {
                skilllyt.setVisibility(View.VISIBLE);
                abilititalarrow.setImageResource(R.drawable.uparrow);
            }
        } else if (id == R.id.dwnaat || id == R.id.atktitle) {
            LinearLayout atklyt = findViewById(R.id.atk);
            if (atklyt.getVisibility() == View.VISIBLE) {
                atklyt.setVisibility(View.GONE);
                attacksarrow.setImageResource(R.drawable.downarrow);
            } else {
                atklyt.setVisibility(View.VISIBLE);
                attacksarrow.setImageResource(R.drawable.uparrow);
            }
        } else if (id == R.id.dwnainvent || id == R.id.inventorylabel) {
            LinearLayout invlyt = findViewById(R.id.inventory);
            if (invlyt.getVisibility() == View.VISIBLE) {
                invlyt.setVisibility(View.GONE);
                inventoryarrow.setImageResource(R.drawable.downarrow);
            } else {
                invlyt.setVisibility(View.VISIBLE);
                inventoryarrow.setImageResource(R.drawable.uparrow);
            }
        } else if (id == R.id.dwnabg || id == R.id.bgtitle) {
            LinearLayout bglyt = findViewById(R.id.background);
            if (bglyt.getVisibility() == View.VISIBLE) {
                bglyt.setVisibility(View.GONE);
                backgroundarrow.setImageResource(R.drawable.downarrow);
            } else {
                bglyt.setVisibility(View.VISIBLE);
                backgroundarrow.setImageResource(R.drawable.uparrow);
            }

        } else if (id == R.id.addxpbtn) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);

            final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setRawInputType(Configuration.KEYBOARD_12KEY);
            alert.setView(input);
            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            final AlertDialog alertd;
            alert.setTitle(getString(R.string.addxpof));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    try {
                        int xp = Integer.parseInt(input.getText().toString());
                        xp += character.EXP;

                        String tempstr = xp + " xp";
                        XP.setText(tempstr);
                        character.EXP = xp;
                        dialog.cancel();
                        try {
                            if (xp >= xptable[character.LV]) {
                                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.newlevel, "" + (character.LV + 1)), Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (Exception ignored) {
                        }
                        PrepareCharPage();//save and update
                    } catch (Exception ex) {
                        input.setError(getString(R.string.numbererror));
                        Toast.makeText(CharacterActivity.this, getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertd = alert.create();
            alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            alertd.show();
        } else if (id == R.id.pfplus) {
            character.HP++;
            tempstr = character.HP + "";
            HP.setText(tempstr);
        } else if (id == R.id.pfminus) {
            character.HP--;
            tempstr = character.HP + "";
            HP.setText(tempstr);
        } else if (id == R.id.comptsfor) {
            character.stSTR = comptsstr.isChecked();
            int tsf = mod(character.STR) + ((comptsstr.isChecked()) ? prof(character.LV) : 0);
            suffix = (tsf >= 0) ? "+" : "";
            tempstr = suffix + tsf;
            tsstrtxt.setText(tempstr);
        } else if (id == R.id.comptsdex) {
            character.stDEX = comptsdex.isChecked();
            int tsd = mod(character.DEX) + ((comptsdex.isChecked()) ? prof(character.LV) : 0);
            suffix = (tsd >= 0) ? "+" : "";
            tempstr = suffix + tsd;
            tsdextxt.setText(tempstr);
        } else if (id == R.id.comptscos) {
            character.stCON = comptscos.isChecked();
            int tsc = mod(character.CON) + ((comptscos.isChecked()) ? prof(character.LV) : 0);
            suffix = (tsc >= 0) ? "+" : "";
            tempstr = suffix + tsc;
            tscostxt.setText(tempstr);
        } else if (id == R.id.comptsint) {
            character.stINT = comptsint.isChecked();
            int tsi = mod(character.INT) + ((comptsint.isChecked()) ? prof(character.LV) : 0);
            suffix = (tsi >= 0) ? "+" : "";
            tempstr = suffix + tsi;
            tsinttxt.setText(tempstr);
        } else if (id == R.id.comptswis) {
            character.stWIS = comptssag.isChecked();
            int tsa = mod(character.WIS) + ((comptssag.isChecked()) ? prof(character.LV) : 0);
            suffix = (tsa >= 0) ? "+" : "";
            tempstr = suffix + tsa;
            tssagtxt.setText(tempstr);
        } else if (id == R.id.comptscha) {
            character.stCHA = comptscar.isChecked();
            int tsca = mod(character.CHA) + ((comptscar.isChecked()) ? prof(character.LV) : 0);
            suffix = (tsca >= 0) ? "+" : "";
            tempstr = suffix + tsca;
            tscartxt.setText(tempstr);
        } else if (id == R.id.atkranged) {
            DiceDialog inputdialog = new DiceDialog(CharacterActivity.this, 1, 20, mod(character.DEX) + prof(character.LV), getString(R.string.atklbl, getString(R.string.ranged), "" + mod(character.DEX), "" + prof(character.LV)));
            inputdialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            inputdialog.show();
        } else if (id == R.id.atkmelee) {
            DiceDialog inputdialog = new DiceDialog(CharacterActivity.this, 1, 20, mod(character.STR) + prof(character.LV), getString(R.string.atklbl, getString(R.string.melee), "" + mod(character.STR), "" + prof(character.LV)));
            inputdialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            inputdialog.show();
        } else if (id == R.id.addmana) {
            character.spellmana = Math.min(character.spellmana + 1, character.spellmanamax);
            tempstr = character.spellmana + "/" + character.spellmanamax;
            spellmana.setText(tempstr);
        } else if (id == R.id.removemana) {
            character.spellmana = Math.max(character.spellmana - 1, 0);
            tempstr = character.spellmana + "/" + character.spellmanamax;
            spellmana.setText(tempstr);
        } else if (id == R.id.castfirstlv) {
            if (character.currslot1 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_1).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_1).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot1 = (character.currslot1 > 0) ? character.currslot1 - 1 : 0;
            firstlvslots.setText(new StringBuilder().append(character.currslot1).append("/").append(character.slot1));
            saveSchedaPG();
        } else if (id == R.id.castsecondlv) {
            if (character.currslot2 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_2).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_2).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot2 = (character.currslot2 > 0) ? character.currslot2 - 1 : 0;
            secondlvslots.setText(new StringBuilder().append(character.currslot2).append("/").append(character.slot2));
            saveSchedaPG();
        } else if (id == R.id.castthirdlv) {
            if (character.currslot3 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_3).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_3).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot3 = (character.currslot3 > 0) ? character.currslot3 - 1 : 0;
            thirdlvslots.setText(new StringBuilder().append(character.currslot3).append("/").append(character.slot3));
            saveSchedaPG();
        } else if (id == R.id.castfourthlv) {
            if (character.currslot4 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_4).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_4).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot4 = (character.currslot4 > 0) ? character.currslot4 - 1 : 0;
            fourthlvslots.setText(new StringBuilder().append(character.currslot4).append("/").append(character.slot4));
            saveSchedaPG();
        } else if (id == R.id.castfifthlv) {
            if (character.currslot5 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_5).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_5).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot5 = (character.currslot5 > 0) ? character.currslot5 - 1 : 0;
            fifthlvslots.setText(new StringBuilder().append(character.currslot5).append("/").append(character.slot5));
            saveSchedaPG();
        } else if (id == R.id.castsixthlv) {
            if (character.currslot6 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_6).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_6).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot6 = (character.currslot6 > 0) ? character.currslot6 - 1 : 0;
            sixthlvslots.setText(new StringBuilder().append(character.currslot6).append("/").append(character.slot6));
            saveSchedaPG();
        } else if (id == R.id.castseventhlv) {
            if (character.currslot7 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_7).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_7).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot7 = (character.currslot7 > 0) ? character.currslot7 - 1 : 0;
            seventhlvslots.setText(new StringBuilder().append(character.currslot7).append("/").append(character.slot7));
            saveSchedaPG();
        } else if (id == R.id.casteightlv) {
            if (character.currslot8 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_8).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_8).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot8 = (character.currslot8 > 0) ? character.currslot8 - 1 : 0;
            eighthlvslots.setText(new StringBuilder().append(character.currslot8).append("/").append(character.slot8));
            saveSchedaPG();
        } else if (id == R.id.castninthlv) {
            if (character.currslot9 > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level_9).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level_9).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslot9 = (character.currslot9 > 0) ? character.currslot9 - 1 : 0;
            ninthlvslots.setText(new StringBuilder().append(character.currslot9).append("/").append(character.slot9));
            saveSchedaPG();
        } else if (id == R.id.castpluslv) {
            if (character.currslotplus > 0)
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.level).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.level).toLowerCase()), Snackbar.LENGTH_SHORT).show();
            character.currslotplus = (character.currslotplus > 0) ? character.currslotplus - 1 : 0;
            pluslvslots.setText(new StringBuilder().append(character.currslotplus).append("/").append(character.slotplus));
            saveSchedaPG();
        } else if (id == R.id.spellatktxt) {
            DiceDialog inputdialog;
            int bonus;
            if (character.spellstat.equals("WIS")) bonus = prof(character.LV) + mod(character.WIS);
            else if (character.spellstat.equals("CHA"))
                bonus = prof(character.LV) + mod(character.CHA);
            else bonus = prof(character.LV) + mod(character.INT);
            inputdialog = new DiceDialog(this, bonus, getString(R.string.cast) + " (" + character.spellstat + ")");
            inputdialog.show();
        }
    }


    public void PrepareCharPage() {
        String tempstr;
        String suffix;
        int bonus;
        STR = findViewById(R.id.STR);
        STRmod = findViewById(R.id.STRmod);
        DEX = findViewById(R.id.DEX);
        DEXmod = findViewById(R.id.DEXmod);
        CON = findViewById(R.id.CON);
        CONmod = findViewById(R.id.CONmod);
        INT = findViewById(R.id.INT);
        INTmod = findViewById(R.id.INTmod);
        WIS = findViewById(R.id.WIS);
        WISmod = findViewById(R.id.WISmod);
        CHA = findViewById(R.id.CHA);
        CHAmod = findViewById(R.id.CHAmod);
        Armorclass = findViewById(R.id.CA);
        HP = findViewById(R.id.PF);
        HPmax = findViewById(R.id.PFmax);
        HPplus = findViewById(R.id.pfplus);
        HPminus = findViewById(R.id.pfminus);
        abilitiesandtal = findViewById(R.id.skillstitle);
        abilititalarrow = findViewById(R.id.dwnaab);
        inventory = findViewById(R.id.inventorylabel);
        inventoryarrow = findViewById(R.id.dwnainvent);
        background = findViewById(R.id.bgtitle);
        backgroundarrow = findViewById(R.id.dwnabg);
        attacks = findViewById(R.id.atktitle);
        attacksarrow = findViewById(R.id.dwnaat);
        addxpbtn = findViewById(R.id.addxpbtn);
        XP = findViewById(R.id.pgxptxtv);
        xpbar = findViewById(R.id.xpbar);
        nametxt = findViewById(R.id.pgnametxt);
        classtxt = findViewById(R.id.pgclasstxt);
        lvtxt = findViewById(R.id.pglvtxt);
        proftxt = findViewById(R.id.proftxt);
        inspirationtbn = findViewById(R.id.inspirationbtn);
        portrait = findViewById(R.id.pgportrait);
        inventoryView = findViewById(R.id.inventoryRecV);
        rangedatks = findViewById(R.id.rangedatks);
        atkranged = findViewById(R.id.atkranged);
        meleeatks = findViewById(R.id.meleeatks);
        atkmelee = findViewById(R.id.atkmelee);
        addranged = findViewById(R.id.addrangedatk);
        addmelee = findViewById(R.id.addmeleeatk);
        addmanabtn = findViewById(R.id.addmana);
        removemanabtn = findViewById(R.id.removemana);
        spellmana = findViewById(R.id.manatxt);
        spellapp = findViewById(R.id.spellappbtn);
        cantrip = findViewById(R.id.cantriplist);
        firstlv = findViewById(R.id.firstlist);
        secondlv = findViewById(R.id.secondlist);
        thirdlv = findViewById(R.id.thirdlist);
        fourthlv = findViewById(R.id.fourthlsit);
        fifthlv = findViewById(R.id.fifthlist);
        sixthlv = findViewById(R.id.sixthlist);
        seventhlv = findViewById(R.id.seventhlist);
        eighthlv = findViewById(R.id.eigththlist);
        ninthlv = findViewById(R.id.ninthlist);
        pluslv = findViewById(R.id.pluslist);
        firstlvslots = findViewById(R.id.slotfirsttxtv);
        secondlvslots = findViewById(R.id.slotsecondtxtv);
        thirdlvslots = findViewById(R.id.slotthirdtxtv);
        fourthlvslots = findViewById(R.id.slotfourthtxtv);
        fifthlvslots = findViewById(R.id.slotfifthtxtv);
        sixthlvslots = findViewById(R.id.slotsixthtxtv);
        seventhlvslots = findViewById(R.id.slotseventhtxtv);
        eighthlvslots = findViewById(R.id.sloteigthtxtv);
        ninthlvslots = findViewById(R.id.slotninthtxtv);
        pluslvslots = findViewById(R.id.slotplustxtv);
        castfirstlv = findViewById(R.id.castfirstlv);
        castsecondlv = findViewById(R.id.castsecondlv);
        castthirdlv = findViewById(R.id.castthirdlv);
        castfourthlv = findViewById(R.id.castfourthlv);
        castfifthlv = findViewById(R.id.castfifthlv);
        castsixthlv = findViewById(R.id.castsixthlv);
        castseventhlv = findViewById(R.id.castseventhlv);
        casteightlv = findViewById(R.id.casteightlv);
        castninthlv = findViewById(R.id.castninthlv);
        castpluslv = findViewById(R.id.castpluslv);
        spellstat = findViewById(R.id.spelstatselection);
        spellatk = findViewById(R.id.spellatktxt);
        spellcd = findViewById(R.id.spellcdtxt);
        firstlvslots = findViewById(R.id.slotfirsttxtv);
        secondlvslots = findViewById(R.id.slotsecondtxtv);
        thirdlvslots = findViewById(R.id.slotthirdtxtv);
        fourthlvslots = findViewById(R.id.slotfourthtxtv);
        fifthlvslots = findViewById(R.id.slotfifthtxtv);
        sixthlvslots = findViewById(R.id.slotsixthtxtv);
        seventhlvslots = findViewById(R.id.slotseventhtxtv);
        eighthlvslots = findViewById(R.id.sloteigthtxtv);
        ninthlvslots = findViewById(R.id.slotninthtxtv);
        pluslvslots = findViewById(R.id.slotplustxtv);

        addmanabtn.setOnClickListener(this);
        removemanabtn.setOnClickListener(this);
        abilitiesandtal.setOnClickListener(this);
        abilititalarrow.setOnClickListener(this);
        inventory.setOnClickListener(this);
        inventoryarrow.setOnClickListener(this);
        background.setOnClickListener(this);
        backgroundarrow.setOnClickListener(this);
        attacks.setOnClickListener(this);
        attacksarrow.setOnClickListener(this);
        addxpbtn.setOnClickListener(this);
        nametxt.setOnLongClickListener(this);
        classtxt.setOnLongClickListener(this);
        lvtxt.setOnLongClickListener(this);
        Armorclass.setOnLongClickListener(this);
        Armorclass.setOnClickListener(this);
        HP.setOnLongClickListener(this);
        HP.setOnClickListener(this);
        HPplus.setOnClickListener(this);
        HPplus.setOnLongClickListener(this);
        HPminus.setOnClickListener(this);
        HPminus.setOnLongClickListener(this);
        HPmax.setOnLongClickListener(this);
        HPmax.setOnClickListener(this);
        STR.setOnLongClickListener(this);
        DEX.setOnLongClickListener(this);
        CON.setOnLongClickListener(this);
        INT.setOnLongClickListener(this);
        WIS.setOnLongClickListener(this);
        CHA.setOnLongClickListener(this);
        STR.setOnClickListener(this);
        DEX.setOnClickListener(this);
        CON.setOnClickListener(this);
        INT.setOnClickListener(this);
        WIS.setOnClickListener(this);
        CHA.setOnClickListener(this);
        spellstat.setOnClickListener(this);


        if (character == null) {//без этого не запускается на чистых устройствах. Весь код присваивания значений должен быть ПОСЛЕ блока
            character = new Character();
            Log.d("frompreparecharpage", "Not OK");
            CharacterAddDialog inputdialog = new CharacterAddDialog(this);
            inputdialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            inputdialog.show();
        }

        //прогрузка картинки(Странно, но не работает)
        //File videoFile = new File(character.portrait);
        //Log.i("PortraitPath", Uri.fromFile(videoFile).toString());
        //portrait.setImageURI(Uri.parse(new File(character.portrait).toString()));

        modstr = mod(character.STR);
        suffix = (modstr >= 0) ? "+" : "";
        tempstr = "" + character.STR;
        STR.setText(tempstr);
        tempstr = suffix + modstr;
        STRmod.setText(tempstr);
        STRmod.setOnClickListener(this);

        moddex = mod(character.DEX);
        suffix = (moddex >= 0) ? "+" : "";
        tempstr = "" + character.DEX;
        DEX.setText(tempstr);
        tempstr = suffix + moddex;
        DEXmod.setText(tempstr);
        DEXmod.setOnClickListener(this);

        modcon = mod(character.CON);
        suffix = (modcon >= 0) ? "+" : "";
        tempstr = "" + character.CON;
        CON.setText(tempstr);
        tempstr = suffix + modcon;
        CONmod.setText(tempstr);
        CONmod.setOnClickListener(this);

        modint = mod(character.INT);
        suffix = (modint >= 0) ? "+" : "";
        tempstr = "" + character.INT;
        INT.setText(tempstr);
        tempstr = suffix + modint;
        INTmod.setText(tempstr);
        INTmod.setOnClickListener(this);

        modwis = mod(character.WIS);
        suffix = (modwis >= 0) ? "+" : "";
        tempstr = "" + character.WIS;
        WIS.setText(tempstr);
        tempstr = suffix + modwis;
        WISmod.setText(tempstr);
        WISmod.setOnClickListener(this);

        modcha = mod(character.CHA);
        suffix = (modcha >= 0) ? "+" : "";
        tempstr = "" + character.CHA;
        CHA.setText(tempstr);
        tempstr = suffix + modcha;
        CHAmod.setText(tempstr);
        CHAmod.setOnClickListener(this);

        tempstr = "" + character.ARMOR;
        Armorclass.setText(tempstr);

        tempstr = "" + character.HP;
        HP.setText(tempstr);

        tempstr = "" + character.HPMAX;
        HPmax.setText(tempstr);
        state.edit().putString("lastchar", character.name).apply();
        setTitle(character.name);
        nametxt.setText(character.name);
        classtxt.setText(character.chclass);
        tempstr = character.LV + "";
        lvtxt.setText(tempstr);
        tempstr = "+" + (prof(character.LV));
        proftxt.setText(tempstr);

        inspirationtbn.setChecked(character.inspiration);
        inspirationtbn.setOnCheckedChangeListener(this);

        tempstr = character.EXP + " xp";
        XP.setText(tempstr);
        XP.setOnLongClickListener(this);
        addxpbtn.setOnClickListener(this);

        spellmana.setOnLongClickListener(this);
        tempstr = character.spellmana + "/" + character.spellmanamax;
        spellmana.setText(tempstr);


        //skills
        athletics = findViewById(R.id.atletica);
        athleticstxt = findViewById(R.id.atleticatxt);
        compathletics = findViewById(R.id.compathletics);
        expatletics = findViewById(R.id.expatletics);
        compathletics.setOnCheckedChangeListener(this);
        expatletics.setOnCheckedChangeListener(this);
        compathletics.setChecked(character.compathletics);
        expatletics.setChecked(character.expatletics);
        bonus = mod((character.STR)) + ((compathletics.isChecked()) ? ((expatletics.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        athletics.setText(tempstr);
        athletics.setOnClickListener(this);
        athleticstxt.setOnClickListener(this);

        acrobatics = findViewById(R.id.acrobatics);
        acrobaticstxt = findViewById(R.id.acrobaticstxt);
        compacrobatics = findViewById(R.id.compacrobatics);
        expacrobatics = findViewById(R.id.expacrobatics);
        compacrobatics.setOnCheckedChangeListener(this);
        expacrobatics.setOnCheckedChangeListener(this);
        compacrobatics.setChecked(character.compacrobatics);
        expacrobatics.setChecked(character.expacrobatics);
        bonus = mod(character.DEX) + ((compacrobatics.isChecked()) ? ((expacrobatics.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        acrobatics.setText(tempstr);
        acrobatics.setOnClickListener(this);
        acrobaticstxt.setOnClickListener(this);

        stealth = findViewById(R.id.stealth);
        stealthtxt = findViewById(R.id.stealthtxt);
        compstealth = findViewById(R.id.compstealth);
        expstealth = findViewById(R.id.expstealth);
        compstealth.setOnCheckedChangeListener(this);
        expstealth.setOnCheckedChangeListener(this);
        compstealth.setChecked(character.compstealth);
        expstealth.setChecked(character.expstealth);
        bonus = mod(character.DEX) + ((compstealth.isChecked()) ? ((expstealth.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        stealth.setText(tempstr);
        stealth.setOnClickListener(this);
        stealthtxt.setOnClickListener(this);

        hand = findViewById(R.id.hand);
        handtxt = findViewById(R.id.handtxt);
        comphand = findViewById(R.id.comphand);
        exphand = findViewById(R.id.exphand);
        comphand.setOnCheckedChangeListener(this);
        exphand.setOnCheckedChangeListener(this);
        comphand.setChecked(character.comphandpractice);
        exphand.setChecked(character.exphandpractice);
        bonus = mod(character.DEX) + ((comphand.isChecked()) ? ((exphand.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        hand.setText(tempstr);
        hand.setOnClickListener(this);
        handtxt.setOnClickListener(this);

        investigation = findViewById(R.id.investigation);
        investigationtxt = findViewById(R.id.investigationtxt);
        compinvestigation = findViewById(R.id.compinvestigation);
        expinvestigation = findViewById(R.id.expinvestigation);
        compinvestigation.setOnCheckedChangeListener(this);
        expinvestigation.setOnCheckedChangeListener(this);
        compinvestigation.setChecked(character.compinvestigation);
        expinvestigation.setChecked(character.expinvestigation);
        bonus = mod(character.INT) + ((compinvestigation.isChecked()) ? ((expinvestigation.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        investigation.setText(tempstr);
        investigation.setOnClickListener(this);
        investigationtxt.setOnClickListener(this);

        arcana = findViewById(R.id.arcana);
        arcanetxt = findViewById(R.id.arcanatxt);
        comparcana = findViewById(R.id.comparcana);
        exparcana = findViewById(R.id.exparcana);
        comparcana.setOnCheckedChangeListener(this);
        exparcana.setOnCheckedChangeListener(this);
        comparcana.setChecked(character.comparcana);
        exparcana.setChecked(character.exparcana);
        bonus = mod(character.INT) + ((comparcana.isChecked()) ? ((exparcana.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        arcana.setText(tempstr);
        arcana.setOnClickListener(this);
        arcanetxt.setOnClickListener(this);

        history = findViewById(R.id.history);
        historytxt = findViewById(R.id.historytxt);
        comphistory = findViewById(R.id.comphistory);
        exphistory = findViewById(R.id.exphistory);
        comphistory.setOnCheckedChangeListener(this);
        exphistory.setOnCheckedChangeListener(this);
        comphistory.setChecked(character.comphistory);
        exphistory.setChecked(character.exphistory);
        bonus = mod(character.INT) + ((comphistory.isChecked()) ? ((exphistory.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        history.setText(tempstr);
        history.setOnClickListener(this);
        historytxt.setOnClickListener(this);

        religionfolklore = findViewById(R.id.religionfolklore);
        religionfolkloretxt = findViewById(R.id.religiontxt);
        compreligionfolklore = findViewById(R.id.compreligionfolklore);
        expreligionfolklore = findViewById(R.id.expreligionfolklore);
        compreligionfolklore.setOnCheckedChangeListener(this);
        expreligionfolklore.setOnCheckedChangeListener(this);
        compreligionfolklore.setChecked(character.compreligion);
        expreligionfolklore.setChecked(character.expreligion);
        bonus = mod(character.INT) + ((compreligionfolklore.isChecked()) ? ((expreligionfolklore.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        religionfolklore.setText(tempstr);
        religionfolklore.setOnClickListener(this);
        religionfolkloretxt.setOnClickListener(this);

        nature = findViewById(R.id.nature);
        naturetxt = findViewById(R.id.naturetxt);
        compnature = findViewById(R.id.compnature);
        expnature = findViewById(R.id.expnature);
        compnature.setOnCheckedChangeListener(this);
        expnature.setOnCheckedChangeListener(this);
        compnature.setChecked(character.compnature);
        expnature.setChecked(character.expnature);
        bonus = mod(character.INT) + ((compnature.isChecked()) ? ((expnature.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        nature.setText(tempstr);
        nature.setOnClickListener(this);
        naturetxt.setOnClickListener(this);

        survival = findViewById(R.id.survival);
        survivaltxt = findViewById(R.id.survivaltxt);
        compsurvival = findViewById(R.id.compsurvival);
        expsurvival = findViewById(R.id.expsurvival);
        compsurvival.setOnCheckedChangeListener(this);
        expsurvival.setOnCheckedChangeListener(this);
        compsurvival.setChecked(character.compsurvival);
        expsurvival.setChecked(character.expsurvival);
        bonus = mod(character.WIS) + ((compsurvival.isChecked()) ? ((expsurvival.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        survival.setText(tempstr);
        survival.setOnClickListener(this);
        survivaltxt.setOnClickListener(this);

        medicine = findViewById(R.id.medicine);
        medicinetxt = findViewById(R.id.medicinetxt);
        compmedicine = findViewById(R.id.compmedicine);
        expmedicine = findViewById(R.id.expmedicine);
        compmedicine.setOnCheckedChangeListener(this);
        expmedicine.setOnCheckedChangeListener(this);
        compmedicine.setChecked(character.compmedicine);
        expmedicine.setChecked(character.expmedicine);
        bonus = mod(character.WIS) + ((compmedicine.isChecked()) ? ((expmedicine.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        medicine.setText(tempstr);
        medicine.setOnClickListener(this);
        medicinetxt.setOnClickListener(this);

        perception = findViewById(R.id.perception);
        perceptiontxt = findViewById(R.id.perceptiontxt);
        compperception = findViewById(R.id.compperception);
        expperception = findViewById(R.id.expperception);
        compperception.setOnCheckedChangeListener(this);
        expperception.setOnCheckedChangeListener(this);
        compperception.setChecked(character.compperception);
        expperception.setChecked(character.expperception);
        bonus = mod(character.WIS) + ((compperception.isChecked()) ? ((expperception.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        perception.setText(tempstr);
        perception.setOnClickListener(this);
        perceptiontxt.setOnClickListener(this);

        insight = findViewById(R.id.insight);
        insighttxt = findViewById(R.id.insighttxt);
        compinsight = findViewById(R.id.compinsight);
        expinsight = findViewById(R.id.expinsight);
        compinsight.setOnCheckedChangeListener(this);
        expinsight.setOnCheckedChangeListener(this);
        compinsight.setChecked(character.compinsight);
        expinsight.setChecked(character.expinsight);
        bonus = mod(character.WIS) + ((compinsight.isChecked()) ? ((expinsight.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        insight.setText(tempstr);
        insight.setOnClickListener(this);
        insighttxt.setOnClickListener(this);

        animal = findViewById(R.id.animal);
        animaltxt = findViewById(R.id.animaltxt);
        companimal = findViewById(R.id.companimal);
        expanimal = findViewById(R.id.expanimal);
        companimal.setOnCheckedChangeListener(this);
        expanimal.setOnCheckedChangeListener(this);
        companimal.setChecked(character.companimal);
        expanimal.setChecked(character.expanimal);
        bonus = mod(character.WIS) + ((companimal.isChecked()) ? ((expanimal.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        animal.setText(tempstr);
        animal.setOnClickListener(this);
        animaltxt.setOnClickListener(this);

        intimidation = findViewById(R.id.intimidation);
        intimidationtxt = findViewById(R.id.intimidationtxt);
        compintimidation = findViewById(R.id.compintimidation);
        expintimidation = findViewById(R.id.expintimidation);
        compintimidation.setOnCheckedChangeListener(this);
        expintimidation.setOnCheckedChangeListener(this);
        compintimidation.setChecked(character.compintimidation);
        expintimidation.setChecked(character.expintimidation);
        bonus = mod(character.CHA) + ((compintimidation.isChecked()) ? ((expintimidation.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        intimidation.setText(tempstr);
        intimidation.setOnClickListener(this);
        intimidationtxt.setOnClickListener(this);

        perfomance = findViewById(R.id.perfomance);
        perfomancetxt = findViewById(R.id.perfomancetxt);
        compperfomance = findViewById(R.id.compperfomance);
        expperfomance = findViewById(R.id.expperfomance);
        compperfomance.setOnCheckedChangeListener(this);
        expperfomance.setOnCheckedChangeListener(this);
        compperfomance.setChecked(character.compperfomanse);
        expperfomance.setChecked(character.expperfomanse);
        bonus = mod(character.CHA) + ((compperfomance.isChecked()) ? ((expperfomance.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        perfomance.setText(tempstr);
        perfomance.setOnClickListener(this);
        perfomancetxt.setOnClickListener(this);

        persuasion = findViewById(R.id.persuation);
        persuasiontxt = findViewById(R.id.persuationtxt);
        comppersuasion = findViewById(R.id.compppersuation);
        exppersuasion = findViewById(R.id.exppersuation);
        comppersuasion.setOnCheckedChangeListener(this);
        exppersuasion.setOnCheckedChangeListener(this);
        comppersuasion.setChecked(character.comppersuasion);
        exppersuasion.setChecked(character.exppersuasion);
        bonus = mod(character.CHA) + ((comppersuasion.isChecked()) ? ((exppersuasion.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        persuasion.setText(tempstr);
        persuasion.setOnClickListener(this);
        persuasiontxt.setOnClickListener(this);

        deception = findViewById(R.id.deception);
        deceptiontxt = findViewById(R.id.deceptiontxt);
        compdeception = findViewById(R.id.compdeception);
        expdeception = findViewById(R.id.expdeception);
        compdeception.setOnCheckedChangeListener(this);
        expdeception.setOnCheckedChangeListener(this);
        compdeception.setChecked(character.compdeception);
        expdeception.setChecked(character.expdeception);
        bonus = mod(character.CHA) + ((compdeception.isChecked()) ? ((expdeception.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        deception.setText(tempstr);
        deception.setOnClickListener(this);
        deceptiontxt.setOnClickListener(this);

        atkranged.setOnClickListener(this);
        rangedatks.removeAllViews();

        TableRow header = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.rangeditem, null);
        TextView name = header.findViewById(R.id.rangedname);
        TextView range = header.findViewById(R.id.range);
        TextView bonusrange = header.findViewById(R.id.rangedbonus);
        TextView comprange = header.findViewById(R.id.rangedbonuscomp);
        TextView damage = header.findViewById(R.id.rangeddamage);
        Button removebtn = header.findViewById(R.id.removeranged);
        name.setText(getString(R.string.weaponname));
        range.setText(getString(R.string.range));
        bonusrange.setText(getString(R.string.bonusdex));
        comprange.setText(getString(R.string.comp));
        damage.setText(getString(R.string.damage));
        removebtn.setText("");
        removebtn.setEnabled(false);
        rangedatks.addView(header);

        spellstat.setText(character.spellstat);
        if (character.spellstat.equals("SAG")) bonus = prof(character.LV) + mod(character.WIS);
        else if (character.spellstat.equals("CAR")) bonus = prof(character.LV) + mod(character.CHA);
        else bonus = prof(character.LV) + mod(character.INT);
        suffix = (bonus < 0) ? "" : "+";
        tempstr = suffix + bonus;
        spellatk.setText(tempstr);
        spellatk.setOnClickListener(this);
        tempstr = "" + (8 + bonus);
        spellcd.setText(tempstr);
        spellstat.setOnClickListener(this);

        for (RangedWeapon weap : character.rangelist) {
            final TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.rangeditem, null);
            name = newrow.findViewById(R.id.rangedname);
            range = newrow.findViewById(R.id.range);
            bonusrange = newrow.findViewById(R.id.rangedbonus);
            comprange = newrow.findViewById(R.id.rangedbonuscomp);
            damage = newrow.findViewById(R.id.rangeddamage);
            removebtn = newrow.findViewById(R.id.removeranged);

            int bonusb = mod(character.DEX);
            String suffixb = (bonusb >= 0) ? "+" : "";

            name.setText(weap.name);
            range.setText(weap.range);
            tempstr = suffixb + bonusb;
            bonusrange.setText(tempstr);
            tempstr = "+" + prof(character.LV);
            comprange.setText(tempstr);
            damage.setText(weap.damage);

            final RangedWeapon finalweap = weap;
            removebtn.setOnLongClickListener(view -> {
                character.rangelist.remove(finalweap);
                rangedatks.removeView(newrow);
                saveSchedaPG();
                return true;
            });
            removebtn.setOnClickListener(view -> {
                Toast.makeText(this, getString(R.string.keeptoremove), Toast.LENGTH_SHORT).show();
            });
            final String dmg = weap.damage.toLowerCase(Locale.ROOT).substring(0, weap.damage.lastIndexOf(" "));
            if (dmg.contains("d")) {
                try {
                    final int dices = Integer.parseInt(dmg.split("d")[0]);
                    int max = 0;
                    int rollbonus = 0;
                    if (dmg.split("d")[1].contains("+")) {
                        max = Integer.parseInt(dmg.split("d")[1].split("\\+")[0].replace(" ", ""));
                        rollbonus = Integer.parseInt(dmg.split("d")[1].split("\\+")[1].replace(" ", ""));
                        Log.d("WEAP", dmg);
                    } else {
                        max = Integer.parseInt(dmg.split("d")[1].replace(" ", ""));
                        rollbonus = 0;
                    }
                    final int finalRollbonus = rollbonus;
                    final int finalMax = max;
                    damage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DiceDialog inputdialog = new DiceDialog(CharacterActivity.this, dices, finalMax, finalRollbonus, finalweap.name);
                            inputdialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                            inputdialog.show();
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rangedatks.addView(newrow);
        }

        atkmelee.setOnClickListener(this);
        meleeatks.removeAllViews();

        header = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleeitem, null);
        name = header.findViewById(R.id.meleename);
        bonusrange = header.findViewById(R.id.meleebonus);
        comprange = header.findViewById(R.id.meleebonuscomp);
        damage = header.findViewById(R.id.meleedamage);
        removebtn = header.findViewById(R.id.removemelee);
        name.setText(getString(R.string.weaponname));
        bonusrange.setText(getString(R.string.bonusstr));
        comprange.setText(getString(R.string.comp));
        damage.setText(getString(R.string.damage));
        removebtn.setText("");
        removebtn.setEnabled(false);
        meleeatks.addView(header);

        for (final MeleeWeapon weap : character.meleelist) {
            final TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleeitem, null);
            name = newrow.findViewById(R.id.meleename);
            bonusrange = newrow.findViewById(R.id.meleebonus);
            comprange = newrow.findViewById(R.id.meleebonuscomp);
            damage = newrow.findViewById(R.id.meleedamage);

            removebtn = newrow.findViewById(R.id.removemelee);

            int bonusb = mod(character.STR);
            String suffixb = (bonusb >= 0) ? "+" : "";

            name.setText(weap.name);
            tempstr = suffixb + bonusb;
            bonusrange.setText(tempstr);
            tempstr = "+" + prof(character.LV);
            comprange.setText(tempstr);
            damage.setText(weap.damage);

            final MeleeWeapon finalweap = weap;
            removebtn.setOnLongClickListener(view -> {
                character.meleelist.remove(finalweap);
                meleeatks.removeView(newrow);
                saveSchedaPG();
                return true;
            });
            removebtn.setOnClickListener(view -> {
                Toast.makeText(this, getString(R.string.keeptoremove), Toast.LENGTH_SHORT).show();
            });
            final String dmg = weap.damage.toLowerCase(Locale.ROOT).substring(0, weap.damage.lastIndexOf(" "));
            if (dmg.contains("d")) {//поиск по кубикам
                try {
                    final int dices = Integer.parseInt(dmg.split("d")[0]);
                    int max = 0;
                    int rollbonus = 0;
                    if (dmg.split("d")[1].contains("+")) {
                        max = Integer.parseInt(dmg.split("d")[1].split("\\+")[0].replace(" ", ""));
                        rollbonus = Integer.parseInt(dmg.split("d")[1].split("\\+")[1].replace(" ", ""));
                        Log.d("WEAP", dmg);
                    } else {
                        max = Integer.parseInt(dmg.split("d")[1].replace(" ", ""));
                        rollbonus = 0;
                    }
                    final int finalRollbonus = rollbonus;
                    final int finalMax = max;
                    damage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DiceDialog inputdialog = new DiceDialog(CharacterActivity.this, dices, finalMax, finalRollbonus, weap.name);
                            inputdialog.show();
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            meleeatks.addView(newrow);
        }

        addranged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.rangeditem, null);
                RangedDialog inputdialog = new RangedDialog(CharacterActivity.this, character, newrow, rangedatks);
                inputdialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                inputdialog.show();
            }
        });

        addmelee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleeitem, null);
                MeleeDialog inputdialog = new MeleeDialog(CharacterActivity.this, character, newrow, meleeatks);
                inputdialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                inputdialog.show();
            }
        });

        try {
            int progress = (int) floor((double) (character.EXP * 100) / xptable[character.LV]);
            xpbar.setVisibility(View.VISIBLE);
            xpbar.setProgress(progress);
        } catch (Exception ex) {
            xpbar.setProgress(100);
        }

        portrait.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                //gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //startActivityForResult(gallery, PICK_IMAGE);
                Intent gallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
                return true;
            }
        });


        EditText linguetxt = findViewById(R.id.linguetxt);
        linguetxt.setText(character.languaestxt);
        linguetxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.languaestxt = editable.toString();
                saveSchedaPG();
            }
        });
        linguetxt.clearFocus();

        EditText armitxt = findViewById(R.id.armitxt);
        armitxt.setText(character.armitxt);
        armitxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.armitxt = editable.toString();
                saveSchedaPG();
            }
        });
        armitxt.clearFocus();

        EditText talentitxt = findViewById(R.id.talentitxt);
        talentitxt.setText(character.talentstxt);
        talentitxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.talentstxt = editable.toString();
                saveSchedaPG();
            }
        });
        talentitxt.clearFocus();

        EditText abilitatxt = findViewById(R.id.abilitatxt);
        abilitatxt.setText(character.abilitytxt);
        abilitatxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.abilitytxt = editable.toString();
                saveSchedaPG();
            }
        });
        abilitatxt.clearFocus();

        cantrip.setText(character.cantrips);
        cantrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.cantrips = editable.toString();
                saveSchedaPG();
            }
        });
        cantrip.clearFocus();

        firstlv.setText(character.lv1);
        firstlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv1 = editable.toString();
                saveSchedaPG();
            }
        });
        firstlv.clearFocus();
        firstlvslots.setText(new StringBuilder().append(character.currslot1).append("/").append(character.slot1));
        firstlvslots.setOnLongClickListener(this);
        firstlvslots.setOnClickListener(this);
        castfirstlv.setOnClickListener(this);


        secondlv.setText(character.lv2);
        secondlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv2 = editable.toString();
                saveSchedaPG();
            }
        });
        secondlv.clearFocus();
        secondlvslots.setText(new StringBuilder().append(character.currslot2).append("/").append(character.slot2));
        secondlvslots.setOnLongClickListener(this);
        secondlvslots.setOnClickListener(this);
        castsecondlv.setOnClickListener(this);

        thirdlv.setText(character.lv3);
        thirdlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv3 = editable.toString();
                saveSchedaPG();
            }
        });
        thirdlv.clearFocus();
        thirdlvslots.setText(new StringBuilder().append(character.currslot3).append("/").append(character.slot3));
        thirdlvslots.setOnLongClickListener(this);
        thirdlvslots.setOnClickListener(this);
        castthirdlv.setOnClickListener(this);

        fourthlv.setText(character.lv4);
        fourthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv4 = editable.toString();
                saveSchedaPG();
            }
        });
        fourthlv.clearFocus();
        fourthlvslots.setText(new StringBuilder().append(character.currslot4).append("/").append(character.slot4));
        fourthlvslots.setOnLongClickListener(this);
        fourthlvslots.setOnClickListener(this);
        castfourthlv.setOnClickListener(this);

        fifthlv.setText(character.lv5);
        fifthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv5 = editable.toString();
                saveSchedaPG();
            }
        });
        fifthlv.clearFocus();
        fifthlvslots.setText(new StringBuilder().append(character.currslot5).append("/").append(character.slot5));
        fifthlvslots.setOnLongClickListener(this);
        fifthlvslots.setOnClickListener(this);
        castfifthlv.setOnClickListener(this);

        sixthlv.setText(character.lv6);
        sixthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv6 = editable.toString();
                saveSchedaPG();
            }
        });
        sixthlv.clearFocus();
        sixthlvslots.setText(new StringBuilder().append(character.currslot6).append("/").append(character.slot6));
        sixthlvslots.setOnLongClickListener(this);
        sixthlvslots.setOnClickListener(this);
        castsixthlv.setOnClickListener(this);

        seventhlv.setText(character.lv7);
        seventhlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv7 = editable.toString();
                saveSchedaPG();
            }
        });
        seventhlv.clearFocus();
        seventhlvslots.setText(new StringBuilder().append(character.currslot7).append("/").append(character.slot7));
        seventhlvslots.setOnLongClickListener(this);
        seventhlvslots.setOnClickListener(this);
        castseventhlv.setOnClickListener(this);

        eighthlv.setText(character.lv8);
        eighthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv8 = editable.toString();
                saveSchedaPG();
            }
        });
        eighthlv.clearFocus();
        eighthlvslots.setText(new StringBuilder().append(character.currslot8).append("/").append(character.slot8));
        eighthlvslots.setOnLongClickListener(this);
        eighthlvslots.setOnClickListener(this);
        casteightlv.setOnClickListener(this);

        ninthlv.setText(character.lv9);
        ninthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv9 = editable.toString();
                saveSchedaPG();
            }
        });
        ninthlv.clearFocus();
        ninthlvslots.setText(new StringBuilder().append(character.currslot9).append("/").append(character.slot9));
        ninthlvslots.setOnLongClickListener(this);
        ninthlvslots.setOnClickListener(this);
        castninthlv.setOnClickListener(this);

        pluslv.setText(character.lvplus);
        pluslv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lvplus = editable.toString();
                saveSchedaPG();
            }
        });
        pluslv.clearFocus();
        pluslvslots.setText(new StringBuilder().append(character.currslotplus).append("/").append(character.slotplus));
        pluslvslots.setOnLongClickListener(this);
        pluslvslots.setOnClickListener(this);
        castpluslv.setOnClickListener(this);

        mptxtv = findViewById(R.id.platcount);
        mgtxtv = findViewById(R.id.goldcount);
        mstxtv = findViewById(R.id.silvercount);
        mctxtv = findViewById(R.id.coppercount);
        totalmtxtv = findViewById(R.id.totalpgmoneytxtv);

        double money;
        money = ceil(character.mplat * 10 + character.mgold + character.msilver * 0.1 + character.mcopper * 0.01);
        String txt = String.format(Locale.getDefault(), "%.0f", money);
        String strstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
        totalmtxtv.setText(strstr);

        tempstr = character.mplat + "";
        mptxtv.setText(tempstr);
        mptxtv.setOnLongClickListener(this);
        tempstr = character.mgold + "";
        mgtxtv.setText(tempstr);
        mgtxtv.setOnLongClickListener(this);
        tempstr = character.msilver + "";
        mstxtv.setText(tempstr);
        mstxtv.setOnLongClickListener(this);
        tempstr = character.mcopper + "";
        mctxtv.setText(tempstr);
        mctxtv.setOnLongClickListener(this);

        tsstrtxt = findViewById(R.id.TSFOR);
        comptsstr = findViewById(R.id.comptsfor);
        comptsstr.setChecked(character.stSTR);
        int ts = mod(character.STR) + ((comptsstr.isChecked()) ? prof(character.LV) : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsstrtxt.setText(tempstr);
        tsstrtxt.setOnClickListener(this);
        comptsstr.setOnClickListener(this);

        tsdextxt = findViewById(R.id.TSDEX);
        comptsdex = findViewById(R.id.comptsdex);
        comptsdex.setChecked(character.stDEX);
        ts = mod(character.DEX) + ((comptsdex.isChecked()) ? prof(character.LV) : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsdextxt.setText(tempstr);
        tsdextxt.setOnClickListener(this);
        comptsdex.setOnClickListener(this);

        tscostxt = findViewById(R.id.TSCOS);
        comptscos = findViewById(R.id.comptscos);
        comptscos.setChecked(character.stCON);
        ts = mod(character.CON) + ((comptscos.isChecked()) ? prof(character.LV) : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tscostxt.setText(tempstr);
        tscostxt.setOnClickListener(this);
        comptscos.setOnClickListener(this);

        tsinttxt = findViewById(R.id.TSINT);
        comptsint = findViewById(R.id.comptsint);
        comptsint.setChecked(character.stINT);
        ts = mod(character.INT) + ((comptsint.isChecked()) ? prof(character.LV) : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsinttxt.setText(tempstr);
        tsinttxt.setOnClickListener(this);
        comptsint.setOnClickListener(this);

        tssagtxt = findViewById(R.id.TSSAG);
        comptssag = findViewById(R.id.comptswis);
        comptssag.setChecked(character.stWIS);
        ts = mod(character.WIS) + ((comptssag.isChecked()) ? prof(character.LV) : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tssagtxt.setText(tempstr);
        tssagtxt.setOnClickListener(this);
        comptssag.setOnClickListener(this);

        tscartxt = findViewById(R.id.TSCAR);
        comptscar = findViewById(R.id.comptscha);
        comptscar.setChecked(character.stCHA);
        ts = mod(character.CHA) + ((comptscar.isChecked()) ? prof(character.LV) : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tscartxt.setText(tempstr);
        tscartxt.setOnClickListener(this);
        comptscar.setOnClickListener(this);

        final InventoryAdapter inventoryAdapter = new InventoryAdapter(character);
        inventoryView.setAdapter(inventoryAdapter);
        LinearLayoutManager lytmngr = new LinearLayoutManager(CharacterActivity.this);
        lytmngr.setOrientation(LinearLayoutManager.HORIZONTAL);
        inventoryView.setLayoutManager(lytmngr);

        Button addObjBtn = findViewById(R.id.addobjbtn);
        addObjBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                final EditText input = new EditText(view.getContext());
                input.setHint(getString(R.string.weaponname));
                alert.setView(input);
                alert.setNegativeButton(view.getContext().getString(R.string.cancel), null);
                final AlertDialog alertd;
                alert.setTitle(getString(R.string.addobtninventory));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = input.getText().toString();

                        inventoryAdapter.addObj(new InventoryItem(name, getString(R.string.keeppressedtoedit)));
                        inventoryAdapter.notifyDataSetChanged();
                        dialog.cancel();
                        saveSchedaPG();
                    }
                });
                alertd = alert.create();
                alertd.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                alertd.show();
            }
        });
        EditText invtxt = findViewById(R.id.invtxt);
        invtxt.setText(character.inventorytxt);
        invtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.inventorytxt = editable.toString();
                saveSchedaPG();
            }
        });
        invtxt.clearFocus();
        EditText backgroundtxt = findViewById(R.id.backgroundtxt);
        backgroundtxt.setText(character.backgroundtxt);
        backgroundtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.backgroundtxt = editable.toString();
                saveSchedaPG();
            }
        });
        backgroundtxt.clearFocus();
    }

    public void migrateFromPreferences() {
        if (state.getString("pgname", null) != null) {
            Snackbar.make(findViewById(R.id.mainscroll), R.string.migratemsg, Snackbar.LENGTH_LONG).show();
            character = new Character();
            character.name = state.getString("pgname", "");
            character.chclass = state.getString("pgclass", "");
            character.LV = state.getInt("pglv", 1);
            character.STR = state.getInt("STR", 10);
            character.DEX = state.getInt("DEX", 10);
            character.CON = state.getInt("CON", 10);
            character.INT = state.getInt("INT", 10);
            character.WIS = state.getInt("WIS", 10);
            character.CHA = state.getInt("CHA", 10);
            character.ARMOR = state.getInt("ARMOR", 10);
            character.HP = state.getInt("HP", 0);
            character.HPMAX = state.getInt("HPMAX", 0);
            character.EXP = state.getInt("xp", 0);
            character.spellstat = state.getString("SPELLSTAT", "INT");
            character.spellmana = state.getInt("spellmana", 0);
            character.spellmanamax = state.getInt("spellmanamax", 0);
            character.stSTR = state.getBoolean("comptsfor", false);
            character.stDEX = state.getBoolean("comptsdex", false);
            character.stCON = state.getBoolean("comptscos", false);
            character.stINT = state.getBoolean("comptsint", false);
            character.stWIS = state.getBoolean("comptssag", false);
            character.stCHA = state.getBoolean("comptscar", false);
            character.compathletics = state.getBoolean("compathletics", false);
            character.expatletics = state.getBoolean("expatletics", false);
            character.compacrobatics = state.getBoolean("compacrobatics", false);
            character.expacrobatics = state.getBoolean("expacrobatics", false);
            character.compstealth = state.getBoolean("compstealth", false);
            character.expstealth = state.getBoolean("expstealth", false);
            character.comphandpractice = state.getBoolean("comphand", false);
            character.exphandpractice = state.getBoolean("exphand", false);
            character.compinvestigation = state.getBoolean("compinvestigation", false);
            character.expinvestigation = state.getBoolean("expinvestigation", false);
            character.comparcana = state.getBoolean("comparcana", false);
            character.exparcana = state.getBoolean("exparcana", false);
            character.comphistory = state.getBoolean("comphistory", false);
            character.exphistory = state.getBoolean("exphistory", false);
            character.compreligion = state.getBoolean("compreligionfolklore", false);
            character.expreligion = state.getBoolean("expreligionfolklore", false);
            character.compnature = state.getBoolean("compnature", false);
            character.expnature = state.getBoolean("expnature", false);
            character.compsurvival = state.getBoolean("compsurvival", false);
            character.expsurvival = state.getBoolean("expsurvival", false);
            character.compmedicine = state.getBoolean("compmedicine", false);
            character.expmedicine = state.getBoolean("expmedicine", false);
            character.compperception = state.getBoolean("compperception", false);
            character.expperception = state.getBoolean("expperception", false);
            character.compinsight = state.getBoolean("compinsight", false);
            character.expinsight = state.getBoolean("expinsight", false);
            character.companimal = state.getBoolean("companimal", false);
            character.expanimal = state.getBoolean("expanimal", false);
            character.compintimidation = state.getBoolean("compintimidation", false);
            character.expintimidation = state.getBoolean("expintimidation", false);
            character.compdeception = state.getBoolean("compdeception", false);
            character.expdeception = state.getBoolean("expdeception", false);
            character.compperfomanse = state.getBoolean("compperfomance", false);
            character.expperfomanse = state.getBoolean("expperfomance", false);
            character.comppersuasion = state.getBoolean("comppersuasion", false);
            character.exppersuasion = state.getBoolean("exppersuasion", false);
            character.languaestxt = state.getString("linguetxt", "");
            character.armitxt = state.getString("armitxt", "");
            character.talentstxt = state.getString("talentitxt", "");
            character.abilitytxt = state.getString("abilitatxt", "");
            character.mplat = state.getInt("mp", 0);
            character.mgold = state.getInt("mo", 0);
            character.msilver = state.getInt("ma", 0);
            character.mcopper = state.getInt("mr", 0);
            character.inventorytxt = state.getString("inv", "");
            character.backgroundtxt = state.getString("background", "");
            character.cantrips = state.getString("cantripss", "");
            character.lv1 = state.getString("firstlv", "");
            character.currslot1 = state.getInt("currfirstlvslots", 0);
            character.slot1 = state.getInt("firstlvslots", 0);
            character.lv2 = state.getString("secondlv", "");
            character.currslot2 = state.getInt("currsecondlvslots", 0);
            character.slot2 = state.getInt("secondlvslots", 0);
            character.lv3 = state.getString("thirdlv", "");
            character.currslot3 = state.getInt("currthirdlvslots", 0);
            character.slot3 = state.getInt("thirdlvslots", 0);
            character.lv4 = state.getString("fourthlv", "");
            character.currslot4 = state.getInt("currfourthlvslots", 0);
            character.slot4 = state.getInt("fourthlvslots", 0);
            character.lv5 = state.getString("fifthlv", "");
            character.currslot5 = state.getInt("currfifthlvslots", 0);
            character.slot5 = state.getInt("fifthlvslots", 0);
            character.lv6 = state.getString("sixthlv", "");
            character.currslot6 = state.getInt("currsixthlvslots", 0);
            character.slot6 = state.getInt("sixthlvslots", 0);
            character.lv7 = state.getString("seventhlv", "");
            character.currslot7 = state.getInt("currseventhlvslots", 0);
            character.slot7 = state.getInt("seventhlvslots", 0);
            character.lv8 = state.getString("eighthlv", "");
            character.currslot8 = state.getInt("curreighthlvslots", 0);
            character.slot8 = state.getInt("eighthlvslots", 0);
            character.lv9 = state.getString("ninthlv", "");
            character.currslot9 = state.getInt("currninthlvslots", 0);
            character.slot9 = state.getInt("ninthlvslots", 0);
            character.lvplus = state.getString("pluslv", "");
            character.currslotplus = state.getInt("currpluslvslots", 0);
            character.slotplus = state.getInt("pluslvslots", 0);
            character.portrait = state.getString("portrait", null);

            Set<String> set = new HashSet<>(state.getStringSet("meleeatks", new HashSet<String>()));
            for (String str : set) {
                String[] melee = str.split("%");
                character.meleelist.add(new MeleeWeapon(melee[0], melee[1]));
            }
            set = new HashSet<>(state.getStringSet("rangedatks", new HashSet<String>()));
            for (String str : set) {
                String[] ranged = str.split("%");
                character.rangelist.add(new RangedWeapon(ranged[0], ranged[1], ranged[2]));
            }
            set = state.getStringSet("inventory", null);
            for (String str : set) {
                String[] item = str.split("::");
                character.inventorylist.add(new InventoryItem(item[0], item[1]));
            }
            saveSchedaPG();
            state.edit().clear().apply();
        }
    }

    public void saveSchedaPG() {
        if (!character.name.equals("")) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("characters", Context.MODE_PRIVATE);
            File pgfile = new File(directory, character.name);
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(new FileOutputStream(pgfile));
                os.writeObject(character);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    assert os != null;
                    os.close();
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadCharecter(boolean loadlastchar) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("characters", Context.MODE_PRIVATE);
        final File[] files = directory.listFiles();
        assert files != null;
        if (files.length > 0) {
            if (loadlastchar && state.getString("lastchar", null) != null) {
                int which = -1;
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().equals(state.getString("lastchar", null))) {
                        which = i;
                        break;
                    }
                }
                ObjectInputStream os = null;
                try {
                    os = new ObjectInputStream(new FileInputStream(files[which]));
                    character = (Character) os.readObject();
                    os.close();
                    PrepareCharPage();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (os != null) os.close();
                    } catch (IOException e) {
                        Snackbar.make(findViewById(R.id.mainscroll), R.string.fileopenerror, Snackbar.LENGTH_LONG).show();
                    }
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(CharacterActivity.this);
                builder.setTitle(getString(R.string.selectpg));
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CharacterActivity.this, R.layout.pageselectchoice){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        // Get the current item from ListView
                        View view = super.getView(position, convertView, parent);
                        if (parent.getChildCount()==files.length ||parent.getChildCount()==files.length+1) {
                            // Set a background color for ListView regular row/item
                            view.setBackgroundResource(R.drawable.shape);
                            view.setForeground(getDrawable(R.drawable.ripple_custom));
                            GradientDrawable drawable = (GradientDrawable) view.getBackground();
                            drawable.setColor(getResources().getColor(R.color.colorAccent));
                        }

                        return view;
                    }
                };
                for (int i = 0; i < files.length; i++) {
                    arrayAdapter.getViewTypeCount();
                    arrayAdapter.add((i + 1) + ". " + files[i].getName());
                }
                arrayAdapter.add(getString(R.string.newpg));
                arrayAdapter.add(getString(R.string.delpg));
                builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        Log.d("Shit",String.valueOf(position));
                        if (position == files.length) {
                            character = null;
                            PrepareCharPage();
                        } else if (position == files.length + 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CharacterActivity.this);
                            builder.setTitle(getString(R.string.selectpg));
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CharacterActivity.this, R.layout.pageselectchoice);
                            for (int i = 0; i < files.length; i++) {
                                arrayAdapter.add((i + 1) + ". " + files[i].getName());
                            }
                            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, final int which) {
                                    new AlertDialog.Builder(CharacterActivity.this)
                                            .setTitle(R.string.deleteconfirm)
                                            .setMessage(getString(R.string.delconfirmpg, files[which].getName()))
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    if (files[which].delete())
                                                        Snackbar.make(findViewById(R.id.mainscroll), R.string.pgdeleteok, Snackbar.LENGTH_LONG).show();
                                                    loadCharecter(false);
                                                }
                                            })
                                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    loadCharecter(false);
                                                }
                                            })
                                            .setCancelable(false).show();
                                }
                            });
                            builder.show();
                        } else {
                            ObjectInputStream os = null;
                            try {
                                os = new ObjectInputStream(new FileInputStream(files[position]));
                                Log.d("Loaded char", files[position].toString());
                                character = (Character) os.readObject();
                                os.close();
                                PrepareCharPage();
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (os != null) os.close();
                                } catch (IOException e) {
                                    Snackbar.make(findViewById(R.id.mainscroll), R.string.fileopenerror, Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } else {
            character = null;
            PrepareCharPage();
        }
    }

    public static int mod(int score) {//округляет дробь в меншую сторону
        return (int) floor((((double) score - 10) / 2));
    }

    public static int prof(int lv) {//округляет уровень в большую сторону
        return (int) ceil(1 + ((double) lv / 4));
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        if (id == R.id.inspirationbtn) {
            character.inspiration = b;
            //FOR
        } else if (id == R.id.compathletics) {
            compSwitch(b, compathletics, expatletics, athletics, character.STR);
            character.compathletics = b;
        } else if (id == R.id.expatletics) {
            expSwitch(compathletics, expatletics, athletics, character.STR);
            character.expatletics = expatletics.isChecked();
            //DEX
        } else if (id == R.id.compacrobatics) {
            compSwitch(b, compacrobatics, expacrobatics, acrobatics, character.DEX);
            character.compacrobatics = b;
        } else if (id == R.id.expacrobatics) {
            expSwitch(compacrobatics, expacrobatics, acrobatics, character.DEX);
            character.expacrobatics = expacrobatics.isChecked();
        } else if (id == R.id.compstealth) {
            compSwitch(b, compstealth, expstealth, stealth, character.DEX);
            character.compstealth = b;
        } else if (id == R.id.expstealth) {
            expSwitch(compstealth, expstealth, stealth, character.DEX);
            character.expstealth = expstealth.isChecked();
        } else if (id == R.id.comphand) {
            compSwitch(b, comphand, exphand, hand, character.DEX);
            character.comphandpractice = b;
        } else if (id == R.id.exphand) {
            expSwitch(comphand, exphand, hand, character.DEX);
            character.exphandpractice = exphand.isChecked();
            //INT
        } else if (id == R.id.compinvestigation) {
            compSwitch(b, compinvestigation, expinvestigation, investigation, character.INT);
            character.compinvestigation = b;
        } else if (id == R.id.expinvestigation) {
            expSwitch(compinvestigation, expinvestigation, investigation, character.INT);
            character.expinvestigation = expinvestigation.isChecked();
        } else if (id == R.id.comparcana) {
            compSwitch(b, comparcana, exparcana, arcana, character.INT);
            character.comparcana = b;
        } else if (id == R.id.exparcana) {
            expSwitch(comparcana, exparcana, arcana, character.INT);
            character.exparcana = exparcana.isChecked();
        } else if (id == R.id.comphistory) {
            compSwitch(b, comphistory, exphistory, history, character.INT);
            character.comphistory = b;
        } else if (id == R.id.exphistory) {
            expSwitch(comphistory, exphistory, history, character.INT);
            character.exphistory = exphistory.isChecked();
        } else if (id == R.id.compreligionfolklore) {
            compSwitch(b, compreligionfolklore, expreligionfolklore, religionfolklore, character.INT);
            character.compreligion = b;
        } else if (id == R.id.expreligionfolklore) {
            expSwitch(compreligionfolklore, expreligionfolklore, religionfolklore, character.INT);
            character.expreligion = expreligionfolklore.isChecked();
        } else if (id == R.id.compnature) {
            compSwitch(b, compnature, expnature, nature, character.INT);
            character.compnature = b;
        } else if (id == R.id.expnature) {
            expSwitch(compnature, expnature, nature, character.INT);
            character.expnature = expnature.isChecked();
            //SAG
        } else if (id == R.id.compsurvival) {
            compSwitch(b, compsurvival, expsurvival, survival, character.WIS);
            character.compsurvival = b;
        } else if (id == R.id.expsurvival) {
            expSwitch(compsurvival, expsurvival, survival, character.WIS);
            character.expsurvival = expsurvival.isChecked();
        } else if (id == R.id.compmedicine) {
            compSwitch(b, compmedicine, expmedicine, medicine, character.WIS);
            character.compmedicine = b;
        } else if (id == R.id.expmedicine) {
            expSwitch(compmedicine, expmedicine, medicine, character.WIS);
            character.expmedicine = expmedicine.isChecked();
        } else if (id == R.id.compperception) {
            compSwitch(b, compperception, expperception, perception, character.WIS);
            character.compperception = b;
        } else if (id == R.id.expperception) {
            expSwitch(compperception, expperception, perception, character.WIS);
            character.expperception = expperception.isChecked();
        } else if (id == R.id.compinsight) {
            compSwitch(b, compinsight, expinsight, insight, character.WIS);
            character.compinsight = b;
        } else if (id == R.id.expinsight) {
            expSwitch(compinsight, expinsight, insight, character.WIS);
            character.expinsight = expinsight.isChecked();
        } else if (id == R.id.companimal) {
            compSwitch(b, companimal, expanimal, animal, character.WIS);
            character.companimal = b;
        } else if (id == R.id.expanimal) {
            expSwitch(companimal, expanimal, animal, character.WIS);
            character.expanimal = expanimal.isChecked();
            //CAR
        } else if (id == R.id.compintimidation) {
            compSwitch(b, compintimidation, expintimidation, intimidation, character.CHA);
            character.compintimidation = b;
        } else if (id == R.id.expintimidation) {
            expSwitch(compintimidation, expintimidation, intimidation, character.CHA);
            character.expintimidation = expintimidation.isChecked();
        } else if (id == R.id.compdeception) {
            compSwitch(b, compdeception, expdeception, deception, character.CHA);
            character.compdeception = b;
        } else if (id == R.id.expdeception) {
            expSwitch(compdeception, expdeception, deception, character.CHA);
            character.expdeception = expdeception.isChecked();
        } else if (id == R.id.compperfomance) {
            compSwitch(b, compperfomance, expperfomance, perfomance, character.CHA);
            character.compperfomanse = b;
        } else if (id == R.id.expperfomance) {
            expSwitch(compperfomance, expperfomance, perfomance, character.CHA);
            character.expperfomanse = expperfomance.isChecked();
        } else if (id == R.id.compppersuation) {
            compSwitch(b, comppersuasion, exppersuasion, persuasion, character.CHA);
            character.comppersuasion = b;
        } else if (id == R.id.exppersuation) {
            expSwitch(comppersuasion, exppersuasion, persuasion, character.CHA);
            character.exppersuasion = exppersuasion.isChecked();
        }
        PrepareCharPage();
        saveSchedaPG();
    }

    public void compSwitch(boolean b, CheckBox comp, CheckBox exp, TextView label, int caratt) {
        if (b) exp.setVisibility(View.VISIBLE);
        else exp.setVisibility(View.INVISIBLE);
        int bonus = mod(caratt) + ((comp.isChecked()) ? ((exp.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        String suffix = (bonus >= 0) ? "+" : "";
        String tempstr = suffix + bonus;
        label.setText(tempstr);
    }

    public void expSwitch(CheckBox comp, CheckBox exp, TextView label, int caratt) {
        int bonus = mod(caratt) + ((comp.isChecked()) ? ((exp.isChecked()) ? prof(character.LV) * 2 : prof(character.LV)) : 0);
        String suffix = (bonus >= 0) ? "+" : "";
        String tempstr = suffix + bonus;
        label.setText(tempstr);
    }
    //методы работы с меткой
    private void write(String text, Tag tag) throws IOException, FormatException {
        try {
            NdefRecord[] records = {createRecord(text)};
            NdefMessage message = new NdefMessage(records);

            Ndef ndef = Ndef.get(tag);
            ndef.connect();
            ndef.writeNdefMessage(message);
            ndef.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes(StandardCharsets.US_ASCII);
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];

        payload[0] = (byte) langLength;

        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        Log.d("testrecord", recordNFC.getPayload().toString());
        return recordNFC;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdapter != null) WriteModeOn();
    }

    private void WriteModeOff() {
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void WriteModeOn() {
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writingTagFilters, null);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Uri imageUri;
        ImageView imageView = findViewById(R.id.pgportrait);

        if (resultCode == RESULT_OK && reqCode == 100){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            character.portrait=imageUri.toString();
        }
        saveSchedaPG();
    }
}