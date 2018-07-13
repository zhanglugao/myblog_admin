package com.zhanglugao.swagger.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig  
 * 配置中心,提供便捷的应用层接口查询
 * @author zhanghao
 */
@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {
	@Bean
	public Docket comApi()
	{
		return new Docket(DocumentationType.SWAGGER_2).pathMapping("/").select()
				.apis(RequestHandlerSelectors.basePackage("com.zhanglguao"))//只扫描应用层接口
				.build().apiInfo(comApiInfo());
	}
	private ApiInfo comApiInfo()
	{
		@SuppressWarnings("deprecation")
		ApiInfo comApiInfo = new ApiInfo("综合业务中心",                     //大标题
				"EMR Platform's REST API",                            //小标题
				"1.0",                                                //版本
				"NO terms of service", "平台开发部",                      //作者
				"The Apache License, Version 2.0",                    //链接显示文字
				"http://www.apache.org/licenses/LICENSE-2.0.html"     //网站链接
		);
		return comApiInfo;
	}
	
}
