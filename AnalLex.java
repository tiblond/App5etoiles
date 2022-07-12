package app5;

/** @author Ahmed Khoumsi */

import app6.type;

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

// Attributs
//  ...
String chaineTotal;
int size;
int position;
enum e_Machine{
  STATE_0,
  STATE_1,
  STATE_2
};


/** Constructeur pour l'initialisation d'attribut(s)
 */
  public AnalLex(String word) {  // arguments possibles
    chaineTotal = word;
    size = word.length();
    position = 0;
  }


/** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
 */
  public boolean resteTerminal( ) {
    if(position < size-1){
      return true;
    }
    return false;
  }
  
  
/** prochainTerminal() retourne le prochain terminal
      Cette methode est une implementation d'un AEF
 */  
  public Terminal prochainTerminal() throws SyntaxErreur {
    e_Machine etat;
    String s = "";
    while(position < size-1)
    {

    }
    if(chaineTotal.charAt(position) == ('+' | '-' | '*' | '/' | '(' | ')')) {
      s += chaineTotal.charAt(position);
      position++;
      return new Terminal(s, type.OPERATEUR, 0);
    }
    else if(Character.isUpperCase(chaineTotal.charAt(position)))
    {
      s += chaineTotal.charAt(position);
      position++;
      while(Character.isLetter(chaineTotal.charAt(position)) | chaineTotal.charAt(position) == '_')
      {
        if(chaineTotal.charAt(position-1) == '_' && chaineTotal.charAt(position) == '_')
        {
          s+= chaineTotal.charAt(position);
          ErreurLex(s,position,1);
          return null;
        }
        s += chaineTotal.charAt(position);
        position++;
      }
      if(chaineTotal.charAt(position) == ('+' | '-' | '*' | '/' | '(' | ')')){
        if(chaineTotal.charAt(position-1) == '_')
        {
          s+= chaineTotal.charAt(position);
          ErreurLex(s,position,3);
        }
        return new Terminal(s,type.OPERANTE,0 );
      }
      if(Character.isDigit(chaineTotal.charAt(position)))
      {
        s+= chaineTotal.charAt(position);
        ErreurLex(s,position,2);
        return null;
      }
      else
      {
        s+= chaineTotal.charAt(position);
        ErreurLex(s,position,5);
        return null;
      }
    }
    else if(Character.isLowerCase(chaineTotal.charAt(position)) && chaineTotal.charAt(position) == '_')
    {
      s+= chaineTotal.charAt(position);
      ErreurLex(s,position,4);
      return null;
    }
    else {
      s += chaineTotal.charAt(position);
      ErreurLex(s,position,5);
      return null;
    }
  }

 
/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s,int errPos,int errCode) throws SyntaxErreur{
    String message = "";
    switch (errCode) {
      case 1:
        message = "Error detected get two '_' consecutive in the lexical unit : " + s + " at position : " + errPos+".";
        break;
      case 2:
        message = "Error detected get digit in the lexical unit : " + s + " at position : " + errPos+".";
        break;
      case 3:
        message = "Error detected get '_' at the end of a lexical unit : " + s + " at position : " + errPos+".";
        break;
      case 4:
        message = "Error detected no capitalize letter at the begginning of the lexical unit : " + s + " at position : " + errPos+".";
        break;
      case 5:
        message = "Error unknown symbol detected in the lexical unit : " + s + " at position : " + errPos+".";
        break;
      default:
        break;
    }
    throw new SyntaxErreur(message);
  }

  
  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
    args = new String [2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite +=t.chaine + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
