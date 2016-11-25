package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ImageFile;

/**
 * Panel that displays image name and image 
 * 
 * @author markwang
 *
 */
public class ImagePanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private ImageIcon imageIcon;
	private JLabel imageLabel;

	/**
	 * Instantiates ImagePanel with new layout
	 * 
	 * @param layout
	 *            layout manager
	 */
	public ImagePanel(BorderLayout layout) {
		super(layout);
		
		this.imageLabel = new JLabel();
		this.imageIcon = new ImageIcon();

		this.imageLabel.setVerticalTextPosition(JLabel.TOP);
		this.imageLabel.setHorizontalTextPosition(JLabel.CENTER);
	}

	/**
	 * updates name and image if a different imageFile is selected
	 * 
	 * @param o
	 *            Observable ImageFile that has changed
	 * @param arg
	 *            Arguments to notifyObservers method
	 */
	@Override
	public void update(Observable o, Object arg) {
		PhotoRenamer.appLogger.log(Level.INFO, "ImagePanel Updating...");

		this.removeAll();
	
		// updates ImageIcon
		ImageFile imageFile = ((ImageFile) o);
		BufferedImage img = this.readImage(imageFile.file);
		this.imageIcon.setImage(img);

		// resizes ImageIcon
		ImageIcon ic = this.resizeIcon(this.imageIcon);

		System.out.println(imageFile.file.getPath());
		// resets text and imageIcon for imageLabel
		System.out.println(imageFile.originalName);
		this.imageLabel.setText(imageFile.originalName);
		this.imageLabel.setIcon(ic);

		// adds imageLabel to ImagePanel
		this.add(this.imageLabel, BorderLayout.NORTH);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Resizes image for display
	 * 
	 * @param imageIcon
	 *            User selected Image Icon
	 * @return Resized Image Icon
	 */
	public ImageIcon resizeIcon(ImageIcon icon) {
		Image image = icon.getImage();
		// -1 supplied to keep aspect ratio while enforcing resizing based on
		// height
		Image newImg = image.getScaledInstance(-1, ConfigReader.IMAGE_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}

	/**
	 * Reads images from file
	 * 
	 * @param f
	 *            image File
	 * @return BufferedImage read
	 */
	public BufferedImage readImage(File f) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return img;
	}

}
