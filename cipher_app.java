package cipher_app;

// These are all the necessary imports for this program
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class cipher_app extends Application 
{   
    //Image and ImageView variables
    private Image imageLogo = new Image("file:logo.jpg");
    private ImageView imageView = new ImageView(imageLogo);
    
    // A variable declared at the global scope that holds the file path.
    private String filePath = null;
    
    // This is simply the main method.
    public static void main(String[] args) 
    {
        launch(args);
    }    
    
    // This is the start method, which holds the program's GUI
    @Override
    public void start(Stage primaryStage) 
    {
        // A FileChooser that will provide dialog boxes for opening and saving files
        FileChooser fileChooser = new FileChooser();
        
        // Buttons for the application
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");
        Button createFileButton = new Button("Create File");
        Button readFileButton = new Button("Read File");
        
        // Text area and respective label
        Label fileContentFieldLabel = new Label("Enter contents here:");
        TextArea fileContentField = new TextArea();
        
        // Labels for the slider control and feedback for the user
        Label shiftCountLabel = new Label("Shift count:");
        Label feedbackLabel = new Label();
        
        // Label for the program's instructions
        Label Instructions = new Label("Welcome to the Jaguar Caesar Cipher.\n"
                                    + "Please create a plaintext file to begin,\n"
                                    + "only use uppercase or lowercase letters.\n\n"
                                    + "To encrypt, choose a location to save the\n"
                                    + "encrypted file to. Make sure to add a .txt\n"
                                    + "extension.\n\n"
                                    + "To decrypt, first make sure the encrypted\n"
                                    + "file's contents are read in to the program,\n"
                                    + "then choose a location to save the decrypted\n"
                                    + "file to.\n");
        
        // Aspect ratio of the image
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        
        // Set the style classes for the buttons and the functionality for each button
	encryptButton.getStyleClass().add("encrypt-button");
	decryptButton.getStyleClass().add("decrypt-button");
	createFileButton.getStyleClass().add("createFile-button");
	readFileButton.getStyleClass().add("readFile-button");       
        
        // HBox for the header of the program
        HBox headerBox = new HBox(20, Instructions, imageView);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        // Slider for the shift count and defined attributes
        Slider shiftCount = new Slider(0,25,0);
        shiftCount.setShowTickMarks(true);
        shiftCount.setMajorTickUnit(1);
        shiftCount.setMinorTickCount(0);
        shiftCount.setShowTickLabels(true);
        shiftCount.setSnapToTicks(true);
        
        // HBox for the encrypt/decrypt buttons
        HBox en_decrypthBox = new HBox(60, encryptButton, decryptButton);
        en_decrypthBox.setAlignment(Pos.CENTER);
        
        // HBox for the create/read file buttons
        HBox read_createhBox = new HBox(60, createFileButton, readFileButton);
        read_createhBox.setAlignment(Pos.CENTER);
        
        // HBox for the button containers
        HBox buttons = new HBox(60, en_decrypthBox, read_createhBox);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));
        
        // VBox for the text area
        VBox fileContents = new VBox(10, fileContentFieldLabel, fileContentField);
        
        //VBox for the slider
        VBox shiftCountBox = new VBox(10, shiftCountLabel, shiftCount);
        
        // VBox for all containers
        VBox allcontentVBox = new VBox(10, headerBox, buttons, fileContents, shiftCountBox, feedbackLabel);
        allcontentVBox.setPadding(new Insets(10));
        allcontentVBox.setAlignment(Pos.CENTER);
        
        // Event handler for the createFile button
        createFileButton.setOnAction(event -> {
            try 
            {
                File selectedFile = fileChooser.showSaveDialog(primaryStage);
                    
                String fileName = selectedFile.getPath();
                filePath = fileName;
                
                primaryStage.setTitle("Color Control Panel - " + filePath);
                String input = fileContentField.getText();
                    
                PrintWriter outputFile = new PrintWriter(filePath);
                outputFile.write(input);
                outputFile.close();
                    
                feedbackLabel.setText("File successfully created.");
            } 
            catch (IOException ex) 
            {
                feedbackLabel.setText("Something went wrong while creating the file.");
                Logger.getLogger(cipher_app.class.getName()).log(Level.SEVERE, null, ex);
            }   
        });

	// Event handler for the readFile button
        readFileButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            
             if (selectedFile != null)
            {
                try 
                {
                    String fileName = selectedFile.getPath();
                    filePath = fileName;

                    Scanner inputFile = new Scanner(selectedFile);
                    fileContentField.setText(inputFile.nextLine());
                    inputFile.close();
                    
                    feedbackLabel.setText("Successfully read contents from the file.");
                } 
                catch (FileNotFoundException ex) 
                {
                    feedbackLabel.setText("Something went wrong while reading the file.");
                    Logger.getLogger(cipher_app.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        });
        
        // Event handler that calls the encrypt method
        encryptButton.setOnAction(event -> {
            if(filePath != null){
                try 
                {
                    int shiftCountValue = (int) shiftCount.getValue();
                    
                    File selectedFile = fileChooser.showSaveDialog(primaryStage);
                
                    String fileName = selectedFile.getPath();
                    filePath = fileName;
                
                    String originalContent = fileContentField.getText();
                    StringBuffer encryptedInput = Algorithm.encrypt(originalContent, shiftCountValue);
                    String encryptedString = encryptedInput.toString();
                    
                    PrintWriter encryptedFile = new PrintWriter(filePath);
                    encryptedFile.write(encryptedString);
                    encryptedFile.close();
                    
                    feedbackLabel.setText("File contents have been successfully encrypted.");
                } 
                catch (IOException ex) 
                {
                    feedbackLabel.setText("Something went wrong while encrypting the file.");
                    Logger.getLogger(cipher_app.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            else feedbackLabel.setText("You must first create a plaintext file.");
        });
       
        // Event handler that calls the decrypt method
        decryptButton.setOnAction(event -> {
            if(filePath != null){
                try 
                {
                    int shiftCountValue = (int) shiftCount.getValue();
                                        
                    File selectedFile = fileChooser.showSaveDialog(primaryStage);
                
                    String fileName = selectedFile.getPath();
                    filePath = fileName;
                
                    String originalContent = fileContentField.getText();
                    StringBuffer decryptedInput = Algorithm.decrypt(originalContent, shiftCountValue);
                    String decryptedString = decryptedInput.toString();
                    
                    PrintWriter decryptedFile = new PrintWriter(filePath);
                    decryptedFile.write(decryptedString);
                    decryptedFile.close();
                    
                    feedbackLabel.setText("File contents have been successfully decrypted.");
                } 
                catch (IOException ex) 
                {
                    feedbackLabel.setText("Something went wrong while decrypting the file.");
                    Logger.getLogger(cipher_app.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            else feedbackLabel.setText("You must first open an encrypted file.");
        });
        
        // Create the scene and set up the style
        Scene scene = new Scene(allcontentVBox, 550, 650);
        scene.getStylesheets().add(this.getClass().getResource("Style.css").toExternalForm());
        
        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cipher Program");
        primaryStage.show();
    }
}