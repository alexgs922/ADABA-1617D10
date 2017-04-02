
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Chirp {

	//Constructors ---------------------------------------------------------------------------

	public Chirp() {
		super();
	}


	//Attributes ---------------------------------------------------------------------------

	private Date	moment;
	private String	subject;
	private String	text;
	private String	attachments;
	private boolean	copy;


	//Getters & Setters ----------------------------------------------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@URL
	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public boolean isCopy() {
		return this.copy;
	}

	public void setCopy(final boolean copy) {
		this.copy = copy;
	}
	
	//Relationships
		private Chorbi recipient;
		private Chorbi sender;
		private CreditCard creditCard;
		private Taste taste;
		private Coordinate coordinate;
		private Template template;
		
		
		@Valid
		@NotNull
		@OneToMany(mappedBy="chirpReceives")
		public Chorbi getRecipient() {
			return recipient;
		}

		public void setRecipient(Chorbi recipient) {
			this.recipient = recipient;
		}


		@Valid
		@NotNull
		@OneToMany(mappedBy="chirpWrites")
		public Chorbi getSender() {
			return sender;
		}

		public void setSender(Chorbi sender) {
			this.sender = sender;
		}
		
		@Valid
		@NotNull
		@OneToOne(optional=true)
		public CreditCard getCreditCard() {
			return creditCard;
		}

		public void setCreditCard(CreditCard creditCard) {
			this.creditCard = creditCard;
		}

		@Valid
		@NotNull
		@OneToMany()
		public Taste getTaste() {
			return taste;
		}

		public void setTaste(Taste taste) {
			this.taste = taste;
		}

		@Valid
		@NotNull
		@OneToOne
		public Coordinate getCoordinate() {
			return coordinate;
		}

		public void setCoordinate(Coordinate coordinate) {
			this.coordinate = coordinate;
		}


		@Valid
		@NotNull
		@OneToOne(optional=false)
		public Template getTemplate() {
			return template;
		}

		public void setTemplate(Template template) {
			this.template = template;
		}

		
	

}
