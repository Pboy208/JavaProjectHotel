package rooms;

public class Filter {
	
	private int extensions[] = new int[12]; //1,0 ?
	private String location;
	
	
	private static String[] extensionLibrary = {"breakfast","wifi","carpark","airport",
			"restaurant","deposit_box","baby","bar","laundry","tour","spa","pool"};
	
	public Filter() {
		
	}
	
	public void setExtensions(int[] extension) {
		this.extensions=extension;
	}
	
	public int[] getExtensions() {
		return this.extensions;
	}
	
	public String[] getExtensionsLibrary() {
		return Filter.extensionLibrary;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	private int star;
}
