// Student: Kaillah Selvaretnam
// CSE 145 1736
// Professor: Jeremiah Ramsey
// Lab 6
// This program creates a Question tree with which one can play the game 20 Questions. 

import java.util.*;
import java.io.*;

public class QuestionTree {
   //fields
   private QuestionNode root;
   private final UserInterface ui;
   private int gamesPlayed;
   private int gamesWon;
   
   //This method constructs a QuestionTree. The parameter is a UserInterface used for
   //input/output. An IllegalArgumentExcpetion is thrown if the UserInterface object
   //is null. 
   public QuestionTree(UserInterface ui) {
      if (ui == null) {
         throw new IllegalArgumentException(); 
      }
      
      this.root = new QuestionNode("Computer");
      this.ui = ui;
      this.gamesPlayed = 0;
      this.gamesWon = 0;
   }
   
   //This method playes a full game with the user. There are no parameters.  
   public void play() {
     this.root = playHelper(this.root); 
   }
   
   //This method recursively guides the player throgh a round of the guessing game
   //by providing question and answer prompts and updating the tree structure and 
   //game statistics according to the users responses. The parameter is a Question node
   //representing the current node that is being assest. The return of the method is 
   //a QuestionNode representing the modified node. 
   private QuestionNode playHelper(QuestionNode node) {
      if(isAnswer(node)) {
         this.gamesPlayed++;
         System.out.print("Is it a " + node.data + "? ");
         
         if(ui.nextBoolean()) {
            ui.println("I win!");
            this.gamesWon++; 
         } else {
            node = learn(node);
         }
         
      } else {
         //play another round and evalutate yes and no branch
         ui.print(node.data);
         if(ui.nextBoolean()) {
            node.yes = playHelper(node.yes);
         } else {
            node.no = playHelper(node.no);
         }
      }
      return node;
   }
   
   //This method determines whether or not the given node is an answer node. The
   //parameter is a QuestionNode representing the node we want to inspect. The return
   //is a boolean representing if the node is an answer node.
   private boolean isAnswer(QuestionNode node) {
      return (node.yes == null && node.no == null);
   }
   
   //This method allows the program to "learn" by modifying and adding nodes.
   //The parameter is the QuestionNode that is being modified and the return is 
   //the mofified QuestionNode.   
   private QuestionNode learn (QuestionNode node) {
      System.out.print("What were you thinking of? ");
      QuestionNode answer = new QuestionNode(ui.nextLine());
      System.out.print("Privde a yes/no question that distinguishes our objects: ");
      String question = ui.nextLine();
      
      System.out.print("Does your object fall under yes or no to the provided question? ");
      
      if(ui.nextBoolean()) {
         return new QuestionNode(question, answer, node);
      } else {
         return new QuestionNode(question, node, answer);
      }
   }
   
   //This method reports the total number of games played. There are no parameters and the 
   //return is an integer representing the total number of games played. 
   public int totalGames() {
      return this.gamesPlayed;
   }
   
   //This method reports the total number of games won. There are no parameters and the 
   //return is an integer representing the total number of games won.
   public int gamesWon() {
      return this.gamesWon;
   }
   
   //This method recursively saves a QuestionTree to a file. The parameters are a
   //QuestionNode representing the root of the tree and a PrintStream for the output. 
   private void saveHelper(QuestionNode root, PrintStream output) {
      if (isAnswer(root)) {
         output.println("A:" + root.data); 
      } else {
         output.println("Q:" + root.data);
         saveHelper(root.yes, output);
         saveHelper(root.no, output); 
      }   
   }
   
   //This method saves a QuestionTree to a file. The parameter is a PrintStream for the output.
   //An IllegalArugemenException is thrown if the PrintStream is null. 
   public void save(PrintStream output) {
      if(output == null) {
         throw new IllegalArgumentException();
      }
      
      saveHelper(root, output);
   }
   
   //This method allows the user to load in a file containing a populated QuestionTree.
   //The parameter is a Scanner to read the file and an IllegalArgumentException is 
   //thrown if the Scanner is null. 
   public void load(Scanner input) {
      if (input == null) {
         throw new IllegalArgumentException(); 
      }
      while(input.hasNextLine()) {
         root = loadHelper(input); 
      }
   }
   
   // This method reads entire lines of input to construct a QuestionTree based on a file. 
   //The parameter is a Scanner for input and the return is a QuestionNode representing the
   //current node. 
   private QuestionNode loadHelper(Scanner input) {
      String entry = input.nextLine();
      String type = entry.substring(0,2);
      String data = entry.substring(2);
      QuestionNode currRoot = new QuestionNode(data);  
  
      if (type.contains("Q:")) {
         currRoot.yes = loadHelper(input);
         currRoot.no = loadHelper(input);   
      }
      return currRoot; 
   }
}