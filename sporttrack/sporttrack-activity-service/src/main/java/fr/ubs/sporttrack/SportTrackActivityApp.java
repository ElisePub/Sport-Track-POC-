package fr.ubs.sporttrack;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.ubs.sporttrack.activity.model.JSONFileReader;

@SpringBootApplication
public class SportTrackActivityApp {
    public static void main(String[] args){
        getDataJson();
        SpringApplication.run(SportTrackActivityApp.class, args);
    }

    private static void getDataJson(){
        File file = new File("data.json"); // Création d'un fichier json
        if (file.length() == 0) {
            InputStream is = JSONFileReader.class.getResourceAsStream("/data.json"); // Recupération du fichier json dans un inputstream
            if(is != null){ 
                try {
                    FileUtils.copyInputStreamToFile(is, file); // Copie le contenu du json du TP1 dans le nouveau fichier.
                } catch(Exception e){
                    System.out.println("conversion to file failed : " + e.getMessage());
                }
            }
        } 
    }
}
