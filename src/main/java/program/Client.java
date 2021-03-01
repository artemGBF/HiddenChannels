package program;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
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
                clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.print("plz enter a filename u wanna to transfer: ");
                FileInputStream reader = new FileInputStream(scanner.nextLine());
                System.out.print("\nplz enter secret message: ");
                String line = scanner.nextLine();
                String secret = convertStringToBinary(line);
                System.err.println(secret);

                byte[] arr = new byte[reader.available()];
                reader.read(arr);

                int ones = countOnes(secret);
                System.out.println("ones = " + ones);
                int buffSize = arr.length / ones;
                System.out.println("buffsize: " + buffSize);
                out.write(ones + "\n");
                out.flush();
                out.write(buffSize + "\n");
                out.flush();

                for (int i = 0,j = 0; i < secret.length() + ones; i++) {
                    if (secret.charAt(i) == '1') {
                        Thread.sleep(250);
                        String word = new String(Arrays.copyOfRange(arr, j, j + buffSize));
                        j += buffSize;
                        out.write(word);
                        out.flush();
                        Thread.sleep(250);
                    } else {
                        Thread.sleep(520);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Клиент был закрыт...");
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static int countOnes(String binary) {
        int count = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                count++;
            }
        }
        return count;
    }

    public static String convertStringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();
    }
}
