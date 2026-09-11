import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class galyZKFy {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2206;
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "galyZKFy";
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE;

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST, PORT), TIMEOUT_MS);
            socket.setSoTimeout(TIMEOUT_MS);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // a. Gửi studentCode;qCode
            System.out.println("Send: " + request);

            out.write(request.getBytes(StandardCharsets.UTF_8));
            out.flush();

            // b. Nhận danh sách số
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);

            if (bytesRead == -1) {
                throw new RuntimeException("Server đóng kết nối.");
            }

            String response = new String(
                    buffer,
                    0,
                    bytesRead,
                    StandardCharsets.UTF_8
            ).trim();

            System.out.println("Receive: " + response);

            String[] parts = response.split(",\\s*");
            int[] nums = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }

            // c. Tìm khoảng cách nhỏ nhất
            Arrays.sort(nums);

            int gap = Integer.MAX_VALUE;
            int first = 0;
            int second = 0;

            for (int i = 0; i < nums.length - 1; i++) {
                int currentGap = nums[i + 1] - nums[i];

                // <= để nếu cùng khoảng cách,
                // lấy cặp có giá trị lớn hơn
                if (currentGap <= gap) {
                    gap = currentGap;
                    first = nums[i];
                    second = nums[i + 1];
                }
            }

            String result = gap + "," + first + "," + second;

            System.out.println("Send result: " + result);

            // Gửi kết quả
            out.write(result.getBytes(StandardCharsets.UTF_8));
            out.flush();

            // d. Kết thúc, try-with-resources tự đóng socket

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}