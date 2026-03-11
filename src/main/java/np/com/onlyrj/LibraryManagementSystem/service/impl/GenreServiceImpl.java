package np.com.onlyrj.LibraryManagementSystem.service.impl;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import np.com.onlyrj.LibraryManagementSystem.exception.GenreException;
import np.com.onlyrj.LibraryManagementSystem.mapper.GenreMapper;
import np.com.onlyrj.LibraryManagementSystem.models.Genre;
import np.com.onlyrj.LibraryManagementSystem.payload.dto.GenreDTO;
import np.com.onlyrj.LibraryManagementSystem.repository.GenreRepository;
import np.com.onlyrj.LibraryManagementSystem.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;


    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {

        Genre genre = genreMapper.toEntity(genreDTO);
        Genre saveGenre = genreRepository.save(genre);

        return genreMapper.toDTO(saveGenre);
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO getGenreById(Long genreId) throws GenreException {
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                ()-> new GenreException("Genre not found...")
        );

        return genreMapper.toDTO(genre);
    }

    @Override
    public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                ()-> new GenreException("Genre not found")
        );

        genreMapper.updateEntityFromDTO(genreDTO, existingGenre);

        Genre updateGenre = genreRepository.save(existingGenre);

        return genreMapper.toDTO(updateGenre);
    }

    @Override
    public void deleteGenre(Long genreId) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                ()-> new GenreException("Genre not found")
        );
        existingGenre.setActive(false);
        genreRepository.save(existingGenre);
    }

    @Override
    public void hardDeleteGenre(Long genreId) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                ()-> new GenreException("Genre not found")
        );
        genreRepository.delete(existingGenre);
    }


    @Override
    public List<GenreDTO> getAllActiveGenreWithSubGenres() {
        List<Genre> topLevelGenres = genreRepository
                .findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
        return genreMapper.toDTOList(topLevelGenres);
    }

    @Override
    public List<GenreDTO> getTopLevelGenres() {
        List<Genre> topLevelGenres = genreRepository
                .findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
        return genreMapper.toDTOList(topLevelGenres);
    }


    @Override
    public long getTotalActiveGenres() {
        return genreRepository.countByActiveTrue();
    }

    @Override
    public long getBookCountByGenres(Long genreId) {
        return 0;
    }


}
