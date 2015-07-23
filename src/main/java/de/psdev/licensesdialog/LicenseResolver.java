
package de.psdev.licensesdialog;

import java.util.HashMap;
import java.util.Map;

import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.BSD3ClauseLicense;
import de.psdev.licensesdialog.licenses.CreativeCommonsAttribution25Generic;
import de.psdev.licensesdialog.licenses.FontAwesomeLicense;
import de.psdev.licensesdialog.licenses.License;

public final class LicenseResolver {

    private static final int INITIAL_LICENSES_COUNT = 4;
    private static final Map<String, License> sLicenses = new HashMap<String, License>(INITIAL_LICENSES_COUNT);

    static {
        registerDefaultLicenses();
    }

    static void registerDefaultLicenses() {
        sLicenses.clear();
        registerLicense(new ApacheSoftwareLicense20());
        registerLicense(new CreativeCommonsAttribution25Generic());
        registerLicense(new BSD3ClauseLicense());
        registerLicense(new FontAwesomeLicense());
    }

    /**
     * Register an additional license.
     *
     * @param license the license to register
     */
    public static void registerLicense(final License license) {
        sLicenses.put(license.getName(), license);
    }

    /**
     * Get a license by name
     *
     * @param license license name
     * @return License
     * @throws java.lang.IllegalStateException when unknown license is requested
     */
    public static License read(final String license) {
        final String trimmedLicense = license.trim();
        if (sLicenses.containsKey(trimmedLicense)) {
            return sLicenses.get(trimmedLicense);
        } else {
            throw new IllegalStateException(String.format("no such license available: %s, did you forget to register it?", trimmedLicense));
        }
    }

    private LicenseResolver() {
    }
}
