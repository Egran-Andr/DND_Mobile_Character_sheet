package com.example.rpg_character;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class InventoryAdapter extends RecyclerView.Adapter {//адаптер для инвенторя пользователя
    private final Character character;

    public InventoryAdapter(Character character) {
        this.character = character;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.objectcard, viewGroup, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        InventoryItem obj = character.inventorylist.get(i);
        if (obj != null) ((InventoryViewHolder) viewHolder).bindObj(obj);
    }

    @Override
    public int getItemCount() {
        return character.inventorylist.size();
    }

    public void removeObj(InventoryItem obj) {
        character.inventorylist.remove(obj);
        notifyDataSetChanged();
    }

    public void addObj(InventoryItem obj) {
        character.inventorylist.add(obj);
    }

    class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        TextView descTxt;
        Button removeBtn;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.objNameTxtV);
            descTxt = itemView.findViewById(R.id.objDescTxtV);
            removeBtn = itemView.findViewById(R.id.removeObjBtn);
        }

        void bindObj(final InventoryItem obj) {
            nameTxt.setText(obj.name);
            nameTxt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    final EditText input = new EditText(view.getContext());
                    input.setText(obj.name);
                    alert.setView(input);
                    alert.setNegativeButton(view.getContext().getString(R.string.cancel), null);
                    final AlertDialog alertd = alert.create();
                    alert.setTitle(view.getContext().getString(R.string.edit) + " " + obj.name);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String newTitle = input.getText().toString();
                            removeObj(obj);
                            addObj(new InventoryItem(newTitle, obj.desc));
                            dialog.cancel();
                            alertd.dismiss();
                        }
                    });
                    alert.show();
                    return true;
                }
            });
            descTxt.setText(obj.desc);
            descTxt.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    final EditText input = new EditText(view.getContext());
                    input.setText(obj.desc);
                    alert.setView(input);
                    alert.setNegativeButton(view.getContext().getString(R.string.cancel), null);
                    final AlertDialog alertd = alert.create();
                    alert.setTitle(view.getContext().getString(R.string.editdesc));
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String newDesc = input.getText().toString();
                            removeObj(obj);
                            addObj(new InventoryItem(obj.name, newDesc));
                            dialog.cancel();
                            alertd.dismiss();
                        }
                    });
                    alert.show();
                    return true;
                }
            });
            removeBtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle(view.getContext().getString(R.string.deleteconfirm))
                            .setMessage(view.getContext().getString(R.string.delconfirmpg,obj.name))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    removeObj(obj);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                    return true;
                }
            });
        }
    }
}
