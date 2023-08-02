package com.vivek.camelproject.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.FluentProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.camelproject.router.CamelRoute;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CamelController {
	
	
	private FluentProducerTemplate producertemplate;
	private  CamelRoute camelRoute;
	
	public CamelController(FluentProducerTemplate producerTemplate,CamelRoute camelRoute ) {
		this.producertemplate=producerTemplate;
		this.camelRoute=camelRoute;
	}
	
	@GetMapping("/test/{page}/{ids}")
	public ResponseEntity<List<String>> getfilteredResponse( @PathVariable("page") int pageNo, @PathVariable("ids") List<Integer> ids){
		
		Map<String, Object> headers = new HashMap<>();
        headers.put("pageNo", pageNo);
        headers.put("ids", ids);
		
		producertemplate.withHeaders(headers).to("direct:fetchUserData").send();
		List<String> filteredData = camelRoute.getFilteredData();

        return ResponseEntity.ok(filteredData);
		
		
	}
}
