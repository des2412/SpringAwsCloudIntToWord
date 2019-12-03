package org.desz.serverless.functions;

import static java.util.Objects.isNull;
import static org.desz.inttoword.language.ProvLang.valueOf;

import java.util.Random;

import org.desz.inttoword.converters.ConversionDelegate;
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
		conversionDelegate = new ConversionDelegate();
	}

	public IntToWordConverter() {

	}
	
	public long getRandom() {
		final long l = new Random().nextLong();
		return  l >= 0 ? l : getRandom();
	}

	@Override
	public String handleRequest(IntToWordRequest input, Context context) {
		LambdaLogger log = context.getLogger();
		//log.log("lambda function input:" + input.toString());
		String res = null;
		try {
			// if n null generate random long.

			long n = isNull(input.getNumber()) ? getRandom() : input.getNumber();
			System.out.println("long value:" + n);
			//log.log("long value:" + n);
			res = conversionDelegate.convertIntToWord(n, valueOf(input.getLang()));
		} catch (AppConversionException e) {
			log.log(e.getMessage());
			res = "Conversion failed";
		}
		return res;
	}

}
