package hu.andrasn.summertime.data;

public class DestinationDto {
	
	private String name;
	private String dependency;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDependency() {
		return dependency;
	}
	public void setDependency(String dependency) {
		this.dependency = dependency;
	}
	
	public boolean hasDependency() {
		return dependency != null;
	}
}
