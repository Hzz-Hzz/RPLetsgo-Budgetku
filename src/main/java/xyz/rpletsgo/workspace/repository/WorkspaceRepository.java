package xyz.rpletsgo.workspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.rpletsgo.workspace.model.Workspace;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, String> {
    @Override
    public abstract Optional<Workspace> findById(String s);
}
