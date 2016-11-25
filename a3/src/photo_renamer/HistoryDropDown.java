package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;

import javax.swing.JComboBox;

import backend.History;
import backend.ImageFile;

/**
 * A drop down menu containing file name history
 * 
 * @author markwang
 *
 */
public class HistoryDropDown extends JComboBox<History> implements ItemListener{

	
	public HistoryDropDown(){
		this.setSelectedItem(null);
		this.addItemListener(this);
	}
	
	/**
	 * Revert to a previous name upon click event
	 * @param e
	 * 	Click event
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			PhotoRenamer.appLogger.log(Level.INFO, "Reverting back name ");
			
			History selectedHistory = (History) this.getSelectedItem();
			PhotoRenamer.imageFile.revertHistory(selectedHistory);
        }
		
	}


}
