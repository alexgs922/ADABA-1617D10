
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {

	@Query("select m from Chorbi c join c.chirpReceives m where m.copy=false and c.id=?1")
	Collection<Chirp> myRecivedMessages(int actorId);

	@Query("select m from Chorbi c join c.chirpWrites m where m.copy=true and c.id=?1")
	Collection<Chirp> mySendedMessages(int actorId);

}
