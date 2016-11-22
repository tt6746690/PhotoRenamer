package backend;

import java.io.Serializable;

/**
 * Tags for images
 * @author markwang
 *
 */
public class Tag implements Serializable{

	// serialization version number
	private static final long serialVersionUID = -4734550086995215884L;

  /** name of the tag */
	public String name;

  /**
   * Creates new Tag object
   * @param name
   * 	name of the new tag
   */
	public Tag(String name){
		this.name = name;
	}

	/**
	 * Gets tag name
	 * @return
	 * 	name of the tag
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets tag name
	 * @param name
	 * 	name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets Tag name with "@" appended
	 * @return
	 */
	public String getTagName(){
		return "@" + this.getName();
	}

	@Override
	public String toString(){
		return this.getTagName();
	}


}
