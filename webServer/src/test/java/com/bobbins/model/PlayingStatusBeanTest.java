package com.bobbins.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayingStatusBeanTest {


    @Test
    public void testConstructor(){
        PlayingStatusBean bean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 2L);
        assertEquals("name", bean.name);
        assertEquals(Integer.valueOf(25), bean.volume);
        assertTrue(bean.isPlaying);
        assertEquals(Integer.valueOf(100), bean.songLength);
        assertEquals(Long.valueOf(2), bean.elapsedTime);
        assertEquals(bean.volumeUrl+26, bean.volumeUpUrl);
        assertEquals(bean.volumeUrl+24, bean.volumeDownUrl);
    }

    @Test
    public void testToString(){
        PlayingStatusBean bean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 3L);
        assertTrue(bean.toString().contains("name"));
        assertTrue(bean.toString().contains("3"));
        assertTrue(bean.toString().contains("100"));
        assertTrue(bean.toString().contains("true"));
    }

    @Test
    public void testEquals(){
        PlayingStatusBean bean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 3L);
        PlayingStatusBean similarBean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 3L);
        System.out.println(bean.equals(similarBean));
        assertEquals(bean, similarBean);
    }

    @Test
    public void testNotEquals(){
        PlayingStatusBean bean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 3L);
        PlayingStatusBean dissimilarBean = new PlayingStatusBean("name", 26, Boolean.TRUE, 100, 3L);
        assertFalse(bean.equals(dissimilarBean));
    }

    @Test
    public void testHashCodeEqualObjects(){
        PlayingStatusBean bean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 3L);
        PlayingStatusBean similarBean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 3L);
        assertEquals(bean.hashCode(), similarBean.hashCode());
    }

    @Test
    public void testHashCodeUnequalObjects(){
        PlayingStatusBean bean = new PlayingStatusBean("name", 25, Boolean.TRUE, 100, 3L);
        PlayingStatusBean dissimilarBean = new PlayingStatusBean("name", 26, Boolean.TRUE, 100, 3L);
        assertFalse(bean.hashCode() == dissimilarBean.hashCode());
    }
}
