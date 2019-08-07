import static cTools.KernelWrapper.*;
import java.util.Scanner;

class shell_versuch {

    /*
     * Was nicht geht:
     * - Leerzeichen vor / nach <>|&& werden nicht beachtet, GEHT NICHT: ls > /home/ira/test.txt GEHT: ls>/home/ira/test.txt
     * - Anführungszeichen / Maskierungszeichen werden nicht beachtet, GEHT NICHT: ls>"mein dateiname" ODER: ls>mein\ dateiname
     */   
    
     // Pfade zum suchen / ausführen von Dateien, Reihenfolge der Pfade entspricht Priorität
    public static final String[] INCLUDE_PATH = {"/usr/local/bin/", "/bin/", "/usr/bin/"};
    
     // Befehl Separator
    public static final char SEPARATOR = ' ';
    
    public static void main(String[] args) {
        System.out.println("$ test shell (heute mehr 1337) $");  
        String input = "";
        
        // Shell wird mit Parameter geöffnet
        if (args.length > 0) {            
            for (int i = 0; i < args.length; i++) {
                input += args[i];
                if (i != args.length -1)
                    input += " ";
            }
            parseInput(input);
         
        // Shell wird ohne Parameter geöffnet, wartet auf Eingabe
        } else {
            Scanner scanner = new Scanner(System.in);;
            boolean exit = false;
            do {

                System.out.print("$ "); // get_current_dir_name() crasht immer )-:
                input = scanner.nextLine();
                
                // Shell bei "exit" Eingabe beenden
                if (input.equals("exit")) {
                    exit = true;
                
                // andere Eingaben verarbeiten
                } else {
                    parseInput(input);
                }            
            } while (exit == false);
            scanner.close();
        }
    }
    
    // interpretiere | < > &&, führe Teil Befehle separiert von diesen aus, jeder Befehl hat neuen Thread
    public static void parseInput(String input) {        
        String cmd = "";
        char currentChar;     
        int[] status = {0};                     
        for (int i = 0; i < input.length(); i++) {        
            currentChar = input.charAt(i);           
            if (currentChar == '|') { 
                execPipe(cmd, nextInput(input, i));
                return;
            } else if (currentChar == '>') {   
                execToFile(cmd, nextInput(input, i));
                return;
            } else if (currentChar == '<') {  
                execFromFile(cmd, nextInput(input, i));
                return;
            } else if (currentChar == '&' && i + 1 < input.length() && input.charAt(i+1) == '&') {
                status = exec(cmd);
                if (status[0] == 0)
                    parseInput(nextInput(input, i + 1));
                return;
            } else {            
                cmd += currentChar;
            }
        }            
        if (cmd.length() > 0) {
            exec(cmd);
        }
     }
     
     public static int[] exec(String cmd) {
        int[] status = {0};
        int pid = fork(); 
        if (pid == 0) {  
            execv(addIncludePath(parseCommand(cmd)), parseArguments(cmd));
            exit(1);
        } else {
            waitpid(pid, status, 0); 
        }  
        return status;
     }
     
    public static int[] execPipe(String cmd, String nextInput) {
        int[] status = {0};
        int[] pipefd = new int[2];
        pipe(pipefd); 
        int pid = fork(); 
        if (pid == 0) {                
            dup2(pipefd[STDOUT_FILENO], STDOUT_FILENO);
            close(pipefd[STDIN_FILENO]);
            close(pipefd[STDOUT_FILENO]);
            execv(addIncludePath(parseCommand(cmd)), parseArguments(cmd));
            exit(1);                        
        } else {               
            dup2(pipefd[STDIN_FILENO], STDIN_FILENO);
            close(pipefd[STDOUT_FILENO]);
            close(pipefd[STDIN_FILENO]);
            parseInput(nextInput);
            waitpid(pid, status, 0); 
            dup2(STDOUT_FILENO, STDIN_FILENO);
        } 
        return status;
     }

     public static int[] execToFile(String cmd, String path) {
        int[] status = {0};
        int pid = fork(); 
        if (pid == 0) {
            int fdf = open(path, O_CREAT | O_WRONLY);
            dup2(fdf, STDOUT_FILENO);
            //dup2(fdf, STDERR_FILENO); wir ignorieren stderr
            close(fdf);
            execv(addIncludePath(parseCommand(cmd)), parseArguments(cmd));
            exit(1);
        } else  {
            waitpid(pid, status, 0); 
        }   
        return status;
     }
     
     public static int[] execFromFile(String cmd, String path) {
        int[] status = {0};
        int pid = fork(); 
        if (pid == 0) { 
            int fdf = open(path, O_RDONLY); // vielleicht r/w besser?
            dup2(fdf, STDIN_FILENO);
            close(fdf);
            execv(addIncludePath(parseCommand(cmd)), parseArguments(cmd));
            exit(1);
        } else  {
            waitpid(pid, status, 0); 
        }
        return status;
     }
     
     // ermittle nächsten Teilstring
     public static String nextInput(String input, int current) {
        return input.substring(current + 1);
     }
     
     // ermittle Befehl: Teil-String bis zum ersten Leerzeichen
     public static String parseCommand(String input) {
        int offset = input.indexOf(SEPARATOR);
        if (offset == -1)
            return input;
        return input.substring(0, offset);
     }
     
     // parse input String in Array: Wörter getrennt von Leerzeichen
     public static String[] parseArguments(String input) {     
        int i = input.indexOf(' ');
        int len = 0;
        int execArgsCountOffset = 0;
        String[] execArgs;
        if (i > -1) {
            for (int j = i; j < input.length(); j++) // Zähle Leerzeichen nach erstem Leerzeichen, für String Array größe
                if (input.charAt(j) == SEPARATOR)
                    ++len;              
            execArgs = new String[len+1];
            char currentChar; 
            String part = "";
            for (int k = 0; k < input.length(); k++) {
                currentChar = input.charAt(k);
                if (currentChar == SEPARATOR) {
                    execArgs[execArgsCountOffset] = part;
                    ++execArgsCountOffset;
                    part = "";
                } else {
                    part += currentChar;
                }
            }
            if (part.length() > 0)
                execArgs[execArgsCountOffset] = part;
        } else {
            execArgs = new String[len+1];
            execArgs[execArgsCountOffset] = input;
        }        
        int slash = execArgs[0].lastIndexOf('/'); // nur Prozess Name als erstes Argument, auch wenn /bin/ls
        if (slash != -1) { 
            execArgs[0] = execArgs[0].substring(execArgs[0].indexOf('/') + 1); 
        }
        return execArgs;
     }

    
    // prüfe ob ausführbare Datei in "einbinde" Pfaden
     public static String addIncludePath(String execCommand) {
        if ((execCommand.length() > 0 && execCommand.charAt(0) != '/') || (execCommand.length() > 1 && execCommand.charAt(0) != '.' && execCommand.charAt(1) != '/')) { // kein auto Einbinden bei ./ oder / am Pfad Anfang
            for (int i = 0; i < INCLUDE_PATH.length; i++) {        
                String[] files = readdir(INCLUDE_PATH[i]);
                for (int k = 0; k < files.length; k++)
                    if (files[k].equals(execCommand))
                        execCommand = INCLUDE_PATH[i] + execCommand;
            }
        }
        return execCommand;
     }  
}
 
