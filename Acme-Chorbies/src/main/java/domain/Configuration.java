
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	//Constructors ---------------------------------------------------------------------------

	public Configuration() {
		super();
	}


	//Attributes ---------------------------------------------------------------------------

	private String	banner;
	private Integer	hour;
	private Integer	minute;
	private Integer	second;


	//Getters & Setters ----------------------------------------------------------------------

	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public Integer getHour() {
		return this.hour;
	}

	public void setHour(final Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return this.minute;
	}

	public void setMinute(final Integer minute) {
		this.minute = minute;
	}

	public Integer getSecond() {
		return this.second;
	}

	public void setSecond(final Integer second) {
		this.second = second;
	}

}
