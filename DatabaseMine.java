import com.sun.org.apache.bcel.internal.generic.FLOAD;

import java.util.Arrays;

public class DatabaseMine implements DatabaseInterface {


    private int size; // size of linear probing table
    private int NumOfValues; // number of key-value pairs in the symbol table


    private String[] keys;      // the keys
    private String[] vals;    // the values
    private int LoadFactor = 2; //Resize the list when its half full
    private int NumOfCollsions = 0; //Number of collisions when trying to index Value
    private int TotalNumOfProbs = 0;    //Number of jumps of indexs when Collsions occured


    public DatabaseMine(int size) {
        this.size = size;
        keys = new String[this.size];
        vals = new String[this.size];
    }

    public DatabaseMine() {
        this(917333);
    }


    int hash(String key) {
        int address = key.hashCode() % size;
        return (address >= 0) ? address : (address + size);
    }

    @Override
    public String save(String plainPassword, String encryptedPassword) {
        if (NumOfValues >= size / LoadFactor) resize();                        //Checks if List size is to small if so double
        int address = hash(encryptedPassword);                              //Calculate the hash index
        //Find the next avaible index that is free

        if (keys[address] != null) NumOfCollsions++;                         //Check if address is null = Collision

        for (address = hash(encryptedPassword); keys[address] != null; address = (address + 1) % size) {    //Keep looping util A index is empty or the original key is found
            TotalNumOfProbs++;                                              //Check how many jumps till Available Index = every jump one probe
            if (keys[address].equals(encryptedPassword)) {                  //If the key is the same as key Given input the value if not keep jumping
                String PreviousValue = vals[address];
                vals[address] = plainPassword;
                return PreviousValue;
            }
        }
        keys[address] = encryptedPassword;
        vals[address] = plainPassword;
        NumOfValues++;
        return null;
    }

    // resizes the hash table to the given capacity by re-hashing all of the keys
    private void resize() {
        this.size *= LoadFactor;
        this.keys = Arrays.copyOf(keys, size);
        this.keys = Arrays.copyOf(vals, size);
    }

    private String get(String key) {

        for (int address = hash(key); keys[address] != null; address = (address + 1) % size) {
            if (keys[address].equals(key))
                return vals[address];
        }
        return "";
    }

    private void put(String key, String val) {
        if (NumOfValues >= size / LoadFactor)
            resize();                         //Checks if List size is to small if so double
        int address = hash(key);                                                //Calculate the hash index

        if (keys[address] != null) NumOfCollsions++;                             //Check if address is null = Collision

        for (address = hash(key); keys[address] != null; address = (address + 1) % size) {      //Keep looping util A index is empty or the original key is found
            TotalNumOfProbs++;                                                   //Check how many jumps till Available Index = every jump one probe
            if (keys[address].equals(key)) {
                vals[address] = val;
                return;
            }
        }
        keys[address] = key;
        vals[address] = val;
        NumOfValues++;

    }

    @Override
    public String decrypt(String encryptedPassword) {
        return get(encryptedPassword);
    }

    @Override
    public int size() {
        return NumOfValues;
    }

    @Override
    public void printStatistics() {
        System.out.println("*** DatabaseMine Statistics ***");
        System.out.println("Size is " + NumOfValues + " passwords");
        System.out.println("Number of indexes is  " + this.size);
        System.out.println("Load factor is " + this.LoadFactor);
        System.out.println("Average Number of Probes is " + ((float)TotalNumOfProbs/(float)NumOfCollsions ));
        System.out.println("Number Of Displacements (due to collisions) is " + NumOfCollsions);
        System.out.println("*** End DatabaseMine Statistics ***");
    }
}
