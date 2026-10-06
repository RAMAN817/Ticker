
# Ticker - A finance news and stock trading app 
> It is a work in progress 🚧.
<img width="405" height="877" alt="Home" src="https://github.com/user-attachments/assets/a9e7c3a1-4d40-43ed-8011-f59d5b832d7e" />
<img width="397" height="874" alt="Discover" src="https://github.com/user-attachments/assets/c1fdbb96-d3ea-49a3-a51a-70da9ca7c3d0" />




## Tech Stack 


## Architecture
Ticker app follows the Google's recommended MVMM architecture. 
UI (Fragments) → ViewModel (StateFlow, sealed UI states) → Repository → Retrofit API / Room cache
- **API key safety:** an OkHttp interceptor injects the Finnhub token only on a dedicated `@Named("finnhub")` client, so it never leaks to other requests.
- **Caching:** stock quotes are stored in Room to avoid refetching on every load.


## Testing 

