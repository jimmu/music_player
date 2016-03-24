package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.example.model.FilesystemEntryBean;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

@Path("files")
public class FileBrowser {

    private String rootPath = null;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilesystemEntryBean> rootList() {
      return list("."); //TODO. Find the root of the music tree
    }
 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public List<FilesystemEntryBean> list(@QueryParam("path") String path) {
        List<FilesystemEntryBean> files = new ArrayList<FilesystemEntryBean>();
        try{
            if (rootPath == null){
                rootPath = new File(".").getCanonicalPath();
            }
            File[] allFilesAndDirs = new File(path).listFiles();
            for (File file : allFilesAndDirs){
                String fullpath = file.getCanonicalPath();
                String shortPath = fullpath.substring(rootPath.length()+1);
                files.add(new FilesystemEntryBean(file.getName(),
                                                  fullpath.hashCode(),
                                                  shortPath,
                                                  "files/list?path="+path));
            } 
        }
        catch(IOException e){
            files.add(new FilesystemEntryBean(e.toString(), 0, "", ""));
        }
        return files;
    }

}
