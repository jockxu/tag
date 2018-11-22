package com.crt.tag.web.ui.common.intercept;


import com.crt.tag.web.ui.common.constant.JsonResult;
import com.crt.tag.web.ui.common.enums.ResultCode;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Aspect
@Component
@Log4j2
public class LoggerInterceptor {

	private Gson gson = new Gson();
	private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
	// @Pointcut("execution(* com.crt.tag.web.ui.modules..*(..)) and
	// @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void controllerMethodPointcut() {
	}
	
	
	@Around("controllerMethodPointcut()")
	public Object Interceptor(ProceedingJoinPoint pjp) throws IOException {
		long beginTime = System.currentTimeMillis();

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		HttpServletResponse response = sra.getResponse();

		String url = request.getRequestURL().toString();
		String method = request.getMethod();
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
   
		if (StringUtils.isBlank(queryString)) {
			Map<?, ?> paramMap = request.getParameterMap();
			queryString = gson.toJson(paramMap);
		}

		log.info("[REQUEST BEGINING] >>> URL: {} |#| METHOD: {} |#| URI: {} |#| PARAMS: {}", url, method, uri,
				queryString);

		Object result = null;
		try {
			result = pjp.proceed();

			log.info("[RESPONSE  RESULT] <<< RETURN: {}", gson.toJson(result));
		} catch (Throwable e) {
			log.info("exception: ", e);
			result = new JsonResult(ResultCode.EXCEPTION, "发生异常：" + e.getMessage());
		}

		long endTime = System.currentTimeMillis();
		log.debug(
				"[PERFORMANCE INFO] === BEGIN: {} |#| END: {} |#| COST: {} |#| MAX MEM: {}m |#| ASSIGNED MEM: {}m |#| ASSIGNED REMAIN MEM: {}m |#| MAX AVAILABLE MEM: {}m",
				sdf.format(beginTime), sdf.format(endTime), formatDateTime(endTime - beginTime),
				Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024,
				Runtime.getRuntime().freeMemory() / 1024 / 1024, (Runtime.getRuntime().maxMemory()
						- Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);

		return result;
	}


	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}
}
