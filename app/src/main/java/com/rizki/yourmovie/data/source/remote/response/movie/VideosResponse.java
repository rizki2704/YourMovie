package com.rizki.yourmovie.data.source.remote.response.movie;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VideosResponse{

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<VideosItem> results;

	public int getId(){
		return id;
	}

	public List<VideosItem> getResults(){
		return results;
	}
}