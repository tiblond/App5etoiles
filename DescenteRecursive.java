package app5;

/** @author Ahmed Khoumsi */

import static app5.Test.testLexicale;
import static app5.Test.testSyntax;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {
  AnalLex lexical;
  Terminal UL;
  int cptP = 0;
  // Attributs

/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
  lexical = new AnalLex(in); // Creation de l'analyseur lexical
  UL = null;
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt() throws SyntaxErreur, LexicalErreur {
  String data;
  if(lexical.resteTerminal()){
    UL = lexical.prochainTerminal();
    cptP = 0;
    ElemAST racine = S();
    if(cptP < 0)
    {
      ErreurSynt(UL.chaine, UL.position, 1);
    }
    else {
      return racine;
    }
  }
  return null;
}

// Methode pour chaque symbole non-terminal de la grammaire retenue
private ElemAST S() throws SyntaxErreur, LexicalErreur {
  ElemAST response = U();
  if (UL.typeUL == type.OPERATEURB)
  {
    ElemAST n1 = response;
    ElemAST n2 = null;
    String type = UL.chaine;
    if(lexical.resteTerminal()) {
      UL = lexical.prochainTerminal();
      if (UL.typeUL == app5.type.OPERATEURO | UL.typeUL == app5.type.OPERANTENUM | UL.typeUL == app5.type.OPERANTE)
      {
        n2 = S();
      }
      else {
        ErreurSynt(UL.chaine, UL.position, 5);
      }
      response = new NoeudAST(type, n1, n2);
    }
  }

  return response;
};
private ElemAST U() throws SyntaxErreur, LexicalErreur {
  ElemAST response = V();
  if (UL.typeUL == type.OPERATEURC)
  {
    ElemAST n1 = response;
    ElemAST n2 = null;
    String type = UL.chaine;
    if(lexical.resteTerminal()) {
      UL = lexical.prochainTerminal();
      if (UL.typeUL == app5.type.OPERATEURB | UL.typeUL == app5.type.OPERATEURC | UL.typeUL == app5.type.OPERATEURF)
      {
        ErreurSynt(UL.chaine, UL.position, 5);
      }
      n2 = U();
      response = new NoeudAST(type, n1, n2);
    }
  }
  return response;
};
private ElemAST V() throws SyntaxErreur, LexicalErreur {
  ElemAST response = null;
  if (UL.typeUL == type.OPERANTENUM)
  {
    response = new FeuilleAST(UL.chaine,UL.value);
    if(lexical.resteTerminal()) {
        UL = lexical.prochainTerminal();
    }
  }
  else if(UL.typeUL == type.OPERANTE){
    response = new FeuilleAST(UL.chaine,UL.value);
    if(lexical.resteTerminal()) {
      UL = lexical.prochainTerminal();
    }
  }
  else if(UL.typeUL == type.OPERATEURO){
    cptP++;
    if(lexical.resteTerminal()) {
        UL = lexical.prochainTerminal();
        if (UL.typeUL == type.OPERATEURC | UL.typeUL == type.OPERATEURB)
        {
          ErreurSynt(UL.chaine, UL.position, 4);
        }
        response = S();
        if (UL.typeUL == type.OPERATEURF) {
          cptP--;
          if(lexical.resteTerminal()) {
            UL = lexical.prochainTerminal();
          }
        } else {
          ErreurSynt(UL.chaine, UL.position, 6);
        }
    }
  }
  else if (UL.typeUL == type.OPERATEURF){
    cptP--;
  }

  return response;
};



/** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s,int errPos,int errCode) throws SyntaxErreur {
  String message = "";
  switch (errCode) {
    case 1 -> message = "Error detected, too much ')' in current expression.";
    case 4-> message = "Error detected, operator after '(' in UL : " + s + " at position : " + errPos + ".";
    case 5-> message = "Error detected, subsequent operator in UL : " + s + " at position : " + errPos + ".";
    case 6-> message = "Error detected, doesn't find a matching ')' for '(' in UL : " + s + " at position : " + errPos + ".";
    default -> {
      message = "Unknown syntax error occurred.";
    }
  }
  throw new SyntaxErreur(message);
}



  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {

    if (args.length == 0){
      args = new String [6];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatSyntaxique.txt";
      args[2] = "ResultatSyntaxique1.txt";
      args[3] = "ResultatSyntaxique2.txt";
      args[4] = "ResultatLexical.txt";
      args[5] = "ResultatLexical1.txt";

    }

    testSyntax("(U_x-V_y)*W_z/35",1, args[1]);
    testSyntax("(55-47)*14/2",2, args[2]);
    testSyntax("(U_x-)*W_z/35",3, args[3]);
    testLexicale("(U_x+V_y)*W_z/35",1,args[4]);
    testLexicale("(U_x+V_y)*W__z/35",2,args[5]);
  }

}

