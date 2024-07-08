package com.evn.web.service.accesscontrol.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evn.web.model.Question;
import com.evn.web.service.accesscontrol.AccessControlService;

@Aspect
@Component
public class AccessControlAspectsQuestion {

	@Autowired
	private AccessControlService<Question> accessControlService;

	public void setAccessControlService(AccessControlService<Question> accessControlService) {
		this.accessControlService = accessControlService;
	}

	@Around("execution(* com.evn.web.repository.QuestionRepository.save(..)) && args(question)")
	public Object save(ProceedingJoinPoint proceedingJoinPoint, Question question) throws Throwable {
		if (question.getId() == null) {
			accessControlService.canCurrentUserCreateObject(question);
		} else {
			accessControlService.canCurrentUserUpdateObject(question);
		}

		return proceedingJoinPoint.proceed();
	}

	@Around("execution(* com.evn.web.repository.QuestionRepository.find(Long)) && args(id)")
	public Object find(ProceedingJoinPoint proceedingJoinPoint, Long id) throws Throwable {
		accessControlService.canCurrentUserReadObject(id);

		return proceedingJoinPoint.proceed();
	}

	@Around("execution(* com.evn.web.repository.QuestionRepository.findAll())")
	public Object findAll(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		accessControlService.canCurrentUserReadAllObjects();

		return proceedingJoinPoint.proceed();
	}

	@Around("execution(* com.evn.web.repository.QuestionRepository.delete(..)) && args(question)")
	public Object delete(ProceedingJoinPoint proceedingJoinPoint, Question question) throws Throwable {
		accessControlService.canCurrentUserDeleteObject(question);

		return proceedingJoinPoint.proceed();
	}

}
