import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCP_NIO_5fkQs6l8 {
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "5fkQs6l8";
    static final String HOST = "36.50.135.242";
    static final int PORT = 2211;
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE;

        try (SocketChannel channel = SocketChannel.open()) {
            channel.socket().connect(new InetSocketAddress(HOST, PORT), TIMEOUT_MS);
            channel.socket().setSoTimeout(TIMEOUT_MS);

            System.out.println("Send: " + request);
            writeFrame(channel, request);

            StringBuilder jsonBuilder = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                jsonBuilder.append(readFrame(channel));
            }

            String fullJson = jsonBuilder.toString();
            System.out.println("Receive: " + fullJson);

            String event = extractStringField(fullJson, "event");
            String user = extractStringField(fullJson, "user");
            boolean ok = extractBooleanField(fullJson, "ok");

            String okValue = ok ? "1" : "0";

            String finalResult = "event=" + event + ";user=" + user + ";ok=" + okValue;
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

    private static String extractStringField(String json, String field) {
        // Tìm kiếm Regex dạng: "tên_trường":"giá_trị" (có thể có khoảng trắng)
        Pattern pattern = Pattern.compile("\"" + field + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static boolean extractBooleanField(String json, String field) {
        Pattern pattern = Pattern.compile("\"" + field + "\"\\s*:\\s*(true|false)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return Boolean.parseBoolean(matcher.group(1));
        }
        return false;
    }
}