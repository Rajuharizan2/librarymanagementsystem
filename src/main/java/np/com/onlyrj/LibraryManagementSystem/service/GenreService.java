package np.com.onlyrj.LibraryManagementSystem.service;


import np.com.onlyrj.LibraryManagementSystem.exception.GenreException;
import np.com.onlyrj.LibraryManagementSystem.models.Genre;
import np.com.onlyrj.LibraryManagementSystem.payload.dto.GenreDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface GenreService {
    GenreDTO createGenre(GenreDTO genre);

    List<GenreDTO> getAllGenres();

    GenreDTO getGenreById(Long genreId) throws GenreException;

    GenreDTO updateGenre(Long genreId, GenreDTO genre) throws GenreException;

    void deleteGenre(Long genreId) throws GenreException;

    void hardDeleteGenre(Long genreId) throws GenreException;

    List<GenreDTO> getAllActiveGenreWithSubGenres();

    List<GenreDTO> getTopLevelGenres();

//    Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);

    long getTotalActiveGenres();

    long getBookCountByGenres(Long genreId);

}
