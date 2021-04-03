package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private static ServerSocket server;

    public static void main(String[] args) {
        String val = "";
        try {
            try {
                server = new ServerSocket(4005);
                System.out.println("Сервер запущен!");
                Socket clientSocket = server.accept();
                InputStream in = clientSocket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String countPackages = br.readLine();
                System.out.println("countPackages = " + countPackages);
                String buffSize = br.readLine();
                System.out.println("buffSize = " + buffSize);
                try {
                    int check = 0;
                    while (check != Integer.parseInt(countPackages)) {
                        check++;
                        long l = System.currentTimeMillis();
                        in.readNBytes(Integer.parseInt(buffSize));
                        long l1 = System.currentTimeMillis();
                        long diff = l1 - l;
                        if (diff > 600) {
                            long l2 = diff / 500 - 1;
                            for (int j = 0; j < l2; j++) {
                                val += "0";
                            }
                        }
                        val += "1";
                    }
                } finally {
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