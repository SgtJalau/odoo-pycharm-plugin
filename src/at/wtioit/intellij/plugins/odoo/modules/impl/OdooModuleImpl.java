package at.wtioit.intellij.plugins.odoo.modules.impl;

import at.wtioit.intellij.plugins.odoo.models.OdooModel;
import at.wtioit.intellij.plugins.odoo.modules.OdooManifest;
import at.wtioit.intellij.plugins.odoo.modules.OdooModule;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.SystemIndependent;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OdooModuleImpl implements OdooModule {

    private final PsiDirectory directory;
    private final OdooManifest manifest;
    private List<OdooModel> models = Collections.emptyList();

    OdooModuleImpl(PsiDirectory moduleDir) {
        directory = moduleDir;
        PsiFile manifestFile = moduleDir.findFile("__manifest__.py");
        manifest = manifestFile == null ? null : OdooManifestParser.parse(manifestFile);
    }

    @Override
    public String getName() {
        return directory.getName();
    }

    @Override
    public PsiElement getDirectory() {
        return directory;
    }

    @Override
    public Icon getIcon() {
        return directory.getIcon(0);
    }

    @Override
    public String getRelativeLocationString() {
        String locationString = directory.getPresentation().getLocationString();
        @SystemIndependent String basePath = directory.getProject().getBasePath();
        if (locationString.startsWith(basePath)) {
            return locationString.substring(basePath.length() + 1);
        }
        return locationString;
    }

    @Override
    public Collection<OdooModule> getDependencies(){
        return manifest.getDependencies();
    }

    @Override
    public void setModels(List<OdooModel> models) {
        this.models = models;
    }

    @Override
    public List<OdooModel> getModels() {
        return models;
    }
}
