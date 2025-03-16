import java.awt.image.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.*;

// --------------------------------------------------------------- //
class Color {
  public Color(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }
  public int r() { return r; }
  public int g() { return g; }
  public int b() { return b; }

  private int r = 0;
  private int g = 0;
  private int b = 0;
}

// --------------------------------------------------------------- //
public class Generator {
  private static String fwe = "failed with exception";

  // --------------------------------------------------------------- //
  private static BufferedReader openFile() {
    Logger.print("open file: ", false);
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("data.dat"));
    } catch (IOException e) {
      Logger.print("failed");
      e.printStackTrace();
      return null;
    }
    Logger.print("successful");
    return reader;
  }

  // --------------------------------------------------------------- //
  private static boolean checkFileType(BufferedReader reader) {
    Logger.print("check filetype: ", false);
    try {
      String line = reader.readLine();
      if (line.compareTo("indeximage") != 0) {
        Logger.print("failed");
        return false;
      }
    } catch (IOException e) {
      Logger.print(fwe);
      return false;
    }
    Logger.print("succesful");
    return true;
  }

  // --------------------------------------------------------------- //
  private static int loadWidthHeight(BufferedReader reader) {
    Logger.print("reading image dimension: ", false);
    int value = -1;
    try {
      String line = reader.readLine();
      value = Integer.parseInt(line);
    } catch (IOException e) {
      Logger.print(fwe);
      return -1;
    }
    Logger.print("succesful");
    return value;
  }

  // --------------------------------------------------------------- //
  private static ArrayList<Color> loadColorPalette(BufferedReader reader) {
    Logger.print("color palette: ", false);
    ArrayList<Color> palette = new ArrayList<>();
    int count = -1;
    try {
      String line = reader.readLine();
      count = Integer.parseInt(line);
      for (int i = 0; i < count; ++i) {
        line = reader.readLine();
        String[] cvals = line.split(" ");
        int r = Integer.parseInt(cvals[0]);
        int g = Integer.parseInt(cvals[1]);
        int b = Integer.parseInt(cvals[2]);
        palette.add(new Color(r, g, b));
      }
    } catch (IOException e) {
      Logger.print(fwe);
      return new ArrayList<>();
    }
    Logger.print("succesful");
    Logger.print("read " + count + " colors");
    return palette;
  }

  // --------------------------------------------------------------- //
  private static void createImage(BufferedReader reader, int width, int height,
                                  ArrayList<Color> palette) {
    Logger.print("save image: ", false);
    try {
      BufferedImage img =
          new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      // ---
      for (int y = 0; y < height; ++y) {
        String line = reader.readLine();
        String[] idxs = line.split(" ");
        for (int x = 0; x < width; ++x) {
          int idx = Integer.parseInt(idxs[x]);
          Color col = palette.get(idx);
          int val = (col.r() << 16) | (col.g() << 8) | col.b();
          img.setRGB(x, y, val);
        }
      }
      // ---
      File f = new File("image.png");
      ImageIO.write(img, "PNG", f);
    } catch (Exception e) {
      Logger.print(fwe);
    }
    Logger.print("successful");
  }

  // --------------------------------------------------------------- //
  private static void generateImage() {
    BufferedReader reader = openFile();
    try {
      // ---
      if (!checkFileType(reader)) {
        return;
      }
      // ---
      int width = loadWidthHeight(reader);
      int height = loadWidthHeight(reader);
      ArrayList<Color> palette = loadColorPalette(reader);
      createImage(reader, width, height, palette);
      // ---
      reader.close();
    } catch (IOException e) {
      Logger.print(fwe);
    }
  }

  // --------------------------------------------------------------- //
  public static void main(String[] args) {
    Logger.printStartInfo();
    generateImage();
  }
}