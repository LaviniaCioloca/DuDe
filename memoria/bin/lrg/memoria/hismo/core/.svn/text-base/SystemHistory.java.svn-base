package lrg.memoria.hismo.core;

import lrg.memoria.core.ModelElementList;
import lrg.memoria.core.Namespace;
import lrg.memoria.core.Package;

import java.util.Iterator;

public class SystemHistory extends AbstractHistory {

    private String name;
    private PackageHistoryGroup packageHistories;
    private NamespaceHistoryGroup namespaceHistories;

    public SystemHistory(VersionsList versions, String name) {
        super(versions);
        this.name = name;
    }

    public SystemHistory(String name) {
        this.name = name;
        initializeInnerHistories();
    }

    protected void initializeInnerHistories() {
        packageHistories = new PackageHistoryGroup();
        namespaceHistories = new NamespaceHistoryGroup();
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return name;
    }

    public PackageHistoryGroup getPackageHistories() {
        return packageHistories;
    }

    public NamespaceHistoryGroup getNamespaceHistories() {
        return namespaceHistories;
    }

    public ClassHistoryGroup getClassHistories() {
        ClassHistoryGroup allClassHistories = new ClassHistoryGroup();
        ClassHistoryGroup currentClassHistories;
        for (Iterator<AbstractHistory> it = getNamespaceHistories().getHistoriesCollection().iterator(); it.hasNext();) {
            currentClassHistories = ((NamespaceHistory) it.next()).getClassHistories();
            allClassHistories.addAll(currentClassHistories);
        }
        return allClassHistories;
    }

    /**
     * @param aSystemHistory
     * @param aVersionName
     * @return null if aVersionName represents the first version of the system or if aVersionName
     *         is an invalid version name
     */
    public static String getVersionNamePreviousTo(SystemHistory aSystemHistory, String aVersionName) {
        String previous = null;
        for (Iterator it = aSystemHistory.getVersionIterator(); it.hasNext();) {
            String current = ((SystemVersion) it.next()).versionName();
            if (current.equals(aVersionName))
                return previous;
            previous = current;
        }
        return null;
    }

    /*------------------------ Construction ---------------------------*/

    protected void updateInnerHistories(AbstractVersion version) {
        updatePackageHistories((SystemVersion) version);
        updateNamespaceHistories((SystemVersion) version);
    }

    private void updatePackageHistories(SystemVersion version) {
        ModelElementList<Package> currentPackages = version.getPackages();
        PackageVersion currentPackageVersion;
        String currentPackageName;
        AbstractHistory currentPackageHistory;
        for (Package currentPackage : currentPackages) {
            currentPackageVersion = new PackageVersion(version.versionName(), currentPackage);
            currentPackageName = currentPackage.getName();
            currentPackageHistory = packageHistories.get(currentPackageName);
            if (currentPackageHistory == null) {
                currentPackageHistory = new PackageHistory();
                currentPackageHistory.addVersion(currentPackageVersion);
                packageHistories.put(currentPackageHistory);
            } else
                currentPackageHistory.addVersion(currentPackageVersion);
        }
    }

    private void updateNamespaceHistories(SystemVersion version) {
        ModelElementList<Namespace> currentNamespaces = version.getNamespaces();
        NamespaceVersion currentNamespaceVersion;
        String currentNamespaceName;
        AbstractHistory currentNamespaceHistory;
        for (Namespace currentNamespace : currentNamespaces) {
            currentNamespaceVersion = new NamespaceVersion(version.versionName(), currentNamespace);
            currentNamespaceName = currentNamespace.getName();
            currentNamespaceHistory = namespaceHistories.get(currentNamespaceName);
            if (currentNamespaceHistory == null) {
                currentNamespaceHistory = new NamespaceHistory();
                currentNamespaceHistory.addVersion(currentNamespaceVersion);
                namespaceHistories.put(currentNamespaceHistory);
            } else
                currentNamespaceHistory.addVersion(currentNamespaceVersion);
        }
    }
}
