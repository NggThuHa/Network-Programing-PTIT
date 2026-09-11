import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCP_Data_ftbwcKaW {
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "ftbwcKaW";
    static final String HOST = "36.50.135.242";
    static final int PORT = 2207;
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE;
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(HOST, PORT), TIMEOUT_MS);

            try (DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())){

                System.out.println("Send: " + request);
                out.writeUTF(request);
                out.flush();

                String response = in.readUTF();
                System.out.println("Response: " + response);
                int s = in.readInt();
                System.out.println("s: " + s);

                StringBuilder decodedStr = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    char c = response.charAt(i);
                    if (Character.isLetter(c)) {
                        char base = Character.isLowerCase(c) ? 'a' : 'A';
                        int offset = (c - base - s) % 26;
                        if (offset < 0) {
                            offset += 26;
                        }
                        decodedStr.append((char) (base + offset));
                    } else {
                        decodedStr.append(c);
                    }
                }

                System.out.println("Send: " + decodedStr.toString());
                out.writeUTF(decodedStr.toString());
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
