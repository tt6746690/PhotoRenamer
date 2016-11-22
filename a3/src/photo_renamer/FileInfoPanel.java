package photo_renamer;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ImageFile;

public class FileInfoPanel extends JPanel implements Observer {

	private JLabel fileFullName;

	public FileInfoPanel(BorderLayout layout) {
		super(layout);
		this.fileFullName = new JLabel();
	}

	/**
	 * updates upon ImageFile state change
	 * 
	 * @param o
	 *            Observable ImageFile that has changed
	 * @param arg
	 *            Arguments to notifyObservers method
	 */
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		PhotoRenamer.appLogger.log(Level.INFO, "InfoPanel Updating...");

	}

}
