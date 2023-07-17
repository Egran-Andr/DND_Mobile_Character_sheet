package com.example.rpg_character;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Character implements Parcelable, Serializable {//класс хранящий в себе параметры персонажа
    //base stats
    public String name, chclass;//name and class
    public int STR, DEX, CON, INT, WIS, CHA, HP, HPMAX, ARMOR, EXP, LV;//base stats
    public boolean inspiration; //1 true 0 false

    public String spellstat;//spells(Curently dont work)
    public int slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, slotplus;
    public int currslot1, currslot2, currslot3, currslot4, currslot5, currslot6, currslot7, currslot8, currslot9, currslotplus;
    public String cantrips, lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8, lv9, lvplus;
    public int spellmana, spellmanamax;

    public int mplat, mgold, msilver, mcopper;//money

    public boolean stSTR, stDEX, stCON, stINT, stWIS, stCHA;//saving throws
    //player prof
    public boolean compathletics, expatletics,
            compacrobatics, expacrobatics, compstealth, expstealth, comphandpractice,
            exphandpractice, compinvestigation, expinvestigation, comparcana, exparcana, comphistory,
            exphistory, compreligion, expreligion, compnature, expnature, compsurvival,
            expsurvival, compmedicine, expmedicine, compperception, expperception,
            compinsight, expinsight, companimal, expanimal, compintimidation, expintimidation, compdeception,
            expdeception, compperfomanse, expperfomanse, comppersuasion, exppersuasion;

    public String portrait;//picture inside gallery(dont work on other devises)

    public String languaestxt, armitxt, talentstxt, abilitytxt, inventorytxt, backgroundtxt;//descriptiom fields
    public List<MeleeWeapon> meleelist;//melee weapons
    public List<RangedWeapon> rangelist;//ranged weapons
    public List<InventoryItem> inventorylist;//character inventory

    public static final Creator<Character> CREATOR = new Creator<Character>() {

        @Override
        public Character createFromParcel(Parcel source) {
            return new Character(source);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public Character() {//empty character
        name = "";
        chclass = "";
        STR = 10;
        DEX = 10;
        CON = 10;
        INT = 10;
        WIS = 10;
        CHA = 10;
        HP = 0;
        HPMAX = 0;
        ARMOR = 10;
        EXP = 0;
        LV = 1;
        inspiration = false;
        slot1 = 0;
        slot2 = 0;
        slot3 = 0;
        slot4 = 0;
        slot5 = 0;
        slot6 = 0;
        slot7 = 0;
        slot8 = 0;
        slot9 = 0;
        slotplus = 0;
        currslot1 = 0;
        currslot2 = 0;
        currslot3 = 0;
        currslot4 = 0;
        currslot5 = 0;
        currslot6 = 0;
        currslot7 = 0;
        currslot8 = 0;
        currslot9 = 0;
        currslotplus = 0;
        cantrips = "";
        lv1 = "";
        lv2 = "";
        lv3 = "";
        lv4 = "";
        lv5 = "";
        lv6 = "";
        lv7 = "";
        lv8 = "";
        lv9 = "";
        lvplus = "";
        mplat = 0;
        mgold = 0;
        msilver = 0;
        mcopper = 0;
        stSTR = false;
        stDEX = false;
        stCON = false;
        stINT = false;
        stWIS = false;
        stCHA = false;
        portrait = null;
        languaestxt = "";
        armitxt = "";
        talentstxt = "";
        abilitytxt = "";
        inventorytxt = "";
        backgroundtxt = "";
        meleelist = new ArrayList<>();
        rangelist = new ArrayList<>();
        inventorylist = new ArrayList<>();
        compathletics = false;
        expatletics = false;
        compacrobatics = false;
        expacrobatics = false;
        compstealth = false;
        expstealth = false;
        comphandpractice = false;
        exphandpractice = false;
        compinvestigation = false;
        expinvestigation = false;
        comparcana = false;
        exparcana = false;
        comphistory = false;
        exphistory = false;
        compreligion = false;
        expreligion = false;
        compnature = false;
        expnature = false;
        compsurvival = false;
        expsurvival = false;
        compmedicine = false;
        expmedicine = false;
        compperception = false;
        expperception = false;
        compinsight = false;
        expinsight = false;
        compintimidation = false;
        expintimidation = false;
        compdeception = false;
        expdeception = false;
        compperfomanse = false;
        expperfomanse = false;
        comppersuasion = false;
        exppersuasion = false;
        spellstat = "INT";
        spellmana = 0;
        spellmanamax = 0;
    }

    public Character(Parcel source) {
        name = source.readString();
        chclass = source.readString();
        STR = source.readInt();
        DEX = source.readInt();
        CON = source.readInt();
        INT = source.readInt();
        WIS = source.readInt();
        CHA = source.readInt();
        HP = source.readInt();
        HPMAX = source.readInt();
        ARMOR = source.readInt();
        EXP = source.readInt();
        LV = source.readInt();
        inspiration = (source.readInt() == 1);
        spellstat = source.readString();
        slot1 = source.readInt();
        slot2 = source.readInt();
        slot3 = source.readInt();
        slot4 = source.readInt();
        slot5 = source.readInt();
        slot6 = source.readInt();
        slot7 = source.readInt();
        slot8 = source.readInt();
        slot9 = source.readInt();
        slotplus = source.readInt();
        currslot1 = source.readInt();
        currslot2 = source.readInt();
        currslot3 = source.readInt();
        currslot4 = source.readInt();
        currslot5 = source.readInt();
        currslot6 = source.readInt();
        currslot7 = source.readInt();
        currslot8 = source.readInt();
        currslot9 = source.readInt();
        currslotplus = source.readInt();
        cantrips = source.readString();
        lv1 = source.readString();
        lv2 = source.readString();
        lv3 = source.readString();
        lv4 = source.readString();
        lv5 = source.readString();
        lv6 = source.readString();
        lv7 = source.readString();
        lv8 = source.readString();
        lv9 = source.readString();
        lvplus = source.readString();
        spellmana = source.readInt();
        spellmanamax = source.readInt();
        mplat = source.readInt();
        mgold = source.readInt();
        msilver = source.readInt();
        mcopper = source.readInt();
        stSTR = (source.readInt() == 1);
        stDEX = (source.readInt() == 1);
        stCON = (source.readInt() == 1);
        stINT = (source.readInt() == 1);
        stWIS = (source.readInt() == 1);
        stCHA = (source.readInt() == 1);
        compathletics = (source.readInt() == 1);
        expatletics = (source.readInt() == 1);
        compacrobatics = (source.readInt() == 1);
        expacrobatics = (source.readInt() == 1);
        compstealth = (source.readInt() == 1);
        expstealth = (source.readInt() == 1);
        comphandpractice = (source.readInt() == 1);
        exphandpractice = (source.readInt() == 1);
        compinvestigation = (source.readInt() == 1);
        expinvestigation = (source.readInt() == 1);
        comparcana = (source.readInt() == 1);
        exparcana = (source.readInt() == 1);
        comphistory = (source.readInt() == 1);
        exphistory = (source.readInt() == 1);
        compreligion = (source.readInt() == 1);
        expreligion = (source.readInt() == 1);
        compnature = (source.readInt() == 1);
        expnature = (source.readInt() == 1);
        compsurvival = (source.readInt() == 1);
        expsurvival = (source.readInt() == 1);
        compmedicine = (source.readInt() == 1);
        expmedicine = (source.readInt() == 1);
        compperception = (source.readInt() == 1);
        expperception = (source.readInt() == 1);
        compinsight = (source.readInt() == 1);
        expinsight = (source.readInt() == 1);
        compintimidation = (source.readInt() == 1);
        expintimidation = (source.readInt() == 1);
        compdeception = (source.readInt() == 1);
        expdeception = (source.readInt() == 1);
        compperfomanse = (source.readInt() == 1);
        expperfomanse = (source.readInt() == 1);
        comppersuasion = (source.readInt() == 1);
        exppersuasion = (source.readInt() == 1);

        portrait = source.readString();
        languaestxt = source.readString();
        armitxt = source.readString();
        talentstxt = source.readString();
        abilitytxt = source.readString();
        inventorytxt = source.readString();
        backgroundtxt = source.readString();

        meleelist = new ArrayList<>();
        source.readTypedList(meleelist, MeleeWeapon.CREATOR);
        rangelist = new ArrayList<>();
        source.readTypedList(rangelist, RangedWeapon.CREATOR);
        inventorylist = new ArrayList<>();
        source.readTypedList(inventorylist, InventoryItem.CREATOR);
        companimal = (source.readInt() == 1);
        expanimal = (source.readInt() == 1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(chclass);
        dest.writeInt(STR);
        dest.writeInt(DEX);
        dest.writeInt(CON);
        dest.writeInt(INT);
        dest.writeInt(WIS);
        dest.writeInt(CHA);
        dest.writeInt(HP);
        dest.writeInt(HPMAX);
        dest.writeInt(ARMOR);
        dest.writeInt(EXP);
        dest.writeInt(LV);
        dest.writeInt((inspiration) ? 1 : 0);
        dest.writeString(spellstat);
        dest.writeInt(slot1);
        dest.writeInt(slot2);
        dest.writeInt(slot3);
        dest.writeInt(slot4);
        dest.writeInt(slot5);
        dest.writeInt(slot6);
        dest.writeInt(slot7);
        dest.writeInt(slot8);
        dest.writeInt(slot9);
        dest.writeInt(slotplus);
        dest.writeInt(currslot1);
        dest.writeInt(currslot2);
        dest.writeInt(currslot3);
        dest.writeInt(currslot4);
        dest.writeInt(currslot5);
        dest.writeInt(currslot6);
        dest.writeInt(currslot7);
        dest.writeInt(currslot8);
        dest.writeInt(currslot9);
        dest.writeInt(currslotplus);
        dest.writeString(cantrips);
        dest.writeString(lv1);
        dest.writeString(lv2);
        dest.writeString(lv3);
        dest.writeString(lv4);
        dest.writeString(lv5);
        dest.writeString(lv6);
        dest.writeString(lv7);
        dest.writeString(lv8);
        dest.writeString(lv9);
        dest.writeString(lvplus);
        dest.writeInt(spellmana);
        dest.writeInt(spellmanamax);
        dest.writeInt(mplat);
        dest.writeInt(mgold);
        dest.writeInt(msilver);
        dest.writeInt(mcopper);
        dest.writeInt((stSTR) ? 1 : 0);
        dest.writeInt((stDEX) ? 1 : 0);
        dest.writeInt((stCON) ? 1 : 0);
        dest.writeInt((stINT) ? 1 : 0);
        dest.writeInt((stWIS) ? 1 : 0);
        dest.writeInt((stCHA) ? 1 : 0);
        dest.writeInt((compathletics) ? 1 : 0);
        dest.writeInt((expatletics) ? 1 : 0);
        dest.writeInt((compacrobatics) ? 1 : 0);
        dest.writeInt((expacrobatics) ? 1 : 0);
        dest.writeInt((compstealth) ? 1 : 0);
        dest.writeInt((expstealth) ? 1 : 0);
        dest.writeInt((comphandpractice) ? 1 : 0);
        dest.writeInt((exphandpractice) ? 1 : 0);
        dest.writeInt((compinvestigation) ? 1 : 0);
        dest.writeInt((expinvestigation) ? 1 : 0);
        dest.writeInt((comparcana) ? 1 : 0);
        dest.writeInt((exparcana) ? 1 : 0);
        dest.writeInt((comphistory) ? 1 : 0);
        dest.writeInt((exphistory) ? 1 : 0);
        dest.writeInt((compreligion) ? 1 : 0);
        dest.writeInt((expreligion) ? 1 : 0);
        dest.writeInt((compnature) ? 1 : 0);
        dest.writeInt((expnature) ? 1 : 0);
        dest.writeInt((compsurvival) ? 1 : 0);
        dest.writeInt((expsurvival) ? 1 : 0);
        dest.writeInt((compmedicine) ? 1 : 0);
        dest.writeInt((expmedicine) ? 1 : 0);
        dest.writeInt((compperception) ? 1 : 0);
        dest.writeInt((expperception) ? 1 : 0);
        dest.writeInt((compinsight) ? 1 : 0);
        dest.writeInt((expinsight) ? 1 : 0);
        dest.writeInt((compintimidation) ? 1 : 0);
        dest.writeInt((expintimidation) ? 1 : 0);
        dest.writeInt((compdeception) ? 1 : 0);
        dest.writeInt((expdeception) ? 1 : 0);
        dest.writeInt((compperfomanse) ? 1 : 0);
        dest.writeInt((expperfomanse) ? 1 : 0);
        dest.writeInt((comppersuasion) ? 1 : 0);
        dest.writeInt((exppersuasion) ? 1 : 0);
        dest.writeString(portrait);
        dest.writeString(languaestxt);
        dest.writeString(armitxt);
        dest.writeString(talentstxt);
        dest.writeString(abilitytxt);
        dest.writeString(inventorytxt);
        dest.writeString(backgroundtxt);
        dest.writeTypedList(meleelist);
        dest.writeTypedList(rangelist);
        dest.writeTypedList(inventorylist);
        dest.writeInt((companimal) ? 1 : 0);
        dest.writeInt((expanimal) ? 1 : 0);
    }
}
