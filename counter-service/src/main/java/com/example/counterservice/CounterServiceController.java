package com.example.counterservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterServiceController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(path = "/api/score/scores", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getScore() {
        String pattern = "{ \"wins\":\"%s\", \"losses\":\"%s\", \"ties\": \"%s\"}";
        return String.format(pattern, Score.WINS, Score.LOSSES, Score.TIES);
    }

    @PutMapping(path = "/api/score/scores", produces = MediaType.APPLICATION_JSON_VALUE)
    public String update(
            @RequestParam("wins") int wins,
            @RequestParam("losses") int losses,
            @RequestParam("ties") int ties) {
        Score.WINS = wins;
        Score.TIES = ties;
        Score.LOSSES = losses;
        String pattern = "{ \"wins\":\"%s\", \"losses\":\"%s\", \"ties\": \"%s\"}";
        return String.format(pattern, Score.WINS, Score.LOSSES, Score.TIES);
    }

    @GetMapping(path = "/api/score/wins", produces = MediaType.TEXT_PLAIN_VALUE)
    public int getWins() {
        return Score.WINS;
    }

    @GetMapping(path = "/api/score/losses", produces = MediaType.TEXT_PLAIN_VALUE)
    public int getLosses() {
        return Score.LOSSES;
    }

    @GetMapping(path = "/api/score/ties", produces = MediaType.TEXT_PLAIN_VALUE)
    public int getTies() {
        return Score.TIES;
    }

    @PostMapping(path = "/api/score/wins", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> incrementWins() {
        return new ResponseEntity<>(++Score.WINS, HttpStatus.OK);
    }

    @PostMapping(path = "/api/score/losses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> incrementLosses() {
        return new ResponseEntity<>(++Score.LOSSES, HttpStatus.OK);
    }

    @PostMapping(path = "/api/score/ties", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> incrementTies() {
        return new ResponseEntity<>(++Score.TIES, HttpStatus.OK);
    }
}
