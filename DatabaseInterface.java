
// CSI2110/CSI2510 Assignment#4 2018 
// Lucia Moura, Robert Laganiere
// This interface is to be used, and not be changed


public interface DatabaseInterface {
	
	public String save(String plainPassword, String encryptedPassword); 
	 // Stores plainPassword and corresponding encryptedPassword in a map.
	 // if there was a value associated with this key, it is replaced, 
	 // and previous value returned; otherwise, null is returned
	 // The key is the encryptedPassword the value is the plainPassword

	public String decrypt(String encryptedPassword); 
	// returns plain password corresponding to encrypted password
	
    public int size(); 
    // returns the number of password pairs stored in the database
	
    public void printStatistics(); // print statistics based on type of Database
}
