
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Template extends DomainEntity {

	//Constructors ---------------------------------------------------------------------------

	public Template() {
		super();
	}


	//Attributes ---------------------------------------------------------------------------

	private Relationship	relationShip;
	private Integer			approximatedAge;
	private Genre			genre;
	private String			keyword;
	private Date			moment;


	//Getters & Setters ----------------------------------------------------------------------

	public Relationship getRelationShip() {
		return this.relationShip;
	}

	public void setRelationShip(final Relationship relationShip) {
		this.relationShip = relationShip;
	}

	public Integer getApproximatedAge() {
		return this.approximatedAge;
	}

	public void setApproximatedAge(final Integer approximatedAge) {
		this.approximatedAge = approximatedAge;
	}

	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(final Genre genre) {
		this.genre = genre;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
