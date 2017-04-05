
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;
import domain.Taste;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select c from Chorbi c where c.userAccount.id = ?1")
	Chorbi findByUserAccountId(int userAccountId);

	@Query("select c from Chorbi c where c.ban=false")
	Collection<Chorbi> findAllNotBannedChorbies();

	@Query("select c from Chorbi c join c.givenTastes t where t.chorbi.id=?1 and c.ban=false")
	Collection<Chorbi> findAllChorbiesWhoLikeThem(int chorbiId);

	@Query("select c2 from Chorbi c join c.givenTastes t join t.chorbi c2 where c.id=?1 and c2.ban=0")
	Collection<Chorbi> findAllChorbiesWhoLikedByThisUser(int chorbiId);

	@Query("select c2 from Chorbi c join c.givenTastes t join t.chorbi c2 where c.id=?1")
	Collection<Chorbi> findAllChorbiesWhoLikedByThisUserForNotDoubleLike(int chorbiId);

	@Query("select t from Chorbi c join c.givenTastes t where t.chorbi.ban=0 and c.id=?1")
	Collection<Taste> findAllMyTastesWithoutBannedChorbies(int chorbiId);

	@Query("select c from Chorbi c join c.givenTastes t where t.chorbi.id=?1 and c.ban=0")
	Collection<Chorbi> findAllTastesToMeWithoutBannedChorbies(int chorbiId);

	@Query("select c from Chorbi c where c.coordinate.country like %?1% and c.coordinate.state like %?2% and c.coordinate.province like %?3%  and c.coordinate.city like %?4%")
	Collection<Chorbi> findByAllCoordinate(String country, String state, String province, String city);

	@Query("select c from Chorbi c where c.coordinate.country like %?1% and c.coordinate.province like %?2%  and c.coordinate.city like %?3%")
	Collection<Chorbi> findByCountryProvinceCity(String country, String province, String city);

	@Query("select c from Chorbi c where c.coordinate.country like %?1% and c.coordinate.state like %?2%  and c.coordinate.city like %?3%")
	Collection<Chorbi> findByCountryStateCity(String country, String state, String city);

	@Query("select c from Chorbi c where c.coordinate.country like %?1% and c.coordinate.city like %?2%")
	Collection<Chorbi> findByCountryCity(String country, String city);

}
