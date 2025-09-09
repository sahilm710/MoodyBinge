package com.example.moodybinge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class QuotesFragment extends Fragment {

    private TextView quoteText;
    private Button btnNewQuote;
    private Random random = new Random();

    private String[] quotes = {
            "May the Force be with you. – Star Wars",
            "I’ll be back. – The Terminator",
            "Why so serious? – The Dark Knight",
            "Life is like a box of chocolates. – Forrest Gump",
            "I am Iron Man. – Avengers: Endgame",
            "Here’s looking at you, kid. – Casablanca",
            "With great power comes great responsibility. – Spider-Man",
            "You talking to me? – Taxi Driver",
            "Houston, we have a problem. – Apollo 13",
            "I see dead people. – The Sixth Sense",
            "Keep your friends close, but your enemies closer. – The Godfather Part II",
            "Say hello to my little friend! – Scarface",
            "Just keep swimming. – Finding Nemo",
            "Hasta la vista, baby. – Terminator 2: Judgment Day",
            "I feel the need... the need for speed! – Top Gun",
            "You can’t handle the truth! – A Few Good Men",
            "Why did it have to be snakes? – Raiders of the Lost Ark",
            "Here’s Johnny! – The Shining",
            "I’m king of the world! – Titanic",
            "To infinity and beyond! – Toy Story",
            "They may take our lives, but they’ll never take our freedom! – Braveheart",
            "I drink your milkshake! – There Will Be Blood",
            "I’ll have what she’s having. – When Harry Met Sally",
            "I am vengeance. I am the night. – Batman",
            "I volunteer as tribute! – The Hunger Games",
            "This is Sparta! – 300",
            "Roads? Where we’re going, we don’t need roads. – Back to the Future",
            "Do, or do not. There is no try. – Star Wars: The Empire Strikes Back",
            "Don’t ever let somebody tell you you can’t do something. – The Pursuit of Happyness",
            "Carpe diem. Seize the day, boys. – Dead Poets Society",
            "Great men are not born great, they grow great. – The Godfather",
            "It’s not our abilities that show what we truly are… it’s our choices. – Harry Potter and the Chamber of Secrets",
            "Happiness can be found, even in the darkest of times, if one only remembers to turn on the light. – Harry Potter and the Prisoner of Azkaban",
            "Why do we fall, sir? So that we can learn to pick ourselves up. – Batman Begins",
            "Oh yes, the past can hurt. But you can either run from it, or learn from it. – The Lion King",
            "The flower that blooms in adversity is the most rare and beautiful of all. – Mulan",
            "All we have to decide is what to do with the time that is given to us. – The Lord of the Rings",
            "Get busy living, or get busy dying. – The Shawshank Redemption",
            "Hope is a good thing, maybe the best of things, and no good thing ever dies. – The Shawshank Redemption",
            "Just keep swimming. – Finding Nemo",
            "It’s what you do right now that makes a difference. – Black Hawk Down",
            "Our lives are defined by opportunities, even the ones we miss. – The Curious Case of Benjamin Button",
            "Every man dies, not every man really lives. – Braveheart",
            "After all, tomorrow is another day. – Gone With the Wind",
            "Sometimes it is the people no one imagines anything of who do the things no one can imagine. – The Imitation Game",
            "A man who doesn’t spend time with his family can never be a real man. – The Godfather",
            "Fear is the path to the dark side. – Star Wars",
            "May the Force be with you. – Star Wars",
            "I’ll be back. – The Terminator",
            "Why so serious? – The Dark Knight",
            "Life is like a box of chocolates. – Forrest Gump",
            "I am Iron Man. – Avengers: Endgame",
            "Here’s looking at you, kid. – Casablanca",
            "With great power comes great responsibility. – Spider-Man",
            "You talking to me? – Taxi Driver",
            "I feel the need—the need for speed! – Top Gun",
            "Hasta la vista, baby. – Terminator 2",
            "Say hello to my little friend! – Scarface",
            "You can’t handle the truth! – A Few Good Men",
            "Keep your friends close, but your enemies closer. – The Godfather Part II",
            "They may take our lives, but they’ll never take our freedom! – Braveheart",
            "I see dead people. – The Sixth Sense",
            "You shall not pass! – The Lord of the Rings",
            "Here’s Johnny! – The Shining",
            "Bond. James Bond. – Dr. No",
            "To infinity… and beyond! – Toy Story",
            "I’m king of the world! – Titanic",
            "Yippee-ki-yay! – Die Hard",
            "Houston, we have a problem. – Apollo 13",
            "This is Sparta! – 300",
            "A martini. Shaken, not stirred. – Goldfinger",
            "Wax on, wax off. – The Karate Kid",
            "I volunteer as tribute! – The Hunger Games",
            "I wish I knew how to quit you. – Brokeback Mountain",
            "Elementary, my dear Watson. – The Adventures of Sherlock Holmes",
            "I drink your milkshake! – There Will Be Blood",
            "You’re gonna need a bigger boat. – Jaws",
            "It’s alive! – Frankenstein",
            "I’m always angry. – The Avengers",
            "Avengers, assemble! – Avengers: Endgame",
            "Wakanda forever! – Black Panther",
            "Part of the journey is the end. – Avengers: Endgame",
            "Just because something works doesn’t mean it can’t be improved. – Black Panther",
            "Dreams feel real while we’re in them. – Inception",
            "A dream is not reality, but who’s to say which is which? – Inception",
            "I wish there was a way to know you’re in the good old days before you’ve actually left them. – The Office (Movie-like line)",
            "Love means never having to say you’re sorry. – Love Story",
            "You either die a hero, or you live long enough to see yourself become the villain. – The Dark Knight",
            "It’s not who I am underneath, but what I do that defines me. – Batman Begins",
            "Freedom! – Braveheart",
            "To boldly go where no man has gone before. – Star Trek",
            "Roads? Where we’re going, we don’t need roads. – Back to the Future",
            "ET phone home. – E.T.",
            "Keep the change, ya filthy animal. – Home Alone",
            "A dream is not reality, but who’s to say which is which? – Inception",



    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        quoteText = view.findViewById(R.id.quoteText);
        btnNewQuote = view.findViewById(R.id.btnNewQuote);

        // Show initial random quote
        showRandomQuote();

        // Button click → show new random quote
        btnNewQuote.setOnClickListener(v -> showRandomQuote());

        return view;
    }

    private void showRandomQuote() {
        int index = random.nextInt(quotes.length);
        quoteText.setText(quotes[index]);
    }
}
