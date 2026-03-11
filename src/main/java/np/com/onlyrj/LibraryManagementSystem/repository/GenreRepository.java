package np.com.onlyrj.LibraryManagementSystem.repository;

import np.com.onlyrj.LibraryManagementSystem.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findByActiveTrueOrderByDisplayOrderAsc();

    List<Genre> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

    List<Genre> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(
            Long parentGenreId
    );

    long countByActiveTrue();


//    @Query("select count(b) form book b where b.genre.id=:genreId")
//    long countBooksByGenre(@Param("genreId") Long genreId);

}