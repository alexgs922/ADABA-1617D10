
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
	private int		hour;
	private int		minute;
	private int		second;


	//Getters & Setters ----------------------------------------------------------------------

	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public int getHour() {
		return this.hour;
	}

	public void setHour(final int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return this.minute;
	}

	public void setMinute(final int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return this.second;
	}

	public void setSecond(final int second) {
		this.second = second;
	}

}
