package app5;

public class SyntaxErreur extends Exception{

    public SyntaxErreur(String message, int erreurPos){
        super("Error on character : " + message + "position : " + erreurPos);
    }
}