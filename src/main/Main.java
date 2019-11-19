package main; 

public class Main {
    /**
     * Execute une batterie de tests sur l'ensemble du fil rouge
     * @param args
     */
    public static void execExamples(String[] args) {
        examples.Main.main(args);
    }
    public static void execRepresentations(String[] args) {
        representations.Main.main(args);
    }
    public static void execPPC(String[] args) {
        ppc.Main.main(args);
    }
    public static void execDatamining(String[] args) {
        datamining.Main.main(args);
    }
    public static void main(String[] args) {
        System.out.println("-----PARTIE EXAMPLES-----");
        execExamples(args);
        System.out.println("--PARTIE REPRESENTATIONS-");
        execRepresentations(args);
        System.out.println("-------PARTIE PPC--------");
        execPPC(args);
        System.out.println("----PARTIE DATAMINING----");
        execDatamining(args);
    }
}