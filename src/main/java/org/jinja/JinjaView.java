package org.jinja;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.FatalTemplateErrorsException;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

import static org.springframework.aop.interceptor.ExposeBeanNameAdvisors.getBeanName;

/**
 * @author Marco Andreini
 *
 */
@Component
public class JinjaView {

	@Getter @Setter
	private Charset encoding = Charsets.UTF_8;
	@Getter @Setter @Autowired
	private Jinjava engine;
	@Getter @Setter
	private boolean renderExceptions = false;
	@Getter @Setter
	private String contentType;


	public String renderMergedTemplateModel(String fileName, PrintStream logger)
					throws Exception {
		return doRender(fileName, logger);
	}

	private String doRender(String fileName, PrintStream logger) throws IOException {
		logger.println("Rendering Jinja template [" + fileName + "] in JinjaView '");

		StringBuilder responseWriter =new StringBuilder();

		if (renderExceptions) {
			try {
				responseWriter.append(engine.render(getTemplate(fileName), new HashMap<String, String>()));
			} catch (FatalTemplateErrorsException e) {
				// TODO: render exception
				responseWriter.append(e.getLocalizedMessage());
				logger.println("failed to render template [" + fileName + "] " + e);
			} catch (IOException e) {
				responseWriter.append("<pre>could not find template: " + fileName + "\n");
				logger.println(e.getLocalizedMessage());
				responseWriter.append("</pre>");
				logger.println("could not find template " + e);
			}
		} else {
			try {
				responseWriter.append(engine.render(getTemplate(fileName), new HashMap<String, String>()));
			} catch (Throwable e) {
				logger.println("failed to render template [" + fileName + "]\n" + e);
			}
		}
		return responseWriter.toString();
	}

	protected String getTemplate(String fileName) throws IOException {
		// XXX: interpreter could be null...
		return engine.getResourceLocator().getString(fileName, encoding,
				null);
	}

	public boolean checkResource(Locale locale) throws Exception {
		try {
			// XXX: interpreter could be null...
			engine.getResourceLocator().getString(locale.toString(), encoding, null);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
    public void setEngine(@Autowired Jinjava engine) {
		this.engine = engine;
    }

	public void setRenderExceptions(boolean renderExceptions) {
		this.renderExceptions = renderExceptions;
	}

	public void setEncoding(Charset charset) {
		encoding = charset;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
