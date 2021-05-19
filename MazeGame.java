import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.Scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.animation.*;
import java.lang.*;
import java.util.*;
import java.io.*;

//Lab written by Ted (Edward) Schultz
// May 20, 2021
// Professor Mood

public class MazeGame extends Application {
   
   GraphicsContext gc;
   MazeCanvas mazeCanvas = new MazeCanvas();
   FlowPane root = new FlowPane();
   Random rand = new Random();
   
   ArrayList<ArrayList<Integer>> blockNum = new ArrayList<ArrayList<Integer>>();

   public static int X;
   public static int Y;
   public int blockSize = 25; //This is the size of the blocks in the game and the size of the game itself.
   
   public void start(Stage stage) {
   
      mazeCanvas.draw();
      mazeCanvas.initializeBlock();
      mazeCanvas.setOnKeyPressed(new KeyListener()); //listens to when the mouse is pressed and dragged and relays it to the canvas
      
      root.getChildren().add(mazeCanvas); //adding the Canvas to the FlowPane

      Scene scene = new Scene(root,525,525); 
      
      stage.setScene(scene); 
      stage.setTitle("Super Epic Maze Game");
      stage.show();
     
      mazeCanvas.requestFocus(); //request focus so the program looks to the click event.
      
      }
   
   public class MazeCanvas extends Canvas {
   
      GraphicsContext gc = getGraphicsContext2D();
      
      public MazeCanvas() {
         setWidth(525); //setting the dimensions of the canvas to be 500 by 500
         setHeight(525);
         }
         
      public void initializeBlock() {
      
         for (int j=0; j<21; j++) {
         if (blockNum.get(0).get(j) == 0) { //for and if sets the inital starting point based on upper row of the maze
               Y = 0;
               X = j*blockSize;
               }
            }
            
         //gc.setFill(new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1)); //Color of user square when initialized
         gc.setFill(new Color(1, 0.5, 0, 1)); //Color of user square when initialized (Solid color)
         gc.fillRect(X, Y, blockSize, blockSize);
         }
         
      public void draw() {
      
         try {

            Scanner scan = new Scanner(new File("maze.txt"));
            
            for (int i=0; i<21; i++) { //initializing the inner list of the 2d ArrayList
               ArrayList<Integer> innerList = new ArrayList<Integer>();
               blockNum.add(innerList);
               }
            
            while (scan.hasNextInt()) { //reading in the values of the 2d Array list one row at a time
               for (int i=0; i<21; i++) {
                  for (int j=0; j<21; j++) {
                     blockNum.get(i).add(scan.nextInt());
                     }
                  }
               }
               
            for (int i=0; i<21; i++) { //two for loops draw the maze based on the read file
               for (int j=0; j<21; j++) {
                  if (blockNum.get(i).get(j) == 1) {
                     gc.setFill(Color.BLACK);
                     gc.fillRect(j*blockSize,i*blockSize,blockSize,blockSize);
                     }
                  }
               }
            }
         
         catch (FileNotFoundException fnfe)  
            {
            System.out.println("File not found.");
            }

         }
      }
  
   public class KeyListener implements EventHandler<KeyEvent> { //KeyListener will stop and start the animation timer depending on which key is pressed 
      
      public void handle(KeyEvent event) {  
      
      gc = mazeCanvas.getGraphicsContext2D();
      gc.clearRect(0,0,525,525);
      mazeCanvas.draw();
      gc.setFill(new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1)); //Decided to make a random color for the user every move
      //gc.setFill(new Color(1, 0.5, 0, 1)); //If the user wants to be a single color. 
      
      switch(event.getCode()) {
      
         case DOWN:
         
            if (Y<500 && blockNum.get((Y/blockSize) + 1).get(X/blockSize) != 1) { //if statement prevents the user from moving pas the canvas boundry and the black squares where there are 1's. 
               Y += blockSize;   
               gc.fillRect(X, Y, blockSize, blockSize); //redraws the rectangle with the new coordinates
               }
            else {
               gc.fillRect(X, Y, blockSize, blockSize);
               }
            break;
   
         case RIGHT: 
               if (X<500 && blockNum.get(Y/blockSize).get(X/blockSize + 1) != 1) {  
               X += blockSize;   
               gc.fillRect(X, Y, blockSize, blockSize);
               }
               else {
                  gc.fillRect(X, Y, blockSize, blockSize);
                  }
            break;
            
         case UP:   
            if (Y>0 && blockNum.get(Y/blockSize - 1).get(X/blockSize) != 1) { 
               Y -= blockSize;   
               gc.fillRect(X, Y, blockSize, blockSize);
               }
            else {
               gc.fillRect(X, Y, blockSize, blockSize);
               }
            break;
               
         case LEFT: 
            if (X>0 && blockNum.get(Y/blockSize).get(X/blockSize - 1) != 1) {    
               X -= blockSize;   
               gc.fillRect(X, Y, blockSize, blockSize);
               }
            else {
                  gc.fillRect(X, Y, blockSize, blockSize);
                  }
            break;
            }
         
      for (int i=0; i<21; i++) {
         if (blockNum.get(20).get(i) == 0) {
            if (X == (i*blockSize) && Y == 500) { //when y == 500 and the user reaches the blank space the user has won. 
               System.out.println("You Win!"); //printing a message that the user won. 
               mazeCanvas.initializeBlock(); //initalizes the block as soon as the user wins. 
               }
            }
         }
         
      }   
   }
         
   public static void main(String []args) {
      launch(args);
      }           
}