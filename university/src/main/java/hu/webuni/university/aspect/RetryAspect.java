package hu.webuni.university.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {
	   
    @Pointcut("@annotation(hu.webuni.university.aspect.Retry) || @within(hu.webuni.university.aspect.Retry)")
    public void retryPointCut() {
    }
 
    @Around("retryPointCut()")
    //@Around("retryPointCut() && @annotation(retry)") --> nem működik, ha osztály szinten van a @Retry
    public Object around(ProceedingJoinPoint joinPoint/*, Retry retry*/) throws Throwable {
        
    	Retry retry = null;
        Signature signature = joinPoint.getSignature();
        
        if(signature instanceof MethodSignature methodSignature) {
        	Method method = methodSignature.getMethod();
        	retry = method.getAnnotation(Retry.class);
        	if(retry == null) {
        		retry = method.getDeclaringClass().getAnnotation(Retry.class);
        	}
        } 
        
        int times = retry.times();
        long waitTime = retry.waitTime();
        
        if (times <= 0) {
            times = 1;
        }
 
        for (int numTry=1; numTry <= times; numTry++) {
        	
        	System.out.format("Try times: %d %n", numTry);
            
        	try {
                return joinPoint.proceed();
            } catch (Exception e) {

                if (numTry == times) 
                    throw e;
 
                if (waitTime > 0) 
                    Thread.sleep(waitTime);
            }
        }
        
        return null;	//soha nem jutunk ide
    }
}