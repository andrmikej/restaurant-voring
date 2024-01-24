package ru.javaops.bootjava.web.vote;

import org.springframework.stereotype.Component;
import ru.javaops.bootjava.model.User;
import ru.javaops.bootjava.model.Vote;
import ru.javaops.bootjava.to.VoteCreationTo;
import ru.javaops.bootjava.to.VoteTo;

@Component
public class VoteMapper {

    public Vote toVote(VoteCreationTo to, User user){
        return new Vote(to.getId(), to.getRestaurantId(), user);
    }

    public VoteTo toVoteTo(Vote vote){
        return new VoteTo(vote.getId(), vote.getDate(), vote.getUser().getId(), vote.getRestaurant().getId());
    }
}
