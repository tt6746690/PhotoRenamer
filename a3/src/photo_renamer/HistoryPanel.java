package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.History;
import backend.ImageFile;
import backend.Tag;
import backend.TagManager;

/**
 * @author Eddie
 *
 */
public class HistoryPanel extends JPanel implements Observer{
	
	public JComboBox<History> dropDownBox;
	
	/**
	 * Creates new drop down displaying rename history 
	 * @param layout 
	 * 	layout manager for the dropdown
	 */
	public HistoryPanel (BorderLayout layout) {
		super(layout);
	}

	/**
	 * Displays history of previous file names
	 * Allows reversal to a previous name
	 * 
	 * @param o
	 *            Observable ImageFile that has changed
	 * @param arg
	 *            Arguments to notifyObservers method
	 */
	@Override
	public void update(Observable o, Object arg) {
		PhotoRenamer.appLogger.log(Level.INFO, "HistoryDropDown Updating..." + (ImageFile) o);
		
		this.removeAll();
		
		ImageFile imageFile = (ImageFile) o;
				
		dropDownBox = new HistoryDropDown();
		
		
		for (int i = 0; i< imageFile.history.size(); i++){
			History hist = imageFile.history.get(i);
			dropDownBox.insertItemAt(hist, i);
		}
		
		this.add(dropDownBox, BorderLayout.SOUTH);
		
		this.revalidate();
		this.repaint();
	}

}
