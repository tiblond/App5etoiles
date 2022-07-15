package app5;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
Float value;
String chaine = "";

/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(String el,Float val) {  // avec arguments
    value = val;
    chaine = el;
  }


  /** Evaluation de feuille d'AST
   */
  public float EvalAST( ) throws SyntaxErreur {
      if(value == null){
          ErreurEvalAST(1);
      }
      return value;
  }

    private void ErreurEvalAST(int errCode) throws SyntaxErreur {
        String message = "";
        if (errCode == 1) {
            message = "Error detected, try to evaluate operand : " + chaine + ".";
        } else {
            message = "Unknown evaluation error occurred.";
        }
        throw new SyntaxErreur(message);
    };


    /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
      return chaine;
  }
  public String PostLectAST( ) {
        return chaine;
    }
}
