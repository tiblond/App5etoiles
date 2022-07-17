package app5;

/** @author Ahmed Khoumsi */

/**
 * Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

    // Attributs
    // ...
    String chaineTotal;
    int size;
    state_Lex actualState;
    state_Lex previousState;
    int position;

    enum state_Lex {
        STATE_0,
        STATE_1,
        STATE_2,
        STATE_3,
        STATE_4,
        STATE_5,
        STATE_6,
        STATE_7,
        STATE_8,
        STATE_9
    };

    /**
     * Constructeur pour l'initialisation d'attribut(s)
     */
    public AnalLex(String word) { // arguments possibles
        chaineTotal = word;
        size = word.length();
        position = 0;
        actualState = state_Lex.STATE_0;
        previousState = state_Lex.STATE_0;
    }

    /**
     * resteTerminal() retourne :
     * false si tous les terminaux de l'expression arithmetique ont ete retournes
     * true s'il reste encore au moins un terminal qui n'a pas ete retourne
     */
    public boolean resteTerminal() {
        if (position < size) {
            return true;
        }
        return false;
    }

    /**
     * prochainTerminal() retourne le prochain terminal
     * Cette methode est une implementation d'un AEF
     */
    public Terminal prochainTerminal() throws LexicalErreur {
        actualState = state_Lex.STATE_0;
        previousState = state_Lex.STATE_0;
        String s = "";
        Float value = null;

        stateMachine();
        s += chaineTotal.charAt(position);
        position++;
        if (actualState == state_Lex.STATE_1) {
            return new Terminal(s, type.OPERATEURB, null, position - 1);
        } else if (actualState == state_Lex.STATE_2) {
            return new Terminal(s, type.OPERATEURC, null, position - 1);
        } else if (actualState == state_Lex.STATE_3) {
            return new Terminal(s, type.OPERATEURO, null, position - 1);
        } else if (actualState == state_Lex.STATE_4) {
            return new Terminal(s, type.OPERATEURF, null, position - 1);
        } else if (actualState == state_Lex.STATE_5) {
            stateMachine();
            while (actualState == state_Lex.STATE_5 | actualState == state_Lex.STATE_6) {
                if (position == size) {
                    return new Terminal(s, type.OPERANTE, null, position - 1);
                }
                if (actualState == state_Lex.STATE_6 && previousState == state_Lex.STATE_6) {
                    s += chaineTotal.charAt(position);
                    ErreurLex(s, position, 1);
                }
                s += chaineTotal.charAt(position);
                position++;
                if(position < size){
                    stateMachine();
                }
            }

            if (actualState == state_Lex.STATE_9) {
                if (previousState == state_Lex.STATE_6) {
                    s += chaineTotal.charAt(position);
                    ErreurLex(s, position, 3);
                }
                return new Terminal(s, type.OPERANTE, null, position - 1);
            }

            if (actualState == state_Lex.STATE_7) {
                s += chaineTotal.charAt(position);
                ErreurLex(s, position, 2);
            } else if (actualState == state_Lex.STATE_8) {
                s += chaineTotal.charAt(position);
                ErreurLex(s, position, 5);
            }
        }

        else if (actualState == state_Lex.STATE_7) {
            stateMachine();
            while (actualState == state_Lex.STATE_7) {
                if (position == size) {
                    value = Float.parseFloat(s);
                    return new Terminal(s, type.OPERANTENUM, value, position - 1);
                }
                s += chaineTotal.charAt(position);
                position++;
                if(position < size){
                    stateMachine();
                }
            }

            if (actualState == state_Lex.STATE_9) {
                value = Float.parseFloat(s);
                return new Terminal(s, type.OPERANTENUM, value, position - 1);
            } else {
                s += chaineTotal.charAt(position);
                ErreurLex(s, position, 6);
            }

        } else if (actualState == state_Lex.STATE_9) {
            s += chaineTotal.charAt(position);
            ErreurLex(s, position, 4);
        } else if (actualState == state_Lex.STATE_8) {
            s += chaineTotal.charAt(position);
            ErreurLex(s, position, 5);
        }
        return null;
    }

    private void stateMachine() throws LexicalErreur {
        switch (actualState) {
            case STATE_0:
                if (chaineTotal.charAt(position) == '+' | chaineTotal.charAt(position) == '-') {
                    actualState = state_Lex.STATE_1;
                } else if (chaineTotal.charAt(position) == '*' | chaineTotal.charAt(position) == '/') {
                    actualState = state_Lex.STATE_2;
                } else if (chaineTotal.charAt(position) == '(') {
                    actualState = state_Lex.STATE_3;
                } else if (chaineTotal.charAt(position) == ')') {
                    actualState = state_Lex.STATE_4;
                } else if (Character.isUpperCase(chaineTotal.charAt(position))) {
                    actualState = state_Lex.STATE_5;
                } else if (Character.isDigit(chaineTotal.charAt(position))) {
                    actualState = state_Lex.STATE_7;
                } else if (Character.isLowerCase(chaineTotal.charAt(position)) | chaineTotal.charAt(position) == '_') {

                    actualState = state_Lex.STATE_9;

                } else {
                    actualState = state_Lex.STATE_8;
                }
                break;
            case STATE_1:
                break;
            case STATE_2:
                break;
            case STATE_3:
                break;
            case STATE_4:
                break;
            case STATE_5:
                if (Character.isLetter(chaineTotal.charAt(position))) {
                    previousState = actualState;
                    actualState = state_Lex.STATE_5;
                } else if (Character.isDigit(chaineTotal.charAt(position))) {
                    previousState = actualState;
                    actualState = state_Lex.STATE_7;
                } else if (chaineTotal.charAt(position) == '_') {
                    previousState = actualState;
                    actualState = state_Lex.STATE_6;
                } else if (chaineTotal.charAt(position) == '+' | chaineTotal.charAt(position) == '-'
                        | chaineTotal.charAt(position) == '*'
                        | chaineTotal.charAt(position) == '/' | chaineTotal.charAt(position) == '('
                        | chaineTotal.charAt(position) == ')') {
                    previousState = actualState;
                    actualState = state_Lex.STATE_9;
                } else {
                    previousState = actualState;
                    actualState = state_Lex.STATE_8;
                }
                break;
            case STATE_6:
                if (Character.isLetter(chaineTotal.charAt(position))) {
                    previousState = actualState;
                    actualState = state_Lex.STATE_5;
                }
                else if (Character.isDigit(chaineTotal.charAt(position))) {
                    previousState = actualState;
                    actualState = state_Lex.STATE_7;
                }
                else if (chaineTotal.charAt(position) == '+' | chaineTotal.charAt(position) == '-'
                        | chaineTotal.charAt(position) == '*'
                        | chaineTotal.charAt(position) == '/' | chaineTotal.charAt(position) == '('
                        | chaineTotal.charAt(position) == ')') {
                    previousState = actualState;
                    actualState = state_Lex.STATE_9;
                }
                else if (chaineTotal.charAt(position) == '_') {
                    previousState = actualState;
                    actualState = state_Lex.STATE_6;
                } else {
                    previousState = actualState;
                    actualState = state_Lex.STATE_8;
                }
                break;
            case STATE_7:
                if (Character.isDigit(chaineTotal.charAt(position))) {
                    break;
                }
                else if (chaineTotal.charAt(position) == '+' | chaineTotal.charAt(position) == '-' | chaineTotal.charAt(position) == '*' | chaineTotal.charAt(position) == '/' | chaineTotal.charAt(position) == '(' | chaineTotal.charAt(position) == ')') {
                    actualState = state_Lex.STATE_9;
                }
                else {
                    actualState = state_Lex.STATE_8;
                }
                break;
            case STATE_8: // erreur
                break;
            case STATE_9: // good
                break;
        }
    }

    /**
     * ErreurLex() envoie un message d'erreur lexicale
     */
    public void ErreurLex(String s, int errPos, int errCode) throws LexicalErreur {
        String message = "";
        switch (errCode) {
            case 1 -> message = "Error detected, get two '_' consecutive in the lexical unit : " + s + " at position : "
                    + errPos + ".";
            case 2 ->
                message = "Error detected, get digit in the lexical unit : " + s + " at position : " + errPos + ".";
            case 3 ->
                message = "Error detected, get '_' at the end of a lexical unit : " + s + " at position : " + errPos
                        + ".";
            case 4 -> message = "Error detected, no capitalize letter at the begginning of the lexical unit : " + s
                    + " at position : " + errPos + ".";
            case 5 -> message = "Error detected, unknown symbol detected in the lexical unit : " + s + " at position : "
                    + errPos + ".";
            case 6 -> message = "Error detected, current char is not a digit: " + s + " at position : " + errPos + ".";
            default -> {
                message = "Unknown lexical error occurred.";
            }
        }
        throw new LexicalErreur(message);
    }

    // Methode principale a lancer pour tester l'analyseur lexical
    public static void main(String[] args) {
        String toWrite = "";
        System.out.println("Debut d'analyse lexicale");
        if (args.length == 0) {
            args = new String[2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";
        }
        Reader r = new Reader(args[0]);

        AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

        // Execution de l'analyseur lexical
        Terminal t = null;
        while (lexical.resteTerminal()) {
            try {
                t = lexical.prochainTerminal();
                toWrite += t.chaine + "\n";
            } catch (LexicalErreur e) {
                toWrite += e.getMessage();
                break;
            }
            // toWrite contient le resultat
        } // d'analyse lexicale
        System.out.println(toWrite); // Ecriture de toWrite sur la console
        Writer w = new Writer(args[1], toWrite); // Ecriture de toWrite dans fichier args[1]
        System.out.println("Fin d'analyse lexicale");
    }
}
