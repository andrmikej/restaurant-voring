package ru.javaops.bootjava.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.bootjava.model.MenuItem;
import ru.javaops.bootjava.repository.MenuItemRepository;
import ru.javaops.bootjava.to.MenuItemTo;
import ru.javaops.bootjava.web.RestValidation;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemController {

    private final MenuItemRepository repository;

    @GetMapping("/api/menu-items/{id}")
    public MenuItem get(@PathVariable int id){
        return repository.getExisted(id);
    }

    @GetMapping("/api/menu-items")
    public List<MenuItem> getAll(@RequestParam(required = false) Integer restaurantId, @RequestParam(required = false) LocalDate date){
        return repository.findAllByDateAndRestaurantId(restaurantId, date);
    }

    @PutMapping(value = "/api/admin/menu-items/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItemTo menuItemTo, @PathVariable int id){
        MenuItem menuItem = new MenuItem(menuItemTo);
        log.info("update {} with id={}", menuItem, id);
        RestValidation.assureIdConsistent(menuItem, id);
        repository.save(menuItem);
    }

    @PostMapping(value = "/api/admin/menu-items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@Valid @RequestBody MenuItemTo menuItemTo){
        MenuItem menuItem = new MenuItem(menuItemTo);
        log.info("create {}", menuItem);
        RestValidation.checkNew(menuItem);
        MenuItem created = repository.save(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/menu-items/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/api/admin/menu-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        repository.deleteExisted(id);
    }
}
