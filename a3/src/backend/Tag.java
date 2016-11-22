package backend;

import java.io.Serializable;

/**
 * Tags for images
 * 
 * @author markwang
 *
 */
public class Tag implements Serializable {

	// serialization version number
	private static final long serialVersionUID = -4734550086995215884L;

	/** name of the tag */
	public String name;

	/**
	 * Creates new Tag object
	 * 
	 * @param name
	 *            name of the new tag
	 */
	public Tag(String name) {
		this.name = name;
	}

	/**
	 * Gets tag name
	 * 
	 * @return name of the tag
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets tag name
	 * 
	 * @param name
	 *            name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets Tag name with "@" appended
	 * 
	 * @return
	 */
	public String getTagName() {
		return "@" + this.getName();
	}

	@Override
	public String toString() {
		return this.getTagName();
	}
	
	/**
	 * Evaluates if Object obj equals to this in tag name
	 * @param obj 
	 * 	Object compared to
	 */
	@Override
	public boolean equals(Object obj) {
		
		return this.getName().equals( ((Tag)obj).getName() );
	}
//
//	public static void main(String[] args){
//		Tag t1 = new Tag("a");
//		Tag t2 = new Tag("a");
//		
//		System.out.println(t1==t2);
//		System.out.println(t1.equals(t2));
//	}

}
