package app5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {
  // Attributs
  String type;
  ElemAST EnfantG;
  ElemAST EnfantD;
  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(String el,ElemAST Eg,ElemAST Ed) { // avec arguments
    type = el;
    EnfantG = Eg;
    EnfantD = Ed;
  }


  /** Evaluation de noeud d'AST
   */
  public float EvalAST() {
    return switch (type) {
      case "+" -> EnfantG.EvalAST() + EnfantD.EvalAST();
      case "-" -> EnfantG.EvalAST() - EnfantD.EvalAST();
      case "*" -> EnfantG.EvalAST() * EnfantD.EvalAST();
      case "/" -> EnfantG.EvalAST() / EnfantD.EvalAST();
      default -> throw new IllegalStateException("Unexpected value: " + type);
    };
  }


  /** Lecture de noeud d'AST
   */
  public String PostLectAST( ) {
    return EnfantG.PostLectAST() + " " + EnfantD.PostLectAST() + " " + type;
  }

  public String LectAST( ) {
    return EnfantG.LectAST() + " " + type+ " " + EnfantD.LectAST();
  }
}


