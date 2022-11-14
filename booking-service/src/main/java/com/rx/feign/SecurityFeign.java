package com.rx.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "security-service", url = "${security-service.url}")
public interface SecurityFeign {
	@GetMapping(path = "/user/admin")
    public boolean checkRole(@RequestHeader(name=HttpHeaders.AUTHORIZATION) String authorization);
	
	@GetMapping(path="/validate")
	public boolean validateToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization);
	
	@GetMapping(path = "/validate/username")
    public boolean doValidateUsername(@RequestParam(name = "name")String name,@RequestHeader(name=HttpHeaders.AUTHORIZATION) String auth);
}
