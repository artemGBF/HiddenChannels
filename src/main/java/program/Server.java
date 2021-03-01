package program;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static InputStream in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        String val = "";
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу
                clientSocket = server.accept(); // accept() будет ждать пока
                InputStream in = clientSocket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String countPackages = br.readLine();
                String buffSize = br.readLine();
                try {
                    int check = 0;
                    while (check != Integer.parseInt(countPackages)) {
                        check++;
                        long l = System.currentTimeMillis();
                        byte[] bytesN = in.readNBytes(Integer.parseInt(buffSize));
                        long l1 = System.currentTimeMillis();
                        long diff = l1 - l;
                        if (diff > 600) {
                            long l2 = diff / 500 - 1;
                            //if (l2 == 0) l2 = 1;
                            for (int j = 0; j < l2; j++) {
                                val += "0";
                            }
                            val += "1";
                        } else {
                            val += "1";
                        }
                    }
                } finally { // в любом случае сокет будет закрыт
                    clientSocket.close();
                    in.close();
                }
            } finally {
                System.out.println(val);
                String secretWord = "";
                int i = 0;
                while (i + 8 < val.length()) {
                    String substring = val.substring(i, i + 8);
                    int i1 = Integer.parseInt(substring, 2);
                    secretWord += Character.toString(i1);
                    i += 8;
                }
                System.out.println(secretWord);
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}