
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

	//Relationships
	
	private Coordinate coordinate;
	private Chorbi chorbi;

	@Valid
	@NotNull
	@OneToMany(mappedBy="template")
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	@Valid
	@NotNull
	@OneToMany()
	public Chorbi getChorbi() {
		return chorbi;
	}

	public void setChorbi(Chorbi chorbi) {
		this.chorbi = chorbi;
	}
	
	
	
}
