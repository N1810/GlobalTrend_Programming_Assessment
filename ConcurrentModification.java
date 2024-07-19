import java.util.ArrayList;
import java.util.Iterator;

public class ConcurrentModification {

  public static void main(String[] args) {
    ArrayList<String> names = new ArrayList<>();
    names.add("In");
    names.add("Neeraj");
    names.add("Kumar");

    Iterator<String> it = names.iterator();

    while (it.hasNext()) {
      String name = it.next();
      System.out.println(name);

     
      if (name.equals("Neeraj")) {
        names.remove("Kumar"); 
      }
    }
  }
}
