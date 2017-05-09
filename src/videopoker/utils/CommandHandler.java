package videopoker.utils;

import videopoker.game.InteractiveGame;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;

public class CommandHandler{

    Scanner scanner;
    private static final String[] regexs = {"([^\\d]*?)", "(\\$(\\s*?))", "b(\\s*?)", "b(\\s+([0-5]?(\\s*?)))", "d(\\s*?)", "h\\s*?(\\s+([1-5](\\s*?))){0,6}", "a(\\s*?)", "s(\\s*?)", "q(\\s*?)"};
    private Pattern[] patterns;
    private String command;

    public CommandHandler(InputStream input){
        this.scanner = new Scanner(input);

        patterns = new Pattern[regexs.length];
        for(int i = 0; i < regexs.length; i++)
            patterns[i] = Pattern.compile(regexs[i]);
    }

    public CommandHandler(File cmdFile){
        try {
            this.scanner = new Scanner(cmdFile);
            this.scanner.useDelimiter("\\s+");
        } catch(FileNotFoundException fnfex ){
            fnfex.printStackTrace();
        }

        patterns = new Pattern[regexs.length];
        for(int i = 0; i < regexs.length; i++)
            patterns[i] = Pattern.compile(regexs[i]);

    }

    public String getCommand(Class callerClass){

        if(callerClass.equals(InteractiveGame.class))
            return scanner.nextLine();
        else
            try{
                String buf;
                StringBuilder sb = new StringBuilder("");

                while(true){
                    buf = scanner.next();
                    sb.append(buf);
                    sb.append(" ");
                    if(scanner.hasNext(patterns[0]))
                        break;
                }

                System.out.println("-cmd " + sb.toString());
                return sb.toString();
            } catch(NoSuchElementException nseex) {
                return null;
            }

    }

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