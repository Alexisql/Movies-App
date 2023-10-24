package com.alexis.moviesapp.data.room.entities

import com.alexis.moviesapp.data.builder.MovieEntityBuilder
import org.junit.Assert.*
import org.junit.Test

class MovieEntityTest{

    @Test
    fun `convert movie data to movie domain validate movie successful`(){
        //Arrange
        val movieRoom = MovieEntityBuilder()
            .withId(575264)
            .build()
        //Act
        val movieDomain = movieRoom.toDomain()
        //Assert
        assertEquals(movieRoom.id, movieDomain.id)
    }

}