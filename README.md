🎬 MoodyBinge – An AI-Powered Movie Discovery App
🚀 Overview

MoodyBinge is a movie discovery and review platform designed to help users pick what to watch next. It combines Firebase backend, AI-powered recommendations, and a clean Netflix-inspired UI.

The app was developed as part of the “Tiny AI-Powered App” internship assignment, with the goal of building something simple yet impactful that demonstrates real-world AI integration.

💡 Problem Statement

Choosing a movie is hard — endless scrolling often leads to frustration. Users also want to see authentic reviews, but existing platforms either feel too bloated or lack personalization.

🎯 Solution

MoodyBinge solves this by offering:

AI-Assisted Recommendations – Suggests movies based on genre, mood, and past preferences (powered by recommendation logic + extendable with AI APIs).

Smart Review System – Users can write reviews and rate movies. In future versions, reviews can be summarized with NLP to generate quick pros/cons.

Multi-Language Support – English, Hindi, and Spanish translations via Android localization.

Firebase Integration – Real-time review posting, editing, and deletion.

⚙️ Tech Stack

Frontend: Android Studio (Java, XML, Material UI)

Backend: Firebase Firestore + Firebase Auth

AI Angle:

Simple recommendation logic (genre/mood based).

Future-ready for GPT/NLP integration (review summarization).

UI Inspiration: Netflix theme (dark mode, white text, red accents).

🔑 Features

✅ Browse and pick movies (TMDB API support).
✅ Write, edit, and delete reviews.
✅ Star-based ratings.
✅ AI-powered recommendation logic.
✅ Multilingual interface (English, Hindi, Spanish).
✅ Real-time database sync with Firebase.

📸 Screenshots

(Include the screenshots you already took of reviews, stars, black theme, etc.)

🧪 Journey & Learnings

This project was a crash course in full-stack mobile development:

Challenges faced:

Firebase initialization crashes.

Language change not reflecting.

Review submission not saving to Firestore.

Rating stars invisible in dark theme.

Fixes applied:

Added FirebaseApp initialization in MainActivity.

Proper localized strings.xml folders.

Firestore security rules for authenticated reviews.

Customized RatingBar + text colors for dark mode.

Lessons learned:

Debugging with Logcat is invaluable.

Iterating fast is better than aiming for perfect code.

Even a “tiny app” can teach a lot when real-world integrations are added.
