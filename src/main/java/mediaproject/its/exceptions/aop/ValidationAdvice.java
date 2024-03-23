//package mediaproject.its.exceptions.aop;
//
//
//import mediaproject.its.exceptions.ex.CustomAppException;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@Aspect
//public class ValidationAdvice {
//
//    @Around("execution(* mediaproject.its.controller.*Controller.*(..))")
//    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//
//        Object[] args = proceedingJoinPoint.getArgs();
//        for (Object arg : args) {
//            if (arg instanceof BindingResult) {
//                BindingResult bindingResult = (BindingResult) arg;
//
//                if (bindingResult.hasErrors()) {
//                    Map<String, String> errorMap = new HashMap<>();
//
//                    for (FieldError error : bindingResult.getFieldErrors()) {
//                        errorMap.put(error.getField(), error.getDefaultMessage());
//                    }
//                    throw new CustomAppException("에러 발생",errorMap);
//                }
//
//            }
//        }
//        return proceedingJoinPoint.proceed();
//
//    }
//
//    @Around("execution(* mediaproject.its.auth.controller.*Controller.*(..))")
//    public Object joinApiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//
//        Object[] args = proceedingJoinPoint.getArgs();
//        for (Object arg : args) {
//            if (arg instanceof BindingResult) {
//                BindingResult bindingResult = (BindingResult) arg;
//
//                if (bindingResult.hasErrors()) {
//                    Map<String, String> errorMap = new HashMap<>();
//
//                    for (FieldError error : bindingResult.getFieldErrors()) {
//                        errorMap.put(error.getField(), error.getDefaultMessage());
//                    }
//                    throw new CustomAppException("회원가입 에러 발생",errorMap);
//                }
//
//            }
//        }
//        return proceedingJoinPoint.proceed();
//    }
//}
