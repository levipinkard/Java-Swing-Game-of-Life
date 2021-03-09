import java.io.*;
import java.util.Random;
public class Gen {
	private Random generator;
	private PrintWriter out;
	private int xSize;
	private int ySize;
	private double interval;
	private File file;
	private FileWriter fileWriter;
	public Gen(String filePath, int xSize, int ySize) {
		out = null;	
		this.xSize = xSize;
		this.ySize = ySize;
		this.interval = .5;
		file = new File(filePath);
		file.delete();

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		}catch (IOException e) {
		}
		generator = new Random(System.currentTimeMillis());
	}
	public Gen(String filePath, int xSize, int ySize, double interval) {
		out = null;	
		this.xSize = xSize;
		this.ySize = ySize;
		this.interval = interval;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
		}catch (IOException e) {
		}
		generator = new Random(System.currentTimeMillis());
	}
	public void setInterval(double interval){
		this.interval = interval;
	}
	public void run() {
		generator.setSeed(System.currentTimeMillis());
		file.delete();
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int x = 0; x < xSize; x ++) {
			for (int y = 0; y < ySize; y++) {
				if (generator.nextDouble() >interval) {
					out.write('1');
				} else out.write('0');
			}
			out.write("\n");
			
		}
		out.flush();
	}
}
