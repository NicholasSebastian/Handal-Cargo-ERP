package Types;

public class Address {
	
	public String address;
	public String kelurahan;
	public String city;
	public int postalCode;
	
	public Address(String address, String kelurahan, String city) {
		this.address = address;
		this.kelurahan = kelurahan;
		this.city = city;
	}
	
	public Address(String address, String kelurahan, String city, int postalCode) {
		this(address, kelurahan, city);
		this.postalCode = postalCode;
	}
	
	@Override
	public String toString() {
		return String.format(
			"%s%n%s, %s",
			this.address,
			this.kelurahan,
			this.city
		);
	}
}
