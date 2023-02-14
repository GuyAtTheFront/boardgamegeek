package iss.nus.workshop27_class.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import iss.nus.workshop27_class.model.Comment;
import iss.nus.workshop27_class.model.Game;
import iss.nus.workshop27_class.repositories.GamesMongoRepository;
import iss.nus.workshop27_class.repositories.GamesSqlRepository;

@Controller
public class GameController {
    
    @Autowired
    GamesSqlRepository gamesSqlRepo;

    @Autowired
    GamesMongoRepository gamesMongorepo;

    @GetMapping("/games")
    public String getGames(
                @RequestParam(defaultValue = "25") Integer limit,
                @RequestParam(defaultValue = "0") Integer offset,
                Model model) {
        List<Game> games = gamesSqlRepo.getGames(limit, offset);
        model.addAttribute("games", games);
        return "games";
    }

    @GetMapping("/games/{id}")
    public String getGameById(@PathVariable Integer id, Model model) {
        
        // Optional<Game> game = gamesSqlRepo.getGameById(id);
        Optional<Game> game = gamesMongorepo.findGameById(id);
        List<Comment> comments = gamesMongorepo.findComments(id);

        model.addAttribute("game", game.get());
        model.addAttribute("comments", comments);

        return "comments";
    }

    @PostMapping("/games/{id}/submit")
    public String submitComment(@PathVariable Integer id, 
                                @RequestBody MultiValueMap<String, String> form, Model model) {

        Optional<Game> game = gamesMongorepo.findGameById(id);

        if (game.isEmpty()) {
            return "redirect:/games/%s".formatted(id);
        }

        Comment comment = new Comment();
        comment.setGameId(id);
        comment.setUser(form.getFirst("name"));
        comment.setRating(Integer.parseInt(form.getFirst("rating")));
        comment.setCommentText(form.getFirst("comment"));

        gamesMongorepo.insertComment(comment);
        return "redirect:/games/%s".formatted(id);
    }
}
