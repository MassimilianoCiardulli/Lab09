package it.polito.tdp.borders.model;

public class Country implements Comparable<Country>{
	private String stateAbb ;
	private int ccode ;
	private String stateName ;
	
	public Country(String stateAbb, int ccode, String stateName) {
		super();
		this.stateAbb = stateAbb;
		this.ccode = ccode;
		this.stateName = stateName;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public int getCcode() {
		return ccode;
	}

	public void setCcode(int ccode) {
		this.ccode = ccode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ccode;
		result = prime * result + ((stateAbb == null) ? 0 : stateAbb.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (ccode != other.ccode)
			return false;
		if (stateAbb == null) {
			if (other.stateAbb != null)
				return false;
		} else if (!stateAbb.equals(other.stateAbb))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Country [stateAbb=");
		builder.append(stateAbb);
		builder.append(", ccode=");
		builder.append(ccode);
		builder.append(", stateName=");
		builder.append(stateName);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Country altro) {
		
		return this.stateAbb.compareTo(altro.stateAbb);
	}
	
}
