package com.kstoi.web_chess.services;

import com.kstoi.web_chess.mappers.GameMapper;
import com.kstoi.web_chess.models.dtos.GameDto;
import com.kstoi.web_chess.models.entities.Game;
import com.kstoi.web_chess.repos.GameRepository;
import com.kstoi.web_chess.repos.PieceRepository;
import com.kstoi.web_chess.repos.GameRepository;
import com.kstoi.web_chess.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@AllArgsConstructor
public class GameService {
    private GameMapper mapper;
    private final GameRepository repository;
    private final PieceRepository pieceRepository;
    private final UserRepository userRepository;

    public Map<String,Object> create(GameDto dto) throws Exception {
        Game entity = mapper.toEntity(dto, userRepository.findAllById(dto.getUsers()), pieceRepository.findAllById(dto.getPieces()));
        repository.save(entity);
        return Map.of("result","successful");
    }

    public Map<String,Object> read(Long id){
        var entity = repository.findById(id).orElseThrow();
        var ids = new ArrayList<Long>();
        var ids2 = new ArrayList<String>();
        for(var p : entity.getPieces()) {
            ids.add(p.getId());
        }
        for(var p : entity.getUsers()) {
            ids2.add(p.getUsername());
        }
        return Map.of("dtos",mapper.toDto(entity,ids2,ids)
        ,"result","successfull");
    }

    public Map<String,Object> read(Integer page,Integer size) {
        var entities = repository.findAll(PageRequest.of(page, size));
        var dtos = new ArrayList<GameDto>();
        for (var entity : entities ) {
            var ids = new ArrayList<Long>();
            var ids2= new ArrayList<String>();
            for (var p : entity.getPieces()) {
                ids.add(p.getId());
            }
            for (var p : entity.getUsers()) {
                ids2.add(p.getUsername());
            }
             dtos.add(mapper.toDto(entity, ids2, ids));

        }
        return Map.of("result","successful",
                "pages",repository.count()/size,
                "dtos",dtos);
    }


    public Map<String,Object> update(GameDto dto) throws Exception {
        if(repository.existsById(dto.getId())) {
            Game entity = mapper.toEntity(dto, userRepository.findAllById(dto.getUsers()), pieceRepository.findAllById(dto.getPieces()));
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
