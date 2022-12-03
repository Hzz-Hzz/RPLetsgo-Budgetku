package xyz.rpletsgo.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.workspace.core.IWorkspace;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.service.WorkspaceService;

import java.util.List;

@Controller
@RequestMapping("/workspace")
public class WorkspaceController {
    @Autowired
    WorkspaceService workspaceService;

    @PostMapping("/create")
    @ResponseBody
    public Workspace createWorkspace (@RequestParam String nama){
        Workspace workspace = workspaceService.createWorkspace(nama);
        return workspace;
    }

    @PostMapping("/update/{workspaceId}")
    @ResponseBody
    public String updateWorkspace (@PathVariable(name = "workspaceId") String workspaceId, @RequestParam String nama){
        workspaceService.updateWorkspace(workspaceId, nama);
        return "Workspace updated";
    }

    @GetMapping("/get/{workspaceId}")
    @ResponseBody
    public IWorkspace getWorkspace (@PathVariable(name = "workspaceId") String workspaceId){
        return workspaceService.getWorkspace(workspaceId);
    }

    @GetMapping("/get")
    @ResponseBody
    public List<Workspace> getWorkspace () {
        return workspaceService.getWorkspaces();
    }

    @PostMapping("/delete/{workspaceId}")
    @ResponseBody
    public String deleteWorkspace (@PathVariable(name = "workspaceId") String workspaceId){
        workspaceService.deleteWorkspace(workspaceId);
        return "Workspace deleted";
    }
}
