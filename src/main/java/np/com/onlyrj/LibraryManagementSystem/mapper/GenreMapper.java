package np.com.onlyrj.LibraryManagementSystem.mapper;

import lombok.RequiredArgsConstructor;
import np.com.onlyrj.LibraryManagementSystem.models.Genre;
import np.com.onlyrj.LibraryManagementSystem.payload.dto.GenreDTO;
import np.com.onlyrj.LibraryManagementSystem.repository.GenreRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreMapper {
    
    private final GenreRepository genreRepository;

    public GenreDTO toDTO(Genre savedGenre) {
        if (savedGenre == null) {
            return null;
        }


        GenreDTO dto = GenreDTO.builder()
                .id(savedGenre.getId())
                .code(savedGenre.getCode())
                .name(savedGenre.getName())
                .description(savedGenre.getDescription())
                .displayOrder(savedGenre.getDisplayOrder())
                .active(savedGenre.getActive())
                .createdAt(savedGenre.getCreatedAt())
                .updatedAt(savedGenre.getUpdatedAt())
                .build();

        if (savedGenre.getParentGenre() != null) {
            dto.setParentGenreId(savedGenre.getParentGenre().getId());
            dto.setParentGenreName(savedGenre.getParentGenre().getName());
        }

        if(savedGenre.getSubGenre()!=null && !savedGenre.getSubGenre().isEmpty()){
            dto.setSubGenre(savedGenre.getSubGenre().stream()
                    .filter(subGenre -> savedGenre.getActive())
                    .map(this::toDTO).collect(Collectors.toList()));

        }

//        dto.setBookCount((long) (saveGenre.getBook));

        return dto;
    }

    public Genre toEntity(GenreDTO genreDTO){
        if(genreDTO == null){
            return null;
        }

        Genre genre = Genre.builder()
                .code(genreDTO.getCode())
                .name(genreDTO.getName())
                .description(genreDTO.getDescription())
                .displayOrder(genreDTO.getDisplayOrder())
                .active(true)
                .build();

        if(genreDTO.getParentGenreId()!=null){
            genreRepository.findById(genreDTO.getParentGenreId())
                    .ifPresent(genre::setParentGenre);
        }
        return genre;
    }
    public void updateEntityFromDTO(GenreDTO dto, Genre existingGenre){
        if(dto == null || existingGenre == null){
            return;
        }

        existingGenre.setCode(dto.getCode());
        existingGenre.setName(dto.getName());
        existingGenre.setDescription(dto.getDescription());
        existingGenre.setDisplayOrder(dto.getDisplayOrder());
        if(dto.getActive() != null){
            existingGenre.setActive(dto.getActive());
        }

        if(dto.getParentGenreId() != null){
            genreRepository.findById(dto.getParentGenreId())
                    .ifPresent(existingGenre::setParentGenre);
        }
    }

    public List<GenreDTO> toDTOList(List<Genre> genreList){
        return genreList.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}