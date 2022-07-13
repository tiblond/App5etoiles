package app5;

/** @author Ahmed Khoumsi */

/** Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {


  
  /** Evaluation d'AST
   */
  public abstract int EvalAST();


  /** Lecture d'AST
   */
  public abstract String LectAST();


/** ErreurEvalAST() envoie un message d'erreur lors de la construction d'AST
 */  
  public void ErreurEvalAST(String s, int errPos,int errCode) throws SyntaxErreur {
    String message = "";
    switch (errCode) {
      case 1 -> message = "Error detected get two '_' consecutive in the lexical unit : " + s + " at position : " + errPos + ".";
      case 2 -> message = "Error detected get digit in the lexical unit : " + s + " at position : " + errPos + ".";
      case 3 -> message = "Error detected get '_' at the end of a lexical unit : " + s + " at position : " + errPos + ".";
      case 4 -> message = "Error detected no capitalize letter at the begginning of the lexical unit : " + s + " at position : " + errPos + ".";
      case 5 -> message = "Error unknown symbol detected in the lexical unit : " + s + " at position : " + errPos + ".";
      case 6 -> message = "Error detected current char is not a digit: " + s + " at position : " + errPos + ".";
      default -> {
        message = "Unknown syntax error occurred.";
      }
    }
    throw new SyntaxErreur(message);
  }

}
