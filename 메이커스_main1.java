main activitiy1
package com.example.testformaker;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class UDPEchoClient {

    private String str = "send";
    private String msg = "receive";
    private int tempcheck = 0;
    private byte buffer[];
    private BufferedReader file;
    //연구실 165.229.125.104
    //집 192.168.149.140
    public static String serverIP = "";
    private static int SERVERPORT = 2000;
    public DatagramPacket dp;
    public DatagramSocket ds;
    public InetAddress ia;
    public String address;


    public UDPEchoClient(String ip, int port) {
        /*try {
            ia = InetAddress.getByName(ip);
            ds = new DatagramSocket(port); //DatagramSocket
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }*/
    }
    public UDPEchoClient(int port) {
        try {
            ia = InetAddress.getByName(serverIP);

            //ip주소를 자동으로 같은 wifi 연결하는게 없을까?
            /*ia = InetAddress.getLocalHost();
            String address = ia.getHostAddress();
            ia = InetAddress.getByName(address);
            */

            ds = new DatagramSocket(port); //DatagramSocket
        } catch (UnknownHostException UE) {
            //
        } catch (SocketException SE) {
            //
        }
    }
    public void connect(String ip, int port, View view) {
        try {
            ia = InetAddress.getByName(ip);
            ds = new DatagramSocket(port); //DatagramSocket
        } catch (UnknownHostException UHE) {
            Context c = view.getContext();
            Toast.makeText(c, "잘못된 도메인이나 ip입니다.", Toast.LENGTH_LONG).show();
        } catch (SocketException SE) {
            //
        }
    }
    public void connect(int port, View view) {
        try {
            ia = InetAddress.getByName(serverIP);
            ds = new DatagramSocket(port); //DatagramSocket
        } catch (UnknownHostException UHE) {
            Context c = view.getContext();
            Toast.makeText(c, "잘못된 도메인이나 ip입니다.", Toast.LENGTH_LONG).show();
        } catch (SocketException SE) {
            //
        }
    }
    public void data_send(View v) {
        //데이터 전송후 토스트 출력 메소드
        try {
            byte buffer[] = str.getBytes();
            dp = new DatagramPacket( //DatagramPacket
                    buffer, buffer.length, ia, SERVERPORT);
            //this.showStrbyToast("보내기전", v);
            ds.send(dp);
            //this.showStrbyToast("보냈습니다 : ", v);
        }
        catch (Exception ioe) {
            Context c = v.getContext();
            Toast.makeText(c, "전송실패", Toast.LENGTH_LONG).show();
        }
    }
    public void data_send() {
        //데이터 전송만 하는 메소드
        try {
            byte buffer[] = str.getBytes();
            //Context c = v.getContext();
            //Toast.makeText(c, str, Toast.LENGTH_LONG).show();
            dp = new DatagramPacket( //DatagramPacket
                    buffer, buffer.length, ia, SERVERPORT);
            ds.send(dp);
        }
        catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    //private String str accessor & mutator
    public void setStr(String str) {
        this.str = str;
    }
    public String getStr() {
        return this.str;
    }

    public String getMsg() {
        return this.msg;
    }

    public void showStrbyToast(View v) {
        //str을 토스트로 출력
        Context c = v.getContext();
        Toast.makeText(c, this.str, Toast.LENGTH_LONG).show();
    }

    public void showStrbyToast(String msg, View v) {
        //매개변수 메시지와 str를 합쳐 토스트 출력
        Context c = v.getContext();
        Toast.makeText(c, msg + this.str, Toast.LENGTH_LONG).show();
    }
    public void receive(View v) {
        //토스트 출력 receive 메소드
        try {
            buffer = new byte[256];
            dp = new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);
            this.msg = new String(dp.getData()).trim();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        Context c = v.getContext();
        Toast.makeText(c, "server ip" + dp.getAddress() + "\nserver port : " + dp.getPort()
                + "\n수신된 데이터 : " + this.msg, Toast.LENGTH_LONG).show();

    }
    public void receive() {
        //receive 메소드
        try {
            buffer = new byte[256];
            dp = new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);
            this.msg = new String(dp.getData()).trim();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
}




public class MainActivity extends AppCompatActivity {

    //연구실 165.229.125.104
    //집 192.168.149.140
    public static UDPEchoClient Client = new UDPEchoClient(2000);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Client.receive();

        Button SB = findViewById(R.id.startbutton);
        final EditText IP1 = findViewById(R.id.IP_Net_addr);
        final EditText IP2 = findViewById(R.id.IP_Host_addr_top);
        final EditText IP3 = findViewById(R.id.IP_Host_addr_mid);
        final EditText IP4 = findViewById(R.id.IP_Host_addr_bottom);


        final EditText ET_bednumber = findViewById(R.id.editText_bednumber);

        SB.setOnClickListener(new View.OnClickListener() {
            @Override //시작버튼을 눌렀을때 동작
            public void onClick(View view) {
                String Str_bednumber = ET_bednumber.getText().toString();
                String Str_IP1 = IP1.getText().toString();
                String Str_IP2 = IP2.getText().toString();
                String Str_IP3 = IP3.getText().toString();
                String Str_IP4 = IP4.getText().toString();

                String Str_IP = Str_IP1 + "." + Str_IP2 + "." + Str_IP3 + "." + Str_IP4;

                if((Str_bednumber.isEmpty()) || (Str_IP1.isEmpty()) || (Str_IP2.isEmpty())
                        || (Str_IP3.isEmpty()) || (Str_IP4.isEmpty()))
                {
                    Toast.makeText(getApplicationContext(), "다시 입력하시오.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), Str_IP, Toast.LENGTH_LONG).show();
                try {
                    // Client.connect("192.168.149.140", 2000, view);




                    Client.connect(Str_IP, 3000, view);
                    //Client.connect("172.0.0.1", 3000);
                    //Client.connect(2000,view);
                    Client.setStr(Str_bednumber);
                    Client.data_send(view);

                    //침대번호를 보낸 후 침대번호가 다시 돌아온다면 다음 화면 넘어감
                    Client.receive(view);
                }catch(Exception e) {
                    //
                }


                if(Client.getStr().equals(Client.getMsg())) {
                    //다음 레이아웃 넘기기
                    Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                    Toast.makeText(getApplicationContext(), "원하시는 모드를 선택하세요.", Toast.LENGTH_LONG).show();
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "잘못된 침대번호입니다.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
