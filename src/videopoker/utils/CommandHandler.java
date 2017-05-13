package videopoker.utils;

import videopoker.game.InteractiveGame;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class used to process, retrieve and validate commands for the
 * videopoker game.
 *
 * This class can read commands from the standard input or from a text file.
 * It uses regexs to allow for more flexible valid command inputs.
 * It has methods for command retrieval and command validation according
 * to the regexs specified in @regexs.
 */
public class CommandHandler{

    Scanner scanner;
    private static final String[] regexs = {"([^\\d]*?)", "(\\$(\\s*?))", "b(\\s*?)", "b(\\s+([0-5]?(\\s*?)))", "d(\\s*?)", "h\\s*?(\\s+([1-5](\\s*?))){0,6}", "a(\\s*?)", "s(\\s*?)", "q(\\s*?)"};
    private Pattern[] patterns;
    private String command;

    /**
     * CommandHandler constructor that instanciates this class with the ability to
     * read from the received InputStream @input. This is used by the InteractiveGame
     * class that needs to read the commands from the System.in standard input.
     *
     * @param input InputStream from where the commands will be read.
     */
    public CommandHandler(InputStream input){
        this.scanner = new Scanner(input);

        patterns = new Pattern[regexs.length];
        for(int i = 0; i < regexs.length; i++)
            patterns[i] = Pattern.compile(regexs[i]);
    }

    /**
     * CommandHandler constructor that instanciates this class with the ability to
     * read from the file referenced by @cmdFile. This is used by the DebugGame class
     * that requires commands to read from a file.
     *
     * @param cmdFile File instance referencing the file with the commands to be used.
     */
    public CommandHandler(File cmdFile){
        try {
            this.scanner = new Scanner(cmdFile);
            this.scanner.useDelimiter("(\\s+)");
        } catch(FileNotFoundException fnfex ){
            fnfex.printStackTrace();
        }

        patterns = new Pattern[regexs.length];
        for(int i = 0; i < regexs.length; i++)
            patterns[i] = Pattern.compile(regexs[i]);

    }

    /**
     * Retrieve command from input. The input from which the commands will be read can be
     * different depending on the constructor used.
     * The class of the object from which this method is being called is necessary because
     * it will determine how the commands will be read.
     *
     * @param callerClass the class of the object from which this method is being called.
     * @return command
     */
    public String getCommand(Class callerClass){

        if(callerClass.equals(InteractiveGame.class))
            return scanner.nextLine();
        else {
            String buf = new String("");
            StringBuilder sb = new StringBuilder("");
            try {
                while (true) {
                    //if(scanner.hasNext(Pattern.compile("")))
                    buf = scanner.next();
                    sb.append(buf);
                    sb.append(" ");
                    if (scanner.hasNext(patterns[0])) {
                        break;
                    }
                }

                System.out.println("-cmd " + sb.toString());
                return sb.toString();
            } catch (NoSuchElementException nseex) {
                return sb.toString().equals("") ? null : sb.toString();
            }

        }
    }

    /**
     * Command validation through the regular expressions @regexs. It tries to match the @cmd
     * with any of the regexs from @regexs. If a match is found, the index of the regex from @regexs
     * that the command was matched against, is returned. Else and invalid index is returned.
     *
     * @param cmd String with the command line.
     * @return indentification number for the command @cmd according to which @regexs element it was matched against.
     */
    public int validateCommand(String cmd){
        int match = -1;
        boolean found = false;
        for(Pattern pattern : patterns)
            if(++match > 0 && pattern.matcher(cmd).matches()){
                found = true;
                break;
            }

        if(!found)
            return ++match;

        return match;
    }

}
