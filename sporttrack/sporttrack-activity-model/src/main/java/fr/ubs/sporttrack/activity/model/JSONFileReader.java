package fr.ubs.sporttrack.activity.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class JSONFileReader {
    private List<Activity> activities;

    /**
     * Creates a JSONFileReader object that opens and reads the file
     * specified as parameter, and stores the content in a list of
     * objects of type <code>Activity</code>.
     * @param f The file that must be read.
     * @exception IOException if the file does not exist or cannot be
     * read.
     */
    public JSONFileReader(File f) throws IOException {
        this.activities = new ArrayList<>();
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
            JSONArray activitiesArray = new JSONArray(jsonText.toString());
            for (int i = 0; i < activitiesArray.length(); i++) {
                JSONObject activityJson = activitiesArray.getJSONObject(i);
                Activity activity = Activity.fromJSON(activityJson);
                activities.add(activity);
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
    public List<Activity> getActivities() {
        return this.activities;
    }
}
