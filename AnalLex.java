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
  public Terminal prochainTerminal() {
    e_Machine etat;
    switch (chaineTotal.charAt(position)) {
      case '+':
        String s = "+";
        return new Terminal(s, type.OPERATEUR, 0);
      case '-':
        s = "-";
        position++;
        return new Terminal(s, type.OPERATEUR, 0);
      case '*':
        s = "*";
        position++;
        return new Terminal(s, type.OPERATEUR, 0);
      case '/':
        s = "/";
        position++;
        return new Terminal(s, type.OPERATEUR, 0);
      case '(':
        s = "(";
        position++;
        return new Terminal(s, type.OPERATEUR, 0);
      case ')':
        s = ")";
        position++;
        return new Terminal(s, type.OPERATEUR, 0);
    }
    if(Character.isUpperCase(chaineTotal.charAt(position)))
    {
      String s ="";
      while(true)
      {
        s += chaineTotal.charAt(position);
        if(Character.isLetter(chaineTotal.charAt(position)) == false)
        {
          return new Terminal(s, type.OPERANTE, 0);
        }
        position++;
      }

      if()
    }

    return null;
  }

 
/** ErreurLex() envoie un message d'erreur lexicale
 */ 
  public void ErreurLex(String s,int erreurPos) throws SyntaxErreur{
     throw new SyntaxErreur(s,erreurPos);
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
