/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.sdev425hw2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jim Adopted from Oracle's Login Tutorial Application
 * <a href="https://docs.oracle.com/javafx/2/get_started/form.htm">...</a>
 */
public class SDEV425_2 extends Application {


    @Override
    public void start(Stage primaryStage) {
        final int[] loginAttempt = {2};
        //Date and Time for audit purposes
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String filename = "AuditLog.txt";


        primaryStage.setTitle("SDEV425 Login");
        // Grid Pane divides your window into grids
        GridPane grid = new GridPane();
        // Align to Center
        // Note Position is geometric object for alignment
        grid.setAlignment(Pos.CENTER);
        // Set gap between the components
        // Larger numbers mean bigger spaces
        grid.setHgap(10);
        grid.setVgap(10);

        // Create some text to place in the scene
        Text sceneTitle = new Text("""
                1) You are attempting to access a US Gov system
                2) System usage may be monitored, recorded, and subject to audit
                3) Unauthorized use of the system is prohibited and subject to criminal and civil penalties; and
                4) Use of the system indicates consent to monitoring and recording;
                 \s
                """);
        // Add text to grid 0,0 span 2 columns, 1 row
        grid.add(sceneTitle, 0, 0, 2, 1);

        // Create Label
        Label userName = new Label("User Name:");
        // Add label to grid 0,1
        grid.add(userName, 0, 1);

        // Create Text field
        TextField userTextField = new TextField();
        // Add text-field to grid 1,1
        grid.add(userTextField, 1, 1);

        // Create Label
        Label pw = new Label("Password:");
        // Add label to grid 0,2
        grid.add(pw, 0, 2);

        // Create Password-field
        PasswordField pwBox = new PasswordField();
        // Add Password field to grid 1,2
        grid.add(pwBox, 1, 2);

        // Create Login Button
        Button btn = new Button("Login");
        // Add button to grid 1,4
        grid.add(btn, 1, 4);

        // Create Label
        Label attemptsRemaining = new Label("Attempts Remaining:" + 3);
        // Add label to grid 0,2
        grid.add(attemptsRemaining, 0, 3);

        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);

        // Set the Action when button is clicked
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // Authenticate the user
                boolean isValid = authenticate(userTextField.getText(), pwBox.getText());
                // If valid clear the grid and Welcome the user
                try {
                        FileWriter fw = new FileWriter("AuditLog.txt");
                        PrintWriter pw = new PrintWriter(fw);
                    boolean isLoggedIn = false;
                    // If valid clear the grid and Welcome the user
                    if (loginAttempt[0] <= 0 && isValid) {
                        grid.setVisible(false);
                        GridPane grid2 = new GridPane();
                        // Align to Center
                        // Note Position is geometric object for alignment
                        grid2.setAlignment(Pos.CENTER);
                        // Set gap between the components
                        // Larger numbers mean bigger spaces
                        grid2.setHgap(10);
                        grid2.setVgap(10);
                        Text sceneTitle = new Text("Welcome " + userTextField.getText() + "!");
                        // Add text to grid 0,0 span 2 columns, 1 row
                        grid2.add(sceneTitle, 0, 0, 2, 1);
                        Scene scene = new Scene(grid2, 500, 400);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        isLoggedIn = true;
                        fw.write(("Success Login " + dtf.format(now) +" " + userTextField.getText()));
                        fw.close();


                        // If Invalid Ask user to try again
                    } else if (loginAttempt[0] != 0) {
                        final Text actionTarget = new Text();
                        grid.add(actionTarget, 1, 6);
                        actionTarget.setFill(Color.FIREBRICK);
                        actionTarget.setText("Please try again.");
                        fw.write(("Failed Login " + dtf.format(now) +" " + userTextField.getText()));
                        fw.close();
                    } else {
                        final Text actionTarget = new Text();
                        grid.add(actionTarget, 1, 6);
                        actionTarget.setFill(Color.FIREBRICK);
                        actionTarget.setText("Login attempts exceeded." + loginAttempt[0]);
                        //Force closes the application to prevent further login attempts
                        primaryStage.close();


                    }
                    loginAttempt[0]--;

                } catch (IOException ex) {
                    Logger.getLogger(SDEV425_2.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
        );
        // Set the size of Scene
        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param user     the username entered
     * @param password the password entered
     * @return isValid true for authenticated
     */
    public boolean authenticate(String user, String password) {

        return user.equalsIgnoreCase("sdevadmin")
                && password.equals("425!pass");
    }

}
