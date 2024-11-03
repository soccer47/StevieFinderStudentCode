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

    private Hashmap table = new Hashmap();

    public void buildTable(BufferedReader br, int keyCol, int valCol) throws IOException {
        // TODO: Complete the buildTable() function!
        // Array of Strings to hold the values at each line of the csv file one at a time
        String[] line;
        // While there are more key-value pairs to add to the TST, call the readLine() function to add to the TST
        while (br.ready()) {
            line = br.readLine().split(",");
            table.add(line[keyCol], line[valCol]);
        }
        // Close the csv file
        br.close();
    }

    public String query(String key){
        // If the key is in the table, return the value of the key-value pair
        // Return "INVALID KEY" if key isn't in the table
        return table.get(key);
    }

}