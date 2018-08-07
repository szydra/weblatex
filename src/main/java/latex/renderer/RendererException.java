package latex.renderer;

/**
 * This exception is thrown when an image cannot be rendered. Most likely causes
 * are an invalid LaTeX formula or an IOException.
 */
public class RendererException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	RendererException(String message) {
		super(message);
	}

	RendererException(String message, Throwable cause) {
		super(message, cause);
	}

}
