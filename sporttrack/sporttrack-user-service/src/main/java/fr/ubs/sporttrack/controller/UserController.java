package fr.ubs.sporttrack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ubs.sporttrack.user.model.User;
import fr.ubs.sporttrack.user.model.JSONFileReader;
import fr.ubs.sporttrack.user.model.JSONFileWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/users")
public class UserController {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<List<User>> findAll(){
        List<User> users;
        File file = new File("user.json"); // Récupération du fichier json
        try {
            JSONFileReader jsonReader = new JSONFileReader(file);
            users = jsonReader.getUsers();
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Search a specific user and return token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> searchUser(@RequestParam String email) {
        File file = new File("user.json"); // Récupération d'un fichier json
        try {
            List<User> users = findAllUsersFromFile(file);
            Map<String, Object> response = new HashMap<>();
            User foundUser = null;
            boolean exist = false;
            for (User user : users) {
                if (!exist && user.getEmail().equals(email)) {
                    exist = true;
                    foundUser = user;
                    break;
                }
            }
            response.put("exist", exist);
            response.put("user", foundUser);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("exist", false);
            errorResponse.put("user", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Add a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping(path="/", consumes="application/json", produces="application/json")
    public ResponseEntity<?> addUser(@RequestBody User user){
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if(!violations.isEmpty()){
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                message.append(violation.getMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(message.toString());
        }
    
        if(user != null){
            File file = new File("user.json"); // Récupération d'un fichier json
            try {
                List<User> users = findAllUsersFromFile(file);
                users.add(user);
    
                JSONFileWriter jsonWriter = new JSONFileWriter(file);
                jsonWriter.writeData(users); 
                jsonWriter.close();
                return ResponseEntity.ok("User added successfully");
            } catch(IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error writing data into json file: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid request body");
        }
    }
    
    private List<User> findAllUsersFromFile(File file) throws IOException {
        JSONFileReader jsonReader = new JSONFileReader(file);
        return jsonReader.getUsers();
    }
}
