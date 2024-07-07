package com.kstoi.web_chess.services;

import com.kstoi.web_chess.mappers.UserMapper;
import com.kstoi.web_chess.models.dtos.UserDto;
import com.kstoi.web_chess.models.entities.User;
import com.kstoi.web_chess.repos.GameRepository;
import com.kstoi.web_chess.repos.PieceRepository;
import com.kstoi.web_chess.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Map;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {
    private UserMapper mapper;
    private final UserRepository repository;
    private final PieceRepository pieceRepository;
    private final GameRepository gameRepository;

    public Map<String,Object> create(UserDto dto) throws Exception {
        if(!repository.existsById(dto.getUsername())) {
            User entity = mapper.toEntity(dto, pieceRepository.findAllById(dto.getPieces())
                    , dto.getGame() != null ?
                            gameRepository.findById(dto.getGame()).orElseThrow()
                            : null);
            repository.save(entity);
            return Map.of("result","successful");
        }
        throw new Exception("username taken");
    }

    public Map<String,Object> read(String id){
        var entity = repository.findById(id).orElseThrow();
        var ids = new ArrayList<Long>();
        for(var p : entity.getPieces()) {
            ids.add(p.getId());
        }
        return Map.of("dtos",mapper.toDto(entity,ids,entity.getGame().getId())
        ,"result","successfull");
    }

    public Map<String,Object> read(Integer page,Integer size) {
        var entities = repository.findAll(PageRequest.of(page, size));
        var dtos = new ArrayList<UserDto>();
        for (var entity : entities ) {
            var ids = new ArrayList<Long>();
            for (var p : entity.getPieces()) {
                ids.add(p.getId());
            }
             dtos.add(mapper.toDto(entity, ids, entity.getGame().getId()));

        }
        return Map.of("result","successful",
                "pages",repository.count()/size,
                "dtos",dtos);
    }


    public Map<String,Object> update(UserDto dto) throws Exception {
        if(repository.existsById(dto.getUsername())) {
            User entity = mapper.toEntity(dto, pieceRepository.findAllById(dto.getPieces())
                    , dto.getGame() != null ?
                            gameRepository.findById(dto.getGame()).orElseThrow()
                            : null);
            repository.save(entity);
            return Map.of("result","successful");
        }
        throw new Exception("user with username "+dto.getUsername()+"does not exist");
    }
    public Map<String,Object> delete(String username){
        repository.deleteById(username);
        return Map.of("result","successful");
    }

}
