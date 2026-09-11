import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCP_NIO_tb3W0jLf {
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "tb3W0jLf";
    static final String HOST = "36.50.135.242";
    static final int PORT = 2211;
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE;

        try (SocketChannel channel = SocketChannel.open()) {
            channel.socket().connect(new InetSocketAddress(HOST, PORT), TIMEOUT_MS);

            System.out.println("Send: " + request);
            writeFrame(channel, request);

            StringBuilder httpBuilder = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                httpBuilder.append(readFrame(channel));
            }

            String fullHttpRequest = httpBuilder.toString();
            System.out.println("Receive:\n" + fullHttpRequest);

            String method = "";
            String path = "";
            String host = "";

            String[] lines = fullHttpRequest.split("\r\n");
            if (lines.length > 0) {
                String[] firstLineParts = lines[0].split(" ");
                if (firstLineParts.length >= 2) {
                    method = firstLineParts[0];
                    path = firstLineParts[1];
                }
                for (String line : lines) {
                    if (line.toLowerCase().startsWith("host:")) {
                        host = line.substring(5).trim();
                        break;
                    }
                }
            }

            String finalResult = method + ";" + path + ";" + host;
            System.out.println("Send: " + finalResult);
            writeFrame(channel, finalResult);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFrame(SocketChannel channel, String payload) throws IOException {
        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(4 + payloadBytes.length);
        buffer.putInt(payloadBytes.length);
        buffer.put(payloadBytes);
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    private static String readFrame(SocketChannel channel) throws IOException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        readFully(channel, lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        ByteBuffer payloadBuffer = ByteBuffer.allocate(length);
        readFully(channel, payloadBuffer);
        payloadBuffer.flip();

        return new String(payloadBuffer.array(), StandardCharsets.UTF_8);
    }

    private static void readFully(SocketChannel channel, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            if (channel.read(buffer) == -1) {
                throw new IOException("Connection closed");
            }
        }
    }
}