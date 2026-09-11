import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class i7DEO1bX {
    static final int PORT = 2207;
    static final String HOST = "36.50.135.242";
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "i7DEO1bX";
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

                int a = in.readInt();
                int b = in.readInt();
                System.out.println("a: " + a + ", b: " + b);
                int sum = a + b;
                System.out.println("Sum: " + sum);
                out.writeInt(sum);
                int mul = a * b;
                System.out.println("Mul: " + mul);
                out.writeInt(mul);
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
