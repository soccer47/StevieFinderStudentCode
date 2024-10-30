import java.io.BufferedReader;
import java.io.IOException;
/**
 * Finder
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Stevie Halprin
 **/

public class Finder {

    private static final String INVALID = "INVALID KEY";

    public Finder() {}

    private TST table = new TST();

    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException {
        // TODO: Complete the buildTable() function!
        // Array of Strings to hold the values at each line of the csv file one at a time
        String[] line;
        // Set the array to hold the first line of values in the csv file
        line = br.readLine().split(",");
        // Set the root of the TST to the first character of the key on the first line
        table.setRoot(line[keyCol].charAt(0));
        // Add the first key-value pair to the TST
        table.addKeyValue(line[keyCol], line[valCol]);
        // While there are more key-value pairs to add to the TST, call the readLine() function to add to the TST
        while (br.ready()) {
            line = br.readLine().split(",");
            table.addKeyValue(line[keyCol], line[valCol]);
        }
        // Close the csv file
        br.close();
    }

    public String query(String key){
        // If the key isn't in the table, return INVALID
        if (table.checkKey(key) == null) {
            return INVALID;
        }
        // If the key is in the table, return the value of the key-value pair
        return table.checkKey(key);
    }


    private class TST {

        // Root node of TST
        private Node root;

        // Node class
        public class Node {
            // Char representing character value of node
            char character;
            // String representing the value at the node (if it exists)
            String value;
            // TST of nodes representing children with greater character value
            TST bigKids;
            // TST of nodes representing children with lesser character value
            TST littleKids;
            // Node representing child with equal character value
            TST middleKid;

            // Initialize node variables
            public Node (char c, String val, TST big, TST little, TST mid) {
                character = c;
                value = val;
                bigKids = big;
                littleKids = little;
                middleKid = mid;
            }

        }

        // Method to set the root of the TST to a given character
        public void setRoot(char c) {
            root = new Node(c, null, null, null, null);
        }

        // Method that returns value of given key, returns null if the key isn't in the table
        public String checkKey(String key) {
            // Start by setting the current node to the root node
            Node current = root;
            // Substring representing remaining suffix
            String remainder = key;
            // Iterate through every letter in the key
            while (!remainder.isEmpty()) {
                // Set curVal equal to the ASCII value of the current node's character
                int curVal = current.character;
                // Set charVal equal to the ASCII value of the current key's character
                int charVal = remainder.charAt(0);


                // If the current letter is equal to the character of the current node, explore the middle link
                if (charVal == curVal) {
                    // Cut off the first letter of the remaining key, as the node of the current letter has been visited
                    remainder = remainder.substring(1);
                    // If there aren't more letters to explore, break to return whether key-value pair is in table
                    if (remainder.isEmpty()) {
                        break;
                    }
                    // If there are more chars and middleKid TST hasn't been initialized, key-value pair isn't in table
                    // Therefore return false
                    if (current.middleKid == null) {
                        return null;
                    }
                    // Otherwise, if the middleKid TST is already initialized, set current to the root of middleKid
                    else {
                        current = current.middleKid.root;
                    }
                }
                // Otherwise if the value of the root letter is less than the current Node, explore left link
                else if (charVal < curVal) {
                    // If littleKids TST hasn't been initialized, key-value pair isn't in table, so return false
                    if (current.littleKids == null) {
                        return null;
                    }
                    // Otherwise, if the littleKids TST is already initialized, set current to the root of littleKids
                    else {
                        current = current.littleKids.root;
                    }
                }
                // Otherwise the value of the current character is greater than the current Node, so explore right link
                else {
                    // If bigKids TST hasn't been initialized, key-value pair isn't in table, so return false
                    if (current.bigKids == null) {
                        return null;
                    }
                    // Otherwise, if the bigKids TST is already initialized, set current to the root of bigKids
                    else {
                        current = current.bigKids.root;
                    }
                }

            }
            // Return whether current node represents end of real key or not
            return current.value;
        }


        // Method to insert new key-value pairs into the TST
        public void addKeyValue(String key, String value) {
            // Start by setting the current node to the root node
            Node current = root;
            // Substring representing remaining suffix
            String remainder = key;
            // Iterate through every char in the key
            while (!remainder.isEmpty()) {
                // Set curVal equal to the ASCII value of the current node's character
                int curVal = current.character;
                // Set charVal equal to the ASCII value of the current key's character
                int charVal = remainder.charAt(0);

                // If the value of the root char is less than the current Node, explore left link
                if (charVal < curVal) {
                    // If the littleKids TST of the current node hasn't been initialized, set littleKids to a new TST
                    if (current.littleKids == null) {
                        current.littleKids = new TST();
                        // Set the root of the TST to a new Node with the char as the first char of the remaining key
                        current.littleKids.root = new Node(remainder.charAt(0),null,null,null,null);
                    }
                    // Set the current node equal to the root of the littleKids TST
                    current = current.littleKids.root;
                }
                // Otherwise if the value of the current character is greater than the current Node, explore right link
                else if (charVal > curVal) {
                    // If the bigKids TST of the current node hasn't been initialized, set bigKids to a new TST
                    if (current.bigKids == null) {
                        current.bigKids = new TST();
                        // Set the root of the TST to a new Node with the char as the first char of the remaining key
                        current.bigKids.root = new Node(remainder.charAt(0),null,null,null,null);
                    }
                    // Set the current node equal to the root of the bigKids TST
                    current = current.bigKids.root;
                }
                // Otherwise, the current char is equal to the character of the current node, so explore the middle link
                else {
                    // Cut off the first letter of the remainder, because the node of the current char has been visited
                    remainder = remainder.substring(1);
                    // If there are no more remaining chars in the key, break from the for loop to set the value
                    if (remainder.isEmpty()) {
                        break;
                    }
                    // If the current node has no children yet, set the middle node to the current letter in the key
                    if (current.middleKid == null) {
                        // Set the middle kid of the current node to a new TST
                        current.middleKid = new TST();
                        // Set the root of the middle kid to the current character in the key
                        current.middleKid.root = new Node(remainder.charAt(0),null,null,null,null);
                    }
                    // Set current to the root of the resulting suffix
                    current = current.middleKid.root;
                }

            }
            // Set the value of the current node to true to represent that key is in table
            current.value = value;
        }

    }
}