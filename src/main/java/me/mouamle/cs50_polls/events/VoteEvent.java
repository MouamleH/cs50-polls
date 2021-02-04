package me.mouamle.cs50_polls.events;

import java.io.Serializable;
import java.util.Objects;

public class VoteEvent implements Serializable {

    private long totalVotes;

    private String teamName;
    private long votesCount;

    public VoteEvent() { }

    public VoteEvent(long totalVotes, String teamName, long votesCount) {
        this.totalVotes = totalVotes;
        this.teamName = teamName;
        this.votesCount = votesCount;
    }

    public long getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(long totalVotes) {
        this.totalVotes = totalVotes;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public long getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(long votesCount) {
        this.votesCount = votesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteEvent voteEvent = (VoteEvent) o;
        return totalVotes == voteEvent.totalVotes &&
                votesCount == voteEvent.votesCount &&
                Objects.equals(teamName, voteEvent.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalVotes, teamName, votesCount);
    }

    @Override
    public String toString() {
        return "VoteEvent{" +
                "totalVotes=" + totalVotes +
                ", teamName='" + teamName + '\'' +
                ", votesCount=" + votesCount +
                '}';
    }

}
