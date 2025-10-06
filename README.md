# Light Novel Reader (WIP) 

An open-source Android app for reading and managing light novels.

This app is being developed using **Kotlin**, **Jetpack Compose**, and follows **MVI architecture** with clean principles.

> Status: Actively under development  
> MVP Focus: Online-first reading experience and bookmarking

## Features (Current MVP)

- Browse novel details (title, author, cover, description)
- View chapters list and open chapter reader
- Add/remove novels to/from **library bookmarks**
- Persist bookmarks locally using **Room**
- Fetch content dynamically from custom API
- Language support (`en`, `pt-BR`, `ja`, `de`)
- Highlight individual words in chapter text based on learning progress

## Disclaimer

The developer of this application does not have any affiliation with the content providers available.

## Screenshots :camera:

| Views    | Dark                                                       | Light                                                        |
| -------- | ---------------------------------------------------------- | ------------------------------------------------------------ |
| Library  | ![library_view_dark](screenshots/home-dark.jpeg)           | ![library_view_light](screenshots/home-light.jpeg)           |
| Book     | ![book_view_dark](screenshots/novel-dark.jpeg)             | ![book_view_light](screenshots/novel-light.jpeg)             |
| Reader   | ![book_view_dark](screenshots/reading-dark.jpeg)           | ![book_view_light](screenshots/reading-light.jpeg)           |

## Tech Stack

| Layer       | Technology             |
|-------------|-------------------------|
| UI          | Jetpack Compose         |
| State       | Kotlin Flows + MVI      |
| Local DB    | Room                    |
| Networking  | Kotlinx Serialization   |
| DI          | Koin                    |
| Architecture| Clean (Domain/Data/UI)  |

## Getting Started

1. Clone the repo
2. Open in Android Studio
3. Create a `local.properties` file in the project root and add your Supabase keys:
   
   - `SUPABASE_URL=https://your-project.supabase.co`
   - `SUPABASE_ANON_KEY=your-anon-key`
5. Run on an emulator or device

## Note

This is a personal/experimental project. Contributions are welcome, but the codebase is evolving quickly.

## License

Licensed under the [MIT License](./LICENSE).

