
package de.psdev.licensesdialog.licenses;

import android.content.Context;

import de.psdev.psdev.licensesdialog.R;

public class CreativeCommonsAttribution25Generic extends License {

    @Override
    public String getName() {
        return "Attribution 2.5 Generic (CC BY 2.5)";
    }

    @Override
    public String readSummaryTextFromResources(final Context context) {
        return getContent(context, R.raw.ccand_25_summary);
    }

    @Override
    public String readFullTextFromResources(final Context context) {
        return getContent(context, R.raw.ccand_25_summary);
    }

    @Override
    public String getVersion() {
        return "3.0";
    }

    @Override
    public String getUrl() {
        return "http://creativecommons.org/licenses/by-nd/3.0/";
    }

}
