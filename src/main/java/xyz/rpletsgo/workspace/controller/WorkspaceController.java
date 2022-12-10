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
@RequestMapping("/workspace")
public class WorkspaceController {
    @Autowired
    WorkspaceService workspaceService;

    @PostMapping("/create")
    public String createWorkspace (
            @RequestParam String nama
    ){
        workspaceService.createWorkspace(nama);
        return "redirect:/list";
    }

    @GetMapping("workspace/create")
    public String getWorkspaceListAfterCreate(){
        return "workspace/workspace-create.html";
    }

    @PostMapping("/update/{workspaceId}")
    public String updateWorkspace (
            @RequestParam String nama,
            @RequestParam String workspaceId
    ){
        workspaceService.updateWorkspace(workspaceId, nama);
        return "success";
    }

    @GetMapping("/get/{workspaceId}")
    @ResponseBody
    public IWorkspace getWorkspace (@PathVariable(name = "workspaceId") String workspaceId){
        return workspaceService.getWorkspace(workspaceId);
    }

    @GetMapping("/list")
    public String getWorkspace (
            Model model
    ) {
        model.addAttribute("workspaces",
                workspaceService.getWorkspaces());
        return "workspace/workspace-list.html";
    }

    @PostMapping("/delete/{workspaceId}")
    public String deleteWorkspace (
            @RequestParam String workspaceId
    ){
        workspaceService.deleteWorkspace(workspaceId);
        return "Workspace deleted";
    }
}
