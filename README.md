Fetch Rewards App

This is a native Android app built in Kotlin that retrieves and displays data from the Fetch Rewards API.

<p align="center">
    <img src="https://github.com/user-attachments/assets/cbe36fc3-9e03-4c51-b52c-53dcd6d8d2a4" width="30%" />
    <img src="https://github.com/user-attachments/assets/b13ab2c6-358e-4b99-a543-af19da7ff374" width="30%" />
    <img src="https://github.com/user-attachments/assets/bd20a6f3-6766-4ceb-a7b3-edbc827e3624" width="30%" />
</p>

Required Features
- Display all the items grouped by "listId"
- Sort the results first by "listId" then by "name" when displaying
- Filter out any items where "name" is blank or null

Additional Features/Considerations
- Brand-aligned color pallette for a cohesive look
- Expandable list buttons improve readability by grouping items by listId, reducing excessive scrolling
- Dynamically adjusting layout for different screen sizes, ensuring a smooth experience across various devices

Tech Stack
- Language: Kotlin
- Framework: Jetpack Compose
- Networking: Retrofit
- State Management: ViewModel, StateFlow

