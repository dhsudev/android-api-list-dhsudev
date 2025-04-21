<div align="center"> 
<table style="border: none;">
    <tr>
      <td style="border: none;"><img src="app/src/main/res/drawable/logo.png" alt="app_logo" height="200" /></td>
      <td style="border: none;" valign="middle"><h1>MagicLook: another MTG Card Browser</h1></td>
    </tr>
  </table>
</div>

<br clear="left"/>
<p align="center">  
  This is an Android app built with Kotlin and Jetpack Compose that allows users to browse Magic: The Gathering cards. It fetches card data from the Scryfall API, displaying cards in a responsive grid with infinite scrolling. Mana symbols are correctly rendered, and card properties like name, type, colors, and mana cost are clearly presented, along with color-based emojis.
  <br>
</p>

## Features

#### 📖 **Extensive Card Library**
Browse a comprehensive list of Magic: The Gathering cards.
#### 📡 **Real-time Data**
Fetches card details directly from Scryfall API, ensuring you have access to the latest data.
#### 📜 **Infinite Scrolling**
Load more cards seamlessly as you scroll, providing a smooth and continuous browsing experience. 

#### 🔮 **Mana & ability Symbols**
The mana and the ability symbols are shown correctly in each card using it's symbols.
<div align="center">
  <table>
    <th colspan="2">Example Symbols</th>
    <tr>
      <td><img src="https://svgs.scryfall.io/card-symbols/W.svg" alt="White Mana" height="20"></td>
      <td>White Mana</td>
    </tr>
    <tr>
      <td><img src="https://svgs.scryfall.io/card-symbols/U.svg" alt="Blue Mana" height="20"></td>
      <td>Blue Mana</td>
    </tr>
    <tr>
      <td><img src="https://svgs.scryfall.io/card-symbols/B.svg" alt="Black Mana" height="20"></td>
      <td>Black Mana</td>
    </tr>
    <tr>
      <td><img src="https://svgs.scryfall.io/card-symbols/R.svg" alt="Red Mana" height="20"></td>
      <td>Red Mana</td>
    </tr>
    <tr>
      <td><img src="https://svgs.scryfall.io/card-symbols/G.svg" alt="Green Mana" height="20"></td>
      <td>Green Mana</td>
    </tr>
    <tr>
      <td><img src="https://svgs.scryfall.io/card-symbols/T.svg" alt="Tap" height="20"></td>
      <td>Tap ability</td>
    </tr>
  </table>
</div>

#### ❤️ **Favorite List**
Allows users to add or remove cards from their favorite list.
#### ⚙️ **Configure view**
Allows to toggle between text/image views and dark/light mode. The text view allows to configure which properties to show.

## Screenshots

## Tech Stack

*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose
*   **Architectural Pattern:** MVVM (Model-View-ViewModel)
*   **Libraries:**
    *   **Coil:** For fast and efficient image loading and caching.
    *   **Retrofit:** For making network requests and interacting with the Magic: The Gathering API.
    *   **ViewModel:** For managing and storing UI-related data.
    *   **Compose Foundation:** For essential UI building blocks, including the `Image` composable.
    *   **Compose UI:** For a wide range of UI components and functionality, such as `painterResource` and other composables.
    * **Compose Lazy Grid**: To create dynamic grids, with the `LazyVerticalGrid`.
* **Tools:**
    * **Gradle:** For managing dependencies and handling the build process.
    * **Git:** For version control and collaboration.

## Installation

1.  **Prerequisites:**
    *   Android Studio (latest stable version is recommended)
    *   Android SDK (configured within Android Studio)
    * Git
   2.  **Clone the Repository:**
   `bash git clone https://github.com/dhsudev/android-api-list-dhsudev`
## Usage

1.  Launch the app on your Android device or emulator.
2.  Scroll vertically through the list of cards.
3.  As you approach the end of the list, more cards will be automatically loaded.
4. Examine the card's properties, you will see its name, type, colors, mana cost, and the mana symbols.
5. Every card that has colors will have a list of emojis at the end of the card.

## Contact
[Lua] - [lua@gnomo.net]

Feel free to contact me via email or open an issue on GitHub if you have any questions, suggestions, or feedback.