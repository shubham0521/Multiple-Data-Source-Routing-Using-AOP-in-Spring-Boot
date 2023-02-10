package database.dynamic.routing.dynamic.routing.aspect;

import java.lang.reflect.Method;

import antlr.StringUtils;
import database.dynamic.routing.dynamic.routing.annotations.TransactionType;
import database.dynamic.routing.dynamic.routing.context.DatabaseContextHolder;
import database.dynamic.routing.dynamic.routing.context.DatabaseEnvironment;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author shubhamkumar
 */

@Aspect
@Component
@Order(0)
public class TransactionReadonlyAspect {

  @Around("@annotation(database.dynamic.routing.dynamic.routing.annotations.TransactionType)")
  public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("Aspect executed");
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    TransactionType recordTransaction = method.getAnnotation(TransactionType.class);
    DatabaseContextHolder.set(DatabaseEnvironment.UPDATABLE);
    try {
      if (recordTransaction.readOnly()) {
        DatabaseContextHolder.set(DatabaseEnvironment.READONLY);
      }
      return joinPoint.proceed();
    } finally {
      DatabaseContextHolder.reset();
    }
  }
}
