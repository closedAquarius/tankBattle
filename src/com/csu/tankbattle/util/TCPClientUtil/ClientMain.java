package com.csu.tankbattle.util.TCPClientUtil;

import com.csu.tankbattle.instance.Tank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static com.csu.tankbattle.scene.PVEScene.localTank;

public class ClientMain {
    //192.168.149.130
    public static final String SERVER_ADDRESS = "localhost";
    public static final int PORT = 8888;

    public static Tank webInfo = new Tank();
    public static ArrayList<String> webId=new ArrayList<String>();


    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(SERVER_ADDRESS,PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            //接收信息
            Thread readThread = new Thread(() -> {
                try {
                    String message;
                    while((message = in.readLine()) != null) {
                        String id = message.split(";")[0];
                        if(!webId.contains(id)) {
                            webId.add(id);
                        }
                        webInfo = Tank.toTank(message);
                        System.out.println(webInfo.toString());
                    }
                } catch (IOException e) {
                    System.err.println("...[ERROR] Client failed to get message: " + e.getMessage());
                }
            });

            //发送信息
            String input;
            while(true) {
                input = localTank.toString();
                if(input.equalsIgnoreCase("exit")) {
                    break;
                }
                out.println(input);
            }
        } catch (IOException e) {
            System.err.println("...[ERROR] Client failed to connect: " + e.getMessage());
        }
    }
}
