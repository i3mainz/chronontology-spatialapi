package de.i3mainz.chronontology.errorlog;

/**
 * EXCEPTION for warnings if RDF model parsing is wrong
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 */
public class JenaModelException extends Exception{
	
	/**
	 * EXCEPTION for warnings if RDF model parsing is wrong
	 * @param message
	 */
	public JenaModelException(String message) {
        super(message);
    }
	
	/**
	 * EXCEPTION for warnings if RDF model parsing is wrong
	 */
	public JenaModelException() {
        super();
    }
	
}

