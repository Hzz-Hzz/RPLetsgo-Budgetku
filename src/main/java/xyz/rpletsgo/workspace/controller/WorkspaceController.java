package xyz.rpletsgo.workspace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
