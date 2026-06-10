package com.comiccon.repository;

import com.comiccon.entity.Artist;

public class ArtistRepository extends GenericRepository<Artist, Integer> {
    public ArtistRepository() { super(Artist.class); }
}
