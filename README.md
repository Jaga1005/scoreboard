# Scoreboard


`StartNewGame(HomeTeam, AwayTeam)`

Start new game for HomeTeam and AwayTeam with score 0-0

- When game already exists for HomeTeam or AwayTeam, it throws an error
- When HomeTeam is equal to AwayTeam, it throws an error
- When HomeTeam or AwayTeam is null or empty, it throws an error

`UpdateScore(HomeTeam, AwayTeam, HomeTeamScore, AwayTeamScore)`

Update score for existing match 

- When game for the required pair doesn't exist, it throws an error
- When new HomeTeamScore or AwayTeamScore is lower than 0, it throws an error


`FinishGame(HomeTeam, AwayTeam)`

Remove required pair {HomeTeam, AwayTeam} from the scoreboard

- When game doesn't exist for the required pair, it throws an error


`GetSummary()`

Get summary of games in progress. List is sorted by total score, then by timestamp

- When there is no game in progress, it returns empty list

