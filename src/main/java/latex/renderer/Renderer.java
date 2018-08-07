package latex.renderer;

import java.io.File;

public interface Renderer {

	/**
	 * This method generates images, as base64 and with a rendered formula, which
	 * can be used, e.g., in <code>src</code> attribute of an <code>img</code> HTML
	 * tag.
	 * 
	 * @param latexFormula
	 *            LaTeX string to parsed
	 * @param fontSize
	 *            Formula font size
	 * @return LaTeX image as base64
	 * @throws RendererException
	 *             if the passed formula cannot be parsed
	 */
	String getImage(String latexFormula, int fontSize);

	/**
	 * This method converts the passed image file to base64 which can be used in
	 * <code>src</code> attribute of an <code>img</code> HTML tag.
	 * 
	 * @param image
	 *            File to be converted
	 * @return Image as base64
	 * @throws RendererException
	 *             if an IO problem occurred
	 */
	String getImage(File image);

	/**
	 * This method returns the format of generated images; it can be used, e.g., for
	 * setting the file extension.
	 * 
	 * @return Format of generated images
	 */
	String getFormat();

}
