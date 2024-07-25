package fr.ubs.sporttrack.user.model;

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

    public void writeData(List<User> users) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (User user : users) {
            jsonArray.put(user.toJSON());
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