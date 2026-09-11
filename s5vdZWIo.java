import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class s5vdZWIo {
    static final int PORT = 2208;
    static final int TIMEOUT_MS = 5000;
    static final String HOST = "36.50.135.242";
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "s5vdZWIo";

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE;
        try(Socket socket = new Socket()){
            socket.connect(new java.net.InetSocketAddress(HOST, PORT), TIMEOUT_MS);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))){
                System.out.println("Send: " + request);
                writer.write(request);
                writer.newLine();
                writer.flush();

                String response = reader.readLine().trim();
                System.out.println("Receive: " + response);

                LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
                for (char c : response.toCharArray()) {
                    if (c != ' '){
                        map.put(String.valueOf(c), map.getOrDefault(String.valueOf(c), 0) + 1);
                    }
                }

                String result = new String();
                for (String key : map.keySet()) {
                    if (map.get(key) > 1){
                        result += key + ":" + map.get(key) + ",";
                    }
                }
                System.out.println("Result: " + result);
                writer.write(result);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
