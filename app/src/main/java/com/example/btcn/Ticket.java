package com.example.btcn;

import java.util.Date;

public class Ticket {
    private String id;
    private String userId;
    private String showtimeId;
    private String movieTitle;
    private String theaterName;
    private Date bookingDate;
    private String seatNumber;

    public Ticket() {}

    public Ticket(String id, String userId, String showtimeId, String movieTitle, String theaterName, Date bookingDate, String seatNumber) {
        this.id = id;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.movieTitle = movieTitle;
        this.theaterName = theaterName;
        this.bookingDate = bookingDate;
        this.seatNumber = seatNumber;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getShowtimeId() { return showtimeId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getTheaterName() { return theaterName; }
    public void setTheaterName(String theaterName) { this.theaterName = theaterName; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
}