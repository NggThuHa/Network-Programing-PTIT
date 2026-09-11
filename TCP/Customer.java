package TCP;

import java.io.Serializable;
import java.util.Locale;

public class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;

    private int id;
    private String code;
    private String name;
    private String userName;
    private String dayOfBirth;

    public Customer(){}

    public Customer(int id, String code, String name, String userName, String dayOfBirth) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.userName = userName;
        this.dayOfBirth = dayOfBirth;
    }

    public String normalizeName(){
        String[] words = this.name.split(" ");
        StringBuilder sb = new StringBuilder(words[words.length - 1].toUpperCase());
        sb.append(",");
        for (int i = 0; i < words.length - 1; i++) {
            String word = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
            sb.append(" ").append(word);
        }
        return this.name = sb.toString();
    }

    public String normalizeDayOfBirth() {
        String[] parts = this.dayOfBirth.split("-");
        return this.dayOfBirth = parts[1] + "/" + parts[0] + "/" + parts[2];
    }

    public String normalizeUserName() {
        String[] words = this.name.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            sb.append(words[i].substring(0, 1).toLowerCase());
        }
        sb.append(words[words.length - 1].toLowerCase());
        return this.userName = sb.toString();
    }

    @Override
    public String toString(){
        return "Customer{" + "id=" + id + ", code=" + code + ", name=" + name + ", userName=" + userName + ", dayOfBirth=" + dayOfBirth + '}';
    }
}
