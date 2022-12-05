package days;

import input.Cookie;
import template.Template;

public class Day2 extends Template {
    public Day2(int day, Cookie cookie) {
        super(day, cookie);
    }

    enum CHOICE {
        ROCK ('A', 'X', 1),
        PAPER ('B', 'Y', 2),
        SCISSOR ('C', 'Z', 3);

        private final char opponent, mine;
        private final int points;

        CHOICE(char opponent, char mine, int points) {
            this.opponent = opponent;
            this.mine = mine;
            this.points = points;
        }

        public char getOpponent() {
            return opponent;
        }

        public char getMine() {
            return mine;
        }

        public int getPoints() {
            return points;
        }
    }

    enum MATCH {
        LOST (0, 'X'),
        DRAW (3, 'Y'),
        WON (6,  'Z');

        private final int score;
        private final char ending;

        MATCH(int score, char ending) {
            this.score = score;
            this.ending = ending;
        }
    }

    private int calculateScoreOfRound(CHOICE opponent, CHOICE mine) {
        int score = mine.getPoints();
        if(opponent.toString().equalsIgnoreCase("ROCK") &&
            mine.toString().equalsIgnoreCase("PAPER") ||
        opponent.toString().equalsIgnoreCase("SCISSOR") &&
            mine.toString().equalsIgnoreCase("ROCK") ||
        opponent.toString().equalsIgnoreCase("PAPER") &&
            mine.toString().equalsIgnoreCase("SCISSOR")) {
            score += MATCH.WON.score;
        } else if(opponent.toString().equalsIgnoreCase(mine.toString())) {
            score += MATCH.DRAW.score;
        }
        return score;
    }

    private CHOICE findChoice(char character) {
        return switch (character) {
            case 'A','X' -> CHOICE.ROCK;
            case 'B','Y' -> CHOICE.PAPER;
            case 'C','Z' -> CHOICE.SCISSOR;
            default -> throw new IllegalStateException("Unexpected value: " + character);
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveFirst() {
        return (T) getContent().stream()
                .map(str -> {
                    char p1 = str.toCharArray()[0];
                    char p2 = str.toCharArray()[2];
                    return calculateScoreOfRound(findChoice(p1), findChoice(p2));
                })
                .reduce(Integer::sum)
                .orElse(0);
    }

    private MATCH findEnding(char character) {
        return switch (character) {
            case 'X' -> MATCH.LOST;
            case 'Y' -> MATCH.DRAW;
            default -> MATCH.WON;
        };
    }

    private CHOICE decideChoice(CHOICE opponent, MATCH ending) {
        switch (opponent) {
            case ROCK:
                if (ending == MATCH.WON) return CHOICE.PAPER;
                else if (ending == MATCH.DRAW) return opponent;
                else return CHOICE.SCISSOR;
            case PAPER:
                if (ending == MATCH.WON) return CHOICE.SCISSOR;
                else if (ending == MATCH.DRAW) return opponent;
                else return CHOICE.ROCK;
            case SCISSOR:
                if (ending == MATCH.WON) return CHOICE.ROCK;
                else if (ending == MATCH.DRAW) return opponent;
                else return CHOICE.PAPER;
            default:
                return CHOICE.SCISSOR;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveSecond() {
        return (T) getContent().stream()
                .map(str -> {
                    char p1 = str.toCharArray()[0];
                    char p2 = str.toCharArray()[2];
                    return calculateScoreOfRound(findChoice(p1), decideChoice(findChoice(p1), findEnding(p2)));
                })
                .reduce(Integer::sum)
                .orElse(0);
    }
}
