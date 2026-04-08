package com.example.btcn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        rvMovies = findViewById(R.id.rvMovies);
        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(movieList, this);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(movieAdapter);

        fetchMovies();
    }

    private void fetchMovies() {
        db.collection("movies")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        movieList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = document.toObject(Movie.class);
                            movie.setId(document.getId());
                            movieList.add(movie);
                        }
                        movieAdapter.notifyDataSetChanged();
                        
                        // Add sample data if empty for demo purposes
                        if (movieList.isEmpty()) {
                            addSampleMovies();
                        }
                    } else {
                        Log.w("MainActivity", "Error getting documents.", task.getException());
                        Toast.makeText(this, "Failed to load movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addSampleMovies() {
        Movie movie1 = new Movie(null, "Avengers: Endgame", "After the devastating events...", "url1", 9.0);
        Movie movie2 = new Movie(null, "Joker", "In Gotham City, mentally troubled comedian...", "url2", 8.5);
        
        db.collection("movies").add(movie1);
        db.collection("movies").add(movie2).addOnSuccessListener(documentReference -> fetchMovies());
    }

    @Override
    public void onBookClick(Movie movie) {
        String userId = mAuth.getCurrentUser().getUid();
        String ticketId = UUID.randomUUID().toString();
        
        // Simplified booking: create a ticket directly
        Ticket ticket = new Ticket(
                ticketId,
                userId,
                "sample_showtime_id",
                movie.getTitle(),
                "Grand Cinema",
                new Date(),
                "A1"
        );

        db.collection("tickets").document(ticketId)
                .set(ticket)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivity.this, "Ticket booked for " + movie.getTitle(), Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Booking failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}