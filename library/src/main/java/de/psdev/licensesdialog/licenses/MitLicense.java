
package de.psdev.licensesdialog.licenses;

import android.content.Context;

import de.psdev.psdev.licensesdialog.R;

public class MitLicense extends License {

    private static final long serialVersionUID = 4854000031990891449L;

    @Override
    public String getName() {
        return "The MIT License (MIT)";
    }

    @Override
    public String readSummaryTextFromResources(final Context context) {
        return getContent(context, R.raw.mit_summary);
    }

    @Override
    public String readFullTextFromResources(final Context context) {
        return getContent(context, R.raw.mit_summary);
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getUrl() {
        return "https://opensource.org/licenses/MIT";
    }


}
