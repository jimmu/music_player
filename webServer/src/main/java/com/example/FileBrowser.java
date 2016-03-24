package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.example.model.FilesystemEntryBean;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

@Path("files")
public class FileBrowser {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilesystemEntryBean> rootList() {
        List<FilesystemEntryBean> files = new ArrayList<FilesystemEntryBean>();
        try{
            File rootDir = new File("."); //TODO. Find the root of the music tree
            File[] allFilesAndDirs = rootDir.listFiles();
            for (File file : allFilesAndDirs){
                files.add(new FilesystemEntryBean(file.getName(), file.getCanonicalPath().hashCode()));
            } 
        }
        catch(IOException e){
            files.add(new FilesystemEntryBean(e.toString(), 0));
        }
        //files.add(new FilesystemEntryBean("One", 1));
        //files.add(new FilesystemEntryBean("Two", 2));
        return files;
    }
}
