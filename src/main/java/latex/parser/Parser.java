package latex.parser;

import latex.model.LatexFormula;

public interface Parser {

	/**
	 * This method processes LaTeX in the passed formula and sets an encoded image
	 * as well as its format. In case of any exceptions the set image can contain
	 * error info or be empty.
	 * 
	 * @param latexFormula
	 *            A formula to be processed
	 */
	void parseLatex(LatexFormula latexFormula);

}
