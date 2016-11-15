package com.bobbins;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlainPlayerTest {
    @Test
    public void testList() throws PlayerException{
        Player player = new PlainPlayer();
        System.out.println(player.list(null,null));
    }
}
