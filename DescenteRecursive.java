package app5;

/** @author Ahmed Khoumsi */

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
      if (UL.typeUL == app5.type.OPERATEURF | UL.typeUL == app5.type.OPERATEURO | UL.typeUL == app5.type.OPERANTENUM)
      {
        n2 = S();
      }
      else if(UL.typeUL == app5.type.OPERANTE)
      {
        ErreurSynt(UL.chaine, UL.position, 3);
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
      if (UL.typeUL == app5.type.OPERATEURF | UL.typeUL == app5.type.OPERATEURO | UL.typeUL == app5.type.OPERANTENUM)
      {
        n2 = U();
      }
      else if(UL.typeUL == app5.type.OPERANTE)
      {
        ErreurSynt(UL.chaine, UL.position, 3);
      }
      else {
        ErreurSynt(UL.chaine, UL.position, 5);
      }
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
  else if(UL.typeUL == type.OPERATEURO){
    cptP++;
    if(lexical.resteTerminal()) {
        UL = lexical.prochainTerminal();
        if (UL.typeUL == type.OPERANTENUM | UL.typeUL == type.OPERATEURO)
        {
          response = S();
        }
        else {
          ErreurSynt(UL.chaine, UL.position, 4);
        }
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
  else if(UL.typeUL == type.OPERANTE){
    ErreurSynt(UL.chaine, UL.position, 3);
  }
  return response;
};



/** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s,int errPos,int errCode) throws SyntaxErreur {
  String message = "";
  switch (errCode) {
    case 1 -> message = "Error detected, too much ')' in current expression.";
    case 3 -> message = "Error detected, alphabetical operand detected in UL : " + s + " at position : " + errPos + ".";
    case 4-> message = "Error detected, operator after '(' in UL : " + s + " at position : " + errPos + ".";
    case 5-> message = "Error detected, subsequent operator in UL : " + s + " at position : " + errPos + ".";
    case 6-> message = "Error detected, doesn't find a matching ')' for '(' : " + s + " at position : " + errPos + ".";
    default -> {
      message = "Unknown syntax error occurred.";
    }
  }
  throw new SyntaxErreur(message);
}



  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {
    String toWriteLect = "";
    String toWriteEval = "";
    String toWriteLectPost = "";

    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatSyntaxique.txt";
    }
    Reader reader = new Reader(args[0]);

    DescenteRecursive dr = new DescenteRecursive(reader.toString());
    try {

      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
      System.out.println(toWriteLect);
      toWriteLectPost += "Lecture de l'AST postfix trouve : " + RacineAST.PostLectAST() + "\n";
      System.out.println(toWriteLectPost);
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      Writer w = new Writer(args[1],toWriteLect+toWriteLectPost+toWriteEval); // Ecriture de toWrite
                                                              // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }

}

