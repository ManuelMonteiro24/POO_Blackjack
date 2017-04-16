package src;

import java.io.File;

public class Main{

  public static boolean isInteger(String s){
    try{
      Integer.parseInt(s);
    } catch(NumberFormatException e) {
      return false;
    } catch(NullPointerException e){
      return false;
    }

    return true;
  }

  public static boolean checkFile(String s){
    File f = new File(s);
    if(f.exists() && !f.isDirectory() && f.canRead())
      return true;
    else
      return false;
  }

  public static void main(String[] args) {

    if(args.length == 0){
      System.out.println("Usage: -mode -parameters");
      System.exit(1);
    }

    if(args[0].equals("-i")){
      //interactive mode
      if(args.length != 2 || !(isInteger(args[1]))){
        System.out.println("Interactive mode usage: -i credit<num>");
        System.exit(1);
      }

      //call interactive mode...

    }else if(args[0].equals("-d")){
      //debug mode
      if(args.length != 4 || !(isInteger(args[1]))){
        System.out.println("Debug mode usage: -d credit<num> cmd-file card-file");
        System.exit(1);
      }

      //check if files exist ??? not working ????
      if(!(checkFile(args[2]) && checkFile(args[3]))){
        System.out.println("Error on opening files");
        System.exit(1);
      }

      //call debug mode...

    }else if(args[0].equals("-s")){
      //simulation mode
      if(args.length != 4 || !(isInteger(args[1])) || !(isInteger(args[2])) || !(isInteger(args[3]))){
        System.out.println("Simulation mode usage: -s credit<num> bet<num> nbdeals<num>");
        System.exit(1);
      }

      //call simulation mode...

    }else{
      System.out.println("Usage: -mode -parameters\nAvailable modes: interactive -i debug -d simulation -s");
      System.exit(1);
    }
  }
}
