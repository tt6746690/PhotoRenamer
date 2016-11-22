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

public class ImageChooserButton extends JButton implements ActionListener {

	/**
	 * actionListener for mainWindow, use JFileChooser to choose images..
	 * 
	 * @param mainWindow
	 */
	public ImageChooserButton(String label) {
		super(label);
		this.addActionListener(this);
	}

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
				PhotoRenamer.tagManager.addTag("dooooog");
				PhotoRenamer.tagManager.addTag("cat");
				PhotoRenamer.tagManager.addTag("elephant");
				PhotoRenamer.tagManager.addTag("carrot");

				// loads initial view of tags
				// tagManager.updateState();

				String filePath = image.getAbsolutePath();
				PhotoRenamer.imageFile = PhotoRenamer.imageFileManager.fetchImageFile(filePath);

				PhotoRenamer.appLogger.log(Level.INFO, "selected ImageFile fetched...");

				// attach observers to imageFile
				PhotoRenamer.imageFile.addObserver((Observer) PhotoRenamer.imagePanel);

				// load Initial application state
				PhotoRenamer.imageFile.updateState();
				PhotoRenamer.mainWindow.pack();

			}

		}

	}
}
