package com.bellotech.chanlurker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 7/18/2014.
 */
public class Boards {
    private List<Board> boards = new ArrayList<Board>();

    public List<Board> getBoards(){
        return boards;
    }

    public void setBoards(List<Board> boards){
        this.boards = boards;
    }
}
