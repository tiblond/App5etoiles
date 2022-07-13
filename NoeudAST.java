package app5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {
  // Attributs
  String type;
  FeuilleAST EnfantG;
  FeuilleAST EnfantD;
  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(String el,FeuilleAST Eg,FeuilleAST Ed) { // avec arguments
    type = el;
    EnfantG = Eg;
    EnfantD = Ed;
  }

 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST() {
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
  public String LectAST( ) {
    return EnfantG.LectAST() + type + EnfantD.LectAST();
  }

}


