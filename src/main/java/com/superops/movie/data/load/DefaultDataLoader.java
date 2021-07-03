package com.superops.movie.data.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DefaultDataLoader {

    @Autowired
    private UserLoader userLoader;

    @Autowired
    private TheatreLoader theatreLoader;

    @PostConstruct
    public void loadDefaultData(){
        userLoader.addUsers();
        theatreLoader.addTheatres();
    }
}
