import TCP.Customer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCP_Object_ONArENAD {
    static final String STUDENT_CODE = "B23DCCN465";
    static final String QUESTION_CODE = "ONArENAD";
    static final String HOST = "36.50.135.242";
    static final int PORT = 2209;
    static final int TIMEOUT_MS = 5000;

    public static void main(String[] args) {
        String request = STUDENT_CODE + ";" + QUESTION_CODE;
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(HOST, PORT), TIMEOUT_MS);

            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())){
                System.out.println("Send: " + request);
                out.writeObject(request);
                out.flush();

                Customer customer = (TCP.Customer) in.readObject();
                System.out.println("Receive: " + customer);

                System.out.println("Username: "+ customer.normalizeUserName());
                System.out.println("Name: " + customer.normalizeName());
                System.out.println("Day of Birth: " + customer.normalizeDayOfBirth());

                System.out.println("Send: " + customer);
                out.writeObject(customer);
                out.flush();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
