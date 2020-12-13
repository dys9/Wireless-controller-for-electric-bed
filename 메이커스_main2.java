main activity2
package com.example.testformaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.testformaker.MainActivity.Client;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button Bt_bed90 = findViewById(R.id.bt_bed90);
        Button Bt_bed0 = findViewById(R.id.bt_bed0);
        Button Bt_table90 = findViewById(R.id.bt_table90);
        Button Bt_table0 = findViewById(R.id.bt_table0);
        ImageButton Bt_bed1up = findViewById(R.id.Bt_bed1up);
        ImageButton Bt_bed1down = findViewById(R.id.Bt_bed1down);
        Switch Sw_keep = findViewById(R.id.switch_keep);

        Bt_bed90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UDPEchoClient UDPClient = new UDPEchoClient("192.168.149.138", 3000);
                String str = "90";
                Client.setStr(str);
                Client.data_send(view);
            }
        });
        Bt_bed0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UDPEchoClient UDPClient = new UDPEchoClient("192.168.149.138", 3000);
                String str = "0";
                Client.setStr(str);
                Client.data_send(view);
            }
        });
        Bt_table90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UDPEchoClient UDPClient = new UDPEchoClient("192.168.149.138", 3000);
                String str = "5";
                Client.setStr(str);
                Client.data_send(view);
            }
        });
        Bt_table0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UDPEchoClient UDPClient = new UDPEchoClient("192.168.149.138", 3000);
                String str = "-5";
                Client.setStr(str);
                Client.data_send(view);
            }
        });
        Bt_bed1up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UDPEchoClient UDPClient = new UDPEchoClient("192.168.149.138", 3000);
                String str = "1";
                Client.setStr(str);
                Client.data_send(view);
            }
        });
        Bt_bed1down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UDPEchoClient UDPClient = new UDPEchoClient("192.168.149.138", 3000);
                String str = "-1";
                Client.setStr(str);
                Client.data_send(view);
            }
        });
        Sw_keep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    String str = "1024";
                    Client.setStr(str);
                    Client.data_send();
                    Toast.makeText(getApplicationContext(), "침대가 사람유무를 측정합니다. ", Toast.LENGTH_LONG).show();
                } else {
                    String str = "-1024";
                    Client.setStr(str);
                    Client.data_send();
                    Toast.makeText(getApplicationContext(), "침대상태를 유지시킵니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}

