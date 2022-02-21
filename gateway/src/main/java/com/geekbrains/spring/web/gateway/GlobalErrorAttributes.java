package com.geekbrains.spring.web.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.net.ConnectException;
import java.util.Map;

@Component
@Slf4j
public class GlobalErrorAttributes extends DefaultErrorAttributes {
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

		Throwable error = super.getError(request);

		Map<String, Object> map = super.getErrorAttributes(request, options);

		if (error.getCause() instanceof ConnectException) {
			map.put("statusCode", HttpStatus.GATEWAY_TIMEOUT.value());
		}
		else {
			map.put("statusCode", HttpStatus.BAD_REQUEST.value());
		}
		map.put("message", error.getMessage());

		return map;
	}

}
