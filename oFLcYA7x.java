import java.io.IOException;
import java.net.*;

public class oFLcYA7x {
    static final int PORT = 2207;
    static final String HOST = "36.50.135.242";
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "oFLcYA7x";
    static final int TIMEOUT_MS = 5000;
    public static void main(String[] args) {
        String request = ";" + STUDENT_CODE + ";" + QUESTION_CODE;
        try (DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName(HOST), PORT);

            System.out.println("Send: " + request);
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(), request.getBytes().length);
            socket.send(requestPacket);

            byte[] byteResponse = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(byteResponse, byteResponse.length);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.println("Receive: " + response);

            String[] parts = response.split(";");
            String requestId = parts[0];
            int n = Integer.parseInt(parts[1]);
            String[] aValues = parts[2].split(",");

            boolean[] present = new boolean[n + 1];
            for (String val : aValues) {
                if (!val.trim().isEmpty()) {
                    present[Integer.parseInt(val.trim())] = true;
                }
            }
            StringBuilder missing = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                if (!present[i]) {
                    missing.append(i).append(",");
                }
            }
            if (missing.length() > 0) {
                missing.setLength(missing.length() - 1);
            }

            String result = requestId + ";" + missing.toString();
            System.out.println("Send Result: " + result);
            DatagramPacket resultPacket = new DatagramPacket(result.getBytes(), result.getBytes().length);
            socket.send(resultPacket);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
