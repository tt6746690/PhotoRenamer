package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ImageFile;
import backend.Tag;
import backend.TagManager;

public class TagPanel extends JPanel implements Observer {

	public static final int TAG_PANEL_WIDTH = 200;
	public static final int TAG_PANEL_HEIGHT = 500;

	/**
	 * Instantiates TagPanel with new layout
	 * 
	 * @param layout
	 *            layout manager
	 */
	public TagPanel(FlowLayout layout) {
		super(layout);
		this.setPreferredSize(new Dimension(TAG_PANEL_WIDTH, TAG_PANEL_HEIGHT));
	}

	/**
	 * updates upon TagManager state change
	 * 
	 * @param o
	 *            Observable TagManager that has changed
	 * @param arg
	 *            Arguments to notifyObservers method
	 */
	@Override
	public void update(Observable o, Object arg) {
		PhotoRenamer.appLogger.log(Level.INFO, "TagPanel Updating...");

		this.removeAll();
		TagManager tm = (TagManager) o;

		// creating buttons based on states of TagManager
		for (Tag t : tm.tags.values()) {
			AddTagButton btn = new AddTagButton(t.getName());
			this.add(btn);
		}

		//

	}
}
