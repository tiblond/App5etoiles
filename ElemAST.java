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
      case 1 -> message = "Error detected '(' not closed at position : " + errPos + ".";
      case 2 -> message = "Error detected ')' never open at position : " + errPos + ".";
      default -> {
        message = "Unknown syntax error occurred.";
      }
    }
    throw new SyntaxErreur(message);
  }

}
