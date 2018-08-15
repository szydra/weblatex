package latex.renderer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

public class PngRendererTest {

	Renderer renderer = new PngRenderer();

	@Test(expected = RendererException.class)
	public void testInvalidFormula() {
		renderer.getImage("\\badFomula", 50);
	}

	@Test
	public void testValidFormula() {
		String data = renderer.getImage("x \\in \\mathbb{R}", 10);
		assertTrue(isPng(data));
	}

	@Test(expected = RendererException.class)
	public void testFileWithIOException() {
		File mockFile = Mockito.mock(File.class, invocation -> {
			throw new IOException();
		});
		doReturn("error.png").when(mockFile).getName();
		renderer.getImage(mockFile);
	}

	@Test(expected = RendererException.class)
	public void testNull() {
		renderer.getImage(null);
	}

	@Test
	public void testFileWithoutIOException() throws IOException {
		String data = renderer.getImage(new ClassPathResource("test.png").getFile());
		assertTrue(isPng(data));
	}

	private boolean isPng(String base64) {
		return base64 != null && base64.startsWith("data:image/png;base64,");
	}

}
