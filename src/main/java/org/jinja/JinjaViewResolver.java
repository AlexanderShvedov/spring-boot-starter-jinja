package org.jinja;

import java.nio.charset.Charset;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.google.common.base.Charsets;
import com.hubspot.jinjava.Jinjava;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Marco Andreini
 *
 */
public class JinjaViewResolver extends AbstractTemplateViewResolver {

	@Setter
	private Jinjava engine;
	@Setter
	private Charset charset = Charsets.UTF_8;
	@Setter
	private boolean renderExceptions = false;
	@Getter @Setter
	private String contentType = "text/html;charset=UTF-8";

	public JinjaViewResolver() {
		setViewClass(requiredViewClass());
	}

	@Override
	protected Class<?> requiredViewClass() {
		return JinjaView.class;
	}

    public void setCharset(Charset encoding) {
		this.charset = encoding;
    }

	public void setEngine(Jinjava engine) {
		this.engine = engine;
	}
}
