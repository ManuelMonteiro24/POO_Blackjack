package videopoker;

import videopoker.game.*;
import videopoker.gui.VideopokerGui;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

import java.awt.EventQueue;
import java.awt.Window;

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

        Game game = null;
        /*Only used for simulation mode*/
        int nbDeals = 0;
        int roundCounter = 0;

        if (args[0].equals("-g")) {
            //Graphic interactive mode
            if (args.length != 1) {
                System.out.println("Graphic Interactive mode usage: -g");
                System.exit(1);
            }

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    //check exception???
                    VideopokerGui frame = new VideopokerGui();
                    frame.setVisible(true);
                }
            });

        } else if(args[0].equals("-i")){
            //interactive mode
            if(args.length != 2 || !(isInteger(args[1]))){
                System.out.println("Interactive mode usage: -i credit<num>");
                System.exit(1);
            }

            game =  new InteractiveGame(Integer.parseInt(args[1]));

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
            game =  new DebugGame(Integer.parseInt(args[1]), args[2], args[3]);

        } else if(args[0].equals("-s")){
            //simulation mode
            if(args.length != 4 || !(isInteger(args[1])) || !(isInteger(args[2])) || !(isInteger(args[3]))){
                System.out.println("Simulation mode usage: -s credit<num> bet<num> nbdeals<num>");
                System.exit(1);
            }

            int bet = Integer.parseInt(args[2]);
            if(bet < 1 || bet > 5){
                System.out.println("invalid betting amount\nexiting..");
                System.exit(-1);
            }

            nbDeals = Integer.parseInt(args[3]);
            game = new SimulationGame(Integer.parseInt(args[1]), bet, nbDeals);
            roundCounter = 0;

        } else{
            System.out.println("Usage: -mode -parameters\nAvailable modes: interactive -i debug -d simulation -s");
            System.exit(1);
        }


        if(game != null) {
            while(true) {
                if(game instanceof SimulationGame && roundCounter++ == nbDeals){
                    System.out.println("round " + roundCounter);
                    break;
                }
                game.betStage();
                game.dealStage();
                game.holdStage();
                game.evaluationStage();

            }
        }
    }
}