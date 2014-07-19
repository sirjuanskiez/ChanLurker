package com.bellotech.chanlurker.parsers;

import com.bellotech.chanlurker.data.Board;
import com.bellotech.chanlurker.data.Boards;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Juan on 7/18/2014.
 */
public class BoardListLoader {
    private final String BOARDS_URL = "http://a.4cdn.org/boards.json";
    BoardListLoader(){};

    public static List<Board> LoadBoardList(){
        try{
            return new BoardListLoader().buildBoardList();
        }catch (MalformedURLException me){

        } catch (IOException e){

        }
        return null;
    }

    private List<Board> buildBoardList() throws MalformedURLException, IOException{
        String json = IOUtils.toString(new URL(BOARDS_URL));
        JsonReader reader = new JsonReader(new StringReader(json));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Boards boards = gson.fromJson(reader, Boards.class);
        return boards.getBoards();
    }
}
