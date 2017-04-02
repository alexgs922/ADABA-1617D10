
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
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

	private Relationship			relationShip;
	private Integer					approximatedAge;
	private Genre					genre;
	private String					keyword;
	private Date					moment;
	private Collection<Coordinate>	coordinates;


	//Getters & Setters ----------------------------------------------------------------------

	@ElementCollection
	public Collection<Coordinate> getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final Collection<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

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

	private Collection<Chorbi>	chorbies;


	@Valid
	@NotNull
	@OneToMany
	public Collection<Chorbi> getChorbies() {
		return this.chorbies;
	}

	public void setChorbies(final Collection<Chorbi> chorbies) {
		this.chorbies = chorbies;
	}

}
