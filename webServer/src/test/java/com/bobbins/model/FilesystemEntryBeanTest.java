package com.bobbins.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilesystemEntryBeanTest {

    @Test
    public void testConstructArtist(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", null, null);
        assertTrue(bean.isArtist);
        assertFalse(bean.isAlbum);
        assertFalse(bean.isSong);
        assertEquals(FilesystemEntryBean.LIST_BASE_URL+"/artist1", bean.listActionUrl);
        assertNull(bean.playActionUrl);
        assertEquals("artist1", bean.name);
    }

    @Test
    public void testConstructAlbum(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", "album1", null);
        assertFalse(bean.isArtist);
        assertTrue(bean.isAlbum);
        assertFalse(bean.isSong);
        assertEquals(FilesystemEntryBean.LIST_BASE_URL+"/artist1/album1", bean.listActionUrl);
        assertEquals(FilesystemEntryBean.PLAY_BASE_URL+"/artist1/album1", bean.playActionUrl);
        assertEquals("album1", bean.name);
    }

    @Test
    public void testConstructSong(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", "album1", "song1");
        assertFalse(bean.isArtist);
        assertFalse(bean.isAlbum);
        assertTrue(bean.isSong);
        assertNull(bean.listActionUrl);
        assertEquals(FilesystemEntryBean.PLAY_BASE_URL+"/artist1/album1/song1", bean.playActionUrl);
        assertEquals("song1", bean.name);
    }

    @Test
    public void testToString(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", "album1", "song1");
        assertTrue(bean.toString().contains("artist1"));
        assertTrue(bean.toString().contains("album1"));
        assertTrue(bean.toString().contains("song1"));
    }

    @Test
    public void testEquals(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", "album1", "song1");
        FilesystemEntryBean similarBean = new FilesystemEntryBean("artist1", "album1", "song1");
        assertEquals(bean, similarBean);
    }

    @Test
    public void testNotEquals(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", "album1", "song1");
        FilesystemEntryBean dissimilarBean = new FilesystemEntryBean("artist1", "album2", "song1");
        assertFalse(bean.equals(dissimilarBean));
    }

    @Test
    public void testHashCodeEqualObjects(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", "album1", "song1");
        FilesystemEntryBean similarBean = new FilesystemEntryBean("artist1", "album1", "song1");
        assertTrue(bean.hashCode() == similarBean.hashCode());
    }

    @Test
    public void testHashCodeUnequalObjects(){
        FilesystemEntryBean bean = new FilesystemEntryBean("artist1", "album1", "song1");
        FilesystemEntryBean similarBean = new FilesystemEntryBean("artist1", "album1", "song2");
        assertFalse(bean.hashCode() == similarBean.hashCode());
    }
}
