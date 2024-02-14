package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.PlayList;
import com.example.demo.entities.Songs;
import com.example.demo.services.PlayListService;

import com.example.demo.services.SongsService;

@Controller
public class PlayListController {
	
	@Autowired
	PlayListService pserv;
	
	@Autowired
	SongsService sserv;

	private Object playlist;
	
	@GetMapping("/createplaylist")
	public String createPlaylist(Model model)
	{
		//fetching the songs using songs service
		List<Songs> songslist = sserv.fetchAllSongs();
		
		//adding the songs in the model
		model.addAttribute("songslist", songslist);
		
		//sending create playlist
		return "createplaylist";
	}
	
	@PostMapping("/addplaylist")
	public String addPlayList(@ModelAttribute PlayList playlist)
	{
		//adding songs to playlist
		pserv.addPlayList(playlist);
		
		//update song table
		
		List<Songs> songList= playlist.getSong();
		
		for(Songs song : songList)
		{
			song.getPlaylist().add(playlist);
			sserv.updateSong(song);
		}
		return "playlistsuccess";
	}
	
	@GetMapping("/viewPlaylist")
	public String viewPlaylist(Model model)
	{
		List<PlayList> plist = pserv.fetchPlaylists();
		model.addAttribute("plist",plist);
		return "viewPlaylist";
		
	}
	
	
	
	
}