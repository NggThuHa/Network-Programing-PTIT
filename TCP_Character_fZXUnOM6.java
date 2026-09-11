import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCP_Character_fZXUnOM6 {
    private static final int PORT = 2208;
    private static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String host = "36.50.135.242";
        String studentCode;
        String questionCode;

        /*
         * Cách chạy:
         *   java Client <studentCode> <questionCode>
         * hoặc nếu server không chạy trên máy cục bộ:
         *   java Client <host> <studentCode> <questionCode>
         */
        if (args.length >= 3) {
            host = args[0];
            studentCode = args[1];
            questionCode = args[2];
        } else if (args.length == 2) {
            studentCode = args[0];
            questionCode = args[1];
        } else {
            try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
                studentCode = readValue(scanner, "Mã sinh viên: ", null);
                questionCode = readValue(scanner, "Mã câu hỏi: ", null);
            }
        }

        String request = studentCode + ";" + questionCode;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, PORT), TIMEOUT_MS);
            socket.setSoTimeout(TIMEOUT_MS);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 BufferedWriter writer = new BufferedWriter(
                         new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                writer.write(request);
                writer.newLine();
                writer.flush();

                String domainList = reader.readLine();
                if (domainList == null) {
                    throw new IOException("Server đã đóng kết nối trước khi gửi danh sách tên miền.");
                }

                String[] domains = domainList.split(",\\s*");
                StringBuilder resultBuilder = new StringBuilder();
                for (String domain : domains) {
                    domain = domain.trim();
                    if (domain.toLowerCase().endsWith(".edu")) {
                        if (resultBuilder.length() > 0) {
                            resultBuilder.append(", ");
                        }
                        resultBuilder.append(domain);
                    }
                }
                String result = resultBuilder.toString();
                writer.write(result);
                writer.newLine();
                writer.flush();

                System.out.println("Danh sách tên miền nhận được: " + domainList);
                System.out.println("Đã gửi các tên miền .edu: " + result);
            }
        } catch (java.net.SocketTimeoutException e) {
            System.err.println("Hết thời gian chờ giao tiếp với server (5 giây).");
        } catch (IOException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
        }
    }

    private static String readValue(Scanner scanner, String prompt, String defaultValue) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        return value.isEmpty() && defaultValue != null ? defaultValue : value;
    }
}