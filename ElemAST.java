package app5;

/** @author Ahmed Khoumsi */

/** Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {


  /**
   * Evaluation d'AST
   */
  public abstract float EvalAST() throws SyntaxErreur;


  /**
   * Lecture d'AST
   */
  public abstract String LectAST();

  public abstract String PostLectAST();

}