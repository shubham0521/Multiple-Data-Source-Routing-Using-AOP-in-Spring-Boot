package database.dynamic.routing.dynamic.routing.context;

/**
 * @author shubhamkumar
 */
public enum DatabaseEnvironment {
  UPDATABLE(1),
  READONLY(2);

  int price;
  DatabaseEnvironment(int p) {
    System.out.println("getting printed for enum");
    price = p;
  }
  int showPrice() {
    return price;
  }
}
