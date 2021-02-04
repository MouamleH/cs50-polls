package me.mouamle.cs50_polls.util;

import me.mouamle.cs50_polls.events.VoteEvent;
import org.greenrobot.eventbus.EventBus;
import org.mapdb.HTreeMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class VotesService {

    private static final ConcurrentHashMap<Integer, String> votes = new ConcurrentHashMap<>();

    private static final HTreeMap<Integer, String> db = Storage.loadDataMap();

    static {
        for (Map.Entry<Integer, String> entry : db.entrySet()) {
            votes.put(entry.getKey(), entry.getValue());
        }
    }

    public static void vote(int userId, String vote) {
        String oldVote = votes.get(userId);
        votes.put(userId, vote);
        db.put(userId, vote);
        Storage.commit();

        if (oldVote != null) {
            final int oldFrequency = Collections.frequency(votes.values(), oldVote);
            EventBus.getDefault().post(new VoteEvent(votes.size(), oldVote, oldFrequency));
        }

        final int frequency = Collections.frequency(votes.values(), vote);
        EventBus.getDefault().post(new VoteEvent(votes.size(), vote, frequency));
    }

    public static List<VoteEvent> getAllVotes() {
        final Collection<String> allVotes = votes.values();
        int totalVotesCount = allVotes.size();

        Set<String> targets = new HashSet<>(allVotes);

        return targets.stream()
                .map(vote -> new VoteEvent(totalVotesCount, vote, Collections.frequency(allVotes, vote)))
                .collect(Collectors.toList());
    }

}
