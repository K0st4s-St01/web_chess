package com.kstoi.web_chess.services;

import com.kstoi.web_chess.mappers.PieceMapper;
import com.kstoi.web_chess.models.dtos.PieceDto;
import com.kstoi.web_chess.models.entities.Piece;
import com.kstoi.web_chess.repos.GameRepository;
import com.kstoi.web_chess.repos.PieceRepository;
import com.kstoi.web_chess.repos.PieceRepository;
import com.kstoi.web_chess.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@AllArgsConstructor
public class PieceService {
    private PieceMapper mapper;
    private final PieceRepository repository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public Map<String,Object> create(PieceDto dto) throws Exception {
        Piece entity = mapper.toEntity(dto
                , gameRepository.findById(dto.getGame()).orElseThrow()
                , userRepository.findById(dto.getUser()).orElseThrow());
        repository.save(entity);
            return Map.of("result","successful");
    }

    public Map<String,Object> read(Long id){
        var entity = repository.findById(id).orElseThrow();
        return Map.of("dtos",mapper.toDto(entity,entity.getGame().getId(),entity.getUser().getUsername())
        ,"result","successfull");
    }

    public Map<String,Object> read(Integer page,Integer size) {
        var entities = repository.findAll(PageRequest.of(page, size));
        var dtos = new ArrayList<PieceDto>();
        for (var entity : entities ) {
             dtos.add(mapper.toDto(entity,entity.getGame().getId(),entity.getUser().getUsername());

        }
        return Map.of("result","successful",
                "pages",repository.count()/size,
                "dtos",dtos);
    }


    public Map<String,Object> update(PieceDto dto) throws Exception {
        if(repository.existsById(dto.getId())) {
            Piece entity = mapper.toEntity(dto
                    , gameRepository.findById(dto.getGame()).orElseThrow()
                    , userRepository.findById(dto.getUser()).orElseThrow());
            repository.save(entity);
            return Map.of("result","successful");
        }
        throw new Exception(dto.getId()+" game does not exist");
    }
    public Map<String,Object> delete(Long id){
        repository.deleteById(id);
        return Map.of("result","successful");
    }

}
