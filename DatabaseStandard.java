import java.util.HashMap;
import java.util.Map;

public class DatabaseStandard implements DatabaseInterface {

    private HashMap<String, String> DB = new HashMap<String, String>();

    // Stores plainPassword and corresponding encryptedPassword in a map.
    // if there was a value associated with this key, it is replaced,
    // and previous value returned; otherwise, null is returned
    // The key is the encryptedPassword the value is the plainPassword
    @Override
    public String save(String plainPassword, String encryptedPassword) {
        return DB.put(plainPassword, encryptedPassword);
    }

    @Override
    public String decrypt(String encryptedPassword) {

        for (Map.Entry<String, String> entry : DB.entrySet()) {
            if (encryptedPassword.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return "";
    }

    @Override
    public int size() {
        return DB.size();
    }

    @Override
    public void printStatistics() {
        System.out.println("*** DatabaseStandard Statistics *** ");
        System.out.println("Size is " +size()+ " passwords Initial of Indexes when Created 10000");
        System.out.println("*** End DatabaseStandard Statistics ***");
    }
}
