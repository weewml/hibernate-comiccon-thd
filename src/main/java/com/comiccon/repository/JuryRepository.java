package com.comiccon.repository;

import com.comiccon.entity.Jury;

public class JuryRepository extends GenericRepository<Jury, Integer> {
    public JuryRepository() { super(Jury.class); }
}
