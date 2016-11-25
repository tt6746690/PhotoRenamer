package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import backend.ImageFile;
import backend.Tag;
import backend.TagManager;

/**
 * Panel containing image file information, including name, history, and tags
 * @author markwang
 *
 */
public class FileInfoPanel extends JPanel implements Observer {

	private JTextPane fileNameLabel;
	private JPanel ImageFileTagPanel;

	/**
	 * Creates a new panel for image file with given layout
	 * @param layout
	 * 	layout for current panel
	 */
	public FileInfoPanel(FlowLayout layout) {
		super(layout);
		this.setPreferredSize(new Dimension(ConfigReader.TAG_PANEL_WIDTH, ConfigReader.WINDOW_HEIGHT/2));

		
		this.fileNameLabel = new JTextPane();
		this.fileNameLabel.setPreferredSize(new Dimension(ConfigReader.TAG_PANEL_WIDTH, ConfigReader.WINDOW_HEIGHT/8));
	    this.fileNameLabel.setOpaque(false);
	    this.fileNameLabel.setEditable(false);
	    this.fileNameLabel.setFocusable(false);
	    
	    this.ImageFileTagPanel = new JPanel(new FlowLayout());
	    this.ImageFileTagPanel.setBorder(BorderFactory.createTitledBorder("Image File's Tags"));
		this.ImageFileTagPanel.setPreferredSize(new Dimension(ConfigReader.TAG_PANEL_WIDTH, ConfigReader.WINDOW_HEIGHT/4));

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
		PhotoRenamer.appLogger.log(Level.INFO, "Change ImageFile Name to " + ((ImageFile) o).file.getName());
		
		this.removeAll();
		this.ImageFileTagPanel.removeAll();

		ImageFile imageFile = ((ImageFile) o);
		this.fileNameLabel.setText(imageFile.file.getName());
		
		// adds fileNameLabel to FileInfoPanel
		this.add(this.fileNameLabel);
		
		
		// creating buttons based on states of ImageFile tags
		for (Tag t : imageFile.tags) {
			RemoveTagButton rmBtn = new RemoveTagButton(t.getName());
			this.ImageFileTagPanel.add(rmBtn);
		}
		
		this.add(this.ImageFileTagPanel);
		
		
		this.ImageFileTagPanel.revalidate();
		this.ImageFileTagPanel.repaint();
		this.revalidate();
		this.repaint();

	}

}
