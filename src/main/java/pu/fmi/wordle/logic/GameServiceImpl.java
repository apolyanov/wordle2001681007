package pu.fmi.wordle.logic;

import org.springframework.stereotype.Component;
import pu.fmi.wordle.model.Game;
import pu.fmi.wordle.model.GameRepo;
import pu.fmi.wordle.model.Guess;
import pu.fmi.wordle.model.WordRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class GameServiceImpl implements GameService {

  final GameRepo gameRepo;

  final WordRepo wordRepo;

  public GameServiceImpl(GameRepo gameRepo, WordRepo wordRepo) {
    this.gameRepo = gameRepo;
    this.wordRepo = wordRepo;
  }

  @Override
  public Game startNewGame() {
    var game = new Game();
    game.setId(UUID.randomUUID().toString());
    game.setStartedOn(LocalDateTime.now());
    game.setWord(wordRepo.getRandom());
    game.setGuesses(new ArrayList<>(game.getMaxGuesses()));
    return game;
  }

  @Override
  public Game getGame(String id) {
    var game = gameRepo.get(id);
    if (game == null) throw new GameNotFoundException(id);
    return game;
  }

  @Override
  public Game makeGuess(String id, String word) throws GameNotFoundException {
    Game game = gameRepo.get(id);

    if (game == null) {
      throw new GameNotFoundException(id);
    }

    if (!wordRepo.exists(word)) {
      throw new UnknownWordException(word);
    }

    Guess newGuess = new Guess();
    newGuess.checkGuess(word, game.getWord());

    game.getGuesses().add(newGuess);
    return game;
  }
}
