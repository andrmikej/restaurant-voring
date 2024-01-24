package ru.javaops.bootjava.web.vote;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.repository.VoteRepository;
import ru.javaops.bootjava.to.VoteCreationTo;
import ru.javaops.bootjava.to.VoteTo;
import ru.javaops.bootjava.web.AuthUser;
import ru.javaops.bootjava.web.RestValidation;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final VoteRepository repository;
    private final VoteMapper mapper;

    @GetMapping("/api/votes/{id}")
    public VoteTo get(@PathVariable int id){
        return mapper.toVoteTo(repository.getExisted(id));
    }

    @GetMapping("/api/votes")
    public List<VoteTo> getAll(@RequestParam(required = false) Integer restaurantId, @RequestParam(required = false) Integer userId){
        List<Vote> votes = repository.findAllByRestaurantIdAndUserId(restaurantId, userId);
        return votes.stream().map(mapper::toVoteTo).toList();
    }

//    @DeleteMapping("/api/profile/votes/{id}")
//    public void delete(@PathVariable int id){
//        repository.deleteExisted(id);
//    }

    @PutMapping(value = "/api/profile/votes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody VoteCreationTo to, @AuthenticationPrincipal AuthUser authUser){
        RestValidation.assureIdConsistent(to, id);
        Vote vote = mapper.toVote(to, authUser.getUser());
        log.info("update {} with id={}", vote, id);
        repository.update(vote);
    }

    @PostMapping(value = "/api/profile/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> vote(@Valid @RequestBody VoteCreationTo to, @AuthenticationPrincipal AuthUser authUser){
        RestValidation.checkNew(to);
        Vote vote = mapper.toVote(to, authUser.getUser());
        log.info("create {}", vote);
        Vote created = repository.vote(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/profile/votes/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
