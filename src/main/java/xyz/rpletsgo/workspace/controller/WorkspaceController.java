package xyz.rpletsgo.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.rpletsgo.workspace.service.WorkspaceService;

@Controller
@RequestMapping("/workspace")
public class WorkspaceController {
    @Autowired
    WorkspaceService workspaceService;

    @PostMapping("/create")
    @ResponseBody
    public String createWorkspace (@RequestParam String nama){
        workspaceService.createWorkspace(nama);
        return "Workspace created";
    }

    @PostMapping("/update/{workspaceId}")
    @ResponseBody
    public String updateWorkspace (@PathVariable(name = "workspaceId") String workspaceId, @RequestParam String nama){
        workspaceService.updateWorkspace(workspaceId, nama);
        return "Workspace updated";
    }

    @GetMapping("/get/{workspaceId}")
    @ResponseBody
    public String getWorkspace (@PathVariable(name = "workspaceId") String workspaceId){
        workspaceService.getWorkspace(workspaceId);
        return "Workspace retrieved";
    }

    @PostMapping("/delete/{workspaceId}")
    @ResponseBody
    public String deleteWorkspace (@PathVariable(name = "workspaceId") String workspaceId){
        workspaceService.deleteWorkspace(workspaceId);
        return "Workspace deleted";
    }
}
