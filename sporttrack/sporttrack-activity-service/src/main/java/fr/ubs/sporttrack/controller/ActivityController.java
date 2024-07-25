package fr.ubs.sporttrack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.ubs.sporttrack.activity.model.Activity;
import fr.ubs.sporttrack.activity.model.JSONFileReader;
import fr.ubs.sporttrack.activity.model.JSONFileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/activities")
public class ActivityController {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Operation(summary = "Get all activities")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Activity.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<List<Activity>> findAll(){
        List<Activity> activities;
        File file = new File("data.json"); // Récupération du fichier json
        try {
            JSONFileReader jsonReader = new JSONFileReader(file);
            activities = jsonReader.getActivities();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
        return ResponseEntity.ok(activities);
    }

    @Operation(summary = "Add an activity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Activity added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(path="/", consumes="application/json", produces="application/json")
    public ResponseEntity<?> addActivity(@RequestBody Activity act){
        Set<ConstraintViolation<Activity>> violations = validator.validate(act);
        if(!violations.isEmpty()){
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<Activity> violation : violations) {
                message.append(violation.getMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(message.toString());
        }
    
        if(act != null){
            File file = new File("data.json"); // Récupération d'un fichier json
            try {
                List<Activity> activities = findAllActivitiesFromFile(file);
                activities.add(act);
    
                JSONFileWriter jsonWriter = new JSONFileWriter(file);
                jsonWriter.writeData(activities); 
                jsonWriter.close();
                return ResponseEntity.ok("Activity added successfully");
            } catch(IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing data into json file: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid request body");
        }
    }
    
    private List<Activity> findAllActivitiesFromFile(File file) throws IOException {
        JSONFileReader jsonReader = new JSONFileReader(file);
        return jsonReader.getActivities();
    }



    @Operation(summary = "Find activities by keyword")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Activity.class))),
        @ApiResponse(responseCode = "404", description = "No activities found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{keyword}")
    public ResponseEntity<?> findByKeyword(@PathVariable("keyword") @NotBlank String keyword){
        if (keyword.isBlank()) {
            return ResponseEntity.badRequest().body("Keyword cannot be blank");
        }
    
        List<Activity> sort_activities = new ArrayList<Activity>();
        File file = new File("data.json"); // Récupération du fichier json
        try {   
            JSONFileReader jsonReader = new JSONFileReader(file);
            List<Activity> allActivities = jsonReader.getActivities();
            for(Activity activity : allActivities){
                if(activity.toString().toUpperCase().contains(keyword.toUpperCase())){
                    sort_activities.add(activity);
                }
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
        if(sort_activities.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(sort_activities);
    }
    
}
