package com.corock.config.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
@EnableWebMvc
public class MVCConfig extends WebMvcConfigurerAdapter {

	/** View resolver */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix( "/WEB-INF/views/" );
		resolver.setSuffix( ".jsp" );
		resolver.setExposeContextBeansAsAttributes( true );

		return resolver;
	}

	/** Falling back on the DefaultServlet to serve resources */
	@Override
	public void configureDefaultServletHandling( DefaultServletHandlerConfigurer configurer ) {
		configurer.enable();
	}

	/** Message converters */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		Jackson2ObjectMapperBuilder builder =
				new Jackson2ObjectMapperBuilder().indentOutput( true )
												 .dateFormat( new SimpleDateFormat("yyyy-MM-dd") )
												 .modulesToInstall( new ParameterNamesModule() );
		
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList( new MediaType("application", "json", Charset.forName("utf-8")) ));

		return converter;
	}
	
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList( new MediaType("text", "html", Charset.forName("utf-8")) ));
		return converter;
	}
	
	@Override
	public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
		converters.add( mappingJackson2HttpMessageConverter() );
		converters.add( stringHttpMessageConverter() );
	}
	
}
