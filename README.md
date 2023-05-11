# Scoreboard


`startNewGame(HomeTeam, AwayTeam)`

Start new game for HomeTeam and AwayTeam with score 0-0

- When game already exists for HomeTeam or AwayTeam, it throws an exception
- When HomeTeam is equal to AwayTeam, it throws an exception
- When HomeTeam or AwayTeam is null or empty, it throws an exception

`updateGame(HomeTeam, AwayTeam, HomeTeamScore, AwayTeamScore)`

Update score for existing match 

- When HomeTeam or AwayTeam is null or empty, it throws an exception
- When game for the required pair doesn't exist, it throws an exception
- When new HomeTeamScore or AwayTeamScore is lower than 0, it throws an exception

`finishGame(HomeTeam, AwayTeam)`

Remove required match for {HomeTeam, AwayTeam} from the scoreboard

- When HomeTeam or AwayTeam is null or empty, it throws an exception
- When game doesn't exist for the required pair, it throws an exception


`getSummary()`

Get summary of games in progress. List is sorted by total score, then by timestamp

- When there is no game in progress, it returns empty list


Assumed requirements:
- Teams names are case-insensitive
- Teams names should be saved in upper case
- Score needs to be a natural number 
- Score can be updated to lower or higher value
- No multithreading
- There is no need to save memory