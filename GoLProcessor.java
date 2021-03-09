import java.util.ArrayList;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class GoLProcessor {
    private ArrayList<Dimension> thanosList = new ArrayList<Dimension>();
    private int xSize = 1920;
    private int ySize = 1080;
    private boolean[][] board;
    private boolean[][] copyBoard;
    private PixelWriter pixelWriter;
    private Gen gen;
    private int tickCount;
    private boolean tickProcess;
    private String filePath;
    public GoLProcessor(int xSize, int ySize, boolean[][] board, boolean[][] copyBoard,String filePath) {
        this.xSize = xSize;
        this.ySize = ySize;
        tickCount = 0;
        this.board = board;
        this.copyBoard = copyBoard;
        this.filePath = filePath;
        tickProcess = true;
        gen = new Gen(filePath,xSize,ySize);
        gen.run();
        pixelWriter = new PixelWriter(xSize, ySize, this);
        pixelWriter.run();
    }
    public boolean getState() {
        return tickProcess;
    }
    public void randomize() throws IOException {
        gen.setInterval(.5);
        gen.run();
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
        br.close();
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                copyBoard[x][y] = board[x][y];
            }
        }
	tickCount = 0;
    }
    public void pause(boolean bool) {
        if (bool) tickProcess = false;
        if (!bool) tickProcess = true;
    }
    public void randomize(double interval) throws IOException {
        gen.setInterval(interval);
        gen.run();
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
        br.close();
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                copyBoard[x][y] = board[x][y];
            }
        }
	tickCount = 0;
    }
    public void paintBoard() {
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    if (board[x][y]) {
                        pixelWriter.writePixel(x, y, 255, 255, 255);
                    } else pixelWriter.writePixel(x, y, 0, 0, 0);
                }
            }
        
        pixelWriter.repaint();

    }
    public void snap() {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize;y++) {
                if (board[x][y]) thanosList.add(new Dimension(x,y));
            }
        }
        Collections.shuffle(thanosList);
        for (int x = 0; x < thanosList.size()/2; x++) {
            board[(int) thanosList.get(x).getWidth()][(int) thanosList.get(x).getHeight()] = false;
        }
        for (int x = 0; x < xSize; x++) {
            copyBoard[x] = board[x].clone();
        }
    }
    public void singleTick() {
    	perTick();
        paintBoard();
        for (int x = 0; x < xSize; x++) {
            copyBoard[x] = board[x].clone();
        }
    }
    public void togglePixel(int x, int y) {
        if (board[x][y]) {
            board[x][y] = false;
        } else {
            board[x][y] = true;
        }
        copyBoard[x][y] = board[x][y];
    }
    public void perTick() {

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                int totalSurround = 0;
                if (x != 0 && x!= xSize - 1) {
                    if (copyBoard[x-1][y]) totalSurround++;
                    if (copyBoard[x+1][y]) totalSurround++;

                }
                if (y != 0 && y != ySize -1) {
                    if (copyBoard[x][y-1]) totalSurround++;
                    if (copyBoard[x][y+1]) totalSurround++;
                }
                if ((x != 0 && x!= xSize - 1) && (y != 0 && y != ySize -1)) {
                    if (copyBoard[x-1][y-1]) totalSurround++;
                    if (copyBoard[x-1][y+1]) totalSurround++;
                    if (copyBoard[x+1][y-1]) totalSurround++;
                    if (copyBoard[x+1][y+1]) totalSurround++;
                }
                if (copyBoard[x][y]) {
                    if (totalSurround < 2) {
                        board[x][y] = false;
                    } else if (totalSurround >3) {
                        board[x][y] = false;
                    }
                } else {
                    if (totalSurround == 3) board[x][y] = true;
                }
            }
        }
        //tickCount++;
    }
    public void tickRun() {
        if (tickProcess) {
            //thread1.start();
            //thread2.start();
            //thread3.start();
            tickCount++;
	    singleTick();
            System.out.println(tickCount);
            paintBoard();
            for (int x = 0; x < xSize; x++) {
                copyBoard[x] = board[x].clone();
            }
        } else {
            paintBoard();
        }
    }
}
