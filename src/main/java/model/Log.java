package model;

public class Log {
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String RESET = "\u001B[0m";


    public static String getHora(){
        long millis = System.currentTimeMillis();
        int hours   = (int) ((millis / (1000*60*60)) % 24);
        int minutes = (int) ((millis / (1000*60)) % 60);
        int seconds = (int) (millis / 1000) % 60 ;
        return (hours+6) + ":"+ minutes+":"+seconds + " : " + YELLOW +"[LOG]"+ RESET+" : ";
    }
    public static void info(String msg){
        System.out.println(getHora() + CYAN + "[INFO]"+ RESET + " : " + msg);
    }

    public static void warn(String msg){
        System.out.println(getHora() + YELLOW + "[WARM]"+ RESET + " : " + msg);
    }


    public static void error(String msg){
        System.out.println(getHora() + RED + "[ERROR]"+ RESET + " : " + msg);
    }

    public static void debug(String msg){
        System.out.println(getHora() + PURPLE + "[DEBUG]"+ RESET + " : "  + msg);
    }

    public static void success(String msg){
        System.out.println(getHora() + GREEN + "[SUCCESS]"+ RESET + " : " + msg);
    }

    public static void trace(String msg){
        System.out.println(getHora() + BLUE + "[TRACE]"+ RESET + " : " + msg);
    }

}
