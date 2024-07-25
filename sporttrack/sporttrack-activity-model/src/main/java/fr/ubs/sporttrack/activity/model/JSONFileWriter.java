package fr.ubs.sporttrack.activity.model;

import org.json.JSONArray;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONFileWriter implements AutoCloseable {

    private BufferedWriter writer;

    public JSONFileWriter(File f) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(f, false)); // True for append mode
    }

    public void writeData(List<Activity> activities) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Activity activity : activities) {
            jsonArray.put(activity.toJSON());
        }

        try {
            writer.write(jsonArray.toString());
            writer.flush();
        } finally {
            close(); // Ensure the writer is closed after writing
        }
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}