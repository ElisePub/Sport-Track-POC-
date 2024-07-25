package fr.ubs.sporttrack.user.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class JSONFileReader {
    private List<User> users;

    /**
     * Creates a JSONFileReader object that opens and reads the file
     * specified as parameter, and stores the content in a list of
     * objects of type <code>User</code>.
     * @param f The file that must be read.
     * @exception IOException if the file does not exist or cannot be
     * read.
     */
    public JSONFileReader(File f) throws IOException {
        
        this.users = new ArrayList<>();
        StringBuilder jsonText = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + f.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Error reading file: " + f.getAbsolutePath());
        }

        try {
            JSONArray usersArray = new JSONArray(jsonText.toString());
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJson = usersArray.getJSONObject(i);
                User user = User.fromJSON(userJson);
                users.add(user);
            }
        } catch (Exception e) {
            throw new IOException("Error parsing JSON from file: " + f.getAbsolutePath());
        }
    }

    /**
     * Returns a list of objects of type <code>JSONObject</code> that have
     * been read from the file.
     * @return a list of objects of type <code>JSONObject</code>.
     */
    public List<User> getUsers() {
        return this.users;
    }
}
