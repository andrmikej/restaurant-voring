package ru.javaops.bootjava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.error.IllegalRequestDataException;
import ru.javaops.bootjava.error.NotFoundException;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.web.RestValidation;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote>{

    @Query("SELECT v FROM Vote v WHERE (:restaurantId is null or v.restaurant.id=:restaurantId) and (:userId is null or v.user.id=:userId)")
    List<Vote> findAllByRestaurantIdAndUserId(@Param("restaurantId") Integer restaurantId, @Param("userId") Integer userId);

//    @Transactional
//    default Vote vote(Vote vote){
//        if (vote.isNew()){
//            return save(vote);
//        } else if (getVoteByUserId(vote.getId(), vote.getUser().getId()) == null) {
//            throw new NotFoundException("Your today's vote with id=" + vote.getId() + " not found");
//        } else if (!LocalTime.now().isBefore(LocalTime.of(11, 0))) {
//            throw new IllegalRequestDataException("You can't change your vote after 11:00 AM");
//        } else {
//            return save(vote);
//        }
//    }

    @Transactional
    default Vote vote(Vote newVote){
        Vote found = getVoteByUserId(newVote.getUser().getId());
        if (found == null){
            return save(newVote);
        } else  {
            validateUpdate(found, newVote);
            return found;
        }
    }
    @Transactional
    default void update (Vote updatedVote){
        Vote found = getVoteByIdAndUserId(updatedVote.getId(), updatedVote.getUser().getId()).orElseThrow(() -> new NotFoundException("Your today's vote with id=" + updatedVote.getId() + " not found"));
        validateUpdate(found, updatedVote);
    }

    private void validateUpdate(Vote found, Vote newVote){
        if (!LocalTime.now().isBefore(LocalTime.of(11, 0))){
            throw new IllegalRequestDataException("You can't change your vote after 11:00 AM");
        }
        found.setRestaurant(newVote.getRestaurant());
    }

    @Query("SELECT v FROM Vote v WHERE v.id=:id and v.user.id=:userId and v.date=current_date()")
    Optional<Vote> getVoteByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId and v.date=current_date()")
    Vote getVoteByUserId(@Param("userId") Integer userId);
}
