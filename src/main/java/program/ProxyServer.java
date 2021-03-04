package program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ProxyServer {

    public static void main(String[] args) throws Exception {
        ServerSocket proxy = new ServerSocket(4004);
        Socket server = new Socket("localhost", 4005);
        Socket sock = proxy.accept();

        InputStream in = sock.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        int countBytes = Integer.parseInt(br.readLine());
        byte[] content = br.readLine().getBytes();

        String secretBinary = convertStringToBinary("line");
        int countOnes = countOnes(secretBinary);
        int buffSize = countBytes / countOnes - 1;
        System.out.println(secretBinary);

        BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        outputStream.write(countOnes + "\n");
        outputStream.flush();
        outputStream.write(buffSize + "\n");
        outputStream.flush();

        for (int i = 0, j = 0; i < secretBinary.length()+countOnes; i++) {
            if (secretBinary.charAt(i) == '1') {
                Thread.sleep(250);
                byte[] bytesN = Arrays.copyOfRange(content, j, j + buffSize);
                String word = new String(bytesN);
                j += buffSize;
                outputStream.write(word);
                outputStream.flush();
                Thread.sleep(250);
            } else {
                Thread.sleep(520);
            }
        }

        /*Socket sck = new Socket("localhost", 4005);
        PrintStream ps = new PrintStream(sock.getOutputStream());*/

        /*boolean waiting = true;
        while (waiting) {
            if (message != null) {
                ps.println("Hey Server, Client says: " + message);
                waiting = false;
            }
        }
        sock.close();*/
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
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }
        return result.toString();
    }
}