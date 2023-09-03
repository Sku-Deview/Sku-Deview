package kr.co.skudeview.infra.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class MethodLoggingAspect {

    @Pointcut("execution(* kr.co.skudeview.service.MemberService.*(..))")
    private void memberService() {

    }

    @Pointcut("execution(* kr.co.skudeview.service.PostService.*(..))")
    private void postService() {

    }

    @Pointcut("execution(* kr.co.skudeview.service.ReplyService.*(..))")
    private void replyService() {

    }

    @Pointcut("execution(* kr.co.skudeview.service.ReportService.*(..))")
    private void reportService() {

    }

    @Pointcut("execution(* kr.co.skudeview.service.SkillService.*(..))")
    private void skillService() {

    }

    @Pointcut("execution(* kr.co.skudeview.service.CompanyService.*(..))")
    private void companyService() {

    }

    @Pointcut("execution(* kr.co.skudeview.service.UniversityService.*(..))")
    private void universityService() {

    }

    @Pointcut("execution(* kr.co.skudeview.service.MessageService.*(..))")
    private void messageService() {

    }

    @Around("memberService() || postService() || replyService() || reportService() || skillService() || companyService() || universityService() || messageService()")
    public Object logServiceTime(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getTarget().getClass().getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        Object result = null;

        try {
            result = joinPoint.proceed();
            stopWatch.stop();
            log.info("[serviceTime] ClassName:{} - MethodName:{} : {}ms", className, methodName, stopWatch.getTotalTimeMillis());
        } catch (Throwable throwable) {
            // 예외 정보 로깅
            log.error("[EXCEPTION] {}-{} : {}", className, methodName, throwable.getMessage(), throwable);
            throw throwable;
        }
        return result;
    }

}
