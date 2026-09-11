import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_Gzip_AnG5WNQm {
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "AnG5WNQm";
    static final String HOST = "36.50.135.242";
    static final int PORT = 2210;
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) throws IOException {
        String request = STUDENT_CODE + ";" + QUESTION_CODE + "\n";

        try (Socket socket = new Socket()) {
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

            char[] chars = response.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            String finalResult = sorted + "\n";
            System.out.println("Send final: " + finalResult.trim());
            out.write(finalResult.getBytes());
            out.flush();

            out.finish();
        }
    }
}