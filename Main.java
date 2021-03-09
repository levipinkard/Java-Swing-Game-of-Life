import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int xSize;
        int ySize;
        String filePath;
        if (args.length < 2) {
        	filePath = System.getProperty("user.dir") + "/.gameCahse";
        	xSize = 200;
        	ySize = 200;
        } else if (args.length < 3) {
            filePath = System.getProperty("user.dir") + "/.gameCache";
            xSize = Integer.parseInt(args[0]);
            ySize = Integer.parseInt(args[1]);
        } else {
            filePath = args[0];
            xSize = Integer.parseInt(args[1]);
            ySize = Integer.parseInt(args[2]);
        }
        boolean[][] board = new boolean[xSize][ySize];
        boolean[][] copyBoard = new boolean[xSize][ySize];
        Gen generator = new Gen(filePath,xSize,ySize);
        generator.run();
        BufferedReader br = null;
        try {
                File file = new File(filePath);
                br = new BufferedReader(new FileReader(file));
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e);
        }
        String line;

        for (int x = 0; x < xSize; x++) {
            try{
                line = br.readLine();
                for (int y = 0; y < ySize; y++) {
                    if (line.charAt(y) == '1'){
                        board[x][y] = true;
                    }  else board[x][y] = false;
                }
            } catch (IOException e) {

            }
        }
        try {
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                copyBoard[x][y] = board[x][y];
            }
        }
        GoLProcessor processor = new GoLProcessor(xSize,ySize,board,copyBoard,filePath);
        while (true) {
            processor.tickRun();
        }
    }
}