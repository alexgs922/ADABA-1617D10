
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Coordinate extends DomainEntity {

	//Constructors ---------------------------------------------------------------------------

	public Coordinate() {
		super();
	}


	//Attributes ---------------------------------------------------------------------------

	private String	country;
	private String	state;
	private String	province;
	private String	city;


	//Getters & Setters ----------------------------------------------------------------------

	@NotBlank
	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	@NotBlank
	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	//Relationships
	
	private Chorbi chorbi;
	private Template template;

		@Valid
		@NotNull
		@OneToOne(optional=false)
		public Chorbi getChorbi() {
			return chorbi;
		}

		public void setChorbi(Chorbi chorbi) {
			this.chorbi = chorbi;
		}
		@Valid
		@NotNull
		@ManyToOne(optional=false)
		public Template getTemplate() {
			return template;
		}

		public void setTemplate(Template template) {
			this.template = template;
		}



	
}
