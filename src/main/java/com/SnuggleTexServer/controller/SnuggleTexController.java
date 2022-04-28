package com.SnuggleTexServer.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.SnuggleTexServer.bean.Peticion;
import com.SnuggleTexServer.bean.Resultado;

import uk.ac.ed.ph.snuggletex.SnuggleEngine;
import uk.ac.ed.ph.snuggletex.SnuggleInput;
import uk.ac.ed.ph.snuggletex.SnuggleSession;
import uk.ac.ed.ph.snuggletex.XMLStringOutputOptions;
import uk.ac.ed.ph.snuggletex.upconversion.UpConvertingPostProcessor;
import uk.ac.ed.ph.snuggletex.upconversion.internal.UpConversionPackageDefinitions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/snuggle")
public class SnuggleTexController {
	@RequestMapping(value = "obtenerResultado", method = RequestMethod.GET)
	public Resultado obtenerResultado(HttpServletRequest request) throws IOException {
		Resultado resultado = new Resultado();

		String input = "$$6$$";

		SnuggleEngine engine = new SnuggleEngine();
		engine.addPackage(UpConversionPackageDefinitions.getPackage());

		SnuggleSession session = engine.createSession();

		/* Parse input. I won't bother checking it here */
		session.parseInput(new SnuggleInput(input));

		/*
		 * Create an UpConvertingPostProcesor that hooks into the DOM generation process
		 * to do all of the work. We'll use its (sensible) default behaviour here;
		 * options can be passed to this constructor to tweak things.
		 */
		UpConvertingPostProcessor upConverter = new UpConvertingPostProcessor();

		/*
		 * We're going to create a simple XML String output, which we configure as
		 * follow. Note how we hook the up-conversion into this options Object.
		 */
		XMLStringOutputOptions xmlStringOutputOptions = new XMLStringOutputOptions();
		xmlStringOutputOptions.addDOMPostProcessors(upConverter);
		xmlStringOutputOptions.setIndenting(true);
		xmlStringOutputOptions.setUsingNamedEntities(true);

		/* Build up the resulting XML */
		String result = session.buildXMLString(xmlStringOutputOptions);
		result = result.replace("\n", "");
		String patternString = "<annotation-xml encoding=\"MathML-Content\">.*</annotation-xml>";
		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(result);

		if (matcher.find()) {
			// System.out.println("group count: "+matcher.groupCount());
			result = matcher.group(0);
		} else {
			System.out.println("NO MATCH");
		}

		result = result.replace("<annotation-xml encoding=\"MathML-Content\">", "<math>");
		result = result.replace("</annotation-xml>", "</math>");

		System.out.println("Up-Conversion process generated: " + result);

		resultado.setSalida(result);

		return resultado;
	}

	@PostMapping(value = "/procesarLatex", consumes = "application/json", produces = "application/json")
	public Resultado procesarLatex(@RequestBody Peticion peticion) throws IOException {

		Resultado resultado = new Resultado();
		String input = peticion.getEntrada();

		SnuggleEngine engine = new SnuggleEngine();
		engine.addPackage(UpConversionPackageDefinitions.getPackage());

		SnuggleSession session = engine.createSession();

		/* Parse input. I won't bother checking it here */
		session.parseInput(new SnuggleInput(input));

		/*
		 * Create an UpConvertingPostProcesor that hooks into the DOM generation process
		 * to do all of the work. We'll use its (sensible) default behaviour here;
		 * options can be passed to this constructor to tweak things.
		 */
		UpConvertingPostProcessor upConverter = new UpConvertingPostProcessor();

		/*
		 * We're going to create a simple XML String output, which we configure as
		 * follow. Note how we hook the up-conversion into this options Object.
		 */
		XMLStringOutputOptions xmlStringOutputOptions = new XMLStringOutputOptions();
		xmlStringOutputOptions.addDOMPostProcessors(upConverter);
		xmlStringOutputOptions.setIndenting(true);
		xmlStringOutputOptions.setUsingNamedEntities(true);

		/* Build up the resulting XML */
		String result = session.buildXMLString(xmlStringOutputOptions);
		result = result.replace("\n", "");
		String patternString = "<annotation-xml encoding=\"MathML-Content\">.*</annotation-xml>";
		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(result);

		if (matcher.find()) {
			// System.out.println("group count: "+matcher.groupCount());
			result = matcher.group(0);
		} else {
			System.out.println("NO MATCH");
		}

		result = result.replace("<annotation-xml encoding=\"MathML-Content\">", "<math>");
		result = result.replace("</annotation-xml>", "</math>");

		System.out.println("Up-Conversion process generated: " + result);

		resultado.setSalida(result);

		return resultado;
	}

}
