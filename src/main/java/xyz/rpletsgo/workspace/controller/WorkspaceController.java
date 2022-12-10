package xyz.rpletsgo.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.rpletsgo.workspace.core.IWorkspace;
import xyz.rpletsgo.workspace.model.Workspace;
import xyz.rpletsgo.workspace.service.WorkspaceService;

import java.util.List;

@Controller
@RequestMapping("/")
public class WorkspaceController {
    @Autowired
    WorkspaceService workspaceService;

    @PostMapping("/workspace/create")
    @ResponseBody
    public Workspace createWorkspace (@RequestParam String nama){
        return workspaceService.createWorkspace(nama);
    }

    @PostMapping("/{workspaceId}/workspace/update")
    public String updateWorkspace (
            @RequestParam String nama,
            @RequestParam String workspaceId
    ){
        workspaceService.updateWorkspace(workspaceId, nama);
        return "success";
    }

    @GetMapping("/{workspaceId}/workspace/get")
    @ResponseBody
    public IWorkspace getWorkspace (@PathVariable(name = "workspaceId") String workspaceId){
        return workspaceService.getWorkspace(workspaceId);
    }

    @GetMapping("/workspace/get")
    public String getWorkspace (
            Model model
    ) {
        model.addAttribute("workspaces",
                workspaceService.getWorkspaces());
        return "workspace/workspace-list.html";
    }

    @PostMapping("/{workspaceId}/workspace/delete")
    public String deleteWorkspace (
            @RequestParam String workspaceId
    ){
        workspaceService.deleteWorkspace(workspaceId);
        return "Workspace deleted";
    }
}
