import java.util.ArrayList;

public class Hashmap {

    // Default size of the hashmap
    private final int DEFAULT_TABLE_SIZE = 29;
    // Radix to be used for hashing key Strings
    private final int RADIX = 256;
    // Current size of the hashmap
    private int tableSize;
    // Current number of key-value pairs in the hashmap
    private int keyCount;
    // Array holding the inputted key-value pairs
    private keyVal[] keyVals;

    // Initialize hashmap variables
    public Hashmap () {
        tableSize = DEFAULT_TABLE_SIZE;
        keyCount = 0;
        keyVals = new keyVal[tableSize];
    }

    // Method to return the hash the inputted key
    private int hash(String key) {
        // Integer to hold the hashed value of the inputted String
        int hash = 0;
        // Add the ASCII value of each letter
        for (int i = 0; i < key.length(); i++) {
            hash = hash * RADIX + key.charAt(i);
        }
        // Return the hash of the String
        return hash;
    }

    // Method to add the inputted key-value pair to the hashmap
    public void add(String key, String value) {
        // Increment recorded number of keys in Hashmap by 1
        keyCount++;
        // Resize the Hashmap if it's more than 50% full
        if (keyCount > tableSize / 2) {
            resize();
        }
        // Hashed value of key
        int hashKey = hash(key);
        // Find the first open index on or after the modded hash value of the key in the Hashmap
        // Initially set the index where the key-value pair is to be added to the hash of the key % tableSize
        int index = hashKey % tableSize;
        while (keyVals[index] != null) {
            if (index < tableSize - 1) {
                index++;
            }
            else {
                // Wrap around to the first index in the Hashmap if needed
                index = 0;
            }
        }
        // Add the key-value pair to the first available index on or after the modded hash value
        keyVals[index] = new keyVal(key, value, hashKey);
    }

    // Method to return the value associated with the inputted String in the hashmap, return invalid message otherwise
    public String get(String key) {
        // Initially set the index to check to the hash of the key % tableSize
        int index = hash(key) % tableSize;
        // Continue as long as an empty index hasn't been reached
        while (keyVals[index] != null) {
            // If the key at the index in keys matches the inputted key, return the value associated with the key
            if (keyVals[index].getKey().equals(key)) {
                return keyVals[index].getVal();
            }
            // Increment the index by 1 otherwise
            if (index < tableSize - 1) {
                index++;
            }
            else {
                // Wrap around to the first index in the Hashmap if needed
                index = 0;
            }
        }
        // Return "INVALID KEY" if the key doesn't exist in the Hashmap
        return "INVALID KEY";
    }

    // Method to resize the hashmap to double its current size
    private void resize() {
        // Double tableSize
        tableSize *= 2;
        // Integer to hold the value to insert value associated with each key;
        int newSpot;
        // New array (twice as large) to hold key-value pairs
        keyVal[] newKeyVals = new keyVal[tableSize];

        // Add all keys in keys to newKeys, and add all the values
        for (int index = 0; index < keyVals.length; index++) {
            // If a key-value pair exists at the index, add it to the new array
            if (keyVals[index] != null) {
                // Find the first open index on or after the modded hash value of the key in the Hashmap
                // Initially set the index where the key-value pair is to be added to the hash of the key % tableSize
                newSpot = keyVals[index].getHashKey() % tableSize;
                while (newKeyVals[newSpot] != null) {
                    if (newSpot < tableSize - 1) {
                        newSpot++;
                    }
                    else {
                        // Wrap around to the first index in the Hashmap if needed
                        newSpot = 0;
                    }
                }
                // Add the key-value pair to the first available index on or after the modded hash value
                newKeyVals[newSpot] = keyVals[index];
            }
        }

        // Set key-value pair array to newKeyVals
        keyVals = newKeyVals;
    }


    // Key-Value pair object class
    public class keyVal {

        // String to hold key
        private String key;
        // String to hold value
        private String val;
        // Int to hold hashed value of key
        private int hashKey;

        // Initialize instance variables
        public keyVal(String theKey, String theVal, int hashedKey) {
            key = theKey;
            val = theVal;
            hashKey = hashedKey;
        }

        // Getter Methods
        // Method returning key of object
        public String getKey() {
            return key;
        }
        // Method returning value associated with key-value pair
        public String getVal() {
            return val;
        }
        // Method returning hashed value of key
        public int getHashKey() {
            return hashKey;
        }
    }

}
