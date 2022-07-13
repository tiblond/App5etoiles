package app5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
int value = 0;
String chaine = "";

/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(String el,int val) {  // avec arguments
    value = val;
    chaine = el;
  }


  /** Evaluation de feuille d'AST
   */
  public int EvalAST( ) {
      return value;
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
      return chaine;
  }

}