// Student: Kaillah Selvaretnam
// CSE 145 1736
// Professor: Jeremiah Ramsey
// Lab 6
// This program creates the node used in the question tree. 

public class QuestionNode {
   //fields
   public String data;
   public QuestionNode yes;
   public QuestionNode no;
   
   //This method constructs an answer node (leaf). The parameter is a String representing the 
   //answer to a question. 
   public QuestionNode(String data) {
      this.data = data;
      this.yes = null;
      this.no = null;
   }
   
   //This method constructs a question node. The parameters are a String representing the
   //question, and two QuestionNodes, one representing the yes answer to the question and
   //one the no answer. 
   public QuestionNode(String data, QuestionNode yes, QuestionNode no){
      this.data = data;
      this.yes = yes;
      this.no = no;
   }
}