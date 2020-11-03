package hr.fer.progi.domain;

/**
 * Represents in which state is current request.
 */
public enum RequestStatus {

	//active, but no answer
	ACTNOANS,
	
	//active with answer
	ACTANS,
	
	DELETED,
	
	BLOCKED,
	
	ACCEPTED,
	
	DONE;
	
	
	
	
}
