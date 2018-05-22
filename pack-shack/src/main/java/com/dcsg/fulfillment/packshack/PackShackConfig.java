package com.dcsg.fulfillment.packshack;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class PackShackConfig {

	@Value("${settings.cors_origin}")
	private String allowedOrigin;

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin(allowedOrigin);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
	
	@Bean
	public Map<String,Integer> shipStatusMap() {
		Map<String,Integer> shipStatusMap = new HashMap<String,Integer>();
		shipStatusMap.put("Ready for Packing", 130);
		shipStatusMap.put("Packed", 150);
		shipStatusMap.put("Manifested", 170);
		return shipStatusMap;
	}

	@Bean
	public Map<String,Integer> bopisStatusMap() {
		Map<String,Integer> bopisStatusMap = new HashMap<String,Integer>();
		bopisStatusMap.put("Ready for Packing", 116);
		bopisStatusMap.put("Ready for Pickup", 130);
		return bopisStatusMap;
	}

}