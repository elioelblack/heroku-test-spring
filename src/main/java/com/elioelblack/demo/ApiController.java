package com.elioelblack.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@Controller
@RestController
@RequestMapping("voicemail")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST})
public class ApiController {
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/messages")
	public String getLista() {
		String accessToken = "NDY0MmU2NDA0MGNkYjhiODljMzEwYTIxYTA3YzdmNjI6MjMyNjQxNTY1OTA3NWU3NTAwMGNlY2Q3YmNiZjM3NTY=";
	
		String url = "https://sandbox.2600hz.com:8443/v2/accounts/4642e64040cdb8b89c310a21a07c7f62/vmboxes/b37675a2d7b90d60f0ee5d4175502394/messages";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Basic "+accessToken);
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		System.out.println("Entity: "+entity);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	}
}
