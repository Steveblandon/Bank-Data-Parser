package proj.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import proj.core.beans.DefaultIdentifier;
import proj.core.beans.Identifier;

public class IdentifierRegistry {
	
	List<Identifier> identifiers;
	
	public IdentifierRegistry(List<Identifier> identifiers) {
		this.identifiers = identifiers;
		sortIdentifiers();
	}
	
	
	private void sortIdentifiers(){
		Collections.sort(identifiers, new Comparator<Identifier>(){

			@Override
			public int compare(Identifier currentIdentifier, Identifier nextIdentifier) {
				String currentIdentifierID = currentIdentifier.getId();
				String nextIdentifierID = nextIdentifier.getId(); 
				return currentIdentifierID.compareTo(nextIdentifierID);
			}
		});
	}
	
	
	public Identifier findIdentifier(String key) {
		int low = 0;
		int mid = 0;
		int high = identifiers.size() - 1;
		Identifier identifier = null;
		String identifierID = "";
		key = key.toUpperCase();		//identifier IDs are uppercase, but we dont want to change case of final descriptors
		
		while (low <= high) {
			mid = (low + high) / 2;
			identifier = identifiers.get(mid);
			identifierID = identifier.getId();
			if (key.compareTo(identifierID) == 0 || key.contains(identifierID)) {
				return identifier;
			}
			else if (key.compareTo(identifierID) > 0) {
				low = mid + 1;
			}
			else {
				high = mid - 1;
			}
		}
		identifier = new DefaultIdentifier();
		return identifier;
	}
}
