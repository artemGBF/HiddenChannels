package program;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private static Socket clientSocket;
    private static BufferedWriter out;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.print("plz enter a filename u wanna to transfer: ");
                FileInputStream reader = new FileInputStream(scanner.nextLine());

                byte[] arr = new byte[reader.available()];
                reader.read(arr);

                out.write(arr.length + "\n");
                out.write(new String(arr));
                out.flush();
            } finally {
                System.out.println("Клиент был закрыт...");
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
