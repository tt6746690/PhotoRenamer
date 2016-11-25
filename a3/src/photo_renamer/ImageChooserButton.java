package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import backend.ImageFile;

/**
 * Button that allows user to select an image in filesystem
 * @author markwang
 *
 */
public class ImageChooserButton extends JButton implements ActionListener {

	/**
	 * Creates a new button that allows user to select an image
	 * @param mainWindow
	 */
	public ImageChooserButton(String label) {
		super(label);
		this.addActionListener(this);
	}

	/**
	 * Creates a new dialogue with view of the file system with filters 
	 * for images applied. Gets user selected file from storage, if exists,
	 * and attaches various component observers to tagManager and imageFile.
	 * Then initiates view in the main window
	 * 
	 * @param e
	 * 	Button cilck event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button clicked");

		// create JFileChooser and apply image filter
		JFileChooser imageChooser = new JFileChooser();
		imageChooser.addChoosableFileFilter(new ImageFilter());
		imageChooser.setAcceptAllFileFilterUsed(false); // disable default
														// acceptAll file filter

		int returnVal = imageChooser.showOpenDialog(PhotoRenamer.mainWindow.getContentPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) { // APPROVE_OPTION
														// defaults to 0
			File image = imageChooser.getSelectedFile();

			if (image.exists()) {

				// attaching observers tagPanel
				PhotoRenamer.tagManager.addObserver((Observer) PhotoRenamer.tagPanel);

				// fetch ImageFile
				String filePath = image.getAbsolutePath();
				PhotoRenamer.imageFile = PhotoRenamer.imageFileManager.fetchImageFile(filePath);
				System.out.println(filePath + PhotoRenamer.imageFile);

				PhotoRenamer.appLogger.log(Level.INFO, "use fetched ImageFile >>>>>>>" + PhotoRenamer.imageFile);

				// attach observers to imageFile
				PhotoRenamer.imageFile.addObserver((Observer) PhotoRenamer.imagePanel);
				PhotoRenamer.imageFile.addObserver((Observer) PhotoRenamer.fileInfoPanel);
				PhotoRenamer.imageFile.addObserver((Observer) PhotoRenamer.dropDownPanel);
				
				// load Initial application state
				PhotoRenamer.imageFile.updateState();
				PhotoRenamer.tagManager.updateState();
				PhotoRenamer.mainWindow.pack();

			}

		}

	}
}
