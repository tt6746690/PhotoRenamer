package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ImageFile;
import backend.ImageFileManager;
import backend.TagManager;


public class PhotoRenamer implements WindowListener{

	public static ImageFileManager imageFileManager;
	public static TagManager tagManager;
	public static ImageFile imageFile;

	public static JFrame mainWindow;
	public static JPanel contentPane;
	public static JPanel imagePanel;
	public static JPanel tagPanel;
	public static JPanel fileInfoPanel;

	/** logger for logging renaming events */
	public static Logger appLogger = Logger.getLogger(PhotoRenamer.class.getName());

	public PhotoRenamer() throws IOException, ClassNotFoundException {

		// initialize new ConfigurationReader
		ConfigurationReader properties = new ConfigurationReader();

		// get values from config file
		properties.getValues();


		// instantiates imageFileManager and tagManager
		imageFileManager = ImageFileManager.getInstance();
		imageFileManager.initialize(ConfigurationReader.IMAGE_FILE_STORAGE_PATH);

		tagManager = TagManager.getInstance();
		tagManager.initialize(ConfigurationReader.TAG_STORAGE_PATH);

		// create new JFrame
		mainWindow = new JFrame("ImageRenamer");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.addWindowListener(this);

		// create panels
		contentPane = new JPanel(new BorderLayout());
		imagePanel = new ImagePanel(new BorderLayout());
		tagPanel = new TagPanel(new FlowLayout());
		fileInfoPanel = new FileInfoPanel(new BorderLayout());

	}

	public static void buildMainWindow(){

		// create components
		JButton ImageChooserButton = new ImageChooserButton("Choose an Image");

		// bundle components to contentPane
		contentPane.add(ImageChooserButton, BorderLayout.NORTH);
		contentPane.add(imagePanel, BorderLayout.CENTER);
		contentPane.add(tagPanel, BorderLayout.EAST);

		// configure main window
		mainWindow.setContentPane(contentPane);
		mainWindow.pack();
		mainWindow.setSize(ConfigurationReader.WINDOW_WIDTH, ConfigurationReader.WINDOW_HEIGHT);
		mainWindow.setVisible(true);

	}

	/**
	 * Create and show a ImageRenamer window
	 *
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		try {
			PhotoRenamer pr = new PhotoRenamer();
			pr.buildMainWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		appLogger.log(Level.INFO, "window closed");
		imageFileManager.saveToFile(ConfigurationReader.IMAGE_FILE_STORAGE_PATH);
		tagManager.saveToFile(ConfigurationReader.TAG_STORAGE_PATH);
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
