public class Logger {
  // --------------------------------------------------------------- //
  public static void print(String info, boolean new_line) {
    if (new_line) {
      System.out.println(info);
    } else {
      System.out.print(info);
    }
  }

  // --------------------------------------------------------------- //
  public static void print(String info) { print(info, true); }

  // --------------------------------------------------------------- //
  public static void printStartInfo() {
    print("SE & IT-PM: Image generator -- Java Version");
    print("===========================================");
  }
}