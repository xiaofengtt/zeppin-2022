package cn.product.treasuremall.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import cn.product.treasuremall.api.base.ActionParam;
import io.swagger.annotations.ApiImplicitParam;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE - 10)
//@Order
public class ExplainOperationBuilderPlugin2 implements OperationBuilderPlugin {
	
	private final static Logger log = LoggerFactory.getLogger(ExplainOperationBuilderPlugin2.class);
	
	private final DescriptionResolver descriptions;
	
	public ExplainOperationBuilderPlugin2(DescriptionResolver descriptions) {
		super();
		this.descriptions = descriptions;
	}

	@Override
	public boolean supports(DocumentationType delimiter) {
		return SwaggerPluginSupport.pluginDoesApply(delimiter);
	}

	@Override
	public void apply(OperationContext context) {
		Optional<ApiImplicitParam> annotation = context.findAnnotation(ApiImplicitParam.class);
		if(!annotation.isPresent()) {
			context.operationBuilder().parameters(readParameters(context));
//			Optional<ActionParam> apO = context.findAnnotation(ActionParam.class);
////			if(apO.isPresent()) {
////				context
////			}
//			List<Parameter> parameters = Lists.newArrayList();
//			parameters.add(new ParameterBuilder().name(apO.get().message()).build());
//			context.operationBuilder().parameters(parameters);
		}
	}


	private List<Parameter> readParameters(OperationContext context) {
	    Optional<ActionParam> annotation = context.findAnnotation(ActionParam.class);
	    List<Parameter> parameters = Lists.newArrayList();
	    System.out.println(annotation.isPresent());
	    if (annotation.isPresent()) {
	    	System.out.println(context.requestMappingPattern());
	    	System.out.println(annotation.get().message());
	      parameters.add(ExplainOperationBuilderPlugin2.implicitParameter(descriptions, annotation.get()));
	    }
	    return parameters;
	}
	
	  static Parameter implicitParameter(DescriptionResolver descriptions, ActionParam param) {
		    return new ParameterBuilder()
		        .name(param.message())
		        .description(descriptions.resolve(param.message()))
//		        .defaultValue(param.defaultValue())
//		        .required(param.required())
//		        .allowMultiple(param.allowMultiple())
//		        .allowableValues(allowableValueFromString(param.allowableValues()))
//		        .parameterType(emptyToNull(param.paramType()))
//		        .parameterAccess(param.access())
//		        .order(SWAGGER_PLUGIN_ORDER)
//		        .scalarExample(param.example())
//		        .complexExamples(examples(param.examples()))
		        .build();
		  }
	  
}
