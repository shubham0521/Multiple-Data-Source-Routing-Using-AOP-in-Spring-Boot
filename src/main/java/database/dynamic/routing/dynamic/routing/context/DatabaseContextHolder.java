package database.dynamic.routing.dynamic.routing.context;

/**
 * @author shubhamkumar
 */

public class DatabaseContextHolder {
  public static final ThreadLocal<DatabaseEnvironment> CONTEXT = new InheritableThreadLocal<>();

  public static void set(DatabaseEnvironment databaseEnvironment) {
    System.out.println("setting env context" + CONTEXT);
    CONTEXT.set(databaseEnvironment);
  }

  public static DatabaseEnvironment getEnvironment() {
    System.out.println("getting env context"+ CONTEXT);
    return CONTEXT.get();
  }

  public static void reset() {
    System.out.println("reset env context"+ CONTEXT);
    CONTEXT.remove();
  }
}
