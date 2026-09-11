import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_Gzip_2KTUhJu8 {
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "2KTUhJu8";
    static final String HOST = "36.50.135.242";
    static final int PORT = 2210;
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE + "\n";
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(HOST, PORT), TIMEOUT_MS);
            GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream(), true);

            System.out.println("Send: " + request.trim());
            out.write(request.getBytes());
            out.flush();

            GZIPInputStream in = new GZIPInputStream(socket.getInputStream());

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);

            String response = new String(buffer, 0, bytesRead).trim();
            System.out.println("Receive: " + response);

            String reveresed = new StringBuilder(response).reverse().toString();
            String strBase64 = Base64.getEncoder().encodeToString(reveresed.getBytes());

            String finalResult = reveresed + "|" + strBase64 + "\n";
            System.out.println("Reverse: " + finalResult.trim());

            out.write(finalResult.getBytes());
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}