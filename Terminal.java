package app5;

/** @author Ahmed Khoumsi */

;
public class Terminal {


// Constantes et attributs
//  ....

String chaine;
type typeUL;
Float value;
int position;


/** Un ou deux constructeurs (ou plus, si vous voulez)
  *   pour l'initalisation d'attributs 
 */	
  public Terminal(String el, type blabla, Float val,int pos) {   // arguments possibles
    chaine = el;
    typeUL = blabla;
    value = val;
    position = pos;
  }
  public String printTerminal(){
    if (typeUL == type.OPERANTE)
    {
      return "Identifier" + chaine;
    }
    else if (typeUL == type.OPERANTENUM){
      return "Number" + chaine;
    }
    else{
      return "Operator" + chaine;
    }
  }

}
