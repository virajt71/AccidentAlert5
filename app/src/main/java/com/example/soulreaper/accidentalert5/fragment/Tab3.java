package com.example.soulreaper.accidentalert5.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soulreaper.accidentalert5.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Tab3 extends Fragment {

    TextView mRecipient_1_TextView;
    TextView mRecipient_2_TextView;
    TextView mRecipient_3_TextView;
    TextView mRecipient_4_TextView;
    TextView mRecipient_5_TextView;
    TextView mMsg_TextView;

    ImageButton mEmg_1;
    ImageButton mEmg_2;
    ImageButton mEmg_3;
    ImageButton mEmg_4;
    ImageButton mEmg_5;
    ImageButton mEmg_msg;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecipient_1 = mRootRef.child("Recipient Number 1");
    DatabaseReference mRecipient_2 = mRootRef.child("Recipient Number 2");
    DatabaseReference mRecipient_3 = mRootRef.child("Recipient Number 3");
    DatabaseReference mRecipient_4 = mRootRef.child("Recipient Number 4");
    DatabaseReference mRecipient_5 = mRootRef.child("Recipient Number 5");
    DatabaseReference mMsg = mRootRef.child("Message To Send");
    DatabaseReference mAccident = mRootRef.child("Accident");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        mRecipient_1_TextView = rootView.findViewById(R.id.textView6);
        mRecipient_2_TextView = rootView.findViewById(R.id.textView7);
        mRecipient_3_TextView = rootView.findViewById(R.id.textView8);
        mRecipient_4_TextView = rootView.findViewById(R.id.textView9);
        mRecipient_5_TextView = rootView.findViewById(R.id.textView10);
        mMsg_TextView = rootView.findViewById(R.id.msg_body);

        mEmg_1 = rootView.findViewById(R.id.imageButton1);
        mEmg_2 = rootView.findViewById(R.id.imageButton2);
        mEmg_3 = rootView.findViewById(R.id.imageButton3);
        mEmg_4 = rootView.findViewById(R.id.imageButton4);
        mEmg_5 = rootView.findViewById(R.id.imageButton5);
        mEmg_msg = rootView.findViewById(R.id.imageButton6);

        mRecipient_1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mRecipient_1_TextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecipient_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mRecipient_2_TextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecipient_3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mRecipient_3_TextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecipient_4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mRecipient_4_TextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecipient_5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mRecipient_5_TextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMsg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mMsg_TextView.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mAccident.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ACC = dataSnapshot.getValue(String.class);
                int text = Integer.parseInt(ACC);
                if (text == 1)
                {
                    String str_addtes1 = (String) mRecipient_1_TextView.getText();
                    String str_message = (String) mMsg_TextView.getText();
                    if (str_addtes1.length() > 0 && str_message.length() > 0) {

                        sendSMS(str_addtes1, str_message);

                    }

                    String str_addtes2 = (String) mRecipient_2_TextView.getText();
                    String str_message2 = (String) mMsg_TextView.getText();
                    if (str_addtes1.length() > 0 && str_message.length() > 0) {

                        sendSMS(str_addtes2, str_message2);

                    }

                    String str_addtes3 = (String) mRecipient_3_TextView.getText();
                    String str_message3 = (String) mMsg_TextView.getText();
                    if (str_addtes1.length() > 0 && str_message.length() > 0) {

                        sendSMS(str_addtes3, str_message3);

                    }

                    String str_addtes4 = (String) mRecipient_4_TextView.getText();
                    String str_message4 = (String) mMsg_TextView.getText();
                    if (str_addtes1.length() > 0 && str_message.length() > 0) {

                        sendSMS(str_addtes4, str_message4);

                    }

                    String str_addtes5 = (String) mRecipient_5_TextView.getText();
                    String str_message5 = (String) mMsg_TextView.getText();
                    if (str_addtes1.length() > 0 && str_message.length() > 0) {

                        sendSMS(str_addtes5, str_message5);

                    }

                    Toast.makeText(getActivity(), "Msg send", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mEmg_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                @SuppressLint("InflateParams") final View login_layout = inflater.inflate(R.layout.dialog_no, null);

                final EditText edit_text_1 = login_layout.findViewById(R.id.emg);

                alertDialog.setView(login_layout);

                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRecipient_1.setValue(edit_text_1.getText().toString());
                        edit_text_1.setText("");
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        mEmg_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                @SuppressLint("InflateParams") final View login_layout = inflater.inflate(R.layout.dialog_no, null);

                final EditText edit_text_2 = login_layout.findViewById(R.id.emg);

                alertDialog.setView(login_layout);

                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRecipient_2.setValue(edit_text_2.getText().toString());
                        edit_text_2.setText("");
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        mEmg_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                @SuppressLint("InflateParams") final View login_layout = inflater.inflate(R.layout.dialog_no, null);

                final EditText edit_text_3 = login_layout.findViewById(R.id.emg);

                alertDialog.setView(login_layout);

                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRecipient_3.setValue(edit_text_3.getText().toString());
                        edit_text_3.setText("");
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        mEmg_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                @SuppressLint("InflateParams") final View login_layout = inflater.inflate(R.layout.dialog_no, null);

                final EditText edit_text_4 = login_layout.findViewById(R.id.emg);

                alertDialog.setView(login_layout);

                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRecipient_4.setValue(edit_text_4.getText().toString());
                        edit_text_4.setText("");
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        mEmg_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                @SuppressLint("InflateParams") final View login_layout = inflater.inflate(R.layout.dialog_no, null);

                final EditText edit_text_5 = login_layout.findViewById(R.id.emg);

                alertDialog.setView(login_layout);

                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRecipient_5.setValue(edit_text_5.getText().toString());
                        edit_text_5.setText("");
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        mEmg_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                @SuppressLint("InflateParams") final View login_layout = inflater.inflate(R.layout.dialog_msg, null);

                final EditText edit_text_msg = login_layout.findViewById(R.id.emg);

                alertDialog.setView(login_layout);

                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMsg.setValue(edit_text_msg.getText().toString());
                        edit_text_msg.setText("");
                        dialog.dismiss();
                    }
                });

                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        return rootView;
    }

    public static boolean sendSMS(String toPhoneNumber, String smsMessage) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}