package latex.parser;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import latex.model.LatexFormula;
import latex.renderer.Renderer;
import latex.renderer.RendererException;

@Service
class LatexParser implements Parser {

	private static final Logger logger = LoggerFactory.getLogger(LatexParser.class);

	@Autowired
	private Renderer renderer;

	@Override
	public void parseLatex(LatexFormula latexFormula) {
		if (latexFormula == null) {
			logger.warn("Parser method was invoked with null.");
			return;
		}
		latexFormula.setEncodedImage(getEncodedFormula(latexFormula));
		latexFormula.setFormat(renderer.getFormat());
	}

	private String getEncodedFormula(LatexFormula formula) {
		try {
			return renderer.getImage(formula.getFormula(), formula.getFontSize());
		} catch (RendererException re) {
			logger.info("Error while parsing LaTeX.", re);
			return getErrorString();
		}
	}

	private String getErrorString() {
		try {
			return renderer.getImage(new ClassPathResource("error.png").getFile());
		} catch (IOException ioe) {
			logger.error("Cannot read error.png file.", ioe);
			return "";
		}
	}

}
