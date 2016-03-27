package com.bobbins.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.bobbins.model.FilesystemEntryBean;
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
        System.out.println("Listing "+path);
        List<FilesystemEntryBean> files = new ArrayList<FilesystemEntryBean>();
        try {
            if (rootPath == null) {
                rootPath = new File(".").getCanonicalPath();
            }
            File[] allFilesAndDirs = new File(path).listFiles();
            if (allFilesAndDirs != null) {
                for (File file : allFilesAndDirs) {
                    String fullPath = file.getCanonicalPath();
                    String shortPath = fullPath.substring(rootPath.length() + 1);
                    System.out.println(file.getName()+" is a leaf? "+!file.isDirectory());
                    files.add(new FilesystemEntryBean(file.getName(),
                            fullPath.hashCode(),
                            shortPath,
                            !file.isDirectory(),
                            "files/list?path=" + shortPath,
                            "play?path=" + shortPath));
                }
            }
        }
        catch(IOException e){
            files.add(new FilesystemEntryBean(e.toString(), 0, "", false, "", ""));
        }
        return files;
    }

}
