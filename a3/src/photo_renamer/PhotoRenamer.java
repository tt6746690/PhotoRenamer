package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observer;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.ImageFile;
import backend.ImageFileManager;
import backend.TagManager;


/**
 * GUI that allows users to select/rename image files, add/remove tag based 
 * on a pool of tags, which can be created/deleted.
 * 
 * @author markwang
 *
 */
public class PhotoRenamer implements WindowListener{

	public static ImageFileManager imageFileManager;
	public static TagManager tagManager;
	public static ImageFile imageFile;

	public static JFrame mainWindow;
	public static JPanel contentPane;
	public static JPanel imagePanel;
	public static JPanel tagPanel;
	public static JPanel fileInfoPanel;
	public static JPanel rightPanel;
	public static JPanel dropDownPanel;

	/** logger for logging renaming events */
	public static Logger appLogger = Logger.getLogger(PhotoRenamer.class.getName());
	public static FileHandler fileHandler;

	/**
	 * Creates new instance of PhotoRenamer application
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public PhotoRenamer() throws IOException, ClassNotFoundException {
		
		// initialize new ConfigurationReader
		ConfigReader properties = new ConfigReader();
		properties.getConfig();
		
		// configure global logger
    	fileHandler = new FileHandler(ConfigReader.HISTORY_LOG_PATH, true);
        appLogger.addHandler(fileHandler);
        fileHandler.setFormatter(new SimpleFormatter());

		// instantiates imageFileManager and tagManager
		imageFileManager = ImageFileManager.getInstance();
		imageFileManager.initialize(ConfigReader.IMAGE_FILE_STORAGE_PATH);

		tagManager = TagManager.getInstance();
		tagManager.initialize(ConfigReader.TAG_STORAGE_PATH);

		// create new JFrame
		mainWindow = new JFrame("ImageRenamer");
		mainWindow.addWindowListener(this);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// create panels
		contentPane = new JPanel(new BorderLayout());
		rightPanel = new JPanel(new BorderLayout());
		
		// create custom panels
		imagePanel = new ImagePanel(new BorderLayout());
		fileInfoPanel = new FileInfoPanel(new FlowLayout());
		dropDownPanel = new HistoryPanel(new BorderLayout());
		tagPanel = new TagPanel(new FlowLayout());
		
		
	}

	/**
	 * Compose components together and starts application window
	 */
	public static void buildMainWindow(){

		// create components
		JButton ImageChooserButton = new ImageChooserButton("Choose an Image");

		// bundle components to contentPane
		contentPane.add(ImageChooserButton, BorderLayout.SOUTH);
		contentPane.add(imagePanel, BorderLayout.WEST);
		contentPane.add(rightPanel, BorderLayout.EAST);
		
		// bundle components to rightPanel
		rightPanel.add(fileInfoPanel, BorderLayout.NORTH);
		rightPanel.add(dropDownPanel, BorderLayout.CENTER);
		rightPanel.add(tagPanel, BorderLayout.SOUTH);

		
		// configure panels 
		imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
		fileInfoPanel.setBorder(BorderFactory.createTitledBorder("File Info"));
		tagPanel.setBorder(BorderFactory.createTitledBorder("Tags"));
		dropDownPanel.setBorder(BorderFactory.createTitledBorder("History"));
		rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		
		
		// configure main window
		mainWindow.setContentPane(contentPane);
		mainWindow.pack();
		mainWindow.setSize(ConfigReader.WINDOW_WIDTH, ConfigReader.WINDOW_HEIGHT);
		mainWindow.setVisible(true);

	}

	/**
	 * Instantiates new PhotoRenamer instance
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

	/**
	 * Saves application state upon closing
	 * @param e
	 * 	Window close event
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		appLogger.log(Level.INFO, "window closed");
		imageFileManager.saveToFile(ConfigReader.IMAGE_FILE_STORAGE_PATH);
		tagManager.saveToFile(ConfigReader.TAG_STORAGE_PATH);
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
