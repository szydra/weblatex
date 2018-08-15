package latex.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import latex.model.LatexFormula;
import latex.renderer.Renderer;
import latex.renderer.RendererException;

@RunWith(MockitoJUnitRunner.class)
public class LatexParserTest {

	@Mock
	private Renderer renderer;

	@InjectMocks
	private LatexParser parser;

	@Test
	public void testNull() {
		parser.parseLatex(null);
		verifyZeroInteractions(renderer);
	}

	@Test
	public void testVoidFormula() {
		doReturn("base64 image").when(renderer).getImage(any(), anyInt());
		doReturn("png").when(renderer).getFormat();
		LatexFormula formula = new LatexFormula();
		parser.parseLatex(formula);
		assertNotNull(formula.getEncodedImage());
		assertNotNull(formula.getFormat());
	}

	@Test
	public void testNonVoidFormula() {
		doReturn("base64 image").when(renderer).getImage(any(), anyInt());
		doReturn("png").when(renderer).getFormat();
		LatexFormula formula = new LatexFormula();
		formula.setFormula("a^2+b^2=c^2");
		formula.setFontSize(20);
		parser.parseLatex(formula);
		verify(renderer).getImage("a^2+b^2=c^2", 20);
		assertNotNull(formula.getEncodedImage());
		assertNotNull(formula.getFormat());
	}

	@Test
	public void testRendererException() {
		doThrow(RendererException.class).when(renderer).getImage(any(), anyInt());
		doReturn("base64 error image").when(renderer).getImage(any());
		LatexFormula formula = new LatexFormula();
		parser.parseLatex(formula);
		assertEquals("base64 error image", formula.getEncodedImage());
	}

	@Test
	public void testIOException() {
		doThrow(RendererException.class).when(renderer).getImage(any(), anyInt());
		doAnswer(invocation -> {
			throw new IOException();
		}).when(renderer).getImage(any());
		LatexFormula formula = new LatexFormula();
		parser.parseLatex(formula);
		assertNotNull(formula.getEncodedImage());
	}

}
