//WatchlistMovie extends the Movie Class as it has an added variable
public class WatchlistMovie extends Movie {
    private double runtime;
   
//no-arg constructor
    public WatchlistMovie(){

    }

    public WatchlistMovie(double runtime){
        this.runtime = runtime;
    }

    public WatchlistMovie(String title, String genre,String platform, double runtime ) {
        super(title, genre, platform); //calling the parent class variables
        this.runtime = runtime;
        
    }
    
  //Getter and Setter for the runtime variable
    public double getRuntime() {
        return runtime;
    }

    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }

}
