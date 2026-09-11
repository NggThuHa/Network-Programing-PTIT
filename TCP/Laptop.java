package TCP;

import java.io.Serializable;
import java.util.Arrays;

public class Laptop implements Serializable {
    private int id;
    private String name;
    private String code;
    private int quantity;
    private static final long serialVersionUID = 20150711L;

    public Laptop(){}

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public void reverseName(){
        String[] words = this.name.split(" ");
        String tmpWord = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = tmpWord;
        this.name = String.join(" ", words);
    }

    public void reverseQuantity(){
        StringBuilder sb = new StringBuilder(String.valueOf(this.quantity));
        this.quantity = Integer.parseInt(sb.reverse().toString());
    }

    @Override
    public String toString(){
        return "Laptop{" + "id=" + id + ", name=" + name + ", quantity=" + quantity + '}';
    }
}
