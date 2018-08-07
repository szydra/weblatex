package latex.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.springframework.stereotype.Component;

@Component
class PngRenderer implements Renderer {

	private byte[] toByteArray(BufferedImage image) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(image, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RendererException("Cannot convert an image to a byte array.", e);
		}
	}

	private BufferedImage toBufferedImage(String latexFormula, int fontSize) {
		TeXFormula formula = new TeXFormula(latexFormula);
		TeXIcon icon = formula.new TeXIconBuilder()
				.setStyle(TeXConstants.STYLE_DISPLAY)
				.setSize(fontSize)
				.build();
		icon.setInsets(new Insets(5, 5, 5, 5));
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		paintFormula(icon, image);
		return image;
	}

	private void paintFormula(TeXIcon icon, BufferedImage image) {
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
		JLabel label = new JLabel();
		label.setForeground(Color.BLACK);
		icon.paintIcon(label, graphics, 0, 0);
	}

	private String toBase64String(BufferedImage image) {
		Encoder encoder = Base64.getEncoder();
		return "data:image/png;base64," + encoder.encodeToString(toByteArray(image));
	}

	@Override
	public String getImage(String latexFormula, int fontSize) {
		return toBase64String(toBufferedImage(latexFormula, fontSize));
	}

	@Override
	public String getImage(File image) {
		if (image == null) {
			throw new RendererException("Image to be encoded cannot be null.");
		}
		try {
			return toBase64String(ImageIO.read(image));
		} catch (IOException e) {
			throw new RendererException("Cannot encode image " + image.getName(), e);
		}
	}

	@Override
	public String getFormat() {
		return "png";
	}
}
