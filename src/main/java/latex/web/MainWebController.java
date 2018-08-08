package latex.web;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import latex.model.LatexFormula;

@Controller
class MainWebController {

	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private LatexService latexService;

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
	public String postWeblatex(@ModelAttribute LatexFormula latexString, Model model) {
		latexService.parseLatex(latexString);
		model.addAttribute("id", counter.incrementAndGet());
		model.addAttribute("encodedImage", latexString.getEncodedImage());
		return "result";
	}

}
