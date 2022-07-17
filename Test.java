package app5;


public class Test {
    public static void testSyntax(String el,int i,String wFile){
        String toWriteLect = "";
        String toWriteEval = "";
        String toWriteLectPost = "";

        System.out.println("Debut d'analyse syntaxique "+ i);

        DescenteRecursive dr = new DescenteRecursive(el);
        try {

            ElemAST RacineAST = dr.AnalSynt();
            toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST();
            System.out.println(toWriteLect);
            toWriteLectPost += "Lecture de l'AST postfix trouve : " + RacineAST.PostLectAST();
            System.out.println(toWriteLectPost);
            toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST();
            System.out.println(toWriteEval);
            Writer w = new Writer(wFile,toWriteLect+toWriteLectPost+toWriteEval); // Ecriture de toWrite
            // dans fichier args[1]
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            //System.exit(51);
        }
        System.out.println("Analyse syntaxique terminee "+ i + "\n");
    }
    public static void testLexicale(String el,int i,String wFile){
        StringBuilder toWrite = new StringBuilder();
        System.out.println("Debut d'analyse lexicale " + i);
        AnalLex lexical = new AnalLex(el); // Creation de l'analyseur lexical

        // Execution de l'analyseur lexical
        Terminal t = null;

        while (lexical.resteTerminal()) {
            try {
                t = lexical.prochainTerminal();
                toWrite.append(t.printTerminal());
            } catch (LexicalErreur e) {
                toWrite.append(e.getMessage());
                break;
            }
            // toWrite contient le resultat
        } // d'analyse lexicale
        System.out.println(toWrite); // Ecriture de toWrite sur la console
        Writer w = new Writer(wFile, toWrite.toString()); // Ecriture de toWrite dans fichier args[1]
        System.out.println("Fin d'analyse lexicale " + i +"\n");
    }
}
