package ru.javaops.bootjava.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.Restaurant;
import ru.javaops.bootjava.repository.RestaurantRepository;
import ru.javaops.bootjava.web.RestValidation;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantRepository repository;
    @GetMapping("/api/restaurants/{id}")
    public Restaurant get(@PathVariable int id){
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    @GetMapping("/api/restaurants")
    public List<Restaurant> getAllAsOfToday(){
        log.info("get All Restaurants");
        return repository.findAllAsOfToday(Sort.by(Sort.Direction.DESC, "voteCount"));
    }

    @DeleteMapping("/api/admin/restaurants/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        log.info("delete restaurant with id={}", id);
        repository.deleteExisted(id);
    }

    @PutMapping(value = "/api/admin/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id){
        log.info("update {} with id={}", restaurant, id);
        RestValidation.assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @PostMapping(value = "/api/admin/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant){
        log.info("create {}", restaurant);
        RestValidation.checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
