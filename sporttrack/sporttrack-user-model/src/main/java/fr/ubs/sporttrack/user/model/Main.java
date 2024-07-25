package fr.ubs.sporttrack.user.model;

import java.io.File;
import java.util.List;


public class Main{

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: Main <path_to_json_file>");
            System.exit(1);
        }

        String jsonFilePath = args[0];

        try {
            JSONFileReader jsonFileReader = new JSONFileReader(new File(jsonFilePath));
            List<User> users = jsonFileReader.getUsers();

            System.out.println("Contenu du fichier JSON :");
            for (User user : users) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
