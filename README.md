# Boxscores Backend

Live backend: https://boxscores-backend-eoig.onrender.com/

This Spring Boot backend provides player and game boxscore data consumed by the frontend. Build with Maven and run as a standalone JAR.

## Table of contents

- [Live demo](#live-demo)
- [Prerequisites](#prerequisites)
- [Local development](#local-development)
- [Available commands](#available-commands)
- [Configuration](#configuration)
- [API endpoints](#api-endpoints)
- [Behavior notes](#behavior-notes)
- [License](#license)

## Live demo

Backend base URL: `https://boxscores-backend-eoig.onrender.com/`

Example:
- `GET https://boxscores-backend-eoig.onrender.com/players`

## Prerequisites

- JDK 17+ (or compatible)
- Maven 3.6+
- (Optional) Docker

## Local development

1. Open a terminal in `backend`
2. Install & build:
   - `mvn clean package`
3. Run:
   - `mvn spring-boot:run`
   - or `java -jar target/*.jar`
4. Default server port: `8080` (see configuration)

## Available commands

- `mvn clean package` — build the artifact
- `mvn spring-boot:run` — run the app directly
- `mvn test` — run unit tests

## Configuration

- `server.port` — change HTTP port (application.properties or env)
- CORS: development defaults to allow `http://localhost:3000`. When using the live frontend or deploying the frontend, add the frontend origin to allowed CORS origins (for example `https://boxscores-frontend.vercel.app`) and allow the live backend origin if your frontend needs it. On the deployed backend at `https://boxscores-backend-eoig.onrender.com/`, ensure CORS is configured to include your frontend production origin.

## API endpoints

All responses are JSON.

- `GET /players`  
  Returns list of players.

- `GET /playerdetails/{playerName}`  
  Accepts a combined name key (e.g. `FirstNameLastName`, hyphens/underscores/spaces tolerated) and returns matching player details (array).

- `GET /player/{playerName}/latestgame`  
  Returns the most recent `GameStats` for the player. Returns `404` if none.

- `GET /player/{playerName}/games`  
  Returns list of `GameStats`. Optional query params:
  - `gameType`
  - `season`
  - `opponent`

- `GET /player/{playerName}/games/filters`  
  Returns available `GameFilters` for the player (seasons, opponents, game types).

Path variable `playerName` may be:
- a numeric player ID (e.g. `1234`), or
- a combined-friendly name (letters, hyphens, underscores, spaces). The service will attempt to resolve combined names to the canonical player ID.

Example:
- `GET https://boxscores-backend-eoig.onrender.com/player/JohnDoe/games?season=2024&gameType=regular`

## Behavior notes

- The backend tolerates multiple naming conventions for incoming keys and returns conventional JSON objects used by the frontend.
- If a combined name resolves to multiple players the first match is used for ID resolution.
- When deploying behind a reverse proxy or cloud platform, ensure SPA routing (frontend) and CORS are configured so the frontend can reach the API.

## License

MIT
