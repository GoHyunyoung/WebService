package koreatech.cse.domain;

public class SearchMovie
{
    private String pubDate;

    private String title;

    private String userRating;

    private String link;

    private String subtitle;

    private String image;

    private String actor;

    private String director;

    private DirectorRelatedMovies[] directorRelatedMovies;

    public String getPubDate ()
    {
        return pubDate;
    }

    public void setPubDate (String pubDate)
    {
        this.pubDate = pubDate;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getUserRating ()
    {
        return userRating;
    }

    public void setUserRating (String userRating)
    {
        this.userRating = userRating;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public String getSubtitle ()
    {
        return subtitle;
    }

    public void setSubtitle (String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getActor ()
    {
        return actor;
    }

    public void setActor (String actor)
    {
        this.actor = actor;
    }

    public String getDirector ()
    {
        return director;
    }

    public void setDirector (String director)
    {
        this.director = director;
    }

    public DirectorRelatedMovies[] getDirectorRelatedMovies ()
    {
        return directorRelatedMovies;
    }

    public void setDirectorRelatedMovies (DirectorRelatedMovies[] directorRelatedMovies)
    {
        this.directorRelatedMovies = directorRelatedMovies;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pubDate = "+pubDate+", title = "+title+", userRating = "+userRating+", link = "+link+", subtitle = "+subtitle+", image = "+image+", actor = "+actor+", director = "+director+", directorRelatedMovies = "+directorRelatedMovies+"]";
    }
}