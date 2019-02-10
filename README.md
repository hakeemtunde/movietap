# Project Overview
As part of udacity android nanodegree programme, I was task with designing a movie app that fetch most popular/rated 
movies from [themoviedb.org](https://www.themoviedb.org/) and allow user to see video trailer.

## App Features
* **User Interface - Layout**
    * UI contains an element (settings menu) to toggle the sort order of the movies by: most popular, highest rated.
    * Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
    * UI contains a screen for displaying the details for a selected movie.
    * Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
    * Movie Details layout contains a section for displaying trailer videos and user reviews.
*  **User Interface - Function**
    * When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.
    * When a movie poster thumbnail is selected, the movie details screen is launched.
    * When a trailer is selected, app uses an Intent to launch the trailer.
    * In the movies detail screen, a user can tap a button to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.
    
* **Network API Implementation**
    * In a background thread, app queries the `/movie/popular` or `/movie/top_rated` API for the sort criteria specified in the settings menu.
    * App requests for related videos for a selected movie via the `/movie/{id}/videos` endpoint in a background thread and displays those details when the user selects a movie.
    * App requests for user reviews for a selected movie via the `/movie/{id}/reviews` endpoint in a background thread and displays those details when the user selects a movie.
    
* **Data Persistence**
    * The titles and IDs of the user’s favorite movies are stored using Room and are updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.
    * When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the database.
    
* **Android Architecture Components**
    * Database is implemented using Room. No other persistence libraries are used.
    * Database is not re-queried unnecessarily. LiveData is used to observe changes in the database and update the UI accordingly.
    * Database is not re-queried unnecessarily after rotation. Cached LiveData from ViewModel is used instead.
    
* ***Favorite movies (movie poster, synopsis, user rating, and release date) are stored and displayed when offline.*** 
    
## Project Configuration
### API KEY
To run this app you need to get [themoviedb.org](https://www.themoviedb.org/) api key and [Google Youtube](https://console.developers.google.com/) api key 
then add it to gradle.properties file in your home directory, 
follow first part of this [article](https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906)
for more details. 

### Note: 
When adding your api keys to the gradle.properties file, use the below key names for proper mapping

-  themoviedb key: `themovie_api_key`
-  Google Youtube key : `YOUTUBE_API_KEY`
 
e.g:
- ***`themovie_api_key="api_value`***
- ***`YOUTUBE_API_KEY="api_value`***
