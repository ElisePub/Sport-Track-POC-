package fr.ubs.sporttrack;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.ubs.sporttrack.user.model.JSONFileReader;

@SpringBootApplication
public class SportTrackUserApp {
    public static void main(String[] args){
        getDataJson();
        SpringApplication.run(SportTrackUserApp.class, args);
    }

    private static void getDataJson(){
        File file = new File("user.json"); // Création d'un fichier json
        if (file.length() == 0) {
            InputStream is = JSONFileReader.class.getResourceAsStream("/user.json"); // Recupération du fichier json dans un inputstream
            if(is != null){ 
                try {
                    FileUtils.copyInputStreamToFile(is, file); // Copie le contenu des informations en json dans le nouveau fichier.
                } catch(Exception e){
                    System.out.println("conversion to file failed : " + e.getMessage());
                }
            }
        } 
    }
}
