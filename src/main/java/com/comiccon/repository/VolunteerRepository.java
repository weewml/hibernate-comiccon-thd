package com.comiccon.repository;

import com.comiccon.entity.Volunteer;

public class VolunteerRepository extends GenericRepository<Volunteer, Integer> {
    public VolunteerRepository() { super(Volunteer.class); }
}
