# cs50-polls
A telegram bot for cs50xIraq polls

You can repurpose the code for usage in any other live voting system. 

Go to `me.mouamle.cs50_polls.util.Projects#names` and fill the array with the voting options you want

## A Cool Feature
The bot implements a WebSocket server for providing the live feedback functionality

When you first connect to it, it sends you an event with all previous votes and will keep sending you live updates in the following format
```json
{
  "totalVotes": 0,
  "teamName": "",
  "votesCount": 0
}
```
