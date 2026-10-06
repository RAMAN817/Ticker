# Ticker 📈
A finance news and stock tracker for Android, powered by the Finnhub API.

> 🚧 Work in progress

<p float="left">
  <img src="https://github.com/user-attachments/assets/a9e7c3a1-4d40-43ed-8011-f59d5b832d7e" width="250" alt="Home"/>
  <img src="https://github.com/user-attachments/assets/c1fdbb96-d3ea-49a3-a51a-70da9ca7c3d0" width="250" alt="Discover"/>
</p>

## Features
- Live market news feed with featured and regular article cards
- Distraction-free article reader (Readability4J)
- Discover screen: watchlist, large-caps and indices, cached with Room
- Stock detail view for symbols mentioned in articles

## Tech Stack
Kotlin · MVVM · Hilt · Retrofit · OkHttp · Room · StateFlow · Navigation Component + Safe Args · ViewBinding · JUnit

## Architecture
Ticker follows Google's recommended MVVM architecture:

UI (Fragments) → ViewModel (StateFlow, sealed UI states) → Repository → Retrofit API / Room cache

- **API key safety:** an OkHttp interceptor injects the Finnhub token only on a dedicated `@Named("finnhub")` client, so it never leaks to other requests.
- **Caching:** stock quotes are stored in Room to avoid refetching on every load.

## Testing
ViewModel logic is unit tested with JUnit on the JVM. `HomeViewModel` tests use a fake repository with test data instead of the real API, then assert that the emitted UI states match the expected results.

## Getting Started
1. Clone the repo
2. Get a free API key at [finnhub.io](https://finnhub.io)
3. Add to `local.properties`: `FINNHUB_API_KEY=your_key_here`
4. Build and run in Android Studio

## Roadmap
- [x] News feed and article reader
- [x] Discover screen with Room caching
- [x] HomeViewModel unit tests
- [ ] More test coverage (repositories, other ViewModels)
- [ ] Search
- [ ] Jetpack Compose migration

