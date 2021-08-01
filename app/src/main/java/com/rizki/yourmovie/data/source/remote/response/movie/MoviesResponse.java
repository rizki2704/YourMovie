package com.rizki.yourmovie.data.source.remote.response.movie;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MoviesResponse{

	@SerializedName("results")
	private List<MoviesItem> results;

	public List<MoviesItem> getResults(){
		return results;
	}

}