
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int userAccountId);

	@Query("select c from Chorbi c where c.ban=false")
	Collection<Chorbi> findAllNotBannedChorbies();

	@Query("select c from Chorbi c join c.givenTastes t where t.chorbi.id=?1")
	Collection<Chorbi> findAllChorbiesWhoLikeThem(int chorbiId);

}
