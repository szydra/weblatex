package latex.web;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import latex.model.LatexFormula;
import latex.parser.Parser;

@Controller
class MainWebController {

	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private Parser parser;

	@GetMapping("/")
	public String redirectToWeblatex() {
		return "redirect:/weblatex";
	}

	@GetMapping("/weblatex")
	public String getWeblatex(Model model) {
		model.addAttribute("latexFormula", new LatexFormula());
		return "weblatex";
	}

	@PostMapping("/weblatex")
	public String postWeblatex(@ModelAttribute LatexFormula latexFormula, Model model) {
		parser.parseLatex(latexFormula);
		model.addAttribute("id", counter.incrementAndGet());
		model.addAttribute("encodedImage", latexFormula.getEncodedImage());
		model.addAttribute("format", latexFormula.getFormat());
		return "weblatex";
	}

}
