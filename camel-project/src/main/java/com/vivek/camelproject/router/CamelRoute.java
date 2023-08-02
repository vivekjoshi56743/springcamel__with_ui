package com.vivek.camelproject.router;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

	private int pageNo;
	private List<Integer> ids;
	private List<String> filteredData = new ArrayList<>();

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub

		from("direct:fetchUserData")

				.process(exchange -> {

					pageNo = exchange.getIn().getHeader("pageNo", Integer.class);

					setIds(exchange.getIn().getHeader("ids", List.class));
					filteredData.clear();

				}).setHeader("CamelHttpMethod", constant("GET"))
				.setHeader("CamelHttpQuery", simple("page=${header.pageNo}")).toD("https://reqres.in/api/users").split()
				.jsonpath("$.data").process(exchange -> {
					String data = exchange.getIn().getBody(String.class);
					String idString = data.substring(data.indexOf('=') + 1, data.indexOf(',')).trim();
					int id = Integer.parseInt(idString);
					if (getIds().contains(-1)) {
						addfiltereddata(data);
					} else if (getIds().contains(id)) {
						addfiltereddata(data);
					}

				});

	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<String> getFilteredData() {
		return filteredData;
	}

	public void setFilteredData(List<String> filteredData) {
		this.filteredData = filteredData;
	}

	private void addfiltereddata(String data) {
		this.filteredData.add(data);
	}

}
