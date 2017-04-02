
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chorbi extends Actor {

	//Constructors ---------------------------------------------------------------------------

	public Chorbi() {
		super();
	}


	//Attributes ---------------------------------------------------------------------------

	private String			picture;
	private String			description;
	private Relationship	relationship;
	private Date			birthDate;
	private Genre			genre;
	private boolean			ban;


	//Getters & Setters ----------------------------------------------------------------------

	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Relationship getRelationship() {
		return this.relationship;
	}

	public void setRelationship(final Relationship relationship) {
		this.relationship = relationship;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public Genre getGenre() {
		return this.genre;
	}

	public void setGenre(final Genre genre) {
		this.genre = genre;
	}

	public boolean isBan() {
		return this.ban;
	}

	public void setBan(final boolean ban) {
		this.ban = ban;
	}

	
	//Relationships
		private Chirp chirpReceives;
		private Chirp chirpWrites;

		@Valid
		@NotNull
		@ManyToOne(optional=false)
		public Chirp getChirpReceives() {
			return chirpReceives;
		}

		public void setChirpReceives(Chirp chirpReceives) {
			this.chirpReceives = chirpReceives;
		}
		@Valid
		@NotNull
		@ManyToOne(optional=false)
		public Chirp getChirpWrites() {
			return chirpWrites;
		}

		public void setChirpWrites(Chirp chirpWrites) {
			this.chirpWrites = chirpWrites;
		}

		
		
	
}
