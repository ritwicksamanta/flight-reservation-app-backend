package com.rx.flights.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="security-service", url="${security.service.url}")
public interface SecurityFeign {
	@GetMapping(path="/validate")
	public boolean validateToken(@RequestHeader(name=HttpHeaders.AUTHORIZATION) String authToken);
	
	@GetMapping(path = "/user/admin")
	boolean checkIfAdmin(@RequestHeader(name=HttpHeaders.AUTHORIZATION) String auth);
}
