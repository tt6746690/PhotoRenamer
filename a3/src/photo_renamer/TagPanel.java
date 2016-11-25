package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ImageFile;
import backend.Tag;
import backend.TagManager;

/**
 * Panel that displays current pool of tags 
 * @author markwang
 *
 */
public class TagPanel extends JPanel implements Observer {


	/**
	 * Instantiates TagPanel with new layout
	 * 
	 * @param layout
	 *            layout manager
	 */
	public TagPanel(FlowLayout layout) {
		super(layout);
		this.setPreferredSize(new Dimension(ConfigReader.TAG_PANEL_WIDTH, ConfigReader.WINDOW_HEIGHT/2));
	}

	/**
	 * Displays tags, and instantiates components 
	 * that adds/creates/deletes tags
	 * 
	 * @param o
	 *            Observable TagManager that has changed
	 * @param arg
	 *            Arguments to notifyObservers method
	 */
	@Override
	public void update(Observable o, Object arg) {
		PhotoRenamer.appLogger.log(Level.INFO, "Tag updates to" + (TagManager) o);

		this.removeAll();
		
		TagManager tm = (TagManager) o;

		// creating buttons based on states of TagManager
		for (Tag t : tm.tags.values()) {
			AddTagButton addBtn = new AddTagButton(t.getName());
			DeleteTagButton deleteBtn = new DeleteTagButton(t.getName());
			
			// bundle add and delete button together
			FlowLayout layout = new FlowLayout();
			layout.setHgap(0);
			JPanel buttonBundle = new JPanel(layout);
			buttonBundle.add(addBtn);
			buttonBundle.add(deleteBtn);
			
			// adds bundled buttons to panel
			this.add(buttonBundle);
			
		}
		this.add(new CreateTagTextField());
		
		
		this.revalidate();
		this.repaint();
	}
}
