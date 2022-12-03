package xyz.rpletsgo.workspace.core;

import xyz.rpletsgo.workspace.model.Workspace;

public class WorkspaceFactory {
    public Workspace create(String nama){
        var ret = new Workspace();
        ret.initialize();
        ret.setNama(nama);
        
        return ret;
    }
}
