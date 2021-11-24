package ca.concordia.poll.app.dao;

import ca.concordia.poll.core.Choice;

import java.util.List;
import java.util.Optional;

public interface ChoiceDAO {

    public Optional<Choice> get(String pollID, int id);

    public Choice save(Choice choice, String pollID, int id);

    public List<Choice> getAll(String pollID);

    public List<Choice> saveAll(List<Choice> choices);

}
