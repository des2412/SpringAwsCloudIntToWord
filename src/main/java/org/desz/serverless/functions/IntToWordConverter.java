package org.desz.serverless.functions;

import static java.util.Arrays.asList;
import static java.util.Locale.UK;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.desz.inttoword.factory.ProvLangFactory.getInstance;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import static java.util.function.Function.identity;

import org.desz.inttoword.exceptions.AppConversionException;
import org.desz.inttoword.language.IntWordMapping;
import org.desz.inttoword.language.ProvLang;
import org.desz.inttoword.results.DeDecorator;
import org.desz.inttoword.results.WordResult;
import org.desz.serverless.pojo.IntToWordRequest;
import org.springframework.stereotype.Component;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * @author des ConversionFunction: Function interface -> Integer to
 *         corresponding word specified by ProvLang
 * 
 */
@Component
public class IntToWordConverter implements RequestHandler<IntToWordRequest, String> {

	private IHundConverter hundredthConverter = new HundredthConverter();
	private static final NumberFormat nf = NumberFormat.getIntegerInstance(UK);

	public IntToWordConverter() {

	}

	/**
	 * BiFunction funcHunConv.
	 */
	private BiFunction<String, IntWordMapping, String> funcHunConv = (x, y) -> {
		return hundredthConverter.hundrethToWord(x, y).orElse(EMPTY);
	};

	/**
	 * 
	 * @param n the integer.
	 * @return the word for n including units.
	 * @throws AppConversionException
	 */

	public String convertIntToWord(Integer n, ProvLang pl) throws AppConversionException {

		n = requireNonNull(n, "Integer parameter null");
		final ProvLang provLang = requireNonNull(pl);
		if (provLang.equals(ProvLang.EMPTY))
			throw new AppConversionException();
		final List<String> numUnits = asList(nf.format(n).split(","));
		DeDecorator deDecorator = null;
		final int sz = numUnits.size();
		// save last element of numUnits..
		final int prmLastHun = Integer.parseInt(numUnits.get(numUnits.size() - 1));
		// singleton IntWordMapping per ProvLang.
		final IntWordMapping langMap = getInstance().getMapForProvLang(provLang);
		// convert each hundredth to word.
		final Map<Integer, String> wordMap = range(0, sz).boxed()
				.collect(toMap(identity(), i -> funcHunConv.apply(numUnits.get(i), langMap)));

		final WordResult.Builder wordBuilder = new WordResult.Builder();

		// build with UNIT added to each part.
		switch (sz) {
		case 1:
			// result returned.
			if (provLang.equals(ProvLang.DE)) {
				WordResult deWordResult = wordBuilder.withHund(wordMap.get(0)).build();

				return new DeDecorator(new DeDecorator(deWordResult).restructureHundrethRule()).pluraliseOneRule(prmLastHun)
						.toString();

			}
			return wordMap.get(0);
		case 4:

			wordBuilder.withBill(wordMap.get(0) + langMap.getBilln()).withMill(wordMap.get(1) + langMap.getMilln())
					.withThou(wordMap.get(2) + langMap.getThoud());
			break;

		case 3:
			wordBuilder.withMill(wordMap.get(0) + langMap.getMilln()).withThou(wordMap.get(1) + langMap.getThoud());

			break;

		case 2:
			wordBuilder.withThou(wordMap.get(0) + langMap.getThoud());
			break;
		default:
			break;
		}
		if (IHundConverter.inRange(prmLastHun))
			// 1 to 99 -> prepend with AND.
			wordBuilder.withHund(langMap.getAnd() + wordMap.get(sz - 1));

		else
			wordBuilder.withHund(wordMap.get(sz - 1));

		// wordResult output for non DE case.
		final WordResult wordResult = wordBuilder.build();
		// decorate DE word.
		if (provLang.equals(ProvLang.DE)) {
			WordResult.Builder deBuilder = new WordResult.Builder();
			if (nonNull(wordResult.getBill()))
				deBuilder.withBill(wordResult.getBill().trim());
			if (nonNull(wordResult.getMill()))
				deBuilder.withMill(wordResult.getMill().trim());
			if (nonNull(wordResult.getThou()))
				deBuilder.withThou(wordResult.getThou());
			if (nonNull(wordResult.getHund()))
				deBuilder.withHund(wordMap.get(sz - 1));
			deDecorator = new DeDecorator(deBuilder.build());
			WordResult deWordRes = deDecorator.pluraliseUnitRule();
			deDecorator = new DeDecorator(deWordRes);
			deWordRes = deDecorator.pluraliseOneRule(prmLastHun);
			deDecorator = new DeDecorator(deWordRes);
			deWordRes = deDecorator.restructureHundrethRule();
			deDecorator = new DeDecorator(deWordRes);
			deWordRes = deDecorator.combineThouHundRule();
			// trim, multi to single whitespace.
			return normalizeSpace(deWordRes.toString());

		}

		return wordResult.toString();

	}

	@Override
	public String handleRequest(IntToWordRequest input, Context context) {
		LambdaLogger log = context.getLogger();
		log.log(input.toString());
		String res = null;
		try {
			res = convertIntToWord(input.getNumber(), ProvLang.valueOf(input.getLang()));
		} catch (AppConversionException e) {
			log.log(e.getMessage());
		}
		return res;
	}

}
