package org.desz.serverless.functions;

import static org.desz.inttoword.language.ProvLang.valueOf;

import org.desz.inttoword.converters.ConversionDelegate;
import org.desz.inttoword.converters.HundredthConverter;
import org.desz.inttoword.converters.NumberFormatValidator;
import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.serverless.pojo.IntToWordRequest;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * @author des IntToWordConverter: Delegates to ConversionDelegate.
 * 
 */
@Component
public class IntToWordConverter implements RequestHandler<IntToWordRequest, String> {

	private static ConversionDelegate conversionDelegate;
	static {
		conversionDelegate = new ConversionDelegate(new HundredthConverter());
		conversionDelegate.setNumberFormatValidator(NumberFormatValidator.getInstance());
	}

	public IntToWordConverter() {

	}

	@Override
	public String handleRequest(IntToWordRequest input, Context context) {
		LambdaLogger log = context.getLogger();
		log.log("lambda function input:" + input.toString());
		String res = null;
		try {
			res = conversionDelegate.convertIntToWord(input.getNumber(), valueOf(input.getLang()));
		} catch (AppConversionException e) {
			log.log(e.getMessage());
			res = "Conversion failed";
		}
		return res;
	}

}
