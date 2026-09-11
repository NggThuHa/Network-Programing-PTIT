import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_Byte_ErncJF6t {
    static final int PORT = 2206;
    static final String HOST = "36.50.135.242";
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "ErncJF6t";
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE;
        try (Socket socket = new Socket()){
            socket.connect(new java.net.InetSocketAddress(HOST, PORT), TIMEOUT_MS);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            System.out.println("Send: " + request);
            out.write(request.getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String response = new String(buffer, 0, bytesRead);
            String[] listDomain = response.split(",");
            System.out.println("Receive: " + response);

            int first_number = Integer.MIN_VALUE, second_number = Integer.MIN_VALUE, first_index = 0, second_index = 0;
            for (int i = 0; i < listDomain.length; i++) {
                int number = Integer.parseInt(listDomain[i].trim());
                if (number > first_number) {
                    second_number = first_number;
                    second_index = first_index;
                    first_number = number;
                    first_index = i;
                } else if (number > second_number) {
                    second_number = number;
                    second_index = i;
                }
            }

            String result = second_number + "," + second_index;
            System.out.println("Result: " + result);
            out.write(result.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
