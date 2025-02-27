package com.example.FoosBall.Service;

import com.example.FoosBall.Adapter.TeamAdapter;
import com.example.FoosBall.Dtos.TeamDto;
import com.example.FoosBall.Entity.Team;
import com.example.FoosBall.Exception.NameException;
import com.example.FoosBall.Exception.RecordNotFoundException;
import com.example.FoosBall.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class TeamServiceImpl implements Service<TeamDto>{

    @Autowired
    TeamRepository teamRepo;

    @Override
    public List<TeamDto> findAll() {
        TeamAdapter teamAdapter = new TeamAdapter();
        List<Team> teamList = teamRepo.findAll();
        List<TeamDto> teamDtoList = new ArrayList<>();
        for (Team team:teamList) {
            teamDtoList.add(teamAdapter.convertDaoToDto(team));
        }
        return teamDtoList;
    }

    @Override
    public TeamDto add(TeamDto dto) throws NameException {
        TeamAdapter teamAdapter = new TeamAdapter();
        if ( null != dto.getName() ) {
            if (!dto.getName().matches("^[a-zA-Z\\s]+")) {
                throw new NameException("Only alphabets and spaces are allowed for student's name.");
            }
        }
        Team team = teamRepo.save(teamAdapter.convertDtoToDao(dto));
        return teamAdapter.convertDaoToDto(team);
    }

    @Override
    public void deleteUsingId(Long id) {
        Optional<Team> teamOptional = teamRepo.findById(id);
        if(teamOptional.isPresent()){
            teamRepo.delete(teamOptional.get());
        }
        else {
            throw new RecordNotFoundException("Team not found");
        }
    }

    @Override
    public TeamDto update(Long id, TeamDto dto) {
        return null;
    }
}
